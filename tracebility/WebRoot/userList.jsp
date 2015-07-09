<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>管理所有用户</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jobHandle.js"></script>
  </head>
  
  <body>
	<div class="pageHeader">
   	<form class="pageForm required-validate" onsubmit="return navTabSearch(this)" action="getAllUsers" method="post" id="getAllUsersForm">
	<div class="searchBar">
	 <table class="searchContent">
			<tr>
				<td>
					工号：<s:textfield alt="请输入员工号" name="uid" onkeyup="queryUser(this.form.id)" cssClass="required" id="uid"></s:textfield>
				</td>
				<td>
					姓名：<s:textfield alt="请输入员工姓名" name="uName" cssClass="required" id="uName"></s:textfield>
				</td>
			</tr>
		</table>
		<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button name="btn" id="btn" type="button" onclick="checkAssignAuthority('uid','uName',this.form.id)">检索</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button name="btn" id="btn" type="reset">清空</button>
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
	<li><a width="510" height="250" class="add" href="userAdd.jsp" target="dialog"><span>添加新工号</span></a></li>
	<li class="line">line</li>
	<li>
		<span>
			共有&nbsp;
				<font color="blue"><b><s:property value="#request.listUsers.size()"/></b></font>
			&nbsp;
			条记录	
		</span>
		<span>以下为员工信息清单，点击姓名为其添加权限。</span>
	</li>
	</ul>
	</div>
  <div>
	  <table class="table" width="100%" LayoutH="110">
	  	<thead>
	  	<tr>
	  		<th width="15%">工号</th>
	  		<th>用户名</th>
	  		<th>密码</th>
	  		<th>显示名</th>
	  		<th>状态</th>
	  		<th width="20%">操作</th>
	  	</tr>
	  	</thead>
	  	<tbody>
	    <s:iterator value="listUsers" id="users" status="index">
	  	<tr align="center" <s:if test="#users.enable != 0">style="background-color: yellow"</s:if>>
	  		<td>
	  			<s:property value="#users.uid"/>
	  		</td>
	  		<td>
	  		<a href="loadNodesByUser?uid=<s:property value="#users.uid"/>"  title="<s:property value="#users.udisplayName"/>-相关权限" target="navTab<s:property value="#index.index"/>" rel="page<s:property value="#index.index"/>">
	  			<s:property value="#users.uname"/>
	  		</a>
	  		</td>
	  		<td>
	  			<!--<s:password name="userInfo.upwd" value="<s:property value='#users.upwd'/>"></s:password>-->
	  			******
	  		</td>
	  		<td>
	  			<s:property value="#users.udisplayName"/>	  			
	  		</td>
	  		<td>
	  			<s:if test="#users.enable == 0">
	  				正常
	  			</s:if>
	  			<s:else>
	  				被禁用
	  			</s:else>
	  		</td>
	  		<td>
	  			<a <s:if test="#users.enable != 0">class="buttonDisabled"</s:if><s:else>class="button"</s:else>
	  				onclick="userStatus('<s:property value="#users.uid"/>','userStatus',1)">
	  				<span>禁用</span>
	  			</a>
				<a <s:if test="#users.enable == 0">class="buttonDisabled"</s:if><s:else>class="button"</s:else>
					onclick="userStatus('<s:property value="#users.uid"/>','userStatus',0)">
	  				<span>启用</span>
	  			</a>
	  			<a class="button" onclick="ajaxGet('<s:property value="#users.uid"/>','delUser')">
	  				<span>删除</span>
	  			</a>
	  			<a class="button" onclick="ajaxGet('<s:property value="#users.uid"/>','resetPassword')">
	  				<span>密码重置</span>
	  			</a>
	  		</td>
	  	</tr>
	    </s:iterator>
	  	</tbody>
	  </table>
  </div>
  </body>
</html>
