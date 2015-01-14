Ext.ns('app');

function pagination(currentPage,data){
	var len = data.length;
	var pageSize = 7;
	var limit = 5;
	var page = Math.ceil(len / (pageSize * limit));
	var size = 0;
	var start = 0;
	if(currentPage <= page){
		size = currentPage * pageSize * limit;
		start = (currentPage-1) * pageSize * limit;
	}
	var searchVal = Ext.getCmp('certifiId').getValue();
	var result = "<tbody><tr>";
	for(var i=start;i< size;i++){
		var val = data[i];
		if(val == undefined){
			val = '&nbsp;';
			result += '<td>' + val + '</td>';
		}else{
			if(searchVal == val){
				result += '<td class="selected" style="background-color:red;">' + val + '</td>';
			}else{
				result += '<td>' + val + '</td>';
			}
		}
		
		var split = i + 1;
		if((split % pageSize) == 0){
			result = result + '</tr><tr>';
		}
	}
	var lastSecondChar = result.substring(result.length-4,result.length);
	if(lastSecondChar == '<tr>'){
		result = result.substring(0,result.length - 4);
	}else{
		result = result + '</tr>';
	}
	result = result + '</tbody>';
	return result;
}

function isNull(val){
	if(val == null || val == '')
		val = '&nbsp;';
	return val;
}

function getBirthAddress(){
	BirthCertificateMsgService.getBirthAdress(function(data){
		if(data.length != 0){
			return data;
		}
	});
}

function paginationUsed(currentPage,data){
	var len = data.length;
	var pageSize = 10;
	var page = Math.ceil(len / pageSize);
	var size = 0;
	var start = 0;
	if(currentPage < page){
		size = currentPage * pageSize;
	}else{
		size = len;
	}
	start = (currentPage-1) * pageSize;
	var result = "<tbody>";
	
	for(var i=start;i< size;i++){
		result = result + '<tr><td><input type="checkbox" class="checkable"/></td><td>' + isNull(data[i].certifiId) + '</td><td>' + isNull(data[i].name) +
			'</td><td>' + isNull(data[i].motherName) + '</td><td>' + isNull(data[i].fatherName) + 
			'</td><td>' + isNull(data[i].borthOrganization) + '</td><td> ' + isNull(data[i].linkmanTel) + '</td></tr>'; 
	}
	result = result + '</tbody>';
	return result;
}
function $eachCheckBox(i){
	$('.checkable').each(function(j){
		var check = $(this).attr('checked');
		if(check){
			$(this).attr('checked','');
		}else{
			if(i == j){
				$(this).attr('checked','checked');
			}
		}
	});
}
function checked(){
	$('.checkable').each(function(i){
		$(this).click(function(){
			$eachCheckBox(i);
		});
	});
}

function getCertifiId(){
	var certifiId = '';
	$('.checkable').each(function(){
		var check = $(this).attr('checked');
		if(check){
			certifiId = $(this).parent('td').next().html();
		}
	});
	return certifiId;
}

function cssACA899(fields){
	var field = fields.split(',');
	for(var i = 0;i<field.length;i++){
		$('#' + field[i]).css('color','#ACA899');
	}
}
function css000(fields){
	var field = fields.split(',');
	for(var i = 0;i<field.length;i++){
		$('#' + field[i]).css('color','#000');
	}
}
function controlShow(currentPage,page){
	if(currentPage == page){
		cssACA899('lastPage,nextPage');
		css000('firstPage,prevPage');
	}else if(currentPage == 1){
		css000('lastPage,nextPage');
		cssACA899('firstPage,prevPage');
	}else{
		css000('firstPage,prevPage,lastPage,nextPage');
	}
}

function controlShow1(currentPage,page){
	if(currentPage == page){
		cssACA899('lastPage1,nextPage1');
		css000('firstPage1,prevPage1');
	}else if(currentPage == 1){
		css000('lastPage1,nextPage1');
		cssACA899('firstPage1,prevPage1');
	}else{
		css000('firstPage1,prevPage1,lastPage1,nextPage1');
	}
}

