<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="css/imageTable.css">
	<link rel="stylesheet" type="text/css" href="css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/themes/icon.css">
	
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jobHandle.js"></script>
</head>
<body>
	<div class="easyui-panel" title="企划4小时报表查询表单" style="width:100%" data-options="collapsible:true">
        <div style="padding:10px 60px 20px 60px">
        <form id="ff" method="post" action="queryTotalReport">
            <table cellpadding="5">
                <tr>
					<td>
						机种：<!-- onchange="cascadeERPProcessID('fillErpProcess',this.value,'erp_process_id')" -->
						<s:select  list="listModelNames" listKey="model_name" listValue="model_name" headerKey="0" headerValue="--请选择机种名称--" name="model_name"/>
					</td>
					<!-- <td>
						料号：<s:property value="#listERPProcessID.size()"/>
							<select id="erp_process_id"></select>
					</td> -->
					<td>
						开始日期：<s:textfield name="datetime1" cssClass="easyui-datebox" data-options="required:true,formatter:myformatter,parser:myparser"/><font color="red">*</font>
					</td>
					<td>
						结束日期：<s:textfield name="datetime2" cssClass="easyui-datebox" data-options="required:true,formatter:myformatter,parser:myparser"/><font color="red">*</font>
					</td>
					<td>
						<font color="red">*</font>为必选项
					</td>
				</tr>
				<tr align="center">
					<td colspan="4">
						<button onclick="this.form.submit()">提交查询</button>
						<button onclick="clearForm()">清空表单</button>
					</td>
				</tr>
            </table>
        </form>
      
        <script>
	        function submitForm(){
	            $('#ff').form('submit');
	        }
	        function clearForm(){
	            $('#ff').form('clear');
	        }
	        function myformatter(date){
	            var y = date.getFullYear();
	            var m = date.getMonth()+1;
	            var d = date.getDate();
	            return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	        }
	        function myparser(s){
	            if (!s) return new Date();
	            var ss = (s.split('-'));
	            var y = parseInt(ss[0],10);
	            var m = parseInt(ss[1],10);
	            var d = parseInt(ss[2],10);
	            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
	                return new Date(y,m-1,d);
	            } else {
	                return new Date();
	            }
	        }
	    </script>
        </div>
    </div>
	
	<form action="#">
	<table class="imagetable" align="center" width="100%">
	<thead>
		<tr>
			<th colspan="3">
				4H生产状况
			</th>
			<s:iterator value="dates" id="date">
			<th colspan="7">
				<s:property value="date"/>
			</th>
			</s:iterator>
		</tr>
		<tr>
			<th rowspan="2">制程料号</th>
			<th rowspan="2">工序</th>
			<th style="width: 80px" rowspan="2">项目</th>
		<s:iterator value="dates" id="date">
			<th id="8-12">08:00-12:00</th>
			<th id="12-16">12:00-16:00</th>
			<th id="16-20">16:00-20:00</th>
			<th id="20-24">20:00-24:00</th>
			<th id="24-4">24:00-04:00</th>
			<th id="4-8">04:00-08:00</th>
			<th rowspan="3">单日汇总</th>
		</s:iterator>
		</tr>
		<tr>
		<s:iterator value="dates" id="date">
			<th colspan="3">白班</th>
			<th colspan="3">晚班</th>
		</s:iterator>
		</tr>
	</thead>
	<s:iterator value="listO" id="setid" status="i" >
	<tbody>
		<tr>
			<td rowspan="12">
				<s:set value="1" name="index"></s:set>
				<s:iterator value="setid" id="set" status="status">
					<s:if test="#set.ERP_PROCESS_ID != null">
						<s:if test="#index == 1">
							<s:property value="#set.ERP_PROCESS_ID" />
							<s:set name="index" value="2" />
						</s:if>
					</s:if>
				</s:iterator>
			</td>

			<td rowspan="13">
				<s:set value="1" name="index"></s:set>
				<s:iterator value="setid" id="set" status="status">
					<s:if test="#set.PROCESS != null">
						<s:if test="#index == 1">
							<s:property value="#set.PROCESS" />
							<s:set name="index" value="2" />
						</s:if>
					</s:if>
				</s:iterator>
			</td>

			<td>目标日产能</td>
			<s:iterator value="dates" id="date">
				<td colspan="7" align="center"><div>目标日产能</div></td>
				
			</s:iterator>
		</tr>
