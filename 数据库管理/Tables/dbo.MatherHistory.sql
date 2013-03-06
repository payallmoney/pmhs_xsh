CREATE TABLE [dbo].[MatherHistory]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HeredityID] [int] NOT NULL CONSTRAINT [DF__MatherHis__Hered__0EC32C7A] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[MatherHistory] ADD CONSTRAINT [pk_MatherHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
