package rt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rt.connection.DBManager;

public class ICustomizeDAOImpl implements ICustomizeDAO {

	public static void main(String[] args){
		System.out.println(Long.parseLong("2012062100056"));
//		ICustomizeDAOImpl d = new ICustomizeDAOImpl();
		
//		d.updateStatus(d.updateStatusBySN("测试", 123, "DYH0100081474676"));
	}
	
	private static Logger log = Logger.getLogger(ICustomizeDAOImpl.class);
	
	private DBManager db ;
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	@Override
	public boolean MaintainWIP(ArrayList<ArrayList<String>> serials,String handle) {
		String sql = "update sajet.g_sn_status set enabled = ? where serial_number= ?";
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			pstmt = connection.prepareStatement(sql);

			for(ArrayList<String> list : serials){
				pstmt.setString(1, handle);
				pstmt.setString(2, list.get(0));

				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (SQLException e) {
			log.error("维护WIP状态异常："+e.getMessage());
			e.printStackTrace();
			return false;
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return true;
	}

	public String qryStatus(String serial){
		String sql = "select (case enabled when 'N' then '禁用' else '正常' end) status from sajet.g_sn_status where serial_number='"+serial+"'";
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			rs = connection.createStatement().executeQuery(sql);
			if(rs.next()){
				return rs.getString("status");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return null;
	}
	
	public List<Map<String,Object>> qrySampleBySN(String serial_number,String r_type){
//		System.out.println("有二维码查借出信息");
		String sql = "select a.serial_number,a.exp_time,a.exp_emp,a.worder_number,a.reason,c.model_name"+
				" from sajet.G_AT1_Status a, sajet.g_sn_status b, sajet.sys_part c"+
				" where a.serial_number = b.serial_number"+
				" and c.part_id = b.model_id and a."+r_type+" = '"+serial_number+"' and a.status = 1";

//		System.out.println("sql="+sql);
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				Map<String,Object> sampleMap =  new HashMap<String,Object>();
				sampleMap.put("serial_number", rs.getString("serial_number"));
				sampleMap.put("exp_time", rs.getString("exp_time"));
				sampleMap.put("exp_emp", rs.getString("exp_emp"));
				sampleMap.put("worder_number", rs.getString("worder_number"));
				sampleMap.put("model_name", rs.getString("model_name"));
				sampleMap.put("reason", rs.getString("reason"));
				
				mapList.add(sampleMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		
		return mapList;
	}
	
	public Map<String,Object> qrySampleByWorderNum(long worder_number){
		String sql = "select a.exp_qty,a.serial_number,a.worder_number,a.exp_time,a.exp_emp,a.reason,nvl(a.imp_qty,0) as qty from sajet.G_AT1_Status a where a.worder_number="+worder_number+" and a.status = '0'";
		List<Map<String,Object>> mapList =  new ArrayList<Map<String,Object>>();
		db = new DBManager();
		connection = db.GetOraConnection();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				map.put("serial_number", rs.getString("serial_number"));
				map.put("w_exp_qty", rs.getString("exp_qty"));
				map.put("w_qty", rs.getString("qty"));
				map.put("w_worder_number", rs.getString("worder_number"));
				map.put("w_exp_time", rs.getString("exp_time"));
				map.put("w_exp_emp", rs.getString("exp_emp"));
				map.put("w_reason", rs.getString("reason"));
				
				mapList.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return map;
	}
	
	@Override
	public boolean updateStatusByWorderNum(String reason,String imp_emp,long worderNumber){
//		System.out.println("reason="+reason);
		String sql = "update sajet.G_AT1_Status set IMP_TIME=sysdate,status = '3' ,REASON = REASON || '&' || '"+reason+"',IMP_EMP=(select emp_id from sajet.sys_hr_emp where emp_no = '"+imp_emp+"'),IMP_QTY = nvl(EXP_QTY,0)-nvl(IMP_QTY,0) where WORDER_NUMBER=" + worderNumber;

		String sql1 = "insert into sajet.G_HT_AT1_Status select * from sajet.G_AT1_Status where (WORDER_NUMBER="+worderNumber+")";

		String sql2 = "update  sajet.G_AT1_Status set IMP_QTY = EXP_QTY where WORDER_NUMBER="+worderNumber+"";

		String sql3 = "delete from sajet.G_AT1_Status where (WORDER_NUMBER="+worderNumber+") and (IMP_QTY >= EXP_QTY)";
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			connection.setAutoCommit(false);
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);
			stmt.executeUpdate(sql3);
			System.out.println("sql="+sql);
			System.out.println("sql1="+sql1);
			System.out.println("sql2="+sql2);
			System.out.println("sql3="+sql3);
//			System.out.println("sql4="+sql4);
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("updateStatusByWorderNum Exception:"+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				log.error("updateStatusByWorderNum rollback Exception:"+e1.getMessage());
			}
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}
	
	@Override
	public boolean updateStatusBySN(String reason,String emp,String serial_number,long terminal_id,String r_type){
//		System.out.println("dao r_type="+r_type);
		String sql = "update sajet.G_AT1_Status set IMP_TIME=sysdate,status = '2' ,reason ='"+reason+"' ,IMP_EMP=(select emp_id from sajet.sys_hr_emp where emp_no = '"+emp+"') where serial_number";
		if(r_type.equals("serial_number")){
			sql += " ='"+serial_number+"'";
		} else {
			sql += " in (select serial_number from sajet.g_at1_status where worder_number = '"+serial_number+"')";
		}
		
		String sql1 = "insert into sajet.G_HT_AT1_Status select * from sajet.G_AT1_Status where serial_number";
		if(r_type.equals("serial_number")){
			sql1 += " ='"+serial_number+"'";
		} else {
			sql1 += " in (select serial_number from sajet.g_at1_status where worder_number = '"+serial_number+"')";
		}
		
		String sql2 = "delete from sajet.G_AT1_Status where serial_number";
		if(r_type.equals("serial_number")){
			sql2 += " ='"+serial_number+"'";
		} else {
			sql2 += " in (select serial_number from sajet.g_at1_status where worder_number = '"+serial_number+"')";
		}
		
		String sql3 = "update sajet.g_sn_status set in_process_time = out_process_time,out_process_time = sysdate,emp_id = (select emp_id from sajet.sys_hr_emp where emp_no = '"+emp+"'),process_id = (select process_id from sajet.sys_terminal where terminal_id="+terminal_id+"),terminal_id = "+terminal_id+
			" where serial_number";
		if(r_type.equals("serial_number")){
			sql3 += " ='"+serial_number+"'";
		} else {
			sql3 += " in (select serial_number from sajet.g_at1_status where worder_number = '"+serial_number+"')";
		}
		sql3 += " and out_process_time = (select out_process_time from (select out_process_time,route_id,process_id from sajet.G_SN_TRAVEL where serial_number";
		if(r_type.equals("serial_number")){
			sql3 += " ='"+serial_number+"'";
		} else {
			sql3 += " in (select serial_number from sajet.g_at1_status where worder_number = '"+serial_number+"')";
		}
		sql3 +=" order by out_process_time desc) where rownum = 1) and rownum = 1";
		
		
		String sql4 ="insert into sajet.g_sn_travel"+
				" select work_order,serial_number,model_id,version,route_id,pdline_id,stage_id,"+
				" process_id,terminal_id,next_process,current_status,work_flag,in_process_time,"+
				" out_process_time,in_pdline_time,out_pdline_time,enc_cnt,pallet_no,carton_no,"+
				" container,qc_no,qc_result,customer_id,warranty,rework_no,emp_id,shipping_id,"+
				" customer_sn,rc_no,innerbox_no,wip_process,ec,rp_station,recount_type,weight,"+
				" last_process_id,last_terminal_id,rework_cnt,model_name,enabled,"+
				" sajet.g_sn_travel_recid_seq.nextval"+
				" from sajet.g_sn_status where serial_number";
		if(r_type.equals("serial_number")){
			sql4 += " ='"+serial_number+"'";
		} else {
			sql4 += " in (select serial_number from sajet.g_at1_status where worder_number = '"+serial_number+"')";
		}
//		sql3 +=" and out_process_time = (select out_process_time from (select out_process_time,route_id,process_id from sajet.G_SN_TRAVEL where serial_number";
//		if(r_type.equals("serial_number")){
//			sql3 += " ='"+serial_number+"'";
//		} else {
//			sql3 += " in (select serial_number from sajet.g_at1_status where worder_number = '"+serial_number+"')";
//		}
//		sql3 +=" order by out_process_time desc) where rownum = 1"+" ) and rownum = 1";
		sql4 += "and rownum = 1";
//		System.out.println("sql="+sql);
//		System.out.println("sql1="+sql1);
//		System.out.println("sql2="+sql2);
//		System.out.println("sql3="+sql4);
//		System.out.println("sql4="+sql3);
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			connection.setAutoCommit(false);
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);
			stmt.executeUpdate(sql3);
			stmt.executeUpdate(sql4);
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(serial_number+"销帐异常："+e.getMessage());
			try {
				log.info("销帐处理开始回滚");
				connection.rollback();
				log.info("销帐处理回滚成功");
			} catch (SQLException e1) {
				log.error(serial_number+"销帐失败，并且回滚异常："+e1.getMessage());
				e1.printStackTrace();
			}
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}
	
}