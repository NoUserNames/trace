<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>镭雕品质查询</title>
</head>
<body>
    <div class="pageHeader">
		<form id="queryMissForm" onsubmit="return navTabSearch(this)" action="qryLaserQuality" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						机种信息：
						<s:select name="partNO" list="listParts" listKey="route_id+','+part_no"
							listValue="'机种：'+model_name+';料号：'+part_no" value="partNO"
							id="model_id_qty" headerKey="0" headerValue="--请选择机种--">
						</s:select><!-- onchange="cascadingProcess(this.form.id,'initMissScanQry')" -->
					</td>
					<td>
					选择日期：<s:textfield name="timeB" cssClass="required date" dateFmt="yyyy-MM-dd HH" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
					&nbsp;&nbsp;--&nbsp;&nbsp;
					<s:textfield name="timeE" cssClass="required date" dateFmt="yyyy-MM-dd HH" readonly="true" minDate="{2014}-{1}-{1}" maxDate="{%y}-%M-{%d}"/>
					</td>
				</tr>
			</table>
		
			<div class="subBar">
				<ul>
					<li>
					<div class="buttonActive">
					<div class="buttonContent">
						<button name="btn" id="btnQueryDefectByProcess" type="button" onclick="checkQueryUniversal('model_id_qty',this.form.id,'qryLaserQuality')">检索</button>
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
				<a class="icon" title="导出Excel" <s:if test="#request.listMaps.size != 0">onclick="download_universal('laserQtyExp?laserQtyExp=<s:property value="laserQtyExp"/>')"</s:if>>
					<span>导出Excel</span>
				</a>
			</li>
			<li class="line">line</li>

			<span>
				共有&nbsp;<font color="blue"><b><s:property value="#request.listMaps.size()"/></b></font>&nbsp;条记录，
				为加快显示速度，只显示前50条，查看所有数据请点击【导出Excel】查看。
			</span>
		</ul>
	</div>
	<TABLE class="table" width="100%" layoutH="110">
	<THEAD>
		<TR>
			<TH>序号</TH>
			<TH>工单号</TH>
			<TH>产品二维码</TH>
			<TH>镭雕条码等级</TH>
			<TH>镭雕时间</TH>
		</TR>
	</THEAD>
	<TBODY>
	<s:if test="#request.listMaps.size != 0">
	<s:iterator value="listMaps" id="maps" status="index" begin="0" end="detailSize">
		<tr align="center">
			<td>
				<s:property value="#index.index+1"/>
			</td>
			<td>
				<s:property value="#maps.work_order"/>
			</td>
			<td>
				<s:property value="#maps.serial_number"/>
			</td>
			<td>
				<s:property value="#maps.qc_result"/>
			</td>
			<td>
				<s:property value="#maps.LASRR_TIME"/>
			</td>
		</tr>
	</s:iterator>
	</s:if>
	<s:else>
		<tr align="center">
			<td colspan="5"><font color="red">根据条件没有查到数据</font></td>
		</tr>
	</s:else>
	</TBODY>
	</TABLE>
</body>
</html>