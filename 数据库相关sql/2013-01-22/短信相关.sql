/*
Navicat SQL Server Data Transfer

Source Server         : localhost
Source Server Version : 105000
Source Host           : localhost:1433
Source Database       : pmhs_test
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 105000
File Encoding         : 65001

Date: 2013-01-22 21:36:41
*/


-- ----------------------------
-- Table structure for [dbo].[Cod_TelSendRule]
-- ----------------------------
DROP TABLE [dbo].[Cod_TelSendRule]
GO
CREATE TABLE [dbo].[Cod_TelSendRule] (
[id] nvarchar(36) NOT NULL ,
[name] nvarchar(30) NULL ,
[tablename] nvarchar(30) NOT NULL ,
[col] nvarchar(50) NOT NULL ,
[days] int NOT NULL ,
[optdate] datetime NOT NULL ,
[msg] nvarchar(200) NOT NULL DEFAULT '' ,
[wherestr] nvarchar(100) NOT NULL DEFAULT ('1=1') ,
[tableidname] nvarchar(50) NULL 
)


GO

-- ----------------------------
-- Records of Cod_TelSendRule
-- ----------------------------
INSERT INTO [dbo].[Cod_TelSendRule] ([id], [name], [tablename], [col], [days], [optdate], [msg], [wherestr], [tableidname]) VALUES (N'2-5次产检', N'2~5次产检下次产检提醒', N'VisitBeforeBorn', N'NextVisitDate', N'3', N'2013-01-16 11:00:26.540', N'您好！您下次产检的时间是（$(nextVisitDate)），为了安全渡过您的孕产期，请您准时到卫生院或者妇幼保健院做孕期检查。祝您全家健康幸福。', N'1=1', N'ID');
GO
INSERT INTO [dbo].[Cod_TelSendRule] ([id], [name], [tablename], [col], [days], [optdate], [msg], [wherestr], [tableidname]) VALUES (N'402881e63a933b88013a933bb5250001', N'首次产检下次产检提醒', N'FirstVistBeforeBorn', N'NextVisitDate', N'3', N'2013-01-15 18:03:39.343', N'您好！怀孕是一段美妙的人生体验，为了助您好“孕”，请您准时到卫生院或者妇幼保健院做孕期检查。祝您全家幸福。', N'1=1', N'ID');
GO

-- ----------------------------
-- Table structure for [dbo].[Cod_TelUpdateCol]
-- ----------------------------
DROP TABLE [dbo].[Cod_TelUpdateCol]
GO
CREATE TABLE [dbo].[Cod_TelUpdateCol] (
[id] nvarchar(36) NOT NULL ,
[tablename] nvarchar(30) NOT NULL ,
[col] nvarchar(50) NOT NULL ,
[name] nvarchar(50) NOT NULL ,
[ord] int NOT NULL 
)


GO

-- ----------------------------
-- Records of Cod_TelUpdateCol
-- ----------------------------
INSERT INTO [dbo].[Cod_TelUpdateCol] ([id], [tablename], [col], [name], [ord]) VALUES (N'2', N'PersonalInfo', N'LinkmanTEL', N'联系人电话', N'2');
GO
INSERT INTO [dbo].[Cod_TelUpdateCol] ([id], [tablename], [col], [name], [ord]) VALUES (N'4028811b3aa751ff013aa75263d40001', N'PersonalInfo', N'TEL', N'联系电话2', N'1');
GO
INSERT INTO [dbo].[Cod_TelUpdateCol] ([id], [tablename], [col], [name], [ord]) VALUES (N'4028811b3aa751ff013aa752ef150002', N'HealthFile', N'TEL', N'基本档案-联系电话', N'0');
GO

-- ----------------------------
-- Table structure for [dbo].[Sms_PersonTel]
-- ----------------------------
DROP TABLE [dbo].[Sms_PersonTel]
GO
CREATE TABLE [dbo].[Sms_PersonTel] (
[id] nvarchar(36) NOT NULL ,
[fileNo] nvarchar(36) NOT NULL ,
[filetype] int NOT NULL ,
[name] nvarchar(50) NOT NULL ,
[tel] nvarchar(50) NULL ,
[updateCol] nvarchar(100) NOT NULL ,
[updatetype] int NOT NULL 
)


GO


-- ----------------------------
-- Table structure for [dbo].[Sms_SendLog]
-- ----------------------------
DROP TABLE [dbo].[Sms_SendLog]
GO
CREATE TABLE [dbo].[Sms_SendLog] (
[smsdate] datetime NOT NULL ,
[examname] varchar(100) NOT NULL ,
[fileno] varchar(36) NOT NULL ,
[tel] varchar(50) NOT NULL ,
[msg] varchar(500) NOT NULL ,
[status] int NOT NULL ,
[sendtime] datetime NULL ,
[error] varchar(50) NULL ,
[tablename] varchar(50) NULL ,
[tableidvalue] varchar(50) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'Sms_SendLog', 
'COLUMN', N'smsdate')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'status = 0 未发送
          = 1 已发送
          = -1 发送失败
