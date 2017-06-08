package GUI;

/**
 *@Name Cosmin Baciu, Quinn Fisher, Olivier Hébert
 *@DateCreated: May 30th, 2017
 *@DateModified: June 8th, 2017
 *@Description: This Class is the main menu, which loads the game, and allows quick changes to the game's settings
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 *       __
 * 	 ___( o)> quack
 *   \ <_. )
 *    `---' 
 */

@SuppressWarnings("serial")
public class GameMenu extends JFrame implements ActionListener{
	
	private static MainGame ech;
	
	/*private static JButton play;
	
	public GameMenu() {
		

		
		
		JPanel pane = new JPanel();
		setContentPane(pane);
		
		play = new JButton("pls work");
		pane.add(play);
		play.addActionListener(this);
		
		setVisible(true);
		setSize(new Dimension(500, 500));
		
		
	}

	
	public static void main (String[] args) {
		new GameMenu();
		
	} */
	// images
	BufferedImage i1;
	BufferedImage i2;
	BufferedImage i3;
	BufferedImage i4;
	BufferedImage i5;
	// GUI components
	JButton play;
	JButton exit;
	JFrame guiFrame;

	public static void main(String[] args) {
		
		new GameMenu();
		
		/*SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
			}
		});*/
	}

	public GameMenu() {
		try {
			i1 = ImageIO.read(new File("images/1.png"));
			i2 = ImageIO.read(new File("images/2.png"));
			i3 = ImageIO.read(new File("images/3.png"));
			i4 = ImageIO.read(new File("images/4.png"));
			i5 = ImageIO.read(new File("images/5.png"));
		} catch (IOException e) {
			// catch
		}
		// ================== BASIC FRAME SETUP ===============================
		guiFrame = new JFrame("Joe-Mentum");
		guiFrame.setResizable(false);
		guiFrame.setLayout(new BorderLayout());
		guiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		guiFrame.setSize(1080, 720);

		// ================== DRAWING THE BACKGROUND ==========================
		final JPanel pane = new JPanel() { // TODO make images become endless
											// background + parallax
			
			
			protected void paintComponent(Graphics g) {
				Graphics g2 = g.create();
				g2.drawImage(i1, 0, 0, guiFrame.getWidth(), guiFrame.getHeight(), null);
				g2.drawImage(i2, 0, 0, guiFrame.getWidth(), guiFrame.getHeight(), null);
				g2.drawImage(i3, 0, 0, guiFrame.getWidth(), guiFrame.getHeight(), null);
				g2.drawImage(i4, 0, 0, guiFrame.getWidth(), guiFrame.getHeight(), null);
				g2.drawImage(i5, 0, 0, guiFrame.getWidth(), guiFrame.getHeight(), null);
				g2.dispose();
				System.out.println(guiFrame.getWidth() + ", " + guiFrame.getHeight());
			}
			
			
		};
		guiFrame.setContentPane(pane); // added this to the background of the guiFrame
		
		// declaring buttons
		play = new JButton("Play");
		exit = new JButton("Pause");

		// add action listeners
		play.addActionListener(this);
		exit.addActionListener(this);

		// adding button pane to the guiFrame, and packing.
		JPanel buttonPane = new JPanel(new GridLayout(1, 2));
		buttonPane.add(play);
		guiFrame.add(buttonPane, BorderLayout.CENTER);
		guiFrame.setVisible(true);
	}


	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//if (e.getSource() == play) {
			this.dispose();
			//ech.setVisible(true);
			//new Thread(new MainGame()).start();
			new MainGame();
		//}
	}

}