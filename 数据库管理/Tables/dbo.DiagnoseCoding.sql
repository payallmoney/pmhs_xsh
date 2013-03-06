CREATE TABLE [dbo].[DiagnoseCoding]
(
[ID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DiagnoseName] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DiagnoseName_Png] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ServiceType] [int] NULL,
[Number] [int] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DiagnoseCoding] ADD CONSTRAINT [PK_DiagnoseCoding] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
