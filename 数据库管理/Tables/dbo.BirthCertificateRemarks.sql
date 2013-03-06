CREATE TABLE [dbo].[BirthCertificateRemarks]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Remarks] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[type] [int] NOT NULL,
[otherCondition] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[Prex] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[OrgID] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BirthCertificateRemarks] ADD CONSTRAINT [PK_BirthCertificateRemarks] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
