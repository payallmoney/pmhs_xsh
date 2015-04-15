'use strict';

//angular.module('tasksystem.taskmanager', ['ngRoute'])
//
//    .config(['$routeProvider', function ($routeProvider) {
//        $routeProvider.when('/taskmanager', {
//            templateUrl: 'app/taskmanager/taskmanager.html',
//            controller: 'TaskManagerCtrl'
//        });
//    }])
//
//    .controller('TaskManagerCtrl', function ($scope) {
//        $scope.test = "测试成功";
//    });
//
//function TaskManagerCtrl($scope){
//    $scope.test = "测试成功";
//}


app.controller('TaskManagerCtrl', function ($scope, i18nService) {
    //设置树参数
    //以下处理是为了防止树内容一次加载导致的性能问题,改为了点击加载 , 使用了非deepcopy
    $scope.base =  angular.extend({},$scope.$parent.district[0]);
    $scope.base.children = null;
    $scope.dist = {children:[$scope.base]};
    var rootid = $scope.$parent.district[0].id;
    var rootlength = $scope.$parent.district[0].id.length;
    var extcount = 0;
    if(rootid.substring(rootid.length-2)=='00'){
        extcount = 1
    }
    $scope.treeclick = function(item){
        item.active = !item.active ;
        $scope.currentdistid = item.id ;
        $scope.currentdistname = item.text;
        if(!item.children){
            var root =   $scope.$parent.district[0];
            var count = (item.id.length - rootlength )/3 +extcount;
            for(var i = 0;i<count;i++){
                var childid = item.id.substr(0,6+i*3);
                for(var j = 0 ; j <root.children.length;j++){
                    if(root.children[j].id == childid){
                        root = root.children[j];
                        break;
                    }
                }
            }
            var children = [];
            for(var i = 0 ;i <root.children.length;i++){
                var childitem = angular.extend({},root.children[i]);
                childitem.children = null;
                children.push(childitem);
            }
            item.children= children;
        }
    };
    console.log($scope.districtdata);
    //表格参数
    i18nService.setCurrentLang('zh-cn');
    $scope.myData = [{name: "Moroni", age: 50},
        {name: "Tiancum", age: 43},
        {name: "Jacob", age: 27},
        {name: "Nephi", age: 29},
        {name: "Enos", age: 34}];
    $scope.gridOptions = {
        data: 'myData',
        columnDefs: [{field: 'name'}, {field: 'gender'}],
        paginationPageSizes: [20],
        paginationPageSize: 20,
        rowHeight: 30,
        enableRowSelection: true,
        enableRowHeaderSelection: false,
        multiSelect: false,
        modifierKeysToMultiSelect: false,
        noUnselect: true,
        onRegisterApi: function (gridApi) {
            $scope.gridApi = gridApi;
        }
    };
    $scope.query = function (){
        console.log($scope.districtdata);
    }
});