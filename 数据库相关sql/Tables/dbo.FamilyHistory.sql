CREATE TABLE [dbo].[FamilyHistory]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HeredityID] [int] NOT NULL CONSTRAINT [DF__FamilyHis__Hered__0169315C] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FamilyHistory] ADD CONSTRAINT [pk_FamilyHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
