CREATE TABLE [dbo].[DiseaseScreening]
(
[ChildBirthRecordID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DiseaseScreeningID] [int] NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DiseaseScreening] ADD CONSTRAINT [PK__DiseaseS__3214EC2750E5F592] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
