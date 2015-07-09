package rt.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import rt.dao.ICustomizeDAO;
import rt.dao.ICustomizeDAOImpl;
import rt.dao.impl.ISysLogImpl;
import rt.dao.interfaces.ISysLog;
import rt.excel.PoiExcel2k3Helper;
import rt.excel.PoiExcelHelper;
import rt.pojo.LogTraceMaintain;
import rt.util.Struts2Utils;
import rt.util.TUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 维护WIP状态
 * @author 张强
 *
 */
public class MaintainWIPAction extends ActionSupport {

	private static Logger log = Logger.getLogger(MaintainWIPAction.class);

	private static final long serialVersionUID = 3932175013193301113L;

	private File upload;
	private String function;
    private String uploadFileName;
	private String utype;
	private String serial_number;//启用/禁用
	private String serial_status;//查状态
	private String enabled;
	private String result;

	private ICustomizeDAO customizeDAO;

	public String initMaintainWIP(){
		return SUCCESS;
	}

	public void maintainWIP() throws IOException{
		function = function.equals("启用") ? "" : "N";
		String serverPath = null;
		ArrayList<ArrayList<String>> dataList = null;
		if("one".equals(utype)){
			log.info("维护单片");
			ArrayList<ArrayList<String>> array1 = new ArrayList<ArrayList<String>>();
			ArrayList<String> array2 = new ArrayList<String>();
			array2.add(serial_number);
			array1.add(array2);
			dataList = array1;
		} else {
			log.info("维护批量");
			//先把文件上传到服务器
			serverPath = ServletActionContext.getServletContext().getRealPath("/")+"upload"+"\\";
			File savefile = new File(serverPath,uploadFileName);

			FileUtils.copyFile(upload, savefile);
			//从服务器读取文件，解析
			PoiExcelHelper helper = new PoiExcel2k3Helper();
			// 读取A两列数据
			dataList = helper.readExcel(serverPath+uploadFileName, 0, new String[]{"a"});
		}
		customizeDAO = new ICustomizeDAOImpl();
		boolean bool =customizeDAO.MaintainWIP(dataList,function);
		
		function = function.equals("") ? "启用" :"禁用";
		
		ISysLog sysLog = new ISysLogImpl();
		LogTraceMaintain logTraceMaintain = new LogTraceMaintain();
		String sender = Struts2Utils.getSession().getAttribute("username").toString();
		sender = sender.substring(0, sender.indexOf(","));
		logTraceMaintain.setLogId(TUtil.format("yyyyMMddHHmmssssss"));
		logTraceMaintain.setAction(function);
		logTraceMaintain.setEmpNO(sender);

		sysLog.log(logTraceMaintain, dataList);
		
		log.info(Struts2Utils.getSession().getAttribute("username").toString()+function+dataList);
		File file = new File(serverPath+uploadFileName);
		if(file.exists())
			file.delete();
		if(bool){
			Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"message\":\"状态修改成功。\"}");
		}else
			Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"300\",\"message\":\"状态修改失败！\"}");
	}

	public String qryStatus(){
		customizeDAO = new ICustomizeDAOImpl();
		enabled = customizeDAO.qryStatus(serial_status);
		return SUCCESS;
	}
	
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	/***************公共属性**************/

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}

	public String getSerial_status() {
		return serial_status;
	}

	public void setSerial_status(String serial_status) {
		this.serial_status = serial_status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}