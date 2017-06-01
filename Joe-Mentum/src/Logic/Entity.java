package Logic;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Image.*;
/*
 *Project: ICS4U - Final Project - Joe-Mentum
 *Author: Olivier J Hébert
 *Date Created: 25/05/2017
 *Date Edited: 26/05/2017
 *Description: This class will be the parent for all physics-based objects in the game; characters & level objects.
 */

public class Entity extends Rectangle {
	/**** Variables ****/
	private Rectangle hitbox;//the rectangle object indicating the physical edges of the object
	private Image[] sprite;//the images, frame by frame, for the objet's animation
	private String collideType = "Solid";//used in collision() to determine the appropriate handling
	private int frame = 0;//the preceding image in the animation
	private double xSpeed = 0;//the horizontal speed of the object
	private double ySpeed = 0;//the vertical speed of the object
	
	/**** Getters & Setters ****/
	public void setCollide(String collideType){this.setCollideType(collideType);}
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
	public void collide(String type){//TODO
		if(type == "Solid"){
			this.ySpeed = 0;
		}//end if
		if(type == "Bounce"){
			this.ySpeed = (ySpeed*-1);
		}//end if
		if(type == "Skip"){
			this.ySpeed = (ySpeed*-1)/2;
		}//end if
	}//end collide
	public String getCollideType() {
		return collideType;
	}
	public void setCollideType(String collideType) {
		this.collideType = collideType;
	}
}//end Class
