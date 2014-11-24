package com.game.zhangzc.guessgame;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.xmlpull.v1.XmlPullParserException;

import com.game.zhangzc.DAO.QAData;
import com.game.zhangzc.DAO.QADataDAO;
import com.game.zhangzc.DAO.QaDataConfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
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
	private GridView OptionGrid, InputGrid;
	private static QAData currentQaData;
	private static boolean[] ItemState = new boolean[24];
	private static int answerCount = 0;
	private static boolean isWrong = false;
	private static List<Integer> randomList = new ArrayList<Integer>();
	private static int Tag = 1;
	public static MainActivity instance;
	private static int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		instance = this;
		index = 0;

		tvNumber = (TextView) this.findViewById(R.id.tvNumber);
		tvScore = (TextView) this.findViewById(R.id.tvScore);
		tvPrompt1 = (TextView) this.findViewById(R.id.tvPrompt1);
		tvPrompt2 = (TextView) this.findViewById(R.id.tvPrompt2);
		tvPrompt3 = (TextView) this.findViewById(R.id.tvPrompt3);
		tvPrompt4 = (TextView) this.findViewById(R.id.tvPrompt4);
		OptionGrid = (GridView) this.findViewById(R.id.OptionGrid);
		InputGrid = (GridView) this.findViewById(R.id.InputGrid);

		pre = getPreferences(MODE_PRIVATE);
		userName = pre.getString("username", "");
		if (userName == "") {
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivityForResult(intent, REQUESTCODE);
		}
		// 获取进度
		getPreferenceData();

		try {
			dataList = QADataDAO.getData(this);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 绑定数据
		if (dataList.size() > 0) {
			fillData();
		}
	}

	private void fillData() {
		randomList = new ArrayList<Integer>();
		isWrong = false;
		answerCount = 0;
		ItemState = new boolean[24];
		Tag = 1;
		for (int i = 0; i < ItemState.length; i++) {
			ItemState[i] = true;
		}
		currentQaData = dataList.get(qid);
		tvNumber.setText((qid + 1) + "/" + QaDataConfig.TOTALCOUNT);
		tvScore.setText(String.valueOf(score));

		setPromptDefault();
		setAnswerCell();
		setItemCell();
	}

	// 答案框
	private void setAnswerCell() {
		InputGrid.setNumColumns(currentQaData.ANSWER_FIELD.trim().length());
		LayoutParams params = InputGrid.getLayoutParams();
		params.width = Dp2Px(33) * currentQaData.ANSWER_FIELD.trim().length();
		InputGrid.setLayoutParams(params);
		String[] answerArr = new String[currentQaData.ANSWER_FIELD.trim()
				.length()];
		for (int i = 0; i < answerArr.length; i++) {
			answerArr[i] = "　";
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.inputcell, answerArr);
		InputGrid.setAdapter(adapter);

		InputGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				TextView TVItem = (TextView) view;
				if (!TVItem.getText().toString().trim().equals("　")) {
					TVItem.setText("　");
					cancelAnswerValue(TVItem.getId());
				}
			}
		});
	}

	// 选项列表
	private void setItemCell() {
		List<String> optionList = new ArrayList<String>();
		List<String> ls_optionList = new ArrayList<String>();
		String option = currentQaData.GAME_OPTION.trim();
		String answer = currentQaData.ANSWER_FIELD.trim();
		for (int i = 0; i < answer.length(); i++) {
			ls_optionList.add(answer.substring(i, i + 1));
		}
		for (int i = 0; i < option.length(); i++) {
			if (!ls_optionList.contains(option.substring(i, i + 1)))
				ls_optionList.add(option.substring(i, i + 1));
			if (ls_optionList.size() == 24)
				break;
		}
		if (ls_optionList.size() < 24) {
			String otherOptionString = QaDataConfig.OPTION_OTHER;
			for (int i = 0; i < otherOptionString.length(); i++) {
				if (!ls_optionList.contains(otherOptionString.substring(i,
						i + 1)))
					ls_optionList.add(otherOptionString.substring(i, i + 1));
				if (ls_optionList.size() == 24)
					break;
			}
		}

		getIntRandom(ls_optionList.size());
		for (int i = 0; i < randomList.size(); i++) {
			optionList.add(ls_optionList.get(randomList.get(i)));
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.optioncell, optionList);
		OptionGrid.setAdapter(adapter);

		// 选择答案
		OptionGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				TextView TVItem = (TextView) view;
				if (ItemState[position]) {
					// 选择
					if (answerCount < currentQaData.ANSWER_FIELD.length())
						setItemAnswerValue(TVItem, position);
				} else {
					// 取消选择
					cancelItemAnswerValue(TVItem, position);
				}

			}
		});
	}

	// 获取小于size的随机数,获取size个存入randomList
	private void getIntRandom(int size) {
		Random random = new Random();
		int number = random.nextInt(size);
		if (randomList.contains(number)) {
			getIntRandom(size);
		} else {
			randomList.add(number);
			if (randomList.size() < size)
				getIntRandom(size);
		}
	}

	// 取消答案
	private void cancelAnswerValue(int id) {
		TextView TVItem = (TextView) OptionGrid.getChildAt(id);
		TVItem.setBackgroundColor(Color.WHITE);
		ItemState[id] = !ItemState[id];
		answerCount--;
		if (isWrong) {
			setAnswerCellBorder(false);
		}
	}

	// 取消选择答案(选项)
	private void cancelItemAnswerValue(TextView TVItem, int position) {
		TextView tVInputView;
		String itemVal;
		for (int i = 0; i < InputGrid.getChildCount(); i++) {
			tVInputView = (TextView) InputGrid.getChildAt(i);
			itemVal = tVInputView.getText().toString().trim();
			if (itemVal.equals(TVItem.getText().toString().trim())) {
				tVInputView.setText("　");
				tVInputView.setId(position + 9999); // id随意
				TVItem.setBackgroundColor(Color.WHITE);
				ItemState[position] = true;
				answerCount--;
				break;
			}
		}
		if (isWrong) {
			setAnswerCellBorder(false);
		}
	}

	// 选择答案(选项)
	private void setItemAnswerValue(TextView TVItem, int position) {
		TextView tVInputView;
		String itemVal;
		for (int i = 0; i < InputGrid.getChildCount(); i++) {
			tVInputView = (TextView) InputGrid.getChildAt(i);
			itemVal = tVInputView.getText().toString().trim();
			if (itemVal.equals("　")) {
				tVInputView.setText(TVItem.getText().toString().trim());
				tVInputView.setId(position);
				TVItem.setBackgroundColor(Color.GRAY);
				ItemState[position] = false;
				answerCount++;
				break;
			}
		}
		// 提交
		if (answerCount == currentQaData.ANSWER_FIELD.length()) {
			SubmitResult();
		}
	}

	// 提交答案
	private void SubmitResult() {
		TextView tVInputView;
		String inputAnswer = "";
		for (int i = 0; i < InputGrid.getChildCount(); i++) {
			tVInputView = (TextView) InputGrid.getChildAt(i);
			inputAnswer += tVInputView.getText().toString().trim();
		}
		if (inputAnswer.equals(currentQaData.ANSWER_FIELD.trim())) {
			Toast.makeText(MainActivity.this, "恭喜您,回答正确", Toast.LENGTH_SHORT)
					.show();

			openPromptText(R.id.tvPrompt2);
			openPromptText(R.id.tvPrompt3);
			openPromptText(R.id.tvPrompt4);

			// 加分
			AddScore();
			// 存储进度
			savePreferenceData();

			// 延时执行
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					Message message = new Message();
					message.what = 2;
					handler.sendMessage(message);
				}
			};
			Timer timer = new Timer();
			timer.schedule(task, 1500);

		} else {
			setAnswerCellBorder(true);
		}

	}

	// 计分
	private void AddScore() {
		switch (Tag) {
		case 1:
			score += 100;
			break;
		case 2:
			score += 50;
			break;
		case 3:
			score += 20;
			break;
		case 4:
			score += 10;
			break;

		default:
			break;
		}

		tvScore.setText(String.valueOf(score));
	}

	// 设置答案框格式
	private void setAnswerCellBorder(boolean isOK) {
		isWrong = isOK;
		TextView tVInputView;
		for (int i = 0; i < InputGrid.getChildCount(); i++) {
			tVInputView = (TextView) InputGrid.getChildAt(i);
			if (isOK) {
				tVInputView
						.setBackgroundResource(R.drawable.textview_border_red);
			} else {
				tVInputView.setBackgroundResource(R.drawable.textview_border);
			}
		}
	}

	// 提示默认样式
	private void setPromptDefault() {

		LayoutParams params = tvPrompt1.getLayoutParams();
		params.height = Dp2Px(22);
		params.width = LayoutParams.WRAP_CONTENT;
		tvPrompt1.setLayoutParams(params);
		tvPrompt1.setBackgroundResource(R.color.backgroundColor);
		tvPrompt1.setTextSize(16);
		tvPrompt1.setText(currentQaData.PROMPT_FIRST.trim());

		params = tvPrompt2.getLayoutParams();
		params.height = Dp2Px(22);
		params.width = Dp2Px(100);
		tvPrompt2.setLayoutParams(params);
		tvPrompt2.setBackgroundResource(R.color.DefaultPromptColor);
		tvPrompt2.setText("");
		tvPrompt2.setOnClickListener(promptListener);

		params = tvPrompt3.getLayoutParams();
		params.height = Dp2Px(22);
		params.width = Dp2Px(100);
		tvPrompt3.setLayoutParams(params);
		tvPrompt3.setBackgroundResource(R.color.DefaultPromptColor);
		tvPrompt3.setText("");
		tvPrompt3.setOnClickListener(promptListener);

		params = tvPrompt4.getLayoutParams();
		params.height = Dp2Px(22);
		params.width = Dp2Px(100);
		tvPrompt4.setLayoutParams(params);
		tvPrompt4.setBackgroundResource(R.color.DefaultPromptColor);
		tvPrompt4.setText("");
		tvPrompt4.setOnClickListener(promptListener);
	}

	// 提示点击事件
	private View.OnClickListener promptListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			openPromptText(v.getId());

			switch (v.getId()) {
			case R.id.tvPrompt2:
				Tag++;
				break;
			case R.id.tvPrompt3:
				Tag++;
				break;
			case R.id.tvPrompt4:
				Tag++;
				break;
			}
		}
	};

	// 显示提示
	private void openPromptText(int id) {
		LayoutParams params;
		switch (id) {
		case R.id.tvPrompt2:
			params = tvPrompt2.getLayoutParams();
			params.height = Dp2Px(22);
			params.width = LayoutParams.WRAP_CONTENT;
			tvPrompt2.setLayoutParams(params);
			tvPrompt2.setBackgroundResource(R.color.backgroundColor);
			tvPrompt2.setTextSize(16);
			tvPrompt2.setText(currentQaData.PROMPT_SECOND.trim());
			break;
		case R.id.tvPrompt3:
			params = tvPrompt3.getLayoutParams();
			params.height = Dp2Px(22);
			params.width = LayoutParams.WRAP_CONTENT;
			tvPrompt3.setLayoutParams(params);
			tvPrompt3.setBackgroundResource(R.color.backgroundColor);
			tvPrompt3.setTextSize(16);
			tvPrompt3.setText(currentQaData.PROMPT_THIRD.trim());
			break;
		case R.id.tvPrompt4:
			params = tvPrompt4.getLayoutParams();
			params.height = Dp2Px(22);
			params.width = LayoutParams.WRAP_CONTENT;
			tvPrompt4.setLayoutParams(params);
			tvPrompt4.setBackgroundResource(R.color.backgroundColor);
			tvPrompt4.setTextSize(16);
			tvPrompt4.setText(currentQaData.PROMPT_FOUR.trim());
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == REQUESTCODE) {
			// 存用户名
			Editor editor = pre.edit();
			editor.putString("username", data.getExtras().getString("username"));
			editor.commit();
		}
	}

	@Override
	protected void onPause() {
		savePreferenceData();
		super.onPause();
	}

	@Override
	protected void onStop() {
		savePreferenceData();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		savePreferenceData();
		super.onDestroy();
	}

	// 获取进度
	private void getPreferenceData() {
		qid = pre.getInt("currentCount", 0);
		score = pre.getInt("currentScore", 0);
	}

	// 保存进度
	private void savePreferenceData() {
		Editor editor = pre.edit();
		editor.putInt("currentCount", qid);
		editor.putInt("currentScore", score);
		editor.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			exit();
			return false;
		}
		return false;
	}

	private void exit() {
		if (index == 0) {
			Toast.makeText(MainActivity.this, "再按一次返回键就可以退出啦",
					Toast.LENGTH_SHORT).show();
			index++;

			// 延时执行
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					Message message = new Message();
					message.what = 3;
					handler.sendMessage(message);
				}
			};
			Timer timer = new Timer();
			timer.schedule(task, 3000);

		} else {
			MainActivity.this.finish();
		}
	}

	// 延时执行
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				SubmitResult();
				break;
			case 2:
				qid++;
				if (qid < dataList.size())
					fillData();
				break;

			case 3:
				index = 0;
				break;
			}
		}

	};

	public int Dp2Px(float dp) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
}