function controlShow2(currentPage,page){
	if(currentPage == page){
		cssACA899('lastPage2,nextPage2');
		css000('firstPage2,prevPage2');
	}else if(currentPage == 1){
		css000('lastPage2,nextPage2');
		cssACA899('firstPage2,prevPage2');
	}else{
		css000('firstPage2,prevPage2,lastPage2,nextPage2');
	}
}
function controlShow3(currentPage,page){
	if(currentPage == page){
		cssACA899('lastPage3,nextPage3');
		css000('firstPage3,prevPage3');
	}else if(currentPage == 1){
		css000('lastPage3,nextPage3');
		cssACA899('firstPage3,prevPage3');
	}else{
		css000('firstPage3,prevPage3,lastPage3,nextPage3');
	}
}

function controlShow4(currentPage,page){
	if(currentPage == page){
		cssACA899('lastPage4,nextPage4');
		css000('firstPage4,prevPage4');
	}else if(currentPage == 1){
		css000('lastPage4,nextPage4');
		cssACA899('firstPage4,prevPage4');
	}else{
		css000('firstPage4,prevPage4,lastPage4,nextPage4');
	}
}

function tbodyAppend($this,currentPage,data){
	var $tbody = $('#' + $this + ' tbody');
	$tbody.remove();
	result = paginationUsed(currentPage,data);
	$('#' + $this).append(result);
}

function tbodyAppendUnused($this,currentPage,data){
	var $tbody = $('#' + $this + ' tbody');
	$tbody.remove();
	result = pagination(currentPage,data);
	$('#' + $this).append(result);
}

function showMsg(msg){
	Ext.Msg.show({
		title : '提示',
		msg : msg,
		icon: Ext.MessageBox.INFO,
		animEl: 'elId',
		buttons: Ext.Msg.OK
	});
}

BirthCertificateMsgService.getAuthority(function(data){
	if(data != null){
		var isAdmin = data[0];
		if(!isAdmin){
			Ext.getCmp('restore').setVisible(false);
			Ext.getCmp('restorePigeonhole').setVisible(false);
			Ext.getCmp('cancelUsed').setVisible(false);
			
			var isOrgDepart = data[1]
			if(isOrgDepart != 1){
				Ext.getCmp('supply').setVisible(false);
			}
		}
	}
});



function openWin(targetUrl,param) {
    var win = new Ext.Window({
      modal: true,
      title: '录入记录',
      border: false
//      autoScroll : true
//      closable : false
    });
    if(param !=null){
    	window.other_init_param = param;
    }
    win.show();
    win.maximize();
    win.add( {
      xtype: 'iframepanel',
      defaultSrc : targetUrl,
//      width: win.getInnerWidth(),
      height: win.getInnerHeight(),
      title : '',
      loadMask : true,
      autoScroll: true,
      listeners:{
          message : function(f,data) {
            console.log("receive message...");
            console.log(data);
            if ( data.data == 'quit' ) {
              win.close();
            } else if ( data.data == 'saved' ) {
//              this.load();
            	runTabPanel();
            }
          }.createDelegate(this)
        }
    });
    win.doLayout(true);
  }



function selectRow(){
	$('.certifiTable tbody tr').each(function(i){
		$(this).click(function(){
			$eachCheckBox(i);
		}).hover(function(){
			$(this).css('cursor','pointer');
		},function(){
			
		});
	});
}

var con = 2;
var currentNode = null;

function services(params){
	if(currentNode != null){
		var certifiId = getCertifiId();		
		if(certifiId != ''){
			openWin('/birthCertificateInfo_show.html?certifiId=' + certifiId + "&type=" + params);
		}else{
			showMsg('请选择出生证明信息');
		}
	}else{
		showMsg('请选择组织机构');
	}
}

