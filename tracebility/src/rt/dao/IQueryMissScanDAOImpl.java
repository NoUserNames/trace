package rt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import rt.connection.DBManager;

public class IQueryMissScanDAOImpl implements IQueryMissScanDAO {

	private static Logger log = Logger.getLogger(IQueryMissScanDAOImpl.class);
	
	private DBManager db;
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	@Override
	public List<LinkedHashMap<String, Object>> queryMissScan(String partNO,
			String process, String timeB, String timeE, boolean travel_falg) {
		String sql = "";
		if(travel_falg){
			sql = queryMissScanTravel(process);
		}else{
			sql = queryMissScanNonTravel(process);
		}

		db = new DBManager();
		connection = db.GetOraConnection();
		List<LinkedHashMap<String, Object>> listMissScan = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, partNO);
			pstmt.setString(2, partNO);
			pstmt.setString(3, timeB);
			pstmt.setString(4, timeE);
			if (!process.equals("0")){
				pstmt.setString(5, process);
			}
			rs = pstmt.executeQuery();
			listMissScan = new ArrayList<LinkedHashMap<String, Object>>();
			while(rs.next()){
				LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
				map.put("process_name", rs.getString("process_name"));
				map.put("terminal_name", rs.getString("terminal_name"));
				map.put("trev_type", rs.getString("trev_type"));
				map.put("trev", rs.getString("trev"));
				map.put("errTime", rs.getString("errTime"));
				map.put("carton_no", rs.getString("carton_no"));
				map.put("out_process_time", rs.getString("out_process_time"));
				map.put("emp_no", rs.getString("emp_no"));
				map.put("emp_name", rs.getString("emp_name"));

				listMissScan.add(map);
			}
		} catch (SQLException e) {
			log.error("查询漏扫出错："+e.getMessage());
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return listMissScan;
	}

	/**
	 * 不包含过站记录的漏扫信息
	 * @param partNO
	 * @param process
	 * @param timeB
	 * @param timeE
	 * @return
	 */
	public String queryMissScanNonTravel(String process){
		StringBuffer sql =new StringBuffer("select e.*, f.emp_no, f.emp_name"+
				  " from (select b.process_name,c.terminal_name,a.trev_type,a.trev,"+
				" to_char(a.update_time, 'YYYY-MM-DD HH24:MI:SS') errTime,"+
				" d.carton_no,to_char(d.out_process_time,'yyyy-mm-dd hh24:mi:ss') out_process_time,a.update_userid"+
				" from sajet.g_trev_log a,"+
				" sajet.sys_process  b,"+
				" sajet.sys_terminal c,"+
				" sajet.g_sn_status d"+
				" where a.trev_type like 'DYH%'"+
				" and a.process_id = b.process_id"+
				" and a.process_id in"+
				" (select next_process_id from sajet.sys_route_detail where route_id in (select route_id from sajet.sys_part where part_no = ?))"+
				" and a.terminal_id = c.terminal_id"+
				" and a.trev_type = d.serial_number and d.model_id = (select part_id from sajet.sys_part where part_no = ?)"+
				" and a.update_time >=to_date(?, 'yyyy-mm-dd hh24:mi')"+
				" and a.update_time <to_date(?, 'yyyy-mm-dd hh24:mi')");
				if (!process.equals("0")){
					sql.append("and b.process_id = ?) e");
				} else {
					sql.append(") e");
				}
				sql.append(" left join sajet.sys_emp f on f.emp_id = e.update_userid");
		return sql.toString();
	}
	
	/**
	 * 包含过站记录的漏扫信息
	 * @param partNO
	 * @param process
	 * @param timeB
	 * @param timeE
	 * @param travel_falg
	 * @return
	 */
	public String queryMissScanTravel(String process){
		StringBuffer sql =new StringBuffer("select e.*,to_char(f.out_process_time,'YYYY-MM-DD HH24:MI:SS') out_process_time,g.emp_no,g.emp_name from ("+
				" select b.process_name,b.process_id,c.terminal_name,a.trev_type,a.trev,"+
				" to_char(a.update_time, 'YYYY-MM-DD HH24:MI:SS') errTime,d.carton_no,a.update_userid"+
				" from sajet.g_trev_log a, sajet.sys_process b, sajet.sys_terminal c,sajet.g_sn_status d"+
				" where a.trev_type like 'DYH%'"+
				" and a.process_id = b.process_id"+
				" and a.process_id in(select next_process_id from sajet.sys_route_detail where route_id in(select route_id from sajet.sys_part where part_no=?))"+
				" and a.terminal_id = c.terminal_id"+
				" and a.trev_type=d.serial_number and d.model_id = (select part_id from sajet.sys_part where part_no = ?)"+
				" and a.update_time >=to_date(?, 'yyyy-mm-dd hh24:mi')"+
				" and a.update_time <=to_date(?, 'yyyy-mm-dd hh24:mi')");
		if (!process.equals("0")){
			sql.append(" and b.process_id=? ) e left OUTER join sajet.g_sn_travel f on e.trev_type= f.serial_number and f.process_id =e.process_id left join sajet.sys_emp g on e.update_userid=g.emp_id");
		}else{
			sql.append(" ) e left OUTER join sajet.g_sn_travel f on e.trev_type= f.serial_number and f.process_id =e.process_id left join sajet.sys_emp g on e.update_userid=g.emp_id");
		}
		return sql.toString();
	}
}
