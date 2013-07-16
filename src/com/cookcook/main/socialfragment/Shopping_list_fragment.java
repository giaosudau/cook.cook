package com.cookcook.main.socialfragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.cookcook.main.R;

public class Shopping_list_fragment extends SherlockFragment{

	ListView list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.shopping_list_layout, container, false);
//		list = (ListView)rootView.findViewById(R.id.list_shopping_list);
//		list.setOnItemClickListener(new Shopping_List_Item_Listener());
        return rootView;
	}
	
	
	
}
