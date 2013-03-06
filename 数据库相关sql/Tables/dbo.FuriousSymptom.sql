CREATE TABLE [dbo].[FuriousSymptom]
(
[FuriousInfoID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FuriousSymptomID] [int] NOT NULL CONSTRAINT [DF__FuriousSy__Furio__078C1F06] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FuriousSymptom] ADD CONSTRAINT [pk_FuriousSymptom] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
