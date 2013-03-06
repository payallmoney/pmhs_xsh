CREATE TABLE [dbo].[Hospitalization]
(
[MedicalExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Type] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[BeginDate] [datetime] NULL,
[EndDate] [datetime] NULL,
[Reason] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Hospital] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ReportNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Hospitalization] ADD CONSTRAINT [pk_Hospitalization] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
