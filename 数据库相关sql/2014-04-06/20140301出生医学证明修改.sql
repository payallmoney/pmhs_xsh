--���ӻ���Ȩ�ޱ�ʶ����ʼ��Ϊ0 0������Ȩ�� 1������Ȩ��
Alter Table sam_taxempcode Add IsChangeCertifiAuthority Integer
GO
Update sam_taxempcode Set IsChangeCertifiAuthority = 0
GO
--���Ӳ���Ȩ�ޱ�ʶ����ʼ��Ϊ0 0������Ȩ�� 1������Ȩ��
Alter Table sam_taxempcode Add IsSupplyCertifiAuthority Integer
GO
Update sam_taxempcode Set IsSupplyCertifiAuthority = 0
GO
--���ӳ���ҽѧ֤���߼�����Ȩ�ޱ�ʶ����ʼ��Ϊ0 0������Ȩ�� 1������Ȩ��
Alter Table sam_taxempcode Add IsAdvancedCertifiAuthority Integer
GO
Update sam_taxempcode Set IsAdvancedCertifiAuthority = 0
GO
--���滻����¼ ��BirthCertificate����IsEffectived�ı�ʶֵΪ5
--����BirthCertificate����IsEffectived��ֵ˵����
--2��ʾʹ�� 3��ʾ������ 4��ʾ�ѹ鵵
Create Table BirthCertificateChange (
	ID Nvarchar(36) Primary Key,
	SourceBirthCertifiID Nvarchar(30) Not NULL,
	DestBirthCertifiID Nvarchar(30) Not NULL,
	ChangeReasons Nvarchar(1000),
);
GO
--�����Ƿ񻻷���ʶ 1�������ĳ���ҽѧ֤��
Alter Table BirthCertificate Add IsChanged Int
GO