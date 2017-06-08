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
	Return Value/Type: N/A
	Dependencies: Logic.Entity
	Exceptions: N/A
	Date Created: June 7th, 2017
	Date Modified: June 7th, 2017
	 */
	public void add(LinkedEntity node){//adds a node to the list
		
		if (root == null) {
			root = new LinkedEntity(node.x, node.y, node.width, node.height, node.colour, node.getCollideType());
			return;
		}
		
		LinkedEntity runner;
		runner = root;
		
		while (true) {
			if (runner.next == null) {
				runner.setNext(node);
				return;
				
			}
			else {
				runner = runner.next;
			}
		}
		
	}
	
	public LinkedEntity search(int n){//searches for the n'th object in the list
		LinkedEntity runner = root;//the node that iterates through the list
		
		if(runner != null){
			for(int i=0;i<n;i++){
				if(runner.next != null)runner = runner.next;
				else return null;
			}//end for
			return runner;
		}//end if
		return null;
	}//end search
}//end class
