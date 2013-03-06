CREATE TABLE [dbo].[GravidityKey]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[CurrentGravidity] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[GravidityKey] ADD CONSTRAINT [PK_GravidityKey] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
