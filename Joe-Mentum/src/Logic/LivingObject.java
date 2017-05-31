/*
Name: Quinn Fisher
Date Created: May 25th, 2017
Date Modified: May 26th, 2017
Description: The class containing data and methods to handle moving entities in game (Player, bosses, enemies, etc.)
 */

package Logic;

public class LivingObject extends Entity {
	
	//TODO Move these constants to Entity.Java
	private static final double ACC = 0.5; //Constant used to define acceleration rate
	public static final double GRAV = 0.2; //Constant used as gravitational acceleration
	private static final double INIT_JUMP = -3.0; //Constant used to define initial jump speed

	private int health; 
	private int attack;
	private double maxSpeed;
	private boolean attackState;
	private Item item;
	
	public int getHealth() { return health; }
	public void setHealth (int health) { this.health = health; };
	
	public int getAttack() { return attack; }
	public void setAttack (int attack) { this.attack = attack; }
	
	public double getSpeed() { return maxSpeed; }
	public void setSpeed (double speed) { this.maxSpeed = speed; }
	
	public boolean getAttackState() { return attackState; }
	public void setAttackState(boolean attackState) { this.attackState = attackState; }
	
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
	Date Modified: May 27th, 2017
	 */
	public void moveSide(boolean side) {
		
		int directionMultiplier = 1;
		if (!side)
			directionMultiplier = -1;
		
		//V2 = V1 + at
		//Handles character acceleration
		if (this.getXSpeed() != maxSpeed) //Accelerates if not already at top speed
			this.setXSpeed(this.getXSpeed() + ACC);
		
		if (directionMultiplier * this.getXSpeed() < 0) { //If the intended direction and the current direction do not match, turn around.
			this.setXSpeed(this.getXSpeed() * -1);
		}
		
		this.x = (int) (this.getX() + (getDirectionVal(side) * this.getXSpeed())); //TODO Verify if this needs better rounding
	}
	
	
	/*
	Name: jump
	Description: Handles initial jumps, adding vertical speed to the entity
	Parameters:
	Return Value/Type: N/A
	Dependencies: N/A
	Exceptions: N/A
	Date Created: May 26th, 2017
	Date Modified: May 26th, 2017
	 */
	public void jump() {
		if (this.getYSpeed() == 0)
			this.setYSpeed(INIT_JUMP);
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
	
}
