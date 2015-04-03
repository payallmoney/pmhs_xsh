<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>恒辰重点人群管理系统</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta name="description" content="Dashboard"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="shortcut icon" href="../assets/img/favicon.png" type="image/x-icon">

    <!--Basic Styles-->
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../assets/bootstrap-dialog/css/bootstrap-dialog.min.css" rel="stylesheet"/>
    <link id="bootstrap-rtl-link" href="" rel="stylesheet"/>
    <link href="../assets/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="../assets/css/weather-icons.min.css" rel="stylesheet"/>

    <!--Fonts-->
    <!--Beyond styles-->
    <link id="beyond-link" href="../assets/css/beyond.min.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/css/demo.min.css" rel="stylesheet"/>
    <link href="../assets/css/typicons.min.css" rel="stylesheet"/>
    <link href="../assets/css/animate.min.css" rel="stylesheet"/>
    <link id="skin-link" href="" rel="stylesheet" type="text/css"/>

    <!--Skin Script: Place this script in head to load scripts for skins and rtl support-->
    <script src="../assets/js/skins.js"></script>
</head>
<!-- /Head -->
<!-- Body -->
<body>
<!-- Loading Container -->
<div class="loading-container">
    <div class="loader"></div>
</div>
<!--  /Loading Container -->
<!-- Navbar -->
<div class="navbar">
    <div class="navbar-inner">
        <div class="navbar-container">
            <!-- Navbar Barnd -->
            <div class="navbar-header pull-left">
                <a href="#" class="navbar-brand">
                    <small>
                        <img src="../assets/img/logo.png" alt=""/>
                    </small>
                </a>
            </div>
            <!-- /Navbar Barnd -->
            <!-- Sidebar Collapse -->
            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="collapse-icon fa fa-bars"></i>
            </div>
            <!-- /Sidebar Collapse -->
            <!-- Account Area and Settings --->
            <div class="navbar-header pull-right">
                <div class="navbar-account">
                    <ul class="account-area">
                        <li>
                            <a style="min-width: 80px;" class="login-area dropdown-toggle" data-toggle="dropdown">
                                <section>
                                    <h2><span class="profile"><span>登录人员</span></span></h2>
                                </section>
                            </a>
                            <!--Login Area Dropdown-->
                            <ul class="pull-right dropdown-menu dropdown-arrow dropdown-login-area"
                                style="min-width: 80px;">
                                <li class="username"><a>登录人员</a></li>
                                <li class="theme-area">
                                    <ul class="colorpicker" id="skin-changer">
                                        <li><a class="colorpick-btn" href="#" style="background-color:#5DB2FF;"
                                               rel="../assets/css/skins/blue.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#2dc3e8;"
                                               rel="../assets/css/skins/azure.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#03B3B2;"
                                               rel="../assets/css/skins/teal.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#53a93f;"
                                               rel="../assets/css/skins/green.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#FF8F32;"
                                               rel="../assets/css/skins/orange.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#cc324b;"
                                               rel="../assets/css/skins/pink.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#AC193D;"
                                               rel="../assets/css/skins/darkred.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#8C0095;"
                                               rel="../assets/css/skins/purple.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#0072C6;"
                                               rel="../assets/css/skins/darkblue.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#585858;"
                                               rel="../assets/css/skins/gray.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#474544;"
                                               rel="../assets/css/skins/black.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="#" style="background-color:#001940;"
                                               rel="../assets/css/skins/deepblue.min.css"></a></li>
                                    </ul>
                                </li>
                                <!--/Theme Selector Area-->
                                <li class="dropdown-footer">
                                    <a href="/tasksystem/logout">
                                        登出
                                    </a>
                                </li>
                            </ul>
                            <!--/Login Area Dropdown-->
                        </li>
                        <!-- /Account Area -->
                        <!--Note: notice that setting div must start right after account area list.
                        no space must be between these elements-->
                        <!-- Settings -->
                    </ul>
                    <div class="setting">
                        <a id="btn-setting" title="Setting" href="#">
                            <i class="icon glyphicon glyphicon-cog"></i>
                        </a>
                    </div>
                    <div class="setting-container">

                    </div>
                    <!-- Settings -->
                </div>
            </div>
            <!-- /Account Area and Settings -->
        </div>
    </div>
</div>
<div class="main-container container-fluid">
    <!-- Page Container -->
    <div class="page-container">
        <!-- Page Sidebar -->
        <div class="page-sidebar" id="sidebar">
            <!-- Page Sidebar Header-->
            <div class="sidebar-header-wrapper">
                <input type="text" class="searchinput"/>
                <i class="searchicon fa fa-search"></i>

                <div class="searchhelper">搜索菜单</div>
            </div>
            <ul class="nav sidebar-menu">
                <li class="active">
                    <a href="javascript:void(0)" onclick="loadHtml('/admin/helpmanager','.page-body')">
                        <i class="menu-icon glyphicon glyphicon-tasks"></i>
                        <span class="menu-text">重点人群管理 </span>
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <!-- Page Breadcrumb -->
            <div class="page-breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="#">首页</a>
                    </li>
                    <li class="active">重点人群管理</li>
                </ul>
            </div>

            <div class="page-body">


            </div>
            <!-- /Page Body -->
        </div>
        <!-- /Page Content -->
    </div>
    <!-- /Page Container -->
    <!-- Main Container -->

</div>
<script src="../assets/js/jquery-2.0.3.min.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>
<script src="../assets/js/slimscroll/jquery.slimscroll.min.js"></script>

<!--Beyond Scripts-->
<script src="../assets/js/beyond.min.js"></script>

<!--Page Related Scripts-->
<script src="../assets/js/bootbox/bootbox.js"></script>
</body>
</html>