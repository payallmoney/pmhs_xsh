CREATE TABLE [dbo].[VaccinateHistory]
(
[MedicalExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[BacterinName] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VDate] [datetime] NULL,
[Hostpital] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccinateHistory] ADD CONSTRAINT [pk_VaccinateHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
