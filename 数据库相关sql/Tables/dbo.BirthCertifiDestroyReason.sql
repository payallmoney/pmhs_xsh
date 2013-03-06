CREATE TABLE [dbo].[BirthCertifiDestroyReason]
(
[ID] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[CertifiId] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[type] [int] NOT NULL,
[ReasonRemarks] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[ReasonDate] [datetime] NULL,
[InputPersonId] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[OtherDestroyReason] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BirthCertifiDestroyReason] ADD CONSTRAINT [PK_BirthCertifiDestroyReason] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
