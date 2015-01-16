/*
Navicat SQL Server Data Transfer

Source Server         : localhost
Source Server Version : 105000
Source Host           : localhost:1433
Source Database       : pmhs_km
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 105000
File Encoding         : 65001

Date: 2014-08-11 09:38:48
*/


-- ----------------------------
-- Table structure for map_interface
-- ----------------------------
DROP TABLE [dbo].[map_interface]
GO
CREATE TABLE [dbo].[map_interface] (
[code] varchar(20) NOT NULL ,
[name] varchar(20) NULL ,
[version] varchar(100) NULL ,
[validdatebegin] datetime NULL ,
[validdateend] datetime NULL ,
[valid] bit NULL ,
[auto] bit NULL ,
[class] varchar(100) NULL 
)


GO

-- ----------------------------
-- Records of map_interface
-- ----------------------------
INSERT INTO [dbo].[map_interface] ([code], [name], [version], [validdatebegin], [validdateend], [valid], [auto], [class]) VALUES (N'shandong', N'山东诺安诺泰', N'2014', N'2014-01-01 00:00:00.000', N'2016-01-01 00:00:00.000', N'1', N'1', N'cn.net.tongfang.web.port.shangdong.ShangDongInterface')
GO
GO

-- ----------------------------
-- Table structure for map_interface_cfg
-- ----------------------------
DROP TABLE [dbo].[map_interface_cfg]
GO
CREATE TABLE [dbo].[map_interface_cfg] (
[interface] varchar(20) NOT NULL ,
[code] varchar(20) NOT NULL ,
[value] varchar(100) NULL 
)


GO

-- ----------------------------
-- Records of map_interface_cfg
-- ----------------------------
INSERT INTO [dbo].[map_interface_cfg] ([interface], [code], [value]) VALUES (N'shandong', N'dataMode', N'0')
GO
GO
INSERT INTO [dbo].[map_interface_cfg] ([interface], [code], [value]) VALUES (N'shandong', N'foramt', N'json')
GO
GO
INSERT INTO [dbo].[map_interface_cfg] ([interface], [code], [value]) VALUES (N'shandong', N'ip', N'124.133.239.162')
GO
GO
INSERT INTO [dbo].[map_interface_cfg] ([interface], [code], [value]) VALUES (N'shandong', N'key', N'476035743664029266825384875995117369268221954084662752473736190551067918373765337720654151')
GO
GO
INSERT INTO [dbo].[map_interface_cfg] ([interface], [code], [value]) VALUES (N'shandong', N'merId', N'100036')
GO
GO
INSERT INTO [dbo].[map_interface_cfg] ([interface], [code], [value]) VALUES (N'shandong', N'openId', N'ph003300')
GO
GO
INSERT INTO [dbo].[map_interface_cfg] ([interface], [code], [value]) VALUES (N'shandong', N'port', N'9898')
GO
GO

-- ----------------------------
-- Table structure for map_interface_col_map
-- ----------------------------
DROP TABLE [dbo].[map_interface_col_map]
GO
CREATE TABLE [dbo].[map_interface_col_map] (
[interface] varchar(20) NULL ,
[subcode] varchar(20) NULL ,
[systable] varchar(50) NULL ,
[syscol] varchar(100) NULL ,
[type] varchar(100) NULL ,
[value] varchar(100) NULL ,
[interfacecol] varchar(100) NULL 
)


GO

-- ----------------------------
-- Records of map_interface_col_map
-- ----------------------------
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'FolkOther', N'check:1:null:map', null, N'BaseInfo.nation')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'BloodTypeABO', N'map', null, N'BaseInfo.bloodtype')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'BloodTypeRH', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'Education', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'Occupation', N'map', null, N'BaseInfo.job')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'MaritalStatus', N'map', null, N'BaseInfo.maritalstatus')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'PaymentModeOther', N'map', null, N'BaseInfo.medicalpaytype')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'AllergiesOther', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'fHistoryOther', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'mHistoryOther', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'bHistoryOther', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'fmHistoryOther', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'GeneticHistory', N'map', null, N'BaseInfo.disease')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'GeneticHistoryOther', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'DisabilityStatusOther', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'InputPersonID', N'inputperson', N'BaseInfo.orgVillageCode,BaseInfo.orgTownCode,BaseInfo.orgDistinctCode,BaseInfo.orgCityCode', N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'InputDate', N'parseDate', null, N'BaseInfo.createdDate')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'farmStatus', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'HomeID', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'Kitchen', N'map', null, N'LifeEnvironment.blowmeasure')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'Bunkers', N'map', null, N'LifeEnvironment.fueltype')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'DrinkingWater', N'map', null, N'LifeEnvironment.drinkwater')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'Toilet', N'map', null, N'LifeEnvironment.toilet')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'Poultry', N'map', null, N'LifeEnvironment.livestockrail')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'TownStatus', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'bornStatus', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'allergiesHistory', null, N'map', null, N'LifeEnvironment.pillallergic')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'exposeHistory', null, N'map', null, N'LifeEnvironment.exposure')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'diseaseHistory', null, N'map', null, N'IllnessHistoryInfo.illnesstype=1  illnessname')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'opshistory', null, N'map', null, N'IllnessHistoryInfo.illnesstype=2  illnessname')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'traumaHistory', null, N'map', null, N'IllnessHistoryInfo.illnesstype=3  illnessname')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'bloodTrans', null, N'map', null, N'IllnessHistoryInfo.illnesstype=4  illnessname')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'fatherHistory', null, N'map', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'matherHistory', null, N'map', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'brotherHistory', null, N'map', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'familyHistory', null, N'map', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'disabilityStatus', null, N'map', null, N'BaseInfo.diseasendition')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'Name', N'value', null, N'BaseInfo.customername')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'?shandong', N'healthfile', N'healthfile', N'FileNo', N'fileno', N'BaseInfo.orgVillageCode,BaseInfo.orgTownCode,BaseInfo.orgDistinctCode,BaseInfo.orgCityCode', N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'Name', N'value', null, N'BaseInfo.customername')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'Address', N'value', null, N'BaseInfo.addr')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'ResidenceAddress', N'value', null, N'BaseInfo.householdAddr')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'TEL', N'value', null, N'BaseInfo.phone')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'Township', N'map', null, N'BaseInfo.townCode')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'Village', N'map', null, N'BaseInfo.villageCode')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'BuildUnit', N'map', null, N'BaseInfo.createunit')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'BuildPerson', N'map', null, N'BaseInfo.createdBy')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'Doctor', N'value', null, N'BaseInfo.doctor')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'BuildDate', N'parseDate', null, N'BaseInfo.createdDate')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'DistrictNumber', N'matchvalue', null, N'BaseInfo.orgVillageCode,BaseInfo.orgTownCode,BaseInfo.orgDistinctCode,BaseInfo.orgCityCode')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'Name_Png', N'static', null, N'BaseInfo.nmpng')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'InputPersonID', N'inputperson', N'BaseInfo.orgVillageCode,BaseInfo.orgTownCode,BaseInfo.orgDistinctCode,BaseInfo.orgCityCode', N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'InputDate', N'parseDate', null, N'BaseInfo.createdDate')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'LastModifyDate', N'parseDate', null, N'BaseInfo.lastUpdateDate')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'ModifyPerson', N'value', null, N'BaseInfo.lastUpdateBy')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'BarCode', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'IsOverCount', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'IsModifyOrNew', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'PaperFileNo', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'Status', N'static', N'0', N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'healthfile', N'Nation', N'static', N'中国', N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'ID', N'uuid', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'FileNo', N'fileno', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'FileNoSub', N'static', null, N'')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'Sex', N'map', null, N'BaseInfo.sex')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'Birthday', N'parseDate', null, N'BaseInfo.birthday')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'IDNumber', N'value', null, N'BaseInfo.idcard')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'WorkUnit', N'value', null, N'BaseInfo.workunit')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'TEL', N'value', null, N'BaseInfo.phone')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'Linkman', N'value', null, N'BaseInfo.contactname')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'LinkmanTEL', N'value', null, N'BaseInfo.contactphone')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'ResideType', N'map', null, N'BaseInfo.livetype')
GO
GO
INSERT INTO [dbo].[map_interface_col_map] ([interface], [subcode], [systable], [syscol], [type], [value], [interfacecol]) VALUES (N'shandong', N'healthfile', N'personalinfo', N'Folk', N'check:1:汉族:少数民族', null, N'BaseInfo.nation')
GO
GO

