/**
 *@Name Cosmin Baciu, Quinn Fisher, Olivier Hébert
 *@DateCreated: May 30th, 2017
 *@DateModified: June 11th, 2017
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
import Intermediary.MonsterSet;
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
	private MonsterSet enemies = new MonsterSet();
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
		joe.width = 42;
		joe.height = 70;
		
		theLevel = new LinkedList();
		
		theLevel.add(new LinkedEntity(0, 332, 755, 100, Color.BLACK, 'f'));
		theLevel.add(new LinkedEntity(468, 182, 100, 150, Color.BLUE, 'w'));
		theLevel.add(new LinkedEntity(800, 175, 160, 100, Color.BLUE, 'w')).setYScroll(60, 1.0);
		theLevel.add(new LinkedEntity(1015, 332, 100, 100, Color.BLACK, 'f'));
		theLevel.add(new LinkedEntity(1180, 332, 100, 100, Color.BLACK, 'f'));
		theLevel.add(new LinkedEntity(1325, 150, 100, 100, Color.BLUE, 'w')).setYScroll(550, 1.0);
		theLevel.add(new LinkedEntity(1450, 100, 755, 100, Color.BLACK, 'w'));
		
		game.setDoubleBuffered(true);
		add(game);
		setVisible(false);
		setState(PAUSED);
		
		gc.setFont(new Font("Baskerville Old Face", Font.BOLD, 24));
		gc.addKeyListener(this);
		gc.setVisible(false);
		
		enemies.add(new Monster(850, 100, 15, 15, 3, 2, 0.5, Monster.LgWANDER));
		enemies.add(new Monster(1500, 90, 30, 30, 12, 5, 0.8, Monster.LgWANDER));
		
		
		enemies.add(new Monster(475, 100, 30, 30, 3, 2, 0.5, Monster.BIRD)).setAssociatedTerrain(fetch(theLevel.getHead(), 0)); //Test monster
		
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
			paintEnemy(enemies.getHead());
			
			gc.setColor(Color.RED);
			gc.drawImage(joe.getCurrentFrame(), (int) joe.getX(), (int) joe.getY(), (int) joe.getWidth(), (int) joe.getHeight());
			//gc.fillRect((int) joe.getX(), (int) joe.getY(), (int) joe.getWidth(), (int) joe.getHeight());
			
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
			if (activeEntity.x <= 1000 && activeEntity.x >= -1000) { //Only renders within a certain range to avoid overloading the graphics console
				gc.setColor(activeEntity.colour);
				gc.fillRect(activeEntity.x, activeEntity.y, activeEntity.width, activeEntity.height);
			}

			paintLevelComponent(activeEntity.next);
		}
	}
	
	public void paintEnemy(Monster enemy) {
		if (enemy == null) {
			return;
		}
		else {
			if (enemy.x <= 1000 && enemy.x >= -1000 && enemy.getHealth() > 0) { //Only renders within a certain range to avoid overloading the graphics console
				gc.setColor(Color.RED);
				gc.fillRect(enemy.x, enemy.y, enemy.width, enemy.height);
			}

			paintEnemy(enemy.next);
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
	 Date Modified: June 11th, 2017
	 */
	public void run() {
		gravity(joe);
		gravity(enemies.getHead());
		move(joe);
		move(enemies.getHead());
		platformScroll(theLevel.getHead());
		checkLevelCollision(joe, theLevel.getHead());
		checkLevelCollision(enemies.getHead(), theLevel.getHead());
		checkEnemyCollision(joe, enemies.getHead());
		manageEnemyBehavior(enemies.getHead());
		manageCD(theLevel.getHead());
		manageCD(enemies.getHead());
		scroll(joe, theLevel.getHead());
		scroll(joe, enemies.getHead());
		joe.x = gc.getWidth()/2;
		deathCheck(joe);
		playerAnimReset(joe);
		
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
	 Description: Adjusts the ySpeed of an object to cause it to fall 
	 Parameters: One LivingObject 
	 Return Value/Type: N/A 
	 Dependencies: Logic.LivingObject 
	 Exceptions: N/A 
	 Date Created: May 29th, 2017 (Created in another class) 
	 Date Modified: May 29th, 2017
	 */
	public void gravity(Monster e) {
		if (e == null)
			return;
		e.setYSpeed(e.getYSpeed() + LivingObject.GRAV);
		e.y += e.getYSpeed();
		gravity(e.getNext());
	}

	/*
	 Name: move 
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
	 Name: move 
	 Description: Handles the horizontal motion of an object
	 Parameters: One LivingObject 
	 Return Value/Type: N/A 
	 Dependencies: Logic.LivingObject 
	 Exceptions: N/A 
	 Date Created: May 30th, 2017
	 Date Modified: May 30th, 2017
	 */
	public void move(Monster e) {
		if (e == null)
			return;
		//System.out.println(e.getXSpeed());
		e.x += e.getXSpeed();
		move(e.getNext());
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
	
	public void checkEnemyCollision(Player joe, Monster enemy) {
		if (enemy == null) {
			return;
		}
		else if (enemy.getHealth() > 0) {
			if (joe.intersects(enemy) && enemy.damageCD == 0) {
				if (joe.getYSpeed() >= 0.4) {
					enemy.damage(joe.getAttack());
					System.out.println("Enemy got rekt");
					joe.setYSpeed(-6.0);
				}
				else {
					joe.damage(enemy.getAttack());
					System.out.println("Joe got rekt");
				}
				enemy.damageCD = 70;
			}
		}
		checkEnemyCollision(joe, enemy.next);
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
	 Name: manageCD 
	 Description: Manages damage cooldowns, preventing any hit from one-shoting Joe
	 Parameters: One Monster
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 8th, 2017
	 Date Modified: June 8th, 2017
	 */
	public void manageCD (Monster a) {
		if (a == null){
			return;
		}
		else if (a.damageCD > 0) {
			a.damageCD--;
		}
		manageCD(a.next);
	}
	
	/*
	 Name: manageEnemyBehavior 
	 Description: Triggers ai scripts for all enemies
	 Parameters: One Monster
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 11th, 2017
	 Date Modified: June 11th, 2017
	 */
	public void manageEnemyBehavior(Monster e) {
		if (e == null)
			return;
		e.behave(joe);
		manageEnemyBehavior(e.getNext());
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
	 Name: platformScroll 
	 Description: Handles vertically moving platforms
	 Parameters: One LinkedEntity
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 10th, 2017
	 Date Modified: June 10th, 2017
	 */
	public void platformScroll(LinkedEntity a) {
		if (a == null)
			return;
		if (a.getYScroll() != 0) {
			a.y += a.getySpeed();
			a.floorbox.y += a.getySpeed();
			a.ledges[0].y += a.getySpeed();
			a.ledges[1].y += a.getySpeed();
			if (a.y == a.getDefaultY() + a.getYScroll() || a.y == a.getDefaultY()) {
				a.setySpeed(a.getYSpeed() * -1);
			}
		}
		platformScroll(a.next);
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
		if (joe.y >= gc.getHeight() + gc.getHeight()/6 || joe.getHealth() <= 0) {
			this.setState(GAME_OVER);
		}
	}
	
	/*
	 Name: playerAnimReset 
	 Description: Fixes any animation inconsistencies
	 Parameters: One Player
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 11th, 2017
	 Date Modified: June 11th, 2017
	 */
	public void playerAnimReset(Player joe) {
		if (joe.getAnimState() != Player.LEDGE) {
			if (joe.getXSpeed() == 0 && (joe.getYSpeed() == 0 || joe.getySpeed() == 0.2)) {
				joe.setAnimState(Player.IDLE);
			}
			else if (joe.getYSpeed() == 0 || joe.getySpeed() == 0.2) {
				joe.setAnimState(Player.MOVE);
			}
		}
		
	}
	
	/*
	Name: fetch
	Description: Retrieves an element of designated depth from the list
	Parameters: One LinkedEntity and one integer
	Return Value/Type: One LinkedEntity
	Dependencies: Logic.Entity
	Exceptions: N/A
	Date Created: June 11th, 2017
	Date Modified: June 11th, 2017
	 */
	public LinkedEntity fetch (LinkedEntity e, int depth) {
		if (e == null)
			return null;
		else if (depth == 0) {
			return e;
		}
		return fetch(e.next, depth--);
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
		
		joe.height = 70;
		
		int key = e.getKeyCode(); // Tracks the key pressed
		pressed.add(key);
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {// Player jumps when spacebar/the 'w' key is pressed
			joe.jump();
			joe.setAnimState(Player.VERT);
		}

		if (key == KeyEvent.VK_D) {// Player moves right when the 'd' key is pressed
			joe.moveSide(true);
			joe.setDirection(true);
			if (joe.getYSpeed() == 0)
				joe.setAnimState(Player.MOVE);
		}

		else if (key == KeyEvent.VK_A) {// Player moves left when the 'a' key is pressed
			joe.moveSide(false);
			joe.setDirection(false);
			if (joe.getYSpeed() == 0)
				joe.setAnimState(Player.MOVE);
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
			if (joe.getYSpeed() == 0) {
				joe.setAnimState(Player.IDLE);
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
}// end class
