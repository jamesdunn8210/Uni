import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL;

import GUI.GUI;
import assets.Assets;
import entity.AI;
import entity.Player;
import io.Timer;
import io.Window;
import render.Camera;
import render.Shader;
import world.TileRenderer;
import world.World;

public class Main {
	
	
	
		
	public Main() {
		
		if(!glfwInit()) {
			System.err.println("GLFW FAILED TO INIT");
			System.exit(1);
		}
				
		Window win = new Window();
		
		
		GL.createCapabilities();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);
		
		
		Assets.initAsset();
		Shader shader = new Shader("shader");
		Camera camera = new Camera();		
		TileRenderer tr = new TileRenderer();
		
		World world = new World("world2.png");
			
		Game game = new Game(camera, world);
		Player p1 = new Player(world, new Vector2f(7,6), 1);
		//Player p2 = new Player(world, new Vector2f(22,6), 2);
		//AI p1 = new AI(world, new Vector2f(7,6), 1);
		AI p2 = new AI(world, new Vector2f(22,6), 2, p1);
		
		double frame_cap = 1.0/60.0; //set to 60fps
		double frame_time = 0;
		double time = Timer.getTime();
		double unprocessed = 0; //proccess for time where window dragged eg. 
			
		Player current = p1;
		Player enemy = p2;
		
		while(!win.shouldClose()) {
			

			while(!current.isDead()) {
				
			if(current == p2) {
				current = p1;
				enemy = p2;
			}
			else {
				current = p2;
				enemy = p1;
			}
			
			while(!current.turnOver()) {
				
			boolean can_render = false; //stop rerendering 
			
			double time_2 = Timer.getTime();
			double passed = time_2 - time; 
			unprocessed+=passed;
			frame_time+=passed;
			
			time = time_2; //stop game getting faster
			
			while(unprocessed>= frame_cap) 	{ //update loop

				if(win.hasResized()) {
					camera.setProjection();
					glViewport(0,0,Window.WIDTH, Window.HEIGHT);
				}
				
				unprocessed-=frame_cap;
				can_render = true;

				if(current instanceof AI) {
					
					((AI) current).nextTurn();
				//if ai do this
				}
				else {
				game.nextTurn(current, enemy);
				}
				world.correctCamera();
				
				game.update(); //poll events
				if(frame_time >=1.0) {
					frame_time = 0;
				}
			}
			
			if(can_render) { //only render when scene changes
				glClear(GL_COLOR_BUFFER_BIT);
				world.render(tr, shader, camera);			
				p1.render(shader, camera);
				p2.render(shader, camera);
				
				win.swapBuffers();				
			}
			
			}
		}
			
			System.out.println("Game over, congrats " + current.toString());
	}
		
		Assets.deleteAsset();
		glfwTerminate();
		
	}
	
	public static void main(String[] args) {
		new Main();
	}

}