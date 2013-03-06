CREATE TABLE [dbo].[BornStatus]
(
[BabyVisitID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[BornStatusID] [int] NOT NULL CONSTRAINT [DF__BornStatu__BornS__1758727B] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BornStatus] ADD CONSTRAINT [pk_BornStatus] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
