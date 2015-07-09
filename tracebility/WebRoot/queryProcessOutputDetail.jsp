<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>queryProcessOutputDetail</title>
</head>
<body>
	<table class="table" width="100%" layoutH="53">
		<thead>
 		<tr>
 			<th>工单类别</th>
 			<th>产品序列号</th>
 			<th>查询制程</th>
 			<th>过站时间</th>
 			<th>当前制程</th>
 			<th>当前站点</th>
 			<th>最后扫描时间</th>
 			<th>当前状态</th>
 		</tr>
		</thead>
		<tbody>
		<s:iterator value="listResultDetail" id="list" status="index" begin="0" end="outputDetailSize">
		<tr align="center">
			<td>
				<s:property value="#list.wo_type"/>
			</td>
			<td>
				<s:property value="#list.serial_number"/>
			</td>
			<td>
				<s:property value="#list.process_name"/>
			</td>
			<td>
				<s:property value="#list.scanTime"/>
			</td>
			<td>
				<s:property value="#list.process_name_now"/>
			</td>
			<td>
				<s:property value="#list.terminal_name_now"/>
			</td>
			<td>
				<s:property value="#list.NowTime"/>
			</td>
			<td>
				<s:property value="#list.status"/>
			</td>
		</tr>
		</s:iterator>
		</tbody>
	</table>
	<div class="formBar">
		<ul>
			<li>				
				<div align="right">
				共有<b><s:property value="#request.listResultDetail.size()"/></b>条记录,
				只显示前20条，需要所有数据请下载后查阅。
				</div>
				
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" onclick="download_universal('expProcessOutputDetail?filesDetail=<s:property value="filesDetail"/>')">导出数据</button>
					</div>
				</div>
			</li>
			<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" class="close">关闭窗口</button>
				</div>
			</div>
		</li>
		</ul>
	</div>
</body>
</html>