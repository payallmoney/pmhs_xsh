CREATE TABLE [dbo].[sam_taxorgcode]
(
[taxorgcode] [varchar] (11) COLLATE Chinese_PRC_CI_AS NOT NULL,
[taxorgname] [varchar] (40) COLLATE Chinese_PRC_CI_AS NOT NULL,
[taxorgshortname] [varchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[parentId] [varchar] (11) COLLATE Chinese_PRC_CI_AS NOT NULL,
[rootnode] [varchar] (2) COLLATE Chinese_PRC_CI_AS NOT NULL,
[mgauth] [varchar] (4) COLLATE Chinese_PRC_CI_AS NULL,
[levyauth] [varchar] (4) COLLATE Chinese_PRC_CI_AS NULL,
[mgdropauth] [varchar] (4) COLLATE Chinese_PRC_CI_AS NULL,
[levydropauth] [varchar] (4) COLLATE Chinese_PRC_CI_AS NULL,
[orgclass] [varchar] (2) COLLATE Chinese_PRC_CI_AS NOT NULL,
[orgtype] [varchar] (2) COLLATE Chinese_PRC_CI_AS NOT NULL,
[valid] [varchar] (2) COLLATE Chinese_PRC_CI_AS NOT NULL,
[levelNum] [int] NULL,
[childrenNum] [int] NULL,
[hierarchy] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[sam_taxorgcode] ADD CONSTRAINT [PK__sam_taxorgcode__62E4AA3C] PRIMARY KEY CLUSTERED  ([taxorgcode]) ON [PRIMARY]
GO
