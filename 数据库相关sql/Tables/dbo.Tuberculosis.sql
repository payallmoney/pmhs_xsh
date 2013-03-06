CREATE TABLE [dbo].[Tuberculosis]
(
[ID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[name] [varchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[sex] [varchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[age] [int] NOT NULL,
[address] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[TypeForSick] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[TimeForStart] [datetime] NOT NULL,
[RegNo] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[CaseHisNo] [varchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[FileNo] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[districtNumber] [varchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Tuberculosis] ADD CONSTRAINT [PK_TUBERCULOSIS] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
