CREATE TABLE [dbo].[BeforeBornCheckDirect]
(
[FirstVistBeforeBornID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[BeforeBornCheckDirectID] [int] NOT NULL CONSTRAINT [DF__BeforeBor__Befor__2FEF161B] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BeforeBornCheckDirect] ADD CONSTRAINT [pk_BeforeBornCheckDirect] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
