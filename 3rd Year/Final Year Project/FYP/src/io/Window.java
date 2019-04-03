package io;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;

import org.lwjgl.glfw.*;

public class Window {
	
	public static int WIDTH = 640;
	public static int HEIGHT = 480;
	public static long WINDOW;
	private static String title = "game";
	
	//private long window;
	private static boolean fullscreen;
	private static boolean hasResized;
	private static GLFWWindowSizeCallback callback;
	
	private Input input;
	
	private static void setWindow_lon() {
		
				WINDOW = glfwCreateWindow(
						  WIDTH,
						  HEIGHT,
						  title, 
						  fullscreen ? glfwGetPrimaryMonitor() : 0, //test for fullscreen
						  0
						  );
		
		if(WINDOW ==0) {
		throw new IllegalStateException("Failed to create window");	
		}
		
		if(!fullscreen) {
		GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(WINDOW,
					(vid.width()-WIDTH)/2,
					(vid.height()-HEIGHT)/2);
				
		glfwShowWindow(WINDOW);
		
		}
		glfwMakeContextCurrent(WINDOW);
		
		//input = new Input(window);
		setLocalCallbacks();
		
		
	}
	
	private static void setLocalCallbacks() { //called to get new screen coords and size
		callback = new GLFWWindowSizeCallback() {
			@Override 
			public void invoke(long argWindow, int argWidth, int argHeight) {
				WIDTH = argWidth;
				HEIGHT = argHeight;
				hasResized = true;
			}
		};
		glfwSetWindowSizeCallback(WINDOW, callback);
	}
	
	
	
	public Window() {
		setFullscreen(false);
		hasResized = false; //helper 
		setWindow_lon();
		
		//createWindow("game");
	}
	
	public void cleanUp() {
		glfwFreeCallbacks(WINDOW);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(WINDOW);
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(WINDOW);
	}
	
//	public void update() {
//		hasResized = false;
//		glfwPollEvents();
//	}
	

	public boolean hasResized() {return hasResized;}
	public boolean isFullscreen() {return fullscreen;}
	public long getWindow() {return WINDOW;} //temp

	
}
