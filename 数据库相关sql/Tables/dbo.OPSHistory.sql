CREATE TABLE [dbo].[OPSHistory]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[OPSName] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[OPSDate] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[OPSHistory] ADD CONSTRAINT [pk_OPSHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
