CREATE TABLE [dbo].[HealthFileTransfer]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FromFileNo] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[TransferReason] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NOT NULL,
[TransferTime] [datetime] NULL,
[FromOpt] [varchar] (36) COLLATE Chinese_PRC_CI_AS NULL,
[FromShowOptName] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[FromOptOrg] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[Name] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Sex] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[Birthday] [datetime] NULL,
[IdNumber] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[FromTown] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[FromVillage] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[FromBuildDoctor] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[FromBuildUnit] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[FromBuildPerson] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[ResidenceAddress] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL,
[Address] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL,
[FromDistrictNumber] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[ToDistrictNumber] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[ToFileNo] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[IsSure] [nvarchar] (2) COLLATE Chinese_PRC_CI_AS NULL,
[IsSureOpt] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[IsSureDate] [datetime] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HealthFileTransfer] ADD CONSTRAINT [pk_HealthFileTransfer] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
