package rt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rt.connection.DBManager;

public class DAOUtils {
	
	private static Logger log = Logger.getLogger(DAOUtils.class);
	
	private DBManager db;
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	/**
	 * 查询类-单表查询--多列
	 * @param columns 列集合
	 * @param table 表名
	 * @return List<Map<String,Object>
	 */
	public List<Map<String,Object>> queryColumnNames(String[] columns, String table) {
		String sql ="select ";
		int index = 0;
		for(String column : columns){
			sql += (index > 0 ? column + "," : column);
			index ++;
		}
		sql += " from "+table;
		db = new DBManager();
		connection = db.GetOraTestConnection();
		List<Map<String,Object>> listMaps = new ArrayList<Map<String,Object>>();
		try {
			rs = connection.prepareStatement(sql).executeQuery();
			while (rs.next()){
				Map<String,Object> map = new HashMap<String,Object>();
				for(String column : columns){
					map.put(column, rs.getString(column));
				}
				listMaps.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return listMaps;
	}
	
	/**
	 * 查询类-单表查询--单列
	 * @param column 列集合
	 * @param table 表名
	 * @param distinct
	 * @return String
	 */
	public List<Map<String,Object>> queryColumnName(String column, String table, boolean distinct) {
		String sql ="select " ;
		if(distinct)
			sql += "distinct("+column+") "+column +" from "+table;
		else
			sql += column + " from "+table;
		sql += " order by " + column;
		db = new DBManager();
		connection = db.GetOraTestConnection();
		List<Map<String,Object>> listMaps = new ArrayList<Map<String,Object>>();
		try {
			rs = connection.prepareStatement(sql).executeQuery();
			while (rs.next()){
				Map<String,Object> map = new HashMap<String,Object>();
					map.put(column, rs.getString(column));
				listMaps.add(map);
			}
			return listMaps;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
	}
	
	/**
	 * @param column 列
	 * @param table 表
	 * @param c 条件名
	 * @param v 值
	 * @return
	 */
	public List<Map<String,Object>> queryColumnName(String column, String table, String c, String v) {
		String sql ="select " + column + " from "+ table + " where "+ c + " ='" + v +"'";
		db = new DBManager();
		connection = db.GetOraTestConnection();
		List<Map<String,Object>> listMaps = new ArrayList<Map<String,Object>>();
		try {
			rs = connection.prepareStatement(sql).executeQuery();
			while (rs.next()){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put(column, rs.getString(column));
				listMaps.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return listMaps;
	}
	
	public ResultSet executeStatementQuery(String sql, Connection connection) throws SQLException{
		return connection.createStatement().executeQuery(sql);
	}
	
	public ResultSet executePreparedStatementQuery(String sql, Connection connection) throws SQLException{
		return connection.prepareStatement(sql).executeQuery(sql);
	}
	
	public boolean excuteUpdate(String sql, Connection connection){
		try {
			return connection.createStatement().executeUpdate(sql) != -1 ? true : false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}