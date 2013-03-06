CREATE TABLE [dbo].[Vaccination]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FileNo] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputPersonID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[InputDate] [datetime] NOT NULL,
[Name] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Sex] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[Birthday] [datetime] NULL,
[Guardian] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Relation] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[TEL] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[Address] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[RegisterAddress] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InDate] [datetime] NULL,
[OutDate] [datetime] NULL,
[OutReason] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[AbnormalHistory] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Taboo] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[InfectiousHistory] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[BuildDate] [datetime] NULL,
[Doctor] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[ExecDistrictNum] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Vaccination] ADD CONSTRAINT [pk_Vaccination] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
