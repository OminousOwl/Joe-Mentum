/**
 *@Name Cosmin Baciu, Quinn Fisher, Olivier Hébert
 *@DateCreated: May 30th, 2017
 *@DateModified: June 2nd, 2017
 *@Description: The class used to handle the actual game physics and gameplay
 */

//TODO Known bugs:
//A fall at fast enough speeds will lodge Joe in the floor, requiring a jump to get free

package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.EventListener;
import java.util.Timer;

import javax.swing.*;

import Intermediary.Player;
import Logic.Entity;
import Logic.LivingObject;

/*
 *       __
 * 	 ___( o)> quack
 *   \ <_. )
 *    `---' 
 */

public class MainGame extends JFrame implements Runnable, EventListener, KeyListener {

	/**** Constants ****/
	private static final int RUNNING = 0;// the ID# for the game's running state.
	private static final int PAUSED_MENU = 1;// the ID# for the game's paused state with the basic menu.
	private static final int PAUSED_OPTIONS = 2;// the ID# for the game's paused state with the options menu.

	/**** Variables ****/
	private static int state = RUNNING;// the flag that triggers different behaviors in the program
	public static final Player joe = new Player(); // The man, the myth, the legend himself, Joe
	private Entity floor = new Entity();

	// TODO get this POS out of our code
	public static void main(String[] args) {
		new MainGame();
	}

	public MainGame() {
		super("Joe-Mentum");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Sets up the game panel
		setSize(768, 432);
		JPanel game = new JPanel();

		floor.x = 0;
		floor.width = 768;

		floor.y = 332;
		floor.height = 100;

		floor.setCollide("Solid");

		joe.x = 100;
		joe.y = 100;
		joe.width = 30;
		joe.height = 30;

		add(game);
		setVisible(true);
		repaint();

		addKeyListener(this);

		while (true)
			run();
	}

	public void paint(Graphics g) {
		// TODO update with image
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 768, 432);
		g.setColor(Color.BLACK);
		g.fillRect((int) floor.getX(), (int) floor.getY(), (int) floor.getWidth(), (int) floor.getHeight());
		g.setColor(Color.RED);
		g.fillRect((int) joe.getX(), (int) joe.getY(), (int) joe.getWidth(), (int) joe.getHeight());
	}

	/*
	 Name: run 
	 Description: Performs each command that needs to be done in a single frame
	 Parameters: None
	 Return Value/Type: N/A 
	 Dependencies: Joe (Entity.Player object(
	 Exceptions: N/A 
	 Date Created: May 30th, 2017
	 Date Modified: June 2nd, 2017
	 */
	public void run() {
		gravity(joe);
		checkCollision(joe, floor);
		move(joe);
		repaint();
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
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
		e.y += e.getYSpeed();
	}

	public void move(LivingObject e) {
		e.x += e.getXSpeed();
	}

	/*
	 Name: checkCollision 
	 Description: Checks to see if any entities collide
	 Parameters: Two entities 
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: May 31st, 2017 
	 Date Modified: May 31st, 2017
	 */
	public void checkCollision(Entity a, Entity b) {
		if (a.intersects(b)) {
			a.collide(b);
		}
	}

	public void setState(int newState) {
		state = newState;
	}// end setState

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Back")) {
			state = state - 1;
		} // end if

		if (e.getActionCommand().equals("Options")) {
			state = PAUSED_OPTIONS;
		} // end if

		if (e.getActionCommand().equals("Quit")) {
			// terminate game
		} // end if
	}// end actionPerformed

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode(); // Tracks the key pressed
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {// Player jumps when spacebar/the 'w' key is pressed
			joe.jump();
		}

		if (key == KeyEvent.VK_D) {// Player moves right when the 'd' key is
									// pressed
			joe.moveSide(true);
		}

		if (key == KeyEvent.VK_A) {// Player moves left when the 'a' key is
									// pressed
			joe.moveSide(false);
		}

		if (key == KeyEvent.VK_Q) {//
			joe.pickupItem();
		}
		
		//TODO Fix bug with fastfalling allowing you to clip through floors
		if (key == KeyEvent.VK_S) {// Player fastfalls when the 's' key is pressed
			joe.fastFall();
		}


		if (key == KeyEvent.VK_E) {// picks up an item
			// use item
		}

		if (key == KeyEvent.VK_P) {// pauses the game
			// MainGame.setState(1);
		}
		run();
	}// end keyPressed

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_A) {
			joe.setXSpeed(0);
		}
	}

	public void keyTyped(KeyEvent e) {

	}

}// end class
