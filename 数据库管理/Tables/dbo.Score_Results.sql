CREATE TABLE [dbo].[Score_Results]
(
[personid] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[scorename] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[item] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[score] [decimal] (5, 2) NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Score_Results] ADD CONSTRAINT [PK__Score_Re__0F7AF4BE76177A41] PRIMARY KEY CLUSTERED  ([personid], [scorename], [item]) ON [PRIMARY]
GO
