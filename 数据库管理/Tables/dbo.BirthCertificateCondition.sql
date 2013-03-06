CREATE TABLE [dbo].[BirthCertificateCondition]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[startCondition] [bigint] NOT NULL,
[endCondition] [bigint] NOT NULL,
[RemarkId] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BirthCertificateCondition] ADD CONSTRAINT [PK_BirthCertificateCondition] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
