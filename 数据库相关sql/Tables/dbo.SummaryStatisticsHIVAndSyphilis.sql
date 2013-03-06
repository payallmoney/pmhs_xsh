CREATE TABLE [dbo].[SummaryStatisticsHIVAndSyphilis]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[RowID] [int] NOT NULL,
[InputPersonID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HIVNegative] [bigint] NOT NULL,
[HIVMasculine] [bigint] NOT NULL,
[HIVOthers] [bigint] NOT NULL,
[SyphilisNegative] [bigint] NOT NULL,
[SyphilisMasculine] [bigint] NOT NULL,
[SyphilisOthers] [bigint] NOT NULL,
[OrgName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[UserName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[GroupDate] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[OptPersonID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[SummaryStatisticsHIVAndSyphilis] ADD CONSTRAINT [PK_SummaryStatisticsHIVAndSyphilis] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
