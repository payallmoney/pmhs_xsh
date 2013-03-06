CREATE TABLE [dbo].[BarCode]
(
[ID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InitDate] [datetime] NOT NULL,
[InitPersonID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Status] [char] (1) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BarCode] ADD CONSTRAINT [PK_BarCode] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
