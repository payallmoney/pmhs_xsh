CREATE TABLE [dbo].[BasicInformation]
(
[ID] [int] NOT NULL,
[Number] [int] NOT NULL,
[Name] [nvarchar] (1000) COLLATE Chinese_PRC_CI_AS NULL,
[Name_Png] [nvarchar] (64) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Type] [smallint] NOT NULL CONSTRAINT [DF__BasicInfor__Type__3AC1AA49] DEFAULT ((0)),
[State] [smallint] NOT NULL CONSTRAINT [DF__BasicInfo__State__3BB5CE82] DEFAULT ((0)),
[IsMain] [bit] NOT NULL,
[IsBeforehand] [bit] NOT NULL,
[IsInputValue] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL CONSTRAINT [DF__BasicInfo__IsInp__3CA9F2BB] DEFAULT ((0)),
[PrintPage] [int] NOT NULL CONSTRAINT [DF__BasicInfo__Print__3D9E16F4] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BasicInformation] ADD CONSTRAINT [PK__BasicInf__3214EC273F865F66] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
