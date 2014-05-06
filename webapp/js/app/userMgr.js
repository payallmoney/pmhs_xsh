//{
var itemRec = new Ext.data.Record.create([ {
	name : 'code',
	mapping : 'id'
}, {
	name : 'desc',
	mapping : 'name'
} ]);

/**
 * 取得所有角色
 */
var fromStoreAll = new Ext.data.Store({
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : UserService.findAllRoles
	}),
	reader : new Ext.data.ArrayReader({}, itemRec)
});

var formSubmiter = function() {
	userGrid.selModel.clearSelections();
	store.load();
}

var addUser = function() {

	var saveNewUser = function() {
		var formBean = editForm.getForm().getValues(false);
		if (formBean.password != formBean.dupPassword) {
			Ext.Msg.alert('校验错误', '两次密码不一致');
			editForm.getComponent('password').markInvalid();
			editForm.getComponent('DupPassword').markInvalid();
			return false;
		}

		if (formBean.validFlag)
			formBean.validFlag = 1;
		else
			formBean.validFlag = 0;
		
		if (formBean.isLookAuthority)
			formBean.isLookAuthority = 1;
		else
			formBean.isLookAuthority = 0;
		
		if (formBean.isChangeCertifiAuthority)
			formBean.isChangeCertifiAuthority = 1;
		else
			formBean.isChangeCertifiAuthority = 0;
		
		if (formBean.isSupplyCertifiAuthority)
			formBean.isSupplyCertifiAuthority = 1;
		else
			formBean.isSupplyCertifiAuthority = 0;
		
		if (formBean.isAdvancedCertifiAuthority)
			formBean.isAdvancedCertifiAuthority = 1;
		else
			formBean.isAdvancedCertifiAuthority = 0;
		
		if (formBean.isOver2MonthCertifiAuthority)
			formBean.isOver2MonthCertifiAuthority = 1;
		else
			formBean.isOver2MonthCertifiAuthority = 0;
		
		formBean.user = {};
		formBean.user = formBean;
		if (!Ext.isEmpty(formBean.typeList) && !Ext.isArray(formBean.typeList)) {
			formBean.user.typeList = [ formBean.typeList ];
		}
		var rolesArray = formBean.roles.split(",");
		formBean.roles = rolesArray;

		if (editForm.getForm().isValid()) {
			UserService.saveNewUser(formBean, function(res) {
				Ext.Msg.alert('', '保存完成');
			});

		}
	}

	var editForm = new Ext.FormPanel({
		// width:auto,
		title : '',
		defaultType : 'textfield',
//		autoHeight : true,
		height : 500,
		width : 740,
		border : false,
		hidden : false,
		frame : true,
		monitorValid : true,
		layout : 'absolute',
		items : [ {
			xtype : 'label',
			text : '用户ID',
			x : 0,
			y : 3
		},{
//			fieldLabel : '用户ID',
			name : 'loginname',
			allowBlank : false,
			x : 80,
			y : 0
		},{
			xtype : 'label',
			text : '用户名称',
			x : 0,
			y : 33
		}, {
//			fieldLabel : '用户名称',
			name : 'username',
			allowBlank : false,
			x : 80,
			y : 30
		},{
			xtype : 'label',
			text : '密码',
			x : 0,
			y : 63
		}, {
//			fieldLabel : '密码',
			name : 'password',
			id : 'password',
			inputType : 'password',
			allowBlank : false,
			x : 80,
			y : 60
		},{
			xtype : 'label',
			text : '重复密码',
			x : 0,
			y : 93
		}, {
//			fieldLabel : '重复密码',
			name : 'dupPassword',
			id : 'DupPassword',
			inputType : 'password',
			allowBlank : false,
			x : 80,
			y : 90
		},{
			xtype : 'label',
			text : '是否有效',
			x : 0,
			y : 123
		}, {
//			fieldLabel : '是否有效',
			name : 'validFlag',
			xtype : 'checkbox',
			x : 80,
			y : 120
		},{
			xtype : 'label',
			text : '人员属性',
			x : 0,
			y : 153
		}, {
//			fieldLabel : '人员属性',
			xtype : 'checkboxgroup',
			columns : [ 200, 150, 150, 200 ],
			x : 80,
			y : 150,
			items : [ {
				boxLabel : '一般操作人员',
				name : 'typeList',
				inputValue : 1
			}, {
				boxLabel : '医生',
				name : 'typeList',
				inputValue : 2
			}, {
				boxLabel : '护士',
				name : 'typeList',
				inputValue : 4
			}, {
				boxLabel : '公卫医师',
				name : 'typeList',
				inputValue : 8
			} ]
		},{
			xtype : 'label',
			text : '所属组织机构',
			x : 0,
			y : 183
		}, {
			fieldLabel : '所属组织机构',
			xtype : 'popselect',
			allowBlank : false,
			refName : 'orgId',
			x : 80,
			y : 180,
			queryUrl : UserMenuTreeService.findOrgs.createDelegate(this),
			queryConfig : [ {
				fieldLabel : '组织机构名称',
				name : 'name'
			} ],
			readerConfig : [ {
				name : 'id',
				mapping : 'id'
			}, {
				name : 'name',
				mapping : 'name'
			} , {
                name : 'district.name',
                mapping : 'district.name'
            }, {
                name : 'district.parentName',
                mapping : 'district.parentName'
            } ],
			gridCm : [ {
				"header" : "ID",
				"dataIndex" : "id",
                "sortable" : "true"
			}, {
				"header" : "名称",
				"dataIndex" : "name",
                "sortable" : "true"
			} , {
                "header" : "上级机构",
                "dataIndex" : "district.name",
                "sortable" : "true"
            } , {
                "header" : "上上级机构",
                "dataIndex" : "district.parentName",
                "sortable" : "true"
            }  ],
			name : 'org.name'
		}, {
			name : 'orgId',
			xtype : 'hidden'
		},{
			xtype : 'label',
			text : '行政区域',
			x : 0,
			y : 213
		}, {
			fieldLabel : '行政区域',
			name : 'district.name',
			refName : 'districtId',
			xtype : 'popselect',
			x : 80,
			y : 210,
			queryUrl : UserMenuTreeService.findDistricts.createDelegate(this),
			queryConfig : [ {
				fieldLabel : '行政区域名称',
				name : 'name'
			} ],
			readerConfig : [ {
				name : 'id',
				mapping : 'id'
			}, {
				name : 'name',
				mapping : 'name'
			} ],
			gridCm : [ {
				"header" : "ID",
				"dataIndex" : "id"
			}, {
				"header" : "名称",
				"dataIndex" : "name"
			} ]
		}, {
			fieldLabel : '行政区域ID',
			name : 'districtId',
			xtype : 'hidden'
		}, {
			x : 0,
			y : 240,
			xtype : "itemselector",
			name : "roles",
			fieldLabel : "用户角色",
			dataFields : [ "code", "desc" ],
			toData : [],
			msWidth : 350,
			msHeight : 200,
			valueField : "code",
			displayField : "desc",
			imagePath : "/resources/multiselect/",
			toLegend : "当前角色",
			fromLegend : "可用角色",
			fromStore : fromStoreAll,
			drawUpIcon : false,
			drawDownIcon : false,
			drawTopIcon : false,
			drawBotIcon : false,
			toTBar : [ {
				text : "清除",
				handler : function() {
					var i = editForm.getForm().findField("roles");
					i.reset.call(i);
				}
			} ]
		},{
			xtype : 'fieldset',
			x : 220,
			y : 0,
			title : '特殊权限',
			width : 500,
			height : 130,
			layout : 'absolute',
			items : [{
				x : 0,
				y : 0,
				name : 'isLookAuthority',
				xtype : 'checkbox',
				boxLabel : '查看下级机构统计数据'
			},{
				x : 0,
				y : 30,
				name : 'isChangeCertifiAuthority',
				xtype : 'checkbox',
				boxLabel : '出生医学证明换发权限'
			},{
				x : 0,
				y : 60,
				name : 'isSupplyCertifiAuthority',
				xtype : 'checkbox',
				boxLabel : '出生医学证明补发权限'
			},{
				x : 150,
				y : 0,
				name : 'isAdvancedCertifiAuthority',
				xtype : 'checkbox',
				boxLabel : '出生医学证明高级功能权限'
			},{
				x : 150,
				y : 30,
				name : 'isOver2MonthCertifiAuthority',
				xtype : 'checkbox',
				boxLabel : '超过2个月的出生医学证明签发'
			}]
		}],
		buttons : [ {
			text : '保存',
			formBind : true,
			scope : this,
			keys : [ {
				key : [ 10, 13 ],
				fn : saveNewUser
			} ],
			handler : saveNewUser
		}, {
			text : '取消',
			scope : this,
			handler : function() {
				// editWin.close();
				editWin.hide();
			}
		} ]
	});

	var editWin = new Ext.Window({
		title : '新增用户',
		closeAction : 'close',
		modal : true,
		autoHeight : true,
		width : 750,
		items : [ editForm ]
	})

	editWin.show();
	fromStoreAll.load();

}

