'use strict';


app.controller('TaskManagerCtrl', function ($scope, i18nService,$filter) {
    //初始化查询参数
    $scope.query = {};
    //设置树参数
    //以下处理是为了防止树内容一次加载导致的性能问题,改为了点击加载 , 使用了非deepcopy
    var base = angular.extend({}, $scope.$parent.district[0]);
    base.children = null;
    $scope.query.dist = base.id;
    $scope.currentdistname = base.text;
    $scope.dist = {children: [base]};
    var rootid = $scope.$parent.district[0].id;
    var rootlength = $scope.$parent.district[0].id.length;
    var extcount = 0;
    if (rootid.substring(rootid.length - 2) == '00') {
        extcount = 1
    }

    $scope.treeclick = function (item) {
        item.active = !item.active;
        $scope.query.dist = item.id;
        $scope.currentdistname = item.text;
        if (!item.children) {
            var root = $scope.$parent.district[0];
            var count = (item.id.length - rootlength ) / 3 + extcount;
            for (var i = 0; i < count; i++) {
                var childid = item.id.substr(0, 6 + i * 3);
                if (root.children) {
                    for (var j = 0; j < root.children.length; j++) {
                        if (root.children[j].id == childid) {
                            root = root.children[j];
                            break;
                        }
                    }
                }
            }
            if (root.children) {
                var children = [];
                for (var i = 0; i < root.children.length; i++) {
                    var childitem = angular.extend({}, root.children[i]);
                    childitem.children = null;
                    children.push(childitem);
                }
                item.children = children;
            }
        }
    };
    console.log($scope.districtdata);
    //$scope.dateStr = function(code){
    //    if (typeof code === 'string'){
    //        return code.substring(0,10);
    //    }else{
    //        return code;
    //    }
    //};
    //表格参数
    i18nService.setCurrentLang('zh-cn');
    $scope.gridOptions = {
        data: [],
        columnDefs: [
            {displayName: '任务日期', field: 'smsdate',cellFilter :"substr:0:10"},
            {displayName: '任务类型', field: 'examid'},
            {displayName: '状态', field: 'status',cellTemplate:'<div class="ui-grid-cell-contents"><span ng-show="COL_FIELD==0">正在创建</span><span ng-show="COL_FIELD==2">已完成</span><button  ng-show="COL_FIELD==1"'+
            ' ng-click=" grid.appScope.onCellClick(row)"> '+
            ' 处理任务</button></div>'},
            {displayName: '档案号', field: 'fileno'},
            {displayName: '姓名', field: 'personname'},
            {displayName: '身份证号', field: 'idnumber'},
            {displayName: '生日', field: 'birthday',cellFilter :"date:'yyyy-MM-dd'"},
            {displayName: '电话', field: 'tel'},
            {displayName: '内容', field: 'msg'}
            //{displayName:'完成情况',field: 'status'}
        ],
        paginationPageSizes: [20],
        paginationPageSize: 20,
        paginationCurrentPage: 1,
        useExternalPagination: true,
        //totalItems: $scope.totalItems,
        rowHeight: 30,
        enableRowSelection: true,
        enableRowHeaderSelection: false,
        multiSelect: false,
        modifierKeysToMultiSelect: false,
        noUnselect: true,
        onRegisterApi: function (gridApi) {
            $scope.gridApi = gridApi;
            $scope.gridApi.pagination.on.paginationChanged($scope, function (currentPage, pageSize) {
                $scope.querydata();
            });
        }
    };
    //初始化查询参数
    //初始化分类下拉
    TaskService.getTaskCatOption(function (data) {
        var cats = [];
        $.each(data, function (key, value) {
            cats.push({
                id: value[0],
                name: value[1],
                ord: value[2]
            })
        });
        $scope.query.cat = cats[0].id;
        $scope.cats = cats;
    });
    //初始化类型下拉
    TaskService.getTaskRuleOption(function (data) {
        var rules = [];
        $.each(data, function (key, value) {
            rules.push({
                id: value[0],
                name: value[1],
                ord: value[3],
                parent: value[2]
            })
        });
        $scope.query.rule = rules[0].id;
        $scope.rules = rules;
    });
    //日期校验20110101-20120101的类型
    $scope.dateValidate = function (code) {
        var dateregstr = "(?:(?!0000)[0-9]{4}(?:(?:0[1-9]|1[0-2])(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)";
        var regexp1 = new RegExp("^(" + dateregstr + '|' + dateregstr + "-" + dateregstr + ")$");
        return regexp1;
    };
    $scope.$watch("query.cat", function (newval, oldval) {
        if (!inarray($scope.query.rule, $scope.rules, "id")) {
            $scope.query.rule = "";
        }
    });
    function inarray(str, array, name) {
        if(array && array.length){
            $.each(array, function (key, value) {
                if (name && value[name] == str) {
                    return true;
                } else if (!name && value == str) {
                    return true;
                }
            });
        }
        return false;
    };
    $scope.filterRule = function (item) {
        if (item.id == '' || $scope.query.cat == '' || item.parent == $scope.query.cat) {
            return true;
        }
        return false;
    };
    $scope.$watch("query.dist",function(newval,oldval){
        if(newval != oldval){
            $scope.querydata();
        }
    });
    $scope.querydata = function () {
        var cond = {
            district: $scope.query.dist,
            conditions: []
        };
        var datestr = $scope.query.querydate;
        if (datestr) {
            var begindate = datestr;
            var enddate = datestr;
            if (datestr.indexOf("-") > 0) {
                var strs = datestr.split("-");
                begindate = strs[0];
                enddate = strs[1];
            }
            cond.conditions.push({
                filterKey: "vo.smsdate",
                filterVal: begindate,
                opt: ">="
            });
            cond.conditions.push({
                filterKey: "vo.smsdate",
                filterVal: enddate,
                opt: "<="
            });
        }
        cond.conditions.push({
            filterKey: "vo.examid",
            filterVal: $scope.query.rule,
            opt: "="
        });
        cond.conditions.push({
            filterKey: "vo.parentid",
            filterVal: $scope.query.cat,
            opt: "="
        });
        cond.conditions.push({
            filterKey: "vo.status",
            filterVal: $scope.query.status,
            opt: "="
        });
        TaskService.queryLogs(cond, {start: ($scope.gridOptions.paginationCurrentPage - 1) * 20, limit: 20}, function (data) {
            $scope.gridOptions.data = data.data;
            $scope.gridOptions.totalItems = data.totalSize;
            $scope.$digest();
        });
    }

});