function useCertifiService(url){
	BirthCertificateMsgService.getBirthAdress(function(data){
		if(data.length != 0){
			var certifiId = $('.selected').html();
			var orgName = escape(currentNode.text);
			var nationality = escape('中国');
			var province = escape(data[0]);
			var city = escape(data[1]);
			var county = escape(data[2]);
			var params = '?certifiId=' + certifiId + '&borthOrganization=' + orgName +
			'&issuingOrganization=' + orgName + '&motherNationality=' + nationality +
			'&fatherNationality=' + nationality + '&province=' + province + 
			'&city=' + city + '&county=' + county + 
			'&districtNumber=';
			openWin(url + params);
		}
	});
}

var handleDistributed = function(tab){
	if(currentNode != null){
		Ext.getCmp('save').setDisabled(false);
		Ext.getCmp('change').setDisabled(false);
		Ext.getCmp('supply').setDisabled(false);
		Ext.getCmp('modify').setDisabled(true);
		Ext.getCmp('lookup').setDisabled(true);
		Ext.getCmp('destroy').setDisabled(true);
		Ext.getCmp('pigeonhole').setDisabled(true);
		Ext.getCmp('restore').setDisabled(true);
		Ext.getCmp('restorePigeonhole').setDisabled(true);
		Ext.getCmp('cancelUsed').setDisabled(true);
		var id = currentNode.id;
		var resTxt = Ext.getCmp('certifiId').getValue();
		BirthCertificateMsgService.getDistributedCertiId(id,resTxt,function(data){
			if(data == null){
				$('#unUsed').html('<font color="red" size="2">暂无分配</font>');
			}else{
				var page = Math.ceil(data.length / 35);
				var currentPage = 1;
				var result = "<table oncontextmenu= 'return false;' onselectstart= 'return false;' oncopy= 'return false;' "+
						"oncut= 'return false;' ondragstart= 'return false;' id='tableContainer1' class='container' cellpadding='0' cellspacing='0'>" + 
						pagination(currentPage,data);
				result = result + '<tfoot><tr><td colspan="7"><span>共有' + data.length + '条记录&nbsp;&nbsp;共有' + page + '页&nbsp;&nbsp;当前为第'+
					'<span id="curPage1"></span>页&nbsp;&nbsp;</span><span id="firstPage1" class="paging">首页</span>' +
				 	'&nbsp;<span id="prevPage1" class="paging">上一页</span>&nbsp;<span id="nextPage1" class="paging">下一页</span>'+
				 	'&nbsp;<span id="lastPage1" class="paging">尾页</span></td></tr><tr><td colspan="7">'+
				 	'<font size="2" color="red">注：背景颜色为红色的表示该出生证明编号可以使用</font></td></tr></tfoot></table>';
				
				$('#unUsed').html(result);
				$('#curPage1').html(currentPage);
				controlShow1(currentPage,page);
				$('.paging').click(function(){
					var id = $(this).attr('id');
					if(id == 'firstPage1'){
						currentPage = 1;
						tbodyAppendUnused('tableContainer1',currentPage,data);
					}else if(id == 'nextPage1'){
						currentPage = currentPage + 1;
						if(currentPage <= page){
							tbodyAppendUnused('tableContainer1',currentPage,data);
						}else{
							currentPage = currentPage - 1;
						}
					}else if(id == 'prevPage1'){
						currentPage = currentPage - 1;
						if(currentPage >= 1){
							tbodyAppendUnused('tableContainer1',currentPage,data);
						}else{
							currentPage = currentPage + 1;
						}
					}else if(id == 'lastPage1'){
						currentPage = page;
						tbodyAppendUnused('tableContainer1',currentPage,data);
					}
					controlShow1(currentPage,page);
					$('#curPage1').html(currentPage);
				});
				
				$('.selected').each(function(){
					$(this).hover(function(){
						$(this).css('cursor', 'pointer');
					},function(){
						
//					}).click(function(){
//						$('.selectabled').each(function() {
//							if ($(this).hasClass('selected')) {
//								$(this).removeClass('selected');
//							}
//							$(this).css('background-color', '#FFF');
//						});
//						$(this).addClass('selected');
//						$(this).css('background-color', '#CAE2FE');
					}).bind('dblclick',function(){
						useCertifiService('/birthCertificateInfo.html');
					});
				});
			}
		});
	}
}

