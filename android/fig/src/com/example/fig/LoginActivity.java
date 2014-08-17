package com.example.fig;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity implements OnClickListener { 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button signinbtn = (Button)findViewById(R.id.btnSingIn);
		Button signupbtn = (Button)findViewById(R.id.btnSignUp);
		signinbtn.setOnClickListener(this);
		signupbtn.setOnClickListener(this);
	}

	@Override
    public void onClick(View view) {
	Intent intent ;
		switch(view.getId()){
        case R.id.btnSingIn:
        	 intent = new Intent(getApplicationContext(), SignInActivity.class);
        	 startActivity(intent);
        break;
        case R.id.btnSignUp:
        	 intent = new Intent(getApplicationContext(), SignUpActivity.class);
        	 startActivity(intent);
        break;

				}
		
	
    }
}
