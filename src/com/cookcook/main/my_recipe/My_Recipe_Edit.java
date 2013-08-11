package com.cookcook.main.my_recipe;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.cookcook.main.R;
import com.cookcook.main.database.DBAdapter;
import com.cookcook.main.http.RestClient;
import com.cookcook.main.sherlockprogressfragment.SherlockProgressFragment;
import com.cookcook.main.socialfragment.Helper;
import com.cookcook.main.login.Login_Preference;
import com.cookcook.main.my_recipe.Item;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
public class My_Recipe_Edit extends SherlockProgressFragment {
	
	TextView text_category;
	EditText Title_of_Recipe, Time_Prepare, Time_Serving;
	Button btn_add_required_ingredient, btn_add_direction_step, btn_add_category_edit, btn_save;
	ListView list_required_ingredient, list_direction_step;
	
	SimpleTextArrayAdapter adapter_required_ingredient;
	SimpleTextArrayAdapter adapter_direction_step;
	
	List<Item> data_required_ingredient;
	List<Item> data_direction_step;
//	DBAdapter mDb;
	String recipe_id = "";
	View rootView;
	int step =1;
	private Handler mHandler;
	
	public My_Recipe_Edit(String recipe_id)
	{
		this.recipe_id = recipe_id;
	}
	
    private Runnable mShowContentRunnable = new Runnable() {

        @Override
        public void run() {
            setContentShown(true);
        }

    };
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.my_recipe_builder, container, false);
		setHasOptionsMenu(true);
		text_category = (TextView)rootView.findViewById(R.id.text_view_category);
        Title_of_Recipe = (EditText)rootView.findViewById(R.id.txt_title_recipe_edit);
        Time_Prepare = (EditText)rootView.findViewById(R.id.txt_time_prepare_edit);
        Time_Serving = (EditText)rootView.findViewById(R.id.txt_time_serving_edit);
        
        data_required_ingredient = new ArrayList<Item>();
        data_direction_step = new ArrayList<Item>();
        
//      Require ingredient
	      btn_add_required_ingredient = (Button)rootView.findViewById(R.id.btn_add_required_ingredient_edit_recipe);
	      btn_add_required_ingredient.setOnClickListener(OnClick_Add_New_Required_Ingredient);
	      list_required_ingredient = (ListView)rootView.findViewById(R.id.list_required_ingredient_edit);
	    
	      
	      adapter_required_ingredient = new SimpleTextArrayAdapter(getActivity(), data_required_ingredient);
	      list_required_ingredient.setAdapter(adapter_required_ingredient);
	//      if (data_required_ingredient.size() >0)
//	      {
//	      	Helper.getListViewSize(list_required_ingredient);
//	      }
	      
	      
	//    Direction Step
	      btn_add_direction_step = (Button)rootView.findViewById(R.id.btn_add_direction_step_edit_recipe);
	      btn_add_direction_step.setOnClickListener(OnClick_Add_New_Direction_Step);
	      list_direction_step = (ListView)rootView.findViewById(R.id.list_direction_step_edit);
	      
	      
	      adapter_direction_step = new SimpleTextArrayAdapter(getActivity(), data_direction_step);
	      list_direction_step.setAdapter(adapter_direction_step);
	//      if (data_direction_step.size() >0)
