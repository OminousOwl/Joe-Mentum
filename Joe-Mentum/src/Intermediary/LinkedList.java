package Intermediary;

import java.awt.Color;
import Logic.Entity;

public class LinkedList extends Entity {
	public Entity next;//the next node in the list
	
	public LinkedList(int x, int y, int width, int height,Color colour, char collision){
		new Entity(x,y,width,height,collision);
		next = null;
	}//end constructor
}
