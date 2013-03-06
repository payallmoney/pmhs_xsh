CREATE TABLE [dbo].[ExamSymptom]
(
[MedicalExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ExamSymptomID] [int] NOT NULL CONSTRAINT [DF__ExamSympt__ExamS__2C538F61] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ExamSymptom] ADD CONSTRAINT [pk_ExamSymptom] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
