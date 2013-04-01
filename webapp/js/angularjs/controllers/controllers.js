function MarryCheck($scope, $dialog,$routeParams,$location,$filter) {
	// dwr.engine._async =false;
	var newstr = "/new";
	
	if($location.path().indexOf(newstr)==0){
		CommonExamService.newExam("女方婚检",{
			callback:function(data){
				$scope.woman = data;
				$scope.woman.base = {
					inputdate:data.today,
					examname:'女方婚检',
					checkdate:data.today,
					visitdate:data.today,
					inputpersonid:data.user.username
				};
				$scope.woman.headpicHtml = "近期一寸<br>免冠正面<br>照片加盖<br>婚检专用章<br><font color=red>注:点击照相</font>";
				$scope.woman.items = [{
					"头像":"",
					"血缘关系":"无",
					"既往病史无":true,
					"手术史":"无",
					"现病史":'无',
					"痛经":'无',
					"月经量":'多',
					"既往婚育史":'无',
					"与遗传有关的家族史无":true,
					"家族近亲婚配":'无',
					"精神状态":"正常",
					"特殊体态":"无",
					"智力":"正常",
					"特殊面容":"无",
					"五官":"正常",
					"皮肤毛发":"正常",
					"辨色力":"正常",
					"甲状腺":"正常",
					"杂音":"无",
					"肺":"正常",
					"肝":"未及",
					"四肢脊柱":"正常",
					"阴毛":"正常",
					"乳房":"正常",
					"医学意见":"未发现医学上不宜结婚的情形",
					"咨询指导结果":"接受指导意见",
					"胸部透视":'未发现异常',
					"HIV抗体":"陰性",
					"尿糖":"陰性",
					"梅毒":"陰性",
					"淋球菌":"陰性",
					"滴度":"未检测",
					"检查结果未见异常":true,
					"乙肝表面抗原检测":"陰性",
					"尿HCG":"陰性",
					"尿常规":"正常"
					}];
				$scope.woman.names=['abc','bcd'];
			},
			async:false
		});
		CommonExamService.newExam("男方婚检",{
			callback:function(data){
				console.log(data)
				$scope.man = data;
				$scope.man.base = {
					inputdate:data.today,
					examname:'男方婚检',
					checkdate:data.today,
					visitdate:data.today,
					inputpersonid:data.user.username
				};
				$scope.man.headpicHtml = "近期一寸<br>免冠正面<br>照片加盖<br>婚检专用章<br><font color=red>注:点击照相</font>";
				$scope.man.items = [{
					"头像":"",
					"血缘关系":"无",
					"既往病史无":true,
					"手术史":"无",
					"现病史":'无',
					"既往婚育史":'无',
					"与遗传有关的家族史无":true,
					"家族近亲婚配":'无',
					"精神状态":"正常",
					"特殊体态":"无",
					"智力":"正常",
					"特殊面容":"无",
					"五官":"正常",
					"皮肤毛发":"正常",
					"辨色力":"正常",
					"甲状腺":"正常",
					"杂音":"无",
					"肺":"正常",
					"肝":"未及",
					"四肢脊柱":"正常",
					"阴毛":"正常",
					"医学意见":"未发现医学上不宜结婚的情形",
					"咨询指导结果":"接受指导意见",
					"胸部透视":'未发现异常',
					"HIV抗体":"陰性",
					"尿糖":"陰性",
					"梅毒":"陰性",
					"淋球菌":"陰性",
					"滴度":"未检测",
					"检查结果未见异常":true,
					"喉结":"有",
					"阴茎":"正常",
					"包皮":"正常",
					"睾丸":"双侧扪及",
					"附睾双侧正常":true,
					"精索静脉曲张":"无",
					"乙肝表面抗原检测":"陰性",
					"尿常规":"正常"
					}];
				$scope.man.names=[];
			},
			async:false
		});
		$scope.alerts = [ ];
		$("#exam_addpanel").dialog("open");
	}
	//TODO 输入框统一绑定回车事件
    $scope.save = function(){
		//TODO 页面校验
		if(!$scope.man.base.fileno){
			$scope.alerts.splice(0, 1);
			$scope.alerts.push({type:'error',msg: "男性婚检请选择体检人员!"});
			return;
		}
		if(!$scope.woman.base.fileno){
			$scope.alerts.push({type:'error',msg: "女婚检请选择体检人员!"});
			return;
		}
		console.log($filter('json')($scope.man));
		console.log($filter('json')($scope.woman));
		CommonExamService.saveExam($scope.man,{async:false,
			callback:function(data){
				console.log(data);
				CommonExamService.saveExam($scope.woman,{async:false,
				callback:function(data){
					console.log(data);
					$scope.alerts.splice(0, 1);
					$scope.alerts.push({type:'success',msg: "保存成功!"});
				}});
			}});
    }
	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
    var phototmp = '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"'+
                    'id="onlineTakePhoto" width="280" height="340"'+
                        'codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">'+
                        '<param name="movie" value="onlineTakePhoto.swf" />'+
                        '<param name="quality" value="high" />'+
                        '<param name="bgcolor" value="#a6c9e2" />'+
                        '<param name="allowScriptAccess" value="sameDomain" />'+
                        '<embed src="/onlineTakePhoto.swf" quality="high" bgcolor="#a6c9e2"'+
                            'width="280" height="340" name="onlineTakePhoto" align="middle"'+
                            'play="true"'+
                            'loop="false"'+
                            'quality="high"'+
                            'allowScriptAccess="sameDomain"'+
                            'type="application/x-shockwave-flash"'+
                            'pluginspage="http://www.adobe.com/go/getflashplayer">'+
                        '</embed>'+
                    '</object>'+
					'<div class="modal-footer">'+
					'<a ng-click="close(result)" class="btn" >关闭</button>'+
					'</div>';

    $scope.opts = {
        backdrop: true,
        keyboard: true,
        backdropClick: true,
		dialogClass:'onlinedialog',
        template:  phototmp, // OR: templateUrl: 'path/to/view.html',
        controller: 'OnlinePhotoDialogController'
    };
	$scope.namemap = {"男":{},"女":{}};
	function queryName (query , type ){
		var typecode = type=="男" ? 11:12;
		if(query.term){
			var listdata = null;
			if($scope.namemap[type][query.term.substring(0,1)]){
				var querydata = $scope.namemap[type][query.term.substring(0,1)];
				listdata = {results: []};
				for(var i = 0 ;i <querydata.results.length;i++){
					if(querydata.results[i].text.indexOf(query.term)==0){
						listdata.results.push(querydata.results[i])
					}
				}
			}else{
				var querydata = {results: []}, i, j, s;
				FileNumSearch.listCodePageSize(0,0,$location.path().substring(5)+"%"+query.term.substring(0,1),true,2,typecode,{async:false,
					callback:function(data){
						s = "";
						for (var i = 0; i < data.res.length; i++) {
							s = s + query.term;
							querydata.results.push({id:  data.res[i][0], text: data.res[i][1] +" " + data.res[i][2] + " " + $filter("date")(data.res[i][3],'yyyy-MM-dd') +" " + data.res[i][7],data:data.res[i]});
						}
					}
				});
				listdata=querydata;
				$scope.namemap[type][query.term.substring(0,1)] = querydata;
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
		//这里对数据进行填充
		var obj = null;
		var otherobj = null;
		console.log(item);
		if(item.data[2]=="女"){
			obj = $scope.woman;
			otherobj = $scope.man;
		}else{
			obj = $scope.man;
			otherobj = $scope.woman;
		}
		obj.base.fileno = item.data[0];
		otherobj.items[0]['对方编号'] = item.data[0];
		otherobj.othersidename = item.data[1];
		obj.birthday = item.data[3];
		obj.idcard = denc(item.data[16]);
		obj.occupation = item.data[15];
		obj.education = item.data[14];
		obj.folk = item.data[12];
		obj.items[0]['国籍'] = "中国";
		obj.residenceAddress = item.data[17];
		obj.address = item.data[7];
		obj.zipcode = 678200;
		obj.workUnit = item.data[11];
		obj.linkmanTel = item.data[8];
		obj.address = item.data[7];
		return item.data[1];
	};
	$scope.formatSearching=function(){
		return "输入姓名进行查询...";
	};
	
	$scope.formatNoMatches=function(term){
		return "查询'"+term+"'无结果!";
	}
    $scope.onlinePhoto = function(id){
        var d = $dialog.dialog($scope.opts);
        d.open().then(function(result){
			PersonalInfoService.getHeadPicture({callback:function(data){
					if(data){
						$scope[id].headpicHtml = "<img width=120 height=120 src='/headPicture/" + data+"'/>";
						$scope[id].items[0]['头像'] =  data;
					}
				},
				async:false
			});
        });
    };
}

// the dialog is injected in the specified controller
function OnlinePhotoDialogController($scope, dialog){
  $scope.close = function(result){
    dialog.close(result);
  };
}