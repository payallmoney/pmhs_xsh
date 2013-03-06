CREATE TABLE [dbo].[HealthFileLoginOffExit]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HealthFileLoginOffID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[LoginOffExitReasion] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputPersonID] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HealthFileLoginOffExit] ADD CONSTRAINT [pk_HealthFileLoginOffExit] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
