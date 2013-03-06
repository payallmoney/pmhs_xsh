CREATE TABLE [dbo].[CheckDirect3]
(
[ChildrenMediExam3ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[CheckDirect3ID] [int] NOT NULL CONSTRAINT [DF__CheckDire__Check__57DD0BE4] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[CheckDirect3] ADD CONSTRAINT [pk_CheckDirect3] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