//显示列
this.readerConfig = [ {
	name : 'certifiId'
}, {
	name : 'name'
}, {
	name : 'motherName'
}, {
	name : 'fatherName'
}, {
	name : 'borthOrganization'
}, {
	name : 'linkmanTel'
}];
this.gridCmConfig = [ {
	"header" : "<center>出生医学证明编号</center>",
	"dataIndex" : "certifiId",
	"width" : 200
}, {
	"header" : "<center>姓名</center>",
	"dataIndex" : "name"
}, {
	"header" : "<center>母亲姓名</center>",
	"dataIndex" : "motherName"
}, {
	"header" : "<center>父亲姓名</center>",
	"dataIndex" : "fatherName"
}, {
	"header" : "<center>接生机构</center>",
	"dataIndex" : "borthOrganization",
	"width" : 300
}, {
	"header" : "<center>联系电话</center>",
	"dataIndex" : "linkmanTel"
} ];
var tabUsedreader = new Ext.data.JsonReader({
	totalProperty : "totalSize",
	root : "data",
	id : "id"
}, Ext.data.Record.create(this.readerConfig));
var tabUsedstore = new Ext.data.Store({
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : BirthCertificateMsgService.getUsedCertificate,
		listeners : {
			'beforeload' : function(dataProxy, params) {
				var orgId = currentNode.id;
				var resTxt = Ext.getCmp('certifiId').getValue();
				var o = {
					orgId : orgId,
					resTxt : resTxt
				};
				if (!params.limit)
					params.limit = 15;
				params[dataProxy.loadArgsKey] = [ o, params ];
			}.createDelegate(this)
		}
	}),
	reader : tabUsedreader
});
this.tabUsedpagingBar = new Ext.PagingToolbar({
	pageSize : 15,
	store : tabUsedstore,
	displayInfo : true,
	displayMsg : '{0} - {1} of {2}',
	emptyMsg : "没有记录"
});
var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
this.gridCmConfig.unshift(sm);
this.tabUsedGrid = new Ext.grid.GridPanel({
	id : 'tabUsedGrid',
	bbar : this.tabUsedpagingBar,
	layout : 'fit',
	store : tabUsedstore,
	cm : new Ext.grid.ColumnModel(this.gridCmConfig),
	sm : sm
});

var tabDestroyedreader = new Ext.data.JsonReader({
	totalProperty : "totalSize",
	root : "data",
	id : "id"
}, Ext.data.Record.create(this.readerConfig));
var tabDestroyedstore = new Ext.data.Store({
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : BirthCertificateMsgService.getDestroyedCertiId,
		listeners : {
			'beforeload' : function(dataProxy, params) {
				var orgId = currentNode.id;
				var resTxt = Ext.getCmp('certifiId').getValue();
				var o = {
					orgId : orgId,
					resTxt : resTxt
				};
				if (!params.limit)
					params.limit = 15;
				params[dataProxy.loadArgsKey] = [ o, params ];
			}.createDelegate(this)
		}
	}),
	reader : tabDestroyedreader
});
this.tabDestroyedpagingBar = new Ext.PagingToolbar({
	pageSize : 15,
	store : tabDestroyedstore,
	displayInfo : true,
	displayMsg : '{0} - {1} of {2}',
	emptyMsg : "没有记录"
});
//var tabDestroyedsm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
//this.gridCmConfig.unshift(sm);
this.tabDestroyedGrid = new Ext.grid.GridPanel({
	id : 'tabDestroyedGrid',
	bbar : this.tabDestroyedpagingBar,
	layout : 'fit',
	store : tabDestroyedstore,
	cm : new Ext.grid.ColumnModel(this.gridCmConfig),
	sm : sm
});

