/**
* Author:Jackstraw
* Date:2012/09/20
**/
/**
* ��1�β�ǰ���
**/
--�ֲ�ʷ
Alter Table FirstVistBeforeBorn Add PresentIllnessHistory Nvarchar(Max)
GO
--��������
Alter Table FirstVistBeforeBorn Add MenarcheAge Int 
GO
--����
Alter Table FirstVistBeforeBorn Add CycleOne Int
Alter Table FirstVistBeforeBorn Add CycleTwo Int
GO
--���Σ�̥ͷ��������ǯ����λ
Alter Table FirstVistBeforeBorn Add VacuumExtraction Int
Alter Table FirstVistBeforeBorn Add Forceps Int
Alter Table FirstVistBeforeBorn Add Breech Int
GO
--δ�η���ʱ��
Alter Table FirstVistBeforeBorn Add EndChildbirthDate Datetime
GO
--����ʷ����
Alter Table FirstVistBeforeBorn Add ContraceptiveHistoryOther Nvarchar(4000)
GO
--δ������ʱ��
Alter Table FirstVistBeforeBorn Add EndAbortionDate Datetime
GO
--����������
Alter Table FirstVistBeforeBorn Add NewbornDeath Nvarchar(20)
Alter Table FirstVistBeforeBorn Add NewbornDeathDate Datetime
Alter Table FirstVistBeforeBorn Add NewbornDeathReason Nvarchar(4000)
GO
--����ȱ�ݶ�
Alter Table FirstVistBeforeBorn Add BirthDefects Nvarchar(20)
Alter Table FirstVistBeforeBorn Add BirthDefectsName Nvarchar(1000)
GO
--���
Alter Table FirstVistBeforeBorn Add PrematureBirth Int
GO
--�����Ů
Alter Table FirstVistBeforeBorn Add SurvivalMale Int
Alter Table FirstVistBeforeBorn Add SurvivalFemale Int
GO
--��������ϲ�֢������֢
Alter Table FirstVistBeforeBorn Add ComplicationHistory Nvarchar(1000)
GO
--��������
Alter Table FirstVistBeforeBorn Add HeartRate Int
GO
--�������
Alter Table FirstVistBeforeBorn Add BreathingRate Int
GO
--����
Alter Table FirstVistBeforeBorn Add Liver Nvarchar(100)
Alter Table FirstVistBeforeBorn Add LiverOther Nvarchar(1000)
GO
--Ƣ��
Alter Table FirstVistBeforeBorn Add Spleen Nvarchar(100)
Alter Table FirstVistBeforeBorn Add SpleenOther Nvarchar(1000)
GO
--�鷿
Alter Table FirstVistBeforeBorn Add Breast Nvarchar(100)
Alter Table FirstVistBeforeBorn Add BreastOther Nvarchar(1000)
GO
--�������
Alter Table FirstVistBeforeBorn Add OtherExam Nvarchar(100)
Alter Table FirstVistBeforeBorn Add OtherExamOther Nvarchar(1000)
GO
--HIV��⡢÷����⡢�Ҹμ��ʱ��
Alter Table FirstVistBeforeBorn Add HIVDetectDate Datetime
Alter Table FirstVistBeforeBorn Add SyphilisDetectDate Datetime
Alter Table FirstVistBeforeBorn Add HepatitisBDetectDate Datetime
GO
--�����������
Alter Table FirstVistBeforeBorn Add DiagnosisRemark Nvarchar(1000)
GO
/**
* ����ʷ�� 2004
**/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ContraceptiveHistory]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
	drop table [dbo].[ContraceptiveHistory]
GO
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ContraceptiveHistory](
	[FirstVistBeforeBornID] [char](36) NOT NULL,
	[ID] [char](36) NOT NULL,
	[ContraceptiveID] [int] NOT NULL,
 CONSTRAINT [pk_ContraceptiveHistory] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/**
* ��2~5�β�ǰ���
**/
--����
Alter Table VisitBeforeBorn Add Edema Nvarchar(1000)
GO
--���
Alter Table VisitBeforeBorn Add DiagnosisRemark Nvarchar(1000)
GO
--�ƻ�����ص� 
Alter Table VisitBeforeBorn Add BornBirthAddressPlan Nvarchar(100)
GO
--���������
Alter Table VisitBeforeBorn Add Pelvis01 Int --�ļ�
Alter Table VisitBeforeBorn Add Pelvis02 Int --����
Alter Table VisitBeforeBorn Add Pelvis03 Int --����
Alter Table VisitBeforeBorn Add Pelvis04 Int --����
GO

/**
* �������������¼��
**/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PregnancyRecord]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
	drop table [dbo].[PregnancyRecord]
