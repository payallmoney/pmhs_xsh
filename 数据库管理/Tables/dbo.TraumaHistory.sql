CREATE TABLE [dbo].[TraumaHistory]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[TraumaName] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[TraumaDate] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[TraumaHistory] ADD CONSTRAINT [pk_TraumaHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
