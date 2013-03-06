CREATE TABLE [dbo].[SummaryStatisticsBirthCertifi]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[RowID] [int] NOT NULL,
[OrgName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Username] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[GroupDate] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[UnUsedCount] [bigint] NOT NULL,
[UsedCount] [bigint] NOT NULL,
[DestroyCount] [bigint] NOT NULL,
[ArchiveCount] [bigint] NOT NULL,
[InputPersonID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[SummaryStatisticsBirthCertifi] ADD CONSTRAINT [PK__SummaryS__3214EC2707420643] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
