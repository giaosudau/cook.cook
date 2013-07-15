package com.cookcook.main.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cookcook.main.R;
import com.cookcook.main.http.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class JoinActivity extends Activity {

	EditText field_screen_name_sign_up;
	EditText field_username_sign_up;
	EditText field_email_sign_up;
	EditText field_password_sign_up;
	Button btn_join;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_an_account);
		
		field_screen_name_sign_up = (EditText) findViewById(R.id.field_screen_name_sign_up);
		field_username_sign_up = (EditText) findViewById(R.id.field_username_sign_up);
		field_email_sign_up = (EditText) findViewById(R.id.field_email_sign_up);
		field_password_sign_up = (EditText) findViewById(R.id.field_password_sign_up);
		btn_join = (Button) findViewById(R.id.btn_join);
		
		
		
		btn_join.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String str_screen_name = field_screen_name_sign_up.getText()
						.toString();
				final String str_username = field_username_sign_up.getText()
						.toString();
				String str_email = field_email_sign_up.getText().toString();
				final String str_password = field_password_sign_up.getText()
						.toString();

				Toast.makeText(
						getApplicationContext(),
						str_screen_name + "*--" + str_username + "*--"
								+ str_email + "*--" + str_password,
						Toast.LENGTH_SHORT).show();
				if (str_screen_name.trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please Enter Screen Name", Toast.LENGTH_SHORT)
							.show();
				} else if (str_username.trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please Enter User Name", Toast.LENGTH_SHORT)
							.show();

				} else if (str_email.trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please Enter Email", Toast.LENGTH_SHORT).show();

				} else if (str_password.trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please Enter Password", Toast.LENGTH_SHORT).show();
				} else {
					RequestParams params = new RequestParams();
					params.put("screen_name", str_screen_name);
					params.put("name", str_username);
					params.put("email", str_email);
					params.put("password", str_password);
					RestClient.post("auth/register", params,
							new JsonHttpResponseHandler() {
								@Override
								public void onSuccess(JSONObject result) {
									try {
										if(result.get("info") == "success"){
											Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
											startActivityForResult(myIntent, 0);
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Toast.makeText(getApplicationContext(),
											result.toString(),
											Toast.LENGTH_LONG).show();
									Intent intent = getIntent();
									intent.putExtra("register", "OK");
									intent.putExtra("name", str_username);
									intent.putExtra("password", str_password);
									setResult(RESULT_OK, intent);
									finish();
									
								}
							});
				}

			}
		});

	}
}
