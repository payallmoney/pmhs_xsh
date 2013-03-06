SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO



Create Proc [dbo].[Proc_ListExamInfoByFile](
 @FileNos Nvarchar(Max) = ''
)As
Begin
	SET NOCOUNT ON
	CREATE TABLE #TMP(
		ID Nvarchar(36),
		FileNo NVARCHAR(32),
		Name NVARCHAR(20),
		ExamDate NVARCHAR(20),
		InputPersonID NVARCHAR(36),
		InputPersonName NVARCHAR(30),
		OrgName NVARCHAR(100),
		Flag NVARCHAR(10)
	)
	Select * Into #TmpFileNos From dbo.f_split(@FileNos,',')
	--健康体检记录
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.ExamDate,23),A.InputPersonID,C.username,D.Name,'1' As Flag
	From MedicalExam A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos);
	--新生儿家庭访视
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.VisitDate,23),A.InputPersonID,C.username,D.Name,'2' As Flag
	From BabyVisit A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos);
	--1岁以内
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.VisitDate,23),A.InputPersonID,C.username,D.Name,'3' As Flag
	From ChildrenMediExam A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos) And A.DataType = 0;
	--1~2岁
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,Cast(CONVERT(varchar(20),A.VisitDate,23) As Nvarchar(10)),A.InputPersonID,C.username,D.Name,'4' As Flag
	From ChildrenMediExam A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos) And A.DataType = 1;
	--3~6岁
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.VisitDate,23),A.InputPersonID,C.username,D.Name,'5' As Flag
	From ChildrenMediExam3_6 A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos);
	--第一次产前随访
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.VisitDate,23),A.InputPersonID,C.username,D.Name,'6' As Flag
	From FirstVistBeforeBorn A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos);
	--第2~5次产前随访
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.VisitDate,23),A.InputPersonID,C.username,D.Name,'7' As Flag
	From VisitBeforeBorn A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos);
	--产后访视
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.VisitDate,23),A.InputPersonID,C.username,D.Name,'8' As Flag
	From VisitAfterBorn A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos) And A.RecordType = 0;
	--产后42天访视
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.VisitDate,23),A.InputPersonID,C.username,D.Name,'9' As Flag
	From VisitAfterBorn A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos) And A.RecordType = 1;
	--重性精神病随访
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.VisitDate,23),A.InputPersonID,C.username,D.Name,'10' As Flag
	From FuriousVisit A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos);
	--重性精神病信息补充
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,'',A.InputPersonID,C.username,D.Name,11 As Flag
	From FuriousInfo A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos);
	--高血压随访
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.VisitDate,23),A.InputPersonID,C.username,D.Name,'12' As Flag
	From HypertensionVisit A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos);
	--糖尿病随访
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.VisitDate,23),A.InputPersonID,C.username,D.Name,'13' As Flag
	From DiabetesVisit A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos);
	--儿童保健册
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.BuildDate,23),A.InputPersonID,C.username,D.Name,'14' As Flag
	From HealthFileChildren A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos);
	--孕产妇保健册
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.BuildDate,23),A.InputPersonID,C.username,D.Name,'15' As Flag
	From HealthFileMaternal A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNO In (Select * From #TmpFileNos);
	--预防接种卡
	Insert Into #TMP
	Select A.ID,A.VFileNo,B.Name,CONVERT(varchar(20),A.VBuildCardDate,23),A.VInputPersonID,C.username,D.Name,'16' As Flag
	From VaccineImmune A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.VFileNo = B.FileNo And A.VInputPersonID = C.loginname And C.org_id = D.ID And 
		A.VFileNo In (Select * From #TmpFileNos);
	--疫苗接种信息
	Insert Into #TMP
	Select A.ID,A.FileNo,B.Name,CONVERT(varchar(20),A.VaccinationDate,23),A.InputPersonID,C.username,D.Name,'17' As Flag
	From VaccineImmuneInfo A,HealthFile B,sam_taxempcode C,Organization D 
	Where A.FileNo = B.FileNo And A.InputPersonID = C.loginname And C.org_id = D.ID And 
		A.FileNo In (Select * From #TmpFileNos);
	/*Select @Count_VaccineImmuneHistoryStaticData = COUNT(*) From VaccineImmuneHistoryStaticData Where FileNo = @FileNoOne;*/
	Select * From #TMP
	SET NOCOUNT OFF
End

GO
