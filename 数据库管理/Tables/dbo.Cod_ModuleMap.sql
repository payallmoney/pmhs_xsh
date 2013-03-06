CREATE TABLE [dbo].[Cod_ModuleMap]
(
[id] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[mainmoduleid] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[submoduleid] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Cod_ModuleMap] ADD CONSTRAINT [PK__Cod_Modu__3213E83F3A02903A] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
