/**
 * ��Ŀ���ƣ���ͯ��ҽҩ�����������
 * ��Ŀʱ�䣺2014-03-08
 * ���ߣ�Jackstraw
 **/
 --�����ҽҩ������������ָ����Ŀ
 Insert Into BasicInformation Values(2017,0,'��ͯ��ҽҩ�����������1~2','',2017,0,1,1,0,0)
 Insert Into BasicInformation Values(3057,1,'��ҽ��ʳ����ָ��','��ͯ��ҽҩ�����������1~2',2017,0,0,1,0,0)
 Insert Into BasicInformation Values(3058,2,'��ҽ��ӵ���ָ��','��ͯ��ҽҩ�����������1~2',2017,0,0,1,0,0)
 Insert Into BasicInformation Values(3059,3,'����Ħ�����󼹷���','��ͯ��ҽҩ�����������1~2',2017,0,0,1,0,0)
 Insert Into BasicInformation Values(3060,4,'����','��ͯ��ҽҩ�����������1~2',2017,0,0,1,0,0)
 GO
 --**************************************************
 Insert Into BasicInformation Values(2019,0,'��ͯ��ҽҩ�����������3~6','',2019,0,1,1,0,0)
 Insert Into BasicInformation Values(3065,1,'��ҽ��ʳ����ָ��','��ͯ��ҽҩ�����������3~6',2019,0,0,1,0,0)
 Insert Into BasicInformation Values(3066,2,'��ҽ��ӵ���ָ��','��ͯ��ҽҩ�����������3~6',2019,0,0,1,0,0)
 Insert Into BasicInformation Values(3067,3,'���ڰ��������Ѩ����','��ͯ��ҽҩ�����������3~6',2019,0,0,1,0,0)
 Insert Into BasicInformation Values(3068,4,'����','��ͯ��ҽҩ�����������3~6',2019,0,0,1,0,0)
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