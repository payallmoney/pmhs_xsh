CREATE TABLE [dbo].[PaymentMode]
(
[PersonalInfoID] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ID] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[PaymentModeID] [int] NOT NULL CONSTRAINT [DF__PaymentMo__Payme__119F9925] DEFAULT ((0))
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[PaymentMode] ADD CONSTRAINT [pk_PaymentMode] PRIMARY KEY CLUSTERED  ([ID]) ON [PRIMARY]
GO
