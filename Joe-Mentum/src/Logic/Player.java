/*
Name: Quinn Fisher
Date Created: May 25th, 2017
Date Modified: May 25th, 2017
Description: The class used to handle the player's stats and motion
 */

package Logic;

public class Player extends LivingObject {

	private int EXP = 0;
	private int level = 1;
	private Item passiveItems;
	private Item activeItem;
	
	public Player() {
		//TODO Update Values
		this.setAttack(0);
		this.setHealth(0);
		this.setSpeed(0);
	}
	
}
