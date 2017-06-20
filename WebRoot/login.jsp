<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/login.css">
<title>用户登录界面</title>
</head>
<body>
	<script>
		(function(T, h, i, n, k, P, a, g, e) {
			g = function() {
				P = h.createElement(i);
				a = h.getElementsByTagName(i)[0];
				P.src = k;
				P.charset = "utf-8";
				P.async = 1;
				a.parentNode.insertBefore(P, a)
			};
			T["ThinkPageWeatherWidgetObject"] = n;
			T[n] || (T[n] = function() {
				(T[n].q = T[n].q || []).push(arguments)
			});
			T[n].l = +new Date();
			if (T.attachEvent) {
				T.attachEvent("onload", g)
			} else {
				T.addEventListener("load", g, false)
			}
		}(window, document, "script", "tpwidget",
				"//widget.seniverse.com/widget/chameleon.js"))
	</script>
	<script>
		tpwidget("init", {
			"flavor" : "bubble",
			"location" : "WW02P7XHT0KB",
			"geolocation" : "enabled",
			"position" : "top-right",
			"margin" : "10px 10px",
			"language" : "zh-chs",
			"unit" : "c",
			"theme" : "chameleon",
			"uid" : "U09DD5FEDD",
			"hash" : "a960cd4c891f2800349b252dc721c72d"
		});
		tpwidget("show");
	</script>
<body>
	<video autoplay loop="-1"> <source
		src="video/backgroundvideo.mp4" type="video/mp4">
	您的游览器暂时不支持HTML5 </video>
	<div class="box">

		<div class="loginpanel">
			<div class="systemName">
				<span class="time"><i class="glyphicon glyphicon-time"></i>&nbsp;<i
					id="times"></i></span> <span class="welcomeMessage"><em><marquee
							direction="left">欢迎使用</marquee></em></span>
			</div>
			<div class="userinfo">

				<div class="img"></div>
				<div class="userLogin">
					<span class="userName"><i class="fa fa-user-circle"></i><input
						type="text" name="userName" id="userName" placeholder="username" /><i
						class="fa fa-check"></i></span> <span class="userPassword"><i
						class="fa fa-lock"></i><input type="password" name="userPassword"
						id="userPassword" placeholder="userpassword" /><i
						class="fa fa-eye-slash "></i><i class="fa fa-eye"></i></span> </span>
					<div class="operationBtn">
						<button class="btn btn-danger" id="registerBtn">
							<i class="fa fa-address-card" aria-hidden="true"></i>&nbsp;注册
						</button>
						<button class="btn btn-success" id="loginBtn">
							登录&nbsp;<i class="fa fa-arrow-circle-right" aria-hidden="true"></i>
						</button>
					</div>

				</div>
			</div>
		</div>
		<div class="regiserpanel">
			<div class="registertitle">
				<i class="fa fa-user-plus"></i> 用户注册
			</div>
			<div class="registerUsers">
				<ul class="registerItems">
					<li class=" registerItem registerUserName"><i
						class="fa fa-user"></i><input placeholder="至少5-16位用户名" type="text"
						id="registerUserName" /><span class="registerUserNameMsg">*用户名 </span></li>
					<li class=" registerItem registerUserEmail"><i
						class="fa fa-envelope"></i><input placeholder="您的邮箱" type="text"
						id="registerUserEmail" /><span class="registerUserEmailMsg">*邮箱地址</span></li>
					<li class=" registerItem registerUserPassword"><i
						class="fa fa-lock"></i><input placeholder="至少8位的英文和数字" type="password"
						id="registerUserPassword" /><span class="registerUserPasswordMsg">*至少8位</span></li>
					<li class=" registerItem registerConfirmPassword"><i
						class="fa fa-key"></i><input placeholder="再次输入您的密码"
						type="password" id="registerConfirmPassword" /><span
						class="registerConfirmPasswordMsg">*确认密码</span></li>
					<li class=" registerItem registerSubmit">注册</li>
					<li class=" registerItem registerLogin">如果您已经拥有账号,请点击 <a
						href="javascript:void(0);" id="loginTP">登录&nbsp;<i
							class="fa fa-anchor" aria-hidden="true"></i></a>
					</li>
				</ul>
			</div>
			<div class="registerPower">Copyright ©2017 Powered By
				Administrator Version 1.0.0</div>
		</div>
</body>

