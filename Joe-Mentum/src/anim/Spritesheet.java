package anim;
/**
 *@Name Cosmin Baciu
 *@DateCreated: June 9th, 2017
 *@DateModified: June 12th, 2017
 *@Description: This class is used to efficiently split up pictures into animation
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 *       __
 * 	 ___( o)> quack
 *   \ <_. )
 *    `---' 
 */

public class Spritesheet {

	// Instance Variables
	private String path;
	private int frameWidth;
	private int frameHeight;
	private BufferedImage sheet = null;
	private BufferedImage[] frameImages;

	// Constructors
	public Spritesheet(String aPath, int width, int height) {

		path = aPath;
		frameWidth = width;
		frameHeight = height;

		try {
			sheet = ImageIO.read(new File(path));
			frameImages = getAllSprites();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public BufferedImage getSprite(int frame) {
		return frameImages[frame];
	}

	// Methods
	public int getHeight() {
		return frameHeight;
	}

	public int getWidth() {
		return frameWidth;
	}

	public int getColumnCount() {
		return sheet.getWidth() / getWidth();
	}

	public int getRowCount() {
		return sheet.getHeight() / getHeight();
	}

	public int getFrameCount() {
		int cols = getColumnCount();
		int rows = getRowCount();
		return cols * rows;
	}

	private BufferedImage getSprite(int x, int y, int h, int w) {
		BufferedImage sprite = sheet.getSubimage(x, y, h, w);
		return sprite;
	}

	public BufferedImage[] getAllSprites() {
		//takes the original photo, and crops it, once it is cropped, take all
		// the cropped pictures and put the photos into an array, for easy animation use
		int cols = getColumnCount();
		int rows = getRowCount();
		int frameCount = getFrameCount();
		BufferedImage[] sprites = new BufferedImage[frameCount];
		int index = 0;
		System.out.println("cols = " + cols);
		System.out.println("rows = " + rows);
		System.out.println("frameCount = " + frameCount);
		for (int row = 0; row < getRowCount(); row++) {
			for (int col = 0; col < getColumnCount(); col++) {
				int x = col * getWidth();
				int y = row * getHeight();
				System.out.println(index + " " + x + "x" + y);
				BufferedImage currentSprite = getSprite(x, y, getWidth(), getHeight());
				sprites[index] = currentSprite;
				index++;
			}
		}
		return sprites;

	}

}