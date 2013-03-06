CREATE TABLE [dbo].[FuriousHealing]
(
[FuriousVisitID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FuriousHealingID] [int] NOT NULL CONSTRAINT [DF__FuriousHe__Furio__0D44F85C] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FuriousHealing] ADD CONSTRAINT [pk_FuriousHealing] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
