CREATE TABLE [dbo].[FinishGestation]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HealthFileMaternalID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FinishReason] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FinishDate] [datetime] NULL,
[InputPersonID] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FinishGestation] ADD CONSTRAINT [PK__FinishGe__3214EC2753C2623D] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
