CREATE TABLE [dbo].[BarCodeHistory]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DistriNo] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Years] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[MinVal] [int] NULL,
[MaxVal] [int] NULL,
[OtherVal] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[InputPersonID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[Type] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BarCodeHistory] ADD CONSTRAINT [PK_BarCodeHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
