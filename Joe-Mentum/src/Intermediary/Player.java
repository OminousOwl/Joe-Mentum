/*
Name: Quinn Fisher
Date Created: May 25th, 2017
Date Modified: June 19th, 2017
Description: The class used to handle the player's stats and motion
 */

package Intermediary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import Logic.Item;
import Logic.LivingObject;
import anim.Spritesheet;

public class Player extends LivingObject {

	private int EXP = 0;
	private int level = 1;
	private ItemSet passives = new ItemSet();
	private Item active;
	private int maxHealth = 10;
	private boolean grabbing = false;
	
	public final static int VERT = 2;
	public final static int LEDGE = 3;
	
	private static final double ACC = 1.0; //Constant used to define acceleration rate
	
	public Player() {
		
		this.isJoe = true;
		
		this.filepaths = new String[4];
		this.sprites = new Spritesheet[4];
		
		filepaths[0] = "SpriteSheets/Joe/idle.png";
		filepaths[1] = "SpriteSheets/Joe/run.png";
		filepaths[2] = "SpriteSheets/Joe/jump.png";
		filepaths[3] = "SpriteSheets/Joe/ledge grab outline.png";
		
		
		sprites[0] = new Spritesheet(filepaths[0], 21, 35);
		sprites[1] = new Spritesheet(filepaths[1], 23, 34);
		sprites[2] = new Spritesheet(filepaths[2], 22, 38);
		sprites[3] = new Spritesheet(filepaths[3], 22, 42);
		 
		Timer timer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getAnimState() == IDLE) {
					setCurrentFrame(flipHorizontal(sprites[0].getSprite(frame % sprites[0].getFrameCount())));
					frame = overflowProtect(frame + 1);
				}
				else if (getAnimState() == MOVE) {
					setCurrentFrame(flipHorizontal(sprites[1].getSprite(frame % sprites[1].getFrameCount())));
					frame = overflowProtect(frame + 1);
				}
				else if (getAnimState() == VERT) {
					if (getYSpeed() <= -6.6) {
						setCurrentFrame(flipHorizontal(sprites[2].getSprite(0)));
					}
					else if (getYSpeed() < -0.2) {
						setCurrentFrame(flipHorizontal(sprites[2].getSprite(1)));
					}
					else if (getYSpeed() < 0.4) {
						setCurrentFrame(flipHorizontal(sprites[2].getSprite(2)));
					}
					else {
						setCurrentFrame(flipHorizontal(sprites[2].getSprite(3)));
					}
					
					if (getYSpeed() == 0 && getXSpeed() == 0)
						setAnimState(IDLE);
				}
				else if (getAnimState() == LEDGE) {
					if (frame > 5)
						frame = 0;
					setCurrentFrame(flipHorizontal(sprites[3].getSprite(frame % sprites[3].getFrameCount())));
					if (frame < 4) {
						frame = overflowProtect(frame + 1);
					}
					
				}
			}
				
		});
		timer.start();
		
		//TODO Update Values
		this.setAttack(3);
		this.setHealth(maxHealth);
		this.setSpeed(3.0);
		
	}//end constructor
	
	/*
	Name: moveSide
	Description: Handles movement to either side
	Parameters: One boolean (direction)
	Return Value/Type: N/A
	Dependencies: N/A
	Exceptions: N/A
	Date Created: May 25th, 2017
	Date Modified: June 14th, 2017
	 */
	public void moveSide(boolean side) {
		
		int directionMultiplier = 1;
		if (!side)
			directionMultiplier = -1;
		
		//Handles character acceleration
		if (this.getXSpeed() == 0) {
			this.setXSpeed(directionMultiplier * 1.4);
		}
		else if (Math.abs(this.getXSpeed()) < getSpeed()) //Accelerates if not already at top speed
			this.setXSpeed(this.getXSpeed() + (directionMultiplier * ACC));
		
		if (directionMultiplier * this.getXSpeed() < 0) { //If the intended direction and the current direction do not match, turn around.
			//System.out.println("Turn at " + System.currentTimeMillis());
			this.setXSpeed(this.getXSpeed() * -1);
		}
		
	}
	
	/*
	Name: levelUp
	Description: Handles stat increases on level up
	Parameters: One boolean (direction)
	Return Value/Type: N/A
	Dependencies: N/A
	Exceptions: N/A
	Date Created: June 16th, 2017
	Date Modified: June 16th, 2017
	 */
	public void levelUp() {
		setEXP(getEXP() - 100);
		level++;
		
		int points = randNumber(1, 4);
		for (int i = 0; i < points; i++) {
			int stat = randNumber(1, 3);
			switch(stat) {
			case 1:
				maxHealth += 5;
				System.out.println("HP up");
				break;
			case 2:
				this.setAttack(this.getAttack() + 2);
				System.out.println("Atk up");
				break;
			case 3:
				this.setSpeed(this.getSpeed() + 0.4);
				System.out.println("Spd up");
				break;
			}
		}
		
		this.setHealth(maxHealth);
		
	}



	public int getEXP() { return EXP; }
	public void setEXP(int eXP) { EXP = eXP; }
	public int getLevel() { return level; }
	public void setLevel(int level) { this.level = level;}
	public Item getActive() { return active; }
	public void setActive(Item active) { this.active = active; }



	public ItemSet getPassives() {
		return passives;
	}



	public void setPassives(ItemSet passives) {
		this.passives = passives;
	}



	public boolean isGrabbing() {
		return grabbing;
	}



	public void setGrabbing(boolean grabbing) {
		this.grabbing = grabbing;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(int newMax) {
		maxHealth = newMax;
	}
	
}
