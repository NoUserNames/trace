<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改密码</title>
	<script type="text/javascript" src="js/jobHandle.js"></script>
  </head>
  
  <body>
    <div class="pageContent">	
	<form method="post" action="changePassword" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="60">
			<div class="unit">
				<label>旧密码</label>
				<input onblur="checkOldPwd('<s:property value="#session.username"/>',this.value,'div_oldPwd',this.id)" type="password" id="oldPassword" name="oldPassword" size="30" minlength="3" maxlength="20" class="required" />
				<s:hidden name="uid" value="%{#session.username}"/>
				<div id="div_oldPwd" style="color: red;display: none">旧密码不正确</div>
			</div>
			<div class="unit">
				<label>新密码</label>
				<input type="password" id="cp_newPassword" name="newPassWord" size="30" minlength="3" maxlength="20" class="required alphanumeric"/>
			</div>
			<div class="unit">
				<label>确认新密码</label>
				<input type="password" name="rnewPassword" size="30" equalTo="#cp_newPassword" class="required alphanumeric"/>
			</div>			
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">确定</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">关闭</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>	
	</div>
  </body>
</html>
