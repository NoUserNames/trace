<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通讯录管理</title>
<script type="text/javascript">
	function getChks(value,checked){
		var tmpchk = pagerForm.chkFlag.value;
		if(checked){
			if(tmpchk.indexOf(','+value) <=0)
				tmpchk += value+',';
		}else{
			tmpchk = tmpchk.replaceAll(value+',','');
		}
		pagerForm.chkFlag.value = tmpchk;
	}

	function doDel(id){
		document.getElementById(id).href='delContact?chkFlag='+pagerForm.chkFlag.value;
	}

	function getAll() {
		var str = "";
		$("[name='chk'][checked]").each(function() {
			str += $(this).val() + ",";
		})
		pagerForm.chkFlag.value = str;
	}
</script>
</head>
<body>
	<div class="pageHeader">
		<form onsubmit="return navTabSearch(this)" action="initContact" method="post">
		<input type="hidden" name="pageNum" value="2" />
		<div class="searchBar">
		<table class="searchContent">
				<tr>
					<td>
						请选择厂别<s:select list="#{'10001':'日沛', '10013':'日腾一厂', '10015':'日铭', '10016':'日闰', '10017':'胜瑞', '10018':'应华'}" name="factory_id"  headerKey="0" headerValue="----"/>
					</td>
					<td>
						请选择群组名称<s:select name="sms_group" list="groups" listKey="group_id" listValue="group_name" headerKey="0" headerValue="----"></s:select>
					</td>
				</tr>
			</table>
			<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button name="btn" id="btn" type="submit" >检索</button>
								</div>
							</div>
						</li>
						<li><div class="buttonActive"><div class="buttonContent"><button name="btn" id="btnReset" type="reset">清空</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>

	<div class="panelBar">
		<ul class="toolBar">
		<li><a width="510" height="300" class="add" href="initAddGroup" target="dialog" title="添加收短信人员" mask="true"><span>添加</span></a></li>
		<li class="line">line</li>
		<li><a class="delete" target="ajaxTodo" id="delA" onclick="doDel(this.id)" title="确定要删除选中的人员信息吗?"><span>删除</span></a></li>
		<li class="line">line</li>
		<li><button class="checkboxCtrl" group="chk" selectType="invert" onmouseout="getAll()" title="反向选择">反选</button></li>
		<li class="line">line</li>
		</ul>
	</div>

	<div class="pageContent">
	<form action="delContact" name="contactForm" onsubmit="return dwzSearch(this,'navTab')" method="get">
		<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th style="padding-top: 3">
						<input type="checkbox" onmouseout="getAll()" class="checkboxCtrl" group="chk" title="全选" />
					</th>
					<th>姓名</th>
					<th>工号</th>
					<th>手机号码</th>
					<th>厂别</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="listContacts" id="contacts" status="index">
					<tr>
						<td align="center">
							<input type="checkbox" name="chk" id="chk" onclick="getChks(this.value,this.checked)" value="<s:property value="#contacts.emp_no" />-<s:property value="#contacts.group_id" />" />
						</td>
						<td>
							<s:property value="#contacts.emp_name" />
						</td>
						<td><s:property value="#contacts.emp_no" /></td>
						<td>
							<s:property value="#contacts.mobile_number" />
						</td>
						<td>
							<s:property value="#contacts.factory_id" />
						</td>
						<td>
							<s:property value="#contacts.create_time" />
						</td>
						<td width="110px">
							<a class="button" target="dialog" href="contact_edit_query?emp_no_edit=<s:property value="#contacts.emp_no" />"  width="900" height="270" mask="true" ><span>修改</span></a>
							<a class="button" target="ajaxTodo" href="delContact?chkFlag=<s:property value="#contacts.emp_no" />-<s:property value="#contacts.group_id" />" id="delA" title="确定要删除【<s:property value="#contacts.emp_name" />】吗?"><span>删除</span></a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		</form>
		<!-- 分页栏  -->
		<form id="pagerForm" action="initContact" method="post">
	 		<input type="hidden" name="pageNum" value="1" />
	 		<input type="hidden" name="numPerPage" value="<s:property value="numPerPage"/>">
	 		<input type="hidden" id="chkFlag" name="chkFlag">
		</form>
	 	<div class="panelBar">
	 		<input type="hidden" name="totalCount" value="<s:property value="totalCount"/>" />
			<input type="hidden" name="numPerPage" value="<s:property value="numPerPage"/>" />
			<div class="pages">
				<span>显示</span>
				<s:select list="#{'20':'20', '30':'30', '50':'50', '100':'100'}" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})"/>
				<span>条，共<s:if test="totalCount != 0"><s:property value="totalCount"/></s:if><s:else>0</s:else>条</span>
				<span>，共${pageNumShown }页</span>
			</div>		
			<div class="pagination" targetType="navTab" totalCount="<s:property value="totalCount"/>" numPerPage="<s:property value="numPerPage"/>" pageNumShown="<s:property value="pageNumShown"/>" currentPage="<s:property value="pageNum"/>"></div>
		</div>
	</div>
</body>
</html>