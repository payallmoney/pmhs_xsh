CREATE TABLE [dbo].[Sms_SendLog]
(
[smsdate] [datetime] NOT NULL,
[examname] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[fileno] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[tel] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[msg] [varchar] (500) COLLATE Chinese_PRC_CI_AS NOT NULL,
[status] [int] NOT NULL,
[sendtime] [datetime] NULL,
[error] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[tablename] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[tableidvalue] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[personname] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[querytype] [varchar] (2) COLLATE Chinese_PRC_CI_AS NULL,
[id] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL CONSTRAINT [DF__Sms_SendLog__id__61DB776A] DEFAULT (newid())
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Sms_SendLog] ADD CONSTRAINT [PK__Sms_Send__5AB1D2807EACC042] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
EXEC sp_addextendedproperty N'MS_Description', N'status = 0 未发送
          = 1 已发送
          = -1 发送失败
', 'SCHEMA', N'dbo', 'TABLE', N'Sms_SendLog', 'COLUMN', N'smsdate'
GO
