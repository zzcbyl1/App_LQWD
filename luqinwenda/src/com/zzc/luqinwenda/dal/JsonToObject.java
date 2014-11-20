package com.zzc.luqinwenda.dal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.zzc.luqinwenda.model.Answer;
import com.zzc.luqinwenda.model.Feed;

public class JsonToObject {

	private JSONTokener jsonTokener;
	private JSONObject Obj;

	// 初始化
	public JsonToObject(String strContent) {
		jsonTokener = new JSONTokener(strContent);
		try {
			Obj = (JSONObject) jsonTokener.nextValue();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 获取当前页码
	public int getCurrentPage() {
		int currentPage = 1;
		try {
			currentPage = Obj.getInt("nowPage");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return currentPage;
	}

	// 获取总页码
	public int getMaxCount() {
		int maxpage = 1;
		try {
			maxpage = Obj.getInt("totalPages");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return maxpage;
	}

	// json转对象
	public List<Feed> parseJson(String strContent) {
		List<Feed> feedlist = new ArrayList<Feed>();
		// 解析json
		try {
			JSONArray dataObj = Obj.getJSONArray("data");
			Feed feed = null;
			Answer answer = null;
			JSONObject data = null;
			JSONArray answerArr = null;
			JSONObject answerObj = null;
			JSONObject userObj = null;
			String description = null;
			for (int i = 0; i < dataObj.length(); i++) {
				feed = new Feed();
				data = dataObj.getJSONObject(i);
				feed.feed_id = data.getLong("feed_id");
				feed.uid = data.getLong("uid");
				if (data.getString("user_info") != "false") {
					userObj = data.getJSONObject("user_info");
					feed.uname = userObj.getString("uname");
				} else {
					feed.uname = "匿名";
				}
				feed.title = data.getString("body");
				description = data.getString("description");
				description = description.length() > 55 ? description
						.substring(0, 55) + "..." : description;
				feed.content = description;

				if (data.getString("answer") != "false") {
					answerArr = data.getJSONArray("answer");
					answerObj = (JSONObject) answerArr.get(0);
					answer = new Answer();
					answer.feed_id = answerObj.getLong("feed_id");
					answer.uid = answerObj.getLong("uid");
					answer.content = answerObj.getString("body");
					feed.answer = answer;
				}
				feedlist.add(feed);
			}

			return feedlist;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
