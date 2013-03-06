CREATE TABLE [dbo].[VaccineInfo]
(
[ID] [int] NOT NULL IDENTITY(1, 1),
[Number] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[Name] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Target] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Times] [int] NOT NULL,
[Postion] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Path] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Dosage] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Name_Png] [nvarchar] (256) COLLATE Chinese_PRC_CI_AS NULL,
[Description] [nvarchar] (256) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccineInfo] ADD CONSTRAINT [pk_VaccineInfo] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
