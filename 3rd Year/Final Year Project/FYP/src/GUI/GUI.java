package GUI;

import org.joml.Vector2f;

import collision.AABB;
import io.Input;
import io.Window;
import render.Camera;
import render.Shader;
import render.TileSheet;
import world.World;

public class GUI {
	private Shader shader;
	private Camera camera;
	private TileSheet sheet;

	private Button endTurn;
	private Panel panel;
	private AABB UI;
	private Vector2f position = new Vector2f();

	private int button_height = 64;
	private int button_width = 64;
	
	public GUI() {
		
		position = new Vector2f(Window.WIDTH/2 ,-Window.HEIGHT/2);
		
		shader = new Shader("gui");
		camera = new Camera();
		sheet = new TileSheet("gui.png", 9);
		//endTurn = new Button(position, (new Vector2f(button_width,button_height)));
		//endTurn = new Button(position, (new Vector2f(button_width,button_height)));

		panel = new Panel(new Vector2f(0,0), new Vector2f(button_width,button_height));
	}
	
	public void update(Input input) {
		
		//endTurn.update(input);		
	}

	public void resizeCamera() {
		camera.setProjection();
		//endTurn.updatePosition(new Vector2f(Window.WIDTH/2 ,-Window.HEIGHT/2));
	}
	
	public void render() {
		//shader.bind();
		panel.render(camera, sheet, shader);
		//endTurn.render(camera, sheet, shader);
	}
	
	public boolean buttonClicked(Input input) {
		if(endTurn.getState() == Button.STATE_CLICKED) {
			endTurn.resetState();
			return true;
		}
		return false;
	}
			
	
}
