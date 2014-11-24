package com.zzc.luqinwenda.app;

import java.util.ArrayList;
import java.util.List;

import com.zzc.luqinwenda.dal.DataService;
import com.zzc.luqinwenda.dal.JsonToObject;
import com.zzc.luqinwenda.model.Feed;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView gvList;
	private ProgressBar pBar;
	private TextView pTextView;
	View footer;
	private int pagesize = 10;
	private static int maxpage = 0;
	private int currentpage = 1;
	private List<Feed> datalist = new ArrayList<Feed>();
	private DataAdapter adapter;
	private boolean finish_load = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		gvList = (ListView) this.findViewById(R.id.gvList);
		pBar = (ProgressBar) this.findViewById(R.id.progressBar);
		pTextView = (TextView) this.findViewById(R.id.proText);

		footer = getLayoutInflater().inflate(R.layout.footer, null);

		gvList.setOnScrollListener(listener);
		gvList.addFooterView(footer);// ���ҳ�ţ�����ListView���

		new getHttpData().execute(currentpage, pagesize, this);

	}

	private OnScrollListener listener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (gvList.getLastVisiblePosition() + 1 == totalItemCount) {
				if (totalItemCount > 0) {
					// ��ȡ��ǰҳ
					if ((currentpage + 1) <= maxpage && finish_load) {
						finish_load = false;
						new Thread(new Runnable() {

							@Override
							public void run() {
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								DataService dservice = new DataService();
								String result = dservice.getData(
										String.valueOf(currentpage + 1),
										String.valueOf(pagesize));

								List<Feed> feedlist = new ArrayList<Feed>();
								JsonToObject jto = new JsonToObject(result);
								currentpage = jto.getCurrentPage();
								maxpage = jto.getMaxCount();
								feedlist = jto.parseJson(result);
								handler.sendMessage(handler.obtainMessage(1,
										feedlist));
							}
						}).start();
					}
				}
			}
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			datalist.addAll((List<Feed>) msg.obj);
			adapter.notifyDataSetChanged();
			finish_load = true;
		}
	};

	class getHttpData extends AsyncTask<Object, Object, Object> {

		@Override
		protected Object doInBackground(Object... params) {
			DataService dservice = new DataService();
			return dservice.getData(params[0].toString(), params[1].toString());
		}

		@Override
		protected void onPostExecute(Object dataContent) {
			JsonToObject jto = new JsonToObject(dataContent.toString());
			currentpage = jto.getCurrentPage();
			maxpage = jto.getMaxCount();
			datalist = jto.parseJson(dataContent.toString());
			adapter = new DataAdapter(MainActivity.this);
			gvList.setAdapter(adapter);
			pBar.setVisibility(View.GONE);
			pTextView.setVisibility(View.GONE);
			super.onPostExecute(dataContent);

		}

	}

	private class DataAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;

		private DataAdapter(Context context) {
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return datalist.size();
		}

		@Override
		public Object getItem(int position) {
			return datalist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return datalist.get(position).feed_id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item, null);
				viewHolder = new ViewHolder();
				viewHolder.tv1 = (TextView) convertView
						.findViewById(R.id.uName);
				viewHolder.tv2 = (TextView) convertView
						.findViewById(R.id.title);
				viewHolder.tv3 = (TextView) convertView
						.findViewById(R.id.content);
				convertView.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tv1.setText(datalist.get(position).uname);
			viewHolder.tv2.setText(datalist.get(position).title);
			viewHolder.tv3.setText(datalist.get(position).content);

			return convertView;
		}

		class ViewHolder {
			TextView tv1;
			TextView tv2;
			TextView tv3;
		}
	}

	/*
	 * public boolean isNetworkAvailable(Activity activity) { Context context =
	 * activity.getApplicationContext(); // ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
	 * ConnectivityManager connectivityManager = (ConnectivityManager) context
	 * .getSystemService(Context.CONNECTIVITY_SERVICE);
	 * 
	 * if (connectivityManager == null) { return false; } else { //
	 * ��ȡNetworkInfo���� NetworkInfo[] networkInfo =
	 * connectivityManager.getAllNetworkInfo();
	 * 
	 * if (networkInfo != null && networkInfo.length > 0) { for (int i = 0; i <
	 * networkInfo.length; i++) { System.out.println(i + "===״̬===" +
	 * networkInfo[i].getState()); System.out.println(i + "===����===" +
	 * networkInfo[i].getTypeName()); // �жϵ�ǰ����״̬�Ƿ�Ϊ����״̬ if
	 * (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) { return true;
	 * } } } } return false; }
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		LoginActivity.openCloseDialog(keyCode, MainActivity.this);
		return false;
	}
}