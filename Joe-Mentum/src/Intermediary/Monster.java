/*
Name: Quinn Fisher
Date Created: May 26th, 2017
Date Modified: June 16th, 2017
Description: The class used to handle enemy AI
 */

package Intermediary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import Logic.Item;
import Logic.LivingObject;
import anim.Spritesheet;

public class Monster extends LivingObject {


	private static final long serialVersionUID = -7434225452647913349L;
	public static final int FIGHT = 2;
	public static final int DEAD = 3;

	public static final char RUSH = 'r';
	public static final char WANDER = 'w';
	public static final char AgWANDER = 'g';
	public static final char RUN = 'n';
	public static final char RtWANDER = 't';
	public static final char LgWANDER = 'l';
	public static final char BIRD = 'b';
	public static final char SADBIRD = 's';
	public static final char DWAIT = 'd';
	public static final char DUEL = 'u';
	public static final char ATTACK = 'a';
	
	public Monster next;
	private char aiState;
	private String enemyType;
	private int moveFrames;
	public LinkedEntity associatedTerrain;
	public int damageCD;
	public int attackCD;
	private boolean deathAnimFlag = false;
	private boolean stabCrabDrawn = false;
	private boolean stabCrabSwingStart = false;
	
	private int expGain;
	private Item drop;
	
	private int sineValue = 0;
	
