<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>漏扫查询</title>
    
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
		<form id="queryMissForm" onsubmit="return navTabSearch(this)" action="missScan" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td> 
					    请选择机种：
						<s:select name="partNO" list="listParts" listKey="part_no"
							listValue="'机种：'+model_name+';料号：'+part_no" value="partNO"
							onchange="cascadingProcess(this.form.id,'initMissScanQry')"
							headerKey="0" headerValue="--请选择机种--" id="model_id_miss">
						</s:select>
					</td>
					<td colspan="2">
						请选择制程：
						<s:select list="listProcesses" id="p_cbo_id_miss" listKey="process_id" listValue="process_name"
						   onchange="p_exp_name.value = this.options[this.selectedIndex].text" headerKey="0" name="process" headerValue="--请选择制程--">
						</s:select><!-- onchange="cascadingTerminal(this.form.id,'initMissScanQry')" -->
						&nbsp;&nbsp;<s:hidden id="p_wip" name="p_exp_name"></s:hidden>
						
					</td>
				</tr>
				<tr>
					<td>
						<s:checkbox title="是否带过站记录?" id="travel_falg" name="travel_falg">是否查漏扫历史</s:checkbox>
						<font color="blue">提示：勾选表示查询漏扫历史记录，速度较慢。</font>
					</td>
					<td>
						开始日期：<s:textfield name="timeB" cssClass="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
						&nbsp;~&nbsp;
						结束日期：<s:textfield name="timeE" cssClass="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
					</td>
				</tr>
			</table>
		
			<div class="subBar">
				<ul>
					<li>
					<div class="buttonActive">
					<div class="buttonContent">
						<button name="btn" id="btnQueryDefectByProcess" type="button" onclick="checkQueryMissScan('model_id_miss','p_cbo_id_miss',this.form.id)">检索</button>
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
				<a class="icon" title="导出Excel" <s:if test="#request.listMaps.size != 0">onclick="download_universal('missScanExp?missExp=<s:property value="miss"/>')"</s:if>>
					<span>导出Excel</span>
				</a>
			</li>
			<li class="line">line</li>
				<span>
					共有&nbsp;<font color="blue"><b><s:property value="listMapsSize"/></b></font>&nbsp;条记录	
				</span>
			<li class="line">line</li>
			<span>
				提示：为加快显示速度，只显示前20条记录。查看全部请【导出Excel】后查阅。
			</span>
			<li class="line">line</li>
		</ul>
	</div>
	
	<TABLE class="table" width="100%" layoutH="138">
	<THEAD>
		<TR>
			<th>序号</th>
			<TH>记录制程</TH>
			<TH>记录站点</TH>
			<TH>产品序列号</TH>
			<TH>记录描述</TH>
			<TH>记录时间</TH>
			<TH>箱号</TH>
			<TH>最后扫描时间</TH>
			<!-- <TH>作业员工号</TH> -->
			<TH>操作员</TH>
		</TR>
	</THEAD>
	<TBODY>
	<s:if test="#request.listMaps.size != 0">
	<s:iterator value="listMaps" id="maps" status="index">
		<tr>
			<td>
				<s:property value="#index.index +1"/>
			</td>
			<td>
				<s:property value="#maps.process_name"/>
			</td>
			<td>
				<s:property value="#maps.terminal_name"/>
			</td>
			<td>
				<s:property value="#maps.trev_type"/>
			</td>
			<td>
				<s:property value="#maps.trev"/>
			</td>
			<td>
				<s:property value="#maps.errTime"/>
			</td>
			<td>
				<s:property value="#maps.carton_no"/>
			</td>
			<td>
				<s:property value="#maps.out_process_time"/>
			</td>
			<!-- <td>
				<s:property value="#maps.emp_no"/>
			</td> -->
			<td>
				<s:property value="#maps.emp_name"/>
			</td>
		</tr>
	</s:iterator>
	</s:if>
	<s:else>
		<tr align="center">
			<td colspan="8"><font color="red">根据条件没有查到数据</font></td>
		</tr>
	</s:else>
	</TBODY>
	</TABLE>
  </body>
</html>