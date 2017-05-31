package GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/*
 *Project: ICS4U - Final Project - Joe-Mentum
 *Author: Olivier J Hébert
 *Date Created: 29/05/2017
 *Date Edited: 29/05/2017
 *Description: This is the interface for the keyboard that handles all player input.
 */
public class KeyControl implements KeyListener {
	/**
	 *Title:
	 * Key Pressed
	 *Author:
	 * Olivier J Hébert
	 *Date Created:
	 * 29/05/2017
	 *Date Edited:
	 * 29/05/2017
	 *Parameters:
	 * N/A
	 *Dependencies: 
	 * Keylistener
	 *Exceptions:
	 * N/A
	 *Description: this object is a listener that manages all keyboard input
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.equals('w') || e.equals(' ')){//Player jumps when spacebar/the 'w' key is pressed
			Joe.jump();
		}//end if
		if(e.equals('d')){//Player moves right when the 'd' key is pressed
			moveSide(Joe.getSpeed() > 0);
		}//end if
		if(e.equals('a')){//Player moves left when the 'a' key is pressed
			moveSide(Joe.getSpeed() > 0);
		}//end if
		if(e.equals('s')){
			Joe.pickupItem();
		}//end if
		if(e.equals('e')){
			//use item
		}//end if
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}//end class
