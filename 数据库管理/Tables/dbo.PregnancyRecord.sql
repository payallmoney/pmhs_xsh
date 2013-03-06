CREATE TABLE [dbo].[PregnancyRecord]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HealthFileMaternalID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[RecordDate] [datetime] NULL,
[Record] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[DealOpinion] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[PregnancyRecord] ADD CONSTRAINT [PK__Pregnanc__3214EC2764ECEE3F] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
