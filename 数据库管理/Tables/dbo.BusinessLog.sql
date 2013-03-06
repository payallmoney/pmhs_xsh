CREATE TABLE [dbo].[BusinessLog]
(
[ID] [int] NOT NULL IDENTITY(1, 1),
[BusinessID] [int] NOT NULL CONSTRAINT [DF__BusinessL__Busin__412EB0B6] DEFAULT ((0)),
[Person] [int] NOT NULL CONSTRAINT [DF__BusinessL__Perso__4222D4EF] DEFAULT ((0)),
[Date] [datetime] NULL,
[BusinessKey] [int] NOT NULL CONSTRAINT [DF__BusinessL__Busin__4316F928] DEFAULT ((0)),
[BusinessParentKey] [int] NOT NULL CONSTRAINT [DF__BusinessL__Busin__440B1D61] DEFAULT ((0)),
[Memo] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BusinessLog] ADD CONSTRAINT [PK__BusinessLog__403A8C7D] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
