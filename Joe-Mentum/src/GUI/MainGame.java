/**
 *@Name Cosmin Baciu, Quinn Fisher, Olivier H�bert
 *@DateCreated: May 30th, 2017
 *@DateModified: June 5th, 2017
 *@Description: The class used to handle the actual game physics and gameplay
 */

//TODO Known bugs:


package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import Intermediary.LinkedEntity;
import Intermediary.LinkedList;
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
<<<<<<< HEAD
	public static void main(String[] args){
		new MainGame();
	}//end main
	
=======

>>>>>>> parent of 4f4615f... kill me (At least we know what not to do to display the MainGame?)
	/**** Constants ****/
	private final int RUNNING = 0;// the ID# for the game's running state.
	private final int PAUSED_MENU = 1;// the ID# for the game's paused state with the basic menu.
	private final int PAUSED_OPTIONS = 2;// the ID# for the game's paused state with the options menu.
	
	private final Set<Integer> pressed = new HashSet<Integer>(); //Stores all currently pressed keys. This allows momentum to be maintained when releasing A or D when holding the other

	/**** Variables ****/
	private int state = RUNNING;// the flag that triggers different behaviors in the program
	public static final Player joe = new Player(); // The man, the myth, the legend himself, Joe
	private LinkedList theLevel = new LinkedList(0, 332, 768, 100, Color.BLACK, 'f');
	

	// TODO get this POS out of our code
	/*public static void main(String[] args) {
		new MainGame();
	}*/

	public MainGame() {
		super("Joe-Mentum");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Sets up the game panel
		setSize(768, 432);
		JPanel game = new JPanel();

		joe.x = 100;
		joe.y = 100;
		joe.width = 30;
		joe.height = 30;
		
		theLevel.add(new LinkedEntity(468, 232, 100, 100, Color.BLACK, 'w'));//this is only temporary, the linked list will eventually be used to read all of it's entities in from a file

		add(game);
		setVisible(true);
		repaint();

		addKeyListener(this);
		

		while (true) {
			if (this.state == 0)
				run();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("A thing broke");
			}
		}
		
	}

	public void paint(Graphics g) {
		LinkedEntity runner = theLevel.getHead();
		int i = 1;//the element in the list being drawn
		
		// TODO update with image
		do{
			g.setColor(runner.colour);
			g.fillRect(runner.x, runner.y, runner.width, runner.height);
			i++;
			runner = theLevel.search(i);
		}while(runner.equals(theLevel.getTail()));
		
		/*
		g.setColor(Color.GREEN);
		g.fillRect((int) wall.ledges[0].getX(), (int) wall.ledges[0].getY(), (int) wall.ledges[0].getWidth(), (int) wall.ledges[0].getHeight());
		g.fillRect((int) wall.ledges[1].getX(), (int) wall.ledges[1].getY(), (int) wall.ledges[1].getWidth(), (int) wall.ledges[1].getHeight());
		*/
		
		g.setColor(Color.RED);
		g.fillRect((int) joe.getX(), (int) joe.getY(), (int) joe.getWidth(), (int) joe.getHeight());
	}//end paint

	/*
	 Name: run 
	 Description: Performs each command that needs to be done in a single frame
	 Parameters: None
	 Return Value/Type: N/A 
	 Dependencies: Joe (Entity.Player object(
	 Exceptions: N/A 
	 Date Created: May 30th, 2017
	 Date Modified: June 4th, 2017
	 */
	public void run() {
		LinkedEntity runner;
		int i = 1;
		
		gravity(joe);
		move(joe);
		//TODO Make an actual checkCollision algorithm
<<<<<<< HEAD
		do{
			runner = theLevel.search(i);
			checkCollision(joe, runner);
			manageCD(runner);
		}while(runner.equals(theLevel.getTail()));
		
=======
		checkCollision(joe, floor);
		checkCollision(joe, wall);
		manageCD(wall);
>>>>>>> parent of 4f4615f... kill me (At least we know what not to do to display the MainGame?)
		repaint();

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

	/*
	 Name: gravity 
	 Description: Handles the horizontal motion of an object
	 Parameters: One LivingObject 
	 Return Value/Type: N/A 
	 Dependencies: Logic.LivingObject 
	 Exceptions: N/A 
	 Date Created: May 30th, 2017
	 Date Modified: May 30th, 2017
	 */
	public void move(LivingObject e) {
		//System.out.println(e.getXSpeed());
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
	
	/*
	 Name: manageCD 
	 Description: Manages ledge cooldowns, reducing them on each frame
	 Parameters: One Entity
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 5th, 2017
	 Date Modified: June 5th, 2017
	 */
	public void manageCD (Entity a) {
		if (!a.ledgeFlag) {
			a.resetCounter--;
			if (a.resetCounter == 0) {
				a.ledgeFlag = true;
			}
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
			//MainGame.dispose(); causes errors because apparently you 'Cannot make a static reference to the non-static method dispose() from the type Window'
		} // end if
	}// end actionPerformed

	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode(); // Tracks the key pressed
		pressed.add(key);
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {// Player jumps when spacebar/the 'w' key is pressed
			joe.jump();
		}

		if (key == KeyEvent.VK_D) {// Player moves right when the 'd' key is pressed
			joe.moveSide(true);
		}

		else if (key == KeyEvent.VK_A) {// Player moves left when the 'a' key is pressed
			joe.moveSide(false);
		}

		if (key == KeyEvent.VK_Q) {//
			joe.pickupItem();
		}
		
		if (key == KeyEvent.VK_S) {// Player fastfalls when the 's' key is pressed
			joe.fastFall();
		}


		if (key == KeyEvent.VK_E) {// picks up an item
			// use item
		}

		if (key == KeyEvent.VK_P) {// pauses the game
			if (this.state == 0) {
				this.setState(1);
			}
			else {
				this.setState(0);
			}
		}
		run();
	}// end keyPressed


	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		pressed.remove(key);
		if (key == KeyEvent.VK_D && !pressed.contains(KeyEvent.VK_A) || key == KeyEvent.VK_A && !pressed.contains(KeyEvent.VK_D)) {
			joe.setXSpeed(0);
		}
	}

	public void keyTyped(KeyEvent e) {

	}
}// end class
