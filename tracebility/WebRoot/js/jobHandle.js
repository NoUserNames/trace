function chkLogin(){
	var type = document.getElementById('type').value;
	var uid = $("#uName").val();
	var upwd = $("#uPwd").val();
	if(uid == '' || upwd == ''){
		document.getElementById('requisite').style.display = 'block';
		return;
	}else{
		document.getElementById('login').submit();
	}
}
function chkLabel(value){
	if(value == '1'){
		document.getElementById('label').innerHTML = '工&nbsp;&nbsp;&nbsp;号：';
	}else{
		document.getElementById('label').innerHTML = '域帐号：';
	}
}

function AddFavorite(sURL, sTitle) {
	try {
		window.external.addFavorite(sURL, sTitle);
	} catch (e) {
		try {
			window.sidebar.addPanel(sTitle, sURL, "");
		} catch (e) {
			alert("加入收藏失败，请使用Ctrl+D进行添加");
		}
	}
}

function SetHome(obj, vrl) {
	try {
		obj.style.behavior = 'url(#default#homepage)';
		obj.setHomePage(vrl);
	} catch (e) {
		if (window.netscape) {
			try {
				netscape.security.PrivilegeManager
						.enablePrivilege("UniversalXPConnect");
			} catch (e) {
				alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。");
			}
			var prefs = Components.classes['@mozilla.org/preferences-service;1']
					.getService(Components.interfaces.nsIPrefBranch);
			prefs.setCharPref('browser.startup.homepage', vrl);
		}
	}
}

function go(id){
	location.hash = '#'+id;
}

function test2(name) {
	if (document.all) {
		document.getElementById(name).click();
	} else {
		var evt = document.createEvent("MouseEvents");
		evt.initEvent("click", true, true);
		document.getElementById(name).dispatchEvent(evt);
	}
}

function sub_serial(id){
	var serial = document.getElementById(id).value;
	if(serial==""){
		alertMsg.error('条件不完整,请输入产品序列号或FATP标签!');
		return false;
	}else{
		navTabSearch(document.getElementById('myForm'));
	}
}

function checkRequired(id,formID,desc){
	var serial = document.getElementById(id).value;
	if(serial==""){
		alertMsg.error(desc+'没有输入，请输入后重新提交！');
	}else{
		navTabSearch(document.getElementById(formID));
	}
}

function queryCartons(tid,formID,desc){
	var cartons = document.getElementById(tid).value;

	if(cartons.length ==0){
		alertMsg.error(desc+'没有输入，请输入后重新提交！');
		return false;
	}else{
		navTabSearch(document.getElementById(formID));
	}
}

function clearup(id){
	document.getElementById(id).value = "";
}

function expCarton(){
	var cartonNO = document.getElementById('cartonNO').value;
	$.ajax({
		type: 'get',//请求方式为post方式
		url: 'exprot.action',//请求地址
		dataType: 'text',//服务器返回类型为字符串类型
		data:{'cartonNO':cartonNO},//发送到服务器的数据
		success:function(data){
			//document.getElementById('export').innerHTML = data;
			document.getElementById('exp').href=data;
			document.getElementById('exp').click();
		},error:function (data){
			alert('导出excel出错');
		}
	});
}

function logout(){
	invalidate();
	window.location.href='login.jsp';
}

function invalidate() {
	$.ajax({
		type : 'get',// 请求方式为get方式
		url : 'doLoginOut'// 请求地址
//		dataType : 'text',// 服务器返回类型为字符串类型
//		success : function(data) {
//			if (data = 'ok') {
//				window.location.href = 'login.jsp';
//			}
//		},
//		error : function(data) {
//			alert('注销出错');
//		}
	});
}

/***  按机种、制程、站点查不良  --开始 ***/
var oInputField ,oPopDiv , oColorsUl,aColors;  
//初始化变量 
function initVars(modelId,divId,ulId){
    oInputField = document.getElementById(modelId);
    oPopDiv = document.getElementById(divId);
    oColorsUl = document.getElementById(ulId);
}

//查机种、料号 自动补全
function findModel(div_id,ul_id,model_id,form_id,p_cbo_id){
 	var keyCode = event.keyCode;
 	var arrayStr ='';
	initVars(model_id,div_id,ul_id);
	var aResult = new Array();  //用于存放匹配结果
    var params = document.getElementById(model_id).value; //序列化表格数据"myForm1"为表格的id
    if((keyCode >= 65 && keyCode <= 90) || (keyCode >= 48 && keyCode <= 57) || keyCode == 8 || keyCode == 46){//要过滤的键位 字母、数字、删除键
		if(oInputField.value.length >= 2){//超过两位开始查
        $.ajax({
	    	type:'post',
	        data:{'modelName':params},
	        dataType:'json',
	        url:'queryPart.action',
	        success:function(data) {
	           	if(data == "error" || data == null || data == ""){
	                clearModel();
	                return;
	            }
	            document.getElementById(div_id).style.display = 'block';
		        $.each(data,function(index,element){ 
					arrayStr +=element.model_name+','+element.part_no+','+element.route_id+'$';
				});
		        aResult = arrayStr.split("$");
		        setModel(model_id,div_id,aResult,p_cbo_id);
			}
	     });
		}else{
			clearModel();//无输入时清除提示框
		}
	}
 }
