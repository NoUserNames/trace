package rt.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapTest {
	public static void main(String[] a) {

		test1();

	}

	public static void test() {//无序
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("a", "aa");
		map.put("b", "bb");
		map.put("c", "cc");
		map.put("d", "dd");
		Set<?> set = map.entrySet();

		for (Iterator<?> iter = set.iterator(); iter.hasNext();) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();

			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			System.out.println(key + " :" + value);
		}
	}

	public static void test1() {//按put顺序
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		map.put("a", "aa1");
		map.put("c", "cc1");
		map.put("d", "dd1");
		map.put("b", "bb1");
		Set<String> set = map.keySet();

		for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			String value = (String) map.get(key);
			System.out.println(key + "====" + value);
		}
	}
	
	public static void test3() {//按键顺序排序
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("a", "aa1");
		map.put("c", "cc1");
		map.put("d", "dd1");
		map.put("b", "bb1");
		Set<String> set = map.keySet();

		for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			String value = (String) map.get(key);
			System.out.println(key + "====" + value);
		}
	}

	public static void test2() {
		Map<String, String> map = new HashMap<String, String>();

		map.put("apple", "新鲜的苹果"); //向列表中添加数据

		map.put("computer", "配置优良的计算机"); //向列表中添加数据

		map.put("book", "堆积成山的图书"); //向列表中添加数据

		Collection<String> values = map.values();

		for (Object object : values) {

			System.out.println("键值：" + object.toString());

		}
	}
}