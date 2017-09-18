package me.joshferrell.samkatz.math.GUI;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import me.joshferrell.samkatz.math.Main;

public class InfoFrame extends JFrame implements WindowListener {
	
	private HashMap<String, ExpressionBuilder> integrations;
	private HashMap<String, JLabel> labels = new HashMap<String, JLabel>();
	private InfoUpdateThread update;
	private double lowerBound;
	private double upperBound;
	private int precision;
	private InfoFrame instance;
	
	public InfoFrame(String initMessage, int initPrecision, HashMap<String, ExpressionBuilder> integrations, double lowerBound, double upperBound){
		
		super("Graph Info: precision level " + initPrecision);
		//System.out.println("Graph info begin!");
		this.instance = this;
		this.integrations = integrations;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.precision = initPrecision;
		
		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		listPane.add(new JLabel(initMessage));
		for(String s : integrations.keySet()){
			try {
				
				double value = Main.integrateThis(lowerBound, upperBound, initPrecision, integrations.get(s));
				JLabel label = new JLabel(s + value);
				listPane.add(label);
				labels.put(s, label);
				
				
				update = new InfoUpdateThread();
				update.start();
				
			} catch (UnknownFunctionException e) {
				e.printStackTrace();
			} catch (UnparsableExpressionException e) {
				e.printStackTrace();
			}
			
		}
		
		this.add(listPane);
		
		
		setVisible(true);
		
		this.addWindowListener(this);
		
		this.pack();
		
		//System.out.println("Graph info end!");
		
	}
	final int threshold = Integer.MAX_VALUE / 2;
	public class InfoUpdateThread extends Thread{
		
		private boolean end = false;
		
		public void run(){
			while(!end){
				 precision *= 2;
				 if(precision > threshold){
					 end = true;
				 }
				 instance.setTitle("Graph Info: precision level " + precision);
				 for(String labelText : labels.keySet()){
					 JLabel label = labels.get(labelText);
					 //System.out.println("updating label " + label.getText());
					 
					 
					 
					 try {
						label.setText(labelText + Main.integrateThis(lowerBound, upperBound, precision, integrations.get(labelText)));
					 } catch (UnknownFunctionException e) {
						e.printStackTrace();
					 } catch (UnparsableExpressionException e) {
						e.printStackTrace();
					 }
					 //System.out.println("updated label to" + label.getText());
				 }
			}
		}
		
		public void cancel(){
			end = true;
		}
		
	}
	
	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}
	
	//assorted unused stuff
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void windowClosing(WindowEvent arg0) {
		this.update.cancel();
		System.out.println("Cancelling update task");
		this.dispose();
		
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
	
	
	
}
