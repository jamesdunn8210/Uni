package entity;

import org.joml.Vector2f;

import render.Animation;

public class MedUnit  extends BaseUnit {
	public static final int ANIM_IDLE = 0;
	public static final int ANIM_WALK = 1;
	
	public static final int ANIM_SIZE = 2;
	
	public static int COST= 12;

	public MedUnit(Vector2f location) {	
		super(ANIM_SIZE);
		
		setAnimation(ANIM_IDLE, new Animation(1, 1, "player/med/idle"));
		
		this.location = location;		
		
		this.attkDmg = 10;
		this.health = 15;
		this.moveRange = 2;
	}
}
