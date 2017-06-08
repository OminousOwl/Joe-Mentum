/**
 *@Name Cosmin Baciu, Quinn Fisher, Olivier H�bert
 *@DateCreated: May 30th, 2017
 *@DateModified: June 8th, 2017
 *@Description: The class used to handle the actual game physics and gameplay
 */

//TODO Known bugs:


package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Intermediary.LinkedEntity;
import Intermediary.LinkedList;
import Intermediary.Monster;
import Intermediary.Player;
import Logic.Entity;
import Logic.LivingObject;
import hsa2.GraphicsConsole;
import jaco.mp3.player.MP3Player;

/*
 *       __
 * 	 ___( o)> quack
 *   \ <_. )
 *    `---' 
 */

public class MainGame extends JFrame implements EventListener, KeyListener {

	/**** Constants ****/
	private final int RUNNING = 0;// the ID# for the game's running state.
	private final int PAUSED = 1;// the ID# for the game's paused state with the basic menu.
	private final int PAUSED_OPTIONS = 2;// the ID# for the game's paused state with the options menu.
	private final int GAME_OVER = 3;
	
	private final Set<Integer> pressed = new HashSet<Integer>(); //Stores all currently pressed keys. This allows momentum to be maintained when releasing A or D when holding the other

	/**** Variables ****/
	private int state = RUNNING;// the flag that triggers different behaviors in the program
	private boolean muted = false;
	public static final Player joe = new Player(); // The man, the myth, the legend himself, Joe
	private LinkedList theLevel;
	GraphicsConsole gc = new GraphicsConsole();
	MP3Player spagoogi = new MP3Player();
	
	BufferedImage i1;
	BufferedImage i2;
	BufferedImage i3;
	BufferedImage i4;
	BufferedImage i5;

	public static void main(String[] args) {
		new MainGame().startGame();
		
	}

	@SuppressWarnings("serial")
	public MainGame() {
		super("Joe-Mentum");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(768, 432);
		gc.setSize(650, 432);
		
		// Sets up the game panel
		JPanel game = new JPanel();

		joe.x = gc.getWidth()/2;
		joe.y = 100;
		joe.width = 30;
		joe.height = 30;		
		
		theLevel = new LinkedList();
		
		theLevel.add(new LinkedEntity(0, 332, 768, 100, Color.BLACK, 'f'));
		theLevel.add(new LinkedEntity(468, 232, 100, 100, Color.BLUE, 'w'));
		theLevel.add(new LinkedEntity(800, 175, 100, 100, Color.BLUE, 'w'));
		
		game.setDoubleBuffered(true);
		add(game);
		setVisible(false);
		setState(PAUSED);
		
		gc.setFont(new Font("Baskerville Old Face", Font.BOLD, 24));
		gc.addKeyListener(this);
		gc.setVisible(false);
		
		enemies = new Monster(850, 100, 15, 15, 3, 2, 0.5, Monster.LgWANDER);
		
		spagoogi.addToPlayList(new File("music/StabCrabV2Orchestra.mp3"));
		spagoogi.skipForward();
		spagoogi.play();
		
		try {
			i1 = ImageIO.read(new File("images/1.png"));
			i2 = ImageIO.read(new File("images/2.png"));
			i3 = ImageIO.read(new File("images/3.png"));
			i4 = ImageIO.read(new File("images/4.png"));
			i5 = ImageIO.read(new File("images/5.png"));
		} catch (IOException e) {
			// catch
		}
		
		animate();
		
		new GameMenu(this);
		
	
	}
	
