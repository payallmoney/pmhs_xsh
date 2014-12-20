Ext.ns("Ext.tcm");

Ext.grid.GridPanel.prototype.initComponent = Ext.grid.GridPanel.prototype.initComponent.createInterceptor(function() {
	if (this.store && this.bbar && this.bbar.xtype == 'paging' && !(this.bbar instanceof Ext.PagingToolbar) && !this.bbar.store) {
		if (this.store.xtype && !(this.store instanceof Ext.data.Store)) {
			this.store = Ext.ComponentMgr.create(this.store);
		}
		this.bbar.store = this.store;
		if (this.bbar.xtype && !(this.bbar instanceof Ext.PagingToolbar)) {
			this.bbar = Ext.ComponentMgr.create(this.bbar);
		}
	}
});
// Ext.QuickTips.init();
tcmString = {
	"1" : "中医饮食调养指导",
	"2" : "中医起居调摄指导",
	"3" : "传授摩腹、捏脊方法",
	"4" : "传授按揉迎香穴、足三里穴方法",
	"5" : "传授按揉四神聪穴方法",
	"6" : "其他",
},
tcmcurrentnode = null;
tcmConfig = {
	'hf.name' : {
		code : 'hf.name',
		text : '姓名',
		type : 'string',
		opt : 'alike'
	},
	'hf.fileno' : {
		code : 'hf.fileno',
		text : '档案编码',
		type : 'string',
		opt : 'alike'
	},
	'pf.birthday' : {
		code : 'pf.birthday',
		text : '出生日期',
		type : 'date',
		opt : '='
	}
}
function getTCMString(tcm) {
	if (tcm != null && tcm.length && tcm.length > 0) {
		var tcms = ',' + tcm + ",";
		var ret = '<ul style="position:relative;">';
		for (var i = 1; i < 7; i++) {
			if (tcms.indexOf("," + i + ",") >= 0) {
				ret += "<li class='checkedtcm'>" + i + '、' + tcmString["" + i] + ''
			} else {
				ret += "<li>" + i + '、' + tcmString["" + i] + ''
			}

		}
		ret += '</ul>';
		return ret;
	} else {
		return '';
	}
}

