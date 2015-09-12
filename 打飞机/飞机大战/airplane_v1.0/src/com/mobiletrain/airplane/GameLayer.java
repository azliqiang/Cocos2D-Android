package com.mobiletrain.airplane;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCCallFuncND;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.util.CGPointUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import com.mobiletrain.airplane.spirte.Bullet;
import com.mobiletrain.airplane.spirte.Enemy;
import com.mobiletrain.airplane.spirte.Player;
import com.mobiletrain.airplanetest.R;

public class GameLayer extends CCLayer {

	private CCSprite background1;
	private CCSprite background2;

	public static final int GAME_RUN = 1;
	public static final int GAME_PAUSE = 2;
	public static final int GAME_OVER = 3;

	public static final int GAME_SUCCESS = 99;

	private int status = GAME_RUN;// 运行中 1 暂停2 游戏结束3

	private Context context;
	private Player player;
	private CGSize winSize;

	private List<Bullet> playerBullets;

	private List<Enemy> enemies;
	private CGPoint startPoint;
	private CCLabelAtlas labelScore;

	private Handler handler;
	private boolean effectMusic;
	private boolean bgMusic;

	public GameLayer(Context context, Handler handler) {

		setIsTouchEnabled(true);

		this.context = context;
		this.handler = handler;

		// 初始化背景
		initBackground();

		schedule("moveBackground");

		initPlayer();

		playerBullets = new ArrayList<Bullet>();

		enemies = new ArrayList<Enemy>();

		CCScheduler.sharedScheduler().schedule("shoot", this, 0.2f, false);

		CCScheduler.sharedScheduler().schedule("makeEnemies", this, 1f, false);

		schedule("checkCollision");

		initLabelScore();

		SharedPreferences sp = context.getSharedPreferences("game.xml",
				Context.MODE_PRIVATE);

		effectMusic = sp.getBoolean("effectMusic", false);
		bgMusic = sp.getBoolean("bgMusic", false);

		// 背景音乐
		playSound(R.raw.game_music);

	}

	private void initLabelScore() {

		labelScore = CCLabelAtlas.label("0", "myfont.png", 30, 50, '0');

		labelScore.setPosition(winSize.width / 10, winSize.height - 80);

		this.addChild(labelScore);
	}

	private void initPlayer() {

		winSize = CCDirector.sharedDirector().winSize();

		player = new Player();
		player.setAnchorPoint(0.5f, 0);
		player.setPosition(winSize.width / 2, 0);

		this.addChild(player);
	}

	private void initBackground() {
		background1 = CCSprite.sprite("background.png");
		background2 = CCSprite.sprite("background.png");

		background1.setAnchorPoint(0, 0);// 锚点在左下角
		background2.setAnchorPoint(0, 0);

		background1.setPosition(0, 0);// 位置在屏幕上
		background2.setPosition(0, background1.getContentSize().height);// 在背景1的上边

		this.addChild(background1);
		this.addChild(background2);
	}

	public void moveBackground(float delta) {
		float speedBG = 5;
		background1.setPosition(background1.getPosition().x,
				background1.getPosition().y - speedBG);// 往下移动
		background2.setPosition(background2.getPosition().x,
				background2.getPosition().y - speedBG);// 往下移动

		if (background2.getPosition().y <= 0) {// 表示背景2已经向下移动到了原点位置了
			// 初始化 背景
			background1.setPosition(0, 0);
			background2.setPosition(0, background1.getContentSize().height);
		}

	}

