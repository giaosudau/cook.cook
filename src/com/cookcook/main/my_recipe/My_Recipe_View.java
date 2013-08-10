package com.cookcook.main.my_recipe;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.cookcook.main.R;
import com.cookcook.main.database.DBAdapter;
import com.cookcook.main.http.RestClient;
import com.cookcook.main.socialfragment.Helper;
import com.cookcook.main.take_picture.PictureAdapter;
import com.cookcook.main.login.LoginActivity;
import com.cookcook.main.login.Login_Preference;
import com.cookcook.main.login.Login_Success;
import com.cookcook.main.my_recipe.Item;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
public class My_Recipe_View extends PictureAdapter {
	
	View rootView;
	TextView text_category;
	
	ImageView avatar ;
	TextView txt_title;
	RelativeLayout emptyLayout;
	TextView txt_PrepareTime;
	ListView list_require_ingredient;
	ListView list_direction;
	TextView txt_category;
	
	TextView txt_like;
	Button button_add_like;
	ListView list_like;
	
	TextView txt_comment;
	Button button_add_comment;
	ListView list_comment;
	
	TextView txt_photo;
	Button button_add_photo;
	ListView list_photo;
	
	SimpleTextArrayAdapter adapter_required_ingredient;
	SimpleTextArrayAdapter adapter_direction_step;
	
	List<Item> data_required_ingredient;
	List<Item> data_direction_step;
	Login_Preference preference;
	String username ="";
	String token = "";
	String device = "";
//	DBAdapter mDb;
	String recipe_id = "";
	int step =1;
	private Handler mHandler;
	
	public My_Recipe_View(String recipe_id)
	{
		this.recipe_id = recipe_id;
	}
	public My_Recipe_View()
	{
		this.recipe_id = "";
	}
	private Runnable mShowContentRunnable = new Runnable() {

	        @Override
	        public void run() {
	        	setProgressBarIndeterminateVisibility(false);
//	            setContentShown(true);
	        }

	    };
	    
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		recipe_id = bundle.getString("recipe_id");
		Log.v("recipe id to start view","+++++++++++++"+recipe_id);
//		rootView = getLayoutInflater().inflate(R.layout.test_my_recipe_builder, null, false);
		setContentView(R.layout.test_my_recipe_builder);
		
		avatar = (ImageView)findViewById(R.id.avatar_recipe_view);
		txt_title = (TextView)findViewById(R.id.txt_title_recipe_view);
		emptyLayout = (RelativeLayout)findViewById(R.id.empty_relative_layout_recipe_view);
		txt_PrepareTime = (TextView)findViewById(R.id.txt_prepare_time_recipe_view);
		list_require_ingredient = (ListView)findViewById(R.id.list_required_ingredient_view);
		list_direction = (ListView)findViewById(R.id.list_direction_view);
		
		txt_like = (TextView)findViewById(R.id.txt_add_like_recipe_view);
		button_add_like = (Button)findViewById(R.id.button_add_like_recipe_view);
		list_like = (ListView)findViewById(R.id.list_review_recipe_view);
		
		txt_comment = (TextView)findViewById(R.id.txt_add_comment_recipe_view);
		button_add_comment = (Button)findViewById(R.id.button_add_comment_recipe_view);
		list_comment = (ListView)findViewById(R.id.list_comments_recipe_view);
		
		txt_comment = (TextView)findViewById(R.id.txt_add_comment_recipe_view);
		button_add_photo = (Button)findViewById(R.id.button_add_photo_recipe_view);
		list_photo = (ListView)findViewById(R.id.list_photo_recipe_view);
		txt_category = (TextView)findViewById(R.id.txt_category_recipe_view);
		
		data_required_ingredient = new ArrayList<Item>();
        data_direction_step = new ArrayList<Item>();
        
        adapter_required_ingredient = new SimpleTextArrayAdapter(this, data_required_ingredient);
        list_require_ingredient.setAdapter(adapter_required_ingredient);
        
