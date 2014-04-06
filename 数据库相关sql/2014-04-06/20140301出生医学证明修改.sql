--增加换发权限标识并初始化为0 0代表无权限 1代表有权限
Alter Table sam_taxempcode Add IsChangeCertifiAuthority Integer
GO
Update sam_taxempcode Set IsChangeCertifiAuthority = 0
GO
--增加补发权限标识并初始化为0 0代表无权限 1代表有权限
Alter Table sam_taxempcode Add IsSupplyCertifiAuthority Integer
GO
Update sam_taxempcode Set IsSupplyCertifiAuthority = 0
GO
--增加出生医学证明高级功能权限标识并初始化为0 0代表无权限 1代表有权限
Alter Table sam_taxempcode Add IsAdvancedCertifiAuthority Integer
GO
Update sam_taxempcode Set IsAdvancedCertifiAuthority = 0
GO
--保存换发记录 在BirthCertificate表中IsEffectived的标识值为5
--对于BirthCertificate表中IsEffectived的值说明：
--2表示使用 3表示已作废 4表示已归档
Create Table BirthCertificateChange (
	ID Nvarchar(36) Primary Key,
	SourceBirthCertifiID Nvarchar(30) Not NULL,
	DestBirthCertifiID Nvarchar(30) Not NULL,
	ChangeReasons Nvarchar(1000),
);
GO
--增加是否换发标识 1代表换发的出生医学证明
Alter Table BirthCertificate Add IsChanged Int
GO