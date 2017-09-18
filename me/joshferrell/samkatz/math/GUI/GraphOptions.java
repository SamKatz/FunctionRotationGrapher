package me.joshferrell.samkatz.math.GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class GraphOptions extends JFrame implements ActionListener {
	JPanel main;
	JLabel precisionlabel;
	JTextArea precision;
	JLabel speedlabel;
	JTextArea speed;
	JButton saveButton;
	JLabel errLabel;
	
	JCheckBox aaCheck;
	
	public GraphOptions(){
		super("Graphing Options");
		
	    main = new JPanel();
		precisionlabel = new JLabel("Precision:");
		precision = new JTextArea("" + Calculator.precision);
		speedlabel = new JLabel("Speed:");
		speed = new JTextArea("" + Calculator.speed);
		saveButton = new JButton("Save");
		errLabel = new JLabel("");
		aaCheck = new JCheckBox("Anti-Aliasing");
		
		
		saveButton.addActionListener(this);
		main.add(precisionlabel);
		main.add(precision);
		
		main.add(speedlabel);
		main.add(speed);
		
		main.add(aaCheck);
		aaCheck.setSelected(Calculator.antiAliasing);
		
		main.add(saveButton);
		main.add(errLabel);
	
		
		this.add(main);
		this.pack();
		
		setDesign();
        setSize(350, 100);
        setResizable(false);
        
        //setDefaultCloseOperation();
       
        setVisible(true);
	}
	
	public final void setDesign() {
        try {
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception e) {   
        }
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == saveButton){
			try{
				Calculator.speed = Double.parseDouble(speed.getText());
				Calculator.precision = Double.parseDouble(precision.getText());
				Calculator.antiAliasing = aaCheck.isSelected();
				errLabel.setText("");
				this.dispose();
			}catch(NumberFormatException e){
				errLabel.setText("Invalid Input");
				
			}
		}
		
	}
}
