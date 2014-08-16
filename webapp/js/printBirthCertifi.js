(function(){
	printBirthObj = {
		print : print,
		init : init
	}
	
	function showPromptPrintSuccess(birthCertifiId){
		Ext.Msg.show({
			title:'提示',
			msg: '编号：' + birthCertifiId + '是否打印成功？<br/>如果成功，则此出生证明将归档！',
			buttons: Ext.Msg.YESNO,
			animEl: 'elId',
			icon: Ext.MessageBox.QUESTION,
			fn : function(e){
				if(e == 'yes'){
					BirthCertificateMsgService.printSuccess(birthCertifiId);
					sendMessage('quit');
				}else if(e == 'no'){
					sendMessage('quit');
				}
			}
		});
		
	}
	
	function init(){
		$('.birthCertificate').click(function(){
			var url = window.location.search;
			if(url.indexOf('certifiId') > 0){
				var json = UrlParse.parse();
				console.log(json);
				if(json != null){
					var birthCertifiId = json.certifiId;
					BirthCertificateMsgService.getBirthCertificate(birthCertifiId,function(data){
						if(data != null){
							printObj.printPreview(generateJson(data),0);
							showPromptPrintSuccess(birthCertifiId);
						}
					});
				}
			}
		});
	}
	
	function dataIsNull(value){
		if(value == null)
			return '';
		return value;
	}
	
	//打印数据获取
	function generateJson(data){
		
		//性别
		var sex = data.sex;
		
		//出生日期
		var dBirthday = data.birthday;
		var years = dBirthday.getFullYear();
		var month = dBirthday.getMonth() + 1;
		var day = dBirthday.getDate();
		var hour = dBirthday.getHours();
		var minute = dBirthday.getMinutes();
		
		//出生地
		var province = dataIsNull(data.province);
		var city = dataIsNull(data.city);
		var county = dataIsNull(data.county);
		var township = dataIsNull(data.township);
		
		//健康状况
		var healthStatus = data.healthStatus;
		var well = '';
		var normal = '';
		var weak = '';
		if(healthStatus == '良好'){
			well = '√';
		}else if(healthStatus == '一般'){
			normal = '√';
		}else if(healthStatus == '差'){
			weak = '√';
		}
		
		//出生机构
		var borthAddressCategory = dataIsNull(data.borthAddressCategory);
		var otherBorthAddressCategory = dataIsNull(data.otherBorthAddressCategory);
		if (otherBorthAddressCategory != ''){
			borthAddressCategory = borthAddressCategory + ':' + otherBorthAddressCategory;
		}
		var motherAddress = dataIsNull(data.motherAddress);
		var motherCardType = dataIsNull(data.motherCardType);
		var motherCard = '';
		var motherhz = '';
//		var motherOtherType = '';
		var motherOther = '';
		var fatherAddress = dataIsNull(data.fatherAddress);
		var fatherCardType = dataIsNull(data.fatherCardType);
		var fatherCard = '';
		var fatherhz = '';
//		var fatherOtherType = '';
		var fatherOther = '';
		
		if(motherCardType == '居民身份证'){
			motherCard = '√';
		}else if(motherCardType == '护照'){
			motherhz = '√';
		}else if(motherCardType == '其他'){
//			motherOtherType = '√';
			motherOther = data.motherCardTypeOther;
		}
		if(fatherCardType == '居民身份证'){
			fatherCard = '√';
		}else if(fatherCardType == '护照'){
			fatherhz = '√';
		}else if(fatherCardType == '其他'){
//			fatherOtherType = '√';
			fatherOther = data.motherCardTypeOther;
		}
		
		//签发日期
		var dissuingDate = data.issuingDate;
		var issueYear = dissuingDate.getFullYear();
		var issueMonth = dissuingDate.getMonth() + 1;
		var issueDay = dissuingDate.getDate();
		
		//打印的json串
		var jsonPrint = {
			fullNamfOfbaby : dataIsNull(data.name),//新生儿姓名
//			male : male,
//			female : female,
			sex : sex,//性别
			years : years,//出生年
			months : month,//出生月
			day : day,//出生日			
			hour : hour,//出生小时
			minute : minute,//出生分
			province :province.replace("省",""),//出生地省
			city : city.replace("市",""),//出生地市
			county : county.replace("县",""),//出生地县
//			township : township,
//			birthPlace : county + township,
			weeks :dataIsNull(data.borthWeekly),//孕周
//			well : well,
//			normal : normal,
//			weak : weak,
			weight : dataIsNull(data.weight),//体重
			height : dataIsNull(data.height),//身长
			motherName : dataIsNull(data.motherName),//母亲姓名
			motherAge : dataIsNull(data.motherAge),//母亲年龄
			motherNationality : dataIsNull(data.motherNationality),//母亲国籍
			motherNation : dataIsNull(data.motherNation),//母亲民族
			motherIdCard : dataIsNull(data.motherIdCard),//母亲身份证号
			fatherName : dataIsNull(data.fatherName),//父亲姓名
			fatherAge : dataIsNull(data.fatherAge),//父亲年龄
			fatherNationality : dataIsNull(data.fatherNationality),//父亲国籍
			fatherNation : dataIsNull(data.fatherNation),//父亲民族
			fatherIdCard : dataIsNull(data.fatherIdCard),//父亲身份证号
			borthAddressCategory : dataIsNull(borthAddressCategory),//医疗机构名称
			motherAddress : dataIsNull(motherAddress),//母亲住址
			fatherAddress : dataIsNull(fatherAddress),//父亲住址
			motherCard : dataIsNull(motherCard),//母亲有效身份证
			motherhz : dataIsNull(motherhz),//母亲护照
			motherOther : dataIsNull(motherOther),//母亲有效身份证件其他
			fatherCard : dataIsNull(fatherCard),//父亲有效身份证
			fatherhz : dataIsNull(fatherhz),//父亲护照
			fatherOther : dataIsNull(fatherOther),//父亲有效身份证件其他
			issuingOrganization : dataIsNull(data.issuingOrganization),//签发机构
			certifiId : dataIsNull(data.certifiId),//出生医学证明编号
//			generalHospital : generalHospital,
//			MCHhospital : MCHhospital,
//			home : home,
//			otherRemark : otherRemark,
//			other : other,
//			nameOfFacility : data.borthOrganization,//接生机构
			issueYear :issueYear,//签发日期年
			issueMonth : issueMonth,//签发日期月
			issueDay : issueDay,//签发日期日
//			familyAddress : data.familyAddress,//家庭住址
			widwife : dataIsNull(data.widWife),// 接生人员
			issuingUsers : dataIsNull(data.issuingUsers)//签发人员
		}
		return jsonPrint;
	}
	
	function print(birthCertifiId){
		if(birthCertifiId == '0'){
			Ext.Msg.show({
				title:'错误',
				msg: '保存错误，原出生医学证明编号填写不正确或者原出生医学证明还没有归档，请先进行归档操作。',
				buttons: Ext.Msg.OK,
				animEl: 'elId',
				icon: Ext.MessageBox.ERROR
			});
		}else if(birthCertifiId == "1"){
			Ext.Msg.show({
				title:'错误',
				msg: '保存错误，您没有权限补发出生医学证明。',
				buttons: Ext.Msg.OK,
				animEl: 'elId',
				icon: Ext.MessageBox.ERROR
			});
		}else{
			Ext.Msg.show({
				title:'提示',
				msg: '保存成功，是否打印出生医学证明？',
				buttons: Ext.Msg.YESNO,
				animEl: 'elId',
				icon: Ext.MessageBox.QUESTION,
				fn : function(e){
					if(e == 'yes'){
						BirthCertificateMsgService.getBirthCertificate(birthCertifiId,function(data){
							if(data != null){
								printObj.printPreview(generateJson(data),0);
								showPromptPrintSuccess(birthCertifiId);
							}
						});
					}else if(e == 'no'){
						sendMessage('quit');
					}
				}
			});
		}
	}
})();