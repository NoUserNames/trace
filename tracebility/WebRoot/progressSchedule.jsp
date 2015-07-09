<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="pageContent">
	<table class="list" width="100%" LayoutH="30">
 	<thead>
  		<tr>
  			<th width="3%">序号</th>
  			<th width="5%">程序名称</th>
  			<th width="30%">异常描述</th>
  			<th width="30%">状态</th>
  			<th>PDM对应人员</th>
  			<th>需求提出时间</th>
  			<th>需求完成时间</th>
  		</tr>
 	</thead>
 	<tbody>
	<s:if test="#request.list.size() != 0">
  		<s:iterator value="list" id="wip" status="index">
  		<tr>
  		
  			<td>
  				<s:property value="#wip.a"/>
  			</td>
  			<td>
  				<s:property value="#wip.b"/>
  			</td>
  			<td>
  				<s:property value="#wip.c"/>
  			</td>
  			<td>
  				<s:property value="#wip.d"/>
  			</td>
  			<td>
  				<s:property value="#wip.e"/>
  			</td>
  			<td>
  				<s:property value="#wip.f"/>
  			</td>
  			<td>
  				<s:property value="#wip.g"/>
  			</td>
  		</tr>
  		</s:iterator>
  	</s:if>
  	<s:if test="#request.list.size() == 0">
	  	<tr align="center">
			<td colspan="8" style="font-size: 30px">
				<font color="red">
					根据条件没有查到数据
				</font>
				<br/>
			</td>
	  	</tr>
	  	</s:if>
  	</tbody>
  	</table>
</div>
</body>
</html>