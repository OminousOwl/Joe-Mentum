/*
Name: Quinn Fisher
Date Created: May 30th, 2017
Date Modified: May 30th, 2017
Description: The class used to handle the actual game physics and runtime
 */

package GUI;

import javax.swing.JFrame;

import Intermediary.Player;
import Logic.Entity;
import Logic.LivingObject;

/*
 *       __
 * 	 ___( o)> quack
 *   \ <_. )
 *    `---' 
 */

public class MainGame extends JFrame implements Runnable {
	
	public static final Player Joe = new Player(); //The man, the myth, the legend himself, Joe

	//TODO get this POS out of our code
	public static void main(String[] args) { 
		System.out.println("Hello world!");
		MainGame test = new MainGame();
	}
	
	public MainGame() {
		//Sets up the game panel
		setSize(500, 500);
		Entity floor = new Entity();
		floor.x = 300;
		floor.height = 200;
		floor.y = 0;
		floor.width = 500;
	}
	

	
	public void run() {
		
	}
	
	/*
	Name: gravity
	Description: Adjusts the ySpeed of an object to cause it to fall
	Parameters: One LivingObject
	Return Value/Type: N/A
	Dependencies: Logic.LivingObject
	Exceptions: N/A
	Date Created: May 29th, 2017 (Created in another class)
	Date Modified: May 29th, 2017
	 */
	public void gravity(LivingObject e) {
		e.setYSpeed(e.getYSpeed() + LivingObject.GRAV);
	}


}
