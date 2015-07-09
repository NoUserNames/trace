<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>量测数据查询</title>
</head>
<body>
<div class="pageHeader">
	<form class="pageForm required-validate" action="qryMeasure" onsubmit="return navTabSearch(this)" method="post" id="qryMeasureForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						量测类别：<s:select list="#{'sajet.Rcdepth':'镜头孔深度','sajet.BOTTOM_THICK':'底部料厚','sajet.rcgap':'RC断差','sajet.logo_thick':'LOGO厚度'
						,'sajet.capdepth':'CAP深度','sajet.capgap':'CAP断差','sajet.awgap':'AW铣切断差','sajet.AWBin':'AW分BIN'
						,'sajet.ajdepth':'AJ孔深度','sajet.LOGO_GAP_CAP':'LOGO间隙断差','sajet.LOGO_ENDOCOELE_CAP':'LOGO内腔断差','sajet.IO_GAP_CAP':'IO间隙断差'
						,'sajet.rcdepth':'镜头孔深度','sajet.G_TESTDATA_INNER_XY':'内长宽','sajet.AW_GAP_CAP':'AW间隙断差','spc.G_view_test_rt':'磁铁磁通量'
						,'sajet.G_TESTDATA_FLATNESS_DA':'A基准面','sajet.G_TESTDATA_FLATNESS_BACK':'大平面','sajet.oriongap':'orion断差'}" 
						headerKey="0" headerValue="-- 请选择量测类别 --" id="table" name="table" onchange="serial_number.value=''"/>
					</td>
					<td>
						产品序列号/箱号：<s:textfield cssStyle="width:200px" name="serial_number" id="serial_number" 
						cssClass="required" onclick="$('#queryValue').animate({width:'300px'})" 
						onblur="$('#queryValue').animate({width:'200px'})"/>
					</td>
					<td>
						<!-- 
						选择时间：
						<input style="width: 110" class="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="readonly"/>
						&nbsp;~&nbsp;
						<input style="width: 110" class="required date" dateFmt="yyyy-MM-dd HH:mm" readonly="readonly"/>
						 -->
						 提示：输入<b>[产品序列号]</b>或者<b>[箱号]</b>都可以作为查询条件。
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button name="btn" id="btn" type="button" onclick="chkMeasureData('table','serial_number',this.form.id)">检索</button>
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
	
	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<li>
				<a class="icon"  title="导出Excel" <s:if test="#request.dataList.size() != 0">onclick="download_universal('downloadMeasure.action?wipExp=<s:property value="wipExp"/>')"</s:if>>
					<span>导出Excel</span>
				</a>
			</li>
			<li class="line">line</li>
			<span>
				共有&nbsp;
				<font color="blue"><b>
				<s:property value="#request.dataList.size()"/>
				
				</b></font>&nbsp;条记录	
			</span>
			
			<li class="line">line</li>
		</ul>
	</div>
	
	<div class="pageContent">
	<table class="list" width="100%" layoutH="85" layoutY="50">
		<thead>
		<tr>
			<s:iterator value="columnList" id="columnNmae">
			<th>
				<s:property value="columnNmae"/>
			</th>
			</s:iterator>
		</tr>
		</thead>
		<tbody>
		<s:iterator value="dataList" id="data">
		<tr align="center">
			<s:iterator value="data" id="tmp">
			<td>
				<s:property value="tmp"/>
			</td>
			</s:iterator>
		</tr>
		</s:iterator>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>