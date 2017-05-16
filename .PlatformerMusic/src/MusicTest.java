import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import javax.swing.*;
import jaco.mp3.player.MP3Player;
import java.io.File;

public class MusicTest implements ActionListener {

	public static void main(String[] args) {
		// TODO end my life
		new MusicTest();
	}

	MP3Player player = new MP3Player();
	JButton play = new JButton("Play");
	JButton pause = new JButton("Pause");
	JButton song2 = new JButton("Different Song");

	public MusicTest() {
		JFrame guiFrame = new JFrame("Music Test");
		guiFrame.setLayout(new GridLayout(3, 1));
		guiFrame.setSize(250, 300);
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setLocationRelativeTo(null);

		play.addActionListener(this);
		pause.addActionListener(this);
		song2.addActionListener(this);

		guiFrame.add(play);
		guiFrame.add(song2);
		guiFrame.add(pause);

		
		
		guiFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO end my life
		if (e.getSource() == play) {
			player.addToPlayList(new File("music/song.mp3"));
			player.skipForward();
			player.play();
			//new MP3Player(new File("music/song.mp3")).play();
		} else if (e.getSource() == pause) {
			player.pause();
		} else if (e.getSource() == song2){
			player.addToPlayList(new File("music/song2.mp3"));
			player.skipForward();
			player.play();
		}
	}
}
