CREATE TABLE [dbo].[BirthCertificateOrg]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[OrgID] [int] NULL,
[CertificateID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[DistriDate] [datetime] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BirthCertificateOrg] ADD CONSTRAINT [PK_BirthCertificateOrg] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
