/**
 * 
 */
package rt.pojo;

/**
 * 管理个人权限专用实体类
 * @author Administrator
 */
public class NodeTree {
	private String id;
	private String text;
//	private String state;
	private String pid;
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
//	public String getState() {
//		return state;
//	}
//	public void setState(String state) {
//		this.state = state;
//	}
}
