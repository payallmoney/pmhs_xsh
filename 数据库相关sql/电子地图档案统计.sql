SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

--存储过程如果存在，则先删除
If Exists(Select * From sys.procedures Where name = 'HealthFileElectronicMap') 
Begin
	Drop Proc HealthFileElectronicMap
End
GO

Create Proc HealthFileElectronicMap(
	@OrgId Int = 0
) As 
Begin
	SET NOCOUNT ON
	--定义参数
	Declare @IsFinal Nvarchar(10),
		@DistrictNumber Nvarchar(50),
		@SqlWhere Nvarchar(MAX),
		@InputPersonIds Nvarchar(MAX),
		@ExecSql Nvarchar(MAX),
		@UnionSql Nvarchar(MAX),
		@VHealthFileSql Nvarchar(MAX),
		@CHealthFileSql Nvarchar(MAX),
		@HealthFileSql Nvarchar(MAX),
		@ChildrenHealthFileSql Nvarchar(MAX),
		@OldManHealthFileSql Nvarchar(MAX),
		@TmpChildrenHighRiskHealthFileSql Nvarchar(MAX),
		@ChildrenHighRiskHealthFileSql Nvarchar(MAX),
		@HypertensionHealthFileSql Nvarchar(MAX),
		@DiabetesHealthFileSql Nvarchar(MAX),
		@HolergasiaHealthFileSql Nvarchar(MAX),
		@SqlWomanWhere Nvarchar(MAX),
		@WomanInitBirthHealthFileSql Nvarchar(MAX),
		@WomanAreadyBirthHealthFileSql Nvarchar(MAX),
		@WomanExceptionBirthHealthFileSql Nvarchar(MAX),
		@TmpWomanHighRiskHealthFileSql Nvarchar(MAX),
		@WomanHighRiskInitBirthHealthFileSql Nvarchar(MAX),
		@WomanHighRiskAreadyBirthHealthFileSql Nvarchar(MAX),
		@WomanHighRiskExceptionBirthHealthFileSql Nvarchar(MAX)
	--初始化参数
	Select @IsFinal = '',@DistrictNumber = '',@SqlWhere = '',@InputPersonIds = '',@ExecSql = '',@VHealthFileSql = '',
		@CHealthFileSql = '',@UnionSql = '',@HealthFileSql = '',@ChildrenHealthFileSql = '',@OldManHealthFileSql = '',
		@TmpChildrenHighRiskHealthFileSql = '',@ChildrenHighRiskHealthFileSql = '',@HypertensionHealthFileSql = '',
		@DiabetesHealthFileSql = '',@HolergasiaHealthFileSql = '',@SqlWomanWhere = '',@WomanInitBirthHealthFileSql = '',
		@WomanAreadyBirthHealthFileSql = '',@WomanExceptionBirthHealthFileSql = '',@TmpWomanHighRiskHealthFileSql = '',
		@WomanHighRiskInitBirthHealthFileSql = '',@WomanHighRiskAreadyBirthHealthFileSql = '',
		@WomanHighRiskExceptionBirthHealthFileSql = ''
	
	--判断组织机构是否为末级
	Select @IsFinal = ID From (Select Top 1 ID  From Organization Where ParentID = @OrgId) A;
	If @IsFinal != ''
	Begin
		Select @DistrictNumber = DistrictNumber From Organization Where ID = @OrgId;
		If LEN(@DistrictNumber) >= 2 And SUBSTRING(@DistrictNumber,(LEN(@DistrictNumber) - 1),2) = '00'
		Begin
			Set @DistrictNumber = SUBSTRING(@DistrictNumber,1,(LEN(@DistrictNumber) - 2));
		End
		Set @SqlWhere = ' A.DistrictNumber Like ''' + @DistrictNumber + '%'' ';
		Set @SqlWomanWhere = ' A.DistrictNumber Like ''' + @DistrictNumber + '%'' ';
	End
	Else
	Begin
		Select @InputPersonIds = @InputPersonIds + '''' + loginname + ''',' From sam_taxempcode Where org_id = @OrgId;
		If @InputPersonIds != ''
		Begin
			Set @InputPersonIds = SUBSTRING(@InputPersonIds,1,LEN(@InputPersonIds) - 1);
		End
		Set @SqlWhere = ' A.InputPersonId In (' + @InputPersonIds + ') ';
		Set @SqlWomanWhere = ' B.InputPersonId In (' + @InputPersonIds + ') ';
	End
	
	Set @VHealthFileSql = ' Select ''农业人口档案'' As ItemName,Count(*) As Totals From HealthFile A,PersonalInfo B Where ' + @SqlWhere + ' And B.FarmStatus = ''是'' And A.FileNo = B.FileNo ';
	Set @CHealthFileSql = ' Select ''城镇人口档案'' As ItemName,Count(*) As Totals From HealthFile A,PersonalInfo B Where ' + @SqlWhere + ' And FarmStatus = ''否'' And A.FileNo = B.FileNo ';
	--Set @CHealthFileSql = ' Select ''城镇人口档案'' As ItemName,Count(*) As Totals From HealthFile A,PersonalInfo B Where ' + @SqlWhere + ' And FarmStatus = ''否'' And A.FileNo = B.FileNo ';
	Set @ChildrenHealthFileSql = ' Select ''儿童档案'' As ItemName,Count(*) As Totals From HealthFile A,(
		Select * From(
			Select FileNo,
				(Case 
					When (DATEPART(DY,GETDATE()) - DATEPART(DY,Birthday)) < 0 Then DATEDIFF(YEAR,Birthday,GETDATE()) - 1 
					When (DATEPART(DY,GETDATE()) - DATEPART(DY,Birthday)) >= 0 Then DATEDIFF(YEAR,Birthday,GETDATE())
				End) As Age
			From PersonalInfo) B Where Age <= 6 
		) B Where ' + @SqlWhere + ' And A.FileNo = B.FileNo ';
	Set @TmpChildrenHighRiskHealthFileSql = 'Select * Into #Tmp_Child_IsHighRisk From 
			(Select FileNo,InputDate From BabyVisit Where High_Risk = ''是''
			Union All
			Select FileNo,InputDate From ChildrenMediExam Where High_Risk = ''是''
			Union All
			Select FileNo,InputDate From ChildrenMediExam3_6 Where High_Risk = ''是'') A;
			Select * Into #Tmp_Child_IsNotHighRisk From 
			(Select FileNo,InputDate From BabyVisit Where High_Risk = ''否''
			Union All
			Select FileNo,InputDate From ChildrenMediExam Where High_Risk = ''否''
			Union All
			Select FileNo,InputDate From ChildrenMediExam3_6 Where High_Risk = ''否'') A;';	
	Set @ChildrenHighRiskHealthFileSql = ' Select ''高危儿童档案'' As ItemName,Count(*) As Totals From HealthFile A,(
			Select B.FileNo,B.InputDate From 
				(Select FileNo From
						(Select FileNo,
							(Case 
								When (DATEPART(DY,GETDATE()) - DATEPART(DY,Birthday)) < 0 Then DATEDIFF(YEAR,Birthday,GETDATE()) - 1 
								When (DATEPART(DY,GETDATE()) - DATEPART(DY,Birthday)) >= 0 Then DATEDIFF(YEAR,Birthday,GETDATE())
							End) As Age
						From PersonalInfo) B Where Age <= 6) A,
					(Select A.FileNo,A.InputDate From
						(Select * From #Tmp_Child_IsHighRisk Where InputDate In(Select MAX(InputDate) From #Tmp_Child_IsHighRisk Group By FileNo)) A,
						(Select * From #Tmp_Child_IsNotHighRisk Where InputDate In(Select MAX(InputDate) From #Tmp_Child_IsNotHighRisk Group By FileNo)) B 
					Where A.FileNo = B.FileNo And A.InputDate > B.InputDate) B 
				Where A.FileNo = B.FileNo
			) B Where ' + @SqlWhere + ' And A.FileNo = B.FileNo ';
	
	Set @WomanInitBirthHealthFileSql = 'Select ''未结案孕产妇档案'' As ItemName,Count(*) As Totals From HealthFile A,HealthFileMaternal B Where ' +
		@SqlWhere + ' And A.FileNo = B.FileNo And B.IsClosed = 0';
	Set @WomanAreadyBirthHealthFileSql = 'Select ''已结案孕产妇档案'' As ItemName,Count(*) As Totals From HealthFile A,HealthFileMaternal B Where ' +
		@SqlWhere + ' And A.FileNo = B.FileNo And B.IsClosed = 1';
	Set @WomanExceptionBirthHealthFileSql = 'Select ''终止妊娠的孕产妇档案'' As ItemName,Count(*) As Totals From HealthFile A,HealthFileMaternal B Where ' +
		@SqlWhere + ' And A.FileNo = B.FileNo And B.IsClosed = 2';
	Set @TmpWomanHighRiskHealthFileSql = 'Select * Into #Tmp_Woman_IsHighRisk From
			(Select FileNo,Gravidity,InputDate From FirstVistBeforeBorn Where High_Risk = ''是'' And Gravidity Is Not NULL
			Union All
			Select FileNo,Gravidity,InputDate From VisitBeforeBorn Where High_Risk = ''是'' And Gravidity Is Not NULL ) A;
			Select * Into #Tmp_Woman_IsNotHighRisk From
			(Select FileNo,Gravidity,InputDate From FirstVistBeforeBorn Where High_Risk = ''否'' And Gravidity Is Not NULL
			Union All
			Select FileNo,Gravidity,InputDate From VisitBeforeBorn Where High_Risk = ''否'' And Gravidity Is Not NULL ) A;
			Select A.* Into #Tmp_Woman_SureIsHighRisk From 
				(Select * From #Tmp_Woman_IsHighRisk Where InputDate In (Select MAX(InputDate) From #Tmp_Woman_IsHighRisk Group By FileNo,Gravidity))A,
				(Select * From #Tmp_Woman_IsNotHighRisk Where InputDate In (Select MAX(InputDate) From #Tmp_Woman_IsNotHighRisk Group By FileNo,Gravidity)) B 
			Where A.FileNo = B.FileNo And A.InputDate > B.InputDate;';
	Set @WomanHighRiskInitBirthHealthFileSql = 'Select ''未结案的高危孕产妇档案'' As ItemName,Count(*) As Totals From HealthFile A,(
		Select B.* From #Tmp_Woman_SureIsHighRisk A Join HealthFileMaternal B On A.FileNo = B.FileNo And A.Gravidity = B.Gravidity And B.IsClosed = 0
		) B Where ' + @SqlWhere + ' And A.FileNo = B.FileNo ';
	Set @WomanHighRiskAreadyBirthHealthFileSql = 'Select ''已结案的高危孕产妇档案'' As ItemName,Count(*) As Totals From HealthFile A,(
		Select B.* From #Tmp_Woman_SureIsHighRisk A Join HealthFileMaternal B On A.FileNo = B.FileNo And A.Gravidity = B.Gravidity And B.IsClosed = 1
		) B Where ' + @SqlWhere + ' And A.FileNo = B.FileNo ';
	Set @WomanHighRiskExceptionBirthHealthFileSql = 'Select ''终止妊娠的高危孕产妇档案'' As ItemName,Count(*) As Totals From HealthFile A,(
		Select B.* From #Tmp_Woman_SureIsHighRisk A Join HealthFileMaternal B On A.FileNo = B.FileNo And A.Gravidity = B.Gravidity And B.IsClosed = 2
		) B Where ' + @SqlWhere + ' And A.FileNo = B.FileNo ';
	
	Set @OldManHealthFileSql = ' Select ''老年人档案'' As ItemName,Count(*) As Totals From HealthFile A,(
		Select * From(
			Select FileNo,
				(Case 
					When (DATEPART(DY,GETDATE()) - DATEPART(DY,Birthday)) < 0 Then DATEDIFF(YEAR,Birthday,GETDATE()) - 1 
					When (DATEPART(DY,GETDATE()) - DATEPART(DY,Birthday)) >= 0 Then DATEDIFF(YEAR,Birthday,GETDATE())
				End) As Age
			From PersonalInfo) B Where Age > 65 
		) B Where ' + @SqlWhere + ' And A.FileNo = B.FileNo ';
	
	Set @HypertensionHealthFileSql = ' Select ''高血压档案'' As ItemName,Count(*) As Totals From HealthFile A,(
		Select FileNo From PersonalInfo A,diseaseHistory B Where A.ID = B.PersonalInfoID And DiseaseID = 2
		) B Where ' + @SqlWhere + 'And A.FileNo = B.FileNo ';
	Set @DiabetesHealthFileSql = ' Select ''糖尿病档案'' As ItemName,Count(*) As Totals From HealthFile A,(
		Select FileNo From PersonalInfo A,diseaseHistory B Where A.ID = B.PersonalInfoID And DiseaseID = 3
		) B Where ' + @SqlWhere + 'And A.FileNo = B.FileNo ';
	Set @HolergasiaHealthFileSql =  ' Select ''重性精神病档案'' As ItemName,Count(*) As Totals From HealthFile A,(
		Select FileNo From PersonalInfo A,diseaseHistory B Where A.ID = B.PersonalInfoID And DiseaseID = 8
		) B Where ' + @SqlWhere + 'And A.FileNo = B.FileNo ';
		
	Set @UnionSql = Case When @VHealthFileSql != '' Then @VHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @CHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @CHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @ChildrenHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @ChildrenHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @ChildrenHighRiskHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @ChildrenHighRiskHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @WomanInitBirthHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @WomanInitBirthHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @WomanAreadyBirthHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @WomanAreadyBirthHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @WomanExceptionBirthHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @WomanExceptionBirthHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @WomanHighRiskInitBirthHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @WomanHighRiskInitBirthHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @WomanHighRiskAreadyBirthHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @WomanHighRiskAreadyBirthHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @WomanHighRiskExceptionBirthHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @WomanHighRiskExceptionBirthHealthFileSql Else '' End;
	
	Set @UnionSql = @UnionSql + Case When @OldManHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @OldManHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @HypertensionHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @HypertensionHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @DiabetesHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @DiabetesHealthFileSql Else '' End;
	Set @UnionSql = @UnionSql + Case When @HolergasiaHealthFileSql != '' Then (Case When @UnionSql != '' Then ' Union All ' Else '' End) + @HolergasiaHealthFileSql Else '' End;
	
	Set @ExecSql = @TmpChildrenHighRiskHealthFileSql + @TmpWomanHighRiskHealthFileSql + 
			@UnionSql;
	Print(@ExecSql);
	Exec(@ExecSql);
End
GO