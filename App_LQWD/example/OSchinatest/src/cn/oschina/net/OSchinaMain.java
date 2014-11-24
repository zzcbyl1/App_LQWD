package cn.oschina.net;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class OSchinaMain extends Activity {

	public OSchinaMain() {
		// MyCache cache = (MyCache) getApplicationContext();
		// cacheDoc = cache.getCacheDoc();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Log.v("dd",c.toString() );
		PageTask task = new PageTask(this);
		task.execute(null);
	}

	class PageTask extends
			AsyncTask<String, Integer, List<Map<String, Object>>> {
		// 可变长的输入参数，与AsyncTask.exucute()对应
		ProgressDialog pdialog;
		MyCache cache;

		public PageTask(Context context) {
			cache = (MyCache) context.getApplicationContext();
			pdialog = new ProgressDialog(context, 0);
			pdialog.setTitle("正在连接请稍候....");
			pdialog.setButton("cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int i) {
					dialog.cancel();
				}
			});
			pdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					finish();
				}
			});
			// pdialog.setCancelable(true);
			pdialog.setMax(100);
			pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pdialog.show();

		}

		@Override
		protected List<Map<String, Object>> doInBackground(String... params) {
			List<Map<String, Object>> arr = null;
			try {

				int count = 1;
				int length = 100;

				if (cache.getCacheDoc().containsKey("doc")) {
					SoftReference<List<Map<String, Object>>> soft = cache
							.getCacheDoc().get("doc");
					arr = soft.get();
					for (int i = 1; i <= 100; i++) {
						publishProgress((int) ((i / (float) length) * 100));
					}
					Log.v("cache", "走缓存");
				} else {
					Log.v("cache", "不走缓存");
					arr = new ArrayList<Map<String, Object>>();
					Document doc = Jsoup
							.connect(
									"http://www.oschina.net/bbs/forum/1/development")
							.timeout(30000).get();
					Elements es = null;
					es = doc.select(".TopicList").select("table").select("a");

					length = es.size();
					for (Element e : es) {
						String name = e.text();
						String url = e.absUrl("href");
						// System.out.println(url);
						count++;
						publishProgress((int) ((count / (float) length) * 100));

						Map<String, Object> m = new HashMap<String, Object>();
						m.put("image", R.drawable.bbs);
						m.put("title", name);
						arr.add(m);

					}
					cache.getCacheDoc().put("doc",
							new SoftReference<List<Map<String, Object>>>(arr));

				}
				// count++;
				// publishProgress((int) ((count / (float) length) * 100));

			} catch (Exception e) {
				Log.e("doInBackground", e.getMessage());
			}
			return arr;

		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			// 返回HTML页面的内容
			// message.setText(result);

			ListView lv = (ListView) findViewById(R.id.bbslist);
			SimpleAdapter listItemAdapter = new SimpleAdapter(OSchinaMain.this,
					result,// 数据源
					R.layout.bbslist,// ListItem的XML实现
					// 动态数组与ImageItem对应的子项
					new String[] { "title", "image" },
					// list_items中对应的的ImageView和TextView
					new int[] { R.id.title, R.id.image });

			// 绑定数据源
			lv.setAdapter(listItemAdapter);
			pdialog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			// 任务启动，可以在这里显示一个对话框，这里简单处理
			// message.setText(R.string.task_started);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// 更新进度
			// System.out.println("" + values[0]);
			// message.setText(""+values[0]);
			pdialog.setProgress(values[0]);
		}

	}

	class MyAdpter extends ArrayAdapter<BBsList> {
		private ListView listView;

		public MyAdpter(Activity activity, List<BBsList> bbsList,
				ListView listView) {
			super(activity, 0, bbsList);
			this.listView = listView;
		}

		// public View getView(int position, View convertView, ViewGroup parent)
		// {
		// Activity activity = (Activity) getContext();
		//
		// // Inflate the views from XML
		// View rowView = convertView;
		// ViewCache viewCache;
		// if (rowView == null) {
		// LayoutInflater inflater = activity.getLayoutInflater();
		// rowView = inflater.inflate(R.layout.bbslist, null);
		// viewCache = new ViewCache(rowView);
		// rowView.setTag(viewCache);
		// } else {
		// viewCache = (ViewCache) rowView.getTag();
		// }
		// ImageAndText imageAndText = getItem(position);
		//
		// // Load the image and set it on the ImageView
		// String imageUrl = imageAndText.getImageUrl();
		// ImageView imageView = viewCache.getImageView();
		// imageView.setTag(imageUrl);
		// Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new
		// ImageCallback() {
		// public void imageLoaded(Drawable imageDrawable, String imageUrl) {
		// ImageView imageViewByTag = (ImageView)
		// listView.findViewWithTag(imageUrl);
		//
		// if (imageViewByTag != null) {
		// imageViewByTag.setImageDrawable(imageDrawable);
		// }
		// }
		// });
		// if (cachedImage == null) {
		// imageView.setImageResource(R.drawable.default_image);
		// }else{
		// imageView.setImageDrawable(cachedImage);
		// }
		// // Set the text on the TextView
		// TextView textView = viewCache.getTextView();
		// textView.setText(imageAndText.getText());
		//
		// return rowView;
		// }
	}

	class BBsList {

	}

}
