CREATE TABLE [dbo].[sam_module_category]
(
[id] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[name] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ordinal] [int] NOT NULL,
[CLS] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ParentId] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NULL,
[IsDetail] [bit] NULL,
[Level] [int] NULL,
[ParentName] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[TemplateId] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[sam_module_category] ADD CONSTRAINT [PK__sam_module_categ__5B438874] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
