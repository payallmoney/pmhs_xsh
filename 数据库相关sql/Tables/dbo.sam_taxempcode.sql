CREATE TABLE [dbo].[sam_taxempcode]
(
[loginname] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[username] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[password] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[org_id] [int] NOT NULL,
[create_date] [datetime] NOT NULL,
[valid_flag] [varchar] (2) COLLATE Chinese_PRC_CI_AS NOT NULL,
[district_id] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[type] [int] NULL,
[IsLookAuthority] [int] NULL CONSTRAINT [DF__sam_taxem__IsLoo__09946309] DEFAULT ((0)),
[IsStasticAuth] [int] NULL CONSTRAINT [DF__sam_taxem__IsSta__3572E547] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[sam_taxempcode] ADD CONSTRAINT [PK__sam_taxempcode__60FC61CA] PRIMARY KEY CLUSTERED  ([loginname]) ON [PRIMARY]
GO
