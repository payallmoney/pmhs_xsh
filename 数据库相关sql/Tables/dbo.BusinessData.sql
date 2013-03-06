CREATE TABLE [dbo].[BusinessData]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[BusinessID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Items] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Date] [datetime] NOT NULL,
[Type] [int] NOT NULL,
[InputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BusinessData] ADD CONSTRAINT [PK__Business__3214EC2725FB978D] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
