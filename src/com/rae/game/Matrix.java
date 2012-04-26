package com.rae.game;


import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.rae.game.tetromino.Tetromino;

public class Matrix {
	
	private int width, height;
	private Color[][] matrix;
	private Tetromino currentShape;
	//private TetrominoFactory tetrominoFactory;
	//private static final int BLOCK_SIZE = 10;
	private Game game;
	private boolean gameOver = false;

	public Matrix(int width, int height, Game game) {
		this.width = width;
		this.height = height;
		this.game = game;
		generateMatrix();
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public boolean isGameOver(){
		return gameOver;
	}
	
	public Color[][] getColorMatrix(){
		return matrix;
	}
	
	private void generateMatrix(){
		matrix = new Color[width][height];
		
		for(int row = 0; row < width; row++){
			for(int col = 0; col < height; col++){
				matrix[row][col] = Color.black;
			}
		}
	}
	
	public void fillCell(int row, int col, Color color){
		matrix[row][col] = color;
	}
	
	public void eraseCell(int row, int col){
		matrix[row][col] = Color.black;
	}
	
	
	public boolean isCellFilled(int row, int col){
		if(matrix[row][col] == Color.black){
			return false;
		}
		else{
			return true;
		}
	}

	public void checkLineFilled(){
		
		ArrayList<Integer> columns = new ArrayList<Integer>();
		int filledCount = 0;
		for(int col = height - 1; col >= 0; col--){
			for(int row = 0; row < width; row++){
				if(matrix[row][col] != Color.black){
					filledCount++;
				}
			}
			if(filledCount == width && currentShape.isAtBottom()){
				// Increase score.
				game.addPoints();
				
				columns.add(col);
			}
			// reset filled count
			filledCount = 0;
		}
		if(!columns.isEmpty()){
			Collections.sort(columns);
			eraseLines(columns);
		}
	}
	
	private void eraseLines(ArrayList<Integer> columns){
		for(int col : columns){
			for(int row = 0; row < width; row++){
				  matrix[row][col] = Color.black;
			}
		}
		dropBlocks(columns);
	}
	
	
	public boolean isMatrixFull(){
		
		int filledCount = 0;
		
		for(int row = 0; row < width; row++){
			for(int col = 0; col < height; col++){
				if(matrix[row][col] != Color.black){
					filledCount++;
				}
			}
		}
		
		if(filledCount == (width * height)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void generateTetromino(){
		//tetrominoFactory = new TetrominoFactory(this);
		currentShape = Tetromino.generateTetromino(this);
		
		Point[] coordinates = currentShape.getCoordinates();
		Color color = currentShape.getColor();
		
		for(int i = 0; i < coordinates.length; i++){
			if(!isCellFilled(coordinates[i].x, coordinates[i].y)){
				fillCell(coordinates[i].x, coordinates[i].y, color);
			}
			else{
				gameOver = true;
			}
		}
	}
	
	public void update(){
		
		Point[] coordinates = currentShape.getCoordinates();
		Color shapeColor = currentShape.getColor();
		
		for(int i = 0; i < coordinates.length; i++){
				fillCell(coordinates[i].x, coordinates[i].y, shapeColor);
		}
	}
	
	public Tetromino getCurrentShape(){
		return currentShape;
	}
	
	private void dropBlocks(ArrayList<Integer> columns){
		
		for(int column : columns){
			for(int col = column; col >= 1; col--){
				for(int row = 0; row < width; row++){
					matrix[row][col] = matrix[row][col-1];
				}
			}
		}
		
	}
}
