package ajay.ld38.levels;

import ajay.ld38.main.Block;
import ajay.ld38.main.Level;
import ajay.ld38.main.Logic;

public class Level2 extends Level{
	public Level2(Logic logic){
		super(logic, 5, 7);
		
		blocks.add(new Block(logic, 0, 0, 0));
		blocks.add(new Block(logic, 0, 3, 2));
		blocks.add(new Block(logic, 0, 1, 6));
		blocks.add(new Block(logic, 1, 4, 4));
		blocks.add(new Block(logic, 1, 1, 3));
		blocks.add(new Block(logic, 1, 1, 2));
		blocks.add(new Block(logic, 1, 2, 4));
		blocks.add(new Block(logic, -1, 2, 2));
		blocks.add(new Block(logic, -1, 2, 1));
		blocks.add(new Block(logic, -1, 0, 6));
		setWallColor(5, 4, 5, 5, 0);
		setWallColor(0, 5, 0, 6, 1);
	}
}
