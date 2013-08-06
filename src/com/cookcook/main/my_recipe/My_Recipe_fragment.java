package com.cookcook.main.my_recipe;

import java.util.ArrayList;
import java.util.List;


import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.cookcook.main.database.DBAdapter;
import com.cookcook.main.my_recipe.My_Recipe_Edit;
import com.cookcook.main.my_recipe.Item;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.cookcook.main.R;
public class My_Recipe_fragment extends SherlockFragment {
	ListView list;
//	public ArrayList<Model> list_model =new ArrayList<Model>();
	List<Item> data;
	RecipeListAdapter adapter;
	DBAdapter mDb;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.my_recipe, container, false);
		setHasOptionsMenu(true);
		data = new ArrayList<Item>();
		mDb = new DBAdapter(getActivity());
		mDb.open();
//		mDb.CreateNewRecipe("title", "time", "serving", "category");
		Cursor cursor = mDb.getAllRecipe();
		Log.v("\\\\\\","count:"+cursor.getCount());
		if (cursor.getCount() != 0)
		{
			cursor.moveToFirst();
			for (int i=0; i< cursor.getCount(); i++)
			{
				String name  = cursor.getString(1);
				Integer id = cursor.getInt(0);
				data.add(new RecipeListItem(name, R.drawable.ic_launcher, id));
				if (! cursor.isLast())
					cursor.moveToNext();
			}
		}
		mDb.close();
		
		adapter = new RecipeListAdapter(getActivity(), data);
		list = (ListView)rootView.findViewById(R.id.lv_recipe);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
				adb.setTitle("Choose action");
				final ListView list_view = new ListView(getActivity());
				list_view.setBackgroundResource(R.color.white);
				adb.setView(list_view);
				final Dialog dialog = adb.create();
				final String data[] = getResources().getStringArray(R.array.action_recipe_array);
				ArrayAdapter<String> adapter_dialog = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
				list_view.setAdapter(adapter_dialog);
				
				list_view.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Log.v("=====b=bb=b==b==","choose:"+data[position]);
						
						//View
						if (position == 0)
						{
							Log.v("----","view");
							Intent intentMain = new Intent(getActivity(),My_Recipe_View.class);
							Log.v("recipe_id", "::"+My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
							intentMain.putExtra("recipe_id", My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
							startActivityForResult(intentMain, 0);
						}
						
						//Edit
						else if (position == 1)
						{
							Intent intentMain = new Intent(getActivity(),My_Recipe_Edit.class);
							Log.v("recipe_id", "::"+My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
							intentMain.putExtra("recipe_id", My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
							startActivityForResult(intentMain, 0);
						}
						
						//Remove
						else
						{
							mDb.open();
							mDb.DeleteRecipe(My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
							My_Recipe_fragment.this.data.remove(arg2);
							adapter.notifyDataSetChanged();
						}
					}
				});
				dialog.show();
			}
		});
		
		return rootView;
	}
	private void Add_New_Recipe()
	{
		Intent intentMain = new Intent(getActivity(),My_Recipe_Edit.class);
		startActivityForResult(intentMain, 0);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu,com.actionbarsherlock.view.MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.view_list_recipe, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		Add_New_Recipe();
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent datas) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, datas);
		data.clear();
		mDb.open();
//		mDb.CreateNewRecipe("title", "time", "serving", "category");
		Cursor cursor = mDb.getAllRecipe();
		Log.v("\\\\\\","count:"+cursor.getCount());
		if (cursor.getCount() != 0)
		{
			cursor.moveToFirst();
			for (int i=0; i< cursor.getCount(); i++)
			{
				String name  = cursor.getString(1);
				Integer id = cursor.getInt(0);
				data.add(new RecipeListItem(name, R.drawable.ic_launcher, id));
				if (! cursor.isLast())
					cursor.moveToNext();
			}
		}
		mDb.close();
		adapter.notifyDataSetChanged();
//		onCreateView(getLayoutInflater(null), null, null);
	}
	







	
	
	


}
	
