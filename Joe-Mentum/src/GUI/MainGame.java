/**
 *@Name Cosmin Baciu, Quinn Fisher, Olivier Hébert
 *@DateCreated: May 30th, 2017
 *@DateModified: June 19th, 2017
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
import javafx.animation.FadeTransition;
import javafx.util.Duration;

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
	private final int VICTORY = 4;
	
	private final Set<Integer> pressed = new HashSet<Integer>(); //Stores all currently pressed keys. This allows momentum to be maintained when releasing A or D when holding the other

	/**** Variables ****/
	private int state = RUNNING;// the flag that triggers different behaviors in the program
	private boolean muted = false;
	private boolean devMode = false;
	public static Player joe = new Player(); // The man, the myth, the legend himself, Joe
	private LinkedList theLevel;
	private MonsterSet enemies = new MonsterSet();
	private Monster stabCrab;
	private DialogQueue narrative;
	private ItemSet levelItems;
	private boolean itemsChanged = false;
	GraphicsConsole gc = new GraphicsConsole();
	MP3Player audio = new MP3Player();
	
	private int[] spawners;
	private boolean[] spawnerFlags;
	
	private int victoryDelay = 300;
	private boolean victoryFlag = false;
	
	private static final String floor = "images/floor.png";
	private static final String sTile = "images/sTile.png";
	private static final String lTile = "images/lTile.png";
	private static final String fTile = "images/floorTile.png";
	
	BufferedImage bg;
	BufferedImage hpHeart;
	BufferedImage exp;
	BufferedImage lvlStar;
	BufferedImage itemFrame;
	//PauseMenu pausemenu;

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
		
		//pausemenu = new PauseMenu();
		
		game.setDoubleBuffered(true);
		add(game);
		setVisible(false);
		setState(PAUSED);
		
		gc.setFont(new Font("Baskerville Old Face", Font.BOLD, 24));
		gc.addKeyListener(this);
		gc.setVisible(false);
		
		
		audio.addToPlayList(new File("music/StabCrabV2Orchestra.mp3"));
		audio.skipForward();
		audio.play();
		
		try {
			bg = ImageIO.read(new File("images/bg.png"));
			hpHeart = ImageIO.read(new File("gui/heart.png"));
			exp = ImageIO.read(new File("gui/exp.png"));
			lvlStar = ImageIO.read(new File("gui/Level.png"));
			itemFrame = ImageIO.read(new File("gui/item.png"));

		} catch (IOException e) {
			// catch
		}
		
		setup();
		animate();
		
		//TODO reimplement when GameMenu is remade
		new GameMenu(this);
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
		theLevel.add(new LinkedEntity(1900, 350, 377, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(2400, 332, 740, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(2800, 100, 377, 80, Color.BLACK, 's', floor)).setYScroll(50, 1.0);
		theLevel.add(new LinkedEntity(3300, 100, 95, 80, Color.BLACK, 's', sTile));
		theLevel.add(new LinkedEntity(3200, 332, 189, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(3465, 200, 189, 80, Color.BLACK, 's', floor)).setYScroll(100, 1.0);;
		theLevel.add(new LinkedEntity(3700, 80, 189, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(4000, 80, 377, 80, Color.BLACK, 's', floor)).setYScroll(100, 1.0);
		theLevel.add(new LinkedEntity(4500, 350, 189, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(4200, 332, 755, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(4750, 100, 189, 80, Color.BLACK, 's', floor)).setYScroll(50,1.0);
		theLevel.add(new LinkedEntity(4900, 332, 189, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(5000, 100, 377, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(5600, 332, 189, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(6000, 332, 755, 80, Color.BLACK, 's', floor));
		theLevel.add(new LinkedEntity(6755, 332, 755, 80, Color.BLACK, 's', floor));
		
		
		enemies = new MonsterSet();
		//X, Y, Width, Height, Health, Attack Damage, Speed, EXP Reward, AI State, Sprite Type
		enemies.add(new Monster(475, 100, 40, 60, 3, 2, 0.5, 50, Monster.WANDER, "skeleton")).setAssociatedTerrain(fetch(theLevel.getHead(), 0));
		enemies.add(new Monster(850, 100, 20, 30, 3, 2, 0.5, 50, Monster.LgWANDER, "skeleton"));
		
		//First wave of birds
		enemies.add(new Monster(900, 100, 65, 65, 3, 1, 0.5, 25, Monster.BIRD, "bird"));
		enemies.add(new Monster(925, 100, 65, 65, 3, 1, 0.5, 25, Monster.BIRD, "bird")).offsetBird(15);
		enemies.add(new Monster(950, 100, 65, 65, 3, 1, 0.5, 25, Monster.BIRD, "bird")).offsetBird(30);
		
		stabCrab = new Monster(6800, 200, 120, 90, 75, 8, 2.0, 100, Monster.DWAIT, "stabcrab");
		
		spawners = new int[3];
		spawners[0] = 1000;
		spawners[1] = 3500;
		spawners[2] = 5800;
		
		spawnerFlags = new boolean[3];
		
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
			paintEnemy(stabCrab);
			paintItems(levelItems.getHead());
			
			gc.setColor(Color.RED);
			gc.drawImage(joe.getCurrentFrame(), (int) joe.getX(), (int) joe.getY(), (int) joe.getWidth(), (int) joe.getHeight());
			//gc.fillRect((int) joe.getX(), (int) joe.getY(), (int) joe.getWidth(), (int) joe.getHeight());
			
			
			if (this.state == GAME_OVER) {
				gc.drawString("GAME OVER", gc.getWidth()/2 - 50, gc.getHeight()/2 - 25);
				gc.drawString("PRESS R TO RESTART", gc.getWidth()/2 - 110, gc.getHeight()/2 + 25);
				gc.drawString("PRESS Q TO QUIT", gc.getWidth()/2 - 90, gc.getHeight()/2 + 75);
			}
			else if (this.state == VICTORY) {
				gc.setColor(Color.YELLOW);
				gc.drawString("STAB CRAB IS DEAD! YOU WIN!", gc.getWidth()/2 - 195, gc.getHeight()/2 - 75);
				gc.drawString("JOE IS ALLOWED TO GO HOME NOW!", gc.getWidth()/2 - 235, gc.getHeight()/2 - 25);
				gc.drawString("PRESS R TO PLAY AGAIN", gc.getWidth()/2 - 155, gc.getHeight()/2 + 25);
				gc.drawString("PRESS Q TO QUIT", gc.getWidth()/2 - 110, gc.getHeight()/2 + 75);
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
			gc.drawImage(itemFrame, 180, 10, 50, 50);
			
			if (joe.getActive() != null) {
				gc.drawImage(joe.getActive().getIcon(), 183, 14, 40, 40);
				if (joe.getActive().cdRemaining > 0) {
					gc.setColor(Color.GREEN);
					gc.drawString(String.valueOf(joe.getActive().cdRemaining/100 + 1), 195, 40); //Display cooldown
				}
			}
				
			
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
			
			if (joe.getLevel() < 10) {
				gc.drawString(String.valueOf(joe.getLevel()), 28, 40);
			}
			else {
				gc.drawString(String.valueOf(joe.getLevel()), 23, 40);
			}
			
			gc.setColor(Color.WHITE);
			
			gc.drawString(joe.levelStrings[0], 10, 80);
			gc.drawString(joe.levelStrings[1], 10, 110);
			gc.drawString(joe.levelStrings[2], 10, 140);
			gc.drawString(joe.levelStrings[3], 10, 170);
			
			if (narrative.getHead() != null) {
				if (narrative.getHead().isQueued() && narrative.getHead().getDisplayTime() > 0) {
					gc.setColor(Color.WHITE);
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
		if (nextItem.getIcon() != null) {
			gc.drawImage(nextItem.getIcon(), nextItem.x, nextItem.y, 30, 30);
		}
		else {
			gc.setColor(Color.YELLOW);
			gc.fillRect(nextItem.x, nextItem.y, 30, 30);			
		}

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
		gravity(stabCrab);
		move(joe);
		move(enemies.getHead());
		move(stabCrab);
		platformScroll(theLevel.getHead());
		checkLevelCollision(joe, theLevel.getHead());
		checkLevelCollision(enemies.getHead(), theLevel.getHead());
		checkLevelCollision(stabCrab, theLevel.getHead());
		checkEnemyCollision(joe, enemies.getHead());
		checkEnemyCollision(joe, stabCrab);
		checkItemCollision(joe, levelItems.getHead());
		manageEnemyBehavior(enemies.getHead());
		manageEnemyBehavior(stabCrab);
		manageCD(theLevel.getHead());
		manageCD(enemies.getHead());
		manageCD(stabCrab);
		manageItemCD();
		resetLevelString();
		scroll(joe, theLevel.getHead());
		scroll(joe, enemies.getHead());
		scroll(joe, stabCrab);
		scroll(joe, narrative.getHead());
		scroll(joe, levelItems.getHead());
		scroll(joe, spawners);
		spawnCheck(spawners);
		joe.x = gc.getWidth()/2;
		deathCheck(joe);
		deathCheck(stabCrab);
		victoryTrigger();
		dialogTriggerCheck(joe);
		playerAnimReset(joe);
		
		itemsChanged = false;
		
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
	
	/*
	 Name: checkEnemyCollision 
	 Description: Checks to see if any enemies collide with Joe, and handles damage
	 Parameters: One player, and one enemy 
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 8th, 2017 
	 Date Modified: June 19th, 2017 
	 */
	public void checkEnemyCollision(Player joe, Monster enemy) {
		if (enemy == null) {
			return;
		}
		else if (enemy.getHealth() > 0) {
			if (joe.intersects(enemy) && enemy.damageCD == 0) {
				if (enemy.getAnimState() == Monster.FIGHT) {
					damageJoe(joe, enemy);
				}
				else if (joe.getYSpeed() >= 0.4) {
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
					damageJoe(joe, enemy);
				}
				enemy.damageCD = 70;
			}
		}
		checkEnemyCollision(joe, enemy.next);
	}
	
	/*
	 Name: damageJoe 
	 Description: Deals an enemy's damage to Joe and prevents negative life
	 Parameters: One player, and one enemy 
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 8th, 2017 
	 Date Modified: June 19th, 2017 
	 */
	public void damageJoe(Player joe, Monster enemy) {
		joe.damage(enemy.getAttack());
		if (joe.getHealth() < 0) {
			joe.setHealth(0);
		}
		joe.setDamageFrames(5);
	}
	
	/*
	 Name: checkItemCollision 
	 Description: Checks to see if any items collide with Joe, and handles pickup
	 Parameters: One player, and one Item 
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 17th, 2017 
	 Date Modified: June 19th, 2017 
	 */
	public void checkItemCollision(Player joe, Item i) {
		if (i == null)
			return;
		if (joe.intersects(i) && joe.isGrabbing()) {
			if (i.isActive()) {
				joe.setActive(i);
			}
			else {
				joe.getPassives().add(i);
				if (i.getType() == Item.SWORD) {
					joe.setAttack(joe.getAttack() + 1);
				}
				else if (i.getType() == Item.ARMOUR) {
					joe.setMaxHealth(joe.getMaxHealth() + 2);
					joe.damage(-2);
				}
				else if (i.getType() == Item.BOOTS) {
					joe.setSpeed(joe.getSpeed() + 0.1);
				}
			}
			levelItems.remove(levelItems.getHead(), i.getItemGenID());
			joe.setGrabbing(false);
			itemsChanged = true;
			return;
		}
		checkItemCollision(joe, i.next);
	}
	
	public void spawnCheck(int[] spawners) {
		if (spawners[0] < 0 && !spawnerFlags[0]) {
			System.out.println("Spawner 0");
			enemies = new MonsterSet();
			enemies.add(new Monster(650, 90, 40, 60, 10, 5, 0.8, 50, Monster.AgWANDER, "skeleton"));
			enemies.add(new Monster(1000, 250, 40, 60, 10, 5, 0.8, 75, Monster.RtWANDER, "skeleton"));
			enemies.add(new Monster(2050, 0, 40, 60, 15, 3, 0.8, 25, Monster.AgWANDER, "skeleton"));
			enemies.add(new Monster(2150, 250, 40, 60, 20, 5, 0.8, 25, Monster.LgWANDER, "skeleton"));
			enemies.add(new Monster(2250, 250, 40, 60, 20, 5, 0.8, 25, Monster.LgWANDER, "skeleton"));
			spawnerFlags[0] = true;
		}
		else if (spawners[1] < 0 && !spawnerFlags[1]) {
			enemies = new MonsterSet();
			enemies.add(new Monster(800, 0, 40, 60, 20, 5, 0.8, 25, Monster.LgWANDER, "skeleton"));
			enemies.add(new Monster(1000, 200, 40, 60, 22, 6, 0.8, 25, Monster.AgWANDER, "skeleton"));
			enemies.add(new Monster(1100, 200, 40, 60, 22, 6, 0.8, 25, Monster.WANDER, "skeleton"));
			enemies.add(new Monster(1200, 200, 40, 60, 22, 6, 0.8, 25, Monster.LgWANDER, "skeleton"));
			enemies.add(new Monster(1300, 200, 40, 60, 22, 6, 0.8, 25, Monster.WANDER, "skeleton"));
			spawnerFlags[1] = true;
		}
		else if (spawners[2] < 0 && !spawnerFlags[2]) {
			enemies = new MonsterSet();
			enemies.add(new Monster(900, 100, 65, 65, 3, 1, 0.5, 25, Monster.BIRD, "bird"));
			enemies.add(new Monster(925, 100, 65, 65, 3, 1, 0.5, 25, Monster.BIRD, "bird")).offsetBird(15);
			enemies.add(new Monster(950, 100, 65, 65, 3, 1, 0.5, 25, Monster.BIRD, "bird")).offsetBird(30);
			enemies.add(new Monster(975, 100, 65, 65, 3, 1, 0.5, 25, Monster.BIRD, "bird")).offsetBird(45);
			enemies.add(new Monster(1000, 100, 65, 65, 3, 1, 0.5, 25, Monster.BIRD, "bird")).offsetBird(60);
			spawnerFlags[2] = true;
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
		if (a.damageCD > 0) {
			a.damageCD--;
		}
		if (a.attackCD > 0) {
			a.attackCD--;
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
	
	public void resetLevelString() {
		if (joe.levelStringCD > 0) {
			joe.levelStringCD--;
		}
		else if (joe.levelStringCD <= 0) {
			joe.levelStrings[0] = "";
			joe.levelStrings[1] = "";
			joe.levelStrings[2] = "";
			joe.levelStrings[3] = "";
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
	 Date Modified: June 19th, 2017
	 */
	public void scroll(Player joe, Item a) {
		if (a == null) {
			return;
		}
		
		//System.out.println(a.getText());
		a.x -= joe.x - gc.getWidth()/2;
		if (!itemsChanged);
			scroll(joe, a.next);
	}
	
	/*
	 Name: scroll 
	 Description: Scrolls all enemy spawners in relation to Joe's current location, used to avoid overloading the system with enemies
	 Parameters: One Player and One DialogBox
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 20th, 2017
	 Date Modified: June 20th, 2017
	 */
	public void scroll(Player joe, int[] spawners) {
		for (int i = 0; i < spawners.length; i++) {
			spawners[i] -= joe.x - gc.getWidth()/2;
		}
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
			a.y += a.getYSpeed();
			a.floorbox.y += a.getYSpeed();
			a.ledges[0].y += a.getYSpeed();
			a.ledges[1].y += a.getYSpeed();
			if (a.y == a.getDefaultY() + a.getYScroll() || a.y == a.getDefaultY()) {
				a.setYSpeed(a.getYSpeed() * -1);
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
	 Name: deathCheck 
	 Description: Checks to see if a victory state is needed in response to Stab Crab's death
	 Parameters: One Monster
	 Return Value/Type: N/A 
	 Dependencies: Logic.Entity 
	 Exceptions: N/A 
	 Date Created: June 19th, 2017
	 Date Modified: June 19th, 2017
	 */
	public void deathCheck(Monster crab) {
		if (crab.y >= gc.getHeight() + gc.getHeight()/6 || crab.getHealth() <= 0) {
			this.victoryFlag = true;
		}
	}
	
	
	public void victoryTrigger() {
		if (victoryFlag)
			victoryDelay--;
		if (victoryDelay <= 0)
			this.setState(VICTORY);
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
					//System.out.println("Dialog End");
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
			if (joe.getXSpeed() == 0 && (joe.getYSpeed() == 0 || joe.getYSpeed() == 0.2)) {
				joe.setAnimState(Player.IDLE);
			}
			else if (joe.getYSpeed() == 0 || joe.getYSpeed() == 0.2) {
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
				pressed.remove(KeyEvent.VK_A);
				joe.height = 70;
				joe.moveSide(true);
				joe.setDirection(true);
				if (joe.getYSpeed() == 0)
					joe.setAnimState(Player.MOVE);
			}
			
		}

		else if (key == KeyEvent.VK_A) {// Player moves left when the 'a' key is pressed
			if (!(!joe.getDirection() && joe.getAnimState() == Player.LEDGE)) {
				pressed.remove(KeyEvent.VK_D);
				joe.height = 70;
				joe.moveSide(false);
				joe.setDirection(false);
				if (joe.getYSpeed() == 0)
					joe.setAnimState(Player.MOVE);
			}
		}

		if (key == KeyEvent.VK_Q) {//Quit or use item depending on context
			if (this.state == GAME_OVER || this.state == VICTORY) {
				System.exit(0);
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
				//TODO get shit done here --cosmin
				/*if (!pausemenu.isVisible()){
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), pausemenu);
                    ft.setFromValue(0);
                    ft.setToValue(1);

                    pausemenu.setVisible(true);
                    ft.play();
				} else {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), pausemenu);
                    ft.setFromValue(1);
                    ft.setToValue(0);
                    ft.setOnFinished(evt -> pausemenu.setVisible(false));
                    ft.play();
				}*/
			} 
			else {
				this.setState(0);
			}
		}
		
		if (key == KeyEvent.VK_M) { //Mute
			muted = !muted;
			if (muted) {
				audio.pause();
			}
			else {
				audio.play();
			}
				
		}
		
		if (key == KeyEvent.VK_R && (this.state == GAME_OVER || this.state == VICTORY)) {
			setup();
		}
		
		//Dev Commands
		if (pressed.contains(KeyEvent.VK_CONTROL) && pressed.contains(KeyEvent.VK_SHIFT) && pressed.contains(KeyEvent.VK_C) && !devMode) {
			devMode = true;
			pressed.remove(KeyEvent.VK_C);
		}
		if (pressed.contains(KeyEvent.VK_CONTROL) && pressed.contains(KeyEvent.VK_SHIFT) && pressed.contains(KeyEvent.VK_C)  && devMode) {
			joe.x = 6800;
			pressed.remove(KeyEvent.VK_C);
		}
		if (pressed.contains(KeyEvent.VK_CONTROL) && pressed.contains(KeyEvent.VK_SHIFT) && pressed.contains(KeyEvent.VK_X)  && devMode) {
			joe.setActive(new Item(Item.HEALTH_POTION));
			joe.getActive().defineSprite();
			joe.getActive().defineActive();
			pressed.remove(KeyEvent.VK_X);
		}
		if (pressed.contains(KeyEvent.VK_CONTROL) && pressed.contains(KeyEvent.VK_SHIFT) && pressed.contains(KeyEvent.VK_Z)  && devMode) {
			joe.setActive(new Item(Item.WINGS));
			joe.getActive().defineSprite();
			joe.getActive().defineActive();
			pressed.remove(KeyEvent.VK_Z);
		}
		if (pressed.contains(KeyEvent.VK_CONTROL) && pressed.contains(KeyEvent.VK_SHIFT) && pressed.contains(KeyEvent.VK_V)  && devMode) {
			joe.setEXP(joe.getEXP() + 100);
			joe.levelUp();
			pressed.remove(KeyEvent.VK_V);
		}
		if (pressed.contains(KeyEvent.VK_CONTROL) && pressed.contains(KeyEvent.VK_SHIFT) && pressed.contains(KeyEvent.VK_BACK_SLASH)  && devMode) {
			joe.setHealth(9999);
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
