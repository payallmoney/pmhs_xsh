
<html lang="cn" ng-app="myApp" class="ng-scope">
<head>
    <meta name="generator" content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>昆明市公共卫生服务管理系统</title>
    <script type="text/javascript" src="/js/ext/adapter/ext/ext-base.js"></script>

    <script type='text/javascript' src='/dwr/engine.js'></script> 	
    <script type='text/javascript' src='/dwr/util.js'></script>
    <script type='text/javascript' src='/dwr/interface/systemInformationUtils.js'></script>
	<script type="text/javascript" src="js/jquery.tools.min.js"></script>
    <script type="text/javascript" src="js/jquery.blockUI.js"></script>
    <script type="text/javascript" src="/js/ext/ext-all.js"></script>
    <script type="text/javascript" src="../js/ext/ext-lang-zh_CN.js"></script>

    <script type="text/javascript" src="/js/ext/dwrproxy.js"></script>
    <script type="text/javascript" src="/js/jsLoader.js"></script>
    <script type="text/javascript" src="/js/FormSetValues.js"></script>
    <script type="text/javascript" src="/js/TabCloseMenu.js"></script>
    <script type="text/javascript" src="/js/tf.js"></script>
    <link rel="shortcut icon" href="resources/logo/pmhs_title.png" >
    
    <script type="text/javascript" src="js/getDate.js"></script>
    <script type="text/javascript" src="js/valideCode.js"></script>
    <script type="text/javascript">
    	var lastUsername, lastExceptionMessage;
    	
	    <c:if test="${not empty param.login_error}">
    	lastUsername = '<c:out value="${SPRING_SECURITY_LAST_USERNAME}"/>';
    	lastExceptionMessage = '<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>';
    	</c:if>
    	dwr.engine.setErrorHandler(function(){});
    	//alert("lastUsername:"+lastUsername);
    	//alert("lastExceptionMessage:"+lastExceptionMessage);
    </script>
    <link rel="stylesheet" href="css/app.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/jquery-ui-1.10.2.custom.min.css">
</head>
  <body style="height:100%;overflow:hidden">
  <div style="height:100%"><div style="height:100%;width:100%;padding-top:100px;background:url(image/bg1.jpg)" class="ng-scope">
	<div style="width:1000px;height:80px;margin:0px auto 0 auto;background:url(image/title.png) no-repeat">
		<img src="image/title.png" style="with:1000px;height:80px">
	</div>
	<div close="close()" class="login" style="width:300px;margin:20px auto 0px auto;">
		<div class="title">系统登录</div>
		<div class="content">
			<form style="padding:10px 20px 10px 40px;" class="ng-pristine ng-valid"  id="loginForm">
				<p>用户名: <input type="text" id="j_username" name="j_username" style='height:30px;'/></p>
				<p>密&nbsp;&nbsp;码: <input type="password" id="j_password" name="j_password" style='height:30px;' ></p>
				<p>验证码: <br><input type="text" id="code" style='height:30px;width:95px;' ><input title="换一个" id="valideCode" style='height:30px;width:95px;margin-left:12px;text-align:center;' type="text" readonly="readonly"></input></p>
				<div style="text-align:center;">
					<button class="btn" style="margin:0 10px" id="login_sure">登录</button>
					<button class="btn" style="margin:0 10px" id="login_cancel">取消</button>
				</div>
			</form>
			
			
			
			
			
			
			
			
		</div>
	</div>
</div>
</div>




</body></html>