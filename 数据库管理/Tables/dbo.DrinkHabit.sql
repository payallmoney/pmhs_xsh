CREATE TABLE [dbo].[DrinkHabit]
(
[MedicalExamID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DrinkHabitID] [int] NOT NULL CONSTRAINT [DF__DrinkHabi__Drink__123EB7A3] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DrinkHabit] ADD CONSTRAINT [pk_DrinkHabit] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
