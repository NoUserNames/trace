<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>shipping Query</title>
<script src="js/jobHandle.js" type="text/javascript"/></script>
</head>
<body>
	<div class="pageHeader">
	   <form class="pageForm required-validate" onsubmit="return navTabSearch(this)"
	   action="qryShipping" method="post" id="qryShippingForm">
		<div class="searchBar">
		 <table class="searchContent" width="100%">
				<tr>
					<td width="60%">
						查询条件：
						<s:select list="#{'serial_number':'产品序列号','customer_sn':'FATP标签','batchNum':'批次号', 'cartonNum':'箱号','part_no_erp':'ERP料号','part_no_mes':'MES料号'}" 
						headerKey="0" headerValue="-- --" id="queryType" name="queryType" onchange="queryValue.value=''"/>
						<s:textfield cssStyle="width:200px" name="queryValue" id="queryValue" cssClass="required" onclick="$('#queryValue').animate({width:'300px'})" onblur="$('#queryValue').animate({width:'200px'})"></s:textfield>
					</td>
					<!-- <td>
						<fieldset>
						<legend><input type="radio" value="qt_date" id="qt_date" name="qt"/>按时间区段查询</legend>
						选择日期：
						<s:textfield name="timeB" id="timeB" style="width: 110" cssClass="required date" dateFmt="yyyy-MM-dd HH" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
						&nbsp;~&nbsp;
						<s:textfield name="timeE" id="timeE" style="width: 110" cssClass="required date" dateFmt="yyyy-MM-dd HH" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
						</fieldset>
					</td>
					 -->
					<td>
						<p><font color="blue"><b>查询说明</b></font></p>
						<li>可通过产品序列号、FATP标签、批次号、箱号、ERP料号、MES料号查询</li>
						<li>为加快浏览器显示速度，只显示前50条。如要查看所有数据请点击【导出Excel】后查看</li>
					</td>
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
						<li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btnReset" type="reset">清空</button></div></div></li>
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
		<a class="icon"  title="导出Excel" <s:if test="#request.listShipping.size() != 0">onclick="download_universal('shippingExp?shippingExp=<s:property value="shippingExp"/>')"</s:if>>
			<span>导出Excel</span>
		</a>
	</li>
	<li class="line">line</li>
		<span>
			共有&nbsp;
				<font color="blue">
					<b>
						<s:if test="#request.listShipping.size() != 0"><s:property value="#request.listShipping.size()"/></s:if>
						<s:else>0</s:else>
					</b>
				</font>
			&nbsp;
			行记录
		</span>
	<li class="line">line</li>
	</ul>
	</div>
<!-- 数据表格 -->		
	<table class="table" width="100%" layoutH="120">
		<thead>
 		<tr>
 			<th>序号</th>
 			<th>箱号</th>
 			<th>产品二维码</th>
 			<th>出货标签</th>
 			<th>更新时间</th>
 			<th>批次号</th>
 			<th>MES料号</th>
 			<th>FATP标签</th>
 		</tr>
		</thead>
		<tbody>
		<s:if test="#request.listShipping.size() != 0">
		<s:iterator value="listShipping" id="list" status="index" begin="0" end="detailSize">
		<tr align="center">
			<td>
				<s:property value="#index.index+1"/>
			</td>
			<td>
				<s:property value="#list.carton_no"/>
			</td>
			<td>
				<s:property value="#list.serail_number"/>
			</td>
			<td>
				<s:property value="#list.shipping_status"/>
			</td>
			<td>
				<s:property value="#list.update_time"/>
			</td>
			<td>
				<s:property value="#list.batch_no"/>
			</td>
			<td>
				<s:property value="#list.part_no_mes"/>
			</td>
			<td>
				<s:property value="#list.customer_sn"/>
			</td>
		</tr>
		</s:iterator>
		</s:if>
		<s:else>
		<tr align="center">
			<td colspan="7">根据条件没有查到数据</td>
		</tr>
		</s:else>
		</tbody>
	</table>
</body>
</html>