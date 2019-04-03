package GUI;

import java.awt.Font ;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import assets.Assets;
import render.Camera;
import render.Shader;
import render.Texture;
import render.TileSheet;
import world.Tile;
import world.World;


public class Panel {

	//private AABB boundingBox;
	private Matrix4f world;
	
	
	private static Matrix4f transform = new Matrix4f();
	private Texture panelTex;
	private int id;
	
	private Font font;

	int fontHeight; 
	
	private Vector2f position;
	private Vector2f scale;
	private Button b;
	
	public Panel(Vector2f position, Vector2f scale) {
		
		this.position = position;
		this.scale = scale;
		
		this.world = new Matrix4f().setTranslation(new Vector3f(0));
		this.world.scale(World.SCALE);
		
		//panelTex = new Texture("grass.png", "TESTSETES");
		
		
		
	}

	
	public void render(Camera camera, TileSheet sheet, Shader shader) {
		
//		Matrix4f target = camera.getProjection();
//		target.mul(world.getWorldMatrix());
//		
//		target.translate(new Vector3f(location.x, -location.y, 0)); //- to go down
//		target.scale(scale);
		
		transform.identity().translate(position.x-scale.x, position.y+scale.y, 0).scale(scale.x, scale.y, 1); // Middle/Fill

		shader.setUniform("projection", camera.getProjectionWindow().mul(transform));

		sheet.bindTile(shader, 4, 1);
		
		
		Assets.getModel().render();
		
//		Matrix4f tile_pos = new Matrix4f().translate(new Vector3f(0, 0, 0)); //*2
//		Matrix4f target = new Matrix4f();
//		
//		camera.getProjection().mul (world, target);
//		target.mul(tile_pos);
//		
//		shader.bind();
//		shader.setUniform("sampler", 0);
//		shader.setUniform("projection", target);
//		panelTex.bind(0);
//		Assets.getModel().render();
	}
}
