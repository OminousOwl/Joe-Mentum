package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class GameMenu  extends JFrame implements ActionListener {
	BufferedImage bg1 = null;
	//TODO try/catch statment for reading in the backgrouds
	
	JButton play = new JButton("Play");
	JButton settings = new JButton("Settings");
	JButton exit = new JButton("Exit");
	
	public GameMenu() {
		JFrame guiFrame = new JFrame("Joe-Mentum");
		guiFrame.setSize(759, 580);
		guiFrame.setResizable(false);
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setLocationRelativeTo(null);
		
	}
	public void paint(Graphics g){
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}
