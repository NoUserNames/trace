package rt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rt.connection.DBManager;
import rt.dao.DAOUtils;
import rt.dao.interfaces.IPlanningStageReportDAO;
import rt.pojo.G_Report_Record;
import rt.pojo.SYS_STAGE_REPORT_SETTING;
import rt.util.TUtil;

/**
 * @author 张强
 *
 */
public class IPlanningStageReportDAOImpl implements IPlanningStageReportDAO {
	private static Logger log = Logger.getLogger(IPlanningStageReportDAOImpl.class);
	
	private DBManager db;
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	@Override
	public boolean saveReport(G_Report_Record g_report_record) {
		String sql ="insert into sj.g_report_record (REPORT_RECORD_ID,STAGE_REPORT_SETTING_ID,TIME_ZONE,TIME_TYPE,TARGET_DAY,INPUT_CNT,OUTPUT_CNT,INPUT_NG_CNT,PROCESS_NG_CNT,INPUT_SCRAPT_CNT,PROCESS_SCRAPT_CNT,EMP_NO,UPDATE_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, g_report_record.getReport_record_id());
			pstmt.setString(2, g_report_record.getStage_report_setting_id());
			pstmt.setString(3, g_report_record.getTime_zone());
			pstmt.setInt(4, g_report_record.getTime_type());
			pstmt.setInt(5, g_report_record.getTarget_day());
			pstmt.setInt(6, g_report_record.getInput_cnt());
			pstmt.setInt(7, g_report_record.getOutput_cnt());
			pstmt.setInt(8, g_report_record.getInput_ng_cnt());
			pstmt.setInt(9, g_report_record.getProcess_ng_cnt());
			pstmt.setInt(10, g_report_record.getInput_scrapt_cnt());
			pstmt.setInt(11, g_report_record.getProcess_scrapt_cnt());
			pstmt.setString(12, g_report_record.getEmp_no());
			pstmt.setDate(13, java.sql.Date.valueOf(g_report_record.getUpdate_date()));
			int k = pstmt.executeUpdate();
			return  k != -1 ? true : false;
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}

	@Override
	public boolean saveAppend(String report_record_id, String field_id, int field_value, int original_cnt, String emp_no) {
		db = new DBManager();
		connection = db.GetOraConnection();
		String sql = "INSERT INTO sj.g_report_append (REPORT_RECORD_ID,NOTE_ID,APPEND_CNT,ORIGINAL_CNT,EMP_NO) VALUES (?,?,?,?,?)";
		String sql2 = "update sj.g_report_record set "+field_id.substring(0, field_id.lastIndexOf("_"))+" = "+field_value+" where report_record_id = '"+report_record_id+"'";

		try {
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, report_record_id);
			pstmt.setString(2, field_id);
			pstmt.setInt(3, field_value);
			pstmt.setInt(4, original_cnt);
			pstmt.setString(5, emp_no);
			pstmt.executeUpdate();
			
			connection.createStatement().executeUpdate(sql2);
			connection.commit();
			return true;
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}

	@Override
	public int saveComment(String report_record_id, String field_id, String comment,String emp_no) {
		db = new DBManager();
		connection = db.GetOraConnection();
		String sqlDel = "delete from sj.g_report_note where report_record_id = '"+report_record_id+"' and note_id = '"+field_id+"'";
		String sql = "insert into sj.g_report_note values ('"+report_record_id.replaceAll("or", "").replaceAll("--", "")+"','"+field_id.replaceAll("or", "").replaceAll("--", "")+"','"+comment.replaceAll("or", "").replaceAll("--", "")+"','"+emp_no+"')";
		try {
			connection.setAutoCommit(false);
			Statement stmt = connection.createStatement();
			stmt.addBatch(sqlDel);
			if(comment.length() !=0)
				stmt.addBatch(sql);
			stmt.executeBatch();
			connection.commit();
			return 0;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				log.error(e1.getMessage());
			}
			log.error(e.getMessage());
			e.printStackTrace();
			return 1;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}

