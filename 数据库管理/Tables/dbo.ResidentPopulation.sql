CREATE TABLE [dbo].[ResidentPopulation]
(
[ID] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Name] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Years] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Population_Number] [decimal] (10, 0) NOT NULL,
[Town_Number] [decimal] (10, 0) NOT NULL,
[Farm_Number] [decimal] (10, 0) NOT NULL,
[OrgID] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ResidentPopulation] ADD CONSTRAINT [PK_ResidentPopulation] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
