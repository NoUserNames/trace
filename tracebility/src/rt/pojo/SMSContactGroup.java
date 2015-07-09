/**
 * 
 */
package rt.pojo;

import java.util.Date;

/**
 * 短信功能-通讯录群组数据模型
 * @author 张强
 *
 */
public class SMSContactGroup {
	private String GROUP_ID;
	private String GROUP_NAME;
	private String GROUP_DESC;
	private Date CREATE_TIME;
	
	public String getGROUP_ID() {
		return GROUP_ID;
	}
	public void setGROUP_ID(String gROUP_ID) {
		GROUP_ID = gROUP_ID;
	}
	public String getGROUP_NAME() {
		return GROUP_NAME;
	}
	public void setGROUP_NAME(String gROUP_NAME) {
		GROUP_NAME = gROUP_NAME;
	}
	public String getGROUP_DESC() {
		return GROUP_DESC;
	}
	public void setGROUP_DESC(String gROUP_DESC) {
		GROUP_DESC = gROUP_DESC;
	}
	public Date getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(Date cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
}
