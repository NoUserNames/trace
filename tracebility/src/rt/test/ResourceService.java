package rt.test;

import java.util.List;

import rt.dao.IUserDAO;
import rt.dao.IUserDAOImpl;
import rt.pojo.Node;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ResourceService {

	public static void main(String[] args) {
		ResourceService s = new ResourceService();
		IUserDAO i = new IUserDAOImpl();
		System.out.println(i.getAllNodes().size());
		System.out.println(s.createTreeJson(i.getAllNodes()));
	}

	/**
	 * 创建一颗树，以json字符串形式返回
	 * 
	 * @param list
	 *            原始数据列表
	 * @return 树
	 */
	public String createTreeJson(List<Node> list) {
		JSONArray rootArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Node resource = list.get(i);
			if (resource.getPid().equals("0")) {
				JSONObject rootObj = createBranch(list, resource);
				rootArray.add(rootObj);
			}
		}

		return rootArray.toString();
	}

	/**
	 * 递归创建分支节点Json对象
	 * 
	 * @param list
	 *            创建树的原始数据
	 * @param currentNode
	 *            当前节点
	 * @return 当前节点与该节点的子节点json对象
	 */
	private JSONObject createBranch(List<Node> list, Node currentNode) {
		/*
		 * 将javabean对象解析成为JSON对象
		 */
		JSONObject currentObj = JSONObject.fromObject(currentNode);
		JSONArray childArray = new JSONArray();
		/*
		 * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
		 * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该 节点放入当前节点的子节点列表中
		 */
		for (int i = 0; i < list.size(); i++) {
			Node newNode = list.get(i);
			if (newNode.getPid() != null && newNode.getPid().compareTo(currentNode.getId()) == 0) {// 
				JSONObject childObj = createBranch(list, newNode);
				childArray.add(childObj);
			}
		}

		/*
		 * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
		 */
		if (!childArray.isEmpty()) {
			currentObj.put("children", childArray);
		}

		return currentObj;
	}
}