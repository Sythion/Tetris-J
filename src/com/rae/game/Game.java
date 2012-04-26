/**
 * 	Created by: Jason Rae
 *  9/15/2011
 *  Main Tetris Screen
 */

// TODO: Fix proportionment of graphical components

package com.rae.game;


import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import com.rae.graphics.InfoPanel;
import com.rae.graphics.MatrixPanel;

public class Game extends JFrame implements WindowListener{
	
	// Logical components
	private int WIDTH = 400;
	private int HEIGHT = 600;
	private Matrix matrix;
	private boolean gameOver = false;
	private int points = 100;
	
	// Visual Components
	private MatrixPanel mp;
	private InfoPanel ip;;
	
	public Game(){
		super("Tetris");
		init();
		
		addWindowListener(this);
		pack();
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void init(){
		
		Container c = getContentPane();
		
		matrix = new Matrix(10, 22, this);
		mp = new MatrixPanel(this, matrix);
		c.add(mp, "Center");
		
		ip = new InfoPanel();
		ip.setLayout(new BoxLayout(ip, BoxLayout.Y_AXIS));
		
		c.add(ip, "West");
	}
	
	public void addPoints(){
		ip.addPoints(points);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args){
		new Game();
	}
}
