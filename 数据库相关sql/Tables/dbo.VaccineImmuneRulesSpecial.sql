CREATE TABLE [dbo].[VaccineImmuneRulesSpecial]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ColNum] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[RowNum] [int] NULL,
[SpecialVal] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccineImmuneRulesSpecial] ADD CONSTRAINT [PK_VaccineImmuneRulesSpecial] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
