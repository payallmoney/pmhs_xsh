CREATE TABLE [dbo].[HomeInfoKey]
(
[DistrictNumber] [varchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[MaxKey] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HomeInfoKey] ADD CONSTRAINT [PK_HOMEINFOKEY] PRIMARY KEY CLUSTERED  ([DistrictNumber]) ON [PRIMARY]
GO
