package Logic;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Intermediary.Player;

public class Entity extends Rectangle {
	
	//Collision type constants
	public static final char SOLID = 's';
	public static final char BOUNCE = 'b';
	public static final char SKIP = 'k';
	public static final char FLOOR = 'f';
	public static final char WALL = 'w';
	
	/**** Variables ****/
	public Rectangle floorbox;//the rectangle object defining the surface of an object
	public Rectangle[] ledges = new Rectangle[2]; //The ledges of an object
	public boolean ledgeFlag = true; //A flag used to prevent autosnap to a ledge immediately after jumping form one
	public int resetCounter; //Used to countdown frames until the ledgeFlag resets
	private char collideType = SOLID;//used in collision() to determine the appropriate handling
	private double xSpeed = 0;//the horizontal speed of the object
	private double ySpeed = 0;//the vertical speed of the object

	private String filepath;
	private BufferedImage tile;
	
	private int yScroll = 0;
	protected int defaultY = 0;
	
	protected int animState = 0;
	
	
	public Color colour = Color.RED;
	
	public Entity() {
		
	}
	
	//Constructor June 4
	public Entity(int x, int y, int width, int height, Color colour, char collision, String tileFilepath) {
		collideType = collision;
		this.x = x;
		this.y = y;
		this.defaultY = y;
		this.width = width;
		this.height = height;
		this.colour = colour;
		
		
		try {
			filepath = tileFilepath;
			tile = ImageIO.read(new File(tileFilepath));
		} catch(IOException e) {
			
		}
		
		floorbox = new Rectangle(this.x, this.y + 2, this.width, this.height/6); //Defining the floorbox on the very surface of the object causes some jumps to be ignored
		ledges[0] = new Rectangle(this.x - 35, this.y + 3, 35, this.height/3);
		ledges[1] = new Rectangle(this.x + this.width, this.y + 3, 35, this.height/3);

	}


	/**** Getters & Setters ****/
	public void setCollide(char collideType){this.collideType = collideType;}
	
	//public Image getAnimation(){//TODO
		//if(this.frame<this.sprite.length)this.frame++;
		//else this.frame = 0;
		//return this.sprite[this.frame];
	//}//end getAnimation
	
	/**** Methods ****/

	public double getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
	
	public void setYScroll (int yScroll, double ySpeed) {
		this.yScroll = yScroll;
		this.ySpeed = ySpeed;
	}

	public int getYScroll() {
		return yScroll;
	}

	public int getDefaultY() {
		return defaultY;
	}

	public BufferedImage getTile() {
		return tile;
	}

	public void setTile(BufferedImage tile) {
		this.tile = tile;
	}
	

	public String getFilepath() {
		return filepath;
	}
	
	public char getCollideType() {
		return collideType;
	}

	public double getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
	
}//end Class
