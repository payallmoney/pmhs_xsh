CREATE TABLE [dbo].[DiseaseHistory]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DiseaseID] [int] NOT NULL CONSTRAINT [DF__DiseaseHi__Disea__04459E07] DEFAULT ((0)),
[Remark] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ConfirmDate] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DiseaseHistory] ADD CONSTRAINT [pk_DiseaseHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
