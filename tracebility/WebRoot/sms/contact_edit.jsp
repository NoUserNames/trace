<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑短信接收人</title>
<script type="text/javascript">
	
</script>
</head>
<body>
<div class="pageContent">
	<form method="post" action="contact_edit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	<div class="pageFormContent" layoutH="60">
		<dl>
			<dt>姓名：</dt>
			<dd><input name="emp_name" maxlength="20" value="${contact_edit.emp_name }" class="required" style="width: 200px"/></dd>
		</dl>
		<dl>
			<dt>工号：</dt>
			<dd><input class="required" name="emp_no" value="${contact_edit.emp_no }" style="width: 200px"/></dd>
		</dl>
		<dl>
			<dt>手机号码：</dt>
			<dd><input class="required" name="mobile_number" value="${contact_edit.mobile_number }" style="width: 200px"/></dd>
		</dl>
		<dl>
			<dt>厂别：</dt>
			<dd>
				<s:select value="contact_edit.factory_id" list="#{'10001':'日沛', '10013':'日腾一厂', '10015':'日铭', '10016':'日闰', '10017':'胜瑞', '10018':'应华'}" name="factory_id"/>
			</dd>
		</dl>
		<dl>
			<dt>通讯录群组：</dt>
			<dd>
				<s:select value="contact_edit.group_id" list="groups" name="sms_group" listKey="group_id" listValue="group_name"/>
				<input type="hidden" value="<s:property value="contact_edit.group_id"/>" name="sms_group_old"/>
			</dd>
		</dl>
	</div>
	<div>
		<font color="red"><b>*</b></font>为必填项
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
		</ul>
	</div>
	</form>
</div>
</body>
</html>