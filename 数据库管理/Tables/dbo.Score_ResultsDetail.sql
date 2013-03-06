CREATE TABLE [dbo].[Score_ResultsDetail]
(
[personid] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[scorename] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[item] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[detailitem] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[score] [decimal] (5, 2) NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Score_ResultsDetail] ADD CONSTRAINT [PK__Score_Re__D56B27F778F3E6EC] PRIMARY KEY CLUSTERED  ([personid], [scorename], [item], [detailitem]) ON [PRIMARY]
GO
