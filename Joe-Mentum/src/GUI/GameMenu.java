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
import javax.swing.JPanel;

/*
 *       __
 * 	 ___( o)> quack
 *   \ <_. )
 *    `---' 
 */

@SuppressWarnings("serial")
public class GameMenu extends JPanel implements ActionListener, Runnable {
	// images
	BufferedImage bg1;
	BufferedImage bg2;
	BufferedImage bg3;
	BufferedImage bg4;
	BufferedImage bg5;

	// menu stuff
	JButton play = new JButton("Play");
	JButton settings = new JButton("Settings");
	JButton exit = new JButton("Exit");

	public static void main(String[] args) {
		// TODO ------ new GameMenu();
		new GameMenu();
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
			bg1 = ImageIO.read(new File("images/1.png"));
			bg2 = ImageIO.read(new File("images/2.png"));
			bg3 = ImageIO.read(new File("images/3.png"));
			bg4 = ImageIO.read(new File("images/4.png"));
			bg5 = ImageIO.read(new File("images/5.png"));
		} catch (IOException e) {
			System.out.println("Error found:\n" + e);
		}
		
		guiFrame.setVisible(true);
		repaint();
	}

	public void paint(Graphics g) {
		int x = 0, x2 = 0, x3 = 0, x4 = 0, x5 = 0;
		while(true){
			g.drawImage(bg1, x, 0, null);
			g.drawImage(bg2, x2, 0, null);
			g.drawImage(bg3, x3, 0, null);
			g.drawImage(bg4, x4, 0, null);
			g.drawImage(bg5, x5, 0, null);
			
			x2 += 10;
			x3 += 7;
			x4 += 5;
			x5 += 3;
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
