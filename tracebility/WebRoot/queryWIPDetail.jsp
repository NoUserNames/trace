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
    
    <title>My JSP 'queryWIPDetail.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <form>
	<table class="table" height="600px" width="100%" layoutH="55">
 	<thead>
  		<tr>
  			<th>序列号</th>
  			<th>作业站</th>
  			<th>最后扫描时间</th>
  			<th>箱号</th>
  			<th>FATP条码</th>
  			<th>作业员</th>
  			<!-- <th>状态</th> -->
  		</tr>
 	</thead>
 	<tbody>
	<s:if test="#request.listWIPDetail.size() != 0">
  		<s:iterator value="listWIPDetail" id="wip" status="index" begin="0" end="detailSize">
  		<tr><!-- <s:if test="#wip.current_status ='报废'">bgcolor="yellow"</s:if><s:if test="#wip.current_status ='不良'">bgcolor="yellow"</s:if> -->
  			<td>
  				<s:property value="#wip.serial_number"/>
  			</td>
  			<td>
  				<s:property value="#wip.terminal_name"/>
  			</td>
  			<td>
  				<s:property value="#wip.out_process_time"/>
  			</td>
  			<td>
  				<s:property value="#wip.carton_no"/>
  			</td>
  			<td>
  				<s:property value="#wip.customer_sn"/>
  			</td>
  			<td>
  				<s:property value="#wip.emp_name"/>
  			</td>
  			<!-- <td>
  				<s:property value="#wip.current_status"/>				
  			</td> -->
  		</tr>
  		</s:iterator>
  	</s:if>
  	<s:if test="#request.listWIPDetail.size() == 0">
	  	<tr align="center">
			<td colspan="5">
				<font color="red">
					根据条件没有查到数据
				</font>
				<br/>
			</td>
	  	</tr>
	  	</s:if>
	  	
  	</tbody>
  	</table>
  	<div class="formBar">
		<ul>
			<li>			
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="download_universal('exp?wipExp=<s:property value="wipExp"/>&expName=${param.label }')">导出数据</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">关闭窗口</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
	</form>
  </body>
</html>