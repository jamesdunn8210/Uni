package render;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.Window;

public class Camera {
	
	public static Vector3f POSITION = new Vector3f(0,0,0);
	private Matrix4f projection;
	
	public Camera() {
		setProjection();
	}
	
	public void setProjection() {
		projection = new Matrix4f().setOrtho2D(-Window.WIDTH/2, Window.WIDTH/2, -Window.HEIGHT/2, Window.HEIGHT/2);
	}
	
	public Matrix4f getProjection() { //for in game
		Matrix4f target = new Matrix4f(); 
		Matrix4f pos = new Matrix4f().setTranslation(POSITION); //for camera

		target = projection.mul(pos,target); //points camera to target
		return target;
		
	}	
	
	public Matrix4f getProjectionWindow() { //for gui relative to window
		return projection;
	}
	
	
}
