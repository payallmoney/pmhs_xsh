CREATE TABLE [dbo].[CureBack]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Date] [datetime] NOT NULL,
[ExportOrg] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ReceptionDoctor] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Result] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[RecordNumber] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[CheckResult] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL,
[CureAdvice] [nvarchar] (300) COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Tel] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL,
[FromOrg] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ExecDistrictNum] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[CureBack] ADD CONSTRAINT [pk_CureBack] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
