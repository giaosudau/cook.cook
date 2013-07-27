package com.cookcook.main.socialfragment;

import java.util.ArrayList;
import java.util.List;


import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.cookcook.main.socialfragment.My_Recipe_Builder;

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
public class My_Recipe_fragment extends Fragment implements OnClickListener {
	ListView list;
	public ArrayList<Model> list_model =new ArrayList<Model>();
	ShoppingListAdapter adapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.my_recipe, container, false);
		
		//Button Add Item
				Button btn_add_recipe =(Button)rootView.findViewById(R.id.btn_add_recipe);
				btn_add_recipe.setOnClickListener(OnClick_Add_Recipe);
				//list = (ListView)rootView.findViewById(R.id.list_recipe);
		
		return rootView;
	}

	
	private final OnClickListener OnClick_Add_Recipe = new OnClickListener() 
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intentMain = new Intent(v.getContext(),My_Recipe_Builder.class);
			startActivityForResult(intentMain, 0);
			
			
		}
		
	
		
	};


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}







	
	
	


}
	
