CREATE TABLE [dbo].[BrotherHistory]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HeredityID] [int] NOT NULL CONSTRAINT [DF__BrotherHi__Hered__0BE6BFCF] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BrotherHistory] ADD CONSTRAINT [pk_BrotherHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
