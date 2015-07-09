package rt.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import rt.pojo.Product;

public class Rel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new Rel().reflect6(new Product(),null);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Product reflect6(Product pro,Object obj1) throws InvocationTargetException{
	try {
		 Class<?> cls = Class.forName("rt.pojo.Product");//加载UserBean类到内存中！获取一个Class对象
	     Object obj = cls.newInstance();//通过class类反射一个对象实体！
	     Method[] method = cls.getDeclaredMethods();
	     Field[] field = cls.getDeclaredFields();
	     System.out.println("method="+method.length+"\t"+"field="+field.length);
	     for(int i=0;i< method.length;i++){	    	 
	    	 if(method[i].getName().startsWith("set")){
	    		 System.out.println(method[i].getName()+"\t"+field[i].getName()+"\t"+field[i].getType());
//	    		 cls.getDeclaredMethod(method[i].getName(),new Class[]{field[i].getType()});
//	    		 method[i].invoke(obj, new Object[]{obj1});
	    	 }
	     }
	     pro = (Product)obj;
	     System.out.println(pro.getMODEL_ID());
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return pro;
	}
}
