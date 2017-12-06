package ajay.ld38.main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Block {
	int color;
	
	BufferedImage image;
	
	int x,y;//map cordinates
	
	boolean moved;//moved this frame
	
	boolean dying;
	float dyingsize = 1;
	
	int startx,starty;//for animations
//	float percentagemoved = 1;
	
	public Block(Logic logic, int color, int x, int y){
		this.color = color;
		this.x = x;
		this.y = y;
		startx = x;
		starty = y;
		
		try {
			image = ImageIO.read(Block.class.getResourceAsStream("/res/block"+color+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void render(Logic logic, Graphics2D g){
		int renderx = logic.paddingleft + x*image.getWidth();
		int rendery = logic.paddingtop + y*image.getHeight();
		int anix = logic.paddingleft + startx*image.getWidth();
		int aniy = logic.paddingtop + starty*image.getHeight();
//		if(color ==0)System.out.println(x + " " + y + " " + startx + " " + starty);
//		percentagemoved = 0;
		if(dying){
			g.drawImage(image, (int) (renderx + (image.getWidth() - image.getWidth()*dyingsize)/2), (int) (rendery + (image.getHeight() - image.getHeight()*dyingsize)/2), (int) (image.getWidth()*dyingsize), (int) (image.getHeight()*dyingsize), null);
		}else if(logic.screen.pressed){
			g.drawImage(image, (int) (logic.paddingleft + (x+((float)startx-x)*logic.percentagemoved)*image.getWidth()), (int) (logic.paddingtop + (y+((float)starty-y)*logic.percentagemoved)*image.getHeight()), null);
		}else{
			g.drawImage(image, (int) (renderx), (int) (rendery), null);
		}
//		g.drawImage(image, (int) (renderx + (anix-renderx)*percentagemoved), (int) (rendery + (aniy-rendery)*percentagemoved), null);
//		g.drawImage(image, (int)(logic.paddingleft + x*image.getWidth() + ((logic.paddingleft+x*image.getWidth())-(logic.paddingleft+startx*image.getWidth()))*percentagemoved), (int) (logic.paddingtop + y*image.getHeight() + ((logic.paddingtop+y*image.getHeight())-(logic.paddingtop+starty*image.getHeight()))*percentagemoved), null);
	}
	
	public boolean move(Logic logic, int xmove,int ymove){  //how much to move in each direction
		int tempstartx = x;
		int tempstarty = y;
	
//		percentagemoved = 1;
		int x = this.x + xmove;
		int y = this.y + ymove;
		
		Block block = logic.getBlock(x,y);
		Wall wall = logic.level.getWall(x, y, x+Math.abs(ymove), y+Math.abs(xmove));
		if(xmove == -1) wall = logic.level.getWall(x-xmove, y, x-xmove+Math.abs(ymove), y+Math.abs(xmove));
		if(ymove == -1) wall = logic.level.getWall(x, y-ymove, x+Math.abs(ymove), y-ymove+Math.abs(xmove));
		boolean success = false;
		while((x<logic.level.width && x>=0 && !logic.isBlocked(x,y) && y<logic.level.height && y>=0) || (wall!=null && wall.color == color)){
			block = logic.getBlock(x,y);
			if(block != null){
				if(!block.move(logic, xmove, ymove) && logic.isOccupied(x,y)){
					return false;
				}
			}
			startx = tempstartx;
			starty = tempstarty;
			success = true;
			this.x = x;
			this.y = y;
			moved = true;
			logic.moved = true;
			x+=xmove;
			y+=ymove;
			if((wall!=null && wall.color == color)) wall = null;
			else{
				wall = logic.level.getWall(x, y, x+Math.abs(ymove), y+Math.abs(xmove));
				if(xmove == -1) wall = logic.level.getWall(x-xmove, y, x-xmove+Math.abs(ymove), y+Math.abs(xmove));
				if(ymove == -1) wall = logic.level.getWall(x, y-ymove, x+Math.abs(ymove), y-ymove+Math.abs(xmove));
			}
		}
		if(!success){
//			if(wall!=null && wall.color == color)
			return false;
		}
		return true;
	}
	
	public void update(Logic logic, int direction){
		if(dying){
			dyingsize-= 0.06*logic.screen.delta;
			if(dyingsize<=0){
				dyingsize = 1;
				logic.level.blocks.remove(this);
				boolean containsthiscolor = false;
				for(Block block: logic.level.blocks){
					if(block.color == color){
						containsthiscolor = true;
						break;
					}
				}
				if(!containsthiscolor){
					logic.colorsgone.add(color);
					logic.screen.keyReleased(new KeyEvent(logic.screen, 0, 0, 0, KeyEvent.VK_SPACE, ' '));
				}
			}
		}
		
		if(!moved){
			switch (direction){
				case 0:
					move(logic, 1, 0);
					break;
				case 1:
					move(logic, -1, 0);
					break;
				case 2:
					move(logic, 0, -1);
					break;
				case 3:
					move(logic, 0, 1);
			}
		}
		
		moved = false;
	}
}
