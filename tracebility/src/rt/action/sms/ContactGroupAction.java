/**
 * 
 */
package rt.action.sms;

import java.io.IOException;

import rt.dao.impl.ISMSDAOImpl;
import rt.dao.interfaces.ISMSDAO;
import rt.pojo.SMSContactGroup;
import rt.util.Struts2Utils;
import rt.util.TUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 短信模块-通讯群组功能
 * @author 张强
 *
 */
public class ContactGroupAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5265237997765411889L;

	protected ISMSDAO iSMSdao;
	
	private String groupId;
	private String groupName;
	private String groupDesc;
	
	private SMSContactGroup smsContactGroup;
	
	public String initContactGroup(){
		return SUCCESS;
	}
	
	public void contactGroupAdd(){
		iSMSdao = new ISMSDAOImpl();
		SMSContactGroup smsContactGroup = new SMSContactGroup();
		smsContactGroup.setGROUP_ID(TUtil.format("yyyyMMddHHmmssssss"));
		smsContactGroup.setGROUP_NAME(groupName);
		smsContactGroup.setGROUP_DESC(groupDesc);
		try{
			if(iSMSdao.addContactGroup(smsContactGroup))
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"message\":\"群组添加成功\",\"navTabId\":\"\",\"rel\":\"\",\"callbackType\":\"closeCurrent\",\"forwardUrl\":\"\",\"confirmMsg\":\"\"}");
			else
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\"群组添加失败，请重试。\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void delContactGroup(){
		iSMSdao = new ISMSDAOImpl();
		try{
			if(iSMSdao.delContactGroup(groupId))
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\", \"message\":\"群组删除成功。\"}");
			else
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\"群组删除失败，请重试。\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String contactEditQuery(){
		iSMSdao = new ISMSDAOImpl();
		smsContactGroup = iSMSdao.contactGroupEditQuery(groupId);
		return SUCCESS;
	}
	
	public void updateContactGroup(){
		iSMSdao = new ISMSDAOImpl();
		smsContactGroup = new SMSContactGroup();
		smsContactGroup.setGROUP_ID(groupId);
		smsContactGroup.setGROUP_NAME(groupName);
		smsContactGroup.setGROUP_DESC(groupDesc);
		try{
			if(iSMSdao.updateContactGroup(smsContactGroup))
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"message\":\"修改成功\",\"navTabId\":\"\",\"rel\":\"\",\"callbackType\":\"closeCurrent\",\"forwardUrl\":\"\",\"confirmMsg\":\"\"}");
			else
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\"群组更新失败，请重试。\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*************        公共属性           *************/
	
	public String getGroupId() {
		return groupId;
	}
	
	public SMSContactGroup getSmsContactGroup() {
		return smsContactGroup;
	}

	public void setSmsContactGroup(SMSContactGroup smsContactGroup) {
		this.smsContactGroup = smsContactGroup;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getGroupDesc() {
		return groupDesc;
	}
	
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	
}
