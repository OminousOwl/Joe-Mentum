/*
Name: Quinn Fisher
Date Created: May 25th, 2017
Date Modified: June 10th, 2017
Description: The class containing data and methods to handle moving entities in game (Player, bosses, enemies, etc.)
 */

package Logic;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import Intermediary.Player;
import anim.Spritesheet;

public class LivingObject extends Entity {
	
	//TODO Move these constants to Entity.Java
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
		
		//V2 = V1 + at
		//Handles character acceleration
		if (Math.abs(this.getXSpeed()) < maxSpeed) //Accelerates if not already at top speed
			this.setXSpeed(this.getXSpeed() + directionMultiplier * ACC);
		
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
		//System.out.println("Jump " + System.currentTimeMillis()); //TODO reimplement as debug tool
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

	
}