GO
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[PregnancyRecord](
	[ID] [char](36) NOT NULL,--����
	[HealthFileMaternalID] [nvarchar](36) NOT NULL,--�����ֲ��в�������ID
	[RecordDate] [Datetime] NULL,--����
	[Record] [Nvarchar](1000) NULL,--�������
	[DealOpinion] [Nvarchar](1000) NULL,--�������
	[Doctor] [Nvarchar](20) NULL,--ҽ��ǩ��
	[InputPersonID] [nvarchar](20) NULL,--¼����
	[InputDate] [datetime] NULL,--¼��ʱ��
 CONSTRAINT [pk_PregnancyRecord] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/**
* �����¼
**/
--�ܲ���
Alter Table ChildBirthRecord Add TotalLaborHours Int
Alter Table ChildBirthRecord Add OneLaborHours Int
Alter Table ChildBirthRecord Add TwoLaborHours Int
Alter Table ChildBirthRecord Add TwoLaborMinutes Int
Alter Table ChildBirthRecord Add ThreeLaborMinutes Int
GO
--̥�����ʱ�估�����ʽ 2005
Alter Table ChildBirthRecord Add PlacentaParturitionDate Datetime
Alter Table ChildBirthRecord Add PlacentaParturitionWay Nvarchar(20)
GO
--�Ƿ����� 2007 2008
Alter Table ChildBirthRecord Add IsComplete Nvarchar(20)
Alter Table ChildBirthRecord Add isNotComplete Nvarchar(20)
GO
--��������
Alter Table ChildBirthRecord Add CervicalLaceration Nvarchar(20)
Alter Table ChildBirthRecord Add CervicalLacerationPosition01 Int
Alter Table ChildBirthRecord Add CervicalLacerationPosition02 Int
GO
--����2Сʱ������24Сʱ��Ѫ��
Alter Table ChildBirthRecord Add Bleeding01 Decimal(10,2)
Alter Table ChildBirthRecord Add Bleeding02 Decimal(10,2)
GO
--��ʱ���󲢷�֢
Alter Table ChildBirthRecord Add Complication Nvarchar(30)
Alter Table ChildBirthRecord Add ComplicationName Nvarchar(100)
GO
--Apgar����
Alter Table ChildBirthRecord Add Apgar01 Nvarchar(30)
Alter Table ChildBirthRecord Add Apgar02 Nvarchar(30)
GO
--��Ϣ 2009
Alter Table ChildBirthRecord Add Suffocation Nvarchar(30)
Alter Table ChildBirthRecord Add SuffocationOther Nvarchar(100)
Alter Table ChildBirthRecord Add SuffocationOther01 Nvarchar(100)
GO
--����
Alter Table ChildBirthRecord Add BirthTrauma Nvarchar(30)
Alter Table ChildBirthRecord Add BirthTraumaOther Nvarchar(100)
GO
--����ȱ��
Alter Table ChildBirthRecord Add BirthDefects Nvarchar(30)
Alter Table ChildBirthRecord Add BirthDefectsOther Nvarchar(100)
GO
--��Ժ���
Alter Table ChildBirthRecord Add DischargeDiagnosis01 Nvarchar(1000)
Alter Table ChildBirthRecord Add DischargeDiagnosis02 Nvarchar(1000)
GO
--����������ɸ�� 2006
Alter Table ChildBirthRecord Add DiseaseScreeningOther Nvarchar(1000)
GO
/**
* ����������ɸ��
**/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[DiseaseScreening]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
	drop table [dbo].[DiseaseScreening]
GO
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[DiseaseScreening](
	[ChildBirthRecordID] [char](36) NOT NULL,
	[ID] [char](36) NOT NULL,
	[DiseaseScreeningID] [int] NOT NULL,
 CONSTRAINT [pk_DiseaseScreening] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/**
* ����42��
**/
--��������
Alter Table VisitAfterBorn Add PostnatalDays Int
GO
--����
Alter Table VisitAfterBorn Add [Weight] Decimal(10,2)
GO
--����
Alter Table VisitAfterBorn Add Cervix Nvarchar(100)
Alter Table VisitAfterBorn Add CervixOther Nvarchar(100)
GO
--����
Alter Table VisitAfterBorn Add Attachment Nvarchar(100)
Alter Table VisitAfterBorn Add AttachmentOther Nvarchar(100)
GO
--����\����
Alter Table VisitAfterBorn Add Vulva Nvarchar(100)
Alter Table VisitAfterBorn Add VulvaOther Nvarchar(100)
GO
--
--Alter Table VisitAfterBorn Add Vagina Nvarchar(100)
--Alter Table VisitAfterBorn Add VaginaOther Nvarchar(100)
--GO
/**
* �������
**/
--����
Alter Table VisitAfterBorn Add PulseRate Int
GO
--��֭�����ס���ͷ 2001 151
Alter Table VisitAfterBorn Add Milk Nvarchar(30)
Alter Table VisitAfterBorn Add Swelling Nvarchar(30)
Alter Table VisitAfterBorn Add Nipple Nvarchar(30)
GO
--���׸߶�
Alter Table VisitAfterBorn Add PalaceHeight Decimal(10,2)
GO
--�˿����� 2002
Alter Table VisitAfterBorn Add WoundHealing Nvarchar(100)
GO
--��¶
Alter Table VisitAfterBorn Add Lochia01 Nvarchar(100)
Alter Table VisitAfterBorn Add Lochia02 Nvarchar(100)
GO


/**
* �в�����������
**/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[HealthFileMaternal]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
	drop table [dbo].[HealthFileMaternal]
GO
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[HealthFileMaternal](	
	[ID] [nvarchar](36) NOT NULL,--����
	[FileNo] [nvarchar](30) NOT NULL,--�������
	[Name] [nvarchar](30) NOT NULL,--����
	[Birthday] [datetime] NULL,--��������
	[TEL] [nvarchar](20) NULL,--��ϵ�绰
	[FirstAidTel] [nvarchar](20) NULL,--���ȵ绰
	[AddressProvence] [nvarchar](20) NULL,--��סַʡ
	[AddressCity] [nvarchar](50) NULL,--��סַ�ݣ��У�
	[AddressCounty] [nvarchar](50) NULL,--��סַ�أ��С�����
	[AddressTownship] [nvarchar](50) NULL,--��סַ��
	[AddressVillage] [nvarchar](50) NULL,--��סַ��
	[AddressGroup] [nvarchar](50) NULL,--��סַ��Ȼ��
	[ResidenceProvence] [nvarchar](20) NULL,--������ַʡ
	[ResidenceCity] [nvarchar](50) NULL,--������ַ�ݣ��У�
	[ResidenceCounty] [nvarchar](50) NULL,--������ַ�أ��С�����
	[ResidenceTownship] [nvarchar](50) NULL,---������ַ��
	[ResidenceVillage] [nvarchar](50) NULL,--������ַ��
	[ResidenceGroup] [nvarchar](50) NULL,--������ַ��Ȼ��
	[HighRiskCode] [nvarchar](10) NULL,--��Σ����
	[BuildUnit] [nvarchar](50) NULL,--���ᵥλ
	[BuildDate] [datetime] NULL,--����ʱ��
	[Nationality] [nvarchar](50) NULL,--����
	[NationalityOther] [nvarchar](50) NULL,--��������
	[IDNumber] [nvarchar](20) NOT NULL,--���֤��
	[WorkUnit] [nvarchar](50) NULL,--������λ
	[ResideType] [nvarchar](10) NULL,--��������
	[AreaOfResidence] [nvarchar](10) NULL,--��ס��ַ
	[Distance] Decimal(10,2) NULL,--����
	[Folk] [nvarchar](10) NULL,--����
	[FolkOther] [nvarchar](30) NULL,--��������
	[Education] [nvarchar](30) NULL,--�ܽ�������
	[Occupation] [nvarchar](50) NULL,--ְҵ
	[RecuperateProvence] [nvarchar](20) NULL,--������ʡ
	[RecuperateCity] [nvarchar](50) NULL,--�������ݣ��У�
	[RecuperateCounty] [nvarchar](50) NULL,--�������أ��С�����
	[RecuperateTownship] [nvarchar](50) NULL,--��������
	[RecuperateVillage] [nvarchar](50) NULL,--�����ش�
	[RecuperateGroup] [nvarchar](50) NULL,--��������Ȼ��
	[RelatedInfoSearch] [nvarchar](36) NULL,--��ص������
	[HusbandName] [nvarchar](20) NULL,--�ɷ�����
	[HusbandBirthday] [datetime] NULL,--�ɷ��������
	[HusbandTEL] [nvarchar](20) NULL,--�ɷ���ϵ�绰
	[HusbandEducation] [nvarchar](30) NULL,--�ɷ��Ļ��̶�
	[HusbandOccupation] [nvarchar](50) NULL,--�ɷ�ְҵ
	[HusbandOccupationOther] [nvarchar](50) NULL,--�ɷ�ְҵ����˵��
	[HusbandWorkUnit] [nvarchar](50) NULL,--�ɷ�����λ
	[InputPersonID] [varchar](30) NULL,--¼����
	[InputDate] [datetime] NULL,--¼��ʱ��
	[Gravidity] [int] NULL,--�д�
	[IsClosed] [Nvarchar](10) NULL,--�᰸
	[ClosedDate] Datetime NULL,--�᰸ʱ��
 CONSTRAINT [pk_HealthFileMaternal] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO
/**
* ��ֹ����
**/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FinishGestation]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
	drop table [dbo].[FinishGestation]
GO
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[FinishGestation](	
	[ID] [nvarchar](36) NOT NULL,--����
	[HealthFileMaternalID] [nvarchar](36) NOT NULL,--�����ֲ��в�������ID
	[FinishReason] [nvarchar](30) NOT NULL,--��ֹ����ԭ��
	[FinishDate] [datetime] NULL,--��ֹ����ʱ��
	[InputPersonID] [nvarchar](20) NULL,--¼����
	[InputDate] [datetime] NULL,--¼��ʱ��
 CONSTRAINT [pk_FinishGestation] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

Alter Table dbo.HealthFileMaternal Add RelatedInfoSearch Nvarchar(36)