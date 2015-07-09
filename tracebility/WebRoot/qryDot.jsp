<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CNC打点信息查询</title>
</head>
<body>
<div class="pageHeader">
   <form class="pageForm required-validate" onsubmit="return navTabSearch(this)" action="dotQry" method="post" id="myForm">
	<div class="searchBar">
	 <table class="searchContent">
			<tr>
				<td>
					产品序列号：<s:textfield name="serial_number" cssClass="required" id="serial_travel"></s:textfield>						
				</td>
				<td>
					<div id="serial_validata" style="color: red;display: none;">请输入序号</div>
				</td>
			</tr>
		</table>
		<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button name="btn" id="btn" type="submit" >检索</button><!-- onclick="sub_serial()" -->
							</div>
						</div>
					</li>
					<li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btnReset" type="reset">清空</button></div></div></li>
				</ul>
			</div>
		</div>
	</form>
</div>

<table class="table" width="100%" LayoutH="110">
 	<thead>
  		<tr>
  			<th>产品序列号</th>
  			<th>CELL号</th>
  			<th>时间</th>
  			<th>CCD机台号</th>
  		</tr>
 	</thead>
 	<tbody>
	<s:if test="#request.CNCMap.size() != 0">
  		<tr align="center">
  			<td>
  				<s:property value="CNCMap.serial_number"/>
  			</td>
  			<td>
  				<s:property value="CNCMap.dot_value"/>
  			</td>
  			<td>
  				<s:property value="CNCMap.update_time"/>
  			</td>
  			<td>
  				<s:property value="CNCMap.machine_name"/>
  			</td>
  		</tr>
  	</s:if>
  	<s:if test="#request.CNCMap.size() == 0">
	  	<tr align="center">
			<td colspan="4" style="font-size: 30px">
				<font color="red">
					根据条件没有查到数据
				</font>
				<br/>
			</td>
	  	</tr>
	  	</s:if>
  	</tbody>
</table>
</body>
</html>