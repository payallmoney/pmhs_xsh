CREATE TABLE [dbo].[IntOutpatient]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DirectID] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DistrictNumber] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HospitalName] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[OpKey] [varchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[OpType] [smallint] NOT NULL CONSTRAINT [DF__IntOutpat__OpTyp__3C1FE2D6] DEFAULT ((0)),
[State] [smallint] NOT NULL CONSTRAINT [DF__IntOutpat__State__3D14070F] DEFAULT ((0)),
[Date] [datetime] NULL,
[Section] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[EMRNsoFileNo] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[EMRPdfFileNo] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[Diagnosis] [varchar] (200) COLLATE Chinese_PRC_CI_AS NULL,
[Signs] [varchar] (200) COLLATE Chinese_PRC_CI_AS NULL,
[GatherType] [smallint] NOT NULL CONSTRAINT [DF__IntOutpat__Gathe__3E082B48] DEFAULT ((0)),
[AllMoney] [numeric] (18, 5) NOT NULL CONSTRAINT [DF__IntOutpat__AllMo__3EFC4F81] DEFAULT ((0)),
[WipeMoney] [numeric] (18, 5) NOT NULL CONSTRAINT [DF__IntOutpat__WipeM__3FF073BA] DEFAULT ((0)),
[FileNo] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[MakeDate] [datetime] NULL,
[MakePerson] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[EmrFilePDF] [image] NULL,
[EmrFileNso] [image] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[IntOutpatient] ADD CONSTRAINT [pk_IntOutpatient] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
