/**
* Author:Jackstraw
* Date:2012/09/20
**/
/**
* 第1次产前检查
**/
--现病史
Alter Table FirstVistBeforeBorn Add PresentIllnessHistory Nvarchar(Max)
GO
--初潮年龄
Alter Table FirstVistBeforeBorn Add MenarcheAge Int 
GO
--周期
Alter Table FirstVistBeforeBorn Add CycleOne Int
Alter Table FirstVistBeforeBorn Add CycleTwo Int
GO
--产次：胎头吸引、产钳、臀位
Alter Table FirstVistBeforeBorn Add VacuumExtraction Int
Alter Table FirstVistBeforeBorn Add Forceps Int
Alter Table FirstVistBeforeBorn Add Breech Int
GO
--未次分娩时间
Alter Table FirstVistBeforeBorn Add EndChildbirthDate Datetime
GO
--避孕史其它
Alter Table FirstVistBeforeBorn Add ContraceptiveHistoryOther Nvarchar(4000)
GO
--未次流产时间
Alter Table FirstVistBeforeBorn Add EndAbortionDate Datetime
GO
--新生儿死亡
Alter Table FirstVistBeforeBorn Add NewbornDeath Nvarchar(20)
Alter Table FirstVistBeforeBorn Add NewbornDeathDate Datetime
Alter Table FirstVistBeforeBorn Add NewbornDeathReason Nvarchar(4000)
GO
--出生缺陷儿
Alter Table FirstVistBeforeBorn Add BirthDefects Nvarchar(20)
Alter Table FirstVistBeforeBorn Add BirthDefectsName Nvarchar(1000)
GO
--早产
Alter Table FirstVistBeforeBorn Add PrematureBirth Int
GO
--存活男女
Alter Table FirstVistBeforeBorn Add SurvivalMale Int
Alter Table FirstVistBeforeBorn Add SurvivalFemale Int
GO
--既往妊娠合并症及并发症
Alter Table FirstVistBeforeBorn Add ComplicationHistory Nvarchar(1000)
GO
--心脏心率
Alter Table FirstVistBeforeBorn Add HeartRate Int
GO
--肺脏呼吸
Alter Table FirstVistBeforeBorn Add BreathingRate Int
GO
--肝脏
Alter Table FirstVistBeforeBorn Add Liver Nvarchar(100)
Alter Table FirstVistBeforeBorn Add LiverOther Nvarchar(1000)
GO
--脾脏
Alter Table FirstVistBeforeBorn Add Spleen Nvarchar(100)
Alter Table FirstVistBeforeBorn Add SpleenOther Nvarchar(1000)
GO
--乳房
Alter Table FirstVistBeforeBorn Add Breast Nvarchar(100)
Alter Table FirstVistBeforeBorn Add BreastOther Nvarchar(1000)
GO
--其它检查
Alter Table FirstVistBeforeBorn Add OtherExam Nvarchar(100)
Alter Table FirstVistBeforeBorn Add OtherExamOther Nvarchar(1000)
GO
--HIV检测、梅毒检测、乙肝检测时间
Alter Table FirstVistBeforeBorn Add HIVDetectDate Datetime
Alter Table FirstVistBeforeBorn Add SyphilisDetectDate Datetime
Alter Table FirstVistBeforeBorn Add HepatitisBDetectDate Datetime
GO
--总体评估诊断
Alter Table FirstVistBeforeBorn Add DiagnosisRemark Nvarchar(1000)
GO
/**
* 避孕史表 2004
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
* 第2~5次产前检查
**/
--浮肿
Alter Table VisitBeforeBorn Add Edema Nvarchar(1000)
GO
--诊断
Alter Table VisitBeforeBorn Add DiagnosisRemark Nvarchar(1000)
GO
--计划分娩地点 
Alter Table VisitBeforeBorn Add BornBirthAddressPlan Nvarchar(100)
GO
--骨盆外测量
Alter Table VisitBeforeBorn Add Pelvis01 Int --髂棘
Alter Table VisitBeforeBorn Add Pelvis02 Int --髂嵴
Alter Table VisitBeforeBorn Add Pelvis03 Int --骶耻
Alter Table VisitBeforeBorn Add Pelvis04 Int --出口
GO

