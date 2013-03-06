CREATE TABLE [dbo].[Score_Examdate]
(
[groupname] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[scoredate] [datetime] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Score_Examdate] ADD CONSTRAINT [PK__Score_Ex__ED1647CD6AA5C795] PRIMARY KEY CLUSTERED  ([groupname]) ON [PRIMARY]
GO
