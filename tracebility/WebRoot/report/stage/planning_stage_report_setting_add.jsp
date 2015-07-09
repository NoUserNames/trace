<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//ttd HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.ttd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增短信接收人</title>
<script type="text/javascript" src="js/jobHandle.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
<h2 class="contentTitle">添加工序表单</h2>
	<form method="post" id="stage_report_add" action="stageReportSettingAdd" class="pageForm required-validate" onsubmit="return validateCallback(this)">
	<div>
		<dl>
			<dt>机种名称</dt>
			<dd>
				<s:select list="#{'A44':'A44','AV9 DH':'AV9 DH','AV9 TC':'AV9 TC','AV9 BC':'AV9 BC','BV':'BV','BV-3G':'BV-3G','BV-WIFI':'BV-WIFI','E5':'E5','E5-WIFI':'E5-WIFI','E5-3G':'E5-3G','FJ6':'FJ6','Hulk DH':'Hulk DH','Hulk TC':'Hulk TC','Lincoln':'Lincoln','Lincoln-WIFI':'Lincoln-WIFI','Lincoln-3G':'Lincoln-3G','MiLan':'MiLan','MiLan-WIFI':'MiLan-WIFI','MiLan-3G':'MiLan-3G','Tiger':'Tiger','Tiger-3G':'Tiger-3G','Tiger-WIFI':'Tiger-WIFI','Venice':'Venice','Venice-WIFI':'Venice-WIFI','Venice-3G':'Venice-3G'}" cssStyle="width:280px" headerKey="0" headerValue="--请选择机种名称--" name="model_name"></s:select>
			</dd>
		</dl><br>
		<dl>
			<dt>tracebbility 料号</dt>
			<dd>
				<s:select cssStyle="width:280px" name="part_stage_report_add" list="listParts" listKey="part_no"
				listValue="'机种：'+model_name+';料号：'+part_no"
					onchange="cascadingProcess(this.form.id,'initStageReportSettingAdd')"
					headerKey="0" headerValue="--请选择机种--" id="model_id_wip">
				</s:select>
			</dd>
		</dl><br>
		<dl>
			<dt>tracebbility 制程</dt>
			<dd>
				<s:select multiple="multiple" cssStyle="width:280px" list="listProcesses" listKey="process_id" listValue="process_name"
					headerKey="0" name="process_stage_report_add" id="p_cbo_id_wip" headerValue="--请选择制程--">
				</s:select>
			</dd>
		</dl><br>
		<dl>
			<dt>工序名称：</dt>
			<dd><input type="text" name="process" size="50" class="required"/></dd>
		</dl><br>
		<dl>
			<dt>部门名称：</dt>
			<dd><input name="dept_name" type="text" size="50"/></dd>
		</dl><br>
		<dl>
			<dt>ERP制程料号：</dt>
			<dd><input name="erp_part_no" type="text" size="50" class="required"/></dd>
		</dl><br>
		<dl>
			<dt>授权操作：</dt>
			<dd><!-- onkeyup="getEmpName(this.value)" -->
				<input onkeyup="addComma(this.id,this.value)" name="emp_no_add" id="emp_no_add" type="text" size="50"/>

			</dd>
		</dl><br>
		</div>
		<span style="color:red" id="emp_error"></span>
		<div class="divider"></div>
		<p><font color="red"><b>*</b></font>为必填项</p>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn" onclick="doSettingSub(this.form.id)">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</body>
</html>