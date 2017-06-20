package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/*
 *       __
 * 	 ___( o)> quack
 *   \ <_. )
 *    `---' 
 */

@SuppressWarnings("serial")
public class GameMenu extends JFrame implements ActionListener{
	
	private static MainGame game;

	// GUI components
	JButton play;
	JButton exit;
	
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem;
	
	JFrame frame;
	//images
	BufferedImage bg;
	BufferedImage logo;

	public GameMenu(MainGame game) {
		this.game = game;
		frame = new JFrame();
		setSize(768, 432);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		//create the menu bar
		menuBar = new JMenuBar();
		
		//build the first menu
		menu = 	new JMenu("Play");
		menuBar.add(menu);
		
		//JMenuItems show the menu items
		menuItem = new JMenuItem("New", new ImageIcon("images/bg.png"));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Exit", new ImageIcon("images/sTile.png"));
		menu.add(menuItem);
		
		//add menu bar to the the frame
		frame.add(menu);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == play) {
			this.dispose();
			game.gc.setVisible(true);
			game.setState(0);
		}
	}

}