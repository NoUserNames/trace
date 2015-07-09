package rt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import rt.connection.DBManager;
import rt.dao.interfaces.ISysLog;
import rt.pojo.LogTraceMaintain;

public class ISysLogImpl implements ISysLog {

	private DBManager db ;
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement pstmt;
	private static Logger log = Logger.getLogger(ISysLogImpl.class);
	
	@Override
	public boolean log(LogTraceMaintain logTraceMaintain, ArrayList<ArrayList<String>> dataList) {
		db = new DBManager();
		connection = db.GetOraConnection();
		String sql = "insert into sj.log_trace_maintain (log_id,emp_no,action,action_desc) values (?,?,?,?)";
		try {
			pstmt = connection.prepareStatement(sql);
			for(ArrayList<String> list : dataList){
				System.out.println(2);
				pstmt.setString(1, logTraceMaintain.getLogId());
				pstmt.setString(2, logTraceMaintain.getEmpNO());
				pstmt.setString(3, logTraceMaintain.getAction());
				pstmt.setString(4, list.get(0));
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return false;
	}

}