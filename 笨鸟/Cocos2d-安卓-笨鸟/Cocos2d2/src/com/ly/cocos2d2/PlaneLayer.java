package com.ly.cocos2d2;

import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import android.view.MotionEvent;

public class PlaneLayer extends CCLayer{
	
	CCSprite plane;
	CCSprite dot;
	public PlaneLayer() {
		setIsTouchEnabled(true);
		plane = CCSprite.sprite("plane.png");
		CGPoint initPos = CGPoint.ccp(100, 100);
		plane.setPosition(initPos);
		this.addChild(plane);
	}
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint p = CGPoint.ccp(event.getX(), event.getY());
		CGPoint pressPos = CCDirector.sharedDirector().convertToGL(p);
		plane.setPosition(pressPos);
		return super.ccTouchesBegan(event);
	}
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		return super.ccTouchesEnded(event);
	}
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		CGPoint p = CGPoint.ccp(event.getX(), event.getY());
		CGPoint pressPos = CCDirector.sharedDirector().convertToGL(p);
		plane.setPosition(pressPos);
		dot = CCSprite.sprite("dot.png");
		dot.setPosition(pressPos);
		CGPoint tPos = CGPoint.ccp(0, 1500);
		CCMoveBy moveBy = CCMoveBy.action(1, tPos);
		this.addChild(dot);
		dot.runAction(moveBy);
		return super.ccTouchesMoved(event);
	}
}
