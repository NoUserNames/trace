package rt.action.sms;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import rt.dao.impl.ISMSDAOImpl;
import rt.dao.interfaces.ISMSDAO;
import rt.pojo.SMSContact;
import rt.pojo.SMSContactExternalGroup;
import rt.pojo.SMSContactGroup;
import rt.util.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 短信模块-通讯录功能
 * @author 张强
 *
 */
public class ContactAction extends ActionSupport {

	private static final long serialVersionUID = -5539951882803996013L;

	protected ISMSDAO iSMSdao;
	
	private String emp_name;
	private String emp_no;
	private String mobile_number;
	private int factory_id;
	private String sms_group;
	
	private int numPerPage;
	private int totalCount;
	private int pageNumShown;
	private int pageNum;
	
	private String emp_no_edit;
	private LinkedHashMap<String,Object> contact_edit;
	private String sms_group_old;
	private String chkFlag;
	
//	private int d_type;
	
	private List<LinkedHashMap<String,Object>> groups;
	private List<LinkedHashMap<String,Object>> listContacts;
	
	public String initGroup(){
		iSMSdao = new ISMSDAOImpl();
		groups = iSMSdao.queryContactGroup();
		return SUCCESS;
	}
	
	public String initAddGroup(){
		iSMSdao = new ISMSDAOImpl();
		groups = iSMSdao.queryContactGroup();
		return SUCCESS;
	}
	
	public String contact_edit_query(){
		iSMSdao = new ISMSDAOImpl();
		groups = iSMSdao.queryContactGroup();
		contact_edit = iSMSdao.queryContacts(emp_no_edit);
		return SUCCESS;
	}
	
	public void contact_edit(){
		iSMSdao = new ISMSDAOImpl();
		SMSContactExternalGroup smsctg = new SMSContactExternalGroup();
		SMSContact smsContact = new SMSContact();
		smsContact.setEmp_name(emp_name);
		smsContact.setEmp_no(emp_no.toUpperCase());
		smsContact.setMobile_number(mobile_number);
		smsContact.setFactory_id(factory_id);
		
		SMSContactGroup smscg = new SMSContactGroup();
		smscg.setGROUP_ID(sms_group);
		smsctg.setSmsContact(smsContact);
		smsctg.setSmsContactGroup(smscg);

		try {
			if(iSMSdao.editContact(smsctg,sms_group_old))
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"message\":\"修改成功\",\"navTabId\":\"\",\"rel\":\"\",\"callbackType\":\"closeCurrent\",\"forwardUrl\":\"\",\"confirmMsg\":\"\"}");
			else
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\"修改操作失败，请重试。\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String initContact(){
		iSMSdao = new ISMSDAOImpl();
		groups = iSMSdao.queryContactGroup();
		pageNum = pageNum < 1 ? 1 : pageNum;
		numPerPage = numPerPage < 1 ? 20 : numPerPage;
		sms_group = (sms_group != null ? sms_group : "0");
		listContacts = iSMSdao.queryContacts(numPerPage, pageNum, true, factory_id, sms_group);
		totalCount = iSMSdao.getTotalCount("sj.sms_contact");
		pageNumShown = totalCount % numPerPage == 0 ? totalCount / numPerPage : totalCount / numPerPage + 1;
		return SUCCESS;
	}

	public void addContact() throws IOException{
		iSMSdao = new ISMSDAOImpl();
		SMSContact smsContact = new SMSContact();
		smsContact.setEmp_name(emp_name);
		smsContact.setEmp_no(emp_no.toUpperCase());
		smsContact.setMobile_number(mobile_number);
		smsContact.setFactory_id(factory_id);
		SMSContactExternalGroup smsctg = new SMSContactExternalGroup();
		
		SMSContactGroup smsContactGroup = new SMSContactGroup();
		smsContactGroup.setGROUP_ID(sms_group);
		smsctg.setSmsContactGroup(smsContactGroup);
		smsctg.setSmsContact(smsContact);
		if(iSMSdao.addContact(smsctg)){
			Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"message\":\"成功\",\"navTabId\":\"\",\"rel\":\"\",\"callbackType\":\"closeCurrent\",\"forwardUrl\":\"\",\"confirmMsg\":\"\"}");
		} else{
			Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\"新增操作失败，请重试。\"}");
		}
	}
	
	public void delContact(){
		iSMSdao = new ISMSDAOImpl();
		try {
			if(iSMSdao.delContact(chkFlag.split(",")))
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\", \"message\":\"删除通讯录成功。\"}");
			else
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\", \"message\":\"删除操作失败，请重试。\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/***********************公共属性区域**************************/
	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public int getFactory_id() {
		return factory_id;
	}

	public void setFactory_id(int factory_id) {
		this.factory_id = factory_id;
	}

	public List<LinkedHashMap<String, Object>> getGroups() {
		return groups;
	}

	public void setGroups(List<LinkedHashMap<String, Object>> groups) {
		this.groups = groups;
	}

	public List<LinkedHashMap<String, Object>> getListContacts() {
		return listContacts;
	}

	public void setListContacts(List<LinkedHashMap<String, Object>> listContacts) {
		this.listContacts = listContacts;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageNumShown() {
		return pageNumShown;
	}

	public void setPageNumShown(int pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getSms_group() {
		return sms_group;
	}

	public void setSms_group(String sms_group) {
		this.sms_group = sms_group;
	}

	public String getChkFlag() {
		return chkFlag;
	}

	public void setChkFlag(String chkFlag) {
		this.chkFlag = chkFlag;
	}

	public String getEmp_no_edit() {
		return emp_no_edit;
	}

	public void setEmp_no_edit(String emp_no_edit) {
		this.emp_no_edit = emp_no_edit;
	}

	public LinkedHashMap<String, Object> getContact_edit() {
		return contact_edit;
	}

	public void setContact_edit(LinkedHashMap<String, Object> contact_edit) {
		this.contact_edit = contact_edit;
	}

	public String getSms_group_old() {
		return sms_group_old;
	}

	public void setSms_group_old(String sms_group_old) {
		this.sms_group_old = sms_group_old;
	}
//
//	public int getD_type() {
//		return d_type;
//	}
//
//	public void setD_type(int d_type) {
//		this.d_type = d_type;
//	}

}
