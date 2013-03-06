SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

/**
* type = ABC A=1按组织机构统计 B=1按操作人员统计 C=1按年统计 C=2按月统计 C=3按日统计
*/
CREATE PROC [dbo].[HIVAndSyphilisStatistic_SP](
	@where NVARCHAR(4000) = '',
	@type NVARCHAR(10) = '111',
	@inputPersonId NVARCHAR(30) = ''
)AS
BEGIN
	DECLARE @sql NVARCHAR(MAX),@groupCol NVARCHAR(MAX),@leftjoinsql NVARCHAR(MAX),
			@IsLookAuthority INT,@defaultWhere NVARCHAR(MAX),@orgId INT,
			@groupByCol NVARCHAR(MAX),@totalMarks NVARCHAR(MAX),@districtNum NVARCHAR(30)

	--权限控制
	SET @IsLookAuthority = ''
	SET @orgId = 0
	SET @districtNum = ''
	SELECT @IsLookAuthority = IsLookAuthority,@orgId = org_id,@districtNum = district_id FROM sam_taxempcode WHERE loginname = @inputPersonId
	IF @IsLookAuthority = 1
	BEGIN
		SET @defaultWhere = ' WHERE A.inputPersonId IN ( SELECT loginname FROM sam_taxempcode WHERE SUBSTRING(district_id,1,' + CAST(LEN(@districtNum) AS NVARCHAR(20)) + ') = ' + @districtNum + ')'
	END
	ELSE
	BEGIN
		SET @defaultWhere = ' WHERE A.inputPersonId IN ( SELECT loginname FROM sam_taxempcode WHERE org_id = ' + CAST(@orgId AS NVARCHAR(20)) + ')'
	END
	
	--分组设置
	SET @groupCol = ''
	SET @leftjoinsql = ''
	SET @groupByCol = ''
	SET @totalMarks = ''
	IF SUBSTRING(@type,1,1) = 1
	BEGIN
		SET @groupCol = ' C.ID,'''' AS loginname, '
		SET @groupByCol = ' C.ID'
		SET @leftjoinsql = ' LEFT JOIN sam_taxempcode B ON A.inputPersonId = B.loginname JOIN Organization C ON B.org_id = C.ID '
		SET @totalMarks = '''总计'','''','''''
	END
	IF SUBSTRING(@type,2,1) = 1
	BEGIN
		SET @groupCol = CASE @groupCol WHEN '' THEN ''''' AS ID, B.loginname,' ELSE 'C.ID,B.loginname,' END
		SET @groupByCol = CASE @groupByCol WHEN '' THEN ' B.loginname' ELSE @groupByCol + ' ,B.loginname' END
		SET @leftjoinsql = CASE @leftjoinsql WHEN '' THEN ' LEFT JOIN sam_taxempcode B ON A.inputPersonId = B.loginname ' ELSE @leftjoinsql END
		SET @totalMarks = CASE @totalMarks WHEN '' THEN ''''',''总计'',''''' ELSE @totalMarks END
	END	
	
	DECLARE @groupdate NVARCHAR(10)
	SET @groupdate = SUBSTRING(@type,3,1)
	IF @groupdate = 1
	BEGIN
		SET @groupCol = CASE @groupCol WHEN '' THEN ''''' AS ID, '''' AS loginname, Convert(Varchar(4),A.InputDate,120)GroupDate,' ELSE @groupCol + ' Convert(Varchar(4),A.InputDate,120)GroupDate,' END
		SET @groupByCol = CASE @groupByCol WHEN '' THEN ' Convert(Varchar(4),A.InputDate,120)' ELSE @groupByCol + ' ,Convert(Varchar(4),A.InputDate,120)' END
		SET @totalMarks = CASE @totalMarks WHEN '' THEN ''''','''',''总计''' ELSE @totalMarks END
	END
	ELSE IF SUBSTRING(@type,3,1) = 2
	BEGIN
		SET @groupCol = CASE @groupCol WHEN '' THEN ''''' AS ID, '''' AS loginname, Convert(Varchar(7),A.InputDate,120)GroupDate,' ELSE @groupCol + ' Convert(Varchar(7),A.InputDate,120)GroupDate,' END
		SET @groupByCol = CASE @groupByCol WHEN '' THEN ' Convert(Varchar(7),A.InputDate,120)' ELSE @groupByCol + ' ,Convert(Varchar(7),A.InputDate,120)' END
		SET @totalMarks = CASE @totalMarks WHEN '' THEN ''''','''',''总计''' ELSE @totalMarks END
	END
	ELSE IF SUBSTRING(@type,3,1) = 3
	BEGIN
		SET @groupCol = CASE @groupCol WHEN '' THEN ''''' AS ID, '''' AS loginname, Convert(Varchar(10),A.InputDate,120)GroupDate,' ELSE @groupCol + ' Convert(Varchar(10),A.InputDate,120)GroupDate,' END
		SET @groupByCol = CASE @groupByCol WHEN '' THEN ' Convert(Varchar(10),A.InputDate,120)' ELSE @groupByCol + ' ,Convert(Varchar(10),A.InputDate,120)' END
		SET @totalMarks = CASE @totalMarks WHEN '' THEN ''''','''',''总计''' ELSE @totalMarks END
	END
	ELSE
	BEGIN
		SET @groupCol = CASE @groupCol WHEN '' THEN ''''' AS ID, '''' AS loginname,'''' AS GroupDate,' ELSE @groupCol + ''''' AS GroupDate,' END
		SET @totalMarks = CASE @totalMarks WHEN '' THEN ''''','''',''''' ELSE @totalMarks END
	END
	SET @groupByCol = CASE @groupByCol WHEN '' THEN '' ELSE ' GROUP BY ' + @groupByCol END
	SET @sql = ' INSERT INTO SummaryStatisticsHIVAndSyphilis ' +
			' SELECT Lower(Replace(NewID(),''-'','''')),
			Row_Number() over (Order By A.ID) As RowID,''' + @inputPersonId + ''',
			A.HIVNegative,A.HIVMasculine,A.HIVOthers,
			A.SyphilisNegative,A.SyphilisMasculine,A.SyphilisOthers,
			ISNULL(B.Name,'''')AS OrgName,ISNULL(C.username,'''')AS Username,A.GroupDate,
			A.loginname FROM (SELECT ' + @groupCol + '
			Sum(CASE A.exam27 WHEN ''阴性'' THEN 1 ELSE 0 END)HIVNegative,
			Sum(CASE A.exam27 WHEN ''阳性'' THEN 1 ELSE 0 END)HIVMasculine,
			Sum(CASE WHEN A.exam27 = '''' Or A.exam27 IS NULL THEN 1 ELSE 0 END)HIVOthers,
			Sum(CASE A.exam28 WHEN ''阴性'' THEN 1 ELSE 0 END)SyphilisNegative,
			Sum(CASE A.exam28 WHEN ''阳性'' THEN 1 ELSE 0 END)SyphilisMasculine,
			Sum(CASE WHEN A.exam28 = '''' Or A.exam28 IS NULL THEN 1 ELSE 0 END)SyphilisOthers
			FROM FirstVistBeforeBorn A ' + @leftjoinsql + @defaultWhere + @where + 
			@groupByCol + ') A LEFT JOIN Organization B ON A.ID = B.ID LEFT JOIN sam_taxempcode C ON 
			A.loginname = C.loginname '
	EXEC('DELETE FROM SummaryStatisticsHIVAndSyphilis WHERE InputPersonID = ''' + @inputPersonId + '''')
	EXEC(@sql)
	
	DECLARE @maxrowid int,@sql1 NVARCHAR(MAX)
	SET @maxrowid = 0
	SELECT @maxrowid = MAX(RowID) FROM SummaryStatisticsHIVAndSyphilis WHERE InputPersonID = @inputPersonId
	SET @maxrowid = @maxrowid + 1
	SET @sql1 = ' INSERT INTO SummaryStatisticsHIVAndSyphilis 
			SELECT Lower(Replace(NewID(),''-'','''')),' + 
			CAST(@maxrowid AS NVARCHAR(10)) + ',''' + @inputPersonId + ''',
			SUM(HIVNegative),SUM(HIVMasculine),SUM(HIVOthers),
			SUM(SyphilisNegative),SUM(SyphilisMasculine),SUM(SyphilisOthers),
			' + @totalMarks + ','''' 
			FROM SummaryStatisticsHIVAndSyphilis WHERE InputPersonID = ''' + @inputPersonId + ''''
	EXEC(@sql1)
	EXEC('SELECT * FROM SummaryStatisticsHIVAndSyphilis WHERE InputPersonID = ''' + @inputPersonId + ''' ORDER BY RowID ')
	--PRINT @sql1
END
GO
