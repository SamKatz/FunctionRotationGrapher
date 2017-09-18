package me.joshferrell.samkatz.math;
import me.joshferrell.samkatz.math.GUI.Calculator;
import me.joshferrell.samkatz.math.threedimensions.TwoFunc3D;
import me.joshferrell.samkatz.math.twodimensions.TwoFunc2D;
import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.InvalidCustomFunctionException;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;


public class Main {
	//new PixelFormat(0, 8, 0, 4)
	//parameters: 0 alpha bits, 8 depth bits, 0 stencil bits, 4 samples
	
	public static CustomFunction logten;
	
	public static void main(String[] args) {
		
		
		
		try {
			logten = new CustomFunction("logten"){
				public double applyFunction(double... values){
					return Math.log10(values[0]);
				}
			};
		} catch (InvalidCustomFunctionException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(integrateThis(0,100,320000, new ExpressionBuilder("100*cos(0.001*x)")));
		} catch (UnknownFunctionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnparsableExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		new Calculator();
		
		//TwoFunc2D g = new TwoFunc2D(new ExpressionBuilder("x"), new ExpressionBuilder("x*x"), 0.01f, 0.1f, -10f, 10f);
		//g.start();
		//TwoFunc3D g = new TwoFunc3D(new ExpressionBuilder("y^2"), new ExpressionBuilder("y"), 0.01f, 0.1f, -10f, 10f, 2.0f, true);
		//g.start();
		//OneFunc3D g = new OneFunc3D(new ExpressionBuilder("x"), 0.01f, 0.1f, -10f, 10f, false, 5.0f);
		//g.start();
		//OneFunc2D g = new OneFunc2D(new ExpressionBuilder("x"), 0.01f, 0.1f, -10f, 10f);
		//g.start();
		//TwoFunc2D g = new TwoFunc2D(new ExpressionBuilder("x"), new ExpressionBuilder("x^2"), 0.01f, 0.1f, -10f, 10f);
		//g.start();
	}
	
	public static double integrateThis(double a,double b,int n, ExpressionBuilder eb) throws UnknownFunctionException, UnparsableExpressionException{
           //by increasing the value of n we can reduce the error of our approximation.
        if(n%2 == 1) n--;       //  n must be an even number. This step assures that condition.
        double h=(double) (b-a)/n;
        double term= (double) eb.withVariable("x", a).withVariable("y", a).withCustomFunction(Main.logten).build().calculate();
        
        for(int j=1;j<n;j++){
            if(j%2==1){
                term+=4*eb.withVariable("x", a+h*j).withVariable("y", a+h*j).withCustomFunction(Main.logten).build().calculate();
            }else{
                term+=2*eb.withVariable("x", a+h*j).withVariable("y", a+h*j).withCustomFunction(Main.logten).build().calculate();
            }
        }
        term+=eb.withVariable("x", a+h*n).withVariable("y", a+h*n).withCustomFunction(Main.logten).build().calculate();

        return (h/3)*term ;
    }

}
