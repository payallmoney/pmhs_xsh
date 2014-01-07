<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="cn" ng-app="myApp" class="ng-scope">
<head>
<meta name="generator" content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>测试环境</title> <script type="text/javascript" src="/js/ext/adapter/ext/ext-base.js"></script>
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
		<link rel="shortcut icon" href="resources/logo/pmhs_title.png">

			<script type="text/javascript" src="js/getDate.js"></script>
			<script type="text/javascript" src="js/valideCode.js"></script>
			<script type="text/javascript">
				var lastUsername, lastExceptionMessage;

				<c:if test="${not empty param.login_error}">
				lastUsername = '<c:out value="${SPRING_SECURITY_LAST_USERNAME}"/>';
				lastExceptionMessage = '<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>';
				</c:if>
				dwr.engine.setErrorHandler(function() {
				});
				//alert("lastUsername:"+lastUsername);
				//alert("lastExceptionMessage:"+lastExceptionMessage);
			</script>
			<link rel="stylesheet" href="css/app.css">
				<link rel="stylesheet" href="css/bootstrap.min.css">
					<link rel="stylesheet" href="css/jquery-ui-1.10.2.custom.min.css">
</head>
<body style="height: 100%; overflow: hidden">
	<div style="height: 100%;width:100%;">
		<div style="height: 100%; width: 100%; background-image:url(image/bg3.png);" class="ng-scope">
			<div id='div_login' close="close()" class="login"
				style="width: 542px; height: 600px; margin: 0 auto; background-image: url(image/login.png); background-repeat: no-repeat;">
				<!-- <div class="title">系统登录</div>
				<div class="content">
					<form style="padding: 10px 20px 10px 40px;" class="ng-pristine ng-valid" id="loginForm">
						<p>
							用户名: <input type="text" id="j_username" name="j_username" style='height: 30px;' />
						</p>
						<p>
							密&nbsp;&nbsp;码: <input type="password" id="j_password" name="j_password" style='height: 30px;'>
						</p>
						<p>
							验证码: <br><input type="text" id="code" style='height: 30px; width: 95px;'><input title="换一个" id="valideCode"
									style='height: 30px; width: 95px; margin-left: 12px; text-align: center;' type="text" readonly="readonly"></input>
						</p>
						<div style="text-align: center;">
							<button class="btn" style="margin: 0 10px" id="login_sure">登录</button>
							<button class="btn" style="margin: 0 10px" id="login_cancel">取消</button>
						</div>
					</form>
				</div>
				<div>
					<a href='/download/chrome_installer_552.210.exe'>谷歌浏览器下载</a> <a href='/download/install_lodop.exe'>打印控件下载</a><br>
				</div>
				 -->
				 <div class="content1">
					<form style="padding: 15px 20px 10px 40px;" class="ng-pristine ng-valid" id="loginForm">
						<p>
							<input type="text" id="j_username" name="j_username" style='height: 12px;width:150px;' />
						</p>
						<p>
							<input type="password" id="j_password" name="j_password" style='height: 12px;width:150px;'>
						</p>
						<p>
							<input type="text" id="code" style='height: 12px; width: 60px;'><input title="换一个" id="valideCode"
									style='height: 12px; width: 60px; margin-left: 12px; text-align: center;' type="text" readonly="readonly"></input>
						</p>
						<div style="text-align: left;margin-left:100px;">
							<button class="btn" style="margin: 0 10px" id="login_sure">登录</button>
							<button class="btn" style="margin: 0 10px" id="login_cancel">取消</button>
						</div>
					</form>
				</div>
				<div>
					<a href='/download/chrome_installer_552.210.exe'>谷歌浏览器下载</a> <a href='/download/install_lodop.exe'>打印控件下载</a><br>
				</div>
			</div>
			<script>
				var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
				if (is_chrome) {

				} else {
					$("#div_login").hide();
					document
							.write("<div style='text-align:center;width:100%;margin:20px 0;color:#fff;'>系统不支持您目前使用的浏览器，请下载谷歌浏览器：<br><a style='color:red' href='/download/chrome_installer_552.210.exe'>谷歌浏览器下载</a> </div>")
				}
			</script>
		</div>
	</div>
</body>
</html>