Ext.ns("Ext.sms");

Ext.grid.GridPanel.prototype.initComponent =
    Ext.grid.GridPanel.prototype.initComponent.createInterceptor(function() {
        if ( this.store && this.bbar && this.bbar.xtype == 'paging'
            && ! (this.bbar instanceof Ext.PagingToolbar) && ! this.bbar.store
        ) {
            if ( this.store.xtype && ! (this.store instanceof Ext.data.Store) ) {
                this.store = Ext.ComponentMgr.create(this.store);
            } 
            this.bbar.store = this.store;
            if ( this.bbar.xtype && ! (this.bbar instanceof Ext.PagingToolbar) ) {
                this.bbar = Ext.ComponentMgr.create(this.bbar);
            } 
        } 
    });
// Ext.QuickTips.init();

Ext.sms.MsgSender = new Ext.Panel({
    layout : 'fit',
    items : [{
        xtype : 'grid',
        layout : 'fit',
        id : 'sms.msgsender.grid',
        title : '短信系统',
        autoWidth : true,
        //autoHeight:true,
        //height : 500,
        tbar : [ {
            text : '启动短信发送任务',
            iconCls : 'c_query',
            id : 'sms.button.start',
            handler : function(obj) {
                obj.disable();
                Ext.getCmp("sms.button.stop").disable();
                SmsService.smsStartSend(function(data) {
                    Ext.getCmp("sms.button.stop").enable();
                });
            }
        }, "-", {
            text : '停止短信发送任务',
            iconCls : 'c_del',
            id : 'sms.button.stop',
            handler : function(obj) {
                obj.disable();
                Ext.getCmp("sms.button.start").disable();
                SmsService.smsStopSend(function(data) {
                    Ext.getCmp("sms.button.start").enable();
                });
            }
        }, "-", {
            xtype : 'datefield',
            id : 'sms.msgsender.query.startdatefield'
        },"至", {
            xtype : 'datefield',
            id : 'sms.msgsender.query.enddatefield'
        }, "-", {
            text : '查询发送日志',
            iconCls : 'c_refresh',
            handler : function(obj) {
                //obj.disable();
                Ext.getCmp("sms.msgsender.grid").getStore().reload();
            }
        }],
        store : new Ext.data.Store({
            // autoLoad : true,
            proxy : new Ext.ux.data.DWRProxy({
                dwrFunction : SmsService.queryLogs,
                listeners : {
                    'beforeload' : function(dataProxy, params) {
                        var cond = {
                            district : "",
                            conditions : []
                        };
                        if(!Ext.isEmpty(Ext.getCmp("sms.msgsender.query.startdatefield").getValue())){
                            //cond.conditions["type"] = this.combo1.getValue();
                            cond.conditions[cond.conditions.length] = {filterKey:"vo.smsdate",filterVal:this.combo1.getValue(),opt:">="};
                        }
                        if(!Ext.isEmpty(Ext.getCmp("sms.msgsender.query.enddatefield").getValue())){
                            //cond.conditions["type"] = this.combo1.getValue();
                            cond.conditions[cond.conditions.length] = {filterKey:"vo.smsdate",filterVal:this.combo1.getValue(),opt:"<="};
                        }
                        console.log(cond);
                        
                        var o = cond;
                        console.log("getParams: ")
                        console.log(o);
                        if (!params.limit)
                            params.limit = 20;
                        params[dataProxy.loadArgsKey] = [ o, params ];
                        console.log(params[dataProxy.loadArgsKey])                    }.createDelegate(this)
                }

            }),
            reader : new Ext.data.JsonReader({
                totalProperty : "totalSize", // 总记录数
                root : "data", // 分页对象中的数据集
                id : "smsdate" //
            }, Ext.data.Record.create([{
                name : 'smsdate',
                mapping : 'id.smsdate'
            }, {
                name : 'examname',
                mapping : 'id.examname'
            }, {
                name : 'fileno',
                mapping : 'id.fileno'
            }, {
                name : 'examgroup',
                mapping : 'examgroup'
            }, {
                name : 'tel',
                mapping : 'tel'
            }, {
                name : 'msg',
                mapping : 'msg'
            }, {
                name : 'status',
                mapping : 'status'
            }, {
                name : 'sendtime',
                mapping : 'sendtime'
            }, {
                name : 'error',
                mapping : 'error'
            }]))
        }),
        cm : new Ext.grid.ColumnModel([{
            "sortable" : true,
            "header" : "发送日期",
            "sortable" : true,
            "dataIndex" : "smsdate",
            "renderer" : Ext.util.Format.dateRenderer('Y-m-d'),
            width : 80
        }, {
            "sortable" : true,
            "header" : "短信名目",
            "sortable" : true,
            "dataIndex" : "examname",
            width : 180
        }, {
            "sortable" : true,
            "header" : "档案号",
            "dataIndex" : "fileno",
            width : 140
        }, {
            "sortable" : true,
            "header" : "电话",
            "dataIndex" : "tel",
            width : 90
        }, {
            "sortable" : true,
            "header" : "短信内容",
            "dataIndex" : "msg",
            width : 300,
            renderer : function (data, metadata, record, rowIndex, columnIndex, store) {  
                var qtip = '';  
                console.log(data)
                if(data >= 0){  
                    qtip = data;  
                    return '<span qtip="' + qtip+'"/>' + data + '</span>';  
                }  
                return data;  
            } 
        }, {
            "header" : "状态",
            "dataIndex" : "status",
            width : 50,
            "renderer": function(v){
                if(v === 1){
                    return '已发送';
                }else {
                    return '未发送';
                }
            }
        }, {
            "header" : "发送时间",
            "dataIndex" : "sendtime",
            "renderer" : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
            width : 135
        }, {
            "header" : "发送情况",
            "dataIndex" : "error",
            width : 80
        }]),
        // viewConfig : {},
        bbar : {
            xtype : 'paging',
            pageSize : 20,
            displayInfo : true,
            displayMsg : '{0} - {1} of {2}',
            emptyMsg : "没有记录"
        }, 
        sm : new Ext.grid.CheckboxSelectionModel()

    }]
});
ModuleMgr.register(Ext.sms.MsgSender);
