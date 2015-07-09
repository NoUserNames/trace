package rt.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

/**
 * 多叉树类
 */
public class MultipleTree {
	public static void main(String[] args) {
		new MultipleTree().testJSON();
	}

	public String testJSON(){
		// 读取层次数据结果集列表 
		List<HashMap<String, String>> dataList = VirtualDataGenerator.getVirtualResult();
		System.out.println("dataList="+dataList);
		// 节点列表（散列表，用于临时存储节点对象）
		HashMap<String, Node> nodeList = new HashMap<String, Node>();
		// 根节点
		Node root = null;
		// 将结果集存入散列表（后面将借助散列表构造多叉树）
		for (Iterator<HashMap<String, String>> it = dataList.iterator(); it.hasNext();) {
			Map<?, ?> dataRecord = (Map<?, ?>) it.next();
			Node node = new Node();
			node.id = (String) dataRecord.get("id");
			node.name = (String) dataRecord.get("text");
			node.pId = (String) dataRecord.get("parentId");
			nodeList.put(node.id, node);
		}
		// 构造无序的多叉树
		Set<?> entrySet = nodeList.entrySet();
		for (Iterator<?> it = entrySet.iterator(); it.hasNext();) {
			@SuppressWarnings("rawtypes")
			Node node = (Node) ((Map.Entry) it.next()).getValue();
			if (node.pId == null || node.pId.equals("")) {
				root = node;
			} else {
				((Node) nodeList.get(node.pId)).addChild(node);
			}
		}
		// 输出无序的树形菜单的JSON字符串
		System.out.println(root.toString());
		// 对多叉树进行横向排序
		root.sortChildren();
		// 输出有序的树形菜单的JSON字符串
		System.out.println(root.toString());
		return root.toString();
	}
}

/**
 * 节点类
 */
class Node {
	/**
	 * 节点编号
	 */
	public String id;

	/**
	 * 节点内容
	 */
	public String name;

	/**
	 * 父节点编号
	 */
	public String pId;

	/**
	 * 孩子节点列表
	 */
	private List<Node> children = new ArrayList<Node>();

	// 添加孩子节点
	public void addChild(Node node) {
		children.add(node);
	}

	// 先序遍历，拼接JSON字符串
	public String toString() {
		String result = "{" + "id : '" + id + "'" + ", pId : '" + name + "'";
		if (children.size() != 0) {
			result += ", children : [";
			for (Iterator<Node> it = children.iterator(); it.hasNext();) {
				result += ((Node) it.next()).toString() + ",";
			}
			result = result.substring(0, result.length() - 1);
			result += "]";
		} else {
			result += ", leaf : true";
		}
		return result + "}";
	}

	// 兄弟节点横向排序
	public void sortChildren() {
		if (children.size() != 0) {
			// 对本层节点进行排序（可根据不同的排序属性，传入不同的比较器，这里传入ID比较器）
			Collections.sort(children, new NodeIDComparator());
			// 对每个节点的下一层节点进行排序
			for (Iterator<Node> it = children.iterator(); it.hasNext();) {
				((Node) it.next()).sortChildren();
			}
		}
	}

}

/**
 * 节点比较器
 */
class NodeIDComparator implements Comparator<Object> {
	// 按照节点编号比较
	public int compare(Object o1, Object o2) {
		int j1 = Integer.parseInt(((Node) o1).id);
		int j2 = Integer.parseInt(((Node) o2).id);
		return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
	}
}

/**
 * 构造虚拟的层次数据
 */
class VirtualDataGenerator {
	// 构造无序的结果集列表，实际应用中，该数据应该从数据库中查询获得；
	public static List<HashMap<String, String>> getVirtualResult() {
		List<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		
		HashMap<String, String> dataRecord1 = new HashMap<String, String>();
		dataRecord1.put("id", "112000");
		dataRecord1.put("text", "廊坊银行解放道支行");
		dataRecord1.put("parentId", "110000");

		HashMap<String, String> dataRecord2 = new HashMap<String, String>();
		dataRecord2.put("id", "112200");
		dataRecord2.put("text", "廊坊银行三大街支行");
		dataRecord2.put("parentId", "112000");

		HashMap<String, String> dataRecord3 = new HashMap<String, String>();
		dataRecord3.put("id", "112100");
		dataRecord3.put("text", "廊坊银行广阳道支行");
		dataRecord3.put("parentId", "112000");

		HashMap<String, String> dataRecord4 = new HashMap<String, String>();
		dataRecord4.put("id", "113000");
		dataRecord4.put("text", "廊坊银行开发区支行");
		dataRecord4.put("parentId", "110000");

		HashMap<String, String> dataRecord5 = new HashMap<String, String>();
		dataRecord5.put("id", "100000");
		dataRecord5.put("text", "廊坊银行总行");
		dataRecord5.put("parentId", "");

		HashMap<String, String> dataRecord6 = new HashMap<String, String>();
		dataRecord6.put("id", "110000");
		dataRecord6.put("text", "廊坊分行");
		dataRecord6.put("parentId", "100000");

		HashMap<String, String> dataRecord7 = new HashMap<String, String>();
		dataRecord7.put("id", "111000");
		dataRecord7.put("text", "廊坊银行金光道支行");
		dataRecord7.put("parentId", "110000");

		dataList.add(dataRecord1);
		dataList.add(dataRecord2);
		dataList.add(dataRecord3);
		dataList.add(dataRecord4);
		dataList.add(dataRecord5);
		dataList.add(dataRecord6);
		dataList.add(dataRecord7);
//		IUserDAOImpl dao = new IUserDAOImpl();
		return dataList;
	}
}
