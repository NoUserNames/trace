<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送短信</title>
    <link rel="stylesheet" type="text/css" href="css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="css/demo.css">
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" href="css/sms/jquery-ui.css">

    <script type="text/javascript">
    	function sendSMS(){
    		var t = $("input[name='gp1']:checked").val();
    		t = t.substring(t.indexOf('_')+1);
    		var g = $('#cc_'+t).combobox('getValues');
    		if(g == ''){
    			$.messager.alert('警告','请选择收件人','warning');
    			return false;
    		}
    		
    		var grid = $('#cc_'+t).combogrid('grid');
    		var r = grid.datagrid('getSelected');
    		
    		var msg = f.message.value;
    		if($.trim(msg) == ''){
    			$.messager.alert('警告','系统不允许发送空白短信，请输入内容。','warning');
    			f.message.value = "";
    			tip.innerHTML="0";
    			return false;
    		}
    		$.post(
    			"sms.action",
    			{
    				reveice: ""+g+"", message: msg, gp:div_name = $("input[name='gp1']:checked").val(),
    				reveice_name:$('#cc_'+t).combobox('getText'),selected_member_hidden:$("#selected_member_hidden").val(),
    				g_member_hidden:$("#g_member_hidden").val()
    			},
				function(data,status){
	    			if('true' == data){
	    				$.messager.alert('系统提示','短信息已发送。','info');
	    			}else{
	    				$.messager.alert('出错啦',data,'error');
	    			}
				}
    		);
	    }

    	function chkType(id){
    		var div_name = $("input[name='gp1']:checked").val();
    		$("#"+div_name).show();
    		$("#div_"+id).hide();
    		if(id=='p'){
    			$("#g_member").show();
    		}else{
    			$("#g_member").hide();
    		}
    	}
	    	
    	function getChk(name){
	    	$.each(JSON.parse(name), function (n, value) {
	    		g_member_view.innerHTML += "<span id='"+value.mobile_number+"'><input type='checkbox' onchange='doChange(this.checked,this.value)' name='g_p' value='"+value.emp_name+","+value.mobile_number+"'/>"+value.emp_name+'</span>';
	    		var content = $("#g_member_hidden").val() + value.emp_name+","+value.mobile_number+";";
	    		$("#g_member_hidden").val(content);
	    	})
    	}

    	function removes(id){
    		$.each(JSON.parse(id), function (n, value) {
	    		$("#"+value.mobile_number).remove();
	    		$("#g_member_hidden").val($("#g_member_hidden").val().replace(value.emp_name+","+value.mobile_number+";",''));
			})
    	}

    	function doChange(checked,value){
    		var number = value.substring(value.indexOf(',')+1);
    		var name = value.substring(0,value.indexOf(','));
    		if(checked){
    			var content = $("#selected_member_hidden").val() + value+';';
    			$("#selected_member_hidden").val(content);
    		}else{
    			var content = $("#selected_member_hidden").val();
    			content = content.replace(value+';','');
    			$("#selected_member_hidden").val(content);
    			$("#"+number+"1").remove();
    		}
    	}
    </script>
	<script language="javascript" type="text/javascript">
		$(function(){
			$("#selectAll").click(function() {
				$("input[name='g_p']").each(function() {
					$(this).attr("checked", true);
				});
			});

			$("#unSelect").click(function() {
				$("input[name='g_p']").each(function() {
					$(this).attr("checked", false);
				});
			});

			$("#reverse").click(function() {//反选
				$("input[name='g_p']").each(function() {
					this.checked = !this.checked;
				})
			});

			$("#div_g").hide();
			$("#g_member").hide();
		});
	</script>
