package test;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import com.rae.game.Game;
import com.rae.game.Matrix;
import com.rae.game.tetromino.Tetromino;
import com.rae.graphics.MatrixPanel;

public class TestCases {
	
	private Matrix matrix;
	//private Tetromino tetromino;
	//private MatrixPanel matrixPanel;
	private Game game;
	
	@Before
	public void setUp(){
		game = new Game();
		matrix = new Matrix(10, 22, null);
		//matrixPanel = new MatrixPanel(game, matrix);
	}
	
	/**
	 * Tetromino
	 */
	
	// jrae - 2/25/2012
	@Test
	public void testGenerateTetromino(){
		Boolean result = false;
		Object o = Tetromino.generateTetromino(matrix);
		if(o != null && o instanceof Tetromino){
			result = true;
		}
		assertEquals(true, result);
	}
	/**
	 * Preview
	 */
	/*@Test
	public void testGeneratePreview(){
		Tetromino tetromino = Tetromino.generateTetromino(matrix);
		Preview preview = new Preview(tetromino);
		assertEquals(tetromino, preview.getTetromino());
	}*/
	
	@Test
	public void testSize(){
		
	}
	
	@Test
	public void testColor(){
		
	}
	

	
}
