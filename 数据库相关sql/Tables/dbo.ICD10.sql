CREATE TABLE [dbo].[ICD10]
(
[ID] [int] NOT NULL IDENTITY(1, 1),
[Name] [nvarchar] (64) COLLATE Chinese_PRC_CI_AS NULL,
[Number] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[StructNumber] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[ParentNumber] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[Description] [nvarchar] (256) COLLATE Chinese_PRC_CI_AS NULL,
[ParentID] [int] NOT NULL,
[Level] [int] NOT NULL,
[IsDetail] [bit] NOT NULL,
[Name_Png] [nvarchar] (64) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ICD10] ADD CONSTRAINT [pk_ICD10] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
