package ajay.ld38.main;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Level {
	public int width = 5;
	public int height = 5;
	public int blockwidth=64,blockheight=64;
	public int color,colormax=2;
	
	public ArrayList<Wall> walls = new ArrayList<>();
	public ArrayList<Block> blocks = new ArrayList<>();
	
	public Level(Logic logic){
		System.err.println("YOU DO NOT WANT THIS, YOU CALLED THE WRONG SUPER FUNCTION FOR LEVEL");
	}
	
	public Level(Logic logic, int width, int height){
		this.width = width;
		this.height = height;
		for(int i=0;i<width;i++){
			walls.add(new Wall(i, 0, i+1, 0, -1));
			walls.add(new Wall(i, height, i+1, height, -1));
		}
		for(int i=0;i<height;i++){
			walls.add(new Wall(0, i, 0, i+1, -1));
			walls.add(new Wall(width, i, width, i+1, -1));
		}
	}
	
	public void setWallColor(int x, int y, int endx, int endy, int color){
		getWall(x,y,endx,endy).color = color;
	}
	
	public Wall getWall(int x, int y, int endx, int endy){
		for(Wall wall: walls){
			if(wall.x == x && wall.y == y && wall.endx == endx && wall.endy == endy){
				return wall;
			}
		}
		return null;
	}
	
	public void render(Logic logic, Graphics2D g){
		for(Wall wall: walls){
			if(wall.color == -1) wall.render(logic, g);
		}
		for(Wall wall: walls){
			if(wall.color != -1) wall.render(logic, g);
		}
	}
}
