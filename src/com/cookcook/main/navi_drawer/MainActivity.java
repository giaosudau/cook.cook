package com.cookcook.main.navi_drawer;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.cookcook.main.R;
import com.cookcook.main.socialfragment.Meal_planner_fragment;
import com.cookcook.main.socialfragment.Shopping_list_fragment;
//import com.actionbarsherlock.app.SherlockActivity;


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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer);
		List<Item> items = new ArrayList<Item>();
		String[] names  = getResources().getStringArray(R.array.navi_menus_array);
		//Array Icon 
		final Integer[] Icons = {     0,
									  R.drawable.icon_home,
									  R.drawable.icon_search,
									  R.drawable.icon_categ,
									  R.drawable.icon_favorite,
									  R.drawable.icon_news,
									  0,
									  0,
									  R.drawable.icon_news_feed,
									  R.drawable.icon_forum,
									  R.drawable.icon_receipt,
									  R.drawable.icon_meal_plan,
									  R.drawable.icon_shop_list,
									  R.drawable.icon_message,
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
//     update the main content by replacing fragments
//      Bundle args = new Bundle();
//      args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//      fragment.setArguments(args);

      android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

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
}
