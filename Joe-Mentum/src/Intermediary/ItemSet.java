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
		
		while (true) {
			if (runner.next.getItemGenID() == itemID) {
				runner.setNext(runner.next.next);
				return;
				
			}
			else {
				runner = runner.next;
			}
		}
	}

	public Item getHead() {
		return root;
	}
}
