package com.cookcook.main.login;

import java.util.ArrayList;
import java.util.List;


import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

import com.cookcook.main.socialfragment.Model;
import com.cookcook.main.socialfragment.ShoppingListAdapter;
import com.cookcook.main.my_recipe.My_Recipe_Edit;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;

import com.cookcook.main.R;
public class Login_Facebook extends Fragment implements OnClickListener {
	ListView list;
	public ArrayList<Model> list_model =new ArrayList<Model>();
	ShoppingListAdapter adapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.login, container, false);
		

				Button btn_login_fb =(Button)rootView.findViewById(R.id.btn_login_facebook);
				btn_login_fb.setOnClickListener(OnClick_Login_Facebook);
				Button btn_login_server = (Button) rootView.findViewById(R.id.btn_login_server);
				btn_login_server.setOnClickListener(OnClick_Login_Server);
		return rootView;
	}

	private final OnClickListener OnClick_Login_Server = new OnClickListener() 
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intentMain = new Intent(v.getContext(),LoginActivity.class);
			startActivityForResult(intentMain, 0);
			
			
		}
	};
	private final OnClickListener OnClick_Login_Facebook = new OnClickListener() 
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intentMain = new Intent(v.getContext(),LoginFBActivity.class);
			startActivityForResult(intentMain, 0);
			
			
		}
		
	
		
	};


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}







	
	
	


}
	
