/*
Name: Quinn Fisher
Date Created: May 26th, 2017
Date Modified: June 11th, 2017
Description: The class used to handle enemy AI
 */

package Intermediary;

import java.util.Random;

import Logic.Entity;
import Logic.LivingObject;

public class Monster extends LivingObject {

	public static final char RUSH = 'r';
	public static final char WANDER = 'w';
	public static final char AgWANDER = 'g';
	public static final char RUN = 'n';
	public static final char RtWANDER = 't';
	public static final char LgWANDER = 'l';
	public static final char BIRD = 'b';
	
	public Monster next;
	private char aiState;
	private int moveFrames;
	public LinkedEntity associatedTerrain;
	public int damageCD;
	
	boolean direction = true;
	private int sineValue = 0;
	
	public Monster(int x, int y, int width, int height, int health, int attack, double maxSpeed, char AIstate) {
		this.x = x;
		this.y = y;
		this.defaultY = y;
		this.width = width;
		this.height = height;
		this.setHealth(health);
		this.setAttack(attack);
		this.setSpeed(maxSpeed);
		this.aiState = AIstate;
	}
	
	//AIState setter/getter (May 26th, 2017)
	public void setAIState(char newState) { aiState = newState; }
	public char getAIState() { return aiState ; }
	
	
 	public void behave(Player joe) {
 		switch(aiState) {
 		case RUSH:
			direction = true;
 			if (joe.getX() < this.getX()) //TODO Update w/ actual Joe object
 				direction = false;
 			this.moveSide(direction);
 			if (Math.abs(joe.x - this.x) > 200)
				this.setAIState(AgWANDER);
 			break;
 			
		//True wandering, no player detection, 100% passive
 		case WANDER:
 			wander();
			break;
		case AgWANDER:
			wander();
			if (Math.abs(joe.x - this.x) <= 200)
				this.setAIState(RUSH);
			break;
		case RUN:
			if (joe.getX() > this.getX()) //TODO Update w/ actual Joe object
				direction = false;
			this.moveSide(direction);
			if (Math.abs(joe.x - this.x) > 200)
				this.setAIState(RtWANDER);
			break;
		case RtWANDER:
			wander();
			if (Math.abs(joe.x - this.x) <= 200)
				this.setAIState(RUN);
			break;
		case LgWANDER:
			direction = ledgeGuard(direction);
			moveSide(direction);
			break;
		case BIRD:
			this.setYSpeed(this.getYSpeed() - LivingObject.GRAV + Math.sin(this.x));
			moveSide(false);
			break;
		}
	}
 	
	/*
	Name: ledgeGuard
	Description: Returns a direction to prevent a monster from falling off the edge of its platform
	Parameters: One boolean value
	Return Value/Type: One boolean value
	Dependencies: None
	Exceptions: N/A
	Date Created: June 11th, 2017
	Date Modified: June 11th, 2017
	 */
 	public boolean ledgeGuard(boolean oldDirection) {
		if (!(associatedTerrain == null)) {
			if (this.x == associatedTerrain.x) {
				return true;
			}
			else if (this.x == associatedTerrain.x + associatedTerrain.width - this.width){
				return false;
			}
		}
		return oldDirection;
 	}
	
	/*
	Name: wander
	Description: Causes a monster to aimlessly roam
	Parameters: None
	Return Value/Type: N/A
	Dependencies: None
	Exceptions: N/A
	Date Created: June 11th, 2017
	Date Modified: June 11th, 2017
	 */
	public void wander() {
		if (moveFrames > 0) { //If the entity still has frames left to move
			if (Math.abs(this.getXSpeed()) > 0) { //Move frames in progress
				moveSide(direction);
				direction = ledgeGuard(direction);
			} //Alternative: Do nothing (ergo no code)
			moveFrames--;
		}
		else { //New action should be called
			if (this.getXSpeed() == 0) {
				Random rnd = new Random();
				direction = rnd.nextBoolean();
				moveSide(direction);
				moveFrames = randNumber(15, 60);
			}
			else { //Time to stop moving!
				this.setXSpeed(0);
				moveFrames = randNumber(15, 100);
			}
		}
	}
	
	/*
	 Name: randNumber
	 Description: Generates a random number
	 Parameters: Two Integers
	 Return Value/Type: A random number within the range of the parameters
	 Dependencies: Java.Math
	 Creation Date: October 23rd, 2015
	 Modification Date: April 13th, 2017
	 Throws: None
	*/
	public static int randNumber(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	/*
	Name: setNext
	Description: Assigns the Entity's link to another enemy
	Parameters: One Monster
	Return Value/Type: N/A
	Dependencies: Logic.Entity
	Exceptions: N/A
	Date Created: June 8th, 2017
	Date Modified: June 8th, 2017
	 */
	public void setNext(Monster node) {
		this.next = node;
	}
	
	/*
	Name: getNext
	Description: Returns the link
	Parameters: One Monster
	Return Value/Type: N/A
	Dependencies: Logic.Entity
	Exceptions: N/A
	Date Created: June 11th, 2017
	Date Modified: June 11th, 2017
	 */
	public Monster getNext() {
		return this.next;
	}
	
	/*
	Name: setAssociatedTerrain
	Description: Assigns a reference to the monster's floor
	Parameters: One LinkedEntity
	Return Value/Type: N/A
	Dependencies: Logic.Entity
	Exceptions: N/A
	Date Created: June 8th, 2017
	Date Modified: June 11th, 2017
	 */
	public void setAssociatedTerrain(LinkedEntity terrain) {
		this.associatedTerrain = terrain;
	}
	
}
