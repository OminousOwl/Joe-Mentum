package Intermediary;

import java.awt.Color;

import Logic.Entity;

public class LinkedEntity extends Entity {
	public LinkedEntity next;//the next object in the list
	
	public LinkedEntity(int x,int y,int width,int height,Color colour,char collision){
		super (x, y, width, height, colour, collision);
	}//end constructor
	
	/*
	Name: setNext
	Description: Assigns the Entity's link to another object
	Parameters: One LinkedEntity
	Return Value/Type: N/A
	Dependencies: Logic.Entity
	Exceptions: N/A
	Date Created: June 7th, 2017
	Date Modified: June 7th, 2017
	 */
	public void setNext(LinkedEntity node) {
		this.next = node;
	}
}