-- ----------------------------
-- Table structure for map_interface_item
-- ----------------------------
DROP TABLE [dbo].[map_interface_item]
GO
CREATE TABLE [dbo].[map_interface_item] (
[code] varchar(20) NOT NULL ,
[name] varchar(20) NULL 
)


GO

-- ----------------------------
-- Records of map_interface_item
-- ----------------------------
INSERT INTO [dbo].[map_interface_item] ([code], [name]) VALUES (N'healthexam', N'健康体检')
GO
GO
INSERT INTO [dbo].[map_interface_item] ([code], [name]) VALUES (N'healthfile', N'基本健康档案')
GO
GO

-- ----------------------------
-- Table structure for map_interface_itemcfg
-- ----------------------------
DROP TABLE [dbo].[map_interface_itemcfg]
GO
CREATE TABLE [dbo].[map_interface_itemcfg] (
[code] varchar(20) NOT NULL ,
[interface] varchar(20) NOT NULL ,
[url] varchar(100) NULL ,
[valid] bit NULL 
)


GO

-- ----------------------------
-- Records of map_interface_itemcfg
-- ----------------------------
INSERT INTO [dbo].[map_interface_itemcfg] ([code], [interface], [url], [valid]) VALUES (N'healthexam', N'shandong', N'/api/physical/get_physical', N'0')
GO
GO
INSERT INTO [dbo].[map_interface_itemcfg] ([code], [interface], [url], [valid]) VALUES (N'healthfile', N'shandong', N'/api/baseinfo/list_baseinfo', N'1')
GO
GO

-- ----------------------------
-- Table structure for map_interface_log
-- ----------------------------
DROP TABLE [dbo].[map_interface_log]
GO
CREATE TABLE [dbo].[map_interface_log] (
[interfacecode] varchar(20) NULL ,
[subcode] varchar(20) NULL ,
[opttime] datetime NULL ,
[jsondata] varchar(MAX) NULL ,
[paramdata] varchar(1000) NULL ,
[url] varchar(255) NULL 
)


GO

