package Intermediary;

import java.awt.Color;

public class LinkedList {
	private LinkedEntity head;//the first node in the list
	private LinkedEntity tail;//the last node in the list
	
	public LinkedList(int x, int y, int width, int height,Color colour, char collision){
		head = new LinkedEntity(x,y,width,height,colour,collision);
	}//end constructor
	
	public LinkedList(){
		head = null;
	}//end constructor
	
	public LinkedEntity getHead(){return this.head;}
	
	public LinkedEntity getTail(){return this.tail;}
	
	public void add(LinkedEntity node){//adds a node to the list
		LinkedEntity runner = head;
		if(runner.equals(null) == true){
			runner = node;
		}//end if
		
		else{
			runner = runner.next;
		}//end else
	}//end add
	
	public LinkedEntity search(int n){//searches for the n'th object in the list
		LinkedEntity runner = head;//the node that iterates through the list
		
		if(runner.equals(null) == false){
			for(int i=0;i<n;i++){
				if(runner.next.equals(null) == false)runner = runner.next;
				else return null;
			}//end for
			return runner;
		}//end if
		return null;
	}//end search
}//end class
