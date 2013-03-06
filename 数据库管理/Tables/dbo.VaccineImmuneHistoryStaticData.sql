CREATE TABLE [dbo].[VaccineImmuneHistoryStaticData]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[BirthDay] [datetime] NULL,
[LimitDate] [datetime] NULL,
[VaccinationDate] [datetime] NULL,
[VaccinationName] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VaccinationNum] [int] NULL,
[ColNum] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[RowNum] [int] NULL,
[InputPersonID] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[State] [smallint] NULL CONSTRAINT [DF__VaccineIm__State__290D0E62] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccineImmuneHistoryStaticData] ADD CONSTRAINT [pk_VaccineImmuneHistoryStaticData] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
