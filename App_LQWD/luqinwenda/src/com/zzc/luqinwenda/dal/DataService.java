package com.zzc.luqinwenda.dal;

import com.zzc.luqinwenda.http.BasicHttpClient;

public class DataService {

	public String getData(String p, String psize) {
		String url = "http://www.luqinwenda.com/index.php?app=public&mod=api&act=getallfeed&p="
				+ p + "&psize=" + psize;
		String dataContent = new BasicHttpClient().httpGet(url);
		return dataContent;
	}
	
	/**
	 * 登录
	 * @param login  用户名
	 * @param password  密码
	 * @return  用户信息
	 */
	public String Login(String login, String password) {
		String url = "http://www.luqinwenda.com/index.php?app=public&mod=api&act=userlogin&login="
				+ login + "&pwd=" + password;
		String dataContent = new BasicHttpClient().httpGet(url);
		return dataContent;
	}
}
