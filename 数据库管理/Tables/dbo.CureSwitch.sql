CREATE TABLE [dbo].[CureSwitch]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Date] [datetime] NOT NULL,
[ExportOrg] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ExportDepartment] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ReceptionDoctor] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Impress] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[Reason] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[History] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL,
[CureContent] [nvarchar] (300) COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL,
[Tel] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Name] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Age] [int] NULL,
[FromOrg] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Sex] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[OrgID] [int] NULL,
[ExecDistrictNum] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[CureSwitch] ADD CONSTRAINT [pk_CureSwitch] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
