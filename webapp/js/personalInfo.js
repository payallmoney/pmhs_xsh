// Generated by CoffeeScript 1.4.0
function formatdate(date){
	var curr_date = date.getDate();
    var curr_month = date.getMonth() + 1; //Months are zero based
    var curr_year = date.getFullYear();
    var strmonth = "0"+curr_month;
    strmonth = strmonth.substr(strmonth.length-2);
    var strday = "0"+curr_date;
    strday = strday.substr(strday.length-2);
    return curr_year+""+strmonth+""+strday;
}

(function() {
  var queryjson;
  window. get_birthday = function(card) {
	  
	    var len = card.length;
	    //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
	    if (len == 15) {
	        var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
	        var arr_data = card.match(re_fifteen);
	        var year = arr_data[2];
	        var month = arr_data[3];
	        var day = arr_data[4];
	        return '19' + year  + month +  day;
	    }else if (len == 18) {
	        var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
	        var arr_data = card.match(re_eighteen);
	        var year = arr_data[2];
	        var month = arr_data[3];
	        var day = arr_data[4];
	        return  year  + month +  day;
	    }
	    return false;
	};

	window.get_sex = function (card){
		var len = card.length;
		if (len == 18) {
			if(parseInt(card.charAt(16))%2==1)
			     return '男';
			else
			     return '女';
		}else if(len==15){
			if(parseInt(card.charAt(14))%2==1)
			     return '男';
			else
			     return '女';
		}
	}
  
  window.printdata = function(data, type, start) {
    var item, msg;
    msg = "";
    if (type === 0 || type === undefined) {
      for (item in data) {
        msg = msg + item + "=" + data[item] + "\n";
      }
    } else if (type === 1) {
      for (item in data) {
        if (typeof item === "function") {
          msg = msg + item + "=" + data[item] + "\n";
        }
      }
    } else if (type === 2) {
      for (item in data) {
        if (item.toLowerCase().indexOf(start.toLowerCase()) >= 0) {
          msg = msg + item + "=" + data[item] + "\n";
        }
      }
    }
    return console.log(msg);
  };

  window.getDWRUrl = function(geturl) {
    return "dwr/interface/" + geturl.substr(0, geturl.indexOf(".")) + ".js";
  };

  window.addHeadScript = function(srcs, callback) {
    var i;
    if (!window.personal_colsMaps.headscripts) {
      window.personal_colsMaps.headscripts = new Array();
    }
    i = 0;
    while (i < srcs.length) {
      if (!window.personal_colsMaps.headscripts[srcs[i]]) {
        $("<script></script>").attr({
          src: getDWRUrl(srcs[i]),
          type: "text/javascript",
          name: "script_autoload"
        }).appendTo($("head"));
        window.personal_colsMaps.headscripts[srcs[i]] = true;
      }
      i++;
    }
    return $(document).queue(callback);
  };

  window.addHeadScriptByKeyWord = function(keyword, data, callback) {
    var count, i, m, srcs, urlreg, urls;
    srcs = new Array();
    count = 0;
    m = 0;
    while (m < keyword.length) {
      urlreg = new RegExp(keyword[m] + "\\s*:\\s*([0-9a-zA-Z_\\.]+)", "g");
      urls = data.match(urlreg);
      i = 0;
      while (i < urls.length) {
        urls[i].match(urlreg);
        srcs[count++] = RegExp.$1;
        i++;
      }
      m++;
    }
    return addHeadScript(srcs);
  };

  window.getContext = function(obj) {
    var html, item;
    html = "";
    if (typeof obj !== "function") {
      if (typeof obj === "object") {
        for (item in obj) {
          if (obj.hasOwnProperty(item)) {
            html = html + getContext(obj[item]);
          }
        }
      } else {
        html = obj;
      }
    }
    return html;
  };

  window.clickCount = function(obj) {
    return $(obj.previousSibling).click();
  };

  window.changedata = function(index, tablename) {
    var datamap, func;
    datamap = window.personal_colsMaps[tablename];
    if (datamap.datas["" + index]) {
      setLabelVal(datamap.datas["" + index], tablename, datamap.pre);
    } else {
      func = eval(datamap.item.url);
      func({
        id: datamap.item.idlist[index].trim()
      }, {
        callback: function(data) {
          datamap.datas["" + index] = data;
          return setLabelVal(data, tablename, datamap.pre);
        }
      });
    }
    return true;
  };

  window.subtabs_click = function(obj, index, count) {
    var i, _results;
    $.each($(obj).parent().parent().find("a"), function(index, item) {
      return $(item).removeClass("subtab_current");
    });
    $(obj).addClass("subtab_current");
    i = 0;
    _results = [];
    while (i < count) {
      if (i === index) {
        $("#" + $(obj).attr("tabname") + (i + 1)).css("display", "block");
      } else {
        $("#" + $(obj).attr("tabname") + (i + 1)).css("display", "none");
      }
      _results.push(i++);
    }
    return _results;
  };

  window.setLabelVal = function(data, tablename, pre) {
    var c, datecols, index, listcols, map, prop, spans, valuecol, _len, _results;
    listcols = window.personal_colsMaps[tablename].listcols;
    datecols = window.personal_colsMaps[tablename].datecols;
    _results = [];
    for (prop in data) {
      c = $("#" + pre + prop);
      if (c && c.html) {
        if (listcols[prop] && (data[prop] != null) && data[prop] !== undefined) {
          index = listcols[prop].ds;
          map = getData(index);
          valuecol = void 0;
          valuecol = "number";
          if (data[prop][0] && typeof data[prop][0] === "object") {
            if (listcols[prop].id) {
              valuecol = listcols[prop].id;
            } else {
              valuecol = 0;
            }
          }
          if (map) {
            _len = map.length - 1;
            spans = $.map(map, function(v, i) {
              var flag, label, number;
              number = void 0;
              label = void 0;
              if ($.isArray(v)) {
                number = v[0];
                label = v[1];
              } else {
                number = v["number"];
                label = v["name"];
              }
              if (label === undefined || label === "undefined") {
                label = "";
              }
              flag = false;
              if (typeof data[prop] === "string") {
                flag = data[prop] === label;
              } else if (typeof data[prop] === "boolean") {
                if (v["number"] === 1 && !data[prop]) {
                  flag = true;
                }
                if (v["number"] === 2 && data[prop]) {
                  flag = true;
                }
              } else {
                i = 0;
                while (i < data[prop].length) {
                  if (v["id"] === data[prop][i][valuecol]) {
                    flag = true;
                    break;
                  }
                  i++;
                }
              }
              if (flag) {
                return "<span><em class='list-selected'>" + number + ". </em><em class='list-label'>" + label + "</em></span>";
              } else {
                return "<span><em>" + number + ". </em><em class='list-label'>" + label + "</em></span>";
              }
            }).join("");
            c.html(spans + "&nbsp;");
            _results.push($("span ->em").addClass("spanAlign"));
          } else {
            _results.push(void 0);
          }
        } else if (datecols[prop] && data[prop]) {
          c.html(Ext.util.Format.date(data[prop], "Y-m-d"));
          _results.push(c.addClass("readcontext"));
        } else if (typeof data[prop] === "object") {

        } else if (typeof data[prop] !== "function") {
          c.html(data[prop]);
          _results.push(c.addClass("readcontext"));
        } else {
          _results.push(void 0);
        }
      } else {
        _results.push(void 0);
      }
    }
    return _results;
  };

  window.loadTriggerParameters = ["fileNo"];

  window.services = {
    get: PersonalInfoService.get,
    save: PersonalInfoService.save,
    aftersave:FileNumSearch.saveHistoryLink,
    afterinit : function(flag){
    	if(flag){
    		//加载页面时
    	}else{
    		//新增页面时
    	}
    }
  };
  function showDialog(msg, clickToClose){
      $.blockUI({ css: { 
          border: 'none', 
          padding: '15px', 
          backgroundColor: '#000', 
          '-webkit-border-radius': '10px', 
          '-moz-border-radius': '10px', 
          opacity: .5, 
          color: '#fff'
      },  message: msg });
      if (clickToClose) {
          $('.blockOverlay').one('click', $.unblockUI);
      }

  }
  window.alertinfo = function(id){
	  var msg = window.checkidnumber(id);
	  console.log("msg=="+msg);
	  if (!isEmpty(msg)){
		  showDialog(msg, true);
	  }else{
		  showDialog("身份证号可以使用,没有重复!", true);
	  }
  }
  function validateIdnumber(code) { 
      var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
      var tip = "";
      var pass= true;
      
      if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|[x|X])?$/i.test(code)){
          tip = "身份证号格式错误";
          pass = false;
      }
      
     else if(!city[code.substr(0,2)]){
          tip = "地址编码错误";
          pass = false;
      }
      else{
          //18位身份证需要验证最后一位校验位
          if(code.length == 18){
              code = code.split('');
              //∑(ai×Wi)(mod 11)
              //加权因子
              var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
              //校验位
              var parity = [ 1, 0 , 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
              var sum = 0;
              var ai = 0;
              var wi = 0;
              for (var i = 0; i < 17; i++)
              {
                  ai = code[i];
                  wi = factor[i];
                  sum += ai * wi;
              }
              var last = parity[sum % 11];
              if(parity[sum % 11] != code[17].toUpperCase()){
                  tip = "校验位错误";
                  pass =false;
              }
          }else if(code.length == 15){
          	
          }else{
          	tip = "格式或长度错误";
              pass = false;
          }
      }
      //if(!pass) alert(tip);
      
      return { valid : pass, msg : tip};
      //return pass;
  }
  window.checkidnumber =  function ( id ){
//	  FileNumSearch.getFileByIdNumber($("#"+id+" input").val(),function(data){
//		  console.log(data);
//	  });
	  var ret = null;
//	  console.log('$("#"+id+" input").val()====='+$("#"+id+" input").val());
	  var idnumber = $("#"+id+" input").val();
	  var valid = validateIdnumber(idnumber);
	  if(!valid.valid){
		  return "身份证号"+valid.msg;
	  }
	  FileNumSearch.checkFileByIdNumber($("#fileNo span").html(),idnumber,		{ 
	        async: false,
	        callback: function(data){
	        	console.log(data);
	        	if(data.length>0){
	        		ret = "不允许录入重复的身份证号!身份证与以下档案重复:"
	        		for(var i = 0 ; i <data.length;i++){
	        			ret += "<br>姓名:"+denc(data[i][1])+"&nbsp;&nbsp;档案编号:"+denc(data[i][0]);
	        		}
	        	}
	        }
	  });
	  return ret;
  }

  window.cfg = [
    {
      id: "fileNo",
      xtype: "input",
      setting: {
        maxlen: 18,
        size: 18,
        asLabel: true
      }
    },{
        id: "historyselect",
        xtype: "historycombo",
        setting : {
    		ds : {
    			search : FileNumSearch.getHistoryList,
    			get : FileNumSearch.getHistoryItem
    		},
    		local : false,
    		cssstyle :"height:100%;width:200px;",
    		model : {
    			id : 0,
    			code : 0,
    			display : 1
    		},
    		maxlen:{
    			"0":',2,3,4,5,',
    			"1":',15,18,'
    		},
    		showDisplay : false,
    		roWhenSet : false,
    		writeback : [ {
    			id : "name",
    			col : 1,
    			force:true
    		} , {
    			id : "sex",
    			col : 2,
    			force:true,
    			nullvalue: "${$3|get_sex}",
    			forcenullvalue:true
    		}  , {
    			id : "idnumber",
    			col : 3,
    			force:true
    		} , {
    			id : "birthday",
    			col : 4,
    			force:true,
    			nullvalue: "${$3|get_birthday}",
    			forcenullvalue:true
    		},{
    			id : "address",
    			col : 6,
    			force:true
    		},{
    			id : "residenceAddress",
    			col : 7,
    			force:true
    		},{
    			id : "tel0",
    			col : 8,
    			force:true
    		},{
    			id : "buildUnit",
    			col : 11,
    			force:true
    		},{
    			id : "buildPerson",
    			col : 12,
    			force:true
    		},{
    			id : "doctor",
    			col : 13,
    			force:true
    		},{
    			id : "buildDate",
    			col : 14,
    			force:true,
    			nullvalue: formatdate(new Date())
    		}
    		],
    		mCodePrefixCtrlId : 'districtNumber',
    		//select id, name, sex, idcard, birthday,(year(getDate()) - year(birthday)) as age,"address,raddress,tel,xz,cwh,jddw,jdr,zrys,jdrq"
    		displayCols : [ 1, 2, 3, 4,11,12 ],
    		displayColNames : [ "姓名", "性别", "身份证号", "生日",'年龄','地址','户籍地址','电话','乡镇','村委会','建档单位','建档人','责任医师','建档日期' ]
    	}
     }, {
      id: "paperFileNo",
      xtype: "input",
      setting: {
        maxlen: 18,
        size: 18
      }
    }, {
      id: "name",
      xtype: "input",
      setting: {
        maxlen: 18,
        size: 18
      },
      required: [true, "姓名"]
    }, {
      id: "barCode",
      xtype: "input",
      setting: {
        maxlen: 18,
        size: 18
      }
    }, {
      id: "sex",
      xtype: "list",
      setting: {
        ds: "111",
        controlShow: 3,
        controlShowVal: "bornStatus"
      },
      required: [true, "性别"]
    }, {
      id: "folk",
      xtype: "list",
      setting: {
        ds: "57",
        isDefaultVal: true,
        defaultVal: 0
      },
      requires: {
        valEq: "2",
        fields: ["folkOther"]
      }
    }, {
      id: "folkOther",
      xtype: "input",
      setting: {
        disabled: true,
        maxlen: 10,
        size: 10,
        caption: "其他民族"
      }
    }, {
      id: "idnumber",
      xtype: "input",
      setting: {
        maxlen: 18,
        size: 18,
        format:'idnumber',
        calculateBirthday: true,
        calculateBirthdayByIDNumber: ["birthday"],
        checkfunc:window.checkidnumber
      },
      required: [true, "身份证号"]
    }, {
      id: "birthday",
      xtype: "input",
      setting: {
        format: "date",
        maxlen: 8,
        size: 10
      },
      required: [true, "出生日期"]
    }, {
      id: "nation",
      xtype: "input",
      setting: {
        maxlen: 8,
        size: 2,
        defaultVal: "中国"
      },
      required: [true, "国籍"]
    }, {
      id: "workUnit",
      xtype: "input",
      setting: {
        maxlen: 19,
        size: 19,
        defaultVal: "无"
      }
    }, {
      id: "tel",
      xtype: "input",
      setting: {
        maxlen: 30,
        size: 15
      }
    }, {
      id: "linkman",
      xtype: "input",
      setting: {
        maxlen: 10,
        size: 10
      }
    }, {
      id: "linkmanTel",
      xtype: "input",
      setting: {
        maxlen: 11,
        size: 11
      }
    }, {
      id: "bloodTypeAbo",
      xtype: "list",
      setting: {
        ds: "115"
      }
    }, {
      id: "maritalStatus",
      xtype: "list",
      setting: {
        ds: "37"
      }
    }, {
      id: "farmStatus",
      xtype: "list",
      setting: {
        ds: "171",
        isDefaultVal: true,
        defaultVal: 0
      },
      required: [true, "是否农业人口"]
    }, {
      id: "townStatus",
      xtype: "list",
      setting: {
        ds: "171",
        isDefaultVal: true,
        defaultVal: 1
      },
      required: [true, "是否城镇居民"]
    }, {
      id: "allergiesHistory",
      xtype: "list",
      setting: {
        ds: "34",
        multi: true,
        save: "id",
        mapping: {
          value: "allergiesId"
        },
        controlShow: 0,
        isDefaultVal: true,
        defaultVal: 0
      },
      requires: {
        valEq: 5,
        fields: ["allergiesOther"]
      }
    }, {
      id: "allergiesOther",
      xtype: "input",
      setting: {
        disabled: true,
        maxlen: 10,
        size: 10,
        caption: "其他药物过敏史"
      }
    }, {
      id: "exposeHistory",
      xtype: "list",
      setting: {
        ds: "192",
        multi: true,
        save: "id",
        mapping: {
          value: "exposeId"
        },
        controlShow: 0,
        isDefaultVal: true,
        defaultVal: 0
      }
    }, {
      id: "education",
      xtype: "list",
      setting: {
        ds: "99"
      }
    }, {
      id: "bloodTypeRh",
      xtype: "list",
      setting: {
        ds: "3",
        isDefaultVal: true,
        defaultVal: 2
      }
    }, {
      id: "occupation",
      xtype: "list",
      setting: {
        ds: "137",
        newlineStep: 1,
        isDefaultVal: true,
        defaultVal: 4
      }
    }, {
      id: "resideType",
      xtype: "list",
      setting: {
        ds: "7",
        isDefaultVal: true,
        defaultVal: 0
      }
    }, {
      id: "opshistory",
      xtype: "grid",
      setting: {
        ds: "operations",
        displayCols: ["opsname", "opsdate"],
        displayColNames: ["名称", "时间"],
        colXtypes: ["input", "input"],
        colSettings: [
          {}, {
              maxlen: 8,
            format: "date"
          }
        ],
        required: ["opsname"]
      },
      errCaption: "手术"
    }, {
      id: "traumaHistory",
      xtype: "grid",
      setting: {
        ds: "injuries",
        displayCols: ["traumaName", "traumaDate"],
        displayColNames: ["名称", "时间"],
        colXtypes: ["input", "input"],
        colSettings: [
          {}, {
              maxlen: 8,
            format: "date"
          }
        ],
        required: ["traumaName"]
      },
      errCaption: "外伤"
    }, {
      id: "bloodTrans",
      xtype: "grid",
      setting: {
        ds: "bloodTrans",
        displayCols: ["reason", "transDate"],
        displayColNames: ["原因", "时间"],
        colXtypes: ["input", "input"],
        colSettings: [{}, {}],
        required: ["reason"]
      },
      errCaption: "输血"
    }, {
      id: "disabilityStatus",
      xtype: "list",
      setting: {
        ds: "6",
        multi: true,
        save: "id",
        mapping: {
          value: "disabilityStatusId"
        },
        newlineStep: 7,
        controlShow: 0,
        isDefaultVal: true,
        defaultVal: 0
      },
      requires: {
        valEq: 8,
        fields: ["disabilityStatusOther"]
      }
    }, {
      id: "disabilityStatusOther",
      xtype: "input",
      setting: {
        maxlen: 10,
        disabled: true,
        size: 10,
        caption: "其他残疾"
      }
    }, {
      id: "paymentMode",
      xtype: "list",
      setting: {
        ds: "123",
        multi: true,
        save: "id",
        mapping: {
          value: "paymentModeId"
        },
        newlineStep: 4,
        isDefaultVal: true,
        defaultVal: 2
      },
      requires: {
        valEq: 8,
        fields: ["paymentModeOther"]
      }
    }, {
      id: "paymentModeOther",
      xtype: "input",
      setting: {
        maxlen: 10,
        disabled: true,
        size: 10,
        caption: "其他支付方式"
      }
    }, {
      id: "geneticHistory",
      xtype: "list",
      setting: {
        ds: "151",
        isDefaultVal: true,
        defaultVal: 0
      },
      requires: {
        valEq: 2,
        fields: ["geneticHistoryOther"]
      }
    }, {
      id: "geneticHistoryOther",
      xtype: "input",
      setting: {
        disabled: true,
        maxlen: 10,
        size: 10,
        caption: "疾病名称"
      }
    }, {
      id: "diseaseHistory",
      xtype: "grid",
      setting: {
        ds: "diseaseHistory",
        other_init_param: window.parent.other_init_param,
        displayCols: ["diseaseId", "confirmDate", "remark"],
        displayColNames: ["疾病名称", "确诊时间", "疾病说明"],
        colXtypes: ["combo", "input", "input"],
        colSettings: [
          {
            ds: "38",
            multi: false,
            model: {
              id: "number",
              code: "number",
              display: "name"
            },
            displayCols: ["number", "name"],
            displayColNames: ["编号", "疾病"]
          }, {
              maxlen: 8,
            format: "date"
          }, {}
        ],
        required: ["diseaseId"]
      },
      errCaption: "疾病"
    }, {
      id: "address",
      xtype: "input",
      setting: {
        maxlen: 40,
        size: 40
      }
    }, {
      id: "residenceAddress",
      xtype: "input",
      setting: {
        maxlen: 40,
        size: 40
      }
    }, {
      id: "tel0",
      xtype: "input",
      setting: {
        maxlen: 18,
        size: 18
      }
    }, {
      id: "township",
      xtype: "input",
      setting: {
        maxlen: 18,
        size: 18,
        readonly: true
      }
    }, {
      id: "village",
      xtype: "input",
      setting: {
        maxlen: 18,
        size: 18,
        readonly: true
      }
    }, {
      id: "buildUnit",
      xtype: "input",
      setting: {
        maxlen: 18,
        size: 18
      }
    }, {
      id: "districtNumber",
      xtype: "input",
      setting: {
        readonly: true
      }
    }, {
      id: "buildPerson",
      xtype: "input",
      setting: {
        maxlen: 18,
        size: 18
      }
    }, {
      id: "doctor",
      xtype: "input",
      setting: {
        maxlen: 18,
        size: 18
      }
    }, {
      id: "buildDate",
      xtype: "input",
      setting: {
         maxlen: 8,
        format: "date"
      },
      required: [true, "建档日期"]
    }, {
      id: "matherHistory",
      xtype: "list",
      setting: {
        ds: "148",
        multi: true,
        save: "id",
        newlineStep: 5,
        forceNewline: true,
        mapping: {
          value: "heredityId"
        },
        controlShow: 0,
        isDefaultVal: true,
        defaultVal: 0
      },
      requires: {
        valEq: 12,
        fields: ["mhistoryOther"]
      }
    }, {
      id: "mhistoryOther",
      xtype: "input",
      setting: {
        maxlen: 10,
        disabled: true,
        size: 10,
        caption: "残疾"
      }
    }, {
      id: "fatherHistory",
      xtype: "list",
      setting: {
        ds: "148",
        multi: true,
        save: "id",
        newlineStep: 5,
        forceNewline: true,
        mapping: {
          value: "heredityId"
        },
        controlShow: 0,
        isDefaultVal: true,
        defaultVal: 0
      },
      requires: {
        valEq: 12,
        fields: ["fhistoryOther"]
      }
    }, {
      id: "fhistoryOther",
      xtype: "input",
      setting: {
        maxlen: 10,
        disabled: true,
        size: 10,
        caption: "其他"
      }
    }, {
      id: "brotherHistory",
      xtype: "list",
      setting: {
        ds: "148",
        multi: true,
        save: "id",
        newlineStep: 5,
        forceNewline: true,
        mapping: {
          value: "heredityId"
        },
        controlShow: 0,
        isDefaultVal: true,
        defaultVal: 0
      },
      requires: {
        valEq: 12,
        fields: ["bhistoryOther"]
      }
    }, {
      id: "bhistoryOther",
      xtype: "input",
      setting: {
        maxlen: 10,
        disabled: true,
        size: 10,
        caption: "其他残疾"
      }
    }, {
      id: "familyHistory",
      xtype: "list",
      setting: {
        ds: "148",
        multi: true,
        save: "id",
        newlineStep: 5,
        forceNewline: true,
        mapping: {
          value: "heredityId"
        },
        controlShow: 0,
        isDefaultVal: true,
        defaultVal: 0
      },
      requires: {
        valEq: 12,
        fields: ["fmHistoryOther"]
      }
    }, {
      id: "fmHistoryOther",
      xtype: "input",
      setting: {
        maxlen: 10,
        disabled: true,
        size: 10,
        caption: "其他残疾"
      }
    }, {
      id: "kitchen",
      xtype: "list",
      setting: {
        ds: "1245",
        isDefaultVal: true,
        defaultVal: 0
      }
    }, {
      id: "bunkers",
      xtype: "list",
      setting: {
        ds: "1250",
        isDefaultVal: true,
        defaultVal: 4
      }
    }, {
      id: "drinkingWater",
      xtype: "list",
      setting: {
        ds: "1257",
        isDefaultVal: true,
        defaultVal: 0
      }
    }, {
      id: "toilet",
      xtype: "list",
      setting: {
        ds: "1264",
        isDefaultVal: true,
        defaultVal: 4
      }
    }, {
      id: "poultry",
      xtype: "list",
      setting: {
        ds: "1270",
        isDefaultVal: true,
        defaultVal: 0
      }
    }, {
      id: "bornStatus",
      xtype: "list",
      setting: {
        ds: "171",
        disabled: true,
        scoredisable : true
      }
    }
  ];

  window.personal_colsMaps = {};

  queryjson = parseParams(window.location.search);

  if (queryjson.fileNo && queryjson.fileNo.length > 0) {
    PersonalInfoService.getExamInfo(queryjson, function(data) {
      var i, item, ordarray, pre, sortNum, sortarray, _results;
      if (data.length > 0) {
        window.personal_colsMaps = {};
        i = 0;
        sortarray = [];
        ordarray = {};
        while (i < data.length) {
          ordarray[window.parseInt(data[i]["ord"])] = i;
          sortarray[i] = window.parseInt(data[i]["ord"]);
          i++;
        }
        sortNum = function(a, b) {
          return a - b;
        };
        sortarray.sort(sortNum);
        i = 0;
        _results = [];
        while (i < data.length) {
          item = data[ordarray[sortarray[i]]];
          pre = item.tablename + item.ord + "_";
          $(".panes").append("<div class='span-22 last' id='" + pre + "div_id'> </div>");
          $(".tabs").append("<li><a href=\"#\" style='color:blue !important;'>" + item.name + "(" + item.idlist.length + "次)" + "</a></li>");
          $("ul.tabs").tabs("div.panes > div", {
            api: true
          });
          $.ajax({
            url: item.htmlurl,
            dataType: "text",
            error: function(req, stat, error) {
              return alert(error);
            },
            scope: item,
            success: function(htmldata, stat) {
              var divstr, htmlstr, regexp, tablename;
              tablename = this.scope.tablename + this.scope.ord;
              pre = tablename + "_";
              addHeadScript(new Array(this.scope.url));
              regexp = /[i|I][d|D]\s*=\s*['|"](\w+)['|"]/g;
              htmldata = htmldata.replace(regexp, "id='" + pre + "$1'");
              htmldata = htmldata.substr(htmldata.indexOf("\n"));
              $("#" + pre + "div_id").append(htmldata);
              i = 0;
              $("#" + pre + "id_tabcount").addClass("tdspan");
              divstr = "<div style='display:none'>";
              htmlstr = "<ul class='middletabs'>";
              while (i < this.scope.idlist.length) {
                divstr += "<div class='hiddendiv'/>";
                if (i === 0) {
                  htmlstr += "<li><a href='#' onclick='changedata(" + i + ",\"" + tablename + "\");'>第" + (i + 1) + "次检查记录</a></li>";
                } else {
                  htmlstr += "<li><a href='#' onclick='changedata(" + i + ",\"" + tablename + "\");'>第" + (i + 1) + "次检查记录</a></li>";
                }
                i++;
              }
              htmlstr += "</ul>";
              divstr += "</div>";
              $("#" + pre + "id_tabcount").append(htmlstr);
              $("#" + pre + "id_tabcount").append(divstr);
              $("#" + pre + "id_subtabcount ul.subtabs").tabs("#" + pre + "div_id tr.trtab");
              $("#" + pre + "id_tabcount ul.middletabs").tabs("#" + pre + "div_id hiddendiv");
              window.personal_colsMaps[tablename] = {
                datecols: {},
                listcols: {},
                pre: pre,
                datas: {},
                cfg: {},
                item: this.scope
              };
              return $.ajax({
                url: this.scope.listcol,
                dataType: "text",
                error: function(req, stat, error) {
                  return alert(error);
                },
                scope: this.scope,
                success: function(jsdata, stat) {
                  var cfg, func, serv;
                  tablename = this.scope.tablename + this.scope.ord;
                  regexp = /var\s+(services)\s*=/g;
                  jsdata = jsdata.replace(regexp, "var " + tablename + "_services = ");
                  regexp = /([var\s|,]\s*)(cfg)\s*=/g;
                  jsdata = jsdata.replace(regexp, "$1" + tablename + "_cfg = ");
                  addHeadScriptByKeyWord(new Array("search", "get"), jsdata);
                  eval(jsdata);
                  serv = eval(tablename + "_services");
                  cfg = eval(tablename + "_cfg");
                  i = 0;
                  while (i < cfg.length) {
                    if (cfg[i].setting) {
                      if (cfg[i].xtype === "list") {
                        window.personal_colsMaps[tablename].listcols[cfg[i].id] = {
                          ds: cfg[i].setting.ds,
                          id: (cfg[i].setting.mapping ? cfg[i].setting.mapping.value : null)
                        };
                      } else {
                        if (cfg[i].xtype === "input" && cfg[i].setting.format === "date") {
                          window.personal_colsMaps[tablename].datecols[cfg[i].id] = true;
                        }
                      }
                    }
                    i++;
                  }
                  func = eval(this.scope.url);
                  return func({
                    id: this.scope.idlist[0].trim()
                  }, {
                    callback: function(data) {
                      window.personal_colsMaps[tablename].datas["0"] = data;
                      return setLabelVal(data, tablename, window.personal_colsMaps[tablename].pre);
                    }
                  });
                }
              });
            },
            errorHandler: function(errStr, e) {
              hideDialog();
              return showDialog("系统发生异常(PersonalInfo.js加载应用数据过程中)<br/>" + errStr, true);
            }
          });
          _results.push(i++);
        }
        return _results;
      }
    });
  }

}).call(this);
