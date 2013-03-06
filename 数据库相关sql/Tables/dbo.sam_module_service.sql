CREATE TABLE [dbo].[sam_module_service]
(
[module_id] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[service_id] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[sam_module_service] ADD CONSTRAINT [PK__sam_module_servi__5D2BD0E6] PRIMARY KEY CLUSTERED  ([module_id], [service_id]) ON [PRIMARY]
GO
