CREATE TABLE [dbo].[StoreHighRiskData]
(
[ID] [bigint] NOT NULL,
[DistrictNumber] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[Name] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Fvb_IsHighRiskCount] [int] NULL,
[Vb_IsHighRiskCount] [int] NULL,
[Va_IsHighRiskCount] [int] NULL,
[Va42_IsHighRiskCount] [int] NULL,
[InputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[StoreHighRiskData] ADD CONSTRAINT [PK__StoreHig__3214EC2704659998] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
