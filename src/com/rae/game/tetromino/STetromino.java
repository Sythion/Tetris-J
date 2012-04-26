package com.rae.game.tetromino;

import java.awt.Color;
import java.awt.Point;

import com.rae.game.Matrix;


public class STetromino extends Tetromino {

	public STetromino(Matrix matrix) {
		super(matrix);
		
		Point[] coordinates = new Point[MAX_COORDINATES];
		int matrixWidth = matrix.getWidth();
		
		coordinates[0] = new Point(matrixWidth/2 - 1, 1);
		coordinates[1] = new Point(matrixWidth/2, 1);
		coordinates[2] = new Point(matrixWidth/2, 0);
		coordinates[3] = new Point(matrixWidth/2 + 1, 0);
		
		setColor(Color.blue);
		setCoordinates(coordinates);
		setOrigin(new Point(matrixWidth/2, 1));
		setShapeType(S_TETROMINO);
	}
}
