CREATE TABLE [dbo].[BabyDirect]
(
[BabyVisitID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[BabyDirectID] [int] NOT NULL CONSTRAINT [DF__BabyDirec__BabyD__503BEA1C] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BabyDirect] ADD CONSTRAINT [pk_BabyDirect] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
