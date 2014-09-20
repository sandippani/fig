package com.example.fig;

import java.util.HashMap;
import java.util.Map;

import com.sandip.fig.rest.dtos.RegistrationDto;
import com.sandip.fig.rest.dtos.ResponseDto;
import com.sandip.fig.rest.dtos.ResponseStatus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends ActionBarActivity implements
		HttpClientListener {
	private EditText email;
	private EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		email = (EditText) findViewById(R.id.etUserName);
		password = (EditText) findViewById(R.id.etPass);
		SystemGlobalVariable.setCurrentUserId(0);
	}

	public void signInToApp(View view) {
		RegistrationDto registrationDto = new RegistrationDto();
		registrationDto.setEmail(email.getText().toString());
		registrationDto.setPassword(password.getText().toString());
		HttpAsyncTask httpAsyncTask = new HttpAsyncTask(registrationDto,
				HttpRequestType.POST, "signin", this, null);
		Void[] array = null;
		httpAsyncTask.execute(array);

	}

	@Override
	public void processResponse(ResponseDto responseDto, Object extraParameters) {
		if (responseDto.getResponseStatus().equals(ResponseStatus.FAIL)) {
			Toast.makeText(getApplicationContext(),
					responseDto.getReturnObject().toString(), Toast.LENGTH_LONG)
					.show();
		} else {
			SystemGlobalVariable.setCurrentUserId((Integer)responseDto.getReturnObject());
			Intent intent;
			intent = new Intent(getApplicationContext(),
					CameraPhotoCaptureActivity.class);
			startActivity(intent);
		}
	}

}
