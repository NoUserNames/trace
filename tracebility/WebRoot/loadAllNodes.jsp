<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	
	<title>管理菜单</title>
	
	<link rel="stylesheet" type="text/css" href="css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/themes/icon.css">

	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
</head>

<body>	
	<div>
		<a href="javascript:void(0)" title="编辑选中行" class="easyui-linkbutton" onclick="edit()">编辑</a>
		<a href="javascript:void(0)" title="保存修改后的值" class="easyui-linkbutton" onclick="save()">保存</a>
		<a href="javascript:void(0)" title="取消当前选中编辑行" class="easyui-linkbutton" onclick="cancel()">取消</a>
	</div>
	<table id="tg" class="easyui-treegrid"
		title="提示：选中需要修改的节点，点击修改按钮进行修改操作。或者右键选中节点，进行相关操作。" style="width: 100%; height: 90%"
		data-options="
	        iconCls: 'icon-ok',
	        rownumbers: true,
	        animate: true,
	        collapsible: true,
	        fitColumns: true,
	        data: <s:property value="nodesJSON"/>,
	        method: 'get',
	        idField: 'id',
	        treeField: 'nodename',
	        showFooter: true,
	        onContextMenu: onContextMenu
           ">
		<thead>
			<tr>
				<th data-options="field:'id'">节点编号</th>
				<th data-options="field:'nodename',width:150,editor:'text'">节点</th>
				<th data-options="field:'pid',width:40,align:'right',editor:'numberbox'">父节点</th>
				<th data-options="field:'nodeurl',width:80,editor:'text'">访问路径</th>
				<th data-options="field:'enable',editor:'numberbox'">是否可用</th>
				<th data-options="field:'target',editor:'text'">打开目标</th>
				<th data-options="field:'rel',editor:'text'">打开方式</th>
				<th data-options="field:'external',editor:'text'">是否外部引用</th>
			</tr>
		</thead>
	</table>

	<div id="mm" class="easyui-menu" style="width:120px;z-index: 1">
	    <div onclick="openSelf()" title="以当前节点为父节点，添加子节点。" data-options="iconCls:'icon-add'">追加子菜单</div><!-- onclick="append()" -->
	    <div onclick="removeIt()" title="删除当前选中的菜单节点" data-options="iconCls:'icon-remove'">删除当前菜单</div>
	    <div class="menu-sep"></div>
	    <div onclick="collapse()">收起所有菜单</div>
	    <div onclick="expand()">展开所有菜单</div>
    </div>
    
    <div id="newNode" class="easyui-window" title="新增菜单" data-options="modal:true,closed:true,iconCls:'icon-save',min:'true'" style="width:600px;height:225px;padding:10px;">
	<s:form action="updateNode" method="post" id="updateNodeForm">		
    	<table>
    		<tr>
    			<td>菜单编号</td>
    			<td><input style="color: gray;" id="id_new" title="由系统自动生成"/></td>
    			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    			<td>菜单名称</td>
    			<td><input id="nodename_new"/></td>
    		</tr>
    		<tr>
    			<td>父节点</td>
    			<td><input style="color: gray;" id="pnode_new" readonly="readonly"/></td>
    			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    			<td>菜单路径</td>
    			<td><input id="nodeurl_new"/></td>
    		</tr>
    		<tr>
    			<td>是否可用</td>
    			<td><input id="enable_new" value="0"/></td>
    			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    			<td>打开目标</td>
    			<td><input id="target_new" value="navTab"/></td>
    		</tr>
    		<tr>
    			<td>打开方式</td>
    			<td><input id="rel_new" value="page"/></td>
    			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    			<td colspan="2">
    				是否外部引用<input id="external_new" type="checkbox" value="false" onclick="this.value = this.checked"/>
    				是否顶级目录<input id="isRoot_new" type="checkbox" value="false" onclick="this.value = this.checked;id_new.value='';pnode_new.value=0"/>
    			</td>
    		</tr>
    		<tr align="center">
    			<td colspan="5">
    				<a onclick="closeSelf()" class="easyui-linkbutton" iconCls="icon-ok" style="width:30%;height:32px">确定</a>
    			</td>
    		</tr>
    	</table>
    </s:form>
    </div>
	<script type="text/javascript">
		function formatProgress(value) {
			if (value) {
				var s = '<div style="width:100%;border:1px solid #ccc">'
						+ '<div style="width:' + value
						+ '%;background:#cc0000;color:#fff">' + value + '%'
						+ '</div>'
				'</div>';
				return s;
			} else {
				return '';
			}
		}
		var editingId;
		function edit() {
			if (editingId != undefined) {
				$('#tg').treegrid('select', editingId);
				return;
			}
			var row = $('#tg').treegrid('getSelected');
			if (row) {
				editingId = row.id
				$('#tg').treegrid('beginEdit', editingId);
			}
		}
		function save() {
			if (editingId != undefined) {
				var t = $('#tg');
				t.treegrid('endEdit', editingId);
				var row = $('#tg').treegrid('getSelected');
				
				$.ajax({
			        type:'post',
			        data:{
			        	'nodeid_up':row.id,
			        	'nodename_up':row.nodename,
			        	'pid_up':row.pid,
			        	'nodeurl_up':row.nodeurl,
			        	'enable_up':row.enable,
			        	'target_up':row.target,
			        	'rel_up':row.rel,
			        	'external_up':row.external
			        },
			        dataType:'text',
			        url:'updateNode',
			        
			        success:function(data) {
			     		if(data == 'ok'){
			     			cancel();
				        	$.messager.show({
				                title:'系统提示',
				                msg:'数据修改成功,2秒后本窗口自动关闭。',
				                timeout:2000,
				                showType:'slide',
				                style:{
				                    right:'',
				                    top:document.body.scrollTop+document.documentElement.scrollTop,
				                    bottom:''
				                }
				            });
						}else{
							alert('失败了');
						}
			        },error:function (data){
						alert('出错'+data);
					}
			    });
				
			}
		}

		function cancel() {
			if (editingId != undefined) {
				$('#tg').treegrid('cancelEdit', editingId);
				editingId = undefined;
			}
		}

		function setValue(id, value) {
			document.getElementById(id).value = value;
		}

		function onContextMenu(e, row) {
			e.preventDefault();
			$(this).treegrid('select', row.id);
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY,
			});
		}

		function openSelf() {
			var node = $('#tg').treegrid('getSelected');
			$("#pnode_new").val(node.id);
			$.ajax({
				type : 'get',
				data : {
					'pnode' : node.id,
				},
				dataType : 'text',
				url : 'getNodeId.action',

				success : function(data) {
					if (data != '') {
						$("#id_new").val(data);
					} else {
						alert('失败了');
					}
				},
				error : function(data) {
					alert('出错' + data);
				}
			});
			$('#newNode').window('open');
			$("#nodename_new").focus().select();
		}

		function closeSelf() {
			toAddNode();
			$('#newNode').window('close');
		}

		function toAddNode() {
			$.ajax({
				type : 'post',
				data : {
					'nodeid_new' : $('#id_new').val(),
					'nodename_new' : $('#nodename_new').val(),
					'pid_new' : $('#pnode_new').val(),
					'nodeurl_new' : $('#nodeurl_new').val(),
					'enable_new' : $('#enable_new').val(),
					'target_new' : $('#target_new').val(),
					'rel_new' : $('#rel_new').val(),
					'external_new' : $('#external_new').val()
				},
				dataType : 'text',
				url : 'addNode.action',

				success : function(data) {
					if (data == 'ok') {
						append($('#id_new').val(), $('#nodename_new').val(), $(
								'#pnode_new').val(), $('#nodeurl_new').val(),
								$('#enable_new').val(), $('#target_new').val(),
								$('#rel_new').val(), $('#external_new').val());
						$.messager.show({
							title : '系统提示',
							msg : '节点添加成功,2秒后本窗口自动关闭。',
							timeout:2000,
							showType : 'slide',
							style : {
								right : '',
								top : document.body.scrollTop
										+ document.documentElement.scrollTop,
								bottom : ''
							}
						});
						cancel();
					} else {
						alert('失败了'+data);
					}
				},
				error : function(data) {
					alert('出错' + data);
				}
			});
		}

		var idIndex = 100;
		function append(id1, name1, pid1, nodeurl1, enable1, target1, rel1, external1) {
			idIndex++;
			var d1 = new Date();
			var d2 = new Date();
			d2.setMonth(d2.getMonth() + 1);
			var node = $('#tg').treegrid('getSelected');
			$('#tg').treegrid('append', {
				parent : node.id,
				data : [ {
					id : $('#id_new').val(),
					nodename : $('#nodename_new').val(),
					pid : node.id,
					nodeurl : $('#nodeurl_new').val(),
					enable : $('#enable_new').val(),
					target : $('#target_new').val(),
					rel : $('#rel_new').val(),
					external : $('#external_new').val()
				} ]
			})
		}


		function removeIt() {
			$.messager.confirm(
			'系统提示',
			'确定要删除此节点吗?此操作不可恢复！',
			function(r) {
				if (r) {
					var node = $('#tg').treegrid('getSelected');
					$.ajax({
						type : 'get',
						data : {
							'nodeid_del' : node.id
						},
						dataType : 'text',
						url : 'deletNode',

						success : function(data) {
							if (data == 'ok') {
								$.messager.show({
									title : '系统提示',
									msg : '节点删除成功,2秒后本窗口自动关闭。',
									timeout : 2000,
									showType : 'slide',
									style : {
										right : '',
										top : document.body.scrollTop
												+ document.documentElement.scrollTop,
										bottom : ''
									}
								});
							}
						},
						error : function(data) {
							alert('出错' + data);
						}
					});

					if (node) {
						$('#tg').treegrid('remove', node.id);
					}
				}
			});
		}

		function collapse() {
			var node = $('#tg').treegrid('getSelected');
			if (node) {
				$('#tg').treegrid('collapse', node.id);
			}
		}

		function expand() {
			var node = $('#tg').treegrid('getSelected');
			if (node) {
				$('#tg').treegrid('expand', node.id);
			}
		}
	</script>
	
</body>
</html>