var tabPigeonholedreader = new Ext.data.JsonReader({
	totalProperty : "totalSize",
	root : "data",
	id : "id"
}, Ext.data.Record.create(this.readerConfig));
var tabPigeonholedstore = new Ext.data.Store({
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : BirthCertificateMsgService.getPigeonholedCertiId,
		listeners : {
			'beforeload' : function(dataProxy, params) {
				var orgId = currentNode.id;
				var resTxt = Ext.getCmp('certifiId').getValue();
				var o = {
					orgId : orgId,
					resTxt : resTxt
				};
				if (!params.limit)
					params.limit = 15;
				params[dataProxy.loadArgsKey] = [ o, params ];
			}.createDelegate(this)
		}
	}),
	reader : tabPigeonholedreader
});
this.tabPigeonholedpagingBar = new Ext.PagingToolbar({
	pageSize : 15,
	store : tabPigeonholedstore,
	displayInfo : true,
	displayMsg : '{0} - {1} of {2}',
	emptyMsg : "没有记录"
});
this.tabPigeonholedGrid = new Ext.grid.GridPanel({
	id : 'tabPigeonholedGrid',
	bbar : this.tabPigeonholedpagingBar,
	layout : 'fit',
	store : tabPigeonholedstore,
	cm : new Ext.grid.ColumnModel(this.gridCmConfig),
	sm : sm
});

function handleUsed(tab){
	if(currentNode != null){
		Ext.getCmp('save').setDisabled(true);
		Ext.getCmp('change').setDisabled(true);
		Ext.getCmp('supply').setDisabled(true);
		Ext.getCmp('modify').setDisabled(false);
		Ext.getCmp('lookup').setDisabled(false);
		Ext.getCmp('destroy').setDisabled(false);
		Ext.getCmp('pigeonhole').setDisabled(false);
		Ext.getCmp('restore').setDisabled(true);
		Ext.getCmp('restorePigeonhole').setDisabled(true);
		Ext.getCmp('cancelUsed').setDisabled(false);
		var orgId = currentNode.id;
		var resTxt = Ext.getCmp('certifiId').getValue();
		//在此处做优化
		Ext.getCmp('tabUsedGrid').getStore().reload();
		this.doLayout(true);
	}
}

function handleDestroyed(tab){
	if(currentNode != null){
		Ext.getCmp('save').setDisabled(true);
		Ext.getCmp('change').setDisabled(true);
		Ext.getCmp('supply').setDisabled(true);
		Ext.getCmp('modify').setDisabled(true);
		Ext.getCmp('lookup').setDisabled(false);
		Ext.getCmp('destroy').setDisabled(true);
		Ext.getCmp('pigeonhole').setDisabled(true);
		Ext.getCmp('restore').setDisabled(false);
		Ext.getCmp('restorePigeonhole').setDisabled(true);
		Ext.getCmp('cancelUsed').setDisabled(true);
		//在此处做优化
		Ext.getCmp('tabDestroyedGrid').getStore().reload();
		this.doLayout(true);
	}
}

function handlePigeonholed(tab){
	if(currentNode != null){
		Ext.getCmp('save').setDisabled(true);
		Ext.getCmp('change').setDisabled(true);
		Ext.getCmp('supply').setDisabled(true);
		Ext.getCmp('modify').setDisabled(true);
		Ext.getCmp('lookup').setDisabled(false);
		Ext.getCmp('destroy').setDisabled(true);
		Ext.getCmp('pigeonhole').setDisabled(true);
		Ext.getCmp('restore').setDisabled(true);
		Ext.getCmp('restorePigeonhole').setDisabled(false);
		Ext.getCmp('cancelUsed').setDisabled(true);
		//在此处做优化
		Ext.getCmp('tabPigeonholedGrid').getStore().reload();
		this.doLayout(true);
	}
}

var runTabPanel = function(){
	var activeTab = Ext.getCmp('tabOrg').getActiveTab();
	var activeTabId = activeTab.id;
	if(activeTabId == 'tabUnused'){
		handleDistributed(activeTab);
	}else if(activeTabId == 'tabUsed'){
		handleUsed(activeTab);
	}else if(activeTabId == 'tabDestroyed'){
		handleDestroyed(activeTab);
	}else if(activeTabId == 'tabPigeonholed'){
		handlePigeonholed(activeTab);
	}
}

