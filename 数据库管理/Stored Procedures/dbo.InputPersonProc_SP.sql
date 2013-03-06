SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

--创建存储过程
--@Type = 'ABC' A=1 按照组织机构分组 B=1按照录入人分组 C=1按照年分组 = 2按照月分组 = 3 按照日分组
--@BusnessType = 'ABCDEF' A=1 查看档案数据 B=1 儿童业务数据 C=1 孕产妇业务数据 D=1 慢性病业务数据 E=1 老年人业务数据
--	F=1 疾控业务数据
--@IsQry 是否查询报销统计数据
CREATE Proc [dbo].[InputPersonProc_SP](
	@InputPersonId Nvarchar(30),
	@Type Nvarchar(10) = '100',
	@Where Nvarchar(1000) = '',
	@BusnessType Nvarchar(10) = '111111',
	@IsQry Nvarchar(10) = '0'
) As
Begin
	Declare @sql varchar(Max),
			@sql1 varchar(Max),
			@IsLookAuthority Int,
			@IsStasticAuth Int,
			@OrgId Int,
			@DistrictNumber Nvarchar(20),
			@DistrictLen Smallint,
			@DefaultWhere varchar(2000),
			@LeftJoinSql Varchar(200),
			@VacciInfoCol Nvarchar(max),
			@VacciInfoWhere Nvarchar(max)

	SET @IsStasticAuth = 1 

	Select @IsLookAuthority = IsLookAuthority ,@OrgId = Org_ID,@DistrictNumber = District_ID
	  from sam_taxempcode where LoginName = @InputPersonId
	Declare @SelGroup varchar(1000),@GroupName Varchar(1000)
	Declare @ShowTotalSql Varchar(4000)
	if Substring(@Type,1,1) = 1 Or Substring(@Type,2,1) = 1
	Begin
		Set @ShowTotalSql = ' Declare @MaxRowId Int ' + 
			' Select @MaxRowId = Max(RowID) From SummaryStatistics01 Where OptPersonID = ''' + @InputPersonId + ''' ' +
			' Insert Into SummaryStatistics01 ' + 
			' Select Lower(Replace(NewID(),''-'','''')) ID,' + 
			' (Case When @MaxRowId Is NULL Then 0 Else @MaxRowId + 1 End) As RowID,' + 
			' '''','''',Count(GroupDate),IsNULL(Sum(VHealthCount),0),' + 
			' IsNULL(Sum(CHealthCount),0),IsNULL(Sum(BabyVisitCount),0),IsNULL(Sum(BabyHealthCount),0),' +
			' IsNULL(Sum(Children01Count),0),IsNULL(Sum(Children02Count),0),' + 
			' IsNULL(Sum(Children3_6Count),0),IsNULL(Sum(BabyAllVisitCount),0),' +
			' IsNULL(Sum(MaternalCount),0),IsNULL(Sum(FirstVistBeforeBornCount),0),' + 
			' IsNULL(Sum(VisitBeforeBornCount),0),' +
			' IsNULL(Sum(PrenatalVisitCount),0),IsNULL(Sum(VisitAfterBornCount),0),' +
			' IsNULL(Sum(VisitAfterBorn42Count),0),' +
			' IsNULL(Sum(HypertensionVisitCount),0),IsNULL(Sum(HypertensionHealthCount),0),' +
			' IsNULL(Sum(DiabetesVisitCount),0),' + 
			' IsNULL(Sum(DiabetesHealthCount),0),IsNULL(Sum(FuriousVisitCount),0),' +
			' IsNULL(Sum(FuriousHealthCount),0),' + 
			' Count(IsNULL(UserName,0)),''总计'',''' + @InputPersonId + '''' +
			' ,IsNULL(Sum(IsNULL(VacciInfoCount,0)),0) ' +
			' From SummaryStatistics01 Where OptPersonID = ''' + @InputPersonId + ''' '
	End  	

	if Substring(@Type,1,1) = 0 And Substring(@Type,2,1) = 0 And 
		(Substring(@Type,3,1) = 1 Or Substring(@Type,3,1) = 2 Or Substring(@Type,3,1) = 3)
	Begin
		Set @ShowTotalSql = ' Declare @MaxRowId Int ' + 
			' Select @MaxRowId = Max(RowID) From SummaryStatistics01 Where OptPersonID = ''' + @InputPersonId + ''' ' +
			' Insert Into SummaryStatistics01 ' + 
			' Select Lower(Replace(NewID(),''-'','''')) ID,' + 
			' (Case When @MaxRowId Is NULL Then 0 Else @MaxRowId + 1 End) As RowID,' + 
			' '''','''',''总计'',IsNULL(Sum(VHealthCount),0),' + 
			' IsNULL(Sum(CHealthCount),0),IsNULL(Sum(BabyVisitCount),0),IsNULL(Sum(BabyHealthCount),0),' +
			' IsNULL(Sum(Children01Count),0),IsNULL(Sum(Children02Count),0),' + 
			' IsNULL(Sum(Children3_6Count),0),IsNULL(Sum(BabyAllVisitCount),0),' +
			' IsNULL(Sum(MaternalCount),0),IsNULL(Sum(FirstVistBeforeBornCount),0),' + 
			' IsNULL(Sum(VisitBeforeBornCount),0),' +
			' IsNULL(Sum(PrenatalVisitCount),0),IsNULL(Sum(VisitAfterBornCount),0),' +
			' IsNULL(Sum(VisitAfterBorn42Count),0),' +
			' IsNULL(Sum(HypertensionVisitCount),0),IsNULL(Sum(HypertensionHealthCount),0),' +
			' IsNULL(Sum(DiabetesVisitCount),0),' + 
			' IsNULL(Sum(DiabetesHealthCount),0),IsNULL(Sum(FuriousVisitCount),0),' +
			' IsNULL(Sum(FuriousHealthCount),0),' + 
			' NULL,NULL,''' + @InputPersonId + '''' +
			' ,IsNULL(Sum(ISNULL(VacciInfoCount,0)),0) ' +
			' From SummaryStatistics01 Where OptPersonID = ''' + @InputPersonId + ''' '
	End

    Set @SelGroup = ''
    Set @GroupName = ''
	Set @VacciInfoCol = ''
	Set @VacciInfoWhere = ''
	Set @LeftJoinSql = ' Left Join Organization C on A.OrgID = C.ID '
    if Substring(@Type,1,1) = 1 
    begin
      Set @SelGroup = 'OrgID'
      Set @GroupName = 'OrgID'
	  Set @VacciInfoCol = 'OrgID'
	  Set @VacciInfoWhere = ' A.OrgID = B.OrgID '
    end
    else
    begin
      Set @SelGroup = '0 As OrgID'
	  Set @GroupName = ''
    end

    if Substring(@Type,2,1) = 1 
    begin
      Set @SelGroup = Case when @SelGroup = '' then 'InputPersonID' else @SelGroup + ',InputPersonID ' end
      Set @GroupName = Case when @GroupName = '' then 'InputPersonID' else @GroupName + ',InputPersonID ' end
	  Set @LeftJoinSql = ' Left Join Organization C on B.Org_ID = C.ID '
	  Set @VacciInfoCol = Case When @VacciInfoCol = '' Then 'InputPersonID' Else @VacciInfoCol + ',InputPersonID' End
      Set @VacciInfoWhere = Case When @VacciInfoWhere = '' Then ' A.InputPersonID = B.InputPersonID ' Else @VacciInfoWhere + ' And A.InputPersonID = B.InputPersonID ' End
	end
    else
      Set @SelGroup =  @SelGroup + ','''' InputPersonID '  

    if Substring(@Type,3,1) = 1 
    begin
      Set @SelGroup = Case when @SelGroup = '' then 'Convert(Varchar(4),InputDate,120) As GroupDate' else @SelGroup + ',Convert(Varchar(4),InputDate,120) As GroupDate ' end
      Set @GroupName = Case when @GroupName = '' then 'Convert(Varchar(4),InputDate,120)' else @GroupName + ',Convert(Varchar(4),InputDate,120) ' end
	  Set @VacciInfoCol = Case When @VacciInfoCol = '' Then 'Convert(Varchar(4),InputDate,120) As GroupDate' Else @VacciInfoCol + ',Convert(Varchar(4),InputDate,120) As GroupDate' End
	end
    if Substring(@Type,3,1) = 2 
    begin
      Set @SelGroup = Case when @SelGroup = '' then 'Convert(Varchar(7),InputDate,120) As GroupDate' else @SelGroup + ',Convert(Varchar(7),InputDate,120) As GroupDate ' end
      Set @GroupName = Case when @GroupName = '' then 'Convert(Varchar(7),InputDate,120)' else @GroupName + ',Convert(Varchar(7),InputDate,120) ' end
	  Set @VacciInfoCol = Case When @VacciInfoCol = '' Then 'Convert(Varchar(7),InputDate,120) As GroupDate' Else @VacciInfoCol + ',Convert(Varchar(7),InputDate,120) As GroupDate' End
	end
    if Substring(@Type,3,1) = 3
    begin
      Set @SelGroup = Case when @SelGroup = '' then 'Convert(Varchar(10),InputDate,120) As GroupDate' else @SelGroup + ',Convert(Varchar(10),InputDate,120) As GroupDate ' end
      Set @GroupName = Case when @GroupName = '' then 'Convert(Varchar(10),InputDate,120)' else @GroupName + ',Convert(Varchar(10),InputDate,120) ' end
      Set @VacciInfoCol = Case When @VacciInfoCol = '' Then 'Convert(Varchar(10),InputDate,120) As GroupDate' Else @VacciInfoCol + ',Convert(Varchar(10),InputDate,120) As GroupDate' End

	end
	if Substring(@Type,3,1) = 0
      Set @SelGroup = @SelGroup + ','''' As GroupDate '
	If SubString(@Type,3,1) != 0
	Begin
      Set @VacciInfoWhere = Case When @VacciInfoWhere = '' Then ' A.GroupDate = B.GroupDate 'Else @VacciInfoWhere + ' And A.GroupDate = B.GroupDate ' End
	End
	Set @SelGroup = Case when @SelGroup = '' then '' else @SelGroup + ',' End
	Set @GroupName = Case when @GroupName = '' then '' Else ' Group By ' + @GroupName End 

    --构造条件
	If @IsLookAuthority = 1
	Begin
	  --待完善
	  Set @DistrictLen = Len(@DistrictNumber)
	  Set @DefaultWhere = ' where A.InputPersonID in (Select LoginName from sam_taxempcode where Substring(District_ID,1,' + Cast(@DistrictLen As varchar(10)) + ') = ''' +  @DistrictNumber + ''')'
	End
	Else
	Begin
	  Set @DefaultWhere = ' where A.InputPersonID in (Select LoginName from sam_taxempcode where Org_ID = ' + Cast(@OrgId As varchar(10))	 + ')'
	End


    Declare @HealthSql Varchar(4000),@BabyVisitSql Varchar(4000),@BabyHealthSql Varchar(4000),@Children01Sql Varchar(4000),
		@Children02Sql Varchar(4000),@Children3_6Sql Varchar(4000),@MaternalSql Varchar(4000),
		@FirstVisitBeforeBornSql Varchar(4000),@VisitBeforeBornSql Varchar(4000),@VisitAfterBornSql Varchar(4000),
		@VisitAfterBorn42Sql Varchar(4000),@HypertensionHealthSql Varchar(4000),@HypertensionVisitSql Varchar(4000),
		@DiabetesVisitSql Varchar(4000),@DiabetesHealthSql Varchar(4000),
		@FuriousVisitSql Varchar(4000),@FuriousHealthSql Varchar(4000)
    Select @HealthSql = Case When SubString(@BusnessType,1,1) = 1 Then
      'Select A.InputPersonID,A.InputDate,' + 
      '(Case FarmStatus when ''是'' then 1 else 0 end)VHealthCount,
       (Case FarmStatus when ''否'' then 1 else 0 end)CHealthCount,0 As BabyVisitCount,0 As BabyHealthCount,' +
	  '  0 As Children01Count,0 As Children02Count,0 As Children3_6Count,0 As MaternalCount,' + 
	  '  0 As FirstVistBeforeBornCount,0 As VisitBeforeBornCount,0 As VisitAfterBornCount, ' + 
	  '  0 As VisitAfterBorn42Count,0 As HypertensionVisitCount,0 As HypertensionHealthCount, ' +
	  '  0 As DiabetesVisitCount,0 As DiabetesHealthCount,0 As FuriousVisitCount,0 As FuriousHealthCount ' +
      '  from HealthFile A ' +
      '  Left Join PersonalInfo B on A.FileNo = B.FileNo ' +
      @DefaultWhere + @Where
	  Else '' End
	
	DECLARE @TmpBabyVisitWhere NVARCHAR(MAX)
	SET @TmpBabyVisitWhere = ''
	IF @IsStasticAuth = 1 And @IsQry = 1
	BEGIN
		SET @TmpBabyVisitWhere = ' AND ((ABS(datediff(day,A.Birthday,A.VisitDate)) <= 28 And ABS(datediff(day,A.Birthday,A.VisitDate)) >= 25) 
					OR (ABS(datediff(day,A.Birthday,A.VisitDate)) <= 14 And ABS(datediff(day,A.Birthday,A.VisitDate)) >= 10)) '
	END	
    Select @BabyVisitSql = Case When SubString(@BusnessType,2,1) = 1 Then
      ' Select InputPersonID,InputDate,' + 
      ' 0 As VHealthCount,0 CHealthCount, 1 As BabyVisitCount,0 As BabyHealthCount,0 As Children01Count, ' + 
	  ' 0 As Children02Count,0 As Children3_6Count,0 As MaternalCount,0 As FirstVistBeforeBornCount,' +
	  ' 0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' +
	  ' 0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' + 
	  ' 0 As FuriousVisitCount,0 As FuriousHealthCount ' +
      ' from BabyVisit A ' + 
      @DefaultWhere + @Where + @TmpBabyVisitWhere
	  Else '' End
	
	Set @BabyHealthSql = ''
	/**Select @BabyHealthSql = Case When SubString(@BusnessType,1,1) = 1 Or SubString(@BusnessType,2,1) = 1 Then
	  'Select A.InputPersonID,A.InputDate,' + 
	  ' 0 As VHealthCount,0 CHealthCount, 0 As BabyVisitCount, ' + 
	  ' (Case When DateDiff(Day,Birthday,getDate()) / 365 < 7 Then 1 Else 0 End) BabyHealthCount,0 As Children01Count, ' +
	  ' 0 As Children02Count,0 As Children3_6Count,0 As MaternalCount,0 As FirstVistBeforeBornCount,' +
	  ' 0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' +
	  ' 0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' + 
	  ' 0 As FuriousVisitCount,0 As FuriousHealthCount ' +
	  ' From  HealthFile A ' + 
	  ' Left Join PersonalInfo B on A.FileNo = B.FileNo ' + 
	  @DefaultWhere + @Where
	  Else '' End**/

	
	DECLARE @TmpChildren01Where NVARCHAR(MAX)
	SET @TmpChildren01Where = ''
	IF @IsStasticAuth = 1 And @IsQry = 1
	BEGIN
		SET @TmpChildren01Where = ' AND A.CheckItem IN (''3月龄'',''6月龄'',''8月龄'') '
	END	
	Select @Children01Sql = Case When SubString(@BusnessType,2,1) = 1 Then
	  ' Select InputPersonID,InputDate,' + 
      ' 0 As VHealthCount,0 CHealthCount, 0 As BabyVisitCount,0 As BabyHealthCount,1 As Children01Count,  ' + 
	  ' 0 As Children02Count,0 As Children3_6Count,0 As MaternalCount,0 As FirstVistBeforeBornCount,' +
	  ' 0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' +
	  ' 0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' + 
	  ' 0 As FuriousVisitCount,0 As FuriousHealthCount ' +
      ' from ChildrenMediExam A ' + 
	  @DefaultWhere + @Where + ' And A.DataType = 0 ' + @TmpChildren01Where
	  Else '' End

	DECLARE @TmpChildren02Where NVARCHAR(MAX)
	SET @TmpChildren02Where = ''
	IF @IsStasticAuth = 1 And @IsQry = 1
	BEGIN
		SET @TmpChildren02Where = ' AND A.CheckItem IN (''12月龄'',''18月龄'',''24月龄'',''30月龄'') '
	END	
	Select @Children02Sql = Case When SubString(@BusnessType,2,1) = 1 Then
	  ' Select InputPersonID,InputDate,' + 
      ' 0 As VHealthCount,0 CHealthCount, 0 As BabyVisitCount,0 As BabyHealthCount,0 As Children01Count, ' +
	  ' 1 As Children02Count,0 As Children3_6Count,0 As MaternalCount,0 As FirstVistBeforeBornCount,  ' + 
	  ' 0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' +
	  ' 0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' + 
	  ' 0 As FuriousVisitCount,0 As FuriousHealthCount ' +
      ' from ChildrenMediExam A ' + 
	  @DefaultWhere + @Where + ' And A.DataType = 1 ' + @TmpChildren02Where
	  Else '' End

	Select @Children3_6Sql = Case When SubString(@BusnessType,2,1) = 1 Then
	  ' Select InputPersonID,InputDate,' + 
      ' 0 As VHealthCount,0 CHealthCount, 0 As BabyVisitCount,0 As BabyHealthCount,0 As Children01Count, ' +
	  ' 0 As Children02Count,1 As Children3_6Count,0 As MaternalCount,0 As FirstVistBeforeBornCount,  ' + 
	  ' 0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' +
	  ' 0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' + 
	  ' 0 As FuriousVisitCount,0 As FuriousHealthCount ' +
      ' from ChildrenMediExam3_6 A ' + 
	  @DefaultWhere + @Where
	  Else '' End
	
	Set @MaternalSql = ''
	/**Select @MaternalSql = Case When SubString(@BusnessType,1,1) = 1 Or SubString(@BusnessType,3,1) = 1 Then
	  'Select A.InputPersonID,A.InputDate,' + 
      '  0 As VHealthCount,0 As CHealthCount,0 As BabyVisitCount,0 As BabyHealthCount,' +
	  '  0 As Children01Count,0 As Children02Count,0 As Children3_6Count,' + 
	  '  (Case BornStatus when ''是'' then 1 else 0 end)MaternalCount,0 As FirstVistBeforeBornCount,' +
	  '  0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' +
	  '  0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' + 
	  '  0 As FuriousVisitCount,0 As FuriousHealthCount ' +
      '  from HealthFile A ' +
      '  Left Join PersonalInfo B on A.FileNo = B.FileNo ' +
      @DefaultWhere + @Where
	  Else '' End**/
	Select @FirstVisitBeforeBornSql = Case When SubString(@BusnessType,3,1) = 1 Then
	  ' Select InputPersonID,InputDate,' + 
      ' 0 As VHealthCount,0 CHealthCount, 0 As BabyVisitCount,0 As BabyHealthCount,0 As Children01Count, ' +
	  ' 0 As Children02Count,0 As Children3_6Count,0 As MaternalCount,1 As FirstVistBeforeBornCount, ' + 
	  ' 0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' +
	  ' 0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' + 
	  ' 0 As FuriousVisitCount,0 As FuriousHealthCount ' +
      ' from FirstVistBeforeBorn A ' + 
	  @DefaultWhere + @Where
	  Else '' End

	DECLARE @TmpVisitBeforeBornWhere NVARCHAR(MAX)
	SET @TmpVisitBeforeBornWhere = ''
	IF @IsStasticAuth = 1 And @IsQry = 1
	BEGIN
		SET @TmpVisitBeforeBornWhere =  ' And Cast(IsNULL(Item,''0'') As Int) <= 5 '
	END	
	Select @VisitBeforeBornSql = Case When SubString(@BusnessType,3,1) = 1 Then
	  ' Select InputPersonID,InputDate,' + 
      ' 0 As VHealthCount,0 CHealthCount, 0 As BabyVisitCount,0 As BabyHealthCount,0 As Children01Count, ' +
	  ' 0 As Children02Count,0 As Children3_6Count,0 As MaternalCount,0 As FirstVistBeforeBornCount, ' + 
	  ' 1 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' + 
	  ' 0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' + 
	  ' 0 As FuriousVisitCount,0 As FuriousHealthCount ' +
      ' from VisitBeforeBorn A ' + 
	  @DefaultWhere + @Where + @TmpVisitBeforeBornWhere
	  Else '' End

	DECLARE @TmpVisitAfterBornWhere NVARCHAR(MAX)
	SET @TmpVisitAfterBornWhere = ''
	IF @IsStasticAuth = 1 And @IsQry = 1
	BEGIN
		SET @TmpVisitAfterBornWhere =  ' And RecordType = ''0'' And Item = ''10~14天'''
	END	
	Select @VisitAfterBornSql = Case When SubString(@BusnessType,3,1) = 1 Then
	  ' Select InputPersonID,InputDate,' + 
      ' 0 As VHealthCount,0 CHealthCount, 0 As BabyVisitCount,0 As BabyHealthCount,0 As Children01Count, ' +
	  ' 0 As Children02Count,0 As Children3_6Count,0 As MaternalCount,0 As FirstVistBeforeBornCount, ' + 
	  ' 0 As VisitBeforeBornCount,1 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount,  ' + 
	  ' 0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' + 
	  ' 0 As FuriousVisitCount,0 As FuriousHealthCount ' +
      ' from VisitAfterBorn A ' + 
	  @DefaultWhere + @Where + @TmpVisitAfterBornWhere
	  Else '' End
	Select @VisitAfterBorn42Sql = Case When SubString(@BusnessType,3,1) = 1 Then
	  ' Select InputPersonID,InputDate,' + 
      ' 0 As VHealthCount,0 CHealthCount, 0 As BabyVisitCount,0 As BabyHealthCount,0 As Children01Count, ' +
	  ' 0 As Children02Count,0 As Children3_6Count,0 As MaternalCount,0 As FirstVistBeforeBornCount, ' + 
	  ' 0 As VisitBeforeBornCount,0 As VisitAfterBornCount,1 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' + 
      ' 0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' + 
	  ' 0 As FuriousVisitCount,0 As FuriousHealthCount ' +
	  ' from VisitAfterBorn A ' + 
	  @DefaultWhere + @Where + ' And RecordType = ''1'''
	  Else '' End
	Select @HypertensionVisitSql = Case When SubString(@BusnessType,4,1) = 1 Then
	  ' Select InputPersonID,InputDate,' + 
      ' 0 As VHealthCount,0 CHealthCount, 0 As BabyVisitCount,0 As BabyHealthCount,0 As Children01Count, ' +
	  ' 0 As Children02Count,0 As Children3_6Count,0 As MaternalCount,0 As FirstVistBeforeBornCount, ' + 
	  ' 0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,1 As HypertensionVisitCount, ' + 
      ' 0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' + 
	  ' 0 As FuriousVisitCount,0 As FuriousHealthCount ' +
	  ' from HypertensionVisit A ' + 
	  @DefaultWhere + @Where 
	  Else '' End
	Set @HypertensionHealthSql = ''
	/**Select @HypertensionHealthSql = Case When SubString(@BusnessType,1,1) = 1 Or SubString(@BusnessType,4,1) = 1 Then
	  'Select A.InputPersonID,A.InputDate,' + 
      '  0 As VHealthCount,0 As CHealthCount,0 As BabyVisitCount,0 As BabyHealthCount,' +
	  '  0 As Children01Count,0 As Children02Count,0 As Children3_6Count,' + 
	  '  0 As MaternalCount,0 As FirstVistBeforeBornCount,' +
	  '  0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' +
	  '  1 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' +
	  '  0 As FuriousVisitCount,0 As FuriousHealthCount ' +
      '  from DiseaseHistory C ' +
      '  Join PersonalInfo B on B.ID = C.PersonalInfoID And C.DiseaseID = 2 ' +
	  '  Left Join HealthFile A On A.FileNo = B.FileNo ' +
      @DefaultWhere + @Where
	  Else '' End**/
	Select @DiabetesVisitSql = Case When SubString(@BusnessType,4,1) = 1 Then
	  ' Select InputPersonID,InputDate,' + 
      ' 0 As VHealthCount,0 CHealthCount, 0 As BabyVisitCount,0 As BabyHealthCount,0 As Children01Count, ' +
	  ' 0 As Children02Count,0 As Children3_6Count,0 As MaternalCount,0 As FirstVistBeforeBornCount, ' + 
	  ' 0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' + 
      ' 0 As HypertensionHealthCount,1 As DiabetesVisitCount,0 As DiabetesHealthCount,' + 
	  ' 0 As FuriousVisitCount,0 As FuriousHealthCount ' +
	  ' from DiabetesVisit A ' + 
	  @DefaultWhere + @Where 
	  Else '' End

	Set @DiabetesHealthSql = ''
	/**Select @DiabetesHealthSql = Case When SubString(@BusnessType,1,1) = 1 Or SubString(@BusnessType,4,1) = 1 Then
	  'Select A.InputPersonID,A.InputDate,' + 
      '  0 As VHealthCount,0 As CHealthCount,0 As BabyVisitCount,0 As BabyHealthCount,' +
	  '  0 As Children01Count,0 As Children02Count,0 As Children3_6Count,' + 
	  '  0 As MaternalCount,0 As FirstVistBeforeBornCount,' +
	  '  0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' +
	  '  0 As HypertensionHealthCount,0 As DiabetesVisitCount,1 As DiabetesHealthCount, ' +
	  '  0 As FuriousVisitCount,0 As FuriousHealthCount ' +
      '  from DiseaseHistory C ' +
      '  Join PersonalInfo B on B.ID = C.PersonalInfoID And C.DiseaseID = 3 ' +
	  '  Left Join HealthFile A On A.FileNo = B.FileNo ' +
      @DefaultWhere + @Where
	  Else '' End**/

	Select @FuriousVisitSql = Case When SubString(@BusnessType,4,1) = 1 Then
	  ' Select InputPersonID,InputDate,' + 
      ' 0 As VHealthCount,0 CHealthCount, 0 As BabyVisitCount,0 As BabyHealthCount,0 As Children01Count, ' +
	  ' 0 As Children02Count,0 As Children3_6Count,0 As MaternalCount,0 As FirstVistBeforeBornCount, ' + 
	  ' 0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' + 
      ' 0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount,' + 
	  ' 1 As FuriousVisitCount,0 As FuriousHealthCount ' +
	  ' from FuriousVisit A ' + 
	  @DefaultWhere + @Where 
	  Else '' End

	Set @FuriousHealthSql = ''
	/**Select @FuriousHealthSql = Case When SubString(@BusnessType,1,1) = 1 Or SubString(@BusnessType,4,1) = 1 Then
	  'Select A.InputPersonID,A.InputDate,' + 
      '  0 As VHealthCount,0 As CHealthCount,0 As BabyVisitCount,0 As BabyHealthCount,' +
	  '  0 As Children01Count,0 As Children02Count,0 As Children3_6Count,' + 
	  '  0 As MaternalCount,0 As FirstVistBeforeBornCount,' +
	  '  0 As VisitBeforeBornCount,0 As VisitAfterBornCount,0 As VisitAfterBorn42Count,0 As HypertensionVisitCount, ' +
	  '  0 As HypertensionHealthCount,0 As DiabetesVisitCount,0 As DiabetesHealthCount, ' +
	  '  0 As FuriousVisitCount,1 As FuriousHealthCount ' +
      '  from DiseaseHistory C ' +
      '  Join PersonalInfo B on B.ID = C.PersonalInfoID And C.DiseaseID = 8 ' +
	  '  Left Join HealthFile A On A.FileNo = B.FileNo ' +
      @DefaultWhere + @Where
	  Else '' End**/

	Declare @UnionSql Nvarchar(Max)
	Select @UnionSql = Case @HealthSql When '' Then '' Else @HealthSql End
	Select @UnionSql = @UnionSql + Case @BabyVisitSql When '' Then '' 
		Else (Case @UnionSql When '' Then @BabyVisitSql Else ' Union All ' + @BabyVisitSql End) End
	Select @UnionSql = @UnionSql + Case @BabyHealthSql When '' Then '' 
		Else (Case @UnionSql When '' Then @BabyHealthSql Else ' Union All ' + @BabyHealthSql End) End
	Select @UnionSql = @UnionSql + Case @Children01Sql When '' Then '' 
		Else (Case @UnionSql When '' Then @Children01Sql Else ' Union All ' + @Children01Sql End) End
	Select @UnionSql = @UnionSql + Case @Children02Sql When '' Then '' 
		Else (Case @UnionSql When '' Then @Children02Sql Else ' Union All ' + @Children02Sql End) End	
	Select @UnionSql = @UnionSql + Case @Children3_6Sql When '' Then '' 
		Else (Case @UnionSql When '' Then @Children3_6Sql Else ' Union All ' + @Children3_6Sql End) End	
	Select @UnionSql = @UnionSql + Case @MaternalSql When '' Then '' 
		Else (Case @UnionSql When '' Then @MaternalSql Else ' Union All ' + @MaternalSql End) End
	Select @UnionSql = @UnionSql + Case @FirstVisitBeforeBornSql When '' Then '' 
		Else (Case @UnionSql When '' Then @FirstVisitBeforeBornSql Else ' Union All ' + @FirstVisitBeforeBornSql End) End
	Select @UnionSql = @UnionSql + Case @VisitBeforeBornSql When '' Then '' 
		Else (Case @UnionSql When '' Then @VisitBeforeBornSql Else ' Union All ' + @VisitBeforeBornSql End) End
	Select @UnionSql = @UnionSql + Case @VisitAfterBornSql When '' Then '' 
		Else (Case @UnionSql When '' Then @VisitAfterBornSql Else ' Union All ' + @VisitAfterBornSql End) End
	Select @UnionSql = @UnionSql + Case @VisitAfterBorn42Sql When '' Then '' 
		Else (Case @UnionSql When '' Then @VisitAfterBorn42Sql Else ' Union All ' + @VisitAfterBorn42Sql End) End
	Select @UnionSql = @UnionSql + Case @HypertensionVisitSql When '' Then '' 
		Else (Case @UnionSql When '' Then @HypertensionVisitSql Else ' Union All ' + @HypertensionVisitSql End) End
	Select @UnionSql = @UnionSql + Case @HypertensionHealthSql When '' Then '' 
		Else (Case @UnionSql When '' Then @HypertensionHealthSql Else ' Union All ' + @HypertensionHealthSql End) End
	Select @UnionSql = @UnionSql + Case @DiabetesVisitSql When '' Then '' 
		Else (Case @UnionSql When '' Then @DiabetesVisitSql Else ' Union All ' + @DiabetesVisitSql End) End
	Select @UnionSql = @UnionSql + Case @DiabetesHealthSql When '' Then '' 
		Else (Case @UnionSql When '' Then @DiabetesHealthSql Else ' Union All ' + @DiabetesHealthSql End) End
	Select @UnionSql = @UnionSql + Case @FuriousVisitSql When '' Then '' 
		Else (Case @UnionSql When '' Then @FuriousVisitSql Else ' Union All ' + @FuriousVisitSql End) End
	Select @UnionSql = @UnionSql + Case @FuriousHealthSql When '' Then '' 
		Else (Case @UnionSql When '' Then @FuriousHealthSql Else ' Union All ' + @FuriousHealthSql End) End

    Set @sql = 
      'Select A.*,C.ID OrgID from (' + 
      'Select InputPersonID,InputDate,' + 
      'IsNULL(Sum(VHealthCount),0)VHealthCount,' + 
      'Sum(CHealthCount)CHealthCount,' + 
      'Sum(BabyVisitCount)BabyVisitCount,' +
	  'Sum(BabyHealthCount)BabyHealthCount,' +
	  'Sum(Children01Count)Children01Count,' +
	  'Sum(Children02Count)Children02Count,' +
	  'Sum(Children3_6Count)Children3_6Count,' +
	  'Sum(MaternalCount)MaternalCount,' +
	  'Sum(FirstVistBeforeBornCount)FirstVistBeforeBornCount,' +
	  'Sum(VisitBeforeBornCount)VisitBeforeBornCount,' +
	  'Sum(VisitAfterBornCount)VisitAfterBornCount,' +
	  'Sum(VisitAfterBorn42Count)VisitAfterBorn42Count,' +
	  'Sum(HypertensionVisitCount)HypertensionVisitCount,' +
	  'Sum(HypertensionHealthCount)HypertensionHealthCount,' +
	  'Sum(DiabetesVisitCount)DiabetesVisitCount,' +
	  'Sum(DiabetesHealthCount)DiabetesHealthCount,' +
	  'Sum(FuriousVisitCount)FuriousVisitCount,' +
	  'Sum(FuriousHealthCount)FuriousHealthCount from (' + 
       @UnionSql + 
      ') A Group By InputPersonID,InputDate ' + 
      ') As A ' + 
      ' Left Join sam_taxempcode B on A.InputPersonID = B.LoginName ' +
      ' Left Join Organization C on B.Org_ID = C.ID'

	Set @sql = 'Insert Into SummaryStatistics01 ' +
	  'Select Lower(Replace(NewID(),''-'','''')) ID,' +
	  'Row_Number() over (Order By OrgId) As RowID,A.*,B.UserName,C.Name OrgName,''' + 
	  @InputPersonId + ''' OptPersonID,0 from (' +
      'Select ' + @SelGroup + 
      'IsNULL(Sum(VHealthCount),0)VHealthCount,
       IsNULL(Sum(CHealthCount),0)CHealthCount,
       IsNULL(Sum(BabyVisitCount),0)BabyVisitCount, ' + 
	  'IsNULL(Sum(BabyHealthCount),0)BabyHealthCount, ' +
	  'IsNULL(Sum(Children01Count),0)Children01Count, ' +
	  'IsNULL(Sum(Children02Count),0)Children02Count, ' +
	  'IsNULL(Sum(Children3_6Count),0)Children3_6Count, ' +
	  'IsNULL((Sum(BabyVisitCount) + Sum(Children01Count) + Sum(Children02Count) + Sum(Children3_6Count)),0)BabyAllVisitCount, ' + 
	  'IsNULL(Sum(MaternalCount),0)MaternalCount, ' +
	  'IsNULL(Sum(FirstVistBeforeBornCount),0)FirstVistBeforeBornCount, ' +
	  'IsNULL(Sum(VisitBeforeBornCount),0)VisitBeforeBornCount, ' +
	  'IsNULL((Sum(FirstVistBeforeBornCount) + Sum(VisitBeforeBornCount)),0)PrenatalVisitCount, ' +
	  'IsNULL(Sum(VisitAfterBornCount),0)VisitAfterBornCount, ' + 
	  'IsNULL(Sum(VisitAfterBorn42Count),0)VisitAfterBorn42Count, ' +
	  'IsNULL(Sum(HypertensionVisitCount),0)HypertensionVisitCount, ' +
	  'IsNULL(Sum(HypertensionHealthCount),0)HypertensionHealthCount, ' +
	  'IsNULL(Sum(DiabetesVisitCount),0)DiabetesVisitCount, ' +
	  'IsNULL(Sum(DiabetesHealthCount),0)DiabetesHealthCount, ' +
	  'IsNULL(Sum(FuriousVisitCount),0)FuriousVisitCount, ' +
	  'IsNULL(Sum(FuriousHealthCount),0)FuriousHealthCount ' +
      ' From (' + @sql
       + ') A ' + @GroupName +
      ') As A ' + 
      ' Left Join sam_taxempcode B on A.InputPersonID = B.LoginName ' + @LeftJoinSql
	
	Declare @VacciInfoSql Nvarchar(max)
	Select @VacciInfoSql = Case When SubString(@BusnessType,6,1) = 1 Then
	  ' Update SummaryStatistics01 Set VacciInfoCount = A.VacciInfoCount ' + 
	  ' From SummaryStatistics01 B Left Join ' +
	  ' (Select Count(*) VacciInfoCount,' + @VacciInfoCol + ' From (Select B.org_id as OrgId,A.InputDate,A.InputPersonID ' +
	  ' from VaccineImmuneInfo A Left Join sam_taxempcode B on A.InputPersonID = B.LoginName ' + 
	  @DefaultWhere + @Where + ')A ' + @GroupName + ') A ON ' + @VacciInfoWhere
	  Else '' End
	
	--PRINT @VacciInfoSql
	Set @sql1 = @ShowTotalSql +
		' Select * From SummaryStatistics01 Where OptPersonID = ''' + @InputPersonId + ''' Order By RowID ASC '
	Exec('Delete From SummaryStatistics01 Where OptPersonID = ''' + @InputPersonId + ''' ')
	Exec(@sql)
	EXEC(@VacciInfoSql)
	Exec(@sql1)
	--Print(@sql1)

End
/*
Truncate Table SummaryStatistics01
Alter Table SummaryStatistics01 Add VacciInfoCount BigInt 
GO*/
--Exec InputPersonProc_SP 'admin','100',' And A.InputDate >= ''2000-06-01 00:00:00'' And A.InputDate <= ''2012-08-15 23:59:59'' ','100101','0'

GO
