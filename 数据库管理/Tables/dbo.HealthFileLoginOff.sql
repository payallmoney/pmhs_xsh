CREATE TABLE [dbo].[HealthFileLoginOff]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[LoginOffReason] [int] NOT NULL,
[LoginOffRemark] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[LoginOffOpt] [varchar] (36) COLLATE Chinese_PRC_CI_AS NULL,
[LoginOffOptOrg] [varchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[LoginOffDate] [datetime] NULL,
[InputPersonID] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HealthFileLoginOff] ADD CONSTRAINT [pk_HealthFileLoginOff] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
