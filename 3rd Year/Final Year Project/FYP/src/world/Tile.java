package world;

public class Tile {

	public static Tile tiles[] = new Tile[255];
	public static byte nbrOfTiles = 0;
	
	private byte id;
	private String texture;
	
	public static final Tile water = new Tile("water");
	public static final Tile grass = new Tile("grass"); //id 0
	public static final Tile selected = new Tile("selected"); //2
	public static final Tile base = new Tile("base");
	
	public Tile(String texture) {
		this.id = nbrOfTiles;
		nbrOfTiles++;
		this.texture = texture;
		
		if(tiles[id] !=null) {
			throw new IllegalStateException("Tiles at ["+id+"] is already being used");
		}
		tiles[id] = this; //now not null
	}
	
	public byte getId() {
		return id;
	}
	
	public String getTexture() {
		return texture;
	}
	
}
