Ext.ns("app");
var itemRec = new Ext.data.Record.create([ {
	name : 'code',
	mapping : 'id'
}, {
	name : 'desc',
	mapping : 'name'
} ]);

var fromStore = new Ext.data.Store({
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : BirthReportWithMonthService.getUnSetOrganization,
		listeners : {
			'beforeload' : function(dataProxy, params) {
//				var loginname = 'admin';
				params[dataProxy.loadArgsKey] = [];
			}
		}
	}),
	reader : new Ext.data.ArrayReader({}, itemRec)
});
var toStore = new Ext.data.Store({
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : BirthReportWithMonthService.getSetOrganization,
		listeners : {
			'beforeload' : function(dataProxy, params) {
//				var loginname = 'admin';
				params[dataProxy.loadArgsKey] = [ ];
			}
		}
	}),
	reader : new Ext.data.ArrayReader({}, itemRec)
});
var editForm = new Ext.FormPanel({
	height : 500,
	width : 740,
	border : false,
	hidden : false,
	frame : true,
	layout : 'absolute',
	items : [{
		x : 3,
		y : 0,
		name : 'isStartJob',
		id : 'isStartJob',
		xtype : 'checkbox',
		boxLabel : '是否定时执行任务'
	}
//	,{
//		xtype : 'label',
//		text : '定时执行时间:',
//		x : 130,
//		y : 5
//	},{
//		x : 210,
//		y : 0,
//		xtype : 'datefield',
//		width : 150,
//		format : 'Y-m-d'
//	}
	,{
		x : 0,
		y : 30,
		xtype : "itemselector",
		name : "organizations",
//		fieldLabel : "用户角色",
		dataFields : [ "code", "desc" ],
		toData : [],
		toStore : toStore,
		msWidth : 350,
		msHeight : 450,
		valueField : "code",
		displayField : "desc",
		imagePath : "/resources/multiselect/",
		toLegend : "当前组织机构",
		fromLegend : "可用组织机构",
		fromStore : fromStore,
		fromData : [],
		drawUpIcon : false,
		drawDownIcon : false,
		drawTopIcon : false,
		drawBotIcon : false,
		toTBar : [ {
			text : "清除",
			handler : function() {
				var i = editForm.getForm().findField("organizations");
				i.reset.call(i);
			}
		} ]
	}]
})

app.manageBirthReportJob = new Ext.Panel({
	tbar : ['-',{
		text : '保存',
		iconCls : 'c_add',
		handler : function(){
			var formBean = editForm.getForm().getValues(false);
			if (formBean.isStartJob)
				formBean.isStartJob = '1';
			else
				formBean.isStartJob = '0';
			var organizationsArray = formBean.organizations.split(",");
			formBean.organizations = organizationsArray;
			console.log(formBean);
			BirthReportWithMonthService.controlJobForBirthReport(formBean,function(data){
				showInfoObj.Infor('保存成功')
			});
		}.createDelegate(this)
	}],
	items : [ editForm ]
});
_tab = ModuleMgr.register(app.manageBirthReportJob);
fromStore.load();
toStore.load();
systemInformationUtils.getVal(6,function(data){
	console.log(data);
	if(data != null && data == '1'){
		Ext.getCmp('isStartJob').setValue(true);
	}
});