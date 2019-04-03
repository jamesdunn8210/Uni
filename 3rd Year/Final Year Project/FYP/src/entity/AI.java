package entity;

import java.util.ArrayList;
import java.util.Iterator;

import org.joml.Vector2f;

import world.Tile;
import world.World;

public class AI extends Player {
	
	BaseUnit currentUnit;
	Player player;
	

	public AI(World world, Vector2f towerLoc, int myIndex, Player player) {
		super(world, towerLoc, myIndex);
		this.player = player;
	}
	
	public void nextTurn() {

		if(units.isEmpty()) {
			buyNewUnit();
		}
		
		currentUnit = getNextUnit();
		if(currentUnit == null) {
			isTurnover = true;
			this.addGold();
		}
		
		if(!isTurnover)
		{	
			if(myBaseSafe()) {
				
				if(!currentUnit.moved) {
					
					if(!canAttackPlayerBase()){
						
						ArrayList<Vector2f> moves = getValidMoves(currentUnit, Tile.water);
						moves = checkForEnemys(moves); //all safe tiles
						if(moves != null) {
							moveSafely(moves); //all safe places to move 
						}

					}
				}
			}	
			else {
				
				//move unit towards enemy 
			}
			
			if(units.size() < player.units.size()) {
				System.out.print(this.gold);
				buyNewUnit();
			}
		}	
	}
	
	private void protectTower() {
		ArrayList<Vector2f> moves = getValidMoves(currentUnit, Tile.water);
		
		Vector2f closest = moves.get(0);
		for(Vector2f m : moves) {
			m.sub(tower.getLocation());
			if( (m.x < closest.x) && (m.y < closest.y)) {
				closest = m;
			}
		}
		
		
		currentUnit.move(closest);
		
		 
		
		//move towards my tower
		
		
		//if in range of enemy in range of tower, attack
		
		
	}

	private boolean buyNewUnit() {
		
		if(this.gold >= HeavyUnit.COST) {
			newUnit(3); 
			this.gold-=HeavyUnit.COST;
			 System.out.println("Built new unit");
			 return true;
		}
		else if (this.gold >= MedUnit.COST) {
			newUnit(2);
			this.gold-=MedUnit.COST;
			System.out.println("Built new unit");
			return true;
		}
		else if (this.gold >= LightUnit.COST) {
			newUnit(1);
			this.gold-=LightUnit.COST;
			System.out.println("Built new unit");
			return true;
		}
		return false;
	}
	
	private BaseUnit getNextUnit() {
		
		for(BaseUnit u : units) {
			if(u.moved != true) {
				return u;
			}
		}
		return null;
	}

	private ArrayList<Vector2f> checkForEnemys(ArrayList<Vector2f> validMoves) {
	
		Iterator<Vector2f> i = validMoves.iterator();
		while (i.hasNext()) {
			BaseUnit playerUnit = world.getUnit(i.next());
			if(playerUnit != null) { //! ==
				
				if(canUnit1Win(playerUnit, currentUnit)) {
					i.remove(); //remove unit we can't kill
					System.out.println("Enemy in range - can't defeat it ");
				}
				else if(canUnit1Win(currentUnit, playerUnit)) {
					//can kill it
					
					if(isNewTileSafe(playerUnit)) {
						currentUnit.attack(playerUnit);
   					 	checkDead(playerUnit);
   					 	isTurnover = true;
   					 	System.out.println("Moved to new tile and killed enemy unit");
   					 	return null;
					}					
				}
			}			
		}
		return validMoves;
		
	}
	
	private void moveSafely(ArrayList<Vector2f> validMoves) {
				
		for(Vector2f move : validMoves) {
			
			if(isTileSafe(move)) {
				System.out.println("MOVING SAFE");
				move(currentUnit, move.sub(currentUnit.location));
				currentUnit.moved = true;
				
				break;
				//find nearest to enemy tower 
			}
			
		}
		currentUnit.moved = true; //can't move 
		
	}

