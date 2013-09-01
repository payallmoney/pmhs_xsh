drop TRIGGER vaccineImmune_update_trigger on  VaccineImmune
go
CREATE TRIGGER vaccineImmune_update_trigger on  VaccineImmune  FOR  INSERT , UPDATE , DELETE  AS
begin 
	declare @vaccid as varchar(36)
	declare @fileno as varchar(30)
	if exists (select 1 from deleted)
	begin 
		select @vaccid = ID,@fileno = VFileNo from deleted
	end 
	else
	begin 
		select @vaccid = ID,@fileno = VFileNo from inserted
	end 
	if not exists (select 1 from vacc where fileno = @fileno)
	begin
		insert into vacc_vaccinfo (
				FileNo	,
				updatedate	,
				downloadflag	,
				childinnercode	,
				childcode	,
				childname	,
				childsex	,
				vacccard	,
				vaccproperty	,
				birthday	,
				accountaddress	,
				homeaddress	,
				address	,
				hometel	,
				email	,
				callnumber	,
				phonenumber	,
				custommsg	,
				customdatetime	,
				fathername	,
				fatherworkunit	,
				fathertel	,
				mothername	,
				motherworkunit	,
				mothertel	,
				motherHB	,
				needindemnify	,
				indemnifybegindate	,
				indemnifyenddate	,
				indemnifymoney	,
				childdistrict	,
				censusproperty	,
				censuschange	,
				accountproperty	,
				hascontraindications	,
				inbooksituation	,
				inbookremark	,
				inbookdate	,
				inbookchange	,
				makecarddate	,
				givecarddate	,
				vaccaddress	,
				remark	,
				vaccine	,
				vaccinationtime1	,
				vaccinationtime2	,
				vaccinationtime3	,
				vaccinationtime4	,
				vaccinationaddress1	,
				vaccinationaddress2	,
				vaccinationaddress3	,
				vaccinationaddress4	,
				vaccinationflag	,
				originalposition	,
				currentposition	,
				datastatus	,
				modifydate	,
				uploadtime	,
				offsitevaccination	,
				uploadflag	,
				printreservedate	,
				fullservice	,
				fullservicecontent	,
				choose	,
				col1	,
				col2	,
				vaccinebatchnumber	,
				freevaccine	,
				visitno	,
				visitdate	,
				visitnobackup	,
				servings	,
				vaccinationdoctor	,
				vaccinationposition	,
				terminalnumber	,
				registdoctor	,
				visitconfirmdate	,
				childidcard	,
				certificateno	,
				birthtime	,
				birthhospital	,
				parity	,
				birthweight	,
				fatherphonenumber	,
				motherphonenumber	,
				countrycode	,
				infectioushistory	,
				allergyhistory	,
				vaccinationreactionshistory	,
				contraindications	,
				makecardunit	,
				makecardperson	,
				onlycode	,
				isinpark	,
				inparkyear	,
				parknumber	,
				BCGvaccinationtime	,
				HBvaccinationtime	,
				producers	,
				nation	,
				vaccinationtime	,
				billscode	,
				billsdate	,
				givecardunit	,
				downloadtime	,
				downloadunit	,
				col3	,
				col4	,
				col5	,
				profession	,
				uploadunit	,
				col6	,
				col7	,
				col8	,
				col9	,
				col10	,
				motheridcard	,
				fatheridcard	,
				healthfileno	,
				scar	,
				inoculation	,
				usebills	,
				col11	,
				migratetime	,
				returnflag	,
				newborn	,
				newbornid	,
				outpatient	,
				repeatdeleteflag	,
				col12	,
				provincemigrate)
		select a.VFileNo,
				getdate(),
				0,
				'',--[儿童内码]
				'',--[儿童编码]
				dbo.denc(b.name),--[儿童姓名]
				c.sex,--[儿童性别]
				null,--[免疫卡号]
				null,--[免疫卡属性]
				REPLACE( CONVERT( CHAR(10), c.birthday, 102), '.', '-'),--[出生日期]
				a.VCensusAddressCounty+a.VCensusAddressTown+a.VCensusAddressVillage,--[户口地址]
				b.VFamilyAddress,--[家庭住址]
				b.Address,--[通讯地址]
				c.linkmanTel,--[家庭电话]
				null,--[邮件地址]
				null,--[呼机]
				c.tel,--[手机号码]
				'0',--[短信息定制]
				null,--[定制时间]
				a.VFatherName,--[父亲姓名]
				a.VFatherWorkUnit,--[父亲单位]
				a.VFatherPhone,--[父亲电话]
				a.VMotherName,--[母亲姓名]
				a.VMotherWorkUnit,--[母亲单位]
				a.VMotherPhone,--[母亲电话]
				null,--[母亲HB]
				'0',--[是否保偿]
				d.nowcode,--[开始保偿日期]
				null,--[结束保偿日期]
				null,--[保偿金额]
				'',--[儿童区域划分]
				'1',--[户籍属性]
				null,--[户籍变化]
				'1',--[户口属性]
				null,--[是否有禁忌症]
				'B',--[在册情况]
				null,--[在册备注]
				REPLACE( CONVERT( CHAR(10), a.VInputDate, 102),--[在册日期]
				null,--[在册变化]
				REPLACE( CONVERT( CHAR(10), a.VInputDate, 102), '.', '-'),--[建卡日期]
				null,--[发卡日期]
				null,--[儿童接种地点]
				null,--[备注]
				'',--[接种疫苗]
				'',--[接种时间1]
				'',--[接种时间2]
				'',--[登记时间1]
				'',--[登记时间2]
				'',--[疫苗接种地点1]
				'',--[疫苗接种地点2]
				'',--[疫苗接种地点3]
				'',--[疫苗接种地点4]
				'',--[接种标记]
				d.nowcode,--[原位置]
				d.nowcode,--[现位置]
				'x',--[数据状态]
				REPLACE( CONVERT( CHAR(10), a.VInputDate, 102), '.', '-')+ CONVERT( CHAR(8), a.VInputDate, 20) +"."+left(right(convert(varchar,a.VInputDate,109),5),3),--[修改日期]
				a.VInputDate,--[上传时间]
				'0',--[异地接种]
				'0',--[上传标志]
				null,--[打印预约单日期]
				null,--[全程服务]
				null,--[全程服务内容]
				0,--[选择]
				null,--[字段1]
				d.nowcode,--[字段2]
				null,--[疫苗批号]
				'',--[免费疫苗]
				null,--[当日就诊号]
				null,--[就诊日期]
				null,--[备用就诊号]
				null,--[人份]
				null,--[接种医师]
				null,--[接种位置]
				null,--[终端号]
				null,--[登记医师]
				null,--[就诊确认日期]
				null,--[儿童身份证]
				null,--[出生证编号]
				null,--[出生时间]
				null,--[出生医院]
				null,--[胎次]
				null,--[出生体重]
				null,--[父亲手机]
				null,--[母亲手机]
				'530521',--[户口县国标]
				null,--[传染病史]
				null,--[过敏史]
				null,--[接种反应史]
				null,--[禁忌症]
				d.fullname,--[建卡单位]
				a.VInputPersonid,--[建卡人]
				null,--[唯一码]
				null,--[是否入园]
				null,--[入园年度]
				null,--[幼儿园编码]
				null,--[卡介苗接种时间]
				null,--[乙肝接种时间]
				null,--[生产企业]
				null,--[民族]
				'',--[接种时间]
				null,--[票据编码]
				null,--[票据发放日期]
				a.VCertifiUnit,--[发卡单位]
				null,--[下载时间]
				null,--[下载单位]
				null,--[字段3]
				null,--[字段4]
				null,--[字段5]
				null,--[职业]
				null,--[上传单位]
				null,--[字段6]
				null,--[字段7]
				'2',--[字段8]
				null,--[字段9]
				'1',--[字段10]
				null,--[母亲身份证]
				null,--[父亲身份证]
				null,--[健康档案编码]
				'0',--[疤痕]
				'',--[接种途径]
				'',--[使用票据]
				'1',--[字段11]
				null,--[迁移时间]
				null,--[回传]
				null,--[新生儿]
				null,--[新生儿ID]
				null,--[归属门诊]
				null,--[查重删除]
				null,--[字段12]
				null--[省迁移]
		from VaccineImmune a  , HealthFile b , PersonalInfo c ,vacc_map_DepartMent d , sam_taxempcode e
		where a.VFileNo =  @fileno and a.VFileNo = b.fileno and a.VFileNo = c.fileno
		and a.VInputPersonid = e.loginname and e.org_id = d.org_id
	end
	--将原有信息清空
	update vacc_vaccinfo 
	set updatedate = getdate(),
		downloadflag = 0 ,
		vaccine='',
		vaccinationtime1 = '',
		vaccinationtime3 = '',
		vaccinationaddress1 = '',
		datastatus = 'X',
		freevaccine = '',
		vaccinationtime = '',
		inoculation = '',
		usebills = ''
	where fileNo = @fileno
	--取出此人的所有接种信息
	DECLARE vacc_cursor CURSOR FOR
	select a.VaccinationName,a.VaccinationDate,c.fullname,a.IsPlan from VaccineImmuneInfo a , sam_taxempcode b, vacc_map_DepartMent c
	where a.VFileNo = @fileno and a.InputPersonID = b.loginname and b.org_id = c.org_id 
	
	OPEN vacc_cursor
	FETCH NEXT FROM vacc_cursor INTO @value2

	
	
	
end
go
