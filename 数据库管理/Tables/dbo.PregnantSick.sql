CREATE TABLE [dbo].[PregnantSick]
(
[BabyVisitID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[KidneySickID] [int] NOT NULL CONSTRAINT [DF__PregnantS__Kidne__3D7E1B63] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[PregnantSick] ADD CONSTRAINT [pk_PregnantSick] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