//清除提示内容
function clearModel(){
    for(var i = oColorsUl.childNodes.length - 1 ; i >= 0 ; i-- )
    	oColorsUl.removeChild(oColorsUl.childNodes[i]);
    oPopDiv.className = "hide";
}
//设置自动补全数据  
function setModel(modelId,divId,the_models,p_cbo_id){
    //显示提示框、传入的参数即为匹配出来的结果组成的数组
    clearModel();//每输入一个字母就先清除原先的提示、再继续
    oPopDiv.className = "show";
    var oLi;
 	
    for(var i = 0 ; i < the_models.length ; i++ ){
        //将匹配的提示结果逐一显示给用户
        oLi = document.createElement("li");
        oColorsUl.appendChild(oLi);
        oLi.appendChild(document.createTextNode(the_models[i]));
        
        oLi.onmouseover = function(){
            this.className = "mouseOver" ;  //鼠标指针经过时高亮
        }
        oLi.onmouseout = function(){
            this.className = "mouseOut" ;   //鼠标指针离开时恢复原样
        }
        oLi.onclick = function(){
            //用户单击某个匹配项时、设置输入框为该项的值
            oInputField.value = this.firstChild.nodeValue;
            clearModel();//同时清除提示框
            document.getElementById(divId).style.display = 'none';
            getProcessNames(modelId,p_cbo_id);
        }
    }
}

//查制程
function getProcessNames(modelId,p_cbo_id){
	var routeid = document.getElementById(modelId).value;
	var tmp = routeid;
	routeid = routeid.substring(routeid.lastIndexOf(',')+1,routeid.legth);
	$.ajax({
	     type:'get',
	     data:{'routeID':routeid},
	     dataType: 'json',
	     url:'queryProcessByRoute.action',
	     success:function(data) {
	        if(data == "error" || data == null || data == ""){
	            return;
	        }
	        document.getElementById(p_cbo_id).innerHTML = '';
	        document.getElementById(p_cbo_id).options.add(new Option('--请选择制程--','0'));
	     	$.each(data,function(index,element){ 
				var option = document.createElement('OPTION');
				option.text = element.process_name;
				option.value = element.process_id;
				document.getElementById(p_cbo_id).options.add(option);
			});
		 }
	 });
	document.getElementById(modelId).value = tmp;
}

//下拉框方式获取制程
function addProcessCBO(value,p_cbo_id){
	value = value.substring(0,value.lastIndexOf(','));
	$.ajax({
	     type:'get',
	     data:{'routeID':value},
	     dataType: 'json',
	     url:'queryProcessByRoute.action',
	     success:function(data) {
	        if(data == "error" || data == null || data == ""){
	            return;
	        }
	        document.getElementById(p_cbo_id).innerHTML = '';
	        document.getElementById(p_cbo_id).options.add(new Option('--请选择制程--','0'));
	     	$.each(data,function(index,element){ 
				var option = document.createElement('OPTION');
				option.text = element.process_name;
				option.value = element.process_id;
				document.getElementById(p_cbo_id).options.add(option);
			});
		 }
	 });
}

//利用隐藏域传值到action
function setHidden(cbxid,hidvalue){
	document.getElementById(cbxid).value = hidvalue;
}

//查站点 共用
function queryTerminalByProcess(value,t_cbo_id_wip,hid,cbotext){
	$.ajax({
      type:'get',
      data:{'processID':value},
      dataType:'json',
      url:'queryTerminalByProcess.action',
      success:function(data) {
         if(data == "error" || data == null || data == ""){
             return;
         }
        document.getElementById(t_cbo_id_wip).innerHTML = '';
		document.getElementById(t_cbo_id_wip).options.add(new Option('--请选择站点--',''));
      	$.each(data,function(index,element){ 
			var option = document.createElement('OPTION');
			option.text = element.terminal_name;
			option.value = element.terminal_id;
			document.getElementById(t_cbo_id_wip).options.add(option);
		});
      }
  });
  if(hid != ''){
  	setHidden(hid,cbotext);
  }
}
/***  按机种、制程、站点查不良 --结束   ***/

