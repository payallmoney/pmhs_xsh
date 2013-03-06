CREATE TABLE [dbo].[CheckSickness]
(
[ChildrenMediExam3ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[CheckSicknessID] [int] NOT NULL CONSTRAINT [DF__CheckSick__Check__4BCC3ABA] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[CheckSickness] ADD CONSTRAINT [pk_CheckSickness] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
