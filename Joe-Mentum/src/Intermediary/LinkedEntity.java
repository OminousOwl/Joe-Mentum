package Intermediary;

import java.awt.Color;

import Logic.Entity;

public class LinkedEntity extends Entity {
	public LinkedEntity next;//the next object in the list
	
	public LinkedEntity(int x,int y,int width,int height,Color colour,char collision){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.colour = colour;
		this.setCollide(collision);
	}//end constructor
}
