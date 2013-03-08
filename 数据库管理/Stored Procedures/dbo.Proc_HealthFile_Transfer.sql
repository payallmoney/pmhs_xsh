
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE PROC [dbo].[Proc_HealthFile_Transfer](
	@TransferId NVARCHAR(36),--转档ID
	@InputPersonId NVARCHAR(30),--操作人
	@NewFileNo NVARCHAR(32),--新的档案号
	@Address NVARCHAR(1000),--现住址
	@residenceAddress NVARCHAR(1000),--户籍地址
	@fromBuildDoctor NVARCHAR(30),--责任医生
	@fromBuildUnit NVARCHAR(100),--建档单位
	@fromBuildPerson NVARCHAR(30)--建档人
)AS
BEGIN
	DECLARE @OldFileNo NVARCHAR(32),--老的档案号
			@Sex NVARCHAR(20),--性别
			@DistrictNumber NVARCHAR(30)--行政区划名称
	SELECT @OldFileNo = FromFileNo,@Sex = Sex,@DistrictNumber = ToDistrictNumber FROM HealthFileTransfer WHERE ID = @TransferId
	IF @OldFileNo IS NOT NULL
	BEGIN
		BEGIN TRAN T1--开启事务
		--更新档案封面
		UPDATE HealthFile SET FileNo = @NewFileNo,[Status] = 0,DistrictNumber = @DistrictNumber,
			[Address] = @Address,ResidenceAddress = @residenceAddress,Doctor = @fromBuildDoctor,
			BuildUnit = @fromBuildUnit,BuildPerson = @fromBuildPerson
		WHERE FileNo = @OldFileNo
		--更新个人基本信息
		UPDATE PersonalInfo SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo
		--更新健康体检记录
		UPDATE MedicalExam SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo
		--儿童业务数据
		UPDATE BabyVisit SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--新生儿家庭访视
		UPDATE ChildrenMediExam SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--1岁以内及1~2岁儿童体检
		UPDATE ChildrenMediExam3_6 SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--3~6岁儿童体检
		UPDATE ChildLastMedicalExamRecord SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--最后一次体检记录
		IF @Sex = '女'
		BEGIN
			--孕产妇业务数据
			UPDATE HealthFileMaternal SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--第1次产前随访
			UPDATE FirstVistBeforeBorn SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--第1次产前随访
			UPDATE VisitBeforeBorn SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--第2~5次产前随访
			UPDATE VisitAfterBorn SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--产后访视及产后42天健康体检
			UPDATE WomanLastMedicalExamRecord SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--最后一次体检记录
			UPDATE ChildBirthRecord SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--分娩记录
		END
		--高血压随访
		UPDATE HypertensionVisit SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo
		--糖尿病随访
		UPDATE DiabetesVisit SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo
		--重性精神疾病随访
		UPDATE FuriousVisit SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo
		--预防接种业务
		UPDATE VaccineImmune SET VFileNo = @NewFileNo WHERE VFileNo = @OldFileNo--预防接种卡
		UPDATE VaccineImmuneHistoryStaticData SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--预防接种统计
		UPDATE VaccineImmuneInfo SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--预防接种疫苗信息
		--出生医学证明
		UPDATE BirthCertificate SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo
		--接诊、会诊、双向转诊、双向转诊回转
		UPDATE Reception SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--接诊
		UPDATE Consultation SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--会诊
		UPDATE CureSwitch SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--双向转诊
		UPDATE CureBack SET FileNo = @NewFileNo WHERE FileNo = @OldFileNo--双向转诊回转
		--更新转档表
		UPDATE HealthFileTransfer SET IsSure = 1,IsSureOpt = @InputPersonId,IsSureDate = GETDATE(),ToFileNo = @NewFileNo WHERE ID = @TransferId
		COMMIT TRAN T1--提交事务
	END
	ELSE
	BEGIN
		PRINT 'IS NULL'
	END
END
GO
