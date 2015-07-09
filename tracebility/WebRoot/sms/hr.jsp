<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>短信模块</title>
	
	<script type="text/javascript">
	function sendSMS(){
		var g = $('#uploadify').val();
		if(g == ''){
			alertMsg.error('请先导入名单列表，然后点击上传附档上传到系统中。');
			return false;
		}
		
		var msg = $("#message").val();
		if($.trim(msg) == ''){
			alertMsg.error('系统不允许发送空白短信,请输入短信内容。');
			clearMsg();
			return false;
		}

		$.post(
			"sms.action",
			{
				message: msg, gp:"div_other", upload:$('#uploadify').val()
			},
			function(data,status){
    			if('true' == data){
    				alert('短信息已发送');
    				clearMsg();
    			}else{
    				alert('出错啦');
    			}
			}
		);
    }
	
	function clearMsg(){
		$("#uploadify").val("");
		$("#message").val("");
		tip.innerHTML = "0";
		tip1.innerHTML = "0";
	}
	</script>
</head>
<body>
	<form id="hrsms" action="doUpload" method="post" enctype="multipart/form-data" onsubmit="return iframeCallback(this);">
		<div class="pageHeader">
			<input type="file" style="font-size:30" name="upload" id="uploadify" width="60%"/>
			<input type = "submit" style="font-size:23" value="上传附档">
		</div>
	</form>

	<div id="d1" style="width: 100%; height: 170px;">
		<div class="panel">
			<h1>请输入短信内容(<font color='red'><span id='tip'>0</span></font>/<font color='red'><span id='tip1'>0</span></font>)，超过70字会自动拆分为多条短信。</h1>
			<textarea id="message" style="width: 100%;font-size: 16px" rows="7" onkeyup="tip.innerHTML=this.value.length;tip1.innerHTML=(Math.ceil(this.value.length / 70))" ></textarea>
		</div>
	</div>

	<div align="center">
		<button onclick="sendSMS()">发送短信</button>
		<button onclick="message.value='';tip.innerHTML=0,tip1.innerHTML=0">清空内容</button>
	</div>

		<div class="pageHeader" layoutH=70>
		<div class="searchBar">
			<div style="font-size: 20">
				<b>1.导入模板</b>
			</div>
			<li>
				<div style="font-size: 15">请先导入待发送名单，点击上传附档上传到系统中。</div>
			</li>
			<br/>
			<div style="font-size: 20">
				<b>2.输入短信内容</b>
			</div>
			<li>
				<div style="font-size: 15">
					在短信编辑框中输入要发送的短信，然后点击发送短信。系统会给出相应的提示结果。
				</div>
			</li>
			<li>
				<div style="font-size: 15">
					模板格式为从首列开始，第一列为姓名，第二列为手机号码。如不清楚可
					<a href="upload\model\WIPModel.xlsx">
					<div style="font-size: 15;display: inline;text-decoration: none;">【点此下载模板】</div>
					</a>使用模板导入。
				</div>
			</li>
		</div>
	</div>
</body>
</html>