Ext.tcm.tcmQuery = new Ext.Panel({
	// layout : 'anchor',
	layout : 'border',
	defaults : {
		style : "float:left",
	},
	tbar : [ '选择条件：', {
		xtype : 'combo',
		id : 'tcmQuery.query.combokey',
		store : [ [ 'hf.name', '姓名' ], [ 'hf.fileno', '档案编号' ], [ 'pf.birthday', '出生日期' ] ],
		mode : 'local',
		editable : false,
		width : 80,
		allowBlank : false,
		triggerAction : 'all'
	}, {
		xtype : 'field',
		id : 'tcmQuery.query.combovalue',
	// format : 'Y-m-d',
	// value :new Date()
	}, '-', '录入日期：', {
		xtype : 'datefield',
		id : 'tcmQuery.query.startdatefield',
		format : 'Y-m-d',
	// value : new Date()
	}, "至", {
		xtype : 'datefield',
		id : 'tcmQuery.query.enddatefield',
		format : 'Y-m-d',
	// value : new Date()
	}, "-", {
		text : '查询',
		iconCls : 'c_refresh',
		id : 'tcmQuery.querybtn',
		handler : function(obj) {
			// obj.disable();
			Ext.getCmp("tcmQuery.querybtn").disable();
			Ext.getCmp("tcmQuery.msgsender.grid").getStore().reload();
		}
	}, {
		text : '导出',
		iconCls : 'c_save',
		id : 'tcmQuery.savebtn',
		handler : function(obj) {
			// obj.disable();
			// Ext.getCmp(this).getEl().mask('导出数据加载中...');
			var cond = {
				district : "",
				conditions : []
			};
			var root = Ext.getCmp("tcmQuery.query.district").getSelectionModel().getSelectedNode();
			if (!root) {
				root = Ext.getCmp("tcmQuery.query.district").getRootNode().firstChild;
			}
			cond.district = root.id;
			while (cond.district.substr(cond.district.length - 2, 2) == '00') {
				cond.district = cond.district.substr(0, cond.district.length - 2);
			}
			if (!Ext.isEmpty(Ext.getCmp("tcmQuery.query.startdatefield").getValue())) {
				cond.conditions[cond.conditions.length] = {
					filterKey : "begindate",
					filterVal : Ext.getCmp("tcmQuery.query.startdatefield").getValue(),
					opt : ">=",
					type : 'date',
					notsql : true
				};
			}
			if (!Ext.isEmpty(Ext.getCmp("tcmQuery.query.enddatefield").getValue())) {
				cond.conditions[cond.conditions.length] = {
					filterKey : "enddate",
					filterVal : Ext.getCmp("tcmQuery.query.enddatefield").getValue(),
					opt : "<=",
					type : 'date',
					notsql : true
				};
			}
			if (!Ext.isEmpty(Ext.getCmp("tcmQuery.query.combovalue").getValue())) {
				cond.conditions[cond.conditions.length] = {
					filterKey : Ext.getCmp("tcmQuery.query.combokey").getValue(),
					filterVal : Ext.getCmp("tcmQuery.query.combovalue").getValue(),
					opt : tcmConfig[Ext.getCmp("tcmQuery.query.combokey").getValue()].opt,
					type : tcmConfig[Ext.getCmp("tcmQuery.query.combokey").getValue()].type
				};
			}
			DataExportService.exportTcmQuery(cond, function(data) {
				window.location.href = data;
				Ext.getCmp(id).getEl().unmask();
			});
		}
	} ],
	items : [ {
		width : 200,
		region : 'west',
		items : [ {
			xtype : 'treepanel',
			layout : 'fit',
			animate : true,
			title : '行政区划',
			id : 'tcmQuery.query.district',
			enableDD : false,
			loader : new Ext.ux.DWRTreeLoader({
				dwrCall : UserMenuTreeService.getUserDistrictNodes
			}),
			lines : true,
			autoScroll : true,
			border : false,
			root : new Ext.tree.AsyncTreeNode({
				text : 'root',
				draggable : false,
				id : 'org'
			}),
			rootVisible : false,
//			selModel : new Ext.tree.DefaultSelectionModel({
//				listeners :{
//					selectionchange :function(){
//						Ext.getCmp("tcmQuery.msgsender.grid").store.reload();
//					}
//				}
//			}),
			listeners : {
				click:function(node){
					tcmcurrentnode = node;
					Ext.getCmp("tcmQuery.msgsender.grid").store.reload();
				},
				load : function() {
					Ext.getCmp("tcmQuery.msgsender.grid").store.reload();
				}
			}
		}

		]
	}, {
		xtype : 'grid',
		region : 'center',
		id : 'tcmQuery.msgsender.grid',
		listeners : {
			rowdblclick : function(obj, rowIndex, e) {
				var row = obj.store.getAt(rowIndex).data;
				TcmService.finddetail(row.type, row.id, function(data) {
					console.log(data);
					var dataarray = [];
					for (var i = 0; i < 14; i++) {
						dataarray[i] = [];
					}
					dataarray[0] = [ '随访日期' ];
					dataarray[1] = [ '中医健康管理服务' ];
					dataarray[2] = [ '下次随访日期' ];
					dataarray[3] = [ '医生签名' ];
					dataarray[4] = [ '月龄', '12月龄', '18月龄', '24月龄', '30月龄' ];
					dataarray[5] = [ '随访日期' ];
					dataarray[6] = [ '中医健康管理服务' ];
					dataarray[7] = [ '下次随访日期' ];
					dataarray[8] = [ '医生签名' ];
					dataarray[9] = [ '月龄', '3岁', '4岁', '5岁', '6岁' ];
					dataarray[10] = [ '随访日期' ];
					dataarray[11] = [ '中医健康管理服务' ];
					dataarray[12] = [ '下次随访日期' ];
					dataarray[13] = [ '医生签名' ];
					for (var i = 0; i < data.length; i++) {
						console.log(Math.floor(i / 4) * 5);
						dataarray[Math.floor(i / 4) * 5].push(data[i].visitdate);
						// 处理tcm
						dataarray[Math.floor(i / 4) * 5 + 1].push(getTCMString(data[i].tcm));
						dataarray[Math.floor(i / 4) * 5 + 2].push(data[i].nextvisitdate);
						dataarray[Math.floor(i / 4) * 5 + 3].push(data[i].doctor);
					}
					console.log(dataarray);
					var dialog = new Ext.Window({
						width : 1000,
						height : 640,
						layout : 'border',
						title : '详细信息',
						modal : true,
						items : [ {
							xtype : 'panel',
							region : 'north',
							id : 'tcm.detail.grid',
							height : 30,
							items : [ {
								xtype : 'label',
								text : '   姓名:' + row.name
							} ]
						}, {
							xtype : 'grid',
							region : 'center',
							store : new Ext.data.SimpleStore({
								data : dataarray,
								fields : [ 'col1', 'col2', 'col3', 'col4', 'col5' ]
							}),
							viewConfig : {
								getRowClass : function(record, rowIndex, rowParams, store) { // 指定行的样式
									if (rowIndex > 0 && rowIndex % 5 == 4) {
										return "tcm-row-title";
									}else if(rowIndex > 0 && rowIndex % 5 == 1){
										return "tcm-row-tcm";
									}
								}
							},
							cm : new Ext.grid.ColumnModel([ {
								"header" : "月龄",
								width : 160
							}, {
								"header" : "满月",
								width : 200
							}, {
								"header" : "3月龄",
								width : 200
							}, {
								"header" : "6月龄",
								width : 200
							}, {
								"header" : "8月龄",
								width : 200
							}])
						} ]
					});
					dialog.show();
					// Ext.getCmp("tcm.detail.grid")
				});
			}
		},
		store : new Ext.data.Store({
			// autoLoad : true,
			proxy : new Ext.ux.data.DWRProxy({
				dwrFunction : TcmService.findChildren,
				listeners : {
					load : function() {
						Ext.getCmp("tcmQuery.querybtn").enable();
					},
					beforeload : function(dataProxy, params) {
						var cond = {
							district : "",
							conditions : []
						};
						var root = tcmcurrentnode;
						if(!root){
							root = Ext.getCmp("tcmQuery.query.district").getSelectionModel().getSelectedNode()
						}
						if (!root) {
							root = Ext.getCmp("tcmQuery.query.district").getRootNode().firstChild;
						}
						cond.district = root.id;
						while (cond.district.substr(cond.district.length - 2, 2) == '00') {
							cond.district = cond.district.substr(0, cond.district.length - 2);
						}
						if (!Ext.isEmpty(Ext.getCmp("tcmQuery.query.startdatefield").getValue())) {
							cond.conditions[cond.conditions.length] = {
								filterKey : "begindate",
								filterVal : Ext.getCmp("tcmQuery.query.startdatefield").getValue(),
								opt : ">=",
								type : 'date',
								notsql : true
							};
						}
						if (!Ext.isEmpty(Ext.getCmp("tcmQuery.query.enddatefield").getValue())) {
							cond.conditions[cond.conditions.length] = {
								filterKey : "enddate",
								filterVal : Ext.getCmp("tcmQuery.query.enddatefield").getValue(),
								opt : "<=",
								type : 'date',
								notsql : true
							};
						}
						if (!Ext.isEmpty(Ext.getCmp("tcmQuery.query.combovalue").getValue())) {
							cond.conditions[cond.conditions.length] = {
								filterKey : Ext.getCmp("tcmQuery.query.combokey").getValue(),
								filterVal : Ext.getCmp("tcmQuery.query.combovalue").getValue(),
								opt : tcmConfig[Ext.getCmp("tcmQuery.query.combokey").getValue()].opt,
								type : tcmConfig[Ext.getCmp("tcmQuery.query.combokey").getValue()].type
							};
						}
						var o = cond;
						if (!params.limit)
							params.limit = 20;
						params[dataProxy.loadArgsKey] = [ o, params ];
					}.createDelegate(this)
				}
			}),
			reader : new Ext.data.JsonReader({
				totalProperty : "totalSize", // 总记录数
				root : "data", // 分页对象中的数据集
				id : "id" //
			}, Ext.data.Record.create([ {
				name : 'rowid',
				mapping : 'rowid'
			}, {
				name : 'type',
				mapping : 'type'
			}, {
				name : 'id',
				mapping : 'id'
			}, {
				name : 'inputdate',
				mapping : 'inputdate'
			}, {
				name : 'fileno',
				mapping : 'fileno'
			}, {
				name : 'parent',
				mapping : 'parent'
			}, {
				name : 'name',
				mapping : 'name'
			}, {
				name : 'birthday',
				mapping : 'birthday'
			}, {
				name : 'sex',
				mapping : 'sex'
			}, {
				name : 'checkitem',
				mapping : 'checkitem'
			}, {
				name : 'tel',
				mapping : 'tel'
			}, {
				name : 'address',
				mapping : 'address'
			}, {
				name : 'tcm',
				mapping : 'tcm'
			} ]))
		}),
		cm : new Ext.grid.ColumnModel([ {
			"sortable" : true,
			"header" : "行号",
			"dataIndex" : "rowid",
			width : 40
		}, {
			"sortable" : true,
			"header" : "录入日期",
			"dataIndex" : "inputdate",
			"renderer" : Ext.util.Format.dateRenderer('Y-m-d'),
			width : 80
		}, {
			"sortable" : true,
			"header" : "档案号",
			"dataIndex" : "fileno",
			width : 140
		}, {
			"sortable" : true,
			"header" : "姓名",
			"dataIndex" : "name",
			width : 60
		}, {
			"sortable" : true,
			"header" : "家长",
			"dataIndex" : "parent",
			width : 90
		}, {
			"sortable" : true,
			"header" : "性别",
			"dataIndex" : "sex",
			width : 40
		}, {
			"sortable" : true,
			"header" : "生日",
			"dataIndex" : "birthday",
			"renderer" : Ext.util.Format.dateRenderer('Y-m-d'),
			width : 80
		}, {
			"sortable" : true,
			"header" : "项目",
			"dataIndex" : "checkitem",
			width : 50
		}, {
			"sortable" : true,
			"header" : "联系电话",
			"dataIndex" : "tel",
			width : 120
		}, {
			"header" : "中医健康指导",
			"dataIndex" : "tcm",
			width : 500,
			"renderer" : function(v) {
				var strs = v.split(",");
				var txt = [];
				for (var i = 0; i < strs.length; i++) {
					txt.push(tcmString[strs[i]]);
				}
				console.log(this);
				this.tooltip = txt.join('，');
				// return txt.join('，');
				return '<span qtip="' + txt.join('&lt;br&gt;') + '"/>' + txt.join('，') + '</span>';
				// return v
			}
		} ]),
		// viewConfig : {},
		bbar : {
			xtype : 'paging',
			pageSize : 20,
			displayInfo : true,
			displayMsg : '{0} - {1} of {2}',
			emptyMsg : "没有记录"
		},
		sm : new Ext.grid.CheckboxSelectionModel()

	} ]
});
ModuleMgr.register(Ext.tcm.tcmQuery);
