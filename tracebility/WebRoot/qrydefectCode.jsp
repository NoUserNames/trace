<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>不良代码-显示</title>
</head>
<body>
    <div class="pageHeader">
	<form id="queryWIPForm" onsubmit="return navTabSearch(this)" action="qryDefectCode" method="post">
	<div class="searchBar">
	<table class="searchContent">
		<tr>
			<td>
			    请选择机种：<!-- listKey="route_id+','+part_no" -->
				<s:select name="partNO" list="listParts" listKey="part_no"
					listValue="'机种：'+model_name+';料号：'+part_no" value="partNO"
					onchange="cascadingProcess(this.form.id,'initDefectCode')"
					headerKey="0" headerValue="--请选择机种--" id="model_id_wip">
				</s:select>
			</td>
			<td>
				请选择制程：
				<s:select list="listProcesses" listKey="process_id" listValue="process_name"
					 value="processID" onchange="p_exp_name.value = this.options[this.selectedIndex].text"
					headerKey="0" name="processID" id="p_cbo_id_wip" headerValue="--请选择制程--">
				</s:select><!-- onchange="cascadingTerminal(this.form.id,'initDefectCode',this.options[this.selectedIndex].text,'p_wip')" -->
				<s:hidden id="p_wip" name="p_exp_name"></s:hidden>
			</td>
			<td><!-- 
				开始日期：<s:textfield size="14" name="timeB" cssClass="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
				&nbsp;~&nbsp;<s:textfield size="14" name="timeE" cssClass="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
				 -->
			</td>
		</tr>
		<!-- 请选择站点：
				<s:select name="terminalID" id="terminalID" list="listTerminals" listKey="terminal_id" listValue="terminal_name" value="terminalID"
					onchange="terminalID.value = this.value" headerKey="0" headerValue="--请选择站点--"/> -->
	</table>
	<div class="subBar">
			<ul>
				<li>
				<div class="buttonActive">
				<div class="buttonContent"><!-- onclick="checkQueryUniversal('model_id_wip',this.form.id,'queryWIP')" -->
					<button name="btn" id="btnQueryWIP" type="submit" >检索</button>
				</div>
				</div>
				</li>
				<li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btn" type="reset">清空</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
	</div>
	
	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<li>
				<a class="icon" title="确实要导出这些记录吗?" <s:if test="listSize > 0">onclick="download_universal('expDCode?dCodeExp=<s:property value="codeExp"/>')"</s:if>><span>导出EXCEL</span></a>
			</li>
			<li class="line">line</li>
			<span>共查到<b><font color="blue"><s:property value="listSize"/></font></b>条记录</span>
			<li class="line">line</li>
			<span>提示：为加快显示速度，只显示前20条记录。查看全部请【导出Excel】后查阅。</span>
			<li class="line">line</li>
		</ul>
	</div>
	
	<div class="pageContent">
	<table class="table" width="100%" LayoutH="110">
 	<thead>
 		<tr>
 			<th>序号</th>
 			<th>英文不良描述</th>
 			<th>中文不良表述</th>
 			<th>不良类型</th>
 			<th>责任归属</th>
 		</tr>
 	</thead>
 	<tbody>
 		<s:iterator value="list" id="list" status="index">
 		<tr>
 			<td><s:property value="#index.index + 1"/></td>
 			<td><s:property value="#list.defect_desc2"/></td>
 			<td><s:property value="#list.defect_desc"/></td>
 			<td><s:property value="#list.defect_type"/></td>
 			<td><s:property value="#list.responsibility_unit"/></td>
 		</tr>
 		</s:iterator>
 	</tbody>
 	</table>
 	</div>
</body>
</html>