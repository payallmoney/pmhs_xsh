CREATE TABLE [dbo].[VaccineImmune]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VFileNo] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VInputPersonID] [nvarchar] (30) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VInputDate] [datetime] NOT NULL,
[VName] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VSex] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VBirthday] [datetime] NULL,
[VFatherName] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[VFatherWorkUnit] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VFatherPhone] [nvarchar] (11) COLLATE Chinese_PRC_CI_AS NULL,
[VMotherName] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[VMotherWorkUnit] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[VMotherPhone] [nvarchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[VFamilyAddress] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VCensusAddressCounty] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VCensusAddressTown] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VCensusAddressVillage] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[VBuildCardDate] [datetime] NULL,
[VBuildCardPerson] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[VMovedDate] [datetime] NULL,
[VMovedAddress] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[VVacciUnit] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[VCertifiUnit] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[VWeight] [decimal] (10, 2) NULL,
[ExecDistrictNum] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[Taboo] [nvarchar] (max) COLLATE Chinese_PRC_CI_AS NULL,
[InfectiousHistory] [nvarchar] (max) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccineImmune] ADD CONSTRAINT [PK_VaccineImmune] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
