<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>现场阶段报表</title>

<link rel="stylesheet" type="text/css" href="css/imageTable.css">
<link rel="stylesheet" type="text/css" href="css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="css/themes/icon.css">

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="js/jobHandle.js"></script>
</head>
<body onload="setAttribute()">
	<form id="report" name="report" action="saveReport" method="post">
	<div>
        <a href="#" class="easyui-linkbutton" onclick="javascript:$('#p').panel('open')">查看帮助信息</a>
        <a href="#" class="easyui-linkbutton" onclick="javascript:$('#p').panel('close')">隐藏帮助信息</a>
    </div>
	<div id="p" class="easyui-panel" data-options="closed:true" title="帮助信息" style="width:100%;height:'auto';">
        <ul>
            <li>所有各段输入时间限制为：开始时间推后1小时，结束时间推后半小时。如08:00-12:00段可录入时间为09:00-12:30.超过此段时间系统自动冻结输入框。</li>
            <li>录入数据必须在4H进度前选择需要录入的ERP料号后系统才允许录入报表数据。</li>
            <li><font color="red"><b>批注与补差录入：批注与补差必须在点击保存数据后才可以输入。</b></font>输入方式为,鼠标右键选中要录入批注/补差的单元格，选择自己需要录入的项目，输入数据。</li>
       		<li>白班数据：当天8点开始到晚8点结束的班次数据。夜班数据：当前晚8到次日早上8点的班次数据。夜班早8点数据录入时间范围是凌晨5点到8:30之间。</li>
       		<li>数量补差：如果保存数据后，需要对已录入的数据进行修改，可鼠标右键选中要补差的格子，选择数量补差，输入更新后的数据，确定保存即可。</li>
       		<li>报表数据填写：报表页面只能填写<font color="red">红色时间段</font>内白色的格子。其他格子不可编辑。当日产出、投入数系统会在填完必要数据格子后自动计算。</li>
       	</ul>
    </div>
	
	<table class="imagetable" align="center" width="100%">
		<tbody>
			<tr>
				<th colspan="3" width="30%">
					<input style="width: 280px" name="stage_report_setting_id" id="stage_report_setting_id" class="easyui-combobox" data-options="
		                data: <s:property value="listReportSettingJSON"/>,
		                method: 'get',
		                valueField: 'STAGE_REPORT_SETTING_ID',
		                textField: 'ERP_PROCESS_ID',
		                panelWidth: 310,
		                panelHeight: 400,
		                formatter: formatItem,
		                groupField:'MODEL_NAME',
		                onSelect:function(param){
							getSetting(param.STAGE_REPORT_SETTING_ID,<s:property value="yyyy"/>,<s:property value="MM"/>,<s:property value="dd"/>,<s:property value="HH"/>,<s:property value="mm"/>);
						}
					">-4H进度
				    <script type="text/javascript">
				        function formatItem(row){
				            var s = '<span style="color:#888">' +'工序：<font color=black>'+row.PROCESS +'</font>;ERP料号：<font color=black>'+row.ERP_PROCESS_ID + '</font></span>';
				            return s;
				        }
				    </script>
				       
					<input id="json" name="json" type="hidden" value="<s:property value='listReportSettingJSON'/>"/>
					
					<input id="bid" value="<s:property value='stage_report_setting_id'/>">
				</th>
				<th colspan="6" width="50%">
					<div id="date"></div>
				</th>
			</tr>
			<tr>
				<th rowspan="2">制程料号</th>
				<th rowspan="2">工序</th>
				<th rowspan="2">项目</th>
				<th id="8-12">08:00-12:00</th>
				<th id="12-16">12:00-16:00</th>
				<th id="16-20">16:00-20:00</th>
				<th id="20-24">20:00-24:00</th>
				<th id="24-4">24:00-04:00</th>
				<th id="4-8">04:00-08:00</th>
			</tr>
			<tr>
				<th colspan="3">白班</th>
				<th colspan="3">晚班</th>
			</tr>
			<tr bgcolor="lightblue">
				<td rowspan="13">
					<div id="erp_process_id"></div>
				</td>
				<td rowspan="13">
					<div id="process"></div>
				</td>
				<td bgcolor="lightblue">目标日产能</td>
				<td bgcolor="lightblue" colspan="6" align="center"><div>目标日产能</div></td>
			</tr>
			<tr bgcolor="lightblue">
				<td>当日产出</td>
				<td colspan="6" align="center"><div id="day_output_cnt"></div></td>
			</tr>
			<tr bgcolor="lightblue">
				<td>投入数量</td>
				<td><input id="input_cnt_0" size="10"/></td>
				<td><input id="input_cnt_1" size="10"/></td>
				<td><input id="input_cnt_2" size="10"/></td>
				<td><input id="input_cnt_3" size="10"/></td>
				<td><input id="input_cnt_4" size="10"/></td>
				<td><input id="input_cnt_5" size="10"/></td>
			</tr>
			<tr>
				<td>生产良品数量</td>
				<td><input id="output_cnt_0" size="10"/></td>
				<td><input id="output_cnt_1" size="10"/></td>
				<td><input id="output_cnt_2" size="10"/></td>
				<td><input id="output_cnt_3" size="10"/></td>
				<td><input id="output_cnt_4" size="10"/></td>
				<td><input id="output_cnt_5" size="10"/></td>
			</tr>
			<tr>
				<td>来料不良数</td>
				<td><input id="input_ng_cnt_0" size="10" /></td>
				<td><input id="input_ng_cnt_1" size="10" /></td>
				<td><input id="input_ng_cnt_2" size="10" /></td>
				<td><input id="input_ng_cnt_3" size="10" /></td>
				<td><input id="input_ng_cnt_4" size="10" /></td>
				<td><input id="input_ng_cnt_5" size="10" /></td>
			</tr>
			<tr bgcolor="lightblue">
				<td>来料不良率</td>
				<td colspan="3" align="center"><div id="input_ng_rate_day">白班来料不良率</div></td>
				<td colspan="3" align="center"><div id="input_ng_rate_night">夜班来料不良率</div></td>
			</tr>
			<tr>
				<td>制程不良数</td>
				<td><input id="process_ng_cnt_0" size="10" /></td>
				<td><input id="process_ng_cnt_1" size="10" /></td>
				<td><input id="process_ng_cnt_2" size="10" /></td>
				<td><input id="process_ng_cnt_3" size="10" /></td>
				<td><input id="process_ng_cnt_4" size="10" /></td>
				<td><input id="process_ng_cnt_5" size="10" /></td>
			</tr>
			<tr bgcolor="lightblue">
				<td>制程不良率</td>
				<td colspan="3" align="center"><div id="p_ng_rate_day">白班制程不良率</div></td>
				<td colspan="3" align="center"><div id="p_ng_rate_night">夜班制程不良率</div></td>
			</tr>
			<tr>
				<td>来料报废数</td>
				<td><input id="input_scrapt_cnt_0" size="10" /></td>
				<td><input id="input_scrapt_cnt_1" size="10" /></td>
				<td><input id="input_scrapt_cnt_2" size="10" /></td>
				<td><input id="input_scrapt_cnt_3" size="10" /></td>
				<td><input id="input_scrapt_cnt_4" size="10" /></td>
				<td><input id="input_scrapt_cnt_5" size="10" /></td>
			</tr>

			<tr bgcolor="lightblue">
				<td>来料报废率</td>
				<td colspan="3" align="center"><div id="input_scrapt_rate_day">白班来料报废率</div></td>
				<td colspan="3" align="center"><div id="input_scrapt_rate_night">夜班来料报废率</div></td>
			</tr>
			<tr>
				<td>制程报废数</td>
				<td><input id="process_scrapt_cnt_0" size="10" /></td>
				<td><input id="process_scrapt_cnt_1" size="10" /></td>
				<td><input id="process_scrapt_cnt_2" size="10" /></td>
				<td><input id="process_scrapt_cnt_3" size="10" /></td>
				<td><input id="process_scrapt_cnt_4" size="10" /></td>
				<td><input id="process_scrapt_cnt_5" size="10" /></td>
			</tr>
			<tr bgcolor="lightblue">
				<td>制程报废率</td>
				<td colspan="3" align="center"><div id="p_scrapt_rate_day">白班制程报废率</div></td>
				<td colspan="3" align="center"><div id="p_scrapt_rate_night">夜班制程报废率</div></td>
			</tr>
			<tr bgcolor="lightblue">
				<td>良品入库累计</td>
				<td colspan="6" align="center"><div>良品入库累计</div></td>
			</tr>
			<tr>
				<td colspan="9" style="text-align:center">
					<input type="button" style="font-size: 30px;border-width:1;border-color: black" id="btnSave" onclick="confirmForm('stage_report_setting_id',<s:property value="yyyy"/>,<s:property value="MM"/>,<s:property value="dd"/>,<s:property value="HH"/>,<s:property value="mm"/>)" value="保存数据" />
				</td>
			</tr>
		</tbody>
	</table>

	<input type="hidden" id="current_id"/>
	<input type="hidden" name="time_zone" id="time_zone"/>
	</form>
	<s:property value="#setting_id"/>
	<div id="mm" class="easyui-menu" data-options="onClick:menuHandler" style="width:150px">
	    <div data-options="name:'addNote'" id="addNote">添加批注</div>
	    <div class="menu-sep"></div>
	    <div data-options="name:'append'" id="append">数量补差</div>
	</div>
	<div id="newNote"  class="easyui-window" title="添加批注" data-options="modal:true,closed:true,iconCls:'icon-save'" >
		<textarea rows="9" cols="100" id="editor1"></textarea>
		<script>
		// Replace the <textarea id="editor"> with an CKEditor
		// instance, using default configurations.
		/*var editor = CKEDITOR.replace('editor1', {
		toolbar :
        [
           ['Bold','Italic','Underline','-','NumberedList','BulletedList'],
           ['Styles','Format','Font','FontSize','-','TextColor','BGColor']
         ]
		});*/
		
		function saveComment(){
			$("#"+$("#current_id").val()).attr("title",$('#editor1').val());
			$.post(
				"saveComment",
				{comment:$('#editor1').val(),append_id:$("#current_id").val(),report_record_id:$("#"+$("#current_id").val()).attr("alt")},
				function(data,status){
					var msg1;
					if (data == 0)
						msg1 = '批注添加成功';
					else
						msg1 = '批注添加失败，请重新添加。';
					$.messager.show({
		                title:'系统提示',
		                msg:msg1,
		                timeout:2000,
		                showType:'slide',
		                style:{
		                    right:'',
		                    top:document.body.scrollTop+document.documentElement.scrollTop,
		                    bottom:''
		                }
		            });
				}
			);
			$('#editor1').val('');
			$('#newNote').window('close');
		}
		</script>
		<div align="center">
			<a class="easyui-linkbutton" data-options="toggle:true,group:'g2'" onclick="saveComment()">录入批注</a>
		</div>
	</div>
	<div id="append_div"  class="easyui-window" title="请输入要补差的数量" data-options="modal:true,closed:true,iconCls:'icon-save'">
		<div><br/>
			<textarea rows="1" cols="30" id="append_input" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></textarea></div>
			<br/>
		<div align="center">
			<a class="easyui-linkbutton" data-options="toggle:true,group:'g1'" onclick="saveAppend()">确定</a>
			<a class="easyui-linkbutton" data-options="toggle:true,group:'g1'" onclick="$('#append_div').window('close');">取消</a>
		</div>
	</div>
</body>
</html>