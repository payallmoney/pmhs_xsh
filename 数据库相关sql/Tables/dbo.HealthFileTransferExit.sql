CREATE TABLE [dbo].[HealthFileTransferExit]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HealthFileTransferID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ExitReasion] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputPersonID] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HealthFileTransferExit] ADD CONSTRAINT [pk_HealthFileTransferExit] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
