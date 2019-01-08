package utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageConfirm {
	public static boolean isimage(String filePath) {
		File file = new File(filePath);
		try {
			Image image = ImageIO.read(file);
			return image != null;
		} catch(IOException ex) {
			return false;
		}
	}
}
