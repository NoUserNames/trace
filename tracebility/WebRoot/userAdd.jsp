<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add user page</title>
<script type="text/javascript" src="js/jobHandle.js"></script>
</head>
<body>
   <div class="pageContent">	
	<form method="post" id="addUser" action="addUser" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="60">
			<div class="unit">
				<label>员 工 号：</label>
				<input onblur="checkUidExist(this.value)" id="uid_new" name="uid" size="30" class="required" />
			</div>
			<div class="unit">
				<label>员工姓名：</label>
				<input id="uname" name="uName" size="30" minlength="2" maxlength="10" class="required"/>
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