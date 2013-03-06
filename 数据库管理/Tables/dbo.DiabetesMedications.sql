CREATE TABLE [dbo].[DiabetesMedications]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DiabetesVisitID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DrugName] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Usage] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Dosage] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DiabetesMedications] ADD CONSTRAINT [pk_DiabetesMedications] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
