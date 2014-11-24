package com.example.android_dialog;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi")
public class PromptDialogFragment extends DialogFragment implements
		View.OnClickListener {

	private EditText et;

	public static PromptDialogFragment newInstance(String prompt) {

		PromptDialogFragment pdf = new PromptDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString("prompt", prompt);
		pdf.setArguments(bundle);
		return pdf;
	}

	@Override
	public void onAttach(Activity activity) {
		try {
			OnDialogDoneListener test = (OnDialogDoneListener) activity;
		} catch (Exception e) {
			Log.e(MainActivity.LOGTAG, "Activity is not listening");
		}

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setCancelable(true);
		int style = DialogFragment.STYLE_NORMAL, theme = 0;
		setStyle(style, theme);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle icicle) {
		View v = inflater.inflate(R.layout.prompt_dialog, container, false);
		TextView tv = (TextView) v.findViewById(R.id.promptmessage);
		tv.setText(getArguments().getString("prompt"));

		Button dismissbBtn = (Button) v.findViewById(R.id.btn_dismiss);
		dismissbBtn.setOnClickListener(this);

		Button saveBtn = (Button) v.findViewById(R.id.btn_save);
		saveBtn.setOnClickListener(this);

		Button helpBtn = (Button) v.findViewById(R.id.btn_help);
		helpBtn.setOnClickListener(this);

		et = (EditText) v.findViewById(R.id.inputtext);
		if (icicle != null) {
			et.setText(icicle.getCharSequence("input"));
		}

		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle icicle) {
		super.onSaveInstanceState(icicle);
		icicle.putCharSequence("input", et.getText());
	}

	@Override
	public void onClick(View v) {
		OnDialogDoneListener act = (OnDialogDoneListener) getActivity();
		if (v.getId() == R.id.btn_save) {
			TextView tv = (TextView) getView().findViewById(R.id.inputtext);
			act.onDialogDone(this.getTag(), false, tv.getText());
			dismiss();
			return;
		}
		if (v.getId() == R.id.btn_dismiss) {
			act.onDialogDone(this.getTag(), true, null);
			dismiss();
			return;
		}
		if (v.getId() == R.id.btn_help) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.remove(this);
			ft.addToBackStack(null);
			//HelpDialogFragment hdf = HelpDialogFragment.newInstance();
			//hdf.show(ft,MainActivity.HELP_DIALOG_TAG);
			return;
		}

	}

}