	public Monster(int x, int y, int width, int height, int health, int attack, double maxSpeed, int expVal, char AIstate, String enemyType) {
		this.x = x;
		this.y = y;
		this.defaultY = y;
		this.width = width;
		this.height = height;
		this.setHealth(health);
		this.setAttack(attack);
		this.setSpeed(maxSpeed);
		this.setExpGain(expVal);
		this.aiState = AIstate;
		this.setEnemyType(enemyType);
		this.ledgeFlag = false;
		
		this.filepaths = new String[4];
		this.sprites = new Spritesheet[4];
		
		for (int i = 0; i < filepaths.length; i++) {
			filepaths[i] = "SpriteSheets/";
			if (enemyType == "skeleton") {
				filepaths[i] += "Skeleton/";
			}
			else if (enemyType == "bird") {
				filepaths[i] += "Bird/";
			}
			else if (enemyType == "stabcrab") {
				filepaths[i] += "StabCrab/";
			}
				
		}
		
		filepaths[0] += "Idle";
		filepaths[1] += "Walk";
		filepaths[2] += "Attack";
		filepaths[3] += "Dead";
		
		for (int i = 0; i < filepaths.length; i++) {
			filepaths[i] += ".png";
		}
		
		if (enemyType == "skeleton") {
				sprites[0] = new Spritesheet(filepaths[0], 264/11, 32);
				sprites[1] = new Spritesheet(filepaths[1], 286/13, 33);
				sprites[2] = new Spritesheet(filepaths[2], 774/18, 37);
				sprites[3] = new Spritesheet(filepaths[3], 495/15, 32);
		}
		else if (enemyType == "bird") {
			sprites[0] = sprites[1] = sprites[2] = sprites[3] = new Spritesheet(filepaths[1], 3072/6, 512);
		}
		else if (enemyType == "stabcrab") {
			sprites[0] = new Spritesheet(filepaths[0], 960/2, 238);
			sprites[1] = new Spritesheet(filepaths[1], 960/2, 238);
			sprites[2] = new Spritesheet(filepaths[2], 7200/15, 238);
			sprites[3] = sprites[0];
		}
		
		
		
		if (enemyType != null)
			setCurrentFrame(sprites[0].getSprite(0));
		
		//Assigns random item drops
		int itemChance = randNumber(1, 100);
		if (itemChance < expGain/2) {
			Item newDrop = new Item(randNumber(1, 5));
			newDrop.defineActive();
			newDrop.defineSprite();
			setDrop(newDrop);
		}
		
		Timer timer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (aiState == ATTACK && getAnimState() != DEAD) {
					setAnimState(FIGHT);
				}
				else if (getXSpeed() == 0 && getYSpeed() == 0 && getHealth() > 0) {
					if (stabCrabDrawn) {
						setAnimState(MOVE);
					}
					else {
						setAnimState(IDLE);
					}

				}
				else if (Math.abs(getXSpeed()) > 0 && getHealth() > 0) {
					if (!stabCrabDrawn && enemyType == "stabcrab") {
						setAnimState(IDLE);
					}
					else {
						setAnimState(MOVE);
					}
				}
				
				if (getEnemyType() != null) {
					if (getDamageFrames() > 0) {
						setCurrentFrame(flipHorizontal(sprites[3].getSprite((5 - getDamageFrames()) % sprites[3].getFrameCount())));
						setDamageFrames(getDamageFrames() -1);
						setXSpeed(0);
					}
					else if (getAnimState() == IDLE) {
						setCurrentFrame(flipHorizontal(sprites[0].getSprite(frame % sprites[0].getFrameCount())));
						frame = overflowProtect(frame + 1);
					}
					else if (getAnimState() == MOVE) {
						setCurrentFrame(flipHorizontal(sprites[1].getSprite(frame % sprites[1].getFrameCount())));
						frame = overflowProtect(frame + 1);
					}
					else if (getAnimState() == FIGHT) {
						if (!stabCrabDrawn) {
							stabCrabSwingStart = true;
							stabCrabDrawn = true;
							setFrame(0);
						}
						else if (!stabCrabSwingStart) {
							stabCrabSwingStart = true;
							setFrame(5);
						}
						setCurrentFrame(flipHorizontal(sprites[2].getSprite(frame % sprites[2].getFrameCount())));
						frame = overflowProtect(frame + 1);
					}
					else if (getAnimState() == DEAD) {
						if (!deathAnimFlag) {
							setCurrentFrame(flipHorizontal(sprites[3].getSprite(frame % sprites[3].getFrameCount())));
							frame = overflowProtect(frame + 1);
							if (frame >= sprites[3].getFrameCount()) {
								deathAnimFlag = true;
							}
						}
					}
				}
				

			}
				
		});
		timer.start();

	}
	
	//AIState setter/getter (May 26th, 2017)
	public void setAIState(char newState) { aiState = newState; }
	public char getAIState() { return aiState ; }
	
	public String getEnemyType() { return enemyType; }

	public void setEnemyType(String enemyType) { this.enemyType = enemyType; }
	
 	public void behave(Player joe) {
 		if (animState != DEAD) {
 			switch(aiState) {
 			case RUSH:
 				direction = true;
 				if (joe.getX() < this.getX())
 					direction = false;
 				this.moveSide(direction);
 				if (Math.abs(joe.x - this.x) > 200)
 					this.setAIState(AgWANDER);
 				break;
 			
 				//True wandering, no player detection, 100% passive
	 		case WANDER:
	 			wander();
		 		break;
			case AgWANDER: //Wander triggering rushdown
				wander();
				if (Math.abs(joe.x - this.x) <= 200)
					this.setAIState(RUSH);
				break;
			case RUN:
				if (joe.getX() > this.getX())
					direction = false;
				this.moveSide(direction);
				if (Math.abs(joe.x - this.x) > 200)
					this.setAIState(RtWANDER);
				break;
			case RtWANDER: //Wander triggering retreat
				wander();
				if (Math.abs(joe.x - this.x) <= 200)
					this.setAIState(RUN);
				break;
			case LgWANDER:
				direction = ledgeGuard(direction);
				moveSide(direction);
				break;
			case BIRD:
				direction = false;
				this.y = this.getDefaultY() + (int)(100 * Math.sin(((2*Math.PI * sineValue)/(150))));
				sineValue--;
				if (this.x < 750 && this.x > -750)
					moveSide(direction); //Prevents birds from rushing you down until you are near their location
				setYSpeed(-0.2);
				break;
			case SADBIRD: //Screwed up old bird code that we kept because it's funny
				this.setYSpeed(this.getYSpeed() - LivingObject.GRAV + Math.sin(this.x));
				moveSide(false);
				break;
			case DWAIT: //Wait for fight
				if (this.getHealth() < 75) {
					this.setAIState(ATTACK);
					this.setFrame(0);
				}
				break;
			case DUEL:
				if (joe.x - this.x >= 95) {
					direction = true;
					moveSide(direction);
				}
				else if (joe.x - this.x <= -65) {
					direction = false;
					moveSide(direction);
				}
				else if (Math.abs(joe.x - this.x) >= 800) {
					this.setXSpeed(0);
				}
				else if (attackCD <= 0) {
					this.setAIState(ATTACK);
				}
				break;
			case ATTACK:
				this.setXSpeed(0);
				if (joe.x - this.x <= 0) {
					direction = false;
				}
				else {
					direction = true;
				}
				if (this.frame >= 15) {
					this.attackCD = 15;
					this.setAIState(DUEL);
					this.stabCrabSwingStart = false;
				}
				break;
 			}
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
	private void wander() {
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

	public void setFrame(int i) {
		frame = i;
	}

	public int getExpGain() {
		return expGain;
	}

	public void setExpGain(int expGain) {
		this.expGain = expGain;
	}
	
	public Item getDrop() {
		return drop;
	}

	public void setDrop(Item drop) {
		this.drop = drop;
	}
	
	/*
	Name: offsetBird
	Description: Offsets a bird's sine value to allow for birds in non-linear groups
	Parameters: One LinkedEntity
	Return Value/Type: N/A
	Dependencies: Logic.Entity
	Exceptions: N/A
	Date Created: June 17th, 2017
	Date Modified: June 17th, 2017
	 */
	public void offsetBird(int offsetFactor) {
		sineValue += offsetFactor;
	}
	
}
