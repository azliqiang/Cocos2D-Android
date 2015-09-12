package com.mobiletrain.airplane.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobiletrain.airplane.PlayerBean;

public class PlayerDao {
	private DataBaseOpenHelper hepler = null;

	public PlayerDao(Context context) {
		hepler = new DataBaseOpenHelper(context);
	}

	public void add(PlayerBean player) {

		if(isExitName(player.getName())){//存在的话 就修改  不存在 则插入
			update(player);
			
			Log.i("Collision", "---update---");
		}else{
			insert(player);
			
			Log.i("Collision", "---insert---");
		}
		
//		SQLiteDatabase db = hepler.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//
//		values.put(DataBaseOpenHelper.TABLE_NAME, player.getName());
//
//		values.put(DataBaseOpenHelper.TABLE_SCORE, player.getScore());
//
//		db.insert(DataBaseOpenHelper.TABLE_PLAYER, null, values);
//
//		db.close();

	}

	public void update(PlayerBean player){
		
		SQLiteDatabase db = hepler.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put(DataBaseOpenHelper.TABLE_SCORE,player.getScore());
		
		db.update(DataBaseOpenHelper.TABLE_PLAYER, values,"name=?",new String[]{player.getName()});
		
	}
	
	public void insert(PlayerBean player) {
		
		SQLiteDatabase db = hepler.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(DataBaseOpenHelper.TABLE_NAME, player.getName());

		values.put(DataBaseOpenHelper.TABLE_SCORE, player.getScore());

		db.insert(DataBaseOpenHelper.TABLE_PLAYER, null, values);

		db.close();
		
	}

	public boolean isExitName(String name) {
		SQLiteDatabase db = hepler.getWritableDatabase();

		Cursor c = db.query(DataBaseOpenHelper.TABLE_PLAYER, null, "name=?",
				new String[] { name }, null, null, null);

		if (c == null || !c.moveToNext()) {
			return false;
		}
		c.close();
		db.close();
		return true;
	}

	public List<PlayerBean> findAll() {

		List<PlayerBean> list = new ArrayList<PlayerBean>();

		SQLiteDatabase db = hepler.getWritableDatabase();
		Cursor c = db.query(DataBaseOpenHelper.TABLE_PLAYER, null, null, null,
				null, null, "score desc");

		if (c == null) {
			return null;
		}
		while (c.moveToNext()) {
			int id = c.getInt(0);
			String name = c.getString(1);
			int score = c.getInt(2);

			PlayerBean bean = new PlayerBean();
			bean.setId(id);
			bean.setName(name);
			bean.setScore(score);

			list.add(bean);
		}

		c.close();
		db.close();

		return list;

	}
}
