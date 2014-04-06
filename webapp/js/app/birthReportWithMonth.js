Ext.ns("app");
app.birthReportWithMonth = new Ext.tf.BirthCertificateReportWithMonth({
	queryUrl : BirthReportWithMonthService.getHistoryReportFiles,
	readerConfig : [ {
		name : 'id'
	}, {
		name : 'fileName'
	}, {
		name : 'reportYear'
	}, {
		name : 'reportMonth'
	}, {
		name : 'inputDate'
	},{
		name : 'storeFileName'
	} ],
	gridCmConfig : [ {
		"header" : "<center>月统计报表名称</center>",
		"dataIndex" : "fileName",
		"width" : 200
	}, {
		"header" : "<center>统计年份</center>",
		"dataIndex" : "reportYear"
	}, {
		"header" : "<center>统计月份</center>",
		"dataIndex" : "reportMonth"
	}, {
		"header" : "<center>统计日期</center>",
		"dataIndex" : "inputDate",
		"renderer" : Ext.util.Format.dateRenderer('Y-m-d')
	} ]

});
ModuleMgr.register(app.birthReportWithMonth);