	@Override
	public List<Map<String, Object>> query4hReport(String emp_no,String stage_report_setting_id) {
		db = new DBManager();
		connection = db.GetOraConnection();
		Date date = new Date();
		@SuppressWarnings("deprecation")
		int hour = date.getHours();
		String sql = "select a.*,b.*,c.*,(case a.time_zone when '08:00-12:00' then 1 when '12:00-16:00' then 2 when '16:00-20:00' then 3 when '20:00-24:00' then 4 when '00:00-04:00' then 5 when '04:00-08:00' then 6 end) time_index"+
				  " from sj.g_report_record a"+
				  " inner join sj.sys_stage_report_setting b on a.stage_report_setting_id = b.stage_report_setting_id and b.stage_report_setting_id = '"+stage_report_setting_id+"'"+
				  " inner join sj.g_report_process_emp c on a.emp_no = c.emp_no and c.stage_report_setting_id = b.stage_report_setting_id"+
				 " where a.time_type = 4";
		if(hour >= 9)//白班
			sql +=" and a.update_date = to_date('"+TUtil.GetDay(0)+"','yyyy-mm-dd')";
//				" and a.update_date <= to_date('"+TUtil.GetDay(0)+" 20','yyyy-mm-dd hh24')";
		if(hour < 9)
			sql +=" and a.update_date = to_date('"+TUtil.GetDay(-1)+"','yyyy-mm-dd')";
//				" and a.update_date <= to_date('"+TUtil.GetDay(0)+" 09','yyyy-mm-dd hh24')";
		sql += " order by a.update_date";System.out.println(sql);
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		try {
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("REPORT_RECORD_ID", rs.getString("REPORT_RECORD_ID"));
				map.put("STAGE_REPORT_SETTING_ID", rs.getString("STAGE_REPORT_SETTING_ID"));
				map.put("TIME_ZONE", rs.getString("TIME_ZONE"));
				map.put("TIME_TYPE", rs.getInt("TIME_TYPE"));
				map.put("TARGET_DAY", rs.getInt("TARGET_DAY"));
				map.put("INPUT_CNT", rs.getInt("INPUT_CNT"));
				map.put("OUTPUT_CNT", rs.getInt("OUTPUT_CNT"));
				map.put("INPUT_NG_CNT", rs.getInt("INPUT_NG_CNT"));
				map.put("PROCESS_NG_CNT", rs.getInt("PROCESS_NG_CNT"));
				map.put("INPUT_SCRAPT_CNT", rs.getInt("INPUT_SCRAPT_CNT"));
				map.put("PROCESS_SCRAPT_CNT", rs.getInt("PROCESS_SCRAPT_CNT"));
				map.put("EMP_NO", rs.getString("EMP_NO"));
				map.put("TIME_INDEX", rs.getString("TIME_INDEX"));
				
				results.add(map);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return results;
	}

	@Override
	public String queryComment(String alt, String field_name) {
		db = new DBManager();
		connection = db.GetOraConnection();
		String sql = "select emp_no,node_desc from sj.g_report_note where report_record_id = ? and note_id = ?";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, alt);
			pstmt.setString(2, field_name);
			rs = pstmt.executeQuery();
			if(rs.next()){
				String author = rs.getString("emp_no");
				String desc = rs.getString("node_desc");
				return (desc.length() == 0 ? "" : author+"\r\n"+desc);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "";
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return "";
	}

	@Override
	public String queryAppend(String alt, String field_name) {
		db = new DBManager();
		connection = db.GetOraConnection();
		String sql = "select append_cnt from sj.g_report_append where report_record_id = ? and note_id = ?";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, alt);
			pstmt.setString(2, field_name);
			rs = pstmt.executeQuery();
			if(rs.next())
				return rs.getString("append_cnt");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "";
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return "";
	}

