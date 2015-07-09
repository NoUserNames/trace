package rt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rt.connection.DBManager;

/**
 * @author 张强
 * AV9量测数据处理
 */
public class IQueryAV9MeasurationImpl implements IQueryAV9Measuration{

	private static Logger log = Logger.getLogger(IQueryAV9MeasurationImpl.class);
	
	private DBManager db ;
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	public List<Map<String,Object>> queryAV9Measuration(){
		db = new DBManager();
		connection = db.GetOraConnection();
		try {
			pstmt = connection.prepareStatement("");
		} catch (SQLException e) {
			log.error(""+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return null;
	}
}