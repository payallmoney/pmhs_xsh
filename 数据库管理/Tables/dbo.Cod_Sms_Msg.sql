CREATE TABLE [dbo].[Cod_Sms_Msg]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[type] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[name] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[title] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[content] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NOT NULL,
[after] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Cod_Sms_Msg] ADD CONSTRAINT [PK__Cod_Sms___3213E83F4297D63B] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