//通用下载程序
function download_universal(target){
	window.open(target,'newwindow', 'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
}


/*   queryMissScan.jsp  */
function checkQueryMissScan(partid,pId,form){
	var processName = document.getElementById(pId).value;
	var partNO = document.getElementById(partid).value;
	if(partNO == 0){
		alertMsg.error('还没有选择机种，请选择后重新提交！');
		return false;
	}
	navTabSearch(document.getElementById(form));
}

/**************   checkQueryUniversal 通用查询校验  ****/
function checkQueryUniversal(partid,form,action){
	var partNO = document.getElementById(partid).value;
	if(partNO == '0'){
		alertMsg.error('还没有选择机种，请选择后重新提交！');
		return false;
	}
	document.getElementById(form).action = action;
	navTabSearch(document.getElementById(form));
}

/***************验证旧密码*************/
function checkOldPwd(uid,oldpwd,tipid,id){
	$.ajax({
        type:'get',
        data:{'oldPassword':oldpwd,'uid':uid},
        dataType:'text',
        url:'checkOldPwd.action',
        
        success:function(data) {
     		if(data != 'ok'){
	        	document.getElementById(tipid).style.display = 'inline';
	        	$("#"+id).val("");
			}else{
				document.getElementById(tipid).style.display = 'none';
			}
        },error:function (data){
			alert('出错'+data);
		}
    });
}

/***************用户授权***************/
function checkAssignAuthority(uid,uname,id){
	if($("#"+uid).val() == '' && $("#"+uname).val() == ''){
		alertMsg.error('请至少输入工号或姓名！');
		return false;
	}else{
		navTabSearch(document.getElementById(id));
	}
}

/****************重置密码、删除人员公用函数****************/
function ajaxGet(uid,action){
	var desc = '';
	if(action =='resetPassword')
		desc = '本次操作会把密码重置为默认密码001，并且会被系统记录。<br/>请确认是否继续？';
	else
		desc = '本次操作会永久删除工号'+uid+'，并且会被系统记录。<br/>请确认是否继续？';
	alertMsg.confirm(desc, {
		okCall: function(){
			$.get(action,
				{'uid':uid},
				function(data,status){
					alertMsg.correct(data);
				}
			);
		}
	})
}

/****************禁用/启用公用函数****************/
function userStatus(uid,action,status){
	var desc = '';
	if(status == 1)
		desc = '禁用';
	else
		desc = '启用';
	alertMsg.confirm("本次操作会 <b>"+desc+"</b> 工号<b>"+uid+"</b>登陆权限，并且会被系统记录。<br/>请确认是否继续？", {
		okCall: function(){
			$.get(action,
				{'uid':uid,'status':status},
				function(data,status){
					alertMsg.correct(data);
				}
			);
		}
	})
}

function checkUidExist(uid){
	$.get('checkUidExist',
		{'uid':uid,'status':status},
		function(data,status){
			if(data != 'ok'){
				alertMsg.error('工号已存在');
				$('#uid_new').attr('value','');
				$('#uid_new').attr('class','error');
				return false;
			}
		}
	);
}

/***********机种、制程、站点通用级联函数*************/
function cascadingProcess(form,action){
	if(undefined != document.getElementById('terminalID'))
		document.getElementById('terminalID').value = 0;
	document.getElementById(form).action = action;
	navTabSearch(document.getElementById(form));
}

function cascadingTerminal(form,action,pName,pid){
	if(pName != null){
		document.getElementById(pid).value = pName;
	}
	document.getElementById(form).action = action;
	navTabSearch(document.getElementById(form));
}

/***********出货查询*************/
function shippingQry(form){
	if($('#queryType').val() =='0' || $('#queryValue').val() == ''){
		alertMsg.error('请至少选择一个条件输入后查询!');
		return false;
	}
	navTabSearch(document.getElementById(form));
}

/***********磁铁/55°角查询*************/
function chgType(value){
	switch(value){
		case 'carton':
			document.getElementById('time').style.display = "none";
			document.getElementById('carton').style.display = "block";
			break;
		case 'timeZone':{
			document.getElementById('time').style.display = "block";
			document.getElementById('carton').style.display = "none";
			break;
		}
		default:
			alert("default");
		break;
	}
}

function cartonType(value){
	queryValue.value='';
	if('carton' == value){
		document.getElementById('singleCarton').style.display='block';
		document.getElementById('multCarton').style.display='none';
	}else{
		document.getElementById('singleCarton').style.display='none';
		document.getElementById('multCarton').style.display='block';
	}
}

function qryMagetic() {
	if ($.browser.msie) {
		alert("this is msie");
	} else if ($.browser.safari) {
		alert("this is safari!");
	} else if ($.browser.mozilla) {
		alert("this is mozilla!");
	} else if ($.browser.opera) {
		alert("this is opera");
	} else {
		alert("i don't konw!");
	}
}

/**
 * 维护WIP状态，暂时停用
 * @param serial_number
 * @param functions
 */
function chkFileType(serial_number,functions){
	document.getElementById('function1').value = functions;

	var type = $('input[name="utype"]:checked').val();
	var tt = wipForm.upload.value;
	if("batch" == type){alert('batch');
		var upload = tt.substring(tt.lastIndexOf('.')+1).toUpperCase();
		if(upload != 'XLS' && upload != 'XLSX'){
			alertMsg.error('你选择的是批量操作，必须导入excel文件，请确认导入的文件是Excel！');
			return;
		}
		navTabSearch(document.getElementById('wipForm'));
	}else{
		if(serial_number.length ==0){
			alertMsg.error('你选择的是单个操作，必须输入产品序列号！');
			return;
		}
	}
	navTabSearch(document.getElementById('wipForm'));
}

function change(value) {
	if (value == "one") {
		document.getElementById('one').style.display = 'block';
		document.getElementById('batch').style.display = 'none';
	}
	if (value == 'batch') {
		document.getElementById('one').style.display = 'none';
		document.getElementById('batch').style.display = 'block';
	}
	if (value == 'enabled') {
		document.getElementById('one').style.display = 'none';
		document.getElementById('batch').style.display = 'none';
	}
}

// 监听Enter键自动提交事件
function keyboardEvent() {
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;// 解决浏览器之间的差异问题
	if (keyCode == 13) {
		chkLogin();
	}
}

function keyListening() {
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;// 解决浏览器之间的差异问题
	if (keyCode == 13) {
		return true;
	}else{
		return false;
	}
}

function queryUser(formid){
	if(keyListening()){
		navTabSearch(document.getElementById(formid));
	}
}

function chg(id){
	if(id == 's_serial_number') {
		document.getElementById('r_s_div').style.display = 'block';
		document.getElementById('r_w_div').style.display = 'none';
	}else {
		document.getElementById('r_s_div').style.display = 'none';
		document.getElementById('r_w_div').style.display = 'block';
	}
}

function r_serial(id){
	if(keyListening()){
		navTabSearch(document.getElementById(id));
	}
}

function sub(id,action){
	document.getElementById(id).action = action;
	navTabSearch(document.getElementById(id));
}

//自定义查询 
function chkMetaData(id,form){
	var table = document.getElementById(id).value;
	if(table == ''){
		alertMsg.error('请输入表名后再提交！');
		return false;
	}
	navTabSearch(document.getElementById(form));
}

// qryMeasure.jsp查询量测数据
/**
 * 查询Tiger量测数据
 * @param id1 下拉列表id
 * @param id2 二维码id
 */
function chkMeasureData(id1,id2,id3){
	if($('#'+id1).val() == '0' || $('#'+id2).val() == ''){
		alertMsg.error('请选择要查询的量测类别，并且输入条件后再提交！');
		return false;
	}
	navTabSearch(document.getElementById(id3));
}

//仓库领用查询 
function chkData(id,form){
	var value = document.getElementById(id).value;
	if(value == ''){
		alertMsg.error('请输入查询条件后再提交！');
		return false;
	}
	navTabSearch(document.getElementById(form));
}

//现场阶段报表
function setDateTime() {
//	$("input:not(:button,:reset,:hidden,:submit)").prop("readonly", true);
	var dateTime = new Date();
	var hh = dateTime.getHours();//小时
	var mm = dateTime.getMinutes();//分钟
	var ss = dateTime.getSeconds();//秒钟

	var yy = dateTime.getFullYear();//年份
	var MM = dateTime.getMonth() + 1; //月份-因为1月这个方法返回为0，所以加1
	var dd = dateTime.getDate();//日数

	var week = dateTime.getDay();//周(0~6,0表示星期日)
	var days = [ "日 ", "一 ", "二 ", "三 ", "四 ", "五 ", "六 ", ]

	$("#date").html(yy + "/" + MM + "/" + dd + " 星期" + days[week] +" "+hh+":"+mm+":"+ss);

	setTimeout(setDateTime, 1000);
};

function setAttribute(){
	$("input:not(:button,:reset,:hidden,:submit)").prop("readonly", true);

	$("#input_cnt_0").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#output_cnt_0").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_ng_cnt_0").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_ng_cnt_0").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_scrapt_cnt_0").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_scrapt_cnt_0").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	
	$("#input_cnt_0").mouseout(function (){removeAttribute(this.id,"title")});
	$("#output_cnt_0").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_ng_cnt_0").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_ng_cnt_0").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_scrapt_cnt_0").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_scrapt_cnt_0").mouseout(function (){removeAttribute(this.id,"title")});
	
	$("#input_cnt_1").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#output_cnt_1").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_ng_cnt_1").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_ng_cnt_1").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_scrapt_cnt_1").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_scrapt_cnt_1").mouseover(function(){queryComment(this.alt,this.id,this.id)});

	$("#input_cnt_1").mouseout(function (){removeAttribute(this.id,"title")});
	$("#output_cnt_1").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_ng_cnt_1").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_ng_cnt_1").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_scrapt_cnt_1").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_scrapt_cnt_1").mouseout(function (){removeAttribute(this.id,"title")});
	
	$("#input_cnt_2").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#output_cnt_2").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_ng_cnt_2").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_ng_cnt_2").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_scrapt_cnt_2").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_scrapt_cnt_2").mouseover(function (){queryComment(this.alt,this.id,this.id)});

	$("#input_cnt_2").mouseout(function (){removeAttribute(this.id,"title")});
	$("#output_cnt_2").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_ng_cnt_2").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_ng_cnt_2").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_scrapt_cnt_2").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_scrapt_cnt_2").mouseout(function (){removeAttribute(this.id,"title")});
	
	$("#input_cnt_3").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#output_cnt_3").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_ng_cnt_3").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_ng_cnt_3").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_scrapt_cnt_3").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_scrapt_cnt_3").mouseover(function (){queryComment(this.alt,this.id,this.id)});

	$("#input_cnt_3").mouseout(function (){removeAttribute(this.id,"title")});
	$("#output_cnt_3").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_ng_cnt_3").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_ng_cnt_3").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_scrapt_cnt_3").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_scrapt_cnt_3").mouseout(function (){removeAttribute(this.id,"title")});
	
	$("#input_cnt_4").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#output_cnt_4").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_ng_cnt_4").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_ng_cnt_4").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_scrapt_cnt_4").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_scrapt_cnt_4").mouseover(function (){queryComment(this.alt,this.id,this.id)});

	$("#input_cnt_4").mouseout(function (){removeAttribute(this.id,"title")});
	$("#output_cnt_4").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_ng_cnt_4").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_ng_cnt_4").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_scrapt_cnt_4").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_scrapt_cnt_4").mouseout(function (){removeAttribute(this.id,"title")});
	
	$("#input_cnt_5").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#output_cnt_5").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_ng_cnt_5").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_ng_cnt_5").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#input_scrapt_cnt_5").mouseover(function (){queryComment(this.alt,this.id,this.id)});
	$("#process_scrapt_cnt_5").mouseover(function(){queryComment(this.alt,this.id,this.id)});
	
	$("#input_cnt_5").mouseout(function (){removeAttribute(this.id,"title")});
	$("#output_cnt_5").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_ng_cnt_5").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_ng_cnt_5").mouseout(function (){removeAttribute(this.id,"title")});
	$("#input_scrapt_cnt_5").mouseout(function (){removeAttribute(this.id,"title")});
	$("#process_scrapt_cnt_5").mouseout(function (){removeAttribute(this.id,"title")});

	setDateTime();
}

