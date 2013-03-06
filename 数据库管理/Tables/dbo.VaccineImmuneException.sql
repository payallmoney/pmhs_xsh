CREATE TABLE [dbo].[VaccineImmuneException]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[VaccineImmuneID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NULL,
[VaccineImmuneExceptionID] [int] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[VaccineImmuneException] ADD CONSTRAINT [PK__VaccineI__3214EC270A1E72EE] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
