/*
Name: Quinn Fisher
Date Created: May 25th, 2017
Date Modified: June 10th, 2017
Description: The class used to handle the player's stats and motion
 */

package Intermediary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import Logic.Item;
import Logic.LivingObject;
import anim.Spritesheet;

public class Player extends LivingObject {

	private static int EXP = 0;
	private static int level = 1;
	private static Item active; //We don't need a passive slot as it is included in LivingObject
	private static Spritesheet[] sprites = new Spritesheet[5];
	private static String[] filepaths = new String[5];
	
	public final static int IDLE = 0;
	public final static int MOVE = 1;
	public final static int VERT = 2;
	public final static int LEDGE = 3;
	
	public Player() {
		filepaths[0] = "SpriteSheets/idle.png";
		filepaths[1] = "SpriteSheets/run.png";
		filepaths[2] = "SpriteSheets/jump.png";
		filepaths[3] = "SpriteSheets/ledge grab outline.png";
		
		
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
	
	public int getAnimState() { return this.animState; }
	public void setAnimState(int animState) { this.animState = animState;}
	
	private BufferedImage flipHorizontal(BufferedImage source) {
		if (!direction) {
			AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
			at.translate(-source.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			return op.filter(source, null);
		}
		else {
			return source;
		}
		
	}
	
	public void pickupItem(){//TODO
		//determine if colliding with an object. if colliding & object is of type item...
			//if passive, add to inventory
			//otherwise if Joe has no active item move the item to his active item slot
				//otherwise swap the items (move the colliding item to inventory & the other to the outside)
	}//end pickupItem
}
