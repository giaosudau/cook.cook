package com.cookcook.main.my_recipe;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.cookcook.main.universal_image_loader.ImagePagerActivity;
import com.cookcook.main.login.LoginActivity;
import com.cookcook.main.login.Login_Preference;
import com.cookcook.main.login.Login_Success;
import com.cookcook.main.my_recipe.Item;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
public class My_Recipe_View extends PictureAdapter {
	
	View rootView;
	TextView text_category;
	
	ImageView avatar ;
	TextView txt_title;
	RelativeLayout emptyLayout;
	Button btn_change_avatar;
	TextView txt_PrepareTime;
	ListView list_require_ingredient;
	ListView list_direction;
	TextView txt_category;
	
	TextView txt_like;
	Button button_add_like;
	TextView list_like;
	
	TextView txt_comment;
	Button button_add_comment;
	ListView list_comment;
	
	TextView txt_photo;
	Button button_add_photo;
	
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
	DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	int isMyRecipe=0;
	int isAvatar;
	String prepare_time;
	String serving_number;
	String category_local;
	ArrayList<String> picture_array_local;
	String main_picture_local;
	
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
		picture_array_local = new ArrayList<String>();
		
		avatar = (ImageView)findViewById(R.id.avatar_recipe_view);
		txt_title = (TextView)findViewById(R.id.txt_title_recipe_view);
		emptyLayout = (RelativeLayout)findViewById(R.id.empty_relative_layout_recipe_view);
		btn_change_avatar = (Button)findViewById(R.id.btn_changeAvatarRecipeView);
		btn_change_avatar.setOnClickListener(OnClick_ChangeAvatar);
		
		txt_PrepareTime = (TextView)findViewById(R.id.txt_prepare_time_recipe_view);
		list_require_ingredient = (ListView)findViewById(R.id.list_required_ingredient_view);
		list_direction = (ListView)findViewById(R.id.list_direction_view);
		
		txt_like = (TextView)findViewById(R.id.txt_add_like_recipe_view);
		button_add_like = (Button)findViewById(R.id.button_add_like_recipe_view);
		button_add_like.setOnClickListener(OnClick_Add_Like);
		list_like = (TextView)findViewById(R.id.txt_list_like_recipe_view);
		
		txt_comment = (TextView)findViewById(R.id.txt_add_comment_recipe_view);
		button_add_comment = (Button)findViewById(R.id.button_add_comment_recipe_view);
		list_comment = (ListView)findViewById(R.id.list_comments_recipe_view);
		
		txt_photo = (TextView)findViewById(R.id.txt_add_photo_recipe_view);
		txt_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] arr = picture_array_local.toArray(new String[picture_array_local.size()]);
				Intent intent = new Intent(getApplicationContext(), ImagePagerActivity.class);
				intent.putExtra("IMAGES", arr);
				startActivity(intent);
			}
		});
		button_add_photo = (Button)findViewById(R.id.button_add_photo_recipe_view);
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
        
        options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_empty)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