function getSetting(id,yyyy,MM,dd,hh,mm){
	var obj = eval($("#json").val());
    $(obj).each(function(index) {
        var val = obj[index];
        if(val.STAGE_REPORT_SETTING_ID == id){
        	$("#dept_name").html(val.DEPT_NAME);
        	$("#erp_process_id").html(val.ERP_PROCESS_ID);
        	$("#process").html(val.PROCESS);
        }
    });
    queryReprot(id,yyyy,MM,dd,hh,mm);
}

function queryReprot(id,yyyy,MM,dd,hh,mm){
	$.post(
		"queryReport",
		{stage_report_setting_id:id},
		function(data,status){
			obj = eval(data);
			var tindex = 0;
			$(obj).each(function(index) {
				$("#input_cnt_"+index).removeAttr("alt");
				$("#input_cnt_"+index).val("");

				$("#output_cnt_"+index).removeAttr("alt");
				$("#output_cnt_"+index).val("");

				$("#input_ng_cnt_"+index).removeAttr("alt");
				$("#input_ng_cnt_"+index).val("");

				$("#process_ng_cnt_"+index).removeAttr("alt");
				$("#process_ng_cnt_"+index).val("");

				$("#input_scrapt_cnt_"+index).removeAttr("alt");
				$("#input_scrapt_cnt_"+index).val("");
				
				$("#process_scrapt_cnt_"+index).removeAttr("alt");
				$("#process_scrapt_cnt_"+index).val("");

				$("#output_cnt_"+index).removeAttr("alt");
				$("#output_cnt_"+index).val("");

				if(obj[index] != null){
					$("#input_cnt_"+index).attr("alt",obj[index].REPORT_RECORD_ID);
					$("#input_cnt_"+index).val(obj[index].INPUT_CNT);
					
					$("#output_cnt_"+index).attr("alt",obj[index].REPORT_RECORD_ID);
					$("#output_cnt_"+index).val(obj[index].OUTPUT_CNT);
					
					$("#input_ng_cnt_"+index).attr("alt",obj[index].REPORT_RECORD_ID);
					$("#input_ng_cnt_"+index).val(obj[index].INPUT_NG_CNT);
					
					$("#process_ng_cnt_"+index).attr("alt",obj[index].REPORT_RECORD_ID);
					$("#process_ng_cnt_"+index).val(obj[index].PROCESS_NG_CNT);
					
					$("#input_scrapt_cnt_"+index).attr("alt",obj[index].REPORT_RECORD_ID);
					$("#input_scrapt_cnt_"+index).val(obj[index].INPUT_SCRAPT_CNT);
					
					$("#process_scrapt_cnt_"+index).attr("alt",obj[index].REPORT_RECORD_ID);
					$("#process_scrapt_cnt_"+index).val(obj[index].PROCESS_SCRAPT_CNT);
					
					$("#output_cnt_"+index).attr("alt",obj[index].REPORT_RECORD_ID);
					$("#output_cnt_"+index).val(obj[index].OUTPUT_CNT);					
				}
			});
			setInputAttribute(yyyy,MM,dd,hh,mm);
			
//			setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);
			
			setRate('input_ng_cnt_', 'input_cnt_', 'input_ng_rate_day', 0, 3);
			setRate('input_ng_cnt_', 'input_cnt_', 'input_ng_rate_night', 3, 5);
			
			setRate('process_ng_cnt_', 'input_cnt_', 'p_ng_rate_day', 0, 3);
			setRate('process_ng_cnt_', 'input_cnt_', 'p_ng_rate_night', 3, 5);
			
			setRate('input_scrapt_cnt_', 'input_cnt_', 'input_scrapt_rate_day', 0, 3);
			setRate('input_scrapt_cnt_', 'input_cnt_', 'input_scrapt_rate_night', 3, 5);
			
			setRate('process_scrapt_cnt_', 'input_cnt_', 'p_scrapt_rate_day', 0, 3);
			setRate('process_scrapt_cnt_', 'input_cnt_', 'p_scrapt_rate_night', 3, 5);
			setSumDayOutput('output_cnt_0','output_cnt','day_output_cnt');
		}
	);
}

