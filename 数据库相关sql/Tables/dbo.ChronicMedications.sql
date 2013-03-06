CREATE TABLE [dbo].[ChronicMedications]
(
[ID] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DrugName] [varchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Usage] [varchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Dosage] [varchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ChronicVisitID] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChronicMedications] ADD CONSTRAINT [PK__ChronicMedicatio__3AD6B8E2] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
