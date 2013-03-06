CREATE TABLE [dbo].[FuriousVisit]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL,
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
[VisitDate] [datetime] NULL,
[Status01] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Status02] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Status03] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Status04] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Status05] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Status06] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Status07] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Status08] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Effect1] [int] NULL,
[Effect2] [int] NULL,
[Effect3] [int] NULL,
[Effect4] [int] NULL,
[Effect5] [int] NULL,
[Examine] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[CureStatus] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[MeasureOther] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Danger] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[LockStatus] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[HospitalCourse] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[HospotalEndDate] [datetime] NULL,
[ExecDistrictNum] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FuriousVisit] ADD CONSTRAINT [pk_FuriousVisit] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [pmhs_furiousVisit] ON [dbo].[FuriousVisit] ([FileNo]) ON [PRIMARY]
GO
