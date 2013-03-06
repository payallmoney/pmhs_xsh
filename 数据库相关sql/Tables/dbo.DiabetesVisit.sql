CREATE TABLE [dbo].[DiabetesVisit]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL,
[VisitDate] [datetime] NOT NULL,
[VisitKind] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[SymptomOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Compliance] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ADR] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ADROther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[VisitType] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Transfer] [bit] NULL,
[TransReason] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[TransUnit] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[NextVistDate] [datetime] NULL,
[VisitDoctor] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[DiastolicPressure] [decimal] (10, 2) NULL,
[SystolicPressure] [decimal] (10, 2) NULL,
[Weight] [decimal] (10, 2) NULL,
[Habitus] [decimal] (10, 2) NULL,
[HeartRate] [int] NULL,
[Other] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Smoke] [int] NULL,
[Drink] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[SportTimes] [int] NULL,
[SportDuration] [int] NULL,
[Food] [int] NULL,
[Salt] [decimal] (10, 2) NULL,
[MindStatus] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Action] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[AssistantExam] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Exam01] [decimal] (10, 2) NULL,
[Exam02] [decimal] (10, 2) NULL,
[Exam03] [datetime] NULL,
[Exam04] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Exam05] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Exam06] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[ExecDistrictNum] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DiabetesVisit] ADD CONSTRAINT [pk_DiabetesVisit] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [pmhs_diabetesVisit] ON [dbo].[DiabetesVisit] ([FileNo]) ON [PRIMARY]
GO
