package rt.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rt.bean.DeviceAvailability;
import rt.connection.DBManager;
import rt.util.ReadProperties;
import rt.util.TUtil;

public class IDeviceAvailabilityImpl implements IDeviceAvailability {
	
	Logger log = Logger.getLogger(IDeviceAvailabilityImpl.class);
	
	public static void main(String[] args){
		TUtil.print(TUtil.format("HH:mm:ss"));
	}
	
	@Override
	public List<DeviceAvailability> lastScanTime(String start,String end) {
		
		// TODO Auto-generated method stub
		DBManager db = new DBManager();
		Connection conn = null;
		ResultSet rs = null;

		String sql ="select terminal.terminal_id,terminal.terminal_name,b.lasttime"
		  +" from sajet.sys_terminal terminal,"
		  +" (select distinct (g.terminal_id) terminal_id, max(to_char(g.out_process_time,'yyyy-mm-dd hh24:mi:ss')) lasttime"
		  +" from sajet.g_sn_status g"
		  +" where g.out_process_time >="
		  +" to_date('"+start+"', 'YYYY-MM-DD')"
		  +" and g.out_process_time <"
		  +" to_date('"+end+"', 'YYYY-MM-DD')"
		  +" group by g.terminal_id) b"
		  +" where terminal.terminal_id = b.terminal_id(+)"
		  +" and terminal.enabled='Y'"
		  +" order by lasttime asc";
		
		List<DeviceAvailability> da = null;
		List<DeviceAvailability> device = null;
		if (start != null && end != null){
			try {
				conn = db.GetOraConnection();
				rs = conn.createStatement().executeQuery(sql);
				DeviceAvailability dappojo = null;
				da = new ArrayList<DeviceAvailability>();
				while(rs.next()){
					dappojo = new DeviceAvailability();
					dappojo.setTerminalID(rs.getInt("terminal_id"));
					dappojo.setTerminalName(rs.getString("terminal_name"));
					dappojo.setLasttime(rs.getString("lasttime"));
					
					da.add(dappojo);
				}				
				
				device = new ArrayList<DeviceAvailability>();
				
				List<Integer> id = getTerminalID(ReadProperties.ReadProprety("terminalid"));
				for(DeviceAvailability dp : da){
					for (int terminalid : id){
						if(dp.getTerminalID() == terminalid){
							device.add(dp);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("设备利用率查询SQL出错");
			}finally{
				db.closeConnection(conn, rs, null);
			}
		}
		return device;
	}

	public List<Integer> getTerminalID(String path){
		File file = new File(path);
        BufferedReader reader = null;
        List<Integer> id = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            // 一次读入一行，直到读入null为文件结束
            id = new ArrayList<Integer>();
            String tempString ="";
            while ((tempString = reader.readLine()) != null) {
                id.add(Integer.parseInt(tempString));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return id;
	}
	
	/**
	 * 设备使用率
	 */
	@Override
	public List<Reprot> getDeviceAvailability(String start,String end,int cnt){
		List<Reprot> list = null;
		DBManager db = new DBManager();
		Connection connection = null;
		ResultSet rs = null;
		try {
			String sqlColumns="select column_name from user_tab_columns where table_name = 'SYS_TERMINAL_MAPPING' AND column_name LIKE 'Ia_%' escape 'a'";
			connection = db.GetOraConnection();
			List<String> columnNames = null;
//			ResultSet rs = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery(sqlColumns);
			rs = connection.createStatement().executeQuery(sqlColumns);

			int columns = 1;
			//查所有列

			columnNames = new ArrayList<String>();
			while(rs.next()){
				columnNames.add(rs.getString("column_name"));
				columns ++;
			}
//			System.out.println("columns="+columns);
			db.closeConnection(connection, rs, null);
			
			//拼SQL查列
			String sql ="select terminal_id,";
			for(String str :columnNames){
				sql +=str+",";
			}
			sql = sql.substring(0, sql.length() -1);
			sql += " from SYS_TERMINAL_MAPPING t order by t.terminal_id";

			
			connection = db.GetOraConnection();
			rs = connection.createStatement().executeQuery(sql);

			List<String> terminal_id_col = null;//列数据
			List<List<String>> terminal_id_row = new ArrayList<List<String>>();//行数据
			List<String> terminal_id = new ArrayList<String>();//terminal_id标识列
			//拆分sys_terminal_mapping的主站和公用站在两个集合中
			while(rs.next()){
				terminal_id_col = new ArrayList<String>();
				for(int i=1;i<=columns;i++){
					if (i ==1 ){//terminal_id
						terminal_id.add(rs.getString(1));
					}else {
						if (rs.getString(i)!=null){
							terminal_id_col.add((rs.getString(i)));
						}
					}
				}
				terminal_id_row.add(terminal_id_col);				
			}			
			db.closeConnection(connection, rs, null);
			
			Map<String,Integer> mapTravel = getAllTravel(start,end,cnt);
			
			Reprot rep = null;
			int index = 0;
			list = new ArrayList<Reprot>();
			
			for(List<String> obj : terminal_id_row){//terminal_id行数据，包含多个terminal_id公用站集合	
				int totalCNT = 0;				
				for(String str : obj){//解析每行terminal_id的每个id						
					rep = new Reprot();
					if (mapTravel.get(str) != null){
						totalCNT += mapTravel.get(str);
					}
				}				
				rep.setTerminal_id(terminal_id.get(index));

				rep.setCnt(totalCNT);
				list.add(rep);
				index ++;
			}
			
			mapTravel.clear();
			
		} catch (SQLException e) {
			log.error("查询列名出错："+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, null);
		}
		return list;
	}
	
	/**
	 * 查一段时间内站点扫描量
	 * @param date1 时间1
	 * @param date2 时间2
	 * @return map
	 */
	public Map<String,Integer>getAllTravel(String date1,String date2,int cnt){
		String sql="select count(*) cnt, a.terminal_id"+
		  " from sajet.g_sn_travel a"+
		  " where a.out_process_time >="+
		  " to_date('"+date1+" 00:00:00', 'yyyy-mm-dd hh24:mi:ss')"+
		  " and a.out_process_time <="+
		  " to_date('"+date2+" 23:59:59', 'yyyy-mm-dd hh24:mi:ss')"+
		  " group by a.terminal_id having count(*)<="+cnt;
		DBManager db = new DBManager();
		Connection connetion = db.GetOraConnection();
		Map<String,Integer> map = new HashMap<String,Integer>();
		
//		System.out.println(sql);
		
		try {
			ResultSet rs = connetion.createStatement().executeQuery(sql);		
			while(rs.next()){
				map.put(rs.getString("terminal_id"), rs.getInt("cnt"));
			}
			db.closeConnection(connetion, rs , null);
		} catch (SQLException e) {
			log.error("取所有站点扫描量出错："+e.getMessage());
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据站点ID查站点名称
	 * @param terminaid 站点ID
	 * @return 站点名
	 */
	@Override
	public String getTerminalName(int terminaid){
		String sql = "select terminal_name from sajet.sys_terminal where terminal_id=?";
		DBManager db = new DBManager();
		Connection connection = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try {
			connection = db.GetOraConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, terminaid);
			rs =pstmt.executeQuery();
			if(rs.next()){
				return rs.getString("terminal_name");
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, null);
		}
		return "";
	}
	
	class Reprot{
		public String terminal_id;
		public int cnt;
		public String terminalName;
		public String getTerminal_id() {
			return terminal_id;
		}
		public void setTerminal_id(String terminal_id) {
			this.terminal_id = terminal_id;
		}
		public int getCnt() {
			return cnt;
		}
		public void setCnt(int cnt) {
			this.cnt = cnt;
		}
		public String getTerminalName() {
			return terminalName;
		}
		public void setTerminalName(String terminalName) {
			this.terminalName = terminalName;
		}
	}
}