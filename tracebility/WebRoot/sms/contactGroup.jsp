<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通讯录</title>
</head>
<body>
	<div class="panelBar">
		<ul class="toolBar">
		<li class="line">line</li>
		<li><a width="510" height="270" class="add" href="sms/contactGroupAdd.jsp" target="dialog" title="添加新群组" mask="true"><span>点这里添加新群组</span></a></li>
		<li class="line">line</li>
		</ul>
	</div>
	
	<div class="pageContent">
	<form action="initGroup" name="contactGroupForm" onsubmit="return dwzSearch(this,'navTab')" method="get">
		<table class="table" width="100%">
			<thead>
				<tr>
					<th>序号</th>
					<th>群组名称</th>
					<th>群组描述</th>
					<th>创建日期</th>
					<th>操作栏</th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="groups" id="group">
				<tr>
					<td><s:property value="#group.group_id"/></td>
					<td><s:property value="#group.group_name"/></td>
					<td><s:property value="#group.group_desc"/></td>
					<td><s:property value="#group.create_time"/></td>
					<td>
						<a class="button" target="dialog" href="contactEditQuery?groupId=<s:property value="#group.group_id" />"  width="510" height="270" mask="true" ><span>修改</span></a>
						<!-- <a class="button" target="ajaxTodo" href="delContactGroup?groupId=<s:property value="#group.group_id"/>" id="delA" title="确定要删除群组【<s:property value="#group.group_name"/>】吗?此操作不可恢复！"><span>删除</span></a> -->
					</td>
				</tr>
				</s:iterator>
			</tbody>
		</table>
		</form>
		<form id="pagerForm" action="initGroup" method="post">
		 		<input type="hidden" name="pageNum" value="1" />
		 		<input type="hidden" name="numPerPage" value="<s:property value="numPerPage"/>">
		</form>
	</div>
</body>
</html>