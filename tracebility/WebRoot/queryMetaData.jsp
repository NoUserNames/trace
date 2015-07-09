<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>表格数据显示</title>
	
	<script type="text/javascript" src="js/jobHandle.js"></script>
</head>
<body>
    <div class="pageHeader">
	<s:form action="qryMetaData" method="post" onsubmit="return navTabSearch(this)">
	<div class="searchBar">
	<table class="searchContent">
		<tr>
			<!-- <td>
			   	排序方式：
			</td> -->
			<td>
			   	表名：
			</td>
			<!-- <td>
				记录数：
			</td> -->
			<td>
				列名：
			</td>
			<td>
				值：
			</td>
			<td>
				参数说明：
			</td>
		</tr>
		<tr>
			<!-- <td valign="top">
				<s:select list="#{'esc':'升序','desc':'降序'}" name="sort" />
			</td> -->
			<td valign="top">
				<s:textfield name="tableName" id="tableName"></s:textfield>
			</td>
			<!-- <td valign="top">
				<s:textfield name="rowNum" size="1"></s:textfield>
			</td> -->
			<td valign="top">
				<s:select headerKey="0" headerValue="--字段清单--" multiple="false" name="columns" list="list" />
			</td>
			<td valign="top">
				<s:textfield name="value"></s:textfield>
			</td>
			<td valign="top">
				<li>首次加载是查询表的字段信息</li><br/>
				<li>再次点击查询是显示表信息</li><br/>
				<li>记录数是填写要显示的行数</li><br/>
			</td>
		</tr>
	</table>
	<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button name="btn" id="btnQueryWIP" type="button" onclick="chkMetaData('tableName',this.form.id)">查询数据</button><!--  -->
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button name="btn" id="btn" type="reset">清空</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</s:form>
	</div>
	<table class="list" width="100%" layoutH="150">
		<thead>
			<tr>
				<s:iterator value="list" id="test">
					<th><s:property value="test"/></th>
				</s:iterator>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="listValue" id="column">
			<tr>
			<s:iterator value="column" id="c">
				<td><s:property value="c"/></td>
			</s:iterator>
			</tr>
			</s:iterator>
		</tbody>
	</table>
</body>
</html>