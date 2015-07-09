package rt.test;

import java.util.ArrayList;
import java.util.List;

import rt.util.Page;

public class PageTest {

	public static void main(String[] args){
		int currPage = 0;
		List<Object> list = new ArrayList<Object>();
		for(int i=1;i<=100;i++){
			list.add(i);
		}
		Page<Object> page = new Page<Object>(list);
		page.setPageRecords(40);
		System.out.println("getCurrentPage="+page.getCurrentPage());
		System.out.println("getTotalPage="+page.getTotalPage());
		list = page.getPage(2);
		System.out.println(list);
		currPage = page.getNextPage();
		System.out.println("getCurrentPage="+page.getCurrentPage());
		list = page.getPage(currPage);
		System.out.println(list);
		currPage = page.getNextPage();
		System.out.println("getCurrentPage="+page.getCurrentPage());
		list = page.getPage(currPage);
		System.out.println(list);
	}
}
