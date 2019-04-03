package entity;

import org.joml.Vector2f;

import io.Window;
import render.Animation;
import render.Camera;
import world.World;

public class HeavyUnit  extends BaseUnit {
	private static final int ANIM_IDLE = 0;
	private static final int ANIM_WALK = 1;
	private static final int ANIM_SIZE = 2;
	
	public static int COST= 18;
	
	public HeavyUnit(Vector2f location) {	
		super(ANIM_SIZE);
		
		setAnimation(ANIM_IDLE, new Animation(1, 1, "player/heavy/idle"));
		this.location = location;
		 
		this.attkDmg = 14;
		this.health = 19;
		this.moveRange = 2;
	}

}
