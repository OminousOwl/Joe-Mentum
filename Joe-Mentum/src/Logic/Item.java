package Logic;
/*
 *Project: ICS4U - Final Project - Joe-Mentum
 *Author: Olivier J Hébert, Quinn Fisher
 *Date Created: 26/05/2017
 *Date Edited: 18/06/2017
 *Description: This class will serve as the node for the item database.
 */
public class Item extends Entity {
	
	public static final int SWORD = 0;
	public static final int ARMOUR = 1;
	public static final int BOOTS = 2;
	public static final int HEALTH_POTION = 3;
	
	
	/**** Variables ****/
	private boolean active = false;//the flag to determine the type of item (consumable/equipment)
	private int type;//the reference to the list of item effects
	private String description;//a description of an item's effect
	
	private int cooldown;
	private int cdRemaining;
	
	private int itemGenID;
	
	public Item next;
	
	public Item (int itemType) {
		setType(itemType);
		this.width = 30;
		this.height = 30;
	}
	
	public Item (int itemType, int x, int y) {
		setType(itemType);
		this.x = x;
		this.y = y;
		this.width = 30;
		this.height = 30;
	}
	
	public Item (int itemType, int x, int y, Item next) {
		setType(itemType);
		this.x = x;
		this.y = y;
		this.width = 30;
		this.height = 30;
		this.next = next;
	}
	
	public void use() {
		if (active && cdRemaining == 0) {
			
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setNext(Item node) {
		this.next = node;
	}

	public int getItemGenID() {
		return itemGenID;
	}

	public void setItemGenID(int itemGenID) {
		this.itemGenID = itemGenID;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


}