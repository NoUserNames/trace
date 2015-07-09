package rt.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jDemo {

	public static void main(String[] a){
		parserXml1("d:/userinfo.xml");
	}
	
	 public static void parserXml(String fileName) {
	        File inputXml = new File(fileName);
	        SAXReader saxReader = new SAXReader();

	        try {
	            Document document = saxReader.read(inputXml);
	            Element users = document.getRootElement();
	            for (Iterator<?> i = users.elementIterator(); i.hasNext();) {
	                Element user = (Element) i.next();
	                for (Iterator<?> j = user.elementIterator(); j.hasNext();) {
	                    Element node = (Element) j.next();
	                    System.out.println(node.getName()+ ":" + node.getText());
	                }
	                System.out.println();
	            }
	        } catch (DocumentException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	 
	 public static List<String> parserXml1(String fileName) {
		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		List<String> list = new ArrayList<String>();int d = 0;
		try {
			Document document = saxReader.read(inputXml);
			
			Element users = document.getRootElement();
			
			String up = "";
			for (Iterator<?> i = users.elementIterator(); i.hasNext();) {
				Element user = (Element) i.next();
				Element EMP_NO = user.element("EMP_NO");
				String name = EMP_NO.getTextTrim();
				
				if (name.startsWith("C")) {
					Element EMP_NAME = user.element("EMP_NAME");
					up = name + "," + EMP_NAME.getTextTrim();
					list.add(up);
				}
				d ++;
			}
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(d);
//		for (String str : list){
			System.out.println(list.size());
//		}
		return list;
	}
}