-- ----------------------------
-- Records of map_interface_log
-- ----------------------------
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-06-23 17:18:59.260', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":1000,\"pageIndex\":1},\"createdDateEnd\":\"2014-06-16 00:00:00\",\"createdDateStart\":\"2014-06-16 00:00:00\",\"doctor\":\"佟医生\"}"},{"name":"sign","value":"557b892f1d62f9f4bbe38f4908ddd409"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:14:55.340', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":1000,\"pageIndex\":1},\"createdDateEnd\":\"2014-06-16 00:00:00\",\"createdDateStart\":\"2014-06-16 00:00:00\",\"doctor\":\"佟医生\"}"},{"name":"sign","value":"557b892f1d62f9f4bbe38f4908ddd409"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:17:43.790', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":1000,\"pageIndex\":1},\"createdDateEnd\":\"2014-06-16 00:00:00\",\"createdDateStart\":\"2014-06-16 00:00:00\",\"doctor\":\"佟医生\"}"},{"name":"sign","value":"557b892f1d62f9f4bbe38f4908ddd409"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:14:55.063', N'{"sign":"98c609258dc5982b13445c543e1c1516","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"查询体检信息列表成功！","customerBaseInfos":[{"medications":[{"id":41363,"physicalid":"354797","starttime":"时间","pilldependence":"1","usenum":"1","medicinalname":"阿司匹林"},{"id":41364,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41365,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41366,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41367,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41368,"physicalid":"354797"}],"assessmentGuide":{"id":497890,"physicalid":"354797","dangercontrol":"1","healthzhidao":"1,2,3","isnormal":"2"},"mediPhysDist":{"faint":"2","id":350056,"bloodstasis":"2","yin":"2","physicalid":"354797","yang":"2","muggy":"2","mild":"2","qiconstraint":"2","characteristic":"2","phlegmdamp":"2"},"provinceCode":"53","orgCityCode":"5301","cityCode":"5301","lifeStyle":{"exerciseexistense":"睡觉","daydrinkvolume":12,"exercisetime":12,"physicalprotect":"1","smokecondition":"2","fangsheprotect":"1","drinkstartage":12,"careerharmfactorhistory":"2","otherprotect":"1","id":350134,"eathobby":"1","smokeagestart":21,"physicalid":"354797","drinktype":"1","smokedaynum":12,"exerciserate":"1","excisepersisttime":12,"chemprotect":"1","smokeageforbiddon":21,"worktype":"清洁工","worktime":11,"dustprotect":"1"},"doctor":"恒辰医生","id":354798,"isDel":"N","checkdate":"2014-06-05 00:00:00","assistCheck":{"albumin":45,"hbsag":"1","cb":45,"scr":45,"other":"45","fpgdl":45,"tg":45,"glu":"45","bchaoEx":"45","id":349664,"chestxEx":"45","bp":45,"tc":45,"ket":"45","bloodOther":"45","plt":45,"wbc":45,"urineOther":"45","bld":"45","hbalc":45,"ecg":"1","chestx":"2","hb":40,"sgpt":45,"tbil":45,"lowCho":45,"fob":"1","physicalid":"354797","fpgl":45,"bun":45,"pc":45,"pro":"45","hype":45,"got":45,"heiCho":45,"bchao":"2"},"orgProvinceCode":"53","physicalExam":{"anus":"2","rale":"2","breathsounds":"2","enclosedmass":"2","heartrhythm":"2","eyeround":"2","voiced":"2","sclera":"2","skin":"2","noise":"2","breast":"5","id":349574,"physicalid":"354797","edema":"3","heartrate":"45","liver":"2","lymph":"2","spleen":"2","presspain":"2","footback":"2","barrelchest":"2"},"createdDate":"2014-06-05 19:02:15","healthQuestion":{"id":349572,"brainDis":"2","elseDisOther":"45","nerveDisOther":"45","physicalid":"354797","vesselDis":"2","renalDis":"2","heartDis":"2","eyeDis":"2","elseDis":"2","nerveDis":"2"},"visceraFunction":{"id":349783,"toothresides":"2","physicalid":"354797","lips":"2","pharyngeal":"2","listen":"2","sportfunction":"1"},"customername":"恒辰测试1","archiveid":"53010200000000001","familyBEDHistorys":[{"id":6277,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":6279,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"}],"idcard":"342225199106053717","hospitalHistorys":[{"id":5434,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":5435,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"}],"generalCondition":{"id":349732,"tem":37,"physicalid":"354797","height":188,"breathRate":55,"weight":45,"waistline":178,"rightpre":130,"bmi":12.73,"rightheight":140,"leftpre":130,"leftheight":140,"pulserate":45},"districtCode":"530102","physicalid":"354797","createdBy":3300,"inoculationHistorys":[{"id":7194,"pillname":"青霉素","physicalid":"354797"},{"id":7195,"pillname":"地塞米松","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"},{"id":7196,"pillname":"红霉素","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"}],"symptom":"2"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"recordStart\":1000,\"pageSize\":1000,\"pageIndex\":1}}"},{"name":"sign","value":"339bba0ad350f7e61c71aad3010354c1"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/list_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-06-23 17:18:59.047', N'{"sign":"98c609258dc5982b13445c543e1c1516","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"查询体检信息列表成功！","customerBaseInfos":[{"medications":[{"id":41363,"physicalid":"354797","starttime":"时间","pilldependence":"1","usenum":"1","medicinalname":"阿司匹林"},{"id":41364,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41365,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41366,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41367,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41368,"physicalid":"354797"}],"assessmentGuide":{"id":497890,"physicalid":"354797","dangercontrol":"1","healthzhidao":"1,2,3","isnormal":"2"},"mediPhysDist":{"faint":"2","id":350056,"bloodstasis":"2","yin":"2","physicalid":"354797","yang":"2","muggy":"2","mild":"2","qiconstraint":"2","characteristic":"2","phlegmdamp":"2"},"provinceCode":"53","orgCityCode":"5301","cityCode":"5301","lifeStyle":{"exerciseexistense":"睡觉","daydrinkvolume":12,"exercisetime":12,"physicalprotect":"1","smokecondition":"2","fangsheprotect":"1","drinkstartage":12,"careerharmfactorhistory":"2","otherprotect":"1","id":350134,"eathobby":"1","smokeagestart":21,"physicalid":"354797","drinktype":"1","smokedaynum":12,"exerciserate":"1","excisepersisttime":12,"chemprotect":"1","smokeageforbiddon":21,"worktype":"清洁工","worktime":11,"dustprotect":"1"},"doctor":"恒辰医生","id":354798,"isDel":"N","checkdate":"2014-06-05 00:00:00","assistCheck":{"albumin":45,"hbsag":"1","cb":45,"scr":45,"other":"45","fpgdl":45,"tg":45,"glu":"45","bchaoEx":"45","id":349664,"chestxEx":"45","bp":45,"tc":45,"ket":"45","bloodOther":"45","plt":45,"wbc":45,"urineOther":"45","bld":"45","hbalc":45,"ecg":"1","chestx":"2","hb":40,"sgpt":45,"tbil":45,"lowCho":45,"fob":"1","physicalid":"354797","fpgl":45,"bun":45,"pc":45,"pro":"45","hype":45,"got":45,"heiCho":45,"bchao":"2"},"orgProvinceCode":"53","physicalExam":{"anus":"2","rale":"2","breathsounds":"2","enclosedmass":"2","heartrhythm":"2","eyeround":"2","voiced":"2","sclera":"2","skin":"2","noise":"2","breast":"5","id":349574,"physicalid":"354797","edema":"3","heartrate":"45","liver":"2","lymph":"2","spleen":"2","presspain":"2","footback":"2","barrelchest":"2"},"createdDate":"2014-06-05 19:02:15","healthQuestion":{"id":349572,"brainDis":"2","elseDisOther":"45","nerveDisOther":"45","physicalid":"354797","vesselDis":"2","renalDis":"2","heartDis":"2","eyeDis":"2","elseDis":"2","nerveDis":"2"},"visceraFunction":{"id":349783,"toothresides":"2","physicalid":"354797","lips":"2","pharyngeal":"2","listen":"2","sportfunction":"1"},"customername":"恒辰测试1","archiveid":"53010200000000001","familyBEDHistorys":[{"id":6277,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":6279,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"}],"idcard":"342225199106053717","hospitalHistorys":[{"id":5434,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":5435,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"}],"generalCondition":{"id":349732,"tem":37,"physicalid":"354797","height":188,"breathRate":55,"weight":45,"waistline":178,"rightpre":130,"bmi":12.73,"rightheight":140,"leftpre":130,"leftheight":140,"pulserate":45},"districtCode":"530102","physicalid":"354797","createdBy":3300,"inoculationHistorys":[{"id":7194,"pillname":"青霉素","physicalid":"354797"},{"id":7195,"pillname":"地塞米松","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"},{"id":7196,"pillname":"红霉素","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"}],"symptom":"2"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"recordStart\":1000,\"pageSize\":1000,\"pageIndex\":1}}"},{"name":"sign","value":"339bba0ad350f7e61c71aad3010354c1"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/list_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:17:43.543', N'{"sign":"98c609258dc5982b13445c543e1c1516","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"查询体检信息列表成功！","customerBaseInfos":[{"medications":[{"id":41363,"physicalid":"354797","starttime":"时间","pilldependence":"1","usenum":"1","medicinalname":"阿司匹林"},{"id":41364,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41365,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41366,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41367,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41368,"physicalid":"354797"}],"assessmentGuide":{"id":497890,"physicalid":"354797","dangercontrol":"1","healthzhidao":"1,2,3","isnormal":"2"},"mediPhysDist":{"faint":"2","id":350056,"bloodstasis":"2","yin":"2","physicalid":"354797","yang":"2","muggy":"2","mild":"2","qiconstraint":"2","characteristic":"2","phlegmdamp":"2"},"provinceCode":"53","orgCityCode":"5301","cityCode":"5301","lifeStyle":{"exerciseexistense":"睡觉","daydrinkvolume":12,"exercisetime":12,"physicalprotect":"1","smokecondition":"2","fangsheprotect":"1","drinkstartage":12,"careerharmfactorhistory":"2","otherprotect":"1","id":350134,"eathobby":"1","smokeagestart":21,"physicalid":"354797","drinktype":"1","smokedaynum":12,"exerciserate":"1","excisepersisttime":12,"chemprotect":"1","smokeageforbiddon":21,"worktype":"清洁工","worktime":11,"dustprotect":"1"},"doctor":"恒辰医生","id":354798,"isDel":"N","checkdate":"2014-06-05 00:00:00","assistCheck":{"albumin":45,"hbsag":"1","cb":45,"scr":45,"other":"45","fpgdl":45,"tg":45,"glu":"45","bchaoEx":"45","id":349664,"chestxEx":"45","bp":45,"tc":45,"ket":"45","bloodOther":"45","plt":45,"wbc":45,"urineOther":"45","bld":"45","hbalc":45,"ecg":"1","chestx":"2","hb":40,"sgpt":45,"tbil":45,"lowCho":45,"fob":"1","physicalid":"354797","fpgl":45,"bun":45,"pc":45,"pro":"45","hype":45,"got":45,"heiCho":45,"bchao":"2"},"orgProvinceCode":"53","physicalExam":{"anus":"2","rale":"2","breathsounds":"2","enclosedmass":"2","heartrhythm":"2","eyeround":"2","voiced":"2","sclera":"2","skin":"2","noise":"2","breast":"5","id":349574,"physicalid":"354797","edema":"3","heartrate":"45","liver":"2","lymph":"2","spleen":"2","presspain":"2","footback":"2","barrelchest":"2"},"createdDate":"2014-06-05 19:02:15","healthQuestion":{"id":349572,"brainDis":"2","elseDisOther":"45","nerveDisOther":"45","physicalid":"354797","vesselDis":"2","renalDis":"2","heartDis":"2","eyeDis":"2","elseDis":"2","nerveDis":"2"},"visceraFunction":{"id":349783,"toothresides":"2","physicalid":"354797","lips":"2","pharyngeal":"2","listen":"2","sportfunction":"1"},"customername":"恒辰测试1","archiveid":"53010200000000001","familyBEDHistorys":[{"id":6277,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":6279,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"}],"idcard":"342225199106053717","hospitalHistorys":[{"id":5434,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":5435,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"}],"generalCondition":{"id":349732,"tem":37,"physicalid":"354797","height":188,"breathRate":55,"weight":45,"waistline":178,"rightpre":130,"bmi":12.73,"rightheight":140,"leftpre":130,"leftheight":140,"pulserate":45},"districtCode":"530102","physicalid":"354797","createdBy":3300,"inoculationHistorys":[{"id":7194,"pillname":"青霉素","physicalid":"354797"},{"id":7195,"pillname":"地塞米松","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"},{"id":7196,"pillname":"红霉素","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"}],"symptom":"2"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"recordStart\":1000,\"pageSize\":1000,\"pageIndex\":1}}"},{"name":"sign","value":"339bba0ad350f7e61c71aad3010354c1"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/list_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:18:57.753', N'{"sign":"f46dc8630c66c59c7ba137060dbddbe5","respCode":"1","respMsg":"查看体检信息输入参数ID不能为空！"}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/get_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:19:40.897', N'{"sign":"f46dc8630c66c59c7ba137060dbddbe5","respCode":"1","respMsg":"查看体检信息输入参数ID不能为空！"}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/get_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:18:57.223', N'{"sign":"98c609258dc5982b13445c543e1c1516","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"查询体检信息列表成功！","customerBaseInfos":[{"medications":[{"id":41363,"physicalid":"354797","starttime":"时间","pilldependence":"1","usenum":"1","medicinalname":"阿司匹林"},{"id":41364,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41365,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41366,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41367,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41368,"physicalid":"354797"}],"assessmentGuide":{"id":497890,"physicalid":"354797","dangercontrol":"1","healthzhidao":"1,2,3","isnormal":"2"},"mediPhysDist":{"faint":"2","id":350056,"bloodstasis":"2","yin":"2","physicalid":"354797","yang":"2","muggy":"2","mild":"2","qiconstraint":"2","characteristic":"2","phlegmdamp":"2"},"provinceCode":"53","orgCityCode":"5301","cityCode":"5301","lifeStyle":{"exerciseexistense":"睡觉","daydrinkvolume":12,"exercisetime":12,"physicalprotect":"1","smokecondition":"2","fangsheprotect":"1","drinkstartage":12,"careerharmfactorhistory":"2","otherprotect":"1","id":350134,"eathobby":"1","smokeagestart":21,"physicalid":"354797","drinktype":"1","smokedaynum":12,"exerciserate":"1","excisepersisttime":12,"chemprotect":"1","smokeageforbiddon":21,"worktype":"清洁工","worktime":11,"dustprotect":"1"},"doctor":"恒辰医生","id":354798,"isDel":"N","checkdate":"2014-06-05 00:00:00","assistCheck":{"albumin":45,"hbsag":"1","cb":45,"scr":45,"other":"45","fpgdl":45,"tg":45,"glu":"45","bchaoEx":"45","id":349664,"chestxEx":"45","bp":45,"tc":45,"ket":"45","bloodOther":"45","plt":45,"wbc":45,"urineOther":"45","bld":"45","hbalc":45,"ecg":"1","chestx":"2","hb":40,"sgpt":45,"tbil":45,"lowCho":45,"fob":"1","physicalid":"354797","fpgl":45,"bun":45,"pc":45,"pro":"45","hype":45,"got":45,"heiCho":45,"bchao":"2"},"orgProvinceCode":"53","physicalExam":{"anus":"2","rale":"2","breathsounds":"2","enclosedmass":"2","heartrhythm":"2","eyeround":"2","voiced":"2","sclera":"2","skin":"2","noise":"2","breast":"5","id":349574,"physicalid":"354797","edema":"3","heartrate":"45","liver":"2","lymph":"2","spleen":"2","presspain":"2","footback":"2","barrelchest":"2"},"createdDate":"2014-06-05 19:02:15","healthQuestion":{"id":349572,"brainDis":"2","elseDisOther":"45","nerveDisOther":"45","physicalid":"354797","vesselDis":"2","renalDis":"2","heartDis":"2","eyeDis":"2","elseDis":"2","nerveDis":"2"},"visceraFunction":{"id":349783,"toothresides":"2","physicalid":"354797","lips":"2","pharyngeal":"2","listen":"2","sportfunction":"1"},"customername":"恒辰测试1","archiveid":"53010200000000001","familyBEDHistorys":[{"id":6277,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":6279,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"}],"idcard":"342225199106053717","hospitalHistorys":[{"id":5434,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":5435,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"}],"generalCondition":{"id":349732,"tem":37,"physicalid":"354797","height":188,"breathRate":55,"weight":45,"waistline":178,"rightpre":130,"bmi":12.73,"rightheight":140,"leftpre":130,"leftheight":140,"pulserate":45},"districtCode":"530102","physicalid":"354797","createdBy":3300,"inoculationHistorys":[{"id":7194,"pillname":"青霉素","physicalid":"354797"},{"id":7195,"pillname":"地塞米松","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"},{"id":7196,"pillname":"红霉素","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"}],"symptom":"2"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"recordStart\":1000,\"pageSize\":1000,\"pageIndex\":1}}"},{"name":"sign","value":"339bba0ad350f7e61c71aad3010354c1"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/list_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:18:57.537', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":1000,\"pageIndex\":1},\"createdDateEnd\":\"2014-06-16 00:00:00\",\"createdDateStart\":\"2014-06-16 00:00:00\",\"doctor\":\"佟医生\"}"},{"name":"sign","value":"557b892f1d62f9f4bbe38f4908ddd409"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:18:58.177', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":2,"recordStart":0,"pageSize":100,"recordEnd":100,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"},{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365830","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"云南省昆明市","disease":"1","doctor":"恒辰医生","id":1166580,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-05 00:00:00","customername":"恒辰测试1","archiveid":"53010200000000001","orgDistinctCode":"530102","job":"1","idcard":"342225199106053717","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-05 18:42:14","orgCityId":602,"illnessHistoryInfos":[{"id":82470,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000001"},{"id":82471,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000001"},{"id":82469,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000001"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:19:40.430', N'{"sign":"98c609258dc5982b13445c543e1c1516","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"查询体检信息列表成功！","customerBaseInfos":[{"medications":[{"id":41363,"physicalid":"354797","starttime":"时间","pilldependence":"1","usenum":"1","medicinalname":"阿司匹林"},{"id":41364,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41365,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41366,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41367,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41368,"physicalid":"354797"}],"assessmentGuide":{"id":497890,"physicalid":"354797","dangercontrol":"1","healthzhidao":"1,2,3","isnormal":"2"},"mediPhysDist":{"faint":"2","id":350056,"bloodstasis":"2","yin":"2","physicalid":"354797","yang":"2","muggy":"2","mild":"2","qiconstraint":"2","characteristic":"2","phlegmdamp":"2"},"provinceCode":"53","orgCityCode":"5301","cityCode":"5301","lifeStyle":{"exerciseexistense":"睡觉","daydrinkvolume":12,"exercisetime":12,"physicalprotect":"1","smokecondition":"2","fangsheprotect":"1","drinkstartage":12,"careerharmfactorhistory":"2","otherprotect":"1","id":350134,"eathobby":"1","smokeagestart":21,"physicalid":"354797","drinktype":"1","smokedaynum":12,"exerciserate":"1","excisepersisttime":12,"chemprotect":"1","smokeageforbiddon":21,"worktype":"清洁工","worktime":11,"dustprotect":"1"},"doctor":"恒辰医生","id":354798,"isDel":"N","checkdate":"2014-06-05 00:00:00","assistCheck":{"albumin":45,"hbsag":"1","cb":45,"scr":45,"other":"45","fpgdl":45,"tg":45,"glu":"45","bchaoEx":"45","id":349664,"chestxEx":"45","bp":45,"tc":45,"ket":"45","bloodOther":"45","plt":45,"wbc":45,"urineOther":"45","bld":"45","hbalc":45,"ecg":"1","chestx":"2","hb":40,"sgpt":45,"tbil":45,"lowCho":45,"fob":"1","physicalid":"354797","fpgl":45,"bun":45,"pc":45,"pro":"45","hype":45,"got":45,"heiCho":45,"bchao":"2"},"orgProvinceCode":"53","physicalExam":{"anus":"2","rale":"2","breathsounds":"2","enclosedmass":"2","heartrhythm":"2","eyeround":"2","voiced":"2","sclera":"2","skin":"2","noise":"2","breast":"5","id":349574,"physicalid":"354797","edema":"3","heartrate":"45","liver":"2","lymph":"2","spleen":"2","presspain":"2","footback":"2","barrelchest":"2"},"createdDate":"2014-06-05 19:02:15","healthQuestion":{"id":349572,"brainDis":"2","elseDisOther":"45","nerveDisOther":"45","physicalid":"354797","vesselDis":"2","renalDis":"2","heartDis":"2","eyeDis":"2","elseDis":"2","nerveDis":"2"},"visceraFunction":{"id":349783,"toothresides":"2","physicalid":"354797","lips":"2","pharyngeal":"2","listen":"2","sportfunction":"1"},"customername":"恒辰测试1","archiveid":"53010200000000001","familyBEDHistorys":[{"id":6277,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":6279,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"}],"idcard":"342225199106053717","hospitalHistorys":[{"id":5434,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":5435,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"}],"generalCondition":{"id":349732,"tem":37,"physicalid":"354797","height":188,"breathRate":55,"weight":45,"waistline":178,"rightpre":130,"bmi":12.73,"rightheight":140,"leftpre":130,"leftheight":140,"pulserate":45},"districtCode":"530102","physicalid":"354797","createdBy":3300,"inoculationHistorys":[{"id":7194,"pillname":"青霉素","physicalid":"354797"},{"id":7195,"pillname":"地塞米松","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"},{"id":7196,"pillname":"红霉素","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"}],"symptom":"2"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"recordStart\":1000,\"pageSize\":1000,\"pageIndex\":1}}"},{"name":"sign","value":"339bba0ad350f7e61c71aad3010354c1"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/list_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:25:53.500', N'{"sign":"f46dc8630c66c59c7ba137060dbddbe5","respCode":"1","respMsg":"查看体检信息输入参数ID不能为空！"}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/get_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:19:40.687', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":1000,\"pageIndex\":1},\"createdDateEnd\":\"2014-06-16 00:00:00\",\"createdDateStart\":\"2014-06-16 00:00:00\",\"doctor\":\"佟医生\"}"},{"name":"sign","value":"557b892f1d62f9f4bbe38f4908ddd409"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:19:41.277', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":2,"recordStart":0,"pageSize":100,"recordEnd":100,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"},{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365830","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"云南省昆明市","disease":"1","doctor":"恒辰医生","id":1166580,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-05 00:00:00","customername":"恒辰测试1","archiveid":"53010200000000001","orgDistinctCode":"530102","job":"1","idcard":"342225199106053717","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-05 18:42:14","orgCityId":602,"illnessHistoryInfos":[{"id":82470,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000001"},{"id":82471,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000001"},{"id":82469,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000001"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:20:46.007', N'{"sign":"98c609258dc5982b13445c543e1c1516","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"查询体检信息列表成功！","customerBaseInfos":[{"medications":[{"id":41363,"physicalid":"354797","starttime":"时间","pilldependence":"1","usenum":"1","medicinalname":"阿司匹林"},{"id":41364,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41365,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41366,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41367,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41368,"physicalid":"354797"}],"assessmentGuide":{"id":497890,"physicalid":"354797","dangercontrol":"1","healthzhidao":"1,2,3","isnormal":"2"},"mediPhysDist":{"faint":"2","id":350056,"bloodstasis":"2","yin":"2","physicalid":"354797","yang":"2","muggy":"2","mild":"2","qiconstraint":"2","characteristic":"2","phlegmdamp":"2"},"provinceCode":"53","orgCityCode":"5301","cityCode":"5301","lifeStyle":{"exerciseexistense":"睡觉","daydrinkvolume":12,"exercisetime":12,"physicalprotect":"1","smokecondition":"2","fangsheprotect":"1","drinkstartage":12,"careerharmfactorhistory":"2","otherprotect":"1","id":350134,"eathobby":"1","smokeagestart":21,"physicalid":"354797","drinktype":"1","smokedaynum":12,"exerciserate":"1","excisepersisttime":12,"chemprotect":"1","smokeageforbiddon":21,"worktype":"清洁工","worktime":11,"dustprotect":"1"},"doctor":"恒辰医生","id":354798,"isDel":"N","checkdate":"2014-06-05 00:00:00","assistCheck":{"albumin":45,"hbsag":"1","cb":45,"scr":45,"other":"45","fpgdl":45,"tg":45,"glu":"45","bchaoEx":"45","id":349664,"chestxEx":"45","bp":45,"tc":45,"ket":"45","bloodOther":"45","plt":45,"wbc":45,"urineOther":"45","bld":"45","hbalc":45,"ecg":"1","chestx":"2","hb":40,"sgpt":45,"tbil":45,"lowCho":45,"fob":"1","physicalid":"354797","fpgl":45,"bun":45,"pc":45,"pro":"45","hype":45,"got":45,"heiCho":45,"bchao":"2"},"orgProvinceCode":"53","physicalExam":{"anus":"2","rale":"2","breathsounds":"2","enclosedmass":"2","heartrhythm":"2","eyeround":"2","voiced":"2","sclera":"2","skin":"2","noise":"2","breast":"5","id":349574,"physicalid":"354797","edema":"3","heartrate":"45","liver":"2","lymph":"2","spleen":"2","presspain":"2","footback":"2","barrelchest":"2"},"createdDate":"2014-06-05 19:02:15","healthQuestion":{"id":349572,"brainDis":"2","elseDisOther":"45","nerveDisOther":"45","physicalid":"354797","vesselDis":"2","renalDis":"2","heartDis":"2","eyeDis":"2","elseDis":"2","nerveDis":"2"},"visceraFunction":{"id":349783,"toothresides":"2","physicalid":"354797","lips":"2","pharyngeal":"2","listen":"2","sportfunction":"1"},"customername":"恒辰测试1","archiveid":"53010200000000001","familyBEDHistorys":[{"id":6277,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":6279,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"}],"idcard":"342225199106053717","hospitalHistorys":[{"id":5434,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":5435,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"}],"generalCondition":{"id":349732,"tem":37,"physicalid":"354797","height":188,"breathRate":55,"weight":45,"waistline":178,"rightpre":130,"bmi":12.73,"rightheight":140,"leftpre":130,"leftheight":140,"pulserate":45},"districtCode":"530102","physicalid":"354797","createdBy":3300,"inoculationHistorys":[{"id":7194,"pillname":"青霉素","physicalid":"354797"},{"id":7195,"pillname":"地塞米松","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"},{"id":7196,"pillname":"红霉素","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"}],"symptom":"2"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"recordStart\":1000,\"pageSize\":1000,\"pageIndex\":1}}"},{"name":"sign","value":"339bba0ad350f7e61c71aad3010354c1"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/list_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:20:46.457', N'{"sign":"f46dc8630c66c59c7ba137060dbddbe5","respCode":"1","respMsg":"查看体检信息输入参数ID不能为空！"}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/get_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:21:47.500', N'{"sign":"f46dc8630c66c59c7ba137060dbddbe5","respCode":"1","respMsg":"查看体检信息输入参数ID不能为空！"}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/get_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:23:57.760', N'{"sign":"f46dc8630c66c59c7ba137060dbddbe5","respCode":"1","respMsg":"查看体检信息输入参数ID不能为空！"}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/get_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:20:46.253', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":1000,\"pageIndex\":1},\"createdDateEnd\":\"2014-06-16 00:00:00\",\"createdDateStart\":\"2014-06-16 00:00:00\",\"doctor\":\"佟医生\"}"},{"name":"sign","value":"557b892f1d62f9f4bbe38f4908ddd409"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:20:46.847', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":2,"recordStart":0,"pageSize":100,"recordEnd":100,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"},{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365830","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"云南省昆明市","disease":"1","doctor":"恒辰医生","id":1166580,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-05 00:00:00","customername":"恒辰测试1","archiveid":"53010200000000001","orgDistinctCode":"530102","job":"1","idcard":"342225199106053717","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-05 18:42:14","orgCityId":602,"illnessHistoryInfos":[{"id":82470,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000001"},{"id":82471,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000001"},{"id":82469,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000001"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:21:47.057', N'{"sign":"98c609258dc5982b13445c543e1c1516","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"查询体检信息列表成功！","customerBaseInfos":[{"medications":[{"id":41363,"physicalid":"354797","starttime":"时间","pilldependence":"1","usenum":"1","medicinalname":"阿司匹林"},{"id":41364,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41365,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41366,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41367,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41368,"physicalid":"354797"}],"assessmentGuide":{"id":497890,"physicalid":"354797","dangercontrol":"1","healthzhidao":"1,2,3","isnormal":"2"},"mediPhysDist":{"faint":"2","id":350056,"bloodstasis":"2","yin":"2","physicalid":"354797","yang":"2","muggy":"2","mild":"2","qiconstraint":"2","characteristic":"2","phlegmdamp":"2"},"provinceCode":"53","orgCityCode":"5301","cityCode":"5301","lifeStyle":{"exerciseexistense":"睡觉","daydrinkvolume":12,"exercisetime":12,"physicalprotect":"1","smokecondition":"2","fangsheprotect":"1","drinkstartage":12,"careerharmfactorhistory":"2","otherprotect":"1","id":350134,"eathobby":"1","smokeagestart":21,"physicalid":"354797","drinktype":"1","smokedaynum":12,"exerciserate":"1","excisepersisttime":12,"chemprotect":"1","smokeageforbiddon":21,"worktype":"清洁工","worktime":11,"dustprotect":"1"},"doctor":"恒辰医生","id":354798,"isDel":"N","checkdate":"2014-06-05 00:00:00","assistCheck":{"albumin":45,"hbsag":"1","cb":45,"scr":45,"other":"45","fpgdl":45,"tg":45,"glu":"45","bchaoEx":"45","id":349664,"chestxEx":"45","bp":45,"tc":45,"ket":"45","bloodOther":"45","plt":45,"wbc":45,"urineOther":"45","bld":"45","hbalc":45,"ecg":"1","chestx":"2","hb":40,"sgpt":45,"tbil":45,"lowCho":45,"fob":"1","physicalid":"354797","fpgl":45,"bun":45,"pc":45,"pro":"45","hype":45,"got":45,"heiCho":45,"bchao":"2"},"orgProvinceCode":"53","physicalExam":{"anus":"2","rale":"2","breathsounds":"2","enclosedmass":"2","heartrhythm":"2","eyeround":"2","voiced":"2","sclera":"2","skin":"2","noise":"2","breast":"5","id":349574,"physicalid":"354797","edema":"3","heartrate":"45","liver":"2","lymph":"2","spleen":"2","presspain":"2","footback":"2","barrelchest":"2"},"createdDate":"2014-06-05 19:02:15","healthQuestion":{"id":349572,"brainDis":"2","elseDisOther":"45","nerveDisOther":"45","physicalid":"354797","vesselDis":"2","renalDis":"2","heartDis":"2","eyeDis":"2","elseDis":"2","nerveDis":"2"},"visceraFunction":{"id":349783,"toothresides":"2","physicalid":"354797","lips":"2","pharyngeal":"2","listen":"2","sportfunction":"1"},"customername":"恒辰测试1","archiveid":"53010200000000001","familyBEDHistorys":[{"id":6277,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":6279,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"}],"idcard":"342225199106053717","hospitalHistorys":[{"id":5434,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":5435,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"}],"generalCondition":{"id":349732,"tem":37,"physicalid":"354797","height":188,"breathRate":55,"weight":45,"waistline":178,"rightpre":130,"bmi":12.73,"rightheight":140,"leftpre":130,"leftheight":140,"pulserate":45},"districtCode":"530102","physicalid":"354797","createdBy":3300,"inoculationHistorys":[{"id":7194,"pillname":"青霉素","physicalid":"354797"},{"id":7195,"pillname":"地塞米松","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"},{"id":7196,"pillname":"红霉素","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"}],"symptom":"2"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"recordStart\":1000,\"pageSize\":1000,\"pageIndex\":1}}"},{"name":"sign","value":"339bba0ad350f7e61c71aad3010354c1"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/list_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:21:47.297', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":1000,\"pageIndex\":1},\"createdDateEnd\":\"2014-06-16 00:00:00\",\"createdDateStart\":\"2014-06-16 00:00:00\",\"doctor\":\"佟医生\"}"},{"name":"sign","value":"557b892f1d62f9f4bbe38f4908ddd409"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:21:47.913', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":2,"recordStart":0,"pageSize":100,"recordEnd":100,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"},{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365830","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"云南省昆明市","disease":"1","doctor":"恒辰医生","id":1166580,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-05 00:00:00","customername":"恒辰测试1","archiveid":"53010200000000001","orgDistinctCode":"530102","job":"1","idcard":"342225199106053717","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-05 18:42:14","orgCityId":602,"illnessHistoryInfos":[{"id":82470,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000001"},{"id":82471,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000001"},{"id":82469,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000001"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:23:57.297', N'{"sign":"98c609258dc5982b13445c543e1c1516","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"查询体检信息列表成功！","customerBaseInfos":[{"medications":[{"id":41363,"physicalid":"354797","starttime":"时间","pilldependence":"1","usenum":"1","medicinalname":"阿司匹林"},{"id":41364,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41365,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41366,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41367,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41368,"physicalid":"354797"}],"assessmentGuide":{"id":497890,"physicalid":"354797","dangercontrol":"1","healthzhidao":"1,2,3","isnormal":"2"},"mediPhysDist":{"faint":"2","id":350056,"bloodstasis":"2","yin":"2","physicalid":"354797","yang":"2","muggy":"2","mild":"2","qiconstraint":"2","characteristic":"2","phlegmdamp":"2"},"provinceCode":"53","orgCityCode":"5301","cityCode":"5301","lifeStyle":{"exerciseexistense":"睡觉","daydrinkvolume":12,"exercisetime":12,"physicalprotect":"1","smokecondition":"2","fangsheprotect":"1","drinkstartage":12,"careerharmfactorhistory":"2","otherprotect":"1","id":350134,"eathobby":"1","smokeagestart":21,"physicalid":"354797","drinktype":"1","smokedaynum":12,"exerciserate":"1","excisepersisttime":12,"chemprotect":"1","smokeageforbiddon":21,"worktype":"清洁工","worktime":11,"dustprotect":"1"},"doctor":"恒辰医生","id":354798,"isDel":"N","checkdate":"2014-06-05 00:00:00","assistCheck":{"albumin":45,"hbsag":"1","cb":45,"scr":45,"other":"45","fpgdl":45,"tg":45,"glu":"45","bchaoEx":"45","id":349664,"chestxEx":"45","bp":45,"tc":45,"ket":"45","bloodOther":"45","plt":45,"wbc":45,"urineOther":"45","bld":"45","hbalc":45,"ecg":"1","chestx":"2","hb":40,"sgpt":45,"tbil":45,"lowCho":45,"fob":"1","physicalid":"354797","fpgl":45,"bun":45,"pc":45,"pro":"45","hype":45,"got":45,"heiCho":45,"bchao":"2"},"orgProvinceCode":"53","physicalExam":{"anus":"2","rale":"2","breathsounds":"2","enclosedmass":"2","heartrhythm":"2","eyeround":"2","voiced":"2","sclera":"2","skin":"2","noise":"2","breast":"5","id":349574,"physicalid":"354797","edema":"3","heartrate":"45","liver":"2","lymph":"2","spleen":"2","presspain":"2","footback":"2","barrelchest":"2"},"createdDate":"2014-06-05 19:02:15","healthQuestion":{"id":349572,"brainDis":"2","elseDisOther":"45","nerveDisOther":"45","physicalid":"354797","vesselDis":"2","renalDis":"2","heartDis":"2","eyeDis":"2","elseDis":"2","nerveDis":"2"},"visceraFunction":{"id":349783,"toothresides":"2","physicalid":"354797","lips":"2","pharyngeal":"2","listen":"2","sportfunction":"1"},"customername":"恒辰测试1","archiveid":"53010200000000001","familyBEDHistorys":[{"id":6277,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":6279,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"}],"idcard":"342225199106053717","hospitalHistorys":[{"id":5434,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":5435,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"}],"generalCondition":{"id":349732,"tem":37,"physicalid":"354797","height":188,"breathRate":55,"weight":45,"waistline":178,"rightpre":130,"bmi":12.73,"rightheight":140,"leftpre":130,"leftheight":140,"pulserate":45},"districtCode":"530102","physicalid":"354797","createdBy":3300,"inoculationHistorys":[{"id":7194,"pillname":"青霉素","physicalid":"354797"},{"id":7195,"pillname":"地塞米松","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"},{"id":7196,"pillname":"红霉素","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"}],"symptom":"2"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"recordStart\":1000,\"pageSize\":1000,\"pageIndex\":1}}"},{"name":"sign","value":"339bba0ad350f7e61c71aad3010354c1"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/list_physical')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:23:57.550', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":1000,\"pageIndex\":1},\"createdDateEnd\":\"2014-06-16 00:00:00\",\"createdDateStart\":\"2014-06-16 00:00:00\",\"doctor\":\"佟医生\"}"},{"name":"sign","value":"557b892f1d62f9f4bbe38f4908ddd409"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:25:53.283', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":1000,\"pageIndex\":1},\"createdDateEnd\":\"2014-06-16 00:00:00\",\"createdDateStart\":\"2014-06-16 00:00:00\",\"doctor\":\"佟医生\"}"},{"name":"sign","value":"557b892f1d62f9f4bbe38f4908ddd409"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:25:53.890', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":2,"recordStart":0,"pageSize":100,"recordEnd":100,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"},{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365830","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"云南省昆明市","disease":"1","doctor":"恒辰医生","id":1166580,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-05 00:00:00","customername":"恒辰测试1","archiveid":"53010200000000001","orgDistinctCode":"530102","job":"1","idcard":"342225199106053717","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-05 18:42:14","orgCityId":602,"illnessHistoryInfos":[{"id":82470,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000001"},{"id":82471,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000001"},{"id":82469,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000001"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthfile', N'2014-07-01 11:23:58.200', N'{"sign":"72fd2425c04d508ed5e55b49177b1285","respCode":"0","pagePara":{"total":2,"recordStart":0,"pageSize":100,"recordEnd":100,"pageIndex":1},"respMsg":"居民档案信息列表查询成功！","baseInfos":[{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365920","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"昆明市","disease":"1","doctor":"佟医生","id":1166592,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-16 00:00:00","customername":"恒辰测试2","archiveid":"53010200000000002","orgDistinctCode":"530102","job":"1","idcard":"342225199106053718","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-16 09:31:15","orgCityId":602,"illnessHistoryInfos":[{"id":82505,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000002"},{"id":82506,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000002"},{"id":82507,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000002"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"},{"exposure":"1","provinceId":13,"birthday":"1991-06-05 00:00:00","phone":"18763365830","sex":"1","cityId":420,"pillallergic":"1","provinceCode":"53","lastUpdateBy":3300,"orgCityCode":"5301","rh":"3","addr":"云南省昆明市","disease":"1","doctor":"恒辰医生","id":1166580,"isDel":"N","maritalstatus":"1","districtId":2208,"createunit":602,"houseRelation":"1","orgProvinceCode":"53","createdDate":"2014-06-05 00:00:00","customername":"恒辰测试1","archiveid":"53010200000000001","orgDistinctCode":"530102","job":"1","idcard":"342225199106053717","bloodtype":"5","nation":"1","lastUpdateDate":"2014-06-05 18:42:14","orgCityId":602,"illnessHistoryInfos":[{"id":82470,"illnessname":"1","illnesstype":"3","archiveid":"53010200000000001"},{"id":82471,"illnessname":"1","illnesstype":"4","archiveid":"53010200000000001"},{"id":82469,"illnessname":"1","illnesstype":"2","archiveid":"53010200000000001"}],"createdBy":3300,"contactname":"无","livetype":"1","orgProvinceId":13,"householdAddr":"云南省","culture":"1","populationType":"1","diseasendition":"1"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"pageSize\":100,\"pageIndex\":1}}"},{"name":"sign","value":"30b6046121695d6518da25802b11dbb6"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/baseinfo/list_baseinfo')
GO
GO
INSERT INTO [dbo].[map_interface_log] ([interfacecode], [subcode], [opttime], [jsondata], [paramdata], [url]) VALUES (N'shandong', N'healthexam', N'2014-07-01 11:25:53.027', N'{"sign":"98c609258dc5982b13445c543e1c1516","respCode":"0","pagePara":{"total":1,"recordStart":0,"pageSize":1000,"recordEnd":1000,"pageIndex":1},"respMsg":"查询体检信息列表成功！","customerBaseInfos":[{"medications":[{"id":41363,"physicalid":"354797","starttime":"时间","pilldependence":"1","usenum":"1","medicinalname":"阿司匹林"},{"id":41364,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41365,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41366,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41367,"physicalid":"354797","usenum":"1","medicinalname":"阿司匹林"},{"id":41368,"physicalid":"354797"}],"assessmentGuide":{"id":497890,"physicalid":"354797","dangercontrol":"1","healthzhidao":"1,2,3","isnormal":"2"},"mediPhysDist":{"faint":"2","id":350056,"bloodstasis":"2","yin":"2","physicalid":"354797","yang":"2","muggy":"2","mild":"2","qiconstraint":"2","characteristic":"2","phlegmdamp":"2"},"provinceCode":"53","orgCityCode":"5301","cityCode":"5301","lifeStyle":{"exerciseexistense":"睡觉","daydrinkvolume":12,"exercisetime":12,"physicalprotect":"1","smokecondition":"2","fangsheprotect":"1","drinkstartage":12,"careerharmfactorhistory":"2","otherprotect":"1","id":350134,"eathobby":"1","smokeagestart":21,"physicalid":"354797","drinktype":"1","smokedaynum":12,"exerciserate":"1","excisepersisttime":12,"chemprotect":"1","smokeageforbiddon":21,"worktype":"清洁工","worktime":11,"dustprotect":"1"},"doctor":"恒辰医生","id":354798,"isDel":"N","checkdate":"2014-06-05 00:00:00","assistCheck":{"albumin":45,"hbsag":"1","cb":45,"scr":45,"other":"45","fpgdl":45,"tg":45,"glu":"45","bchaoEx":"45","id":349664,"chestxEx":"45","bp":45,"tc":45,"ket":"45","bloodOther":"45","plt":45,"wbc":45,"urineOther":"45","bld":"45","hbalc":45,"ecg":"1","chestx":"2","hb":40,"sgpt":45,"tbil":45,"lowCho":45,"fob":"1","physicalid":"354797","fpgl":45,"bun":45,"pc":45,"pro":"45","hype":45,"got":45,"heiCho":45,"bchao":"2"},"orgProvinceCode":"53","physicalExam":{"anus":"2","rale":"2","breathsounds":"2","enclosedmass":"2","heartrhythm":"2","eyeround":"2","voiced":"2","sclera":"2","skin":"2","noise":"2","breast":"5","id":349574,"physicalid":"354797","edema":"3","heartrate":"45","liver":"2","lymph":"2","spleen":"2","presspain":"2","footback":"2","barrelchest":"2"},"createdDate":"2014-06-05 19:02:15","healthQuestion":{"id":349572,"brainDis":"2","elseDisOther":"45","nerveDisOther":"45","physicalid":"354797","vesselDis":"2","renalDis":"2","heartDis":"2","eyeDis":"2","elseDis":"2","nerveDis":"2"},"visceraFunction":{"id":349783,"toothresides":"2","physicalid":"354797","lips":"2","pharyngeal":"2","listen":"2","sportfunction":"1"},"customername":"恒辰测试1","archiveid":"53010200000000001","familyBEDHistorys":[{"id":6277,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":6279,"illcasenums":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","hospitalname":"45","reasons":"45","outhospitaldate":"2014-06-04 00:00:00"}],"idcard":"342225199106053717","hospitalHistorys":[{"id":5434,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"},{"id":5435,"illcasenum":"45","physicalid":"354797","inhospitaldate":"2014-06-01 00:00:00","reason":"45","hospitalname":"45","outhospitaldate":"2014-06-04 00:00:00"}],"generalCondition":{"id":349732,"tem":37,"physicalid":"354797","height":188,"breathRate":55,"weight":45,"waistline":178,"rightpre":130,"bmi":12.73,"rightheight":140,"leftpre":130,"leftheight":140,"pulserate":45},"districtCode":"530102","physicalid":"354797","createdBy":3300,"inoculationHistorys":[{"id":7194,"pillname":"青霉素","physicalid":"354797"},{"id":7195,"pillname":"地塞米松","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"},{"id":7196,"pillname":"红霉素","physicalid":"354797","inoculationdate":"2014-06-04 00:00:00"}],"symptom":"2"}]}', N'[{"name":"merId","value":"100036"},{"name":"dataMode","value":"0"},{"name":"data","value":"{\"pagePara\":{\"recordStart\":1000,\"pageSize\":1000,\"pageIndex\":1}}"},{"name":"sign","value":"339bba0ad350f7e61c71aad3010354c1"},{"name":"openId","value":"ph003300"},{"name":"foramt","value":"json"}]', N'http://124.133.239.162:9898//api/physical/list_physical')
GO
GO

