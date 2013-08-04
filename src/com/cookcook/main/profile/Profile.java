package com.cookcook.main.profile;

import com.cookcook.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Profile extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.profile, container, false);
		
		//Button Add Item
				//Button btn_add_recipe =(Button)rootView.findViewById(R.id.p);
				//btn_add_recipe.setOnClickListener(OnClick_Add_Recipe);
				//list = (ListView)rootView.findViewById(R.id.list_recipe);
		
		return rootView;
	}

}
