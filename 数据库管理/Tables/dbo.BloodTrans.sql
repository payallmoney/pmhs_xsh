CREATE TABLE [dbo].[BloodTrans]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Reason] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[TransDate] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BloodTrans] ADD CONSTRAINT [pk_BloodTrans] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
