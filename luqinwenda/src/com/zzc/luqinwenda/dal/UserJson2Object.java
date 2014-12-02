package com.zzc.luqinwenda.dal;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.zzc.luqinwenda.model.User;

public class UserJson2Object {
	private JSONTokener jsonTokener;
	private JSONObject Obj;

	public UserJson2Object(String _jsonString) {
		jsonTokener = new JSONTokener(_jsonString);
		try {
			Obj = (JSONObject) jsonTokener.nextValue();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param refereStr 来源,组成:XX失败
	 * @return  信息
	 */
	public String getInfo(String refereStr) {
		String info = refereStr + "失败";
		try {
			info = Obj.getString("info");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 获取访问状态
	 * @return 状态
	 */
	public int getStatus() {
		int status = 0;
		try {
			status = Obj.getInt("status");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return status;
	}

	public User JsonToObject() {
		User user = new User();
		try {
			JSONObject dataObj = Obj.getJSONObject("data");
			user.uid = dataObj.getLong("uid");
			user.ctime = dataObj.getInt("ctime");
			user.email = dataObj.getString("email");
			user.idcard = dataObj.getString("idcard");
			user.intro = dataObj.getString("intro");
			user.invite_code = dataObj.getString("invite_code");
			user.is_active = dataObj.getInt("is_active");
			user.is_audit = dataObj.getInt("is_audit");
			user.is_init = dataObj.getInt("is_init");
			user.linknumber = dataObj.getString("linknumber");
			user.location = dataObj.getString("location");
			user.login = dataObj.getString("login");
			user.openid = dataObj.getString("openid");
			user.password = dataObj.getString("password");
			user.realname = dataObj.getString("realname");
			user.sex = dataObj.getInt("sex");
			user.uname = dataObj.getString("uname");
			user.user_extend = dataObj.getString("user_extend");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
}
