package io;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.system.windows.WINDOWPLACEMENT;

import render.Camera;
import world.World;

public class Input {
	
	private boolean keys[];
	private boolean current;
	
	private static Vector2f mousePos = new Vector2f();
	private static double[] x = new double[1], y = new double[1];
	 
	public Input() {
				
		this.keys = new boolean[GLFW_KEY_LAST];
		for(int i = 0; i< GLFW_KEY_LAST; i++) {
			keys[i] = false;
		}
	}
	
	public boolean isMouseDown() {
		//current = true;
		return glfwGetMouseButton(Window.WINDOW, 0) ==1;
	}
	
	public boolean isMouseUp() {
	return glfwGetMouseButton(Window.WINDOW, 0) ==0;
	}
		
	public Vector2f getMousePosition() {
		glfwGetCursorPos(Window.WINDOW, x, y);
		mousePos.set((float)x[0] ,(float)y[0]);
		
		return mousePos;
	}
	
	public Vector2f getMouseTile() {
		Vector2f mouse = new Vector2f();
		
		mouse = getMousePosition();
		
		
		float camX = -(Window.WIDTH - World.SCALE *2) /2 - Camera.POSITION.x;
		float camY = (Window.HEIGHT - World.SCALE *2) /2 - Camera.POSITION.y;

		mouse.add(new Vector2f(camX, -camY));
		
		mouse.x = (int) Math.floor(mouse.x/World.SCALE);
		mouse.y = (int) Math.floor(mouse.y/World.SCALE);
	
		return mouse;
	}
	
	public boolean isKeyDown(int key) {
		return glfwGetKey(Window.WINDOW, key) ==1;
	}
	
	public boolean isKeyPressed(int key) {
		return (isKeyDown(key) && !keys[key]);
	}
	
	public boolean isKeyReleased(int key) {
		return (!isKeyDown(key)); //if key not down but array says true
	}
	
	public void update() {
		for(int i=0; i<GLFW_KEY_LAST; i++) {
			keys[i] = isKeyDown(i);
		}
	}
}
