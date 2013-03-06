CREATE TABLE [dbo].[IntPersonInfo]
(
[id] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[intKey] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NOT NULL,
[HospitalName] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[State] [smallint] NULL CONSTRAINT [DF__IntPerson__State__511AFFBC] DEFAULT ((0)),
[PersonName] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[Sex] [varchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[BirthDay] [datetime] NULL,
[Address] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL,
[DirectID] [nvarchar] (32) COLLATE Chinese_PRC_CI_AS NULL,
[MakeDate] [datetime] NULL,
[MakePerson] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL,
[CompleteDate] [datetime] NULL,
[Type] [smallint] NOT NULL CONSTRAINT [DF__IntPersonI__Type__520F23F5] DEFAULT ((0)),
[CheckState] [smallint] NOT NULL CONSTRAINT [DF__IntPerson__Check__5303482E] DEFAULT ((0)),
[IDNumber] [nvarchar] (18) COLLATE Chinese_PRC_CI_AS NULL,
[Occupation] [nvarchar] (100) COLLATE Chinese_PRC_CI_AS NULL,
[ServiceType] [smallint] NOT NULL CONSTRAINT [DF__IntPerson__Servi__53F76C67] DEFAULT ((0)),
[TagString] [nvarchar] (10) COLLATE Chinese_PRC_CI_AS NULL,
[FileNo] [nvarchar] (36) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO

CREATE TRIGGER [dbo].[IntPersonInfoTrigger]
ON [dbo].[IntPersonInfo]
AFTER INSERT 
AS
BEGIN
	DECLARE @IntID NVARCHAR(36) ,--门诊或住院日志上传主键(对应Outpatient、Inpatient表的ID)
			@State Smallint, --0：上传待处理 1：已建档
			@PersonName Varchar(20) ,--姓名
			@Sex Varchar(10) ,--性别
			@BirthDay DateTime ,--生日
			@Address Varchar(50) ,--住址
			@DirectID [char](32) ,--人员所处地区编号
			@MakeDate datetime ,--上传日期
			@MakePerson Varchar(20),--操作员  
			@CompleteDate datetime, --建档日期  
			@HospitalName NVARCHAR(100),--医院机构名称
			@DistrictParentName Nvarchar(50),
			@DistrictName Nvarchar(50),
			@TmpFileNo NVARCHAR(36),
			@IDNumber Nvarchar(18),--身份证号
			@Occupation Nvarchar(100),--职业
			@ServiceType SmallInt,--
			@FileNo Nvarchar(30),--档案编号
			@SubID Nvarchar(36),
			@Type Int --门诊－住院
	SELECT @TmpFileNo = ID,@IntID = intKey,@State = State,@PersonName = PersonName,@HospitalName = HospitalName,
			@Sex = Sex,@BirthDay = BirthDay,@Address = Address,@DirectID = DirectID,
			@MakeDate = MakeDate,@MakePerson = MakePerson,@CompleteDate = CompleteDate,
			@IDNumber = IDNumber,@Occupation = Occupation,@ServiceType = ServiceType,
			@FileNo = FileNo,@SubID = ID,@Type = [Type]
	FROM Inserted
	
	IF @ServiceType = 2
	BEGIN
		SELECT @DistrictName = Name,@DistrictParentName = ParentName FROM District WHERE ID = @DirectID
		BEGIN TRAN
		INSERT INTO HealthFile(FileNo,Name,Address,Township,Village,BuildDate,DistrictNumber,Status)
			VALUES(@TmpFileNo,@PersonName,@Address,@DistrictParentName,@DistrictName,@CompleteDate,@DirectID,2)
		INSERT INTO PersonalInfo(ID,FileNo,FileNoSub,Sex,Birthday,IDNumber,Occupation) 
			VALUES(LOWER(REPLACE(NEWID(),'-','')),@TmpFileNo,'',@Sex,@BirthDay,@IDNumber,@Occupation)
		INSERT INTO WaitingThing VALUES(LOWER(REPLACE(NEWID(),'-','')),@TmpFileNo,@DirectID,@MakePerson,@MakeDate,@HospitalName,@IntID,0,'XJDAGD00004','新建档案',@Type,-1,NULL,NULL,@SubID)
		
		COMMIT TRAN
	END
	ELSE IF @ServiceType = 0
	BEGIN
		INSERT INTO WaitingThing VALUES(LOWER(REPLACE(NEWID(),'-','')),@FileNo,@DirectID,@MakePerson,@MakeDate,@HospitalName,@IntID,0,'BDDAGD00005','比对档案',@Type,-2,NULL,NULL,@SubID)
	END
END
GO
ALTER TABLE [dbo].[IntPersonInfo] ADD CONSTRAINT [pk_IntPersonInfo] PRIMARY KEY CLUSTERED  ([id]) ON [PRIMARY]
GO
