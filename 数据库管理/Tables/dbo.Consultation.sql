CREATE TABLE [dbo].[Consultation]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Date] [datetime] NOT NULL,
[Reason] [text] COLLATE Chinese_PRC_CI_AS NULL,
[Notion] [text] COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL,
[ExecDistrictNum] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[Consultation] ADD CONSTRAINT [pk_Consultation] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
