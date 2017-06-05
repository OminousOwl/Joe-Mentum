package Logic;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Image.*;
/*
 *Project: ICS4U - Final Project - Joe-Mentum
 *Author: Olivier J Hébert
 *Date Created: 25/05/2017
 *Date Edited: 4/06/2017
 *Description: This class will be the parent for all physics-based objects in the game; characters & level objects.
 */

public class Entity extends Rectangle {
	
	//Collision type constants
	public static final char SOLID = 's';
	public static final char BOUNCE = 'b';
	public static final char SKIP = 'k';
	public static final char FLOOR = 'f';
	public static final char WALL = 'w';
	
	/**** Variables ****/
	private Rectangle hitbox;//the rectangle object indicating the physical edges of the object
	private Rectangle floorbox;//the rectangle object defining the surface of an object
	public Rectangle[] ledges = new Rectangle[2]; //The ledges of an object
	private Image[] sprite;//the images, frame by frame, for the objet's animation
	private char collideType = SOLID;//used in collision() to determine the appropriate handling
	private int frame = 0;//the preceding image in the animation
	private double xSpeed = 0;//the horizontal speed of the object
	private double ySpeed = 0;//the vertical speed of the object
	
	
	public Color colour = Color.RED;
	
	public Entity() {
		
	}
	
	//Constructor June 4
	public Entity(int x, int y, int width, int height, char collision) {
		collideType = collision;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		if (collideType == FLOOR || collideType == WALL)
			floorbox = new Rectangle(this.x, this.y + 2, this.width, this.height - 2); //Defining the floorbox on the very surface of the object causes some jumps to be ignored
		if (collideType == WALL) {
			ledges[0] = new Rectangle(this.x - 35, this.y + 3, 35, this.height/4);
			ledges[1] = new Rectangle(this.x + this.width, this.y + 1, 35, this.height/4);
		}
	}
	
	/**** Getters & Setters ****/
	public void setCollide(char collideType){this.setCollideType(collideType);}
	public double getXSpeed(){return xSpeed;}
	public void setXSpeed(double xSpeed){this.xSpeed = xSpeed;}
	public double getYSpeed(){return this.ySpeed;}
	public void setYSpeed(double ySpeed){this.ySpeed = ySpeed;}
	
	//public Image getAnimation(){//TODO
		//if(this.frame<this.sprite.length)this.frame++;
		//else this.frame = 0;
		//return this.sprite[this.frame];
	//}//end getAnimation
	
	/**** Methods ****/
	
	/*
	Name: collide
	Description: Handles collision results between any two entities
	Parameters:
	Return Value/Type: N/A
	Dependencies: None
	Exceptions: N/A
	Date Created: May 29th, 2017
	Date Modified: June 4th, 2017
	 */
	public void collide(Entity b){
		if(b.getCollideType() == SOLID){
			this.ySpeed = 0;
		}
		else if(b.getCollideType() == FLOOR){
			floorCollide(b);
			
		}
		
		else if(b.getCollideType() == WALL){
				
			if (this.intersects(b.ledges[0]) || this.intersects(b.ledges[1])) {
				this.xSpeed = 0;
				this.ySpeed = 0;
				this.y = b.ledges[0].y;
				
				//TODO prevent Joe from moving horizontally while grabbing a ledge
				//TODO let Joe jump on a ledge
				
				/*if (this.intersects(b.ledges[0])) {
					this.x = b.ledges[0].x + 5;
				}
				else
					this.x = b.ledges[1].x;
					*/
			}
				

			if (this.y > b.y + 5) { //If collision takes place outside the wall's surface
				this.xSpeed = 0;
				if (this.x < b.x + b.width/2) { //Collision from left, judging from mid point
					this.x = b.x - this.width;
				}
				else //Collision from right
					this.x = b.x + b.width;
				}
				
				else {
					floorCollide(b);
				}
		}
		
		else if(b.getCollideType() == BOUNCE){
			this.ySpeed = (ySpeed*-1);
		}
		else if(b.getCollideType() == SKIP){
			this.ySpeed = (ySpeed*-1)/2;
		}
	}
	public char getCollideType() {
		return collideType;
	}
	public void setCollideType(char collideType) {
		this.collideType = collideType;
	}
	
	
	/*
	Name: floorCollide
	Description: Handles collision on a surface to nullify gravity
	Parameters:
	Return Value/Type: N/A
	Dependencies: None
	Exceptions: N/A
	Date Created: June 4th, 2017
	Date Modified: June 4th, 2017
	 */
	public void floorCollide(Entity b) {
		if (this.ySpeed > 0) { //Only collides on drop, otherwise jumping is impossible
			this.ySpeed = 0;
			
			if (this.intersects(b.floorbox))
				this.y = b.y - this.height;
		}
	}
}//end Class
