package com.cookcook.main.take_picture;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.cookcook.main.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
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
			f = setUpPhotoFile();
			mCurrentPhotoPath = f.getAbsolutePath();
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		} catch (IOException e) {
			e.printStackTrace();
			f = null;
			mCurrentPhotoPath = null;
		}
		Log.v("====","6");
	startActivityForResult(takePictureIntent, 1);
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	if(requestCode==1 && data != null && data.getData() != null)
	{
		if(resultCode==Activity.RESULT_OK)
		{
//			Bundle item=data.getExtras();
//			Bitmap bitmap=(Bitmap)item.get("data");
//			image.setImageBitmap(bitmap	);
			Uri _uri = data.getData();
			mCurrentPhotoPath = _uri.toString();
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
}