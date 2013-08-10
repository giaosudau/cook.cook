package com.cookcook.main.socialfragment;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.cookcook.main.R;
import com.cookcook.main.database.DBAdapter;

public class Meal_planner_fragment extends SherlockFragment{

	public static ArrayList<String> list_data =new ArrayList<String>();
	public static ArrayList<Integer> list_week =new ArrayList<Integer>();
	ListView list;
	ArrayAdapter<String> adapter;
	DBAdapter mDb;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.meal_planner_layout, container, false);
		
		//Button Add New Week
		Button btn_add_new_week =(Button)rootView.findViewById(R.id.btn_add_new_week);
		btn_add_new_week.setOnClickListener(OnClick_Add_New_Week);
		list = (ListView)rootView.findViewById(R.id.list_week_meal_planner);
		
		list_data =new ArrayList<String>();
		list_week =new ArrayList<Integer>();
		mDb = new DBAdapter(getActivity());
		mDb.open();
		Cursor cursor = mDb.getWeekMealPlanners();
		if (cursor.getCount() != 0)
		{
			cursor.moveToFirst();
			for (int i=0; i< cursor.getCount(); i++)
			{
				Log.v("====shop_create===", ""+ cursor.getPosition());
				String name  = cursor.getString(0);
				Integer check = cursor.getInt(1);
				list_data.add(name);
				list_week.add(check);
				if (! cursor.isLast())
					cursor.moveToNext();
			}
		}
		mDb.close();
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_data);
		list.setAdapter(adapter);
		list.setOnItemClickListener(onItemClick);
		list.setOnItemLongClickListener(onLongItemClick);
		
        return rootView;
	}

	private final OnClickListener OnClick_Add_New_Week = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
//			adb.setTitle();
			adb.setMessage("Add new week plan");
			
			//Set view EditText on Dialog
			final EditText input =new EditText(getActivity());
			adb.setView(input);
			
			//View Button Ok, Cancel
			adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String value = input.getText().toString();
					if (value.length() > 0)
					{
						if (!list_data.contains(value))
						{
							mDb.open();
							mDb.CreateWeekMealPlanner(value);
							Cursor cursor = mDb.getSelectedWeekMealPlanners(value);
							if (cursor.getCount() != 0)
							{
								cursor.moveToFirst();
								Integer check = cursor.getInt(0);
								Log.v("====new week===",""+check+ "week:"+cursor.getString(1) + "week_id:"+cursor.getString(0));
								list_week.add(check);
							}
							mDb.close();
							list_data.add(value);
							adapter.notifyDataSetChanged();
						}
						else
						{
							Log.v("===mess===","dismiss ");
							dialog.dismiss();
						}
					}
					else
					{
						dialog.dismiss();
					}
				}
			});
			
			adb.setNegativeButton("Cancel", null);
			adb.show();
		}
	};
	
	private OnItemClickListener onItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Log.v("====move===", "week:"+ list_week.get(arg2));
			Fragment fragment = new Meal_planner_week_planning_fragment(list_week.get(arg2));
			android.support.v4.app.FragmentManager fragmentManager = getSherlockActivity().getSupportFragmentManager();
		    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
		    Log.v("====done move===", "week:"+ list_week.get(arg2));
		}
	};
	
	private OnItemLongClickListener onLongItemClick = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			// TODO Auto-generated method stub
			mDb.open();
			mDb.deleteWeekMealPlanner(list_data.get(position));
			mDb.close();
			list_data.remove(position);
			list_week.remove(position);
			adapter.notifyDataSetChanged();
			return true;
		}
	};
	
		
}
