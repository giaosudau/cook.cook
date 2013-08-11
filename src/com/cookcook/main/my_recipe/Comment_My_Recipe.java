package com.cookcook.main.my_recipe;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.cookcook.main.database.DBAdapter;
import com.cookcook.main.http.RestClient;
import com.cookcook.main.login.Login_Preference;
import com.cookcook.main.my_recipe.My_Recipe_Edit;
import com.cookcook.main.my_recipe.Item;
import com.cookcook.main.sherlockprogressfragment.SherlockProgressFragment;
import com.cookcook.main.socialfragment.Meal_planner_week_planning_fragment;
import com.cookcook.main.socialfragment.ShoppingListAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
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
public class Comment_My_Recipe extends SherlockActivity {
	
	ListView listview;
	public static ArrayList<Model_Comments> data = new ArrayList<Model_Comments>();
	CommentListAdapter adapter;
	
    protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String[] arr_usernames = bundle.getStringArray("name");
		String[] arr_comments = bundle.getStringArray("comment");
		String[] arr_times = bundle.getStringArray("time");
		
		setContentView(R.layout.my_recipe);
		for(int i=0; i< arr_comments.length; i++)
		{
			String time_slit =arr_times[i].substring(0, 10);
			data.add(new Model_Comments(arr_usernames[i], arr_comments[i], time_slit));
		}
		listview = (ListView) findViewById(R.id.lv_recipe);
		adapter = new CommentListAdapter(this, data);
		listview.setAdapter(adapter);
	}
    
	
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
//				Integer id = cursor.getInt(0);
//				data.add(new RecipeListItem(name, R.drawable.ic_launcher, id));
//				if (! cursor.isLast())
//					cursor.moveToNext();
//			}
//		}
//		mDb.close();
		
//		adapter = new RecipeListAdapter(getActivity(), data);
//		list = (ListView)rootView.findViewById(R.id.lv_recipe);
//		list.setAdapter(adapter);
//		list.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
//				adb.setTitle("Choose action");
//				final ListView list_view = new ListView(getActivity());
//				list_view.setBackgroundResource(R.color.white);
//				adb.setView(list_view);
//				final Dialog dialog = adb.create();
//				final String data[] = getResources().getStringArray(R.array.action_recipe_array);
//				ArrayAdapter<String> adapter_dialog = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
//				list_view.setAdapter(adapter_dialog);
//				
//				list_view.setOnItemClickListener(new OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> arg0, View arg1,
//							int position, long arg3) {
//						// TODO Auto-generated method stub
//						dialog.dismiss();
//						Log.v("=====b=bb=b==b==","choose:"+data[position]);
//						
//						//View
//						if (position == 0)
//						{
//							Intent intentMain = new Intent(getActivity(),My_Recipe_View.class);
//							Log.v("recipe_id", "::"+Comment_My_Recipe.this.data.get(arg2).getRecipe_id());
//							intentMain.putExtra("recipe_id", Comment_My_Recipe.this.data.get(arg2).getRecipe_id());
//							startActivityForResult(intentMain, 0);
//						}
//						
//						//Edit
//						else if (position == 1)
//						{
////							Intent intentMain = new Intent(getActivity(),My_Recipe_Edit.class);
////							Log.v("recipe_id", "::"+My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
////							intentMain.putExtra("recipe_id", My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
////							startActivityForResult(intentMain, 0);
//							Fragment fragment = new My_Recipe_Edit(Comment_My_Recipe.this.data.get(arg2).getRecipe_id());
//							android.support.v4.app.FragmentManager fragmentManager = getSherlockActivity().getSupportFragmentManager();
//						    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
//						}
//						
//						//Remove
//						else
//						{
////							mDb.open();
////							mDb.DeleteRecipe(My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
//							RemoveRecipe(Comment_My_Recipe.this.data.get(arg2).getRecipe_id());
//							Comment_My_Recipe.this.data.remove(arg2);
//							adapter.notifyDataSetChanged();
//							
//						}
//					}
//				});
//				dialog.show();
//			}
//		});
//		return super.onCreateView(inflater, container, savedInstanceState);
//		return rootView;
//	}
//	private void Add_New_Recipe()
//	{
////		Intent intentMain = new Intent(getActivity(),My_Recipe_Edit.class);
////		startActivityForResult(intentMain, 0);
//		Fragment fragment = new My_Recipe_Edit("");
//		android.support.v4.app.FragmentManager fragmentManager = getSherlockActivity().getSupportFragmentManager();
//	    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//	}
	
//	@Override
//	public void onCreateOptionsMenu(Menu menu,com.actionbarsherlock.view.MenuInflater inflater) {
//		// TODO Auto-generated method stub
//		super.onCreateOptionsMenu(menu, inflater);
//		inflater.inflate(R.menu.view_list_recipe, menu);
//	}
	
//	@Override
//	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
//		// TODO Auto-generated method stub
//		Add_New_Recipe();
//		return super.onOptionsItemSelected(item);
//	}
	
//	private void RemoveRecipe(String _id)
//	{
//		Login_Preference preference = Login_Preference.getLogin(getActivity());
//		String username =  preference.getString("name", "1");
//		String token =  preference.getString("token", "1");
//		String device =  preference.getString("device", "1");
//		String account_id =  preference.getString("account_id", "1");
//		RequestParams params = new RequestParams();
//		params.put("name", username);
//		params.put("token", token);
//		params.put("device", device);
//		params.put("account_id", account_id);
//		params.put("_id", _id);
//		Log.v("start to remove recipe","===========remove recipe");
//		RestClient.post("dishes/deletemyreceipt", params,
//				new JsonHttpResponseHandler() {
//					@Override
//					public void onSuccess(JSONObject result) {
//						// TODO Auto-generated method stub
////						super.onSuccess(arg0);
//						Log.v("info received",""+result.toString());
////						try {
////							JSONArray jArray = result.getJSONArray("data");
////							Log.v("array received",""+jArray.toString());
////							for (int i=0; i< jArray.length(); i++)
////							{
////								JSONObject line_object = jArray.getJSONObject(i);
////								String picture = line_object.getString("main_picture");
////								String title = line_object.getString("title");
////								String recipe_id = line_object.getString("_id");
////								data.add(new RecipeListItem(title, R.drawable.ic_launcher, recipe_id));
////								adapter.notifyDataSetChanged();
////							}
////						} catch (JSONException e) {
////							// TODO Auto-generated catch block
////							e.printStackTrace();
////						}
//					}
//			
//		});
////	}
//	private void obtainData() {
//        // Show indeterminate progress
//        setContentShown(false);
//        Login_Preference preference = Login_Preference.getLogin(getActivity());
//		String username =  preference.getString("name", "1");
//		String token =  preference.getString("token", "1");
//		String device =  preference.getString("device", "1");
//		String account_id =  preference.getString("account_id", "1");
//		RequestParams params = new RequestParams();
//		params.put("name", username);
//		params.put("token", token);
//		params.put("device", device);
//		params.put("account_id", account_id);
//		RestClient.post("dishes/myreceipt", params,
//				new JsonHttpResponseHandler() {
//					@Override
//					public void onSuccess(JSONObject result) {
//						// TODO Auto-generated method stub
////						super.onSuccess(arg0);
//						Log.v("info received",""+result.toString());
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
//					}
//			
//		});
//		
//        mHandler = new Handler();
//        mHandler.postDelayed(mShowContentRunnable, 1000);
//    }
//	



	
	
	


}
	
