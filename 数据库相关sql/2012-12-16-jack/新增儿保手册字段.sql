/**
* Author:Jackstraw
* Date:2012/11/18
* Remarks: HealthFile�����ֶ�IsOverCount����Ϊ��ͯ�����ֲ��Ƿ��Ѿ�������ǣ����ֵΪ1
**/
/**
* ��ͯ��������
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
	[ID] [nvarchar](36) NOT NULL,--����
	[FileNo] [nvarchar](30) NOT NULL,--�������
	[Name] [nvarchar](30) NOT NULL,--����
	[Sex] [nvarchar](20) NOT NULL,--�Ա�
	[AllergiesHistory] [nvarchar](100) ,--����ʷ
	[FatherName] [nvarchar](30),--��������
	[MotherName] [nvarchar](30) ,--ĸ������
	[BuildDate] [datetime] NULL,--����ʱ��
	[BuildUnit] [nvarchar](50) NULL,--���ᵥλ
	[ResidenceProvence] [nvarchar](20) NULL,--������ַʡ
	[ResidenceCity] [nvarchar](50) NULL,--������ַ�ݣ��У�
	[ResidenceCounty] [nvarchar](50) NULL,--������ַ�أ��С�����
	[ResidenceTownship] [nvarchar](50) NULL,---������ַ��
	[ResidenceVillage] [nvarchar](50) NULL,--������ַ��
	[ResidenceGroup] [nvarchar](50) NULL,--������ַ��Ȼ��
	[AddressProvence] [nvarchar](20) NULL,--��סַʡ
	[AddressCity] [nvarchar](50) NULL,--��סַ�ݣ��У�
	[AddressCounty] [nvarchar](50) NULL,--��סַ�أ��С�����
	[AddressTownship] [nvarchar](50) NULL,--��סַ��
	[AddressVillage] [nvarchar](50) NULL,--��סַ��
	[AddressGroup] [nvarchar](50) NULL,--��סַ��Ȼ��
	[TEL] [nvarchar](20) NULL,--Ͻ�����ױ���Ժ��ϵ�绰
	[Birthday] [datetime] NULL,--��������
	[Weight] Decimal(10,2) NULL,--��������
	[Height] Decimal(10,2) NULL,--������
	[ApgarOneMinuts] [nvarchar](50) NULL,--Apgar����
	[ApgarFiveMinuts] [nvarchar](50) NULL,--Apgar����
	[abo] [nvarchar](50) NULL,--ABOѪ��
	[rh] [nvarchar](20) NOT NULL,--RHѪ��
	[Weekly] [nvarchar](50) NULL,--����
	[Gravidity] [int] NULL,--�д�
	[Parity] [int] NULL,--����
	[NumberOfBirths] [nvarchar](10) NULL,--̥��
	[BormWays] [nvarchar](100) NULL,--���䷽ʽ
	[BirthOrgName] [nvarchar](30) NULL,--������������
	[FatherAge] [Int] NULL,--������������
	[FatherNation] [nvarchar](50) NULL,--��������
	[FatherEducational] [nvarchar](50) NULL,--�����Ļ��̶�
	[FatherOccupation] [nvarchar](50) NULL,--����ְҵ
	[MotherAge] [Int] NULL,--ĸ����������
	[MotherNation] [nvarchar](50) NULL,--ĸ������
	[MotherEducational] [nvarchar](50) NULL,--ĸ���Ļ��̶�
	[MotherOccupation] [nvarchar](50) NULL,--ĸ��ְҵ
	[MontherExceptions] [nvarchar](20) NULL,--ĸ�������쳣���
	[MontherExceptionOhter] [nvarchar](1000) NULL,--ĸ�������쳣�������
	[ChildrenExceptions] [nvarchar](20) NULL,--С��Χ�����쳣���
	[ChildrenException1] [nvarchar](50) NULL,--����
	[ChildrenExceptionOhter] [nvarchar](1000) NULL,--С��Χ�����쳣�������
	[BirthDefect] [nvarchar](50) NULL,--����ȱ��
	[BirthDefectOther] [nvarchar](1000) NULL,--����ȱ��˵��
	[ChildIllScreening] [nvarchar](50) NULL,--����������ɸ�����
	[ChildIllScreeningOther] [nvarchar](1000) NULL,--����������ɸ�����˵��
	[ChildHereditary] [nvarchar](50) NULL,--��ͥ�Ŵ��Լ���
	[ChildHereditaryOther] [nvarchar](1000) NULL,--��ͥ�Ŵ��Լ���˵��
	[InputPersonID] [varchar](30) NULL,--¼����
	[InputDate] [datetime] NULL,--¼��ʱ��
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
* ĸ�������쳣���
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
* С��Χ�����쳣���
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
* ��ͯ���������¼��
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
	[ID] [char](36) NOT NULL,--����
	[HealthFileChildrenID] [nvarchar](36) NOT NULL,--�����ֲ��в�������ID
	[RecordDate] [Datetime] NULL,--����
	[Record] [Nvarchar](1000) NULL,--�������
	[DealOpinion] [Nvarchar](1000) NULL,--�������
	[Doctor] [Nvarchar](20) NULL,--ҽ��ǩ��
	[InputPersonID] [nvarchar](20) NULL,--¼����
	[InputDate] [datetime] NULL,--¼��ʱ��
 CONSTRAINT [pk_PregnancyRecordChild] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


/**
* ����ҵ������
**/
Alter Table BabyVisit Add ForeignId Nvarchar(36)
GO
Alter Table ChildrenMediExam Add ForeignId Nvarchar(36)
GO
Alter Table ChildrenMediExam3_6 Add ForeignId Nvarchar(36)
GO