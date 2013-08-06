package com.cookcook.main.my_recipe;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.cookcook.main.socialfragment.Helper;
import com.cookcook.main.login.Login_Preference;
import com.cookcook.main.my_recipe.Item;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
public class My_Recipe_Edit extends SherlockActivity {
	
	TextView text_category;
	EditText Title_of_Recipe, Time_Prepare, Time_Serving;
	Button btn_add_required_ingredient, btn_add_direction_step, btn_add_category_edit, btn_save;
	ListView list_required_ingredient, list_direction_step;
	
	SimpleTextArrayAdapter adapter_required_ingredient;
	SimpleTextArrayAdapter adapter_direction_step;
	
	List<Item> data_required_ingredient;
	List<Item> data_direction_step;
	DBAdapter mDb;
	int recipe_id = -1;
	int step =1;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras()	;
        if (extras != null)
        {
        	 recipe_id = extras.getInt("recipe_id");
        }
        Log.v("====recipe_id ", ";;;;"+ recipe_id);
        setContentView(R.layout.my_recipe_builder);
        mDb = new DBAdapter(getApplicationContext());
        text_category = (TextView)findViewById(R.id.text_view_category);
        Title_of_Recipe = (EditText)findViewById(R.id.txt_title_recipe_edit);
        Time_Prepare = (EditText)findViewById(R.id.txt_time_prepare_edit);
        Time_Serving = (EditText)findViewById(R.id.txt_time_serving_edit);
        
        data_required_ingredient = new ArrayList<Item>();
        data_direction_step = new ArrayList<Item>();
        if (recipe_id != -1)
        {
        	mDb.open();
        	Cursor cursor = mDb.getSelectedRecipe(recipe_id);
        
	        if (cursor.getCount() != 0)
			{
				cursor.moveToFirst();
				String name  = cursor.getString(1);
				String prepare_time  = cursor.getString(2);
				String serving_number  = cursor.getString(3);
				String category  = cursor.getString(4);
				Title_of_Recipe.setText(name);
				Time_Prepare.setText(prepare_time);
				Time_Serving.setText(serving_number);
				text_category.setText(category);
				
				Cursor csr = mDb.getAllIngredient(recipe_id);
				if (csr.getCount() > 0)
				{
					csr.moveToFirst();
					for (int i=0; i< csr.getCount(); i++)
					{
						String name_of_ingredient  = csr.getString(1);
						String amount_of_ingredient  = csr.getString(3);
						data_required_ingredient.add(new RecipeItem(name_of_ingredient, amount_of_ingredient));
						if (! csr.isLast())
							csr.moveToNext();
					}
				}
				
				csr = mDb.getAllDirection(recipe_id);
				if (csr.getCount() > 0)
				{
					csr.moveToFirst();
					for (int i=0; i< csr.getCount(); i++)
					{
						String name_of_direction  = csr.getString(1);
						int step  = csr.getInt(2);
						data_direction_step.add(new RecipeItem(step + "." + name_of_direction, ""));
						if (! csr.isLast())
							csr.moveToNext();
					}
				}
			}
	        
	        mDb.close();
        }
        
//        Require ingredient
        btn_add_required_ingredient = (Button)findViewById(R.id.btn_add_required_ingredient_edit_recipe);
        btn_add_required_ingredient.setOnClickListener(OnClick_Add_New_Required_Ingredient);
        list_required_ingredient = (ListView)findViewById(R.id.list_required_ingredient_edit);
      
        
        adapter_required_ingredient = new SimpleTextArrayAdapter(this, data_required_ingredient);
        list_required_ingredient.setAdapter(adapter_required_ingredient);
        Helper.getListViewSize(list_required_ingredient);
        
        
//      Direction Step
        btn_add_direction_step = (Button)findViewById(R.id.btn_add_direction_step_edit_recipe);
        btn_add_direction_step.setOnClickListener(OnClick_Add_New_Direction_Step);
        list_direction_step = (ListView)findViewById(R.id.list_direction_step_edit);
        
        
        adapter_direction_step = new SimpleTextArrayAdapter(this, data_direction_step);
        list_direction_step.setAdapter(adapter_direction_step);
        Helper.getListViewSize(list_direction_step);
        
        
        btn_add_category_edit = (Button)findViewById(R.id.btn_add_category_edit_recipe);
        
        btn_save = (Button)findViewById(R.id.btn_save_edit_recipe);
        btn_save.setOnClickListener(OnClick_Save);
        
        
    }
	
	@Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        //Used to put dark icons on light action bar
       // boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
        menu.add("Save")
            //.setIcon(isLight ? R.drawable.ic_compose_inverse : R.drawable.ic_compose)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
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
		final Dialog dialog = new Dialog(this);
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
		final Dialog dialog = new Dialog(this);
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
				Save();
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
			mDb.open();
			if( recipe_id != -1)
			{
				mDb.DeleteRecipe(recipe_id);
			}
			if(category.length() == 0)
			{
				category = "";
			}
			long new_recipe_id = mDb.CreateNewRecipe(title, time, serving, category);
			if (data_required_ingredient.size() > 0)
			{
				for ( com.cookcook.main.my_recipe.Item item : data_required_ingredient)
				{
					JSONObject ingredient = new JSONObject();
					ingredient.put("name", item.getName());
					ingredient.put("unit", item.getAmount());
					data_ingredients.put(ingredient);
					mDb.CreateNewIngredient((int)new_recipe_id, item.getName(), item.getAmount());
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
					mDb.CreateNewDirection((int)new_recipe_id, name, Integer.parseInt(pos) );
				}
				
			}
			Log.v("info","Save ok");
			mDb.close();
			
			
			Login_Preference preference = Login_Preference.getLogin(this);
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
			RestClient.post(this, "dishes/create", entity, "application/json",  new JsonHttpResponseHandler()
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
								finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Your recipe can not save at the moment", Toast.LENGTH_SHORT).show();
							}
						}
						
					});
		}
		else
		{
			Toast.makeText(this, "Missing input", 1000).show();
		}
	}
}
