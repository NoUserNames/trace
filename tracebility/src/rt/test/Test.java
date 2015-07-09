package rt.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

public class Test {

	private static TreeSet<String> noReapted;

	public static void main(String[] args){
//		List list = new ArrayList();
//		
//		list.add("1");
//		list.add(2, "123");
		
		Map[] a = new HashMap[5];
		System.out.println(a[4]);
	}
	public static void test2(){
		String a ="a,b";
		String c ="";
		for(String str : a.split(",")){
			c += "'"+str+"'"+",";
		}
		c = c.substring(0,c.lastIndexOf("'")+1);
		System.out.println(c);
	}
	
	public static List<Object> removeDuplicate(List<Object> list){   
	    HashSet<Object> h  =   new  HashSet<Object>(list);
	    list.clear();
	    list.addAll(h);
	    return list;   
	 }
	
	//取两个List重复与不重复的部分。
	public static void Duplicate(){
		List<Integer> a = new ArrayList<Integer>();
		a.add(1);
		a.add(2);
		a.add(2);
		a.add(3);
		List<Integer> b = new ArrayList<Integer>();
		b.add(1);
		b.add(2);
		b.add(3);
		a.removeAll(b);
		a.addAll(b);
		System.out.println(a);
	}
	
	public static void gethome(){
		System.getProperty("user.dir");
	}
	
	public static void test1(){
		String b ="<P>DYH1101000649256</P><P>DYH1201005927724</P>";
		b = b.replaceAll("<P>", "").replaceAll("</P>", ",");
		String[] array = b.split(",");
		System.out.println(array.length);
		String c = "";
		for(String str : array ){
			c += (",'"+str+"'");
		}
		System.out.println(c.substring(1, c.length()));
		
	}
	
	public static int factorial(int n){
        if (n==1){
           return 1;
        } else {
           return n * factorial(n-1);
        }
    }
	
	public static void getComputerName(){
		Map<String,String> map = System.getenv();
		 System.out.println(map.get("USERDOMAIN"));//获取计算机域名  
	}
	
	public static void zifuchuanquchong(){
		 String str = "10001,10004,10002,10004,100014,10004";
		 
	        List<String> data = new ArrayList<String>();
	 
	        for (int i = 0; i < str.length(); i++) {
	            String s = str.substring(i, i + 1);
	            if (!data.contains(s)) {
	                data.add(s);
	            }
	        }
	 
	        String result = "";
	        for (String s : data) {
	            result += s;
	        }
	        System.out.println(result);
	}
	
	//清除重复的数据
    public static void removeRepeated(String str){
    	noReapted = null;
        for (int i = 0; i < str.length(); i++){
        	str = str.substring(str.indexOf(","));
            noReapted.add(""+str.charAt(i));
            //str.charAt(i)返回的是char型  所以先加一个""空格 , 转换成String型
            //TreeSet泛型能保证重复的不加入 , 而且有序
        }
         
        str = "";
         
        for(String index:noReapted){
            str += index;
        }
         
        //输出
        System.out.println (str);
    }
    
    /**
     * 写网络文件到本地
     */
    public void copyURLtoFile(){
    	URL url = null;
		try {
			url = new URL("http://www.163.com");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
         
         File file = new File("c:\\163.html");  
           
         try {
			FileUtils.copyURLToFile(url, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }
}