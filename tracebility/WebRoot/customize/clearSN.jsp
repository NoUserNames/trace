<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特殊还回-单片</title>

<!-- CSS goes in the document HEAD or added to your external stylesheet -->
<style type="text/css">
	table.imagetable {
		font-family: verdana,arial,sans-serif;
		font-size:11px;
		color:#333333;
		border-width: 1px;
		border-color: #999999;
		border-collapse: collapse;
	}
	table.imagetable th {
		background:#b5cfd2 url('img/cell-blue.jpg');
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		border-color: #999999;
	}
	table.imagetable td {
		background:#dcddc0 url('img/cell-grey.jpg') no-repeat;
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		border-color: #999999;
	}
</style>

<script type="text/javascript" src="js/jobHandle.js"></script>

</head>
<body>
<center>
<div class="panel" defH="500">
<h1>按产品序列号单个销帐，如果数量较多，可以选择按单号批量处理。<font color="red">*</font>为必填项。</h1>
<div>
	<form id="r_s_from" name="r_s_from" method="post" action="qrySampleBySN">
	<table class="imagetable" width="500px">
		<thead>
			<tr>
				<th width="50px">列标题</th>
				<th width="300px">值</th>
				<th>二维码清单</th>
			</tr>
		</thead>
	    <tbody>
	        <tr>
	            <td>
	            	<s:select list="#{'serial_number':'按二维码单片处理','worder_number':'按单号批量处理'}" onchange="r_serial_number.value = ''" name="r_type"/>
	            </td>
	            <td>
	            	<s:textfield alt="在此处输入数据后回车会查询借出信息" onkeypress="r_serial(this.form.id)" id ="r_serial_number" name ="r_serial_number" cssClass="required" cssStyle="font-size:20;width:100%"/>
	            	<input type="hidden" name="terminal_id" value="52203817"/>
	            </td>
	            <td rowspan="10" colspan="1" width="150px">
	            	<s:if test="#request.mapList.size()">
	            		共<font color="blue"><b><s:property value="#request.mapList.size()"/></b></font>条
	            		<hr/>
	            		<div layoutH="200">
		            		<s:iterator value="mapList" id="t">
		            			<font><s:property value="#t.serial_number"/></font>
		            		</s:iterator>
	            		</div>
	            	</s:if>
	            </td>
	        </tr>
	        <tr>
	            <td>产品序列号</td>
	            <td>
	            	<s:property value="mapSample.serial_number"/>
	            </td>
	        </tr>
	        <tr>
	            <td>借出单号</td>
	            <td>
	            	<s:property value="mapSample.worder_number"/>
	            </td>
	        </tr>
	        <tr>
	            <td>机种名称</td>
	           	<td>
	            	<s:property value="mapSample.model_name"/>
	            </td>
	        </tr>
	        <tr>
	            <td>借出时间</td>
	            <td>
	            	<s:property value="mapSample.exp_time"/>
	            </td>
	        </tr>
	        <tr>
	            <td>借出人</td>
	            <td>
	            	<s:property value="mapSample.exp_emp"/>
	            </td>
	        </tr>
	        <tr>
	            <td>借出原因</td>
	            <td>
	            	<s:property value="mapSample.reason"/>
	            </td>
	        </tr>
	        <tr>
	            <td colspan="2" rowspan="1">
	            	销帐原因：
	            </td>
	        </tr>
	        <tr>
	            <td colspan="2" rowspan="1">
	            	<input alt="此处输入销帐的原因" class="required" name ="r_serial_reason" style="font-size:20;width:100%"/>
	            </td>
	        </tr>
	        <tr>
	            <td colspan="2" rowspan="1">
	            	<div class="subBar">
						<ul>
							<li>
								<div class="buttonActive">
									<div class="buttonContent">
										<button name="btn" id="btn" type="button" onclick="sub(this.form.id,'clearSN')">销帐</button>
									</div>
								</div>
							</li>
						</ul>
					</div>
	            </td>
	        </tr>
	    </tbody>
	</table>
	</form>
	<div align="center" style="padding-top: 5">
		<s:if test="#request.mapSample.size() ==0">
			<font color="red"><b>序号或单号不存在，或已做过销帐处理。如果按单号销帐请确认单号是有二维码借出的单号，不能输入无二维码的单号！</b></font>
		</s:if>
	
		<s:if test="#request.r_serial_result == true">
			<font color="bule"><b>销帐成功</b></font>
		</s:if>
	</div>
</div>
</div>
</center>
</body>
</html>