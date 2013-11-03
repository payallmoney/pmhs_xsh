function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	    results = regex.exec(location.search);
	return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
	
function CommonCheck($scope, $dialog,$routeParams,$location,$filter,$window) {
	$scope.examname = urlParam("examname");
	console.log("$scope.examname",$scope.examname)
	// dwr.engine._async =false;
	$scope.namequeryoptions = [
	                 {name:'条形码', value:0},
	                 {name:'档案编号', value:1},
	                 {name:'姓名', value:2},
	                 {name:'身份证号', value:3},
	                 {name:'联系人', value:4}
	               ];
	$scope.womannameselect = 2;
	$scope.mannameselect = 2;
	$scope.districtdata = $window.top.districtdata;
	function exam_new (){
		CommonExamService.newExam($scope.examname,{
			callback:function(data){
				$scope.data = {},$scope.woman = {},$scope.man = {};
				$scope.data.base = {
					inputdate:data.today,
					examname:$scope.examname,
					checkdate:data.today,
					visitdate:data.today,
					inputpersonid:data.user.username,
					status:1
				};
				$scope.data.items = [{
					"HIV抗体":"陰性",
					"梅毒":"陰性",
					"淋球菌":"陰性",
					"梅毒滴度":"未检测"
					}];
				
			},
			async:false
		});
		$scope.alerts = [ ];
		
	}
	$scope.newExam = function(){
	    var btns = [{result:'cancel', label: '取消'}, {result:'ok', label: '确定', cssClass: 'btn-primary'}];
	    $dialog.messageBox("确认", '是否确定新建?', btns)
	      .open()
	      .then(function(result){
	    	  if(result=='ok'){
	    		  exam_new();
	    	  }
	    });
	}
	
	if("new" ==urlParam("opt")){
		exam_new();
		$("#exam_addpanel").dialog("open");
		$scope.mandistrictselect = $window.top.districtdata.nodemap[urlParam("district")].value;
	}else if("open" ==urlParam("opt")){
		CommonExamService.common_loadExam(urlParam("id"),{
			callback:function(data){
				$scope.data = data;
				$scope.man = $scope.data.man;
				var mandistrict = $.trim($scope.data.man.districtNumber);
				$scope.mandistrictselect = $window.top.districtdata.nodemap[mandistrict].value;
			},
			async:false
		});
		$scope.alerts = [ ];
		$("#exam_addpanel").dialog("open");
	}
	$scope.initSelection = function(element, callback){
		var id=$(element).val();
		if(id){
			var data = {id: id, text: id};
			callback(data);
		}
	}
	//TODO 输入框统一绑定回车事件
    $scope.save = function(){
		//TODO 页面校验
		if(!$scope.data.base.fileno){
			$scope.alerts.push({type:'error',msg: "请选择体检人员!"});
			return;
		}
		CommonExamService.saveExam($scope.data,{async:false,
			callback:function(data){
				$scope.data.base.id = data.base.id;
				$scope.data.items = data.items;
				$scope.alerts.splice(0, 1);
				$scope.alerts.push({type:'success',msg: "保存成功!"});
			}});
    }
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};

	$scope.namemap = {"男":{},"女":{}};
	function queryName (query , type ){
		var typecode = '13';
		var sexcode =  'man';
		
		var district = $('#'+sexcode+'districtselect').attr("realvalue");
		var querytype = parseInt($('#'+sexcode+'nameselect').attr("realvalue"));
		var flag = false;
		if(querytype == 2 || querytype == 4){ //2是姓名,4是联系人,只要输入一位就进行查询
			if(query.term && query.term.length >=2){
				flag = true;
			}
		}else if (querytype == 3){ //3是身份证号,输入10位后开始查询
			if(query.term && query.term.length >=10){
				flag = true;
			}
		}else if (querytype == 0 || querytype ==1){ //0是条形码,1是档案编号 输入4位进行查询
			if(query.term && query.term.length >=4){
				flag = true;
			}
		}
		console.log(flag)
		console.log(query.term);
		console.log(query.term.length)
		if(flag){
			var listdata = null;
			var cachestr = "";
			if(querytype == 2 || querytype ==4){ //2是姓名,4是联系人,只要输入一位就进行查询
				cachestr = query.term.substring(0,2);
			}else if (querytype == 3){ //3是身份证号,输入10位后开始查询
				cachestr = query.term.substring(0,10);
			}else if (querytype == 0 || querytype ==1){ //0是条形码,1是档案编号 输入4位进行查询
				cachestr = query.term.substring(0,4);
			}
			if(!$scope.namemap[type][querytype]){
				$scope.namemap[type][querytype] = {};
			}
			if(!$scope.namemap[type][querytype][district]){
				$scope.namemap[type][querytype][district]={}
			}
			console.log($scope.namemap[type][querytype][district][cachestr])
			if($scope.namemap[type][querytype][district][cachestr]){
				var querydata = $scope.namemap[type][querytype][district][cachestr];
				listdata = {results: []};
				for(var i = 0 ;i <querydata.results.length;i++){
					if(querydata.results[i].text.indexOf(query.term)==0){
						listdata.results.push(querydata.results[i])
					}
				}
			}else{
				console.log(district+"%"+query.term)
				var querydata = {results: []}, i, j, s;
				FileNumSearch.listCodePageSize(0,0,district+"%"+query.term,true,querytype,typecode,{async:false,
					callback:function(data){
						console.log("data===",data);
						s = "";
						for (var i = 0; i < data.res.length; i++) {
							s = s + query.term;
							querydata.results.push({id:  data.res[i][0], text: data.res[i][1] +" " + data.res[i][2] + " " + $filter("date")(data.res[i][3],'yyyyMMdd') +" " + data.res[i][7],data:data.res[i]});
						}
					}
				});
				listdata=querydata;
				$scope.namemap[type][querytype][district][cachestr] = querydata;
			}
			query.callback(listdata);
		}
	}
    $scope.womanchangeName = function(query){
		queryName(query,"女");
	};
    $scope.manchangeName = function(query){
		queryName(query,"男");
	};
	$scope.formatSelection=function(item){
		if(!item.data){
			return item.text;
		}else{
			//这里对数据进行填充
			var obj = null;
			var otherobj = null;
			$scope.data.items[0]["编号"] = enc(item.data[0])
			$scope.data.base.fileno = enc(item.data[0]);
			obj = $scope.man;
			obj.birthday = item.data[3];
			obj.idcard = denc(item.data[16]);
			obj.occupation = item.data[15];
			obj.education = item.data[14];
			obj.folk = item.data[12];
			$scope.data.items[0]['国籍'] = item.data[19];
			obj.residenceAddress = item.data[17];
			obj.address = item.data[7];
			$scope.data.items[0]['邮编'] = 678200;
			obj.workUnit = item.data[11];
			obj.linkmanTel = item.data[8];
			obj.address = item.data[7];
			item.text = item.data[1];
			var district = $('#mandistrictselect').attr("realvalue");
			return item.data[1];
		}
	};
	$scope.manSearching=function(){
		return "输入"+ $('#mannameselect').find("option:selected").text()+"进行查询...";
	};
	$scope.dateitems = {
		cfg:[
			{parent:$scope.data.base , item: 'inputdate'},
			{parent:$scope.data.base , item: 'checkdate'},
			{parent:$scope.data.base , item: 'visitdate'}
		]};
	function datestrWatch(scope){
		var retstr = "";
		for(var i = 0 ; i <scope.dateitems.cfg.length;i++){
			var item = scope.dateitems.cfg[i];
			if(item.parent[item.item] instanceof Date){
				item.parent[item.item] = $filter("date")(item.parent[item.item],'yyyyMMddHHmmss');
			}
			if(item.parent[item.item]){
				retstr = retstr+item.parent[item.item]+"|";
			}else{
				retstr = retstr+"|";
			}
		}
		return retstr;
	}
	$scope.$watch(datestrWatch, function(newValue, oldValue) { 
		if(oldValue && newValue){
			var olds = oldValue.split("|");
			var news = newValue.split("|");
			for(var i = 0 ; i <news.length;i++){
				var item = $scope.dateitems.cfg[i];
				if(item){
					if(news[i] && item.parent[item.item]){
						var init = item.parent[item.item];
						$scope.dateitems[item.item]=new Date(moment(init, "YYYYMMDDHHmmss"));
					}else{
						$scope.dateitems[item.item]=null;
					}
				}
			}
		}
	});
	
	function dateWatch(scope){
		var retstr = "";
		for(var i = 0 ; i <scope.dateitems.cfg.length;i++){
			var item = scope.dateitems.cfg[i];
			var watchitem = scope.dateitems[item.item];
			if(watchitem){
				retstr = retstr+$filter("date")(watchitem,'yyyyMMddHHmmss')+"|";
			}else{
				retstr = retstr+"|";
			}
		}
		return retstr;
	}
	
	$scope.$watch(dateWatch, function(newValue, oldValue) { 
		if(oldValue && newValue){
			var olds = oldValue.split("|");
			var news = newValue.split("|");
			for(var i = 0 ; i <news.length;i++){
				var item = $scope.dateitems.cfg[i];
				if(item){
					if(news[i]){
						item.parent[item.item] = unescape($filter("date")($scope.dateitems[item.item],'yyyyMMddHHmmss'));
					}else{
						item.parent[item.item] = null;
					}
				}
			}
		}
	});
	
	$scope.formatNoMatches=function(term){
		return "查询'"+term+"'无结果!";
	}
    
    function urlParam(name){
        var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec($location.path());
        return results[1] || null;
    }
    
    $scope.exit = function(){
    	if($window.parent){
    		$window.parent.closeDialog();
    	}
    }
}

// the dialog is injected in the specified controller
function OnlinePhotoDialogController($scope, dialog){
  $scope.close = function(result){
    dialog.close(result);
  };
}