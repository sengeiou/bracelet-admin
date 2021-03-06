package com.jadmin.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

	/**
	 * 随机生成六位数验证码 
	 * @return
	 */
	public static int getRandomNum() {
		Random r = new Random();
		return r.nextInt(900000) + 100000;
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @param str	字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (notEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		} else {
			return null;
		}
	}
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 按照参数format的格式，日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 把时间根据时、分、秒转换为时间段
	 * @param StrDate
	 */
	public static String getTimes(String StrDate) {
		String resultTimes = "";

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date now;

		try {
			now = new Date();
			java.util.Date date = df.parse(StrDate);
			long times = now.getTime() - date.getTime();
			long day = times / (24 * 60 * 60 * 1000);
			long hour = (times / (60 * 60 * 1000) - day * 24);
			long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

			StringBuffer sb = new StringBuffer();
			//sb.append("发表于：");
			if (hour > 0) {
				sb.append(hour + "小时前");
			} else if (min > 0) {
				sb.append(min + "分钟前");
			} else {
				sb.append(sec + "秒前");
			}

			resultTimes = sb.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return resultTimes;
	}

	/**
	 * 写txt里的单行内容
	 * @param filePath  文件路径
	 * @param content  写入的内容
	 */
	public static void writeFile(String fileP, String content) {
		String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../"; //项目路径
		filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
		if (filePath.indexOf(":") != 1) {
			filePath = File.separator + filePath;
		}
		BufferedWriter writer = null;
		try {
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
			writer = new BufferedWriter(write);
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			CloseableUtil.close(writer);
		}
	}
	/**
	   * 验证EXCEL文件
	   * @param filePath
	   * @return
	   */
	  public static boolean validateExcel(String filePath){
	        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){  
	            return false;  
	        }  
	        return true;
	  }
	  // @描述：是否是2003的excel，返回true是2003 
	    public static boolean isExcel2003(String filePath)  {  
	         return filePath.matches("^.+\\.(?i)(xls)$");  
	     }  
	   
	    //@描述：是否是2007的excel，返回true是2007 
	    public static boolean isExcel2007(String filePath)  {  
	         return filePath.matches("^.+\\.(?i)(xlsx)$");  
	     }  
	    
	/**
	  * 验证邮箱
	  * @param email
	  * @return
	  */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 读取txt里的单行内容
	 * @param filePath  文件路径
	 */
	public static String readTxtFile(String fileP) {
		try {
			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../"; //项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if (filePath.indexOf(":") != 1) {
				filePath = File.separator + filePath;
			}
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				BufferedReader bufferedReader = null;
				try {
					InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // 考虑到编码格式
					bufferedReader = new BufferedReader(read);
					String lineTxt = null;
					while ((lineTxt = bufferedReader.readLine()) != null) {
						return lineTxt;
					}
				} finally {
					CloseableUtil.close(bufferedReader);
				}
			} else {
				System.out.println("找不到指定的文件,查看此路径是否正确:" + filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String numberPer(double d1, double d2) {
		double percent = d1 / d2;
		NumberFormat nt = NumberFormat.getPercentInstance();
		nt.setMinimumFractionDigits(2);
		return nt.format(percent);
	}

	public static String numberDouble(double d1, double d2) {
		double percent = (d1 / d2) * 100;
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数   
		String s = df.format(percent);//返回的是String类型 
		return s;
		//		    return 100 * Double.valueOf(s)+"";
	}

	public static void main(String[] args) {
		System.out.println(numberDouble(18, 293));
	}

	/**
	 * 将string的字符串转换成BigDecimal型
	 * @param price
	 * @return
	 */
	/**
	 * 小数点设置长度4位
	 */
	public static int smallfourpoint = 4;
	/**
	 * 小数点设置长度1位
	 */
	public static int smallonepoint = 1;

	public static BigDecimal getBigDecimalValueByString(String price) {
		BigDecimal newPrice = null;
		if (price == null || "".equals(price)) {
			return null;
		}

		newPrice = new BigDecimal(price);

		if (price.contains(".") && !(price.split("\\.")[1].equals("0"))) {
			//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
			newPrice = newPrice.setScale(smallfourpoint, BigDecimal.ROUND_HALF_UP);
		} else {
			//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
			newPrice = newPrice.setScale(smallonepoint, BigDecimal.ROUND_HALF_UP);
		}
		return newPrice;
	}

	/**
	 * 根据前端页面传入的日期参数判断是否是今天以内的日期
	 * @param dateSource (例如：String dateSource = "2016-09-28 14:42:02";)
	 * @return
	 */
	public static boolean compDateByDate(String dateSource) {
		boolean flag = false;
		try {
			Calendar now = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(dateSource);
			if (now.get(Calendar.YEAR) == (date.getYear() + 1900) && (now.get(Calendar.MONTH) + 1) == (date.getMonth() + 1)
					&& now.get(Calendar.DAY_OF_MONTH) == date.getDate()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 90mmHg<收缩压<140mmHg
	 * 60mmHg<舒张压<90mmHg
	 */
	public static List<Map<String, Object>> checkHeartPressure(Integer maxHeartPressure, Integer minHeartPressure) {
		List<Map<String, Object>> resultList = new LinkedList<Map<String, Object>>();
		Map<String, Object> maxResultMap = new HashMap<>();
		if (maxHeartPressure < 90) {
			maxResultMap.put("type", 1);
			maxResultMap.put("code", 2);
			maxResultMap.put("msg", "偏低");
		} else if (maxHeartPressure > 140) {
			maxResultMap.put("type", 1);
			maxResultMap.put("code", 1);
			maxResultMap.put("msg", "偏高");
		} else {
			maxResultMap.put("type", 1);
			maxResultMap.put("code", 0);
			maxResultMap.put("msg", "正常");
		}
		Map<String, Object> minResultMap = new HashMap<>();
		if (minHeartPressure < 60) {
			minResultMap.put("type", 2);
			minResultMap.put("code", 2);
			minResultMap.put("msg", "偏低");
		} else if (minHeartPressure > 90) {
			minResultMap.put("type", 2);
			minResultMap.put("code", 1);
			minResultMap.put("msg", "偏高");
		} else {
			minResultMap.put("type", 2);
			minResultMap.put("code", 0);
			minResultMap.put("msg", "正常");
		}
		resultList.add(maxResultMap);
		resultList.add(minResultMap);
		return resultList;
	}

	/**
	 * 60～100次/分
	 */
	public static Map<String, Object> checkHeartRate(Integer heartRate) {
		Map<String, Object> resultMap = new HashMap<>();
		if (heartRate < 60) {
			resultMap.put("code", 2);
			resultMap.put("msg", "偏慢");
		} else if (heartRate > 100) {
			resultMap.put("code", 1);
			resultMap.put("msg", "偏快");
		} else {
			resultMap.put("code", 0);
			resultMap.put("msg", "正常");
		}
		return resultMap;
	}

	// 根据UnicodeBlock方法判断中文标点符号
		public static boolean isChinesePunctuation(char c) {
		    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		    if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
		            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
		            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
		            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
		            || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
		        return true;
		    } else {
		        return false;
		    }
		}
}
