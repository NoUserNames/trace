<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>磁铁量测信息查询</title>
<script type="text/javascript" src="js/jobHandle.js"></script>
</head>
<body>
	<div class="pageHeader">
	<form class="pageForm required-validate" onsubmit="return navTabSearch(this)" action="qryAPS" method="post" id="qryAPSForm">
		
		<div class="searchBar">
		<table class="searchContent" width="100%">
			<tr>
				<td width="80%">
					查询方式：<s:select list="#{'carton':'按箱号','timeZone':'按时间'}" cssStyle="width:100"
						headerKey="0" headerValue="-- --" id="queryType" name="queryType" onchange="chgType(this.value)"/>
					<div id="carton">
						查询条件：<s:select list="#{'carton':'单箱查询','cartons':'多箱查询'}" cssStyle="width:100"
						headerKey="0" headerValue="-- --" id="queryType" name="queryType" onchange="cartonType(this.value)"/>
						<div id="singleCarton">
							<s:textfield cssStyle="width:200px" name="queryValue" id="queryValue" cssClass="required" />
						</div>
						<div id="multCarton" style="display: none;">
							<s:file name="cartons_upload"></s:file>
						</div>
					</div>
					<div id="time" style="display: none;">
						选择日期：
						<s:textfield name="timeB" id="timeB" cssClass="required date" dateFmt="yyyy-MM-dd HH" readonly="true" 
						cssStyle="width:100px;" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
						&nbsp;&nbsp;-&nbsp;&nbsp;
						<s:textfield name="timeE" id="timeE" cssClass="required date" dateFmt="yyyy-MM-dd HH" readonly="true" 
						cssStyle="width:100px;" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
					</div>
				</td>
				<td>
					
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

</body>
</html>