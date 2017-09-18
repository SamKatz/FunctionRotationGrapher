package me.joshferrell.samkatz.math.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CalButton extends JButton implements ActionListener {
	Font font = new Font("Times new Roman", Font.BOLD, 14);
	String outputText;
	
	public CalButton(String label, int width, String outputText){
		super();
		this.outputText = outputText;
		Calculator.buttonList.add(this);
		Dimension d = new Dimension(width, 30);
		this.setPreferredSize(d);
		this.setText(label);
		this.setFont(font);
		this.addActionListener(this);
	}
	
	public CalButton(String label, int width){
		this(label, width, label);
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		Calculator.currentDisplay.append(outputText);
		
	}
}
