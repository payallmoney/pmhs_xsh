CREATE TABLE [dbo].[VaccineImmuneTaboo]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VaccineImmuneID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VaccineImmuneTabooID] [int] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccineImmuneTaboo] ADD CONSTRAINT [PK__VaccineI__3214EC270FD74C44] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
