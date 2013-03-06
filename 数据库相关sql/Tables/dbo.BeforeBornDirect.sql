CREATE TABLE [dbo].[BeforeBornDirect]
(
[VisitBeforeBornID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[BeforeBornDirectID] [int] NOT NULL CONSTRAINT [DF__BeforeBor__Befor__60C757A0] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BeforeBornDirect] ADD CONSTRAINT [pk_BeforeBornDirect] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
