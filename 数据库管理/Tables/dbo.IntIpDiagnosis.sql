CREATE TABLE [dbo].[IntIpDiagnosis]
(
[id] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[intKey] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[ipKey] [char] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[DiagnosisID] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL,
[DiagnosisName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE TRIGGER [dbo].[IntIpDiagnosisTrigger]
ON [dbo].[IntIpDiagnosis]
AFTER INSERT 
AS
BEGIN
	DECLARE @DiagnosisID NVARCHAR(30),--诊断对照编码
			@DiagnosisName NVARCHAR(50),--上传诊断名称
			@IpID NVARCHAR(36),--对应门诊日志主键
			@FileNo NVARCHAR(36),--公卫号
			@MakePerson NVARCHAR(20),--操作员  
			@MakeDate DATETIME,--上传日期
			@HospitalName NVARCHAR(100),--医院机构名称
			@DistrictNum NVARCHAR(30),--行政区划编码
			@DirectID NVARCHAR(32),--医院机构编号
			@ServiceType SmallInt,--服务类型
			@SubID NVARCHAR(36)
	SELECT @DiagnosisID = A.DiagnosisID,@IpID = A.ipKey,@HospitalName = B.HospitalName,
			@FileNo = B.FileNo,@MakePerson = B.MakePerson,@MakeDate = B.MakeDate,
			@DirectID = B.DirectID,@DistrictNum = DistrictNumber,@SubID = B.ID
	FROM Inserted A,IntInpatient B WHERE B.InKey = A.ipKey

	SET @DiagnosisName = NULL
	SELECT @DiagnosisName = DiagnoseName,@ServiceType = ServiceType FROM DiagnoseCoding WHERE ID = @DiagnosisID
	IF @DiagnosisName IS NOT NULL
	BEGIN
		INSERT INTO WaitingThing VALUES(LOWER(REPLACE(NEWID(),'-','')),@FileNo,@DistrictNum,@MakePerson,@MakeDate,@HospitalName,@IpID,0,@DiagnosisID,@DiagnosisName,1,@ServiceType,NULL,NULL,@SubID)
	END
END
GO
ALTER TABLE [dbo].[IntIpDiagnosis] ADD CONSTRAINT [pk_IntIpDiagnosis] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
