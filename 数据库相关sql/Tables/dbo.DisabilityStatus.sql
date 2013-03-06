CREATE TABLE [dbo].[DisabilityStatus]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DisabilityStatusID] [int] NOT NULL CONSTRAINT [DF__Disabilit__Disab__74AE54BC] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DisabilityStatus] ADD CONSTRAINT [pk_DisabilityStatus] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
