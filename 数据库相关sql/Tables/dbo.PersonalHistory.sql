CREATE TABLE [dbo].[PersonalHistory]
(
[FirstVistBeforeBornID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[PersonalHistoryID] [int] NOT NULL CONSTRAINT [DF__PersonalH__Perso__2D12A970] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[PersonalHistory] ADD CONSTRAINT [pk_PersonalHistory] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
