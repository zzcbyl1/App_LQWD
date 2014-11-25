package cn.oschina.net;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class OSchinaLogin extends Activity {
	ImageButton loginBtn;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        loginBtn = (ImageButton) findViewById(R.id.login_button);
        
        loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(OSchinaLogin.this, OSchinaMain.class);
				startActivity(intent);
				
				//finish();
				
			}
		});
        
    }
}