function setInputAttribute(yyyy,MM,dd,hh,mm){
	var date = yyyy + "/" + MM + "/" + dd+" ";
	var tomorrow = yyyy + "/" + MM + "/" + (dd+1) + " ";
	var compare;
	var now = new Date(yyyy + "/" + MM + "/" + dd + " "+hh+":"+mm);
	var tindex;
	if(hh >= 9 && hh < 13){
		compare = new Date(Date.parse(date + "12:30"));
		if (now < compare){
		tindex = 0;
		$("#8-12").css('color','red');
		$("#time_zone").val('08:00-12:00');

		$('#input_cnt_0').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#input_cnt_0').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');});
		$('#input_cnt_0').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#input_cnt_0").attr("name","input_cnt");

		$("input[id=output_cnt_0]").removeAttr("readonly");
		$('#output_cnt_0').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#output_cnt_0').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
		$('#output_cnt_0').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#output_cnt_0").attr("name","output_cnt");

		$("input[id=input_ng_cnt_0]").removeAttr("readonly");
		$('#input_ng_cnt_0').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#input_ng_cnt_0').bind('keyup', function() {setSumDayOutput(this.id,'','');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
		$('#input_ng_cnt_0').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#input_ng_cnt_0").attr("name","input_ng_cnt");

		$("input[id=process_ng_cnt_0]").removeAttr("readonly");
		$('#process_ng_cnt_0').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#process_ng_cnt_0').bind('keyup', function() {setSumDayOutput(this.id,'','');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
		$('#process_ng_cnt_0').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#process_ng_cnt_0").attr("name","process_ng_cnt");

		$("input[id=input_scrapt_cnt_0]").removeAttr("readonly");
		$('#input_scrapt_cnt_0').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#input_scrapt_cnt_0').bind('keyup', function() {setSumDayOutput(this.id,'','');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
		$('#input_scrapt_cnt_0').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#input_scrapt_cnt_0").attr("name","input_scrapt_cnt");

		$("input[id=process_scrapt_cnt_0]").removeAttr("readonly");
		$('#process_scrapt_cnt_0').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#process_scrapt_cnt_0').bind('keyup', function() {setSumDayOutput(this.id,'','');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
		$('#process_scrapt_cnt_0').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#process_scrapt_cnt_0").attr("name","process_scrapt_cnt");
		}
	}
	if(hh > 12 && hh < 17){
		compare = new Date(Date.parse(date + "16:30"));
		if (now < compare){
			tindex = 1;
			$("#12-16").css('color','red');
			$("#time_zone").val('12:00-16:00');

			$('#input_cnt_1').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_cnt_1').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_cnt_1').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_cnt_1").attr("name","input_cnt");

			$("input[id=output_cnt_1]").removeAttr("readonly");
			$('#output_cnt_1').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#output_cnt_1').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#output_cnt_1').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#output_cnt_1").attr("name","output_cnt");

			$("input[id=input_ng_cnt_1]").removeAttr("readonly");
			$('#input_ng_cnt_1').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_ng_cnt_1').bind('keyup', function() {setSumDayOutput(this.id,'','');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_ng_cnt_1').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_ng_cnt_1").attr("name","input_ng_cnt");

			$("input[id=process_ng_cnt_1]").removeAttr("readonly");
			$('#process_ng_cnt_1').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#process_ng_cnt_1').bind('keyup', function() {setSumDayOutput(this.id,'','');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#process_ng_cnt_1').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#process_ng_cnt_1").attr("name","process_ng_cnt");

			$("input[id=input_scrapt_cnt_1]").removeAttr("readonly");
			$('#input_scrapt_cnt_1').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_scrapt_cnt_1').bind('keyup', function() {setSumDayOutput(this.id,'','');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_scrapt_cnt_1').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_scrapt_cnt_1").attr("name","input_scrapt_cnt");

			$("input[id=process_scrapt_cnt_1]").removeAttr("readonly");
			$('#process_scrapt_cnt_1').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#process_scrapt_cnt_1').bind('keyup', function() {setSumDayOutput(this.id,'','');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#process_scrapt_cnt_1').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#process_scrapt_cnt_1").attr("name","process_scrapt_cnt");
		}
	}
	if(hh > 16 && hh < 21){
		compare = new Date(Date.parse(date + "20:30"));
		if (now < compare){
			tindex = 2;
			$("#16-20").css('color','red');
			$("#time_zone").val('16:00-20:00');

			$('#input_cnt_2').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_cnt_2').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_cnt_2').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_cnt_2").attr("name","input_cnt");

			$("input[id=output_cnt_2]").removeAttr("readonly");
			$('#output_cnt_2').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#output_cnt_2').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#output_cnt_2').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#output_cnt_2").attr("name","output_cnt");

			$("input[id=input_ng_cnt_2]").removeAttr("readonly");
			$('#input_ng_cnt_2').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_ng_cnt_2').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_ng_cnt_2').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_ng_cnt_2").attr("name","input_ng_cnt");

			$("input[id=process_ng_cnt_2]").removeAttr("readonly");
			$('#process_ng_cnt_2').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#process_ng_cnt_2').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#process_ng_cnt_2').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#process_ng_cnt_2").attr("name","process_ng_cnt");

			$("input[id=input_scrapt_cnt_2]").removeAttr("readonly");
			$('#input_scrapt_cnt_2').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_scrapt_cnt_2').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_scrapt_cnt_2').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_scrapt_cnt_2").attr("name","input_scrapt_cnt");

			$("input[id=process_scrapt_cnt_2]").removeAttr("readonly");
			$('#process_scrapt_cnt_2').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#process_scrapt_cnt_2').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#process_scrapt_cnt_2').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#process_scrapt_cnt_2").attr("name","process_scrapt_cnt");
		}
	}
	if(hh > 20 || hh < 1){//分两个方便去判断，0点前和0点后。
		if(hh > 20)
			compare = new Date(Date.parse(tomorrow + "00:30"));
		else
			compare = new Date(Date.parse(date + "00:30"));
		if (now < compare){
			tindex = 3;
			$("#20-24").css('color','red');
			$("#time_zone").val('20:00-24:00');

			$('#input_cnt_3').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_cnt_3').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_cnt_3').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_cnt_3").attr("name","input_cnt");

			$("input[id=output_cnt_3]").removeAttr("readonly");
			$('#output_cnt_3').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#output_cnt_3').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#output_cnt_3').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#output_cnt_3").attr("name","output_cnt");

			$("input[id=input_ng_cnt_3]").removeAttr("readonly");
			$('#input_ng_cnt_3').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_ng_cnt_3').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_ng_cnt_3').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_ng_cnt_3").attr("name","input_ng_cnt");

			$("input[id=process_ng_cnt_3]").removeAttr("readonly");
			$('#process_ng_cnt_3').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#process_ng_cnt_3').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#process_ng_cnt_3').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#process_ng_cnt_3").attr("name","process_ng_cnt");

			$("input[id=input_scrapt_cnt_3]").removeAttr("readonly");
			$('#input_scrapt_cnt_3').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_scrapt_cnt_3').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_scrapt_cnt_3').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_scrapt_cnt_3").attr("name","input_scrapt_cnt");

			$("input[id=process_scrapt_cnt_3]").removeAttr("readonly");
			$('#process_scrapt_cnt_3').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#process_scrapt_cnt_3').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#process_scrapt_cnt_3').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#process_scrapt_cnt_3").attr("name","process_scrapt_cnt");
		}
	}
	if(hh > 0 && hh < 5){
		compare = new Date(Date.parse(date + "04:30"));
		if (now < compare){
			tindex = 4;
			$("#24-4").css('color','red');
			$("#time_zone").val('00:00-04:00');

			$('#input_cnt_4').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_cnt_4').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_cnt_4').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_cnt_4").attr("name","input_cnt");

			$("input[id=output_cnt_4]").removeAttr("readonly");
			$('#output_cnt_4').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#output_cnt_4').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#output_cnt_4').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#output_cnt_4").attr("name","output_cnt");

			$("input[id=input_ng_cnt_4]").removeAttr("readonly");
			$('#input_ng_cnt_4').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_ng_cnt_4').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_ng_cnt_4').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_ng_cnt_4").attr("name","input_ng_cnt");

			$("input[id=process_ng_cnt_4]").removeAttr("readonly");
			$('#process_ng_cnt_4').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#process_ng_cnt_4').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#process_ng_cnt_4').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#process_ng_cnt_4").attr("name","process_ng_cnt");

			$("input[id=input_scrapt_cnt_4]").removeAttr("readonly");
			$('#input_scrapt_cnt_4').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#input_scrapt_cnt_4').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#input_scrapt_cnt_4').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#input_scrapt_cnt_4").attr("name","input_scrapt_cnt");

			$("input[id=process_scrapt_cnt_4]").removeAttr("readonly");
			$('#process_scrapt_cnt_4').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
			$('#process_scrapt_cnt_4').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
			$('#process_scrapt_cnt_4').bind('blur', function() {if(this.value < 1) this.value=0});
			$("#process_scrapt_cnt_4").attr("name","process_scrapt_cnt");
		}
	}
	if(hh > 4 && hh < 9){
		compare = new Date(Date.parse(date + "08:30"));
		if (now < compare){
		tindex = 5;
		$("#4-8").css('color','red');
		$("#time_zone").val('04:00-08:00');

		$('#input_cnt_5').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#input_cnt_5').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
		$('#input_cnt_5').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#input_cnt_5").attr("name","input_cnt");
		
		$("input[id=output_cnt_5]").removeAttr("readonly");
		$('#output_cnt_5').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#output_cnt_5').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
		$('#output_cnt_5').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#output_cnt_5").attr("name","output_cnt");
		
		$("input[id=input_ng_cnt_5]").removeAttr("readonly");
		$('#input_ng_cnt_5').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#input_ng_cnt_5').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
		$('#input_ng_cnt_5').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#input_ng_cnt_5").attr("name","input_ng_cnt");

		$("input[id=process_ng_cnt_5]").removeAttr("readonly");
		$('#process_ng_cnt_5').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#process_ng_cnt_5').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
		$('#process_ng_cnt_5').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#process_ng_cnt_5").attr("name","process_ng_cnt");
		
		$("input[id=input_scrapt_cnt_5]").removeAttr("readonly");
		$('#input_scrapt_cnt_5').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#input_scrapt_cnt_5').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
		$('#input_scrapt_cnt_5').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#input_scrapt_cnt_5").attr("name","input_scrapt_cnt");
		
		$("input[id=process_scrapt_cnt_5]").removeAttr("readonly");
		$('#process_scrapt_cnt_5').bind('focus', function() {this.value = (this.value == 0 ? '' : this.value);cc(this.id)});
		$('#process_scrapt_cnt_5').bind('keyup', function() {setSumDayOutput(this.id,'output_cnt','day_output_cnt');setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);});
		$('#process_scrapt_cnt_5').bind('blur', function() {if(this.value < 1) this.value=0});
		$("#process_scrapt_cnt_5").attr("name","process_scrapt_cnt");
		}
	}

	var btnStatus = typeof($("#input_cnt_"+tindex).attr("alt")) == "undefined";

	if(!btnStatus){
		$("#btnSave").attr("disabled","disabled");
		$("#output_cnt_"+tindex).attr("readonly","readonly");
		$("#input_ng_cnt_"+tindex).attr("readonly","readonly");
		$("#process_ng_cnt_"+tindex).attr("readonly","readonly");
		$("#input_scrapt_cnt_"+tindex).attr("readonly","readonly");
		$("#process_scrapt_cnt_"+tindex).attr("readonly","readonly");
	} else {
		$("#btnSave").removeAttr("disabled");
	}
	
}


