package com.mobiletrain.airplane.spirte;

import org.cocos2d.nodes.CCSprite;

public  class Bullet extends CCSprite{
	 
	int attack;
	float speed;
	
	public Bullet(String filepath){
		super(filepath);	
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
