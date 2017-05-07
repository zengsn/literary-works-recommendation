<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文学名著搜索与推荐系统</title>
<link rel="stylesheet" href="css/layout.css" />
<!-- 路径是从WebContent文件下开始写的，路径的第一个没有加"/" -->
<script type="text/javascript" src="js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="js/search.js"></script>
<style type="text/css">
div {
	margin-top: 150px;
}

#searchKeywords {
	width: 500px;
	height: 34px;
	margin-left: 400px;
}

#searchButton {
	width: 50px;
	height: 36px;
}
</style>
</head>
<body>
	<center>
		<div>
			<p>文学名著搜索与推荐系统</p>
			<!-- 对应web.xml中的/action/SearchServlet -->
			<!-- input中需要设置name和value才能在Servlet中通过request.getParameter()方法拿到传递过去的值 -->
			<form action="action/SearchServlet" method="get">
				<input type="text" id="searchKeywords" name="searchKeywords"
					class="searchKeywords" value="" placeholder="请输入要搜索的关键字" />
				<input type="text" id="currPageNO" name="currPageNO" value="1" style="display:none"/>
				<input
					type="submit" id="searchButton" class="searchButton" value="搜索" />
			</form>
			<img src="img/11.jpg" alt="无法展示"></img>
		</div>
	</center>

</body>
</html>