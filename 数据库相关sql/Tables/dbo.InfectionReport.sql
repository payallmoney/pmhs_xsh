CREATE TABLE [dbo].[InfectionReport]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[OrgID] [int] NOT NULL,
[ReportID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Date] [datetime] NULL,
[ReportType] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Name] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[GenearchName] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[CardID] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Sex] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[Birthday] [datetime] NULL,
[Age] [int] NULL,
[AgeUnit] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[Collaborator] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Unit] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Phone] [nvarchar] (300) COLLATE Chinese_PRC_CI_AS NULL,
[Area] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Address] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[Occupation] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[SickKind] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[BeginDate] [datetime] NULL,
[CureDate] [datetime] NULL,
[DieDate] [datetime] NULL,
[FirstInfection] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[SecondInfection] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[ThirdInfection] [nvarchar] (80) COLLATE Chinese_PRC_CI_AS NULL,
[OtherInfection] [nvarchar] (80) COLLATE Chinese_PRC_CI_AS NULL,
[ConfirmInfection] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Reason] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[LinkPhone] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[ReportPerson] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Remark] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[InfectionReport] ADD CONSTRAINT [pk_InfectionReport] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
