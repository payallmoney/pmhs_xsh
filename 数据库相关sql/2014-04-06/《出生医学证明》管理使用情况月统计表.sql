 --保存类型 0为补发 1为医疗机构外首次签发 2为医疗机构内首次签发

 Alter Table BirthCertificate Add SaveType Nvarchar(10)
 GO

 --医疗保健机构外出生标记

 Insert Into BasicInformation Values(2021,0,'医疗保健机构外出生','',2021,0,1,1,0,0)
 Insert Into BasicInformation Values(3075,1,'家庭接生员接生','医疗保健机构外出生',2021,0,0,1,0,0)
 Insert Into BasicInformation Values(3076,2,'其它情况','医疗保健机构外出生',2021,0,0,1,0,0)
 GO

 /*
 Select * From BasicInformation Where type = 2021
 
 Update BasicInformation Set Name = '医疗保健机构外出生'  Where ID = 2021
 Update BasicInformation Set Name = '家庭接生员接生',Name_Png = '医疗保健机构外出生' Where ID = 3075
 Update BasicInformation Set Name = '其它情况',Name_Png = '医疗保健机构外出生' Where ID = 3076
 */


Create Table BirthCertifiReportWithMonthHistory(
	ID Nvarchar(36) Primary Key,
	PreMonthCount BigInt,
	CurMonthGetCount BigInt,
	InnerHosptal01 BigInt,
	InnerHosptal02 BigInt,
	InnerHosptal03 BigInt,
	InnerHosptalTotal BigInt,
	OuterHosptal01 BigInt,
	OuterHosptal02 BigInt,
	OuterHosptalTotal BigInt,
	DestroyCount01 BigInt,
	DestroyCount02 BigInt,
	DestroyCount03 BigInt,
	DestroyCountTotal BigInt,
	Totals BigInt,
	CurMonthCount BigInt,
	CurLifeChild BigInt,
	ReportYear Int,
	ReportMonth Int,
	OrgID Int,
	StaticDate DateTime,
)
GO
Alter Table BirthCertifiReportWithMonthHistory Add StaticPersonId Nvarchar(50)
GO
--localhost请修改为相应的地址 并建立birthReportData文件夹
Insert Into SystemInformation Values('http://localhost:8080/birthReportData','出生医学证明月统计报表',GETDATE(),'admin')
Insert Into SystemInformation Values('0','0表示不开启出生医学证明月统计报表定时执行任务，1表示开启',GETDATE(),'admin')
GO
Create Table BirthReportWithMonthFile(
	ID Nvarchar(36) Primary Key,
	FileName Nvarchar(100),
	StoreFileName Nvarchar(50),
	ReportYear Int,
	ReportMonth Int,
	OrgId Int,
	InputPersonId Nvarchar(50),
	InputDate Datetime,
)
GO

--历史数据的处理
Update BirthCertificate Set SaveType = 2 Where IsChanged != 1 Or IsSupply != 1
GO
Create Table OrganizationBySetReportFlag(
	ID Nvarchar(36) Primary Key,
	OrgId Int,
)
GO


 
--存储过程如果存在，则先删除
If Exists(Select * From sys.procedures Where name = 'BirthCertificateReportWithMonth') 
Begin
	Drop Proc BirthCertificateReportWithMonth
