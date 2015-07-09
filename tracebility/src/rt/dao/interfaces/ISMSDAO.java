/**
 * 
 */
package rt.dao.interfaces;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import rt.pojo.SMSContactExternalGroup;
import rt.pojo.SMSContactGroup;

/**
 * 短信功能-数据处理模块接口
 * @author 张强
 *
 */
public interface ISMSDAO {

	/**
	 * 查询群组
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryContactGroup();
	
	/**
	 * 增加通讯录群组
	 * @param smsContactGroup
	 * @return 成功||失败
	 */
	public boolean addContactGroup(SMSContactGroup smsContactGroup);
	
	/**
	 * 查询要更新的群组内容
	 * @param groupId
	 * @return
	 */
	public SMSContactGroup contactGroupEditQuery(String groupId);
	
	/**
	 * 更新通讯录群组
	 * @param smsContactGroup
	 * @return
	 */
	public boolean updateContactGroup(SMSContactGroup smsContactGroup);
	
	/**
	 * 删除群组
	 * @param groupId 群组编号
	 * @return
	 */
	public boolean delContactGroup(String groupId);
	
	/**
	 * 新增短信接收人
	 * @param SMSContact 短信接收人数据模型
	 * @return true || false
	 */
	public boolean addContact(SMSContactExternalGroup smsctg);
	
	/**
	 * 删除短信接收人
	 * @param emp_no
	 * @param group_id
	 * @return
	 */
	public boolean delContact(String[] emp_nos);
	
	/**
	 * 修改短信接收人
	 * @param smsctg
	 * @return
	 */
	public boolean editContact(SMSContactExternalGroup smsctg,String sms_group_old);
	
	/**
	 * 获取数据总量
	 * @param tableName表名
	 * @return 总数量
	 */
	public int getTotalCount(String tableName);
	
	/**
	 * 查询通讯录信息
	 * @param numPerPage 每页显示数据量
	 * @param currentPage 当前页
	 * @return
	 */
	public List<LinkedHashMap<String,Object>> queryContacts(int numPerPage, int currentPage, boolean page, int factory_id, String group_id);
	
	/**
	 * 编辑信息前查询信息
	 * @param emp_no_edit
	 * @return
	 */
	public LinkedHashMap<String,Object> queryContacts(String emp_no_edit);
	
	/**
	 * 发送短信
	 * @param emp_no 发送人工号
	 * @param revice_name 接收人姓名
	 * @param mobile_phone 短信接收号码
	 * @param msg 信息内容
	 * @return
	 */
	public boolean sendSMS(String[] revice_name, String[] mobile_phone, String msg, String sender, String flag);
	
	/**
	 * 按群组编号查询成员信息
	 * @param group_id 群组编号
	 * @return
	 */
	public List<HashMap<String,Object>> queryContactsByGroup(String group_id) throws SQLException;
}
