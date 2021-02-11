package User;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Image {

	private BufferedImage img;
	private Map<BufferedImage, String> imageList = new HashMap<BufferedImage, String>();

	
	public Image() {
		getImage("bird.jpeg");
	}

	public BufferedImage getImage(String s) {
		img = null;
		try {
			img = ImageIO.read(new File(s));

		} catch (IOException e) {
			e.printStackTrace();
		}
		imageList.put(img, s);
		return img;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public Map<BufferedImage, String> getImageList() {
		return imageList;
	}

	public void setNewColor(BufferedImage img, String s) {
		this.imageList.put(img, s);

	}
}
