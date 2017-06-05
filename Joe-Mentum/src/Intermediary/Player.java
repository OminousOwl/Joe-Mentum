/*
Name: Quinn Fisher
Date Created: May 25th, 2017
Date Modified: May 25th, 2017
Description: The class used to handle the player's stats and motion
 */

package Intermediary;

import Logic.Item;
import Logic.LivingObject;

public class Player extends LivingObject {

	private static int EXP = 0;
	private static int level = 1;
	private static Item active; //We don't need a passive slot as it is included in LivingObject
	
	public Player() {
		//TODO Update Values
		this.setAttack(0);
		this.setHealth(0);
		this.setSpeed(2.4);
	}//end constructor
	
	public void pickupItem(){//TODO
		//determine if colliding with an object. if colliding & object is of type item...
			//if passive, add to inventory
			//otherwise if Joe has no active item move the item to his active item slot
				//otherwise swap the items (move the colliding item to inventory & the other to the outside)
	}//end pickupItem
}
