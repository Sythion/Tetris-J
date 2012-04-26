package com.rae.game.tetromino;


import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.rae.game.Matrix;
import com.rae.graphics.MatrixPanel;

public abstract class Tetromino {
	
	// TODO: Make into Enum
	public static final int LINE_TETROMINO = 0;
	public static final int SQUARE_TETROMINO = 1;
	public static final int Z_TETROMINO = 2;
	public static final int S_TETROMINO = 3;
	public static final int L_TETROMINO = 4;
	public static final int J_TETROMINO = 5;
	public static final int T_TETROMINO = 6;
	
	public static final int MAX_SHAPES = 7;
	public static final int MAX_COORDINATES = 4;
	
	private static final int SHAPE_UP = 0;
	private static final int SHAPE_LEFT = 1;
	private static final int SHAPE_DOWN = 2;
	private static final int SHAPE_RIGHT = 3;
	
	private int shapeType;
	private Color shapeColor;
	private static Matrix matrix;
	private Point origin;
	
	private Point[] coordinates;
	private boolean atBottom;

	public Tetromino(Matrix matrix) {
		Tetromino.matrix = matrix;
		atBottom = false;
		coordinates = new Point[MAX_COORDINATES];
	}
	
	public Color getColor(){
		return shapeColor;
	}
	
	public void setColor(Color color){
		this.shapeColor = color;
	}
	
	public void setShapeType(int shape){
		shapeType = shape;
	}
	
	public int getShapeType(){
		return shapeType;
	}

	public synchronized Point[] getCoordinates(){
		return coordinates;
	}
	
	public synchronized void setCoordinates(Point[] coordinates){
		this.coordinates = coordinates;
	}
	
	public boolean isAtBottom(){
		return atBottom;
	}
	
	
	public Point getLeftMostPoint(){
		int leftMostX = matrix.getWidth();
		int yCoordinate = 0;
		for(int i = 0; i < MAX_COORDINATES; i++){
			if(coordinates[i].x < leftMostX){
				leftMostX = coordinates[i].x;
				yCoordinate = coordinates[i].y;
			}
		}
		return new Point(leftMostX, yCoordinate);
	}
	
	public Point getRightMostPoint(){
		int rightMostX = 0;
		int yCoordinate = 0;
		for(int i = 0; i < MAX_COORDINATES; i++){
			if(coordinates[i].x > rightMostX){
				rightMostX = coordinates[i].x;
				yCoordinate = coordinates[i].y;
			}
		}
		return new Point(rightMostX, yCoordinate);
	}
	
	public Point getBottomMostPoint(){
		Point coordinate = new Point(0,0);
		int bottomMost = 0;
		for(int i = 0; i < MAX_COORDINATES; i++){
			if(coordinates[i].y > bottomMost){
				bottomMost = coordinates[i].y;
				coordinate = coordinates[i];
			}
		}
		return coordinate;
	}
	
	public void moveLeft(){
	
		Point coordinate = getLeftMostPoint();
		
		if(coordinate.x > 0 && !matrix.isCellFilled(coordinate.x-1, coordinate.y)){
			for(int i = 0; i < MAX_COORDINATES; i++){
				matrix.fillCell(coordinates[i].x, coordinates[i].y, Color.black);
				coordinates[i] = new Point(coordinates[i].x - 1, coordinates[i].y);
			}
			origin.x -= 1;
		}
	}
	
	public void moveRight(){
		
		Point coordinate = getRightMostPoint();
		
		if(coordinate.x < matrix.getWidth() - 1 && !matrix.isCellFilled(coordinate.x+1, coordinate.y)){
			for(int i = 0; i < MAX_COORDINATES; i++){
				matrix.fillCell(coordinates[i].x, coordinates[i].y, Color.black);
				coordinates[i] = new Point(coordinates[i].x + 1, coordinates[i].y);
			}
			origin.x += 1;
		}
	}
	
	public void moveDown(){
		
		if(!isAtBottom()){
		Color[][] colors = matrix.getColorMatrix();
		ArrayList<Point> validCoordinates = getBottomMostCoordinates();
		
		
		for(Point coordinate : validCoordinates){
			
			if(coordinate.y >= matrix.getHeight() - 1){
				setAtBottom(true);
			}
			else if(colors[coordinate.x][coordinate.y+1]!= Color.black){
				setAtBottom(true);
				break;
			}
		}

			if(!isAtBottom()){
				for(int i = 0; i < MAX_COORDINATES; i++){
					matrix.fillCell(coordinates[i].x, coordinates[i].y, Color.black);
					coordinates[i] = new Point(coordinates[i].x, coordinates[i].y + 1);
				}
				origin.y += 1;
			}
		}
	}
	
	protected ArrayList<Point> getBottomMostCoordinates() {
		
		ArrayList<Point> bottomCoordinates = new ArrayList<Point>();
		Point[] coordinates = getCoordinates();
		boolean coordCanBeAdded = true;
		
		for(int i = 0; i < coordinates.length; i++){
			coordCanBeAdded = true;
			Point tempCoordinate = (Point)coordinates[i].clone();
			tempCoordinate.y += 1;
			for(int j = 0; j < coordinates.length; j++){
				if(tempCoordinate.equals(coordinates[j])){
					coordCanBeAdded = false;
					break;
				}
			}
			
			for(Point coordinate: bottomCoordinates){
				if(coordinate.equals(coordinates[i])){
					coordCanBeAdded = false;
					break;
				}
			}
			if(coordCanBeAdded){
				bottomCoordinates.add(coordinates[i]);
			}
		}
		
		return bottomCoordinates;
	}

