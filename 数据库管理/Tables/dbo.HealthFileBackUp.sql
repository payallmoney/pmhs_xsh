CREATE TABLE [dbo].[HealthFileBackUp]
(
[FileNo] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Name] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Address] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[ResidenceAddress] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[TEL] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Township] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[Village] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[BuildUnit] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[BuildPerson] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[BuildDate] [datetime] NULL,
[DistrictNumber] [char] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Name_Png] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[LastModifyDate] [datetime] NULL,
[ModifyPerson] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[BarCode] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[IsOverCount] [smallint] NULL CONSTRAINT [DF__HealthBackFil__IsOve__60924D76] DEFAULT ((0)),
[IsModifyOrNew] [smallint] NULL CONSTRAINT [DF__HealthBackFil__IsMod__618671AF] DEFAULT ((0)),
[PaperFileNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HealthFileBackUp] ADD CONSTRAINT [pk_HealthFileBackUp] PRIMARY KEY CLUSTERED  ([FileNo]) ON [PRIMARY]
GO