        adapter_direction_step = new SimpleTextArrayAdapter(this, data_direction_step);
        list_direction.setAdapter(adapter_direction_step);
        
//		  Photo
        button_add_photo.setOnClickListener(OnClick_Add_New_Photo);
//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);  
//        setProgressBarIndeterminateVisibility(true);
        obtainData();
	}
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		rootView = inflater.inflate(R.layout.test_my_recipe_builder, container, false);
//		setHasOptionsMenu(true);
//		avatar = (ImageView)rootView.findViewById(R.id.avatar_recipe_view);
//		txt_title = (TextView)rootView.findViewById(R.id.txt_title_recipe_view);
//		emptyLayout = (RelativeLayout)rootView.findViewById(R.id.empty_relative_layout_recipe_view);
//		txt_PrepareTime = (TextView)rootView.findViewById(R.id.txt_prepare_time_recipe_view);
//		list_require_ingredient = (ListView)rootView.findViewById(R.id.list_required_ingredient_view);
//		list_direction = (ListView)rootView.findViewById(R.id.list_direction_view);
//		
//		button_add_like = (Button)rootView.findViewById(R.id.button_add_like_recipe_view);
//		list_like = (ListView)rootView.findViewById(R.id.list_review_recipe_view);
//		
//		button_add_comment = (Button)rootView.findViewById(R.id.button_add_comment_recipe_view);
//		list_comment = (ListView)rootView.findViewById(R.id.list_comments_recipe_view);
//		
//		button_add_photo = (Button)rootView.findViewById(R.id.button_add_photo_recipe_view);
//		list_photo = (ListView)rootView.findViewById(R.id.list_photo_recipe_view);
//		txt_category = (TextView)rootView.findViewById(R.id.txt_category_recipe_view);
//		
//		data_required_ingredient = new ArrayList<Item>();
//        data_direction_step = new ArrayList<Item>();
//        
//        adapter_required_ingredient = new SimpleTextArrayAdapter(getActivity(), data_required_ingredient);
//        list_require_ingredient.setAdapter(adapter_required_ingredient);
//        
//        adapter_direction_step = new SimpleTextArrayAdapter(getActivity(), data_direction_step);
//        list_direction.setAdapter(adapter_direction_step);
//        
////		  Photo
//        button_add_photo.setOnClickListener(OnClick_Add_New_Photo);
////        list_required_ingredient = (ListView)findViewById(R.id.list_required_ingredient_edit);
//      
//        
//
////        Helper.getListViewSize(list_required_ingredient);
//        
//        
////      Direction Step
////        btn_add_direction_step = (Button)findViewById(R.id.btn_add_direction_step_edit_recipe);
////        btn_add_direction_step.setOnClickListener(OnClick_Add_New_Direction_Step);
////        list_direction_step = (ListView)findViewById(R.id.list_direction_step_edit);
//        
//        
//
////        Helper.getListViewSize(list_direction_step);
//		
//		return super.onCreateView(inflater, container, savedInstanceState);
//	}
//	
//	public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        preference = Login_Preference.getLogin(getActivity());
//        username =  preference.getString("name", "1");
//        token =  preference.getString("token", "1");
//        device =  preference.getString("device", "1");
//        Log.v("===token username","name:"+username+";;token::"+token);
//        Take_Picture_From_Every_Where();
//        setContentView(R.layout.example);
//        Button button = (Button)findViewById(R.id.button_example);
//        EditText editText = (EditText)findViewById(R.id.txt_example);
//        
//        button.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.v("start to upload","ooo");
//				
//			}
//		});
////        
        
//    }
	private final OnClickListener OnClick_Add_New_Photo = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Take_Picture_From_Every_Where();
			Log.v("post picture to server","===post");
			
		}
	};
	
