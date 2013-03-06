CREATE TABLE [dbo].[CheckDirect]
(
[ChildrenMediExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[CheckDirectID] [int] NOT NULL CONSTRAINT [DF__CheckDire__Check__55009F39] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[CheckDirect] ADD CONSTRAINT [pk_CheckDirect] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
