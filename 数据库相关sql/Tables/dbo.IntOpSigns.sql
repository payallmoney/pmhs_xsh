CREATE TABLE [dbo].[IntOpSigns]
(
[id] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[intKey] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[opKey] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[SignsID] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[SignsName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[SignsValue] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[SignsUnit] [varchar] (10) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[IntOpSigns] ADD CONSTRAINT [pk_IntOpSigns] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
