/*
Name: Quinn Fisher
Date Created: May 25th, 2017
Date Modified: May 25th, 2017
Description: The class containing data and methods to handle moving entities in game (Player, bosses, enemies, etc.)
 */

package Logic;

public class LivingObject extends Entity {

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
	Parameters: One boolean (direction) and one integer (duration)
	Return Value/Type: N/A
	Dependencies: N/A
	Exceptions: N/A
	Date Created:
	Date Modified:
	 */
	public void moveSide(boolean side, int duration) {
		//TODO
	}
	
	/*
	Name: jump
	Description: Handles vertical movement
	Parameters:
	Return Value/Type: N/A
	Dependencies: N/A
	Exceptions: N/A
	Date Created:
	Date Modified:
	 */
	public void jump() {
		//TODO
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
		
	}
	
}
