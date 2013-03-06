CREATE TABLE [dbo].[Producers]
(
[ID] [int] NOT NULL,
[CompanyID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[CompanyAbbreviation] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[CompanyName] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NOT NULL,
[IsEnabled] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Producers] ADD CONSTRAINT [PK_Producers] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
