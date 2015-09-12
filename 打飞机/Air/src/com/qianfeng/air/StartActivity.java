package com.qianfeng.air;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


public class StartActivity extends Activity {

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//设置展示内容    需要创建新的布局文件start 在第2步实现
		setContentView(R.layout.start);
		
		sp = getSharedPreferences("game.xml",Context.MODE_PRIVATE);
		
	}
	
	/*��ʼ��Ϸ*/
	public void onStart(View view) {
		//��ת����Ϸ������
		
		Intent intent=new Intent(StartActivity.this,GameActivity.class);
		startActivity(intent);
	}
	
	/*���а�*/
	public void onCharts(View view) {
		
		
		PlayerListDialog dialog=new PlayerListDialog(this);
		
		dialog.show();
	}

	/*���ֿ���*/
	public void onSwitch(View view) {
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("��������");
		
		String[] items={"��Ϸ��Ч","��������"};
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
		
		builder.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		builder.setCancelable(false);
		
		builder.show();
		
	}

}
