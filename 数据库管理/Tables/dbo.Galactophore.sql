CREATE TABLE [dbo].[Galactophore]
(
[MedicalExamID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[GalactophoreID] [int] NOT NULL CONSTRAINT [DF__Galactoph__Galac__74CE504D] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Galactophore] ADD CONSTRAINT [pk_Galactophore] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
