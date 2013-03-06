CREATE TABLE [dbo].[FemeFamilyHistory]
(
[FirstVistBeforeBornID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FemeFamilyHistoryID] [int] NOT NULL CONSTRAINT [DF__FemeFamil__FemeF__15DA3E5D] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FemeFamilyHistory] ADD CONSTRAINT [pk_FemeFamilyHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
