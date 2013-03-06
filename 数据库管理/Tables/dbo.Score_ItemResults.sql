CREATE TABLE [dbo].[Score_ItemResults]
(
[personid] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[scorename] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[item] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[score] [decimal] (5, 2) NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Score_ItemResults] ADD CONSTRAINT [PK__Score_It__0F7AF4BE705EA0EB] PRIMARY KEY CLUSTERED  ([personid], [scorename], [item]) ON [PRIMARY]
GO
