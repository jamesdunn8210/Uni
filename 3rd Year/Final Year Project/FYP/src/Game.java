import static org.lwjgl.glfw.GLFW.glfwPollEvents;

import java.awt.Button;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import GUI.GUI;
import entity.BaseUnit;
import entity.Player;
import io.Input;
import io.Window;
import render.Camera;
import world.World;

public class Game {
	
	Vector2f mouse = new Vector2f(); //because 0,0
	BaseUnit myUnit = null;
	boolean tileSelected = false;
	Input input;
	World world;
	GUI gui;
	
	public Game(Camera camera, World world) {
		this.world = world;		
		input = new Input();
		gui = new GUI();
	}
	
	private void checkCamera() {
		//Camera controls
		if(input.isKeyDown(GLFW.GLFW_KEY_A) ) {
			Camera.POSITION.sub(new Vector3f(-6,0,0));					
		}
		if(input.isKeyDown(GLFW.GLFW_KEY_D) ) {
			Camera.POSITION.sub(new Vector3f(6,0,0));
		}
		if(input.isKeyDown(GLFW.GLFW_KEY_W) ) {
			Camera.POSITION.sub(new Vector3f(0,6,0));
		}
		if(input.isKeyDown(GLFW.GLFW_KEY_S) ) {
			Camera.POSITION.sub(new Vector3f(0,-6,0));
		}
		if(input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			GLFW.glfwDestroyWindow(Window.WINDOW);
		}
	
	}
	
	private void checkNewUnit(Player current) {
		if(input.isKeyPressed(GLFW.GLFW_KEY_1) ) {
			update();
			if(input.isKeyReleased(GLFW.GLFW_KEY_1) ) {
				current.newUnit(1);
			}
			
		}
		if(input.isKeyPressed(GLFW.GLFW_KEY_2) ) {
			update();
			if(input.isKeyReleased(GLFW.GLFW_KEY_2) ) {
				current.newUnit(2);
			}
			
		}
		if(input.isKeyPressed(GLFW.GLFW_KEY_3) ) {
			update();
			if(input.isKeyReleased(GLFW.GLFW_KEY_3) ) {
				current.newUnit(3);
			}
			
		}
		
		if(input.isKeyDown(GLFW.GLFW_KEY_G) ) {
			update();
			if(input.isKeyReleased(GLFW.GLFW_KEY_G) ) {
				System.out.println("Gold : " + current.getGold());
			}
			
		}
		if(myUnit != null) {
			checkInfo();
		}
		
				
	}
	
	public void nextTurn(Player current, Player enemy) {
		
			checkCamera();
			checkNewUnit(current);
			
			//input.isMouseDown();	
			
			if(input.isKeyDown(GLFW.GLFW_KEY_ENTER)) {
				System.out.println("enter pressed");
				update();
				if(input.isKeyReleased(GLFW.GLFW_KEY_ENTER)) {
					current.isTurnover = true;
					current.addGold();
				}
			}
			
			
			if(input.isMouseDown()) {
			
				
				update();
				
				if(input.isMouseUp()) {
					
					if(!current.isTurnover)
					{
						mouse.set(input.getMouseTile());
										
						if(tileSelected) {
												
							int t = world.getTile((int) mouse.x, (int) mouse.y).getId();
							
							switch(t) {
							
							case 0: //water
								//deselect and nothing
								current.HideValidMoves(myUnit);
								tileSelected = false;
								break;
							case 1: //grass
								//deselect and nothing
								current.HideValidMoves(myUnit);
								tileSelected = false;
								break;
							case 2://selected
								tileSelected(current, enemy);
								tileSelected = false;

								break;
							case 3: //base
								current.attackBase(enemy, myUnit);		
								current.HideValidMoves(myUnit);

								break;
								
							default:
								
							}				
						}
						
						else {
							if(current.selectUnit(mouse) != null) {
								myUnit = current.selectUnit(mouse); 
								
								if(myUnit.canMove()) {
									current.showValidMoves(myUnit);
									tileSelected = true; //tile selected 
								}
							}
							
						}
					}
					
				}
				
				}

	}		
	
	

	private void checkInfo() {
		if(input.isKeyDown(GLFW.GLFW_KEY_H)) {
			update();
			if(input.isKeyReleased(GLFW.GLFW_KEY_H)) {
				myUnit.displayInfo();
			}
		}
		
	}

	public void update() {
		glfwPollEvents();
	}
	
	private void tileSelected(Player current, Player enemy) {
		
		switch(world.checkOccupied(mouse)) { //check unit on tile 
		
			case 0: //unoccupied
				current.HideValidMoves(myUnit);
				mouse.sub(myUnit.getLocation()); // return tile to move to 
				current.move(myUnit, mouse);
				tileSelected = false;
				break;
				
			case 1: //one of my units here
				System.out.println("Cannot move onto tile already occupied");
				break;
				
			case 2: //enemy unit here
				BaseUnit enemyUnit = world.getUnit(mouse);						
				myUnit.attack(enemyUnit);
				current.HideValidMoves(myUnit);
				
				tileSelected = false;

				if(enemyUnit.isDead()) { //move onto it's tile, claim gold
					
					world.setEmpty(enemyUnit);  
					enemy.removeUnit(enemyUnit);
					
					mouse.sub(myUnit.getLocation()); 
					current.move(myUnit, mouse);
					
					//gold
				}
				if(myUnit.isDead()) { // enemy survives 
					world.setEmpty(myUnit); //remove unit from game 
					current.removeUnit(myUnit);
				}
										
				break;
			
		}
	}
}
