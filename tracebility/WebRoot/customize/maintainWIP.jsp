<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>WIP状态维护</title>
	<style type="text/css">
		fieldset {
			border: 5px solid #B5B8C8;
			padding: 5px;
			margin-bottom: 5px;
		}
	</style>
	<script type="text/javascript" src="js/jobHandle.js"></script>
	<script type="text/javascript">
		//滤镜获取本地路径
		function test(){
			var file_upl = document.getElementById('upload_id');  
			file_upl.select();  
			  
			var realpath = document.selection.createRange().text;
			alert(realpath);	
		}
	</script>
</head>
<body>
	<div class="pageHeader">
		<fieldset >
			<legend style="font-size: 20">维护产品状态</legend>
			<form action="maintainWIP" method="post" name="wipForm" id="wipForm" enctype="multipart/form-data" onsubmit="return iframeCallback(this);">
				<div align="center">
					<s:radio cssStyle="font-size:30" list="#{'one':'单个SN','batch':'批量处理'}" name="utype" onclick="change(this.value)" value="'one'" />
				</div>
				<div class="searchBar" id="one">
					<table class="searchContent" width="100%">
						<tr>
							<td width="15%">
								<div id="sn" style="font-size: 30">产品序列号：</div>
							</td>
							<td>
								<s:textfield name="serial_number" cssStyle="font-size:30;width:90%" />
							</td>
						</tr>
					</table>
				</div>
				<!-- 批量处理 -->
				<div id="batch" class="searchBar" style="display: none;">
					<input type="file" style="font-size:30;width:75%" name="upload" id="uploadify" />
				</div>
				<!-- 按钮区 -->
				<div align="center">
					<input type="submit" name="function" value="启用" style="font-size:30" />
					&nbsp;&nbsp;&nbsp;
					<input type="submit" name="function" value="禁用" style="font-size:30"/>
				</div>
			</form>
		</fieldset>
		<br/>
	</div>

	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<span>如下是帮助信息</span>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageHeader" layoutH=70>
		<div class="searchBar">
			<div style="font-size: 20">
				<b>单片操作</b>
			</div>
			<li>
				<div style="font-size: 15">请直接输入产品序列号，进行禁用或启用操作。</div>
			</li>
			<br/>
			<div style="font-size: 20">
				<b>批量操作</b>
			</div>
			<li>
				<div style="font-size: 15">
					请选择整理好的Excel文档导入后，选择相应的启用/禁用操作。Excel文件没有版本限制。
				</div>
			</li>
			<li>
				<div style="font-size: 15">
					文档内容格式为首行首列开始贴入产品序列号。如不清楚可
					<a href="upload\model\WIPModel.xlsx">
					<div style="font-size: 15;display: inline;text-decoration: none;">【点此下载模板】</div>
					</a>
				</div>
			</li>
		</div>
	</div>
</body>
</html>