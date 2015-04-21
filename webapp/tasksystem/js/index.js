'use strict';

// Declare app level module which depends on views, and components
var app =
    angular.module('tasksystem', [
        'ngRoute',
        'ui.grid',
        'ui.grid.autoResize',
        'ui.grid.resizeColumns',
        'ui.grid.pagination',
        'ui.grid.selection',
        'ui.utils',
        'tasksystem.login',
        'tasksystem.main',
        'tasksystem.filters'
    ]).config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({redirectTo: '/login'});
    }]).config(function ($controllerProvider, $compileProvider, $filterProvider, $provide) {
        app.controller = $controllerProvider.register;
    }).factory('Auth', function ($http, $location, $q) {
        var logined;
        var user;
        return {
            setlogin: function (islogined) {
                logined = islogined;
            },
            getUser: function () {
                return user;
            },
            checklogin: function () {
                var deferred = $q.defer();
                if (!logined) {
                    TaskService.getCurrentUser({
                        callback: function (data) {
                            console.log("data==", data)
                            if (data == null) {
                                $location.path('/tasksystem/login');
                                deferred.reject(data);
                            } else {
                                user = data;
                                deferred.resolve(data);
                            }
                        }, errorHandler: function (message) {
                            $location.path('/tasksystem/login');
                            deferred.reject(message);
                        }
                    });
                } else {
                    TaskService.getCurrentUser({
                        callback: function (data) {
                            user = data;
                            deferred.resolve(data);
                        }, errorHandler: function (message) {
                            $location.path('/tasksystem/login');
                            deferred.reject(message);
                        }
                    });
                }
                return deferred.promise;
            }
        }
    }).controller('ModalInstanceCtrl', function ($scope, $modalInstance, data) {
        $scope.data = data;
        $scope.ok = function () {
            $modalInstance.close(data);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    });