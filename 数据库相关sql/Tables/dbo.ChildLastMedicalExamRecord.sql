CREATE TABLE [dbo].[ChildLastMedicalExamRecord]
(
[FileNo] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[LastExamDate] [datetime] NULL,
[HighRiskRemarks] [nvarchar] (max) COLLATE Chinese_PRC_CI_AS NULL,
[Type] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChildLastMedicalExamRecord] ADD CONSTRAINT [PK_ChildLastMedicalExamRecord] PRIMARY KEY CLUSTERED  ([FileNo]) ON [PRIMARY]
GO
