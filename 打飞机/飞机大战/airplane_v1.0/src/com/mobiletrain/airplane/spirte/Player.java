package com.mobiletrain.airplane.spirte;

import java.util.ArrayList;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;

public class Player extends CCSprite{
	
	int lifes;
	int scores;

	public Player() {
		super("player.png");
	}
	
	public int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	public int getScores() {
		return scores;
	}

	public void setScores(int scores) {
		this.scores = scores;
	}



	public void bomb() {
		ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
		for (int i = 1; i < 6; i++) {
			CCSpriteFrame frame = CCSprite.sprite("player_" + i + ".png")
					.displayedFrame();
			frames.add(frame);
		}
		CCAnimation animation = CCAnimation.animation("planebomb", frames);
		CCAnimate animate = CCAnimate.action(0.5f, animation, false);
		CCSequence seq = CCSequence.actions(animate,
				CCCallFunc.action(this, "destory"));
		this.runAction(seq);
	}
	
	public static Player sprite(String filepath){
		return new Player();
	}

}
