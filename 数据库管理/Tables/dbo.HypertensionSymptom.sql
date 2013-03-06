CREATE TABLE [dbo].[HypertensionSymptom]
(
[HypertensionVisitID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HypertensionSymptomID] [int] NOT NULL CONSTRAINT [DF__Hypertens__Hyper__01D345B0] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HypertensionSymptom] ADD CONSTRAINT [pk_HypertensionSymptom] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
