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
    <title>My JSP 'queryDefectByProcess.jsp' starting page</title>
    
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
	<form id="defectByProcessForm" class="pageForm required-validate" onsubmit="return navTabSearch(this)" action="queryDefectsMaps" method="post">
	<div class="searchBar">
	<table class="searchContent">
		<tr>
			<td>
			    请选择机种：
				<s:select list="listParts" id="partNO" name="partNO" listKey="part_no" listValue="'机种:'+model_name+';料号:'+part_no"
				headerKey="0" headerValue="--请选择机种--"
				onchange="cascadingProcess(this.form.id,'initQryDefect')"/>
			</td>
			<td>
			请选择制程：
				<s:select list="listProcesses" listKey="process_id" listValue="process_name" value="processID"
					onchange="cascadingTerminal(this.form.id,'initQryDefect',this.options[this.selectedIndex].text,'p_def_process')"
					headerKey="0" name="processID" id="p_cbo_id_wip" headerValue="--请选择制程--">
				</s:select>
				<s:hidden id="p_def_process" name="p_exp_name"></s:hidden>
			</td>
			<td >
			开始日期：<s:textfield name="timeB" cssClass="required date" readonly="true" dateFmt="yyyy-MM-dd HH:mm" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
			</td>
		</tr>
		<tr>
			<td>
				<div style="color: blue">
					<li>下拉列表中的数据包含机种、料号</li>
					<li>如果不选日期，默认查当天0点-24点数据</li>
				</div> 
			</td>
			<td>
				请选择站点：
				<s:select name="terminalID" list="listTerminals" listKey="terminal_id" listValue="terminal_name" value="terminalID"
					id="terminalID" onchange="terminalID.value = this.value" headerKey="0" headerValue="--请选择站点--"/>
			</td>
			<td>
				结束日期：<s:textfield name="timeE" cssClass="required date" readonly="true" dateFmt="yyyy-MM-dd HH:mm" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
			</td>
		</tr>
	</table>

	<div class="subBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button name="btn" id="btnQueryDefectByProcess" type="submit" >检索</button>
						</div><!-- onclick="checkQueryDefectByProcess('modelName','p_queryDefectByProcess',this.form.id)" -->
					</div>
				</li>
				<li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btn" type="reset">清空</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btn" type="reset" disabled="disabled">打印</button></div></div></li>
			</ul>
		</div>
	</div>
	
	</form>
	</div>
	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<li>
				<a class="icon" title="确实要导出这些记录吗?" <s:if test="#request.listDefectsMaps.size() != 0">onclick="download_universal('exprotDefects?defectP=<s:property value="defectP"/>')"</s:if>><span>导出EXCEL</span></a>
			</li>
		</ul>
		<ul>
			<li>
				<s:if test="#request.listDefectsMaps.size() != 0">
					<span style="color:blue">共<b><s:property value="#request.listDefectsMaps.size()"/></b>条。
					为加快显示速度，只显示前20条。需要查看所有数据请点击【导出Excel】下载后查看</span>
				</s:if>
			</li>
		</ul>
	</div>
	<div class="pageContent">
	<table class="table" width="100%" LayoutH="135"><!-- 140 -->
 	<thead>
  		<tr>
  			<th>序号</th>
  			<th>产品序列号</th>
  			<th>中文不良描述</th>
  			<th>英文不良描述</th>
  			<th>不良判定时间</th>
  			<th>判定状态</th>
  			<th>判定人员</th>
  			<th>责任归属</th>
  		</tr>
 	</thead>
 	<tbody>
	<s:if test="#request.listDefectsMaps.size() != 0">
  		<s:iterator value="listDefectsMaps" id="defect" status="index" begin="0" end="detailSize">
  		<tr>
  			<td>${index.index+1 }</td>
  			<td>
  				<s:property value="#defect.serial_number"/>
  			</td>
  			<td>
  				<s:property value="#defect.defect_desc"/>
  			</td>
  			<td>
  				<s:property value="#defect.defect_desc2"/>
  			</td>
  			<td>
  				<s:property value="#defect.rec_time"/>
  			</td>	  			 	  			
  			<td>
  				<s:property value="#defect.work_flag"/>
  			</td>
  			<td>
  				<s:property value="#defect.emp_name"/>
  			</td>
  			<td>
  				<s:property value="#defect.responsibility_unit"/>
  			</td>
  		</tr>
  		</s:iterator>
  	</s:if>
  	<s:if test="#request.listDefectsMaps.size() == 0">
	  	<tr align="center">
			<td colspan="5" style="font-size: 30px">
				<font color="red">
					根据条件没有查到数据
				</font>
				<br/>
			</td>
	  	</tr>
	  	</s:if>
  	</tbody>
  	</table>
	<!-- 分页栏 
	<form id="pagerForm" action="queryDefectsMaps" method="post">
 	<div class="panelBar">
 		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="${30}" />
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共
			<s:if test="#request.listDefectsMaps.size() != 0">
				<s:property value="#request.listDefectsMaps.size()"/>
			</s:if>
			<s:else>0</s:else>
			条</span>
			 <span>共${totalPage }页</span>
		</div>		
		<div class="pagination" targetType="navTab" totalCount="${requestScope.totalCount}" numPerPage="${requestScope.pageSize}" pageNumShown="${totalPage }" currentPage="${currentPage}"></div>
	</div>
	</form> -->
  </div>
  </body>
</html>