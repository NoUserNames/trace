package rt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import rt.connection.DBManager;
import rt.dao.interfaces.ISMSDAO;
import rt.pojo.SMSContact;
import rt.pojo.SMSContactExternalGroup;
import rt.pojo.SMSContactGroup;

/**
 * 短信-数据处理模块业务实现
 * @author 张强
 *
 */
public class ISMSDAOImpl implements ISMSDAO {

	private static Logger log = Logger.getLogger(ISMSDAOImpl.class);
	
	private DBManager db;
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	public List<LinkedHashMap<String,Object>> queryContactGroup(){
		String sql ="select group_id,group_name,group_desc,to_char(create_time,'yyyy-mm-dd hh24:mi:ss') create_time from sj.sms_contact_group";
		db = new DBManager();
		connection = db.GetOraTestConnection();
		List<LinkedHashMap<String,Object>> listContactGroup = new ArrayList<LinkedHashMap<String,Object>>();
		try {
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				LinkedHashMap<String,Object> group = new LinkedHashMap<String,Object>();
				group.put("group_id", rs.getString("group_id"));
				group.put("group_name", rs.getString("group_name"));
				group.put("group_desc", rs.getString("group_desc"));
				group.put("create_time", rs.getString("create_time"));
				listContactGroup.add(group);
			}
		} catch (SQLException e) {
			log.error("queryContactGroup Exception:"+e.getMessage());
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return listContactGroup;
	}
	@Override
	public boolean addContactGroup(SMSContactGroup smsContactGroup){
		String sql ="insert into sj.sms_contact_group(group_id,group_name,group_desc) values (?,?,?)";
		db = new DBManager();
		connection = db.GetOraTestConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, smsContactGroup.getGROUP_ID());
			pstmt.setString(2, smsContactGroup.getGROUP_NAME());
			pstmt.setString(3, smsContactGroup.getGROUP_DESC());
			return pstmt.executeUpdate() != -1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}
	
	@Override
	public SMSContactGroup contactGroupEditQuery(String groupId){
		db = new DBManager();
		connection = db.GetOraTestConnection();
		String sql = "SELECT * FROM SJ.SMS_CONTACT_GROUP WHERE GROUP_ID = '"+groupId+"'";
		SMSContactGroup smsContactGroup = new SMSContactGroup();
		try {
			rs = connection.prepareStatement(sql).executeQuery();
			while(rs.next()){
				smsContactGroup.setGROUP_ID(rs.getString("group_id"));
				smsContactGroup.setGROUP_NAME(rs.getString("group_name"));
				smsContactGroup.setGROUP_DESC(rs.getString("group_desc"));
				smsContactGroup.setCREATE_TIME(rs.getDate("create_time"));
			}
			return smsContactGroup;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}
	
	@Override
	public boolean updateContactGroup(SMSContactGroup smsContactGroup){
		db = new DBManager();
		connection = db.GetOraTestConnection();
		String sql ="update sj.sms_contact_group a set a.group_name=?,a.group_desc=? where a.group_id = ?";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, smsContactGroup.getGROUP_NAME());
			pstmt.setString(2, smsContactGroup.getGROUP_DESC());
			pstmt.setString(3, smsContactGroup.getGROUP_ID());
			return pstmt.executeUpdate() != -1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}
	
	@Override
	public boolean delContactGroup(String groupId){
		db = new DBManager();
		connection = db.GetOraTestConnection();
		String sql = "delete from sj.sms_contact_group where group_id = '"+groupId+"'";
		try {
			return connection.createStatement().executeUpdate(sql) != -1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}
	
	@Override
	public boolean addContact(SMSContactExternalGroup smsctg) {
		String sql ="insert into sj.sms_contact (EMP_NO,MOBILE_NUMBER,FACTORY_ID,EMP_NAME)"+
				" values ('"+smsctg.getSmsContact().getEmp_no()+"','"+smsctg.getSmsContact().getMobile_number()+"',"+smsctg.getSmsContact().getFactory_id()+",'"+smsctg.getSmsContact().getEmp_name()+"')";
		String sqlctg = "insert into sj.sms_contact_external_group values('"+smsctg.getSmsContact().getEmp_no()+"',"+smsctg.getSmsContactGroup().getGROUP_ID()+")";
		db = new DBManager();
		connection = db.GetOraTestConnection();
		Statement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.addBatch(sql);
			stmt.addBatch(sqlctg);
			int[] results = stmt.executeBatch();
			connection.commit();
			for(int result : results){
				if(result == -1)
					return false;
			}
			return true;
		} catch (SQLException e) {
			log.error("addContact Exception:"+e.getMessage());
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db.closeConnection(connection, rs, pstmt);
		}
	}

	@Override
	public boolean delContact(String[] emp_nos){
		db = new DBManager();
		connection = db.GetOraTestConnection();
		try {
			connection.setAutoCommit(false);
			Statement stmt = connection.createStatement();
			String sqlDelContact = "";
			String sqlDelContactctg ="";
			for(int i=0; i<emp_nos.length; i++){
				String emp_no = emp_nos[i].substring(0, emp_nos[i].lastIndexOf("-"));
				String group_id = emp_nos[i].substring(emp_nos[i].lastIndexOf("-")+1);
				sqlDelContact = "delete from sj.sms_contact where emp_no = '"+emp_no+"'";
				stmt.addBatch(sqlDelContact);
				sqlDelContactctg = "delete from sj.sms_contact_external_group where emp_no='"+emp_no+"' and group_id="+group_id;
				stmt.addBatch(sqlDelContactctg);
			}
			stmt.executeBatch();
			connection.commit();
			return true;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}

	@Override
	public int getTotalCount(String tableName){
		String sql = "select * from "+tableName;
		db = new DBManager();
		connection = db.GetOraTestConnection();
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			rs.last();
			int totalCount = rs.getRow();
			return totalCount;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return 0;
	}

	public List<LinkedHashMap<String,Object>> queryContacts(int numPerPage, int currentPage, boolean page, int factory_id, String group_id){
		int begin = (currentPage - 1) * numPerPage;
		int end = currentPage * numPerPage;
		String sql ="select * from sj.sms_contact_external_group sceg"+
					" inner join (select *"+
					  " from (select row_number() over(order by create_time) num,"+
					               " emp_no,"+
					               " mobile_number,"+
					               " case factory_id when 10001 then '日沛' when 10013 then '日腾一厂' when 10015 then '日铭' when 10016 then '日闰' when 10017 then '胜瑞' end factory_id,"+
					               " to_char(create_time, 'yyyy-mm-dd hh24:mi:ss') create_time,"+
					               " emp_name"+
					          " from sj.sms_contact )";
					if(page)
						sql += "where num > "+begin+" and num<= "+end;
					sql += " ) sms_contact on sceg.emp_no = sms_contact.emp_no"+
					" inner join sj.sms_contact_group scg on sceg.group_id = scg.group_id";
					if(!"0".equals(group_id))
						sql += " and scg.group_id = '"+group_id+"'";
					if(0 != factory_id)
						sql += " inner join sj.sms_contact c on sceg.emp_no = c.emp_no and c.factory_id = "+factory_id;
					sql += " order by sms_contact.factory_id";
		db = new DBManager();
		connection = db.GetOraTestConnection();
		List<LinkedHashMap<String,Object>> listContacts = new ArrayList<LinkedHashMap<String,Object>>();
		try {
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				LinkedHashMap<String,Object> contact = new LinkedHashMap<String,Object>();
				contact.put("emp_no", rs.getString("emp_no"));
				contact.put("mobile_number", rs.getString("mobile_number"));
				contact.put("factory_id", rs.getString("factory_id"));
				contact.put("create_time", rs.getString("create_time"));
				contact.put("emp_name", rs.getString("emp_name"));
				contact.put("group_id", rs.getString("group_id"));
				listContacts.add(contact);
			}
		} catch (SQLException e) {
			log.error("queryContacts Exception"+e.getMessage());
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return listContacts;
	}

	public LinkedHashMap<String,Object> queryContacts(String emp_no_edit){
//		String sql = "select * from sj.sms_contact where emp_no = '"+emp_no_edit+"'";
		String sql ="select * from sj.sms_contact_external_group sceg"+
				" inner join sj.sms_contact sc on sceg.emp_no = sc.emp_no and sc.emp_no='"+emp_no_edit+"'"+
				" inner join sj.sms_contact_group scg on sceg.group_id = scg.group_id";
		db = new DBManager();
		connection = db.GetOraTestConnection();
		LinkedHashMap<String,Object> contact = new LinkedHashMap<String,Object>();
		try {
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				contact.put("emp_no", rs.getString("emp_no"));
				contact.put("mobile_number", rs.getString("mobile_number"));
				contact.put("factory_id", rs.getString("factory_id"));
				contact.put("create_time", rs.getString("create_time"));
				contact.put("emp_name", rs.getString("emp_name"));
				contact.put("group_id", rs.getString("group_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return contact;
	}

	/**
	 * 修改通讯录联系人
	 * @param SMScontact
	 * @return
	 */
	public boolean editContact(SMSContact SMScontact){
		String sql ="update sj.sms_contact c set c.emp_no=?,c.mobile_number=?,c.factory_id=?,c.emp_name=? where emp_no=?";
		db = new DBManager();
		connection = db.GetOraTestConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, SMScontact.getEmp_no());
			pstmt.setString(2, SMScontact.getMobile_number());
			pstmt.setInt(3, SMScontact.getFactory_id());
			pstmt.setString(4, SMScontact.getEmp_name());
			pstmt.setString(5, SMScontact.getEmp_no());
			int k = pstmt.executeUpdate();
			return k != -1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}

	/**
	 * 修改关联关系
	 * @param smsctg
	 * @return
	 */
	public boolean editSmsctg(SMSContactExternalGroup smsctg,String sms_group_old){
		String sql ="update sj.sms_contact_external_group sceg set sceg.emp_no=?,sceg.group_id=? where sceg.emp_no=? and sceg.group_id=?";
		db = new DBManager();
		connection = db.GetOraTestConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, smsctg.getSmsContact().getEmp_no());
			pstmt.setString(2, smsctg.getSmsContactGroup().getGROUP_ID());
			pstmt.setString(3, smsctg.getSmsContact().getEmp_no());
			pstmt.setString(4, sms_group_old);
			int k = pstmt.executeUpdate();
			return k != -1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}

	public boolean editContact(SMSContactExternalGroup smsctg,String sms_group_old){
		return editContact(smsctg.getSmsContact()) && editSmsctg(smsctg,sms_group_old) ? true : false;
	}

	@Override
	public boolean sendSMS(String[] emp_name, String[] mobile_phone, String msg, String sender, String flag){
		String sql = "insert into mes.rmp_msg_detail (sname,mobile_phone,send_msg,sender,flg,send_time) values (?,?,?,?,?,?)";
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			java.util.Date datetime=new java.util.Date();
			java.sql.Timestamp time=new java.sql.Timestamp(datetime.getTime());
			for(int i=0;i < mobile_phone.length;i++){
				pstmt.setString(1, emp_name[i]);
				pstmt.setString(2, mobile_phone[i]);
				pstmt.setString(3, msg);
				pstmt.setString(4, sender);
				pstmt.setString(5, flag);
				pstmt.setTimestamp(6, time);
				
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
	}
	
	public List<HashMap<String,Object>> queryContactsByGroup(String group_id) throws SQLException{
		String sql ="select sc.emp_name,sc.emp_no,sc.mobile_number from sj.sms_contact_external_group sceg"+
				" inner join sj.sms_contact_group scg on sceg.group_id = scg.group_id and scg.group_id='"+group_id+
				"' inner join sj.sms_contact sc on sceg.emp_no = sc.emp_no";
		db = new DBManager();
		connection = db.GetOraTestConnection();
		rs = connection.createStatement().executeQuery(sql);
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		try{
			while(rs.next()){
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("emp_name", rs.getString("emp_name"));
				map.put("emp_no", rs.getString("emp_no"));
				map.put("mobile_number", rs.getString("mobile_number"));
				list.add(map);
			}
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return list;
	}
}
