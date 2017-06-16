/*
Name: Quinn Fisher
Date Created: May 25th, 2017
Date Modified: June 14th, 2017
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

	private static int EXP = 0;
	private static int level = 1;
	private static Item active; //We don't need a passive slot as it is included in LivingObject
	
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
					frame++;
				}
				else if (getAnimState() == MOVE) {
					setCurrentFrame(flipHorizontal(sprites[1].getSprite(frame % sprites[1].getFrameCount())));
					frame++;
				}
				else if (getAnimState() == VERT) {
					if (getySpeed() <= -6.6) {
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
						frame++;
					}
					
				}
			}
				
		});
		timer.start();
		
		//TODO Update Values
		this.setAttack(3);
		this.setHealth(10);
		this.setSpeed(3.0);
		
	}//end constructor
	

	
	public void pickupItem(){//TODO
		//determine if colliding with an object. if colliding & object is of type item...
			//if passive, add to inventory
			//otherwise if Joe has no active item move the item to his active item slot
				//otherwise swap the items (move the colliding item to inventory & the other to the outside)
	}//end pickupItem
	
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
	
}