<!-- 
		<tr>
			<td>产出数</td>
		<s:iterator value="dates" id="date">
			<td colspan="7" align="center">
				产出数
			</td>
		</s:iterator>
		</tr>
 -->
		<tr>
			<td>投入数量</td>
			<s:set var="inputTotal" value="0"></s:set>
		<s:iterator value="setid" id="set" status="index">
			<td>
				<s:if test="#set.INPUT_CNT != null">
					<s:if test="%{(#index.index) % 7 != 6}">
						<s:set var="inputTotal" value="%{#set.INPUT_CNT+#inputTotal}" />
					</s:if>
				</s:if>
				<s:else>
					<s:set var="inputTotal" value="%{0+#inputTotal}" />
				</s:else>
				<s:if test="%{(#index.index) % 7==6}">
					<s:property value="#inputTotal"/>
					<s:set var="inputTotal" value="0"/>
				</s:if>
				<s:else>
					<s:property value="#set.INPUT_CNT"/>
				</s:else>
			</td>
		</s:iterator>
		</tr>

		<tr>
			<td>生产良品数量</td>
			<s:set var="output_cnt_total" value="0"></s:set>
		<s:iterator value="setid" id="set" status="index">
			<td>
				<s:if test="#set.OUTPUT_CNT != null">
					<s:if test="%{(#index.index) % 7 != 6}">
						<s:set var="output_cnt_total" value="%{#set.OUTPUT_CNT+#output_cnt_total}" />
					</s:if>
				</s:if>
				<s:else>
					<s:set var="output_cnt_total" value="%{0+#output_cnt_total}" />
				</s:else>
				<s:if test="%{(#index.index) % 7==6}">
					<s:property value="#output_cnt_total"/>
					<s:set var="output_cnt_total" value="0"/>
				</s:if>
				<s:else>
					<s:property value="#set.OUTPUT_CNT"/>
				</s:else>
			</td>
		</s:iterator>
		</tr>

		<tr>
			<td>来料不良数</td>
			<s:set var="input_ng_total" value="0"></s:set>
		<s:iterator value="setid" id="set" status="index">
			<td id="input_ng_cnt_<s:property value="#i.index"/><s:property value="#index.index"/>" onmouseover="queryComment('<s:property value="#set.REPORT_RECORD_ID"/>',this.id,'input_ng_cnt_<s:property value="#index.index%7"/>')">
				<s:if test="#set.INPUT_NG_CNT != null">
					<s:if test="%{(#index.index) % 7 != 6}">
						<s:set var="input_ng_total" value="%{#set.INPUT_NG_CNT+#input_ng_total}" />
					</s:if>
				</s:if>
				<s:else>
					<s:set var="input_ng_total" value="%{0+#input_ng_total}" />
				</s:else>
				<s:if test="%{(#index.index) % 7==6}">
					<s:property value="#input_ng_total"/>
					<s:set var="input_ng_total" value="0"/>
				</s:if>
				<s:else>
					<s:property value="#set.INPUT_NG_CNT"/>
				</s:else>
			</td>
		</s:iterator>
		</tr>

		<tr>
			<td>来料不良率</td>
			<s:set var="input_ng_rate" value="0"></s:set>
		<s:iterator value="dates" id="date">
			<td colspan="3" align="center">
				<div id="input_ng_rate_day">白班来料不良率</div>
			</td>
			<td colspan="3" align="center"><div id="input_ng_rate_night">夜班来料不良率</div></td>
			<td></td>
		</s:iterator>
		</tr>

		<tr>
			<td>制程不良数</td>
			<s:set var="process_ng_total" value="0"></s:set>
		<s:iterator value="setid" id="set" status="index">
			<td id="process_ng_cnt_<s:property value="#i.index"/><s:property value="#index.index"/>" onmouseover="queryComment('<s:property value="#set.REPORT_RECORD_ID"/>',this.id,'process_ng_cnt_<s:property value="#index.index%7"/>')">
				<s:if test="#set.PROCESS_NG_CNT != null">
					<s:if test="%{(#index.index) % 7 != 6}">
						<s:set var="process_ng_total" value="%{#set.PROCESS_NG_CNT+#process_ng_total}" />
					</s:if>
				</s:if>
				<s:else>
					<s:set var="process_ng_total" value="%{0+#process_ng_total}" />
				</s:else>
				<s:if test="%{(#index.index) % 7==6}">
					<s:property value="#process_ng_total"/>
					<s:set var="process_ng_total" value="0"/>
				</s:if>
				<s:else>
					<s:property value="#set.PROCESS_NG_CNT"/>
				</s:else>
			</td>
		</s:iterator>
		</tr>

		<tr>
			<td>制程不良率</td>
		<s:iterator value="dates" id="date">
			<td colspan="3" align="center"><div id="p_ng_rate_day">白班制程不良率</div></td>
			<td colspan="3" align="center"><div id="p_ng_rate_night">夜班制程不良率</div></td>
			<td></td>
		</s:iterator>
		</tr>

		<tr>
			<td>来料报废数</td>
			<s:set var="input_scrapt_total" value="0"></s:set>
		<s:iterator value="setid" id="set" status="index">
			<td id="input_scrapt_cnt_<s:property value="#i.index"/><s:property value="#index.index"/>" onmouseover="queryComment('<s:property value="#set.REPORT_RECORD_ID"/>',this.id,'input_scrapt_cnt_<s:property value="#index.index%7"/>')">
				<s:if test="#set.INPUT_SCRAPT_CNT != null">
					<s:if test="%{(#index.index) % 7 != 6}">
						<s:set var="input_scrapt_total" value="%{#set.INPUT_SCRAPT_CNT+#input_scrapt_total}" />
					</s:if>
				</s:if>
				<s:else>
					<s:set var="input_scrapt_total" value="%{0+#input_scrapt_total}" />
				</s:else>
				<s:if test="%{(#index.index) % 7==6}">
					<s:property value="#input_scrapt_total"/>
					<s:set var="input_scrapt_total" value="0"/>
				</s:if>
				<s:else>
					<s:property value="#set.INPUT_SCRAPT_CNT"/>
				</s:else>
			</td>
		</s:iterator>
		</tr>

		<tr>
			<td>来料报废率</td>
		<s:iterator value="dates" id="date">
			<td colspan="3" align="center"><div id="input_scrapt_rate_day">白班来料报废率</div></td>
			<td colspan="3" align="center"><div id="input_scrapt_rate_night">夜班来料报废率</div></td>
			<td></td>
		</s:iterator>
		</tr>

		<tr>
			<td>制程报废数</td>
			<s:set var="process_scrapt_total" value="0"></s:set>
		<s:iterator value="setid" id="set" status="index">
			<td id="process_scrapt_cnt_<s:property value="#i.index"/><s:property value="#index.index"/>" onmouseover="queryComment('<s:property value="#set.REPORT_RECORD_ID"/>',this.id,'process_scrapt_cnt_<s:property value="#index.index%7"/>')">
				<s:if test="#set.PROCESS_SCRAPT_CNT != null">
					<s:if test="%{(#index.index) % 7 != 6}">
						<s:set var="process_scrapt_total" value="%{#set.PROCESS_SCRAPT_CNT+#process_scrapt_total}" />
					</s:if>
				</s:if>
				<s:else>
					<s:set var="process_scrapt_total" value="%{0+#process_scrapt_total}" />
				</s:else>
				<s:if test="%{(#index.index) % 7==6}">
					<s:property value="#process_scrapt_total"/>
					<s:set var="process_scrapt_total" value="0"/>
				</s:if>
				<s:else>
					<s:property value="#set.PROCESS_SCRAPT_CNT"/>
				</s:else>
			</td>
		</s:iterator>
		</tr>

		<tr>
			<td>制程报废率</td>
		<s:iterator value="dates" id="date">	
			<td colspan="3" align="center"><div id="p_scrapt_rate_day">白班制程报废率</div></td>
			<td colspan="3" align="center"><div id="p_scrapt_rate_night">夜班制程报废率</div></td>
			<td></td>
		</s:iterator>
		</tr>
		<!-- 
		<tr>
			<td>良品入库累计</td>
		<s:iterator value="dates" id="date">	
			<td colspan="7" align="center"><div>良品入库累计</div></td>
		</s:iterator>
		</tr> -->
	</tbody>
	</s:iterator>
	</table>
</form>
</body>
</html>