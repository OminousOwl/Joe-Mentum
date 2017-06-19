/**
 *@Name Cosmin Baciu, Quinn Fisher, Olivier Hébert
 *@DateCreated: May 30th, 2017
 *@DateModified: June 16th, 2017
 *@Description: The class used to handle the actual game physics and gameplay
 */

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

import Intermediary.ItemSet;
import Intermediary.LinkedEntity;
import Intermediary.LinkedList;
import Intermediary.Monster;
import Intermediary.MonsterSet;
import Intermediary.Player;
import Logic.Entity;
import Logic.Item;
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

	private static final long serialVersionUID = 2624305141673616554L;
	/**** Constants ****/
	private final int RUNNING = 0;// the ID# for the game's running state.
	private final int PAUSED = 1;// the ID# for the game's paused state with the basic menu.
	private final int PAUSED_OPTIONS = 2;// the ID# for the game's paused state with the options menu.
	private final int GAME_OVER = 3;
	
	private final Set<Integer> pressed = new HashSet<Integer>(); //Stores all currently pressed keys. This allows momentum to be maintained when releasing A or D when holding the other

	/**** Variables ****/
	private int state = RUNNING;// the flag that triggers different behaviors in the program
	private boolean muted = false;
	public static Player joe = new Player(); // The man, the myth, the legend himself, Joe
	private LinkedList theLevel;
	private MonsterSet enemies = new MonsterSet();
	private DialogQueue narrative;
	private ItemSet levelItems;
	GraphicsConsole gc = new GraphicsConsole();
	MP3Player spagoogi = new MP3Player();
	
	private static final String floor = "images/floor.png";
	private static final String sTile = "images/sTile.png";
	private static final String lTile = "images/lTile.png";
	private static final String fTile = "images/floorTile.png";
	
	BufferedImage bg;
	BufferedImage hpHeart;
	BufferedImage exp;
	BufferedImage lvlStar;

	public static void main(String[] args) {
		new MainGame();
	}

	public MainGame() {
		super("Joe-Mentum");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(768, 432);
		gc.setSize(650, 432);
		
		// Sets up the game panel
		JPanel game = new JPanel();

		
		game.setDoubleBuffered(true);
		add(game);
		setVisible(false);
		setState(PAUSED);
		
		gc.setFont(new Font("Baskerville Old Face", Font.BOLD, 24));
		gc.addKeyListener(this);
		gc.setVisible(false);
		
		
		spagoogi.addToPlayList(new File("music/StabCrabV2Orchestra.mp3"));
		spagoogi.skipForward();
		spagoogi.play();
		
		try {
			bg = ImageIO.read(new File("images/bg.png"));
			hpHeart = ImageIO.read(new File("gui/heart.png"));
			exp = ImageIO.read(new File("gui/exp.png"));
			lvlStar = ImageIO.read(new File("gui/Level.png"));

		} catch (IOException e) {
			// catch
		}
		
		setup();
		animate();
		
		//TODO reimplement when GameMenu is remade
		//new GameMenu(this);
		gc.setVisible(true);
		this.state = 0;
		startGame();
	}
	
	public void setup() {
		
		joe = new Player();
		joe.x = gc.getWidth()/2;
		joe.y = 100;
		joe.width = 42;
		joe.height = 70;
		theLevel = new LinkedList();
		
		theLevel.add(new LinkedEntity(0, 332, 755, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(468, 195, 150, 150, Color.BLACK, 's', fTile));
		theLevel.add(new LinkedEntity(800, 175, 160, 100, Color.BLACK, 's', lTile)).setYScroll(60, 1.0);
		theLevel.add(new LinkedEntity(1015, 332, 100, 100, Color.BLACK, 's', sTile));
		theLevel.add(new LinkedEntity(1180, 332, 100, 100, Color.BLACK, 's', sTile));
		theLevel.add(new LinkedEntity(1325, 150, 100, 100, Color.BLACK, 's', lTile)).setYScroll(400, 1.0);
		theLevel.add(new LinkedEntity(1450, 100, 755, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(2000, 300, 377, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(2400, 332, 755, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(2800, 100, 377, 80, Color.BLACK, 's', floor)).setYScroll(50, 1.0);
		theLevel.add(new LinkedEntity(3300, 100, 95, 80, Color.BLACK, 's', sTile));
		theLevel.add(new LinkedEntity(3200, 332, 189, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(3465, 200, 189, 80, Color.BLACK, 's', floor)).setYScroll(100, 1.0);;
		theLevel.add(new LinkedEntity(3700, 80, 189, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(4000, 80, 377, 80, Color.BLACK, 's', floor)).setYScroll(100, 1.0);
		theLevel.add(new LinkedEntity(4500, 350, 189, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(4200, 332, 755, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(4800, 100, 189, 80, Color.BLACK, 's', floor)).setYScroll(50,1.0);
		theLevel.add(new LinkedEntity(4900, 332, 189, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(5000, 100, 377, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(5600, 332, 189, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(6000, 332, 755, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(6755, 332, 755, 80, Color.BLACK, 's', floor));
		
		
		enemies = new MonsterSet();
		//X, Y, Width, Height, Health, Attack Damage, Speed, EXP Reward, AI State, Sprite Type
		enemies.add(new Monster(475, 100, 40, 60, 3, 2, 0.5, 50, Monster.WANDER, "skeleton")).setAssociatedTerrain(fetch(theLevel.getHead(), 0));
		enemies.add(new Monster(850, 100, 20, 30, 3, 2, 0.5, 50, Monster.LgWANDER, "skeleton"));
		enemies.add(new Monster(1500, 90, 40, 60, 12, 5, 0.8, 100, Monster.AgWANDER, "skeleton")); //OP one
		
		enemies.add(new Monster(900, 100, 65, 65, 3, 1, 0.5, 25, Monster.BIRD, "bird"));
		enemies.add(new Monster(925, 100, 65, 65, 3, 1, 0.5, 25, Monster.BIRD, "bird")).offsetBird(15);
		enemies.add(new Monster(950, 100, 65, 65, 3, 1, 0.5, 25, Monster.BIRD, "bird")).offsetBird(30);
		
		narrative = new DialogQueue();
		narrative.enqueue(new DialogBox(450, "Where am I? How did I get here?", "Joe", 300));
		narrative.enqueue(new DialogBox(450, "Walk right if you ever want to see", "Disembodied Voice", 300));
		narrative.enqueue(new DialogBox(450, "your home again.", "Disembodied Voice", 300));
		narrative.enqueue(new DialogBox(450, "lol ok", "Joe", 300));
		
		levelItems = new ItemSet();
		
		this.setState(RUNNING);
	}
	
	public void startGame() {
		while (true) {
			if (this.state == 0)
				run();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				System.out.println("A thing broke");
			}
		}
	}

	public void animate() {
		
		synchronized(gc) {
			gc.clear();
			
			gc.drawImage(bg, 0, 0, gc.getWidth(), gc.getHeight());
			
			paintLevelComponent(theLevel.getHead());
			paintEnemy(enemies.getHead());
			paintItems(levelItems.getHead());
			
			gc.setColor(Color.RED);
			gc.drawImage(joe.getCurrentFrame(), (int) joe.getX(), (int) joe.getY(), (int) joe.getWidth(), (int) joe.getHeight());
			//gc.fillRect((int) joe.getX(), (int) joe.getY(), (int) joe.getWidth(), (int) joe.getHeight());
			
			
			if (this.state == GAME_OVER) {
				gc.drawString("GAME OVER", gc.getWidth()/2 - 50, gc.getHeight()/2 - 25);
				gc.drawString("PRESS R TO RESTART", gc.getWidth()/2 - 110, gc.getHeight()/2 + 25);
			}
			
			/*//Outdated Debug Code
			gc.setColor(Color.GREEN);
			gc.fillRect((int) wall.ledges[0].getX(), (int) wall.ledges[0].getY(), (int) wall.ledges[0].getWidth(), (int) wall.ledges[0].getHeight());
			gc.fillRect((int) wall.ledges[1].getX(), (int) wall.ledges[1].getY(), (int) wall.ledges[1].getWidth(), (int) wall.ledges[1].getHeight());
			*/
			
			//GUI elements
			gc.drawImage(lvlStar, 10, 10, 50, 50);
			gc.drawImage(hpHeart, 70, 10, 50, 50);
			gc.drawImage(exp, 120, 10, 50, 50);
			
			gc.setColor(Color.BLACK);
			if (joe.getHealth() < 10) {
				gc.drawString(String.valueOf(joe.getHealth()), 89, 40);
			}
			else {
				gc.drawString(String.valueOf(joe.getHealth()), 82, 40);
			}
			
			if (joe.getEXP() < 10) {
				gc.drawString(String.valueOf(joe.getEXP()), 139, 40);
			}
			else {
				gc.drawString(String.valueOf(joe.getEXP()), 132, 40);
			}
			
			gc.drawString(String.valueOf(joe.getLevel()), 28, 40);
			
			if (narrative.getHead() != null) {
				if (narrative.getHead().isQueued() && narrative.getHead().getDisplayTime() > 0) {
					gc.drawImage(narrative.getHead().frame, 10, gc.getHeight() - gc.getHeight()/4 - 10, gc.getWidth() - 20, gc.getHeight()/6);
					gc.drawString(narrative.getHead().getSpeaker() + ":", 37, gc.getHeight() - gc.getHeight()/6 - 15);
					gc.drawString(narrative.getHead().getText(), 35, gc.getHeight() - gc.getHeight()/6 + 10);
				}
			}
			
			
		}
		
		
	}
	
	
	public void paintLevelComponent (LinkedEntity activeEntity) {
		if (activeEntity == null) {
			return;
		}
		else {
			if (activeEntity.x <= 1000 && activeEntity.x >= -1000) { //Only renders within a certain range to avoid overloading the graphics console
				gc.setColor(activeEntity.colour);
				if (activeEntity.getTile() != null) {
					gc.drawImage(activeEntity.getTile(), activeEntity.x, activeEntity.y-4, activeEntity.width, activeEntity.height-4);
				}
				else
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
			if (enemy.x <= 1000 && enemy.x >= -1000) { //Only renders within a certain range to avoid overloading the graphics console
				if (enemy.getEnemyType() != null) {
					gc.drawImage(enemy.getCurrentFrame(), enemy.x, enemy.y, enemy.width, enemy.height);
				}
				else {
					gc.setColor(Color.RED);
					gc.fillRect(enemy.x, enemy.y, enemy.width, enemy.height);	
				}
			}

			paintEnemy(enemy.next);
		}
	}
	
	
	public void paintItems(Item nextItem) {
		if (nextItem == null)
			return;
		gc.setColor(Color.YELLOW);
		gc.fillRect(nextItem.x, nextItem.y, 30, 30);
		paintItems(nextItem.next);
	}

	/*
	 Name: run 
	 Description: Performs each command that needs to be done in a single frame
	 Parameters: None
	 Return Value/Type: N/A 
	 Dependencies: Joe (Entity.Player object(
	 Exceptions: N/A 
	 Date Created: May 30th, 2017
	 Date Modified: June 19th, 2017
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
		checkItemCollision(joe, levelItems.getHead());
		manageEnemyBehavior(enemies.getHead());
		manageCD(theLevel.getHead());
		manageCD(enemies.getHead());
		manageItemCD();
		scroll(joe, theLevel.getHead());
		scroll(joe, enemies.getHead());
		scroll(joe, narrative.getHead());
		scroll(joe, levelItems.getHead());
		joe.x = gc.getWidth()/2;
		deathCheck(joe);
		dialogTriggerCheck(joe);
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
	 Date Modified: June 17th, 2017
	 */
	public void gravity(Monster e) {
		if (e == null)
			return;
		e.setYSpeed(e.getYSpeed() + LivingObject.GRAV);
		e.y += e.getYSpeed();
		gravity(e.getNext());
		
		if (e.y > 1500) { //Prevents monsters from falling outside integer range
			e.y = 1500;
			e.setYSpeed(0);
		}
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
	public void checkCollision(LivingObject a, Entity b) {
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
	public void checkLevelCollision (LivingObject a, LinkedEntity b) {
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
				if (a.getEnemyType() != "bird") {
					checkCollision(a, b);
					if (a.associatedTerrain == null && a.intersects(b)) {
						a.setAssociatedTerrain(b);
					}
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
					joe.setYSpeed(-6.0);
					
					//Handles enemy death
					if (enemy.getHealth() <= 0) {
						enemy.setFrame(0);
						enemy.setAnimState(Monster.DEAD);
						enemy.setXSpeed(0);
						
						//Handle EXP rewards & level up
						joe.setEXP(joe.getEXP() + enemy.getExpGain());
						if (joe.getEXP() >= 100) {
							joe.levelUp();
						}
						
						//Drop items
						if (enemy.getDrop() != null) {
							System.out.println(enemy.x + ", " + enemy.y);
							enemy.getDrop().x = enemy.x;
							enemy.getDrop().y = enemy.y;
							levelItems.add(enemy.getDrop(), enemy.getDrop().x, enemy.getDrop().y).setItemGenID(levelItems.itemGenIDCounter);
							levelItems.itemGenIDCounter++;
						}
					}
					else {
						enemy.setDamageFrames(5);
					}
				}
				//Handles enemy hit
				else {
					joe.damage(enemy.getAttack());
					if (joe.getHealth() < 0) {
						joe.setHealth(0);
					}
					joe.setDamageFrames(5);
				}
				enemy.damageCD = 70;
			}
		}
		checkEnemyCollision(joe, enemy.next);
	}
	
	public void checkItemCollision(Player joe, Item i) {
		if (i == null)
			return;
		if (joe.intersects(i) && joe.isGrabbing()) {
			System.out.println(i.getType());
			if (i.isActive()) {
				joe.setActive(i);
				if (i.getType() == Item.HEALTH_POTION) {
					System.out.println("Got health pot");
				}
				else if (i.getType() == Item.WINGS) {
					System.out.println("Got wings");
				}
					
			}
			else {
				joe.getPassives().add(i);
				if (i.getType() == Item.SWORD) {
					System.out.println("Got sword");
					joe.setAttack(joe.getAttack() + 1);
				}
				else if (i.getType() == Item.ARMOUR) {
					System.out.println("Got armour");
					joe.setMaxHealth(joe.getMaxHealth() + 2);
					joe.damage(-2);
				}
				else if (i.getType() == Item.BOOTS) {
					System.out.println("Got boots");
					joe.setSpeed(joe.getSpeed() + 0.1);
				}
			}
			levelItems.remove(levelItems.getHead(), i.getItemGenID());
			joe.setGrabbing(false);
		}
		checkItemCollision(joe, i.next);
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
		else if (a.resetCounter > 0) {
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
	 Name: manageItemCD 
	 Description: Manages item cooldowns, preventing Joe from spamming his actives
	 Parameters: None
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 19th, 2017
	 Date Modified: June 19th, 2017
	 */
	public void manageItemCD () {
		if (joe.getActive() == null) {
			return;
		}
		else if (joe.getActive().cdRemaining > 0) {
			joe.getActive().cdRemaining--;
		}
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
	 Name: scroll 
	 Description: Scrolls all invisible narrative points in relation to Joe's current location, used to determine triggers
	 Parameters: One Player and One DialogBox
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 17th, 2017
	 Date Modified: June 17th, 2017
	 */
	public void scroll(Player joe, DialogBox a) {
		if (a == null) {
			return;
		}
		
		//System.out.println(a.getText());
		a.x -= joe.x - gc.getWidth()/2;
		scroll(joe, a.next);
	}
	
	/*
	 Name: scroll 
	 Description: Scrolls all invisible narrative points in relation to Joe's current location, used to determine triggers
	 Parameters: One Player and One DialogBox
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 17th, 2017
	 Date Modified: June 17th, 2017
	 */
	public void scroll(Player joe, Item a) {
		if (a == null) {
			return;
		}
		
		//System.out.println(a.getText());
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
	 Name: dialogTriggerCheck 
	 Description: Checks to see if the next narrative event must be triggered
	 Parameters: One Player
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 17th, 2017
	 Date Modified: June 17th, 2017
	 */
	public void dialogTriggerCheck(Player joe) {
		if (narrative.getHead() != null) {
			if (joe.x > narrative.getHead().getX() && !narrative.getHead().isQueued()) {
				narrative.getHead().setQueued(true);
			}
			else if (narrative.getHead().isQueued() && narrative.getHead().getDisplayTime() > 0) {
				narrative.getHead().setDisplayTime(narrative.getHead().getDisplayTime() - 1);
				if (narrative.getHead().getNext() != null)
					dialogTriggerCheck(joe, narrative.getHead().getNext());
				if (narrative.getHead().getDisplayTime() == 0) {
					System.out.println("Dialog End");
					narrative.dequeue();
				}
			}
		}
	}
	
	/*
	 Name: dialogTriggerCheck 
	 Description: Checks to see if a non-queued narrative event must be triggered after the current event
	 Parameters: One Player and one DialogBox
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 17th, 2017
	 Date Modified: June 17th, 2017
	 */
	public void dialogTriggerCheck(Player joe, DialogBox notFirst) {
		if (notFirst == null) {
			return;
		}
		if (joe.x > notFirst.getX() && !notFirst.isQueued()) {
			notFirst.setQueued(true);
		}
		dialogTriggerCheck(joe, notFirst.next);
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
	
	/*
	Name: boxCount
	Description: Returns the number of dialog boxes in a dialogQueue. Used as an internal debugging tool
	Parameters: One DialogBox
	Return Value/Type: One Integer
	Dependencies: Logic.Entity
	Exceptions: N/A
	Date Created: June 17th, 2017
	Date Modified: June 17th, 2017
	 */
	public void setState(int newState) {
		state = newState;
	}// end setState
	
	public int boxCount(DialogBox a) {
		if (a == null) {
			return 0;
		}
		return 1 + boxCount(a.next);
	}

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
		
		if (key == KeyEvent.VK_UP) {
			key= KeyEvent.VK_W;
		}
		if (key == KeyEvent.VK_DOWN) {
			key= KeyEvent.VK_S;
		}
		if (key == KeyEvent.VK_LEFT) {
			key= KeyEvent.VK_A;
		}
		if (key == KeyEvent.VK_RIGHT) {
			key= KeyEvent.VK_D;
		}
		
		pressed.add(key);
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {// Player jumps when spacebar/the 'w' key is pressed
			joe.height = 70;
			joe.jump();
			joe.setAnimState(Player.VERT);
		}

		if (key == KeyEvent.VK_D) {// Player moves right when the 'd' key is pressed
			if (!(joe.getDirection() && joe.getAnimState() == Player.LEDGE)) {
				joe.height = 70;
				joe.moveSide(true);
				joe.setDirection(true);
				if (joe.getYSpeed() == 0)
					joe.setAnimState(Player.MOVE);
			}
			
		}

		else if (key == KeyEvent.VK_A) {// Player moves left when the 'a' key is pressed
			if (!(!joe.getDirection() && joe.getAnimState() == Player.LEDGE)) {
				joe.height = 70;
				joe.moveSide(false);
				joe.setDirection(false);
				if (joe.getYSpeed() == 0)
					joe.setAnimState(Player.MOVE);
			}
		}

		if (key == KeyEvent.VK_Q) {//
			if (this.state == GAME_OVER) {
				this.dispose();
			}
			else if (joe.getActive() != null) {
				joe.getActive().use(joe);
			}
			
		}
		
		if (key == KeyEvent.VK_S) {// Player fastfalls when the 's' key is pressed
			if (joe.getAnimState() != Player.LEDGE) {
				joe.height = 70;
				joe.fastFall();
			}
	
		}


		if (key == KeyEvent.VK_E) {// picks up an item
			joe.setGrabbing(true);
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
		
		if (key == KeyEvent.VK_R && this.state == GAME_OVER) {
			setup();
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
		else if (key == KeyEvent.VK_E) {
			joe.setGrabbing(false);
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
}// end class
