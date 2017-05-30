package GUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

/*
 *       __
 * 	 ___( o)> quack
 *   \ <_. )
 *    `---' 
 */

public class GameMenu extends JFrame implements ActionListener, Runnable {
	// images
	Graphics g;
	BufferedImage bg1 = null;
	BufferedImage bg2 = null;
	BufferedImage bg3 = null;
	BufferedImage bg4 = null;
	BufferedImage bg5 = null;

	// menu stuff
	JButton play = new JButton("Play");
	JButton settings = new JButton("Settings");
	JButton exit = new JButton("Exit");

	public static void main(String[] args) {
		// TODO ------ new GameMenu();
		
	}

	public GameMenu() {
		// ================ setting basic things for the frame =================
		JFrame guiFrame = new JFrame("Joe-Mentum");
		guiFrame.setSize(384, 216);
		guiFrame.setResizable(false);
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setLocationRelativeTo(null);

		// importing images for background
		try {
			bg1 = ImageIO.read(new File("1.png"));
			bg2 = ImageIO.read(new File("2.png"));
			bg3 = ImageIO.read(new File("3.png"));
			bg4 = ImageIO.read(new File("4.png"));
			bg5 = ImageIO.read(new File("5.png"));
		} catch (IOException e) {
			System.out.println("Error found:\n" + e);
		}
		
		guiFrame.setVisible(true);
		paint(g);
	}

	public void paint(Graphics g) {
		g.drawImage(bg1, 0, 0, null);
		g.drawImage(bg2, 0, 0, null);
		g.drawImage(bg3, 0, 0, null);
		g.drawImage(bg4, 0, 0, null);
		g.drawImage(bg5, 0, 0, null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
