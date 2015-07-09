/**
 * 
 */
package rt.pojo;

/**
 * @author Administrator
 *
 */
public class UserInfo {

	private String uid;
	private String uname;
	private String upwd;
	public String udisplayName;
	private int enable;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUpwd() {
		return upwd;
	}
	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}
	public String getUdisplayName() {
		return udisplayName;
	}
	public void setUdisplayName(String udisplayName) {
		this.udisplayName = udisplayName;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}

}