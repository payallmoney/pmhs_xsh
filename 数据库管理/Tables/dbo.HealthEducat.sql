CREATE TABLE [dbo].[HealthEducat]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[OrgID] [int] NOT NULL,
[Date] [datetime] NULL,
[Place] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Way] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Unit] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Collaborator] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[JoinPersonNum] [int] NULL CONSTRAINT [DF__HealthEdu__JoinP__5090EFD7] DEFAULT ((0)),
[PublicizeInfo] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Subject] [nvarchar] (300) COLLATE Chinese_PRC_CI_AS NULL,
[Propagator] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Conclude] [nvarchar] (300) COLLATE Chinese_PRC_CI_AS NULL,
[Judge] [nvarchar] (300) COLLATE Chinese_PRC_CI_AS NULL,
[Principal] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[WriteDate] [datetime] NULL,
[InputPersonID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL,
[Catagory] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Content] [nvarchar] (500) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HealthEducat] ADD CONSTRAINT [pk_HealthEducat] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
