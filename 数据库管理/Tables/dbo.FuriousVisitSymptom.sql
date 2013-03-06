CREATE TABLE [dbo].[FuriousVisitSymptom]
(
[FuriousVisitID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FuriousVisitSymptomID] [int] NOT NULL CONSTRAINT [DF__FuriousVi__Furio__0A688BB1] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FuriousVisitSymptom] ADD CONSTRAINT [pk_FuriousVisitSymptom] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