var researchPanel = new Ext.FormPanel({
	region : 'north',
	height : 35,
	frame : true,
//	layout : "absolute",
	items : [{
		text : 'text'
	}]
});
//功能按键
this.queryCertifiCardTxt = new Ext.form.Label({
	text : '出生医学证明编号：',
	style : 'font-weight:border;'
});
this.filterField = new Ext.form.TextField({
	fieldLabel : '',
	name : 'certifiId',
	id : 'certifiId',
	width : 100,
	listeners : {
		specialKey :function(field,e){
            if (e.getKey() == Ext.EventObject.ENTER){
            	if(currentNode != null){
					runTabPanel();
				}else{
					Ext.Msg.alert('提示','请选择组织机构');
				}
            }
        }
	}
});
this.researchBtn = new Ext.Button({
	text : '查询',
	name : 'research',
	id : 'research',
	iconCls : 'c_query',
	handler : function(){
		if(currentNode != null){
			runTabPanel();
		}else{
			Ext.Msg.alert('提示','请选择组织机构');
		}
	}
});
this.saveBtn = new Ext.Button({
	name : 'save',
	text : '首次签发',
	id : 'save',
	iconCls : 'c_add',
	handler : function(){
		if(currentNode != null){
			var flag = false;
			$('.selected').each(function(){
				flag = true;
			});
			if(flag){
				useCertifiService('/birthCertificateInfo.html');
			}else{
				showMsg('请选择出生证明编号');
			}
			
		}else{
			showMsg('请选择组织机构');
		}
	}
});
this.changeBtn = new Ext.Button({
	name : 'change',
	text : '换发',
	id : 'change',
	iconCls : 'c_change',
	handler : function(){
		if(currentNode != null){
			var flag = false;
			$('.selected').each(function(){
				flag = true;
			});
			if(flag){
				useCertifiService('/birthCertificateInfo.html');
			}else{
				showMsg('请选择出生证明编号');
			}
			
		}else{
			showMsg('请选择组织机构');
		}
	}
});

