CREATE TABLE [dbo].[MontherException]
(
[HealthFileChildrenID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[MontherExceptionID] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[MontherException] ADD CONSTRAINT [PK__MontherE__3214EC275F3414E9] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
