package ajay.ld38.levels;

import ajay.ld38.main.Block;
import ajay.ld38.main.Level;
import ajay.ld38.main.Logic;

public class Level5 extends Level{
	public Level5(Logic logic){
		super(logic, 9,7);
		
		blocks.add(new Block(logic, 0, 8, 3));
		blocks.add(new Block(logic, 0, 7, 3));
		blocks.add(new Block(logic, 1, 4, 4));
		blocks.add(new Block(logic, 1, 4, 5));
		blocks.add(new Block(logic, 1, 1, 3));
		blocks.add(new Block(logic, 1, 1, 2));
		blocks.add(new Block(logic, 1, 2, 4));
		blocks.add(new Block(logic, 1, 2, 5));
		blocks.add(new Block(logic, 1, 8, 6));
		blocks.add(new Block(logic, -1, 0, 1));
		blocks.add(new Block(logic, -1, 5, 0));
		blocks.add(new Block(logic, -1, 5, 1));
		blocks.add(new Block(logic, -1, 5, 2));
		blocks.add(new Block(logic, -1, 6, 6));

		setWallColor(1, 0, 2, 0, 0);
		setWallColor(0, 0, 0, 1, 1);
	}
}
