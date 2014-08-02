PrintHealthFileAndExamClass = function() {
	// 组件初始化
};
PrintHealthFileAndExamClass.prototype.addZero = function(val){
	if(val < 10){
		return '0' + val;
	}
	return val;
}
PrintHealthFileAndExamClass.prototype.judgeDate = function(judgeType,date){
	if((date == null || date == ' ' || date == '' || date == -1)){
		if(judgeType == 0)
			return '未测';
	}else if(date instanceof Date){
		return date.getFullYear() + '' + PrintHealthFileAndExamClass.addZero(date.getMonth()) + '' + PrintHealthFileAndExamClass.addZero(date.getDate());
	}
	return date;
}

PrintHealthFileAndExamClass.prototype.printHealthFile = function(fileNo){
	var cond = {printType : '0',printWhere : fileNo};
	console.log(cond);
	var healthFileHtml = '<style type="text/css">.printHealthFile,.table_personalInfor {	font-size: 14px;}.td_printHealthFile_content {	border-left: 1px solid #000000;	border-right: 1px solid #000000;	padding-left: 160px;	height: 60px;}.healthfileBasicInfo td {	height:60px;	vertical-align: bottom;}.rightTableTd{	border-bottom: 1px solid #000000;}.table_personalInfor tbody td {	border-left: 1px solid #000;	border-right: 1px solid #000;	border-top: 1px solid #000;	height: 30px;	padding-left: 5px;}.childTable thead td {	border: none;}.childTable tbody td {	border: none;}</style><div class="printHealthFile">		<table class="table_printHealthFile" cellpadding="0" cellspacing="0"			style="width: 19cm; height: 27cm; border-collapse: collapse;">			<tr>				<td					style="text-align: right; font-weight: bolder; border-left: 1px solid #000000; border-right: 1px solid #000000; border-top: 1px solid #000000; font-size: 16px; padding-right: 10px; padding-top: 10px; height: 80px;">编号：					<span class="FileNo"></span></td>			</tr>			<tr>				<td					style="text-align: center; height:100px;font-size: 20px; border-left: 1px solid #000000; border-right: 1px solid #000000; font-weight: bolder; background-color: #FFFFFF">居民健康档案</td>			</tr>			<tr>				<td style="width: 100%;padding-bottom: 40px; border-bottom: 1px solid #000000;border-left: 1px solid #000000;border-right: 1px solid #000000; vertical-align:top;">					<table cellpadding="0" cellspacing="0" class="healthfileBasicInfo"						style="border-collapse: none; width: 70%;margin-left: 60px;">						<tr>							<td style="width:200px;text-align: right;">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>							<td class="rightTableTd"><span class="Name">12</span></td>						</tr>						<tr>							<td style="text-align: right;" >现&nbsp;&nbsp;住&nbsp;&nbsp;址：</td>							<td class="rightTableTd" ><span class="Address"></span></td>						</tr>						<tr>							<td style="text-align: right;">户籍地址：</td>							<td class="rightTableTd"><span class="ResidenceAddress"></span></td>						</tr>						<tr>							<td style="text-align: right;">联系电话：</td>								<td class="rightTableTd"><span class="TEL"></span></td>						</tr>						<tr>							<td style="text-align: right;">乡镇（街道）名称：</td>								<td class="rightTableTd"><span class="Township"></span></td>						</tr>						<tr>							<td style="text-align: right;">村（居）委会名称：</td>								<td class="rightTableTd" ><span class="Village"></span></td>						</tr>						<tr>							<td style="height: 150px;">&nbsp;</td>						</tr>						<tr>							<td style="text-align: right;">建档单位：</td>								<td class="rightTableTd" ><span class="BuildUnit"></span></td>						</tr>						<tr>							<td style="text-align: right;">建&nbsp;&nbsp;档&nbsp;&nbsp;人：</td>								<td class="rightTableTd" ><span class="BuildPerson"></span></td>						</tr>						<tr>							<td style="text-align: right;">责任医生：</td>								<td class="rightTableTd" ><span class="Doctor"></span></td>						</tr>						<tr>							<td style="text-align: right;"								>建档日期：</td>							<td class="rightTableTd" ><span class="BuildDate"></span></td>						</tr>					</table>				</td>			</tr>		</table>		<table class="table_personalInfor" cellpadding="0" cellspacing="0"			style="width: 19cm; border-collapse: collapse; margin-top: 60px;">			<thead>				<tr>					<td style="text-align: center; height: 30px;" colspan="7"><span						style="font-size: 20px; font-weight: bolder;">个人基本信息表</span></td>				</tr>				<tr>					<td colspan="2"><span						style="font-size: 16px; font-weight: bolder; text-align: right;">姓名：</span></td>					<td colspan="3"><span class="Name" ></span></td>					<td><span style="font-size: 16px; font-weight: bolder;">编号：</span></td>					<td><span class="FileNo" ></span></td>				</tr>			</thead>			<tbody>				<tr>					<td colspan="2">性别</td>					<td colspan="3"><div class="Sex"></div></td>					<td>出生日期</td>					<td><div class="Birthday"></div></td>				</tr>				<tr>					<td colspan="2">身份证号</td>					<td colspan="3"><div class="IDNumber"></div></td>					<td>工作单位</td>					<td><div class="WorkUnit"></div></td>				</tr>				<tr>					<td colspan="2">本人电话</td>					<td style="width: 150px;"><input class="TEL"						style="border: none; width: 150px;" readonly="readonly"						type="text" /></td>					<td style="width: 80px;">联系人姓名</td>					<td style="width: 150px;"><div class="Linkman"></div></td>					<td style="width: 80px;">联系人电话</td>					<td><div class="LinkmanTEL"></div></td>				</tr>				<tr>					<td colspan="2">常住类型</td>					<td><div class="ResideType"></div></td>					<td>民 族</td>					<td colspan="3"><div class="Folk"></div>						<div class="FolkOther"></div></td>				</tr>				<tr>					<td colspan="2">血型</td>					<td colspan="3"><div class="BloodTypeABO"></div></td>					<td>RH阴性</td>					<td><div class="BloodTypeRH"></div></td>				</tr>				<tr>					<td colspan="2">文化程度</td>					<td colspan="5"><div class="Education"></div></td>				</tr>				<tr>					<td colspan="2">职业</td>					<td colspan="5"><div class="Occupation"></div></td>				</tr>				<tr>					<td colspan="2">婚姻状况</td>					<td colspan="5"><div class="MaritalStatus"></div></td>				</tr>				<tr>					<td colspan="2" style="height: 40px;">医疗费用<br />支付方式					</td>					<td colspan="5"><div class="PaymentMode"></div>						<div class="PaymentModeOther"></div></td>				</tr>				<tr>					<td colspan="2">药物过敏史</td>					<td colspan="5"><div class="AllergiesHistory"></div>						<div class="AllergiesOther"></div></td>				</tr>				<tr>					<td colspan="2">暴露史</td>					<td colspan="5"><div class="ExposeHistory"></div></td>				</tr>				<tr>					<td rowspan="4" style="width: 20px;">既 <br />往<br /> 史					</td>					<td>疾病</td>					<td colspan="5" style="height: 100%;">						<table class="DiseaseHistory childTable" cellpadding="0"							cellspacing="0" style="border-collapse: none; width: 100%;">							<thead>								<tr>									<td>疾病名称</td>									<td>确诊时间</td>									<td>疾病说明</td>								</tr>							</thead>						</table>					</td>				</tr>				<tr>					<td>手 术</td>					<td colspan="5" style="height: 100%;">						<table class="OPSHistory childTable" cellpadding="0"							cellspacing="0" style="border-collapse: none; width: 100%;">							<thead>								<tr>									<td>名称</td>									<td>时间</td>								</tr>							</thead>						</table>					</td>				</tr>				<tr>					<td>外 伤</td>					<td colspan="5" style="height: 100%;">						<table class="TraumaHistory childTable" cellpadding="0"							cellspacing="0" style="border-collapse: none; width: 100%;">							<thead>								<tr>									<td>名称</td>									<td>时间</td>								</tr>							</thead>						</table>					</td>				</tr>				<tr>					<td>输 血</td>					<td colspan="5" style="height: 100%;">						<table class="BloodTrans childTable" cellpadding="0"							cellspacing="0" style="border-collapse: none; width: 100%;">							<thead>								<tr>									<td>名称</td>									<td>时间</td>								</tr>							</thead>						</table>					</td>				</tr>				<tr>					<td rowspan="2">家<br />族<br />史					</td>					<td style="height: 100%;">父亲</td>					<td colspan="2"><div class="FatherHistory"></div>						<div class="fHistoryOther"></div></td>					<td>母 亲</td>					<td colspan="2"><div class="MatherHistory"></div>						<div class="mHistoryOther"></div></td>				</tr>				<tr>					<td style="height: 100%;">兄弟姐妹</td>					<td colspan="2"><div class="BrotherHistor"></div>						<div class="bHistoryOther"></div></td>					<td>子 女</td>					<td colspan="2"><div class="FamilyHistory"></div>						<div class="fmHistoryOther"></div></td>				</tr>				<tr>					<td colspan="2">遗传病史</td>					<td colspan="5"><div class="GeneticHistory"></div>						<div class="GeneticHistoryOther"></div></td>				</tr>				<tr>					<td colspan="2">残疾情况</td>					<td colspan="5"><div class="DisabilityStatus"></div>						<div class="DisabilityStatusOther"></div></td>				</tr>				<tr>					<td rowspan="5" colspan="2" style="border-bottom: 1px solid #000;">生活环境</td>					<td>厨房排风设施</td>					<td colspan="4"><div class="Kitchen"></div></td>				</tr>				<tr>					<td>燃料类型</td>					<td colspan="4"><div class="Bunkers"></div></td>				</tr>				<tr>					<td>饮水</td>					<td colspan="4"><div class="DrinkingWater"></div></td>				</tr>				<tr>					<td>厕所</td>					<td colspan="4"><div class="Toilet"></div></td>				</tr>				<tr>					<td style="border-bottom: 1px solid #000;">禽畜栏</td>					<td colspan="4" style="border-bottom: 1px solid #000;"><div							class="Poultry"></div></td>				</tr>			</tbody>		</table>	</div>';
	printerService.printer(cond,function(data){
		console.log(data);
		$('#printHealthFile').html(healthFileHtml);
		if(data != null && data.length == 1){
			var obj = data[0];
			var listJson = {
				BloodTrans : 2,
				DiseaseHistory : 3,
				OPSHistory : 2,
				TraumaHistory : 2
			}
			for(var p in obj){
				if (listJson.hasOwnProperty(p)){
					var column = listJson[p];
					var listArray = Ext.util.JSON.decode(obj[p]);
					if(listArray != null){
						var tbody = "<tbody>";
						for(var i=0;i< listArray.length;i++){
							tbody = tbody + '<tr>';
							var td = '';
							for(var prop in listArray[i]){
								td = td + '<td>' + PrintHealthFileAndExamClass.judgeDate(1,listArray[i][prop]) + '</td>';
							}
							tbody = tbody + td + '</tr>';
						}
						tbody = tbody + '</tbody>';
						$('.' + p).append(tbody);
					}
				}else{
					$('.' + p).html(PrintHealthFileAndExamClass.judgeDate(0,obj[p]));
				}
			}
			printObj.printHTML($('#printHealthFile').html(),'居民健康档案','19cm','28cm');
		}else{
			//提示信息 
		}
	});
}

