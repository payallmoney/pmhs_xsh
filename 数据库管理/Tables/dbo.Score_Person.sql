CREATE TABLE [dbo].[Score_Person]
(
[scorename] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[personid] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[examgroup] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[allcount] [decimal] (10, 2) NULL,
[examdate] [datetime] NULL,
[fileNo] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Score_Person] ADD CONSTRAINT [PK__Score_Pe__25E34C3C733B0D96] PRIMARY KEY CLUSTERED  ([personid], [scorename]) ON [PRIMARY]
GO
