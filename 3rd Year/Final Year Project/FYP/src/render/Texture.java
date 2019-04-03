package render;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	private int id;
	private int width;
	private int height;
	
	public Texture(String filename) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File("././textures/" + filename));
			width = bi.getWidth();
			height = bi.getHeight();
			
//			Graphics2D g2d = bi.createGraphics();
//			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 48));
//			g2d.drawString("test", 100, 100);
			
			int[] pixels_raw = new int[width * height];
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
			
			for(int j = 0; j < height; j++)
			{
				for(int i =0; i< width; i++) {
					int pixel = pixels_raw[j*width+i];//flipped
					pixels.put((byte) ((pixel >> 16) & 0xFF)); //RED
					pixels.put((byte) ((pixel >> 8) & 0xFF));	//Green
					pixels.put((byte) (pixel & 0xFF));			//Blue
					pixels.put((byte) ((pixel >> 24) & 0xFF));  //Alpha
				}
			}
			
			pixels.flip();
			
			id  = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels );
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Texture(String filename, String text) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File("././textures/" + filename));
			width = bi.getWidth();
			height = bi.getHeight();
			
//			Graphics2D g2d = bi.createGraphics();
//			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 48));
//			g2d.drawString(text, 100, 100);
			
			int[] pixels_raw = new int[width * height];
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
			
			for(int j = 0; j < height; j++)
			{
				for(int i =0; i< width; i++) {
					int pixel = pixels_raw[j*width+i];//flipped
					pixels.put((byte) ((pixel >> 16) & 0xFF)); //RED
					pixels.put((byte) ((pixel >> 8) & 0xFF));	//Green
					pixels.put((byte) (pixel & 0xFF));			//Blue
					pixels.put((byte) ((pixel >> 24) & 0xFF));  //Alpha
				}
			}
			
			pixels.flip();
			
			id  = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels );
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void bind(int sampler) {
		if(sampler >=0  && sampler <=31) {
			glActiveTexture(GL_TEXTURE0 + sampler); //bind texture to first sampler
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}
	
	
	
}
