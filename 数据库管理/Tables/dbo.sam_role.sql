CREATE TABLE [dbo].[sam_role]
(
[id] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[name] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[descr] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[sam_role] ADD CONSTRAINT [PK__sam_role__5E1FF51F] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
