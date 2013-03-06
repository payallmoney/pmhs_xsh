CREATE TABLE [dbo].[AllergiesHistory]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[AllergiesID] [int] NOT NULL CONSTRAINT [DF__Allergies__Aller__77AABCF8] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[AllergiesHistory] ADD CONSTRAINT [pk_AllergiesHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
