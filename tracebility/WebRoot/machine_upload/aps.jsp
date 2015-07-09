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
	<div class="pageHeader">
	   <form class="pageForm required-validate" onsubmit="return navTabSearch(this)" action="qryAPS" method="post" id="qryAPSForm">
		<div class="searchBar">
		 <table class="searchContent">
				<tr>
					<td>
						查询条件：<s:select list="#{'serial_number':'产品序列号','customer_sn':'FATP标签'}" 
						headerKey="0" headerValue="-- --" id="queryType" name="queryType" onchange="queryValue.value=''"/>
						<s:textfield cssStyle="width:200px" name="queryValue" id="queryValue" 
						cssClass="required" onclick="$('#queryValue').animate({width:'300px'})" 
						onblur="$('#queryValue').animate({width:'200px'})"/>
					</td>
					<td>
						
					</td>
					<!--<td>
						选择时间：
						<input style="width: 110" class="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="readonly"/>
						&nbsp;~&nbsp;
						<input style="width: 110" class="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="readonly"/>
					</td>-->
				</tr>
			</table>
			<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button name="btn" id="btn" type="button" onclick="shippingQry(this.form.id)">检索</button>
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
		</form>
	</div>
	<div class="panelBar">
	<ul class="toolBar">
	<li class="line">line</li>
	<li>
		<a class="icon"  title="导出Excel" <s:if test="#request.listAPS.size() != 0">onclick="download_universal('apsExp.action?apsExp=<s:property value="APSExp"/>')"</s:if>>
			<span>导出Excel</span>
		</a>
	</li>
	<li class="line">line</li>
	<span>
		共有&nbsp;<font color="blue"><b>
		<s:property value="#request.listAPS.size()"/>
		</b></font>&nbsp;条记录	
	</span>
	
	<li class="line">line</li>
	</ul>
	</div>
	
	<div layoutH="210" class="pageContent">
	<table class="list" width="100%">
		<thead>
 		<tr>
 			<th>机种信息</th>
 			<th>产品序列号</th>
 			<th>颜色</th>
 			<th>BIN</th>
 			<th>FATP条码</th>
 			<th>原材供应商编码</th>
 			<th>CELL号</th>
 		</tr>
		</thead>
		<tbody>	
		<s:if test="#request.listAPS.size() != 0">
	  		<s:iterator value="listAPS" id="p" status="index">
	  		<tr>
	  			<td>
	  				<s:property value="#p.model_name"/>
	  			</td>
	  			<td>
	  				<s:property value="#p.serialnumber"/>
	  			</td>
	  			<td>
	  				<s:property value="#p.color"/>
	  			</td>
	  			<td>
	  				<s:property value="#p.binorng"/>
	  			</td>
	  			<td>
	  				<s:property value="#p.customer_sn"/>
	  			</td>
	  			<td>
	  				<s:property value="#p.supplier_sn"/>
	  			</td>
	  			<td>
	  				<s:property value="#p.dot_value"/>
	  			</td>
	  		</tr>
	  		</s:iterator>
	  	</s:if>
	  	<s:if test="#request.listAPS.size() == 0">
	  		<tr align="center">
	  			<td colspan="7">
	  				<font color="blue">
	  					<b>根据条件没有查到数据</b></font>
	  			</td>
	  		</tr>
	  	</s:if>
	  	</tbody>
	  	</table>
	</div>
</body>
</html>