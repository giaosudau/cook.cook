/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.cookcook.main.navi_drawer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.cookcook.main.R;
import com.cookcook.main.http.RestClient;
import com.cookcook.main.login.Login_Preference;
import com.cookcook.main.my_recipe.My_Recipe_View;
import com.cookcook.main.universal_image_loader.AbsListViewBaseActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
//import com.example.imageloader_demo.Constants.Extra;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class HomeGridActivity extends AbsListViewBaseActivity {

	static ArrayList<String> imageUrls;
	static ArrayList<String> _id;
	ImageAdapter adapter;
	DisplayImageOptions options;
	Handler mHandler;
	ArrayList<JSONObject> list_json;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_grid);

//		Bundle bundle = getIntent().getExtras();
//		imageUrls = bundle.getStringArray(Extra.IMAGES);
//		imageUrls = Constants.IMAGES;
		
		imageUrls = new ArrayList<String>();
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_launcher)
			.showImageOnFail(R.drawable.ic_launcher)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
		listView = (GridView) findViewById(R.id.gridview);
		adapter = new ImageAdapter(this, imageUrls);
		((GridView) listView).setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startViewRecipe(position);
			}
		});
		
		obtainData();
	}

	private void startViewRecipe(int position) {
		Intent intent = new Intent(this, My_Recipe_View.class);
		intent.putExtra("recipe_id", _id.get(position));
		startActivity(intent);
	}

	public class ImageAdapter extends BaseAdapter {
		
		ArrayList<String> URLArray = new ArrayList<String>();
		public ImageAdapter(Context c, ArrayList<String> data)
		{
			URLArray = data;
		}
		
		@Override
		public int getCount() {
			return URLArray.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ImageView imageView;
			if (convertView == null) {
				imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
			} else {
				imageView = (ImageView) convertView;
			}

			imageLoader.displayImage(RestClient.getAbsoluteUrl(URLArray.get(position)), imageView, options);

			return imageView;
		}
	}
	
	private void obtainData() {
        // Show indeterminate progress
//        setContentShown(false);
        Login_Preference preference = Login_Preference.getLogin(this);
		String username =  preference.getString("name", "1");
		String token =  preference.getString("token", "1");
		String device =  preference.getString("device", "1");
		String account_id =  preference.getString("account_id", "1");
		RequestParams params = new RequestParams();
		params.put("name", username);
		params.put("token", token);
		params.put("device", device);
		params.put("_id", account_id);
		RestClient.post("home/get", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject result) {
						// TODO Auto-generated method stub
//						super.onSuccess(arg0);
						Log.v("info received",""+result.toString());
						try {
							JSONArray jArray = result.getJSONArray("dishes");
							Log.v("array received",""+jArray.toString());
							list_json = new ArrayList<JSONObject>();
							for (int i=0; i< jArray.length(); i++)
							{
								list_json.add(jArray.getJSONObject(i));
								JSONObject line_object = jArray.getJSONObject(i);
//								Log.v("line",""+line_object.toString());
//								String picture = line_object.getString("main_picture");
////								String title = line_object.getString("title");
//								String recipe_id = line_object.getString("_id");
//								Log.v("picture", picture);
//								Log.v("recipe_id",recipe_id);
								imageUrls.add(line_object.getString("main_picture"));
//								_id.add(line_object.getString("_id"));
//								data.add(new RecipeListItem(title, picture, recipe_id));
//								adapter.notifyDataSetChanged();
						       
							}
							for(int j=0;j< list_json.size(); j++)
							{
//								Log.v("line",""+list_json.get(i).toString());
//								String picture = list_json.get(i).getString("main_picture");
//								String recipe_id = list_json.get(i).getString("_id");
//								Log.v("picture", picture);
//								Log.v("recipe_id",recipe_id);
								imageUrls.add(list_json.get(j).getString("main_picture"));
//								_id.add(recipe_id);
							}
							
							
							Log.v("picture9999",""+imageUrls.size());
//							Log.v("recipe_id",""+_id.size());
							Log.v("load done info","kjhkj");
//							 mHandler = new Handler();
//						     mHandler.postDelayed(mShowContentRunnable, 1000);
							adapter.notifyDataSetChanged();
//							listView.invalidateViews();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
		});
    }
	private Runnable mShowContentRunnable = new Runnable() {

        @Override
        public void run() {
//        	Log.v("faile","===="+list_json.toString());
        	for(int i=0;i< list_json.size(); i++)
			{
//        		Log.v("fa","=="+list_json.get(i).toString());
//				String recipe_id = list_json.get(i).getString("_id");
//				Log.v("recipe_id",recipe_id);
				try {
					_id.add(list_json.get(i).getString("_id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        	Log.v("faile","====2222");
        	adapter.notifyDataSetChanged();
        }

    };
}