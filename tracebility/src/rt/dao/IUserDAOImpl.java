package rt.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import rt.connection.DBManager;
import rt.pojo.Node;
import rt.pojo.NodeTree;
import rt.pojo.UserInfo;
import rt.util.Struts2Utils;
import rt.util.TUtil;

/**
 * @author Administrator
 *
 */
public class IUserDAOImpl implements IUserDAO {

	public static Logger log = Logger.getLogger(IUserDAO.class);
	
	private DBManager db;
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	public static void main(String[] args){
		IUserDAOImpl dao = new IUserDAOImpl();
		List<Object> list = new ArrayList<Object>();
		String nodes = "1001,100101,100102,100103,100104,100105,100106,100107,100108,100109,100110,100111,100112,100113,100114,1002,100201,100202,1003,100301,100302,100303,100304,100401,100402,100403,100404,1005010101,10050102";
		String[] array = nodes.split(",");
		
		for(String node : array){
			list.add(node);
			String test = dao.getNodeList(node);
			list.add(test);
			while (null != test){
				test = dao.getNodeList(test);
				if (null != test)
					list.add(test);
			}
		}
		System.out.println(new TUtil().removeDuplicate(list));
	}
	
	public List<Node> getNode(UserInfo userInfo) {
		List<Node> listNode = new ArrayList<Node>();
		db = new DBManager();
		connection = db.GetSqliteConnection();
		String sql = "select n.nodeid,n.nodename,n.nodedesc,n.createdate,n.nodeurl,n.pnode,n.title"
			+" ,n.target,n.rel,n.icon,n.iconOpen,n.external from node_user_interconnected nui"
			+" inner join userinfo u on nui.uid = u.uid"
			+" inner join node n on nui.nodeid = n.nodeid"
			+" where u.uid = ? and u.upwd = ? and u.enable =0";
		Node node = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userInfo.getUid());
			pstmt.setString(2, userInfo.getUpwd());
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				node = new Node();
				node.setId(rs.getString("nodeid"));
				node.setNodename(rs.getString("nodename"));
				node.setNodedesc(rs.getString("nodedesc"));
				node.setCreatedate(rs.getInt("createdate"));
				node.setNodeurl(rs.getString("nodeurl"));
				node.setPid(rs.getString("pnode"));
				node.setTitle(rs.getString("title"));
				node.setTarget(rs.getString("target"));
				node.setRel(rs.getString("rel"));
				node.setIcon(rs.getString("icon"));
				node.setIconOpen(rs.getString("iconOpen"));
				node.setExternal(rs.getString("external"));
				
