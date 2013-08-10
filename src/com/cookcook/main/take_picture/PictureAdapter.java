package com.cookcook.main.take_picture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.cookcook.main.R;
import com.cookcook.main.http.RestClient;
import com.cookcook.main.login.Login_Preference;
import com.cookcook.main.sherlockprogressfragment.SherlockProgressFragment;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PictureAdapter extends SherlockActivity{

	Button button;
	Intent takePitureIntent;
	ImageView image;
	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	private static final String CAMERA_DIR = "/dcim/";
	public static  String mCurrentPhotoPath;
	public static Uri mCapturedImageURI;
	/* Photo album for this application */
	private String getAlbumName() {
		return getString(R.string.album_name);
	}
	
	public File getAlbumStorageDir(String albumName) {
		return new File (
				Environment.getExternalStorageDirectory()
				+ CAMERA_DIR
				+ albumName
		);
	}
	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			
			storageDir = getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}
			
		} else {
			Log.v("picture info", "External storage is not mounted READ/WRITE.");
		}
		
		return storageDir;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}
	private String createFileName()
	{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		return imageFileName;
	}

private File setUpPhotoFile() throws IOException {
		
		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();
		
		return f;
}
public void takePictureFromGallery()
{
	Intent intent = new Intent();
	intent.setType("image/*");
	intent.setAction(Intent.ACTION_GET_CONTENT);
	startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
}
public void takePicture()
{
	takePitureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	if(isIntentAvailable())
	{	
		Log.v("====","third");
		dispatchTakePictureIntent();
	}
	else
	{
			Toast.makeText(this, "Your device dont have camera", 1000).show();
	}
}
private void dispatchTakePictureIntent() {

	Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	Log.v("====","4");
		File f = null;
		Log.v("====","5");
		try {
			ContentValues values = new ContentValues();
			values.put(MediaStore.Images.Media.TITLE, createFileName());
			mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

			f = setUpPhotoFile();
			mCurrentPhotoPath = f.getAbsolutePath();
//			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
		} catch (IOException e) {
			e.printStackTrace();
			f = null;
			mCurrentPhotoPath = null;
		}
		Log.v("====","6");
	startActivityForResult(takePictureIntent, 1);
}
public String getRealPathFromURI(Uri contentUri)
{
    try
    {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    catch (Exception e)
    {
        return contentUri.getPath();
    }
}



public boolean isIntentAvailable()
{
		Log.v("====","first");
		List<ResolveInfo> list =  getPackageManager().queryIntentActivities(takePitureIntent,PackageManager.MATCH_DEFAULT_ONLY);
		Log.v("====","second");
		return list.size()>0;
}

public boolean onTouch(View v, MotionEvent event) {
	// TODO Auto-generated method stub
	
	return false;
}

public void PostPictureToServer()
{
	Log.v("post picture","=============111111==========");
	RequestParams params = new RequestParams();
	//	File file = new File("/mnt/sdcard/viber/h.keychain");
	File file = new File(mCurrentPhotoPath);
	Login_Preference preference = Login_Preference.getLogin(this);
	String username =  preference.getString("name", "1");
	String token =  preference.getString("token", "1");
	String device =  preference.getString("device", "1");
	params.put("name", username);
	params.put("token", token);
	params.put("device", device);
	try {
		params.put("files", file);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Log.v("start to upload","ooo1"+params);
	RestClient.post("api/photos", params,
			new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject result) {
					
						Log.v("on success","---");
						try {
							Log.v("info receive when post picture",result.getString("success"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}

			});
	Log.v("post picture","=============222222===========");
}
}