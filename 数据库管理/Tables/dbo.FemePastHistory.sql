CREATE TABLE [dbo].[FemePastHistory]
(
[FirstVistBeforeBornID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FemePastHistoryID] [int] NOT NULL CONSTRAINT [DF__FemePastH__FemeP__18B6AB08] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FemePastHistory] ADD CONSTRAINT [pk_FemePastHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
