<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function test(){alert();
		//document.getElementById("carton_no").focus();
	}
</script>

</head>
<body onload="javascript:document.getElementById('carton_no').focus();">
<div class="pageHeader">
		<form class="pageForm required-validate" onsubmit="return navTabSearch(this);test()" action="chkCarton" method="post" id="carton_Form">
		<div class="searchBar">
		 <table class="searchContent">
				<tr>
					<td>
						查询条件：
						<input name="carton_no" id="carton_no" autofocus style="width:300px;font-size: 30"/>
					</td>
					<td>
						<div id="serial_validata" style="color: red;display: none;">请输入序号</div>
					</td>
				</tr>
			</table>
			</div>
		</form>
	</div>
	<div align="center">
		<div style="font-size: 50">
			<s:property value="carton_no"/><br/>
			
			<s:property value="result"/>
		</div>
	</div>
</body>
</html>