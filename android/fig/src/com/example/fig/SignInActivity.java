package com.example.fig;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class SignInActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		//setContentView(R.layout.camera_photo_capture);
	}
	
	public void signInToApp(View view){
		Intent intent;
		intent = new Intent(getApplicationContext(), CameraPhotoCaptureActivity.class);
		startActivity(intent);
	}

}
