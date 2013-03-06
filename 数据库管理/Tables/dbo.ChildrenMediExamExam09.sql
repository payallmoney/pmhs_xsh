CREATE TABLE [dbo].[ChildrenMediExamExam09]
(
[ChildrenMediExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ChildrenMediExamExam09ID] [int] NOT NULL CONSTRAINT [DF__ChildrenM__Child__5708E33C] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChildrenMediExamExam09] ADD CONSTRAINT [pk_ChildrenMediExamExam09] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
