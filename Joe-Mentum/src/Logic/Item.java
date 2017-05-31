package Logic;
/*
 *Project: ICS4U - Final Project - Joe-Mentum
 *Author: Olivier J Hébert
 *Date Created: 26/05/2017
 *Date Edited: 26/05/2017
 *Description: This class will serve as the node for the item database.
 */
public class Item extends Entity {
	/**** Variables ****/
	public boolean isPassive;//the flag to determine the type of item (consumable/equipment)
	public char effect;//the reference to the list of item effects
	public String description;//a description of an item's effect
}