/**
 * 
 */
package rt.pojo;

/**
 * @author Administrator х╗оч
 */
public class Node {
	private String id;
	private String nodename;
	private String nodedesc;
	private long createdate;
	private String nodeurl;
	private String pid;
	private int enable;
	private String title;
	private String target;
	private String rel;
	private String icon;
	private String iconOpen;
	private String external;

	public String getPid() {
		return pid;
	}

	public String getExternal() {
		return external;
	}

	public void setExternal(String external) {
		this.external = external;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	private String state;

	// private List<Node> children = new ArrayList<Node>();

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getNodedesc() {
		return nodedesc;
	}

	public void setNodedesc(String nodedesc) {
		this.nodedesc = nodedesc;
	}

	public long getCreatedate() {
		return createdate;
	}

	public void setCreatedate(long createdate) {
		this.createdate = createdate;
	}

	public String getNodeurl() {
		return nodeurl;
	}

	public void setNodeurl(String nodeurl) {
		this.nodeurl = nodeurl;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public String getTitle() {
		return title;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}
}
