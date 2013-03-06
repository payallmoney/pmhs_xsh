CREATE TABLE [dbo].[ExamMedications]
(
[MedicalExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DrugName] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[UsageWay] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Dosage] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[DrugTime] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Dependency] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ExamMedications] ADD CONSTRAINT [pk_ExamMedications] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
