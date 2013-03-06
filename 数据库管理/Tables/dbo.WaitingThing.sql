CREATE TABLE [dbo].[WaitingThing]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DistrictNum] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[TransPerson] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[TransDate] [datetime] NULL,
[HospitalName] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[HISID] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Status] [int] NOT NULL,
[DiagnoseID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DiagnoseName] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Type] [int] NULL,
[ServiceType] [int] NULL,
[DealDate] [datetime] NULL,
[DealPerson] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[SubID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[WaitingThing] ADD CONSTRAINT [PK_WaitingThing] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
