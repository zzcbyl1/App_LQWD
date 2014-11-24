package com.zzc.luqinwenda.util;

import com.zzc.luqinwenda.app.R;

import android.R.integer;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConfimDialog extends Dialog implements OnClickListener {

	private Context context;
	private TextView dialogtitle, dialogcontent, topline, bottomline;
	private Button btn1, btn2;
	private String title, content;

	public ConfimDialog(Context _context, String _title, String _content,
			int _theme) {
		super(_context, _theme);
		this.context = _context;
		this.title = _title;
		this.content = _content;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mydialog);

		dialogtitle = (TextView) findViewById(R.id.dialogtitle);
		dialogcontent = (TextView) findViewById(R.id.dialogcontent);
		topline = (TextView) findViewById(R.id.topLine);
		bottomline = (TextView) findViewById(R.id.bottonLine);
		btn1 = (Button) findViewById(R.id.dialogsubmit);
		btn2 = (Button) findViewById(R.id.dialogcancel);

		if (title.isEmpty()) {
			dialogtitle.setVisibility(View.GONE);
			topline.setVisibility(View.GONE);
			bottomline.setVisibility(View.GONE);
		} else
			dialogtitle.setText(title);

		dialogcontent.setText(content);
		dialogcontent.setTextColor(context.getResources().getColor(
				R.color.dialogtextcolor));
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialogsubmit:
			System.exit(0);
			break;
		case R.id.dialogcancel:
			cancel();
			break;
		default:
			break;
		}
	}

}
