package com.example.fig;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sandip.fig.rest.dtos.RegistrationDto;
import com.sandip.fig.rest.dtos.ResponseDto;
import com.sandip.fig.rest.dtos.ResponseStatus;

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
		// registerSignInViews();
	}

	public void signInToApp(View view) {
		// if ( checkValidation () )
		// {
		RegistrationDto registrationDto = new RegistrationDto();
		registrationDto.setEmail(email.getText().toString());
		registrationDto.setPassword(password.getText().toString());
		HttpAsyncTask httpAsyncTask = new HttpAsyncTask(registrationDto,
				HttpRequestType.POST, "signin", this, null);
		Void[] array = null;
		httpAsyncTask.execute(array);
		// }
		// else
		// Toast.makeText(SignInActivity.this, "Form contains error",
		// Toast.LENGTH_LONG).show();

	}

	private void registerSignInViews() {
		password = (EditText) findViewById(R.id.etPass);
		// TextWatcher would let us check validation error on the fly
		password.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				Validation.hasText(password);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

		email = (EditText) findViewById(R.id.etUserName);
		email.addTextChangedListener(new TextWatcher() {
			// after every change has been made to this editText, we would like
			// to check validity
			@Override
			public void afterTextChanged(Editable s) {
				Validation.isEmailAddress(email, true);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

	}

	@Override
	public void processResponse(Object obj, Object extraParameters) {
		ResponseDto responseDto = (ResponseDto) obj;
		if (responseDto.getResponseStatus().equals(ResponseStatus.FAIL)) {
			Toast.makeText(getApplicationContext(),
					responseDto.getReturnObject().toString(), Toast.LENGTH_LONG)
					.show();
		} else {
			SystemGlobalVariable.setCurrentUserId((Integer) responseDto
					.getReturnObject());
			Intent intent;
			intent = new Intent(getApplicationContext(),
					CameraPhotoCaptureActivity.class);
			startActivity(intent);
		}
	}

}
