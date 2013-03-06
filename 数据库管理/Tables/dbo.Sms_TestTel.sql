CREATE TABLE [dbo].[Sms_TestTel]
(
[id] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[testTel] [varchar] (11) COLLATE Chinese_PRC_CI_AS NOT NULL,
[remark] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Sms_TestTel] ADD CONSTRAINT [PK__Sms_Test__3213E83F5C229E14] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
