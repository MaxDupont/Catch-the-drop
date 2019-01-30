package com.projects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class WindowGame extends JFrame {

	private static WindowGame windowGame;
	private static Image background;
	private static Image drop;
	private static Image gameOver;
	private static float dropTop = -100;
	private static float dropLeft = 200;
	private static float dropSpeed = 200;
	private static long lastFrameTime;
	private static int score;

    public static void main(String[] args) throws IOException {
    	background = ImageIO.read(WindowGame.class.getResourceAsStream("background.jpg"));
		drop = ImageIO.read(WindowGame.class.getResourceAsStream("drop.png"));
		gameOver = ImageIO.read(WindowGame.class.getResourceAsStream("game over.png"));
    	windowGame = new WindowGame();
		windowGame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		windowGame.setLocation(200, 100);
		windowGame.setSize(900, 500);
		windowGame.setResizable(false);
		lastFrameTime = System.nanoTime();
		PanelGame panelGame = new PanelGame();
		panelGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				float dropRight = dropLeft + drop.getWidth(null);
				float dropBottom = dropTop + drop.getHeight(null);
				boolean isDrop = x>=dropLeft && x<=dropRight && y>=dropTop && y<=dropBottom;
				if (isDrop) {
					dropTop = -100;
					dropLeft = (int)(Math.random() * (panelGame.getWidth() - drop.getWidth(null)));
					dropSpeed = dropSpeed + 20;
					score++;
					windowGame.setTitle("Score is " + score);
				}
			}
		});
		windowGame.add(panelGame);
		windowGame.setVisible(true);
    }

    static void onRepaint(Graphics g) {
    	long currentTime = System.nanoTime();
    	float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
    	dropTop = dropTop + deltaTime * dropSpeed;
    	lastFrameTime = currentTime;
    	g.drawImage(background, 0, 0, null);
    	g.drawImage(drop, (int)dropLeft, (int)dropTop, null);
    	if (dropTop > windowGame.getHeight()) {
    		g.drawImage(gameOver, 250, 50, null);
		}
	}

	private static class PanelGame extends JPanel {
    	@Override
		protected void paintComponent (Graphics g) {
    		super.paintComponent(g);
    		onRepaint(g);
    		repaint();
		}
	}
}