package org.mobiletrain.ninjia;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.ccColor4B;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private CCDirector director = null;
	private CCGLSurfaceView view = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		view = new CCGLSurfaceView(this);
		setContentView(view);

		// 得到 CCDirector 对象
		director = CCDirector.sharedDirector();

		/* 设置应用程序相关的属性 */
		// 设置当前游戏程序当中所使用的 view 对象
		director.attachInView(view);
		// 设置游戏是否显示 FPS 值
		director.setDisplayFPS(true);
		// 设置游戏渲染一帧数据所需要的时间
		director.setAnimationInterval(1 / 30.0);

		/* 生成一个游戏场景对象 */
		CCScene scene = CCScene.node();

		// 生成布景层对象
		ccColor4B color = ccColor4B.ccc4(255, 255, 255, 255);
		GameLayer gameLayer = new GameLayer(color);

		// 将布景层对象添加至游戏场景当中
		scene.addChild(gameLayer);

		// 运行游戏场景
		director.runWithScene(scene);
	}
}
