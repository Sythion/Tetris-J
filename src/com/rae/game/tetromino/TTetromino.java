package com.rae.game.tetromino;

import java.awt.Color;
import java.awt.Point;

import com.rae.game.Matrix;


public class TTetromino extends Tetromino {
	
	public TTetromino(Matrix matrix) {
		super(matrix);
		
		Point[] coordinates = new Point[MAX_COORDINATES];
		int matrixWidth = matrix.getWidth();
		
		coordinates[0] = new Point(matrixWidth/2 - 1, 1);
		coordinates[1] = new Point(matrixWidth/2, 1);
		coordinates[2] = new Point(matrixWidth/2 + 1, 1);
		coordinates[3] = new Point(matrixWidth/2, 0);
		
		setColor(Color.gray);
		setCoordinates(coordinates);
		setOrigin(new Point(matrixWidth/2, 1));
		setShapeType(T_TETROMINO);
	}
}
