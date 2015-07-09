package rt.test;

import java.util.ArrayList;
import java.util.List;

public class ListSplit {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < 101; i++) {
			list.add(i);
		}

		int pagesize = 20;
		int totalcount = list.size();

		int totalPage = 0;

		int m = totalcount % pagesize;

		if (m > 0) {
			totalPage = totalcount / pagesize + 1;
		} else {
			totalPage = totalcount / pagesize;
		}

		for (int i = 1; i <= totalPage; i++) {

			if (m == 0) {
				List<Integer> subList = list.subList((i - 1) * pagesize,
				pagesize * (i));
				System.out.println("1="+subList);
			} else {
				if (i == totalPage) {
					List<Integer> subList = list.subList((i - 1) * pagesize,
							totalcount);
					System.out.println("2="+subList);
				} else {
					List<Integer> subList = list.subList((i - 1) * pagesize,
							pagesize * (i));
					System.out.println("3="+subList);
				}
			}
		}
	}
}