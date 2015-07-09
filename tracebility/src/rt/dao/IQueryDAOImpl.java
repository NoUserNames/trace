package rt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rt.connection.DBManager;
import rt.pojo.GSnDefect;
import rt.pojo.GSupplierSN;
import rt.pojo.Product;
import rt.pojo.SysDefect;
import rt.pojo.SysEmp;
import rt.pojo.SysGWoBase;
import rt.pojo.SysPart;
import rt.pojo.SysPdline;
import rt.pojo.SysProcess;
import rt.pojo.SysRoute;
import rt.pojo.SysTerminal;
import rt.util.TUtil;

public class IQueryDAOImpl implements IQueryDAO {

	private static Logger log = Logger.getLogger(IQueryDAO.class);
	
	public static void main(String[] args){
//		System.out.print(new IQueryDAOImpl().queryWIP("104537","", "60011317", null, null).size());
		System.out.println(new IQueryDAOImpl().showTables());
	}
	
	private DBManager db ;
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement pstmt;
	private TUtil util;
	
	private static List<Object> DuplicateTerminalName;
	
	@Override
	public List<Product> queryTravel(String queryValue) {
		db = new DBManager();
		String sql ="select t.work_order,line.pdline_name,"+
		" process.process_name,"+
		" to_char(t.out_process_time, 'yyyy-mm-dd hh24:mi:ss') out_time,"+
		" t.current_status,"+
		" t.work_flag,"+
		" emp.emp_name,"+
		" terminal.terminal_name,"+
		" t.customer_sn,"+
		" t.carton_no,"+
		" base.wo_type,"+
		" base.model_name,"+
		" part.part_no,"+
		" sr.route_name,"+
		" supplier.supplier_sn,"+
		" part.model_name,"+
		" t.serial_number,"+
		" t.customer_sn"+
		" from sajet.g_sn_travel t"+
		" inner join sajet.sys_pdline line on t.pdline_id = line.pdline_id"+
		" inner join sajet.sys_process process on t.process_id = process.process_id"+
		" left join sajet.sys_emp emp on t.emp_id = emp.emp_id"+
		" inner join sajet.sys_terminal terminal on t.terminal_id = terminal.terminal_id"+
		" inner join sajet.sys_route sr on t.route_id = sr.route_id"+
		" inner join sajet.g_wo_base base on t.work_order = base.work_order"+
		" inner join sajet.sys_part part on t.model_id = part.part_id"+
		" left join sajet.g_Supplier_Sn supplier on t.serial_number = supplier.serial_number"+
		" where t.serial_number = ? or t.serial_number=(select serial_number from sajet.g_sn_status s where s.customer_sn=?)";
		sql += " order by out_process_time";
//		System.out.println("queryTravel="+sql);
		List<Product> listp = new ArrayList<Product>();
		try {
			connection = db.GetOraConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, queryValue);
			pstmt.setString(2, queryValue);
			rs = pstmt.executeQuery();
			Product p;
			SysTerminal sysTerminal;
			SysProcess sysProcess;
			SysPdline sysPdline;
			SysGWoBase sysGWoBase;
			SysPart sysPart;
			SysRoute sysRoute;
			SysEmp sysEmp;
			while(rs.next()){
				p = new Product();
				
				sysPdline = new SysPdline();
				sysPdline.setPDLINE_NAME(rs.getString("pdline_name"));
				p.setSysPdline(sysPdline);
				
				sysProcess = new SysProcess();
				sysProcess.setPROCESS_NAME(rs.getString("process_name"));
				p.setSysProcess(sysProcess);
				
				sysEmp = new SysEmp();
				sysEmp.setEMP_NAME(rs.getString("emp_name"));
				p.setSysEmp(sysEmp);
				
				sysTerminal = new SysTerminal();
				sysTerminal.setTERMINAL_NAME(rs.getString("terminal_name"));
				p.setSysTerminal(sysTerminal);
				
				p.setOUT_PROCESS_TIME(rs.getString("out_time"));
				p.setCURRENT_STATUS(rs.getString("current_status"));
				p.setWORK_FLAG(rs.getString("work_flag"));
				p.setCUSTOMER_SN(rs.getString("customer_sn"));
				p.setCARTON_NO(rs.getString("carton_no"));
				p.setCUSTOMER_SN(rs.getString("customer_sn"));
				p.setSERIAL_NUMBER(rs.getString("serial_number"));
				
				sysGWoBase = new SysGWoBase();
				sysGWoBase.setWO_TYPE(rs.getString("wo_type"));
				sysGWoBase.setMODEL_NAME(rs.getString("model_name"));
				p.setSysGWoBase(sysGWoBase);
				
				sysPart = new SysPart();
				sysPart.setPART_NO(rs.getString("part_no"));
				sysPart.setMODEL_NAME(rs.getString("model_name"));
				p.setSysPart(sysPart);
				
				sysRoute = new SysRoute();
				sysRoute.setROUTE_NAME(rs.getString("route_name"));
				p.setSysRoute(sysRoute);
				
				GSupplierSN gSupplierSN = new GSupplierSN();
				gSupplierSN.setSUPPLIER_SN(rs.getString("supplier_sn"));
				p.setGSupplierSN(gSupplierSN);
				
				p.setWORK_ORDER(rs.getString("work_order"));
				listp.add(p);
			}
		} catch (SQLException e) {
			log.error("查询产品信息时出错："+e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			log.error("非法参数异常："+e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			log.error("发生安全异常："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}		
		return listp;
	}
	
	@Override
	public Map<String,Object> queryStatus(String queryValue){
		String sql ="select t.serial_number,t.work_order,line.pdline_name,"+
				" process.process_name,"+
				" to_char(t.out_process_time, 'yyyy-mm-dd hh24:mi:ss') out_time,"+
				" t.current_status,"+
				" t.work_flag,"+
				" emp.emp_name,"+
				" terminal.terminal_name,"+
				" t.customer_sn,"+
				" t.carton_no,"+
				" base.wo_type,"+
				" base.model_name,"+
				" part.part_no,"+
				" sr.route_name,"+
				" supplier.supplier_sn,"+
				" part.model_name,"+
				" t.customer_sn"+
				" from sajet.g_sn_status t"+
				" inner join sajet.sys_pdline line on t.pdline_id = line.pdline_id"+
				" inner join sajet.sys_process process on t.process_id = process.process_id"+
				" left join sajet.sys_emp emp on t.emp_id = emp.emp_id"+
				" inner join sajet.sys_terminal terminal on t.terminal_id = terminal.terminal_id"+
				" inner join sajet.sys_route sr on t.route_id = sr.route_id"+
				" inner join sajet.g_wo_base base on t.work_order = base.work_order"+
				" inner join sajet.sys_part part on t.model_id = part.part_id"+
				" left join sajet.g_Supplier_Sn supplier on t.serial_number = supplier.serial_number"+
				" where t.serial_number = ? or t.customer_sn=?";
		Map<String,Object> map = new HashMap<String,Object>();
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, queryValue);
			pstmt.setString(2, queryValue);
			rs = pstmt.executeQuery();
			while(rs.next()){
				map.put("serial_number", rs.getString("serial_number"));
				map.put("work_order", rs.getString("work_order"));
				map.put("pdline_name", rs.getString("pdline_name"));
				map.put("process_name", rs.getString("process_name"));
				map.put("out_time", rs.getString("out_time"));
				map.put("current_status", rs.getString("current_status"));
				map.put("work_flag", rs.getString("work_flag"));
				map.put("emp_name", rs.getString("emp_name"));
				map.put("terminal_name", rs.getString("terminal_name"));
				map.put("customer_sn", rs.getString("customer_sn"));
				map.put("carton_no", rs.getString("carton_no"));
				map.put("wo_type", rs.getString("wo_type"));
				map.put("model_name", rs.getString("model_name"));
				map.put("part_no", rs.getString("part_no"));
				map.put("route_name", rs.getString("route_name"));
				map.put("supplier_sn", rs.getString("supplier_sn"));
				map.put("model_name", rs.getString("model_name"));
				map.put("customer_sn", rs.getString("customer_sn"));
			}
		} catch (SQLException e) {
			log.error("查产品当前状态异常："+e.getMessage());
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return map;
	}
	
	@Override
	public List<GSnDefect> fixData(String queryValuel) {
		String sql="select p.process_name,d.rec_time,sd.defect_code,sd.defect_desc,emp.emp_name,terminal.terminal_name"+
		 " from sajet.g_sn_defect d"+
		 " inner join sajet.sys_process p on d.process_id = p.process_id"+
		 " inner join sajet.sys_defect sd on d.defect_id = sd.defect_id"+
		 " inner join sajet.sys_emp emp on d.test_emp_id = emp.emp_id"+
		 " inner join sajet.sys_terminal terminal on d.terminal_id = terminal.terminal_id"+
		 " where d.serial_number=? or d.serial_number=(select serial_number from sajet.g_sn_status s where s.customer_sn=?)";
		db = new DBManager();
		List<GSnDefect> list = new ArrayList<GSnDefect>();
		try {
			connection = db.GetOraConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, queryValuel);
			pstmt.setString(2, queryValuel);
			rs = pstmt.executeQuery();
			GSnDefect g_defect;
			SysDefect sysDefect;
			SysTerminal sysTerminal;
			SysProcess sysProcess;
			SysEmp sysEmp;
			while(rs.next()){
				g_defect = new GSnDefect();
				
				sysProcess = new SysProcess();
				sysProcess.setPROCESS_NAME(rs.getString("process_name"));
				g_defect.setSysProcess(sysProcess);
				
				sysEmp = new SysEmp();
				sysEmp.setEMP_NAME(rs.getString("emp_name"));
				g_defect.setSysEmp(sysEmp);
				
				sysDefect = new SysDefect();
				sysDefect.setDEFECT_CODE(rs.getString("defect_code"));
				sysDefect.setDEFECT_DESC(rs.getString("defect_desc"));
				g_defect.setSysDefect(sysDefect);
				
				sysTerminal = new SysTerminal();
				sysTerminal.setTERMINAL_NAME(rs.getString("terminal_name"));
				g_defect.setSysTerminal(sysTerminal);
				
				g_defect.setREC_TIME(rs.getString("rec_time"));
				
				list.add(g_defect);
			}
		} catch (SQLException e) {
			log.error("查询维修记录时出错："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return list;
	}

	@Override
	public List<LinkedHashMap<String, Object>> queryByCarton(String carton, String flag, String terminalName) {
		StringBuffer buffer = new StringBuffer("select a.work_order,a.serial_number,b.model_name,a.terminal_id,a.carton_no,a.rc_no,a.customer_sn,to_char(a.out_process_time,'yyyy-mm-dd hh24:mi:ss') out_process_time,t.terminal_name");
		if("1".equals(flag)){
			buffer.append(" from sajet.g_sn_status a ");
		}else{
			buffer.append(" from sajet.g_sn_travel a ");
		}
		buffer.append("inner join sajet.sys_part b on a.model_id = b.part_id inner join sajet.sys_terminal t on a.terminal_id = t.terminal_id where b.enabled = 'Y' and a.carton_no = ?");
		if(terminalName.length() !=0){
			buffer.append("and t.terminal_name=?");
		}
		buffer.append(" order by a.serial_number");
		
		db = new DBManager();
		List<LinkedHashMap<String, Object>> listP = null ;
		try {
			connection = db.GetOraConnection();
			pstmt = connection.prepareStatement(buffer.toString());
			pstmt.setString(1, carton);
			if(terminalName.length() !=0){
				pstmt.setString(2, terminalName);
			}
			rs = pstmt.executeQuery();

			listP = new ArrayList<LinkedHashMap<String, Object>>();
			DuplicateTerminalName = new ArrayList<Object>();
			while(rs.next()){
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("work_order", rs.getString("work_order"));
				map.put("serial_number", rs.getString("serial_number"));
				map.put("model_name", rs.getString("model_name"));
				map.put("terminal_name", rs.getString("terminal_name"));
				map.put("carton_no", rs.getString("carton_no"));
				map.put("rc_no", rs.getString("rc_no"));
				map.put("customer_sn", rs.getString("customer_sn"));
				map.put("out_process_time", rs.getString("out_process_time"));

				DuplicateTerminalName.add(rs.getString("terminal_name"));
				
				listP.add(map);
			}
		} catch (SQLException e) {
			log.error("按单个箱号查询时出错："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listP;
	}

	@Override
	public List<LinkedHashMap<String,Object>> queryByCarton(String[] cartonArray) {
		String cartons = "";
		for(String str : cartonArray ){
			cartons += (",'"+str+"'");
		}
		cartons = cartons.substring(1, cartons.length());
		String sql = "select a.work_order,"+
       " a.serial_number,"+
       " b.model_name,"+
       " a.terminal_id,"+
       " a.carton_no,"+
       " a.rc_no,"+
       " a.customer_sn,"+
       " to_char(a.out_process_time, 'yyyy-mm-dd hh24:mi:ss') out_process_time,"+
       " t.terminal_name"+
       " from sajet.g_sn_status a"+
       " inner join sajet.sys_part b on a.model_id = b.part_id"+
       " inner join sajet.sys_terminal t on a.terminal_id = t.terminal_id"+
       " where b.enabled = 'Y'"+
       " and a.carton_no in ("+cartons+")"+
       " order by a.carton_no";
		List<LinkedHashMap<String,Object>> listP = null ;
		db = new DBManager();
		try {
			connection = db.GetOraConnection();
			pstmt = connection.prepareStatement(sql);
			
//			System.out.println("queryByCarton聚合多个箱号："+cartons);

			rs = pstmt.executeQuery();

			listP = new ArrayList<LinkedHashMap<String,Object>>();
			while(rs.next()){
				LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();

				map.put("work_order", rs.getString("work_order"));
				map.put("serial_number", rs.getString("serial_number"));
				map.put("carton_no", rs.getString("carton_no"));
				map.put("rc_no", rs.getString("rc_no"));
				map.put("customer_sn", rs.getString("customer_sn"));
				map.put("out_process_time", rs.getString("out_process_time"));
				map.put("terminal_name", rs.getString("terminal_name"));
				map.put("model_name", rs.getString("model_name"));
				
				listP.add(map);
			}
		} catch (SQLException e) {
			log.error("按多个箱号查询时出错："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listP;
	}
	
	public List<Object> DuplicateTerminalNames(){
		util = new TUtil();
//		System.out.println("DuplicateTerminalName="+util.removeDuplicate(DuplicateTerminalName));
		return util.removeDuplicate(DuplicateTerminalName);
	}

	@Override
	public List<LinkedHashMap<String,Object>> queryDefectByProcess(String processID, String terminalID, String timeB, String timeE) {
//		System.out.println("queryDefectByProcess dao processID="+processID+"\tterminalID"+terminalID+"\ttimeB"+timeB+"\ttimeE"+timeE);
//		log.info("queryDefectByProcess dao processID="+processID+"\tterminalID"+terminalID+"\ttimeB"+timeB+"\ttimeE"+timeE);
		db = new DBManager();
		StringBuffer sql =new StringBuffer("select gsd.serial_number,"+
	       " defect.defect_desc,"+
	       " defect.defect_desc2,"+
	       " gsd.rec_time,"+
	       " (case gsd.work_flag when '1' then '报废' else '不良' end) work_flag,"+
	       " emp.emp_name,gsd.responsibility_unit"+
	       " from sajet.g_sn_defect gsd"+
	       " inner join sajet.sys_defect defect on gsd.defect_id = defect.defect_id"+
	       " inner join sajet.sys_process process on gsd.process_id = process.process_id and process.process_id = ?"+
	       " inner join sajet.sys_emp emp on gsd.test_emp_id = emp.emp_id");
			
	       if(!terminalID.equals("0")){
	    	   sql.append(" inner join sajet.sys_terminal terminal on gsd.terminal_id = terminal.terminal_id and terminal.terminal_id = ?");
	       }
	       sql.append(" where gsd.rec_time >= to_date(?,'yyyy-mm-dd hh24:mi')"+
	       " and gsd.rec_time <= to_date(?,'yyyy-mm-dd hh24:mi')");
		List<LinkedHashMap<String,Object>> listMaps = new ArrayList<LinkedHashMap<String,Object>>();
		try {
			connection = db.GetOraConnection();
			pstmt = connection.prepareStatement(sql.toString());

			pstmt.setString(1, processID);
			if(!terminalID.equals("0")){
				pstmt.setString(2, terminalID);
				pstmt.setString(3, timeB);
				pstmt.setString(4, timeE);
			}else{
				pstmt.setString(2, timeB);
				pstmt.setString(3, timeE);
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				Map<String,Object> map = new LinkedHashMap<String,Object>();
				map.put("serial_number", rs.getString("serial_number"));
				map.put("defect_desc", rs.getString("defect_desc"));
				map.put("defect_desc2", rs.getString("defect_desc2"));
				map.put("rec_time", rs.getString("rec_time"));
				map.put("work_flag", rs.getString("work_flag"));
				map.put("emp_name", rs.getString("emp_name"));
				map.put("responsibility_unit", rs.getString("responsibility_unit"));
				listMaps.add((LinkedHashMap<String, Object>) map);
			}
		} catch (SQLException e) {
			log.error("查询不良时出错："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listMaps;
	}
	
	public List<Map<String,Object>> queryPart(String modelName) {
		db = new DBManager();
		String sql ="select part_id,model_name,part_no,route_id from sajet.sys_part where upper(model_name) like upper(?) and enabled ='Y'  ORDER BY MODEL_NAME";
		List<Map<String,Object>> listPart = new ArrayList<Map<String,Object>>();
		try {
			connection = db.GetOraConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, modelName+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("part_id", rs.getLong("part_id"));
				map.put("model_name", rs.getString("model_name"));
				map.put("route_id", rs.getInt("route_id"));
				map.put("part_no", rs.getString("part_no"));
				listPart.add(map);
			}
		} catch (SQLException e) {
			log.error("查询机种时出错："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listPart;
	}

	@Override
	public List<Map<String,Object>> queryProcessByRoute(String routeId) {
		db = new DBManager();
//		String sql ="select process_id,process_name from sajet.sys_process where process_id in(select next_process_id from sajet.sys_route_detail where route_id = ?)";
//		String sql ="select process_id,process_name from sajet.sys_process p inner join "+
//				"(select rt.next_process_id,rt.seq from sajet.sys_route_detail rt where rt.route_id = ?) s on  p.process_id = s.next_process_id where p.enabled ='Y' order by s.seq";
		String sql = "select b.process_id, b.process_name, a.step"+
          " from sajet.sys_route_detail a"+
         " inner join sajet.sys_process b on a.next_process_id = b.process_id"+
         " where a.route_id = (select route_id from sajet.sys_part where part_no = ? and rownum = 1)"+
         " order by a.step";
		List<Map<String,Object>> listProcess = new ArrayList<Map<String,Object>>();
		try {
			connection = db.GetOraConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, routeId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("process_id", rs.getInt("process_id"));
				map.put("process_name", rs.getString("process_name"));
				listProcess.add(map);
			}
		} catch (SQLException e) {
			log.error("查询制程时出错："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listProcess;
	}

	@Override
	public List<Map<String, Object>> queryTerminalByProcess(String processId) {
		db = new DBManager();
		String sql ="select t.terminal_id,t.terminal_name from sajet.sys_terminal t where process_id=? and enabled='Y' order by t.terminal_name";
		List<Map<String, Object>> listTerminalMaps = new ArrayList<Map<String, Object>>(); 
		try {
			connection = db.GetOraConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, processId);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("terminal_id", rs.getInt("terminal_id"));
				map.put("terminal_name", rs.getString("terminal_name"));
				listTerminalMaps.add(map);
			}
		} catch (SQLException e) {
			log.error("查询不良沾点时出错："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listTerminalMaps;
	}

	@Override
	public List<LinkedHashMap<String, Object>> queryDefectBySN(String SN) {
		String sql="select p.process_name,d.rec_time,sd.defect_code,sd.defect_desc,sd.defect_desc2,emp.emp_name,terminal.terminal_name,d.responsibility_unit"+
		 " from sajet.g_sn_defect d"+
		 " inner join sajet.sys_process p on d.process_id = p.process_id"+
		 " inner join sajet.sys_defect sd on d.defect_id = sd.defect_id"+
		 " inner join sajet.sys_emp emp on d.test_emp_id = emp.emp_id"+
		 " inner join sajet.sys_terminal terminal on d.terminal_id = terminal.terminal_id"+
		 " where d.serial_number=?";
		db = new DBManager();
		List<LinkedHashMap<String, Object>> listDefectsMap = new ArrayList<LinkedHashMap<String, Object>>();
		try {
			connection = db.GetOraConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, SN);
			rs = pstmt.executeQuery();
			while(rs.next()){
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("process_name", rs.getString("process_name"));
				map.put("terminal_name", rs.getString("terminal_name"));
				map.put("rec_time", rs.getString("rec_time"));
				map.put("defect_desc", rs.getString("defect_desc"));
				map.put("defect_desc2", rs.getString("defect_desc2"));
				map.put("emp_name", rs.getString("emp_name"));
				map.put("responsibility_unit", rs.getString("responsibility_unit"));
				
				listDefectsMap.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listDefectsMap;
	}

	public List<LinkedHashMap<String,Object>> queryWIP(String partNO, String timeB, String timeE){
		String sql = "select c.process_name,c.process_id,"+
					       "count(*) WipCount,"+
					       "nvl(sum(d.current_status), 0) as NGCount,"+
					       "nvl(sum(d.work_flag), 0) ScrapCount"+
					  " from (select b.process_id, b.process_name, a.step"+
					         " from sajet.sys_route_detail a"+
					         " inner join sajet.sys_process b on a.next_process_id = b.process_id"+
					         " where a.route_id = (select route_id from sajet.sys_part where part_no = ? and rownum = 1)"+
					         " order by a.step) c"+
					 " inner join sajet.g_sn_status d on d.process_id = c.process_id and d.enabled is null"+
					 " inner join sajet.g_wo_base e on e.work_order = d.work_order"+
					 " inner join sajet.sys_part p on p.part_id = d.model_id and part_no = ?"+
					 " where d.out_process_time >="+
					       " to_date(?, 'YYYY-MM-DD HH24:mi')"+
					   " and d.out_process_time <="+
					       " to_date(?, 'YYYY-MM-DD HH24:mi')"+
					   " and e.wo_type <> 'DOE'"+
					 " group by c.process_name,c.process_id, c.step"+
					 " order by c.step";
//		TUtil.print("拼SQL开始："+TUtil.format("HH:mm:ss"));
//		String sql ="select pro.process_name,pro.process_id,count(1) WipCount,"+
//               " nvl(sum(s.current_status), 0) NGCount,"+
//               " nvl(sum(s.work_flag), 0) ScrapCount"+
//               " from sajet.g_sn_status s, sajet.g_wo_base c,sajet.sys_part p,sajet.sys_process pro"+
//               " where s.work_order = c.work_order"+
//               " and s.process_id = pro.process_id"+
//               " and s.enabled is null"+
//               " and c.wo_type <> 'DOE'"+
//               " and s.model_id = p.part_id"+
//               " and p.part_no=?"+
//               " and s.out_process_time >="+
//               " to_date(?, 'YYYY-MM-DD HH24:mi')"+
//               " and s.out_process_time <="+
//               " to_date(?, 'YYYY-MM-DD HH24:mi')"+
//               " group by pro.process_name,pro.process_id";
//		System.out.println("queryWIP 只有料号sql="+sql);
		db = new DBManager();
		List<LinkedHashMap<String, Object>> listWIP = new ArrayList<LinkedHashMap<String, Object>>();
		connection = db.GetOraConnection();
		try {
			pstmt = connection.prepareStatement(sql.toString());
			pstmt.setString(1, partNO);
			pstmt.setString(2, partNO);
			pstmt.setString(3, timeB);
			pstmt.setString(4, timeE);
//			TUtil.print("拼SQL结束："+TUtil.format("HH:mm:ss"));
			rs = pstmt.executeQuery();
//			TUtil.print("执行SQL结束："+TUtil.format("HH:mm:ss"));
			while(rs.next()){
				LinkedHashMap<String, Object> wipMap = new LinkedHashMap<String, Object>();

				wipMap.put("lableName", rs.getString("process_name"));
				wipMap.put("p_id", rs.getString("process_id"));
				String ng = rs.getString("NGCount");
				
				String cnt = rs.getString("WipCount");
				String scrapt = rs.getString("ScrapCount");
				int ng_t = 0;
				int scrapt_t = 0;
				if (Integer.parseInt(ng) >= Integer.parseInt(scrapt)){//不良品多 不良数=不良-报废
					ng_t = Integer.parseInt(ng) - Integer.parseInt(scrapt);
					wipMap.put("ingcount", ng_t);
				}else{
					wipMap.put("ingcount", ng);
				}
				if (Integer.parseInt(scrapt) > Integer.parseInt(ng)){//不良品多 报废数 = 不良 - 报废
					scrapt_t = Integer.parseInt(scrapt) - Integer.parseInt(ng);
					wipMap.put("iscarpcount", scrapt_t);
				}else{
					wipMap.put("iscarpcount", scrapt);
				}
				wipMap.put("iwipcount", cnt);
				//不良率
				Double ngrate = new Double(Double.parseDouble(wipMap.get("ingcount").toString()) / Double.parseDouble(cnt)*100);
				ngrate = !Double.isNaN(ngrate) ? ngrate : 0;
				wipMap.put("ngrate",ngrate);
				//报废率
//				wipMap.put("iscarpcount", scrapt);
				Double srate = new Double(Double.parseDouble(wipMap.get("iscarpcount").toString()) / Double.parseDouble(cnt)*100);
				srate = !Double.isNaN(srate) ? srate : 0;
				wipMap.put("srate",srate);
				listWIP.add(wipMap);
			}
		} catch (SQLException e) {
			log.error("按料号查询WIP异常："+e.getMessage());
			e.printStackTrace();
			return null;
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listWIP;
	}
	
	@Override
	public List<LinkedHashMap<String, Object>> queryWIP(String process, String partNO, String terminal, String timeB, String timeE) {
//		System.out.println("queryWIP ="+process+"\t"+partNO+"\t"+terminal+"\t"+timeB+"\t"+timeE);
//		log.info("queryWIP ="+process+"\t"+partNO+"\t"+terminal+"\t"+timeB+"\t"+timeE);
		StringBuffer sql = new StringBuffer();
		sql.append("select count(iwipcount) - count(decode(iwipcount, 0, 0)) iwipcount, count(ingcount) - count(decode(ingcount, 0, 0)) ingcount,count(iscarpcount) - count(decode(iscarpcount, 0, 0)) iscarpcount"+
				" from (select count(1) iwipcount, nvl(sum(s.current_status), 0) ingcount, nvl(sum(s.work_flag), 0) iscarpcount"+
				" from sajet.g_sn_status s, sajet.g_wo_base c,sajet.sys_part p"+
				" where s.work_order = c.work_order and s.enabled is null and c.wo_type <> 'DOE'"+
				" and s.model_id = p.part_id and p.part_no=? and s.process_id =  ? and p.enabled = 'Y'");
		if(!terminal.equals("0")){
			sql.append(" and s.terminal_id = ?");
		}
		sql.append(" and s.out_process_time >= to_date(?, 'YYYY-MM-DD HH24:mi') and s.out_process_time <= to_date(?, 'YYYY-MM-DD HH24:mi') group by s.serial_number)");
		
//		System.out.println("queryWIP sql="+sql);
//		log.info("queryWIP sql="+sql);
		db = new DBManager();
		List<LinkedHashMap<String, Object>> listWIP = new ArrayList<LinkedHashMap<String, Object>>();
		try {
			connection = db.GetOraConnection();
			pstmt = connection.prepareStatement(sql.toString());
			pstmt.setString(1, partNO);
			pstmt.setString(2, process);
			if(!terminal.equals("0")){
				pstmt.setString(3, terminal);
				pstmt.setString(4, timeB);
				pstmt.setString(5, timeE);
			}else{
				pstmt.setString(3, timeB);
				pstmt.setString(4, timeE);
			}
			rs = pstmt.executeQuery();

			while(rs.next()){
				LinkedHashMap<String, Object> wipMap = new LinkedHashMap<String, Object>();
				if(terminal.equals("0")){
					wipMap.put("lableName", queryProcess(process).getPROCESS_NAME());
					wipMap.put("p_id", process);
				}else{
					wipMap.put("lableName", queryTerminal(terminal).getTERMINAL_NAME());
					wipMap.put("p_id", process);
					wipMap.put("t_id", terminal);
				}
				String ng = rs.getString("ingcount");
				String cnt = rs.getString("iwipcount");
				String scrapt = rs.getString("iscarpcount");
				int ng_t = 0;
				int scrapt_t = 0;
				if (Integer.parseInt(ng) >= Integer.parseInt(scrapt)){//不良品多 不良数=不良-报废
					ng_t = Integer.parseInt(ng) - Integer.parseInt(scrapt);
					wipMap.put("ingcount", ng_t);
				}else{
					wipMap.put("ingcount", ng);
				}
				if (Integer.parseInt(scrapt) >= Integer.parseInt(ng)){//不良品多 报废数 = 不良 - 报废
					scrapt_t = Integer.parseInt(scrapt) - Integer.parseInt(ng);
					wipMap.put("iscarpcount", scrapt_t);
				}else{
					wipMap.put("iscarpcount", scrapt);
				}
				wipMap.put("iwipcount", cnt);
				//不良率
				Double ngrate = new Double(Double.parseDouble(wipMap.get("ingcount").toString()) / Double.parseDouble(cnt)*100);
				ngrate = !Double.isNaN(ngrate) ? ngrate : 0;
				wipMap.put("ngrate",ngrate);
				//报废率
//				wipMap.put("iscarpcount", scrapt);
				Double srate = new Double(Double.parseDouble(wipMap.get("iscarpcount").toString()) / Double.parseDouble(cnt)*100);
				srate = !Double.isNaN(srate) ? srate : 0;
				wipMap.put("srate",srate);

				listWIP.add(wipMap);
			}
		} catch (SQLException e) {
			log.error("wip查询出错:"+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listWIP;
	}

	@Override
	public SysProcess queryProcess(String processid) {
		db = new DBManager();
		String sql ="select * from sajet.sys_process where process_id=? and enabled ='Y'";
		SysProcess process = new SysProcess();
		ResultSet rs = null ;
		Connection connection = db.GetOraConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, processid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				process.setPROCESS_NAME(rs.getString("process_name"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return process;
	}

	@Override
	public SysTerminal queryTerminal(String terminalid) {
		db = new DBManager();
		String sql ="select * from sajet.sys_terminal where terminal_id=? and enabled ='Y'";
		SysTerminal terminal = new SysTerminal();
		ResultSet rs = null ;
		Connection connection = db.GetOraConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, terminalid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				terminal.setTERMINAL_NAME(rs.getString("terminal_name"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return terminal;
	}

	@Override
	public List<LinkedHashMap<String, Object>> queryWIPDetails(String process,String partNO,String terminal, String timeB, String timeE) {
//		System.out.println("queryWIPDetails ="+process+"\t"+partNO+"\t"+terminal+"\t"+timeB+"\t"+timeE);
//		log.info("queryWIPDetails ="+process+"\t"+partNO+"\t"+terminal+"\t"+timeB+"\t"+timeE);
		StringBuffer sql =new StringBuffer("select s.serial_number,"+
       " t.terminal_name,"+
       " to_char(s.out_process_time,'yyyy-mm-dd hh24:mi:ss') out_process_time,"+
       " s.carton_no,"+
       " s.customer_sn,"+
       " s.current_status,"+
       " s.work_flag,"+
       " emp.emp_name"+
       " from sajet.g_sn_status s"+
       " inner join sajet.sys_part part on s.model_id = part.part_id and part.part_no=?"+
       " inner join sajet.g_wo_base c on s.work_order = c.work_order and c.wo_type <> 'DOE'"+
       " inner join sajet.sys_process p on s.process_id = p.process_id");
		if(!"0".equals(process))
			sql.append(" and s.process_id = ?");
		sql.append(" inner join sajet.sys_terminal t on s.terminal_id = t.terminal_id");
		if(!"0".equals(terminal))
			sql.append(" and s.terminal_id =?");
		sql.append(" left join sajet.sys_emp emp on s.emp_id = emp.emp_id where s.enabled is null"+
       " and s.out_process_time >= to_date(?, 'YYYY-MM-DD HH24:mi')"+
       " and s.out_process_time <= to_date(?, 'YYYY-MM-DD HH24:mi')");
//		System.out.println("queryWIPDetails sql ="+sql.toString());
//		log.info("queryWIPDetails sql ="+sql.toString());
		db = new DBManager();
		connection = db.GetOraConnection();
		List<LinkedHashMap<String, Object>> listWIPDetail = new ArrayList<LinkedHashMap<String, Object>>();
		try {
			pstmt = connection.prepareStatement(sql.toString());
			pstmt.setString(1, partNO);
			if("0".equals(terminal) && "0".equals(process)){
				pstmt.setString(2, timeB);
				pstmt.setString(3, timeE);
			}else{
				if(!"0".equals(terminal)){//按站查
//					System.out.println("按站查");
					pstmt.setString(2, process);
					pstmt.setString(3, terminal);
					pstmt.setString(4, timeB);
					pstmt.setString(5, timeE);
				}else{//按制程查
//					System.out.println("按制程查");
					pstmt.setString(2, process);
					pstmt.setString(3, timeB);
					pstmt.setString(4, timeE);
				}
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				LinkedHashMap<String, Object> linkMap = new LinkedHashMap<String, Object>();
				linkMap.put("serial_number", rs.getString("serial_number"));
				linkMap.put("terminal_name", rs.getString("terminal_name"));
				linkMap.put("out_process_time", rs.getString("out_process_time"));
				linkMap.put("carton_no", rs.getString("carton_no"));
				linkMap.put("customer_sn", rs.getString("customer_sn"));
				String status = rs.getString("current_status");
				String workFlag = rs.getString("work_flag");
				if("0".equals(status) && "0".equals(workFlag)){//良品判定
					linkMap.put("current_status", "正常");
				}else{//报废与不良判定
					if("1".equals(status) && "1".equals(workFlag)){//报废判定
						linkMap.put("current_status", "报废");
					}else{
						if("1".equals(status) && "0".equals(workFlag)){//不良判定
							linkMap.put("current_status", "不良");
						}
					}
				}
				linkMap.put("emp_name", rs.getString("emp_name"));
//				System.out.println("status:"+linkMap.get("current_status"));
				listWIPDetail.add(linkMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listWIPDetail;
	}

	@Override
	public List<Map<String, Object>> queryPart() {		
		return queryPart("");
	}
	
	public List<LinkedHashMap<String,Object>> queryProcessOutput(String part_no,String timeB,String timeE,String processName,String terminalName){
		StringBuffer buffer = new StringBuffer("select e.process_id,e.proname, e.wo_type, e.cnt, e.cnt - e.ng ok, e.ng, e.seq "+
			  "from (select f.process_name proname, f.process_id,"+
			               "f.wo_type, "+
			               "count(f.serial_number) cnt, "+
			               "sum(f.current_status) ng, "+
			               "f.seq "+
			          "from (select b.process_name, b.process_id,"+
			                       "g.wo_type, "+
			                       "a.serial_number, "+
			                       "d.seq, "+
			                       "max(a.current_status) as current_status "+
			                  "From sajet.g_sn_travel      a, "+
			                       "sajet.sys_process      b, "+
			                       "sajet.sys_part         c, "+
			                       "sajet.sys_route_detail d, "+
			                       "sajet.g_wo_base        g "+
			                 "where a.process_id = b.process_id "+
			                   "and c.part_no = ? "+
			                   "and a.model_id = c.part_id "+
			                   "and d.route_id = c.route_id "+
			                   "and a.ENABLED is null "+
			                   "and b.process_id = d.next_process_id "+
			                   "and a.work_order = g.work_order "+
			                   "and a.out_process_time >= "+
			                       "to_date(?, 'yyyy-mm-dd hh24:mi') "+
			                   "and a.out_process_time <= "+
			                       "to_date(?, 'yyyy-mm-dd hh24:mi') ");
		if (!"0".equals(processName))
			buffer.append(" and b.process_id = ? ");
		if (!"0".equals(terminalName))
			buffer.append(" and a.terminal_id = ? ");
		buffer.append("group by a.serial_number, b.process_name, b.process_id,g.wo_type, d.seq order by d.seq) f group by f.process_name,f.process_id, f.wo_type, f.seq) e order by e.seq");
//		System.out.println("queryProcessOutput="+buffer.toString());
		db = new DBManager();
		connection = db.GetOraConnection();
		List<LinkedHashMap<String,Object>> listmap = new ArrayList<LinkedHashMap<String,Object>>();
		try {
			pstmt = connection.prepareStatement(buffer.toString());
			pstmt.setString(1, part_no);
			pstmt.setString(2, timeB);
			pstmt.setString(3, timeE);
			if(!"0".equals(terminalName) && !"0".equals(processName)){//机种、站点都有
//				System.out.println("制程、站点都有");
				pstmt.setString(4, processName);
				pstmt.setString(5, terminalName);
			}else{
				if(!"0".equals(processName)){
//					System.out.println("制程");
					pstmt.setString(4, processName);
				}
				if(!"0".equals(terminalName)){
//					System.out.println("站点");
					pstmt.setString(4, terminalName);
				}
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
				map.put("process_id", rs.getString("process_id"));
				map.put("proname", rs.getString("proname"));
				map.put("wo_type", rs.getString("wo_type"));
				map.put("cnt", rs.getString("cnt"));
				map.put("ok", rs.getString("ok"));
				map.put("ng", rs.getString("ng"));
				map.put("seq", rs.getString("seq"));

				listmap.add(map);
			}
		} catch (SQLException e) {
			log.error("queryProcessOutput Exception:"+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listmap;
	}
	
	@Override
	public List<LinkedHashMap<String,Object>> queryProcessOutputDetail(String part_no,String wo_type, String timeB,String timeE,String processID,String terminalName){	
		StringBuffer buffer = new StringBuffer("select e.*,h.process_name process_name_now,j.terminal_name terminal_name_now,f.out_process_time now_time,"+
		      " (case f.current_status"+
		        " when '0' then 'OK'"+
		        " when '1' then 'NG'"+
		      " end) as current_status"+
		 " from (select b.process_name,g.wo_type,a.serial_number,d.seq,max(a.out_process_time) scanTime"+
		          " From sajet.g_sn_travel      a,"+
		              " sajet.sys_process      b,"+
		              " sajet.sys_part         c,"+
		              " sajet.sys_route_detail d,"+
		              " sajet.g_wo_base        g"+
		        " where a.process_id = b.process_id"+
		          " and c.part_no = ?"+
		          " and a.model_id = c.part_id"+
		          " and d.route_id = c.route_id"+
		          " and a.ENABLED is null"+
		          " and g.wo_type=?"+
		          " and b.process_id = d.next_process_id"+
		          " and a.work_order = g.work_order"+
		          " and b.process_id = ?"+
		          " and a.out_process_time >= to_date(?, 'yyyy-mm-dd hh24:mi')"+
		          " and a.out_process_time <= to_date(?, 'yyyy-mm-dd hh24:mi')");
		          if(!"0".equals(terminalName))
		  			buffer.append(" and a.terminal_id = ?");
		          buffer.append(" group by a.serial_number, b.process_name, g.wo_type, d.seq"+
		        " order by d.seq) e"+
		" inner join sajet.g_sn_status f on f.serial_number = e.serial_number"+
		" inner join sajet.sys_process h on f.process_id = h.process_id"+
		" inner join sajet.sys_terminal j on j.terminal_id = f.terminal_id");
		
//		System.out.println("queryProcessOutputDetail "+buffer.toString());
		db = new DBManager();
		connection = db.GetOraConnection();
		List<LinkedHashMap<String,Object>> listmap = new ArrayList<LinkedHashMap<String,Object>>();
		try {
			pstmt = connection.prepareStatement(buffer.toString());
			pstmt.setString(1, part_no);
			pstmt.setString(2, wo_type);
			pstmt.setString(3, processID);
			pstmt.setString(4, timeB);
			pstmt.setString(5, timeE);
			if(!"0".equals(terminalName))
				pstmt.setString(6, terminalName);
			rs = pstmt.executeQuery();
			while(rs.next()){
				LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
				map.put("wo_type", rs.getString("wo_type"));
				map.put("serial_number", rs.getString("serial_number"));
				map.put("process_name", rs.getString("process_name"));
				map.put("scanTime", TUtil.format(rs.getDate("scanTime"), "yyyy-MM-dd HH:mm:ss"));
				map.put("process_name_now", rs.getString("process_name_now"));
				map.put("terminal_name_now", rs.getString("terminal_name_now"));
				map.put("NowTime", TUtil.format(rs.getDate("now_time"), "yyyy-MM-dd HH:mm:ss"));
				map.put("status", rs.getString("current_status"));
				listmap.add(map);
			}
		} catch (SQLException e) {
			log.error("queryProcessOutputDetail 异常："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listmap;
	}
	
	public List<LinkedHashMap<String,Object>> queryLaserQuality(String routeID, String part_no, String timeB, String timeE){
		String sql = "select w.work_order,w.serial_number,w.qc_result,TO_CHAR(w.OUT_PROCESS_TIME, 'YYYY-MM-DD HH24:Mi:SS') LASRR_TIME"+
                " from sajet.g_sn_status w"+
				" inner join sajet.sys_part part on w.model_id = part.part_id and part.part_no=?"+
				" where w.process_id in"+
				" (SELECT D.PROCESS_ID"+
				" FROM SAJET.SYS_PROCESS D"+
				" inner join sajet.sys_route_detail t on d.process_id = t.next_process_id and t.route_id =?"+
				" WHERE D.PROCESS_NAME LIKE '%Barcode Check%')"+
				" and out_process_time >="+
				" to_date(?, 'yyyy-MM-dd HH24')"+
				" AND out_process_time <"+
				" to_date(?, 'yyyy-MM-dd HH24')";
		db = new DBManager();
		connection = db.GetOraConnection();
		List<LinkedHashMap<String,Object>> listmap = new ArrayList<LinkedHashMap<String,Object>>();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, part_no);
			pstmt.setString(2, routeID);
			pstmt.setString(3, timeB);
			pstmt.setString(4, timeE);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
				map.put("work_order", rs.getString("work_order"));
				map.put("serial_number", rs.getString("serial_number"));
				map.put("qc_result", rs.getString("qc_result"));
				map.put("LASRR_TIME", rs.getString("LASRR_TIME"));
				listmap.add(map);
			}
		} catch (SQLException e) {
			log.error("queryLaserQuality:"+e.getMessage());
			e.printStackTrace();
		} finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listmap;
	}
	
	public List<LinkedHashMap<String,Object>> queryShipping(String queryType, String queryValue){
		StringBuffer buffer = new StringBuffer("select wh.carton_no,wh.serail_number,(case wh.status when '0' then '未出货' when '1' then '已出货' end) shipping_status,to_char(wh.update_time,'yyyy-mm-dd hh24:mi:ss') update_time,wh.batch_no,wh.part_no_mes,wh.customer_sn from sajet.g_sn_wh wh where 1=1");
			if("serial_number".equals(queryType))
				buffer.append(" and wh.serail_number =? ");
			if("customer_sn".equals(queryType))
				buffer.append(" and wh.customer_sn =? ");
			if("batchNum".equals(queryType))
				buffer.append(" and wh.batch_no =? ");
			if("cartonNum".equals(queryType))
				buffer.append(" and wh.carton_no = ?");
			if("part_no_erp".equals(queryType))
				buffer.append(" and wh.part_no_erp = ?");
			if("part_no_mes".equals(queryType))
				buffer.append(" and wh.part_no_mes = ?");
		db = new DBManager();
		connection = db.GetOraConnection();
		List<LinkedHashMap<String,Object>> listmap = new ArrayList<LinkedHashMap<String,Object>>();
		try {
			pstmt = connection.prepareStatement(buffer.toString());
			if("serial_number".equals(queryType))
				pstmt.setString(1, queryValue);
			else if("customer_sn".equals(queryType))
				pstmt.setString(1, queryValue);
			else if("batchNum".equals(queryType))
				pstmt.setString(1, queryValue);
			else if("cartonNum".equals(queryType))
				pstmt.setString(1, queryValue);
			else if("part_no_erp".equals(queryType))
				pstmt.setString(1, queryValue);
			else if("part_no_mes".equals(queryType))
				pstmt.setString(1, queryValue);
			rs = pstmt.executeQuery();
			while(rs.next()){
				LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
				map.put("carton_no", rs.getString("carton_no"));
				map.put("serail_number", rs.getString("serail_number"));
				map.put("shipping_status", rs.getString("shipping_status"));
				map.put("update_time", rs.getString("update_time"));
				map.put("batch_no", rs.getString("batch_no"));
				map.put("part_no_mes", rs.getString("part_no_mes"));
				map.put("customer_sn", rs.getString("customer_sn"));
				listmap.add(map);
			}
		} catch (SQLException e) {
			log.error(""+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		
		return listmap;
	}
	
	public List<LinkedHashMap<String,Object>> queryAPS(String queryType, String queryValue) {
		StringBuffer buffer = new StringBuffer("select p.model_name,aps.serialnumber,aps.color,aps.binorng,s.customer_sn,su.supplier_sn,dot.dot_value,dot.update_time from sajet.g_aps_upload aps"+
		       " left join (select *"+
		                    " from (select t.*, row_number() over (order by update_time desc) as rnum"+
		                            " from sajet.g_dot t where t.serial_number = ");
		if("customer_sn".equals(queryType))
			buffer.append(" (select serial_number from sajet.g_sn_status s where s.customer_sn = ?))");
		else if("serial_number".equals(queryType))
			buffer.append(" ?)");
					buffer.append(" where rnum = 1) dot on aps.serialnumber = dot.serial_number"+
		        " inner join sajet.g_sn_status s on aps.serialnumber = s.serial_number"+
		        " inner join sajet.g_supplier_sn su on aps.serialnumber = su.serial_number"+
		        " inner join sajet.sys_part p on s.model_id = p.part_id"+
		" where 1=1");
		if("customer_sn".equals(queryType))
			buffer.append(" and s.customer_sn=?");
		else if("serial_number".equals(queryType))
			buffer.append(" and aps.serialnumber = ?");
		db = new DBManager();
		connection = db.GetOraConnection();
		List<LinkedHashMap<String,Object>> listmap = new ArrayList<LinkedHashMap<String,Object>>();
		try {
			pstmt = connection.prepareStatement(buffer.toString());
			pstmt.setString(1, queryValue);
			pstmt.setString(2, queryValue);
			rs = pstmt.executeQuery();
			while(rs.next()){
				LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
				map.put("model_name", rs.getString("model_name"));
				map.put("serialnumber", rs.getString("serialnumber"));
				map.put("color", rs.getString("color"));
				map.put("binorng", rs.getString("binorng"));
				map.put("customer_sn", rs.getString("customer_sn"));
				map.put("supplier_sn", rs.getString("supplier_sn"));
				map.put("dot_value", rs.getString("dot_value"));
				
				listmap.add(map);
			}
		} catch (SQLException e) {
			log.error("queryAPS Exception:"+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listmap;
	}
	
	public Map<String, Object> queryDot(String serialnumber){
		String sql ="select * from ("+
					" select dot.serial_number,dot.dot_value,dot.update_time,dot.machine_name from sajet.g_dot dot where serial_number='"+serialnumber+"' order by update_time desc"+
					" ) where rownum =1";
		db = new DBManager();
		connection = db.GetOraConnection();
		Map<String, Object> map = null;
//		System.out.println(sql);
		try {
			rs = connection.createStatement().executeQuery(sql);

			if(rs.next()){
				map = new HashMap<String, Object>();
				map.put("serial_number", rs.getString("serial_number"));
				String tmp_value = rs.getString("dot_value");
				String dot_value = tmp_value.lastIndexOf(":") != -1 ? tmp_value.substring(0, tmp_value.lastIndexOf(":")) : tmp_value;
				String machine_name = tmp_value.lastIndexOf(":") != -1 ? tmp_value.substring(tmp_value.lastIndexOf(":") + 1) : "";
				map.put("dot_value", dot_value);
				map.put("update_time", rs.getString("update_time"));
				map.put("machine_name", machine_name);
			}else{
				map = new HashMap<String, Object>();
			}
		} catch (SQLException e) {
			log.error(""+e.getMessage());
			e.printStackTrace();
			return null;
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return map;
	}
	
	public List<Object> MetaData(String table){
		table = "sajet."+table;
		String sql = "select * from "+table+" where rownum < 2 ";
		db = new DBManager();
		connection = db.GetOraConnection();
//		Map<String, Object> map = null;
		List<Object> metas = new ArrayList<Object>();
		try {
			pstmt = connection.prepareStatement(sql);
			
//			pstmt.setString(1, table);
			ResultSetMetaData rsd = pstmt.executeQuery().getMetaData();
			
			for (int i = 0; i < rsd.getColumnCount(); i++) {
//				System.out.print("java类型：" + rsd.getColumnClassName(i + 1));
//				System.out.print("\t数据库类型:" + rsd.getColumnTypeName(i + 1));
//				System.out.print("\t字段名称:" + rsd.getColumnName(i + 1));
//				System.out.print("\t字段长度:" + rsd.getColumnDisplaySize(i + 1));
//				System.out.println();
//				map = new HashMap<String,Object>();
//				map.put("columnName", rsd.getColumnName(i + 1).toString());
				metas.add(rsd.getColumnName(i + 1).toString());
			}
		} catch (SQLException e) {
			log.error("MetaData query Exception:"+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return metas;
	}
	
	public List<Object> MetaData(String table, List<Object> columns, String value, int rowNum){
		table = "sajet."+table;
		String sql = "select * from " + table +" where " + columns.get(0) +" = '" + value+"'";
//		System.out.println("sql="+sql);
		db = new DBManager();
		connection = db.GetOraConnection();
		List<Object> metas = new ArrayList<Object>();
		try {
			pstmt = connection.prepareStatement(sql);
			ResultSetMetaData rsd = pstmt.executeQuery().getMetaData();
			rs = pstmt.executeQuery();
			while(rs.next()){
				List<Object> temp = new ArrayList<Object>();
				for(int i=1; i <= rsd.getColumnCount(); i++){
					temp.add(rs.getString(i));
				}
				metas.add(temp);
			}
			
		} catch (SQLException e) {
			log.error("MetaData query Exception:"+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return metas;
//		System.out.println("MetaData 三个参数"+table);
//		table = "sajet."+table;
//		String sql = "select ";
//		if(value.length() == 0){
//			for(Object str : columns){
//				sql += str+",";
//			}
//			sql = sql.substring(0, sql.lastIndexOf(","));
//		}else{
//			sql += "*";
//		}
//		sql += " from " +table+" where 1=1";
//		if(columns.size() != 0)
//			sql += " and rownum <= "+rowNum;
//		else
//			sql += " and " + columns.get(0) +" = '" + value + "'";
//		System.out.println("sql="+sql);
//		db = new DBManager();
//		connection = db.GetOraConnection();
//		
//		List<Object> metas = new ArrayList<Object>();
//		try {
//			pstmt = connection.prepareStatement(sql);
//			ResultSetMetaData rsd = pstmt.executeQuery().getMetaData();
//			rs = pstmt.executeQuery();
//			while(rs.next()){
//				List<Object> temp = new ArrayList<Object>();
//				for(int i=1; i <= rsd.getColumnCount(); i++){
//					temp.add(rs.getString(i));
//				}
//				metas.add(temp);
//			}
//			
//		} catch (SQLException e) {
//			log.error("MetaData query Exception:"+e.getMessage());
//			e.printStackTrace();
//		}finally{
//			db.closeConnection(connection, rs, pstmt);
//		}
//		return metas;
	}
	
	public List<Map<String, Object>> showTables(){
		List<Map<String, Object>> metaTables = new ArrayList<Map<String, Object>>();
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			rs = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("TABLE_NAME", rs.getString("TABLE_NAME"));
				metaTables.add(map);
			}
		} catch (SQLException e) {
			log.error(""+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return metaTables;
	}
	
	public List<LinkedHashMap<String, Object>> queryDefectCodeByProcessID(String process_id){
		String sql = "select defect.defect_id,defect.defect_code,defect.defect_desc,defect.defect_desc2,defect.defect_type,ru.responsibility_unit from sajet.sys_test_item item"+
				" inner join sajet.sys_test_item_type item_type on item.item_type_id = item_type.item_type_id and item_type.process_id = ?"+
				" inner join sajet.sys_defect defect on item.defect_code = defect.defect_code"+
				" inner join sajet.sys_test_item_ru_map ru on item.item_id = ru.item_id and ru.enabled='Y'"+
				" where item.enabled ='Y'";
		List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, process_id);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("defect_id", rs.getString("defect_id"));
//				map.put("defect_code", rs.getString("defect_code"));
				map.put("defect_desc", rs.getString("defect_desc"));
				map.put("defect_desc2", rs.getString("defect_desc2"));
				map.put("defect_type", rs.getString("defect_type"));
				map.put("responsibility_unit", rs.getString("responsibility_unit"));
				list.add(map);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		
		return list;
	}
	
	public List<LinkedHashMap<String, Object>> queryFlowBySerialNumber(String serial_number){
		List<LinkedHashMap<String, Object>> mapList = new ArrayList<LinkedHashMap<String, Object>>();
		String sql ="select terminal.terminal_name,hr.emp_name,hr.dept_name,g.carton_no,g.operate_type,g.update_time,g.application_name from sajet.g_pdlinewh_emp g"+
				" inner join sajet.sys_hr_emp hr on g.emp_id = hr.emp_id"+
				" inner join sajet.sys_terminal terminal on g.terminal_id = terminal.terminal_id"+
				" where g.carton_no = '"+serial_number+"'";
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
//			pstmt = connection.prepareStatement(sql);
//			pstmt.setString(1, serial_number);
//			System.out.println("queryFlowBySerialNumber="+sql);
			rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				LinkedHashMap<String, Object> linkMap = new LinkedHashMap<String, Object>();
				linkMap.put("terminal_name", rs.getString("terminal_name"));
				linkMap.put("emp_name", rs.getString("emp_name"));
				linkMap.put("dept_name", rs.getString("dept_name"));
				linkMap.put("carton_no", rs.getString("carton_no"));
				linkMap.put("operate_type", rs.getString("operate_type"));
				linkMap.put("update_time", rs.getString("update_time"));
				linkMap.put("application_name", rs.getString("application_name"));
				mapList.add(linkMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return mapList;
	}
}