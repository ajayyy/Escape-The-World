package ajay.ld38.levels;

import ajay.ld38.main.Block;
import ajay.ld38.main.Level;
import ajay.ld38.main.Logic;

public class Level4 extends Level{
	public Level4(Logic logic){
		super(logic, 9,7);
		
		blocks.add(new Block(logic, 0, 0, 0));
		blocks.add(new Block(logic, 0, 3, 2));
		blocks.add(new Block(logic, 0, 8, 6));
		blocks.add(new Block(logic, 0, 8, 3));
		blocks.add(new Block(logic, 1, 4, 4));
		blocks.add(new Block(logic, 1, 1, 3));
		blocks.add(new Block(logic, 1, 1, 2));
		blocks.add(new Block(logic, 1, 2, 4));
		blocks.add(new Block(logic, -1, 8, 6));
		blocks.add(new Block(logic, -1, 2, 5));
		blocks.add(new Block(logic, -1, 3, 1));
		blocks.add(new Block(logic, -1, 5, 2));

		setWallColor(4, 7, 5, 7, 0);
		setWallColor(0, 2, 0, 3, 1);
	}
}