	public void setAtBottom(boolean atBottom){
		this.atBottom = atBottom;
	}
	
	public void moveToBottom(){
		
		while(!atBottom){
			moveDown();
		}
	}
	
	
	public synchronized void rotateLeft(){
		
		Point[] rotatedCoordinates = new Point[MAX_COORDINATES];
		
		for(int i = 0; i < MAX_COORDINATES; i++){
			
			// Translates current coordinate to be relative to (0,0)
			Point translationCoordinate = new Point(coordinates[i].x - origin.x, coordinates[i].y - origin.y);
			
			// Java coordinates start at 0 and increase as a point moves down, so
			// multiply by -1 to reverse
			translationCoordinate.y *= -1;
			
			// Clone coordinates, so I can use translation coordinates
			// in upcoming calculation
			rotatedCoordinates[i] = (Point)translationCoordinate.clone();
			
			// May need to round results after rotation
			rotatedCoordinates[i].x = (int)Math.round(translationCoordinate.x * Math.cos(Math.PI/2) - translationCoordinate.y * Math.sin(Math.PI/2)); 
			rotatedCoordinates[i].y = (int)Math.round(translationCoordinate.x * Math.sin(Math.PI/2) + translationCoordinate.y * Math.cos(Math.PI/2));
			
			// Multiply y-coordinate by -1 again
			rotatedCoordinates[i].y *= -1;
			
			// Translate to get new coordinates relative to
			// original origin
			rotatedCoordinates[i].x += origin.x;
			rotatedCoordinates[i].y += origin.y;
			
			// Erase the old coordinates by making them black
			matrix.fillCell(coordinates[i].x, coordinates[i].y, Color.black);
		}
		
		// TODO: Fix right side of matrix when rotating
		Point[] validatedCoordinates = validateCoordinates(rotatedCoordinates.clone());
			// Set new coordinates to be drawn on screen
			setCoordinates(validatedCoordinates.clone());
		//setCoordinates(rotatedCoordinates.clone());

	}
	private Point[] validateCoordinates(Point[] coordinates) {
		
		int maxX = getMostRightOutOfBoundsX(coordinates);
		int minX = getMostLeftOutOfBoundsX(coordinates);
		
		Point[] shiftedCoordinates = coordinates.clone();
		
		if(maxX > matrix.getWidth() - 1){
			shiftedCoordinates = shiftCoordinates(coordinates.clone(), maxX - (matrix.getWidth() - 1));
		}
		else if(minX < 0){
			shiftedCoordinates = shiftCoordinates(coordinates.clone(), minX);
		}
		
		return shiftedCoordinates;
		
	}
	
	// TODO: Possibly merge these two methods with leftMostPoint and rightMostPoint
	private int getMostLeftOutOfBoundsX(Point[] coordinates) {
		int minX = 0;
		for(Point coordinate : coordinates){
			if(coordinate.x < 0 && coordinate.x < minX){
				minX = coordinate.x;
			}
		}
		return minX;
	}

	private int getMostRightOutOfBoundsX(Point[] coordinates) {
		int maxX = matrix.getWidth() - 1;
		for(Point coordinate : coordinates){
			if(coordinate.x > matrix.getWidth() - 1 && 
					coordinate.x > maxX){
				maxX = coordinate.x;
			}
		}
		
		return maxX;
	}

	private Point[] shiftCoordinates(Point[] coordinates, int minWidth) {
		for(Point coordinate : coordinates){
			coordinate.x -= minWidth;
		}
		return coordinates;
	}

	public synchronized void rotateRight(){
		rotateLeft();
		rotateLeft();
		rotateLeft();
	}
	
	public void setOrigin(Point origin){
		this.origin = origin;
	}
	
	public Point getOrigin(){
		return origin;
	}
	
	public static Tetromino generateTetromino(Matrix matrix){
		Tetromino tetromino = null;
		//matrix = generatedMatrix;
		int shapeType = generateRandomInteger(Tetromino.MAX_SHAPES - 1);
		//shapeType = Tetromino.LINE_TETROMINO;
		switch(shapeType){
		case Tetromino.LINE_TETROMINO:
			
			tetromino = new LineTetromino(matrix);
			break;
		
		case Tetromino.SQUARE_TETROMINO:
			
			tetromino = new SquareTetromino(matrix);
			break;
			
		case Tetromino.Z_TETROMINO:
			
			tetromino = new ZTetromino(matrix);
			break;
			
		case Tetromino.S_TETROMINO:
			
			tetromino = new STetromino(matrix);
			break;
			
		case Tetromino.L_TETROMINO:

			tetromino = new LTetromino(matrix);
			break;
			
		case Tetromino.J_TETROMINO:
			
			tetromino = new JTetromino(matrix);
			break;
			
		case Tetromino.T_TETROMINO:
			
			tetromino = new TTetromino(matrix);
			break;
			
		default:
			System.out.println("Error when generating initial coordinates");
		}
		
		if(tetromino != null){
			tetromino.setShapeType(shapeType);
		}
		else{
			System.out.println("Error when setting shape type for tetromino");
		}
		
		return tetromino;
	}
	
	private static int generateRandomInteger(int maxInt) {
		
		Random random = new Random();
		return random.nextInt(maxInt);
	}
	
	
}
