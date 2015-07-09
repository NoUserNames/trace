/**
 * 
 */
package rt.dao;

import java.util.List;

import rt.pojo.Node;
import rt.pojo.NodeTree;
import rt.pojo.UserInfo;

/**
 * 用户信息处理类
 * @author 张强
 *
 */
public interface IUserDAO {
	
	/**
	 * 登录后加载用户对应菜单
	 * @param userInfo 页面采集的用户信息
	 * @return 用户信息
	 */
	public List<Node> getNode(UserInfo userInfo);
	
	/**
	 * 加载树形的权限清单，为分配权限使用
	 * @return
	 */
	public List<NodeTree> getNodesByTree();
	
	/** 
	 * 获取指定用户的权限集合
	 * @param uid
	 * @return
	 */
	public String getAuthority(String uid);
	
	/**
	 * 为用户授权。
	 * @param uid 工号
	 * @param nodeid 权限编号清单
	 * @return
	 */
	public boolean assignAuthority(String uid,List<Object> nodeid);
	
	/**
	 * 获取节点的父节点
	 * @param nodeid 节点编号
	 * @return 父节点
	 */
	public String getNodeList(String nodeid);
	
	/**
	 * 加载所有菜单，为编辑菜单使用
	 * @return
	 */
	public List<Node> getAllNodes();
	
	/**
	 * 更新指定菜单节点
	 * @param node
	 * @return
	 */
	public boolean updateNode(Node node);
	
	/**
	 * 计算新子节点ID
	 * @param pnode
	 * @return
	 */
	public String getNodeId(String pnode);
	
	/**
	 * 添加节点
	 * @param node
	 * @return
	 */
	public boolean addNode(Node node);
	
	/**
	 * 删除节点
	 * @param nodeid
	 * @return
	 */
	public boolean deletNode(String nodeid);
	
	/**
	 * 从SPC登陆
	 * @param uid
	 * @return
	 */
	public UserInfo doLogin(String uid);
	
	/**
	 * 正常登陆
	 * @param uName 用户名
	 * @param uPwd 密码
	 * @return 用户信息
	 */
	public UserInfo doLogin(String uName,String uPwd);
	
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<UserInfo> getAllUsers(String uid,String uname);
	
	/**
	 * 新增工号前验证工号是否重复
	 * @param uid
	 * @return
	 */
	public boolean checkUidExist(String uid);
	
	/**
	 * 新增工号
	 * @param uid
	 * @param uname
	 * @param upwd
	 * @return
	 */
	public boolean addUser(String uid,String uname);
	
	/**
	 * 修改密码前验证旧密码
	 * @param uid 工号
	 * @param oldPassWord 输入的旧密码
	 * @return True || False
	 */
	public boolean checkOldPwd(String uid,String oldPassWord);
	
	/**
	 * 修改密码
	 * @param uid 工号
	 * @param newPassWord 新密码
	 * @return 成功 || 失败
	 */
	public boolean changePassword(String uid,String newPassWord);
	
	/**
	 * 重置密码
	 * @param uid
	 * @return
	 */
	public boolean resetPwd(String uid);
	
	/**
	 * 更改工号状态
	 * @param uid 工号
	 * @param flag 状态
	 * @return 成功 || 失败
	 */
	public boolean userStatus(String uid,int flag);
	
	/**
	 * 删除工号
	 * @param uid 工号
	 * @return 成功 || 失败
	 */
	public boolean delUser(String uid);
	
	public String getEmpName(String emp_no);
}