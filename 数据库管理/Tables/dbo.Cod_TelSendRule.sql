CREATE TABLE [dbo].[Cod_TelSendRule]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[name] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[tablename] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[col] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[days] [int] NOT NULL,
[optdate] [datetime] NOT NULL,
[msg] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NOT NULL CONSTRAINT [DF__Cod_TelSend__msg__049AA3C2] DEFAULT (''),
[wherestr] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL CONSTRAINT [DF__Cod_TelSe__where__058EC7FB] DEFAULT ('1=1'),
[tableidname] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Cod_TelSendRule] ADD CONSTRAINT [PK__Cod_TelS__3213E83F457442E6] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
