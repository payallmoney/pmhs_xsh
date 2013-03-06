CREATE TABLE [dbo].[Sms_PersonTel]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[fileNo] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[filetype] [int] NOT NULL,
[name] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[tel] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[updateCol] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[updatetype] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Sms_PersonTel] ADD CONSTRAINT [PK__Sms_Pers__3213E83F7BD05397] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
CREATE UNIQUE NONCLUSTERED INDEX [idx_fileno] ON [dbo].[Sms_PersonTel] ([fileNo]) ON [PRIMARY]
GO
