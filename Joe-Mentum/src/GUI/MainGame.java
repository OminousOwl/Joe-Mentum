package GUI;

import java.awt.event.*;
import java.util.EventListener;

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

public class MainGame extends JFrame implements Runnable, EventListener {
	
	/**** Constants ****/
	private static final int RUNNING = 0;//the ID# for the game's running state.
	private static final int PAUSED_MENU = 1;//the ID# for the game's paused state with the basic menu.
	private static final int PAUSED_OPTIONS = 2;//the ID# for the game's paused state with the options menu.
	
	/**** Variables ****/
	private static int state = RUNNING;//the flag that triggers different behaviors in the program
	public static final Player Joe = new Player(); //The man, the myth, the legend himself, Joe

	//TODO get this POS out of our code
	public static void main(String[] args) { 
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
		setVisible(true);
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
	
	
	public void setState(int newState){
		state = newState;
	}//end setState
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")){
			state = state-1;
		}//end if
		
		if(e.getActionCommand().equals("Options")){
			state = PAUSED_OPTIONS;
		}//end if
		
		if(e.getActionCommand().equals("Quit")){
			//terminate game
		}//end if
	}//end actionPerformed

}//end class
