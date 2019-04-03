package GUI;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import assets.Assets;
import collision.AABB;
import collision.Collision;
import io.Input;
import io.Window;
import render.Camera;
import render.Shader;
import render.TileSheet;
import world.World;

public class Button {
	public static final int STATE_IDLE = 0;
	public static final int STATE_SELECTED = 1;
	public static final int STATE_CLICKED = 2;
	
	private AABB boundingBox;
	private static Matrix4f transform = new Matrix4f();
	
	private int selectedState;

	public Button(Vector2f position, Vector2f scale) {
		this.boundingBox = new AABB(position, scale);
		selectedState = STATE_IDLE;
	}
	
	public void update(Input input) {
		
		Vector2f mousePos = input.getMousePosition(); //for world not camera 

		mousePos.x -=Window.WIDTH/2;
		mousePos.y -=Window.HEIGHT/2;

		Collision data = boundingBox.getCollision(mousePos);

		System.out.println(mousePos.x + " : " + mousePos.y);
		
		if (data.isIntersecting) {
			selectedState = STATE_SELECTED;
				
				if(input.isMouseUp()) {
					selectedState = STATE_CLICKED;
				}
		}
		else selectedState = STATE_IDLE;
	}
	
	public void render(Camera camera, TileSheet sheet, Shader shader) {
		
		Vector2f position = boundingBox.getCenter(), scale = boundingBox.getHalfExtent();
		
	
		//transform.identity().translate(position.x-scale.x, position.y+scale.y, 0).scale(scale.x, scale.y, 1); // Middle/Fill
		
		
		transform.identity().translate(position.x-scale.x, position.y+scale.y, 0).scale(scale.x, scale.y, 1); // Middle/Fill
		//System.out.println(position.x + " " + position.y);
		
		shader.setUniform("projection", camera.getProjectionWindow().mul(transform));
		
		sheet.bindTile(shader, 4, 1);
		
		
		Assets.getModel().render();

	}
	
	public int getState() {
		return selectedState;
	}
	
	public void resetState() {
		selectedState = STATE_IDLE;
		
	}

	public void updatePosition(Vector2f vector2f) {
		boundingBox.setCenter(vector2f);
		
	}
	
	
}
