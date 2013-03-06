CREATE TABLE [dbo].[CheckDirect3_6]
(
[ChildrenMediExam36ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[CheckDirect36ID] [int] NOT NULL CONSTRAINT [DF__CheckDire__Check__2759D01A] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[CheckDirect3_6] ADD CONSTRAINT [pk_CheckDirect3_6] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
