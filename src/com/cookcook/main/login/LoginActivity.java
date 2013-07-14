package com.cookcook.main.login;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cookcook.main.http.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends Activity {
	EditText field_username;
	EditText field_password;
	Button btn_login;
	Button btn_create_an_account;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		field_username = (EditText) findViewById(R.id.field_username);
		field_password = (EditText) findViewById(R.id.field_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_create_an_account = (Button) findViewById(R.id.btn_create_an_account);
		
		btn_login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String strUserName = field_username.getText().toString();
				String strPassword = field_password.getText().toString();
				if (strUserName.trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please Enter Username", Toast.LENGTH_SHORT).show();
				} else if (strPassword.trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please Enter Password", Toast.LENGTH_SHORT).show();
				} else {
					// send to server
					String android_id = Secure.getString(getBaseContext().getContentResolver(),
				            Secure.ANDROID_ID); 
					RequestParams params = new RequestParams();
					params.put("name", strUserName);
					params.put("password", strPassword);
					params.put("device", android_id);
					RestClient.post("auth/login", params,
							new JsonHttpResponseHandler() {
								@Override
								public void onSuccess(JSONObject result) {
									Toast.makeText(getApplicationContext(),
											result.toString(),
											Toast.LENGTH_LONG).show();
								}
							});
				}

			}
		});

		btn_create_an_account.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent myIntent = new Intent(v.getContext(), JoinActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

	}

}
