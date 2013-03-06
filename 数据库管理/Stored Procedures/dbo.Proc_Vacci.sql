SET QUOTED_IDENTIFIER OFF
GO
SET ANSI_NULLS ON
GO

CREATE PROC [dbo].[Proc_Vacci](
	@FileNo NVARCHAR(30) = ''
)AS
BEGIN
	SET NOCOUNT ON
	DECLARE @ColName NVARCHAR(MAX),--列名+值
			@Sql NVARCHAR(MAX),--待执行的SQL
			@SqlUpdate NVARCHAR(MAX),--待执行UPDATE的SQL
			@ColNameEmpty NVARCHAR(1000),--列名+空值
			@ColStr NVARCHAR(4000),-- 拼接初始化更新字符串
			@ColBussiStr NVARCHAR(MAX),-- 拼接业务表更新字符串
			@Flag bit, --业务表是否有数据
			@birthday DATETIME
	SELECT @birthday = birthday FROM PersonalInfo WHERE FileNo = @FileNo
	SET @ColBussiStr = ''
	IF EXISTS(SELECT * FROM VaccineImmuneInfo WHERE FileNo = @FileNo AND IsPlan = 0)
	BEGIN
		SELECT @ColBussiStr = @ColBussiStr + ',Col' + ColNum + '=''第' + CAST(Number AS NVARCHAR(10)) + 
			'剂'' + '':'' + ''' + ColNum +  
			(CASE WHEN EXISTS(SELECT * FROM VaccineImmuneRulesSpecial WHERE ColNum = A.ColNum And RowNum = A.RowNumber) THEN '~' ELSE '' END) +
			''' + '':'' + ''' + CONVERT(NVARCHAR(20),VaccinationDate,120) + ''' WHERE RowNumber=' +
			CAST(RowNumber AS NVARCHAR(10)) 
		FROM VaccineImmuneInfo A WHERE FileNo = @FileNo AND IsPlan = 0
		SET @Flag = 0
		SET @ColBussiStr = REPLACE(@ColBussiStr,',',' UPDATE #VacciProgram SET ')
	END
	ELSE
	BEGIN
		SET @Flag = 1
	END

	SET @ColName = ''
	SET @ColNameEmpty = ''
	SELECT @ColName = @ColName + 'Cast(''' + Name + ''' as Varchar(100)) AS Col' + CAST(ID AS NVARCHAR(10)) + ',' FROM BasicInformation WHERE Type = 167 AND ID != 167 AND IsInputValue = 0 ORDER BY Number
	PRINT @ColName
	SELECT @ColNameEmpty = @ColNameEmpty + ''''' AS A' + CAST(ID AS NVARCHAR(10)) + ',' FROM BasicInformation WHERE Type = 167 AND ID != 167 AND IsInputValue = 0
	SET @ColName = SUBSTRING(@ColName,1,LEN(@ColName)-1)
	SET @ColNameEmpty = SUBSTRING(@ColNameEmpty,1,LEN(@ColNameEmpty)-1)
	
	SET @ColStr = ''
	IF @Flag = 1
	BEGIN
		SELECT @ColStr = @ColStr + ',Col' + ISNULL(VaccineName,'') + '=''第' + CAST(ISNULL(Number,'') AS NVARCHAR(10)) + 
				'剂'' + '':'' + ''' + ISNULL(VaccineName,'') + 
				(CASE WHEN DATEDIFF(MONTH,@birthday,GETDATE()) >= A.MinMonthAge AND DATEDIFF(MONTH,@birthday,GETDATE()) <= A.MaxMonthAge
					THEN '$'
					ELSE '' 
				END)
				+ ''' WHERE RowNumber=' + CAST(ISNULL(RowNumber,'') AS NVARCHAR(10)) 
			FROM VaccineImmuneRules A
		WHERE Rules = 0 AND IsSpecial = 0
	END
	ELSE IF @Flag = 0
	BEGIN
        Select * INTO #InitVacci From (
			SELECT A.*,ISNULL(B.ColNum,'') ColNum
				FROM VaccineImmuneRules A
			LEFT JOIN (SELECT ColNum,RowNumber FROM VaccineImmuneInfo WHERE FileNo = @FileNo) B ON A.VaccineName = B.ColNum AND A.RowNumber = B.RowNumber 
			WHERE Rules = 0 ) A
        WHERE ColNum = ''
		SELECT @ColStr = @ColStr + ',Col' + ISNULL(VaccineName,'') + '=''第' + CAST(Number AS NVARCHAR(10)) + 
				'剂'' + '':'' + ''' + ISNULL(VaccineName,'') + ''' WHERE RowNumber=' + CAST(ISNULL(RowNumber,'') AS NVARCHAR(10)) 
			FROM #InitVacci WHERE IsSpecial = 0	
	END
	SET @ColStr = REPLACE(@ColStr,',',' UPDATE #VacciProgram SET ')
	SET @Sql = ' SELECT ' + @ColName + ' INTO #Vacci ' +
			' SELECT ' + @ColNameEmpty + ' INTO #VacciEmpty ' +
			' SELECT 0 AS RowNumber,'''' AS Age,* INTO #VacciProgram FROM #Vacci ' +
			' UNION ' +
			' SELECT B.RowNumber,B.Age,A.* FROM #VacciEmpty A, VaccineImmuneAgeRules B WHERE Type = 0 ORDER BY RowNumber ' +
			@ColStr + @ColBussiStr + 
			' IF EXISTS(SELECT * FROM VaccineImmuneRulesSpecial)
				BEGIN
					DECLARE @SepcialWhere NVARCHAR(1000)
					SET @SepcialWhere = ''''
					SELECT @SepcialWhere = @SepcialWhere + ''? Col'' + ColNum + ''=''''第'' + SpecialVal + ''剂:'' + ColNum + 
						''~'''' WHERE RowNumber='' + CAST(RowNum AS NVARCHAR(10)) + '' AND Col'' + ColNum + '' = "" '' 
					FROM VaccineImmuneRulesSpecial
					SET @SepcialWhere = REPLACE(@SepcialWhere,''?'','' UPDATE #VacciProgram SET '')
					EXEC(@SepcialWhere)
				END' +
			' SELECT * FROM #VacciProgram '
	CREATE TABLE #TMP(
		RowNumber INT,
		Age NVARCHAR(100),
		HepB NVARCHAR(100),
		BCG NVARCHAR(100),
		OPV NVARCHAR(100),
		DPT NVARCHAR(100),
		DT NVARCHAR(100),
		MV NVARCHAR(100),
		MR NVARCHAR(100),
		MMR NVARCHAR(100),
		MM NVARCHAR(100),
		JE NVARCHAR(100),
		AECM NVARCHAR(100),
		ACECM NVARCHAR(100),
		AHA NVARCHAR(100),
		IHA NVARCHAR(100)
	)
	INSERT INTO #TMP
	EXEC(@Sql)
	SELECT * FROM #TMP
	SET NOCOUNT OFF
	--exec   sp_executesql @Sql
	--PRINT @Sql
END
--exec Proc_Vacci '.*$.@&&$!@$!$$$!!!'
GO
