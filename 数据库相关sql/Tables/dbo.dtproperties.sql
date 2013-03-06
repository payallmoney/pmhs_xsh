CREATE TABLE [dbo].[dtproperties]
(
[id] [int] NOT NULL,
[property] [varchar] (64) COLLATE Chinese_PRC_CI_AS NOT NULL,
[version] [int] NOT NULL,
[objectid] [int] NULL,
[value] [varchar] (255) COLLATE Chinese_PRC_CI_AS NULL,
[uvalue] [varchar] (255) COLLATE Chinese_PRC_CI_AS NULL,
[lvalue] [image] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[dtproperties] ADD CONSTRAINT [PK__dtproperties__4830B400] PRIMARY KEY CLUSTERED  ([id], [property]) ON [PRIMARY]
GO
