CREATE TABLE [dbo].[Sms_SendTarget]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[name] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[tablename] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[msg] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NOT NULL CONSTRAINT [DF__Cod_TelSend__msg__50B0EB68] DEFAULT (''),
[wherestr] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL CONSTRAINT [DF__Cod_TelSe__where__51A50FA1] DEFAULT ('1=1'),
[tableidname] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Sms_SendTarget] ADD CONSTRAINT [PK__Cod_TelS__3213E83F4EC8A2F6] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
