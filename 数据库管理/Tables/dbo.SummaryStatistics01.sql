CREATE TABLE [dbo].[SummaryStatistics01]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[RowID] [int] NOT NULL,
[OrgID] [int] NOT NULL,
[InputPersonID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[GroupDate] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[VHealthCount] [bigint] NOT NULL,
[CHealthCount] [bigint] NOT NULL,
[BabyVisitCount] [bigint] NOT NULL,
[BabyHealthCount] [bigint] NOT NULL,
[Children01Count] [bigint] NOT NULL,
[Children02Count] [bigint] NOT NULL,
[Children3_6Count] [bigint] NOT NULL,
[BabyAllVisitCount] [bigint] NOT NULL,
[MaternalCount] [bigint] NOT NULL,
[FirstVistBeforeBornCount] [bigint] NOT NULL,
[VisitBeforeBornCount] [bigint] NOT NULL,
[PrenatalVisitCount] [bigint] NOT NULL,
[VisitAfterBornCount] [bigint] NOT NULL,
[VisitAfterBorn42Count] [bigint] NOT NULL,
[HypertensionVisitCount] [bigint] NOT NULL,
[HypertensionHealthCount] [bigint] NOT NULL,
[DiabetesVisitCount] [bigint] NOT NULL,
[DiabetesHealthCount] [bigint] NOT NULL,
[FuriousVisitCount] [bigint] NOT NULL,
[FuriousHealthCount] [bigint] NOT NULL,
[UserName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[OrgName] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[OptPersonID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VacciInfoCount] [bigint] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[SummaryStatistics01] ADD CONSTRAINT [PK_SummaryStatistics01] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
