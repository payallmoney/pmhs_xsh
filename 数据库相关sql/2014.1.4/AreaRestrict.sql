USE [pmhs]
GO

/****** Object:  Table [dbo].[AreaRestrict]    Script Date: 01/04/2014 18:15:49 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[AreaRestrict](
	[ID] [nvarchar](32) NOT NULL,
	[SW] [nvarchar](100) NOT NULL,
	[NE] [nvarchar](100) NOT NULL,
	[CenterPoint] [nvarchar](100) NOT NULL,
	[ZoomLevel] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_AreaRestrict] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

