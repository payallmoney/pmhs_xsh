CREATE TABLE [dbo].[Cod_Examlist]
(
[tablename] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[examname] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ord] [bigint] NOT NULL,
[hascount] [bit] NOT NULL,
[countcol] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[datecol] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[namerule] [varchar] (500) COLLATE Chinese_PRC_CI_AS NULL,
[queryMethod] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[html] [varchar] (5000) COLLATE Chinese_PRC_CI_AS NULL CONSTRAINT [DF__Cod_Examli__html__7DEDA633] DEFAULT ((1)),
[listcol] [varchar] (5000) COLLATE Chinese_PRC_CI_AS NULL,
[datetypecol] [varchar] (5000) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Cod_Examlist] ADD CONSTRAINT [PK__Cod_Exam__455CDA003726238F] PRIMARY KEY CLUSTERED  ([examname]) ON [PRIMARY]
GO
