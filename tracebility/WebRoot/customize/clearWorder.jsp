<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特殊还回-单号</title>
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
<body >
<center>
<div class="panel" defH="500">
<h1>无二维码销帐。这里只能处理按无二维码借出的销帐业务。</h1>
<div>
	<form id="r_w_from" method="post" action="qrySampleWorder">
	<table class="imagetable" width="500px">
		<thead>
			<tr>
				<th width="120px">列标题</th>
				<th>值</th>
			</tr>
		</thead>
	    <tbody>
	        <tr>
	            <td>借出单号</td>
	            <td>
	            	<s:textfield alt="在此处输入无二维码借出单号后回车会自动查询借出信息" cssClass="required" onkeypress="r_serial(this.form.id)" name ="r_worder_number" cssStyle="font-size:20;width:100%"/>
	            </td>
	        </tr>
	        <tr>
	            <td>借出数量</td>
	             <td>
	            	<s:property value="mapSample.w_exp_qty"/>
	            </td>
	        </tr>
	        <tr>
	            <td>还回数量</td>
	             <td>
	            	<s:property value="mapSample.w_qty"/>
	            </td>
	        </tr>
	        <tr>
	            <td>单号</td>
	             <td>
	            	<s:property value="mapSample.w_worder_number"/>
	            </td>
	        </tr>
	        <tr>
	            <td>借出时间</td>
	             <td>
	            	<s:property value="mapSample.w_exp_time"/>
	            </td>
	        </tr>
	        <tr>
	            <td>借出人</td>
	             <td>
	            	<s:property value="mapSample.w_exp_emp"/>
	            </td>
	        </tr>
	        <tr>
	            <td>借出原因</td>
	             <td>
	            	<s:property value="mapSample.w_reason"/>
	            </td>
	        </tr>
	        <tr>
	            <td colspan="2" rowspan="1">
	            	销帐原因：
	            </td>
	        </tr>
	        <tr>
	            <td colspan="2" rowspan="1">
	            	<input class="required" name ="r_worder_reason" style="font-size:20;width:100%"/>
	            </td>
	        </tr>
	        <tr>
	            <td colspan="2" rowspan="1">
	            	<div class="subBar">
						<ul>
							<li>
								<div class="buttonActive">
									<div class="buttonContent">
										<button name="btn" id="btn" type="button" onclick="sub(this.form.id,'clearWorder')">销帐</button>
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
			<font color="red"><b>单号不是按无二维码借出的单号，或已做过销帐处理。</b></font>
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