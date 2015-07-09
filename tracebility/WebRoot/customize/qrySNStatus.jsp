<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 查询产品状态 -->
	<div class="pageHeader">
		<form action="qryStatus" class="pageForm required-validate" method="post" onsubmit="return navTabSearch(this)">
		<table class="searchContent" width="100%" >
			<tr>
				<td>
					<div id="sn" style="font-size: 25">产品序列号：</div>
				</td>
				<td>
					<s:textfield name="serial_status" minlength="3" maxlength="20" cssClass="required alphanumeric" cssStyle="font-size:30;width:470" />
				</td>
				<td>
					<input type="submit" id="qryStatus" value="查询状态" style="font-size:25" />
				</td>
			</tr>
		</table>
		<span style="color:red;font-size:25px;color: red"><b>
			<s:if test="enabled==null || null==''">请输入有效产品二维码</s:if>
			<s:else >产品状态：<s:property  value="enabled" /></s:else></b>
		</span>
		</form>
	</div>
</body>
</html>