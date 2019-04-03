package io;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseHandler extends GLFWCursorPosCallback{

	@Override
	public void invoke(long window, double xpos, double ypos) {
		// TODO Auto-generated method stub
		
		
		System.out.println("X:" + xpos + "Y:" + ypos);
	}
	

	
}
