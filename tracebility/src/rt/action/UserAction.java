package rt.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.ldap.LdapContext;

import jiami.Encrypt;
import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import rt.dao.IUserDAO;
import rt.dao.IUserDAOImpl;
import rt.listener.SessionListener;
import rt.pojo.Node;
import rt.pojo.NodeTree;
import rt.pojo.UserInfo;
import rt.test.ResourceService;
import rt.test.ResourceServiceByUser;
import rt.util.LDAPAuthentication;
import rt.util.ReadProperties;
import rt.util.Struts2Utils;
import rt.util.TUtil;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{

	private static final long serialVersionUID = 2965957523565629149L;
	private static Logger log = Logger.getLogger(UserAction.class);
	IUserDAO userDAO;
	private String ip;
	private int onLineSessions;
	List<Node> listNode = null;
	List<UserInfo> listUsers;
	private String uid;
	private String uName;
	private String uPwd;
	private String loginType;
	private String newPassWord;
	private String oldPassword;
	private String testJSON;
	private JSONArray json;
	private String nodesJSON;
	private String uid_assgin_auth;
	private String nodeids;

	private String nodeid_up;
	private String nodename_up;
	private String pid_up;
	private String nodeurl_up;
	private int enable_up;
	private String target_up;
	private String rel_up;
	private String external_up;
	
	private String nodeid_new;
	private String nodename_new;
	private String pid_new;
	private String nodeurl_new;
	private int enable_new;
	private String target_new;
	private String rel_new;
	private String external_new;
	
	private String nodeid_del;
	private int initPwd = 0;
	private int status;
	
	public String doLogin(){
		userDAO = new IUserDAOImpl();
		UserInfo userInfo = null;
		
		if ("1".equals(loginType)){
			uid = uid.toUpperCase();
			userInfo = userDAO.doLogin(uid, uPwd);
			if(null != userInfo){//登录成功
				if (userInfo.getEnable() == 1){
					this.addFieldError("login.error", ReadProperties.ReadTip("user.forbade"));
					return INPUT;
				}
				if(userInfo.getUpwd().equals("001"))
					initPwd = 1;
			}else{
				this.addFieldError("login.error", ReadProperties.ReadTip("user.error"));
				log.error(uid+","+"登录失败");
				return INPUT;
			}
		} else if ("2".equals(loginType)){
			LDAPAuthentication ldap = new LDAPAuthentication();
			LdapContext ldapContext = null;
			ldapContext = ldap.authByLDAP(uid, uPwd, LDAPAuthentication.RP_DOMAIN);
			String domain = LDAPAuthentication.RP_DOMAIN;
			if(null == ldapContext){
				ldapContext = ldap.authByLDAP(uid, uPwd, LDAPAuthentication.OTHER_DOMAIN);
				domain = LDAPAuthentication.OTHER_DOMAIN;
			}
			if(null == ldapContext){
				ldapContext = ldap.authByLDAP(uid, uPwd, LDAPAuthentication.AVY_DOMAIN);
				domain = LDAPAuthentication.AVY_DOMAIN;
			}
			if(null != ldapContext){
				Map<String, Object> mapUser = ldap.searchUserInfo(ldapContext, uid, domain);
				if(!mapUser.isEmpty()){
					userInfo = userDAO.doLogin(mapUser.get("wWWHomePage").toString());
				}
			}else{//域账户登录失败
				this.addFieldError("domain.error", ReadProperties.ReadTip("domain.error"));
				return INPUT;
			}
		} else {
			try {
				log.info("catch anonymous visited:"+Struts2Utils.getRequest().getRemoteHost());
				Struts2Utils.getResponse().sendRedirect("login.jsp");
				return null;
			} catch (IOException e) {
				log.error("anonymous to login exception "+e.getMessage());
				e.printStackTrace();
			}
		}
		return getAuthority(userInfo);
	}
	
	public String getAuthority(UserInfo userInfo){
		//加载权限
		listNode = userDAO.getNode(userInfo);
		if(listNode.size() !=0){
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(Node node : listNode){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", node.getId());
				map.put("nodename", node.getNodename());
				map.put("nodedesc", node.getNodedesc());
				map.put("createdate", node.getCreatedate());
				map.put("nodeurl", node.getNodeurl());
				map.put("pId", node.getPid());
				map.put("enable", node.getEnable());
				map.put("title", node.getTitle());
				map.put("target", node.getTarget());
				map.put("rel", node.getRel());
				map.put("icon", node.getIcon());
				map.put("iconOpen", node.getIconOpen());
				map.put("external", node.getExternal());
				list.add(map);
			}
			JSONArray rootArray = JSONArray.fromObject(list);

			Struts2Utils.getRequest().setAttribute("json", rootArray.toString());
			Struts2Utils.getSession().setAttribute("username", userInfo.getUid()+","+userInfo.getUname());
			ip = Struts2Utils.getRequest().getRemoteAddr();
			onLineSessions = SessionListener.getOnlineSession();

			log.info("用户"+userInfo.getUid()+","+userInfo.getUname()+",IP："+ServletActionContext.getRequest().getRemoteHost()+"登录成功");
			return SUCCESS;
		}else{
			this.addFieldError("uWithOutAuth", ReadProperties.ReadTip("user.uWithOutAuth"));
			return INPUT;
		}
	}
	
	public void doLoginDialog(){
		userDAO = new IUserDAOImpl();
		UserInfo userInfo = null;
		try {
			userInfo = userDAO.doLogin(uid, uPwd);
			if(null != userInfo){//登录成功
				if(userInfo.getUpwd().equals("001"))
					initPwd = 1;
				Struts2Utils.getSession().setAttribute("username", userInfo.getUid()+","+userInfo.getUname());
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"navTabId\":\"\",\"rel\":\"\",\"callbackType\":\"closeCurrent\",\"forwardUrl\":\"\",\"confirmMsg\":\"\"}");
			}else{
				String msg = new String("工号或者密码输入有误，请确认后重新输入。".getBytes("UTF-8"),"ISO-8859-1");
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\""+msg+"\"}");
				log.error(uid+","+"登录失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String doSSO(){
		userDAO = new IUserDAOImpl();
		String suid = Struts2Utils.getRequest().getParameter("suid");
		try {
			suid = new Encrypt().decrypt(suid);
		} catch (Exception e) {
			log.error("从综合管理系统解码失败："+e.getMessage());
			e.printStackTrace();
		}
		UserInfo userInfo = userDAO.doLogin(suid);
		if(null != userInfo){
			return getAuthority(userInfo);
		} else {
			this.addFieldError("uWithOutAuth", ReadProperties.ReadTip("user.uWithOutAuth"));
			return INPUT;
		}
	}
	
	public String getAllUsers(){
		userDAO = new IUserDAOImpl();
		if(null != uid || null != uName )
			listUsers = userDAO.getAllUsers(uid.trim().toUpperCase(),uName.trim());
		return SUCCESS;
	}
	
	public String loadAllNodes(){
		userDAO = new IUserDAOImpl();
		List<Node> permission = userDAO.getAllNodes();
		
		ResourceService s = new ResourceService();

		Struts2Utils.getResponse().setContentType("text/json");
		nodesJSON = s.createTreeJson(permission);
		return SUCCESS;
	}
	
	public void updateNode(){
		Node node = new Node();
		node.setId(nodeid_up);
		node.setNodename(nodename_up);
		node.setPid(pid_up);
		node.setNodeurl(nodeurl_up);
		node.setEnable(enable_up);
		node.setTarget(target_up);
		node.setRel(rel_up);
		node.setExternal(external_up);
		
		userDAO = new IUserDAOImpl();
		
		if(userDAO.updateNode(node)){
			try {
				Struts2Utils.getResponse().getWriter().write("ok");
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	public void deletNode(){
		userDAO = new IUserDAOImpl();
		if(userDAO.deletNode(nodeid_del)){
			try {
				Struts2Utils.getResponse().getWriter().write("ok");
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	
	public void getNodeId() throws IOException{
		userDAO = new IUserDAOImpl();
		String pnode = Struts2Utils.getRequest().getParameter("pnode");
		Struts2Utils.getResponse().getWriter().write(userDAO.getNodeId(pnode));
	}
	
	public void addNode(){
		Node node = new Node();
		node.setId(nodeid_new);
		node.setNodename(nodename_new);
		node.setPid(pid_new);
		node.setNodeurl(nodeurl_new);
		node.setEnable(enable_new);
		node.setTarget(target_new);
		node.setRel(rel_new);
		node.setExternal(external_new);
		userDAO = new IUserDAOImpl();
		if(userDAO.addNode(node))
			try {
				Struts2Utils.getResponse().getWriter().write("ok");
			} catch (IOException e) {
				log.error("addNode Error:"+e.getMessage());
				try {
					Struts2Utils.getResponse().getWriter().write(e.getMessage());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
	}
	
	public String loadNodesByUser(){
		userDAO = new IUserDAOImpl();
		List<NodeTree> permission = userDAO.getNodesByTree();

		String result =userDAO.getAuthority(uid);

		for (int i=0;i<permission.size();i++){
			String id = permission.get(i).getId()+";";
			if(result.indexOf(id) != -1){
				permission.get(i).setChecked(true);
			}
		}
		ResourceServiceByUser s = new ResourceServiceByUser();

		Struts2Utils.getResponse().setContentType("text/json");
		nodesJSON = s.createTreeJson(permission);

		return SUCCESS;
	}
	
	public void assignAuthority(){
		userDAO = new IUserDAOImpl();

		List<Object> distinctNodes = new ArrayList<Object>();
		String[] array = nodeids.split(",");
		for(String node : array){
			distinctNodes.add(node);
			String test = userDAO.getNodeList(node);
			distinctNodes.add(test);
			while (null != test){
				test = userDAO.getNodeList(test);
				if (null != test)
					distinctNodes.add(test);
			}
		}

		if(userDAO.assignAuthority(uid_assgin_auth, new TUtil().removeDuplicate(distinctNodes))){
			try {
				Struts2Utils.getResponse().getWriter().write("ok");
			} catch (IOException e) {
				log.error("assignAuthority Exception:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void doLoginOut(){
		Struts2Utils.getRequest().getSession().invalidate();
	}
	
	public String cpredirect(){
		return SUCCESS;
	}
	
	public void checkOldPwd(){
		userDAO = new IUserDAOImpl();
		uid = uid.substring(0,uid.indexOf(","));
		if(userDAO.checkOldPwd(uid, oldPassword)){
			try {
				Struts2Utils.getResponse().getWriter().write("ok");
			} catch (IOException e) {
				log.error(uid+"旧密码验证失败"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void changePassword(){
		userDAO = new IUserDAOImpl();
		uid = uid.substring(0,uid.indexOf(","));
		if(userDAO.changePassword(uid, newPassWord)){
			try {
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"message\":\"密码修改成功\",\"navTabId\":\"\",\"rel\":\"\",\"callbackType\":\"closeCurrent\",\"forwardUrl\":\"\",\"confirmMsg\":\"\"}");
			} catch (IOException e) {
				log.error(uid+"changePassword失败"+e.getMessage());
				e.printStackTrace();
			}
			log.info(Struts2Utils.getRequest().getSession().getAttribute("username")+"修改了密码:"+newPassWord);
		}else{
			try {
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\"密码修改失败，请重试。\"}");
			} catch (IOException e) {
				log.error("回传会话失败异常："+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void resetPassword(){
		userDAO = new IUserDAOImpl();
		if(userDAO.resetPwd(uid)){
			try {
				Struts2Utils.getResponse().getWriter().write("ok");
			} catch (IOException e) {
				log.error("回传重置密码成功异常："+e.getMessage());
				e.printStackTrace();
			}
			log.info(Struts2Utils.getRequest().getSession().getAttribute("username")+"为工号"+uid+"重置了密码");
		}else{
			try {
				Struts2Utils.getResponse().getWriter().write("ng");
			} catch (IOException e) {
				log.error("回传重置密码ng异常："+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void userStatus(){
		userDAO = new IUserDAOImpl();
		if(userDAO.userStatus(uid, status)){
			try {
				Struts2Utils.getResponse().getWriter().write("状态修改成功!");
			} catch (IOException e) {
				log.error("userStatus异常"+e.getMessage());
				e.printStackTrace();
			}
			log.info(Struts2Utils.getRequest().getSession().getAttribute("username")+"为工号"+uid+"̬设置状态为："+status);
		}else{
			try {
				Struts2Utils.getResponse().getWriter().write("状态修改失败!");
			} catch (IOException e) {
				log.error("回传状态修改失败异常："+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void delUser(){
		userDAO = new IUserDAOImpl();
		if(userDAO.delUser(uid)){
			try {
				Struts2Utils.getResponse().getWriter().write("ok");
			} catch (IOException e) {
				log.error("删除用户异常："+e.getMessage());
				e.printStackTrace();
			}
			log.info(Struts2Utils.getRequest().getSession().getAttribute("username")+"删除了工号："+uid);
		}else{
			try {
				Struts2Utils.getResponse().getWriter().write("ng");
			} catch (IOException e) {
				log.error("call back ng status exception:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void checkUidExist(){
		userDAO = new IUserDAOImpl();
		if(!userDAO.checkUidExist(uid)){
			try {
				Struts2Utils.getResponse().getWriter().write("ok");
			} catch (IOException e) {
				log.error("checkUidExist excption:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void addUser(){
		userDAO = new IUserDAOImpl();
		if(userDAO.addUser(uid,uName)){
			try {
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"message\":\"用户添加成功！\"}");
			} catch (IOException e) {
				log.error("添加用户异常："+e.getMessage());
				e.printStackTrace();
			}
		}else{
			try {
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"301\", \"message\":\"会话已过期，请重新登录！\"}");
			} catch (IOException e) {
				log.error("会话超时回传异常："+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	/**                           公共属性                                        */
	
	public String index(){
		return SUCCESS;
	}
	
	public int getInitPwd() {
		return initPwd;
	}

	public void setInitPwd(int initPwd) {
		this.initPwd = initPwd;
	}

	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUName() {
		return uName;
	}
	public void setUName(String name) {
		uName = name;
	}
	public String getUPwd() {
		return uPwd;
	}
	public void setUPwd(String pwd) {
		uPwd = pwd;
	}

	public List<UserInfo> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<UserInfo> listUsers) {
		this.listUsers = listUsers;
	}

	public JSONArray getJson() {
		return json;
	}

	public void setJson(JSONArray json) {
		this.json = json;
	}

	public String getTestJSON() {
		return testJSON;
	}

	public void setTestJSON(String testJSON) {
		this.testJSON = testJSON;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	public String getNodesJSON() {
		return nodesJSON;
	}

	public void setNodesJSON(String nodesJSON) {
		this.nodesJSON = nodesJSON;
	}

	public String getUid_assgin_auth() {
		return uid_assgin_auth;
	}

	public void setUid_assgin_auth(String uid_assgin_auth) {
		this.uid_assgin_auth = uid_assgin_auth;
	}

	public String getNodeids() {
		return nodeids;
	}

	public void setNodeids(String nodeids) {
		this.nodeids = nodeids;
	}

	public String getNodeid_up() {
		return nodeid_up;
	}

	public void setNodeid_up(String nodeid_up) {
		this.nodeid_up = nodeid_up;
	}

	public String getNodename_up() {
		return nodename_up;
	}

	public void setNodename_up(String nodename_up) {
		this.nodename_up = nodename_up;
	}

	public String getPid_up() {
		return pid_up;
	}

	public void setPid_up(String pid_up) {
		this.pid_up = pid_up;
	}

	public String getNodeurl_up() {
		return nodeurl_up;
	}

	public void setNodeurl_up(String nodeurl_up) {
		this.nodeurl_up = nodeurl_up;
	}

	public String getTarget_up() {
		return target_up;
	}

	public void setTarget_up(String target_up) {
		this.target_up = target_up;
	}

	public String getRel_up() {
		return rel_up;
	}

	public void setRel_up(String rel_up) {
		this.rel_up = rel_up;
	}

	public String getExternal_up() {
		return external_up;
	}

	public void setExternal_up(String external_up) {
		this.external_up = external_up;
	}
	public int getEnable_up() {
		return enable_up;
	}

	public void setEnable_up(int enable_up) {
		this.enable_up = enable_up;
	}

	public String getNodeid_new() {
		return nodeid_new;
	}

	public void setNodeid_new(String nodeid_new) {
		this.nodeid_new = nodeid_new;
	}

	public String getNodename_new() {
		return nodename_new;
	}

	public void setNodename_new(String nodename_new) {
		this.nodename_new = nodename_new;
	}

	public String getPid_new() {
		return pid_new;
	}

	public void setPid_new(String pid_new) {
		this.pid_new = pid_new;
	}

	public String getNodeurl_new() {
		return nodeurl_new;
	}

	public void setNodeurl_new(String nodeurl_new) {
		this.nodeurl_new = nodeurl_new;
	}

	public int getEnable_new() {
		return enable_new;
	}

	public void setEnable_new(int enable_new) {
		this.enable_new = enable_new;
	}

	public String getTarget_new() {
		return target_new;
	}

	public void setTarget_new(String target_new) {
		this.target_new = target_new;
	}

	public String getRel_new() {
		return rel_new;
	}

	public void setRel_new(String rel_new) {
		this.rel_new = rel_new;
	}

	public String getExternal_new() {
		return external_new;
	}

	public void setExternal_new(String external_new) {
		this.external_new = external_new;
	}

	public String getNodeid_del() {
		return nodeid_del;
	}

	public void setNodeid_del(String nodeid_del) {
		this.nodeid_del = nodeid_del;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getOnLineSessions() {
		return onLineSessions;
	}

	public void setOnLineSessions(int onLineSessions) {
		this.onLineSessions = onLineSessions;
	}
	
}
