package rt.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import rt.connection.DBManager;

public class ReflectUtil {

	private DBManager db;
	private Connection connection;
	private ResultSet rs;
	PreparedStatement pstmt;
	
	/**
	 * 通过表名映射数据
	 * @param tableName 数据表名
	 * @param clz 实体类
	 * @return 实体类数据集
	 */
	public List<Object> getByRefect(String tableName,String clz){
		List<Object> listObject = new ArrayList<Object>();
		try {
			String sql = "select * from "+tableName;
			System.out.println(sql);
			db = new DBManager();
			connection = db.GetOraConnection();
			rs = connection.createStatement().executeQuery(sql);
			Class<?> cls;
			cls = Class.forName(clz);
			Object object = null;
			Field[] field = cls.getDeclaredFields();//获取实体类所有声明属性
			while(rs.next()){
				object = cls.newInstance();
				for(int i=0;i<field.length;i++){
					BeanUtils.setProperty(object, field[i].getName(), rs.getObject(i+1));
				}
				listObject.add(object);
			}
		} catch (SQLException e) {
			System.out.println("SQL语句执行出错");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("没有找到实体类");
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("创建实体类实例异常");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("非法访问实体异常");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.out.println("调用目标异常");
			e.printStackTrace();
		}finally{
			db.closeConnection(connection, rs, pstmt);
		}
		return listObject;
	}
}