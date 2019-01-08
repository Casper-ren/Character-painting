package utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageChange {
	public static void filter(ImageIcon imageIcon,int i) {
        BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(),imageIcon.getIconHeight()
                , BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0,imageIcon.getImageObserver());
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
              for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                int pixel = bufferedImage.getRGB(j2, j1);//j2ºá×ø±ê,j1Êú×ø±ê
                switch(i) {
                case 1:
                	Color c=new Color(pixel);
                	int black=(int) ((c.getGreen()+c.getBlue()+c.getRed())/3);
                	int hb=0;
                	if(black<150) {
                		hb=new Color(0,0,0).getRGB();
                	}
                	else {
                		hb=new Color(255,255,255).getRGB();
                	}
                	pixel =hb;
                	break;
                case 2:
                	Color d=new Color(pixel);
                	int g1=255-d.getGreen();
                	int b1=255-d.getBlue();
                	int r1=255-d.getRed();
                	int dp=new Color(r1,g1,b1).getRGB();
                	pixel =dp;
                	break;
                case 3:
                	Color e=new Color(pixel);
                	int grey=(int) (e.getGreen()*0.59+e.getBlue()*0.11+e.getRed()*0.3);
                	int hd=new Color(grey,grey,grey).getRGB();
                	pixel =hd;
                	break;
                default:
                	break;
                }
                bufferedImage.setRGB(j2, j1, pixel);
              }
            }
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
            try {
				ImageIO.write(bufferedImage, "png",  new File("ÂË¾µ.png"));
				File file=new File("ÂË¾µ.png");
				BufferedImage image = ImageIO.read(file);
				JFrame frame3 = new JFrame();
			    JLabel label = new JLabel(new ImageIcon(image));
			    frame3.getContentPane().add(label, BorderLayout.CENTER);
			    frame3.pack();
			    frame3.setVisible(true); 					
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
}