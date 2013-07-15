package com.cookcook.main.navi_drawer;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.cookcook.main.R;
//import com.actionbarsherlock.app.SherlockActivity;


import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends SherlockActivity {

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
		
		ListView list  = (ListView)findViewById(R.id.left_drawer);
		list.setAdapter(adapter);
		
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

}
