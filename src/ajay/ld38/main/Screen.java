package ajay.ld38.main;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Screen extends Canvas implements Runnable, KeyListener{
	//ImageIO.read(Game.class.getResourceAsStream("/res/loading.png"));
	private static final long serialVersionUID = -572016035490436338L;
	BufferStrategy s;
	
	Logic logic;
	
	int direction = -1;
	boolean pressed;
	
	long last;
	double delta;
	long timeperupdate = 1000/60;
	
	Thread fpsThread;
	
	boolean nextlevel;
	
	int moves;
	
	Clip woosh;
	
	public Screen(){
		addKeyListener(this);
	}
	
	public void init(){
		last = System.currentTimeMillis();
//		fpsThread = new Thread(new FPSCounter());
//		fpsThread.start();
		Thread game = new Thread(this);
		game.start();
	}
	
	public void run() {
		
		setup();
		
		while(true){
			long now = System.currentTimeMillis();
			long updateLength = now - last;
		    last = now;
			delta = updateLength / ((double)timeperupdate);
			if(delta > 20) delta = 1;
			
			if(nextlevel){
				logic.nextlevelani = true;
				nextlevel = false;
			}
			
			update();
			
			render();
//			fpsThread.interrupt();
		}
	}
	
	public void update(){
		int direction = this.direction;
		logic.update(this, direction);
		if(direction != -1){
			this.direction = -1;
			woosh.setFramePosition(0);
			woosh.stop();
			woosh.start(); 
		}
	}
	
	public void render(){
		s = getBufferStrategy();
		if(s!=null){
			Graphics2D g = (Graphics2D) s.getDrawGraphics();
			
//			g.setColor(Color.white);
//			g.fillRect(0, 0, getWidth(), getHeight());
			
			logic.render(g);
			
			s.show();
		}else{
			createBufferStrategy(3);
		}
	}
	
	public void setup(){
		logic = new Logic(this, 1);
		
        try {
        	AudioInputStream stream = AudioSystem.getAudioInputStream(new BufferedInputStream(Screen.class.getResourceAsStream("/res/woosh.wav")));
			woosh = AudioSystem.getClip();
			woosh.open(stream);
			stream = AudioSystem.getAudioInputStream(new BufferedInputStream(Screen.class.getResourceAsStream("/res/music.wav")));
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!pressed && e.getKeyCode() == KeyEvent.VK_D){
			direction = 0;
			pressed = true;
		}
		if(!pressed && e.getKeyCode() == KeyEvent.VK_A){
			direction = 1;
			pressed = true;
		}
		if(!pressed && e.getKeyCode() == KeyEvent.VK_W){
			direction = 2;
			pressed = true;
		}
		if(!pressed && e.getKeyCode() == KeyEvent.VK_S){
			direction = 3;
			pressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(logic.ontitle){
			logic.starttime = System.nanoTime();
			logic.ontitle = false;
			return;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(logic.colorsgone.size() >= logic.maxcolor && !nextlevel && !logic.nextlevelani){
				nextlevel = true;
				return;
			}
			if(logic.level.blocks.size() <= 0){
				nextlevel = true;
				return;
			}
			do{
				logic.color++;
				if(logic.color>=logic.maxcolor){
					logic.color = 0;
				}
			}while(logic.colorsgone.contains(logic.color));
		}
		if(e.getKeyCode() == KeyEvent.VK_R && !nextlevel && !logic.nextlevelani){
			logic.levelnum--;
			nextlevel = true;
		}
	}
	
	class FPSCounter extends Thread{
	    private long lastTime;
	    private double fps; //could be int or long for integer values

	    public void run(){
	        while (true){//lazy me, add a condition for an finishable thread
	            lastTime = System.nanoTime();
	            try{
	                Thread.sleep(1000); // longer than one frame
	            }
	            catch (InterruptedException e){

	            }
	            fps = 1000000000.0 / (System.nanoTime() - lastTime); //one second(nano) divided by amount of time it takes for one frame to finish
	            System.out.println(fps);
	            lastTime = System.nanoTime();
	        }
	    }
	    public double fps(){
	        return fps;
	    } 
	}
	
}