'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'Sms_SendLog'
, @level2type = 'COLUMN', @level2name = N'smsdate'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'status = 0 未发送
          = 1 已发送
          = -1 发送失败
'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'Sms_SendLog'
, @level2type = 'COLUMN', @level2name = N'smsdate'
GO

-- ----------------------------
-- Records of Sms_SendLog
-- ----------------------------
INSERT INTO [dbo].[Sms_SendLog] ([smsdate], [examname], [fileno], [tel], [msg], [status], [sendtime], [error], [tablename], [tableidvalue]) VALUES (N'2013-01-22 00:00:00.000', N'2~5次产检下次产检提醒', N'.*$.@&@$!@$*$$@>&@', N'15288429334', N'您好！您下次产检的时间是（2013年1月25日），为了安全渡过您的孕产期，请您准时到卫生院或者妇幼保健院做孕期检查。祝您全家健康幸福。', N'1', N'2013-01-22 21:09:54.947', N'发送成功', N'VisitBeforeBorn', N'8a8116823aeff85f013bd108d4c40db8    ');
GO
INSERT INTO [dbo].[Sms_SendLog] ([smsdate], [examname], [fileno], [tel], [msg], [status], [sendtime], [error], [tablename], [tableidvalue]) VALUES (N'2013-01-22 00:00:00.000', N'2~5次产检下次产检提醒', N'.*$.@&@$}@$}$$@&*}', N'15288429334', N'您好！您下次产检的时间是（2013年1月25日），为了安全渡过您的孕产期，请您准时到卫生院或者妇幼保健院做孕期检查。祝您全家健康幸福。', N'1', N'2013-01-22 21:09:55.000', N'发送成功', N'VisitBeforeBorn', N'8a8116823aeff85f013bd069ec590052    ');
GO
INSERT INTO [dbo].[Sms_SendLog] ([smsdate], [examname], [fileno], [tel], [msg], [status], [sendtime], [error], [tablename], [tableidvalue]) VALUES (N'2013-01-22 00:00:00.000', N'首次产检下次产检提醒', N'.*$.@&&$!@$*$$.=!.', N'15288429334', N'您好！怀孕是一段美妙的人生体验，为了助您好“孕”，请您准时到卫生院或者妇幼保健院做孕期检查。祝您全家幸福。', N'1', N'2013-01-22 21:09:55.130', N'发送成功', N'FirstVistBeforeBorn', N'8a8116823aeff85f013bd11ddfbb0fdb    ');
GO
INSERT INTO [dbo].[Sms_SendLog] ([smsdate], [examname], [fileno], [tel], [msg], [status], [sendtime], [error], [tablename], [tableidvalue]) VALUES (N'2013-01-22 00:00:00.000', N'首次产检下次产检提醒', N'.*$.@&&$@@&@$$@&.$', N'15288429334', N'您好！怀孕是一段美妙的人生体验，为了助您好“孕”，请您准时到卫生院或者妇幼保健院做孕期检查。祝您全家幸福。', N'1', N'2013-01-22 21:09:55.230', N'发送成功', N'FirstVistBeforeBorn', N'8a8116823aeff85f013ba7f6a8595f06    ');
GO
INSERT INTO [dbo].[Sms_SendLog] ([smsdate], [examname], [fileno], [tel], [msg], [status], [sendtime], [error], [tablename], [tableidvalue]) VALUES (N'2013-01-22 00:00:00.000', N'首次产检下次产检提醒', N'.*$.@&&$@@&@$$@&@=', N'15288429334', N'您好！怀孕是一段美妙的人生体验，为了助您好“孕”，请您准时到卫生院或者妇幼保健院做孕期检查。祝您全家幸福。', N'1', N'2013-01-22 21:09:55.330', N'发送成功', N'FirstVistBeforeBorn', N'8a8116823acc0af2013ae8eed29368e7    ');
GO
INSERT INTO [dbo].[Sms_SendLog] ([smsdate], [examname], [fileno], [tel], [msg], [status], [sendtime], [error], [tablename], [tableidvalue]) VALUES (N'2013-01-22 00:00:00.000', N'首次产检下次产检提醒', N'.*$.@&@$>@$&$$:$.*', N'15288429334', N'您好！怀孕是一段美妙的人生体验，为了助您好“孕”，请您准时到卫生院或者妇幼保健院做孕期检查。祝您全家幸福。', N'1', N'2013-01-22 21:09:55.430', N'发送成功', N'FirstVistBeforeBorn', N'8a8116823bd4f569013bdf5e2a416009    ');
GO

-- ----------------------------
-- Table structure for [dbo].[Sms_SendTarget]
-- ----------------------------

CREATE TABLE [dbo].[Sms_SendTarget] (
[id] nvarchar(36) NOT NULL ,
[name] nvarchar(30) NOT NULL ,
[tablename] nvarchar(30) NOT NULL ,
[msg] nvarchar(200) NOT NULL DEFAULT '' ,
[wherestr] nvarchar(100) NOT NULL DEFAULT ('1=1') ,
[tableidname] nvarchar(50) NOT NULL 
)


GO

