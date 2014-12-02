package com.zzc.luqinwenda.dal;

import android.R.integer;
import android.util.Log;

import com.zzc.luqinwenda.app.LoginActivity;
import com.zzc.luqinwenda.app.RegistActivity;
import com.zzc.luqinwenda.http.BasicHttpClient;
import com.zzc.luqinwenda.util.EncryptUtil;

public class DataService {

	private final static String KEY = "weixin_web_md5_lq";
	// java的时间戳13位,php的10位
	private String timeStr = String.valueOf(System.currentTimeMillis())
			.substring(0, 10);

	public String getData(String p, String psize) {
		String url = "http://192.168.1.133/index.php?app=public&mod=api&act=getallfeed&p="
				+ p + "&psize=" + psize;
		String dataContent = new BasicHttpClient().httpGet(url);
		return dataContent;
	}

	/**
	 * 登录
	 * 
	 * @param login
	 * @param password
	 * @return 用户信息
	 */
	public String Login(String login, String password) {
		String url = "http://192.168.1.133/index.php?app=public&mod=api&act=userlogin&login="
				+ login
				+ "&pwd="
				+ password
				+ "&time="
				+ timeStr
				+ "&code="
				+ EncryptUtil.string2MD5(login + password + timeStr + KEY);
		String dataContent = new BasicHttpClient().httpGet(url);
		return dataContent;
	}

	/**
	 * 注册
	 * 
	 * @param login
	 * @param password
	 * @param uname
	 * @param sex
	 * @return 用户信息
	 */
	public String Regist(String login, String password, String uname, String sex) {
		String url = "http://192.168.1.133/index.php?app=public&mod=api&act=userregist&login="
				+ login
				+ "&pwd="
				+ password
				+ "&uname="
				+ uname
				+ "&sex="
				+ sex
				+ "&time="
				+ timeStr
				+ "&code="
				+ EncryptUtil.string2MD5(login + password + timeStr + KEY);
		String dataContent = new BasicHttpClient().httpGet(url);
		return dataContent;
	}
}
