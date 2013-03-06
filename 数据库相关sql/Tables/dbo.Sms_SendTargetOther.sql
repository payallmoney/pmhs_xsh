CREATE TABLE [dbo].[Sms_SendTargetOther]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[name] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[tel] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DistrictNumber] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[type] [int] NOT NULL,
[isTest] [int] NOT NULL CONSTRAINT [DF__Sms_SendT__isTes__5E0AE686] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Sms_SendTargetOther] ADD CONSTRAINT [PK__Sms_Send__3213E83F54817C4C] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
