Ext.ns("app");

app.healthfileAlreadyBuildPanelPanel = new Ext.tf.HealthPanel({
	treeLoaderFn : UserMenuTreeService.getUserDistrictNodes,
	queryUrl : UserMenuTreeService.findHealthfilesAlreadyBuild,
	deleteUrl : UserMenuTreeService.removeHealthfilesAlreadyBuild,
	title : '居民健康档案',
	detailUrl : '/healthfileEnableBuild.html',
	recordId : 'id',
	recordPk : 'id',
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
	} ],

	getAddParams : function() {

		var node = this.getTreeSelNode();

		var districtNumber = node.id;
		var township = '';
		// console.log(node.attributes.data.parentName);
		if (node.parentNode) {
			township = escape(node.parentNode.text);
			if (township == 'root')
				township = escape(node.attributes.data.parentName);
		}
		var village = escape(node.text);

		var param = '?districtNumber=' + districtNumber + '&township='
				+ township + '&village=' + village;
		return param;
	}
});

_tab = ModuleMgr.register(app.healthfileAlreadyBuildPanelPanel);
