CREATE TABLE [dbo].[VaccineImmuneRules]
(
[ID] [int] NOT NULL,
[VaccineName] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VaccinationObject] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[MinMonthAge] [int] NOT NULL,
[MaxMonthAge] [int] NOT NULL,
[VaccinationPosition] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VaccinationWay] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VaccinationDose] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[MinTimeInterval] [int] NULL,
[MaxTimeInterval] [int] NULL,
[Remarks] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[Type] [int] NOT NULL,
[Number] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[DependID] [int] NULL,
[RowNumber] [int] NULL,
[Rules] [int] NULL,
[IsSpecial] [int] NOT NULL CONSTRAINT [DF__VaccineIm__IsSpe__2FBA0BF1] DEFAULT ((0)),
[VaccineRemark] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[MonthLimit] [int] NULL,
[MonthStart] [int] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccineImmuneRules] ADD CONSTRAINT [PK_VaccineImmuneRules] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
