package com.ly.cocos2d2;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	static{
		System.loadLibrary("gdx");
	}
	CCGLSurfaceView view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new CCGLSurfaceView(this);
        CCDirector director = CCDirector.sharedDirector();
		director.setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);
        director.attachInView(view);
        director.setAnimationInterval(1/30.0);
        director.setDisplayFPS(true);
        setContentView(view);
        CCScene scene = CCScene.node();
        FlyppyBirdLayer layer = new FlyppyBirdLayer(this);
        scene.addChild(layer);
        director.runWithScene(scene);
    }
}
