package ajay.ld38.levels;

import ajay.ld38.main.Block;
import ajay.ld38.main.Level;
import ajay.ld38.main.Logic;

public class Level6 extends Level{
	public Level6(Logic logic){
		super(logic, 9,7);
		
		colormax = 3;
		
		blocks.add(new Block(logic, 0, 8, 3));
		blocks.add(new Block(logic, 0, 7, 3));
		blocks.add(new Block(logic, 0, 5, 3));
		blocks.add(new Block(logic, 1, 4, 4));
		blocks.add(new Block(logic, 1, 4, 5));
		blocks.add(new Block(logic, 1, 1, 3));
		blocks.add(new Block(logic, 1, 1, 2));
		blocks.add(new Block(logic, 1, 2, 4));
		blocks.add(new Block(logic, 1, 2, 5));
		blocks.add(new Block(logic, 2, 8, 6));
		blocks.add(new Block(logic, 2, 7, 6));
		blocks.add(new Block(logic, 2, 5, 6));
		blocks.add(new Block(logic, -1, 1, 1));
		blocks.add(new Block(logic, -1, 5, 2));

		setWallColor(4, 7, 5, 7, 0);
		setWallColor(0, 0, 1, 0, 1);
		setWallColor(0, 5, 0, 6, 2);
	}
}
