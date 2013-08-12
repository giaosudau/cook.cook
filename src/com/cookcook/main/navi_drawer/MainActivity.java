package com.cookcook.main.navi_drawer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.cookcook.main.R;
import com.cookcook.main.database.DBAdapter;
import com.cookcook.main.http.RestClient;
import com.cookcook.main.login.LoginActivity;
import com.cookcook.main.login.Login_Facebook;
import com.cookcook.main.login.Login_Preference;
import com.cookcook.main.profile.Profile;
import com.cookcook.main.my_recipe.My_Recipe_View;
import com.cookcook.main.my_recipe.My_Recipe_fragment;
import com.cookcook.main.socialfragment.Meal_planner_fragment;
import com.cookcook.main.socialfragment.Shopping_list_fragment;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
//import com.actionbarsherlock.app.SherlockActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends SherlockFragmentActivity {

	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	private DBAdapter mDb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer);
		List<Item> items = new ArrayList<Item>();
		String[] names  = getResources().getStringArray(R.array.navi_menus_array);
		//Array Icon 
		final Integer[] Icons = {     0,
									  R.drawable.ic_action_home,
									  R.drawable.ic_action_search,
									  R.drawable.ic_action_categ,
									  R.drawable.icon_favorite_light32,
									  R.drawable.ic_action_news,
									  0,
									  R.drawable.ic_action_chief,
									  R.drawable.ic_action_news_feed,
									  R.drawable.ic_action_user,
									  R.drawable.ic_action_my_recipe,
									  R.drawable.ic_action_meal_plan,
									  R.drawable.ic_action_shop_list,
									  R.drawable.ic_action_mail,
									  R.drawable.icon_setting,
									  R.drawable.icon_love_app
						
									};
		//Section Find Stuff
		items.add(new Header(names[0]));
		for(int i=1; i <= 5 ; i++)
		{
			items.add(new ListItem(names[i], Icons[i]));
		}
		//Section Social
		items.add(new Header(names[6]));
		
		//Your nick
		items.add(new ListItem(names[7], Icons[7]));
		
		for(int i=8; i <= 14 ; i++)
		{
			items.add(new ListItem(names[i], Icons[i]));
		}
		TwoTextArrayAdapter adapter = new TwoTextArrayAdapter(getApplicationContext(), items);
		
		mDrawerList  = (ListView)findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new Drawer_List_Item_Listener());
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
//		mDb = new DBAdapter(this);
//		mDb.open();
		//mDb.DeleteAll();
//		mDb.CreateShoppingList("com", 0);
//		mDb.CreateShoppingList("canh", 1);
//		mDb.deleteShoppingList("canh");
//		mDb.ModifyShoppingList("canh", 0);
//		mDb.CreateWeekMealPlanner("week1");
//		mDb.deleteWeekMealPlanner("week1");
		
//		mDb.CreateNewRecipe("com", 10, 2, true, "");
//		Cursor cursor = mDb.getSelectedRecipe("com");
//		int id=0;
//		if (cursor.getCount() != 0)
//		{
//			cursor.moveToFirst();
//			Log.v("====get recipe id===", ""+ cursor.getPosition());
//			id = cursor.getInt(0);
//		}
//    	if (id != 0)
//    	{
//    		Log.v("insert to recipe", "insert");
//    		mDb.CreateNewIngredient(id, "nguyen lieu", 1, 0);
//    		Log.v("insert to recipe", "insert1");
//    		mDb.CreateNewIngredient(id, "ca", 1, 1);
//    		Log.v("insert to recipe", "insert2");
//    		mDb.CreateNewDirection(id, "nau len", 1);
//    		Log.v("update to recipe", "update");
//    		mDb.ModifyIngredient(id, "ca kho to", 1, 1);
//    		Log.v("update to recipe", "update1");
//    		mDb.ModifyDirection(id, "nuong len", 1);
//    		Log.v("update to recipe", "update2");
//    		mDb.ModifyRecipe(id, "com ga", 9, 3, false, "1");
//    		Log.v("delete  recipe", "delete");
//    		mDb.DeleteRecipe(id);
//    	}
//		mDb.close();
		obtainData();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.main, (com.actionbarsherlock.view.Menu) menu);
		return true;
	}
	private void selectItem(int position)
	{
		Log.v("You press item:", ""+position);
		Fragment fragment = null;
		if (position ==1)
		{
			Intent intentMain = new Intent(this,HomeGridActivity.class);
//			intentMain.putExtra("recipe_id", My_Recipe_fragment.this.data.get(arg2).getRecipe_id());
			startActivity(intentMain);
		}
		if (position == 11)
		{
			//Choose meal planner
			fragment = new Meal_planner_fragment();
		}
		else if (position == 12)
		{
			//Choose shopping list
			fragment = new Shopping_list_fragment();
//		    Bundle args = new Bundle();
		}
		else if (position == 10)
		{
			fragment = new My_Recipe_fragment();
		}
		else if(position == 9)
		{
			fragment = new Login_Facebook();
		}
		else if(position == 7)
		{
			fragment = new Profile();
		}
		else
		{
			return;
		}
//     update the main content by replacing fragments
//      Bundle args = new Bundle();
//      args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//      fragment.setArguments(args);

      android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

      // update selected item and title, then close the drawer
//      mDrawerList.setItemChecked(position, true);
//      setTitle(mPlanetTitles[position]);
//      mDrawerLayout.closeDrawer(mDrawerList);
	}

	 private class Drawer_List_Item_Listener implements ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            selectItem(position);
	        }
	    }
	 
	 private void obtainData() {
	        Login_Preference preference = Login_Preference.getLogin(this);
			String username =  preference.getString("name", "1");
			String token =  preference.getString("token", "1");
			String device =  preference.getString("device", "1");
			if(token.equals("1"))
			{
				Intent intentMain = new Intent(getApplicationContext(),LoginActivity.class);
				startActivityForResult(intentMain, 0);
			}
			RequestParams params = new RequestParams();
			params.put("name", username);
			params.put("token", token);
			params.put("device", device);
			Log.v("info post",params.toString());
			RestClient.post("auth/autologin", params,
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject result) {
//							Log.v("result",""+result.toString());
							if(! result.has("info"))
							{
								Intent intentMain = new Intent(getApplicationContext(),LoginActivity.class);
								startActivityForResult(intentMain, 0);
							}
						}
			});
	    }
}
