package Intermediary;

import java.awt.Color;

public class MonsterSet {
	private Monster root;
	
	public Monster getHead(){return this.root;}
	
	public MonsterSet(int x, int y, int width, int height, int health, int attack, double maxSpeed, int exp, char AIstate, String enemyType){
		root = new Monster(x, y, width, height, health, attack, maxSpeed, exp, AIstate, enemyType);
	}//end constructor
	
	public MonsterSet(){
		root = null;
	}//end constructor
	
	/*
	Name: add
	Description: Adds a node to the list
	Parameters: One LinkedEntity
	Return Value/Type: N/A
	Dependencies: Logic.Entity
	Exceptions: N/A
	Date Created: June 11th, 2017
	Date Modified: June 11th, 2017
	 */
	public Monster add(Monster node){//adds a node to the list
		
		if (root == null) {
			root = new Monster(node.x, node.y, node.width, node.height, node.getAttack(), node.getHealth(), node.getSpeed(), node.getExpGain(), node.getAIState(), node.getEnemyType());
			return node;
		}
		
		Monster runner;
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
}
