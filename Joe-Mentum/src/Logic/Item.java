package Logic;
/*
 *Project: ICS4U - Final Project - Joe-Mentum
 *Author: Olivier J Hébert
 *Date Created: 26/05/2017
 *Date Edited: 17/06/2017
 *Description: This class will serve as the node for the item database.
 */
public class Item extends Entity {
	/**** Variables ****/
	private boolean isActive;//the flag to determine the type of item (consumable/equipment)
	private char effect;//the reference to the list of item effects
	private String description;//a description of an item's effect
	
	private int cooldown;
	private int cdRemaining;
	
	
	public void use() {
		if (isActive && cdRemaining == 0) {
			
		}
	}
}