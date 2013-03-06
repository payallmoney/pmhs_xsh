CREATE TABLE [dbo].[AfterBornDirect]
(
[VisitAfterBornID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[AfterBornDirectID] [int] NOT NULL CONSTRAINT [DF__AfterBorn__After__6AEFE058] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[AfterBornDirect] ADD CONSTRAINT [pk_AfterBornDirect] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
EXEC sp_addextendedproperty N'MS_Description', N'', 'SCHEMA', N'dbo', 'TABLE', N'AfterBornDirect', NULL, NULL
GO
EXEC sp_addextendedproperty N'MS_Description', N'ID测试', 'SCHEMA', N'dbo', 'TABLE', N'AfterBornDirect', 'COLUMN', N'ID'
GO
