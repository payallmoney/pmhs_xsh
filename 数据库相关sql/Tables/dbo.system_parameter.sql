CREATE TABLE [dbo].[system_parameter]
(
[id] [varchar] (50) COLLATE Chinese_PRC_CI_AS NOT NULL,
[value] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[descr] [varchar] (200) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[system_parameter] ADD CONSTRAINT [PK__system_parameter__63D8CE75] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
