/**
* Author:Jackstraw
* Date:2012/11/18
* Remarks: HealthFile表中字段IsOverCount定义为儿童保健手册是否已经建立标记，标记值为1
**/
/**
* 儿童档案封面
**/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[HealthFileChildren]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
	drop table [dbo].[HealthFileChildren]
GO
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[HealthFileChildren](	
	[ID] [nvarchar](36) NOT NULL,--主键
	[FileNo] [nvarchar](30) NOT NULL,--档案编号
	[Name] [nvarchar](30) NOT NULL,--姓名
	[Sex] [nvarchar](20) NOT NULL,--性别
	[AllergiesHistory] [nvarchar](100) ,--过敏史
	[FatherName] [nvarchar](30),--父亲姓名
	[MotherName] [nvarchar](30) ,--母亲姓名
	[BuildDate] [datetime] NULL,--建册时间
	[BuildUnit] [nvarchar](50) NULL,--建册单位
	[ResidenceProvence] [nvarchar](20) NULL,--户籍地址省
	[ResidenceCity] [nvarchar](50) NULL,--户籍地址州（市）
	[ResidenceCounty] [nvarchar](50) NULL,--户籍地址县（市、区）
	[ResidenceTownship] [nvarchar](50) NULL,---户籍地址乡
	[ResidenceVillage] [nvarchar](50) NULL,--户籍地址村
	[ResidenceGroup] [nvarchar](50) NULL,--户籍地址自然村
	[AddressProvence] [nvarchar](20) NULL,--现住址省
	[AddressCity] [nvarchar](50) NULL,--现住址州（市）
	[AddressCounty] [nvarchar](50) NULL,--现住址县（市、区）
	[AddressTownship] [nvarchar](50) NULL,--现住址乡
	[AddressVillage] [nvarchar](50) NULL,--现住址村
	[AddressGroup] [nvarchar](50) NULL,--现住址自然村
	[TEL] [nvarchar](20) NULL,--辖区妇幼保健院联系电话
	[Birthday] [datetime] NULL,--出生日期
	[Weight] Decimal(10,2) NULL,--出生体重
	[Height] Decimal(10,2) NULL,--出生身长
	[ApgarOneMinuts] [nvarchar](50) NULL,--Apgar评分
	[ApgarFiveMinuts] [nvarchar](50) NULL,--Apgar评分
	[abo] [nvarchar](50) NULL,--ABO血型
	[rh] [nvarchar](20) NOT NULL,--RH血型
	[Weekly] [nvarchar](50) NULL,--孕周
	[Gravidity] [int] NULL,--孕次
	[Parity] [int] NULL,--产次
	[NumberOfBirths] [nvarchar](10) NULL,--胎数
	[BormWays] [nvarchar](100) NULL,--分娩方式
	[BirthOrgName] [nvarchar](30) NULL,--助产机构名称
	[FatherAge] [Int] NULL,--父亲生育年龄
	[FatherNation] [nvarchar](50) NULL,--父亲民族
	[FatherEducational] [nvarchar](50) NULL,--父亲文化程度
	[FatherOccupation] [nvarchar](50) NULL,--父亲职业
	[MotherAge] [Int] NULL,--母亲生育年龄
	[MotherNation] [nvarchar](50) NULL,--母亲民族
	[MotherEducational] [nvarchar](50) NULL,--母亲文化程度
	[MotherOccupation] [nvarchar](50) NULL,--母亲职业
	[MontherExceptions] [nvarchar](20) NULL,--母亲孕期异常情况
	[MontherExceptionOhter] [nvarchar](1000) NULL,--母亲孕期异常情况其它
	[ChildrenExceptions] [nvarchar](20) NULL,--小儿围产期异常情况
	[ChildrenException1] [nvarchar](50) NULL,--轻重
	[ChildrenExceptionOhter] [nvarchar](1000) NULL,--小儿围产期异常情况其它
	[BirthDefect] [nvarchar](50) NULL,--出生缺陷
	[BirthDefectOther] [nvarchar](1000) NULL,--出生缺陷说明
	[ChildIllScreening] [nvarchar](50) NULL,--新生儿疾病筛查情况
	[ChildIllScreeningOther] [nvarchar](1000) NULL,--新生儿疾病筛查情况说明
	[ChildHereditary] [nvarchar](50) NULL,--家庭遗传性疾病
	[ChildHereditaryOther] [nvarchar](1000) NULL,--家庭遗传性疾病说明
	[InputPersonID] [varchar](30) NULL,--录入人
	[InputDate] [datetime] NULL,--录入时间
 CONSTRAINT [pk_HealthFileChildren] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

Alter Table HealthFileChildren Add MotherTel Nvarchar(20)
Alter Table HealthFileChildren Add FatherTel Nvarchar(20)

/**
* 母亲孕期异常情况
**/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MontherException]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
	drop table [dbo].[MontherException]
GO
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[MontherException](
	[HealthFileChildrenID] [char](36) NOT NULL,
	[ID] [char](36) NOT NULL,
	[MontherExceptionID] [int] NOT NULL,
 CONSTRAINT [pk_MontherException] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/**
* 小儿围产期异常情况
**/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ChildrenException]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
	drop table [dbo].[ChildrenException]
GO
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ChildrenException](
	[HealthFileChildrenID] [char](36) NOT NULL,
	[ID] [char](36) NOT NULL,
	[ChildrenExceptionID] [int] NOT NULL,
 CONSTRAINT [pk_ChildrenException] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/**
* 儿童特殊情况记录表
**/
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PregnancyRecordChild]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
	drop table [dbo].[PregnancyRecordChild]
GO
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[PregnancyRecordChild](
	[ID] [char](36) NOT NULL,--主键
	[HealthFileChildrenID] [nvarchar](36) NOT NULL,--保健手册孕产妇档案ID
	[RecordDate] [Datetime] NULL,--日期
	[Record] [Nvarchar](1000) NULL,--特殊情况
	[DealOpinion] [Nvarchar](1000) NULL,--处理意见
	[Doctor] [Nvarchar](20) NULL,--医生签名
	[InputPersonID] [nvarchar](20) NULL,--录入人
	[InputDate] [datetime] NULL,--录入时间
 CONSTRAINT [pk_PregnancyRecordChild] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


/**
* 增加业务表外键
**/
Alter Table BabyVisit Add ForeignId Nvarchar(36)
GO
Alter Table ChildrenMediExam Add ForeignId Nvarchar(36)
GO
Alter Table ChildrenMediExam3_6 Add ForeignId Nvarchar(36)
GO