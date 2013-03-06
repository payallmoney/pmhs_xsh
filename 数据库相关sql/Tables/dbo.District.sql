CREATE TABLE [dbo].[District]
(
[ID] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Name] [nvarchar] (64) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ParentID] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[Description] [nvarchar] (256) COLLATE Chinese_PRC_CI_AS NULL,
[Level] [int] NOT NULL,
[IsDetail] [bit] NOT NULL,
[Name_Png] [nvarchar] (64) COLLATE Chinese_PRC_CI_AS NULL,
[ParentName] [nvarchar] (64) COLLATE Chinese_PRC_CI_AS NULL,
[OrgID] [int] NOT NULL CONSTRAINT [DF__District__OrgID__787EE5A0] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[District] ADD CONSTRAINT [pk_District] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
