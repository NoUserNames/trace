<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查询过站记录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
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
			background:#dcddc0 url('img/cell-blue.jpg');
			border-width: 1px;
			padding: 8px;
			border-style: solid;
			border-color: #999999;
		}
	</style>
	
	<script type="text/javascript" src="js/jobHandle.js" /></script>
	
  </head>
  <body>
	<div class="pageHeader">
		<form class="pageForm required-validate" onsubmit="return navTabSearch(this)" action="queryQuery" method="post" id="myForm">
		<div class="searchBar">
		 <table class="searchContent">
				<tr>
					<td>
						查询条件：
						<s:textfield cssStyle="width:300px" name="queryValue" id="queryValue" 
						title="输入产品序列号或者FATP标签查询" cssClass="required" />
					</td>
					<td>
						<div id="serial_validata" style="color: red;">输入产品序列号或者FATP标签查询</div>
					</td>
				</tr>
			</table>
			<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button name="btn" id="btn" type="button" onclick="sub_serial('queryValue')">检索</button>
								</div>
							</div>
						</li>
						<li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btnReset" type="reset">清空</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>

	<div class="panel close collapse" defH="130">
		<h1>【产品当前信息】
			<span>
			共有<font color="red"><b><s:property value="#request.listP.size()"/></b></font>条过站记录,
			</span>
			<span><font color="red"><b><s:property value="#request.listFixData.size()"/></b></font>条不良记录</span>
		</h1>
		<div>
		<table class="imagetable" width="100%">
			<tbody>
	 		<tr>
	 			<td width="100px" class="td"><b>工单号:</b></td>
	 			<td>	  				
	 				<s:property value="productStatus.work_order"/>
	 			</td>
	 			<td width="150px" class="td"><b>工单类别:</b></td>
	 			<td>
	 				<s:property value="productStatus.wo_type"/>
	 			</td>
	 			<td width="100px" class="td"><b>机种信息:</b></td>
	 			<td>
	 				<s:property value="productStatus.model_name"/>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td class="td"><b>FATP标签:</b></td>
	 			<td>
	 				<s:property value="productStatus.customer_sn"/>
	 			</td>
	 			<td class="td"><b>箱号:</b></td>
	 			<td>
	 				<s:property value="productStatus.carton_no"/>
	 			</td>
	 			<td class="td"><b>途程:</b></td>
	 			<td>
	 				<s:property value="productStatus.route_name"/>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td class="td"><b>产品状态:</b></td>
	 			<td>
	 				 <s:if test="productStatus.work_flag == 1">
	 					报废
	 				</s:if>
	 				<s:else>
	 					<s:if test="productStatus.current_status == 1">
	 						不良
	 					</s:if>
	 					<s:if test="productStatus.current_status == 0">
	 						正常
	 					</s:if>
	 				</s:else>	
	 			</td>
	 			<td class="td"><b>产品序列号:</b></td>
	 			<td>
	 				<s:property value="productStatus.serial_number"/>
	 			</td>
	 				 
	 			<td class="td"><b>料号:</b></td>
	 			<td>
	 				<s:property value="productStatus.part_no"/>
	 			</td>
	 		</tr>
	 		<tr>
	 			<td class="td"><b>原材二维码:</b></td>
	 			<td colspan="5">
	 				<s:property value="productStatus.supplier_sn"/>
	 			</td>
	 		</tr>
			</tbody>
		</table>
		</div>
	</div>
	
	<s:if test="#request.listP.size() != 0">
	<div class="panel collapse" >
		<h1>【产品履历表】
			<span>
			共有<font color="red"><b><s:property value="#request.listP.size()"/></b></font>条记录	
			</span>
		</h1>
		<div>
		<table class="list" width="100%" layoutH="138">
  		<thead>
	  		<tr>
	  			<th width="60px">机种</th>
	  			<th>线别</th>
	  			<th>制程名称</th>
	  			<th>站点名称</th>
	  			<th>最后扫描时间</th>	  			
	  			<th width="30px">状态</th> 			
	  			<th>员工名称</th>
	  			<th>FATP标签</th>
	  			<th>箱号</th>
	  		</tr>
  		</thead>
  		<tbody>	
	  		<s:iterator value="listP" id="p" status="index">
	  		<tr>
	  			<td>
	  				<s:property value="#p.sysPart.MODEL_NAME"/>
	  			</td>
	  			<td>
	  				<s:property value="#p.sysPdline.PDLINE_NAME"/>
	  			</td>
	  			<td>
	  				<s:property value="#p.SysProcess.PROCESS_NAME"/>
	  			</td>
	  			<td>
	  				<s:property value="#p.sysTerminal.TERMINAL_NAME"/>
	  			</td>
	  			<td>
	  				<s:property value="#p.OUT_PROCESS_TIME"/>
	  			</td>	  			
	  			<td align="center"
	  				<s:if test="#p.WORK_FLAG == 1">
	  					bgcolor="red"
	  				</s:if>
	  				<s:else>
	  					<s:if test="#p.CURRENT_STATUS == 1">
	  						bgcolor="yellow"
	  					</s:if>
	  				</s:else>
	  				 >
	  				 <s:if test="#p.WORK_FLAG == 1">
	  					报废
	  				</s:if>
	  				<s:else>
	  					<s:if test="#p.CURRENT_STATUS == 1">
	  						不良
	  					</s:if>
	  					<s:else>正常</s:else>
	  				</s:else>				
	  			</td>
	  			<td>
	  				<s:property value="#p.sysEmp.EMP_NAME"/>
	  			</td>
	  			
	  			<td>
	  				<s:property value="#p.CUSTOMER_SN"/>
	  			</td>
	  			<td>
	  				<s:property value="#p.CARTON_NO"/>
	  			</td>	
	  		</tr>
	  		</s:iterator>
	  	<s:if test="#request.listP.size() == 0">
	  		<tr align="center">
	  			<td colspan="9">
	  				<font color="blue">
	  					<b>根据条件没有查到数据</b>
	  				</font>
	  			</td>
	  		</tr>
	  	</s:if>
	  		</tbody>
	  	</table>
	  	</div>
	</div>
  	</s:if>

	<s:if test="#request.listFixData.size() != 0">
	<div class="panel collapse">
		<h1>【产品不良记录】
		<span>
			共有<font color="red"><b><s:property value="#request.listFixData.size()"/></b></font>条记录	
		</span>
		</h1>
		<div>
			<table class="list" width="100%" >
				<thead>
				<tr>
					<th>不良站点</th>
					<th>不良制程</th>
					<th>记录时间</th>
					<th>不良代码</th>
					<th>不良描述</th>
					<th>QC</th>
				</tr>
				</thead>
				<tbody>
				<s:iterator value="listFixData" id="fixdata">
				<tr>
					<td><s:property value="#fixdata.sysTerminal.TERMINAL_NAME"/></td>
					<td><s:property value="#fixdata.sysProcess.PROCESS_NAME"/></td>
					<td><s:property value="#fixdata.REC_TIME"/></td>
					<td><s:property value="#fixdata.sysDefect.DEFECT_CODE"/></td>
					<td><s:property value="#fixdata.sysDefect.DEFECT_DESC"/></td>
					<td><s:property value="#fixdata.sysEmp.EMP_NAME"/></td>
				</tr>
				</s:iterator>
				<s:if test="#request.listFixData.size() == 0">
				<tr align="center">
					<td colspan="6" >
						<font color="blue"><b>此工件当前无不良记录</b></font>
					</td>
				</tr>
				</s:if>
				</tbody>
			</table>
	  	</div>
  	</div>
	</s:if>
  </body>
</html>