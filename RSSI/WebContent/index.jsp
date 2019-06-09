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
<body>
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
							<p class="white-text" style = "font-family: STKaiti">该网站是基于给出的app的测量得到的指纹库文件以及待测点的文件格式，
							</p>
							<p class="white-text" style = "font-family: STKaiti">完成指纹库文件上传和后处理定位的功能。
							</p>
							<br/><br/>
							<a href="upload.jsp">
								<button class="white-btn"style="width: 240px; height:  50px;border-width: 2px; border-radius: 5px;font-family: STXingkai ;background: #BEBEBE;font-size: 20px">
									指纹库文件上传
								</button>
							</a>
							<a href="calculate.jsp">
								<button class="white-btn" style="width: 240px; height:  50px;border-width: 2px; border-radius: 5px;font-family: STXingkai ;background: #BEBEBE;font-size: 20px">
									待测点计算
								</button>
							</a>
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