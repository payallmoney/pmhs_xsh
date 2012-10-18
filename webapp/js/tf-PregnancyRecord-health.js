Ext.ns("Ext.tf");

Ext.tf.HealthPregnancyRecordPanel = Ext.extend(Ext.Panel, {
	closable : true,
	currentNode : null, // 当前选择的树节点
	layout : 'fit',
	title : '档案',
	pageSize : 15,
	pageSize01 : 10,
	recordId : 'id',
	recordPk : 'id',
	judgeCondId : '',
	judgeCondVal : '',
	panelId : 'app.HealthPregnancyRecordPanel',
	// height:700,
	// 是否需要在最末级才能增加？
	checkLastLevel : true,

	// 设置查询url
	queryUrl : Ext.emptyFn,
	queryUrl01 : Ext.emptyFn,
	deleteUrl : Ext.emptyFn,
	dataExportUrl : Ext.emptyFn,
	treeLoaderFn : Ext.emptyFn,

	// 设置查询用的类别，比如档案，高血压等。。
	readerConfig : [],
	gridCmConfig : [],
	readerConfig01 : [],
	gridCmConfig01 : [],
	gridViewConfig : {},
	initComponent : function() {
		this.build();
		Ext.tf.HealthPregnancyRecordPanel.superclass.initComponent.call(this);
	},

	build : function() {
		this.items = [ this.createPanel() ];
	},

	getTreeSelNode : function() {
		var selNode = this.currentNode;
		if (selNode) {
			// Ext.Msg.alert('', selNode.text);
		} else {
			Ext.Msg.show({
				icon : Ext.Msg.WARNING,
				buttons : Ext.Msg.OK,
				msg : '请先选择一个行政区域！'
			});
		}
		;
		return selNode;
	},
	createActions : function() {
		var searchCondition =  [ [ 'a.name', '姓名' ], [ 'c.highRisk', '高危筛选' ],
		     					[ 'a.inputDate', '录入日期' ], [ 'a.lastModifyDate', '修改日期' ],
		    					[ 'b.birthday', '出生日期' ], [ 'a.fileNo', '档案编码' ],
		    					[ 'b.idnumber', '身份证号' ], [ 'b.linkman', '联系人' ],
		    					[ 'a.paperFileNo', '纸质档案号' ], [ 'b.workUnit', '工作单位' ] ];
		var store = new Ext.data.SimpleStore({
			fields : [ 'type', 'display' ],
			data : searchCondition
		});
		this.combo = new Ext.form.ComboBox({
			store : store,
			displayField : 'display',
			valueField : 'type',
			typeAhead : true,
			mode : 'local',
			triggerAction : 'all',
			selectOnFocus : true,
			editable : false,
			width : 100,
			value : 'a.name'
		});
		this.filterField = new Ext.form.TextField({
			fieldLabel : '',
			enableKeyEvents : true,
			listeners : {
				'keypress' : function(field, event) {
					if (event.getKey() == 13) {
						this.load(true);
					}
					;
				}.createDelegate(this)
			}
		});

		var advancedF = null;
		var dataExport = new Ext.Action({
			text : '数据导出',
			iconCls : 'c_add',
			handler : function() {
				var selNode = this.getTreeSelNode();
				if (selNode) {
					var disNo = selNode.id;
					var id = this.panelId;
					Ext.getCmp(id).getEl().mask('导出数据加载中...');
					var filterKey = this.combo.getValue();
					var filterValue = this.filterField.getValue();
					this.dataExportUrl(disNo, filterKey, filterValue,
							function(data) {
								window.location.href = data;
								// UserMenuTreeService.removeDataExportFile(data);
								Ext.getCmp(id).getEl().unmask();
							});
				}
			}.createDelegate(this)
		});
		var store01 = new Ext.data.SimpleStore({
			fields : [ 'type', 'display' ],
			data : [ [ '100', '全部' ], [ '0', '未结案' ], [ '1', '已结案' ],
					[ '2', '终止妊娠' ] ]
		});
		this.combo01 = new Ext.form.ComboBox({
			store : store01,
			displayField : 'display',
			valueField : 'type',
			typeAhead : true,
			mode : 'local',
			triggerAction : 'all',
			selectOnFocus : true,
			editable : false,
			width : 80,
			value : '100'
		});
		advancedF = dataExport;
		funcAction = [new Ext.Action({
			text : '增加',
			iconCls : 'addBusinessData',
			handler : function() {
				var selections = this.grid.getSelections();
				var judgeId = this.judgeCondId;
				var judgeVal = this.judgeCondVal;
				if (selections.length == 1) {
					if(selections[0].get(judgeId) == judgeVal){
						MethodObj.pregnancyRecordFunc(selections,0,this.gridother);
					}
				}
			}.createDelegate(this)
		}),new Ext.Action({
			text : '修改',
			iconCls : 'c_edit',
			handler : function() {
				var selections = this.gridother.getSelections();
				if (selections.length == 1) {
					MethodObj.pregnancyRecordFunc(selections,1,this.gridother);
				}
			}.createDelegate(this)
		}),new Ext.Action({
						text : '删除',
						iconCls : 'c_del',
						handler : function() {
							var selections = this.gridother.getSelections();
							if (selections.length > 0) {
								var array = [];
								var pk = this.recordPk;
								Ext.each(selections, function(v) {
									array.push(v.get(pk));
								});
								var del = function(e) {
									if (e == "yes") {
										this.deleteUrl(array, {
											callback : function(data) {
												showInfoObj.Infor('删除成功！');
												this.load01();
											}.createDelegate(this),
											errorHandler : function(msg) {
												console.log(msg);
												showInfoObj.Infor('删除出错！');
											}
										});
									}
								};
								Ext.MessageBox.confirm("提示", "确认要删除所选择的记录么？", del,
										this);
							}
						}.createDelegate(this)
					}),
					'-',
					this.combo01,
					this.combo,
					this.filterField,
					new Ext.Action({
						text : '查询',
						iconCls : 'c_query',
						handler : function() {
							this.load(true);
						}.createDelegate(this)
					}),advancedF];
		return funcAction;
	},

	/*
	 * 取得行政树的节点 如果节点没有选中，提示信息，返回空 如果选中，再取得过滤条件，组合成查询条件，并返回之
	 */
	getParams : function() {
		var selNode = this.getTreeSelNode();
		if (selNode) {
			var filterKey = this.combo.getValue();
			var filterValue = this.filterField.getValue();
			var filterVal01 = '';
			if(this.combo01){
				filterVal01 = this.combo01.getValue();
			}
			var cond = {
				district : selNode.id,
				filterKey : filterKey,
				filterValue : filterValue,
				filterVal01 : filterVal01
			};
			return cond;
		}
		return null;
	},

	/*
	 * 查询数据, 如果树没有选择了节点，不执行
	 */
	load : function(isReset) {
		var selNode = this.getTreeSelNode();
		if (selNode) {
			if (isReset) {
				this.pagingBar.changePage(1);
			}
			this.grid.getStore().reload();
			this.doLayout(true);
		}
	},

	load01 : function(isReset) {
		if (isReset) {
			this.pagingBar01.changePage(1);
		}
		this.gridother.getStore().reload();
		this.doLayout(true);
		
	},
	
	createPanel : function() {
		var reader = new Ext.data.JsonReader({
			totalProperty : "totalSize",
			root : "data",
			id : this.recordId
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
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		this.gridCmConfig.unshift(sm);
		this.grid = new Ext.grid.GridPanel({
			title : '请选择一个行政区划',
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
				this.load01(true);
			}
		}.createDelegate(this));

// this.grid.on('rowdblclick', this.editFn, this);
//		this.grid.on('rowdblclick', function(){
//			var selections = this.grid.getSelections();
//			if (selections.length == 1) {
//				console.log(selections[0]);
//				this.f_edit(selections[0]);
//			}
//		}, this);
		this.grid.on('rowclick', function(){
			this.load01(true);
		}, this);
		var reader01 = new Ext.data.JsonReader({
			totalProperty : "totalSize",
			root : "data",
			id : this.recordId
		}, Ext.data.Record.create(this.readerConfig01));

		var store01 = new Ext.data.Store({
			proxy : new Ext.ux.data.DWRProxy({
				dwrFunction : this.queryUrl01,
				listeners : {
					'beforeload' : function(dataProxy, params) {
						var selections = this.grid.getSelections();
						var healthFileMaternalId = '';
						if (selections.length == 1) {
							healthFileMaternalId = selections[0].get(this.recordId)
						}
						var o = {healthFileMaternalId : healthFileMaternalId};
						
						if (!params.limit)
							params.limit = this.pageSize01;
						params[dataProxy.loadArgsKey] = [ o, params ];
					}.createDelegate(this)
				}
			}),
			reader : reader01
		});

		this.pagingBar01 = new App.PagingToolbar({
			pageSize : this.pageSize01,
			store : store01,
			displayInfo : true,
			displayMsg : '{0} - {1} of {2}',
			emptyMsg : "没有记录"
		});
		var sm01 = new Ext.grid.CheckboxSelectionModel();
		this.gridCmConfig01.unshift(sm01);
		this.gridother = new Ext.grid.GridPanel({
			title : '特殊情况记录',
			bbar : this.pagingBar01,
			layout : 'fit',
			store : store01,
			cm : new Ext.grid.ColumnModel(this.gridCmConfig01),
			sm : sm01
		});
		this.gridother.getView().on('refresh', function() {
			// 缺省选择grid的第一条记录
			var model = this.gridother.getSelectionModel();
			if (model.getCount() == 0) {
				model.selectFirstRow();
			}
		}.createDelegate(this));

// this.grid.on('rowdblclick', this.editFn, this);
		
		
		this.menu = new Ext.tree.TreePanel({
			layout : 'fit',
			animate : true,
			enableDD : false,
			loader : new Ext.ux.DWRTreeLoader({
				dwrCall : this.treeLoaderFn
			}),
			lines : true,
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
						this.currentNode = n;
						// this.load();
					}
				}.createDelegate(this)
			}
		});

		this.menu.on({
			click : {
				stopEvent : true,
				fn : function(n, e) {
					e.stopEvent();
					this.currentNode = n;
					this.grid.setTitle(n.text);
					this.load();
					this.load01(true);
				}.createDelegate(this)
			}
		});

		var panel = new Ext.Panel({
			layout : 'border',
			autoScroll : true,
			id : this.panelId,
			tbar : this.createActions(),
			items : [ {
				region : 'west',
				layout : 'fit',
				frame : false,
				title : '行政区划',
				split : true,
				collapsible : true,
				layoutConfig : {
					animate : true
				},
				width : 200,
				minSize : 100,
				maxSize : 400,
				border : false,
				items : [ this.menu ]
			}, {
				region : 'center',
				layout : 'border',
				frame : false,
				border : false,
				items : [ {
					region : 'center',
					layout : 'fit',
					frame : false,
					border : false,
					items : [ this.grid ]
				},{
					region : 'south',
					layout : 'fit',
					frame : false,
					border : false,
					height : 300,
					items : [ this.gridother ]
				} ]
			} ]
		});
		return panel;
	}
});
