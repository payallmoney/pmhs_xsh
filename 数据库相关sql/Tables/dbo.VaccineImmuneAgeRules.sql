CREATE TABLE [dbo].[VaccineImmuneAgeRules]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[RowNumber] [int] NOT NULL,
[Age] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Type] [nchar] (10) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccineImmuneAgeRules] ADD CONSTRAINT [PK_VaccineImmuneAgeRules] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