	public void makeEnemies(float delta) {

		if (status == GAME_RUN) {

			Enemy enemy = null;

			double random = Math.random();
			if (random <= 0.2) {
				enemy = new Enemy("enemy03_1.png");// 创建敌机
				enemy.setAnchorPoint(0, 0);
				enemy.setTag(3);
				enemy.setLife(50);
				enemy.setScore(150);
				enemy.setSpeed(winSize.height * 0.1f);
			} else if (random <= 0.5) {
				enemy = new Enemy("enemy02_1.png");// 创建敌机
				enemy.setAnchorPoint(0, 0);
				enemy.setTag(2);
				enemy.setLife(30);
				enemy.setScore(100);
				enemy.setSpeed(winSize.height * 0.2f);
			} else {
				enemy = new Enemy("enemy01_1.png");// 创建敌机
				enemy.setAnchorPoint(0, 0);
				enemy.setTag(1);
				enemy.setLife(20);
				enemy.setScore(50);
				enemy.setSpeed(winSize.height * 0.3f);
			}

			/* 设置的出现的位置 */
			float x = (float) (Math.random() * (winSize.width - enemy
					.getContentSize().width));// 随机x轴坐标
			float y = winSize.height + (float) Math.random()
					* enemy.getContentSize().height;// 随机y轴坐标
			enemy.setPosition(x, y);

			enemies.add(enemy);// 添加到敌机集合

			this.addChild(enemy);// 添加到图层

			// 执行动画
			CGPoint pos = CGPoint.ccp(enemy.getPosition().x,
					-enemy.getContentSize().width);// 目的地
			float t = CGPointUtil.distance(enemy.getPosition(), pos)
					/ enemy.getSpeed();// 执行时间
			CCMoveTo move = CCMoveTo.action(t, pos);

			CCCallFunc func = CCCallFuncND.action(this, "removEenemy", enemy);

			CCSequence seq = CCSequence.actions(move, func);

			enemy.runAction(seq);
		}
	}

	public void checkCollision(float delta) {

		if (status == GAME_RUN) {

			/* 检查 敌机有没有与飞机碰撞 */

			for (Enemy enemy : enemies) {

				if (CGRect.intersects(enemy.getBoundingBox(),
						player.getBoundingBox())) {// 表示 敌机与玩家碰撞

					playerBomb();// 玩家爆炸

					playEffect(R.raw.game_over);

					status = GAME_OVER;

					stopScheduler();

					handler.sendEmptyMessage(player.getScores());
					Log.i("GamePlane", "---玩家爆炸---");

				} else {
					/* 没有相撞 看一下 子弹有没有打中 敌机 */
					for (Bullet bullet : playerBullets) {
						if (CGRect.intersects(bullet.getBoundingBox(),
								enemy.getBoundingBox())) {// 表示 子弹打中了敌机
							int sub = enemy.getLife() - bullet.getAttack();
							if (sub <= 0 && !enemy.isDie) {
								enemyBomb(enemy);// 敌机爆炸
								enemy.isDie = true;

								player.setScores(player.getScores()
										+ enemy.getScore());

								labelScore.setString(player.getScores() + "");

								playEffect(R.raw.enemy_down);

							} else {
								enemy.setLife(sub);
							}

							// 从集合中 删除子弹对象
							removeBullet(null, bullet);

						}
					}
				}
			}
		}

	}

	private void stopScheduler() {
		unschedule("moveBackground");
		unschedule("shoot");
		unschedule("makeEnemies");
		unschedule("checkCollision");
	}

	public void enemyBomb(Enemy enemy) {

		ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();

		if (enemy.getTag() == 1) {
			for (int i = 1; i < 6; i++) {
				CCSpriteFrame frame = CCSprite.sprite("enemy01_" + i + ".png")
						.displayedFrame();
				frames.add(frame);
			}
		} else if (enemy.getTag() == 2) {
			for (int i = 1; i < 7; i++) {
				CCSpriteFrame frame = CCSprite.sprite("enemy02_" + i + ".png")
						.displayedFrame();
				frames.add(frame);
			}
		} else if (enemy.getTag() == 3) {
			for (int i = 1; i < 10; i++) {
				CCSpriteFrame frame = CCSprite.sprite("enemy03_" + i + ".png")
						.displayedFrame();
				frames.add(frame);
			}
		}

		CCAnimation animation = CCAnimation.animation("eabomb", frames);
		CCAnimate animate = CCAnimate.action(1f, animation, false);
		CCSequence seq = CCSequence.actions(animate,
				CCCallFuncND.action(this, "removEenemy", enemy));
		enemy.runAction(seq);
	}

