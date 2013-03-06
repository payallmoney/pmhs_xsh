CREATE TABLE [dbo].[HospitalProp]
(
[ID] [int] NOT NULL,
[Name] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Related_Name] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HospitalProp] ADD CONSTRAINT [PK_HospitalProp] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
