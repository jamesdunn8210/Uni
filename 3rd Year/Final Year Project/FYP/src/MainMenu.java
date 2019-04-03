import static org.lwjgl.glfw.GLFW.glfwPollEvents;

import org.joml.Vector2f;

import GUI.Button;
import io.Input;
import io.Window;
import render.Camera;
import render.Shader;
import render.Texture;
import render.TileSheet;

public class MainMenu {
	
	Input input;
		
	private Button pVp; //AI vs AI
	private Button pVc; //Player vs AI
	private Button cVc; //Player vs Player
	
	Vector2f position = new Vector2f(0,0);
	Vector2f scale = new Vector2f(64,64);
	
	TileSheet sheetpvp = new TileSheet("pVp.png", 9);
	TileSheet sheetpvc = new TileSheet("pVc.png", 9);
	TileSheet sheetcvc = new TileSheet("cVc.png", 9);

	
	public MainMenu() {
		input = new Input();
		
		pVp = new Button(new Vector2f(0+scale.x, 0+scale.y), scale);
		pVp = new Button(position, scale);
		pVp = new Button(position, scale);
		

	}

	public boolean startGame() {


		
		
		
		return false;
	}

	public void render(Camera camera, Shader shader) {
		pVp.render(camera, sheetpvp, shader);
		
	}

	public void update() {

		if(input.isMouseDown()) {
			
			poll();
			
			if(input.isMouseUp()) {
									
				pVp.update(input);
				
				if(buttonClicked(input)) {
					System.out.println("Pvp started");
				}
				
				
				//get mouse position
				//check which button presesd
				
				
			}
		}
	}
	
	public void poll() {
		glfwPollEvents();
	}
	
	public boolean buttonClicked(Input input) {
		if(pVp.getState() == Button.STATE_CLICKED) {
			pVp.resetState();
			return true;
		}
		return false;
	}
}
			
	
	


