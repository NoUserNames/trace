package rt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rt.connection.DBManager;
import rt.dao.ICustomizeDAOImpl;
import rt.dao.interfaces.IQueryMeasureDAO;

public class IQueryMeasureDAOImpl implements IQueryMeasureDAO {

	private static Logger log = Logger.getLogger(ICustomizeDAOImpl.class);
	
	private DBManager db ;
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	@Override
	public List<List<Object>> queryMeasure(String table, String serial_number,int columnSize) {
//		String sql = "select * from "+table +" where serial_number='"+serial_number+"' or serial_number in (select serial_number from sajet.g_sn_status@mesdb where carton_no='"+serial_number+"')";
		List<String> sn = new ArrayList<String>();
		sn.add("sajet.LOGO_GAP_CAP");
		sn.add("sajet.LOGO_ENDOCOELE_CAP");
		sn.add("sajet.IO_GAP_CAP");
		sn.add("sajet.AW_GAP_CAP");
		
		StringBuffer buffer = new StringBuffer("select * from "+table +" where ");
		if(sn.contains(table))
			buffer.append("sn = '"+ serial_number +"' or sn in ");
		else
			buffer.append("serial_number = '"+ serial_number +"' or serial_number in ");
		buffer.append("(select serial_number from sajet.g_sn_status where carton_no='"+serial_number+"')");

		db = new DBManager();
		connection = db.GetOraConnection();
		List<List<Object>> lists = new ArrayList<List<Object>>();
		try {
			rs = connection.createStatement().executeQuery(buffer.toString());
			while(rs.next()) {
				List<Object> list = new ArrayList<Object>();
				for(int i=0; i<columnSize; i++){
					list.add(rs.getString(i+1));
				}
				lists.add(list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("queryMeasure Exception:"+e.getMessage());
		} finally {
			db.closeConnection(connection, rs, pstmt);
		}
		return lists;
	}

	@Override
	public List<String> ColumnData(String table) {
		String sql = "select * from "+table+" where rownum = 1 ";
		db = new DBManager();
		connection = db.GetOraConnection();
		List<String> columnList = new ArrayList<String>();
		try {
			pstmt = connection.prepareStatement(sql);

			ResultSetMetaData rsd = pstmt.executeQuery().getMetaData();
			
			for (int i = 0; i < rsd.getColumnCount(); i++) {
				columnList.add(rsd.getColumnName(i + 1).toString());
			}
		} catch (SQLException e) {
			log.error("MetaData query Exception:"+e.getMessage());
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return columnList;
	}

}