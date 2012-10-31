function printdata(data){
	var msg = "";
	for(var item in data){
		//if(item.toLowerCase().indexOf("n")>=0)
			//alert(msg +item+"===="+data[item]+"\n");
			msg = msg +item+"="+data[item]+"\n";
	}
	return msg;
}


function getContext(obj){
	var html = "";
	if(typeof (obj) != "function"){
    	if( typeof (obj)=="object"){
    		for(var item in obj){
    			if(obj.hasOwnProperty(item)){
    				html = html+getContext(obj[item]);
    			}
    		}
    	}else{
    		html = obj;
    	}
	}
	return html;
}
var personal_colsMaps = {};

function changedata(index){
	//alert(index+"==="+personal_colsMaps.datas[""+index]+"==="+personal_colsMaps.item.url)
	if(personal_colsMaps.datas[""+index]){
		setLabelVal(personal_colsMaps.datas[""+index],personal_colsMaps.pre);
	}else{
		var func = eval(personal_colsMaps.item.url);
		//alert(personal_colsMaps.item.idlist[index].trim())
		func({id:personal_colsMaps.item.idlist[index].trim()},{callback : function (data){
			personal_colsMaps.datas[""+index] = data;
			//alert(printdata(data));
			setLabelVal(data,personal_colsMaps.pre);
		}});
	}
	return true;
}
function setLabelVal(data,pre) {
	var listcols = personal_colsMaps.listcols;
	var datecols = personal_colsMaps.datecols;
	//设置值
    for(var prop in data) {
    	var c = $("#"+pre+prop);
    	if(c && c.html){
	        if(listcols[prop] && data[prop] !=null && data[prop] !=undefined) { 
	        	//处理列表list类型
	            var index = listcols[prop].ds;
	            var map = getData(index);
	            var valuecol;
	            valuecol = "number";
	            if(data[prop][0] && typeof(data[prop][0]) == "object"){
	            	if(listcols[prop].id){
	            		valuecol = listcols[prop].id;
	            	}else{
	            		valuecol = 0;
	            	}
	            }
	            if(map){
	            	var _len = map.length - 1;
	                var spans = $.map(map, function(v, i){
	                    var number, label;
	                    if ($.isArray(v)){
	                    	number = v[0];
	                        label = v[1];
	                    } else {
	                        number = v["number"];
	                        label = v["name"];
	                    }
	                    if(label == undefined || label == "undefined"){
	                    	label ="";
	                    }
	                    var flag = false;
	                    if(typeof(data[prop])=="string"){
	                    	flag = (data[prop].indexOf(label)>=0);
	                    }else if(typeof(data[prop])=="boolean"){
	                    	//alert(printdata(v)+"==="+data[prop]+"\n====="+typeof(v["number"]));
	                    	if(v["number"] == 1 && !data[prop]){
	                    		flag = true;
	                    	}if(v["number"] == 2 && data[prop]){
	                    		flag = true;
	                    	}
	                    }else{
		                    for(var i = 0 ; i <data[prop].length ;i ++){
		                    	if(v["id"] == data[prop][i][valuecol]){
		                    		flag = true;
		                    		break;
		                    	}
		                    }
	                    }
	                    if(flag){
	                    	return "<span><em class='list-selected'>" + number + ". </em><em class='list-label'>" + label + "</em></span>"  ;
	                    }else{
	                    	return "<span><em>" + number + ". </em><em class='list-label'>" + label + "</em></span>" ;
	                    }
	                }).join("");
	                c.html(spans+"&nbsp;");
	                $("span ->em").addClass("spanAlign");
	            }
	        }else if (datecols[prop] && data[prop]){
	        	//处理时间类型
	        	 c.html(Ext.util.Format.date(data[prop],"Y-m-d"));
	        	 c.addClass("readcontext");
	        }else if(typeof(data[prop]) == "object"){
	        }else if(typeof(data[prop]) != "function"){
	        	//处理简单的字符串或数字类型
	        	 c.html(data[prop]);
	        	 c.addClass("readcontext");
	        }
    	}
    } //for
}
	var loadTriggerParameters = ["fileNo"];
    var services = {
        get : PersonalInfoService.get,
        save : PersonalInfoService.save
    };
    //增加标签页
    var queryjson = parseParams(window.location.search);
    
    PersonalInfoService.getExamInfo(queryjson,function(data){
    	if(data.length > 0){
    		for(var i=0 ;i <data.length;i++){
	    		var item = data[i]
	    		$.ajax({
	    			url: data[i].htmlurl,
	    			error : function(req,stat,error) {
					    alert(error);
					},
					scope:item,
					success: function(htmldata,stat) {
						var regexp = /[i|I][d|D]\s*=\s*['|"](\w+)['|"]/g;
						htmldata = htmldata.replace(regexp,"id='"+this.scope.tablename+"_$1'")
						htmldata = htmldata.substr(htmldata.indexOf("\n"));
						$(".panes").append(htmldata);
						$(".tabs").append("<li><a href=\"#\">"+this.scope.name+"("+this.scope.idlist.length+"次)"+"</a></li>");
						$("ul.tabs").tabs("div.panes > div", {api:true});
						for(var i = 0 ; i <this.scope.idlist.length ;i ++){
							if(i==0){
								$("#"+this.scope.tablename+"_id_tabcount").append("<input name='"+this.scope.tablename+"_radio' type='radio' onclick='return changedata("+i+");' checked>第"+(i+1)+"次检查记录");
							}else{
								$("#"+this.scope.tablename+"_id_tabcount").append("<input name='"+this.scope.tablename+"_radio'  type='radio' onclick='return changedata("+i+");'>第"+(i+1)+"次检查记录");
							}
						}
						var listcols = this.scope.listcol.split(";");
						var listMap = new Array();
						for(var i = 0; i <listcols.length ;i++){
							var colparam = listcols[i].split(",");
							listMap[colparam[0]]={ds:colparam[1],id:colparam[2]};
						}
						var datecols = this.scope.datetypecol.split(";");
						var datemap = new Array();
						for(var i = 0; i <datecols.length ;i++){
							datemap[datecols[i]]=true;
						}
						personal_colsMaps = {"datecols":datemap,"listcols":listMap,
								"pre":this.scope.tablename+"_",
								"datas":{},
								"item":this.scope}
						var func = eval(this.scope.url);
						func({id:this.scope.idlist[0].trim()},{callback : function (data){
							setLabelVal(data,personal_colsMaps.pre);
							personal_colsMaps.datas["0"] = data;
						}});
                    }, 
                    errorHandler : function(errStr, e){
						hideDialog();
						showDialog('系统发生异常(加载应用数据过程中)<br/>' + errStr, true);
					}
                });
			}
    	}
    });
    
    var cfg = [
        {
            id : "fileNo",
            xtype : "input",
            setting : {
                maxlen : 18,
                size : 18,
                asLabel : true
            }
        },/*
        {
            id : "homeId",
            xtype : "combo",
            setting : {
                ds :  {search:HomeNumSearch.listCodePage, get:HomeNumSearch.getItem},
                local : false,
                width : 140,
                model : {
                    id : 0,
                    code : 0,
                    display : 1
                },
                showDisplay: false,
                roWhenSet : true,
                writeback : [{id:"household", col: 1}],
                mCodePrefixCtrlId : 'districtNumber',
                displayCols : [0,1,2],
                displayColNames : ["家庭编号","","",""]
            }
        },
        {
          id : "household",
          xtype : "input",
          setting : {
        	  readonly : true,
        	  size : 18
          }
        },*/
        {
            id : "paperFileNo",
            xtype : "input",
            setting : {
              maxlen : 18,
              size : 18
            }
          },
        {
          id : "name",
          xtype : "input",
          setting : {
            maxlen : 18,
            size : 18
          },
          required : [true, "姓名"] 
        },
        {
            id : "barCode",
            xtype : "input",
            setting : {
              maxlen : 18,
              size : 18
            }
          },
        {
            id : "sex",
            xtype : "list",
            setting : {
                ds : "111",
                controlShow : 3,
                controlShowVal : 'bornStatus'
            },
            required : [true, "性别"] 
        },
        {
            id : "folk",
            xtype : "list",
            setting : {
                ds : "57",
                isDefaultVal : true,
                defaultVal : 0
            },
            requires : { valEq : "2" , fields : ["folkOther"] }  // 
        },
        {
            id : "folkOther",
            xtype : "input",
            setting : {
               disabled : true,
               maxlen: 10,
               size : 10,
               caption : "其他民族"
            }
        },
        {
            id : "idnumber",
            xtype : "input",
            setting : {
                defaultVal: "533022",
                maxlen : 18,
                size : 18,
                calculateBirthday : true,
                calculateBirthdayByIDNumber : ['birthday']
            },
            required : [true, "身份证号"]
        },
        {
            id : "birthday",
            xtype : "input",
            setting : {
                format: 'date',
                maxlen : 8,
                size : 10
            },
            required : [true, "出生日期"] 
        },
        {
            id : "workUnit",
            xtype : "input",
            setting : {
                maxlen : 19,
                size : 19,
                defaultVal : '无'
            }
        },
        {
            id : "tel",
            xtype : "input",
            setting : {
                maxlen : 30,
                size : 15 
        }},
        {
            id : "linkman",
            xtype : "input",
            setting : {
                maxlen : 10,
                size : 10 
        }},
        {
            id : "linkmanTel",
            xtype : "input",
            setting : {
                maxlen : 11,
                size : 11 
            }
        },
        {
            id : "bloodTypeAbo",
            xtype : "list",
            setting : {
                ds : "115"
            }
        },
        {
            id : "maritalStatus",
            xtype : "list",
            setting : {
                ds : "37"
            }
        },
        {
          id : "farmStatus",
          xtype : "list",
          setting : {
              ds : "171",
              isDefaultVal : true,
              defaultVal : 0
          },
          required : [true, "是否农业人口"] 
        },
        {//城镇居民
            id : "townStatus",
            xtype : "list",
            setting : {
                ds : "171",
                isDefaultVal : true,
                defaultVal : 1
            },
            required : [true, "是否城镇居民"] 
          },
        {
            id : "allergiesHistory",
            xtype : "list",
            setting : {
                ds : "34",
                multi : true,
                save : "id",
                mapping : {
                    value : 'allergiesId'
                },
                controlShow : 0,
                isDefaultVal : true,
                defaultVal : 0
            },
            requires : { valEq : 5 , fields : ['allergiesOther'] }
        },
        {
            id : "allergiesOther",
            xtype : "input",
            setting : {
               disabled : true,
               maxlen: 10,
               size : 10,
               caption : "其他药物过敏史"
            }
        },
        {
            id : "exposeHistory",
            xtype : "list",
            setting : {
                ds : "192",
                multi : true,
                save : "id",
                mapping : {
                    value : 'exposeId'
                },
                controlShow : 0,
                isDefaultVal : true,
                defaultVal : 0
            }
        },
        {
            id : "education",
            xtype : "list",
            setting : {
                ds : "99"
            }
        },
        {
            id : "bloodTypeRh",
            xtype : "list",
            setting : {
                ds : "3",
                isDefaultVal : true,
                defaultVal : 2
            }
        },
        {
            id : "occupation",
            xtype : "list",
            setting : {
                ds : "137",
                newlineStep : 1,
                isDefaultVal : true,
                defaultVal : 4
            }
        }, 
        {
            id : "resideType",
            xtype : "list",
            setting : {
                ds : "7",
                isDefaultVal : true,
                defaultVal : 0
            }
        },
        {
            id : "opshistory",
            xtype : "grid",
            setting : {
                ds : "operations",
                displayCols : ['opsname','opsdate'],
                displayColNames : ["名称", "时间"],
                colXtypes : ['input', 'input'],
                colSettings : [
                    {},
                    { format: 'date'}
                ],
                required : ['opsname']
            },
            errCaption: "手术"
        },
        {
            id : "traumaHistory",
            xtype : "grid",
            setting : {
                ds : "injuries",
                displayCols : ['traumaName','traumaDate'],
                displayColNames : ["名称", "时间"],
                colXtypes : ['input', 'input'],
                colSettings : [
                    {},
                    { format: 'date'}
                ],
                required : ['traumaName']
            },
            errCaption: '外伤'
        },
        {
            id : "bloodTrans",
            xtype : "grid",
            setting : {
                ds : "bloodTrans",
                displayCols : ["reason","transDate"],
                displayColNames : ["原因", "时间"],
                colXtypes : ['input', 'input'],
                colSettings : [
                    {},
                    {}
                ],
                required : ["reason"]
            },
            errCaption: '输血'
        },
        {
            id : "disabilityStatus",
            xtype : "list",
            setting : {
                ds : "6",
                multi : true,
                save : "id",
                mapping : {
                    value : 'disabilityStatusId'
                },
                newlineStep : 7,
                controlShow : 0,
                isDefaultVal : true,
                defaultVal : 0
            },
            requires : { valEq : 8 , fields : ['disabilityStatusOther'] }
        },
        {
            id : "disabilityStatusOther",
            xtype : "input",
            setting : {
                maxlen : 10,
                disabled : true,
                size : 10,
                caption: "其他残疾"
            }
        },
        {
            id : "paymentMode",
            xtype : "list",
            setting : {
                ds : "123",
                multi : true,
                save : "id",
                mapping : {
                        value : 'paymentModeId'
                },
                newlineStep : 4,
                isDefaultVal : true,
                defaultVal : 2
            },
            requires : { valEq : 8 , fields : ['paymentModeOther'] }
        },
        {
            id : "paymentModeOther",
            xtype : "input",
            setting : {
                maxlen : 10,
                disabled : true,
                size : 10,
                caption : "其他支付方式"
            }
        },
        {
            id : "geneticHistory",
            xtype : "list",
            setting : {
                ds : "151",
                isDefaultVal : true,
                defaultVal : 0
            },
            requires : { valEq : 2 , fields : ["geneticHistoryOther"] }
        },
        {
            id : "geneticHistoryOther",
            xtype : "input",
            setting : {
               disabled : true,
               maxlen: 10,
               size : 10,
               caption : '疾病名称'
            }
        },
        {
            id : "diseaseHistory",
            xtype : "grid",
            setting : {
                ds : "diseaseHistory",
                other_init_param : window.parent.other_init_param, //初始化疾病(例如是高血压时设置diseaseId=2)
                displayCols : ['diseaseId','confirmDate', 'remark'],
                displayColNames : ["疾病名称", "确诊时间", "疾病说明"],
                colXtypes : ['combo', 'input', 'input'],
                colSettings : [
                   {
                        ds : "38",
                        multi : false,
                        model: {
                          id : 'number',
                          code : 'number',
                          display : 'name' 
                        },
                        displayCols : ['number', 'name'],
                        displayColNames : ["编号", "疾病"]
                    },
                    { format: 'date'},
                    {}
                ],
//                required : ['diseaseId','confirmDate']
                required : ['diseaseId']
            },
            errCaption : "疾病"
        },

//personalINfo
        {
            id : "address",
            xtype : "input",
            setting : {
                maxlen : 40,
                size : 40
            }
        },
         {
            id : "residenceAddress",
            xtype : "input",
            setting : {
                maxlen : 40,
                size : 40
            }
        },
         {
            id : "tel0",
            xtype : "input",
            setting : {
                maxlen : 18,
                size : 18
            }
        },
         {
            id : "township",
            xtype : "input",
            setting : {
                maxlen : 18,
                size : 18,
                readonly : true
            }
        },
        { id : "village",
            xtype : "input",
            setting : {
                maxlen : 18,
                size : 18,
                readonly : true
            }
        },
         { id : "buildUnit",
            xtype : "input",
            setting : {
                maxlen : 18,
                size : 18
//                readonly : true
            }
        },
        { id : "districtNumber",
            xtype : "input",
            setting : {
                readonly : true
            }
        },
        { id : "buildPerson",
            xtype : "input",
            setting : {
            	maxlen : 18,
                size : 18
//            	readonly : true
            }
        },
        { id : "doctor",
            xtype : "input",
            setting : {
            	maxlen : 18,
                size : 18
//            	readonly : true
            }
        },
        { id : "buildDate",
            xtype : "input",
            setting : {
                format: "date"
                //defaultVal : new Date()
            },
            required : [true, "建档日期"]
        },
        {
            id : "matherHistory",
            xtype : "list",
            setting : {
                ds : "148",
                multi : true,
                save : "id",
                newlineStep : 5,
                forceNewline : true,
                mapping : {
                    value : 'heredityId'
                },
                controlShow : 0,
                isDefaultVal : true,
                defaultVal : 0
            },
            requires : { valEq : 12 , fields : ['mhistoryOther'] }
        },
        {
            id : "mhistoryOther",
            xtype : "input",
            setting : {
                maxlen : 10,
                disabled : true,
                size : 10,
                caption: "残疾"
            }
        },
         {
            id : "fatherHistory",
            xtype : "list",
            setting : {
                ds : "148",
                multi : true,
                save : "id",
                newlineStep : 5,
                forceNewline : true,
                mapping : {
                    value : 'heredityId'
                },
                controlShow : 0,
                isDefaultVal : true,
                defaultVal : 0
            },
            requires : { valEq : 12 , fields : ['fhistoryOther'] }
        },
        {
            id : "fhistoryOther",
            xtype : "input",
            setting : {
                maxlen : 10,
                disabled : true,
                size : 10,
                caption: "其他"
            }
        },
         {
            id : "brotherHistory",
            xtype : "list",
            setting : {
                ds : "148",
                multi : true,
                save : "id",
                newlineStep : 5,
                forceNewline : true,
                mapping : {
                    value : 'heredityId'
                },
                controlShow : 0,
                isDefaultVal : true,
                defaultVal : 0
            },
            requires : { valEq : 12 , fields : ['bhistoryOther'] }
        },
        {
            id : "bhistoryOther",
            xtype : "input",
            setting : {
                maxlen : 10,
                disabled : true,
                size : 10,
                caption: "其他残疾"
            }
        },
        {
            id : "familyHistory",
            xtype : "list",
            setting : {
                ds : "148",
                multi : true,
                save : "id",
                newlineStep : 5,
                forceNewline : true,
                mapping : {
                    value : 'heredityId'
                },
                controlShow : 0,
                isDefaultVal : true,
                defaultVal : 0
            },
            requires : { valEq : 12 , fields : ['fmHistoryOther'] }
        },
        {
            id : "fmHistoryOther",
            xtype : "input",
            setting : {
                maxlen : 10,
                disabled : true,
                size : 10,
                caption: "其他残疾"
            }
        },
        {
            id : "kitchen",
            xtype : "list",
            setting : {
                ds : "1245",
                isDefaultVal : true,
                defaultVal : 0
            }
        },
        {
            id : "bunkers",
            xtype : "list",
            setting : {
                ds : "1250",
                isDefaultVal : true,
                defaultVal : 4
            }
        },
        {
            id : "drinkingWater",
            xtype : "list",
            setting : {
                ds : "1257",
                isDefaultVal : true,
                defaultVal : 0
            }
        },
        {
            id : "toilet",
            xtype : "list",
            setting : {
                ds : "1264",
                isDefaultVal : true,
                defaultVal : 4
            }
        },
        {
            id : "poultry",
            xtype : "list",
            setting : {
                ds : "1270",
                isDefaultVal : true,
                defaultVal : 0
            }
        },{//是否孕产妇
            id : "bornStatus",
            xtype : "list",
            setting : {
                ds : "171",
                disabled : true
            }
          }
    ];




