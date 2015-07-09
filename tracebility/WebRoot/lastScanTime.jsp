<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>最后扫描时间</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="./css/styles.css">
	<link href="themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
	<link href="uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
-->
	<!--[if IE]>
	<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
	<![endif]-->
	
	<!--[if lte IE 9]>
	<script src="js/speedup.js" type="text/javascript"></script>
	<![endif]-->
	<script src="js/jobHandle.js" type="text/javascript"></script>
  </head>
  
  <body>

<div class="pageHeader">   
<form onsubmit="return navTabSearch(this);" action="lastScanTime" method="post">
<div class="searchBar">
 <table class="searchContent">
		<tr>
			<td>
				开始日期：<input name="start" class="date"  readonly="readonly"/>
			</td>
			<td>
				结束日期：<input name="end" class="date" readonly="readonly" />
			</td>
		</tr>
	</table>
	<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<!-- <li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li> -->
			</ul>
		</div>
		</div>
</form>	
</div>	
  
  <div class="pageContent">
  
	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li><!-- target="dwzExport"  -->
			<li><a class="icon"  title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li><!-- targetType="navTab" -->
		</ul>
	</div>
  	<table class="table" width="100%" layoutH="110">
  		<thead>
	  		<tr>
	  			<th>序号</th>
	  			<th>站点ID</th>
	  			<th>站点名称</th>
	  			<th>最后扫描时间</th>
	  		</tr>
  		</thead>
  		<tbody>
  		<s:iterator value="da" id="device" status="index">
  		<tr>
  			<td>
  				${index.index +1 }
  			</td>
  			<td>
  				<s:property value="#device.terminalID"/>
  			</td>
  			<td>
  				<s:property value="#device.terminalName"/>
  			</td>
  			<td>
  				<s:property value="#device.lasttime"/>
  			</td>
  		</tr>
  		</s:iterator>
  		</tbody>
  	</table>
  	</div>  
  </body>
</html>