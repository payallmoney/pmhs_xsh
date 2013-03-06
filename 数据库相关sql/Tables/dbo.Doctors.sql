CREATE TABLE [dbo].[Doctors]
(
[ID] [int] NOT NULL IDENTITY(1, 1),
[Name] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Sex] [nvarchar] (2) COLLATE Chinese_PRC_CI_AS NULL,
[Birthday] [datetime] NULL,
[Tel] [nvarchar] (11) COLLATE Chinese_PRC_CI_AS NULL,
[Address] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[HospID] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Doctors] ADD CONSTRAINT [PK_Doctors] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
