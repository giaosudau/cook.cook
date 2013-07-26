package com.cookcook.main.socialfragment;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.cookcook.main.R;

public class Meal_planner_week_planning_fragment extends SherlockFragment{

	public static ArrayList<String> list_data =new ArrayList<String>();
	ListView list_mon;
	ListView list_tue;
	ListView list_wed;
	ListView list_thu;
	ListView list_fri;
	ListView list_sat;
	ListView list_sun;
	public ArrayList<SimpleTextArrayAdapter>  adapter_day = new ArrayList<SimpleTextArrayAdapter>();
//	TwoTextArrayAdapter adapter_mon;
//	TwoTextArrayAdapter adapter_tue;
//	TwoTextArrayAdapter adapter_wed;
//	TwoTextArrayAdapter adapter_thu;
//	TwoTextArrayAdapter adapter_fri;
//	TwoTextArrayAdapter adapter_sat;
//	TwoTextArrayAdapter adapter_sun;
	public ArrayList<List<Item>> items = new ArrayList<List<Item>>();
//	List<Item> items_mon = new ArrayList<Item>();
//	List<Item> items_tue = new ArrayList<Item>();
//	List<Item> items_wed = new ArrayList<Item>();
//	List<Item> items_thu = new ArrayList<Item>();
//	List<Item> items_fri = new ArrayList<Item>();
//	List<Item> items_sat = new ArrayList<Item>();
//	List<Item> items_sun = new ArrayList<Item>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.meal_planner_week_planning_layout, container, false);
		
		for( int i=0; i<7 ;i++)
		{
			items.add(new ArrayList<Item>());
			adapter_day.add(new SimpleTextArrayAdapter(getActivity(), items.get(i)));
		}
		int pos = 0;
		//Monday
		Button btn_add_item_meal_planner_mon =(Button)rootView.findViewById(R.id.btn_add_item_meal_planner_mon);
		btn_add_item_meal_planner_mon.setOnClickListener(OnClick_Add_New_Monday_Plan);
		list_mon = (ListView)rootView.findViewById(R.id.week_planning_list_mon);
		adapter_day.set(pos, new SimpleTextArrayAdapter(getActivity(), items.get(pos))) ;
	    list_mon.setAdapter(adapter_day.get(pos));
	    
	    pos+=1;
		//Tuesday
		Button btn_add_item_meal_planner_tue =(Button)rootView.findViewById(R.id.btn_add_item_meal_planner_tue);
		btn_add_item_meal_planner_tue.setOnClickListener(OnClick_Add_New_Tuesday_Plan);
		list_tue = (ListView)rootView.findViewById(R.id.week_planning_list_tue);
		adapter_day.set(pos, new SimpleTextArrayAdapter(getActivity(), items.get(pos))) ;
	    list_tue.setAdapter(adapter_day.get(pos));
	    
	    pos+=1;
		//Wednesday
		Button btn_add_item_meal_planner_wed =(Button)rootView.findViewById(R.id.btn_add_item_meal_planner_wed);
		btn_add_item_meal_planner_wed.setOnClickListener(OnClick_Add_New_Wednesday_Plan);
		list_wed = (ListView)rootView.findViewById(R.id.week_planning_list_wed);
		adapter_day.set(pos, new SimpleTextArrayAdapter(getActivity(), items.get(pos))) ;
	    list_wed.setAdapter(adapter_day.get(pos));
	    
	    pos+=1;
		//Thursday
		Button btn_add_item_meal_planner_thu =(Button)rootView.findViewById(R.id.btn_add_item_meal_planner_thu);
		btn_add_item_meal_planner_thu.setOnClickListener(OnClick_Add_New_Thursday_Plan);
		list_thu = (ListView)rootView.findViewById(R.id.week_planning_list_thu);
		adapter_day.set(pos, new SimpleTextArrayAdapter(getActivity(), items.get(pos))) ;
	    list_thu.setAdapter(adapter_day.get(pos));
	    
	    pos+=1;
		//Friday
		Button btn_add_item_meal_planner_fri =(Button)rootView.findViewById(R.id.btn_add_item_meal_planner_fri);
		btn_add_item_meal_planner_fri.setOnClickListener(OnClick_Add_New_Friday_Plan);
		list_fri = (ListView)rootView.findViewById(R.id.week_planning_list_fri);
		adapter_day.set(pos, new SimpleTextArrayAdapter(getActivity(), items.get(pos))) ;
	    list_fri.setAdapter(adapter_day.get(pos));
	    
	    pos+=1;
		//Saturday
		Button btn_add_item_meal_planner_sat =(Button)rootView.findViewById(R.id.btn_add_item_meal_planner_sat);
		btn_add_item_meal_planner_sat.setOnClickListener(OnClick_Add_New_Saturday_Plan);
		list_sat = (ListView)rootView.findViewById(R.id.week_planning_list_sat);
		adapter_day.set(pos, new SimpleTextArrayAdapter(getActivity(), items.get(pos))) ;
		list_sat.setAdapter(adapter_day.get(pos));
	    
