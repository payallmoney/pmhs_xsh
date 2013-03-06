CREATE TABLE [dbo].[HeartSick]
(
[MedicalExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HeartSickID] [int] NOT NULL CONSTRAINT [DF__HeartSick__Heart__151B244E] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HeartSick] ADD CONSTRAINT [pk_HeartSick] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
