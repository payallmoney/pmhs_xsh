CREATE TABLE [dbo].[ChildrenHeightWeight]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Sex] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Height] [numeric] (10, 2) NOT NULL,
[S] [numeric] (10, 2) NOT NULL,
[Sub2S] [numeric] (10, 2) NOT NULL,
[Sub1S] [numeric] (10, 2) NOT NULL,
[AVG] [numeric] (10, 2) NOT NULL,
[Add1S] [numeric] (10, 2) NOT NULL,
[Add2S] [numeric] (10, 2) NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChildrenHeightWeight] ADD CONSTRAINT [PK_ChildrenHeightWeight] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
