CREATE TABLE [dbo].[HealthDirect]
(
[MedicalExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HealthDirectID] [int] NOT NULL CONSTRAINT [DF__HealthDir__Healt__71F1E3A2] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HealthDirect] ADD CONSTRAINT [pk_HealthDirect] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
