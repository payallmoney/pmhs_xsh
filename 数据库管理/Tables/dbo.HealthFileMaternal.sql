CREATE TABLE [dbo].[HealthFileMaternal]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Name] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Birthday] [datetime] NULL,
[TEL] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[FirstAidTel] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[AddressProvence] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[AddressCity] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[AddressCounty] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[AddressTownship] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[AddressVillage] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[AddressGroup] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ResidenceProvence] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[ResidenceCity] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ResidenceCounty] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ResidenceTownship] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ResidenceVillage] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ResidenceGroup] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[HighRiskCode] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[BuildUnit] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[BuildDate] [datetime] NULL,
[Nationality] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[NationalityOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[IDNumber] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[WorkUnit] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ResideType] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[AreaOfResidence] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[Distance] [decimal] (10, 2) NULL,
[Folk] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[FolkOther] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Education] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Occupation] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[RecuperateProvence] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[RecuperateCity] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[RecuperateCounty] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[RecuperateTownship] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[RecuperateVillage] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[RecuperateGroup] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[HusbandName] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[HusbandBirthday] [datetime] NULL,
[HusbandTEL] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[HusbandEducation] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[HusbandOccupation] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[HusbandOccupationOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[HusbandWorkUnit] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[Gravidity] [int] NULL,
[IsClosed] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[ClosedDate] [datetime] NULL,
[RelatedInfoSearch] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NULL,
[BarCode] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HealthFileMaternal] ADD CONSTRAINT [PK__HealthFi__3214EC27597B3B93] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IDX_HealthFileMaternal] ON [dbo].[HealthFileMaternal] ([FileNo]) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [idate_HealthFileMaternal] ON [dbo].[HealthFileMaternal] ([InputDate]) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [iddate_HealthFileMaternal] ON [dbo].[HealthFileMaternal] ([InputDate]) ON [PRIMARY]
GO
