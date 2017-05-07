<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.entity.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索结果页</title>
<link rel="stylesheet" href="../css/layout.css" />
<script type="text/javascript" src="../js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="../js/search.js"></script>
<style type="text/css">
#searchKeywords {
	width: 300px;
	height: 30px;
	margin-left: 500px;
}

#searchButton {
	width: 50px;
	height: 32px;
}

strong {
	color: red;
}
p {
	text-indent:2em;
	font-size:16px;
	font-family:"微软雅黑";
	font-weight:normal;
}
</style>
</head>
<body>
	<c:set var="searchKeywords" value="${paramMap.searchKeywords }"></c:set>
	<c:set var="page" value="${paramMap.page }"></c:set>
	<div style="margin-top: 30px;">

		<form action="SearchServlet" method="get">
			<input type="text" id="searchKeywords" name="searchKeywords"
				class="searchKeywords" value="${searchKeywords}"
				placeholder="请输入要搜索的关键字" /> <input type="text" id="currPageNO"
				name="currPageNO" value="1" style="display: none" /> <input
				type="submit" id="searchButton" class="searchButton" value="搜索" />
		</form>
	</div>
	<div style="position: absolute; margin-left: 300px; margin-top: 30px; width: 750px">
		<h4>
			一共搜索到<strong>${page.totalRecords }</strong>个结果
		</h4>
		<hr style="margin-top: -10px;" />
		
		<c:if test="${page.totalRecords>0 }">
			<c:forEach items="${page.paraList}" var="para">
				<div style="font-size:17px;">
					<div>${para.author}&nbsp;&nbsp;&nbsp;&nbsp;书名：${para.name}&nbsp;&nbsp;&nbsp;&nbsp;标题：${para.title }</div>
				</div>
				<p>${para.para}</p><br />
			</c:forEach>
		</c:if>
		
		<div style="margin-left: 250px; margin-top: 10px;">
			<c:set var="page" scope="session" value="${page}" />
			共有<font color="blue">${page.totalPageNO }</font>页&nbsp;&nbsp; 第<font color="blue">${page.currPageNO }</font>页&nbsp;&nbsp;
			<c:url var="url_pre" value="SearchServlet">
				<c:choose>
					<c:when test="${(page.currPageNO-1)<1}">
						<c:param name="currPageNO" value="1"></c:param>
					</c:when>
					<c:otherwise>
						<c:param name="currPageNO" value="${page.currPageNO-1}"></c:param>
					</c:otherwise>
				</c:choose>
				<c:param name="searchKeywords" value="${searchKeywords}"></c:param>
			</c:url>

			<c:url var="url_next" value="SearchServlet">
				<c:choose>
					<c:when test="${(page.currPageNO+1)>page.totalPageNO}">
						<c:param name="currPageNO" value="${page.totalPageNO}"></c:param>
					</c:when>
					<c:otherwise>
						<c:param name="currPageNO" value="${page.currPageNO+1}"></c:param>
					</c:otherwise>
				</c:choose>
				<c:param name="searchKeywords" value="${searchKeywords}"></c:param>
			</c:url>
			<a href="${url_pre}">上一页</a> <a href="${url_next}">下一页</a>

		</div>

	</div>

</body>

</html>