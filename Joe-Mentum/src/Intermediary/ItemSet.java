package Intermediary;

import Logic.Item;

public class ItemSet {

	private Item root;
	public int itemGenIDCounter = 0;
	
	public ItemSet() {
		root = null;
	}
	
	/*
	Name: add
	Description: Adds a node to the list
	Parameters: One Item
	Return Value/Type: N/A
	Dependencies: Logic.Item
	Exceptions: N/A
	Date Created: June 18th, 2017
	Date Modified: June 18th, 2017
	 */
	public Item add(Item node){//adds a node to the list
		
		if (root == null) {
			root = new Item(node.getType(), node.x, node.y);
			return node;
		}
		
		Item runner;
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
	
	/*
	Name: add
	Description: Adds a node to the list
	Parameters: One Item
	Return Value/Type: N/A
	Dependencies: Logic.Item
	Exceptions: N/A
	Date Created: June 18th, 2017
	Date Modified: June 18th, 2017
	 */
	public Item add(Item node, int x, int y){//adds a node to the list
		
		if (root == null) {
			root = new Item(node.getType(), node.x, node.y);
			return node;
		}
		
		Item runner;
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
	
	/*
	Name: remove
	Description: Removes a node from the list
	Parameters: One Item
	Return Value/Type: N/A
	Dependencies: Logic.Item
	Exceptions: N/A
	Date Created: June 18th, 2017
	Date Modified: June 18th, 2017
	 */
	public void remove(Item node, int itemID) {
		if (node == null) {
			return;
		}
		
		Item runner = root;
		Item previous = root;
		
		while (true) {
			if (runner.getItemGenID() == itemID) {
				if (runner == root && runner.next == null) {
					//Root is the only item in the level
					root = null;
					return;
				}
				else if (runner == root) {
					//The first item is the one that needs to be disposed of
					root = new Item(root.next.getType(), root.next.x, root.next.y, root.next.next);
					return;
				}
				else if (runner.next == null) {
					//Clears the last element of the list
					previous.setNext(null);
					return;
				}
				else { //Central element
					previous.setNext(runner.next);
					return;
				}
			}
			previous = runner;
			runner = runner.next;
		}
	}

	public Item getHead() {
		return root;
	}
}
