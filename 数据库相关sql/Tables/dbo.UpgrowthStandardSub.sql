CREATE TABLE [dbo].[UpgrowthStandardSub]
(
[ID] [int] NOT NULL,
[UpgrowthStandardID] [int] NOT NULL,
[YearOld] [int] NOT NULL,
[MonthOld] [int] NOT NULL,
[ConsultHeight] [decimal] (10, 2) NOT NULL,
[BoyValueLow] [decimal] (10, 2) NOT NULL,
[BoyValueMiddle] [decimal] (10, 2) NOT NULL,
[GirlValueLow] [decimal] (10, 2) NOT NULL,
[GirlValueMiddle] [decimal] (10, 2) NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[UpgrowthStandardSub] ADD CONSTRAINT [pk_UpgrowthStandardSub] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
