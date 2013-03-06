CREATE TABLE [dbo].[BusinessDataGrid]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[RowID] [bigint] NOT NULL,
[MedicalExamID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[MedicalExamDate] [datetime] NULL,
[BabyVisitID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[BabyVisitDate] [datetime] NULL,
[ChildrenMediExam01ID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[ChildrenMediExam01Date] [datetime] NULL,
[ChildrenMediExam02ID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[ChildrenMediExam02Date] [datetime] NULL,
[ChildrenMediExam36ID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[ChildrenMediExam36Date] [datetime] NULL,
[FirstVistBeforeBornID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[FirstVistBeforeBornDate] [datetime] NULL,
[VisitBeforeBornID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VisitBeforeBornItem] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VisitBeforeBornDate] [datetime] NULL,
[VisitAfterBornID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VisitAfterBornItem] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VisitAfterBornDate] [datetime] NULL,
[VisitAfterBorn42ID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VisitAfterBorn42Date] [datetime] NULL,
[HypertensionVisitID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[HypertensionVisitDate] [datetime] NULL,
[DiabetesVisitID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[DiabetesVisitDate] [datetime] NULL,
[FuriousVisitID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[FuriousVisitDate] [datetime] NULL,
[InputPersonID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BusinessDataGrid] ADD CONSTRAINT [PK_BusinessDataGrid] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