</HEAD>
<BODY>
<div class="easyui-panel" style="height: 400px" title="提示：短信接收人员只能从通讯录中选取，所以请先维护好通讯录。超过70字会自动拆分为多条短信。">
<form id="f" action="sms" method="post" data-options="novalidate:true">
<center>
	<input type="radio" name="gp1" value="div_p" id="g" onclick="chkType(this.id)" checked="checked"/><b>按个人发送</b>
	<input type="radio" name="gp1" value="div_g" id="p" onclick="chkType(this.id)"/><b>按群组发送</b>

	<div id="div_p" style="width:100%" class="easyui-tooltip" title="此为必填项。">
		<select class="easyui-combogrid" style="width:80%" id="cc_p" data-options="
            panelWidth: div_p.width,
            multiple: true,
            idField: 'mobile_number',
            textField:'emp_name',
            data: <s:property value="json"/>,
            method: 'get',
            rownumbers:true,
            editable:true,
            columns: [[
                {field:'ck',checkbox:true},
                {field:'emp_name',title:'员工姓名',width:80,align:'center'},
                {field:'emp_no',title:'工号',width:120,align:'center'},
                {field:'mobile_number',title:'手机号码',width:120,align:'center'},
                {field:'factory_id',title:'厂区',width:50,align:'center'},
                {field:'create_time',title:'建立时间',width:200,align:'center'}
            ]],
            fitColumns: true,prompt:'点击后面向下箭头选择接收名单，按单个号码组织发送名单。可以输入接收人姓名并以英文逗号(,)分隔，系统会自动选择符合条件的选项。',
        	onSelect:function(index,row){
        		
			}
        "></select>
	</div>

	<div id="div_g" class="easyui-tooltip" title="此为必填项。">
		<select class="easyui-combogrid" style="width:80%" id="cc_g" data-options="
            panelWidth: div_g.width,
            multiple: true,
            idField: 'group_id',
            textField: 'group_name',
            data: <s:property value="cg"/>,
            panelHeight:400,
            method: 'get',
            rownumbers:true,
            editable:false,
            columns: [[
                {field:'ck',checkbox:true},
                {field:'group_name',title:'群组名称',width:80,align:'center'},
                {field:'group_desc',title:'群组描述',width:120,align:'center'},
                {field:'create_time',title:'建立时间',width:200,align:'center'}
            ]],
            onSelect:function(index,row){
        		$.post(
        			'queryContactsByGroup',
        			{group_id:row.group_id},
					function(data,status){
						getChk(data);
					}
				);
	    	},
	    	onUnselect:function(index,row){
	    		$.post(
        			'queryContactsByGroup', 
        			{group_id:row.group_id},
					function(data,status){
		   				removes(data);
					}
				);
	    	},
            fitColumns: true,prompt:'按群组组织发送名单，点击此处选择群组信息。'
        "></select>
	</div>

	<div style="margin: 10px 0 10px 0;"></div>
	<div id="g_member"><div id="g_member_view" class="easyui-panel" style="width: 80%; height: 100px;" title="提示：以下为群组成员。如果不选默认以下成员全部发送，如果选择，则只发送选中的成员。"></div></div>
	<input type="hidden" id="g_member_hidden" name="g_member_hidden"/>
	<div class="easyui-panel" id="d1" style="width: 80%; height: 190px;"
		title="请输入短信内容&nbsp;(<font color='red'><span id='tip'>0</span></font>/<font color='red'><span id='tip1'>0</span></font>)">
		<div>
		<textarea id="message" style="width: 99.3%;font-size: 16px" rows="7"
			onkeyup="tip.innerHTML=this.value.length;tip1.innerHTML=(Math.ceil(this.value.length / 70))" ></textarea>
		</div>
		<div align="center">
			<a href="#" class="easyui-linkbutton" style="width:120px" onclick="sendSMS()">发送短信</a>
			<a href="#" class="easyui-linkbutton" style="width:120px" onclick="message.value='';tip.innerHTML=0,tip1.innerHTML=0">清空内容</a>
		</div>
	</div>
	<input type="hidden" id="selected_member_hidden" name="selected_member_hidden"/>
	</center>
</form>

</div>
</body>
</HTML>