var queryForm = new Ext.FormPanel({
	region : 'north',
	// autoHeight : true,
	labelWidth : 75,
	height : 100,
	frame : true,
	title : '查询条件',
	bodyStyle : 'padding:5px 5px 0',
	// width : 200,
	defaults : {
		width : 230
	},
	defaultType : 'textfield',
	items : [ {
		columnWidth : 1,
		fieldLabel : '用户ID',
		name : 'loginname',
		allowBlank : true
	} ],

	buttons : [ {
		text : '查询',
		formBind : true,
		scope : this,
		handler : formSubmiter
	}, {
		text : '新增用户',
		handler : addUser
	} ],
	keys : [ {
		key : [ 10, 13 ],
		fn : formSubmiter
	} ]

});

var reader = new Ext.data.JsonReader({
	totalProperty : "totalSize",
	root : "data",
	id : "user.loginname"
}, Ext.data.Record.create([ {
	name : 'validFlag',
	mapping : 'user.validFlag',
	type : 'string'
}, {
	name : 'loginname',
	mapping : 'user.loginname',
	type : 'string'
}, {
	name : 'username',
	mapping : 'user.username',
	type : 'string'
}, {
	name : 'districtId',
	mapping : 'user.districtId',
	type : 'string'
}, {
	name : 'orgId',
	mapping : 'user.orgId',
	type : 'string'
}, {
	name : 'typeList',
	mapping : 'user.typeList'
}, {
	name : 'district',
	mapping : 'user.district'
}, {
	name : 'org',
	mapping : 'user.org'
},{
	name : 'isLookAuthority',
	mapping : 'user.isLookAuthority',
	type : 'int'
},{
	name : 'isChangeCertifiAuthority',
	mapping : 'user.isChangeCertifiAuthority',
	type : 'int'
},{
	name : 'isSupplyCertifiAuthority',
	mapping : 'user.isSupplyCertifiAuthority',
	type : 'int'
},{
	name : 'isAdvancedCertifiAuthority',
	mapping : 'user.isAdvancedCertifiAuthority',
	type : 'int'
},{
	name : 'isOver2MonthCertifiAuthority',
	mapping : 'user.isOver2MonthCertifiAuthority',
	type : 'int'
}  ]));