//	private void PostPictureToServer()
//	{
//		Log.v("post picture","=============1===========");
//		RequestParams params = new RequestParams();
//		//	File file = new File("/mnt/sdcard/viber/h.keychain");
//		File file = new File(mCurrentPhotoPath);
//		Login_Preference preference = Login_Preference.getLogin(getActivity());
//		String username =  preference.getString("name", "1");
//		String token =  preference.getString("token", "1");
//		String device =  preference.getString("device", "1");
//		params.put("name", username);
//		params.put("token", token);
//		params.put("device", device);
//		try {
//			params.put("files", file);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Log.v("start to upload","ooo1"+params);
//		RestClient.post("api/photos", params,
//				new JsonHttpResponseHandler() {
//					@Override
//					public void onSuccess(JSONObject result) {
//						
////							Log.v("info receive","");
//							Log.v("info receive",result.toString());
//					}
//	
//				});
//		Log.v("post picture","=============2===========");
//	}
	public void Take_Picture_From_Every_Where()
	{
		Log.v("take picture","=============1===========");		
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Take a picture from:");
		final ListView list = new ListView(this);
		adb.setView(list);
		final Dialog dialog = adb.create();
		final String data[] = getResources().getStringArray(R.array.action_capture_picture_array);
		ArrayAdapter<String> adapter_dialog = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
		list.setAdapter(adapter_dialog);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 ==0)
				{
					takePicture();
					Log.v("url",mCurrentPhotoPath);
				}
				else
				{
					takePictureFromGallery();
					Log.v("url",mCurrentPhotoPath);
				}
				dialog.dismiss();
				
				Log.v("take picture","=============2===========");
//				PostPictureToServer();
//				Log.v("=====b=bb=b==b==","choose:"+data[arg2]);
//				ShowInputMeal(day, data[arg2], listview);
			}
		});
		dialog.show();
	}
	
//	@Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        // Setup content view
//        setContentView(rootView);
//        // Setup text for empty content
//        setEmptyText(R.string.empty);
//        obtainData();
//    }
	
	
	private void obtainData() {
        // Show indeterminate progress
//        setContentShown(false);
        if (! recipe_id.equals(""))
          {
        	Log.v("get data of recipe to view","=======");
          	Login_Preference preference = Login_Preference.getLogin(this);
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
  	    						txt_title.setText(name);
  	    						txt_PrepareTime.setText("Prep time is "+prepare_time+" min, server "+serving_number);
//  	    						Time_Serving.setText(serving_number);
  	    						String category ="";
  	    						for(int i=0; i< category_array.length(); i++)
  	    							category+= category_array.getString(i);
  	    						txt_category.setText(category);
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
  	    						if(data_direction_step.size()>0)
  	    						{
  	    							Helper.getListViewSize(list_direction);
  	    						}
  	    						if(data_required_ingredient.size()>0)
  	    						{
  	    							Helper.getListViewSize(list_require_ingredient);
  	    						}
  								
  								
  							} catch (JSONException e) {
  								// TODO Auto-generated catch block
  								e.printStackTrace();
  							}
      					}});
          }
//        mHandler = new Handler();
//        mHandler.postDelayed(mShowContentRunnable, 3000);
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.v("on activity result from picture adapter","------------rq"+requestCode+";;;resultcode:"+resultCode);
		if(resultCode==Activity.RESULT_OK)
		{
			if(requestCode==1)
			{
				Log.v("get in to parse","12222222222222222222222222");
				Log.v("get in to parse11111","12222222222222222222222222");
	//			Bundle item=data.getExtras();
	//				Bitmap bitmap=(Bitmap)item.get("data");
	//				image.setImageBitmap(bitmap	);
	//			Uri _uri = data.getData();
	//			mCurrentPhotoPath = _uri.toString();
				try
				{
	//				if(data !=null)
	//				{
	//					Log.v("data is not null","1111111:::))");
	//				}
					mCurrentPhotoPath = getRealPathFromURI(mCapturedImageURI);
					Log.v("photo path to post to server:::",""+mCurrentPhotoPath);
					Handler mHandler = new Handler();
				    mHandler.postDelayed(mPostPicture, 1000);
	//				PostPictureToServer();
				}
				catch(Exception e)
				{
					Log.v("error::",e.toString());
				}
			}
			
		}
		else if(requestCode == 2 && data != null && data.getData() != null) 
		{
	        Uri _uri = data.getData();
	        mCurrentPhotoPath = _uri.toString();

	        //User had pick an image.
	        Cursor cursor =getContentResolver().query(_uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
	        cursor.moveToFirst();

	        //Link to the image
	        mCurrentPhotoPath = cursor.getString(0);
	        cursor.close();
	    }
	}

	private Runnable mPostPicture = new Runnable() {

	    @Override
	    public void run() {
	    	PostPictureToServer();
	    }

	};
	
}