/**
 * 聚合函数
 * @param id 当前选中标签
 * @param name 当前选中标签name
 * @param id2 要显示计算后的值的DIV的ID
 */
function setSumDayOutput(id,name,id2) {
	$("#"+id).val(eval($("#"+id).val().replace(/\D/g,'')));
	name += "_";
	var result = 0;
	for(var i=0;i<6;i++){
		result += getIntegerValue(name+i);
	}
	$("#"+id2).html(result);
}


function setRate(id1,id2,div,a,b){
	$("#"+div).html("");
	var resultA = 0;
	var resultB = 0;
	for(var i=a;i<b;i++){
		resultA += getIntegerValue(id1+i);
		resultB += getIntegerValue(id2+i);
	}
	var t = resultA / resultB * 100 ;
	$("#"+div).html(toDecimal(t)+'%');
}

function setInputCNT(id1,id2,id3,id4,id5,div,a,b){
	for(var i=a;i<b;i++){
		$("#"+div+i).val(getIntegerValue(id1+i) + getIntegerValue(id2+i) + getIntegerValue(id3+i) + getIntegerValue(id4+i) + getIntegerValue(id5+i));
	}
}

function toDecimal(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return 0;
    }
    f = Math.round(x*100)/100;
    return f;
} 

function getIntegerValue(id){
	var result = parseInt($("#"+id).val()) > 0 ? eval($("#"+id).val()) : 0;
	return result;
}

