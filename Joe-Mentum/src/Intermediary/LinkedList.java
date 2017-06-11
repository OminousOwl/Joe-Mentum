package Intermediary;

import java.awt.Color;

public class LinkedList {
	private LinkedEntity root;//the first node in the list
	//private LinkedEntity tail;//the last node in the list
	
	public LinkedList(int x, int y, int width, int height,Color colour, char collision){
		root = new LinkedEntity(x,y,width,height,colour,collision);
	}//end constructor
	
	public LinkedList(){
		root = null;
	}//end constructor
	
	public LinkedEntity getHead(){return this.root;}
	
	//public LinkedEntity getTail(){return this.tail;}
	
	
	/*
	Name: add
	Description: Adds a node to the list
	Parameters: One LinkedEntity
	Return Value/Type: One LinkedEntity
	Dependencies: Logic.Entity
	Exceptions: N/A
	Date Created: June 7th, 2017
	Date Modified: June 7th, 2017
	 */
	public LinkedEntity add(LinkedEntity node){//adds a node to the list
		
		if (root == null) {
			root = new LinkedEntity(node.x, node.y, node.width, node.height, node.colour, node.getCollideType());
			return node;
		}
		
		LinkedEntity runner;
		runner = root;
		
		while (true) {
			if (runner.next == null) {
				runner.setNext(node);
				return node;
				
			}
			else {
				runner = runner.next;
			}
		}
		
	}
	

}//end class
