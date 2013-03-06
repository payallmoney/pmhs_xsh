CREATE TABLE [dbo].[HarnsSick]
(
[MedicalExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HarnsSickID] [int] NOT NULL CONSTRAINT [DF__HarnsSick__Harns__32AB8735] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[HarnsSick] ADD CONSTRAINT [pk_HarnsSick] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
