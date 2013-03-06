CREATE TABLE [dbo].[VaccineImmuneInfectious]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VaccineImmuneID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VaccineImmuneInfectiousID] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccineImmuneInfectious] ADD CONSTRAINT [PK__VaccineI__3214EC270CFADF99] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
