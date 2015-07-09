package rt.dao.interfaces;

import java.util.ArrayList;

import rt.pojo.LogTraceMaintain;

public interface ISysLog {

	/**
	 * 标识WIP状态/权限操作记录数据操作层
	 * @param logTraceMaintain
	 * @return
	 */
	public boolean log(LogTraceMaintain logTraceMaintain, ArrayList<ArrayList<String>> dataList);
}
