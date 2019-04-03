package entity;

import org.joml.Vector2f;

import world.Tile;
import world.World;

public class Tower {
	
	private int hp;
	private static Vector2f size = new Vector2f(2,3);
	
	private Vector2f location;
	
	public Tower(World w, Vector2f tower) {
		
		hp = 100;
		location = tower;
		
		for(int i = 0; i < size.x; i++) {
			for(int j = 0; j < size.y; j++) {
				w.setTile(Tile.base, (int)location.x, (int)location.y+j);
				w.setTile(Tile.base, (int)location.x+i, (int)location.y+j);
			}
			//
		}
	}
	
	public void attacked(int damage) {
		hp -=damage;
		System.out.println("Tower hp: " + hp);
	}
	
	public Vector2f getLocation() {
		Vector2f temp = new Vector2f().add(location);
		return temp;
	}

	public int getHp() {
		return hp;
	}
	
	
}
