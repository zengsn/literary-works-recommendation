<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>数据网格</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="themes/icon.css" type="text/css"></link>

<!-- 有顺序 -->
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>
</head>

<body>
<!-- 输入区 -->
<form>
	请输入关键字：<input id="keywordID" type="text"><input id="find" type="button" value="站内搜索"> 
</form>
<script type="text/javascript">
	$("#find").click(function(){
		var keyword = $("#keywordID").val();
		keyword = $.trim(keyword);
		if(keyword.length==0){
			alert("请输入关键字");
			$("#keywordID").focus();
			$("#keywordID").val("");
		}else{
			//异步发送请求到服务器
			//load表示方法名
			//{}中的是要传递的数据
			$('#dg').datagrid('load', {    
    			keyword: keyword    
			});  
		}
	});
	
</script>

<!-- 显示区 -->
<table id="dg"></table>
	<script type="text/javascript">
		$("#dg").datagrid({
			url:'${pageContext.request.contextPath}/LiteraServlet?time='+new Date().getTime(),
			style:'width:800px;height:250px',
			columns:[[    
       			{field:'para',title:'段落',width:100},    
        		{field:'name',title:'书名',width:10},    
        		{field:'author',title:'作者',width:10}, 
        		{field:'chaptername',title:'章节标题',width:10} 
    		]],
			fitColumns:true,
			singleSelect:true,
			pagination:true,
			pageSize:15,
			pageList:[15]   
		});
	</script>
</body>
</html>