				listNode.add(node);
			}
		} catch (SQLException e) {
			log.error("登陆查询出错："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listNode;
	}

	@Override
	public UserInfo doLogin(String name, String pwd) {// and u.enable =0
		String sql = "select u.uid,u.uname,u.upwd,u.uDisplayName,u.enable from userinfo u where u.uid = ? and u.upwd = ?";
		UserInfo userinfo = null ;
		db = new DBManager();
		connection = db.GetSqliteConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, pwd);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				userinfo = new UserInfo();
				userinfo.setUid(rs.getString("uid"));
				userinfo.setUname(rs.getString("uname"));
				userinfo.setUpwd(rs.getString("upwd"));
				userinfo.setUdisplayName(rs.getString("uDisplayName"));
				userinfo.setEnable(rs.getInt("enable"));
				
			}
		} catch (SQLException e) {
			log.error("登录异常："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}		
		return userinfo;
	}

	/**
	 * 登录成功后更新最后登录时间
	 */
	public void updateLoginDate(){
		
	}
	
	@Override
	public UserInfo doLogin(String uid) {
		String sql = "select u.uid,u.uname,u.upwd,u.uDisplayName,u.enable from userinfo u where u.uid = ?";
		UserInfo userinfo = null ;
		db = new DBManager();
		connection = db.GetSqliteConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, uid);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				userinfo = new UserInfo();
				userinfo.setUid(rs.getString("uid"));
				userinfo.setUname(rs.getString("uname"));
				userinfo.setUpwd(rs.getString("upwd"));
				userinfo.setUdisplayName(rs.getString("uDisplayName"));
				userinfo.setEnable(rs.getInt("enable"));
			}
		} catch (SQLException e) {
			log.error("登录异常："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return userinfo;
	}
	
	@Override
	public List<UserInfo> getAllUsers(String uid,String uname) {//SELECT * FROM USERinfo LIMIT 100 OFFSET 0;--从0开始，取100行
		StringBuffer sql =new StringBuffer("select * from userinfo where 1=1");
		if(uid.length() !=0){
			sql.append(" and uid = '"+uid+"'");
		}
		if(uname.length() !=0){
			sql.append(" and uname = '"+uname+"'");
		}
		sql.append(" order by uid");
//		System.out.println("getAllUsers sql = "+sql.toString());
		List<UserInfo> listUsers = new ArrayList<UserInfo>();
		db = new DBManager();
		connection = db.GetSqliteConnection();
		try {
			rs = connection.createStatement().executeQuery(sql.toString());
			UserInfo userInfo;
			while(rs.next()){
				userInfo = new UserInfo();
				userInfo.setUid(rs.getString("uid"));
				userInfo.setUname(rs.getString("uname"));
				userInfo.setUpwd(rs.getString("upwd"));
				userInfo.setUdisplayName(rs.getString("uDisplayName"));
				userInfo.setEnable(rs.getInt("enable"));
				
				listUsers.add(userInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listUsers;
	}
	
	public List<Node> getPerssion(){
		List<Node> listNode = new ArrayList<Node>();
		try {
			String sql="select * from node order by nodeid";
			db = new DBManager();
			connection = db.GetSqliteConnection();
			rs = connection.createStatement().executeQuery(sql);
			Class<?> cls;
			cls = Class.forName("rt.pojo.Node");
			Node node = null;
			Field[] field = cls.getDeclaredFields();//获取实体类所有声明属性
			while(rs.next()){
				node = (Node) cls.newInstance();
				for(int i=0;i<field.length;i++){
					BeanUtils.setProperty(node, field[i].getName(), rs.getObject(i+1));
				}
				listNode.add(node);
			}
		} catch (SQLException e) {
			log.error("查询权限失败："+e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("没有找到实体类");
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("创建实体类实例异常");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("非法访问实体异常");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.out.println("设置属性目标值异常");
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listNode;
	}
	
	@Override
	public boolean checkOldPwd(String uid,String oldPassWord){
		String sql ="select upwd from userinfo where uid='"+uid+"'";
		db = new DBManager();
		connection = db.GetSqliteConnection();
		try {
			rs = connection.createStatement().executeQuery(sql);
			if(rs.next()){
				if (rs.getString("upwd").equals(oldPassWord))
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			log.error("检查旧密码异常："+e.getMessage());
			e.printStackTrace();
		} finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return false;
	}
	
	@Override
	public boolean changePassword(String uid,String newPassWord){
		String sql="update userinfo set upwd = '"+newPassWord+"' where uid ='"+uid+"'";
		db = new DBManager();
		connection = db.GetSqliteConnection();
		int result = 0;
		try {
			result = connection.createStatement().executeUpdate(sql);
		} catch(Exception e){
			log.error(uid+"更新密码失败："+e.getMessage());
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return result != 0 ? true : false;
	}
	
	@Override
	public List<Node> getAllNodes(){
		String sql="select nodeid,nodename,nodeurl,pnode,enable,target,rel,external from node order by nodeid";
		db = new DBManager();
		connection = db.GetSqliteConnection();
		List<Node> nodes = new ArrayList<Node>();
		try {
			rs = connection.createStatement().executeQuery(sql);

			while(rs.next()){
				Node node  = new Node();
				node.setId(rs.getString("nodeid"));
				node.setNodename(rs.getString("nodename"));
				node.setPid(rs.getString("pnode"));
				node.setNodeurl(rs.getString("nodeurl"));
				node.setEnable(rs.getInt("enable"));
				node.setTarget(rs.getString("target"));
				node.setRel(rs.getString("rel"));
				node.setExternal(rs.getString("external"));
				nodes.add(node);
			}
		} catch (SQLException e) {
			log.error("获取一级目录异常："+e.getMessage());
			e.printStackTrace();
		} finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return nodes;
	}
	
	@Override
	public List<NodeTree> getNodesByTree(){
		String sql="select nodeid,nodename,nodeurl,pnode from node order by nodeid";
		DBManager db = new DBManager();
		Connection connection = db.GetSqliteConnection();
		List<NodeTree> nodes = new ArrayList<NodeTree>();
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				NodeTree node  = new NodeTree();
				String nodeid = rs.getString("nodeid");
				node.setId(nodeid);
				node.setText(rs.getString("nodename"));
				node.setPid(rs.getString("pnode"));
				nodes.add(node);
			}
		} catch (SQLException e) {
			log.error("获取一级目录异常："+e.getMessage());
			e.printStackTrace();
		} finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return nodes;
	}
	
	public String getNodeList(String nodeid){
		db = new DBManager();
		connection = db.GetSqliteConnection();
		String sql = "select pnode from node where nodeid='"+nodeid+"'";
		String pid = null ;
		try {
			rs = connection.createStatement().executeQuery(sql);
			if(rs.next())
				pid = rs.getString("pnode");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return pid;
	}

	@Override
	public String getAuthority(String uid){
		String sql ="select nui.uid,nui.nodeid from node_user_interconnected nui where nui.uid='"+uid+"'";
		db = new DBManager();
		connection = db.GetSqliteConnection();
		String result = "";
		try {
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				result += rs.getString("nodeid")+";";
			}
		} catch (SQLException e) {
			log.error("getAuthority Error:"+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return result;
	}

	@Override
	public boolean assignAuthority(String uid,List<Object> nodeids){
		String sql ="delete from node_user_interconnected where uid = '"+uid+"'";
		
		db = new DBManager();
		connection = db.GetSqliteConnection();
		
		try {
			connection.createStatement().executeUpdate(sql);
			db.closeConnection(connection, rs, pstmt);
			
			connection = db.GetSqliteConnection();
			sql = "insert into node_user_interconnected (uid,nodeid) values (?,?)";
			pstmt = connection.prepareStatement(sql);
			for(Object node : nodeids){
				pstmt.setString(1, uid);
				pstmt.setString(2, node.toString());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			log.info(Struts2Utils.getSession().getAttribute("username")+"为工号"+uid+"授予权限"+nodeids);
		} catch (SQLException e) {
			log.error("授权失败，准备回滚操作："+e.getMessage());	
			return false;
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return true;
	}
	
	public boolean updateNode(Node node){
		String sql = "update node set nodename =?,nodeurl =?,pnode =?,enable =?,target=?,rel=?,external=? where nodeid=?";
		db = new DBManager();
		connection = db.GetSqliteConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, node.getNodename());
			pstmt.setString(2, node.getNodeurl());
			pstmt.setString(3, node.getPid());
			pstmt.setInt(4, node.getEnable());
			pstmt.setString(5, node.getTarget());
			pstmt.setString(6, node.getRel());
			pstmt.setString(7, node.getExternal());
			pstmt.setString(8, node.getId());
			int res = pstmt.executeUpdate();
//			System.out.println(res);
			return res != 0 ? true : false;
		} catch (SQLException e) {
			log.error("updateNode Error:"+e.getMessage());
			e.printStackTrace();			
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return false;
	}
	
	public String getNodeId(String pnode){
//		String sql = "select max(nodeid)+1 newNodeId from node where nodeid like '%"+pnode+"%'";
		String sql = "select max(nodeid) +1 newNodeId from node where nodeid like '"+pnode+"%'";
//		System.out.println("getNodeId sql="+sql);
		db = new DBManager();
		connection = db.GetSqliteConnection();
		try {
			rs = connection.createStatement().executeQuery(sql);
			if(rs.next())
				return rs.getString("newNodeId");
			else
				return pnode+"01";
//			if(rs.next()){
//				if(rs.getString("newNodeId") == null){
//					System.out.println("没有找到节点");
//					
//				}else{
//					System.out.println("新节点是："+rs.getString("newNodeId"));
//				}
//			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
	}
	
	public boolean addNode(Node node){
		String sql = "insert into node (nodeid,nodename,createdate,nodeurl,pnode,enable,target,rel,external) values(?,?,?,?,?,?,?,?,?)";
		db = new DBManager();
		connection = db.GetSqliteConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, node.getId());
			pstmt.setString(2, node.getNodename());
			pstmt.setString(3, TUtil.format("yyyy-MM-dd HH:mm:ss"));
			pstmt.setString(4, node.getNodeurl());
			pstmt.setString(5, node.getPid());
			pstmt.setInt(6, node.getEnable());
			pstmt.setString(7, node.getTarget());
			pstmt.setString(8, node.getRel());
			pstmt.setString(9, node.getExternal());
			int res = pstmt.executeUpdate();

			return res != 0 ? true : false;
		} catch (SQLException e) {
			log.error("addNode Error:"+e.getMessage());
			e.printStackTrace();			
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return false;
	}
	
	public boolean deletNode(String nodeid){
		try {
			db = new DBManager();
			connection = db.GetSqliteConnection();
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			statement.addBatch("delete from node where nodeid='"+nodeid+"'");
			statement.addBatch("delete from node_user_interconnected where nodeid ='"+nodeid+"'");
			statement.executeBatch();
			connection.commit();
			return true;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(e1.getMessage());
//				e1.printStackTrace();
			}
			log.error("deleetNode Error :"+e.getMessage());
//			e.printStackTrace();
			return false;
		}finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}
	
	public boolean resetPwd(String uid){
		String sql="update userinfo set upwd = '001' where uid ='"+uid+"'";
		db = new DBManager();
		connection = db.GetSqliteConnection();
		int result = 0;
		try {
			result = connection.createStatement().executeUpdate(sql);
		} catch(Exception e){
			log.error(uid+"重置密码失败："+e.getMessage());
			return false;
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return result != 0 ? true : false;
	}
	
	public boolean userStatus(String uid,int flag){
		String sql="update userinfo set enable = "+flag+" where uid ='"+uid+"'";
		db = new DBManager();
		connection = db.GetSqliteConnection();
		int result = 0;
		try {
			result = connection.createStatement().executeUpdate(sql);
		} catch(Exception e){
			log.error(uid+"更改工号"+uid+"状态失败："+e.getMessage());
			return false;
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return result != 0 ? true : false;
	}
	
	public boolean delUser(String uid){
		String sql="delete from userinfo where uid ='"+uid+"'";
		db = new DBManager();
		connection = db.GetSqliteConnection();
		int result = 0;
		try {
			result = connection.createStatement().executeUpdate(sql);
		} catch(Exception e){
			log.error(uid+"删除工号"+uid+"失败："+e.getMessage());
			return false;
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return result != 0 ? true : false;
	}
	
	public boolean checkUidExist(String uid){
		String sql ="select uid from userinfo where uid='"+uid+"'";
		db = new DBManager();
		connection = db.GetSqliteConnection();
		try {
			rs = connection.createStatement().executeQuery(sql);
			if(rs.next()){
				if (rs.getString("uid").equals(uid))
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			log.error("检查工号是否重复异常："+e.getMessage());
			e.printStackTrace();
		} finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return false;
	}
	
	public boolean addUser(String uid,String uname){
		String sql ="insert into userinfo (uid,uname,upwd,enable) values ('"+uid+"','"+uname+"','001',0)";
		db = new DBManager();
		connection = db.GetSqliteConnection();
		try {
			int k = connection.createStatement().executeUpdate(sql);
			return k !=0 ? true : false;
		} catch (SQLException e) {
			log.error("addUser异常："+e.getMessage());
			e.printStackTrace();
			return false;
		} finally{
			db.closeConnection(connection, rs, pstmt);
		}
	}

	@Override
	public String getEmpName(String emp_no) {
		String sql ="select emp_name from sajet.sys_hr_emp where emp_no = ?";
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, emp_no);
			rs = pstmt.executeQuery();
			if(rs.next())
				return rs.getString("emp_name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return "";
	}
}