CREATE TABLE [dbo].[Cod_ScoreRule]
(
[id] [bigint] NOT NULL IDENTITY(1, 1),
[name] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[standard] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[method] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[paramurl] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[isfileno] [bit] NOT NULL,
[querytable] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[querypersoncolumn] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[querycolumn] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[querydatecolumn] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[querywhere] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL CONSTRAINT [DF__Cod_Score__query__01BE3717] DEFAULT ((1))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Cod_ScoreRule] ADD CONSTRAINT [PK__Cod_Scor__3213E83F3FBB6990] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