	public void startGame() {
		while (true) {
			if (this.state == 0)
				run();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				System.out.println("A thing broke");
			}
		}
	}

	public void animate() {
		
		synchronized(gc) {
			gc.clear();
			
			
			gc.drawImage(i1, 0, 0, gc.getWidth(), gc.getHeight());
			gc.drawImage(i2, 0, 0, gc.getWidth(), gc.getHeight());
			gc.drawImage(i3, 0, 0, gc.getWidth(), gc.getHeight());
			gc.drawImage(i4, 0, 0, gc.getWidth(), gc.getHeight());
			gc.drawImage(i5, 0, 0, gc.getWidth(), gc.getHeight());
			
			paintLevelComponent(theLevel.getHead());
			
			gc.setColor(Color.GREEN);
			gc.fillRect(enemies.x, enemies.y, enemies.width, enemies.height);
			
			gc.setColor(Color.RED);
			gc.fillRect((int) joe.getX(), (int) joe.getY(), (int) joe.getWidth(), (int) joe.getHeight());
			
			if (this.state == GAME_OVER) {
				gc.drawString("GAME OVER", gc.getWidth()/2 - 50, gc.getHeight()/2);
			}
			
			/*//Outdated Debug Code
			gc.setColor(Color.GREEN);
			gc.fillRect((int) wall.ledges[0].getX(), (int) wall.ledges[0].getY(), (int) wall.ledges[0].getWidth(), (int) wall.ledges[0].getHeight());
			gc.fillRect((int) wall.ledges[1].getX(), (int) wall.ledges[1].getY(), (int) wall.ledges[1].getWidth(), (int) wall.ledges[1].getHeight());
			*/
		}
		
		
	}
	
	public void paintLevelComponent (LinkedEntity activeEntity) {
		if (activeEntity == null) {
			return;
		}
		else {
			gc.setColor(activeEntity.colour);
			gc.fillRect(activeEntity.x, activeEntity.y, activeEntity.width, activeEntity.height);
			paintLevelComponent(activeEntity.next);
		}
	}

	/*
	 Name: run 
	 Description: Performs each command that needs to be done in a single frame
	 Parameters: None
	 Return Value/Type: N/A 
	 Dependencies: Joe (Entity.Player object(
	 Exceptions: N/A 
	 Date Created: May 30th, 2017
	 Date Modified: June 7th, 2017
	 */
	public void run() {
		gravity(joe);
		gravity(enemies);
		move(joe);
		move(enemies);
		checkLevelCollision(joe, theLevel.getHead());
		checkLevelCollision(enemies, theLevel.getHead());
		enemies.behave(joe);
		manageCD(theLevel.getHead());
		scroll(joe, theLevel.getHead());
		scroll(joe, enemies);
		joe.x = gc.getWidth()/2;
		deathCheck(joe);
		
		
		//Always keep animate() as the last function
		animate();
	}//end run

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
	 Name: checkLevelCollision 
	 Description: Checks to see if any entities collide
	 Parameters: Two entities 
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 7th, 2017 
	 Date Modified: June 7th, 2017 
	 */
	public void checkLevelCollision (Entity a, LinkedEntity b) {
		if (b == null) {
			return;
		}
		else {
			checkCollision(a, b);
			checkLevelCollision(a, b.next);
		}
	}
	
	/*
	 Name: checkLevelCollision 
	 Description: Checks to see if any entities collide
	 Parameters: Two entities 
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 8th, 2017 
	 Date Modified: June 8th, 2017 
	 */
	public void checkLevelCollision (Monster a, LinkedEntity b) {
		if (a == null) {
			return;
		}
		else {
			if (b == null) {
				return;
			}
			else {
				checkCollision(a, b);
				if (a.associatedTerrain == null && a.intersects(b)) {
					a.setAssociatedTerrain(b);
				}
				checkLevelCollision(a, b.next);
			}
		}
		checkLevelCollision(a.next, b);
		
	}
	
	/*
	 Name: manageCD 
	 Description: Manages ledge cooldowns, reducing them on each frame
	 Parameters: One Entity
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 5th, 2017
	 Date Modified: June 7th, 2017
	 */
	public void manageCD (LinkedEntity a) {
		if (a == null){
			return;
		}
		else if (!a.ledgeFlag) {
			a.resetCounter--;
			if (a.resetCounter == 0) {
				a.ledgeFlag = true;
			}
		}
		manageCD(a.next);
	}
	
	
	/*
	 Name: scroll 
	 Description: Scrolls all objects on screen in relation to Joe's current location
	 Parameters: One Player and One LinkedEntity
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 8th, 2017
	 Date Modified: June 8th, 2017
	 */
	public void scroll(Player joe, LinkedEntity a) {
		if (a == null) {
			return;
		}
		
		a.x -= joe.x - gc.getWidth()/2;
		a.floorbox.x -= joe.x - gc.getWidth()/2;
		a.ledges[0].x -= joe.x - gc.getWidth()/2;
		a.ledges[1].x -= joe.x - gc.getWidth()/2;
		scroll(joe, a.next);
	}
	
	/*
	 Name: scroll 
	 Description: Scrolls all objects on screen in relation to Joe's current location
	 Parameters: One Player and One Monster
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 8th, 2017
	 Date Modified: June 8th, 2017
	 */
	public void scroll(Player joe, Monster a) {
		if (a == null) {
			return;
		}
		
		a.x -= joe.x - gc.getWidth()/2;
		scroll(joe, a.next);
	}
	
	/*
	 Name: deathCheck 
	 Description: Checks to see if a game over state is needed in response to Joe's death
	 Parameters: One Player
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 8th, 2017
	 Date Modified: June 8th, 2017
	 */
	public void deathCheck(Player joe) {
		if (joe.y >= gc.getHeight() + gc.getHeight()/6 || joe.getHealth() == 0) {
			this.setState(GAME_OVER);
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
			this.dispose();
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
		
		if (key == KeyEvent.VK_M) { //Mute
			muted = !muted;
			if (muted) {
				spagoogi.pause();
			}
			else {
				spagoogi.play();
			}
				
		}
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
