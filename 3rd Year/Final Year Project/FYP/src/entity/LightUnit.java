package entity;

import org.joml.Vector2f;
import io.Window;
import render.Animation;
import render.Camera;
import world.World;

public class LightUnit extends BaseUnit {
	public static final int ANIM_IDLE = 0;
	public static final int ANIM_SIZE = 2;
		
	public static int COST= 8;

	public LightUnit(Vector2f location) {	
		super(ANIM_SIZE);
		
		setAnimation(ANIM_IDLE, new Animation(1, 1, "player/light/idle"));
		this.location = location;
		
		this.attkDmg = 5;
		this.moveRange =3;
		this.health = 10;

	}

}
