package com.ly.cocos2d2;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRepeat;
import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCTintBy;
import org.cocos2d.actions.interval.CCTintTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;

public class GameLayer extends CCLayer{
	CCSprite player1,player2;
	public GameLayer() {
		cocosd10();
	}
	private void cocosd10() {
		player1 = CCSprite.sprite("plane.png");
		CGPoint initPos = CGPoint.ccp(100, 100);
		player1.setPosition(initPos);
		this.addChild(player1);
	}
	private void cocosd9() {
		this.schedule("func", 1);
	}
	
	public void func(float delta) {
		System.out.println(delta);
	}
	
	private void cocosd8() {
		this.setIsTouchEnabled(true);
	}
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		CGPoint p1 = CGPoint.ccp(x, y);
		CCDirector director = CCDirector.sharedDirector();
		CGPoint p2 = director.convertToGL(p1);
		player1 = CCSprite.sprite("player.png");
		player1.setPosition(p2);
		this.addChild(player1);
		
		return super.ccTouchesBegan(event);
	}
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		this.removeChild(player1, true);
		return super.ccTouchesEnded(event);
	}
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		CGPoint p1 = CGPoint.ccp(x, y);
		CCDirector director = CCDirector.sharedDirector();
		CGPoint p2 = director.convertToGL(p1);
		player1.setPosition(p2);
		return super.ccTouchesMoved(event);
	}
	private void cocosd7() {
		player1 = CCSprite.sprite("player.png");
		CGPoint initPos = CGPoint.ccp(100, 100);
		player1.setPosition(initPos);
		this.addChild(player1);
		CGPoint tPos = CGPoint.ccp(300, 300);
		CCMoveTo moveTo1 = CCMoveTo.action(2, tPos);
		CCMoveTo moveTo2 = CCMoveTo.action(2, initPos);
		CCSequence seq = CCSequence.actions(moveTo1, moveTo2);
		CCRepeatForever repeat = CCRepeatForever.action(seq);
		player1.runAction(repeat);
		
	}
	private void cocosd6() {
		player1 = CCSprite.sprite("player.png");
		CGPoint initPos = CGPoint.ccp(100, 100);
		player1.setPosition(initPos);
		this.addChild(player1);
		ccColor3B cc = ccColor3B.ccc3(-120, 0, -255);
		CCTintBy tintby = CCTintBy.action(3, cc);
		player1.runAction(tintby);
	}
	private void cocosd5() {
		player1 = CCSprite.sprite("player.png");
		CGPoint initPos = CGPoint.ccp(100, 100);
		player1.setPosition(initPos);
		this.addChild(player1);

		CCFadeIn fadeIn = CCFadeIn.action(3);
		CCFadeOut fadeOut = CCFadeOut.action(3);
		CCSequence seq = CCSequence.actions(fadeIn,fadeOut);
		player1.runAction(seq);
	}
	private void cocosd4(){
		player1 = CCSprite.sprite("player.png");
		CGPoint initPos = CGPoint.ccp(100, 100);
		player1.setPosition(initPos);
		this.addChild(player1);
		CGPoint tPos = CGPoint.ccp(300, 300);
		CCMoveTo moveTo = CCMoveTo.action(2, tPos);
		
		CCRotateTo rotateTo = CCRotateTo.action(2, 180);
		
		CCScaleTo scaleTo = CCScaleTo.action(2, 2);
//		
//		CCSequence seq = CCSequence.actions(moveTo, rotateTo,scaleTo);
//		player1.runAction(seq);
//		
//		CCSpawn spawn = CCSpawn.actions(moveTo, rotateTo,scaleTo);
//		player1.runAction(spawn);
		
		CCCallFuncN funcN = CCCallFuncN.action(this, "test1");
		CCSequence seq = CCSequence.actions(moveTo,funcN);
		player1.runAction(seq);
	}
	public void test1(Object sender) {
		System.out.println("------------------");
	}
	private void cocosd3(){
		player1 = CCSprite.sprite("player.png");
		player2 = CCSprite.sprite("player.png");
		this.addChild(player1);
		this.addChild(player2);
		
		CGPoint initPos = CGPoint.ccp(400, 400);
		player1.setPosition(initPos);
		player2.setPosition(initPos);
		
		CGPoint dPos = CGPoint.ccp(0, 200);
		CGPoint tPos = CGPoint.ccpAdd(initPos, dPos);
		player2.setPosition(tPos);
	}
	
	private void cocosd2(){
		player1 = CCSprite.sprite("player.png");
		CGPoint pos = CGPoint.ccp(100, 100);
		player1.setPosition(pos);
//		CCFlipY fy = CCFlipY.action(true);
//		player.runAction(fy);
		
//		CGPoint tPos = CGPoint.ccp(400, 400);
//		CCMoveTo toAction = CCMoveTo.action(3, tPos);
//		player.runAction(toAction);
		
		CCRotateTo action3 = CCRotateTo.action(3, 180);
		player1.runAction(action3);
		this.addChild(player1);
	}
}
