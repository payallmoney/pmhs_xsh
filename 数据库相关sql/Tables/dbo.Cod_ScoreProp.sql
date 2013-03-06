CREATE TABLE [dbo].[Cod_ScoreProp]
(
[name] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[prop] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[propname] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[propother] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Cod_ScoreProp] ADD CONSTRAINT [PK__Cod_Scor__3687519E3CDEFCE5] PRIMARY KEY CLUSTERED  ([name], [prop]) ON [PRIMARY]
GO