	private boolean myBaseSafe(){ //possible danger zones 

		int x = (int) tower.getLocation().x;
		int y = (int)  tower.getLocation().y+1;
		
		for(int i = x - BaseUnit.MAX_MOVE_DISTANCE; i <= x + BaseUnit.MAX_MOVE_DISTANCE; i++) { 
			
		    for(int j = y-BaseUnit.MAX_MOVE_DISTANCE+1; j <= y+BaseUnit.MAX_MOVE_DISTANCE+1; j++) {
		    	
		    	if(world.checkOccupied(new Vector2f(i,j)) != 0) {
		    		if(world.checkOccupied(new Vector2f(i,j)) != myIndex) {
		    			
		    			//if enemy unit able to attack my tower
		    			
		    			BaseUnit playerUnit = world.getUnit(new Vector2f(i,j));
		    			
		    			if(canUnit1Win(currentUnit, playerUnit)) { //Survive attack
		    				    				
		    				 if( (isNewTileSafe(playerUnit) || (tower.getHp() <50)) ) {
		    					 currentUnit.attack(playerUnit);
		    					 currentUnit.moved = true;
		    					 checkDead(playerUnit);
		    					 System.out.println("Attacked player");
		    					 break;
		    				 }
		    				 
		    			}
		    			else { //can't kill it 
		    				
		    				if(buyNewUnit()) {
		    					

		    					return true;
		    				}
		    				
		    			}
		    			return false;
		    		}
		    		
		    	}
		    	
		    	
		    }
		}
		return true;
	}

	private boolean isNewTileSafe(BaseUnit unit) {

		int newHealth = currentUnit.health - unit.attkDmg;
		
		for(BaseUnit playerUnit : player.units) {
			
			int x = (int) unit.location.x;
			int y = (int) unit.location.y;
			
			for(int i = x - playerUnit.moveRange; i <= x + playerUnit.moveRange; i++) { 
				
			    for(int j = y - playerUnit.moveRange; j <= y+playerUnit.moveRange; j++) {
	
			    	if(unit.location.equals(new Vector2f(i,j))) { //new location
			    		
			    		if(!unit.equals(unit)) //unit still exists so ignore
			    		{
				    		if(playerUnit.attkDmg >= newHealth) {
				    			return false; //not safe
				    		}		    		
			    		}			    		
			    	}
			    }
			}

		}
		return true;
	}
	
	private boolean isTileSafe(Vector2f newTile) {
		
		for(BaseUnit playerUnit : player.units) {
			
			int x = (int) playerUnit.location.x;
			int y = (int) playerUnit.location.y;
			
			for(int i = x - playerUnit.moveRange; i <= x + playerUnit.moveRange; i++) { 
				
			    for(int j = y - playerUnit.moveRange; j <= y+playerUnit.moveRange; j++) {
	
			    	if(newTile.equals(new Vector2f(i,j))) { //new location
			    		System.out.println("IN RANGE OF ENEEMY");
			    		if(playerUnit.attkDmg >= currentUnit.health) {
				    			return false; //not safe
				    		}		    		
			    		}			    		
			    	}
			    }
			}
		return true;
	}
	
	private boolean canUnit1Win(BaseUnit unit1, BaseUnit unit2){
		
		if(unit1.checkInRange(unit2.location)) { //in range to attack
			if( (unit1.attkDmg >= unit2.getHealth()) && (unit1.health > unit2.attkDmg)) { //can kill it
				return true;
			}
		}
		return false;
	}

	private void checkDead(BaseUnit playerUnit) {
		if(playerUnit.isDead()) { //move onto it's tile
			
			world.setEmpty(playerUnit); 
			player.HideValidMoves(playerUnit);
			move(currentUnit, playerUnit.location.sub(currentUnit.location));
			player.removeUnit(playerUnit);
			this.gold += 10; 
			
			//gold
		}
		if(currentUnit.isDead()) { // player survives 
			world.setEmpty(currentUnit); //remove unit from game 
			removeUnit(currentUnit);
		}
		
	}
	
	private BaseUnit isUnitSafe() { //null == safe
		
		for(BaseUnit playerUnit : player.units) {
			
			int x = (int) playerUnit.location.x;
			int y = (int) playerUnit.location.y;
			
			for(int i = x - playerUnit.moveRange; i <= x + playerUnit.moveRange; i++) { 
				
			    for(int j = y - playerUnit.moveRange; j <= y+playerUnit.moveRange; j++) {

			    	if(currentUnit.location.equals(new Vector2f(i,j))) {
			    		
			    		//can I kill it
			    		if(canUnit1Win(currentUnit, playerUnit)) {
			    			
			    			return null; //safe
			    		}
			    		else {
			    			return playerUnit;
			    		}
			    					    		
			    	}
			    }
			}
		}
		return null;
	}
	
	private boolean canAttackPlayerBase() {
		
		if(currentUnit.checkInRange(player.tower.getLocation())) {
						
			if(isUnitSafe() == null) {
				player.tower.attacked(currentUnit.attkDmg);
				currentUnit.moved = true;
				System.out.println("Attacked players base");
				return true;
			}
		}
		return false;
	}
	
}
