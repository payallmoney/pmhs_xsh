/**
 * 项目名称：儿童中医药健康管理服务
 * 项目时间：2014-03-08
 * 作者：Jackstraw
 **/
 --添加中医药健康管理服务的指导项目
 Insert Into BasicInformation Values(2017,0,'儿童中医药健康管理服务1~2','',2017,0,1,1,0,0)
 Insert Into BasicInformation Values(3057,1,'中医饮食调养指导','儿童中医药健康管理服务1~2',2017,0,0,1,0,0)
 Insert Into BasicInformation Values(3058,2,'中医起居调摄指导','儿童中医药健康管理服务1~2',2017,0,0,1,0,0)
 Insert Into BasicInformation Values(3059,3,'传授摩腹、捏脊方法','儿童中医药健康管理服务1~2',2017,0,0,1,0,0)
 Insert Into BasicInformation Values(3060,4,'其他','儿童中医药健康管理服务1~2',2017,0,0,1,0,0)
 GO
 --**************************************************
 Insert Into BasicInformation Values(2019,0,'儿童中医药健康管理服务3~6','',2019,0,1,1,0,0)
 Insert Into BasicInformation Values(3065,1,'中医饮食调养指导','儿童中医药健康管理服务3~6',2019,0,0,1,0,0)
 Insert Into BasicInformation Values(3066,2,'中医起居调摄指导','儿童中医药健康管理服务3~6',2019,0,0,1,0,0)
 Insert Into BasicInformation Values(3067,3,'传授按揉四神聪穴方法','儿童中医药健康管理服务3~6',2019,0,0,1,0,0)
 Insert Into BasicInformation Values(3068,4,'其他','儿童中医药健康管理服务3~6',2019,0,0,1,0,0)
 GO

 
 Create Table TCMServiceForChildren(
	ID Nvarchar(36) Primary Key,
	ChildrenMediExamID Nvarchar(36) Not NULL,
	ManageServiceID Int Not NULL,
 )
 GO
Create Table TCMServiceForChildren36(
	ID Nvarchar(36) Primary Key,
	ChildrenMediExam36ID Nvarchar(36) Not NULL,
	ManageService36ID Int Not NULL,
 )
 GO
 
 Alter Table ChildrenMediExam Add TCMServiceOther Nvarchar(1000)
 GO
 Alter Table ChildrenMediExam3_6 Add TCMService36Other Nvarchar(1000)
 GO