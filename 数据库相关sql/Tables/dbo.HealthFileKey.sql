CREATE TABLE [dbo].[HealthFileKey]
(
[DistrictNumber] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[MaxKey] [int] NOT NULL CONSTRAINT [DF__HealthFil__MaxKe__46E78A0C] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HealthFileKey] ADD CONSTRAINT [PK__HealthFileKey__45F365D3] PRIMARY KEY CLUSTERED  ([DistrictNumber]) ON [PRIMARY]
GO
