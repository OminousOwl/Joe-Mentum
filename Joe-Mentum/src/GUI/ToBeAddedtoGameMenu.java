package GUI;

import Logic.LivingObject;
import Intermediary.Player;

//TODO Do what the class name tells you to do
public class ToBeAddedtoGameMenu {
	
	
	public static Player Joe;
	
	/*
	Name: gravity
	Description: Adjusts the ySpeed of an object to cause it to fall
	Parameters: One LivingObject
	Return Value/Type: N/A
	Dependencies: Logic.LivingObject
	Exceptions: N/A
	Date Created: May 29th, 2017
	Date Modified: May 29th, 2017
	 */
	public void gravity(LivingObject e) {
		e.setYSpeed(e.getYSpeed() + LivingObject.GRAV);
	}
}
