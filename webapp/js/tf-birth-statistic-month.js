Ext.ns("Ext.tf");
// /////////////
// 统计查询模板
// /////////////
Ext.tf.BirthCertificateReportWithMonth = Ext.extend(Ext.Panel, {
	closable : true,
	layout : 'fit',
	pageSize : 20,
	recordId : 'id',
	recordPk : 'id',
	curNode : null,
	queryJson : null,
	// height:700,

	// 设置查询url
	queryUrl : Ext.emptyFn,
	deleteUrl : Ext.emptyFn,

	// 设置查询用的类别，比如档案，高血压等。。
	readerConfig : [],
	gridCmConfig : [],

	initComponent : function() {
		this.build();
		Ext.tf.BirthCertificateReportWithMonth.superclass.initComponent
				.call(this);
	},

	build : function() {
		this.items = [ this.createPanel() ];
	},
	getCurTreeNode : function(){
		var selNode = this.curNode;
		if (selNode) {
			// Ext.Msg.alert('', selNode.text);
		} else {
			Ext.Msg.show({
				icon : Ext.Msg.WARNING,
				buttons : Ext.Msg.OK,
				msg : '请先选择一个组织结构！'
			});
		}
		;
		return selNode;
	},
	
	loadReportData : function(){
		var selNode = this.getCurTreeNode();
		
		if(selNode){
			var orgId = selNode.id;
			var now = new Date();
			var bean = {
				orgId : orgId,
				reportYear : now.getFullYear(),
				reportMonth : (now.getMonth() + 1)
			}
			this.queryJson = {
				treeNode : selNode,
				bean : bean
			}
//			console.log(bean);
//			this.panel.getEl().mask();
			BirthReportWithMonthService.getReportData(bean,function(data){
				if(data != null){
					if(data.length == 1){
						var objs = data[0];
						for(var p in objs){
							//console.log(p + '-------' + objs[p]);
							$('.birthReportWithMonth .' + p).html(objs[p]);
						}
					}
				}
//				this.panel.getEl().mask();
			});
		}
	},
	getParams : function(){
		var queryCondition = Ext.getCmp('queryCondition').getValue();
		var cond = {
			queryCondition : queryCondition,
			orgId : this.getCurTreeNode().id
		};
		return cond;
	},
	load : function(){
		var selNode = this.getCurTreeNode();
		if(selNode){
			this.grid.getStore().reload();
			this.doLayout(true);
		}
		
	},
	downloadBirthReport : function(){
		selections = this.grid.getSelections();
		if(selections.length > 0){
			var storefilenames = [];
			for(var i=0;i<selections.length;i++){
				storefilenames.push(selections[i].json.storeFileName);
			}
			BirthReportWithMonthService.getBirthReportUrl(storefilenames,function(data){
				if(data != null){
					if(data.length > 0){
						for(var i = 0;i < data.length;i++){
							window.location.href = data[i];
						}
					}
				} 
			});
		}
	},
	createPanel : function() {
		this.menu = new Ext.tree.TreePanel({
			region: 'west',
		    title: '组织机构',
		    width: 200,
			layout : 'fit',
			lines: true,
			id : 'orgTree',
		    name : 'orgTree',
			animate : true,
			enableDD : false,
			loader : new Ext.ux.DWRTreeLoader({
				dwrCall : UserMenuTreeService.getOrganizationNodes
			}),
			autoScroll : true,
			border : false,
			root : new Ext.tree.AsyncTreeNode({
				text : 'root',
				draggable : false,
				id : 'org'
			}),
			rootVisible : false
		});
		this.menu.getRootNode().on({
			append : {
				stopEvent : true,
				fn : function(t, me, n, index) {
					// 自动展开根节点的第一个孩子
					if (index == 0) {
						if (!n.leaf)
							n.expand();
//						this.curNode = n;
					}
				}.createDelegate(this)
			}
		});
		this.menu.on({
			click : {
				stopEvent : true,
				fn : function(n, e) {
					e.stopEvent();
					this.curNode = n;
					var activeTab = Ext.getCmp('birthReportPanel').getActiveTab();
					var activeTabId = activeTab.id;
					if(activeTabId == 'curReportPanel'){
						this.loadReportData();
					}else if(activeTabId == 'historyBirthReport'){
						this.load();
					}
					
				}.createDelegate(this)
			}
		});
		
		var reader = new Ext.data.JsonReader({
			totalProperty : "totalSize",
			root : "data",
			id : "id"
		}, Ext.data.Record.create(this.readerConfig));
		var store = new Ext.data.Store({
			proxy : new Ext.ux.data.DWRProxy({
				dwrFunction : this.queryUrl,
				listeners : {
					'beforeload' : function(dataProxy, params) {
						var o = this.getParams();
						if (!params.limit)
							params.limit = this.pageSize;
						params[dataProxy.loadArgsKey] = [ o, params ];
					}.createDelegate(this)
				}
			}),
			reader : reader
		});
		this.pagingBar = new App.PagingToolbar({
			pageSize : this.pageSize,
			store : store,
			displayInfo : true,
			displayMsg : '{0} - {1} of {2}',
			emptyMsg : "没有记录"
		});
		var sm = new Ext.grid.CheckboxSelectionModel();
		this.gridCmConfig.unshift(sm);
		this.grid = new Ext.grid.GridPanel({
			bbar : this.pagingBar,
			layout : 'fit',
			store : store,
			cm : new Ext.grid.ColumnModel(this.gridCmConfig),
			sm : sm
		});
		this.grid.getView().on('refresh', function() {
			// 缺省选择grid的第一条记录
			var model = this.grid.getSelectionModel();
			if (model.getCount() == 0) {
				model.selectFirstRow();
			}
		}.createDelegate(this));
		this.grid.on("rowcontextmenu",function(grid,rowIndex,e){
			if(rowIndex < 0)
				return;
			e.preventDefault();
			e.stopEvent();
			this.grid_menu = new Ext.menu.Menu({
				items : [{
					text : '另存为电子版本',
					iconCls : 'c_change',
					handler : function(){
						this.downloadBirthReport();
					}.createDelegate(this)
				}]
			});
			this.grid_menu.showAt(e.getPoint());
		}.createDelegate(this));
		
		this.panel = new Ext.Panel({
			layout : 'border',
			autoScroll : true,
			items : [ this.menu,{
				region: 'center',
		        xtype: 'tabpanel',
		        activeTab : 0,
		        id : 'birthReportPanel',
		        defaults: {autoScroll:true},
		        items: [{
		        	title : '当月统计表',
		        	id : 'curReportPanel',
		        	tbar : ['-',{
		        		text : '生成月统计表',
		        		iconCls : 'c_add',
		        		handler : function(){
		        			this.loadReportData();
		        		}.createDelegate(this)
		        	},{
		        		text : '打印月统计表',
		        		iconCls : 'c_print',
		        		handler : function(){
		        			console.log(Ext.getCmp('curReportPanel').body.dom.innerHTML);
		        			printObj.printHTML(Ext.getCmp('curReportPanel').body.dom.innerHTML,'《出生医学证明》管理使用情况月统计表','30.8cm','18cm');
		        		}.createDelegate(this)
		        	},{
		        		text : '另存为电子版本',
		        		iconCls : 'c_change',
		        		handler : function(){
		        			if(this.queryJson != null){
		        				var htmls = Ext.getCmp('curReportPanel').body.dom.innerHTML;
		        				var titles = this.queryJson.bean.reportYear + '-' + (this.queryJson.bean.reportMonth < 10 ? '0' + this.queryJson.bean.reportMonth : this.queryJson.bean.reportMonth) + '_' + this.queryJson.treeNode.text;
		        				var json = {
		        					htmls : htmls,	
		        					titles : titles,
		        					orgId : this.queryJson.bean.orgId,
		        					reportYear : this.queryJson.bean.reportYear,
		        					reportMonth : this.queryJson.bean.reportMonth
		        				}
		        				BirthReportWithMonthService.generateWord(json,function(data){
		        					if(data != null && data != ''){
		        						window.location.href = data;
		        					}
		        				});
		        			}
		        			
		        		}.createDelegate(this)
		        	}],
		        	html : '<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><title>Insert title here</title><style type="text/css">	.reportTable {		margin-top : 10px;	}	.reportTable thead td{		border-left: 1px solid #000;		border-top: 1px solid #000;		text-align: center;		line-height: 30px;		padding-left:1px;		padding-right:1px;		height: 30px;	}	.reportTable tbody td{		border-left: 1px solid #000;		border-top: 1px solid #000;		border-bottom: 1px solid #000;		height : 60px;		text-align: center;	}</style></head><body>	<div style="width: 1080px;height: 600px;margin-left: 10px;margin-top: 10px;" class="birthReportWithMonth">		<p style="font-size: 18px;">附件5</p>		<center><h2 style="font-size: 25px;margin-top:50px;margin-bottom:50px;">《出生医学证明》管理使用情况月统计表</h2></center>		<div style="font-size: 14px;">报表月份<span style="margin-left: 10px;margin-right: 10px;" class="ReportMonth"></span>月</div>		<table cellpadding="0" cellspacing="0" width="100%" class="reportTable">			<thead>				<tr>					<td rowspan="3">上月底库存数(1)</td>					<td rowspan="3">当月申领数(2)</td>					<td colspan="12">当月使用情况</td>					<td rowspan="3">当月月底库存数(15)</td>					<td rowspan="3" style="border-right: 1px solid #000;">当月医疗保健机构内活产数(16)</td>				</tr>				<tr>					<td colspan="4">医疗保健机构内出生的签发数</td>					<td colspan="3">医疗保健机构外出生的签发数</td>					<td colspan="4">废证数</td>					<td rowspan="2">合计(14)</td>				</tr>				<tr>					<td>首次签发数(3)</td>					<td>换发数(4)</td>					<td>补发数(5)</td>					<td>小计(6)</td>					<td>家庭接生员接生的签发数(7)</td>					<td>其他情况的签发数(8)</td>					<td>小计(9)</td>					<td>因打印或填写错误数(10)</td>					<td>遗失数(11)</td>					<td>其他原因数(12)</td>					<td>小计(13)</td>				</tr>			</thead>			<tbody>				<tr>					<td><span class="PreMonthCount"></span></td>					<td><span class="CurMonthGetCount"></span></td>					<td><span class="InnerHosptal01"></span></td>					<td><span class="InnerHosptal02"></span></td>					<td><span class="InnerHosptal03"></span></td>					<td><span class="InnerHosptalTotal"></span></td>					<td><span class="OuterHosptal01"></span></td>					<td><span class="OuterHosptal02"></span></td>					<td><span class="OuterHosptalTotal"></span></td>					<td><span class="DestroyCount01"></span></td>					<td><span class="DestroyCount02"></span></td>					<td><span class="DestroyCount03"></span></td>					<td><span class="DestroyCountTotal"></span></td>					<td><span class="Totals"></span></td>					<td><span class="CurMonthCount"></span></td>					<td style="border-right: 1px solid #000;"><span class="CurLifeChild"></span></td>				</tr>			</tbody>		</table>		<p style="font-size: 14px;">注：本统计表的《出生医学证明》数量为内芯数量，单位均为“枚”。</p>		<p style="font-size: 14px;margin-left:25px;">表中逻辑关系：(6) = (3) + (4) + (5)； (9) = (7) + (8)； (13) = (10) + (11) + (12)； (14) = (6) + (9) + (13)； (15) = (1) + (2) - (14)</p>		<p style="font-size: 14px;">单位名称（盖章）：<span></span></p>		<table style="width: 100%" cellpadding="0" cellspacing="0">			<tr>				<td>					<p style="font-size: 14px;">负责人：<span></span></p>				</td>				<td>					<p style="font-size: 14px;">填表人：<span></span></p>				</td>				<td>					<p style="font-size: 14px;">填表日期：<span></span></p>				</td>			</tr>		</table>	</div></body></html>'
		        },{
		        	title : '历史月统计表',
		        	id : 'historyBirthReport',
		        	tbar : ['-',{
		        		xtype : 'textfield',
		        		emptyText : '请输入报表名称',
		        		width : 150,
		        		id : 'queryCondition'
		        	},{
		        		text : '查询',
		        		iconCls : 'c_query',
		        		handler : function(){
		        			this.load();
		        		}.createDelegate(this)
		        	},{
		        		text : '另存为本地',
		        		iconCls : 'c_change',
		        		handler : function(){
		        			this.downloadBirthReport();
		        		}.createDelegate(this)
		        	}],
		        	layout : 'fit',
		        	items : [this.grid]
		        }]
			} ]
		});
		return this.panel;
	}
});