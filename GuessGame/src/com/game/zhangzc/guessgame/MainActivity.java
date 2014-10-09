package com.game.zhangzc.guessgame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.game.zhangzc.DAO.QAData;
import com.game.zhangzc.DAO.QADataDAO;
import com.game.zhangzc.DAO.QaDataConfig;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final int REQUESTCODE = 9999;
	private static SharedPreferences pre;
	private static String userName;
	private static List<QAData> dataList = new ArrayList<QAData>();
	private static int qid = 0;
	private static int score = 0;

	private TextView tvNumber, tvScore, tvPrompt1, tvPrompt2, tvPrompt3,
			tvPrompt4;
	private LinearLayout AnswerTable;
	private GridView OptionGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tvNumber = (TextView) this.findViewById(R.id.tvNumber);
		tvScore = (TextView) this.findViewById(R.id.tvScore);
		tvPrompt1 = (TextView) this.findViewById(R.id.tvPrompt1);
		tvPrompt2 = (TextView) this.findViewById(R.id.tvPrompt2);
		tvPrompt3 = (TextView) this.findViewById(R.id.tvPrompt3);
		tvPrompt4 = (TextView) this.findViewById(R.id.tvPrompt4);
		AnswerTable = (LinearLayout) this.findViewById(R.id.AnswerTable);
		OptionGrid = (GridView) this.findViewById(R.id.OptionGrid);
		//setPromptDefault();
		
		pre = getPreferences(MODE_PRIVATE);
		userName = pre.getString("username", "");
		if (userName == "") {
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivityForResult(intent, REQUESTCODE);
		}

//		try {
//			dataList = QADataDAO.getData(this);
//		} catch (XmlPullParserException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		// 绑定数据
//		if (dataList.size() > 0) {
//			QAData qaData = dataList.get(qid);
//			tvNumber.setText(qid + "/" + QaDataConfig.TOTALCOUNT);
//			tvScore.setText("得分：" + score);
//			
//			
//		}

	}
	
	private void setPromptDefault()
	{
		LayoutParams params=new LayoutParams(100, 20);
		tvPrompt1.setLayoutParams(params);
		tvPrompt1.setBackgroundColor(R.string.DefaultPromptColor);

		tvPrompt2.setLayoutParams(params);
		tvPrompt2.setBackgroundColor(R.string.DefaultPromptColor);

		tvPrompt3.setLayoutParams(params);
		tvPrompt3.setBackgroundColor(R.string.DefaultPromptColor);

		tvPrompt4.setLayoutParams(params);
		tvPrompt4.setBackgroundColor(R.string.DefaultPromptColor);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == REQUESTCODE) {

			// 存用户名
			Editor editor = pre.edit();
			editor.putString("username", data.getExtras().getString("username"));
			editor.commit();
		}
	}

}
