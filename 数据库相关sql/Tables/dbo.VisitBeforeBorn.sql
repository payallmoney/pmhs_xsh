CREATE TABLE [dbo].[VisitBeforeBorn]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL,
[Item] [int] NULL,
[VisitDate] [datetime] NULL,
[DiastolicPressure] [decimal] (10, 2) NULL,
[SystolicPressure] [decimal] (10, 2) NULL,
[Result] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[ResultOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[BeforeBornDirectOther] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Transfer] [bit] NULL,
[TransReason] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[TransUnit] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[VisitDoctor] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[NextVisitDate] [datetime] NULL,
[Weeks] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[CC] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Weight] [decimal] (10, 2) NULL,
[Exam01] [decimal] (10, 2) NULL,
[Exam02] [decimal] (10, 2) NULL,
[Exam03] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Exam04] [decimal] (10, 2) NULL,
[Exam05] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Exam06] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Exam07] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[High_Risk] [nvarchar] (2) COLLATE Chinese_PRC_CI_AS NULL,
[High_Risk_Remark] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[ExecDistrictNum] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[HighRiskSearch] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[gravidity] [int] NULL,
[Edema] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[DiagnosisRemark] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[BornBirthAddressPlan] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[Pelvis01] [int] NULL,
[Pelvis02] [int] NULL,
[Pelvis03] [int] NULL,
[Pelvis04] [int] NULL,
[ForeignId] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VisitBeforeBorn] ADD CONSTRAINT [pk_VisitBeforeBorn] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [pmhs_visitBeforeBorn] ON [dbo].[VisitBeforeBorn] ([FileNo]) ON [PRIMARY]
GO
