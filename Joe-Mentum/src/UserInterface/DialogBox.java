/**
 *@Name Cosmin Baciu, Quinn Fisher, Olivier Hébert
 *@DateCreated: June 17th, 2017
 *@DateModified: June 17th, 2017
 *@Description: The class used to handle narrative pop-up information
 */

package UserInterface;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Logic.Entity;

public class DialogBox extends Entity{

	private static final long serialVersionUID = 2691393504734868205L;
	
	private String speaker;
	private String text; //The message to display
	private int displayTime;
	public DialogBox next;
	private boolean queued = false;

	public BufferedImage frame;
	
	public DialogBox(int x, String message, int time) {
		this.x = x;
		this.setText(message);
		this.setDisplayTime(time);
		
		try {
			frame = ImageIO.read(new File("res/gui/dialog.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DialogBox(int x, String message, String speaker, int time) {
		this.x = x;
		this.setText(message);
		this.setDisplayTime(time);
		this.setSpeaker(speaker);
		
		try {
			frame = ImageIO.read(new File("res/gui/dialog.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(int displayTime) {
		this.displayTime = displayTime;
	}

	public DialogBox getNext() {
		return next;
	}

	public void setNext(DialogBox next) {
		this.next = new DialogBox(next.x, next.getText(), next.getSpeaker(), next.getDisplayTime());
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}
	
	public void dequeue() {
		if (this.next != null) {
			
		}
	}

	public boolean isQueued() {
		return queued;
	}

	public void setQueued(boolean queued) {
		this.queued = queued;
	}

}