package com.rae.graphics;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;

import com.rae.game.Game;
import com.rae.game.Matrix;
import com.rae.game.tetromino.Tetromino;

public class MatrixPanel extends JPanel implements Runnable{
	
	public static final int MWIDTH = 100;
	public static final int MHEIGHT = 220;
	public static final int BLOCK_SIZE = 10;
	
	// Logical components
	private Game game;
	private Matrix matrix;
	private Tetromino currentShape;
	private boolean running;
	private boolean isPaused;
	
	// Visual components
	Image tetrisImage;
	Graphics tetrisG;
	
	private Thread animator;
	
	public MatrixPanel(Game game, Matrix matrix){
		super();
		this.matrix = matrix;
		this.game = game;
		
		running = false;
		isPaused = false;
		
		setBackground(Color.black);
		setPreferredSize(new Dimension(MWIDTH, MHEIGHT));
		setFocusable(true);
		requestFocus();
		setupKeys();
	}
	
	private void setupKeys() {
		addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(!matrix.isGameOver()){
				int keyCode = e.getKeyCode();
				
				if(keyCode == KeyEvent.VK_DOWN){
					currentShape.moveDown();
				}
				else if(keyCode == KeyEvent.VK_LEFT){
					currentShape.moveLeft();
				}
				else if(keyCode == KeyEvent.VK_RIGHT){
					currentShape.moveRight();
				}
				else if(keyCode == KeyEvent.VK_UP){
					currentShape.moveToBottom();
				}
				else if(keyCode == KeyEvent.VK_S){
					currentShape.rotateRight();
				}
				else if(keyCode == KeyEvent.VK_A){
					currentShape.rotateLeft();
				}
				
				matrix.update();
				refreshGraphics();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void gameRender(){
		if(tetrisImage == null){
			tetrisImage = createImage(MWIDTH, MHEIGHT);
			if(tetrisImage == null){
				System.out.println("tetrisImage is null");
				return;
			}
			tetrisG = tetrisImage.getGraphics();
		}
		
		// redraws the screen black
		tetrisG.setColor(Color.black);
		tetrisG.fillRect(0, 0, MWIDTH, MHEIGHT);
		
		// generates the current shape
		if(currentShape == null || currentShape.isAtBottom() && !matrix.isGameOver()){
			matrix.generateTetromino();
			currentShape = matrix.getCurrentShape();
		}
		
		// draws the screen
		draw();
		
		// draws the line
		tetrisG.setColor(Color.yellow);
		tetrisG.drawLine(0, BLOCK_SIZE * 2, MWIDTH, BLOCK_SIZE * 2);
		
	}
	
	private void drawLine() {
		// TODO Auto-generated method stub
		
	}

	private void draw() {
		Color[][] colors = matrix.getColorMatrix();
		
		for(int i = 0; i < colors.length; i++){
			for(int j = 0; j < colors[i].length; j++){
				tetrisG.setColor(colors[i][j]);
				tetrisG.fillRect(i*BLOCK_SIZE, j*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				//matrix[row][col] = Color.black;
			}
		}
	}

	public void paintScreen(){
		Graphics g;
		try{
			g = this.getGraphics();
			if((g != null) && (tetrisImage != null)){
				g.drawImage(tetrisImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		catch(Exception e){
			System.out.print("System graphics error: " + e);
		}
	}
	
	private void refreshGraphics(){
		gameUpdate();
		gameRender();
		paintScreen();
	}
	
	private void gameUpdate() {
				
		if(currentShape != null){
			currentShape.moveDown();
			matrix.update();
			if(currentShape.isAtBottom()){
				matrix.checkLineFilled();
			}
		}
	}
	
	public void run() {
		running = true;
		while(!matrix.isGameOver()){
			while(running){
				refreshGraphics();
						
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	// TODO: Figure out what the hell this does.
	@Override
	public void addNotify(){
		super.addNotify();
		startGame();
	}
	
	private void startGame(){
		if(animator == null || !running ){
			animator = new Thread(this);
			animator.setName("Animator");
			animator.start();
		}
	}	// end startGame()
	
	public void pauseGame(){
		isPaused = true;
	} // end pauseGame()
	
	public void resumeGame(){
		isPaused = false;
	}  // end resumeGame()
	
	public void stopGame(){
		running = false;
	}  // end stopGame()
}
