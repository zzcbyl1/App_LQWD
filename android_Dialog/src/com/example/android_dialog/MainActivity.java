package com.example.android_dialog;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;


public class MainActivity extends Activity implements OnDialogDoneListener {

	public static final String LOGTAG = "DialogFragmentDemo";
	public static final String ALERT_DIALOG_TAG = "ALERT_DIALOG_TAG";
	public static final String HELP_DIALOG_TAG = "HELP_DIALOG_TAG";
	public static final String PROMPT_DIALOG_TAG = "PROMPT_DIALOG_TAG";
	public static final String EMBED_DIALOG_TAG = "EMBED_DIALOG_TAG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	}

	private void testPromptDialog() {
		//FragmentTransaction ft = getFragmentManager().beginTransaction();
		//PromptDialogFragment pdf=PromptDialogFragment.
	}

	@Override
	public void onDialogDone(String tag, boolean cancelled, CharSequence message) {

	}
}
