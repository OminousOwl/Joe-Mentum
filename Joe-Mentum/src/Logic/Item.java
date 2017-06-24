package Logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Intermediary.Player;

/*
 *Project: ICS4U - Final Project - Joe-Mentum
 *Author: Olivier J Hébert, Quinn Fisher
 *Date Created: 26/05/2017
 *Date Edited: 18/06/2017
 *Description: This class will serve as the node for the item database.
 */
public class Item extends Entity {
	
	public static final int SWORD = 1;
	public static final int ARMOUR = 2;
	public static final int BOOTS = 3;
	public static final int HEALTH_POTION = 4;
	public static final int WINGS = 5;
	
	
	/**** Variables ****/
	private boolean active = false;//the flag to determine the type of item (consumable/equipment)
	private int type;//the reference to the list of item effects
	private BufferedImage icon;
	
	private int cooldown;
	public int cdRemaining;
	
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
	
	/*
	Name: moveSide
	Description: Activates an active item and puts it on cooldown
	Parameters: One Player
	Return Value/Type: N/A
	Dependencies: N/A
	Exceptions: N/A
	Date Created: June 19th, 2017
	Date Modified: June 19th, 2017
	 */
	public void use(Player joe) {
		if (active && cdRemaining == 0) {
			if (this.getType() == Item.HEALTH_POTION) {
				joe.damage(-3);
				if (joe.getHealth() > joe.getMaxHealth())
					joe.setHealth(joe.getMaxHealth());
			}
			
			else if (this.getType() == Item.WINGS) {
				joe.setYSpeed(0);
				joe.setAnimState(LivingObject.IDLE);
				joe.jump();
				joe.setAnimState(Player.VERT);
			}
			
			this.cdRemaining = cooldown;
			
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

	public void setCooldown(int cd) {
		this.cooldown = cd;
	}
	
	//June 19
	public void defineSprite() {
		try {
			String filepath = "res/items/";
			if (this.type == SWORD) {
				filepath += "sword.png";
			}
			else if (this.type == ARMOUR) {
				filepath += "armour.png";
			}
			else if (this.type == BOOTS) {
				filepath += "boot.png";
			}
			else if (this.type == HEALTH_POTION) {
				filepath += "health_potion.png";
			}
			else if (this.type == WINGS) {
				filepath += "double_boot.png";
			}
			icon = ImageIO.read(new File(filepath));
		}
		catch (IOException e) {
			
		}
		
	}

	public BufferedImage getIcon() {
		return icon;
	}
	
	public void defineActive() {
		if (this.type >= HEALTH_POTION) {
			this.setActive(true);
			if (this.type == HEALTH_POTION) {
				this.setCooldown(899);
			}
			else {
				this.setCooldown(500);
			}
		}
	}



}