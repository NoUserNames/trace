<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询仓库领料记录</title>
<script src="js/jobHandle.js" type="text/javascript"/></script>
</head>
<body>
	<div class="pageHeader">
	   	<form class="pageForm required-validate" onsubmit="return navTabSearch(this)" action="queryFlow" method="post" id="queryFlowForm">
		<div class="searchBar">
			<table class="searchContent" width="100%">
				<tr>
					<td width="60%">
						查询条件：
						<s:textfield alt="请在此输入箱号" cssStyle="width:200px" name="flowValue" id="flowValue" cssClass="required" />
					</td>
					<td>
						<p><font color="blue"><b>查询说明</b></font></p>
						<li>可通过箱号查询</li>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button name="btn" id="btn" type="button" onclick="chkData('flowValue',this.form.id)">检索</button>
							</div>
						</div>
					</li>
					<li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btnReset" type="reset">清空</button></div></div></li>
				</ul>
			</div>
			</div>
		</form>
	</div>
	
	<div class="panelBar">
		<ul class="toolBar">
		<li class="line">line</li>
		<li>
			<a class="icon"  title="导出Excel" <s:if test="#request.listQueryFlow.size() != 0">onclick="download_universal('shippingExp?shippingExp=<s:property value="shippingExp"/>')"</s:if>>
				<span>导出Excel</span>
			</a>
		</li>
		<li class="line">line</li>
			<span>
				共有&nbsp;
					<font color="blue">
						<b>
							<s:if test="#request.listQueryFlow.size() != 0"><s:property value="#request.listQueryFlow.size()"/></s:if>
							<s:else>0</s:else>
						</b>
					</font>
				&nbsp;
				行记录
			</span>
		<li class="line">line</li>
		</ul>
	</div>
	
	<table class="table" width="100%">
		<thead>
			<th>扫描站点</th>
			<th>领料人姓名</th>
			<th>领料人部门</th>
			<th>扫描箱号</th>
			<th>操作类别</th>
			<th>扫描时间</th>
			<th>应用程序</th>
		</thead>
		
		<tbody>
			<s:iterator value="listQueryFlow" id="queryFlow">
			<tr>
				<td><s:property value="#queryFlow.terminal_name"/></td>
				<td><s:property value="#queryFlow.emp_name"/></td>
				<td><s:property value="#queryFlow.dept_name"/></td>
				<td><s:property value="#queryFlow.carton_no"/></td>
				<td><s:property value="#queryFlow.operate_type"/></td>
				<td><s:property value="#queryFlow.update_time"/></td>
				<td><s:property value="#queryFlow.application_name"/></td>
			</tr>
			</s:iterator>
		</tbody>
	</table>
</body>
</html>