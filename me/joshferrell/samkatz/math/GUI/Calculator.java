package me.joshferrell.samkatz.math.GUI;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import me.joshferrell.samkatz.math.threedimensions.OneFunc3D;
import me.joshferrell.samkatz.math.threedimensions.TwoFunc3D;
import me.joshferrell.samkatz.math.twodimensions.OneFunc2D;
import me.joshferrell.samkatz.math.twodimensions.TwoFunc2D;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class Calculator extends JFrame implements ActionListener {
	
    public static ArrayList<JButton> buttonList = new ArrayList<JButton>();
    
    public static JTextArea currentDisplay;
    JRadioButton dispSelect1 = new JRadioButton("Y=");
    JRadioButton dispSelect2 = new JRadioButton("Y=");
    
    JCheckBox dispCheck1 = new JCheckBox("Graph?");
    JCheckBox dispCheck2 = new JCheckBox("Graph?");
    
    static double precision = 0.01;
    static double speed = 0.2;
    static boolean antiAliasing = true;
    
    JButton graphButton = new JButton("Graph");
    JButton graphOptionsButton = new JButton("Graphing Options");
    JTextArea lowerBoundAreaX = new JTextArea("lower bound (x)");
    JTextArea upperBoundAreaX = new JTextArea("upper bound (x)");
    
    JTextArea axisEquation = new JTextArea();
    String[] axisOptions = {"Y=", "X="};
    JComboBox<String> axisCombo = new JComboBox<String>(axisOptions);
    JCheckBox axisCheck = new JCheckBox("Rotate equation(s) about axis ");
    
    private static String seperator;
    private static char pi = '\u03C0';
    
    JPanel[] row = new JPanel[9];
    JButton[] button;
    String[] buttonString = {"1", "2", "3", "+", "C", "sin(", "asin(", "e",
    						 "4", "5", "6", "-", "^", "cos(", "acos(", "π",
    						 "7", "8", "9", "*", "(", "tan(", "atan(", "X",
                             ".", "0", "/", "^", ")", "ln( ", "log( ", "_"
                             
                             };
    
    /*int[] dimW = {300,45,100,90};
    int[] dimH = {35, 40};*/
    Dimension displayDimension = new Dimension(300, 20);
    Dimension regularDimension = new Dimension(45, 40);
    Dimension column6Dimension = new Dimension(100, 40);
    JTextArea display = new JTextArea(1,20);
    JTextArea display2 = new JTextArea(1,20);
    Font font = new Font("Courier New", Font.BOLD, 14);
    
    public Calculator(){
    	super("Calculator");
    	
    	seperator = System.getProperty("line.separator");
    	
    	buttonList.clear();
    	
    	currentDisplay = display;
    	dispSelect1.setSelected(true);
    	dispCheck1.setSelected(true);
    	dispSelect1.addActionListener(this);
    	dispSelect2.addActionListener(this);
    	dispCheck1.addActionListener(this);
    	dispCheck2.addActionListener(this);
    	
    	graphButton.addActionListener(this);
    	graphButton.setPreferredSize(new Dimension(75, 20));
    	{
	    	lowerBoundAreaX.addMouseListener(new MouseListener(){
	
				@Override
				public void mouseClicked(MouseEvent e) {
					lowerBoundAreaX.setText("");
					
				}
	
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					lowerBoundAreaX.setText("");
					
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
	    		
	    	});
	    	upperBoundAreaX.addMouseListener(new MouseListener(){
	
				@Override
				public void mouseClicked(MouseEvent e) {
					upperBoundAreaX.setText("");
					
				}
	
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					upperBoundAreaX.setText("");
					
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
	    		
	    	});


			
    		
    
    	}
    	lowerBoundAreaX.setPreferredSize(new Dimension(85, 20));
    	upperBoundAreaX.setPreferredSize(new Dimension(85, 20));
    	
    	axisCombo.addActionListener(this);
    	axisCheck.addActionListener(this);
    	axisEquation.setPreferredSize(new Dimension(45, 20));
    	
    	axisEquation.setEnabled(false);
    	axisCombo.setEnabled(false);
    	
    	graphOptionsButton.setPreferredSize(new Dimension(140, 20));
    	graphOptionsButton.addActionListener(this);
    	
    	new CalButton("1", 45);new CalButton("2", 45);new CalButton("3", 45);new CalButton("+", 45);
    	new CalButton("C", 45){
    		public void actionPerformed(ActionEvent ae){
    			currentDisplay.setText("");
    		}
    	};
    	new CalButton("sin(", 60);new CalButton("asin(", 70);new CalButton("e", 55);//end row 1
    	
    	char pi = '\u03C0';
    	
    	new CalButton("4", 45);new CalButton("5", 45);new CalButton("6", 45);new CalButton("-", 45);new CalButton("^", 45);
    	new CalButton("cos(", 60);new CalButton("acos(", 70);new CalButton("" + pi, 55);//end row 2
    	
    	new CalButton("7", 45);new CalButton("8", 45);new CalButton("9", 45);new CalButton("*", 45);new CalButton("(", 45);
    	new CalButton("tan(", 60);new CalButton("atan(", 70);new CalButton("X", 55);//end row 3
    	
    	new CalButton(".", 45);new CalButton("0", 45);new CalButton("/", 45);new CalButton("^", 45);new CalButton(")", 45);
    	new CalButton("abs(", 60);new CalButton("log(", 70);new CalButton("ln(", 55);//end row 4
    	
    	new CalButton(" ", 45, "");new CalButton(" ", 45, "");new CalButton(" ", 45, "");new CalButton(" ", 45, "");
    	final String s = seperator;
    	new CalButton(" ", 45, "");new CalButton("help", 60){
    		public void actionPerformed(ActionEvent ae){
    			
    			JOptionPane.showMessageDialog(this, "<Controls for 2D graph view>" + s +
    												"W- move upward  S- move downward  A- move left" + s +
    												"D- move right   Q- zoom in        W- zoom out" + s +
    												"<Controls for 3D graph view>" + s +
    												"W- move forward S- move backward  A- move left" + s +
    												"D- move right   Q- move upward    W- move downward" + s +
    												"<FAQ>" + s+
    												"Q: Why do the numbers for area under the curve and volume of rotation keep changing?" + s +
    												"A: The more accurate we make the integration that determines those values, the longer it takes." + s +
    												"So, the program starts out displaying a somewhat accurate answer, and slowly increases the precision of" + s +
    												"the answer over time." + s + 
    												"Q: What do the precision and speed values in the graphing options menu control?" + s +
    												"A: The precision value controls how precise the graphing of the functions are. It does not control" + s +
    												"the precision of the volume/area calculations. Speed controls how fast the camera moves in the graph" + s +
    												"view."
    												
    										  );
    		}
    	};
    	
    	new CalButton("about", 70, ""){
    		public void actionPerformed(ActionEvent ae){
    			JOptionPane.showMessageDialog(this, "JRotation Helper v1.0" + seperator + "This program was created by Joshua Ferrell and Samuel Katz in August of 2014 to assist Math students in visualizing the intersection of functions and the solids of rotations of functions.");
    		}
    	}; new CalButton(" ", 55, "");
    	
        //button = new JButton[buttonString.length];
        setDesign();
        setSize(700, 300);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(9,6);
        setLayout(grid);
        
        
        FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
        FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,1,1);
        for(int i = 0; i < row.length; i++)
            row[i] = new JPanel();
        row[0].setLayout(f1);
        row[1].setLayout(f1);
        for(int i = 0; i < row.length; i++)
            row[i].setLayout(f2);
        
        display.setFont(font);
        display.setEditable(true);
        display.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        display.setPreferredSize(displayDimension);
        display2.setFont(font);
        display2.setEditable(true);
        display2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        display2.setPreferredSize(displayDimension);
        /*for(int i = 0; i < button.length; i++)
            button[i].setPreferredSize(regularDimension);
        /*for(int i = 14; i < 18; i++)
            button[i].setPreferredSize(rColumnDimension);*/
        
        row[0].add(dispSelect1);
        row[0].add(display);
        row[0].add(dispCheck1);
        
        row[1].add(dispSelect2);
        row[1].add(display2);
        row[1].add(dispCheck2);
        
        row[2].add(axisCheck);
        row[2].add(axisCombo);
        row[2].add(axisEquation);
        
        row[3].add(lowerBoundAreaX);
        row[3].add(upperBoundAreaX);
        row[3].add(graphOptionsButton);
        row[3].add(graphButton);
        
        add(row[0]);
        add(row[1]);
        add(row[2]);
        add(row[3]);
        
        
        int width = 8;
        for(int i = 0; i < buttonList.size(); i++){
        	row[(i/width) + 4].add(buttonList.get(i));
        	
        }
        for(JPanel r : row){
        	add(r);
        }
        
        setVisible(true);
    }
    
    
    public void getResult(){
    	ExpressionBuilder equation = new ExpressionBuilder(display.getText());
    	try {
			Calculable calc = equation.build();
			display.setText(calc.calculate() + "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
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
    	if(ae.getSource() == graphButton){
    		
    		
    		boolean disp1error = false;
    		if(dispCheck1.isSelected()){
    			try{
    				if(axisCheck.isSelected()){
	    				if(axisCombo.getSelectedItem().equals("Y=")){
	    					(new ExpressionBuilder(parseExp4j(display.getText().toLowerCase()))).withVariable("x", 0).build().calculate();
	    				}else{
	    					(new ExpressionBuilder(parseExp4j(display.getText().toLowerCase()))).withVariable("y", 0).build().calculate();
	    				}
    				}
    				else{
    					(new ExpressionBuilder(parseExp4j(display.getText().toLowerCase()))).withVariable("x", 0).build().calculate();
    				}
    			}catch(ArithmeticException e){
    				//e.printStackTrace();
    			}catch(Exception e){
    				display.setBackground(Color.RED);
    				disp1error = true;
    			}
    		}
    		boolean disp2error = false;
    		if(dispCheck2.isSelected()){
    			try{
    				if(axisCheck.isSelected()){
	    				if(axisCombo.getSelectedItem().equals("Y=")){
	    					(new ExpressionBuilder(parseExp4j(display2.getText().toLowerCase()))).withVariable("x", 5).build().calculate();
	    				}else{
	    					(new ExpressionBuilder(parseExp4j(display2.getText().toLowerCase()))).withVariable("y", 5).build().calculate();
	    				}
    				}
    				else{
    					(new ExpressionBuilder(parseExp4j(display2.getText().toLowerCase()))).withVariable("x", 5).build().calculate();
    				}
    			}catch(ArithmeticException e){
    				//e.printStackTrace();
    			}catch(Exception e){
    				display2.setBackground(Color.RED);
    				disp2error = true;
    			}
    		}
    		boolean axiserror = false;
    		if(axisCheck.isSelected()){
    			try{
    				Float.parseFloat(axisEquation.getText());
    			}catch(Exception e){
    				axisEquation.setBackground(Color.RED);
    				axiserror = true;
    			}
    		}
    		boolean uBoundError = false;
    		boolean lBoundError = false;
    		try{
    		   Float.parseFloat(lowerBoundAreaX.getText());
    		}catch(NumberFormatException e){
    		   lBoundError = true;
    		}try{
    			Float.parseFloat(upperBoundAreaX.getText());
    		}catch(NumberFormatException e){
    		   uBoundError = true;
    		}try{
    			double upper = Float.parseFloat(upperBoundAreaX.getText());
    			double lower = Float.parseFloat(lowerBoundAreaX.getText());
    			if(lower >= upper){
    				uBoundError = true;
    				lBoundError = true;
    			}
    			
    		}catch(NumberFormatException e){}
    		
    		
    		
    		if(uBoundError){
    			upperBoundAreaX.setBackground(Color.RED);
    		}else{
    			upperBoundAreaX.setBackground(Color.WHITE);
    		}
    		if(lBoundError){
    			lowerBoundAreaX.setBackground(Color.RED);
    		}else{
    			lowerBoundAreaX.setBackground(Color.WHITE);
    		}
    		if(!disp1error)display.setBackground(Color.WHITE);
    		if(!disp2error)display2.setBackground(Color.WHITE);
    		if(!axiserror) axisEquation.setBackground(Color.WHITE);
    		
    		
    		
    		
    		if(disp1error || disp2error || axiserror || uBoundError || lBoundError) return;
    		if(display.getText().equals("") && display2.getText().equals("")){
    			return;
    		}
    		
    		
    		if(axisCheck.isSelected()){
    			if(dispCheck1.isSelected() && dispCheck2.isSelected()){
    				//3d, 2
    				
    				
    		        Thread infoThread = new Thread(){
    		        	public void run(){
    		        		new TwoFunc3D(new ExpressionBuilder(parseExp4j(display.getText())), new ExpressionBuilder(parseExp4j(display2.getText())), precision, speed, Float.parseFloat(lowerBoundAreaX.getText()), Float.parseFloat(upperBoundAreaX.getText()), axisCombo.getSelectedItem().equals("X="), Float.parseFloat(axisEquation.getText())).start();
    		        	}
    		        };
    				
    		        infoThread.start();
    				
    				
    			}
    			else if(dispCheck1.isSelected() || dispCheck2.isSelected()){
    				final JTextArea one;
    				if(dispCheck1.isSelected()) one = display;
    				else one = display2;
    				//3d, 1
    				
    				final HashMap<String, ExpressionBuilder> labels = new HashMap<String, ExpressionBuilder>();
    				ExpressionBuilder vol = new ExpressionBuilder(Math.PI + "*(" + parseExp4j(display.getText()) + ")^2");
    				
    				labels.put("Volume of solid of rotation: ", vol);
    				
    				new InfoFrame("Graph of the solid of revolution of the equation " + one.getText() + " in the bounds (" + Float.parseFloat(lowerBoundAreaX.getText()) + ", " + Float.parseFloat(upperBoundAreaX.getText()) + ")",
    						20, labels, Float.parseFloat(lowerBoundAreaX.getText()), Float.parseFloat(upperBoundAreaX.getText()));
    				
    		        Thread infoThread = new Thread(){
    		        	public void run(){
    		        		new OneFunc3D(new ExpressionBuilder(parseExp4j(one.getText())), precision, speed, Float.parseFloat(lowerBoundAreaX.getText()), Float.parseFloat(upperBoundAreaX.getText()), axisCombo.getSelectedItem().equals("X="), Float.parseFloat(axisEquation.getText())).start();
    		        	}
    		        };
    				
    		        infoThread.start();
    				
    				
    				
    			}
    		}else{
    			if(dispCheck1.isSelected() && dispCheck2.isSelected()){
    				//2d, 2
    				//showing area between two functions
    				final HashMap<String, ExpressionBuilder> labels = new HashMap<String, ExpressionBuilder>();
    				ExpressionBuilder area = new ExpressionBuilder("abs((" + parseExp4j(display.getText()) + ") - (" + parseExp4j(display2.getText()) +"))");
    				
    				labels.put("Area between the two curves: ", area);
    				
    				new InfoFrame("Graph of the equation " + display.getText() + " and the equation " + display2.getText() + " in the bounds (" + Float.parseFloat(lowerBoundAreaX.getText()) + ", " + Float.parseFloat(lowerBoundAreaX.getText()) + ")",
    						20, labels, Float.parseFloat(lowerBoundAreaX.getText()), Float.parseFloat(upperBoundAreaX.getText()));
    				
    		        Thread infoThread = new Thread(){
    		        	public void run(){
    		        		new TwoFunc2D(new ExpressionBuilder(parseExp4j(display.getText())), new ExpressionBuilder(parseExp4j(display2.getText())), precision, speed, Float.parseFloat(lowerBoundAreaX.getText()), Float.parseFloat(upperBoundAreaX.getText())).start();
    		        	}
    		        };
    				
    		        infoThread.start();
    				
    				
    				
    			
    			
    			}
    			else if(dispCheck1.isSelected() || dispCheck2.isSelected()){
    				final JTextArea one;
    				if(dispCheck1.isSelected()) one = display;
    				else one = display2;
    				//2d, 1
    				//showing area under the curve
    				final HashMap<String, ExpressionBuilder> labels = new HashMap<String, ExpressionBuilder>();
    				ExpressionBuilder area = new ExpressionBuilder("abs(" + parseExp4j(one.getText()) + ")");
    				
    				labels.put("Area under the curve: ", area);
    				
    				new InfoFrame("Graph of the equation " + one.getText() + " in the bounds (" + Float.parseFloat(lowerBoundAreaX.getText()) + ", " + Float.parseFloat(lowerBoundAreaX.getText()) + ")",
    						20, labels, Float.parseFloat(lowerBoundAreaX.getText()), Float.parseFloat(upperBoundAreaX.getText()));
    				
    		        Thread infoThread = new Thread(){
    		        	public void run(){
    		        		new OneFunc2D(new ExpressionBuilder(parseExp4j(one.getText())), precision, speed, Float.parseFloat(lowerBoundAreaX.getText()), Float.parseFloat(upperBoundAreaX.getText())).start();
    		        	}
    		        };
    				
    		        infoThread.start();
    		        
    		        
    				
    				
    				
    			}
    		}
    		
    	}else if(ae.getSource() == graphOptionsButton){
    		//System.out.print("options");
    		new GraphOptions();
    	}else if(ae.getSource() instanceof JRadioButton){
        	if(ae.getSource() == dispSelect1){
        		dispSelect1.setSelected(true);
        		currentDisplay = display;
        		dispSelect2.setSelected(false);
        	}else if(ae.getSource() == dispSelect2){
        		dispSelect2.setSelected(true);
        		currentDisplay = display2;
        		dispSelect1.setSelected(false);
        	}
        }else if(ae.getSource() instanceof JCheckBox){
        	if(ae.getSource() == axisCheck){
        		if(axisCheck.isSelected()){
        			axisEquation.setEnabled(true);
        			axisCombo.setEnabled(true);
        		}else{
        			axisEquation.setEnabled(false);
        			axisCombo.setEnabled(false);
        			
        			axisCombo.setSelectedIndex(0);
        			dispSelect1.setText((String) axisCombo.getSelectedItem());
    				dispSelect2.setText((String) axisCombo.getSelectedItem());
        		}
        	}
        }else if(ae.getSource() instanceof JComboBox){
        	if(ae.getSource() == axisCombo){
        		/*for(String option : axisOptions){
        			if(!option.equals((String) axisCombo.getSelectedItem())){
        				dispSelect1.setText(option);
        				dispSelect2.setText(option);
        			}
        		}*/
        		dispSelect1.setText((String) axisCombo.getSelectedItem());
				dispSelect2.setText((String) axisCombo.getSelectedItem());
        		
				for(JButton jb : Calculator.buttonList){
					if(jb.getText().equals("X") || jb.getText().equals("Y")){
						for(String option : axisOptions){
		        			if(!option.equals((String) axisCombo.getSelectedItem())){
		        				jb.setText(option.substring(0,1));
		        			}
	        			}
					}
				}
				
        	}
        }
    
    }
    
    public static String parseExp4j(String input){
    	input = input.toLowerCase();
    	input = input.replaceAll(" ", "");
    	input = input.replaceAll("\\[", "(");
    	input = input.replaceAll("\\]", ")");
    	input = input.replaceAll("\\{", "(");
    	input = input.replaceAll("\\}", ")");
    	
    	input = input.replaceAll("e", "(" + Math.E + ")");
    	input = input.replaceAll(pi +"", "(" + Math.PI + ")");
    	
    	input = input.replaceAll("log\\(", "logten(");
    	for(int i = 0; i < input.length() - 1; i++){
    		if(input.substring(i, i+1).matches("[a-z]") && input.substring(i+1, i+2).matches("[\\d]")
    		|| input.substring(i, i+1).matches("[  \\d]") && input.substring(i+1, i+2).matches("[a-z]")
    		
    		|| input.substring(i, i+1).matches("[\\d]") && input.substring(i+1, i+2).matches("[\\(]")
    		|| input.substring(i, i+1).matches("[x]") && input.substring(i+1, i+2).matches("[\\(]")
    		
    		|| input.substring(i, i+1).matches("[\\)]") && input.substring(i+1, i+2).matches("[a-z]")
    		|| input.substring(i, i+1).matches("[\\)]") && input.substring(i+1, i+2).matches("[\\d]")
    		
    		|| input.substring(i, i+1).matches("[\\)]") && input.substring(i+1, i+2).matches("[\\(]")
    		
    		|| input.substring(i, i+1).matches("[x]") && input.substring(i+1, i+2).matches("[x]")){
    			input = input.substring(0, i+1) + "*" + input.substring(i+1, input.length());
    		}
    	}
    	
    	
    	input = input.replaceAll("ln\\(", "log\\(");
    	return input;
    }
    
    
    
    
}

