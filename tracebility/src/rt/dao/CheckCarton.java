package rt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rt.connection.DBManager;

public class CheckCarton {

	private DBManager db ;
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	public List<String> listCarton_no(){
		System.out.println("检索数据");
		db = new DBManager();
		connection = db.GetOraConnection();
		String sqlList = "select  distinct(a.carton_no) from sajet.g_sn_status a,sajet.sys_part b where a.model_id=b.part_id and b.model_name in("+
				" 'Hulk DH','Hulk TC','Ruby DH','Ruby TC','AV4 DH','AV4 TC') and"+
				" substr(customer_sn,1,3)='DYH'"+
				" union all"+
				" select   distinct(a.carton_no) from sajet.g_sn_status a,sajet.sys_part b where a.model_id=b.part_id and b.model_name in("+
				" 'AV9 DHS',"+
				" 'AV9 DHY',"+
				" 'AV9 TCS',"+
				" 'AV9 TCY',"+
				" 'AV9 BCS',"+
				" 'AV9 BCY',"+
				" 'AV9 BC',"+
				" 'AV9 TC',"+
				" 'AV9 DH') and substr(customer_sn,1,3)='DXW'";
		List<String> carton_nos = new ArrayList<String>();
		try {
			rs = connection.prepareStatement(sqlList).executeQuery();
			while(rs.next()){
				carton_nos.add(rs.getString("carton_no"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return carton_nos;
	}
	
	public boolean checkCarton_no(String carton_no){
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			String sql ="select s.carton_no,s.customer_sn,s.work_order from sajet.g_sn_status s where s.carton_no=? order by s.out_process_time";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, carton_no);
			rs = pstmt.executeQuery();
			List<String> modelName = new ArrayList<String>();
			while(rs.next()){
				String fatp = rs.getString("customer_sn");
				String work_order = rs.getString("work_order");
				if(work_order.contains("AV9") || work_order.contains("AV4") || work_order.contains("HULK")){
					if(!fatp.subSequence(0, 2).equals("DXW")){
						
					}
				}
			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally{
			db.closeConnection(connection, rs, pstmt);
		}
		
	}
}
