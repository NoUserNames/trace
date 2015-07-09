<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Tracebility-管理平台</title>
		<link href="css/popup.right.css" type="text/css" rel="stylesheet">
		<link href="themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
		<link href="uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
		<link rel="StyleSheet" href="css/dtree.css" type="text/css" />
		<!--
		<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
		<//-->
		
		<!--
		<script src="js/speedup.js" type="text/javascript"></script>
		<//-->
		<script src="js/jquery.min.js"></script>
		<script src="js/jquery-1.7.2.js" type="text/javascript"></script>
		<script src="js/jquery.cookie.js" type="text/javascript"></script>
		<script src="js/jquery.validate.js" type="text/javascript"></script>
		<script src="js/jquery.bgiframe.js" type="text/javascript"></script>
		<script src="xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
		<script src="xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
		<script src="uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>
		
		<script type="text/javascript" src="chart/raphael.js"></script>
		<script type="text/javascript" src="chart/g.raphael.js"></script>
		<script type="text/javascript" src="chart/g.bar.js"></script>
		<script type="text/javascript" src="chart/g.line.js"></script>
		<script type="text/javascript" src="chart/g.pie.js"></script>
		<script type="text/javascript" src="chart/g.dot.js"></script>
		
		<script src="bin/dwz.min.js" type="text/javascript"></script>
		<script src="js/jobHandle.js" type="text/javascript"/></script>
		<script src="js/dwz/dwz.regional.zh.js" type="text/javascript"></script>
		
		<script type="text/javascript" src="js/dtree.js"></script>

		<script type="text/javascript">
			$(function(){
				DWZ.init("dwz.frag.xml", {
					loginUrl:"login.jsp",
					statusCode:{ok:200, error:300, timeout:301},
					pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"},
					keys: {statusCode:"statusCode", message:"message"},
					ui:{hideMode:'offsets'},
					debug:false,
					callback:function(){
						initEnv();
						$("#themeList").theme({themeBase:"themes"});
					}
				});

			    var data = ${json};
			    $("#jTree").html(getJTree(data,0));
			});

		    function getContente(data,pId){
		        var treeFormat = getTree(data,pId).replaceAll("<ul></ul>","");
		        if(treeFormat.length>4){
		            treeFormat = "<ul class='tree treeFolder'>"+treeFormat.substring(4,treeFormat.length);
		        }
		        return treeFormat;
		    }

		    function getTree(data,pId){
		        var tree="";
		        tree = '<ul>';
		        var index=0;
		        for(var i in data){
		            if(data[i].pId == pId){

		                if(data[i].nodeurl!=null && data[i].nodeurl.length > 0){
		                    tree += "<li><a href='"+data[i].nodeurl+"' target='navTab' rel='"+data[i].rel+[index]+"' external='"+data[i].external+"'>"+data[i].nodename+"</a>";
		                }else{
		                    tree += "<li><a href='#' target='_blank'>"+data[i].nodename+"</a>";
		                }

		                tree += getTree(data,data[i].id);
		                tree += "</li>";
		            }
		            index ++;
		        }
		        return tree += "</ul>";
		    }
		    function getJTree(data,header){
		        var jTree="";
		        for(var i in data){  

		            if(data[i].pId==header){
		                jTree += "<div class='accordionHeader'><h2><span>Folder</span>"+data[i].nodename+"</h2></div>";
		                jTree += "<div class='accordionContent'>";

		                jTree += getContente(data,data[i].id);
		                jTree += "</div>";
		            }
		        }
		        return jTree;
		    }
		</script>
	</head>

	<body scroll="no" onDragStart="return false" ><!-- onSelectStart="return false" oncontextmenu="return false"-->
		<div id="layout">
			<div id="header">
				<div class="headerNav">
					<a class="logo">标志</a>
					<ul class="nav">
						<li>
							<a style="text-decoration: none" title="当前登录IP">
								<img alt="" height="26" src="img/ip.jpg">
								<br/><s:property value="ip"/>
							</a>
						</li>
						<!--<li>
							<a style="text-decoration: none" title="在线人数">
								<img alt="" height="26" src="img/onLine.png">
								<br/>在线人数&nbsp;
								<s:property value="onLineSessions"/>
							</a>
						</li>-->
						<li>
							<img alt="" height="26" src="img/user.png">
							<a style="text-decoration: none" title="个人信息">
								<br/>&nbsp;<b><s:property value="#session.username"/></b>
							</a>
						</li>
						<li>
							<a width="510" mask="true" target="dialog" href="cpredirect" title="修改密码">
								<img height="26" src="img/user_edit.png">
								<br/>修改密码
							</a>
						</li>
						<li>
							<a href="help/help.html" target="_blank" title="查看帮助">
								<img height="26" src="img/help.png">
								<br/>帮助信息
							</a>
						</li>
						<li>
							<a style="cursor: hand;" onclick="logout()" title="安全退出">
								<img height="26" src="img/logout.png">
								<br/>安全退出
							</a>
						</li>
					</ul>
					
					<ul class="themeList" id="themeList">
						<li title="海蓝" theme="default"><div class="selected">海蓝</div></li>
						<li title="天蓝" theme="azure"><div>天蓝</div></li>
						<li title="青绿" theme="green"><div>青绿</div></li>
						<li title="潮红" theme="purple"><div>潮红</div></li>
						<li title="银色" theme="silver"><div>银色</div></li>
					</ul>
				</div>
			</div>
			<div id="leftside">
				<div id="sidebar_s">
					<div class="collapse">
						<div class="toggleCollapse">
							<div></div>
						</div>
					</div>
				</div>
				<div id="sidebar">
					<div class="toggleCollapse">
						<h2>Tracebility管理平台</h2>
						<div>collapse</div>
					</div>  
					<div id="jTree" class="accordion" fillSpace="sideBar"></div>  
				</div>
			</div>
			<div id="container">
				<div id="navTab" class="tabsPage">
					<div class="tabsPageHeader">
						<div class="tabsPageHeaderContent">
							<ul class="navTab-tab">
								<li tabid="main" class="main">
									<a href="javascript:;">
										<span>
											<span class="home_icon">我的主页</span>
										</span>
									</a>
								</li>
							</ul>
						</div>
						<div class="tabsLeft">
							left
						</div>
						
						<div class="tabsRight">
							right
						</div>
						
						<div class="tabsMore">
							more
						</div>
					</div>
					<ul class="tabsMoreList">
						<li>
							<a href="javascript:;">我的主页</a>
						</li>
					</ul>
					<div class="navTab-panel tabsPageContent layoutBox">
						<!-- 通知栏 -->
						<div class="pageContent" layoutH="10">
							<div style="width:49%;border:0px solid #e66;margin:0px;float:left;min-height:80px">
								<div style="border:0px solid #B8D0D6;padding:0px;margin:0px">
									<div class="panel" defH="200">
										<h1><img src="img/notice.png"></h1>
										<div style="height: 190;font-size: 16px;color: red">
											<center style="font-size: 20px"><b>紧急通知</b></center>
											<span style="font-size: 16px;">各位同仁：<hr/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现在系统数据出现异常，目前正在紧急恢复中，受影响的范围：企划4小时报表录入、查询、工序管理，WIP状态维护，产品销帐功能。其他查询功能不受影响，可以正常使用。对此造成的不便，敬请谅解。</span>
											<div align="right">资讯部</div><hr/>
											<div align="right">2015/7/2</div>
										</div>
									</div>
								</div>

								<div style="border:0px solid #B8D0D6;padding:0px;margin:0px">
									<div class="panel" defH="200">
										<h1><img src="img/notice.png"></h1>
										<div style="height: 190;font-size: 16px;">
											<center style="font-size: 20px"><b>系统公告</b></center>
											<span style="font-size: 16px;">各位同仁：<hr/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;从即日起，环安部将针对盗用他人账户进行冻结产品二维码操作的行为进行清查，对查明属实盗用他人账户的人员开除处分！同时请大家修改默认密码，不要把自己的帐号密码透露给他人，以免被滥用受到连带处罚。</span>
											<div align="right">环安部</div><hr/>
											<div align="right">2015/6/24</div>
										</div>
									</div>
								</div>
							</div>

							<div style="width:49%;border:0px solid #e66;margin:0px;float:left;min-height:100px"><!-- class="sortDrag" -->
								<div style="border: 0px solid #B8D0D6; padding: 0px; margin: 0px">
									<div style="border:0px solid #B8D0D6;padding:0px;margin:0px">
									<div class="panel" defH="28">
										<h1>【申请模块权限请联系下列人员，点击姓名即可发送申请邮件。】</h1>
										<div>
											<a href="mailto:Feng3_Yuan@casetekcorp.com">
												<font color="blue">
													<b><span style="font-size: 20">【袁凤】</span></b>
												</font>
											</a>
											<a href="mailto:Charles_Zhao@casetekcorp.com">
												<font color="blue">
													<b><span style="font-size: 20">【赵阔】</span></b>
												</font>
											</a>
										</div>
									</div>
									</div>
								</div>

								<div style="border:0px solid #B8D0D6;padding:0px;margin:0px">
									<div class="panel collapse" defH="80">
										<h1>【日沛 资讯部 TSG课 夜班值班人员明细】</h1>
										<div>
											<table class="list" width="100%">
												<thead>
													<tr>
														<th>姓名</th>
														<th>长号</th>
														<th>短号</th>
														<th>分机</th>
														<th>备注</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>蘇明輝</td>
														<td>13817561653</td>
														<td>61653</td>
														<td>26981</td>
														<td>处理客户端以及网络异常</td>
													</tr>
													<tr>
														<td>李   宇</td>
														<td>15921098043</td>
														<td>68043</td>
														<td>26982</td>
														<td>处理生产系统相关异常</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>

								<div style="border:0px solid #B8D0D6;padding:0px;margin:0px">
									<div class="panel collapse" defH="80">
										<h1>【胜瑞&日铭&日润 资讯部 夜班值班人员明细】</h1>
										<div>
											<table class="list" width="100%">
												<thead>
													<tr>
														<th>姓名</th>
														<th>长号</th>
														<th>短号</th>
														<th>分机</th>
														<th>备注</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>夏坤</td>
														<td>13641898943</td>
														<td>68003</td>
														<td>27214</td>
														<td>处理客户端以及网络异常</td>
													</tr>
													<tr>
														<td>吳鵬</td>
														<td>18721674915</td>
														<td>64915</td>
														<td>27214</td>
														<td>处理生产系统相关异常</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>

								<div style="border:0px solid #B8D0D6;padding:0px;margin:0px">
									<div class="panel" defH="25">
										<h1>【各常用系统链接】</h1>
										<div>
											<a target="_blank" href="http://bpm.casetekcorp.com:8086/NaNaWeb/GP//ForwardIndex?hdnMethod=findIndexForward">GP电子签核系统</a>
											&nbsp;&nbsp;|&nbsp;&nbsp;
											<a href="http://ehr.casetekcorp.com:8092" target="_blank" style="line-height: 19px">EHR系統</a>
											&nbsp;&nbsp;|&nbsp;&nbsp;
											<a href="http://spc.casetekcorp.com/" target="_blank">SPC生产数据中心</a>
											&nbsp;&nbsp;|&nbsp;&nbsp;
											<a href="http://mic.casetekcorp.com" target="_blank">量仪室作业系统</a>
											&nbsp;&nbsp;|&nbsp;&nbsp;
											<a href="http://oa.casetekcorp.com/" target="_blank">综合管理系統</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
			
		<div id="footer">
			Copyright &copy; 2014-2015 CasetekGroup Inc. All Rights Reserved.
		</div>

	<s:if test="initPwd == 1">
	<SCRIPT type=text/javascript src="js/popup.right.js"></SCRIPT>
	<DIV id=floatad-winpop style="Z-INDEX: 999; RIGHT: 0px; OVERFLOW: hidden; BOTTOM: 0px; POSITION: fixed; BACKGROUND-COLOR: #fff; _position: absolute">
		<DIV>
			<SPAN style="RIGHT:5px;CURSOR:pointer;COLOR:#fff;position: absolute;top: 5px" onclick="tips_pop()">
			<IMG title=关闭 height=13 alt=关闭 src="img/close.jpg" width=13></SPAN>
		</DIV>
		<DIV class=floatad-floatBorder>
			<div class="part02-ad">
				<br/>郑重提示<br/>系统检测到您还在使用默认密码，为防止您的帐号被盗用，请立即修改默认密码，并且牢记。不要把自己的帐号密码借给他人使用，以免被滥用收到公司连带处罚。
			</div>
		</DIV>
	</DIV>
	</s:if>
</body>
</html>