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
	        items: [new Ext.tf.HealthPanel({
	        	treeLoaderFn : UserMenuTreeService.getUserDistrictNodes,
	        	queryUrl : UserMenuTreeService.findHealthfilesAlreadyBuild,
	        	deleteUrl : UserMenuTreeService.removeHealthfilesAlreadyBuild,
//	        	title : '居民健康档案',
	        	detailUrl : '/healthfileEnableBuild.html',
	        	recordId : 'id',
	        	recordPk : 'id',
//	        	height:500,
		        autoScroll: true,
	        	isAlreadyMaternal : true,
	        	judgeCondId : 'isClosed',
	        	judgeCondVal : '0',
	        	panelId : 'app.healthfileAlreadyBuildPanelPanel',
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
	        		name : 'closedDate',
	        		mapping : 'maternal.closedDate'
	        	} ],
	        	gridCmConfig : [ {
	        		"header" : "状态",
	        		"dataIndex" : "isClosed",
	        		"renderer" : function(v){
	        			if(v == '0'){
	        				return '<span>未结案</span>';
	        			}else if(v == '1'){
	        				return '<span>已结案</span>';
	        			}else if(v == '2'){
	        				return '<span>终止妊娠</span>';
	        			}else{
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
	        		"header" : "结案时间",
	        		"dataIndex" : "closedDate",
	        		"renderer" : function(v){
	        			if(v != null)
	        				return calculateTimeObj.formatDate(v,'-');
	        			return '';
	        		}
	        	} ]

//	        	getAddParams : function() {
//
//	        		var node = this.getTreeSelNode();
//
//	        		var districtNumber = node.id;
//	        		var township = '';
//	        		// console.log(node.attributes.data.parentName);
//	        		if (node.parentNode) {
//	        			township = escape(node.parentNode.text);
//	        			if (township == 'root')
//	        				township = escape(node.attributes.data.parentName);
//	        		}
//	        		var village = escape(node.text);
//
//	        		var param = '?districtNumber=' + districtNumber + '&township='
//	        				+ township + '&village=' + village;
//	        		return param;
//	        	}
	        })]
	    }]
	}]
});

_tab = ModuleMgr.register(app.womanBirthBuildPanel);
