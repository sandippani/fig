package com.example.fig;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	
	public void signInClicked(View view) {
		Intent intent;
		intent = new Intent(getApplicationContext(), SignInActivity.class);
		startActivity(intent);
	}

	public void signUpClicked(View view) {
		Intent intent;
		intent = new Intent(getApplicationContext(), SignUpActivity.class);
		startActivity(intent);

	}
}
