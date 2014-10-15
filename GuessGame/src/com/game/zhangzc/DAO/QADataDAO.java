package com.game.zhangzc.DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.game.zhangzc.guessgame.R;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

public class QADataDAO {

	public static List<QAData> getData(Activity activity)
			throws XmlPullParserException, IOException {

		List<QAData> qAList = new ArrayList<QAData>();
		QAData qaData = null;
		Resources resources = activity.getResources();
		XmlResourceParser xrp = resources.getXml(R.xml.list);
		xrp.next();
		int eventType = xrp.getEventType();
		int index = 0;
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG) {
				if (xrp.getName().equals("qadata")) {
					qaData = new QAData();
				}
				if (xrp.getName().equals("prompt1"))
					index = 1;
				if (xrp.getName().equals("prompt2"))
					index = 2;
				if (xrp.getName().equals("prompt3"))
					index = 3;
				if (xrp.getName().equals("prompt4"))
					index = 4;
				if (xrp.getName().equals("answer"))
					index = 5;
				if (xrp.getName().equals("option"))
					index = 6;

			}

			if (eventType == XmlPullParser.TEXT) {
				switch (index) {
				case 1:
					qaData.PROMPT_FIRST = xrp.getText().trim();
					break;
				case 2:
					qaData.PROMPT_SECOND = xrp.getText().trim();
					break;
				case 3:
					qaData.PROMPT_THIRD = xrp.getText().trim();
					break;
				case 4:
					qaData.PROMPT_FOUR = xrp.getText().trim();
					break;
				case 5:
					qaData.ANSWER_FIELD = xrp.getText().trim();
					break;
				case 6:
					qaData.GAME_OPTION = xrp.getText().trim();
					break;
				default:
					break;
				}
			}
			if (eventType == XmlPullParser.END_TAG
					&& xrp.getName().equals("qadata")) {
				qAList.add(qaData);
			}

			eventType = xrp.next();
		}

		return qAList;
	}
}