-- ----------------------------
-- Table structure for map_interface_org_inputperson
-- ----------------------------
DROP TABLE [dbo].[map_interface_org_inputperson]
GO
CREATE TABLE [dbo].[map_interface_org_inputperson] (
[interface] varchar(20) NOT NULL ,
[districtid] varchar(30) NOT NULL ,
[inputperson] varchar(30) NULL 
)


GO

-- ----------------------------
-- Records of map_interface_org_inputperson
-- ----------------------------
INSERT INTO [dbo].[map_interface_org_inputperson] ([interface], [districtid], [inputperson]) VALUES (N'shandong', N'5301', N'admin')
GO
GO

-- ----------------------------
-- Indexes structure for table map_interface
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table map_interface
-- ----------------------------
ALTER TABLE [dbo].[map_interface] ADD PRIMARY KEY ([code])
GO

-- ----------------------------
-- Indexes structure for table map_interface_cfg
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table map_interface_cfg
-- ----------------------------
ALTER TABLE [dbo].[map_interface_cfg] ADD PRIMARY KEY ([interface], [code])
GO

-- ----------------------------
-- Indexes structure for table map_interface_item
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table map_interface_item
-- ----------------------------
ALTER TABLE [dbo].[map_interface_item] ADD PRIMARY KEY ([code])
GO

-- ----------------------------
-- Indexes structure for table map_interface_itemcfg
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table map_interface_itemcfg
-- ----------------------------
ALTER TABLE [dbo].[map_interface_itemcfg] ADD PRIMARY KEY ([code], [interface])
GO

-- ----------------------------
-- Indexes structure for table map_interface_org_inputperson
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table map_interface_org_inputperson
-- ----------------------------
ALTER TABLE [dbo].[map_interface_org_inputperson] ADD PRIMARY KEY ([interface], [districtid])
GO