	@Override
	public boolean stageReportSettingAdd(SYS_STAGE_REPORT_SETTING SYS_STAGE_REPORT_SETTING) {
		db = new DBManager();
		connection = db.GetOraConnection();
		String sql = "insert into sj.sys_stage_report_setting (stage_report_setting_id,part_no,process_id,process,dept_name,erp_process_id,model_name) values (?,?,?,?,?,?,?)";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, SYS_STAGE_REPORT_SETTING.getSTAGE_REPORT_SETTING_ID());
			pstmt.setString(2, SYS_STAGE_REPORT_SETTING.getPART_NO());
			pstmt.setInt(3, SYS_STAGE_REPORT_SETTING.getPROCESS_ID());
			pstmt.setString(4, SYS_STAGE_REPORT_SETTING.getPROCESS());
			pstmt.setString(5, SYS_STAGE_REPORT_SETTING.getDEPT_NAME());
			pstmt.setString(6, SYS_STAGE_REPORT_SETTING.getERP_PROCESS_ID());
			pstmt.setString(7, SYS_STAGE_REPORT_SETTING.getMODEL_NAME());
			return pstmt.executeUpdate() != -1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}

	@Override
	public boolean reprot_setting_emp(String stage_report_setting_id, String emp_no){
		try {
			db = new DBManager();
			connection = db.GetOraConnection();
			connection.createStatement().executeUpdate("delete from sj.g_report_process_emp where stage_report_setting_id = '"+stage_report_setting_id+"'");
			if(emp_no.length() !=0 ){
				String sql = "insert into sj.g_report_process_emp values (?,?)";
				pstmt = connection.prepareStatement(sql);
				String[] emp_nos = emp_no.split(";");
				for(String emp : emp_nos){
					pstmt.setString(1, stage_report_setting_id);
					pstmt.setString(2, emp.split(",")[0]);
					pstmt.addBatch();
				}
				pstmt.executeBatch();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}
	
	@Override
	public List<Map<String, Object>> query4hReportSetting(String erp_process_id, String emp_no) {
		db = new DBManager();
		connection = db.GetOraConnection();
		String sql ="select * from sj.sys_stage_report_setting a"+
				" inner join sj.g_report_process_emp b on a.stage_report_setting_id = b.stage_report_setting_id and b.emp_no = '"+emp_no+"'"+
				" order by a.model_name";
		List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
		try {
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("STAGE_REPORT_SETTING_ID", rs.getString("STAGE_REPORT_SETTING_ID"));
				map.put("ERP_PROCESS_ID", rs.getString("ERP_PROCESS_ID"));
				map.put("PROCESS", rs.getString("PROCESS"));
				map.put("DEPT_NAME", rs.getString("DEPT_NAME"));
				map.put("MODEL_NAME", rs.getString("MODEL_NAME"));
				listMaps.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return listMaps;
	}

	@Override
	public List<Map<String, Object>> queryReprotSetting(String settingid) {
		String sql ="select a.*,b.process_name,c.model_name from sj.sys_stage_report_setting a"+
				" left join sajet.sys_process b on a.process_id = b.process_id"+
				" left join sajet.sys_part c on a.part_no = c.part_no";
		if(null != settingid)
			sql += " where a.stage_report_setting_id = '"+settingid+"'";
		sql += " order by a.model_name";
		db = new DBManager();
		connection = db.GetOraConnection();
		List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
		try {
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("STAGE_REPORT_SETTING_ID", rs.getString("STAGE_REPORT_SETTING_ID"));
				map.put("PART_NO", rs.getString("PART_NO"));
				map.put("PROCESS_ID", rs.getString("PROCESS_ID"));
				map.put("PROCESS", rs.getString("PROCESS"));
				map.put("INCOMING", rs.getString("INCOMING"));
				map.put("CREATE_TIME", rs.getString("CREATE_TIME"));
				map.put("DEPT_NAME", rs.getString("DEPT_NAME"));
				map.put("ERP_PROCESS_ID", rs.getString("ERP_PROCESS_ID"));
				map.put("PROCESS_NAME", rs.getString("PROCESS_NAME"));
				map.put("MODEL_NAME", rs.getString("MODEL_NAME"));
				
				listMaps.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return listMaps;
	}

	@Override
	public String getDeptName(String emp_no) {
		db = new DBManager();
		connection = db.GetOraConnection();
		String sql = "select dept_name from sajet.sys_hr_emp where emp_no = '"+emp_no+"'";
		String deptName = "";
		try {
			rs = connection.createStatement().executeQuery(sql);
			if(rs.next()){
				deptName = rs.getString("dept_name");
			}
			deptName = (deptName.substring(deptName.lastIndexOf(" ")));
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return deptName;
	}

	@Override
	public boolean updatePlanSetting(SYS_STAGE_REPORT_SETTING SYS_STAGE_REPORT_SETTING) {
		String sql = "update sj.sys_stage_report_setting set process = ?,erp_process_id = ?,model_name = ? where stage_report_setting_id = ?";
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, SYS_STAGE_REPORT_SETTING.getPROCESS());
			pstmt.setString(2, SYS_STAGE_REPORT_SETTING.getERP_PROCESS_ID());
			pstmt.setString(3, SYS_STAGE_REPORT_SETTING.getMODEL_NAME());
			pstmt.setString(4, SYS_STAGE_REPORT_SETTING.getSTAGE_REPORT_SETTING_ID());
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
	public String queryReportSettingEmp(String stage_report_setting_id) {
		String sql = "select * from sj.g_report_process_emp a inner join sajet.sys_hr_emp b on a.emp_no = b.emp_no where a.stage_report_setting_id = ?";
		db = new DBManager();
		connection = db.GetOraConnection();
		String emp_no = "";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, stage_report_setting_id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				emp_no += rs.getString("emp_no")+","+rs.getString("emp_name") +";";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return emp_no;
	}

	@Override
	public boolean deletePlanSetting(String stage_report_setting_id) {
		String sql = "delete from sj.sys_stage_report_setting where stage_report_setting_id = '"+stage_report_setting_id+"'";
		String sqlDelProcessEmp = "delete from sj.g_report_process_emp where stage_report_setting_id = '"+stage_report_setting_id+"'";
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			Statement stmt = connection.createStatement();
			stmt.addBatch(sql);
			stmt.addBatch(sqlDelProcessEmp);
			stmt.executeBatch();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}

	@Override
	public List<Map<String,Object>> queryModelName(String column, String table) {
		DAOUtils utils = new DAOUtils();
		List<Map<String,Object>> list = utils.queryColumnName(column, table, true);
		return list;
	}

	@Override
	public List<Map<String,Object>> queryERPProcessID(String column, String table, String c, String v) {
		DAOUtils utils = new DAOUtils();
		return utils.queryColumnName(column, table, c, v);
	}

	@Override
	public List<Map<String, Object>> queryReport(String model_name, String datetime1, String datetime2) {
		String sql ="select b.erp_process_id,"+
				       " b.stage_report_setting_id,"+
				       " a.time_zone,"+
				       " (case a.time_zone when '08:00-12:00' then 1 when '12:00-16:00' then 2 when '16:00-20:00' then 3 when '20:00-24:00' then 4 when '00:00-04:00' then 5 when '04:00-08:00' then 6 end) time_index,"+
				       " b.process,"+
				       " b.model_name,"+
				       " sum(a.input_cnt) input_cnt_total,"+
				       " sum(a.output_cnt) output_cnt_total,"+
				       " sum(a.input_ng_cnt) input_ng_cnt_toal,"+
				       " sum(a.input_scrapt_cnt) input_scrapt_cnt_total,"+
				       " sum(a.process_ng_cnt) process_ng_cnt_total,"+
				       " sum(a.process_scrapt_cnt) process_scrapt_cnt_total"+
				  " from sj.g_report_record a"+
				 " inner join sj.sys_stage_report_setting b on a.stage_report_setting_id = b.stage_report_setting_id and b.stage_report_setting_id = '"+model_name+"'"+
				 " where a.update_date > to_date('"+datetime1+"','yyyy-mm-dd hh24')"+
				   " and a.update_date <= to_date('"+datetime2+"','yyyy-mm-dd hh24')"+
				 " group by b.erp_process_id,b.stage_report_setting_id,a.time_zone, b.erp_process_id,b.process,b.model_name order by b.erp_process_id,time_index";
		System.out.println("queryReport="+sql);
		List<Map<String, Object>> listTotal = new ArrayList<Map<String,Object>>();
		try {
			db = new DBManager();
			connection = db.GetOraConnection();
			rs = connection.prepareStatement(sql).executeQuery();
			while(rs.next()){
				Map<String,Object> map = new HashMap<String,Object>();
				
				int outputCNT = rs.getInt("output_cnt_total");
				int processScriptCnt = rs.getInt("input_scrapt_cnt_total");
				int dayOutputTotal = outputCNT + processScriptCnt;
				
				map.put("ERP_PROCESS_ID", rs.getString("ERP_PROCESS_ID"));
//				map.put("STAGE_REPORT_SETTING_ID", rs.getString("STAGE_REPORT_SETTING_ID"));
				map.put("TIME_ZONE", rs.getString("TIME_ZONE"));
//				map.put("TIME_TYPE", rs.getInt("TIME_TYPE"));
				map.put("MODEL_NAME", rs.getString("model_name"));
				map.put("INPUT_CNT", rs.getInt("input_cnt_total"));
				map.put("OUTPUT_CNT", outputCNT);
				map.put("INPUT_NG_CNT", rs.getInt("input_ng_cnt_toal"));
				map.put("PROCESS_NG_CNT", rs.getInt("process_ng_cnt_total"));
				map.put("INPUT_SCRAPT_CNT", processScriptCnt);
				map.put("PROCESS_SCRAPT_CNT", rs.getInt("process_scrapt_cnt_total"));
//				map.put("EMP_NO", rs.getString("EMP_NO"));
				map.put("TIME_INDEX", rs.getString("TIME_INDEX"));
				map.put("PROCESS", rs.getString("PROCESS"));
				map.put("DAY_OUTPUT_CNT", dayOutputTotal);
				System.out.println("dayOutputTotal="+dayOutputTotal);
				listTotal.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		
		return listTotal;
	}

	public List<Map<String,Object>> queryReportBySetId(String modeName, String erpProcessId, String datetime1, String datetime2){
		db = new DBManager();
		connection = db.GetOraConnection();
		String sqlSetids = "select distinct (b.stage_report_setting_id)"+
					  " from sj.g_report_record a"+
					 " inner join sj.sys_stage_report_setting b on b.stage_report_setting_id = a.stage_report_setting_id and b.model_name = '"+modeName+"'";
		if(!erpProcessId.equals("0"))
			sqlSetids += " and b.erp_process_id='"+erpProcessId+"'";
		sqlSetids += " where a.update_date >= to_date('"+datetime1+"', 'yyyy-mm-dd')"+
					 " and a.update_date <= to_date('"+datetime2+"', 'yyyy-mm-dd')";
		String sql ="select (case a.time_zone when '08:00-12:00' then 1 when '12:00-16:00' then 2 when '16:00-20:00' then 3 when '20:00-24:00' then 4 when '00:00-04:00' then 5 when '04:00-08:00' then 6 end) time_index,a.*,b.erp_process_id,b.process,b.model_name "+
					"from sj.g_report_record a "+
					"inner join sj.sys_stage_report_setting b on a.stage_report_setting_id = b.stage_report_setting_id "+
					      "and b.stage_report_setting_id in ("+sqlSetids+") "+
					"where a.time_type = 4 "+
					      "and a.update_date >= to_date('"+datetime1+"','yyyy-mm-dd') "+
					      "and a.update_date <= to_date('"+datetime2+"','yyyy-mm-dd') "+
					      "order by a.stage_report_setting_id,a.update_date,time_index";
//		System.out.println("sql="+sql);
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		try {
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				int outputCNT = rs.getInt("OUTPUT_CNT");
				int processScriptCnt = rs.getInt("PROCESS_SCRAPT_CNT");
				int dayOutputTotal = outputCNT + processScriptCnt;
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("REPORT_RECORD_ID", rs.getString("REPORT_RECORD_ID"));
				map.put("STAGE_REPORT_SETTING_ID", rs.getString("STAGE_REPORT_SETTING_ID"));
				map.put("TIME_ZONE", rs.getString("TIME_ZONE"));
				map.put("TIME_TYPE", rs.getInt("TIME_TYPE"));
				map.put("TARGET_DAY", rs.getInt("TARGET_DAY"));
				map.put("INPUT_CNT", rs.getInt("INPUT_CNT"));
				map.put("OUTPUT_CNT", outputCNT);
				map.put("INPUT_NG_CNT", rs.getInt("INPUT_NG_CNT"));
				map.put("PROCESS_NG_CNT", rs.getInt("PROCESS_NG_CNT"));
				map.put("INPUT_SCRAPT_CNT", rs.getInt("INPUT_SCRAPT_CNT"));
				map.put("PROCESS_SCRAPT_CNT", processScriptCnt);
				map.put("EMP_NO", rs.getString("EMP_NO"));
				map.put("TIME_INDEX", rs.getInt("TIME_INDEX"));
				map.put("DAY_OUTPUT_CNT", dayOutputTotal);
				map.put("UPDATE_DATE", rs.getDate("UPDATE_DATE"));
				map.put("ERP_PROCESS_ID", rs.getString("ERP_PROCESS_ID"));
				map.put("PROCESS", rs.getString("PROCESS"));
				map.put("MODEL_NAME", rs.getString("model_name"));
				results.add(map);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return results;
	}
}