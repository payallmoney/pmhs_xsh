CREATE TABLE [dbo].[Cod_Enc]
(
[enccode] [nvarchar] (1) COLLATE Chinese_PRC_CI_AS NOT NULL,
[encvalue] [nvarchar] (1) COLLATE Chinese_PRC_CI_AS NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Cod_Enc] ADD CONSTRAINT [PK__Cod_Enc__B2A751DD727BF387] PRIMARY KEY CLUSTERED  ([enccode]) ON [PRIMARY]
GO
