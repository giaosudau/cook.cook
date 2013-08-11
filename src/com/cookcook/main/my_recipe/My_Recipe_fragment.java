package com.cookcook.main.my_recipe;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.cookcook.main.database.DBAdapter;
import com.cookcook.main.http.RestClient;
import com.cookcook.main.login.Login_Preference;
import com.cookcook.main.my_recipe.My_Recipe_Edit;
import com.cookcook.main.my_recipe.Item;
import com.cookcook.main.sherlockprogressfragment.SherlockProgressFragment;
import com.cookcook.main.socialfragment.Meal_planner_week_planning_fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.cookcook.main.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
public class My_Recipe_fragment extends SherlockProgressFragment {
	
	View rootView;
	ListView list;
//	public ArrayList<Model> list_model =new ArrayList<Model>();
	List<Item> data;
	RecipeListAdapter adapter;
//	DBAdapter mDb;
	DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private Handler mHandler;
    private Runnable mShowContentRunnable = new Runnable() {

        @Override
        public void run() {
            setContentShown(true);
        }

    };
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.my_recipe, container, false);
		setHasOptionsMenu(true);
		data = new ArrayList<Item>();
		
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
							Intent intentMain = new Intent(getActivity(),My_Recipe_View.class);
							Log.v("recipe_id", "::"+My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
							intentMain.putExtra("recipe_id", My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
							startActivityForResult(intentMain, 0);
						}
						
						//Edit
						else if (position == 1)
						{
//							Intent intentMain = new Intent(getActivity(),My_Recipe_Edit.class);
//							Log.v("recipe_id", "::"+My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
//							intentMain.putExtra("recipe_id", My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
//							startActivityForResult(intentMain, 0);
							Fragment fragment = new My_Recipe_Edit(My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
							android.support.v4.app.FragmentManager fragmentManager = getSherlockActivity().getSupportFragmentManager();
						    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
						}
						
						//Remove
						else
						{
							RemoveRecipe(My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
							My_Recipe_fragment.this.data.remove(arg2);
							adapter.notifyDataSetChanged();
							
						}
					}
				});
				dialog.show();
			}
		});
		
		 
		return super.onCreateView(inflater, container, savedInstanceState);
//		return rootView;
	}
	private void Add_New_Recipe()
	{
//		Intent intentMain = new Intent(getActivity(),My_Recipe_Edit.class);
//		startActivityForResult(intentMain, 0);
		Fragment fragment = new My_Recipe_Edit("");
		android.support.v4.app.FragmentManager fragmentManager = getSherlockActivity().getSupportFragmentManager();
	    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Setup content view
        setContentView(rootView);
        // Setup text for empty content
        setEmptyText(R.string.empty);
        obtainData();
    }
	private void RemoveRecipe(String _id)
	{
		Login_Preference preference = Login_Preference.getLogin(getActivity());
		String username =  preference.getString("name", "1");
		String token =  preference.getString("token", "1");
		String device =  preference.getString("device", "1");
		String account_id =  preference.getString("account_id", "1");
		RequestParams params = new RequestParams();
		params.put("name", username);
		params.put("token", token);
		params.put("device", device);
		params.put("account_id", account_id);
		params.put("_id", _id);
		Log.v("start to remove recipe","===========remove recipe");
		RestClient.post("dishes/deletemyreceipt", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject result) {
						// TODO Auto-generated method stub
//						super.onSuccess(arg0);
						Log.v("info received",""+result.toString());
//						try {
//							JSONArray jArray = result.getJSONArray("data");
//							Log.v("array received",""+jArray.toString());
//							for (int i=0; i< jArray.length(); i++)
//							{
//								JSONObject line_object = jArray.getJSONObject(i);
//								String picture = line_object.getString("main_picture");
//								String title = line_object.getString("title");
//								String recipe_id = line_object.getString("_id");
//								data.add(new RecipeListItem(title, R.drawable.ic_launcher, recipe_id));
//								adapter.notifyDataSetChanged();
//							}
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					}
			
		});
	}
	private void obtainData() {
        // Show indeterminate progress
        setContentShown(false);
        Login_Preference preference = Login_Preference.getLogin(getActivity());
		String username =  preference.getString("name", "1");
		String token =  preference.getString("token", "1");
		String device =  preference.getString("device", "1");
		String account_id =  preference.getString("account_id", "1");
		RequestParams params = new RequestParams();
		params.put("name", username);
		params.put("token", token);
		params.put("device", device);
		params.put("account_id", account_id);
		RestClient.post("dishes/myreceipt", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject result) {
						// TODO Auto-generated method stub
//						super.onSuccess(arg0);
						Log.v("info received",""+result.toString());
						try {
							JSONArray jArray = result.getJSONArray("data");
							Log.v("array received",""+jArray.toString());
							for (int i=0; i< jArray.length(); i++)
							{
								JSONObject line_object = jArray.getJSONObject(i);
								String picture = line_object.getString("main_picture");
								String title = line_object.getString("title");
								String recipe_id = line_object.getString("_id");
								data.add(new RecipeListItem(title, picture, recipe_id));
								adapter.notifyDataSetChanged();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			
		});
		
        mHandler = new Handler();
        mHandler.postDelayed(mShowContentRunnable, 1000);
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent datas) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, datas);
		data.clear();
//		mDb.open();
////		mDb.CreateNewRecipe("title", "time", "serving", "category");
//		Cursor cursor = mDb.getAllRecipe();
//		Log.v("\\\\\\","count:"+cursor.getCount());
//		if (cursor.getCount() != 0)
//		{
//			cursor.moveToFirst();
//			for (int i=0; i< cursor.getCount(); i++)
//			{
//				String name  = cursor.getString(1);
//				String id = cursor.getString(0);
//				data.add(new RecipeListItem(name, R.drawable.ic_launcher, id));
//				if (! cursor.isLast())
//					cursor.moveToNext();
//			}
//		}
//		mDb.close();
		adapter.notifyDataSetChanged();
//		onCreateView(getLayoutInflater(null), null, null);
	}
	







	
	
	


}
	
