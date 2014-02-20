--存储过程如果存在，则先删除
If Exists(Select * From sys.procedures Where name = 'PrintInformationProc') 
Begin
	Drop Proc PrintInformationProc
End
GO
--函数如果存在，则先删除
If Exists(Select * From sysobjects Where id = object_id(N'[dbo].[TrimFn]') and xtype in (N'FN', N'IF', N'TF'))
Begin
	Drop Function TrimFn
End
GO

Create Function TrimFn(@params Nvarchar(MAX),@type int) 
Returns @t table(params Nvarchar(MAX))
As 
Begin
	Declare @retVal Nvarchar(MAX)
	If @type = 0
	Begin
		If LEN(@params) > 0
		Begin
			Set @retVal = SUBSTRING(@params,1,LEN(@params) - 1);
		End
		Else
		Begin
			Set @retVal = '';
		End
	End
	Else If @type = 1
	Begin
		If LEN(@params) > 0
		Begin
			Set @retVal = '[' + SUBSTRING(@params,1,LEN(@params) - 1) + ']';
		End
	End
	Insert Into @t Values(@retVal)
	Return
End
GO


Create Proc PrintInformationProc(
	@printType Nvarchar(10) = '0',
	@printWhere Nvarchar(36) = ''
)As
Begin
	--参数声明
	Declare @PersonalInfoId Nvarchar(36),
		@PaymentModes Nvarchar(MAX),
		@AllergiesHistorys Nvarchar(MAX),
		@ExposeHistorys Nvarchar(MAX),
		@DiseaseHistorys Nvarchar(MAX),
		@OPSHistorys Nvarchar(MAX),
		@TraumaHistorys Nvarchar(MAX),
		@BloodTranses Nvarchar(MAX),
		@FatherHistorys Nvarchar(MAX),
		@MatherHistorys Nvarchar(MAX),
		@BrotherHistorys Nvarchar(MAX),
		@FamilyHistorys Nvarchar(MAX),
		@DisabilityStatuses Nvarchar(MAX),
		@ExamSymptoms Nvarchar(MAX),
		@MedicalExamId Nvarchar(36),
		@EatHabits Nvarchar(MAX),
		@Galactophores Nvarchar(MAX),
		@HarnsSicks Nvarchar(MAX),
		@KidneySicks Nvarchar(MAX),
		@HeartSicks Nvarchar(MAX),
		@VasSicks Nvarchar(MAX),
		@EyeSicks Nvarchar(MAX),
		@Hospitalizations Nvarchar(MAX),
		@ExamMedications Nvarchar(MAX),
		@VaccinateHistorys Nvarchar(MAX),
		@HealthDirects Nvarchar(MAX),
		@DangerControls Nvarchar(MAX),
		@DrinkHabits Nvarchar(MAX),
		@Life21 Nvarchar(MAX),
		@Life23 Nvarchar(MAX),
		@Life25 Nvarchar(MAX),
		@Life27 Nvarchar(MAX),
		@Life29 Nvarchar(MAX),
		@Exam29 Nvarchar(MAX),@Body01 Nvarchar(MAX),@Body02 Nvarchar(MAX),@Body03 Nvarchar(MAX),
		@Body05 Nvarchar(MAX),@Body06 Nvarchar(MAX),@Body09 Nvarchar(MAX),@Body10 Nvarchar(MAX),
		@Body12 Nvarchar(MAX),@Body13 Nvarchar(MAX),@Body14 Nvarchar(MAX),@Body15 Nvarchar(MAX),
		@Body18 Nvarchar(MAX),@Body20 Nvarchar(MAX),@Body21 Nvarchar(MAX),@Body22 Nvarchar(MAX),
		@Body23 Nvarchar(MAX),@Body24 Nvarchar(MAX),@Exam31 Nvarchar(MAX),@Exam32 Nvarchar(MAX),
		@Exam33 Nvarchar(MAX),@GeneticHistory Nvarchar(MAX),@Folk Nvarchar(MAX)
	--参数初始化
	Select @PersonalInfoId = '',@PaymentModes = '',@AllergiesHistorys = '',@ExposeHistorys = '',@DiseaseHistorys = '',@OPSHistorys = '',
		@TraumaHistorys = '',@BloodTranses = '',@FatherHistorys = '',@MatherHistorys = '',@BrotherHistorys = '',@FamilyHistorys = '',
		@DisabilityStatuses = '',@ExamSymptoms = '',@MedicalExamId = @printWhere,@EatHabits = '',@Galactophores = '',@HarnsSicks = '',
		@KidneySicks = '',@HeartSicks = '',@VasSicks = '',@EyeSicks = '',@Hospitalizations = '',@ExamMedications = '',@VaccinateHistorys = '',
		@HealthDirects = '',@DangerControls = '',@DrinkHabits = '',@Life21 = '',@Life23 = '',@Life25 = '',@Life27 = '',@Life29 = '',
		@Exam29 = '',@Body01 = '',@Body02 = '',@Body03 = '',@Body05 = '',@Body06 = '',@Body09 = '',@Body10 = '',@Body12 = '',@Body13 = '',
		@Body14 = '',@Body15 = '',@Body18 = '',@Body20 = '',@Body21 = '',@Body22 = '',@Body23 = '',@Body24 = '',@Exam31 = '',@Exam32 = '',
		@Exam33 = '',@GeneticHistory = '',@Folk = ''
	--打印居民健康档案
	If @printType = '0'
	Begin
		Select @PersonalInfoId = ID From PersonalInfo Where FileNo = @printWhere;
		--医疗费用支付方式
		Select @PaymentModes = @PaymentModes + B.Name + ',' From PaymentMode A,BasicInformation B Where A.PaymentModeID = B.ID And PersonalInfoID = @PersonalInfoId
		Select @PaymentModes = params From dbo.TrimFn(@PaymentModes,0);
		Select @PaymentModes = @PaymentModes + (Case When PaymentModeOther Is NULL Then '' Else (Case When rtrim(ltrim(PaymentModeOther)) = '' Then '' Else ',' + PaymentModeOther End) End) From PersonalInfo Where ID = @PersonalInfoId
		--药物过敏史
		Select @AllergiesHistorys = @AllergiesHistorys + B.Name + ',' From AllergiesHistory A,BasicInformation B Where A.AllergiesID = B.ID And PersonalInfoID = @PersonalInfoId
		Select @AllergiesHistorys = params From dbo.TrimFn(@AllergiesHistorys,0);
		Select @AllergiesHistorys = @AllergiesHistorys + (Case When AllergiesOther Is NULL Then '' Else (Case When rtrim(ltrim(AllergiesOther)) = '' Then '' Else ',' + AllergiesOther End) End) From PersonalInfo Where ID = @PersonalInfoId
		--暴露史
		Select @ExposeHistorys = @ExposeHistorys + B.Name + ',' From ExposeHistory A,BasicInformation B Where A.ExposeID = B.ID And PersonalInfoID = @PersonalInfoId
		Select @ExposeHistorys = params From dbo.TrimFn(@ExposeHistorys,0);
		--既往史
		Select @DiseaseHistorys = @DiseaseHistorys + '{"Name":"' + B.Name + '","ConfirmDate":"' + SUBSTRING(REPLACE(A.ConfirmDate,'-',''),1,8) + '","Remark":"' + A.Remark + '"},' From DiseaseHistory A,BasicInformation B Where A.DiseaseID = B.Number And PersonalInfoID = @PersonalInfoId And B.[Type] = 38
		Select @DiseaseHistorys = params From dbo.TrimFn(@DiseaseHistorys,1);
		--手术
		Select @OPSHistorys = @OPSHistorys + '{"OPSName":"' + A.OPSName + '","OPSDate":"' + A.OPSDate + '"},' From OPSHistory A Where PersonalInfoID = @PersonalInfoId
		Select @OPSHistorys = params From dbo.TrimFn(@OPSHistorys,1);
		--外伤
		Select @TraumaHistorys = @TraumaHistorys + '{"TraumaName":"' + A.TraumaName + '","TraumaDate":"' + A.TraumaDate + '"},' From TraumaHistory A Where PersonalInfoID = @PersonalInfoId
		Select @TraumaHistorys = params From dbo.TrimFn(@TraumaHistorys,1);
		--输血
		Select @BloodTranses = @BloodTranses + '{"Reason":"' + A.Reason + '","TransDate":"' + A.TransDate + '"},' From BloodTrans A Where PersonalInfoID = @PersonalInfoId
		Select @BloodTranses = params From dbo.TrimFn(@BloodTranses,1);
		--家族史 父亲
		Select @FatherHistorys = @FatherHistorys + B.Name + ',' From FatherHistory A,BasicInformation B Where A.HeredityID = B.ID And PersonalInfoID = @PersonalInfoId
		Select @FatherHistorys = params From dbo.TrimFn(@FatherHistorys,0);
		Select @FatherHistorys = @FatherHistorys + (Case When fHistoryOther Is NULL Then '' Else (Case When rtrim(ltrim(fHistoryOther)) = '' Then '' Else ',' + fHistoryOther End) End) From PersonalInfo Where ID = @PersonalInfoId
		--家族史 母亲
		Select @MatherHistorys = @MatherHistorys + B.Name + ',' From MatherHistory A,BasicInformation B Where A.HeredityID = B.ID And PersonalInfoID = @PersonalInfoId
		Select @MatherHistorys = params From dbo.TrimFn(@MatherHistorys,0);
		Select @MatherHistorys = @MatherHistorys + (Case When mHistoryOther Is NULL Then '' Else (Case When rtrim(ltrim(mHistoryOther)) = '' Then '' Else ',' + mHistoryOther End) End) From PersonalInfo Where ID = @PersonalInfoId
		--家族史 兄弟姐妹
		Select @BrotherHistorys = @BrotherHistorys + B.Name + ',' From BrotherHistory A,BasicInformation B Where A.HeredityID = B.ID And PersonalInfoID = @PersonalInfoId
		Select @BrotherHistorys = params From dbo.TrimFn(@BrotherHistorys,0);
		Select @BrotherHistorys = @BrotherHistorys + (Case When bHistoryOther Is NULL Then '' Else (Case When rtrim(ltrim(bHistoryOther)) = '' Then '' Else ',' + bHistoryOther End) End) From PersonalInfo Where ID = @PersonalInfoId
		--家族史 子女
		Select @FamilyHistorys = @FamilyHistorys + B.Name + ',' From FamilyHistory A,BasicInformation B Where A.HeredityID = B.ID And PersonalInfoID = @PersonalInfoId
		Select @FamilyHistorys = params From dbo.TrimFn(@FamilyHistorys,0);
		Select @FamilyHistorys = @FamilyHistorys + (Case When fmHistoryOther Is NULL Then '' Else (Case When rtrim(ltrim(fmHistoryOther)) = '' Then '' Else ',' + fmHistoryOther End) End) From PersonalInfo Where ID = @PersonalInfoId
		--残疾情况
		Select @DisabilityStatuses = @DisabilityStatuses + B.Name + ',' From DisabilityStatus A,BasicInformation B Where A.DisabilityStatusID = B.ID And PersonalInfoID = @PersonalInfoId
		Select @DisabilityStatuses = params From dbo.TrimFn(@DisabilityStatuses,0);
		Select @DisabilityStatuses = @DisabilityStatuses + (Case When DisabilityStatusOther Is NULL Then '' Else (Case When rtrim(ltrim(DisabilityStatusOther)) = '' Then '' Else ',' + DisabilityStatusOther End) End) From PersonalInfo Where ID = @PersonalInfoId
		--遗传病史
		Select @GeneticHistory = GeneticHistory + (Case When GeneticHistoryOther Is NULL Then '' Else (Case When rtrim(ltrim(GeneticHistoryOther)) = '' Then '' Else ',' + GeneticHistoryOther End) End) From PersonalInfo Where ID = @PersonalInfoId
		--民族
		Select @Folk = Folk + (Case When FolkOther Is NULL Then '' Else (Case When rtrim(ltrim(FolkOther)) = '' Then '' Else ',' + FolkOther End) End) From PersonalInfo Where ID = @PersonalInfoId
		
		Select A.FileNo,A.Name,A.[Address],A.ResidenceAddress,A.TEL,A.Township,A.Village,A.BuildUnit,A.BuildPerson,
			A.Doctor,A.BuildDate,B.Sex,B.Birthday,B.IDNumber,B.WorkUnit,B.TEL As OwnTel,B.Linkman,B.LinkmanTEL,
			B.ResideType,@Folk As Folk,B.BloodTypeABO,B.BloodTypeRH,B.Education,B.Occupation,B.MaritalStatus,
			@PaymentModes As PaymentMode,@AllergiesHistorys As AllergiesHistory,
			@ExposeHistorys As ExposeHistory,@DiseaseHistorys As DiseaseHistory,@OPSHistorys As OPSHistory,
			@TraumaHistorys As TraumaHistory,@BloodTranses As BloodTrans,@FatherHistorys As FatherHistory,
			@MatherHistorys As MatherHistory,@BrotherHistorys As BrotherHistory,
			@FamilyHistorys As FamilyHistory,@GeneticHistory As GeneticHistory,
			@DisabilityStatuses As DisabilityStatus,B.Kitchen,B.Bunkers,B.DrinkingWater,B.Toilet,
			B.Poultry
		From HealthFile A,PersonalInfo B Where A.FileNo = B.FileNo And A.FileNo = @printWhere;
	End
	Else If @printType = 1
	Begin
		--症状
		Select @ExamSymptoms = @ExamSymptoms + B.Name + ',' From ExamSymptom A,BasicInformation B Where A.ExamSymptomID = B.ID And A.MedicalExamID = @MedicalExamId
		Select @ExamSymptoms = params From dbo.TrimFn(@ExamSymptoms,0);
		Select @ExamSymptoms = @ExamSymptoms + (Case When SymptomOther Is NULL Then '' Else (Case When rtrim(ltrim(SymptomOther)) = '' Then '' Else ',' + SymptomOther End) End) From MedicalExam Where ID = @MedicalExamId
		--饮食习惯
		Select @EatHabits = @EatHabits + B.Name + ',' From EatHabit A,BasicInformation B Where A.EatHabitID = B.ID And A.MedicalExamID = @MedicalExamId
		Select @EatHabits = params From dbo.TrimFn(@EatHabits,0);
		--饮酒种类
		Select @DrinkHabits = @DrinkHabits + B.Name + ',' From DrinkHabit A,BasicInformation B Where A.DrinkHabitID = B.ID And A.MedicalExamID = @MedicalExamId
		Select @DrinkHabits = params From dbo.TrimFn(@DrinkHabits,0);
		Select @DrinkHabits = @DrinkHabits + (Case When Life16Other Is NULL Then '' Else (Case When rtrim(ltrim(Life16Other)) = '' Then '' Else ',' + Life16Other End) End) From MedicalExam Where ID = @MedicalExamId
		--防护措施
		Select @Life21 = B.Name From MedicalExam A,BasicInformation B Where A.ID = @MedicalExamId And B.ID != 151 And B.[Type] = 151 And A.Life21 = B.IsInputValue
		Select @Life23 = B.Name From MedicalExam A,BasicInformation B Where A.ID = @MedicalExamId And B.ID != 151 And B.[Type] = 151 And A.Life23 = B.IsInputValue
		Select @Life25 = B.Name From MedicalExam A,BasicInformation B Where A.ID = @MedicalExamId And B.ID != 151 And B.[Type] = 151 And A.Life25 = B.IsInputValue
		Select @Life27 = B.Name From MedicalExam A,BasicInformation B Where A.ID = @MedicalExamId And B.ID != 151 And B.[Type] = 151 And A.Life27 = B.IsInputValue
		Select @Life29 = B.Name From MedicalExam A,BasicInformation B Where A.ID = @MedicalExamId And B.ID != 151 And B.[Type] = 151 And A.Life29 = B.IsInputValue
		Select @Life21 = @Life21 + (Case When Life21Other Is NULL Then '' Else (Case When rtrim(ltrim(Life21Other)) = '' Then '' Else ',' + Life21Other End) End) From MedicalExam Where ID = @MedicalExamId
		Select @Life23 = @Life23 + (Case When Life23Other Is NULL Then '' Else (Case When rtrim(ltrim(Life23Other)) = '' Then '' Else ',' + Life23Other End) End) From MedicalExam Where ID = @MedicalExamId
		Select @Life25 = @Life25 + (Case When Life25Other Is NULL Then '' Else (Case When rtrim(ltrim(Life25Other)) = '' Then '' Else ',' + Life25Other End) End) From MedicalExam Where ID = @MedicalExamId
		Select @Life27 = @Life27 + (Case When Life27Other Is NULL Then '' Else (Case When rtrim(ltrim(Life27Other)) = '' Then '' Else ',' + Life27Other End) End) From MedicalExam Where ID = @MedicalExamId
		Select @Life29 = @Life29 + (Case When Life29Other Is NULL Then '' Else (Case When rtrim(ltrim(Life29Other)) = '' Then '' Else ',' + Life29Other End) End) From MedicalExam Where ID = @MedicalExamId
		--乳腺
		Select @Galactophores = @Galactophores + B.Name + ',' From Galactophore A,BasicInformation B Where A.GalactophoreID = B.ID And A.MedicalExamID = @MedicalExamId
		Select @Galactophores = params From dbo.TrimFn(@Galactophores,0);
		Select @Galactophores = @Galactophores + (Case When Body19Other Is NULL Then '' Else (Case When rtrim(ltrim(Body19Other)) = '' Then '' Else ',' + Body19Other End) End) From MedicalExam Where ID = @MedicalExamId
		--脑血管疾病
		Select @HarnsSicks = @HarnsSicks + B.Name + ',' From HarnsSick A,BasicInformation B Where A.HarnsSickID = B.ID And A.MedicalExamID = @MedicalExamId
		Select @HarnsSicks = params From dbo.TrimFn(@HarnsSicks,0);
		Select @HarnsSicks = @HarnsSicks + (Case When Problem01Other Is NULL Then '' Else (Case When rtrim(ltrim(Problem01Other)) = '' Then '' Else ',' + Problem01Other End) End) From MedicalExam Where ID = @MedicalExamId
		--肾脏疾病
		Select @KidneySicks = @KidneySicks + B.Name + ',' From KidneySick A,BasicInformation B Where A.KidneySickID = B.ID And A.MedicalExamID = @MedicalExamId
		Select @KidneySicks = params From dbo.TrimFn(@KidneySicks,0);
		Select @KidneySicks = @KidneySicks + (Case When Problem02Other Is NULL Then '' Else (Case When rtrim(ltrim(Problem02Other)) = '' Then '' Else ',' + Problem02Other End) End) From MedicalExam Where ID = @MedicalExamId
		--心脏疾病
		Select @HeartSicks = @HeartSicks + B.Name + ',' From HeartSick A,BasicInformation B Where A.HeartSickID = B.ID And A.MedicalExamID = @MedicalExamId
		Select @HeartSicks = params From dbo.TrimFn(@HeartSicks,0);
		Select @HeartSicks = @HeartSicks + (Case When Problem03Other Is NULL Then '' Else (Case When rtrim(ltrim(Problem03Other)) = '' Then '' Else ',' + Problem03Other End) End) From MedicalExam Where ID = @MedicalExamId
		--血管疾病
		Select @VasSicks = @VasSicks + B.Name + ',' From VasSick A,BasicInformation B Where A.VasSickID = B.ID And A.MedicalExamID = @MedicalExamId
		Select @VasSicks = params From dbo.TrimFn(@VasSicks,0);
		Select @VasSicks = @VasSicks + (Case When Problem04Other Is NULL Then '' Else (Case When rtrim(ltrim(Problem04Other)) = '' Then '' Else ',' + Problem04Other End) End) From MedicalExam Where ID = @MedicalExamId
		--眼部疾病
		Select @EyeSicks = @EyeSicks + B.Name + ',' From EyeSick A,BasicInformation B Where A.EyeSickID = B.ID And A.MedicalExamID = @MedicalExamId
		Select @EyeSicks = params From dbo.TrimFn(@EyeSicks,0);
		Select @EyeSicks = @EyeSicks + (Case When Problem05Other Is NULL Then '' Else (Case When rtrim(ltrim(Problem05Other)) = '' Then '' Else ',' + Problem05Other End) End) From MedicalExam Where ID = @MedicalExamId
		--住院治疗情况
		Select @Hospitalizations = @Hospitalizations + '{"Type":"' + A.[Type] + '","BeginDate":"' + Replace(CONVERT(Nvarchar(10),A.BeginDate,120),'-','') + '","EndDate":"' + Replace(CONVERT(Nvarchar(10),A.EndDate,120),'-','') + '","Reason":"' + A.Reason + '","Hospital":"' + A.Hospital + '","ReportNo":"' + A.ReportNo + '"},' From Hospitalization A Where MedicalExamID = @MedicalExamId
		Select @Hospitalizations = params From dbo.TrimFn(@Hospitalizations,1);
		--主要用药情况
		Select @ExamMedications = @ExamMedications + '{"DrugName":"' + A.DrugName + '","UsageWay":"' + A.UsageWay + '","Dosage":"' + A.Dosage + '","DrugTime":"' + A.DrugTime + '","Dependency":"' + A.Dependency + '"},' From ExamMedications A Where MedicalExamID = @MedicalExamId
		Select @ExamMedications = params From dbo.TrimFn(@ExamMedications,1);
		--非免疫规划预防接种史
		Select @VaccinateHistorys = @VaccinateHistorys + '{"BacterinName":"' + A.BacterinName + '","VDate":"' + Replace(CONVERT(Nvarchar(10),A.VDate,120),'-','') + '","Hostpital":"' + A.Hostpital + '"},' From VaccinateHistory A Where MedicalExamID = @MedicalExamId
		Select @VaccinateHistorys = params From dbo.TrimFn(@VaccinateHistorys,1);
		--健康指导
		Select @HealthDirects = @HealthDirects + B.Name + ',' From HealthDirect A,BasicInformation B Where A.HealthDirectID = B.ID And A.MedicalExamID = @MedicalExamId
		Select @HealthDirects = params From dbo.TrimFn(@HealthDirects,0);
		--危险因素控制
		Select @DangerControls = @DangerControls + B.Name + ',' From DangerControl A,BasicInformation B Where A.DangerControlID = B.ID And A.MedicalExamID = @MedicalExamId
		Select @DangerControls = params From dbo.TrimFn(@DangerControls,0);
		Select @DangerControls = @DangerControls + (Case When dangerControlOther3 Is NULL Then '' Else (Case When rtrim(ltrim(dangerControlOther3)) = '' Then '' Else ',' + dangerControlOther3 End) End) From MedicalExam Where ID = @MedicalExamId

		--眼底*
		Select @Exam29 = Exam29 + (Case When Exam29Other Is NULL Then '' Else (Case When rtrim(ltrim(Exam29Other)) = '' Then '' Else ',' + Exam29Other End) End) From MedicalExam Where ID = @MedicalExamId
		--皮肤
		Select @Body01 = Body01 + (Case When Body01Other Is NULL Then '' Else (Case When rtrim(ltrim(Body01Other)) = '' Then '' Else ',' + Body01Other End) End) From MedicalExam Where ID = @MedicalExamId
		--巩膜
		Select @Body02 = Body02 + (Case When Body02Other Is NULL Then '' Else (Case When rtrim(ltrim(Body02Other)) = '' Then '' Else ',' + Body02Other End) End) From MedicalExam Where ID = @MedicalExamId
		--淋巴结
		Select @Body03 = Body03 + (Case When Body03Other Is NULL Then '' Else (Case When rtrim(ltrim(Body03Other)) = '' Then '' Else ',' + Body03Other End) End) From MedicalExam Where ID = @MedicalExamId
		--呼吸音
		Select @Body05 = Body05 + (Case When Body05Other Is NULL Then '' Else (Case When rtrim(ltrim(Body05Other)) = '' Then '' Else ',' + Body05Other End) End) From MedicalExam Where ID = @MedicalExamId
		--罗音
		Select @Body06 = Body06 + (Case When Body06Other Is NULL Then '' Else (Case When rtrim(ltrim(Body06Other)) = '' Then '' Else ',' + Body06Other End) End) From MedicalExam Where ID = @MedicalExamId
		--杂音
		Select @Body09 = Body09 + (Case When Body09Other Is NULL Then '' Else (Case When rtrim(ltrim(Body09Other)) = '' Then '' Else ',' + Body09Other End) End) From MedicalExam Where ID = @MedicalExamId
		--压痛
		Select @Body10 = Body10 + (Case When Body10Other Is NULL Then '' Else (Case When rtrim(ltrim(Body10Other)) = '' Then '' Else ',' + Body10Other End) End) From MedicalExam Where ID = @MedicalExamId
		--包块
		Select @Body12 = Body12 + (Case When Body12Other Is NULL Then '' Else (Case When rtrim(ltrim(Body12Other)) = '' Then '' Else ',' + Body12Other End) End) From MedicalExam Where ID = @MedicalExamId
		--肝大
		Select @Body13 = Body13 + (Case When Body13Other Is NULL Then '' Else (Case When rtrim(ltrim(Body13Other)) = '' Then '' Else ',' + Body13Other End) End) From MedicalExam Where ID = @MedicalExamId
		--脾大
		Select @Body14 = Body14 + (Case When Body14Other Is NULL Then '' Else (Case When rtrim(ltrim(Body14Other)) = '' Then '' Else ',' + Body14Other End) End) From MedicalExam Where ID = @MedicalExamId
		--移动性浊音
		Select @Body15 = Body15 + (Case When Body15Other Is NULL Then '' Else (Case When rtrim(ltrim(Body15Other)) = '' Then '' Else ',' + Body15Other End) End) From MedicalExam Where ID = @MedicalExamId
		--肛门指诊*
		Select @Body18 = Body18 + (Case When Body18Other Is NULL Then '' Else (Case When rtrim(ltrim(Body18Other)) = '' Then '' Else ',' + Body18Other End) End) From MedicalExam Where ID = @MedicalExamId
		--外阴*
		Select @Body20 = Body20 + (Case When Body20Other Is NULL Then '' Else (Case When rtrim(ltrim(Body20Other)) = '' Then '' Else ',' + Body20Other End) End) From MedicalExam Where ID = @MedicalExamId
		--阴道*
		Select @Body21 = Body21 + (Case When Body21Other Is NULL Then '' Else (Case When rtrim(ltrim(Body21Other)) = '' Then '' Else ',' + Body21Other End) End) From MedicalExam Where ID = @MedicalExamId
		--宫颈*
		Select @Body22 = Body22 + (Case When Body22Other Is NULL Then '' Else (Case When rtrim(ltrim(Body22Other)) = '' Then '' Else ',' + Body22Other End) End) From MedicalExam Where ID = @MedicalExamId
		--宫体*
		Select @Body23 = Body23 + (Case When Body23Other Is NULL Then '' Else (Case When rtrim(ltrim(Body23Other)) = '' Then '' Else ',' + Body23Other End) End) From MedicalExam Where ID = @MedicalExamId
		--附件*
		Select @Body24 = Body24 + (Case When Body24Other Is NULL Then '' Else (Case When rtrim(ltrim(Body24Other)) = '' Then '' Else ',' + Body24Other End) End) From MedicalExam Where ID = @MedicalExamId
		--胸片X线片*
		Select @Exam31 = Exam31 + (Case When Exam31Other Is NULL Then '' Else (Case When rtrim(ltrim(Exam31Other)) = '' Then '' Else ',' + Exam31Other End) End) From MedicalExam Where ID = @MedicalExamId
		--B超*
		Select @Exam32 = Exam32 + (Case When Exam32Other Is NULL Then '' Else (Case When rtrim(ltrim(Exam32Other)) = '' Then '' Else ',' + Exam32Other End) End) From MedicalExam Where ID = @MedicalExamId
		--宫颈涂片*
		Select @Exam33 = Exam33 + (Case When Exam33Other Is NULL Then '' Else (Case When rtrim(ltrim(Exam33Other)) = '' Then '' Else ',' + Exam33Other End) End) From MedicalExam Where ID = @MedicalExamId
		
		Select ID,A.FileNo,ExamDate,A.Doctor,General01,General02,General03,General04,General05,General06,General07,
			General08,General09,General10,General11,General12,General13,General14,General15,General16,General17,Life01,
			Life02,Life03,Life04,Life05Other,Life06,Life07,Life08,Life09,Life10,Life11,Life12,Life13,Life14,Life15,
			Life16Other,Life17,Life18,Life19,Life20,Life22,Life24,Viscera01,Viscera02,Viscera02Description,Viscera03,
			Viscera04,Viscera05,Viscera06,Viscera07,Viscera08,Viscera09,Body04,Body07,Body08,Body16,Body17,Body19Other,
			Body25,Exam01,Exam02,Exam03,Exam04,Exam05,Exam06,Exam07,Exam08,Exam09,Exam10,Exam11,Exam12,Exam13,Exam14,
			Exam15,Exam16,Exam17,Exam18,Exam19,Exam20,Exam21,Exam22,Exam23,Exam24,Exam25,Exam26,Exam27,Exam28,Exam30,
			Exam30Other,Exam34,CHN01,CHN02,
			CHN03,CHN04,CHN05,CHN06,CHN07,CHN08,CHN09,Problem01Other,Problem02Other,Problem03Other,Problem04Other,
			Problem05Other,Problem06,Problem06Other,Problem07,Problem07Other,Judge01,Judge02,Judge03,Judge04,Judge05,
			DangerControlOther1,DangerControlOther2,DangerControlOther3,A.InputPersonID,A.InputDate,Age,OldManHealthEstimate,
			OldManLifeEstimate,Life26,Life28,Viscera02Description1,Viscera02Description2,Viscera02Description3,ExecDistrictNum,
			B.Name,@ExamSymptoms As ExamSymptom,@EatHabits As EatHabit,@Galactophores As Galactophore,
			@HarnsSicks As HarnsSick,@KidneySicks As KidneySick,@HeartSicks As HeartSick,@VasSicks As VasSick,
			@EyeSicks As EyeSick,@Hospitalizations As Hospitalization,@ExamMedications As ExamMedication,
			@VaccinateHistorys As VaccinateHistory,@HealthDirects As HealthDirect,@DangerControls As DangerControl,
			@DrinkHabits As DrinkHabit,@Life21 As Life21,@Life23 As Life23,@Life25 As Life25,@Life27 As Life27,
			@Life29 As Life29,@Exam29 As Exam29,@Body01 As Body01,@Body02 As Body02,@Body03 As Body03,@Body05 As Body05,
			@Body06 As Body06,@Body09 As Body09,@Body10 As Body10,@Body12 As Body12,@Body13 As Body13,@Body14 As Body14,
			@Body15 As Body15,@Body18 As Body18,@Body20 As Body20,@Body21 As Body21,@Body22 As Body22,@Body23 As Body23,
			@Body24 As Body24,@Exam31 As Exam31,@Exam32 As Exam32,@Exam33 As Exam33
		From MedicalExam A,HealthFile B Where A.FileNo = B.FileNo And A.ID = @MedicalExamId
	End
End
GO