/**
* 孕期特殊情况记录表
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
	[ID] [char](36) NOT NULL,--主键
	[HealthFileMaternalID] [nvarchar](36) NOT NULL,--保健手册孕产妇档案ID
	[RecordDate] [Datetime] NULL,--日期
	[Record] [Nvarchar](1000) NULL,--特殊情况
	[DealOpinion] [Nvarchar](1000) NULL,--处理意见
	[Doctor] [Nvarchar](20) NULL,--医生签名
	[InputPersonID] [nvarchar](20) NULL,--录入人
	[InputDate] [datetime] NULL,--录入时间
 CONSTRAINT [pk_PregnancyRecord] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/**
* 分娩记录
**/
--总产程
Alter Table ChildBirthRecord Add TotalLaborHours Int
Alter Table ChildBirthRecord Add OneLaborHours Int
Alter Table ChildBirthRecord Add TwoLaborHours Int
Alter Table ChildBirthRecord Add TwoLaborMinutes Int
Alter Table ChildBirthRecord Add ThreeLaborMinutes Int
GO
--胎盘娩出时间及娩出方式 2005
Alter Table ChildBirthRecord Add PlacentaParturitionDate Datetime
Alter Table ChildBirthRecord Add PlacentaParturitionWay Nvarchar(20)
GO
--是否完整 2007 2008
Alter Table ChildBirthRecord Add IsComplete Nvarchar(20)
Alter Table ChildBirthRecord Add isNotComplete Nvarchar(20)
GO
--宫颈裂伤
Alter Table ChildBirthRecord Add CervicalLaceration Nvarchar(20)
Alter Table ChildBirthRecord Add CervicalLacerationPosition01 Int
Alter Table ChildBirthRecord Add CervicalLacerationPosition02 Int
GO
--产后2小时及产后24小时出血量
Alter Table ChildBirthRecord Add Bleeding01 Decimal(10,2)
Alter Table ChildBirthRecord Add Bleeding02 Decimal(10,2)
GO
--产时产后并发症
Alter Table ChildBirthRecord Add Complication Nvarchar(30)
Alter Table ChildBirthRecord Add ComplicationName Nvarchar(100)
GO
--Apgar评分
Alter Table ChildBirthRecord Add Apgar01 Nvarchar(30)
Alter Table ChildBirthRecord Add Apgar02 Nvarchar(30)
GO
--窒息 2009
Alter Table ChildBirthRecord Add Suffocation Nvarchar(30)
Alter Table ChildBirthRecord Add SuffocationOther Nvarchar(100)
Alter Table ChildBirthRecord Add SuffocationOther01 Nvarchar(100)
GO
--产伤
Alter Table ChildBirthRecord Add BirthTrauma Nvarchar(30)
Alter Table ChildBirthRecord Add BirthTraumaOther Nvarchar(100)
GO
--出生缺陷
Alter Table ChildBirthRecord Add BirthDefects Nvarchar(30)
Alter Table ChildBirthRecord Add BirthDefectsOther Nvarchar(100)
GO
--出院诊断
Alter Table ChildBirthRecord Add DischargeDiagnosis01 Nvarchar(1000)
Alter Table ChildBirthRecord Add DischargeDiagnosis02 Nvarchar(1000)
GO
--新生儿疾病筛查 2006
Alter Table ChildBirthRecord Add DiseaseScreeningOther Nvarchar(1000)
GO
/**
* 新生儿疾病筛查
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
* 产后42天
**/
--产后天数
Alter Table VisitAfterBorn Add PostnatalDays Int
GO
--体重
Alter Table VisitAfterBorn Add [Weight] Decimal(10,2)
GO
--宫颈
Alter Table VisitAfterBorn Add Cervix Nvarchar(100)
Alter Table VisitAfterBorn Add CervixOther Nvarchar(100)
GO
--附件
Alter Table VisitAfterBorn Add Attachment Nvarchar(100)
Alter Table VisitAfterBorn Add AttachmentOther Nvarchar(100)
GO
--外阴\阴道
Alter Table VisitAfterBorn Add Vulva Nvarchar(100)
Alter Table VisitAfterBorn Add VulvaOther Nvarchar(100)
GO
--
--Alter Table VisitAfterBorn Add Vagina Nvarchar(100)
--Alter Table VisitAfterBorn Add VaginaOther Nvarchar(100)
--GO
/**
* 产后访视
**/
--脉搏
Alter Table VisitAfterBorn Add PulseRate Int
GO
--乳汁、红肿、乳头 2001 151
Alter Table VisitAfterBorn Add Milk Nvarchar(30)
Alter Table VisitAfterBorn Add Swelling Nvarchar(30)
Alter Table VisitAfterBorn Add Nipple Nvarchar(30)
GO
--宫底高度
Alter Table VisitAfterBorn Add PalaceHeight Decimal(10,2)
GO
--伤口愈合 2002
Alter Table VisitAfterBorn Add WoundHealing Nvarchar(100)
GO
--恶露
Alter Table VisitAfterBorn Add Lochia01 Nvarchar(100)
Alter Table VisitAfterBorn Add Lochia02 Nvarchar(100)
GO


