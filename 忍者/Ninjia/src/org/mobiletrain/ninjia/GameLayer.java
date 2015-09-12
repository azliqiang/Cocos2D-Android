package org.mobiletrain.ninjia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor4B;

import android.view.MotionEvent;

public class GameLayer extends CCColorLayer { // Ctrl + Shift + o

	CGSize winSize = null;
	int bulletV = 480;
	Random random = new Random();

	List<CCSprite> bullets = new ArrayList<CCSprite>();
	List<CCSprite> targets = new ArrayList<CCSprite>();

	GameLayer(ccColor4B color) {
		/*
		 * Call the parent class's constructor must be the first statement on
		 * the method
		 */
		super(color);
		setIsTouchEnabled(true);
		// Get the size of the screen
		winSize = CCDirector.sharedDirector().displaySize();

		addHero();

		schedule("addTarget", 1f);
		schedule("check");
	}

	public void addTarget(float delta) {

		// 生成一个目标精灵对象
		CCSprite targetSprite = CCSprite.sprite("Target.png");
		this.addChild(targetSprite);
		targetSprite.setTag(1);
		targets.add(targetSprite);

		CGSize targetSize = targetSprite.getContentSize();
		float targetInitX = winSize.width + targetSize.width / 2;
		float targetInitY = random
				.nextInt((int) (winSize.height - targetSize.height))
				+ targetSize.height / 2;
		CGPoint initPoint = CGPoint.ccp(targetInitX, targetInitY);
		targetSprite.setPosition(initPoint);

		float targetEndX = -targetSize.width / 2;
		float targetEndY = targetInitY;
		CGPoint endPoint = CGPoint.ccp(targetEndX, targetEndY);

		float t = random.nextFloat() * 2 + 2;
		CCMoveTo moveTo = CCMoveTo.action(t, endPoint);
		CCCallFuncN funcN = CCCallFuncN.action(this, "onMoveFinished");
		CCSequence seq = CCSequence.actions(moveTo, funcN);

		targetSprite.runAction(seq);
	}

	public void addHero() {

		CCSprite hero = CCSprite.sprite("Player.png");
		CGSize size = hero.getContentSize();
		CGPoint pos = CGPoint.ccp(size.width / 2, winSize.height / 2);
		hero.setPosition(pos);
		this.addChild(hero);
	}

	/*
	 * 点击菜单栏： Source --> Override/Implement Methods...
	 * 选择回调函数：ccTouchesBegan(MotionEvent)
	 */

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {

		float x = event.getX();
		float y = winSize.height - event.getY();

		CCSprite bullet = CCSprite.sprite("Bullet.png");
		// 获取精灵对象的 CGSize 对象，包含了宽和高
		CGSize bulletSize = bullet.getContentSize();
		bullet.setTag(2);
		bullets.add(bullet);
		this.addChild(bullet);

		float initX = bulletSize.width / 2;
		float initY = winSize.height / 2;
		CGPoint initPoint = CGPoint.ccp(initX, initY);
		bullet.setPosition(initPoint);

		float endX = winSize.width + bulletSize.width / 2;
		float endY = winSize.width * (y - initY) / (x - initX) + winSize.height
				/ 2;
		CGPoint endPoint = CGPoint.ccp(endX, endY);

		float distance = CGPoint.ccpDistance(initPoint, endPoint);
		float t = distance / bulletV;

		CCMoveTo moveTo = CCMoveTo.action(t, endPoint);
		CCCallFuncN func = CCCallFuncN.action(this, "onMoveFinished");
		CCSequence seq = CCSequence.actions(moveTo, func);

		bullet.runAction(seq);

		// TODO Auto-generated method stub
		return super.ccTouchesBegan(event);
	}

	public void onMoveFinished(Object sender) {

		CCSprite sprite = (CCSprite) sender;
		this.removeChild(sprite, true);

		if (1 == sprite.getTag()) {
			targets.remove(sprite);
		} else if (2 == sprite.getTag()) {
			bullets.remove(sprite);
		}

	}

	public void check(float delta) {

		List<CCSprite> deleteBullets = new ArrayList<CCSprite>();

		for (int i = 0; i < bullets.size(); i++) {

			CCSprite bullet = bullets.get(i);
			CGPoint pos = bullet.getPosition();
			CGSize size = bullet.getContentSize();
			CGRect bulletRect = CGRect.make(pos.x - size.width / 2, pos.y
					- size.height / 2, size.width, size.height);

			List<CCSprite> deleteTargets = new ArrayList<CCSprite>();

			for (int j = 0; j < targets.size(); j++) {

				CCSprite target = targets.get(j);
				CGPoint targetPos = target.getPosition();
				CGSize targetSize = target.getContentSize();
				CGRect targetRect = CGRect.make(targetPos.x - targetSize.width
						/ 2, targetPos.y - targetSize.height / 2,
						targetSize.width, targetSize.height);

				boolean b = CGRect.intersects(targetRect, bulletRect);
				if (b) {
					System.out.println("碰撞");
					deleteTargets.add(target);
					this.removeChild(target, true);
				}
			} // end of loop for targets

			if (deleteTargets.size() > 0) {
				targets.removeAll(deleteTargets);
				this.removeChild(bullet, true);
				deleteBullets.add(bullet);
			}
		} // end of loop for bullets

		if (deleteBullets.size() > 0) {
			bullets.removeAll(deleteBullets);
		}

	}
}
