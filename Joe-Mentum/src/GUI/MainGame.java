package GUI;

import java.awt.event.*;

public class MainGame implements ActionListener, KeyListener {
	/**** Constants ****/
	private final int RUNNING = 0;//the ID# for the game's running state.
	private final int PAUSED_MENU = 1;//the ID# for the game's paused state with the basic menu.
	private final int PAUSED_OPTIONS = 2;//the ID# for the game's paused state with the options menu.
	
	/**** Variables ****/
	private int state = 0;
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Options")){
			
		}//end if
		
	}//end actionPerformed

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
