CREATE TABLE [dbo].[DangerControl]
(
[MedicalExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DangerControlID] [int] NOT NULL CONSTRAINT [DF__DangerCon__Dange__1DB06A4F] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DangerControl] ADD CONSTRAINT [pk_EyeSick] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
