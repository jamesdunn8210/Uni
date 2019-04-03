package assets;

import render.Model;

public class Assets {
	private static Model model;
	
	public static Model getModel() {return model;}
	
	public static void initAsset() {
		float[] vertices = new float[] {
				-1f, 1f, 0, //top left
				1f, 1f, 0,  //top right
				1f, -1f, 0, //bot right
				-1f, -1f, 0, //bot left
		};
		
		float[] texture = new float[] {
				0,0,
				1,0,
				1,1,
				0,1,
		};
		
		int[] indices = new int[] { //render 2 triangles
				0,1,2,
				2,3,0
		};
		
		model = new Model(vertices, texture, indices);
	}
	
	public static void deleteAsset() {
		model = null;
	}
}
