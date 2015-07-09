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
    
    <title>查询箱号</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jobHandle.js" /></script>

  </head>
  
  <body>
	<div class="pageHeader">
	   <form class="pageForm required-validate" id="queryCartonForm" action="queryByCarton" method="post">
		<div class="searchBar">
		 <table class="searchContent">
			<tbody>
		        <tr class="firstRow">
		            <td width="361" valign="top" rowspan="2" colspan="1" style="word-break: break-all;">
			                箱号：
						<s:textarea name="cartons" id="cartonNO" cols="30" rows="3" cssStyle="font-size:16px;"/>
		            </td>
		            <td width="361" valign="top" style="word-break: break-all;">
						选择类别：
						<s:select list="#{'1':'当前状态','2':'过站记录'}" name="type" value="type">
						</s:select>
		            </td>
		            <td width="233" valign="top" rowspan="2" colspan="1" style="word-break: break-all;">
                	<ul class=" list-paddingleft-2" style="list-style-type: disc;">
                	<p style="color: blue"><b>提示：</b></p>
                    <li>
                        <p>
                            <span style="font-size: 13px;color:blue;">查询多个箱号，请回车换行输入</span>
                        </p>
                    </li>
                    <li>
                        <p>
                            <span style="font-size: 13px;color:blue;">查多箱时只能查询最新状态</span><br/>
                        </p>
                    </li>
                    <li>
                        <p>
                            <span style="font-size: 13px;color:blue;">单箱查询导出excel以箱号命名</span><br/>
                        </p>
                    </li>
                    <li>
                        <p>
                            <span style="font-size: 13px;color:blue;">多箱查询导出excel以系统时间命名</span>
                        </p>
                    </li>
                </ul>
            </td>
		        </tr>
		        <tr>
		            <td width="361" valign="top" style="word-break: break-all;">
		            	选择站点：
		                <select name="terminalName" style="width:200px;">
							<option value="">--请选择站点--</option>
						<s:iterator value="terminalNames" id="names" status="index">
							<option value="<s:property value="%{#names}"/>">
								<s:property value="%{#names}"/>
							</option>
						</s:iterator>
						</select>
		            </td>
		        </tr>
		    </tbody>
			
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button name="btn" id="btn" type="button" onclick="queryCartons('cartonNO',this.form.id,'箱号')">检索</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button name="btn" id="btn" type="button" onclick="clearup('cartonNO')">清空</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	</div>

	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<li>
				<a class="icon"  title="导出Excel" onclick="download_universal('expCarton?expCarton=<s:property value="cartonExpName"/>')"><!-- expCarton() -->
					<span>导出Excel</span>
				</a>
			</li>
			
			<span>
				共有&nbsp;<font color="blue"><b><s:property value="#request.listP.size()"/></b></font>&nbsp;条记录	
			</span>
		</ul>
	</div>
	
	<table class="table" width="100%" LayoutH="150">
  		<thead>
	  		<tr>
	  			<th>工单类别</th>
	  			<th>产品序列号</th>
	  			<th>机种</th>	
	  			<th>扫描站点</th>
	  			<th>箱号</th>  			
	  			<th>LOGO编号</th>
	  			<th>FATP标签</th>
	  			<th>扫描时间</th>
	  		</tr>
  		</thead>
  		<tbody>
		<s:if test="#request.listP.size() != 0">
	  		<s:iterator value="listP" id="p" status="index">
	  		<tr>
	  			<td>
	  				<s:property value="work_order"/>
	  			</td>
	  			<td>
	  				<s:property value="serial_number"/>
	  			</td>
	  			<td>
	  				<s:property value="model_name"/>
	  			</td>
	  			
	  			<td>
	  				<s:property value="terminal_name"/>
	  			</td>	  			 
	  			
	  			<td>
	  				<s:property value="carton_no"/>
	  			</td>	  			 
	  			<td>
	  				<s:property value="rc_no"/>
	  			</td>
	  			<td>
	  				<s:property value="customer_sn"/>
	  			</td>
	  			<td>
	  				<s:property value="out_process_time"/>
	  			</td>	
	  		</tr>
	  		</s:iterator>
	  	</s:if>
	  	<s:if test="#request.listP.size() == 0">
	  	<tr align="center">
  			<td>
  				<font color="red">
	  			<b>根据条件没有查到数据</b></font>
	  			<br/>
  			</td>
	  	</tr>
	  	</s:if>
		</tbody>
	  </table>
  </body>
</html>