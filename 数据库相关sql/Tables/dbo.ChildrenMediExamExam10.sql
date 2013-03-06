CREATE TABLE [dbo].[ChildrenMediExamExam10]
(
[ChildrenMediExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ChildrenMediExamExam10ID] [int] NOT NULL CONSTRAINT [DF__ChildrenM__Child__59E54FE7] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChildrenMediExamExam10] ADD CONSTRAINT [pk_ChildrenMediExamExam10] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