//        imageLoader.displayImage("http://dicho2.aws.af.cm/photos/20333d356be86b1CAM00155.jpg", avatar, options);
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
	private final OnClickListener OnClick_ChangeAvatar = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.v("click change avatar:",""+isMyRecipe);
			if( isMyRecipe ==1)
			{
				isAvatar = 1;
				Take_Picture_From_Every_Where();
			}
			
			
		}
	};
	private final OnClickListener OnClick_Add_New_Photo = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(isMyRecipe ==1)
			{
				isAvatar = 0;
				Take_Picture_From_Every_Where();
				Log.v("post picture to server","===post");
			}
			
			
		}
	};
	
	private final OnClickListener OnClick_Add_Like = new OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			UpdateLike();
			Log.v("like receipt","===post");
			
		}
	};
	
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
//					Log.v("url",mCurrentPhotoPath);
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
	
	
	
	private void obtainData() {
        // Show indeterminate progress
//        setContentShown(false);
        if (! recipe_id.equals(""))
          {
        	Log.v("get data of recipe to view","=======");
          	Login_Preference preference = Login_Preference.getLogin(this);
      		final String username =  preference.getString("name", "1");
      		String token =  preference.getString("token", "1");
      		String device =  preference.getString("device", "1");
      		final String _id =  recipe_id;
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
  	    						prepare_time  = result.getString("time_prepare");
  	    						serving_number  = String.valueOf(result.getInt("eat_number"));
  	    						JSONArray category_array  = result.getJSONArray("categories");
  	    						JSONArray ingredient_array  = result.getJSONArray("ingredients");
  	    						JSONArray direction_array  = result.getJSONArray("steps");
  	    						JSONArray picture_array  = result.getJSONArray("picture");
  	    						JSONArray like_array  = result.getJSONArray("likes");
  	    						txt_title.setText(name);
  	    						txt_PrepareTime.setText("Prep time is "+prepare_time+" min, server "+serving_number);
//  	    						Time_Serving.setText(serving_number);
  	    						String category ="";
  	    						for(int i=0; i< category_array.length(); i++)
  	    							category+= category_array.getString(i);
  	    						txt_category.setText(category);
  	    						category_local=category;
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
  	    						if(ingredient_length >0 )
  	    						{
	  	    						for(i=0; i< ingredient_length; i+=1)
	//  	    							Log.v("where=============",""+i);
	//  	    							Log.v("info of each line", ""+ingredient_array.getJSONObject(i));
	//  	    							JSONObject line_obj = ingredient_array.getJSONObject(i);
	  	    							data_required_ingredient.add(new RecipeItem(ingredient_array.getJSONObject(i).getString("name"), ingredient_array.getJSONObject(i).getString("unit")));
  	    						}
	  	    					Log.v("2xxxxxxxxxx", ""+ingredient_array.length());
  	    						if(data_direction_step.size()>0)
  	    						{
  	    							Helper.getListViewSize(list_direction);
  	    						}
  	    						if(data_required_ingredient.size()>0)
  	    						{
  	    							Helper.getListViewSize(list_require_ingredient);
  	    						}
  	    						JSONObject created_by = result.getJSONObject("created_by");
  	    						String name_user_get = created_by.getString("name");
  	    						if(picture_array.length()>0)
  	    						{
  	    							for(i=0; i< picture_array.length(); i+=1)
  	    							{
  	    								picture_array_local.add(picture_array.get(i).toString());
  	    							}
  	    							txt_photo.setText(picture_array_local.size()+ " Photo");
//  	    							imageLoader.displayImage(picture_array_local.get(0), avatar, options);
  	    						}
  	    						String like_person="";
  	    						if(like_array.length()>0)
  	    						{
  	    							for(i=0; i< like_array.length(); i+=1)
  	    							{
  	    								if(i < 10)
  	    								{
  	    									like_person = like_person + ", " + like_array.getJSONObject(i).getString("name");
  	    								}
  	    							}
  	    							txt_like.setText(like_array.length() +" Like");
  	    							list_like.setText(like_person);
//  	    							imageLoader.displayImage(picture_array_local.get(0), avatar, options);
  	    						}
  	    						main_picture_local = result.getString("main_picture");
  	    						imageLoader.displayImage(main_picture_local, avatar, options);
  								if(username.equals(name_user_get))
  								{
  									isMyRecipe=1;
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
			ImageURIGetted = "";
			if(requestCode==1)
			{
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
					new LoadPictureTask().execute();
//					Handler mHandler = new Handler();
//				    mHandler.postDelayed(mPostPicture, 1000);
	//				PostPictureToServer();
				}
				catch(Exception e)
				{
					Log.v("error::",e.toString());
				}
			}
			else if(requestCode == 2) 
			{
		        Uri _uri = data.getData();
		        mCurrentPhotoPath = _uri.toString();
		        //User had pick an image.
		        Cursor cursor =getContentResolver().query(_uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
		        cursor.moveToFirst();

		        //Link to the image
		        mCurrentPhotoPath = cursor.getString(0);
		        Log.v("get in galery2:::","link::::"+mCurrentPhotoPath);
		        cursor.close();
		        new LoadPictureTask().execute();
//		        Handler mHandler = new Handler();
//			    mHandler.postDelayed(mPostPicture, 1000);
		    }
//			imageLoader.displayImage(imageUrls[position], imageView, options);
		}
		
	}

//	private Runnable mPostPicture = new Runnable() {
//
//	    @Override
//	    public void run() {
//	    	PostPictureToServer();
//	    }
//
//	};
	private class LoadPictureTask extends AsyncTask<Void, Void, Void>{
        
		String typeStatus;
		ProgressDialog Asycdialog = new ProgressDialog(My_Recipe_View.this);

        @Override
        protected void onPreExecute() {
            //set message of the dialog
            Asycdialog.setMessage("Loading");
            //show dialog
            Asycdialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

        	PostPictureToServer();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //hide the dialog
        	Handler mHandler = new Handler();
		    mHandler.postDelayed(mWaitForPicture, 1000);
//            Asycdialog.dismiss();

            super.onPostExecute(result);
        }
        private Runnable mWaitForPicture = new Runnable() {

    	    @Override
    	    public void run() {
    	    	if (ImageURIGetted.equals(""))
    	    	{
    	    		Handler mHandler = new Handler();
    			    mHandler.postDelayed(mWaitForPicture, 1000);
    	    	}
    	    	else
    	    	{
    	    		 Asycdialog.dismiss();
    	    		 Toast.makeText(My_Recipe_View.this, "get image:"+ImageURIGetted, 1000).show();
    	    		 if (isAvatar ==1)
    	    		 {
    	    			 main_picture_local = ImageURIGetted;
    	    			 imageLoader.displayImage(RestClient.getAbsoluteUrl(ImageURIGetted), avatar, options);
    	    		 }
    	    		 else
    	    		 {
    	    			 picture_array_local.add(RestClient.getAbsoluteUrl(ImageURIGetted));
    	    			 txt_photo.setText(picture_array_local.size()+ " Photo");
    	    		 }
    	    		 
    	    		 Handler mHandler = new Handler();
     			     mHandler.postDelayed(mUpdate, 1000);
    	    		 
    	    	}
    	    }

    	};

	}
	private Runnable mUpdate = new Runnable() {

	    @Override
	    public void run() {
	    	try {
				Update();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.v("error unsupportted",""+e.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.v("error exception",""+e.toString());
				e.printStackTrace();
			}
	    }

	};
	
	private void Update() throws JSONException, UnsupportedEncodingException 
	{
		ArrayList<Map<String, String>> data_category = new ArrayList<Map<String,String>>();
		JSONArray data_ingredients = new JSONArray();
		JSONArray steps = new JSONArray();
		String title = txt_title.getText().toString();
		String time = prepare_time;
		String serving = serving_number;
		String category = category_local;
		if (!( title.equals("") || time.equals("") || serving.equals("") ))
		{
			if(category.length() == 0)
			{
				category = "";
			}
			if (data_required_ingredient.size() > 0)
			{
				for ( com.cookcook.main.my_recipe.Item item : data_required_ingredient)
				{
					JSONObject ingredient = new JSONObject();
					ingredient.put("name", item.getName());
					ingredient.put("unit", item.getAmount());
					data_ingredients.put(ingredient);
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
				}
				
			}
			Log.v("info","update ok");
			Login_Preference preference = Login_Preference.getLogin(this);
			String username =  preference.getString("name", "1");
			String token =  preference.getString("token", "1");
			String device =  preference.getString("device", "1");
			
			JSONObject parent = new JSONObject();
			JSONArray picture = new JSONArray();
			if(picture_array_local.size()>0)
			{
				for(int i=0; i< picture_array_local.size(); i++)
				{
					picture.put(picture_array_local.get(i));
				}
			}
			parent.put("name", username);
			parent.put("token", token);
			parent.put("device", device);
			parent.put("_id", recipe_id);
			parent.put("title", title);
			parent.put("description", title);
			parent.put("time_prepare", time);
			parent.put("eat_number", serving);
			parent.put("picture", picture);
			parent.put("main_picture", main_picture_local);
			parent.put("steps", steps);
			parent.put("ingredients", data_ingredients);
			Log.v("post data",""+ parent.toString());
			StringEntity entity = new StringEntity(parent.toString());
			RestClient.post(this, "dishes/update", entity, "application/json",  new JsonHttpResponseHandler()
					{
						@Override
						public void onSuccess(JSONObject result) {
							// TODO Auto-generated method stub
							Log.v("info receive",result.toString());
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
	
	private void UpdateLike()
	{
		Login_Preference preference = Login_Preference.getLogin(this);
		String username =  preference.getString("name", "1");
		String token =  preference.getString("token", "1");
		String device =  preference.getString("device", "1");
		String account_id =  preference.getString("account_id", "1");
		RequestParams params = new RequestParams();
		params.put("name", username);
		params.put("token", token);
		params.put("device", device);
		params.put("account_id", account_id);
		params.put("_id", recipe_id);
		RestClient.post("dishes/like", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject result) {
						Log.v("info received",""+result.toString());
						try {
							Log.v("dish",""+result.getJSONObject("dishes").getJSONObject("likes").toString());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
		);
	}
}
