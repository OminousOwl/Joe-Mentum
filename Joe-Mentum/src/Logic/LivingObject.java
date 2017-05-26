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
	private static final double GRAV = -2.0; //Constant used as gravitational acceleration
	private static final double INIT_JUMP = 3.0; //Constant used to define initial jump speed

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
	Date Modified: May 26th, 2017
	 */
	public void moveSide(boolean side) {
		
		int directionMultiplier = 1;
		if (!side)
			directionMultiplier = -1;
		
		//V2 = V1 + at
		//Handles character acceleration
		if (this.getXSpeed() != maxSpeed)
			this.setXSpeed(this.getXSpeed() + ACC);
		
		this.x = this.getX() + (side * this.getXSpeed());
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
	Name: kill
	Description: Removes the entity
	Parameters:
	Return Value/Type: N/A
	Dependencies: N/A
	Exceptions: N/A
	Date Created:
	Date Modified:
	 */
	public void kill() {
		//TODO
	}
	
}
