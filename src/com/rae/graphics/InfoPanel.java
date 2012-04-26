package com.rae.graphics;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InfoPanel extends JPanel{
	
	private int width;
	private int height;
	private int score;
	private JLabel levelLabel;
	private JLabel scoreLabel;
	
	public InfoPanel(){
		super();
		levelLabel = new JLabel("Level: 1 ");
		levelLabel.setFocusable(false);
		add(levelLabel);
		
		scoreLabel = new JLabel("0");
		scoreLabel.setFocusable(false);
		add(new JLabel("Score: "));
		add(scoreLabel);
	}
	
	public void addPoints(int points){
		score += points;
		scoreLabel.setText(this.score+"");
	}



}
