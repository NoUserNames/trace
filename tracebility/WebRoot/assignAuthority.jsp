<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>授权管理-${param.uid }</title>
    <link rel="stylesheet" type="text/css" href="css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="css/demo.css">
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
</head>
<body>
	<form action="assignAuthority" method="post" id="assignAuthorityForm">
	    <h2 style="color: red">当前是在为工号【${param.uid }】授予权限</h2>
	    <input type="hidden" id="uid" value="${param.uid }" name="uid_assgin_auth"/>
	    <input type="hidden" name="nodeids" id="nodeids">
	    <div style="margin:20px 0;">
	        <a class="easyui-linkbutton" onclick="getChecked()">确定授权</a> 提示：请把需要授予的权限勾选，点击确定即可。
	    </div>
	    <!-- 
	    <div style="margin:10px 0">
	        <input type="checkbox" checked onchange="$('#tt').tree({cascadeCheck:$(this).is(':checked')})">CascadeCheck 
	        <input type="checkbox" onchange="$('#tt').tree({onlyLeafCheck:$(this).is(':checked')})">OnlyLeafCheck
    	</div>
    	 -->
	    <div class="easyui-panel" style="padding:5px;width: 600px">
	    	<ul id="tt" class="easyui-tree" data-options="data:<s:property value="nodesJSON"/>,method:'post',animate:true,checkbox:true"></ul>
	    </div>
	    <script type="text/javascript">
	        function getChecked(){
	            var nodes = $('#tt').tree('getChecked');
	            var s = '';
	            for(var i=0; i<nodes.length; i++){
	                if (s != '') s += ',';
	                s += nodes[i].id;
	            }

	            $.messager.confirm('系统提示', '确定授予所选权限吗？', function(r){
				if (r){
        				$.ajax({
					        type:'post',
					        data:{'uid_assgin_auth':$('#uid').val(),'nodeids':s},
					        dataType:'text',
					        url:'assignAuthority',
					        
					        success:function(data) {
					     		if(data == 'ok'){
						        	$.messager.show({
						                title:'系统提示',
						                msg:'权限操作成功,3秒后本窗口自动关闭，如您浏览器不支持请手动关闭。',
						                showType:'slide',
						                style:{
						                    right:'',
						                    top:document.body.scrollTop+document.documentElement.scrollTop,
						                    bottom:''
						                }
						            });
						            setTimeout(function(){
									    window.close();
									},3000);
								}
					        },error:function (data){
								alert('出错'+data);
							}
				    	});	
		            }
		        });
	        }
	    </script>
    </form>
</body>
</html>