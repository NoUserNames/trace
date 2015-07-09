package rt.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import rt.util.ReadProperties;


public class DBManager {
	static Logger log = Logger.getLogger(DBManager.class);

	public DBManager(){

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		DBManager db = new DBManager();
		db.GetOraTestConnection();
	}
	
	/**
	 * 获取Sqlite数据库连接
	 * @return
	 */
	public synchronized Connection GetSqliteConnection(){		
		Connection conn = null;
		try {
			Class.forName(ReadProperties.ReadProprety("sqlite.db.driver"));
			conn = DriverManager.getConnection(ReadProperties.ReadProprety("sqlite.db.url"));
		} catch (ClassNotFoundException e) {
			log.error("没有找到数据驱动:"+e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			log.error("加载数据连接异常："+e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
	
	public ResultSet query(String sql,Connection connection){
		ResultSet rs = null;
		try {			
			rs = connection.createStatement().executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 获取Oracle【测试】数据库连接
	 * @return
	 */
	public synchronized Connection GetOraTestConnection(){		
		Connection conn = null;
		try {
			Class.forName(ReadProperties.ReadProprety("oracle.testdb.driver"));
			conn = DriverManager.getConnection(ReadProperties.ReadProprety("oralce.testdb.url"),
					ReadProperties.ReadProprety("oracle.testdb.username"),
					ReadProperties.ReadProprety("oracle.testdb.password"));
		} catch (ClassNotFoundException e) {
			log.error("没有找到数据驱动:"+e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			log.error("加载数据连接异常："+e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 获取Oracle数据库连接
	 * @return
	 */
	public synchronized Connection GetOraConnection(){
		Connection conn = null;
		try {
			Class.forName(ReadProperties.ReadProprety("oracle.db.driver"));
			conn = DriverManager.getConnection(ReadProperties.ReadProprety("oralce.db.url"),
					ReadProperties.ReadProprety("oracle.db.username"),
					ReadProperties.ReadProprety("oracle.db.password"));
		} catch (ClassNotFoundException e) {
			log.error("没有找到数据驱动:"+e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			log.error("加载数据连接异常："+e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 获取Oracle 34数据库连接
	 * @return
	 */
	public synchronized Connection GetOra34Connection(){		
		Connection conn = null;
		try {
			Class.forName(ReadProperties.ReadProprety("oracle.34db.driver"));
			conn = DriverManager.getConnection(ReadProperties.ReadProprety("oralce.34db.url"),
					ReadProperties.ReadProprety("oracle.34db.username"),
					ReadProperties.ReadProprety("oracle.34db.password"));
		} catch (ClassNotFoundException e) {
			log.error("没有找到数据驱动:"+e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			log.error("加载数据连接异常："+e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 获取Oracle 31数据库连接
	 * @return
	 */
	public synchronized Connection GetOra31Connection(){		
		Connection conn = null;
		try {
			Class.forName(ReadProperties.ReadProprety("oracle.31db.driver"));
			conn = DriverManager.getConnection(ReadProperties.ReadProprety("oralce.31db.url"),
					ReadProperties.ReadProprety("oracle.31db.username"),
					ReadProperties.ReadProprety("oracle.31db.password"));
		} catch (ClassNotFoundException e) {
			log.error("没有找到数据驱动:"+e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			log.error("加载数据连接异常："+e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
	
	public boolean excuteOra(String sql){
		int rs = 0;
		Connection connection = GetOraConnection();
		try {
			rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs !=0 ? true : false;
	}
	
	/**
	 * 关闭连接
	 * @param connection 数据库连接对象
	 * @param rs 结果集对象
	 * @param pstmt 预编译对象
	 */
	public void closeConnection (Connection connection, ResultSet rs, PreparedStatement pstmt){
		try {
			if(null != pstmt)
				pstmt.close();
			if(null != rs)
				rs.close();
			if(null != connection)
				connection.close();
		} catch (SQLException e) {
			log.error("关闭数据连接异常,错误码："+e.getErrorCode()+"错误信息："+e.getMessage());
		}
	}
}