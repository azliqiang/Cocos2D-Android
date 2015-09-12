package com.mobiletrain.airplane;

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

import com.mobiletrain.airplane.data.PlayerDao;
import com.mobiletrain.airplanetest.R;

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

		// 创建画布
		CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);

		// 设置画布为 显示内容
		setContentView(surfaceView);

		director = CCDirector.sharedDirector();

		director.attachInView(surfaceView);// 导演绑定画布 来控制画布展示操作

		director.setScreenSize(480, 852);

		director.setDisplayFPS(true);

		CCScene scene = CCScene.node();

		layer = new GameLayer(this, handler);

		// 场景中添加图层
		scene.addChild(layer);

		// 运行场景
		director.runWithScene(scene);

	}

	private void handler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				final int scores = msg.what;

				Log.i("GamePlane", "-----handleMessage游戏结束----");

				SoundEngine.sharedEngine().pauseSound();

				View view = null;

				AlertDialog.Builder builder = new AlertDialog.Builder(
						GameActivity.this);

				builder.setTitle("游戏提示");

				view = LayoutInflater.from(getApplicationContext()).inflate(
						R.layout.dialog_item, null);

				final EditText et_name = (EditText) view
						.findViewById(R.id.et_name_item);

				builder.setView(view);

				builder.setPositiveButton("保存", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// 保存数到数据库
						PlayerBean player = new PlayerBean();

						String name = et_name.getText().toString().trim();

						if (TextUtils.isEmpty(name)) {
							name = "匿名";
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
