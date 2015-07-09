<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="charset" content="UTF-8">
<title>4小时报表工序管理</title>
<script type="text/javascript" src="js/jobHandle.js"></script>

<script type="text/javascript">
	$("table tr").click(function() {
		$("#emp_no").removeAttr("readonly");
		var tr = $(this);
		var tds = tr.find("td");
		$("#currentSetting").html('当前选中机种是：'+tds[1].innerHTML+'，ERP制程料号：'+tds[3].innerHTML);
		$("#currentSettingId").val(tds[0].innerHTML);
		
		$.post(
   			"queryReportSettingEmp",
   			{stage_report_setting_id:tds[0].innerHTML},
   			function(data,status){
   				$("#emp_no").val(data);
   			}
   		);
		
	})
</script>
</head>
<body>

	<div class="panelBar">
		<ul class="toolBar">
		<span style="font-size:20">输入工号：<input onkeyup="addComma(this.id,this.value)" readonly="readonly" style="width:900px;font-size:20" id="emp_no" name="emp_no" class="required" /></span>
		<li class="line">line</li>
		<li>
			<a class="icon" target="ajaxTodo" onclick="reprotSettingEmpNO(this.id)" id="delA" title="确定要给所选择的工序添加操作权限吗?"><span style="font-size:20">点击授权</span></a>
		</li>
		<li class="line">line</li>
		
		</ul>
	</div>

	<div class="panelBar">
		<ul class="toolBar">
		<span style="color:blue"><b>提示:</b>在输入框中输入工号后按回车键验证有效性，多个工号系统会自动以分号(;)分隔。点击授权即可授权。</span></span>
		<li class="line">line</li>
		<!-- <li><span id="tips"></span></li> -->
		<span id="currentSetting"></span>
		</ul>
	</div>
	<input type="hidden" id="currentSettingId" value=""/>

	<div class="pageContent">
	<form action="initStageReportSetting" name="stageReportSetting" onsubmit="return dwzSearch(this,'navTab')" method="get">
		<table id="tt" class="list" width="100%" layoutH="54">
			<thead>
				<tr>
					<th>工序编号</th>
					<th>工序名称</th>
					<th>机种名称</th>
					<th>制程名称</th>
					<th>ERP制程料号</th>
					<th>部门名称</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="listSettings" id="setting" status="index">
					<tr>
						<td>
							<s:property value="#setting.STAGE_REPORT_SETTING_ID" />
						</td>
						<td>
							<s:property value="#setting.PROCESS" />
						</td>
						<td><s:property value="#setting.MODEL_NAME" /></td>
						<td>
							<s:property value="#setting.process_name" />
						</td>
						<td>
							<s:property value="#setting.ERP_PROCESS_ID" />
						</td>
						<td>
							<s:property value="#setting.DEPT_NAME" />
						</td>
						<td>
							<s:property value="#setting.CREATE_TIME" />
						</td>
						<td width="110px">
							<a class="button" target="dialog" href="queryPalnReportSetting?stage_report_setting_id=<s:property value="#setting.STAGE_REPORT_SETTING_ID" />"  width="300" height="240" mask="true" ><span>修改</span></a>
							<a class="button" target="ajaxTodo" href="deletePlanSetting?stage_report_setting_id=<s:property value="#setting.STAGE_REPORT_SETTING_ID" />" id="delA" title="确定要删除【<s:property value="#setting.MODEL_NAME" />,<s:property value="#setting.ERP_PROCESS_ID" />】吗?删除工序后，对应操作权限也会被删除。"><span>删除</span></a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		</form>
		
		<form id="pagerForm" action="initStageReportSetting" method="post">
		 		<input type="hidden" name="pageNum" value="1" />
		 		<input type="hidden" name="numPerPage" value="<s:property value="numPerPage"/>">
		 		<input type="hidden" id="chkFlag" name="chkFlag">
		</form>
	</div>
</body>
</html>