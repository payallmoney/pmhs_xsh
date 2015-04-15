'use strict';

angular.module('tasksystem.main', ['ngRoute','ui.bootstrap'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/main', {
            templateUrl: 'app/main/main.html',
            controller: 'MainCtrl'
        });
    }])

    .controller('MainCtrl', function ($scope, Auth, $location ) {
        $scope.tabs =[];
        $scope.activetext = '';
        //登录状态
        Auth.checklogin().then(function () {
            //取出登录数据
            $scope.user = Auth.getUser();
            //取出菜单数据
            $scope.menu = [{
                "text": '重点人群管理',
                "js": "app/taskmanager/taskmanager.js",
                'html': 'app/taskmanager/taskmanager.html',
                'code': 'TaskManagerCtrl',
                'inject':'$scope'
            }];
            //取出行政区划数据
            CommonExamService.getCurrentDistrict({
                callback: function (data) {
                    $scope.district = data;
                }
            });
            //取出机构数据
            CommonExamService.getCurrentOrgListnew({
                callback: function (data) {
                    $scope.orgdata = data;
                }
            });

            var dashboard = {
                "text": '简介',
                "js": "app/dashboard/dashboard.js",
                'html': 'app/dashboard/dashboard.html',
                'code': 'DashboardCtrl',
                'inject': '$scope',
                "close": false
            };
            $scope.loadTab(dashboard);
        }).catch(function () {
            $location.path('/tasksystem/login');
        });
        var lastActive ;
        $scope.loadTab = function (menu) {
            console.log(menu);
            if(menu.js){
            $script(menu.js, function () {
                var flag = false;
                for (var i = 0; i < $scope.tabs.length; i++) {
                    if ($scope.tabs[i].text == menu.text) {
                        flag = true;

                    }else{
                        $scope.tabs[i].active = false;
                    }
                }
                if (!flag) {
                    menu.close=true;
                    $scope.tabs.push(menu)
                }
                menu.active = true;
                $scope.$digest();
            });
            }else{
                var flag = false;
                for (var i = 0; i < $scope.tabs.length; i++) {
                    if ($scope.tabs[i].text == menu.text) {
                        flag = true;

                    }else{
                        $scope.tabs[i].active = false;
                    }
                }
                if (!flag) {
                    menu.close=true;
                    $scope.tabs.push(menu)
                }
                menu.active = true;
            }
        };

    });