<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'queryDefectBySN.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jobHandle.js"></script>
  </head>
  
  <body>
	<div class="pageHeader">
	   <form action="queryDefectBySN" method="post" id="queryDefectBySNForm" onsubmit="return navTabSearch(this)">
		<div class="searchBar">
		 <table class="searchContent">
				<tr>
					<td>
						二维码：<s:textfield name="serial" cssClass="required" id="serial_DefectBySN"></s:textfield>						
					</td>
					<td>
						<div id="serial_validata" style="color: red;display: none;">请输入序号</div>
					</td>
				</tr>
		 </table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button name="btn" id="btnDefectBySN" type="button" onclick="checkRequired('serial_DefectBySN',this.form.id,'二维码')">检索</button>
						</div>
					</div>
				</li>
				<li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btnReset" type="reset">清空</button></div></div></li>
				<!-- <li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btn" type="reset" disabled="disabled">打印</button></div></div></li> -->
			</ul>
		</div>
		</div>
		</form>
	</div>
	<!-- 工具栏 -->
	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<li>
				<a class="icon" title="确实要导出这些记录吗?" onclick="download_universal('exprotDefectsBySN?defectExp=<s:property value="defectExpName"/>')">
					<span>导出EXCEL</span>
				</a>
			</li>
		</ul>
		<ul>
			<li>
				<s:if test="#request.listDefectsMaps.size() != 0">
				共<b><s:property value="#request.listDefectsMaps.size()"/></b>条
				</s:if>
			</li>
		</ul>
	</div>
	
	<table class="list" width="100%" >
		<thead>
		<tr>
			<th>判定不良制程</th>
			<th>判定不良站点</th>
			<th>判定时间</th>
			<th>中文不良描述</th>
			<th>英文不良描述</th>
			<th>判定人员</th>
			<th>责任归属</th>
		</tr>
		</thead>
		<tbody>
		<s:if test="#request.listDefectsMaps.size() != 0">
		<s:iterator value="listDefectsMaps" id="fixdata">
		<tr>
			<td><s:property value="#fixdata.process_name"/></td>
			<td><s:property value="#fixdata.terminal_name"/></td>
			<td><s:property value="#fixdata.rec_time"/></td>
			<td><s:property value="#fixdata.defect_desc"/></td>
			<td><s:property value="#fixdata.defect_desc2"/></td>
			<td><s:property value="#fixdata.emp_name"/></td>
			<td><s:property value="#fixdata.responsibility_unit"/></td>
		</tr>
		</s:iterator>
		</s:if>
		<s:if test="#request.listDefectsMaps.size() == 0">
		<tr align="center">
			<td colspan="6" >
				<font color="blue"><b>根据条件没有查到数据</b></font>
			</td>
		</tr>
		</s:if>
		</tbody>
	</table>
  </body>
</html>