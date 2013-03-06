CREATE TABLE [dbo].[Cod_TelUpdateCol]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[tablename] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[col] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[name] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ord] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Cod_TelUpdateCol] ADD CONSTRAINT [PK__Cod_TelU__3213E83F4850AF91] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