function cc(id){
	var menuAble = typeof($("#"+id).attr("alt")) == "undefined";
	$("#current_id").attr("value",id);
	if(!menuAble){
		$("#"+id).bind('contextmenu', function(e) {
			e.preventDefault();
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		});
	}
};

function confirmForm(id,yyyy,MM,dd,hh,mm){
	if($("#"+id).combobox('getValue') != 0){
		$.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
	        if (data) {
				$.ajax({
					cache: true,
					type: "POST",
					url:'saveReport',
					data:$('#report').serialize(),
					async: false,
				    error: function(request) {
				        alert("与服务器之间的网络连接已断开，请重试。");
				    },
				    success: function(data) {
				    	var msg = '数据录入失败，请重试。';
				    	if('ng' != data){
				    		msg = '数据操作成功';
				    		queryReprot(data,yyyy,MM,dd,hh,mm);
				    	}
				    	$.messager.show({
			                title:'系统提示',
			                msg:msg,
			                timeout:2000,
			                showType:'slide',
			                style:{
			                    right:'',
			                    top:document.body.scrollTop+document.documentElement.scrollTop,
			                    bottom:''
			                }
			            });
				    }
				});
	        }
	    });
	} else {
		$.messager.alert('警告信息','请选择机种、ERP料号等信息','warning');
		$("#"+id).focus();
	}
}

