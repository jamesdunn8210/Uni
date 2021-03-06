package render;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;


public class Shader {
	private int program;
	private int vs; //vertex shader
	private int fs; //fragment shader
	
	public Shader(String filename) {
		program = glCreateProgram();
		
		vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, readFile(filename+".vs"));
		glCompileShader(vs);
		
		if(glGetShaderi(vs, GL_COMPILE_STATUS) !=1) {
			System.err.println(glGetShaderInfoLog(vs));
			System.exit(1);
		}
		
		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(filename+".fs"));
		glCompileShader(fs);
		
		if(glGetShaderi(fs, GL_COMPILE_STATUS) !=1) {	//check
			System.err.println(glGetShaderInfoLog(fs));
			System.exit(1);
		}
		
		glAttachShader(program, vs);
		glAttachShader(program, fs);
		
		glBindAttribLocation(program, 0, "vertices"); //used in shader.vs
		glBindAttribLocation(program, 1, "textures"); 

		glLinkProgram(program);
		if(glGetProgrami(program, GL_LINK_STATUS) !=1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		
		glValidateProgram(program);
		if(glGetProgrami(program, GL_VALIDATE_STATUS) !=1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		
	}
	
	public void setUniform(String name, int value) {
		int location = glGetUniformLocation(program, name);
		if(location != -1) {
			glUniform1i(location, value); //i for int
		}
	}
	
	public void setUniform(String name, Vector4f value) {
		int location = glGetUniformLocation(program, name);
		if(location != -1) {
			glUniform4f(location, value.x, value.y, value.z, value.w);
		}
	}
	
	public void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(program, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16); //4*4
		value.get(buffer);
		
		if(location != -1) {
			glUniformMatrix4fv(location, false, buffer); 
		}
	}
	
	
	public void bind() {
		glUseProgram(program);
		
	}
	
	private String readFile(String filename) {
		StringBuilder s = new StringBuilder();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File( "./shaders/" + filename)));
			String line;
			while((line = br.readLine()) !=null) {
				s.append(line);
				s.append("\n"); //make like file
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return s.toString();
	}
	
	
}
