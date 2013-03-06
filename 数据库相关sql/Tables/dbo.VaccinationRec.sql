CREATE TABLE [dbo].[VaccinationRec]
(
[VaccinationID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VaccineInfoID] [int] NOT NULL,
[Order_] [int] NOT NULL,
[MakeDate] [datetime] NULL,
[Position] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Batch] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[UsefulLife] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Producer] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Description] [nvarchar] (256) COLLATE Chinese_PRC_CI_AS NULL,
[Recorder] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[PatchStatus] [varchar] (2) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccinationRec] ADD CONSTRAINT [pk_VaccinationRec] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
