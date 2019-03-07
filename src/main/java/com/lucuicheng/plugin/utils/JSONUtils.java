package com.lucuicheng.plugin.utils;

import net.sf.json.JSONObject;

import java.io.*;

/**
 * 
 * <p class="detail">
 * 前端json工具类
 * </p>
 *
 * @ClassName: JSONUtils
 * @version V1.0  @date 2015年12月9日 下午10:11:56 
 * @author <a href="mailto:lucuicheng@gmail.com ">陆崔程</a>                             
 *
 */
public class JSONUtils {

	/**
	 * 
	 * <p class="detail">
	 * 读取测试使用的json文件
	 * </p>
	 * @author <a href="mailto:lucuicheng@gmail.com ">陆崔程</a> 2015年12月9日 下午10:11:37
	 * @param file
	 * @return
	 */
	public static String readJsonFile(File file) {
		BufferedReader reader = null;
		String laststr = "";
		try{
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while((tempString = reader.readLine()) != null){
			laststr += tempString;
		}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		} finally{
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}

	public static JSONObject toJsonObj(File file) {
		if(file != null) {
			return JSONObject.fromObject(JSONUtils.readJsonFile(file));
		}
		return null;
	};
}
