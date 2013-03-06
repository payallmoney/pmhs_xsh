CREATE TABLE [dbo].[ContraceptiveHistory]
(
[FirstVistBeforeBornID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ContraceptiveID] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ContraceptiveHistory] ADD CONSTRAINT [PK__Contrace__3214EC274B2D1C3C] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [fvbidx_ContraceptiveHistory] ON [dbo].[ContraceptiveHistory] ([FirstVistBeforeBornID]) ON [PRIMARY]
GO
