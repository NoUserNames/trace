/**
 * 
 */
package rt.action.sms;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import rt.dao.impl.ISMSDAOImpl;
import rt.dao.interfaces.ISMSDAO;
import rt.util.ReadWriteTXT;
import rt.util.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 短信内容相关操作
 * @author 张强
 *
 */
public class SMSAction extends ActionSupport {

	private static final long serialVersionUID = -1318283748799812429L;
	
	private static Logger log = Logger.getLogger(SMSAction.class);
	
	protected ISMSDAO iSMSdao;
	
	private JSONArray json;
	private JSONArray cg;
	
	private String reveice;
	private String reveice_name;
	private String message;
	
	private String gp;
	private String group_id;
	String error;
	
	private String g_member_hidden;
	private String selected_member_hidden;
	
	private String upload;

	public String initSMS(){
		iSMSdao = new ISMSDAOImpl();
		List<LinkedHashMap<String,Object>> listContacts = iSMSdao.queryContacts(0,0,false,0,"0");
		List<LinkedHashMap<String,Object>> listContactGroups = iSMSdao.queryContactGroup();
		json = JSONArray.fromObject(listContacts);
		cg = JSONArray.fromObject(listContactGroups);
		return SUCCESS;
	}

	public void sms() {
		iSMSdao = new ISMSDAOImpl();

		String sender = Struts2Utils.getSession().getAttribute("username").toString();
		sender = sender.substring(0, sender.indexOf(","));
		String[] reveiceName = null;
		String[] reveiceNum = null;
		String[] reveiceInfo = null;
		String flag = "N";
		if(gp.equals("div_p")){
			reveiceName = reveice_name.split(",");
			reveiceNum = reveice.split(",");
		}
		if(gp.equals("div_g")){
			reveiceInfo = selected_member_hidden.length()==0 ? g_member_hidden.split(";") : selected_member_hidden.split(";");
			reveiceName = new String[reveiceInfo.length];
			reveiceNum = new String[reveiceInfo.length];
			for(int i = 0;i<reveiceInfo.length; i++){
				reveiceName[i] = reveiceInfo[i].substring(0,reveiceInfo[i].lastIndexOf(","));
				reveiceNum[i] = reveiceInfo[i].substring(reveiceInfo[i].lastIndexOf(",")+1);
			}
		}
		if(gp.equals("div_other")){
			System.out.println("div_other"+message+"\tupload="+upload);
			flag = "H";
			upload = upload.substring(upload.lastIndexOf("\\")+1);
			String filePath = ServletActionContext.getServletContext().getRealPath("/")+"upload"+"\\"+upload;
			XSSFWorkbook wb = null;
			
			try {
				wb = new XSSFWorkbook(filePath);
				
				XSSFSheet sheet = wb.getSheetAt(0);
				int totalRows = sheet.getLastRowNum();
				List<String> cellphone = new ArrayList<String>();
				String appendNum = "";
				reveiceNum = new String[totalRows];
				reveiceName = new String[totalRows];
				for(int i = 1; i<= totalRows;i++){
					DataFormatter df = new DataFormatter();
					df.addFormat("###########", null);
					String name = df.formatCellValue(sheet.getRow(i).getCell(0));
					String num = df.formatCellValue(sheet.getRow(i).getCell(1));
					cellphone.add(num);
					appendNum += num + "\r\n";
					reveiceNum[i-1] = num;
					reveiceName[i-1] = name;
				}
				ReadWriteTXT write = new ReadWriteTXT();
				
				write.writeTxtFile(new File("c:/sms/1.txt"), appendNum);
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			} finally {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			boolean s_result = iSMSdao.sendSMS(reveiceName, reveiceNum, message, sender, flag);
			System.out.println("flag="+flag);
			if(flag.equals("H")){
				System.out.println("上传白名单");
				Runtime.getRuntime().exec("D:/UPLOADWHITE/Check.exe");
			}
			Struts2Utils.getResponse().getWriter().write(s_result+"");
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public void queryContactsByGroup(){
		iSMSdao = new ISMSDAOImpl();
		try {
			List<HashMap<String,Object>> listmap = iSMSdao.queryContactsByGroup(group_id);
			JSONArray json = JSONArray.fromObject(listmap);
			Struts2Utils.getResponse().getWriter().write(json.toString());
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public String initHRSms(){
		return SUCCESS;
	}
	
	/*******************************公共属性********************************/
	public JSONArray getJson() {
		return json;
	}
	
	public void setJson(JSONArray json) {
		this.json = json;
	}

	public String getReveice() {
		return reveice;
	}

	public void setReveice(String reveice) {
		this.reveice = reveice;
	}

	public String getReveice_name() {
		return reveice_name;
	}

	public void setReveice_name(String reveice_name) {
		this.reveice_name = reveice_name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JSONArray getCg() {
		return cg;
	}

	public void setCg(JSONArray cg) {
		this.cg = cg;
	}

	public String getGp() {
		return gp;
	}

	public void setGp(String gp) {
		this.gp = gp;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getG_member_hidden() {
		return g_member_hidden;
	}

	public void setG_member_hidden(String g_member_hidden) {
		this.g_member_hidden = g_member_hidden;
	}

	public String getSelected_member_hidden() {
		return selected_member_hidden;
	}

	public void setSelected_member_hidden(String selected_member_hidden) {
		this.selected_member_hidden = selected_member_hidden;
	}

	public String getUpload() {
		return upload;
	}

	public void setUpload(String upload) {
		this.upload = upload;
	}
	
}