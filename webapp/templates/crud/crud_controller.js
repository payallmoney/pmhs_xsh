function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	    results = regex.exec(location.search);
	return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
	
function CommonCheck($scope, $dialog,$routeParams,$location,$filter,$window) {
	$scope.crudname = urlParam("crudname");
	$scope.alerts = [ ];
	$scope.dateitems = {
			cfg:[
			]};
	CommonExamService.getCrudCols($scope.crudname,{
		callback:function(data){
			$scope.colset = data;
		},
		async:false
	});
	function exam_new (){
		$scope.data = {};
	}
	$scope.testType = function(type){
		return type;
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
	}else if("open" ==urlParam("opt")){
		CommonExamService.loadCrud(urlParam("id"),$scope.crudname,{
			callback:function(data){
				console.log(data);
				$scope.data = data;
			},
			async:false
		});
		$scope.alerts = [ ];
		$("#exam_addpanel").dialog("open");
	}
	//TODO 输入框统一绑定回车事件
    $scope.save = function(){
		//TODO 页面校验
//		if(!$scope.data.fileno){
//			$scope.alerts.push({type:'error',msg: "请选择体检人员!"});
//			return;
//		}
		CommonExamService.saveCrud($scope.data,$scope.crudname,{async:false,
			callback:function(data){
				$scope.data = data;
				if($scope.alerts.length>0)
					$scope.alerts.splice(0, 1);
				$scope.alerts.push({type:'success',msg: "保存成功!"});
			}});
    }
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};

    
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
