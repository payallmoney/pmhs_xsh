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


app.controller('TaskManagerCtrl', function ($scope, i18nService, uiGridConstants) {
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
});