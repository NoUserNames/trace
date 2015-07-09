<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>wip query</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<script type="text/javascript" src="js/jobHandle.js"></script>
  </head>
  
  <body>
    <div class="pageHeader">
	<form id="queryWIPForm" onsubmit="return navTabSearch(this)" action="queryWIP" method="post">
	<div class="searchBar">
	<table class="searchContent">
		<tr>
			<td>
			    请选择机种：<!-- listKey="route_id+','+part_no" -->
				<s:select name="partWIP" list="listParts" listKey="part_no"
					listValue="'机种：'+model_name+';料号：'+part_no"
					onchange="cascadingProcess(this.form.id,'initQueryWIP')"
					headerKey="0" headerValue="--请选择机种--" id="model_id_wip">
				</s:select>
			</td>
			<td>
				请选择制程：
				<s:select list="listProcesses" listKey="process_id" listValue="process_name"
					onchange="cascadingTerminal(this.form.id,'initQueryWIP',this.options[this.selectedIndex].text,'p_wip')" 
					headerKey="0" name="processWIP" id="p_cbo_id_wip" headerValue="--请选择制程--">
				</s:select>
				<s:hidden id="p_wip" name="p_exp_name"></s:hidden>
			</td>
			<td>
				开始日期：<s:textfield name="timeB" cssClass="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
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
				<s:select name="terminalWIP" id="terminalID" list="listTerminals" listKey="terminal_id" listValue="terminal_name" 
					onchange="terminalID.value = this.value" headerKey="0" headerValue="--请选择站点--"/>
			</td>
			<td>
				结束日期：<s:textfield name="timeE" cssClass="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>	
			</td>
		</tr>
	</table>
	<div class="subBar">
			<ul>
				<li>
				<div class="buttonActive">
				<div class="buttonContent">
					<button name="btn" id="btnQueryWIP" type="button" onclick="checkQueryUniversal('model_id_wip',this.form.id,'queryWIP')">检索</button>
				</div>
				</div>
				</li>
				<li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btn" type="reset">清空</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
	</div>
	<!-- 
	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<li>
				<a class="icon" title="确实要导出这些记录吗?" onclick="exp()"><span>导出EXCEL</span></a>
			</li>
		</ul>
		<ul class="toolBar">
			<li>
				<div>为加快显示速度，只显示前20条数据。需要查看所有数据请点击【导出数据】下载后查看。</div>
			</li>
		</ul>
	</div>
	  -->
	
	<div class="pageContent">
	<table class="table" width="100%" LayoutH="110">
 	<thead>
  		<tr>
  			<th>行号</th>
  			<th>
  			<c:choose>
	  			<c:when test="${requestScope.flag ne 3}">制程名</c:when>
	  			<c:otherwise>站点</c:otherwise>
  			</c:choose>
  			</th>
  			<th>滞留数</th>
  			<th>不良数</th>
  			<th>不良率(%)</th>
  			<th>报废数</th>
  			<th>报废率(%)</th>
  		</tr>
 	</thead>
 	<tbody>
	<s:if test="#request.listWIP.size() != 0">
  		<s:iterator value="listWIP" id="wip" status="index">
  		<tr>
  			<td width="30px">${index.index+1 }</td>
  			<td width="350px">
  				<a width="800" height="540"
  					<s:if test="#wip.iwipcount > 20">title="<s:property value="#wip.lableName"/>-共有<s:property value="#wip.iwipcount"/>条记录，窗口只显示前20条。查看所有数据【导出数据】后查看。"</s:if> mask="true" 
  					<s:if test="#wip.iwipcount !=0 "> href="queryWIPDetail?p_id=<s:property value='#wip.p_id'/>&t_id=<s:property value='#wip.t_id'/>&label=<s:property value='#wip.lableName'/>&part_no=${requestScope.partNO }&timeB=<s:property value="timeB"/>&timeE=<s:property value="timeE"/>" rel="dlg_page<s:property value="#index.index+1"/>" target="dialog" </s:if>>
  					<s:property value="#wip.lableName"/>
  				</a>
  			</td>
  			<td width="60px">
  				<s:property value="#wip.iwipcount"/>
  			</td>
  			<td width="60px">
  				<s:property value="#wip.ingcount"/>
  			</td>
  			<td>
  				<div style="width:100%;border:0px solid #ccc" ><!--  -->
                     <div style="width:<s:property value="%{formatDouble(#wip.ngrate)}"/>%;background:yellow;color:black">
                     	<s:property value="%{formatDouble(#wip.ngrate)}"/>%
                     </div>
                </div>
  			</td>
  			<td width="60px">
  				<s:property value="#wip.iscarpcount"/>
  			</td>
  			<td>
  				<div style="width:100%;border:0px solid #ccc" ><!--  -->
                     <div style="width:<s:property value="%{formatDouble(#wip.srate)}"/>%;background:#cc0000;color:#fff">
                     	<s:property value="%{formatDouble(#wip.srate)}"/>%
                     </div>
                </div>
  			</td>
  		</tr>
  		</s:iterator>
  	</s:if>
  	<s:if test="#request.listWIP.size() == 0">
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