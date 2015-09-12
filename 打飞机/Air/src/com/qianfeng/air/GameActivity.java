package com.qianfeng.air;


import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.qianfeng.air.data.PlayerDao;

public class GameActivity extends Activity {

	private GameLayer layer;
	private CCDirector director;
	private PlayerDao dao;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		handler();

		dao = new PlayerDao(this);

		// ��������
		CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);

		// ���������� ��������
		setContentView(surfaceView);

		director = CCDirector.sharedDirector();

		director.attachInView(surfaceView);// ������������ ������������������

		director.setScreenSize(480, 852);

		director.setDisplayFPS(true);

		CCScene scene = CCScene.node();

		layer = new GameLayer(this, handler);

		// ��������������
		scene.addChild(layer);

		// ��������
		director.runWithScene(scene);

	}

	private void handler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				final int scores = msg.what;

				Log.i("GamePlane", "-----handleMessage��������----");

				SoundEngine.sharedEngine().pauseSound();

				View view = null;

				AlertDialog.Builder builder = new AlertDialog.Builder(
						GameActivity.this);

				builder.setTitle("��������");

				view = LayoutInflater.from(getApplicationContext()).inflate(
						R.layout.dialog_item, null);

				final EditText et_name = (EditText) view
						.findViewById(R.id.et_name_item);

				builder.setView(view);

				builder.setPositiveButton("����", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// ��������������
						PlayerBean player = new PlayerBean();

						String name = et_name.getText().toString().trim();

						if (TextUtils.isEmpty(name)) {
							name = "����";
						}

						player.setName(name);
						player.setScore(scores);
						dao.add(player);

						Log.i("GamePlane", "activityscore=" + scores);

						dialog.cancel();

						finish();

					}
				});

				builder.show();
			}
		};

	}

	@Override
	protected void onResume() {

		director.onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {

		director.onPause();
		super.onPause();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			
			SoundEngine.sharedEngine().pauseSound();
			
		}
		
		return super.onKeyDown(keyCode, event);
	}

}
