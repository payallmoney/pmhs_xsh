CREATE TABLE [dbo].[ChildDeathSurveyReinstate]
(
[ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ChildrenDeathSurvey01ID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ReinstateDate] [datetime] NULL,
[ReinstateHospital] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[ReinstateSymbol] [nvarchar] (max) COLLATE Chinese_PRC_CI_AS NULL,
[ResinstateDeal] [nvarchar] (max) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[ChildDeathSurveyReinstate] ADD CONSTRAINT [PK__ChildDea__3214EC2728D80438] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