var store = new Ext.data.Store({
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : UserService.findUsers,
		listeners : {
			'beforeload' : function(dataProxy, params) {
				// alert(dwr.util.toDescriptiveString(params, 2));
				var o = queryForm.getForm().getValues(false);
				if (!params.limit)
					params.limit = pagingBar.pageSize;
				params[dataProxy.loadArgsKey] = [ o, params ];
			}
		}
	}),
	reader : reader
});

var pagingBar = new App.PagingToolbar({
	pageSize : 10,
	store : store,
	displayInfo : true,
	displayMsg : '{0} - {1} of {2}',
	emptyMsg : "没有记录"

});

function edit(rec) {
	console.log("rowselect..");
	var fromStore = new Ext.data.Store({
		proxy : new Ext.ux.data.DWRProxy({
			dwrFunction : UserService.findOtherRoles,
			listeners : {
				'beforeload' : function(dataProxy, params) {
					var loginname = editForm.getForm().findField('loginname')
							.getValue();
					params[dataProxy.loadArgsKey] = [ loginname ];
				}
			}
		}),
		reader : new Ext.data.ArrayReader({}, itemRec)
	});
	var toStore = new Ext.data.Store({
		proxy : new Ext.ux.data.DWRProxy({
			dwrFunction : UserService.findRoles,
			listeners : {
				'beforeload' : function(dataProxy, params) {
					var loginname = editForm.getForm().findField('loginname')
							.getValue();
					params[dataProxy.loadArgsKey] = [ loginname ];
				}
			}
		}),
		reader : new Ext.data.ArrayReader({}, itemRec)
	});

	var editForm = new Ext.FormPanel({
		id : '_editForm',
		// width:auto,
		// labelWidth: 90,
		title : '',
		// defaults: {width: 140}, // Default config options for child items
		defaultType : 'textfield',
//		autoHeight : true,
		height : 500,
		width : 740,
		// bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px
		// 15px;',
		border : false,
		hidden : false,
		frame : true,

		// style: {
		// "margin-left": "10px", // when you add custom margin in IE 6...
		// "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") :
		// "0" // you have to adjust for it somewhere else
		// },
		layout : 'absolute',
		items : [ {
			xtype : 'label',
			text : '用户ID',
			x : 0,
			y : 3
		}, {
//			fieldLabel : '用户ID',
			name : 'loginname',
			style : 'border:0; background: #DFE8F6 none repeat scroll 0 0;',
			readOnly : true,
			x : 80,
			y : 0
		},{
			xtype : 'label',
			text : '用户名称',
			x : 0,
			y : 43
		}, {
//			fieldLabel : '用户名称',
			name : 'username',
			allowBlank : false,
			x : 80,
			y : 40
		},{
			xtype : 'label',
			text : '密码',
			x : 0,
			y : 83
		}, {
//			fieldLabel : '密码',
			name : 'password',
			value : '',
			inputType : 'password',
			x : 80,
			y : 80
		},{
			xtype : 'label',
			text : '是否有效',
			x : 0,
			y : 123
		}, {
//			fieldLabel : '是否有效',
			name : 'validFlag',
			xtype : 'checkbox',
			x : 80,
			y : 120
		},{
			xtype : 'label',
			text : '人员属性',
			x : 0,
			y : 153
		}, {
			x : 80,
			y : 150,
//			fieldLabel : '人员属性',
			xtype : 'checkboxgroup',
			id : 'typeList',
			columns : [ 200, 150, 150, 200 ],
			items : [ {
				boxLabel : '一般操作人员',
				name : 'typeList',
				inputValue : 1
			}, {
				boxLabel : '医生',
				name : 'typeList',
				inputValue : 2
			}, {
				boxLabel : '护士',
				name : 'typeList',
				inputValue : 4
			}, {
				boxLabel : '公卫医师',
				name : 'typeList',
				inputValue : 8
			} ]
		},{
			xtype : 'label',
			text : '所属组织机构',
			x : 0,
			y : 183
		}, {
			x : 80,
			y : 180,
//			fieldLabel : '所属组织机构',
			xtype : 'popselect',
			allowBlank : false,
			refName : 'orgId',
			queryUrl : UserMenuTreeService.findOrgs.createDelegate(this),
			queryConfig : [ {
				fieldLabel : '组织机构名称',
				name : 'name'
			} ],
			readerConfig : [ {
				name : 'id',
				mapping : 'id'
			}, {
				name : 'name',
				mapping : 'name'
			} , {
                name : 'district.name',
                mapping : 'district.name'
            } , {
                name : 'district.parentName',
                mapping : 'district.parentName'
            }],
			gridCm : [ {
				"header" : "ID",
				"dataIndex" : "id",
				"sortable" : "true"
			}, {
				"header" : "名称",
				"dataIndex" : "name",
				"sortable" : "true"
			} , {
                "header" : "上级机构",
                "dataIndex" : "district.name",
                "sortable" : "true"
            } , {
                "header" : "上上级机构",
                "dataIndex" : "district.parentName",
                "sortable" : "true"
            }  ],
			name : 'org.name'
		}, {
			name : 'orgId',
			xtype : 'hidden'
		},{
			xtype : 'label',
			text : '行政区域',
			x : 0,
			y : 213
		}, {
			x : 80,
			y : 210,
//			fieldLabel : '行政区域',
			name : 'district.name',
			refName : 'districtId',
			xtype : 'popselect',
			queryUrl : UserMenuTreeService.findDistricts.createDelegate(this),
			queryConfig : [ {
				fieldLabel : '行政区域名称',
				name : 'name'
			} ],
			readerConfig : [ {
				name : 'id',
				mapping : 'id'
			}, {
				name : 'name',
				mapping : 'name'
			} ],
			gridCm : [ {
				"header" : "ID",
				"dataIndex" : "id"
			}, {
				"header" : "名称",
				"dataIndex" : "name"
			} ]
		}, {
			fieldLabel : '行政区域ID',
			name : 'districtId',
			xtype : 'hidden'
		}, {
			x : 0,
			y : 240,
			xtype : "itemselector",
			name : "roles",
			fieldLabel : "用户角色",
			dataFields : [ "code", "desc" ],
			toData : [],
			toStore : toStore,
			msWidth : 350,
			msHeight : 200,
			valueField : "code",
			displayField : "desc",
			imagePath : "/resources/multiselect/",
			toLegend : "当前角色",
			fromLegend : "可用角色",
			fromStore : fromStore,
			fromData : [],
			drawUpIcon : false,
			drawDownIcon : false,
			drawTopIcon : false,
			drawBotIcon : false,
			toTBar : [ {
				text : "清除",
				handler : function() {
					var i = editForm.getForm().findField("roles");
					i.reset.call(i);
				}
			} ]
		},{
			xtype : 'fieldset',
			x : 220,
			y : 0,
			title : '特殊权限',
			width : 500,
			height : 130,
			layout : 'absolute',
			items : [{
				x : 0,
				y : 0,
				name : 'isLookAuthority',
				xtype : 'checkbox',
				boxLabel : '查看下级机构统计数据'
			},{
				x : 0,
				y : 30,
				name : 'isChangeCertifiAuthority',
				xtype : 'checkbox',
				boxLabel : '出生医学证明换发权限'
			},{
				x : 0,
				y : 60,
				name : 'isSupplyCertifiAuthority',
				xtype : 'checkbox',
				boxLabel : '出生医学证明补发权限'
			},{
				x : 150,
				y : 0,
				name : 'isAdvancedCertifiAuthority',
				xtype : 'checkbox',
				boxLabel : '出生医学证明高级功能权限'
			},{
				x : 150,
				y : 30,
				name : 'isOver2MonthCertifiAuthority',
				xtype : 'checkbox',
				boxLabel : '超过2个月的出生医学证明签发'
			}]
		} ],
		buttons : [
				{
					text : '保存',
					formBind : true,
					scope : this,
					handler : function() {
						var formBean = editForm.getForm().getValues(false);
						console.log(formBean);
						formBean.user = {};
						if (formBean.validFlag)
							formBean.validFlag = 1;
						else
							formBean.validFlag = 0;
						
						if (formBean.isLookAuthority)
							formBean.isLookAuthority = 1;
						else
							formBean.isLookAuthority = 0;
						
						if (formBean.isChangeCertifiAuthority)
							formBean.isChangeCertifiAuthority = 1;
						else
							formBean.isChangeCertifiAuthority = 0;
						
						if (formBean.isSupplyCertifiAuthority)
							formBean.isSupplyCertifiAuthority = 1;
						else
							formBean.isSupplyCertifiAuthority = 0;
						
						if (formBean.isAdvancedCertifiAuthority)
							formBean.isAdvancedCertifiAuthority = 1;
						else
							formBean.isAdvancedCertifiAuthority = 0;
						
						if (formBean.isOver2MonthCertifiAuthority)
							formBean.isOver2MonthCertifiAuthority = 1;
						else
							formBean.isOver2MonthCertifiAuthority = 0;
						formBean.user = formBean;
						if (!Ext.isEmpty(formBean.typeList)
								&& !Ext.isArray(formBean.typeList)) {
							formBean.user.typeList = [ formBean.typeList ];
						}
						var rolesArray = formBean.roles.split(",");
						formBean.roles = rolesArray;

						if (editForm.getForm().isValid()) {
							var mask = new Ext.LoadMask(Ext.getBody(), {
								msg : '正在更新数据..'
							});
							mask.show();
							UserService.saveUser(formBean, function(res) {
								mask.hide();
								Ext.Msg.alert('', '保存完成', function() {
									// refresh query
									formSubmiter();
									mask.hide();
								});
							});
						}
						;
					}
				}, {
					text : '取消',
					scope : this,
					handler : function() {
						// editWin.close();
						editWin.hide();
					}
				} ]
	})

	var editWin = new Ext.Window({
		title : '用户信息（密码域为空表示不修改密码）',
		closeAction : 'hide',
		modal : true,
		autoHeight : true,
		width : 750,
		items : [ editForm ]
	})

	editWin.show();
	var mask = new Ext.LoadMask(Ext.getBody(), {
		msg : '正在加载数据..'
	});
	mask.show();

	editForm.getForm().loadRecord(rec);

	var typeListValue = rec.get("typeList");
	var typeList = editForm.getForm().findField("typeList");
	var items = typeList.items;
	items.each(function(v) {
		if (typeListValue.indexOf(v.inputValue) != -1)
			v.setValue(true);
	});
	fromStore.load();
	toStore.load();

	mask.hide();
};

