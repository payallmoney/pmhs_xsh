CREATE TABLE [dbo].[UpgrowthStandard]
(
[ID] [int] NOT NULL,
[Number] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[UpgrowthStandardTypeID] [int] NOT NULL,
[Name] [nvarchar] (64) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Forbidden] [bit] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[UpgrowthStandard] ADD CONSTRAINT [pk_UpgrowthStandard] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
