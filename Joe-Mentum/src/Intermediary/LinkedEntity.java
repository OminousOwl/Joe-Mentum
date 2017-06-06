package Intermediary;

import Logic.Entity;

public class LinkedEntity extends Entity {
	public LinkedEntity next;//the next object in the list
	
	public LinkedEntity(int x,int y,int width,int height,char collision){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setCollide(collision);
	}//end constructor
}
