package entity;

import java.util.ArrayList;
import org.joml.Vector2f;

import render.Camera;
import render.Shader;
import world.Tile;
import world.World;


public class Player  {
	
	protected World world = null;
	
	protected ArrayList<BaseUnit> units;
	protected int gold;
	protected int myIndex;
	public boolean isTurnover = false;
	protected Tower tower; 
	private boolean selected = false;
	private boolean isDead = false;
	
	public Player(World world, Vector2f towerLoc, int myIndex) {
		units = new ArrayList<BaseUnit>();
		this.world = world;	
		this.tower = new Tower(world, towerLoc);
		this.myIndex = myIndex;
		this.gold = 38;
		
		newUnit(3);
	}
	

	public void newUnit(int type) {	 //fix start locations
		
		switch(type) {
			case 1:{			
				if(this.gold >= LightUnit.COST) {
					LightUnit lw = new LightUnit(tower.getLocation());
					lw.setPlayerIndex(myIndex);
					this.gold-=LightUnit.COST;
					
					world.setOccupied(lw);
									
					units.add(lw);
					break;
				}
			}
			case 2:{
				if(this.gold >= MedUnit.COST) {
					MedUnit mu = new MedUnit(tower.getLocation());
					mu.setPlayerIndex(myIndex);
					this.gold-=MedUnit.COST;
					
					world.setOccupied(mu);
					
					units.add(mu);
					break;
			
				}
				
			}
			case 3:{
				
				if(this.gold >= HeavyUnit.COST) {
					HeavyUnit hu = new HeavyUnit(tower.getLocation());
					hu.setPlayerIndex(myIndex);
					this.gold-=HeavyUnit.COST;
					
					world.setOccupied(hu);
					
					units.add(hu);
					break;
				}
			}
		}		
	}
	
	public void removeUnit(BaseUnit b) {
		if(units.contains(b)) {
			units.remove(b);
		}
	}
	
	public BaseUnit selectUnit(Vector2f mouse) { //returns unit on mouse tile
		for(BaseUnit u : units) {
			if( (u.getLocation().x == mouse.x) && (u.getLocation().y == mouse.y) ){
				return u;
			}
		}
		return null;
	}
	
	protected ArrayList<Vector2f> getValidMoves(BaseUnit myUnit, Tile t) {
		
		ArrayList<Vector2f> validMoves = new ArrayList<Vector2f>();
		
		int x = (int) myUnit.getLocation().x;
		int y = (int) myUnit.getLocation().y;
		
		
		for(int i = x- myUnit.getMoveRange(); i <= x+myUnit.getMoveRange(); i++) {
			
		    for(int j = y-myUnit.getMoveRange(); j <= y+myUnit.getMoveRange(); j++) {
		    	
		        if(x == i && y == j) { //ignore the center tile
		            
		        	
		        }
		        else { 
		        	if((world.checkOccupied(new Vector2f(i, j)) !=myIndex)) {
			        	if ((world.getTile(i, j).getId() == t.getId())) {
			        		
			        		validMoves.add(new Vector2f(i,j));
			        		
			        	}	
			        }
		
		        }
		    }
		}
		return validMoves;
	}
	
	public void showValidMoves(BaseUnit myUnit) {
		
		ArrayList<Vector2f> validMoves = getValidMoves(myUnit, Tile.water);
		
		for(Vector2f move : validMoves) {
			 world.setTile(Tile.selected, (int)move.x, (int)move.y);
		}
	}
	
	public void HideValidMoves(BaseUnit myUnit) {
		
		ArrayList<Vector2f> validMoves = getValidMoves(myUnit, Tile.selected);
		
		for(Vector2f move : validMoves) {
			 world.setTile(Tile.water, (int)move.x, (int)move.y);
		}
	}
	
	public void render(Shader s, Camera cam) {
		for(BaseUnit entity : units)
			{
				entity.render(s, cam, world);
			}
	}
	
	public void move(BaseUnit e, Vector2f newLocation) { 	
		
		world.setEmpty(e); //set previous spot to 0 
		
		e.move(newLocation);
		
		world.setOccupied(e);
		
		e.moved = true;
		//turnover = true;
	}
	
	public void addGold() {
		gold += 5;
	}
	
	
	public boolean turnOver() {
		if (isTurnover == true)
		{
			isTurnover = false;
			
			for(BaseUnit u: units) {
				u.moved = false;
			}
			
			return true;
		}
		return false;
	}


	public void attackBase(Player enemy, BaseUnit myUnit) {
		
		if(myUnit.checkInRange(enemy.tower.getLocation())) { //hit enemy tower not mine
			enemy.tower.attacked(myUnit.attkDmg);
			if(enemy.tower.getHp() <=0) {
				enemy.isDead = true;
			}
			myUnit.moved = true;
		}		
	}


	public boolean isDead() {
		return isDead;
	}


	public int getGold() {
		// TODO Auto-generated method stub
		return gold;
	}
	
}
