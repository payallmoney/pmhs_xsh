CREATE TABLE [dbo].[ClinicLogs]
(
[ID] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputTime] [datetime] NOT NULL,
[Start_ill] [datetime] NULL,
[Symptom] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[Temperature] [float] NULL,
[Diagnose] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[Suggustion] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[RecepTime] [datetime] NULL,
[Remark] [nvarchar] (4000) COLLATE Chinese_PRC_CI_AS NULL,
[IsModify] [bit] NULL,
[ModifyTime] [timestamp] NOT NULL,
[Presure01] [int] NULL,
[Presure02] [int] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ClinicLogs] ADD CONSTRAINT [PK__ClinicLogs__19FFD4FC] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
EXEC sp_addextendedproperty N'MS_Description', N'门诊登记表', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', NULL, NULL
GO
EXEC sp_addextendedproperty N'MS_Description', N'诊断', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'Diagnose'
GO
EXEC sp_addextendedproperty N'MS_Description', N'接诊医生', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'Doctor'
GO
EXEC sp_addextendedproperty N'MS_Description', N'档案编号', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'FileNo'
GO
EXEC sp_addextendedproperty N'MS_Description', N'主键', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'ID'
GO
EXEC sp_addextendedproperty N'MS_Description', N'录入时间', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'InputTime'
GO
EXEC sp_addextendedproperty N'MS_Description', N'是否修改过', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'IsModify'
GO
EXEC sp_addextendedproperty N'MS_Description', N'修改时间', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'ModifyTime'
GO
EXEC sp_addextendedproperty N'MS_Description', N'血压1', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'Presure01'
GO
EXEC sp_addextendedproperty N'MS_Description', N'血压2', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'Presure02'
GO
EXEC sp_addextendedproperty N'MS_Description', N'接诊时间', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'RecepTime'
GO
EXEC sp_addextendedproperty N'MS_Description', N'备注', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'Remark'
GO
EXEC sp_addextendedproperty N'MS_Description', N'发病时间', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'Start_ill'
GO
EXEC sp_addextendedproperty N'MS_Description', N'处理意见', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'Suggustion'
GO
EXEC sp_addextendedproperty N'MS_Description', N'症状体征', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'Symptom'
GO
EXEC sp_addextendedproperty N'MS_Description', N'体温', 'SCHEMA', N'dbo', 'TABLE', N'ClinicLogs', 'COLUMN', N'Temperature'
GO
