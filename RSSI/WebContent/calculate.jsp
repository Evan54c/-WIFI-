<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

	<title>登录</title>

	<!-- Google font -->
	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700%7CVarela+Round" rel="stylesheet">

	<!-- Bootstrap -->
	<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />

	<!-- Owl Carousel -->
	<link type="text/css" rel="stylesheet" href="css/owl.carousel.css" />
	<link type="text/css" rel="stylesheet" href="css/owl.theme.default.css" />

	<!-- Magnific Popup -->
	<link type="text/css" rel="stylesheet" href="css/magnific-popup.css" />

	<!-- Font Awesome Icon -->
	<link rel="stylesheet" href="css/font-awesome.min.css">

	<!-- Custom stlylesheet -->
	<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>
<body><!--   bgcolor="#EEF9FA" style="font-family: STKaiti">
	<h2 style="font-size:35px;height: 50px" >
		<font color="#4A64FB">RSSI指纹库匹配定位数据存储与计算</font>
		<hr style="height:1px;border:none;border-top:2px solid #555555;" />
	</h2>
	<br/>
	<form action="CalculateServlet" method="post" enctype="multipart/form-data" style="font-size:17px">
		待测点对应指纹库文件编号：<input name="no" style="width: 93px; height: 17px;"/>
		<br/><br/>
		上传待测点文件：<input type="file" name="指纹库文件" style="font-family: STKaiti;font-size:15px"/>
		<br/><br/><br/>
		<input type="submit" value="提交" style="width: 240px; height: 30px;border-width: 2px; border-radius: 5px;background:#5FD7ED;cursor: pointer; outline: none; font-family: STXingkai;color:white;font-size: 17px;"/>
	</form>-->

	<header id="home">
		<!-- Background Image -->
		<div class="bg-img" style="background-image: url('img/background1.jpg');">
			<div class="overlay"></div>
		</div>
		<!-- /Background Image -->

		<!-- Nav -->
		<nav id="nav" class="navbar nav-transparent">
			<div class="container">

				<div class="navbar-header">
					<!-- Logo -->
					<div class="navbar-brand">
						<br/>
						<a href="index.jsp" class="white-text"style = "font-family: STKaiti ;font-size: 35px">
							首页
						</a>
					</div>
					<!-- /Logo -->

					<!-- Collapse nav button -->
					<div class="nav-collapse">
						<span></span>
					</div>
					<!-- /Collapse nav button -->
				</div>

				<!--  Main navigation  -->
				<ul class="main-nav nav navbar-nav navbar-right">
					<li><a style = "font-family: STKaiti;font-size: 20px">关于我们</a></li>
				</ul>
				<!-- /Main navigation -->

			</div>
			<hr style="height:1px;border:none;border-top:2px solid #555555;" />
		</nav>
		<!-- /Nav -->
		<!-- home wrapper -->
		<div class="home-wrapper">
			<div class="container">
				<div class="row">

					<!-- home content -->
					<div class="col-md-10 col-md-offset-1">
						<div class="home-content">
							<h1 class="white-text" style="font-family: STKaiti">基于WIFI的RSSI的室内定位</h1>
							<br/><br/>
							<p class="white-text" style = "font-family: STKaiti">待测点计算，该网站的待测点文件依据给定的adroid端app的采集结果。
							</p>
							<br/><br/>
								<div style="width:100%;text-align:center; margin-left: 350px;">
								<form action="CalculateServlet" method="post" enctype="multipart/form-data"  style="width: 240px;text-align:center;font-family: STKaiti; font-size:18px">										
										<p align="left">待测点指纹文件编号：<input name="no" style="width: 60px; height: 25px;" /></p>										
										<p align="left">上传待测点文件：</p>
										<input type="file" name="指纹库文件"   style="vertical-align:middle; font-family: STKaiti;font-size:15px;"/>			 
									<br/><br/>
									<input type="submit" value="提交" style="width: 240px; height: 45px;border-width: 2px; border-radius: 5px;background:#BEBEBE;cursor: pointer; outline: none; font-family: STXingkai;color:black;font-size: 20px;"/>
								</form>
								</div>
						</div>
					</div>
					<!-- /home content -->

				</div>
			</div>
		</div>
		<!-- /home wrapper -->

	</header>
</body>
</html>