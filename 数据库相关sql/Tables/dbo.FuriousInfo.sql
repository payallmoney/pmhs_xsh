CREATE TABLE [dbo].[FuriousInfo]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL,
[Name] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[GuardianName] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Relation] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[GuardianAddress] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[GuardianTEL] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Contact] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[FirstOccur] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[SymptomOther] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Outpatient] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[InpatientTimes] [int] NOT NULL,
[RecentDiagnose] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Hospital] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[ConfirmDate] [datetime] NOT NULL,
[RecentCure] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[Effect1] [int] NULL,
[Effect2] [int] NULL,
[Effect3] [int] NULL,
[Effect4] [int] NULL,
[Effect5] [int] NULL,
[LockStatus] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[agreeSign] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[agreeSignDate] [datetime] NULL,
[agree] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[outpatientDate] [datetime] NULL,
[incomeStatus] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[doctorAdvice] [nvarchar] (500) COLLATE Chinese_PRC_CI_AS NULL,
[ExecDistrictNum] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FuriousInfo] ADD CONSTRAINT [pk_FuriousInfo] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
