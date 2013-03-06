CREATE TABLE [dbo].[VaccineIntoOrOut]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VaccineImmuneID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NULL,
[FileNo] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[IntoDateTime] [datetime] NULL,
[OutDateTime] [datetime] NULL,
[Reason] [nvarchar] (500) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccineIntoOrOut] ADD CONSTRAINT [PK__VaccineI__3214EC2712B3B8EF] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IDX_VaccineIntoOrOut] ON [dbo].[VaccineIntoOrOut] ([FileNo]) ON [PRIMARY]
GO
