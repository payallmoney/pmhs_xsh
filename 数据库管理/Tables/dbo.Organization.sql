CREATE TABLE [dbo].[Organization]
(
[ID] [int] NOT NULL IDENTITY(1, 1),
[Name] [nvarchar] (64) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Address] [nvarchar] (128) COLLATE Chinese_PRC_CI_AS NULL,
[Postcode] [nvarchar] (6) COLLATE Chinese_PRC_CI_AS NULL,
[TelNumber] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[FaxNumber] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[Description] [nvarchar] (256) COLLATE Chinese_PRC_CI_AS NULL,
[DistrictNumber] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ParentID] [int] NOT NULL,
[Level] [int] NOT NULL,
[IsDetail] [bit] NOT NULL,
[Name_Png] [nvarchar] (64) COLLATE Chinese_PRC_CI_AS NOT NULL,
[IsOrgDepart] [int] NOT NULL CONSTRAINT [DF__Organizat__IsOrg__627A95E8] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Organization] ADD CONSTRAINT [pk_Organization] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
