package com.example.fig;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sandip.fig.rest.dtos.RegistrationDto;
import com.sandip.fig.rest.dtos.ResponseDto;
import com.sandip.fig.rest.dtos.ResponseStatus;

public class SignUpActivity extends ActionBarActivity implements
		HttpClientListener {

	private EditText username;

	private EditText email;

	private EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_screen);
		username = (EditText) findViewById(R.id.etUserName);
		password = (EditText) findViewById(R.id.etPass);
		email = (EditText) findViewById(R.id.etEmail);
	}

	public void registerUser(View view) {
		RegistrationDto registrationDto = new RegistrationDto();
		registrationDto.setEmail(email.getText().toString());
		registrationDto.setPassword(password.getText().toString());
		registrationDto.setUsername(username.getText().toString());
		HttpAsyncTask httpAsyncTask = new HttpAsyncTask(registrationDto,
				HttpRequestType.POST, "registration", this, null);
		Void[] array = null;
		httpAsyncTask.execute(array);
	}

	@Override
	public void processResponse(Object obj, Object extraParameters) {
		ResponseDto responseDto = (ResponseDto) obj;
		// System.out.println("...Reponse..."+responseDto.getResponseStatus()+":::"+responseDto.getReturnObject());
		if (responseDto.getResponseStatus().equals(ResponseStatus.FAIL)) {
			Toast.makeText(getApplicationContext(),
					responseDto.getReturnObject().toString(), Toast.LENGTH_LONG)
					.show();
		} else {
			Intent intent;
			intent = new Intent(getApplicationContext(), SignInActivity.class);
			startActivity(intent);
		}
	}

}
