<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>queryProcessOutput</title>
<script type="text/javascript" src="js/jobHandle.js"></script>
</head>
<body>
<!-- 搜索区域 -->
   	<div class="pageHeader">
	<form id="queryProcessOutputForm" onsubmit="return navTabSearch(this)" action="qryProcessOutput" method="post">
	<div class="searchBar">
	<table class="searchContent" width="100%">
		<tr>
			<td width="20%">
				选择机种：<s:select name="partNO" list="listParts" listKey="part_no" 
				listValue="'机种：'+model_name+';料号：'+part_no" value="partNO" id="model_id_output"
				onchange="cascadingProcess(this.form.id,'initQryProcessOutput')"
				headerKey="0" headerValue="--请选择机种--"></s:select>
			</td>
			<td>
				选择制程：
				<s:select list="listProcesses" listKey="process_id" listValue="process_name" value="processID"
					onchange="cascadingTerminal(this.form.id,'initQryProcessOutput')"
					headerKey="0" name="processID" id="p_cbo_id_wip" headerValue="--请选择制程--">
				</s:select>
			</td>
			<td width="20%">
				<div style="color: blue;">
				<b>
				<li>如果不选制程，默认按机种查询，以制程、工单类型分组</li>
				<li>如果不选日期，默认查当天0点-24点数据</li>
				</b>
				</div>
			</td>
		</tr>
		<tr>
			<td>
			选择日期：
			<s:textfield name="timeB" style="width: 110" cssClass="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
			&nbsp;~&nbsp;
			<s:textfield name="timeE" style="width: 110" cssClass="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
			</td>
			<td colspan="2">
				选择站点：
				<input type="hidden" value="<s:property value="terminalID"/>">
				<s:select name="terminalID" list="listTerminals" listKey="terminal_id" listValue="terminal_name" value="terminalID"
					id="terminalID" onchange="terminalID.value = this.value" headerKey="0" headerValue="--请选择站点--"/>
			</td>
		</tr>
	</table>
	<div class="subBar">
			<ul>
				<li>
				<div class="buttonActive">
				<div class="buttonContent">
					<button name="btn" id="btnQueryWIP" type="button" onclick="checkQueryUniversal('model_id_output',this.form.id,'qryProcessOutput')">检索</button>
				</div>
				</div>
				</li>
				<!-- <li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btn" type="reset">清空</button></div></div></li> -->
			</ul>
		</div>
	</div>
	</form>
	</div>
<!-- 总数显示栏位 -->	
	<div class="panelBar">
	<ul class="toolBar">
	<li class="line">line</li>
	<li>
		<a class="icon"  title="导出Excel" <s:if test="#request.listResult.size() != 0">onclick="download_universal('expProcessOutput?files=<s:property value="files"/>')"</s:if>>
			<span>导出Excel</span>
		</a>
	</li>
	<li class="line">line</li>
	<span>
		共有&nbsp;
			<font color="blue">
				<b>
					<s:if test="#request.listResult.size() != 0"><s:property value="#request.listResult.size()"/></s:if>
					<s:else>0</s:else>
				</b>
			</font>
		&nbsp;
		行记录
	</span>
	<li class="line">line</li>
	<span>
		提示：查看明细是查看当前工单类别的明细
	</span>
	<li class="line">line</li>
	</ul>
	</div>
<!-- 数据表格 -->		
	<table class="table" width="100%" layoutH="140">
		<thead>
 		<tr>
 			<th>序号</th>
 			<th>制程</th>
 			<th>工单类型</th>
 			<th>总投入数</th>
 			<th>过站时OK数</th>
 			<th>过站时NG数</th>
 			<th>途程序号</th>
 			<th>操作</th>
 		</tr>
		</thead>
		<tbody>
		<s:if test="#request.listResult.size() != 0">
		<s:iterator value="listResult" id="list" status="index">
		<tr align="center">
			<td>
				<s:property value="#index.index+1"/>
			</td>
			<td align="left">
				<s:property value="#list.proname"/>
			</td>
			<td>
				<s:property value="#list.wo_type"/>
			</td>
			<td>
				<s:property value="#list.cnt"/>
			</td>
			<td>
				<s:property value="#list.ok"/>
			</td>
			<td>
				<s:property value="#list.ng"/>
			</td>
			<td>
				<s:property value="#list.seq"/>
			</td>
			<td>
				<a class="button" title="<s:property value="#list.proname"/>-<s:property value="#list.wo_type"/>" max="true" href="qryProcessOutputDetail?p=<s:property value="#list.process_id"/>&t=<s:property value="terminalID"/>&partNO=<s:property value="partNO"/>&wtype=<s:property value="wo_type"/>" target="dialog" rel="dlg_page<s:property value="#index.index+1"/>" fresh="false"><span>查看明细</span></a>
			</td>
		</tr>
		</s:iterator>
		</s:if>
		
		<s:else>
		<tr align="center">
			<td colspan="8">根据条件没有查到数据</td>
		</tr>
		</s:else>
		</tbody>
	</table>
</body>
</html>