package Intermediary;

import java.awt.Color;

public class LinkedList {
	private LinkedEntity head;//the first node in the list
	private LinkedEntity tail;//the last node in the list
	
	public LinkedList(int x, int y, int width, int height,Color colour, char collision){
		head = new LinkedEntity(x,y,width,height,collision);
	}//end constructor
	
	public LinkedList(){
		head = null;
	}//end constructor
	
	public void add(LinkedEntity node){//adds a node to the list
		if(head.equals(null)){//Handle empty list
			head = node;
		}//end if
		else{
			tail.next = node;
			tail = node;
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
