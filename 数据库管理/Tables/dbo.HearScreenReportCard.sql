CREATE TABLE [dbo].[HearScreenReportCard]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ExamUnit] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[SerialNumber] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ParentsName] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[BabyName] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[BabySex] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[BabyBirthday] [datetime] NULL,
[ExamDate] [datetime] NULL,
[ReviewExamDate] [datetime] NULL,
[ExamWay] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[RightEar] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[LeftEar] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Suggestion] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[ExamerSign] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[ReportDate] [datetime] NULL,
[DataType] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[ExecDistrictNum] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HearScreenReportCard] ADD CONSTRAINT [PK__HearScre__3214EC275C57A83E] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
