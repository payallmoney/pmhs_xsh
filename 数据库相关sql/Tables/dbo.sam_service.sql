CREATE TABLE [dbo].[sam_service]
(
[id] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[value] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[memo] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[sam_service] ADD CONSTRAINT [PK__sam_service__60083D91] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
