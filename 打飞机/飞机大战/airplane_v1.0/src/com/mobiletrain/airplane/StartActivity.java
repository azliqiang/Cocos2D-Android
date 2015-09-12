package com.mobiletrain.airplane;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.mobiletrain.airplanetest.R;

public class StartActivity extends Activity {

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		
		sp = getSharedPreferences("game.xml",Context.MODE_PRIVATE);
		
	}
	
	/*开始游戏*/
	public void onStart(View view) {
		//跳转到游戏主界面
		
		Intent intent=new Intent(StartActivity.this,GameActivity.class);
		startActivity(intent);
	}
	
	/*排行榜*/
	public void onCharts(View view) {
		
		
		PlayerListDialog dialog=new PlayerListDialog(this);
		
		dialog.show();
	}

	/*音乐开关*/
	public void onSwitch(View view) {
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("音乐提醒");
		
		String[] items={"游戏音效","背景音乐"};
		boolean[] checkedItems={sp.getBoolean("effectMusic",false),sp.getBoolean("bgMusic",false)};
		
		builder.setMultiChoiceItems(items, checkedItems,new OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				switch(which){
				case 0:
					
					sp.edit().putBoolean("effectMusic",isChecked).commit();
					
					break;
				case 1:
					
					sp.edit().putBoolean("bgMusic",isChecked).commit();
					
					break;
				}
			}
		});
		
		builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		builder.setCancelable(false);
		
		builder.show();
		
	}

}
