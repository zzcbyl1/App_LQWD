package com.luqinwenda.lqwdlist.app;


public class DataService {

	public String getData(String p, String psize) {
		String url = "http://www.luqinwenda.com/index.php?app=public&mod=api&act=getallfeed&p="
				+ p + "&psize=" + psize;
		String dataContent = new BasicHttpClient().httpGet(url);
		return dataContent;
	}
}
