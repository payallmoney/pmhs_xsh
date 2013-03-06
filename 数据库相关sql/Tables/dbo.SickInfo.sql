CREATE TABLE [dbo].[SickInfo]
(
[ID] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Name] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Sex] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Address] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NULL,
[IsDiabetesVisit] [bit] NULL,
[IsHypertensionVisit] [bit] NULL,
[IsFuriousInfo] [bit] NULL,
[Birthday] [datetime] NULL,
[Allergies] [varchar] (500) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[SickInfo] ADD CONSTRAINT [PK__SickInfo__1BE81D6E] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
EXEC sp_addextendedproperty N'MS_Description', N'门诊病人信息表', 'SCHEMA', N'dbo', 'TABLE', N'SickInfo', NULL, NULL
GO
EXEC sp_addextendedproperty N'MS_Description', N'住址', 'SCHEMA', N'dbo', 'TABLE', N'SickInfo', 'COLUMN', N'Address'
GO
EXEC sp_addextendedproperty N'MS_Description', N'档案编号', 'SCHEMA', N'dbo', 'TABLE', N'SickInfo', 'COLUMN', N'FileNo'
GO
EXEC sp_addextendedproperty N'MS_Description', N'主键', 'SCHEMA', N'dbo', 'TABLE', N'SickInfo', 'COLUMN', N'ID'
GO
EXEC sp_addextendedproperty N'MS_Description', N'是否有糖尿病', 'SCHEMA', N'dbo', 'TABLE', N'SickInfo', 'COLUMN', N'IsDiabetesVisit'
GO
EXEC sp_addextendedproperty N'MS_Description', N'是否有重性精神病', 'SCHEMA', N'dbo', 'TABLE', N'SickInfo', 'COLUMN', N'IsFuriousInfo'
GO
EXEC sp_addextendedproperty N'MS_Description', N'是否有高血压', 'SCHEMA', N'dbo', 'TABLE', N'SickInfo', 'COLUMN', N'IsHypertensionVisit'
GO
EXEC sp_addextendedproperty N'MS_Description', N'姓名', 'SCHEMA', N'dbo', 'TABLE', N'SickInfo', 'COLUMN', N'Name'
GO
EXEC sp_addextendedproperty N'MS_Description', N'性别', 'SCHEMA', N'dbo', 'TABLE', N'SickInfo', 'COLUMN', N'Sex'
GO
