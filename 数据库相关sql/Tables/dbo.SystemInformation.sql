CREATE TABLE [dbo].[SystemInformation]
(
[ID] [int] NOT NULL IDENTITY(1, 1),
[Val] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NOT NULL,
[Remarks] [nvarchar] (500) COLLATE Chinese_PRC_CI_AS NULL,
[InputDate] [datetime] NULL,
[InputPersonID] [nvarchar] (50) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[SystemInformation] ADD CONSTRAINT [PK_SystemInformation] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
