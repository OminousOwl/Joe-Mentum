/**
 *@Name Cosmin Baciu, Quinn Fisher, Olivier Hébert
 *@DateCreated: June 17th, 2017
 *@DateModified: June 17th, 2017
 *@Description: The class used to handle narrative pop-ups
 */

package UserInterface;

import Intermediary.LinkedEntity;

public class DialogQueue {
	private DialogBox root;
	
	public DialogQueue() {
		root = null;
	}
	
	public DialogQueue(int x, String message, int time) {
		root = new DialogBox(x, message, time);
	}
	
	public DialogQueue(int x, String message, String speaker, int time) {
		root = new DialogBox(x, message, speaker, time);
	}
	
	/*
	Name: enqueue
	Description: Adds a node to the list
	Parameters: One LinkedEntity
	Return Value/Type: One DialogBox
	Dependencies: Logic.DialogBox
	Exceptions: N/A
	Date Created: June 17th, 2017
	Date Modified: June 17th, 2017
	 */
	public void enqueue(DialogBox dialog) {
		if (root == null) {
			root = new DialogBox(dialog.x, dialog.getText(), dialog.getSpeaker(), dialog.getDisplayTime());
			return;
		}
		
		DialogBox runner;
		runner = root;
		
		while (true) {
			if (runner.next == null) {
				runner.setNext(dialog);
				return;
			}
			else {
				runner = runner.next;
			}
		}
	}
	
	/*
	Name: dequeue
	Description: Removes the root from the list
	Parameters: N/A
	Return Value/Type: N/A
	Dependencies: Logic.DialogBox
	Exceptions: N/A
	Date Created: June 17th, 2017
	Date Modified: June 17th, 2017
	 */
	public void dequeue() {
		if (root != null) {
			root = root.getNext();
		}
	}

	public DialogBox getHead() {
		return root;
	}

	public void setHead(DialogBox head) {
		this.root = head;
	}
	
}