/**
* 孕产妇档案封面
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
	[ID] [nvarchar](36) NOT NULL,--主键
	[FileNo] [nvarchar](30) NOT NULL,--档案编号
	[Name] [nvarchar](30) NOT NULL,--姓名
	[Birthday] [datetime] NULL,--出生日期
	[TEL] [nvarchar](20) NULL,--联系电话
	[FirstAidTel] [nvarchar](20) NULL,--急救电话
	[AddressProvence] [nvarchar](20) NULL,--现住址省
	[AddressCity] [nvarchar](50) NULL,--现住址州（市）
	[AddressCounty] [nvarchar](50) NULL,--现住址县（市、区）
	[AddressTownship] [nvarchar](50) NULL,--现住址乡
	[AddressVillage] [nvarchar](50) NULL,--现住址村
	[AddressGroup] [nvarchar](50) NULL,--现住址自然村
	[ResidenceProvence] [nvarchar](20) NULL,--户籍地址省
	[ResidenceCity] [nvarchar](50) NULL,--户籍地址州（市）
	[ResidenceCounty] [nvarchar](50) NULL,--户籍地址县（市、区）
	[ResidenceTownship] [nvarchar](50) NULL,---户籍地址乡
	[ResidenceVillage] [nvarchar](50) NULL,--户籍地址村
	[ResidenceGroup] [nvarchar](50) NULL,--户籍地址自然村
	[HighRiskCode] [nvarchar](10) NULL,--高危代码
	[BuildUnit] [nvarchar](50) NULL,--建册单位
	[BuildDate] [datetime] NULL,--建册时间
	[Nationality] [nvarchar](50) NULL,--国籍
	[NationalityOther] [nvarchar](50) NULL,--其它国籍
	[IDNumber] [nvarchar](20) NOT NULL,--身份证号
	[WorkUnit] [nvarchar](50) NULL,--工作单位
	[ResideType] [nvarchar](10) NULL,--户籍类型
	[AreaOfResidence] [nvarchar](10) NULL,--居住地址
	[Distance] Decimal(10,2) NULL,--距离
	[Folk] [nvarchar](10) NULL,--民族
	[FolkOther] [nvarchar](30) NULL,--其它民族
	[Education] [nvarchar](30) NULL,--受教育程序
	[Occupation] [nvarchar](50) NULL,--职业
	[RecuperateProvence] [nvarchar](20) NULL,--休养地省
	[RecuperateCity] [nvarchar](50) NULL,--休养地州（市）
	[RecuperateCounty] [nvarchar](50) NULL,--休养地县（市、区）
	[RecuperateTownship] [nvarchar](50) NULL,--休养地乡
	[RecuperateVillage] [nvarchar](50) NULL,--休养地村
	[RecuperateGroup] [nvarchar](50) NULL,--休养地自然村
	[RelatedInfoSearch] [nvarchar](36) NULL,--相关档案编号
	[HusbandName] [nvarchar](20) NULL,--丈夫姓名
	[HusbandBirthday] [datetime] NULL,--丈夫出生日期
	[HusbandTEL] [nvarchar](20) NULL,--丈夫联系电话
	[HusbandEducation] [nvarchar](30) NULL,--丈夫文化程度
	[HusbandOccupation] [nvarchar](50) NULL,--丈夫职业
	[HusbandOccupationOther] [nvarchar](50) NULL,--丈夫职业其它说明
	[HusbandWorkUnit] [nvarchar](50) NULL,--丈夫工作单位
	[InputPersonID] [varchar](30) NULL,--录入人
	[InputDate] [datetime] NULL,--录入时间
	[Gravidity] [int] NULL,--孕次
	[IsClosed] [Nvarchar](10) NULL,--结案
	[ClosedDate] Datetime NULL,--结案时间
 CONSTRAINT [pk_HealthFileMaternal] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO
/**
* 终止妊娠
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
	[ID] [nvarchar](36) NOT NULL,--主键
	[HealthFileMaternalID] [nvarchar](36) NOT NULL,--保健手册孕产妇档案ID
	[FinishReason] [nvarchar](30) NOT NULL,--终止妊娠原因
	[FinishDate] [datetime] NULL,--终止妊娠时间
	[InputPersonID] [nvarchar](20) NULL,--录入人
	[InputDate] [datetime] NULL,--录入时间
 CONSTRAINT [pk_FinishGestation] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

Alter Table dbo.HealthFileMaternal Add RelatedInfoSearch Nvarchar(36)