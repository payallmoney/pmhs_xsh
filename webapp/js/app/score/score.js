Ext.ns("Ext.hc");
//ScoreService.getScore
Ext.hc.printScorePanel = new Ext.Panel({
    items : [{
        layout : 'fit',
        xtype : 'grid',
        title : '考试打分系统',
        autoWidth : true,
                autoHeight : true,
        tbar : [{
            text : '打分',
            iconCls : 'c_query',
            handler : function() {
                var store = Ext.hc.printScorePanel.items.item(0).getStore();
                store.load();
                Ext.hc.printScorePanel.render();
                Ext.hc.printScorePanel.items.item(0).render();
            }.createDelegate(this)
        }, "-", {
            text : '刷新配置',
            iconCls : 'c_refresh',
            handler : function() {
                ScoreService.refresh(null);
            }
        }],
        
        store : new Ext.data.Store({
            autoLoad : true,
            proxy : new Ext.ux.data.DWRProxy({
                dwrFunction : ScoreService.getScore,
                listeners : {
                    'beforeload' : function(dataProxy, params) {

                        //var o = this.queryForm.getForm().getValues(false);
                        // console.log(o);
                        // if (!params.limit)
                        // params.limit = this.pageSize;

                        // params.limit = 10;
                        // params[dataProxy.loadArgsKey] = [{}, params];
                        // console.log(params);

                    }.createDelegate(this),
                    'load' : function ( obj, records, options ) {
                        console.log("load=======================================================")
                        console.log(obj);
                        console.log(records);
                        console.log(options);
                        console.log("load=======================================================")
                    },
                    'loadexception' : function(obj, options, response, error) {
                            if (error) {
                                msg = error.javaClassName+":"+error.message;
                                    if(error.stackTrace!=null){
                                        for(var i = 0 ; i <error.stackTrace.length ; i++)
                                            msg= msg+"\n\tat "+ error.stackTrace[i].className+error.stackTrace[i].methodName+"("+error.stackTrace[i].fileName+":"+error.stackTrace[i].lineNumber+")";
                                    }
                                console.log(msg)
                                top.Ext.Msg.alert("错误", "解析数据时发生错误:请查看浏览器log.");
                                return;
                            }
                    }
                }            }),
            reader : new Ext.data.JsonReader({
                totalProperty : "totalSize", // 总记录数
                root : "data", // 分页对象中的数据集
                id : "personid" //
            }, Ext.data.Record.create([{
                name : 'personid',
                mapping : 'personid'
            }, {
                name : 'name',
                mapping : 'scorename'
            }, {
                name : 'examgroup',
                mapping : 'examgroup'
            }, {
                name : 'allcount',
                mapping : 'allcount'
            }, {
                name : 'examdate',
                mapping : 'examdate'
            }]))
        }),
        cm : new Ext.grid.ColumnModel([{
            "header" : "用户名称",
            "sortable" : true,
            "dataIndex" : "personid"
        }, {
            "sortable" : true,
            "header" : "考试名称",
            "dataIndex" : "name"
        }, {
            "sortable" : true,
            "header" : "考试分组",
            "dataIndex" : "examgroup"
        }, {
            "sortable" : true,
            "header" : "总分",
            "dataIndex" : "allcount"
        }, {
            "sortable" : true,
            "header" : "考试时间",
            "dataIndex" : "examdate",
            "renderer" : Ext.util.Format.dateRenderer('Y-m-d')
        }]),
        //viewConfig : {},
         sm : new Ext.grid.CheckboxSelectionModel()
    }]
});
ModuleMgr.register(Ext.hc.printScorePanel);