End
GO
 
 Create Proc BirthCertificateReportWithMonth(
	@ReportYear Int,
	@ReportMonth Int,
	@OrgId Int,
	@InputPersonId Nvarchar(50) = ''
 )As
 Begin
	SET NOCOUNT ON
	Declare @curYear Int,@curMonth Int,
		@BeginDate Nvarchar(10),@EndDate Nvarchar(10),
		@PreMonthCount BigInt,@CurMonthGetCount BigInt,
		@InnerHosptal01 BigInt,@InnerHosptal02 BigInt,
		@InnerHosptal03 BigInt,@InnerHosptalTotal BigInt,
		@OuterHosptal01 BigInt,@OuterHosptal02 BigInt,
		@OuterHosptalTotal BigInt,@DestroyCount01 BigInt,
		@DestroyCount02 BigInt,@DestroyCount03 BigInt,
		@DestroyCountTotal BigInt,@Totals BigInt,
		@CurMonthCount BigInt,@CurLifeChild BigInt
	--初始化变量
	Select @curYear = 1900,@curMonth = 0,@BeginDate = '',@EndDate = '',@PreMonthCount = 0,@CurMonthGetCount = 0,
		@InnerHosptal01 = 0,@InnerHosptal02 = 0,@InnerHosptal03 = 0,@InnerHosptalTotal = 0,@OuterHosptal01 = 0,
		@OuterHosptal02 = 0,@OuterHosptalTotal = 0,@DestroyCount01 = 0,@DestroyCount02 = 0,@DestroyCount03 = 0,
		@DestroyCountTotal = 0,@Totals = 0,@CurMonthCount = 0,@CurLifeChild = 0
	--检查这个月的时间是否已经生成月报表，如果生成，则不再重新生成
	--获得当前年份及月份
	Select @curYear = DATEPART(yyyy,GETDATE()),@curMonth = DATEPART(mm,GETDATE())
	If @ReportYear != @curYear And @ReportMonth != @curMonth
	Begin
		return;
	End
	--查询日期
	If @ReportMonth = 12 
	Begin
		Set @EndDate = Convert(Nvarchar(4),(@ReportYear + 1)) + '-1-1';
	End
	Else
	Begin
		Set @EndDate = Convert(Nvarchar(4),@ReportYear) + '-' + Convert(Nvarchar(2),(@ReportMonth + 1)) + '-' + '1';
	End
	Set @BeginDate = Convert(Nvarchar(4),@ReportYear) + '-' + Convert(Nvarchar(2),@ReportMonth) + '-' + '1';

	--获得上个月库存
	Select @PreMonthCount = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And A.IsEffectived = 1 And B.DistriDate < @BeginDate And B.OrgID = @OrgId
	--当月申领数
	Select @CurMonthGetCount = COUNT(*) From BirthCertificateOrg Where DistriDate >= @BeginDate And DistriDate < @EndDate And OrgID = @OrgId
	--首次签发数
	Select @InnerHosptal01 = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And B.OrgID = @OrgId And A.SaveType = 2 And A.InputDate >= @BeginDate And A.InputDate < @EndDate And A.IsEffectived In (2,4)
	--换发数
	Select @InnerHosptal02 = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And B.OrgID = @OrgId And A.IsChanged = 1 And A.InputDate >= @BeginDate And A.InputDate < @EndDate
	--补发数
	Select @InnerHosptal03 = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And B.OrgID = @OrgId And A.IsSupply = 1 And A.InputDate >= @BeginDate And A.InputDate < @EndDate
	--合计
	Set @InnerHosptalTotal = @InnerHosptal01 + @InnerHosptal02 + @InnerHosptal03
	--家庭接生员接生的签发数
	Select @OuterHosptal01 = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And B.OrgID = @OrgId And A.SaveType = 1 And A.BorthAddressCategory = '家庭接生员接生' And A.InputDate >= @BeginDate And A.InputDate < @EndDate
	--其它情况
	Select @OuterHosptal02 = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And B.OrgID = @OrgId And A.SaveType = 1 And A.BorthAddressCategory = '其它情况' And A.InputDate >= @BeginDate And A.InputDate < @EndDate
	--合计
	Set @OuterHosptalTotal = @OuterHosptal01 + @OuterHosptal02
	--因打印或填写错误	
	Select @DestroyCount01 = Count(*) From BirthCertificate A,BirthCertificateOrg B,BirthCertifiDestroyReason C Where A.CertifiID = B.CertificateID And A.CertifiID = C.CertifiId And B.OrgID = @OrgId And A.IsEffectived = 3 And C.ReasonDate >= @BeginDate And C.ReasonDate < @EndDate And C.ReasonRemarks = '因打印或填写错误'
	--遗失
	Select @DestroyCount02 = Count(*) From BirthCertificate A,BirthCertificateOrg B,BirthCertifiDestroyReason C Where A.CertifiID = B.CertificateID And A.CertifiID = C.CertifiId And B.OrgID = @OrgId And A.IsEffectived = 3 And C.ReasonDate >= @BeginDate And C.ReasonDate < @EndDate And C.ReasonRemarks = '遗失'
	--其它
	Select @DestroyCount03 = Count(*) From BirthCertificate A,BirthCertificateOrg B,BirthCertifiDestroyReason C Where A.CertifiID = B.CertificateID And A.CertifiID = C.CertifiId And B.OrgID = @OrgId And A.IsEffectived = 3 And C.ReasonDate >= @BeginDate And C.ReasonDate < @EndDate And C.ReasonRemarks = '其它'
	--合计
	Set @DestroyCountTotal = @DestroyCount01 + @DestroyCount02 + @DestroyCount03
	--合计
	Set @Totals = @InnerHosptalTotal + @OuterHosptalTotal + @DestroyCountTotal
	--当月月底库存数
	--Select @CurMonthCount = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And A.IsEffectived = 1 And B.DistriDate < @EndDate And B.OrgID = @OrgId
	Select @CurMonthCount = @PreMonthCount + @CurMonthGetCount - @Totals
	--当月医疗保健机构内活产数
	Set @CurLifeChild = @InnerHosptal01 
	--结果显示
	Delete From BirthCertifiReportWithMonthHistory Where ReportYear = @ReportYear And ReportMonth = @ReportMonth And OrgID = @OrgId
	Insert Into [BirthCertifiReportWithMonthHistory]
	Select  Lower(Replace(NEWID(),'-','')) As ID,@PreMonthCount As PreMonthCount,@CurMonthGetCount As CurMonthGetCount,
		@InnerHosptal01 As InnerHosptal01,@InnerHosptal02 As InnerHosptal02,@InnerHosptal03 As InnerHosptal03,
		@InnerHosptalTotal As InnerHosptalTotal,@OuterHosptal01 As OuterHosptal01,@OuterHosptal02 As OuterHosptal02,
		@OuterHosptalTotal As OuterHosptalTotal,@DestroyCount01 As DestroyCount01,@DestroyCount02 As DestroyCount02,
		@DestroyCount03 As DestroyCount03,@DestroyCountTotal As DestroyCountTotal,@Totals As Totals,@CurMonthCount As CurMonthCount,
		@CurLifeChild As CurLifeChild,@ReportYear As ReportYear,@ReportMonth As ReportMonth,@OrgId As OrgId,GETDATE() As StaticDate,
		@InputPersonId As StaticPersonId
	Select A.*,B.Name As OrgName,C.username As Username From BirthCertifiReportWithMonthHistory A Left Join Organization B ON A.OrgID = B.ID Left Join sam_taxempcode C ON A.StaticPersonId = C.loginname Where ReportYear = @ReportYear And ReportMonth = @ReportMonth And OrgID = @OrgId
	
	SET NOCOUNT OFF
 End
 GO
 