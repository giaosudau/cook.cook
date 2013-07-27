package com.cookcook.main.database;

import  com.cookcook.main.database.DatabaseHelper.DAILY_MEAL_PLANNER_ENTRY;
import  com.cookcook.main.database.DatabaseHelper.SHOPPING_LIST_ENTRY;
import  com.cookcook.main.database.DatabaseHelper.WEEK_MEAL_PLANNER_ENTRY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {

	public DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private Context mContext;
	private static String TABLE_SHOPPING_LIST;
	private static String TABLE_WEEK_MEAL_PLANNER;
	private static String TABLE_DAILY_MEAL_PLANNER;
	public DBAdapter(Context ctx){
		this.mContext = ctx;
	}
	
	public DBAdapter open()
	{
		mDbHelper=new DatabaseHelper(mContext);
		mDb=mDbHelper.getWritableDatabase();
		
		TABLE_SHOPPING_LIST 	 = SHOPPING_LIST_ENTRY.TABLE_NAME;
		TABLE_WEEK_MEAL_PLANNER  = WEEK_MEAL_PLANNER_ENTRY.TABLE_NAME;
		TABLE_DAILY_MEAL_PLANNER = DAILY_MEAL_PLANNER_ENTRY.TABLE_NAME;
		return this;
	}
	
    public void close()
    {
    	mDbHelper.close();
    }
    
    //******************************************************
    //******************************************************
    //******************************************************
    //******************************************************
    //Function for shopping list
    public long CreateShoppingList(String name, Integer selected)
    {
    	ContentValues values = new ContentValues();
    	values.put(SHOPPING_LIST_ENTRY.COLLUMN_NAME      , name);
    	values.put(SHOPPING_LIST_ENTRY.COLLUMN_SELECTED  , selected);
    	return mDb.insert(TABLE_SHOPPING_LIST, null,values);
    }
    
    public Boolean deleteShoppingList(String name)
    {
    	return mDb.delete(TABLE_SHOPPING_LIST, SHOPPING_LIST_ENTRY.COLLUMN_NAME + " LIKE " + "\"" + name + "\"",null)>0;
    }
    
    public Cursor getShoppingList()
    {
    	return mDb.query(TABLE_SHOPPING_LIST, new String[] { SHOPPING_LIST_ENTRY.COLLUMN_NAME, SHOPPING_LIST_ENTRY.COLLUMN_SELECTED}, null,null, null, null, null);
    }
    public Boolean ModifyShoppingList(String name, Integer selected)
    {
    	ContentValues values = new ContentValues();
    	values.put(SHOPPING_LIST_ENTRY.COLLUMN_NAME 	 , name);
    	values.put(SHOPPING_LIST_ENTRY.COLLUMN_SELECTED  , selected);
    	
    	String selection = SHOPPING_LIST_ENTRY.COLLUMN_NAME + "= " +  "\"" + name + "\"";
    	int count =  mDb.update(TABLE_SHOPPING_LIST, values, selection, null);
    	if (count > 0)
    		return Boolean.TRUE;
    	return Boolean.FALSE;
    }
  
  //******************************************************
  //******************************************************
  //******************************************************
  //******************************************************
    //Function for week_meal_planner
    public long CreateWeekMealPlanner(String name)
    {
    	ContentValues values = new ContentValues();
    	values.put(WEEK_MEAL_PLANNER_ENTRY.COLLUMN_NAME , name);
    	return mDb.insert(TABLE_WEEK_MEAL_PLANNER, null,values);
    }
    
    public Boolean deleteWeekMealPlanner(String name)
    {
    	return mDb.delete(TABLE_WEEK_MEAL_PLANNER, WEEK_MEAL_PLANNER_ENTRY.COLLUMN_NAME + " LIKE" + "\""   + name + "\"",null)>0;
    }
    
    public Cursor getWeekMealPlanners()
    {
    	return mDb.query(TABLE_WEEK_MEAL_PLANNER, new String[] { WEEK_MEAL_PLANNER_ENTRY.COLLUMN_NAME, WEEK_MEAL_PLANNER_ENTRY._ID }, null,null, null, null, null);
    }
    
    public Cursor getSelectedWeekMealPlanners(String name)
    {
    	String selection = WEEK_MEAL_PLANNER_ENTRY.COLLUMN_NAME + " LIKE " + "\"" + name + "\"";
    	return mDb.query(TABLE_WEEK_MEAL_PLANNER, new String[] { WEEK_MEAL_PLANNER_ENTRY._ID, WEEK_MEAL_PLANNER_ENTRY.COLLUMN_NAME }, selection,null, null, null, null);
    }
    
    //******************************************************
    //******************************************************
    //******************************************************
    //******************************************************
    //Function for daily_meal_planner
    public long CreateDailyMealPlanner(Integer week_id, String day, String time, String meal)
    {
    	ContentValues values = new ContentValues();
    	values.put(DAILY_MEAL_PLANNER_ENTRY.COLLUMN_WEEK_ID , week_id);
    	values.put(DAILY_MEAL_PLANNER_ENTRY.COLLUMN_DAY , day);
    	values.put(DAILY_MEAL_PLANNER_ENTRY.COLLUMN_TIME , time);
    	values.put(DAILY_MEAL_PLANNER_ENTRY.COLLUMN_MEAL , meal);
    	return mDb.insert(TABLE_DAILY_MEAL_PLANNER, null,values);
    }
    
    
    public Cursor getTimeDailyMealPlanners(Integer week_id, String day, String time)
    {
    	String selection = DAILY_MEAL_PLANNER_ENTRY.COLLUMN_WEEK_ID + "=" +week_id + " and " + DAILY_MEAL_PLANNER_ENTRY.COLLUMN_DAY + " LIKE " +  "\"" + day + "\"" + "and " + DAILY_MEAL_PLANNER_ENTRY.COLLUMN_TIME + " LIKE " +  "\"" + time + "\"";
    	return mDb.query(TABLE_DAILY_MEAL_PLANNER, new String[] { DAILY_MEAL_PLANNER_ENTRY.COLLUMN_MEAL }, selection,null, null, null, null);
    }
    
    public Cursor getDailyMealPlanners(Integer week_id, String day)
    {
    	String selection = DAILY_MEAL_PLANNER_ENTRY.COLLUMN_WEEK_ID + "=" +week_id + " and " + DAILY_MEAL_PLANNER_ENTRY.COLLUMN_DAY + " LIKE " +  "\"" + day + "\"";
    	return mDb.query(TABLE_DAILY_MEAL_PLANNER, new String[] { DAILY_MEAL_PLANNER_ENTRY.COLLUMN_MEAL }, selection,null, null, null, null);
    }
    
    public void DeleteAll()
    {
//    	mDb.execSQL("Drop table if exists "+ SHOPPING_LIST_ENTRY.TABLE_NAME);
//    	mDb.execSQL("Drop table if exists "+ WEEK_MEAL_PLANNER_ENTRY.TABLE_NAME);
//    	mDb.execSQL("Drop table if exists "+ DAILY_MEAL_PLANNER_ENTRY.TABLE_NAME);
    	mDbHelper.onUpgrade(mDb, 1, 2);
    }
    
    
}