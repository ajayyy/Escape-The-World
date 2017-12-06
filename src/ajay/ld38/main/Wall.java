package ajay.ld38.main;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Wall {
	int x,y,endx,endy;
	int color;
	public Wall(int x, int y, int endx, int endy, int color){
		this.x = x;
		this.y = y;
		this.endx = endx;
		this.endy = endy;
		this.color = color;
	}
	
	public void render(Logic logic, Graphics2D g){
		g.setColor(logic.getColor(color));
		Stroke oldStroke = g.getStroke();
//		if(color != -1){
//			if(endx != x){
//				g.fillRect(logic.paddingleft + x*logic.level.blockwidth, logic.paddingtop + y*logic.level.blockheight-2, logic.level.blockheight, 7);
//			}else{
//				g.fillRect(logic.paddingleft + x*logic.level.blockwidth-2, logic.paddingtop + y*logic.level.blockheight, 7, logic.level.blockheight);
//			}
//		}else{
			g.setStroke(new BasicStroke(10));
			g.drawLine(logic.paddingleft + x*logic.level.blockwidth, logic.paddingtop + y*logic.level.blockheight, logic.paddingleft + (endx)*logic.level.blockwidth, logic.paddingtop + (endy)*logic.level.blockheight);
			g.setStroke(oldStroke);
//		}
	}
}