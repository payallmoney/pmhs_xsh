Ext.ns("app");

app.womanBirthBuildPanel = new Ext.Panel({
	autoScroll: true,
	layout : 'fit',
	items : [{
		xtype : 'tabpanel',
		activeTab: 0,
//		layout : 'fit',
		autoScroll: true,
		items : [{
	        title: '孕产妇建册',
//	        height:500,
	        layout : 'fit',
	        autoScroll: true,
	        items:[new Ext.tf.HealthPanel({
	        	treeLoaderFn : UserMenuTreeService.getUserDistrictNodes,
	        	queryUrl : UserMenuTreeService.findHealthFilesEnableBuild,
	        	// deleteUrl : UserMenuTreeService.removeHealthFiles,
//	        	title : '可建妇保手册的档案',
//	        	height:500,
	        	autoScroll: true,
	        	detailUrl : '/healthfileEnableBuild.html',
	        	recordId : 'fileNo',
	        	recordPk : 'fileNo',
	        	panelId : 'app.healthfileEnbleBuildPanel',
	        	isMaternal : true,
	        	readerConfig : [ {
	        		name : 'fileNo'
	        	}, {
	        		name : 'name'
	        	}, {
	        		name : 'personalInfo_sex',
	        		mapping : 'personalInfo.sex'
	        	}, {
	        		name : 'personalInfo_birthday',
	        		mapping : 'personalInfo.birthday'
	        	}, {
	        		name : 'personalInfo_idnumber',
	        		mapping : 'personalInfo.idnumber'
	        	}, {
	        		name : 'address'
	        	}, {
	        		name : 'personalInfo_linkman',
	        		mapping : 'personalInfo.linkman'
	        	}, {
	        		name : 'paperFileNo',
	        		mapping : 'paperFileNo'
	        	}, {
	        		name : 'personalInfo_tel',
	        		mapping : 'personalInfo.tel'
	        	} ],
	        	gridCmConfig : [ {
	        		"header" : "档案编号",
	        		"dataIndex" : "fileNo",
	        		"width" : 200
	        	}, {
	        		"header" : "姓名",
	        		"dataIndex" : "name"
	        	}, {
	        		"header" : "性别",
	        		"dataIndex" : "personalInfo_sex",
	        		"width" : 50
	        	}, {
	        		"header" : "生日",
	        		"dataIndex" : "personalInfo_birthday",
	        		"renderer" : Ext.util.Format.dateRenderer('Y-m-d')
	        	}, {
	        		"header" : "身份证号",
	        		"dataIndex" : "personalInfo_idnumber"
	        	}, {
	        		"header" : "联系人",
	        		"dataIndex" : "personalInfo_linkman"
	        	}, {
	        		"header" : "住址",
	        		"dataIndex" : "address"
	        	}, {
	        		"header" : "纸质档案号",
	        		"dataIndex" : "paperFileNo"
	        	}, {
	        		"header" : "电话",
	        		"dataIndex" : "personalInfo_tel"
	        	} ],

	        	getAddParams : function() {
	        		var selections = this.grid.getSelections();
	        		console.log(selections);
	        		if (selections.length > 0) {
	        			var fileNo = escape(selections[0].data.fileNo);
	        			var name = escape(selections[0].data.name);
	        			var birthday = calculateTimeObj.dateToStr(selections[0].data.personalInfo_birthday);
	        			var tel = escape(selections[0].json.tel);
	        			var provence = escape('云南');
	        			var city = escape('保山');
	        			var county = escape('施甸');
	        			var township = escape(selections[0].json.township);
	        			var village = escape(selections[0].json.village);
//	        			console.log(Ext.tf.currentUser);
	        			var buildUnit = escape(Ext.tf.currentUser.org.name);
	        			var idnumber = escape(selections[0].data.personalInfo_idnumber);
	        			var workUnit = escape(selections[0].json.personalInfo.workUnit);
	        			var occupation = escape(selections[0].json.personalInfo.occupation);
	        			var folk = escape(selections[0].json.personalInfo.folk);
	        			var folkOther = escape(selections[0].json.personalInfo.folkOther);
	        			this.storeFileNo = fileNo;
	        			var param = '?fileNo=' + fileNo + '&name=' + name + '&birthday=' + birthday + '&tel=' + tel + 
	        				'&addressProvence=' + provence + '&addressCity=' + city + '&addressCounty=' + county +
	        				'&addressTownship=' + township + '&addressVillage=' + village +
	        				'&residenceProvence=' + provence + '&residenceCity=' + city + '&residenceCounty=' + county +
	        				'&residenceTownship=' + township + '&residenceVillage=' + village +
	        				'&recuperateProvence=' + provence + '&recuperateCity=' + city + '&recuperateCounty=' + county +
	        				'&recuperateTownship=' + township + '&recuperateVillage=' + village +
	        				'&buildUnit=' + buildUnit + '&idnumber=' + idnumber + '&workUnit=' + workUnit +
	        				'&occupation=' + occupation + '&folk=' + folk + '&folkOther=' + folkOther +
	        				'&isForeignId=""';
	        			return param;
	        		} else {
	        			return '-1';
	        		}
	        	}
	        })]
	    },{
	        title: '孕产妇档案查询',
//	        height:500,
	        autoScroll: true,
	        layout : 'fit',
	        items: [new Ext.tf.HealthPregnancyRecordPanel({
	        	treeLoaderFn : UserMenuTreeService.getUserDistrictNodes,
	        	queryUrl : UserMenuTreeService.findHealthfilesAlreadyBuild,
	        	queryUrl01 : UserMenuTreeService.findhealthfilePregnancyRecord,
	        	deleteUrl : UserMenuTreeService.removeHealthfilesAlreadyBuild,
	        	deleteUrl01 : UserMenuTreeService.removehealthfilePregnancyRecord,
//	        	title : '居民健康档案',
	        	recordId : 'id',
	        	detailUrl : '/healthfileEnableBuild.html',
	        	recordPk : 'id',
	        	judgeCondId : 'isClosed',
	        	judgeCondVal : '0',
	        	isAlreadyMaternal : true,
	        	panelId : 'app.healthfilePregnancyRecordPanel',
	        	service : healthfileMaternalService.PregnancyRecordService,
	        	serviceType : 0,
	        	readerConfig : [ {
	        		name : 'id',
	        		mapping : 'maternal.id'
	        	}, {
	        		name : 'fileNo',
	        		mapping : 'file.fileNo'
	        	}, {
	        		name : 'name',
	        		mapping : 'file.name'
	        	}, {
	        		name : 'personalInfo_birthday',
	        		mapping : 'maternal.birthday'
	        	}, {
	        		name : 'personalInfo_idnumber',
	        		mapping : 'maternal.idnumber'
	        	}, {
	        		name : 'personalInfo_tel',
	        		mapping : 'maternal.tel'
	        	}, {
	        		name : 'gravidity',
	        		mapping : 'maternal.gravidity'
	        	}, {
	        		name : 'isClosed',
	        		mapping : 'maternal.isClosed'
	        	},{
	        		name : 'township',
	        		mapping : 'file.township'
	        	},{
	        		name : 'village',
	        		mapping : 'file.village'
	        	} ],
	        	gridCmConfig : [ {
	        		"header" : "状态",
	        		"dataIndex" : "isClosed",
	        		"renderer" : function(v) {
	        			if (v == '0') {
	        				return '<span>未结案</span>';
	        			} else if (v == '1') {
	        				return '<span>已结案</span>';
	        			} else if (v == '2') {
	        				return '<span>终止妊娠</span>';
	        			} else {
	        				return '';
	        			}
	        		}
	        	}, {
	        		"header" : "档案编号",
	        		"dataIndex" : "fileNo",
	        		"width" : 200
	        	}, {
	        		"header" : "姓名",
	        		"dataIndex" : "name"
	        	}, {
	        		"header" : "生日",
	        		"dataIndex" : "personalInfo_birthday",
	        		"renderer" : Ext.util.Format.dateRenderer('Y-m-d')
	        	}, {
	        		"header" : "身份证号",
	        		"dataIndex" : "personalInfo_idnumber"
	        	}, {
	        		"header" : "电话",
	        		"dataIndex" : "personalInfo_tel"
	        	}, {
	        		"header" : "孕次",
	        		"dataIndex" : "gravidity",
	        		"renderer" : function(v) {
	        			if (v != null && v != '')
	        				return '<span>第' + v + '次</span>';
	        			return '';
	        		}
	        	},{
	        		"header" : "所属行政区划",
	        		"width" : 150,
	        		"renderer" : function(value, metadata, record, rowIndex, colIndex, store) {
	        			return record.data.township + record.data.village;
	        		}
	        	} ],
	        	readerConfig01 : [ {
	        		name : 'id',
	        		mapping : 'id'
	        	}, {
	        		name : 'recordDate',
	        		mapping : 'recordDate'
	        	}, {
	        		name : 'record',
	        		mapping : 'record'
	        	}, {
	        		name : 'dealOpinion',
	        		mapping : 'dealOpinion'
	        	}, {
	        		name : 'doctor',
	        		mapping : 'doctor'
	        	}, {
	        		name : 'healthFileMaternalId',
	        		mapping : 'healthFileMaternalId'
	        	} ],
	        	gridCmConfig01 : [ {
	        		"header" : "记录日期",
	        		"dataIndex" : "recordDate",
	        		"renderer" : Ext.util.Format.dateRenderer('Y-m-d')
	        	}, {
	        		"header" : "特殊情况记录",
	        		"dataIndex" : "record",
	        		"width" : 300
	        	}, {
	        		"header" : "处理意见",
	        		"dataIndex" : "dealOpinion",
	        		"width" : 150
	        	}, {
	        		"header" : "医生签名",
	        		"dataIndex" : "doctor"
	        	} ]
	        })]
	    },{
	    	 title: '第一次产前随访',
		     layout : 'fit',
		     autoScroll: true,
		     items: [new Ext.tf.HealthPanel({
		    	    title: '第一次产前随访记录',
		    	    treeLoaderFn: UserMenuTreeService.getUserDistrictNodes,
		    	    queryUrl : UserMenuTreeService.findFirstVisitRecords,
		    	    deleteUrl : UserMenuTreeService.removeFirstVisitRecords,
		    	    dataExportUrl : DataExportService.dataExportFirstBabyVisit,
		    	    recordId : 'firstVisit.id',
		    	    recordPk : 'id',
		    	    detailUrl: '/firstvisit.html',
		    	    panelId : 'app.firstvisitPanel',
		    	    isWomanExam : true,
//		    	    Select A.FileNo 编号,B.Name 姓名,C.Birthday 出生日期,Weeks 孕周,A.VisitDate 随访日期,
//		    	    A.NextVisitDate 下次随访日期,A.VisitDoctor 随访医生,D.UserName 录入人from FirstVistBeforeBorn A
//		    	    left join dbo.HealthFile B on A.FileNo = B.FileNo
//		    	    left join PersonalInfo C on A.FileNo = B.FileNo
//		    	    left join sam_taxempcode D on A.inputpersonId = D.loginname
		    	    readerConfig : [
		    	                    {name:'execOrgName', mapping: 'org.name'},
		    	                    {name:'id', mapping: 'firstVisit.id'},
		    	                    {name:'fileNo', mapping: 'file.fileNo'},
		    	                    {name:'name', mapping: 'file.name'},
		    	                    {name:'birthday', mapping: 'person.birthday'},
		    	                    {name:'highRisk', mapping: 'firstVisit.highRisk'},
		    	                    {name:'weeks', mapping: 'firstVisit.weeks'},
		    	                    {name:'visitDate', mapping: 'firstVisit.visitDate'},
		    	                    {name:'nextVisitDate', mapping: 'firstVisit.nextVisitDate'},
		    	                    {name:'visitDoctor', mapping: 'firstVisit.visitDoctor'},
		    	                    {name:'username', mapping: 'samTaxempcode.username'}
		    	                   ],
		    	    gridCmConfig :
		    	                   [
		    	                    { "header" : "执行机构", "dataIndex" : "execOrgName"}, 
		    	                     { "header" : "编号", "dataIndex" : "fileNo", "width":130 },
		    	                     { "header" : "姓名", "dataIndex" : "name" },
		    	                     { "header" : "出生日期", "dataIndex" : "birthday",
		    	                                         "renderer": Ext.util.Format.dateRenderer('Y-m-d') },
		    	                                         { "header" : "高危", "dataIndex" : "highRisk" },
		    	                     { "header" : "孕周", "dataIndex" : "weeks" },
		    	                     { "header" : "随访日期", "dataIndex" : "visitDate",
		    	                                         "renderer": Ext.util.Format.dateRenderer('Y-m-d') },
		    	                     { "header" : "下次随访日期", "dataIndex" : "nextVisitDate",
		    	                                         "renderer": Ext.util.Format.dateRenderer('Y-m-d') },
		    	                     { "header" : "随访医生", "dataIndex" : "visitDoctor" },
		    	                     { "header" : "录入人", "dataIndex" : "username" }
		    	                   ]
		    	})]
	    },{
	    	 title: '第2~5次产前随访',
		     layout : 'fit',
		     autoScroll: true,
		     items: [new Ext.tf.HealthPanel({
		    	    title: '第2至5次产前随访记录',
		    	    treeLoaderFn: UserMenuTreeService.getUserDistrictNodes,
		    	    queryUrl : UserMenuTreeService.findVisitBeforeBornRecords,
		    	    deleteUrl : UserMenuTreeService.removeVisitBeforeBornRecords,
		    	    dataExportUrl : DataExportService.dataExportVisitBeforeBorn,
		    	    recordId : 'visit.id',
		    	    recordPk : 'id',
		    	    detailUrl: '/VisitBeforeBorn.html',
		    	    panelId : 'app.visitBeforeBornPanel',
		    	    isWomanExam : true,
//		    	    Select A.FileNo 编号,B.Name 姓名,C.Birthday 出生日期,Weeks 孕周,A.Item 项目,A.VisitDate 随访日期,
//		    	    A.NextVisitDate 下次随访日期,A.VisitDoctor 随访医生,D.UserName 录入人from VisitBeforeBorn A
//		    	    left join dbo.HealthFile B on A.FileNo = B.FileNo
//		    	    left join PersonalInfo C on A.FileNo = B.FileNo
//		    	    left join sam_taxempcode D on A.inputpersonId = D.loginname
		    	    
		    	    readerConfig : [
		    	                    {name:'execOrgName', mapping: 'org.name'},
		    	                    {name:'id', mapping: 'visit.id'},
		    	                    {name:'fileNo', mapping: 'file.fileNo'},
		    	                    {name:'name', mapping: 'file.name'},
		    	                    {name:'birthday', mapping: 'person.birthday'},
		    	                    {name:'highRisk', mapping: 'visit.highRisk'},
		    	                    {name:'weeks', mapping: 'visit.weeks'},
		    	                    {name:'item', mapping: 'visit.item'},
		    	                    {name:'visitDate', mapping: 'visit.visitDate'},
		    	                    {name:'nextVisitDate', mapping: 'visit.nextVisitDate'},
		    	                    {name:'visitDoctor', mapping: 'visit.visitDoctor'},
		    	                    {name:'username', mapping: 'samTaxempcode.username'}
		    	                   ],
		    	    gridCmConfig :
		    	                   [
		    	                    { "header" : "执行机构", "dataIndex" : "execOrgName"}, 
		    	                     { "header" : "编号", "dataIndex" : "fileNo", "width":130 },
		    	                     { "header" : "姓名", "dataIndex" : "name" },
		    	                     { "header" : "出生日期", "dataIndex" : "birthday",
		    	                                         "renderer": Ext.util.Format.dateRenderer('Y-m-d') },
		    	                                         { "header" : "高危", "dataIndex" : "highRisk" },   
		    	                     { "header" : "孕周", "dataIndex" : "weeks" },
		    	                     { "header" : "项目", "dataIndex" : "item","renderer" : function(val){
		    	                    	 return "第" + val + "次";
		    	                     }},
		    	                     { "header" : "随访日期", "dataIndex" : "visitDate",
		    	                                         "renderer": Ext.util.Format.dateRenderer('Y-m-d') },
		    	                     { "header" : "下次随访日期", "dataIndex" : "nextVisitDate",
		    	                                         "renderer": Ext.util.Format.dateRenderer('Y-m-d') },
		    	                     { "header" : "随访医生", "dataIndex" : "visitDoctor" },
		    	                     { "header" : "录入人", "dataIndex" : "username" }
		    	                   ]
		    	})]
	    },{
	    	 title: '产后访视记录',
		     layout : 'fit',
		     autoScroll: true,
		     items: [new Ext.tf.HealthPanel({
		    	    title: '产后访视记录',
		    	    treeLoaderFn: UserMenuTreeService.getUserDistrictNodes,
		    	    queryUrl : UserMenuTreeService.findVisitAfterBornRecords,
		    	    deleteUrl : UserMenuTreeService.removeVisitAfterBornRecords,
		    	    dataExportUrl : DataExportService.dataExportVisitAfterBorn,
		    	    recordId : 'visit.id',
		    	    recordPk : 'id',
		    	    detailUrl: '/visitAfterBorn.html',
		    	    panelId : 'app.visitAfterBornPanel',
		    	    isWomanExam : true,
//		    	    Select A.FileNo 编号,B.Name 姓名,C.Birthday 出生日期,A.VisitDate 随访日期,Result 分类,
//		    	    A.NextVisitDate 下次随访日期,A.VisitDoctor 随访医生,D.UserName 录入人from VisitAfterBorn A
//		    	    left join dbo.HealthFile B on A.FileNo = B.FileNo
//		    	    left join PersonalInfo C on A.FileNo = B.FileNo
//		    	    left join sam_taxempcode D on A.inputpersonId = D.loginnam
		    	    readerConfig : [
		    	                    {name:'execOrgName', mapping: 'org.name'},
		    	                    {name:'id', mapping: 'visit.id'},
		    	                    {name:'fileNo', mapping: 'file.fileNo'},
		    	                    {name:'name', mapping: 'file.name'},
		    	                    {name:'birthday', mapping: 'person.birthday'},
		    	                    {name:'highRisk', mapping: 'visit.highRisk'},
		    	                    {name:'visitDate', mapping: 'visit.visitDate'},
		    	                    {name:'result', mapping: 'visit.result'},
		    	                    {name:'nextVisitDate', mapping: 'visit.nextVisitDate'},
		    	                    {name:'visitDoctor', mapping: 'visit.visitDoctor'},
		    	                    {name:'username', mapping: 'samTaxempcode.username'}
		    	                   ],
		    	    gridCmConfig :
		    	                   [
		    	                    { "header" : "执行机构", "dataIndex" : "execOrgName"}, 
		    	                     { "header" : "编号", "dataIndex" : "fileNo", "width":130 },
		    	                     { "header" : "姓名", "dataIndex" : "name" },
		    	                     { "header" : "出生日期", "dataIndex" : "birthday",
		    	                                         "renderer": Ext.util.Format.dateRenderer('Y-m-d') },
		    	                                         { "header" : "高危", "dataIndex" : "highRisk" },
		    	                     { "header" : "随访日期", "dataIndex" : "visitDate",
		    	                                           "renderer": Ext.util.Format.dateRenderer('Y-m-d') },
		    	                     { "header" : "分类", "dataIndex" : "result" },
		    	                     { "header" : "下次随访日期", "dataIndex" : "nextVisitDate",
		    	                                         "renderer": Ext.util.Format.dateRenderer('Y-m-d') },
		    	                     { "header" : "随访医生", "dataIndex" : "visitDoctor" },
		    	                     { "header" : "录入人", "dataIndex" : "username" }
		    	                   ]
		    	})]
	    },{
	    	 title: '产后42天健康体检',
		     layout : 'fit',
		     autoScroll: true,
		     items: [new Ext.tf.HealthPanel({
		    	    title: '产后42天健康检查记录',
		    	    treeLoaderFn: UserMenuTreeService.getUserDistrictNodes,
		    	    queryUrl : UserMenuTreeService.findVisitAfterBorn42Records,
		    	    deleteUrl : UserMenuTreeService.removeVisitAfterBornRecords,
		    	    dataExportUrl : DataExportService.dataExportVisitAfterBorn42,
		    	    recordId : 'visit.id',
		    	    recordPk : 'id',
		    	    detailUrl: '/visitAfterBorn42.html',
		    	    panelId : 'app.visitAfterBorn42Panel',
		    	    isWomanExam : true,
		    	    readerConfig : [
		    	                    {name:'execOrgName', mapping: 'org.name'},
		    	                    {name:'id', mapping: 'visit.id'},
		    	                    {name:'fileNo', mapping: 'file.fileNo'},
		    	                    {name:'name', mapping: 'file.name'},
		    	                    {name:'birthday', mapping: 'person.birthday'},
		    	                    {name:'highRisk', mapping: 'visit.highRisk'},
		    	                    {name:'visitDate', mapping: 'visit.visitDate'},
		    	                    {name:'result', mapping: 'visit.result'},
		    	                    {name:'visitDoctor', mapping: 'visit.visitDoctor'},
		    	                    {name:'username', mapping: 'samTaxempcode.username'}
		    	                   ],
		    	    gridCmConfig :
		    	                   [
		    	                    { "header" : "执行机构", "dataIndex" : "execOrgName"},
		    	                     { "header" : "编号", "dataIndex" : "fileNo", "width":130 },
		    	                     { "header" : "姓名", "dataIndex" : "name" },
		    	                     { "header" : "出生日期", "dataIndex" : "birthday",
		    	                                         "renderer": Ext.util.Format.dateRenderer('Y-m-d') },
		    	                                         { "header" : "高危", "dataIndex" : "highRisk" },
		    	                     { "header" : "随访日期", "dataIndex" : "visitDate",
		    	                                           "renderer": Ext.util.Format.dateRenderer('Y-m-d') },
		    	                     { "header" : "分类", "dataIndex" : "result" },
		    	                     { "header" : "随访医生", "dataIndex" : "visitDoctor" },
		    	                     { "header" : "录入人", "dataIndex" : "username" }
		    	                   ]
		    	})]
	    }]
	}]
});

_tab = ModuleMgr.register(app.womanBirthBuildPanel);
