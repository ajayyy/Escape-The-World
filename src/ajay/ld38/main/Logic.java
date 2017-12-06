package ajay.ld38.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import ajay.ld38.levels.Level1;

public class Logic {
	int color,maxcolor=2;
	
	ArrayList<Integer> colorsgone = new ArrayList<>();
	
	Level level;
	int levelnum;
	
	Screen screen;
	
	int paddingleft,paddingtop;
	
	float percentagemoved = 1;
	
	BufferedImage background,middleground,foreground;
	float panmid,panfront;
	
	Color gameback = new Color(255, 255, 255, 150);
	BufferedImage gamebackimage;
	
	boolean nextlevelani = true;
	boolean start = true;
	float nextlevelpercent = 0f;
	
	boolean moved;
	
	long starttime;
	long finishtime;
	
	BufferedImage title;
	boolean ontitle = true;
	
	public Logic(Screen screen, int levelnum){
		this.screen = screen;
		
		restart(levelnum);
		
		try {
			background = ImageIO.read(Logic.class.getResourceAsStream("/res/background.png"));
			middleground = ImageIO.read(Logic.class.getResourceAsStream("/res/background0.png"));
			foreground = ImageIO.read(Logic.class.getResourceAsStream("/res/background1.png"));
			gamebackimage = ImageIO.read(Logic.class.getResourceAsStream("/res/gameback.png"));
			title = ImageIO.read(Logic.class.getResourceAsStream("/res/title.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void restart(int levelnum){
		level = new Level1(this);
		try {
			level = (Level) Class.forName("ajay.ld38.levels.Level" + levelnum).getConstructor(Logic.class).newInstance(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.levelnum = levelnum;
		color = level.color;
		maxcolor = level.colormax;
		colorsgone = new ArrayList<>();
		
		start = true;
		
//		screen.moves = 0;
		
		paddingleft = screen.getWidth()/2 - level.width*level.blockwidth/2;
		paddingtop = screen.getHeight()/2 - level.height*level.blockheight/2;
		
//		starttime = System.nanoTime();
	}
	
	public void update(Screen screen, int direction){
        if(levelnum > 7) return;
		
		panmid+=0.15*screen.delta;
		if(panmid > middleground.getWidth()) panmid = 0;
		panfront+=0.25*screen.delta;
		if(panfront > foreground.getWidth()) panfront = 0;
		
		if(ontitle) return;
		
		for(Block block: new ArrayList<>(level.blocks)){
			int fulldirection = direction;
			if(block.color != color) fulldirection = -1;
			block.update(this, fulldirection);
		}
		
		if(screen.pressed){
			percentagemoved -= 0.08*screen.delta;
			if(percentagemoved<=0){
				screen.pressed = false;
				percentagemoved = 1;
				for(Block block: level.blocks){
					block.startx = block.x;
					block.starty = block.y;
					if(block.x<0 || block.x>=level.width || block.y<0 || block.y>=level.height){
						block.dying = true;
						screen.pressed = true;
					}
				}
			}
		}
		
		if(nextlevelani){
			if(start){
				nextlevelpercent += 0.08*screen.delta;
				if(nextlevelpercent >= 1){
					nextlevelpercent = 1f;
					nextlevelani = false;
					start = false;
				}
			}else{
				nextlevelpercent -= 0.08*screen.delta;
				if(nextlevelpercent <= 0){
					nextlevelpercent = 0f;
					restart(levelnum+1);
				}
			}
		}
		
		if(moved){
			screen.moves++;
			moved = false;
		}
	}
	
	public void render(Graphics2D g){
	    AffineTransform originalTransform = g.getTransform();
		
		g.drawImage(background, 0, 0, null);
//		g.drawImage(middleground, (int) panmid, 0, null);
		g.translate(panmid, 0);
        g.drawImage(middleground, 0, 0, null);
        g.setTransform(originalTransform);
//		g.drawImage(middleground, (int) (panmid-middleground.getWidth()), 0, null);
		g.translate(panmid-middleground.getWidth(), 0);
        g.drawImage(middleground, 0, 0, null);
        g.setTransform(originalTransform);
//		g.drawImage(foreground, (int) (panfront), 0, null);
		g.translate(panfront, 0);
        g.drawImage(foreground, 0, 0, null);
        g.setTransform(originalTransform);
//		g.drawImage(foreground, (int) (panfront-foreground.getWidth()), 0, null);
		g.translate(panfront-foreground.getWidth(), 0);
        g.drawImage(foreground, 0, 0, null);
        g.setTransform(originalTransform);
        
        if(levelnum > 7){
        	if(finishtime == 0){
        		finishtime = System.nanoTime();
        	}
        	g.setColor(Color.black);
    		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    		g.setFont(g.getFont().deriveFont(Font.PLAIN, 75));
    		String message = "CONGRATS! YOU WON!";
    		g.drawString(message, screen.getWidth()/2 - g.getFontMetrics().stringWidth(message)/2, g.getFont().getSize());
    		g.setFont(g.getFont().deriveFont(Font.PLAIN, 40));
    		message = "You finished in " + screen.moves + " moves and " + ((System.nanoTime()-starttime)/60000000000l) + ":" + ((finishtime-starttime)/1000000000l %60) + " minutes!";
    		g.drawString(message, screen.getWidth()/2 - g.getFontMetrics().stringWidth(message)/2, screen.getHeight()/2+g.getFont().getSize()/2);
    		
    		return;
        }else if(ontitle){
        	g.drawImage(title, 0, 0, null);
        	return;
        }
        
        BufferedImage gamefeild = null;
        Graphics2D g2 = null;
        if(nextlevelani){
        	gamefeild = new BufferedImage(screen.getWidth(), screen.getHeight(), BufferedImage.TYPE_INT_ARGB);
        	g2 = g;
        	g = gamefeild.createGraphics();
        }
        
//        g.setColor(gameback);
//        g.fillRect(paddingleft, paddingtop, level.width*level.blockwidth, level.height*level.blockheight);
        g.drawImage(gamebackimage, paddingleft, paddingtop, level.width*level.blockwidth, level.height*level.blockheight, null);

		
		for(Block block: level.blocks){
			block.render(this, g);
		}
		
//		g.setColor(Color.black);
//		Stroke oldStroke = g.getStroke();
//		g.setStroke(new BasicStroke(4));
//		g.drawRect(paddingleft, paddingtop, level.width*level.blockwidth, level.height*level.blockheight);
//		g.setStroke(oldStroke);
		level.render(this, g);
		
		g.setColor(Color.black);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(g.getFont().deriveFont(Font.PLAIN, 50));
		String message = "Level: " + levelnum;
		g.drawString(message, screen.getWidth()/2 - g.getFontMetrics().stringWidth(message)/2, g.getFont().getSize());
		g.drawString("Moves: " + screen.moves, 0, screen.getHeight() - 20);
		message = "Time: " + ((System.nanoTime()-starttime)/60000000000l) + ":" + ((System.nanoTime()-starttime)/1000000000l %60);
		g.drawString(message, screen.getWidth()-g.getFontMetrics().stringWidth(message), screen.getHeight() - 20);
		message = "Press R to restart";
		g.drawString(message, screen.getWidth()/2 - g.getFontMetrics().stringWidth(message)/2, screen.getHeight()-20);
		
		switch(levelnum){
		case 1:
			g.setFont(g.getFont().deriveFont(Font.PLAIN, 30));
			message = "Use WASD to move!";
			g.drawString(message, 0, g.getFont().getSize());
			message = "Try to make each color exit!";
			g.drawString(message, 0, g.getFont().getSize()*2);
			message = "Red can exit in red walls!";
			g.drawString(message, 0, g.getFont().getSize()*3);
			message = "Press Space to change colors!";
			g.drawString(message, 0, g.getFont().getSize()*4);
			break;
		case 2:
			g.setFont(g.getFont().deriveFont(Font.PLAIN, 30));
			message = "You can make towers";
			g.drawString(message, 0, g.getFont().getSize());
			message = "Towers can help you";
			g.drawString(message, 0, g.getFont().getSize()*2);
			message = "reach high exits";
			g.drawString(message, 0, g.getFont().getSize()*3);
			message = "Black blocks don't move";
			g.drawString(message, 0, g.getFont().getSize()*4);
			break;
		case 3:
			g.setFont(g.getFont().deriveFont(Font.PLAIN, 30));
			message = "You can also use black";
			g.drawString(message, 0, g.getFont().getSize());
			message = "blocks to help you";
			g.drawString(message, 0, g.getFont().getSize()*2);
			break;
		case 5:
			g.setFont(g.getFont().deriveFont(Font.PLAIN, 30));
			message = "You need to use the";
			g.drawString(message, 0, g.getFont().getSize());
			message = "blue ones again";
			g.drawString(message, 0, g.getFont().getSize()*2);
			break;
		case 6:
			g.setFont(g.getFont().deriveFont(Font.PLAIN, 30));
			message = "Now it is getting";
			g.drawString(message, 0, g.getFont().getSize());
			message = "complicated...";
			g.drawString(message, 0, g.getFont().getSize()*2);
			break;
		}
		
		if(nextlevelani){
			g.dispose();
			g = g2;
			g.drawImage(gamefeild, (int) (gamefeild.getWidth() - gamefeild.getWidth() * nextlevelpercent) / 2, 0, (int) (gamefeild.getWidth() * nextlevelpercent), gamefeild.getHeight(), null);
		}
	}
	
	public boolean isOccupied(int x, int y){
		return getBlock(x, y) != null;
	}
	
	public boolean isBlocked(int x, int y){
		Block block = getBlock(x, y);
		return block != null && (block.color == -1);
	}
	
	public Block getBlock(int x, int y){
		for(Block block: level.blocks){
			if(block.x == x && block.y == y){
				return block;
			}
		}
		return null;
	}
	
	public Color getColor(int color){
		switch(color){
		case 0:
			return Color.red;
		case 1:
			return Color.blue;
		case 2:
			return new Color(0,128,0);
		}
		return Color.black;
	}
}
