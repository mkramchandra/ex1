USE [SurveyDb]
GO
/****** Object:  Table [dbo].[SurveyAcceptance]    Script Date: 01/08/2015 15:03:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SurveyAcceptance](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[RCK] [varchar](250) NULL,
	[RCD] [varchar](250) NULL,
	[ANI] [varchar](250) NULL,
	[DNIS] [varchar](250) NULL,
	[CustomerID] [varchar](250) NULL,
	[Accepted] [bit] NULL,
	[Created] [datetime] NULL,
 CONSTRAINT [PK_SurveyAcceptance] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Survey]    Script Date: 01/08/2015 15:03:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Survey](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](250) NOT NULL,
	[Description] [varchar](250) NULL,
	[Active] [bit] NOT NULL,
	[StartDate] [datetime] NULL,
	[EndDate] [datetime] NULL,
 CONSTRAINT [PK_Survey] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Question]    Script Date: 01/08/2015 15:03:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Question](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Prompt] [varchar](250) NULL,
	[Type] [varchar](250) NULL,
 CONSTRAINT [PK_Question] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SurveyResponse]    Script Date: 01/08/2015 15:03:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SurveyResponse](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[SurveyID] [int] NOT NULL,
	[SurveyAcceptanceID] [int] NOT NULL,
	[QuestionID] [int] NOT NULL,
	[Response] [varchar](250) NULL,
 CONSTRAINT [PK_SurveyResponse] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SurveyQuestion]    Script Date: 01/08/2015 15:03:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SurveyQuestion](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[SurveyID] [int] NOT NULL,
	[QuestionID] [int] NOT NULL,
	[Sequence] [int] NOT NULL,
 CONSTRAINT [PK_SurveyQuestion] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SurveyDNIS]    Script Date: 01/08/2015 15:03:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SurveyDNIS](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[SurveyID] [int] NOT NULL,
	[DNIS] [varchar](50) NOT NULL,
 CONSTRAINT [PK_SurveyDNIS] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SurveyCriteria]    Script Date: 01/08/2015 15:03:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SurveyCriteria](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[SurveyID] [int] NOT NULL,
	[Criteria] [varchar](50) NOT NULL,
 CONSTRAINT [PK_SurveyCriteria] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SurveyANI]    Script Date: 01/08/2015 15:03:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SurveyANI](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[SurveyID] [int] NOT NULL,
	[ANI] [varchar](50) NOT NULL,
 CONSTRAINT [PK_SurveyANI] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  ForeignKey [FK_SurveyANI_Survey]    Script Date: 01/08/2015 15:03:00 ******/
ALTER TABLE [dbo].[SurveyANI]  WITH CHECK ADD  CONSTRAINT [FK_SurveyANI_Survey] FOREIGN KEY([SurveyID])
REFERENCES [dbo].[Survey] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SurveyANI] CHECK CONSTRAINT [FK_SurveyANI_Survey]
GO
/****** Object:  ForeignKey [FK_SurveyCriteria_Survey]    Script Date: 01/08/2015 15:03:00 ******/
ALTER TABLE [dbo].[SurveyCriteria]  WITH CHECK ADD  CONSTRAINT [FK_SurveyCriteria_Survey] FOREIGN KEY([SurveyID])
REFERENCES [dbo].[Survey] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SurveyCriteria] CHECK CONSTRAINT [FK_SurveyCriteria_Survey]
GO
/****** Object:  ForeignKey [FK_SurveyDNIS_Survey]    Script Date: 01/08/2015 15:03:00 ******/
ALTER TABLE [dbo].[SurveyDNIS]  WITH CHECK ADD  CONSTRAINT [FK_SurveyDNIS_Survey] FOREIGN KEY([SurveyID])
REFERENCES [dbo].[Survey] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SurveyDNIS] CHECK CONSTRAINT [FK_SurveyDNIS_Survey]
GO
/****** Object:  ForeignKey [FK_SurveyQuestion_QuestionID_Question_ID]    Script Date: 01/08/2015 15:03:00 ******/
ALTER TABLE [dbo].[SurveyQuestion]  WITH CHECK ADD  CONSTRAINT [FK_SurveyQuestion_QuestionID_Question_ID] FOREIGN KEY([QuestionID])
REFERENCES [dbo].[Question] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SurveyQuestion] CHECK CONSTRAINT [FK_SurveyQuestion_QuestionID_Question_ID]
GO
/****** Object:  ForeignKey [FK_SurveyQuestion_Survey]    Script Date: 01/08/2015 15:03:00 ******/
ALTER TABLE [dbo].[SurveyQuestion]  WITH CHECK ADD  CONSTRAINT [FK_SurveyQuestion_Survey] FOREIGN KEY([SurveyID])
REFERENCES [dbo].[Survey] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SurveyQuestion] CHECK CONSTRAINT [FK_SurveyQuestion_Survey]
GO
/****** Object:  ForeignKey [FK_SurveyResponse_QuestionID_Question_ID]    Script Date: 01/08/2015 15:03:00 ******/
ALTER TABLE [dbo].[SurveyResponse]  WITH CHECK ADD  CONSTRAINT [FK_SurveyResponse_QuestionID_Question_ID] FOREIGN KEY([QuestionID])
REFERENCES [dbo].[Question] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SurveyResponse] CHECK CONSTRAINT [FK_SurveyResponse_QuestionID_Question_ID]
GO
/****** Object:  ForeignKey [FK_SurveyResponse_SurveyAcceptance]    Script Date: 01/08/2015 15:03:00 ******/
ALTER TABLE [dbo].[SurveyResponse]  WITH CHECK ADD  CONSTRAINT [FK_SurveyResponse_SurveyAcceptance] FOREIGN KEY([SurveyAcceptanceID])
REFERENCES [dbo].[SurveyAcceptance] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SurveyResponse] CHECK CONSTRAINT [FK_SurveyResponse_SurveyAcceptance]
GO
/****** Object:  ForeignKey [FK_SurveyResponse_SurveyID_Survey_ID]    Script Date: 01/08/2015 15:03:00 ******/
ALTER TABLE [dbo].[SurveyResponse]  WITH CHECK ADD  CONSTRAINT [FK_SurveyResponse_SurveyID_Survey_ID] FOREIGN KEY([SurveyID])
REFERENCES [dbo].[Survey] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SurveyResponse] CHECK CONSTRAINT [FK_SurveyResponse_SurveyID_Survey_ID]
GO
