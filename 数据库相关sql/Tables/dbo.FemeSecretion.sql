CREATE TABLE [dbo].[FemeSecretion]
(
[FirstVistBeforeBornID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[FemeSecretionID] [int] NOT NULL CONSTRAINT [DF__FemeSecre__FemeS__1E6F845E] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FemeSecretion] ADD CONSTRAINT [pk_FemeSecretion] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
