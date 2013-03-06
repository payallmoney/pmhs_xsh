CREATE TABLE [dbo].[DiabetesSymptom]
(
[DiabetesVisitID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DiabetesSymptomID] [int] NOT NULL CONSTRAINT [DF__DiabetesS__Diabe__04AFB25B] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DiabetesSymptom] ADD CONSTRAINT [pk_DiabetesSymptom] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
