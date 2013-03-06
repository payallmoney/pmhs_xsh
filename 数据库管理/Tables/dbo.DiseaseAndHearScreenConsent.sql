CREATE TABLE [dbo].[DiseaseAndHearScreenConsent]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[PregnantWomanName] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[HospitalNumber] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Signature] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[LinkTel] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[AgreeTime] [datetime] NULL,
[InputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[ExecDistrictNum] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DiseaseAndHearScreenConsent] ADD CONSTRAINT [PK__DiseaseA__3214EC274E0988E7] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
