CREATE TABLE [dbo].[sam_role_module]
(
[module_id] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[role_id] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[sam_role_module] ADD CONSTRAINT [PK__sam_role_module__5F141958] PRIMARY KEY CLUSTERED  ([module_id], [role_id]) ON [PRIMARY]
GO
