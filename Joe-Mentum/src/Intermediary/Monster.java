/*
Name: Quinn Fisher
Date Created: May 26th, 2017
Date Modified: May 26th, 2017
Description: The class used to handle enemy AI
 */

package Intermediary;

import Logic.LivingObject;

public class Monster extends LivingObject {

	private static final char RUSH = 'r';
	private static final char WANDER = 'w';
	
	private Monster nextnode;
	private char aiState;
	
	public Monster(int health, int attack, double maxSpeed, char AIstate) {
		this.setHealth(health);
		this.setAttack(attack);
		this.setSpeed(maxSpeed);
		this.aiState = AIstate;
	}
	
	//AIState setter/getter (May 26th, 2017)
	public void setAIState(char newState) { aiState = newState; }
	public char getAIState() { return aiState ; }
	
	
	public void behave() {
		switch(aiState) {
		case RUSH:
			boolean direction = true;
			if (Joe.getX() < this.getX()) //TODO Update w/ actual Joe object
				direction = false;
			this.moveSide(direction);
			break;
		case WANDER:
			break;
		}
	}
}
