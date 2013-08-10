package com.cookcook.main.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.cookcook.main.R;
import com.cookcook.main.login.Login_Preference;

import com.cookcook.main.http.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class LoginActivity extends Activity {
	EditText field_username;
	EditText field_password;
	Button btn_login;
	Button btn_create_an_account;
	static final int SIGN_UP_REQUEST = 1; // The request code
	static final int HOME_REQUEST = 2; 
	SharedPreferences spProfile;
	String strProfileName, strProfileNameGet, strUserName,strPassword,strDevice,strToken;
	private Login_Preference login_preference;
	
	private void login(String strUserName, String strPassword) {
		String android_id = Secure.getString(getBaseContext()
				.getContentResolver(), Secure.ANDROID_ID);
		RequestParams params = new RequestParams();
		params.put("name", strUserName);
		params.put("password", strPassword);
		params.put("device", android_id);
		RestClient.post("auth/login", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject result) {

							Intent home_intent = new Intent(getApplicationContext(),
									Login_Success.class);
							home_intent.putExtra("name", LoginActivity.this.strUserName);
							try {
								Log.v("token receive:",result.toString());
								home_intent.putExtra("device", result.toString());
								home_intent.putExtra("token", result.getString("token"));
								home_intent.putExtra("account_id", result.getString("account_id"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							home_intent.putExtra("name_get", LoginActivity.this.strUserName);

							
							startActivityForResult(home_intent,HOME_REQUEST);
					}

				});
	}
	
	
	
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
				strUserName = field_username.getText().toString();
				strPassword = field_password.getText().toString();
				if (strUserName.trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please Enter Username", Toast.LENGTH_SHORT).show();
				} else if (strPassword.trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please Enter Password", Toast.LENGTH_SHORT).show();
				} else {
					// send to server
					login(strUserName, strPassword);
				}

			}

			
		});

		btn_create_an_account.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent sign_up_intent = new Intent(v.getContext(),
						JoinActivity.class);
				startActivityForResult(sign_up_intent, SIGN_UP_REQUEST);
			}
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		/* super.onActivityResult(requestCode, resultCode, data); */
		// Check which request we're responding to
		if (requestCode == SIGN_UP_REQUEST) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				String username = data.getExtras().getString("username");
				String password = data.getExtras().getString("password");
				login(username, password);
			}
		}

	}

}
