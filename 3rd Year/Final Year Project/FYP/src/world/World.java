package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import entity.BaseUnit;
import io.Window;
import render.Camera;
import render.Shader;

public class World {

	public static int WIDTH;
	public static int HEIGHT;
	public static int SCALE = 100;
	
	
	private byte[] tiles; //id of tile
	private int width;
	private int height;
	
	private Matrix4f world;	
		
	private BaseUnit[][] board;
	
	
	public World(String world) {
		try {
		BufferedImage tile_sheet = ImageIO.read(new File("./levels/"+ world));

		width = tile_sheet.getWidth();
		height = tile_sheet.getHeight();
		//scale = 64;
		
		board = new BaseUnit[width][height];
		setTilesOccupied();
		
		this.world = new Matrix4f().setTranslation(new Vector3f(0));
		this.world.scale(SCALE);
		
		
		int[] colorTileSheet = tile_sheet.getRGB(0, 0, width, height, null, 0, width);
		
		tiles = new byte[width * height];
				
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {	//id of tiles uses RBG red 1 - 255
				int red = (colorTileSheet[x + y * width] >> 16) & 0xFF; //bit shift
				
				Tile t;
				try{
					t = Tile.tiles[red]; //out of bounds,  255 tiles for color
				}catch(ArrayIndexOutOfBoundsException e) {
					t = null;
				}
				
				if(t != null) {
					setTile(t,x,y);
				}
			}
		}
	
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setTilesOccupied() { //initialise 
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				board[x][y] = null;
			}
		}
	}
	
	public void setOccupied(BaseUnit b) { //set players unit here
		board[(int)b.getLocation().x][(int)b.getLocation().y] = b;
	}
	
	public void setEmpty(BaseUnit b) {
		board[(int)b.getLocation().x][(int)b.getLocation().y]  = null;
	}
	
	public int checkOccupied(Vector2f loc) { //check if unit here
		if(board[(int)loc.x][(int)loc.y] == null) {
			return 0;
		}
		else {
			return board[(int)loc.x][(int)loc.y].getPlayerIndex();
		} 
	}
	
	public BaseUnit getUnit(Vector2f loc) {
		return board[(int)loc.x][(int)loc.y];
	}
	
	public Matrix4f getWorldMatrix() {
		return world;
	}
	
	public void render(TileRenderer render, Shader s, Camera cam) {
		for(int i = 0; i <height; i++) {
			for(int j = 0; j <width; j++) {
				render.renderTile(tiles[j+i*width], j, -i, s, world, cam);
			}
		}
	}
			
	public void correctCamera() {
		
		int w= -width * SCALE; //width of world
		int h= height * SCALE;
		
		if(Camera.POSITION.x > -(Window.WIDTH/2) + SCALE) {
			Camera.POSITION.x = -(Window.WIDTH/2) + SCALE;
		}
		
		if(Camera.POSITION.x < w +(Window.WIDTH/2) + SCALE) {
			Camera.POSITION.x = w +(Window.WIDTH/2) + SCALE;
		}
		
		if(Camera.POSITION.y < (Window.HEIGHT/2) - SCALE) {
			Camera.POSITION.y = (Window.HEIGHT/2) - SCALE;
		}
		
		if(Camera.POSITION.y > h -(Window.HEIGHT/2) - SCALE) {
			Camera.POSITION.y = h -(Window.HEIGHT/2) - SCALE;
		}
	}
	
	public void setTile(Tile tile, int x, int y) {
		tiles[x + y * width] = tile.getId();
	}
	
	public Tile getTile(int x, int y) { //return id of tile type
		return Tile.tiles[tiles[x+y*width]];
	}
}
