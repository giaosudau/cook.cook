package com.cookcook.main.socialfragment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.cookcook.main.R;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class LoginFBActivity extends Activity implements OnClickListener {
	Facebook fb;
	ImageView pic, button;
	SharedPreferences sp;
	TextView welcome;
	private AsyncFacebookRunner mAsyncRunner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_facebook);
		String APP_ID = getString(R.string.APP_ID);
		fb= new Facebook(APP_ID);
		
		welcome = (TextView) findViewById(R.id.txt_welcome);
		
		sp = getPreferences(MODE_PRIVATE);
		String access_token = sp.getString("access_token", null);
		long expires = sp.getLong("access_expires", 0);
		if(access_token != null){
			fb.setAccessToken(access_token);
		}
		if(expires != 0){
			fb.setAccessExpires(expires);
		}
		
		
		
		button = (ImageView) findViewById(R.id.login);
		pic = (ImageView) findViewById(R.id.picture_pic);
		button.setOnClickListener(this);
		updateButtonImage();
	}

	private void updateButtonImage() {
		// TODO Auto-generated method stub
		if(fb.isSessionValid()){
			button.setImageResource(R.drawable.logout_button);
			pic.setVisibility(ImageView.VISIBLE);
			JSONObject obj = null;
			URL img_url = null;
			
			String jsonUser;
			try {
				jsonUser = fb.request("me");
				obj = Util.parseJson(jsonUser);
				
				String id = obj.optString("id");
				String name =obj.optString("name");
				String email = obj.optString("email");
				welcome.setText("Welcome " + name+"\nEmail: "+email);
				
				img_url = new URL("http://graph.facebook.com/"+id+"/picture?type=normal");
				Bitmap bmp = BitmapFactory.decodeStream(img_url.openConnection().getInputStream());
				pic.setImageBitmap(bmp);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FacebookError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			button.setImageResource(R.drawable.login_button);
			pic.setVisibility(ImageView.INVISIBLE);
			welcome.setText("Welcome");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(fb.isSessionValid()){
			//button close our session - log out of facebook
			try {
				fb.logout(getApplicationContext());
				updateButtonImage();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			//login to facebook
			fb.authorize(LoginFBActivity.this,new String[] {"email"}, new DialogListener() {
				
				@Override
				public void onFacebookError(FacebookError e) {
					// TODO Auto-generated method stub
					Toast.makeText(LoginFBActivity.this, "fbError", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onError(DialogError e) {
					// TODO Auto-generated method stub
					Toast.makeText(LoginFBActivity.this, "onError", Toast.LENGTH_SHORT).show();
					
				}
				
				@Override
				public void onComplete(Bundle values) {
					// TODO Auto-generated method stub
					Editor editor = sp.edit();
					editor.putString("access_token", fb.getAccessToken());
					editor.putLong("access_expires", fb.getAccessExpires());
					editor.commit();
					updateButtonImage();
				}
				
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					Toast.makeText(LoginFBActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
					
				}
			});
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		fb.authorizeCallback(requestCode, resultCode, data);
	}
	public void PostClicks(View v){
		switch(v.getId()){
			case R.id.btn_post:
				//post
				Bundle params = new Bundle();	
				params.putString("name","Cook.Cook");
				params.putString("caption","Học Nấu Ăn");
				params.putString("description","Nấu nướng là nghệ thuật");
				params.putString("link","https://www.facebook.com/atphanqt");
				params.putString("picture","https://lh3.googleusercontent.com/Dninn90hiAf5HqS7tcOJ2JQNpc6EEnfpuiAKgfCLUCU=s207-p-no");
				fb.dialog(LoginFBActivity.this, "feed",params, new DialogListener() {
				
					@Override
					public void onFacebookError(FacebookError e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onError(DialogError e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onComplete(Bundle values) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onCancel() {
						// TODO Auto-generated method stub
						
					}
				});
				break;
		}
	}
}
