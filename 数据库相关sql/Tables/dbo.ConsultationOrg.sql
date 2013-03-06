CREATE TABLE [dbo].[ConsultationOrg]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ConsultationID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[OrgName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ConsultationOrg] ADD CONSTRAINT [pk_ConsultationOrg] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
