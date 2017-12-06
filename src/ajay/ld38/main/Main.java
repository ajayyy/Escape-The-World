package ajay.ld38.main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main extends JFrame{
	private static final long serialVersionUID = 1484643694107322081L;
	public static void main(String[] args){
		new Main();
	}
	public Main(){
		this.setTitle("");
		this.setSize(1000,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		Screen screen = new Screen();
		screen.setPreferredSize(new Dimension(1000,600));
		this.add(screen);
		this.addKeyListener(screen);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		screen.init();
	}
}
