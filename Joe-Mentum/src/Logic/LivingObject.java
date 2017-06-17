/*
Name: Quinn Fisher
Date Created: May 25th, 2017
Date Modified: June 16th, 2017
Description: The class containing data and methods to handle moving entities in game (Player, bosses, enemies, etc.)
 */

package Logic;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import Intermediary.Player;
import anim.Spritesheet;

public class LivingObject extends Entity {

	private static final long serialVersionUID = -2488364476039279905L;
	
	private static final double ACC = 1.0; //Constant used to define acceleration rate
	public static final double GRAV = 0.2; //Constant used as gravitational acceleration
	static final double INIT_JUMP = -7.2; //Constant used to define initial jump speed
	private static final double FF = 7.0; //Constant used to define initial fast fall speed
	private static final double FF_ACC = 0.4; //Constant used to add gravitational acceleration when fastfalling

	
	public final static int IDLE = 0;
	public final static int MOVE = 1;
	
	
	private int health; 
	private int attack;
	private double maxSpeed;
	private boolean attackState;
	private Item item;
	
	protected boolean isJoe = false;
	
	private int damageFrames;
	
	protected BufferedImage currentFrame;
	protected int frame;
	protected boolean direction = true;
	
	protected Spritesheet[] sprites;
	protected String[] filepaths;
	
	public int getHealth() { return health; }
	public void setHealth (int health) { this.health = health; };
	
	public int getAttack() { return attack; }
	public void setAttack (int attack) { this.attack = attack; }
	
	public double getSpeed() { return maxSpeed; }
	public void setSpeed (double speed) { this.maxSpeed = speed; }
	
	public boolean getAttackState() { return attackState; }
	public void setAttackState(boolean attackState) { this.attackState = attackState; }
	
	public boolean getDirection() { return direction; }
	public void setDirection(boolean direction) { this.direction = direction; }
	
	public BufferedImage getCurrentFrame() { return currentFrame; }
	public void setCurrentFrame(BufferedImage currentFrame) { this.currentFrame = currentFrame;}
	
	public LivingObject() {

	}
	
	/*
	Name: damage
	Description: Deals damage to this object
	Parameters: One integer (the damage to be dealt)
	Return Value/Type: N/A
	Dependencies: N/A
	Exceptions: N/A
	Date Created: May 25th, 2017
	Date Modified: May 25th, 2017
	 */
	public void damage(int damage) {
		health -= damage;
	}
	
	/*
	Name: moveSide
	Description: Handles movement to either side
	Parameters: One boolean (direction)
	Return Value/Type: N/A
	Dependencies: N/A
	Exceptions: N/A
	Date Created: May 25th, 2017
	Date Modified: June 4th, 2017
	 */
	public void moveSide(boolean side) {
		
		int directionMultiplier = 1;
		if (!side)
			directionMultiplier = -1;
		
		//Handles character acceleration
		if (Math.abs(this.getXSpeed()) < maxSpeed) //Accelerates if not already at top speed
			this.setXSpeed(this.getXSpeed() + (directionMultiplier * ACC));
		
		if (directionMultiplier * this.getXSpeed() < 0) { //If the intended direction and the current direction do not match, turn around.
			//System.out.println("Turn at " + System.currentTimeMillis());
			this.setXSpeed(this.getXSpeed() * -1);
		}
		
		
	}
	
	
	/*
	Name: jump
	Description: Handles initial jumps, adding vertical speed to the entity
	Parameters:
	Return Value/Type: N/A
	Dependencies: N/A
	Exceptions: N/A
	Date Created: May 26th, 2017
	Date Modified: June 10th, 2017
	 */
	public void jump() {
		if (this.getYSpeed() == 0 || this.animState != Player.VERT)
			this.setYSpeed(INIT_JUMP);
	}
	
	/*
	Name: fastFall
	Description: Handles faster falling, adding extra acceleration to gravity
	Parameters:
	Return Value/Type: N/A
	Dependencies: N/A
	Exceptions: N/A
	Date Created: June 1st, 2017
	Date Modified: June 2nd, 2017
	 */
	public void fastFall() {
		if (this.getYSpeed() < FF)
			this.setYSpeed(FF);
		else
			this.setYSpeed(this.getYSpeed() + FF_ACC);
	}
	
	
	/*
	Name: getDirectionVal
	Description: Converts the boolean direction into a treatable double value
	Parameters: One boolean value
	Return Value/Type: One double
	Dependencies: N/A
	Exceptions: N/A
	Date Created: May 29th, 2017
	Date Modified: May 29th, 2017
	 */
	private double getDirectionVal(boolean direction)  {
		if (direction)
			return 1.0;
		else
			return -1.0;
	}
	