	public void playerBomb() {
		ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
		for (int i = 1; i < 6; i++) {
			CCSpriteFrame frame = CCSprite.sprite("player_" + i + ".png")
					.displayedFrame();
			frames.add(frame);
		}
		CCAnimation animation = CCAnimation.animation("planebomb", frames);
		CCAnimate animate = CCAnimate.action(1f, animation, false);
		CCSequence seq = CCSequence.actions(animate,
				CCCallFunc.action(this, "destoryPlayer"));
		player.runAction(seq);
	}

	public void destoryPlayer() {
		player.removeSelf();
	}

	public void shoot(float delta) {
		if (status == GAME_RUN) {
			playerShoot();
		}
	}

	/**
	 * 玩家发射子弹
	 */
	public void playerShoot() {

		Bullet bullet = new Bullet("bullet_blue.png");// 创建蓝色子弹
		float y = player.getPosition().y + player.getContentSize().height;
		bullet.setPosition(player.getPosition().x, y);// 位置

		bullet.setAttack(5);// 攻击值
		bullet.setSpeed(winSize.height * 0.6f);// 设置子弹运行速度

		playerBullets.add(bullet);// 添加到子弹集合中

		this.addChild(bullet);// 添加到游戏层中

		/* 子弹执行位移动画 */
		CGPoint pos = CGPoint.ccp(bullet.getPosition().x, winSize.height
				+ bullet.getContentSize().width);// 目的地
		float t = CGPointUtil.distance(bullet.getPosition(), pos)
				/ bullet.getSpeed();// 执行时间
		CCMoveTo move = CCMoveTo.action(t, pos);

		CCCallFunc func = CCCallFuncND.action(this, "removeBullet", bullet);

		CCSequence seq = CCSequence.actions(move, func);

		bullet.runAction(seq);

		playEffect(R.raw.bullet);

	}

	/**
	 * 从集合中 去掉Bullet对象 销毁Bullet对象
	 */
	public void removeBullet(Object node, Object bullet) {

		playerBullets.remove(bullet);// 从子弹集合中 删去

		CCSprite temp = (CCSprite) bullet;

		temp.removeSelf();// 自杀
	}

	/**
	 * 从集合中 去掉Eenemy对象 销毁Eenemy对象
	 */
	public void removEenemy(Object node, Object enemy) {

		enemies.remove(enemy);// 从子弹集合中 删去

		CCSprite temp = (CCSprite) enemy;

		temp.removeSelf();// 自杀
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {

		startPoint = convertTouchToNodeSpace(event);

		return super.ccTouchesBegan(event);
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {

		CGPoint pos = convertTouchToNodeSpace(event);

		float dx = pos.x - startPoint.x;
		float dy = pos.y - startPoint.y;//

		float x = player.getPosition().x + dx;
		float y = player.getPosition().y + dy;

		// 简单判断一下 飞机有没有超出屏幕外边去
		if (x < 0) {
			x = 0;
		} else if (x > winSize.width) {
			x = winSize.width;
		}

		if (y < 0) {
			y = 0;
		} else if (y > winSize.height - player.getContentSize().height) {
			y = winSize.height - player.getContentSize().height;
		}

		player.setPosition(x, y);

		startPoint = pos;

		return super.ccTouchesMoved(event);
	}
	
	public void playEffect(int resId) {
		if (effectMusic) {
			SoundEngine.sharedEngine().playEffect(context, resId);
		}
	}

	public void playSound(int resId) {
		if (bgMusic) {
			SoundEngine.sharedEngine().playSound(context, resId, true);
		}
	}

}
