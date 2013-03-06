CREATE TABLE [dbo].[Sms_Status]
(
[smsdate] [datetime] NOT NULL,
[iscreated] [int] NOT NULL,
[issended] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Sms_Status] ADD CONSTRAINT [PK__Sms_Stat__C1797AB201892CED] PRIMARY KEY CLUSTERED  ([smsdate]) ON [PRIMARY]
GO
EXEC sp_addextendedproperty N'MS_Description', N'iscreated  =  0  未生成
                    1  正在生成
                    2  已生成
issended  = 0  未发送
               = 1   正在发送
               = 2   发送完成', 'SCHEMA', N'dbo', 'TABLE', N'Sms_Status', 'COLUMN', N'issended'
GO
