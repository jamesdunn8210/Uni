package render;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Model {

	private int draw_count;
	private int v_id;
	private int t_id;
	private int i_id;
	
	
	public Model(float[] vertices, float[] tex_coords, int[] indices) {
		draw_count = indices.length;
		
		IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
		buffer.put(indices);
		buffer.flip();
		
		v_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
		
		t_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, t_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(tex_coords), GL_STATIC_DRAW);
		
		i_id = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0); //unbind	
		glBindBuffer(GL_ARRAY_BUFFER, 0); //unbind	
	}
	
	public void render() {
		
		glEnableVertexAttribArray(0); //access attribute and pointer
		glEnableVertexAttribArray(1); //for texture
		
		
		glBindBuffer(GL_ARRAY_BUFFER, v_id); 
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, t_id); //bind texture to buffer
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0); //1 for tex 2 for tex coords
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id); 
		glDrawElements(GL_TRIANGLES, draw_count, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0); 
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0); 
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	private FloatBuffer createBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	
}
