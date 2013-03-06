CREATE TABLE [dbo].[Cod_BaseCode]
(
[name] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[code] [varchar] (10) COLLATE Chinese_PRC_CI_AS NOT NULL,
[value] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Cod_BaseCode] ADD CONSTRAINT [PK__Cod_Base__E1B6FBD53449B6E4] PRIMARY KEY CLUSTERED  ([name], [code]) ON [PRIMARY]
GO
