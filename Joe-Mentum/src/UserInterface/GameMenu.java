package UserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 *       __
 * 	 ___( o)> quack
 *   \ <_. )
 *    `---' 
 */

@SuppressWarnings("serial")
public class GameMenu extends JFrame implements ActionListener {

	private static MainGame game;

	// GUI components
	JButton play;
	JButton exit;
	JTextField joeName;

	// images
	BufferedImage bg;
	BufferedImage logo;

	@SuppressWarnings("static-access")
	public GameMenu(MainGame game) {
		this.game = game;
		// Basic JFrame Setup
		setSize(768, 432);
		setLocationRelativeTo(null);
		setTitle("Joe-Mentum");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// import background image
		try {
			bg = ImageIO.read(new File("res/images/bg.png"));
			logo = ImageIO.read(new File("res/gui/logo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// create image header for the mainmenu
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
		headerPanel.setBackground(Color.BLACK);
		JLabel joeIcon = new JLabel("");
		joeIcon.setIcon(new ImageIcon("/images/bg.png"));

		add(headerPanel, BorderLayout.NORTH);

		// Create the panel which will contain the central elements and absorb
		// any vertical stretch
		JPanel stretchPanel = new JPanel();
		stretchPanel.setLayout(new BorderLayout());

		// Create the panel containing the controls
		JPanel buttonsPanel = new JPanel();
		GridLayout buttonsLayout = new GridLayout(0, 1);
		buttonsLayout.setVgap(5);
		buttonsPanel.setLayout(buttonsLayout);
		stretchPanel.add(buttonsPanel, BorderLayout.NORTH);

		// create text field which contains game name
		joeName = new JTextField("Joe-Mentum");
		joeName.setEditable(false);
		joeName.setBackground(Color.DARK_GRAY);
		joeName.setForeground(Color.WHITE);
		joeName.setBorder(null);
		joeName.setHorizontalAlignment(JTextField.CENTER);
		buttonsPanel.add(joeName);

		// Create the play game button
		play = new JButton("Play");
		play.addActionListener(this);
		play.setBackground(Color.GREEN);
		buttonsPanel.add(play);

		// Create the padding below the play game buttons
		JPanel basePadding = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics g2 = g.create();
				g2.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
				g2.drawImage(logo, 250, 65, null);
			}
		};
		stretchPanel.add(basePadding, BorderLayout.CENTER);

		add(stretchPanel, BorderLayout.CENTER);

		// Create the quit button
		exit = new JButton("Quit Game");
		exit.addActionListener(this);
		exit.setBackground(Color.LIGHT_GRAY);
		add(exit, BorderLayout.SOUTH);

		// pack the elements
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == play) {
			this.dispose();
			game.gc.setVisible(true);
			game.setState(0);
		} else if (e.getSource() == exit) {
			System.exit(0);
		}
	}

}