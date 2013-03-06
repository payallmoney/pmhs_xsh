CREATE TABLE [dbo].[KidneySick]
(
[MedicalExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[KidneySickID] [int] NOT NULL CONSTRAINT [DF__KidneySic__Kidne__3587F3E0] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[KidneySick] ADD CONSTRAINT [pk_KidneySick] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
