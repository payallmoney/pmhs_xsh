CREATE TABLE [dbo].[sam_module]
(
[id] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[name] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[category_id] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[url] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ordinal] [int] NOT NULL,
[IsNavigate] [bit] NOT NULL CONSTRAINT [DF__sam_modul__IsNav__636EBA21] DEFAULT ((0)),
[InputPage] [nvarchar] (40) COLLATE Chinese_PRC_CI_AS NULL,
[Type] [int] NULL CONSTRAINT [DF__sam_module__Type__6462DE5A] DEFAULT ((-1)),
[CLS] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[sam_module] ADD CONSTRAINT [PK__sam_module__5C37ACAD] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
