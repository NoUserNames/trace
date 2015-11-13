package rt.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import rt.mail.MailUtils;
import rt.pojo.SysPart;

/**
 * 基础类
 * 
 * @author Qiang1_Zhang
 */
public class TUtil {
	static Logger log = Logger.getLogger(TUtil.class);

	// static{
	// System.loadLibrary("getTime");
	// }
	// native static String GetOneFileTime(String string);
	public static void main(String[] a) {
		List<Object> c = new ArrayList<Object>();
		c.add(1001);
		c.add(100101);
		System.out.println(new TUtil().removeDuplicate(c));
	}

	/**
	 * 日期转换函数
	 * 
	 * @param format
	 *            需要转换的格式
	 * @return 转换后的日期
	 */
	public static String format(String format) {

		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * 日期转换函数
	 * 
	 * @param format
	 *            需要转换的格式
	 * @return 转换后的日期
	 */
	public static String format(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 打印函数
	 * 
	 * @param str
	 *            对象类型
	 */
	public static void print(Object str) {
		System.out.println(str);
	}

	/**
	 * 计算距今指定天数的日期
	 * 
	 * @param day
	 *            相差的天数，可为负数
	 * @return 计算之后的日期
	 */
	public static String GetDay(int day) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(new Date());// 设置日历时间
		cal.add(Calendar.DAY_OF_MONTH, day);// 天数
		String strDate = sdf.format(cal.getTime());// 得到你想要的天数

		return strDate;
	}

	/**
	 * 获取报表模板路径
	 * 
	 * @return
	 */
	public static String getURL() {
		String dir = System.getProperty("user.dir");
		print("dir=" + dir);
		dir = dir.substring(0, dir.lastIndexOf("\\"));
		String filePath = dir;
		return filePath;
	}

	/**
	 * String类型日期转换为长整型
	 * 
	 * @param date
	 *            String类型日期
	 * @param format
	 *            日期格式
	 * @return long
	 */
	public static long strDateToLong(String date, String... format) {
		String format1 = null;
		if (format.length != 0) {
			format1 = format[0];
		} else {
			format1 = "yyyy-MM-dd HH:mm:ss";
		}
		String sDt = date;
		SimpleDateFormat sdf = new SimpleDateFormat(format1);
		long lTime = 0;
		try {
			Date dt2 = sdf.parse(sDt);
			lTime = dt2.getTime();
			print(lTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lTime;
	}

	public static void longToString(long l) {
		format("");
	}

	public static void sendFaultMail(String reprot) {
		MailUtils mail = new MailUtils();
		mail.setTo(ReadProperties.ReadProprety("mail.fault.to"));
		mail.setSubject(reprot + " 發送失敗");
		mail.setContent("警告,報表： " + reprot + " 發送失敗！");
		mail.sendMail();
	}

	/**
	 * 設置郵件正文
	 * 
	 * @param subject
	 *            主題
	 * @param source
	 *            報表目錄
	 * @return 郵件正文
	 */
	public static String getContent(String subject, String source) {
		String fileName = source.substring(source.lastIndexOf("/") + 1);
		fileName = fileName.substring(0, fileName.indexOf("."));
		String dest = ReadProperties.ReadProprety("server.report.path")
				+ fileName + "/" + TUtil.format("yyyy-MM-dd") + ".xls";
		String content = "Dear All:\n\t這是"
				+ subject
				+ "，\n\t如您使用WINDOWS系統，請點擊後面鏈接獲取報表："
				+ dest
				+ "\n\t如您使用MAC系統，請到上述路徑下查閱此報表！\n\n注：該郵件由系統自動發送，請勿回復！\nTracebility 系統";
		return content;
	}

	/**
	 * 获取文件创建时间
	 * 
	 * @param file
	 *            文件目录
	 */
	public static String getCreateTime(File file) {
		// file = new File("e:/1.xls");
		String date = "";
		// file.lastModified();
		try {
			Process process = Runtime.getRuntime().exec(
					"cmd.exe /c dir " + file.getAbsolutePath() + "/tc");
			InputStream is = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (int i = 0; i < 5; i++) {// 前五行是其他的信息
				br.readLine();
			}
			String createDateLine = br.readLine();
			StringTokenizer tokenizer = new StringTokenizer(createDateLine);
			date = tokenizer.nextToken() + " " + tokenizer.nextToken();
			br.close();
			// print(date);
		} catch (IOException e) {
			log.error("" + e.getMessage());
		}
		return date;
	}

	/**
	 * 获取文件最后修改时间
	 * 
	 * @param filePath
	 *            文件目录
	 */
	public static void getLastModifyTime(File filePath) {
		filePath = new File(
				"\\\\10.131.18.8\\rt3生產機種\\ProductionReprot\\TraceAlterReprot-reprot");
		File[] list = filePath.listFiles();
		// for(File file : list){
		// print(file.getAbsolutePath()+"\tcreate time:"+getCreateTime(file));
		// }
		for (File file : list) {
			Date date = new Date(file.lastModified());
			print(format(date, "yyyy-MM-dd"));
		}
	}

	public static void getFile() {
		String root = "\\\\10.131.18.8\\rt3生產機種\\ProductionReprot";
		File filePath = new File(root);
		File[] list = filePath.listFiles();
		for (File file : list) {
			print(file.getName()
					+ "\t"
					+ new File(file.getAbsolutePath() + "\\"
							+ TUtil.format("yyyy-MM-dd") + ".xls").exists());
		}
	}

	static void test() {
		String today = TUtil.format("yyyy-MM-dd");
		String dest = ReadProperties.ReadProprety("server.report.path")
				+ "TraceAlterReprot-reprot" + "\\" + today + "\\";
		print(dest);
		File dir = new File(dest);// 创建当天目录
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

	public static void getTimeDifference() {
		try {
			Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2014-09-15");

			Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2014-09-14");
			print((d2.getTime() - d1.getTime()) / 1000 / 60 / 60 / 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	//去除两个List中重复值
	public List<Object> duplicate2List(){
		List<Integer> l1 = new ArrayList<Integer>();
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(4);
		l1.add(5);
		l1.add(6);
		List<Integer> l2 = new ArrayList<Integer>();
		l2.add(4);
		l2.add(5);
		l2.add(6);
		l2.add(7);
		l2.add(8);
		l2.add(9);

		List<Integer> temp = new ArrayList<Integer>(l1);// 用来保存两者共同有的数据
		temp.retainAll(l2);
		l1.removeAll(temp);// l1中去掉两者共同有的数据
		l2.removeAll(temp);// l2中去掉两者共同有的数据

		List<Integer> l3 = new ArrayList<Integer>();
		l3.addAll(l1);
		l3.addAll(l2);
		System.out.println(l3);
	}

	public List<String> distinctList(List<String> list) {
		HashSet<String> h = new HashSet<String>(list);
		list.clear();
		list.addAll(h);
		return list;
	}
	
	public List<Object> removeDuplicate(List<Object> list) {
		HashSet<Object> h = new HashSet<Object>(list);
		list.clear();
		list.addAll(h);
		return list;
	}
	
	/**
	 * 获取WIP查询的料号，供查WIP明细使用
	 * @param sysPart
	 * @return
	 */
	public SysPart getPartNO(SysPart sysPart){
		return sysPart;
	}
	
	/**
	 * 获取四舍五入的整数
	 * @param input 乘数
	 * @param rate 比率
	 * @return 取整后的结果
	 */
	public double getRound(int input,double rate){
		double tmp = input * rate;
		return Math.round(tmp);
	}
	
	/**
	 * 获取四舍五入的整数
	 * @param input 乘数
	 * @param rate 比率
	 * @return 取整后的结果
	 */
	public double ceil(int input,double rate){
		double tmp = input * rate;
		return Math.ceil(tmp);
	}
}
