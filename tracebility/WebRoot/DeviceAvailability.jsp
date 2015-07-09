<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>设备扫描率</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 
	<!--<link rel="stylesheet" type="text/css" href="./css/styles.css">
	<link href="themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
	<link href="themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
	-->
	<!--[if IE]>
	<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
	<![endif]-->
	
	<!--[if lte IE 9]>
	<script src="js/speedup.js" type="text/javascript"></script>
	<![endif]-->
	<script src="js/jobHandle.js" type="text/javascript"></script>
	<script type="text/javascript">	
	var flag = 1;
	function timedifference(){
		var st1 = document.getElementById('start').value;
		var st2 = document.getElementById('end').value;
		
		var time1 = new Date(st1);                                //创建时间1
		var time2 = new Date(st2);                                //创建时间2
		/*
		*如果求的时间差为天数则处以864000000，如果是小时数则在这个数字上
		*除以24，分钟数则再除以60，依此类推
		*/			
		var days = (time2.getTime() - time1.getTime()) / 86400000;
		var bool = false;
		
		if(days > 14){
			if(flag ==1){
				var pwd = prompt("你选择的时间跨度超过2周,请输入密码","");
				if(pwd=='ri-teng1234'){
					bool = true;
					flag = 2;
				}else{
					alert('密码错误');
					bool = false;
				}
			}		
		}else{
			bool = true;
		}
		if (bool){
			document.getElementById('btn').type='submit';
			document.getElementById('btn').click();
		}
	}

	</script>
  </head>
  
  <body> 
   <form onsubmit="return navTabSearch(this)" action="DeviceAvailability" method="post">
	<div class="searchBar">
	 <table class="searchContent">
			<tr>
				<td>
					开始日期：<input id="start" name="start" class="date"  readonly="readonly"/><font color="red">*</font>
				</td>
				<td>
					结束日期：<input id="end" name="end"  class="date" readonly="readonly" /><font color="red">*</font>
				</td>
				<td>
					扫描量低于：<input id="cnt" name="cnt" size="5" onkeyup="this.value=this.value.replace(/\D/g,'')"/><!-- onkeyup="this.value=this.value.replace(/\D/g\s,'')" -->
					<font color="red">*</font><font color="red">(时间区段内扫描数量少于此量的记录)</font>	
				</td>
			</tr>
		</table>
		<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btn" type="button" onclick="timedifference()">检索</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button name="btn" id="btn" type="button" onclick="test()" disabled="disabled">一键显示站点名称</button></div></div></li>				
				</ul>
			</div>
		</div>
	</form>	
	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<li><a class="icon" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="100">
  		<thead>
	  		<tr>
	  			<th width="50px">序号</th>
	  			<th width="400px">站点ID</th>
	  			<th width="400px">站点名称</th>
	  			<th>总量(单位/片)</th>
	  		</tr>
  		</thead>
  		<tbody>
  		<s:form name="view">
  		<script type="text/javascript">
  		function checkkey(index){                
			var params=document.getElementById('deviceid'+index).value;// 
			$.ajax({
				type: 'get',                               //请求方式为post方式
				url: 'getDevTerminalName.action',          //请求地址
				dataType: 'text',                          //服务器返回类型为字符串类型
				data:{'params':params},                    //发送到服务器的数据
				success:function(data){
					document.getElementById('terName'+index).innerHTML = data;
				},error:function (data){
					alert('获取触发规则出错');
				}
	      	}); 
		}
		function test(){
			var size = document.getElementById('size').value;
			alert(size); 
			var flag = true;
			for(var i = 1;i<=size;i++){
				 document.getElementById('terName'+i).onclick();
				//$('terName'+i).click();　
			}
		}
  		</script>
  		<s:iterator value="listR" id="device" status="index">
  		<tr ><!-- <s:if test='#device.cnt < 500'>style="background-color: gray;"</s:if> -->
  			<td width="47px">
  				${index.index +1 }
  			</td>
  			<td width="385px">
  				<s:property value="#device.terminal_id"/>
  			</td>
  			<td width="385px">
  				<div id="terName${index.index +1 }" onclick="checkkey(${index.index +1 })">点击显示站名</div>
  				<!--<div id="terName${index.index +1 }" >123</div>-->
  				<s:hidden id="deviceid%{#index.index +1}" value="%{#device.terminal_id}"></s:hidden>  			   
  			</td>
  			<td>
  				<s:property value="#device.cnt"/>
  			</td>
  		</tr>
  		</s:iterator>
  		</s:form>
  		</tbody>
  	</table>			
  </body>
</html>