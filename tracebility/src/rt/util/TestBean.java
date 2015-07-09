package rt.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import rt.pojo.Node;

public class TestBean {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 加载类  
        Class<?> cls;
		try {
			cls = Class.forName("rt.pojo.Node");
			// 创建对象
			Node p = (Node) cls.newInstance();
			Field[] field = cls.getDeclaredFields();
			for(Field f : field){			
				System.out.println(f.getName());
				if("nodedesc".equals(f.getName())){
					BeanUtils.setProperty(p, f.getName(), "描述啊");
				}
				BeanUtils.setProperty(p, f.getName(), "123");
			}
			System.out.println(p.getNodename()+"\t"+p.getNodedesc());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

}
