CREATE TABLE [dbo].[ChildrenStandardHeight]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Sex] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[AgeGroup] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[MinMonth] [int] NOT NULL,
[MaxMonth] [int] NOT NULL,
[S] [decimal] (10, 2) NOT NULL,
[Sub2S] [decimal] (10, 2) NOT NULL,
[Sub1S] [decimal] (10, 2) NOT NULL,
[AVG] [decimal] (10, 2) NOT NULL,
[Add1S] [decimal] (10, 2) NOT NULL,
[Add2S] [decimal] (10, 2) NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChildrenStandardHeight] ADD CONSTRAINT [PK_ChildrenStandardHeight] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
