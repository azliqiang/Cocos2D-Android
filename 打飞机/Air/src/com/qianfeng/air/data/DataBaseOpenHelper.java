package com.qianfeng.air.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseOpenHelper extends SQLiteOpenHelper{

	public static final String TABLE_PLAYER="player";
	
	public static final String TABLE_ID="_id";
	
	public static final String TABLE_NAME="name";
	
	public static final String TABLE_SCORE="score";
	
	public DataBaseOpenHelper(Context context) {
		super(context,"plane.db",null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("create table player(_id integer primary key autoincrement,name text not null,score integer not null)");
	
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