PrintHealthFileAndExamClass.prototype.printMedicalExam = function(id){
	var cond = {printType : '1',printWhere : id};
	var healthFileHtml = '<style type="text/css">.printMedicalExam {	font-size: 14px;}.printMedicalExam table {	width: 19cm;}.table_page tbody td {	border-left: 1px solid #000;	border-right: 1px solid #000;	border-top: 1px solid #000;	height: 28.5px;	padding-left: 5px;}.table_page tbody td span{	font-size: 12px;}.childTable thead td {	border: none;}.childTable tbody td {	border: none;} .printMedicalExam td{white-space: nowrap;}.unitSpace{	padding-left: 5px;	padding-right: 20px;}.unitSpaceend{	padding-left: 5px;}</style><div class="printMedicalExam">		<table class="table_page" cellpadding="0" cellspacing="0"			style="border-collapse: collapse; margin-top: 20px;">			<thead>				<tr>					<td style="text-align: center; height: 50px; vertical-align: top;"						colspan="7"><span						style="font-size: 20px; font-weight: bolder;">健康体检表</span></td>				</tr>				<tr>					<td style="text-align: right;height: 30px;"><span						style="font-size: 16px; font-weight: bolder;">姓名：</span></td>					<td colspan="4"><span class="Name"></span></td>					<td><span style="font-size: 16px; font-weight: bolder;">编号：</span></td>					<td><span class="FileNo"></span></td>				</tr>			</thead>			<tbody>				<tr>					<td style="width: 65px;">体检日期</td>					<td colspan="3"><span class="ExamDate"></span></td>					<td style="width: 120px;">责任医生</td>					<td colspan="2"><span class="Doctor">12</span></td>				</tr>				<tr>					<td style="font-weight: bold; text-align: center;">内 容</td>					<td colspan="6" style="font-weight: bold; text-align: center;">检						查 项 目</td>				</tr>				<tr>					<td style="text-align: center; height: 45px;">症状</td>					<td colspan="6"><span class="ExamSymptom"></span></td>				</tr>				<tr>					<td rowspan="9" style="text-align: center;">一<br />般<br />状<br />况					</td>					<td style="width: 110px;">体温</td>					<td colspan="2"><span class="General01 unitSpace"></span>℃</td>					<td>脉率</td>					<td colspan="2"><span class="General02 unitSpace"></span>次/分钟</td>				</tr>				<tr>					<td rowspan="2">呼吸频率</td>					<td rowspan="2" colspan="2"><span class="General03 unitSpace"></span>次/分钟</td>					<td rowspan="2">血压</td>					<td style="width: 65px;">左侧</td>					<td><span class="General04" style="padding-left: 5px;padding-right: 5px;"></span>/<span class="General05"  style="padding-left: 5px;padding-right: 5px;"></span>mmHg</td>				</tr>				<tr>					<td>右侧</td>					<td><span class="General06"  style="padding-left: 5px;padding-right: 5px;"></span>/<span class="General07"  style="padding-left: 5px;padding-right: 5px;"></span>mmHg</td>				</tr>				<tr>					<td>身高</td>					<td colspan="2"><span class="General08 unitSpace"></span>cm</td>					<td>体重</td>					<td colspan="2"><span class="General09 unitSpace"></span>kg</td>				</tr>				<tr>					<td>腰围</td>					<td colspan="2"><span class="General10 unitSpace"></span>cm</td>					<td>体质指数（BMI）</td>					<td colspan="2"><span class="General11 unitSpace"></span>Kg/m2</td>				</tr>				<tr>					<td colspan="2">老年人健康状态自我评估*					</td>					<td colspan="4"><span class="OldManHealthEstimate"></span></td>				</tr>				<tr>					<td colspan="2">老年人生活自理能力自我评估*					</td>					<td colspan="4"><span class="OldManLifeEstimate"></span></td>				</tr>				<tr>					<td colspan="2">老年人认知功能*</td>					<td><span class="General14"></span></td>					<td style="height: 45px;">简易智力<br />状态检查					</td>					<td colspan="2">总分<span class="General15"></span></td>				</tr>				<tr>					<td colspan="2">老年人情感状态*</td>					<td><span class="General16"></span></td>					<td style="height: 45px;" nowrap>老年人抑郁<br />评分检查					</td>					<td colspan="2">总分<span class="General17"></span></td>				</tr>				<tr>					<td rowspan="19"						style="text-align: center; border-bottom: 1px solid #000;">生<br />活<br />方<br />式					</td>					<td rowspan="3">体育锻炼</td>					<td style="width: 100px;">锻炼频率</td>					<td colspan="4"><span class="Life01"></span></td>				</tr>				<tr>					<td>每次锻炼时间</td>					<td><span class="Life02 unitSpace"></span>分钟</td>					<td>坚持锻炼时间</td>					<td colspan="2"><span class="Life03 unitSpace"></span>年</td>				</tr>				<tr>					<td>锻炼方式</td>					<td colspan="4"><span class="Life04"></span></td>				</tr>				<tr>					<td>饮食习惯</td>					<td colspan="5"><span class="EatHabit"></span></td>				</tr>				<tr>					<td rowspan="3">吸烟情况</td>					<td>吸烟状况</td>					<td colspan="4"><span class="Life06"></span></td>				</tr>				<tr>					<td>日吸烟量</td>					<td colspan="4">平均<span class="Life07 unitSpace"></span>支					</td>				</tr>				<tr>					<td>开始吸烟年龄</td>					<td><span class="Life08 unitSpace"></span>岁</td>					<td>戒烟年龄</td>					<td colspan="2"><span class="Life09 unitSpace"></span>岁</td>				</tr>				<tr>					<td rowspan="5">饮酒情况</td>					<td>饮酒频率</td>					<td colspan="4"><span class="Life10"></span></td>				</tr>				<tr>					<td>日饮酒量</td>					<td colspan="4">平均<span class="Life11 unitSpace"></span>两					</td>				</tr>				<tr>					<td>是否戒酒</td>					<td colspan="2"><span class="Life12"></span>					</td>					<td>戒酒年龄</td>					<td><span class="Life13 unitSpace"></span>岁</td>				</tr>				<tr>					<td>开始饮酒年龄</td>					<td><span class="Life14 unitSpace"></span>岁</td>					<td style="height: 45px;" nowrap>近一年内是否<br />曾醉酒					</td>					<td colspan="2"><span class="Life15"></span></td>				</tr>				<tr>					<td>饮酒种类</td>					<td colspan="4"><span class="DrinkHabit"></span></td>				</tr>				<tr>					<td rowspan="6"						style="border-bottom: 1px solid #000; height: 45px;">职业病危害<br />因素接触史					</td>					<td colspan="5"><span class="Life17"></span><span						style="display: none;">工种<span class="Life18"></span>从业时间<span							class="Life19"></span></span></td>				</tr>				<tr>					<td rowspan="5" style="border-bottom: 1px solid #000;">毒物种类</td>					<td style="width: 120px;">粉尘</td>					<td><span class="Life20"></span></td>					<td>防护措施</td>					<td><span class="Life21"></span></td>				</tr>				<tr>					<td>放射物质</td>					<td><span class="Life22"></span></td>					<td>防护措施</td>					<td><span class="Life23"></span></td>				</tr>				<tr>					<td>物理因素</td>					<td><span class="Life24"></span></td>					<td>防护措施</td>					<td><span class="Life25"></span></td>				</tr>				<tr>					<td>化学物质</td>					<td><span class="Life26"></span></td>					<td>防护措施</td>					<td><span class="Life27"></span></td>				</tr>				<tr>					<td style="border-bottom: 1px solid #000;">其他</td>					<td style="border-bottom: 1px solid #000;"><span						class="Life28"></span></td>					<td style="border-bottom: 1px solid #000;">防护措施</td>					<td style="border-bottom: 1px solid #000;"><span						class="Life29"></span></td>				</tr>			</tbody>		</table>				<table class="table_page" cellpadding="0" cellspacing="0"			style="border-collapse: collapse; margin-top: 60px;">			<tbody>				<tr>					<td rowspan="7" style="width: 60px;text-align: center;">脏<br/>器<br/>功<br/>能</td>					<td rowspan="4" style="width: 100px;">口腔</td>					<td style="width: 100px;">口唇</td>					<td colspan="4"><span class="Viscera01"></span></td>				</tr>				<tr>					<td rowspan="2">齿列</td>					<td rowspan="2"><span class="Viscera02"></span></td>					<td rowspan="2" style="width: 80px;">齿列描述</td>					<td><span class="Viscera02Description"></span></td>					<td><span class="Viscera02Description1"></span></td>				</tr>				<tr>					<td><span class="Viscera02Description2"></span></td>					<td><span class="Viscera02Description3"></span></td>				</tr>				<tr>					<td>咽部</td>					<td colspan="4"><span class="Viscera03"></span></td>				</tr>				<tr>					<td>视力</td>					<td colspan="5">左眼<span class="Viscera04"></span>&nbsp;&nbsp;右眼<span class="Viscera05"></span>&nbsp;&nbsp;(矫正视力：左眼<span class="Viscera06"></span>&nbsp;&nbsp;右眼<span class="Viscera07"></span>)</td>				</tr>				<tr>					<td>听力</td>					<td colspan="5"><span class="Viscera08"></span></td>				</tr>				<tr>					<td>运动能力</td>					<td colspan="5"><span class="Viscera09"></span></td>				</tr>				<tr>					<td rowspan="24">查体</td>					<td>眼底*</td>					<td colspan="5"><span class="Exam29"></span></td>				</tr>				<tr>					<td>皮肤</td>					<td colspan="5"><span class="Body01"></span></td>				</tr>				<tr>					<td>巩膜</td>					<td colspan="5"><span class="Body02"></span></td>				</tr>				<tr>					<td>淋巴结</td>					<td colspan="5"><span class="Body03"></span></td>				</tr>				<tr>					<td rowspan="3">肺</td>					<td>桶状胸</td>					<td colspan="4"><span class="Body04"></span></td>				</tr>				<tr>					<td>呼吸音</td>					<td colspan="4"><span class="Body05"></span></td>				</tr>				<tr>					<td>罗音</td>					<td colspan="4"><span class="Body06"></span></td>				</tr>				<tr>					<td rowspan="2">心脏</td>					<td>心率</td>					<td><span class="Body07 unitSpace"></span>次/分钟</td>					<td>心律</td>					<td colspan="2"><span class="Body08"></span></td>				</tr>				<tr>					<td>杂音</td>					<td colspan="4"><span class="Body09"></span></td>				</tr>				<tr>					<td rowspan="5">腹部</td>					<td>压痛</td>					<td colspan="4"><span class="Body10"></span></td>				</tr>				<tr>					<td>包块</td>					<td colspan="4"><span class="Body12"></span></td>				</tr>				<tr>					<td>肝大</td>					<td colspan="4"><span class="Body13"></span></td>				</tr>				<tr>					<td>脾大</td>					<td colspan="4"><span class="Body14"></span></td>				</tr>				<tr>					<td>移动性浊音</td>					<td colspan="4"><span class="Body15"></span></td>				</tr>				<tr>					<td>下肢水肿</td>					<td colspan="5"><span class="Body16"></span></td>				</tr>				<tr>					<td>足背动脉博动</td>					<td colspan="5"><span class="Body17"></span></td>				</tr>				<tr>					<td>肛门指诊*</td>					<td colspan="5"><span class="Body18"></span></td>				</tr>				<tr>					<td>乳腺*</td>					<td colspan="5"><span class="Galactophore"></span></td>				</tr>				<tr>					<td rowspan="5">妇科</td>					<td>外阴*</td>					<td colspan="4"><span class="Body20"></span></td>				</tr>				<tr>					<td>阴道*</td>					<td colspan="4"><span class="Body21"></span></td>				</tr>				<tr>					<td>宫颈*</td>					<td colspan="4"><span class="Body22"></span></td>				</tr>				<tr>					<td>宫体*</td>					<td colspan="4"><span class="Body23"></span></td>				</tr>				<tr>					<td>附件*</td>					<td colspan="4"><span class="Body24"></span></td>				</tr>				<tr>					<td>其它*</td>					<td colspan="5" ><span class="Body25"></span></td>				</tr>				<tr>					<td rowspan="4" style="border-bottom: 1px solid #000;">辅助<br/>检查</td>					<td rowspan="2" style="border-bottom: 1px solid #000;">血常规*</td>					<td colspan="5">血红蛋白：<span class="Exam03 unitSpace"></span>g/L白细胞：<span class="Exam04 unitSpace"></span>×10<sup>9</sup>/L血小板：<span class="Exam05 unitSpace"></span>×10<sup>9</sup>/L</td>				</tr>				<tr>					<td>其它</td>					<td colspan="4"><span class="Exam06"></span></td>				</tr>				<tr>					<td rowspan="2" style="border-bottom: 1px solid #000;">尿常规*</td>					<td colspan="5">尿蛋白：<span class="Exam07 unitSpace"></span>尿糖：<span class="Exam08 unitSpace"></span>尿酮体：<span class="Exam09 unitSpace"></span></td>				</tr>				<tr>					<td colspan="5" style="border-bottom: 1px solid #000;">尿潜血：<span class="Exam10 unitSpace"></span>其它：<span class="Exam11"></span></td>				</tr>			</tbody>		</table>				<table class="table_page" cellpadding="0" cellspacing="0"			style="border-collapse: collapse; margin-top: 60px;">			<tbody>				<tr>					<td rowspan="26" style="width: 60px; text-align: center;">辅<br/>助<br/>检<br/>查</td>					<td style="width: 140px;">空腹血糖*</td>					<td colspan="2"><span class="Exam01 unitSpace"></span>mmol/L 或 (餐后)<span class="Exam02 unitSpace"></span>mmol/L</td>				</tr>				<tr>					<td>心电图*</td>					<td colspan="2"><span class="Exam30"></span></td>				</tr>				<tr>					<td>尿微量白蛋白*</td>					<td colspan="2"><span class="Exam12"></span></td>				</tr>				<tr>					<td>大便潜血*</td>					<td colspan="2"><span class="Exam13"></span></td>				</tr>				<tr>					<td>糖化血红蛋白*</td>					<td colspan="2"><span class="Exam27"></span></td>				</tr>				<tr>					<td>乙型肝炎表面抗原*</td>					<td colspan="2"><span class="Exam28"></span></td>				</tr>				<tr>					<td rowspan="2">肝功能*</td>					<td colspan="2">血清谷丙转氨酶：<span class="Exam14 unitSpace"></span>U/L血清谷草转氨酶：<span class="Exam15 unitSpace"></span>U/L白蛋白：<span class="Exam16 unitSpaceend"></span>U/L					</td>				</tr>				<tr>					<td colspan="2">总胆红素：<span class="Exam17 unitSpace"></span>µmol结合胆红素：<span class="Exam18 unitSpace"></span>µmol					</td>				</tr>				<tr>					<td rowspan="2">肾功能*</td>					<td colspan="2">血清肌酐：<span class="Exam19 unitSpace"></span>µmol血尿素氮:<span class="Exam20 unitSpace"></span>mmol					</td>				</tr>				<tr>					<td colspan="2">血钾浓度：<span class="Exam21 unitSpace"></span>mmol血纳浓度：<span class="Exam22 unitSpace"></span>mmol					</td>				</tr>				<tr>					<td rowspan="3">血脂*</td>					<td colspan="2">总胆固醇：<span class="Exam23 unitSpace"></span>mmol/L甘油三酯：<span class="Exam24 unitSpace"></span>mmol/L					</td>				</tr>				<tr>					<td colspan="2">血清低密度脂蛋白胆固醇:<span class="Exam25 unitSpace"></span>mmol/L					</td>				</tr>				<tr>					<td colspan="2">血清高密度脂蛋白胆固醇:<span class="Exam26 unitSpace"></span>mmol/L					</td>				</tr>				<tr>					<td>胸片X线片*</td>					<td colspan="2"><span class="Exam31"></span></td>				</tr>				<tr>					<td>B超*</td>					<td colspan="2"><span class="Exam32"></span></td>				</tr>				<tr>					<td>宫颈涂片*</td>					<td colspan="2"><span class="Exam33"></span></td>				</tr>				<tr>					<td>其他*</td>					<td colspan="2"><span class="Exam34"></span></td>				</tr>				<tr>					<td rowspan="9">中医体质辨识*</td>					<td style="width: 60px;">平和质</td>					<td><span class="CHN01">&nbsp;</span></td>				</tr>				<tr>					<td>气虚质</td>					<td><span class="CHN02"></span></td>				</tr>				<tr>					<td>阳虚质</td>					<td><span class="CHN03"></span></td>				</tr><tr>					<td>阴虚质</td>					<td><span class="CHN04"></span></td>				</tr><tr>					<td>痰湿质</td>					<td><span class="CHN05"></span></td>				</tr><tr>					<td>湿热质</td>					<td><span class="CHN06"></span></td>				</tr><tr>					<td>血瘀质</td>					<td><span class="CHN07"></span></td>				</tr><tr>					<td>气郁质</td>					<td><span class="CHN08"></span></td>				</tr><tr>					<td>特秉质</td>					<td><span class="CHN09"></span></td>				</tr>				<tr>					<td rowspan="7" style="border-bottom: 1px solid #000;">现在<br/>主要<br/>健康<br/>问题</td>					<td style="height: 45px;">脑血管疾病</td>					<td colspan="2"><span class="HarnsSick"></span></td>				</tr>				<tr>					<td style="height: 45px;">肾脏疾病</td>					<td colspan="2"><span class="KidneySick"></span></td>				</tr>				<tr>					<td style="height: 45px;">心脏疾病</td>					<td colspan="2"><span class="HeartSick"></span></td>				</tr>				<tr>					<td style="height: 45px;">血管疾病</td>					<td colspan="2"><span class="VasSick"></span></td>				</tr>				<tr>					<td style="height: 45px;">眼部疾病</td>					<td colspan="2"><span class="EyeSick"></span></td>				</tr>				<tr>					<td>神经系统疾病</td>					<td colspan="2"><span class="Problem06"></span></td>				</tr>				<tr>					<td style="border-bottom: 1px solid #000;">其它系统疾病</td>					<td style="border-bottom: 1px solid #000;" colspan="2"><span class="Problem07"></span></td>				</tr>			</tbody>		</table>				<table class="table_page" cellpadding="0" cellspacing="0"			style="border-collapse: collapse; margin-top: 60px;">			<tbody>				<tr>					<td>住院治疗情况</td>					<td style="height: 100%;">						<table class="Hospitalization childTable" cellpadding="0" cellspacing="0" style="border-collapse: none;width: 100%;">							<thead>								<tr>									<td>住院类型</td>									<td>入院/建床日期</td>									<td>出院/撤床日期</td>									<td>原因</td>									<td>医疗机构名称</td>									<td>病案号</td>								</tr>							</thead>						</table>					</td>				</tr>				<tr>					<td>主要用药情况</td>					<td style="height: 100%;">						<table class="ExamMedication childTable" cellpadding="0" cellspacing="0" style="border-collapse: none;width: 100%;">							<thead>								<tr>									<td>药名</td>									<td>用法</td>									<td>用量</td>									<td>用药时间</td>									<td>服药依从性</td>								</tr>							</thead>						</table>					</td>				</tr>				<tr>					<td>非免疫规划<br/>预防接种史</td>					<td style="height: 100%;">						<table class="VaccinateHistory childTable" cellpadding="0" cellspacing="0" style="border-collapse: none;width: 100%;">							<thead>								<tr>									<td>名称</td>									<td>接种日期</td>									<td>接种机构</td>								</tr>							</thead>						</table>					</td>				</tr>				<tr>					<td rowspan="5">健康评价</td>					<td><span class="Judge01"></span></td>				</tr>				<tr>					<td>异常1:<span class="Judge02"></span></td>				</tr>				<tr>					<td>异常2:<span class="Judge03"></span></td>				</tr>				<tr>					<td>异常3:<span class="Judge04"></span></td>				</tr>				<tr>					<td>异常4:<span class="Judge05"></span></td>				</tr>				<tr>					<td>健康指导</td>					<td><span class="HealthDirect"></span></td>				</tr>				<tr>					<td style="border-bottom: 1px solid #000;">危险因素控制</td>					<td style="border-bottom: 1px solid #000;"><span class="DangerControl"></span></td>				</tr>			</tbody>		</table>	</div>';
	printerService.printer(cond,function(data){
		console.log(data);
		$('#printHealthFile').html(healthFileHtml);
		if(data.length == 1){
			var obj = data[0];
			var listJson = {
				Hospitalization : 'Hospitalization',
				ExamMedication : 'ExamMedication',
				VaccinateHistory : 'VaccinateHistory'
			}
		
			for(var p in obj){
				if (listJson.hasOwnProperty(p)){
					var listArray = Ext.util.JSON.decode(obj[p]);
					if(listArray != null){
						var tbody = "<tbody>";
						for(var i=0;i< listArray.length;i++){
							tbody = tbody + '<tr>';
							var td = '';
							for(var prop in listArray[i]){
								td = td + '<td>' + PrintHealthFileAndExamClass.judgeDate(1,listArray[i][prop]) + '</td>';
							}
							tbody = tbody + td + '</tr>';
						}
						tbody = tbody + '</tbody>';
						$('.' + p).append(tbody);
					}					
				}else{
					$('.' + p).html(PrintHealthFileAndExamClass.judgeDate(0,obj[p]));
				}
			}
			printObj.printHTML($('#printHealthFile').html(),'健康体检记录','19cm','28cm');
		}else{
			//提示信息 
		}
	});
}
var PrintHealthFileAndExamClass = new PrintHealthFileAndExamClass();

