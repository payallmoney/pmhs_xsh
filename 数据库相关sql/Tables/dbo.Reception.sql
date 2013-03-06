CREATE TABLE [dbo].[Reception]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Date] [datetime] NOT NULL,
[SubjectiveInformation] [text] COLLATE Chinese_PRC_CI_AS NULL,
[ImpersonalInformation] [text] COLLATE Chinese_PRC_CI_AS NULL,
[Evaluation] [text] COLLATE Chinese_PRC_CI_AS NULL,
[CurePlan] [text] COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL,
[OrgID] [int] NULL,
[ExecDistrictNum] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[Reception] ADD CONSTRAINT [pk_Reception] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
