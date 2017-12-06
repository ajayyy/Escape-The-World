package ajay.ld38.levels;

import ajay.ld38.main.Block;
import ajay.ld38.main.Level;
import ajay.ld38.main.Logic;

public class Level3 extends Level{
	public Level3(Logic logic){
		super(logic, 9,7);
		
		blocks.add(new Block(logic, 0, 0, 0));
		blocks.add(new Block(logic, 0, 3, 2));
		blocks.add(new Block(logic, 0, 8, 6));
		blocks.add(new Block(logic, 0, 8, 3));
		blocks.add(new Block(logic, 1, 4, 4));
		blocks.add(new Block(logic, 1, 1, 3));
		blocks.add(new Block(logic, 1, 1, 2));
		blocks.add(new Block(logic, 1, 2, 4));
		blocks.add(new Block(logic, -1, 5, 4));
		blocks.add(new Block(logic, -1, 6, 4));
		blocks.add(new Block(logic, -1, 7, 4));
		blocks.add(new Block(logic, -1, 8, 4));
		blocks.add(new Block(logic, -1, 5, 3));
		blocks.add(new Block(logic, -1, 5, 2));
		blocks.add(new Block(logic, -1, 5, 1));
		blocks.add(new Block(logic, -1, 0, 5));

		setWallColor(9, 2, 9, 3, 0);
		setWallColor(1, 7, 2, 7, 1);
	}
}
