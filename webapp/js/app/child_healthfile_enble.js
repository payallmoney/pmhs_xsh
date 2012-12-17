Ext.ns("app");

app.childHealthFileEnblePanel = new Ext.tf.HealthBookRecordsPanel({
	treeLoaderFn : UserMenuTreeService.getUserDistrictNodes,
	queryUrl : UserMenuTreeService.findHealthfilesAlreadyBuildChild,
	queryUrl01 : UserMenuTreeService.findhealthfilePregnancyRecordChild,
	deleteUrl : UserMenuTreeService.removeHealthfilesAlreadyBuildChild,
	deleteUrl01 : UserMenuTreeService.removehealthfilePregnancyRecordChild,
	recordId : 'id',
	detailUrl : '/healthfileEnableBuild_child.html',
	recordPk : 'id',
	judgeCondId : 'isClosed',
	judgeCondVal : '0',
	isAlreadyChild : true,
	panelId : 'app.childHealthFileAlreadyPanel',
	funType : 1,
	addHealthBooksText : '新增儿童保健手册',
	delHealthBooksText : '撤销儿童保健手册',
	editHealthBooksText : '修改儿童保健手册',
	service : HealthFileChildrenService.PregnancyRecordChildService,
	serviceType : 1,
	readerConfig : [ {
		name : 'id',
		mapping : 'children.id'
	}, {
		name : 'fileNo',
		mapping : 'file.fileNo'
	}, {
		name : 'name',
		mapping : 'file.name'
	}, {
		name : 'fatherName',
		mapping : 'children.fatherName'
	}, {
		name : 'motherName',
		mapping : 'children.motherName'
	}, {
		name : 'personalInfo_birthday',
		mapping : 'children.birthday'
	}, {
		name : 'personalInfo_tel',
		mapping : 'children.tel'
	},{
		name : 'township',
		mapping : 'file.township'
	},{
		name : 'village',
		mapping : 'file.village'
	} ],
	gridCmConfig : [  {
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
		"header" : "电话",
		"dataIndex" : "personalInfo_tel"
	}, {
		"header" : "父亲姓名",
		"dataIndex" : "fatherName"
	}, {
		"header" : "母亲姓名",
		"dataIndex" : "motherName"
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
		name : 'healthFileChildrenId',
		mapping : 'healthFileChildrenId'
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
	} ],
	examHtmlContainerId : 'children_tabpanel',
	examShowParams :[ {
		title : '新生儿家庭访视',
		url : '/babyvisit.html'
	},{
		title : '1岁以内儿童体检',
		url : '/childexam1.html'
	},{
		title : '1~2岁儿童体检',
		url : '/childexam2.html'
	},{
		title : '3~6岁儿童体检',
		url : '/childexam3_6.html'
	}],
	getAddParams : function() {
		var node = this.getTreeSelNode();
		var districtNumber = node.id;
		var orgName = '';
		if(Ext.tf.currentUser.org != null){
			orgName = escape(Ext.tf.currentUser.org.name);
		}
		var param = '?districtNumber=' + districtNumber + '&buildUnit=' + orgName +
				'&addressProvence=' + escape(CONSTANT_PROVENCE) + '&addressCity=' +
				escape(CONSTANT_CITY) + '&addressCounty=' + escape(CONSTANT_COUNTY) +
				'&residenceProvence=' + escape(CONSTANT_PROVENCE) + '&residenceCity=' +
				escape(CONSTANT_CITY) + '&residenceCounty=' + escape(CONSTANT_COUNTY);
		return param;
	}
});
ModuleMgr.register(app.childHealthFileEnblePanel);
