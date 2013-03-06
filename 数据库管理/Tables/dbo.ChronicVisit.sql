CREATE TABLE [dbo].[ChronicVisit]
(
[ID] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [varchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputPersonID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL,
[VisitDate] [datetime] NULL,
[VisitKind] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Symptom] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Compliance] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ADR] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[VisitType] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Transfer] [tinyint] NULL,
[TransReason] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[TransUnit] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[NextVistDate] [datetime] NULL,
[VisitDoctor] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[DiastolicPressure] [numeric] (10, 2) NULL,
[SystolicPressure] [numeric] (10, 2) NULL,
[Weight] [numeric] (10, 2) NULL,
[Habitus] [numeric] (10, 2) NULL,
[HeartRate] [int] NULL,
[Other] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Smoke] [int] NULL,
[Drink] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[SportTimes] [int] NULL,
[SportDuration] [int] NULL,
[Salt] [int] NULL,
[MindStatus] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Action] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[AssistantExam] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Exam01] [numeric] (10, 2) NULL,
[Exam02] [numeric] (10, 2) NULL,
[Exam03] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Exam04] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChronicVisit] ADD CONSTRAINT [PK__ChronicVisit__7C6F7215] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
