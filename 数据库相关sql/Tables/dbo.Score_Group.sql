CREATE TABLE [dbo].[Score_Group]
(
[groupname] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[item] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ord] [int] NOT NULL CONSTRAINT [DF__Score_Group__ord__14D10B8B] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Score_Group] ADD CONSTRAINT [PK__Score_Gr__448DCFEF6D823440] PRIMARY KEY CLUSTERED  ([groupname], [item]) ON [PRIMARY]
GO
