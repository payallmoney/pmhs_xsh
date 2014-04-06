 --�������� 0Ϊ���� 1Ϊҽ�ƻ������״�ǩ�� 2Ϊҽ�ƻ������״�ǩ��

 Alter Table BirthCertificate Add SaveType Nvarchar(10)
 GO

 --ҽ�Ʊ���������������

 Insert Into BasicInformation Values(2021,0,'ҽ�Ʊ������������','',2021,0,1,1,0,0)
 Insert Into BasicInformation Values(3075,1,'��ͥ����Ա����','ҽ�Ʊ������������',2021,0,0,1,0,0)
 Insert Into BasicInformation Values(3076,2,'�������','ҽ�Ʊ������������',2021,0,0,1,0,0)
 GO

 /*
 Select * From BasicInformation Where type = 2021
 
 Update BasicInformation Set Name = 'ҽ�Ʊ������������'  Where ID = 2021
 Update BasicInformation Set Name = '��ͥ����Ա����',Name_Png = 'ҽ�Ʊ������������' Where ID = 3075
 Update BasicInformation Set Name = '�������',Name_Png = 'ҽ�Ʊ������������' Where ID = 3076
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
--localhost���޸�Ϊ��Ӧ�ĵ�ַ ������birthReportData�ļ���
Insert Into SystemInformation Values('http://localhost:8080/birthReportData','����ҽѧ֤����ͳ�Ʊ���',GETDATE(),'admin')
Insert Into SystemInformation Values('0','0��ʾ����������ҽѧ֤����ͳ�Ʊ���ʱִ������1��ʾ����',GETDATE(),'admin')
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

--��ʷ���ݵĴ���
Update BirthCertificate Set SaveType = 2 Where IsChanged != 1 Or IsSupply != 1
GO
Create Table OrganizationBySetReportFlag(
	ID Nvarchar(36) Primary Key,
	OrgId Int,
)
GO


 
--�洢����������ڣ�����ɾ��
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
	--��ʼ������
	Select @curYear = 1900,@curMonth = 0,@BeginDate = '',@EndDate = '',@PreMonthCount = 0,@CurMonthGetCount = 0,
		@InnerHosptal01 = 0,@InnerHosptal02 = 0,@InnerHosptal03 = 0,@InnerHosptalTotal = 0,@OuterHosptal01 = 0,
		@OuterHosptal02 = 0,@OuterHosptalTotal = 0,@DestroyCount01 = 0,@DestroyCount02 = 0,@DestroyCount03 = 0,
		@DestroyCountTotal = 0,@Totals = 0,@CurMonthCount = 0,@CurLifeChild = 0
	--�������µ�ʱ���Ƿ��Ѿ������±���������ɣ�������������
	--��õ�ǰ��ݼ��·�
	Select @curYear = DATEPART(yyyy,GETDATE()),@curMonth = DATEPART(mm,GETDATE())
	If @ReportYear != @curYear And @ReportMonth != @curMonth
	Begin
		return;
	End
	--��ѯ����
	If @ReportMonth = 12 
	Begin
		Set @EndDate = Convert(Nvarchar(4),(@ReportYear + 1)) + '-1-1';
	End
	Else
	Begin
		Set @EndDate = Convert(Nvarchar(4),@ReportYear) + '-' + Convert(Nvarchar(2),(@ReportMonth + 1)) + '-' + '1';
	End
	Set @BeginDate = Convert(Nvarchar(4),@ReportYear) + '-' + Convert(Nvarchar(2),@ReportMonth) + '-' + '1';

	--����ϸ��¿��
	Select @PreMonthCount = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And A.IsEffectived = 1 And B.DistriDate < @BeginDate And B.OrgID = @OrgId
	--����������
	Select @CurMonthGetCount = COUNT(*) From BirthCertificateOrg Where DistriDate >= @BeginDate And DistriDate < @EndDate And OrgID = @OrgId
	--�״�ǩ����
	Select @InnerHosptal01 = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And B.OrgID = @OrgId And A.SaveType = 2 And A.InputDate >= @BeginDate And A.InputDate < @EndDate And A.IsEffectived In (2,4)
	--������
	Select @InnerHosptal02 = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And B.OrgID = @OrgId And A.IsChanged = 1 And A.InputDate >= @BeginDate And A.InputDate < @EndDate
	--������
	Select @InnerHosptal03 = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And B.OrgID = @OrgId And A.IsSupply = 1 And A.InputDate >= @BeginDate And A.InputDate < @EndDate
	--�ϼ�
	Set @InnerHosptalTotal = @InnerHosptal01 + @InnerHosptal02 + @InnerHosptal03
	--��ͥ����Ա������ǩ����
	Select @OuterHosptal01 = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And B.OrgID = @OrgId And A.SaveType = 1 And A.BorthAddressCategory = '��ͥ����Ա����' And A.InputDate >= @BeginDate And A.InputDate < @EndDate
	--�������
	Select @OuterHosptal02 = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And B.OrgID = @OrgId And A.SaveType = 1 And A.BorthAddressCategory = '�������' And A.InputDate >= @BeginDate And A.InputDate < @EndDate
	--�ϼ�
	Set @OuterHosptalTotal = @OuterHosptal01 + @OuterHosptal02
	--���ӡ����д����	
	Select @DestroyCount01 = Count(*) From BirthCertificate A,BirthCertificateOrg B,BirthCertifiDestroyReason C Where A.CertifiID = B.CertificateID And A.CertifiID = C.CertifiId And B.OrgID = @OrgId And A.IsEffectived = 3 And C.ReasonDate >= @BeginDate And C.ReasonDate < @EndDate And C.ReasonRemarks = '���ӡ����д����'
	--��ʧ
	Select @DestroyCount02 = Count(*) From BirthCertificate A,BirthCertificateOrg B,BirthCertifiDestroyReason C Where A.CertifiID = B.CertificateID And A.CertifiID = C.CertifiId And B.OrgID = @OrgId And A.IsEffectived = 3 And C.ReasonDate >= @BeginDate And C.ReasonDate < @EndDate And C.ReasonRemarks = '��ʧ'
	--����
	Select @DestroyCount03 = Count(*) From BirthCertificate A,BirthCertificateOrg B,BirthCertifiDestroyReason C Where A.CertifiID = B.CertificateID And A.CertifiID = C.CertifiId And B.OrgID = @OrgId And A.IsEffectived = 3 And C.ReasonDate >= @BeginDate And C.ReasonDate < @EndDate And C.ReasonRemarks = '����'
	--�ϼ�
	Set @DestroyCountTotal = @DestroyCount01 + @DestroyCount02 + @DestroyCount03
	--�ϼ�
	Set @Totals = @InnerHosptalTotal + @OuterHosptalTotal + @DestroyCountTotal
	--�����µ׿����
	--Select @CurMonthCount = Count(*) From BirthCertificate A,BirthCertificateOrg B Where A.CertifiID = B.CertificateID And A.IsEffectived = 1 And B.DistriDate < @EndDate And B.OrgID = @OrgId
	Select @CurMonthCount = @PreMonthCount + @CurMonthGetCount - @Totals
	--����ҽ�Ʊ��������ڻ����
	Set @CurLifeChild = @InnerHosptal01 
	--�����ʾ
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
 