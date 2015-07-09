<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Tracebility-管理平台</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="css/themes/icon.css">
    
    
    <link href="themes/css/login.css" rel="stylesheet" type="text/css" /> 
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/jobHandle.js"></script>

  </head>
  
  <body>
    <s:form id="login" namespace="/" method="post" action="doLogin" lass="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
    	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a><img src="themes/azure/images/logo.png" /></a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a style="cursor: hand;" class="easyui-tooltip" title="点击添加到收藏夹，下次再也不用担心忘记地址啦。(*^__^*)" onclick="AddFavorite(window.location,document.title)">收藏本站</a></li>
						<li><a class="easyui-tooltip" href="mailto:Qiang1_Zhang@intra.casetekcorp.com" title="使用中有什么意见或建议，点这里反馈给我们吧 o(∩_∩)o">提意见</a></li>
						<li><a class="easyui-tooltip" href="help/help.html" target="_blank" title="有疑问？不用担心，我们有帮助文档。点击查看。">帮助</a></li>
					</ul>
				</div>
				<h2 class="login_title"><img src="themes/default/images/login_title.png" /></h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm" align="center">	
				<table>
					<tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr>
					<tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr>
					<tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr>
					<tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr>
					<tr>
						<td width="50">类&nbsp;&nbsp;&nbsp;别：</td>
						<td>
							<s:select cssStyle="width:130px" list="#{'1':'工号','2':'域帐号'}" id="type" name="loginType" onchange="chkLabel(this.value)"/>
						</td>
					</tr>
					<tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr>
					<tr>
						<td style="padding-top: 3"><div id="label">工&nbsp;&nbsp;&nbsp;号：</div></td>
						<td><input style="width: 130px;" type="text" id="uName" name="uid"  class="login_input" onkeyup="keyboardEvent()"/></td>
					</tr>
					<tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr><tr><td colspan="2"></td></tr>
					<tr>
						<td>密&nbsp;&nbsp;&nbsp;码：</td>
						<td><input style="width:130px;" type="password" id="uPwd" name="uPwd" class="login_input" onkeyup="keyboardEvent()"/></td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;
							<div align="center" style="color: red;">
								<b>
					        	<s:fielderror>
					        		<s:param>login.error</s:param>
					        	</s:fielderror>
					        	</b>
					        	<b>
					        	<s:fielderror>
					        		<s:param>domain.error</s:param>
					        	</s:fielderror>
					        	</b>
					        </div>
					        <div align="center" style="color: red;">
								<b>
					        	<s:fielderror>
					        		<s:param>uWithOutAuth</s:param>
					        	</s:fielderror>
					        	</b>
					        </div>
					        <div align="center" style="color: red;display: none;" id="card">
					        	<b>请输入工号和密码登录</b>
					        </div>
					        <div align="center" style="color: red;display: none;" id="requisite">
					        	<b>请输入完整的口令和密码</b>
					        </div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<div class="login_bar">
							<input class="sub" onclick="chkLogin()" type="button" value=" " />
						</div>
						</td>
					</tr>
				</table>
			</div> 
			<div class="login_banner"><img src="themes/default/images/login_banner.png" /></div>
			<div class="login_main">
				<ul class="helpList">
					<li><a href="http://oa.casetekcorp.com/" title="点此进入综合管理系统" class="easyui-tooltip" target="_blank">综合管理系统</a></li>
					<li><a href="http://mic.casetekcorp.com/" title="点此进入量仪室作业系统" class="easyui-tooltip" target="_blank">量仪室作业系统</a></li>
					<li><a href="http://spc.casetekcorp.com/" title="点此进入生产数据中心系统" class="easyui-tooltip" target="_blank">生产数据中心</a></li>
					<li><a href="http://ehr.casetekcorp.com:8092/" title="点此进入EHR系統" class="easyui-tooltip" target="_blank">EHR系統</a></li>
					<li><a href="http://bpm.casetekcorp.com:8086/NaNaWeb/GP//ForwardIndex?hdnMethod=findIndexForward" title="点此进入GP电子签核系统" class="easyui-tooltip" target="_blank">GP电子签核系统</a></li>
				</ul>
				<div class="login_inner">
					<p>资讯部·生产系统课·分机 26707</p>
					<p>推荐使用Google Chrome、IE9及以上版本浏览器以获取最佳浏览效果和兼容性。</p>
					<p>请使用各自工号或者域账户/密码登陆，工号默认登陆密码：001。如提示无权限，请联系产线支援课开通，或点击右上角【帮助】查看如何申请权限。</p>
					<p style="color: red;"><b>请还在使用初始密码的账户务必修改密码并且牢记，勿将帐号借与他人。</b></p>
				</div>
			</div>
		</div>
		<div id="login_footer">
			Copyright &copy; 2014 CasetekGroup Inc. All Rights Reserved.
		</div>
	</div>
	</s:form>
  </body>
</html>