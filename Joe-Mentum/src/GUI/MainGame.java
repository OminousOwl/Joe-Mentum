package GUI;

import java.awt.event.*;

public class MainGame implements ActionListener {
	/**** Constants ****/
	private static final int RUNNING = 0;//the ID# for the game's running state.
	private static final int PAUSED_MENU = 1;//the ID# for the game's paused state with the basic menu.
	private static final int PAUSED_OPTIONS = 2;//the ID# for the game's paused state with the options menu.
	
	/**** Variables ****/
	private static int state = RUNNING;//the flag that triggers different behaviors in the program
	
	public static void setState(int newState){
		state = newState;
	}//end setState
	
	@Override
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
