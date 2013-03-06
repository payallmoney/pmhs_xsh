CREATE TABLE [dbo].[HighRisk]
(
[ID] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Number] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Exception] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Type] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Exception_Png] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HighRisk] ADD CONSTRAINT [PK_HighRisk] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
