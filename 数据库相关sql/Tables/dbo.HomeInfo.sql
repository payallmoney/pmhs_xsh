CREATE TABLE [dbo].[HomeInfo]
(
[HomeID] [varchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Household] [varchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[PersonCount] [int] NOT NULL,
[Address] [varchar] (200) COLLATE Chinese_PRC_CI_AS NULL,
[Phone] [varchar] (11) COLLATE Chinese_PRC_CI_AS NULL,
[Township] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[Village] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[BuildUnit] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[BuildPersonID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[BuildDate] [datetime] NULL,
[ResidenceAddress] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[districtNumber] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[UpdateDate] [datetime] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HomeInfo] ADD CONSTRAINT [PK_HOMEINFO] PRIMARY KEY CLUSTERED  ([HomeID]) ON [PRIMARY]
GO
