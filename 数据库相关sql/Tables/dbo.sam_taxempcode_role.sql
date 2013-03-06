CREATE TABLE [dbo].[sam_taxempcode_role]
(
[loginname] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[id] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[sam_taxempcode_role] ADD CONSTRAINT [PK__sam_taxempcode_r__61F08603] PRIMARY KEY CLUSTERED  ([loginname], [id]) ON [PRIMARY]
GO
