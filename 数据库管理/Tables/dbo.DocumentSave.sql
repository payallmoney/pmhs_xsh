CREATE TABLE [dbo].[DocumentSave]
(
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HealthEducatID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DocumentID] [int] NOT NULL CONSTRAINT [DF__DocumentS__Docum__25518C17] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[DocumentSave] ADD CONSTRAINT [pk_DocumentSave] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