-- ----------------------------
-- Records of Sms_SendTarget
-- ----------------------------
INSERT INTO [dbo].[Sms_SendTarget] ([id], [name], [tablename], [msg], [wherestr], [tableidname]) VALUES (N'2-5次产检', N'测试产检2', N'VisitBeforeBorn', N'您好！您下次产检的时间是（$(nextVisitDate)），为了安全渡过您的孕产期，请您准时到卫生院或者妇幼保健院做孕期检查。祝您全家健康幸福。', N'a.fileNo = dbo.enc(''530521104201003269'')', N'ID');
GO
INSERT INTO [dbo].[Sms_SendTarget] ([id], [name], [tablename], [msg], [wherestr], [tableidname]) VALUES (N'402881e63a933b88013a933bb5250001', N'测试首次产检', N'FirstVistBeforeBorn', N'您好！怀孕是一段美妙的人生体验，为了助您好“孕”，请您准时到卫生院或者妇幼保健院做孕期检查。祝您全家幸福。', N'a.fileNo = dbo.enc(''530521104204000468'')', N'ID');
GO

-- ----------------------------
-- Table structure for [dbo].[Sms_SendTargetOther]
-- ----------------------------

CREATE TABLE [dbo].[Sms_SendTargetOther] (
[id] nvarchar(36) NOT NULL ,
[name] nvarchar(30) NOT NULL ,
[tel] nvarchar(30) NOT NULL ,
[DistrictNumber] nvarchar(20) NOT NULL ,
[type] int NOT NULL ,
[isTest] int NOT NULL DEFAULT ((0)) 
)


GO

-- ----------------------------
-- Records of Sms_SendTargetOther
-- ----------------------------
INSERT INTO [dbo].[Sms_SendTargetOther] ([id], [name], [tel], [DistrictNumber], [type], [isTest]) VALUES (N'4028b8813c48de20013c48e0f0ac0001', N'测试1', N'15288429334', N'530521101', N'5005', N'1');
GO
INSERT INTO [dbo].[Sms_SendTargetOther] ([id], [name], [tel], [DistrictNumber], [type], [isTest]) VALUES (N'4028b8813c5aa73f013c5ade31120000', N'2222', N'15825258054', N'530521101', N'5006', N'1');
GO
INSERT INTO [dbo].[Sms_SendTargetOther] ([id], [name], [tel], [DistrictNumber], [type], [isTest]) VALUES (N'4028b8813c5afcea013c5b32c5c80000', N'测试2', N'13888531128', N'530521', N'5005', N'1');
GO

-- ----------------------------
-- Table structure for [dbo].[Sms_Status]
-- ----------------------------

CREATE TABLE [dbo].[Sms_Status] (
[smsdate] datetime NOT NULL ,
[iscreated] int NOT NULL ,
[issended] int NOT NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'Sms_Status', 
'COLUMN', N'issended')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'iscreated  =  0  未生成
                    1  正在生成
                    2  已生成
issended  = 0  未发送
               = 1   正在发送
               = 2   发送完成'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'Sms_Status'
, @level2type = 'COLUMN', @level2name = N'issended'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'iscreated  =  0  未生成
                    1  正在生成
                    2  已生成
issended  = 0  未发送
               = 1   正在发送
               = 2   发送完成'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'Sms_Status'
, @level2type = 'COLUMN', @level2name = N'issended'
GO

-- ----------------------------
-- Records of Sms_Status
-- ----------------------------


-- ----------------------------
-- Indexes structure for table Cod_TelSendRule
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[Cod_TelSendRule]
-- ----------------------------
ALTER TABLE [dbo].[Cod_TelSendRule] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table Cod_TelUpdateCol
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[Cod_TelUpdateCol]
-- ----------------------------
ALTER TABLE [dbo].[Cod_TelUpdateCol] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table Sms_PersonTel
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[Sms_PersonTel]
-- ----------------------------
ALTER TABLE [dbo].[Sms_PersonTel] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table Sms_SendLog
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[Sms_SendLog]
-- ----------------------------
ALTER TABLE [dbo].[Sms_SendLog] ADD PRIMARY KEY ([examname], [smsdate], [fileno])
GO

-- ----------------------------
-- Indexes structure for table Sms_SendTarget
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[Sms_SendTarget]
-- ----------------------------
ALTER TABLE [dbo].[Sms_SendTarget] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table Sms_SendTargetOther
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[Sms_SendTargetOther]
-- ----------------------------
ALTER TABLE [dbo].[Sms_SendTargetOther] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table Sms_Status
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[Sms_Status]
-- ----------------------------
ALTER TABLE [dbo].[Sms_Status] ADD PRIMARY KEY ([smsdate])
GO


INSERT INTO [BasicInformation] VALUES (5004, 0, '人员类型', '', 4001, 0, '1', '1', '0', 0);
GO
INSERT INTO [BasicInformation] VALUES (5005, 1, '管理人员', '人员类型', 4001, 0, '0', '1', '0', 0);
GO
INSERT INTO [BasicInformation] VALUES (5006, 2, '妇幼专干', '人员类型', 4001, 0, '0', '1', '0', 0);
GO