	public int getAnimState() { return this.animState; }
	public void setAnimState(int animState) { this.animState = animState;}
	
	public BufferedImage flipHorizontal(BufferedImage source) {
		if (!this.direction) {
			AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
			at.translate(-source.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			return op.filter(source, null);
		}
		else {
			return source;
		}
		
	}
	public int getDamageFrames() {
		return damageFrames;
	}
	public void setDamageFrames(int damageFrames) {
		this.damageFrames = damageFrames;
	}
	
	
	/*
	Name: collide
	Description: Handles collision results between any two entities
	Parameters:
	Return Value/Type: N/A
	Dependencies: None
	Exceptions: N/A
	Date Created: May 29th, 2017
	Date Modified: June 15th, 2017
	 */
	public void collide(Entity b){
		if(b.getCollideType() == SOLID){
				
			if (this.intersects(b.ledges[0]) && this.direction || this.intersects(b.ledges[1]) && !this.direction) {
				
				if (this.ledgeFlag && b.ledgeFlag) {
					if (this.getySpeed() <= LivingObject.INIT_JUMP + 2*LivingObject.GRAV) {
						b.ledgeFlag = false;
						b.resetCounter = 40;
					}
					else {
						//System.out.println("Triggered at " + System.currentTimeMillis());
						this.animState = Player.LEDGE;
						this.height = 84;
						this.setxSpeed(0);
						this.setySpeed(0);
						this.y = b.ledges[0].y - 14;
						
						if (this.intersects(b.ledges[0])) {
							this.x = b.ledges[0].x - 6;
						}
						else
							this.x = b.ledges[1].x - 1;
					}
				}
			}
			solidCollide(b);
		}
		
		else if(b.getCollideType() == BOUNCE){
			this.setySpeed((getySpeed()*-1));
		}
		else if(b.getCollideType() == SKIP){
			this.setySpeed((getySpeed()*-1)/2);
		}
	}


	
	
	/*
	Name: solidCollide
	Description: Handles collision on a surface to nullify gravity
	Parameters:
	Return Value/Type: N/A
	Dependencies: None
	Exceptions: N/A
	Date Created: June 4th, 2017
	Date Modified: June 16th, 2017
	 */
	public void solidCollide(Entity b) {
		
		
		if (this.y >= b.y + b.height - b.height/10 && this.getYSpeed() < 0.2 && b.resetCounter <= 0) {
			this.setYSpeed(1.0);
			b.resetCounter = 10;
		}
		else if (this.y > b.y + 5 && b.resetCounter <= 0) { //If collision takes place outside the wall's surface
			this.setxSpeed(0);
			if (this.x < b.x + b.width/2) { //Collision from left, judging from mid point
				this.x = b.x - this.width;
			}
			else //Collision from right
				this.x = b.x + b.width;
		}
		
		else if (b.resetCounter <= 0 || !this.isJoe) {
			if (this.getySpeed() > 0) { //Only collides on drop, otherwise jumping is impossible
				this.setySpeed(0);
					
				if (this.intersects(b.floorbox) && this.getySpeed() >= -6.6)
					this.y = b.y - this.height;
			}

		}
	}
	
	/*
	Name: overflowProtect
	Description: Prevents frame counters from exceeding int limitations
	Parameters: One Integer
	Return Value/Type: One Integer
	Dependencies: N/A
	Exceptions: N/A
	Date Created: June 16th, 2017
	Date Modified: June 16th, 2017
	 */
	public int overflowProtect(int frame) {
		if (frame == Integer.MAX_VALUE) {
			return Integer.MIN_VALUE + 1;
		}
		else if (frame == Integer.MIN_VALUE) {
			return Integer.MAX_VALUE - 1;
		}
		else {
			return frame;
		}
	}
	
	/*
	 Name: randNumber
	 Description: Generates a random number
	 Parameters: Two Integers
	 Return Value/Type: A random number within the range of the parameters
	 Dependencies: Java.Math
	 Creation Date: October 23rd, 2015
	 Modification Date: April 13th, 2017
	 Throws: None
	*/
	public static int randNumber(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	
}
