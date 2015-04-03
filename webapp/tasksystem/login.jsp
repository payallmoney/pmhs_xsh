<!DOCTYPE html>
<!--
BeyondAdmin - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.2.0
Version: 1.0.0
Purchase: http://wrapbootstrap.com
-->

<html xmlns="http://www.w3.org/1999/xhtml">
<!-- Head -->
<head>
    <meta charset="utf-8"/>
    <title>恒辰公卫重点人群管理系统</title>
    <meta name="description" content="modals and wells"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="shortcut icon" href="../assets/img/favicon.png" type="image/x-icon">

    <!--Basic Styles-->
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link id="bootstrap-rtl-link" href="" rel="stylesheet"/>
    <link href="../assets/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="../assets/css/weather-icons.min.css" rel="stylesheet"/>

    <!--Fonts-->

    <!--Beyond styles11111111111111-->

    <link id="beyond-link" href="../assets/css/beyond.min.css" rel="stylesheet"/>

    <link href="../assets/css/demo.min.css" rel="stylesheet"/>
    <link href="../assets/css/typicons.min.css" rel="stylesheet"/>
    <link href="../assets/css/animate.min.css" rel="stylesheet"/>
    <script type='text/javascript' src='/dwr/engine.js'></script>
    <script type='text/javascript' src='/dwr/util.js'></script>
    <script type='text/javascript' src='/dwr/interface/systemInformationUtils.js'></script>
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

        </div>
    </div>
</div>
<!-- /Navbar -->
<!-- Main Container -->
<div class="login-panel container-fluid">
    <!-- Page Container -->
    <div class="page-container">
        <!-- Page Body -->
        <div class="modal-dialog">
            <form role="form" method="post" action="/tasksystem/j_spring_security_check" id="loginForm">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">系统登录</h4>
                    </div>
                    <div class="modal-body">
                        <div style="color:red;padding:10px;text-align: center;" id="msg"></div>
                        <div class="form-group">
                            用户名<span class="input-icon icon-right inverted">
                                                        <input id="j_username" name="j_username" type="text"
                                                               class="form-control">
                                                        <i class="fa fa-user bg-darkorange"></i>
                                                    </span>
                        </div>
                        <div class="form-group" >
                            密码<span class="input-icon icon-right inverted">
                                    <input id="j_password" name="j_password" type="password"
                                           class="form-control">
                                    <i class="fa  fa-lock  bg-palegreen"></i>
                                </span>
                        </div>

                        <div class="form-group">
                            验证码
                            <div class="input-group">
                                <input type="text" id="code" class="form-control" placeholder="验证码">
                                <div title="换一个"  class="input-group-addon" style="padding:4px;text-align: center;" ><input style="border:none;background-color: transparent;width:50px;text-align: center;"  readonly='true' id="valideCode"></div>
                            </div>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onclick="authentication()"  id="login_sure">登录</button>
                        <button type="button" class="btn btn-warning" onclick="clearInput()" id="login_cancel">
                            取消
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <!-- /Page Body -->
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

<script type="text/javascript" src="../js/valideCode.js"></script>
<script>
    $(function(){
        valideCode = getValideCode();
        $('#valideCode').click(function(){
            valideCode = getValideCode();
        });

    })
    function authentication(){
        var code = $('#code').val().toUpperCase();
        var j_username = $('#j_username').val();
        var j_password = $('#j_password').val();
        var msg = '';
        if(j_username == ''){
            msg = '用户名不能为空';
        }
        if(j_password == ''){
            if(msg != '')
                msg = msg + '<br />密码不能为空';
            else
                msg = '密码不能为空';
        }
        if(code != valideCode){
            if(msg != '')
                msg = msg + '<br />验证码错误';
            else
                msg = '验证码错误';
        }
        if(msg == ''){
            loginForm.submit();
        }else{
            $(".error").html(msg);
        }
    }
    var valideCode = '';

    function getValideCode(){
        var codeArray = ['A','B','C','D','E','F','G','H','I','J',
            'K','L','M','N','O','P','Q','R','S','T','U','V',
            'W','X','Y','Z'];
        var code = '';
        for(var i = 0;i < 4 ;i++){
            code = code + codeArray[Math.round(Math.random() * 25)];
        }
        $('#valideCode').val(code);
        return code;
    }

    function clearInput(){
        $('#j_username').val('');
        $('#j_password').val('');
        $('#code').val('');
    }


</script>
</body>
</html>
