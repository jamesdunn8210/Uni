package entity;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import assets.Assets;
import io.Window;
import render.Animation;
import render.Camera;
import render.Shader;
import world.Tile;
import world.World;

public abstract class BaseUnit {
	
	public static int MAX_MOVE_DISTANCE = 3; //max move distance of fastest unit
	
	protected Animation[] animations;
	private Vector3f scale;
	private int use_animation;
	
	protected int attkDmg;
	protected int health;
	protected int moveRange;
	protected int cost;
	protected Vector2f location; 
	protected int playerIndex;
	protected boolean moved;
	
	protected boolean isDead;

	
	public BaseUnit(int max_animations) {
		
		this.animations = new Animation[max_animations];
		this.scale = new Vector3f(0.5f,0.5f,0);
		this.use_animation = 0;
		this.isDead = false;
		this.moved = false;
	}
	
	protected void setAnimation(int index, Animation animation) {
		animations[index] = animation;
	}
	
	public void useAnimation(int index) {
		this.use_animation = index;
	}
	
	public void setPlayerIndex(int index) {
		playerIndex = index;
	}
	
	public void move(Vector2f direction) {		
		location.add(direction);
		this.moved = true;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getAttackDamage() {
		return attkDmg;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void attack(BaseUnit enemy) {
		
		this.health -= enemy.attkDmg;
		enemy.health -= this.attkDmg;
		
		if(this.health <=0) {
			this.isDead = true;
			
		}
		if(enemy.health <=0) {
			enemy.isDead = true;
		}
		
		this.moved = true;
		
	}
	
	public void displayInfo() {
		System.out.println("Attack damage: " + attkDmg);
		System.out.println("Health: " + health);
	}
	
	public boolean checkInRange(Vector2f loc) { //all possible moves
		
		for(int i = (int) (location.x - this.moveRange); i <= location.x+moveRange; i++) {
		    for(int j = (int) (location.y-moveRange); j <= location.y+moveRange; j++) {
		    	
		    	if( (i == loc.x) && (j==loc.y) ){ //is in range of tile
		    		return true;
		    	}
		    	
		    }
		}
		return false;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	//public abstract void update(float delta, Window window, Camera camera, World world);
	
	public void render(Shader shader, Camera camera, World world) {
		Matrix4f target = camera.getProjection();
		target.mul(world.getWorldMatrix());
		
		target.translate(new Vector3f(location.x, -location.y, 0)); //- to go down
		target.scale(scale);
		
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		animations[use_animation].bind(0);
		Assets.getModel().render();
	}
	
	public Vector2f getLocation() {
		return location;
	}
	
	public int getMoveRange() {
		return moveRange;
	}
	
	public void SetLocation(Vector2f d) {
		location = d;
		
	}

	public boolean canMove() {
		return !moved;
	}
	
}
