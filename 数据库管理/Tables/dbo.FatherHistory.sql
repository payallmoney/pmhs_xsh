CREATE TABLE [dbo].[FatherHistory]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HeredityID] [int] NOT NULL CONSTRAINT [DF__FatherHis__Hered__090A5324] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FatherHistory] ADD CONSTRAINT [pk_FatherHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
