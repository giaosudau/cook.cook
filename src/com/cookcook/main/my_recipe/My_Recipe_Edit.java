package com.cookcook.main.my_recipe;
import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.cookcook.main.R;
import com.cookcook.main.database.DBAdapter;
import com.cookcook.main.navi_drawer.TwoTextArrayAdapter;
import com.cookcook.main.socialfragment.Helper;
import com.cookcook.main.socialfragment.Item;
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
						data_required_ingredient.add(new RecipeItem(name_of_ingredient));
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
						data_direction_step.add(new RecipeItem(step + "." + name_of_direction));
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
		dialog.setContentView(R.layout.dialog_new_required_ingredient);
		final EditText txt_name = (EditText)dialog.findViewById(R.id.txt_amount_name_of_required_ingredient_edit);
		Button btn_add = (Button)dialog.findViewById(R.id.btn_add_new_required_ingredient_edit);
		Button btn_close = (Button)dialog.findViewById(R.id.btn_close_new_required_ingredient_edit);
		btn_add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = txt_name.getText().toString();
				if (! name.equals("") )
				{
					data_required_ingredient.add(new RecipeItem(name));
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
					data_direction_step.add(new RecipeItem(step + "." + name));
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
			Save();
		}
	};

	private void Save()
	{
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
				for ( Item item : data_required_ingredient)
				{
					mDb.CreateNewIngredient((int)new_recipe_id, item.getName());
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
					}
					mDb.CreateNewDirection((int)new_recipe_id, name, Integer.parseInt(pos) );
				}
				
			}
			Log.v("info","Save ok");
			mDb.close();
			finish();
		}
		else
		{
			Toast.makeText(this, "Missing input", 1000).show();
		}
	}
}
