CREATE TABLE [dbo].[ExposeHistory]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ExposeID] [int] NOT NULL CONSTRAINT [DF__ExposeHis__Expos__2A363CC5] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ExposeHistory] ADD CONSTRAINT [pk_ExposeHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