this.modifyBtn = new Ext.Button({
	name : 'modify',
	text : '修改',
	id : 'modify',
	iconCls : 'c_edit',
	disabled : true,
	handler : function(){
		services(1);
	}
});
this.destroyBtn = new Ext.Button({
	name : 'destroy',
	text : '作废',
	id : 'destroy',
	iconCls : 'c_del',
	disabled : true,
	handler : function(){
		services(3);
	}
});
this.pigeonholeBtn = new Ext.Button({
	name : 'pigeonhole',
	text : '归档',
	iconCls : 'addBusinessData',
	id : 'pigeonhole',
	disabled : true,
	handler : function(){
		if(currentNode != null){
			var certifiId = getCertifiId();
			if(certifiId != ''){
				BirthCertificateMsgService.setPigeonhole(certifiId,0,function(data){
					if(data){
						showMsg('归档成功');
						runTabPanel();
					}
				});
			}else{
				showMsg('请选择出生证明信息');
			}
		}else{
			showMsg('请选择组织机构');
		}
	}
});
this.supplyBtn = new Ext.Button({
	name : 'supply',
	text : '补发',
	id : 'supply',
	iconCls : 'c_addsupply',
	handler : function(){
		if(currentNode != null){
			var flag = false;
			$('.selected').each(function(){
				flag = true;
			});
			if(flag){
				useCertifiService('/birthCertificateInfo_supply.html');
			}else{
				showMsg('请选择出生证明编号');
			}
			
		}else{
			showMsg('请选择组织机构');
		}
	}
});
this.lookupBtn = new Ext.Button({
	name : 'lookup',
	text : '查看',
	id : 'lookup',
	iconCls : 'c_msg',
	disabled : true,
	handler : function(){
		if(currentNode != null){
			var certifiId = getCertifiId();
			if(certifiId != ''){
				var activeTabId = Ext.getCmp('tabOrg').getActiveTab().id;
				var type;
				if(activeTabId == 'tabDestroyed'){
					type = 5;
				}else{
					type = 2;
				}
				services(type);
			}else{
				showMsg('请选择出生证明信息');
			}
		}else{
			showMsg('请选择组织机构');
		}
	}
});
this.advancedF = new Ext.Button({
	text: '高级功能',
	iconCls: 'c_advancedF',
	menu: new Ext.menu.Menu({
        items: [new Ext.Action({
        	name : 'restore',
			text : '作废还原',
			id : 'restore',
			disabled : true,
			handler : function(){
				services(4);
			}
		}),new Ext.Action({
			name : 'restore',
			text : '归档还原',
			id : 'restorePigeonhole',
			disabled : true,
			handler : function(){
				if(currentNode != null){
					var certifiId = getCertifiId();
					if(certifiId != ''){
						BirthCertificateMsgService.setPigeonhole(certifiId,1,function(data){
							if(data){
								showMsg('归档还原成功');
								runTabPanel();
							}
						});
					}else{
						showMsg('请选择出生证明信息');
					}
				}else{
					showMsg('请选择组织机构');
				}
			}
		}),new Ext.Action({
			name : 'cancel',
			text : '撤消使用',
			width : 70,
			id : 'cancelUsed',
			disabled : true,
			handler : function(){
				if(currentNode != null){
					var certifiId = getCertifiId();
					if(certifiId != ''){
						BirthCertificateMsgService.setCancelUsed(certifiId,function(data){
							if(data){
								showMsg('撤消成功');
								runTabPanel();
							}
						});
					}else{
						showMsg('请选择出生证明信息');
					}
				}else{
					showMsg('请选择组织机构');
				}
			}
		})]
   	})
});
app.useCertifi = new Ext.Panel({
	layout : 'border',
	tbar : ['-',this.queryCertifiCardTxt,this.filterField,this.researchBtn,'-',this.saveBtn,this.changeBtn,this.supplyBtn,'-',this.modifyBtn,
	        this.destroyBtn,this.pigeonholeBtn,'-',this.lookupBtn,this.advancedF],
	items : [{
//		region : 'north',
//		xtype : 'panel',
//		frame : true,
//		height : 50,
//		items : [researchPanel]
//	},{
		region: 'west',
        title: '组织机构',
        xtype: 'treepanel',
        width: 200,
        layout: 'fit',
        autoScroll: true,
        lines: true,
        id : 'orgTree',
        name : 'orgTree',
        loader: new Ext.ux.DWRTreeLoader({
            dwrCall: UserMenuTreeService.getOrganizationNodes
        }),
        root : new Ext.tree.AsyncTreeNode( {
            text : 'root',
            draggable : false,
            id : 'org'
        }),
        rootVisible: false
	},{
		region: 'center',
        xtype: 'tabpanel',
        id : 'tabOrg',
        name : 'tabOrg',
        activeTab : 0,
        defaults: {autoScroll:true},
        items: [{
            title: '未使用',
            html: '<div id="unUsed"></div>',
            listeners: {activate: handleDistributed},
            id : 'tabUnused',
            name : 'tabUnused'
        },{
            title: '已使用',
//            html: '<div id="used"></div>',
            listeners: {activate: handleUsed},
            id : 'tabUsed',
            name : 'tabUsed',
            layout : 'fit',
            items : [this.tabUsedGrid]
        },{
            title: '已作废',
//            html: '<div id="destroyed"></div>',
            listeners: {activate: handleDestroyed},
            id : 'tabDestroyed',
            name : 'tabDestroyed',
            layout : 'fit',
            items : [this.tabDestroyedGrid]
        },{
            title: '已归档',
//            html: '<div id="pigeonholed"></div>',
            listeners: {activate: handlePigeonholed},
            id : 'tabPigeonholed',
            layout : 'fit',
            name : 'tabPigeonholed',
            items : [this.tabPigeonholedGrid]
        }]
	}]
});
Ext.getCmp('orgTree').on({
	click : {
		stopEvent : true,
		fn : function(n,e){
			e.stopEvent();
			currentNode = n;
			runTabPanel();
		}.createDelegate(this)
	}
});
ModuleMgr.register(app.useCertifi);