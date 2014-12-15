function denc(str) {
	return str;
	// if(! str || !str.length){
	// return "";
	// }
	// var denclist =
	// '$&@*!.:=>}€‚ƒˆ‰Š‹ŒŽ‘’•–àáâãäæççèéêëìßÞÝÜÛÜÛÚÙØÖÕÔÓÒÑÐÏÊÉÇÆÄÃ£Á';
	// var enclist =
	// '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	// var result = "";
	// var tmpStr = "";
	// for (var i = 0; i < str.length; i++) {
	// tmpStr = str.substr(i,1);
	// if (tmpStr !== "%") {
	// var index = denclist.indexOf(tmpStr);
	// if (index<0) {
	// tmpStr = String.fromCharCode(tmpStr.charCodeAt(0) ^ 'c'.charCodeAt(0));
	// } else {
	// tmpStr = enclist.substr(index,1);
	// }
	// }
	// result = result + tmpStr;
	// }
	// return result;
}

function enc(str) {
	return str;
	// if(! str || !str.length){
	// return "";
	// }
	// var denclist =
	// '$&@*!.:=>}€‚ƒˆ‰Š‹ŒŽ‘’•–àáâãäæççèéêëìßÞÝÜÛÜÛÚÙØÖÕÔÓÒÑÐÏÊÉÇÆÄÃ£Á';
	// var enclist =
	// '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	// var result = "";
	// var tmpStr = "";
	// for (var i = 0; i < str.length; i++) {
	// tmpStr = str.substr(i,1);
	// if (tmpStr !== "%") {
	// var index = enclist.indexOf(tmpStr);
	// if (index<0) {
	// tmpStr = String.fromCharCode(tmpStr.charCodeAt(0) ^ 'c'.charCodeAt(0));
	// } else {
	// tmpStr = denclist.substr(index,1);
	// }
	// }
	// result = result + tmpStr;
	// }
	// return result;
}

function parseParams(url) {
	url = url.substring(1, url.length);
	var addressSymble = url.split('&');
	var json = '{';
	for (var i = 0; i < addressSymble.length; i++) {
		var equalSymble = addressSymble[i].split('=');
		json = json + equalSymble[0] + ':"' + equalSymble[1] + '",';
	}
	if (json != null) {
		return eval('(' + json.substring(0, json.length - 1) + '})');
	}
	return null;
}
function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
			.exec(location.search);
	return results == null ? "" : decodeURIComponent(results[1].replace(
			/\+/g, " "));
}

function initeasytree(selector) {
	var nodedata = window.top.earyuitreedata;
	$(selector).tree({
		data : nodedata,
		checkbox :  false,
		onLoadSuccess : function() {
			// 选中第一节点
			var node = $(selector).tree('find', parseInt(nodedata[0].id));
			$(selector).tree("select", node.target);
		}
	});
}