var userGrid = new Ext.grid.GridPanel({
	region : 'center',
	id : 'userGrid',
	store : store,
	loadMask : {
		msg : '正在加载数据...'
	},
	cm : new Ext.grid.ColumnModel([ {
		"header" : "用户ID",
		"dataIndex" : "loginname",
		'id' : 'user.loginname',
		"sortable" : "true"
	}, {
		"header" : "用户名称",
		"dataIndex" : "username",
		"sortable" : "true",
		"id" : "username"
	} ]),
	viewConfig : {
		forceFit : true
	},

	width : 500,
	height : 200,
	// autoHeight:true,
	frame : true,
	title : '',
	iconCls : 'icon-grid',
	style : 'position:absolute;top:92px;',
	bbar : pagingBar,
	listeners : {
		'rowdblclick' : function(g, rowIndex, e) {
			var rec = g.getStore().getAt(rowIndex);
			//console.log(rec);
			edit(rec);
		}
	},
	sm : new Ext.grid.RowSelectionModel({
		singleSelect : true
	})
});

var userMgrPanel = {
	title : '用户和角色维护',
	closable : true,
	width : 800,
	height : 600,
	layout : 'border',
	items : [ queryForm, userGrid ]
}

// _tab = ModuleMgr.register(panel);
ModuleMgr.register(userMgrPanel);

// store.load();

// }
