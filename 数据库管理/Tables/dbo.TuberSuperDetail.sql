CREATE TABLE [dbo].[TuberSuperDetail]
(
[ID] [int] NOT NULL IDENTITY(1, 1),
[TimeForSupervise] [datetime] NULL,
[JudgmentForSupervise] [varchar] (5000) COLLATE Chinese_PRC_CI_AS NOT NULL,
[NoForSpecies] [int] NULL,
[LoseTime] [int] NULL,
[LoseReason] [varchar] (500) COLLATE Chinese_PRC_CI_AS NULL,
[FillTime] [int] NULL,
[StopTime] [int] NULL,
[VisitPerson] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[BaseID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[TuberSuperDetail] ADD CONSTRAINT [PK_TUBERSUPERDETAIL] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
