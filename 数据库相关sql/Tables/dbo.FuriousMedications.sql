CREATE TABLE [dbo].[FuriousMedications]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FuriousVisitID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DrugName] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Usage] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Dosage] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FuriousMedications] ADD CONSTRAINT [pk_FuriousMedications] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
