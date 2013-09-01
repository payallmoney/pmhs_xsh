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
				'',--[��ͯ����]
				'',--[��ͯ����]
				dbo.denc(b.name),--[��ͯ����]
				c.sex,--[��ͯ�Ա�]
				null,--[���߿���]
				null,--[���߿�����]
				REPLACE( CONVERT( CHAR(10), c.birthday, 102), '.', '-'),--[��������]
				a.VCensusAddressCounty+a.VCensusAddressTown+a.VCensusAddressVillage,--[���ڵ�ַ]
				b.VFamilyAddress,--[��ͥסַ]
				b.Address,--[ͨѶ��ַ]
				c.linkmanTel,--[��ͥ�绰]
				null,--[�ʼ���ַ]
				null,--[����]
				c.tel,--[�ֻ�����]
				'0',--[����Ϣ����]
				null,--[����ʱ��]
				a.VFatherName,--[��������]
				a.VFatherWorkUnit,--[���׵�λ]
				a.VFatherPhone,--[���׵绰]
				a.VMotherName,--[ĸ������]
				a.VMotherWorkUnit,--[ĸ�׵�λ]
				a.VMotherPhone,--[ĸ�׵绰]
				null,--[ĸ��HB]
				'0',--[�Ƿ񱣳�]
				d.nowcode,--[��ʼ��������]
				null,--[������������]
				null,--[�������]
				'',--[��ͯ���򻮷�]
				'1',--[��������]
				null,--[�����仯]
				'1',--[��������]
				null,--[�Ƿ��н���֢]
				'B',--[�ڲ����]
				null,--[�ڲᱸע]
				REPLACE( CONVERT( CHAR(10), a.VInputDate, 102),--[�ڲ�����]
				null,--[�ڲ�仯]
				REPLACE( CONVERT( CHAR(10), a.VInputDate, 102), '.', '-'),--[��������]
				null,--[��������]
				null,--[��ͯ���ֵص�]
				null,--[��ע]
				'',--[��������]
				'',--[����ʱ��1]
				'',--[����ʱ��2]
				'',--[�Ǽ�ʱ��1]
				'',--[�Ǽ�ʱ��2]
				'',--[������ֵص�1]
				'',--[������ֵص�2]
				'',--[������ֵص�3]
				'',--[������ֵص�4]
				'',--[���ֱ��]
				d.nowcode,--[ԭλ��]
				d.nowcode,--[��λ��]
				'x',--[����״̬]
				REPLACE( CONVERT( CHAR(10), a.VInputDate, 102), '.', '-')+ CONVERT( CHAR(8), a.VInputDate, 20) +"."+left(right(convert(varchar,a.VInputDate,109),5),3),--[�޸�����]
				a.VInputDate,--[�ϴ�ʱ��]
				'0',--[��ؽ���]
				'0',--[�ϴ���־]
				null,--[��ӡԤԼ������]
				null,--[ȫ�̷���]
				null,--[ȫ�̷�������]
				0,--[ѡ��]
				null,--[�ֶ�1]
				d.nowcode,--[�ֶ�2]
				null,--[��������]
				'',--[�������]
				null,--[���վ����]
				null,--[��������]
				null,--[���þ����]
				null,--[�˷�]
				null,--[����ҽʦ]
				null,--[����λ��]
				null,--[�ն˺�]
				null,--[�Ǽ�ҽʦ]
				null,--[����ȷ������]
				null,--[��ͯ���֤]
				null,--[����֤���]
				null,--[����ʱ��]
				null,--[����ҽԺ]
				null,--[̥��]
				null,--[��������]
				null,--[�����ֻ�]
				null,--[ĸ���ֻ�]
				'530521',--[�����ع���]
				null,--[��Ⱦ��ʷ]
				null,--[����ʷ]
				null,--[���ַ�Ӧʷ]
				null,--[����֢]
				d.fullname,--[������λ]
				a.VInputPersonid,--[������]
				null,--[Ψһ��]
				null,--[�Ƿ���԰]
				null,--[��԰���]
				null,--[�׶�԰����]
				null,--[���������ʱ��]
				null,--[�Ҹν���ʱ��]
				null,--[������ҵ]
				null,--[����]
				'',--[����ʱ��]
				null,--[Ʊ�ݱ���]
				null,--[Ʊ�ݷ�������]
				a.VCertifiUnit,--[������λ]
				null,--[����ʱ��]
				null,--[���ص�λ]
				null,--[�ֶ�3]
				null,--[�ֶ�4]
				null,--[�ֶ�5]
				null,--[ְҵ]
				null,--[�ϴ���λ]
				null,--[�ֶ�6]
				null,--[�ֶ�7]
				'2',--[�ֶ�8]
				null,--[�ֶ�9]
				'1',--[�ֶ�10]
				null,--[ĸ�����֤]
				null,--[�������֤]
				null,--[������������]
				'0',--[�̺�]
				'',--[����;��]
				'',--[ʹ��Ʊ��]
				'1',--[�ֶ�11]
				null,--[Ǩ��ʱ��]
				null,--[�ش�]
				null,--[������]
				null,--[������ID]
				null,--[��������]
				null,--[����ɾ��]
				null,--[�ֶ�12]
				null--[ʡǨ��]
		from VaccineImmune a  , HealthFile b , PersonalInfo c ,vacc_map_DepartMent d , sam_taxempcode e
		where a.VFileNo =  @fileno and a.VFileNo = b.fileno and a.VFileNo = c.fileno
		and a.VInputPersonid = e.loginname and e.org_id = d.org_id
	end
	--��ԭ����Ϣ���
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
	--ȡ�����˵����н�����Ϣ
	DECLARE vacc_cursor CURSOR FOR
	select a.VaccinationName,a.VaccinationDate,c.fullname,a.IsPlan from VaccineImmuneInfo a , sam_taxempcode b, vacc_map_DepartMent c
	where a.VFileNo = @fileno and a.InputPersonID = b.loginname and b.org_id = c.org_id 
	
	OPEN vacc_cursor
	FETCH NEXT FROM vacc_cursor INTO @value2

	
	
	
end
go
