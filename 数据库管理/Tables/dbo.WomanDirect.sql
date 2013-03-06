CREATE TABLE [dbo].[WomanDirect]
(
[ID] [int] NOT NULL IDENTITY(1, 1),
[Remarks] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Type] [int] NOT NULL,
[Property] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Age] [nvarchar] (200) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[WomanDirect] ADD CONSTRAINT [PK_WomanDirect] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
