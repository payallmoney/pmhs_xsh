CREATE TABLE [dbo].[VaccineImmuneInfo]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ColNum] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NULL,
[RowNumber] [int] NULL,
[Number] [int] NULL,
[FileNo] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VaccinationName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[VaccinationDate] [datetime] NULL,
[VaccinationPosition] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[ImmuneBatchNum] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[VaccinationDoctor] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[ImmuneDose] [int] NULL,
[VaccinationWay] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[Remarks] [nvarchar] (500) COLLATE Chinese_PRC_CI_AS NULL,
[IsPlan] [int] NULL,
[VacciAddress] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VacciDose] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[CompanyId] [int] NULL,
[IsPrint] [int] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccineImmuneInfo] ADD CONSTRAINT [PK_VaccineImmuneInfo] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
