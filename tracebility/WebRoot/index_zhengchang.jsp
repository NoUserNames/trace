<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>Tracebility-管理平台</title>
		<link href="css/popup.right.css" type="text/css" rel="stylesheet"><!-- 右下角弹窗 -->
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
		<script src="js/dwz.regional.zh.js" type="text/javascript"></script>
		
		<script type="text/javascript" src="js/dtree.js"></script>

		<script type="text/javascript">
		
		
			$(function(){
				DWZ.init("dwz.frag.xml", {
					//loginUrl:"login_dialog.html", loginTitle:"登录",
					loginUrl:"login.jsp",
					statusCode:{ok:200, error:300, timeout:301},
					pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"},
					keys: {statusCode:"statusCode", message:"message"},
					ui:{hideMode:'offsets'},
					debug:false,
					callback:function(){
						initEnv();
						$("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
					}
				});
			});
			
		</script>
		<!--<script language="JavaScript">
			var _t;
			window.onbeforeunload = function() {
				setTimeout(function() {
					_t = setTimeout(onunloadcancel, 0)
				}, 0);
				invalidate();
				//return "";//确定要退出系统吗？
			}
			window.onunloadcancel = function() {
				clearTimeout(_t);
				//alert("取消离开");
			}-->
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
						<li>
							<a style="text-decoration: none" title="在线人数">
								<img alt="" height="26" src="img/onLine.png">
								<br/>在线人数&nbsp;
								<s:property value="onLineSessions"/>
							</a>
						</li>
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
						<h2><a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">收起所有</a></h2>
						<div>收缩</div>
					</div>

					<div class="accordion" fillSpace="sidebar">
						<div class="accordionContent">
							 <div class="dtree">							
								<script type="text/javascript">
									<!--
									d = new dTree('d');
									d.add(0,-1,'<b>菜单项</b>');
									<s:iterator value="listNode" id="node" status="index">
									d.add(<s:property value='#node.id'/>,<s:property value="#node.pid"/>,'<s:property value="#node.nodename"/>','<s:property value="#node.nodeurl"/>','','<s:property value="#node.target"/>','page<s:property value="#index.index+1"/>','<s:property value="#node.external"/>');
									</s:iterator>
									
									document.write(d);
									//-->
								</script>
							</div>							 
						</div>						
					</div>
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
						<!-- 
						<div class="divider"></div>
						<marquee style="color: red" scrolldelay=0 onmouseout=this.start() onMouseOver=this.stop() scrollamount=1 direction="left" >
							<img width="20" height="15" src="img/laba.png" align="left">
							重要通知：从2015.3.19起，tracebility管理平台迁至新服务器，将使用新地址trace.casetekcorp.com访问，原访问地址停止使用，请相互转告。
						</marquee>
						<div class="divider"></div>
						-->

						<!-- 通知栏 -->
						<div class="pageContent" layoutH="150">
							<div style="width:50%;border:0px solid #e66;margin:0px;float:left;min-height:100px">
								<div style="border:0px solid #B8D0D6;padding:0px;margin:0px">
									<div style="border: 0px solid #B8D0D6; padding: 0px; margin: 0px">
										<div class="panel collapse" defH="120">
											<!-- collapse展开 -->
											<h1>【公共查询模块清单】</h1>
											<div>
												<table class="table" width="100%">
													<tbody>
														<tr>
															<td width="33%">不良代码查询&nbsp;&nbsp;<img alt=""
																src="img/new_en.gif"></td>
															<td width="33%">量测数据查询&nbsp;&nbsp;<img alt=""
																src="img/new_en.gif"></td>
															<td width="33%">CNC打点查询(未解码)&nbsp;&nbsp;<img alt=""
																src="img/new_en.gif"></td>
														</tr>
														<tr>
															<td>出货扫描单号查询</td>
															<td>卡关异常资料查询</td>
															<td>APS颜色分Bin查询</td>
														</tr>
														<tr>
															<td>制程不良信息查询</td>
															<td>产品不良信息查询</td>
															<td>产品履历查询</td>
														</tr>
														<tr>
															<td>生产在制查询</td>
															<td>箱号数量详情查询</td>
															<td>查询投入产出</td>
														</tr>
														<tr>
															<td>查询镭雕品质</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
								<!-- 
								<div style="border:1px solid #B8D0D6;padding:5px;margin:5px">A4</div>
								<div style="border:1px solid #B8D0D6;padding:5px;margin:5px">A5</div>
								 -->
							</div>
							
							<div style="width:50%;border:0px solid #e66;margin:0px;float:left;min-height:100px"><!-- class="sortDrag" -->
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
								<!-- 
								<div style="border:1px solid #B8D0D6;padding:5px;margin:5px">B3</div>
								<div style="border:1px solid #B8D0D6;padding:5px;margin:5px">B4</div>
								<div style="border:1px solid #B8D0D6;padding:5px;margin:5px">B5</div>
								-->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
			
		<div id="footer">
			Copyright &copy; 2014 CasetekGroup Inc. All Rights Reserved.
		</div>

	<!-- 右下角弹出层 -->
	<SCRIPT type=text/javascript src="js/popup.right.js"></SCRIPT>
	<DIV id=floatad-winpop
		style="Z-INDEX: 999; RIGHT: 0px; OVERFLOW: hidden; BOTTOM: 0px; POSITION: fixed; BACKGROUND-COLOR: #fff; _position: absolute">
		<DIV>
			<SPAN class=close onclick="tips_pop()"><IMG title=关闭 height=13
				alt=关闭 src="img/close.jpg" width=13></SPAN>
		</DIV>
		<DIV class=floatad-floatBorder>
			<div class="part02-ad">
				重要通知：<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;从2015.3.23起，tracebility管理平台迁至新服务器，将使用新地址trace.casetekcorp.com访问，原访问地址停止使用。
			</div>
		</DIV>
	</DIV>
</body>
</html>