//	      {
//	      	Helper.getListViewSize(list_direction_step);
//	      }
	      
	      
	      btn_add_category_edit = (Button)rootView.findViewById(R.id.btn_add_category_edit_recipe);
	      
	      btn_save = (Button)rootView.findViewById(R.id.btn_save_edit_recipe);
	      btn_save.setOnClickListener(OnClick_Save);
	      return super.onCreateView(inflater, container, savedInstanceState);
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
	
	private void obtainData() {
        // Show indeterminate progress
        setContentShown(false);
        if (! recipe_id.equals(""))
          {
          	Login_Preference preference = Login_Preference.getLogin(getSherlockActivity());
      		String username =  preference.getString("name", "1");
      		String token =  preference.getString("token", "1");
      		String device =  preference.getString("device", "1");
      		String _id =  recipe_id;
      		RequestParams params = new RequestParams();
      		params.put("name", username);
      		params.put("token", token);
      		params.put("device", device);
      		params.put("_id", _id);
      		RestClient.post("dishes/get", params,
      				new JsonHttpResponseHandler() {
      					@Override
      					public void onSuccess(JSONObject result) {
      						Log.v("info received",""+result.toString());
      						
  							try {
  								String name = result.getString("title");
  	    						String prepare_time  = result.getString("time_prepare");
  	    						String serving_number  = String.valueOf(result.getInt("eat_number"));
  	    						JSONArray category_array  = result.getJSONArray("categories");
  	    						JSONArray ingredient_array  = result.getJSONArray("ingredients");
  	    						JSONArray direction_array  = result.getJSONArray("steps");
  	    						Title_of_Recipe.setText(name);
  	    						Time_Prepare.setText(prepare_time);
  	    						Time_Serving.setText(serving_number);
  	    						String category ="";
  	    						for(int i=0; i< category_array.length(); i++)
  	    							category+= category_array.getString(i);
  	    						text_category.setText(category);
  	    						Log.v("ingredient array",""+ingredient_array.toString());
  	    						Log.v("direction_array",""+direction_array.toString());
  	    						
  	    						int step =0;
  	    						final int direction_length = direction_array.length();
  	    						if (direction_length >0)
  	    						{
  	    							
	  	    						for(int kkj=0; kkj< direction_length; kkj++)
//	  	    							step+=1;
//	  	    							Log.v("11111111111111111111direction",""+direction_array.getString(kkj));
//	  	    							String line_str = direction_array.getString(j);
//	  	    							Log.v("fffuffuf",""+line_str);
	  									data_direction_step.add(new RecipeItem(step + ". " + direction_array.getString(kkj), ""));
  	    						}
								int i;
  	    						Log.v("1xxxxxxxxxx", ""+ingredient_array.length());
  	    						final int ingredient_length = ingredient_array.length();
  	    						for(i=0; i< ingredient_length; i+=1)
//  	    							Log.v("where=============",""+i);
//  	    							Log.v("info of each line", ""+ingredient_array.getJSONObject(i));
//  	    							JSONObject line_obj = ingredient_array.getJSONObject(i);
  	    							data_required_ingredient.add(new RecipeItem(ingredient_array.getJSONObject(i).getString("unit"), ingredient_array.getJSONObject(i).getString("name")));
  	    						Log.v("2xxxxxxxxxx", ""+ingredient_array.length());
  								Helper.getListViewSize(list_required_ingredient);
  								Helper.getListViewSize(list_direction_step);
  							} catch (JSONException e) {
  								// TODO Auto-generated catch block
  								e.printStackTrace();
  							}
      					}});
          }
        mHandler = new Handler();
        mHandler.postDelayed(mShowContentRunnable, 1000);
    }
	
	private final OnClickListener OnClick_Add_New_Required_Ingredient = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Show_Dialog_Add_Required_Ingredient();
		}
	};
	
	
	private void Show_Dialog_Add_Required_Ingredient()
	{
		final Dialog dialog = new Dialog(getActivity());
		dialog.setTitle("Create new required ingredient");
		dialog.setContentView(R.layout.dialog_new_required_ingredient2);
		final EditText txt_name = (EditText)dialog.findViewById(R.id.txt_amount_name_of_required_ingredient_edit);
		final EditText txt_amount = (EditText)dialog.findViewById(R.id.txt_amount_of_required_ingredient_edit);
		Button btn_add = (Button)dialog.findViewById(R.id.btn_add_new_required_ingredient_edit);
		Button btn_close = (Button)dialog.findViewById(R.id.btn_close_new_required_ingredient_edit);
		btn_add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = txt_name.getText().toString();
				String amount = txt_amount.getText().toString();
				if (! (name.equals("") || amount.equals("")) )
				{
					data_required_ingredient.add(new RecipeItem(name, amount));
					adapter_required_ingredient.notifyDataSetChanged();
					Helper.getListViewSize(list_required_ingredient);
					dialog.dismiss();
					
				}
			}
		});
		btn_close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	
	private final OnClickListener OnClick_Add_New_Direction_Step = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Show_Dialog_Add_New_Direction_Step();
		}
	};
	private void Show_Dialog_Add_New_Direction_Step()
	{
		final Dialog dialog = new Dialog(getActivity());
		dialog.setTitle("Create new direction step");
		dialog.setContentView(R.layout.dialog_new_required_ingredient);
		final EditText txt_name = (EditText)dialog.findViewById(R.id.txt_amount_name_of_required_ingredient_edit);
		txt_name.setHint("step");
		Button btn_add = (Button)dialog.findViewById(R.id.btn_add_new_required_ingredient_edit);
		Button btn_close = (Button)dialog.findViewById(R.id.btn_close_new_required_ingredient_edit);
		btn_add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = txt_name.getText().toString();
				if (! name.equals("") )
				{
					data_direction_step.add(new RecipeItem(step + "." + name, ""));
					step++;
					adapter_direction_step.notifyDataSetChanged();
					Helper.getListViewSize(list_direction_step);
					dialog.dismiss();
					
				}
			}
		});
		btn_close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	
	private final OnClickListener OnClick_Save = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				if(recipe_id.equals(""))
				{
					Save();
				}
				else
				{
					Update();
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	private void Save() throws JSONException, UnsupportedEncodingException
	{
		ArrayList<Map<String, String>> data_category = new ArrayList<Map<String,String>>();
		
		JSONArray data_ingredients = new JSONArray();
		JSONArray steps = new JSONArray();
		String title = Title_of_Recipe.getText().toString();
		String time = Time_Prepare.getText().toString();
		String serving = Time_Serving.getText().toString();
		String category = text_category.getText().toString();
		if (!( title.equals("") || time.equals("") || serving.equals("") ))
		{
//			mDb.open();
//			if( recipe_id != -1)
//			{
//				mDb.DeleteRecipe(recipe_id);
//			}
			if(category.length() == 0)
			{
				category = "";
			}
//			long new_recipe_id = mDb.CreateNewRecipe(title, time, serving, category);
			if (data_required_ingredient.size() > 0)
			{
				for ( com.cookcook.main.my_recipe.Item item : data_required_ingredient)
				{
					JSONObject ingredient = new JSONObject();
					ingredient.put("name", item.getName());
					ingredient.put("unit", item.getAmount());
					data_ingredients.put(ingredient);
//					mDb.CreateNewIngredient((int)new_recipe_id, item.getName(), item.getAmount());
				}
				
			}
			if (data_direction_step.size() > 0)
			{
				for ( Item item : data_direction_step)
				{
					String name = item.getName();
					String[] info = name.split("\\.");
					String pos ="";
					if (info.length >= 2)
					{
						pos = info[0];
						name = "";
						for (int i=1; i< info.length; i++)
						{
							name += info[i];
						}
						steps.put(name);
					}
//					mDb.CreateNewDirection((int)new_recipe_id, name, Integer.parseInt(pos) );
				}
				
			}
			Log.v("info","Save ok");
//			mDb.close();
			
			
			Login_Preference preference = Login_Preference.getLogin(getActivity());
			String username =  preference.getString("name", "1");
			String token =  preference.getString("token", "1");
			String device =  preference.getString("device", "1");
			
			JSONObject parent = new JSONObject();
			JSONArray picture = new JSONArray();
			parent.put("name", username);
			parent.put("token", token);
			parent.put("device", device);
			parent.put("title", title);
			parent.put("description", title);
			parent.put("time_prepare", time);
			parent.put("eat_number", serving);
			parent.put("picture", picture);
			parent.put("main_picture", picture);
			parent.put("steps", steps);
			parent.put("ingredients", data_ingredients);
			Log.v("post data",""+ parent.toString());
			StringEntity entity = new StringEntity(parent.toString());
			RestClient.post(getActivity(), "dishes/create", entity, "application/json",  new JsonHttpResponseHandler()
					{
						@Override
						public void onSuccess(JSONObject result) {
							// TODO Auto-generated method stub
							Log.v("info receive",result.toString());
							try {
								Log.v("info",result.getString("created_by"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (result.has("created_at"))
							{
//								finish();
								Fragment fragment = new My_Recipe_fragment();
								android.support.v4.app.FragmentManager fragmentManager = getSherlockActivity().getSupportFragmentManager();
							    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
							}
							else
							{
								Toast.makeText(getSherlockActivity(), "Your recipe can not save at the moment", Toast.LENGTH_SHORT).show();
							}
						}
						
					});
		}
		else
		{
			Toast.makeText(getSherlockActivity(), "Missing input", 1000).show();
		}
	}

	private void Update() throws JSONException, UnsupportedEncodingException 
	{
		ArrayList<Map<String, String>> data_category = new ArrayList<Map<String,String>>();
		
		JSONArray data_ingredients = new JSONArray();
		JSONArray steps = new JSONArray();
		String title = Title_of_Recipe.getText().toString();
		String time = Time_Prepare.getText().toString();
		String serving = Time_Serving.getText().toString();
		String category = text_category.getText().toString();
		if (!( title.equals("") || time.equals("") || serving.equals("") ))
		{
//			mDb.open();
//			if( recipe_id != -1)
//			{
//				mDb.DeleteRecipe(recipe_id);
//			}
			if(category.length() == 0)
			{
				category = "";
			}
//			long new_recipe_id = mDb.CreateNewRecipe(title, time, serving, category);
			if (data_required_ingredient.size() > 0)
			{
				for ( com.cookcook.main.my_recipe.Item item : data_required_ingredient)
				{
					JSONObject ingredient = new JSONObject();
					ingredient.put("name", item.getName());
					ingredient.put("unit", item.getAmount());
					data_ingredients.put(ingredient);
//					mDb.CreateNewIngredient((int)new_recipe_id, item.getName(), item.getAmount());
				}
				
			}
			if (data_direction_step.size() > 0)
			{
				for ( Item item : data_direction_step)
				{
					String name = item.getName();
					String[] info = name.split("\\.");
					String pos ="";
					if (info.length >= 2)
					{
						pos = info[0];
						name = "";
						for (int i=1; i< info.length; i++)
						{
							name += info[i];
						}
						steps.put(name);
					}
//					mDb.CreateNewDirection((int)new_recipe_id, name, Integer.parseInt(pos) );
				}
				
			}
			Log.v("info","update ok");
//			mDb.close();
			
			
			Login_Preference preference = Login_Preference.getLogin(getActivity());
			String username =  preference.getString("name", "1");
			String token =  preference.getString("token", "1");
			String device =  preference.getString("device", "1");
			
			JSONObject parent = new JSONObject();
			JSONArray picture = new JSONArray();
			parent.put("name", username);
			parent.put("token", token);
			parent.put("device", device);
			parent.put("_id", recipe_id);
			parent.put("title", title);
			parent.put("description", title);
			parent.put("time_prepare", time);
			parent.put("eat_number", serving);
			parent.put("picture", picture);
			parent.put("main_picture", picture);
			parent.put("steps", steps);
			parent.put("ingredients", data_ingredients);
			Log.v("post data",""+ parent.toString());
			StringEntity entity = new StringEntity(parent.toString());
			RestClient.post(getActivity(), "dishes/update", entity, "application/json",  new JsonHttpResponseHandler()
					{
						@Override
						public void onSuccess(JSONObject result) {
							// TODO Auto-generated method stub
							Log.v("info receive",result.toString());
//							try {
//								Log.v("info",result.getString("created_by"));
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
							if (result.has("info"))
							{
//								finish();
								try {
									if (result.getString("info").equals("success"))
									{
//										android.support.v4.app.FragmentManager fragmentManager = getSherlockActivity().getSupportFragmentManager();
//										super.onb
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else
							{
								Toast.makeText(getSherlockActivity(), "Your recipe can not save at the moment", Toast.LENGTH_SHORT).show();
							}
						}
						
					});
		}
		else
		{
			Toast.makeText(getSherlockActivity(), "Missing input", 1000).show();
		}
	}
	
//	public void on
}