		pos+=1;
		//Sunday
		Button btn_add_item_meal_planner_sun =(Button)rootView.findViewById(R.id.btn_add_item_meal_planner_sun);
		btn_add_item_meal_planner_sun.setOnClickListener(OnClick_Add_New_Sunday_Plan);
		list_sun = (ListView)rootView.findViewById(R.id.week_planning_list_sun);
		adapter_day.set(pos, new SimpleTextArrayAdapter(getActivity(), items.get(pos))) ;
		list_sun.setAdapter(adapter_day.get(pos));
	    
//		list.setOnItemClickListener(onItemClick);
//		ListView list_week = (ListView)rootView.findViewById(R.id.week_planning_list_all_week);
//		MealPlanAdapter adapter = new MealPlanAdapter(getActivity(), getResources().getStringArray(R.array.week_array));
//		list_week.setAdapter(adapter);
		return rootView;
	}

	private final OnClickListener OnClick_Add_New_Monday_Plan = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShowTimeMeal("Monday", list_mon);
		}
	};
	private final OnClickListener OnClick_Add_New_Tuesday_Plan = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShowTimeMeal("Tuesday", list_tue);
		}
	};
	private final OnClickListener OnClick_Add_New_Wednesday_Plan = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShowTimeMeal("Wednesday", list_wed);
		}
	};
	private final OnClickListener OnClick_Add_New_Thursday_Plan = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShowTimeMeal("Thursday", list_thu);
		}
	};
	private final OnClickListener OnClick_Add_New_Friday_Plan = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShowTimeMeal("Friday", list_fri);
		}
	};
	private final OnClickListener OnClick_Add_New_Saturday_Plan = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShowTimeMeal("Saturday", list_sat);
		}
	};
	private final OnClickListener OnClick_Add_New_Sunday_Plan = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShowTimeMeal("Sunday", list_sun);
		}
	};
	
	//Show list view to choose what time of the day 
	private void ShowTimeMeal(final String day, final ListView listview)
	{
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
		adb.setTitle("Choose occasion");
//		adb.setMessage("Choose occasion");
		
		//Set view EditText on Dialog
//		final EditText input =new EditText(getActivity());
		final ListView list_view = new ListView(getActivity());
		list_view.setBackgroundResource(R.color.white);
		adb.setView(list_view);
		final Dialog dialog = adb.create();
		final String data[] = getResources().getStringArray(R.array.meal_plan_array);
		ArrayAdapter<String> adapter_dialog = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
		list_view.setAdapter(adapter_dialog);
		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Log.v("=====b=bb=b==b==","choose:"+data[arg2]);
				ShowInputMeal(day, data[arg2], listview);
			}
		});
		dialog.show();
	}
	
	//SHow intput area for meal
	private void ShowInputMeal(final String day, final String time, final ListView listview)
	{
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
		adb.setMessage("Plan Description");
		final EditText input =new EditText(getActivity());
		input.setHint("Type the recipe name here");
		adb.setView(input);
		//View Button Ok, Cancel
		adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String value = input.getText().toString();
				Log.v("=====b=bb=b==b==","refresh:"+value+";;day:"+day+";;time:"+time);
				RefreshData(value, day, time, listview);
			}
		});
		
		adb.setNegativeButton("Cancel", null);
		adb.show();
		
	}
	
	
	//Refresh List view
	private void RefreshData(String value, String day, String time, ListView listview)
	{
		//Data to  load in listview
		List<Item> data_item = new ArrayList<Item>();
		int day_number;
		if (day == "Monday")
		{
			day_number = 0;
		}
		else if (day == "Tuesday")
		{
			day_number = 1;
		}
		else if (day == "Wednesday")
		{
			day_number = 2;
		}
		else if (day == "Thursday")
		{
			day_number = 3;
		}
		else if (day == "Friday")
		{
			day_number = 4;
		}
		else if (day == "Saturday")
		{
			day_number = 5;
		}
		else
		{
			day_number = 6;
		}
		data_item = items.get(day_number);
		if (data_item.size() >0)
		{
			int header_pos = -1;
			int add_pos = -1;
			for (int i= 0; i< data_item.size(); i++)
			{
				if (data_item.get(i).getViewType() == 1 && data_item.get(i).getName().equals(time))
				{
					header_pos = i;
					for (int j = i+1; j< data_item.size(); j++)
					{
						if (data_item.get(j).getViewType() == 1)
						{
							add_pos = j;
							break;
						}
					}
					if (add_pos == -1)
					{
						add_pos = data_item.size();
					}
					break;
				}
			}
			//Add new header, add new meal
			if (header_pos == -1)
			{
				data_item.add(new TextHeader(time));
				data_item.add(new TextListItem(value));
			}
			//add new meal under header
			else
			{
				data_item.add(add_pos, new TextListItem(value));
			}
			items.set(day_number, data_item);
//			adapter_day.set(day_number, new SimpleTextArrayAdapter(getActivity(), data_item));
			adapter_day.get(day_number).notifyDataSetChanged();
		}
		else
		{
			data_item.add(new TextHeader(time));
			data_item.add(new TextListItem(value));
			items.set(day_number, data_item);
//			adapter_day.set(day_number, new SimpleTextArrayAdapter(getActivity(), data_item));
			adapter_day.get(day_number).notifyDataSetChanged();
		}
		Helper.getListViewSize(listview);
		
	}
//	private OnItemClickListener onItemClick = new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			// TODO Auto-generated method stub
//			Fragment fragment = new Meal_planner_week_planning_fragment();
//			android.support.v4.app.FragmentManager fragmentManager = getSherlockActivity().getSupportFragmentManager();
//		    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//		}
//	};
	
		
}
