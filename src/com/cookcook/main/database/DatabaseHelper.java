package com.cookcook.main.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
//	private final String TABLE_RECIPE = "other_recipe";
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cookcook.db";
    
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	//Define table shoppling_list
	public static abstract class SHOPPING_LIST_ENTRY implements BaseColumns
	{
		public static final String TABLE_NAME = "shoppling_list";
		public static final String COLLUMN_NAME = "name";
		public static final String COLLUMN_SELECTED = "selected";
	}
	
	//Define table week_meal_planner
	public static abstract class WEEK_MEAL_PLANNER_ENTRY implements BaseColumns
	{
		public static final String TABLE_NAME = "week_meal_planner";
		public static final String COLLUMN_NAME = "week";
		public static final String COLLUMN_WEEK_ID = "week_id";
	}
	
	//Define table daily_meal_planner
	public static abstract class DAILY_MEAL_PLANNER_ENTRY implements BaseColumns
	{
		public static final String TABLE_NAME = "daily_meal_planner";
		public static final String COLLUMN_WEEK_ID = "week_id";
		public static final String COLLUMN_DAY = "day";
		public static final String COLLUMN_TIME = "time";
		public static final String COLLUMN_MEAL = "meal";
	}
	
	//Will do it later
	public static abstract class MY_RECIPE_ENTRY implements BaseColumns
	{
//		public static final String TABLE_NAME = "my_recipe";
//		public static final String COLLUMN_WEEK_ID = "week_id";
//		public static final String COLLUMN_DAY = "day";
//		public static final String COLLUMN_TIME = "time";
//		public static final String COLLUMN_meal = "meal";
	}
	
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	
	private static final String CREATE_TABLE_SHOPPING_LIST =
			"CREATE TABLE "+ SHOPPING_LIST_ENTRY.TABLE_NAME + " (" +
			SHOPPING_LIST_ENTRY._ID 		     + " INTEGER PRIMARY KEY," +
			SHOPPING_LIST_ENTRY.COLLUMN_NAME     + TEXT_TYPE + COMMA_SEP +
			SHOPPING_LIST_ENTRY.COLLUMN_SELECTED + INTEGER_TYPE + " )";
	
	private static final String CREATE_TABLE_WEEK_MEAL_PLANNER =
			"CREATE TABLE " + WEEK_MEAL_PLANNER_ENTRY.TABLE_NAME + " (" +
			WEEK_MEAL_PLANNER_ENTRY._ID		        + " INTEGER PRIMARY KEY," +
			WEEK_MEAL_PLANNER_ENTRY.COLLUMN_NAME    + TEXT_TYPE + " )";
//	COMMA_SEP +
//			"FOREIGN KEY (" + WEEK_MEAL_PLANNER_ENTRY.COLLUMN_WEEK_ID + " ) REFERENCES " +
//			DAILY_MEAL_PLANNER_ENTRY.TABLE_NAME + " ON DELETE CASCADE )";
	
	private static final String CREATE_TABLE_DAILY_WEEK_MEAL_PLANNER = 
			"CREATE TABLE " + DAILY_MEAL_PLANNER_ENTRY.TABLE_NAME + " (" +
			DAILY_MEAL_PLANNER_ENTRY._ID 			 + " INTEGER PRIMARY KEY," +
			DAILY_MEAL_PLANNER_ENTRY.COLLUMN_WEEK_ID + INTEGER_TYPE + COMMA_SEP +
			DAILY_MEAL_PLANNER_ENTRY.COLLUMN_DAY	 + TEXT_TYPE 	+ COMMA_SEP +
			DAILY_MEAL_PLANNER_ENTRY.COLLUMN_TIME	 + TEXT_TYPE	+ COMMA_SEP +
			DAILY_MEAL_PLANNER_ENTRY.COLLUMN_MEAL	 + TEXT_TYPE	+ " )";
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.v("===create====", "ok");
		db.execSQL(CREATE_TABLE_SHOPPING_LIST);
		Log.v("===create====", "ok1");
		db.execSQL(CREATE_TABLE_WEEK_MEAL_PLANNER);
		Log.v("===create====", "ok2");
		db.execSQL(CREATE_TABLE_DAILY_WEEK_MEAL_PLANNER);
		Log.v("===create====", "ok2");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("Drop table if exists "+ SHOPPING_LIST_ENTRY.TABLE_NAME);
		db.execSQL("Drop table if exists "+ WEEK_MEAL_PLANNER_ENTRY.TABLE_NAME);
		db.execSQL("Drop table if exists "+ DAILY_MEAL_PLANNER_ENTRY.TABLE_NAME);
		onCreate(db);
	}
	

}