function sleep(numberMillis) {
	var now = new Date();
	var exitTime = now.getTime() + numberMillis;
	while (true) {
		now = new Date();
		if (now.getTime() > exitTime)
			return;
	}
}

function menuHandler(item) {
	if(item.name == 'addNote'){
		$('#newNote').window('open');
		$("#editor1").val("");
		$.post(
			"queryComment",
			{report_record_id:$("#"+$("#current_id").val()).attr("alt"),field_name:$("#current_id").val()},
			function(data,status){
				if(data.length !=0 ){
					var desc = data.replace(data.split("\r\n")[0],"").replace("\r\n","");
					$("#editor1").val(desc);
				}
			}
		);
	}
	if(item.name == 'append'){//数量补差，在用。
		$.messager.prompt('数量补差', '请输入数量，本次操作会被系统记录。', function(r){
            if (r){
            	$.post(
        			"saveAppen",
        			{append_input:r,append_id:$("#current_id").val(),report_record_id:$("#"+$("#current_id").val()).attr("alt"),original_cnt:$("#"+$("#current_id").val()).val()},
        			function(data,status){
        				$("#"+$("#current_id").val()).val(r);
        				setInputCNT('output_cnt_','input_ng_cnt_','process_ng_cnt_','input_scrapt_cnt_','process_scrapt_cnt_','input_cnt_',0,6);
        				$.messager.show({
    		                title:'系统提示',
    		                msg:data,
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
            }
        });
	}
}

//数量补差，暂时不用。
function saveAppend(){
	$('#append_div').window('close');
	$.post(
		"saveAppen",
		{append_input:$('#append_input').val(),append_id:$("#current_id").val()},
		function(data,status){
			alert(data);
		}
	);
	$('#append_input').val('');
}

//function queryComment(alt,field_name){
//	$.get(
//		"queryComment",
//		{report_record_id:alt,field_name:field_name},
//		function(data,status){
//			if(data.length !=0 ){
//				$("#"+field_name).attr("title",data);
//				$("#addNote").attr("display","none");
//			}
//		}
//	);
//}

/**
 * 查询批注
 * @param report_record_id
 * @param title_id
 * @param field_name
 */
function queryComment(report_record_id,title_id,field_name){
	if($("#"+title_id).attr('title') == null){
		$.get(
			"queryComment",
			{report_record_id:report_record_id,field_name:field_name},
			function(data,status){
				if(data.length !=0 ){
					$("#"+title_id).attr("title",data);
				}
			}
		);
	}
}

function removeAttribute(id,attribute){
	$("#"+id).removeAttr(attribute);
}

function getEmpName(value){
	if(keyListening()){
		$.post(
   			"getEmpName",
   			{emp_no:value},
   			function(data,status){
   				if(data.length!=0){
   					$("#emp_info").attr("value",value+','+data+';');
   					$("#emp_error").html("");
   				} else {
   					$("#emp_error").html("工号输入错误");
   					$("#emp_info").attr("value","");
   				}
   			}
   		);
	}
}

function doSettingSub(formid){
	validateCallback(stage_report_add);
}

function addComma(id,value){
	if(keyListening()){
		var tmp_value = (value.substring(value.lastIndexOf(';')+1));
		$.post(
			"getEmpName",
			{emp_no:tmp_value},
			function(data,status){
				if(null != data){
					$("#"+id).val(value+','+data+';');
				} else {
					value = value.replaceAll(tmp_value,'');
					$("#"+id).val(value);
				}
			}
		);
	}
}

function reprotSettingEmpNO(id){
	var settingid = $("#currentSettingId").val();
	$("#"+id).attr("href","assignReprotSettingEmp?stage_report_setting_id="+settingid+'&emp_no='+$("#emp_no").val());
}

function cascadeERPProcessID(action,modelName,id){
	$.getJSON(
		action,
		{model_name:modelName},
		function(data,status){
			if(null != data){
				document.getElementById(id).innerHTML = '';
				document.getElementById(id).options.add(new Option('--请选择ERP料号--','0'));
				$.each(data,function(index,element){ 
					var option = document.createElement('OPTION');
					option.text = element.erp_process_id;
					option.value = element.erp_process_id;
					document.getElementById(id).options.add(option);
				});
			} else {
				//value = value.replaceAll(tmp_value,'');
				//$("#"+id).val(value);
			}
		}
	);
}