<script type="text/javascript"
	src="https://cdn.bootcss.com/jquery/3.2.0/jquery.min.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {

						//getTime
						function getTime() {
							var date = new Date();
							var year = date.getFullYear();
							var month = rightTime((date.getMonth() + 1));
							var day = rightTime(date.getDate());
							var hour = rightTime(date.getHours());
							var minutes = rightTime(date.getMinutes());
							var seconds = rightTime(date.getSeconds());
							//时间格式化
							function rightTime(timers) {

								// timers>10 ? return timers : return  '0'+timers;
								if (timers > 10) {
									return timers;
								} else {
									return '0' + timers;
								}
							}
							var nowTime = year + "年" + month + "月" + day
									+ "日 &nbsp;" + hour + ":" + minutes + ":"
									+ seconds;
							$('#times').html(nowTime);
						}

						setInterval(getTime, 1000);
						//gettime end
						//进入注册页面
						$('#registerBtn').on('click', function() {
							$('.loginpanel').fadeOut(1000);
							$('.regiserpanel').fadeIn(1000);

						});
						//进入登录界面
						$('#loginTP').on('click', function() {
							$('.loginpanel').fadeIn(1000);
							$('.regiserpanel').fadeOut(1000);

						});
						//输入密码是否可见
						$('.fa-eye').on('click', function() {
							$(this).css('display', 'none');
							$('.fa-eye-slash').css('display', 'inline-block');
							$('#userPassword').removeAttr("type");
							$('#userPassword').attr("type", "password");
						});
						$('.fa-eye-slash').on('click', function() {
							$(this).css('display', 'none');
							$('.fa-eye').css('display', 'inline-block');
							$('#userPassword').removeAttr("type");
							$('#userPassword').attr("type", "text");
						});
						//登录
						$('#loginBtn').on('click', function() {
							var password = $('#userPassword').val().toString();
							var name = $('#userName').val().toString();
							checkStr(name, password);
						});
						//注册
						$('.registerSubmit').on(
								'click',
								function() {
									//获取用户数据
									var registerUserName = $(
											'#registerUserName').val()
											.toString();
									var registerUserEmail = $(
											'#registerUserEmail').val()
											.toString();
									var registerUserPassword = $(
											'#registerUserPassword').val()
											.toString();
									var registerConfirmPassword = $(
											'#registerConfirmPassword').val()
											.toString();
									checkRegister(registerUserName,
											registerUserEmail,
											registerUserPassword,
											registerConfirmPassword);

								});
						// 校验用户注册的数据
						function checkRegister(registerUserName,
								registerUserEmail, registerUserPassword,
								registerConfirmPassword) {
							var userNameReg = /[a-zA-Z0-9_-]{5,16}/;//最少5-16位
							var userNameReg = /^[\u4e00-\u9fa5]{2,4}$/; //请正确填写姓名！姓名为两到四个汉字。
							//var userNameReg = /[\u4e00-\u9fa5_a-zA-Z0-90_]{4,10}///匹配中文，英文字母和数字及_ （4-10个长度）
							var userEmailReg = /^([a-zA-Z0-9_-])+\@([a-zA-Z0-9_-])+.([a-zA-Z])+$/;//验证邮箱
							var userPassword = /^([a-zA-z]+[0-9]+)|([0-9]+[a-zA-Z]+)$/;//必须有字母和数字至少八位 
							var rightHTML = "<i style='color:green;padding-left:25px;' class='fa fa-check'></i>";
							var faliureHTML = "<i style='color:red;padding-left:25px' class='fa fa-times'></i>";
							//注册用户名验证
							if (registerUserName
									&& userNameReg.test(registerUserName)) {
								var UserNameFlag = true;
								$('.registerUserNameMsg').html(rightHTML);

							} else {

								$('.registerUserNameMsg').html(faliureHTML);

								var UserNameFlag = false;
							}
							if (registerUserEmail
									&& userEmailReg.test(registerUserEmail)) {
								var userEmailFlag = true;
								$('.registerUserEmailMsg').html(rightHTML);
							} else {
								$('.registerUserEmailMsg').html(faliureHTML);
								var userEmailFlag = false;
							}
							if (registerUserPassword
									&& userPassword.test(registerUserPassword)) {
								var UserPasswordFlag = true;
								$('.registerUserPasswordMsg').html(rightHTML);
							} else {
								$('.registerUserPasswordMsg').html(faliureHTML);
								var UserPasswordFlag = false;
							}
							if (registerConfirmPassword
									&& registerConfirmPassword === registerUserPassword) {
								var ConfirmPasswordFlag = true;
								$('.registerConfirmPasswordMsg')
										.html(rightHTML);
							} else {
								$('.registerConfirmPasswordMsg').html(
										faliureHTML);
								var ConfirmPasswordFlag = false;
							}
							//验证通过准备提交
							if (UserNameFlag == true && userEmailFlag == true
									&& UserPasswordFlag == true
									&& ConfirmPasswordFlag) {
								$.ajax({
									type : 'post',
									url : 'user/register',
									data : {
										userName : registerUserName,
										userEmail : registerUserEmail,
										userPassword : registerUserPassword
									},
									success : function(data) {
										if(data == "抱歉！此用户名已经被占用！"||data == "抱歉！此邮箱已经被占用！"){
											alert(data);
										}
										else{
											alert(data);
											window.location.href="login.jsp";
										}
									},
									error : function(XMLHttpRequest,
											textStatus, errorThrown) {
										alert('用户注册数据提交失败！');
										console.log(XMLHttpRequest.status);
										console.log(XMLHttpRequest.readyState);
										console.log(textStatus);
									}
								});

							}

						}

						//校验提交用户登录的数据
						function checkStr(name, password) {
							if ((name == '' || name == null)
									|| (password == '' || password == null)) {
								alert('用户名或密码不能为空！');
							} else {
								//向后台提交数据
								$.ajax({
									type : 'post',
									url : 'user/login',
									data : {
										userName : name,
										userPassword : password
									},
									success : function(data) {
										if(data == "账号或密码错误,请重新输入！"){
											alert(data);
											//window.location.href="login.jsp";
										}
										else{
											alert(data);											
											window.location.href="index.jsp";
										}
									},
									error : function(XMLHttpRequest,
											textStatus, errorThrown) {
										alert('用户登录数据提交失败！');
										console.log(XMLHttpRequest.status);
										console.log(XMLHttpRequest.readyState);
										console.log(textStatus);
									}
								});

							}
						}

					});
</script>
</body>
</html>