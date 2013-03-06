CREATE TABLE [dbo].[Test]
(
[ID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Name] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ParentID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Level] [int] NULL,
[IsDetail] [int] NULL,
[Name_Png] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ParentName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[OrgID] [int] NULL
) ON [PRIMARY]
GO
