<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增通讯录群组</title>
</head>
<body>
	<form method="post" action="updateContactGroup" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	<input type="hidden" name="groupId" value="<s:property value="smsContactGroup.GROUP_ID"/>">
	<div class="pageFormContent" layoutH="90">
		<dl>
			<dt>群组名称：</dt>
			<dd><input type="text" value="<s:property value="smsContactGroup.GROUP_NAME"/>" name="groupName" maxlength="20" class="required" style="width: 200px"/></dd>
		</dl>
		<dl>
			<dt>群组描述：</dt>
			<dd><input class="required" value="<s:property value="smsContactGroup.GROUP_DESC"/>" name="groupDesc" type="text" style="width: 200px"/></dd>
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
</body>
</html>