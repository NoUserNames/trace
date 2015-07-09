<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	dl{
		padding-top: 10;
		padding-left: 40;
	}
	dd{
		padding-top: 5;
	}
</style>
</head>
<body>
	<form method="post" action="updatePlanSetting" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="stage_report_setting_id" value = "<s:property value="listSettings[0].STAGE_REPORT_SETTING_ID"/>">
		<dl>
			<dt>工序名称</dt>
			<dd><input name="process" size="30" value="<s:property value="listSettings[0].PROCESS"/>"/></dd>
		</dl>
		
		<dl>
			<dt>ERP制程料号</dt>
			<dd><input name="erp_part_no" size="30" value="<s:property value="listSettings[0].ERP_PROCESS_ID"/>"/></dd>
		</dl>
		
		<dl>
			<dt>机种信息</dt>
			<dd>
				<s:select list="#{'A44':'A44','AV9 DH':'AV9 DH','AV9 TC':'AV9 TC','AV9 BC':'AV9 BC','BV':'BV','BV-3G':'BV-3G','BV-WIFI':'BV-WIFI','E5':'E5','E5-WIFI':'E5-WIFI','E5-3G':'E5-3G','FJ6':'FJ6','Hulk DH':'Hulk DH','Hulk TC':'Hulk TC','Lincoln':'Lincoln','Lincoln-WIFI':'Lincoln-WIFI','Lincoln-3G':'Lincoln-3G','MiLan':'MiLan','MiLan-WIFI':'MiLan-WIFI','MiLan-3G':'MiLan-3G','Tiger':'Tiger','Tiger-3G':'Tiger-3G','Tiger-WIFI':'Tiger-WIFI','Venice':'Venice','Venice-WIFI':'Venice-WIFI','Venice-3G':'Venice-3G'}" name="model_name"></s:select>
			</dd>
		</dl>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</body>
</html>