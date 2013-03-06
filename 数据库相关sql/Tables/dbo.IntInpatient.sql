CREATE TABLE [dbo].[IntInpatient]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DirectID] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DistrictNumber] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HospitalName] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[InKey] [varchar] (36) COLLATE Chinese_PRC_CI_AS NULL,
[InType] [smallint] NOT NULL CONSTRAINT [DF__IntInpati__InTyp__469D7149] DEFAULT ((0)),
[State] [smallint] NOT NULL CONSTRAINT [DF__IntInpati__State__47919582] DEFAULT ((0)),
[InDate] [datetime] NULL,
[OutDate] [datetime] NULL,
[Section] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Doctor] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Nurse] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[EMRNsoFileNo] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[EMRPdfFileNo] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[Diagnosis] [varchar] (200) COLLATE Chinese_PRC_CI_AS NULL,
[Signs] [varchar] (200) COLLATE Chinese_PRC_CI_AS NULL,
[GatherType] [smallint] NOT NULL CONSTRAINT [DF__IntInpati__Gathe__4885B9BB] DEFAULT ((0)),
[AllMoney] [numeric] (18, 5) NOT NULL CONSTRAINT [DF__IntInpati__AllMo__4979DDF4] DEFAULT ((0)),
[WipeMoney] [numeric] (18, 5) NOT NULL CONSTRAINT [DF__IntInpati__WipeM__4A6E022D] DEFAULT ((0)),
[FileNo] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[MakeDate] [datetime] NULL,
[MakePerson] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[EmrFilePDF] [image] NULL,
[EmrFileNso] [image] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[IntInpatient] ADD CONSTRAINT [pk_IntInpatient] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
