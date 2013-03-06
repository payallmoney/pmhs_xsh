CREATE TABLE [dbo].[ChildrenException]
(
[HealthFileChildrenID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ChildrenExceptionID] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChildrenException] ADD CONSTRAINT [PK__Children__3214EC27316D4A39] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
