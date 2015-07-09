package rt.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import rt.util.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -205046878445719313L;

	private static Logger log = Logger.getLogger(UploadAction.class);
	private String uploadFileName;
	private File upload;
	
	public void doUpload(){
		String serverPath = null;
		serverPath = ServletActionContext.getServletContext().getRealPath("/")+"//upload"+"//";
		File savefile = new File(serverPath,uploadFileName);
		try {System.out.println(upload.getAbsolutePath());
			FileUtils.copyFile(upload, savefile);
			Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"200\",\"message\":\"上传成功\",\"navTabId\":\"\",\"rel\":\"\",\"callbackType\":\"closeCurrent\",\"forwardUrl\":\"\"}");
		} catch (IOException e) {
			try {
				Struts2Utils.getResponse().getWriter().write("{\"statusCode\":\"301\",\"message\":\"文件上传失败，请重试。\",\"navTabId\":\"\",\"rel\":\"\",\"callbackType\":\"closeCurrent\",\"forwardUrl\":\"\"}");
			} catch (IOException e1) {
				log.error(e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
//		return SUCCESS;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	
}