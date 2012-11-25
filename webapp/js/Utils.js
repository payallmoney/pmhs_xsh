Utils = function() {
	// URL解析
};
Utils.prototype.openwindow = function(targetUrl,param,gridId,titles) {
	var win = new Ext.Window({
		modal : true,
		title : titles,
		border : false
	});
	if (param != null) {
		window.other_init_param = param;
	}
	win.show();
	win.maximize();

	win.add({
		xtype : 'iframepanel',
		defaultSrc : targetUrl,
		title : '',
		loadMask : true,
		autoScroll : false,
		listeners : {
			message : function(f, data) {
				if (data.data == 'quit') {
					win.close();
				} else if (data.data == 'saved') {
					//...
				}
				Ext.getCmp(gridId).getStore().reload();
			}.createDelegate(this)
		}
	});
	win.doLayout(true);
}
Utils.prototype.formatNullOrEmpty = function(val){
	if(val == null || val == '')
		return '无';
	return val;
} 
Utils.prototype.parseParams = function(url){
	url = url.substring(1,url.length);
	var addressSymble = url.split('&');
	var json = '{';
	for(var i = 0;i<addressSymble.length;i++){
		var equalSymble = addressSymble[i].split('=');
		json = json + equalSymble[0] + ':"' + equalSymble[1] + '",';
	}
	if(json != null){
		return eval('(' + json.substring(0,json.length - 1) + '})');
	}
	return null;
}
var Utils = new Utils();

