package me.joshferrell.samkatz.math.threedimensions;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.awt.Color;

import me.joshferrell.samkatz.math.Main; 
import me.joshferrell.samkatz.math.utils.Point;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import de.congrace.exp4j.ExpressionBuilder;

public class TwoFunc3D{
	
    private int maxfps = 60;
    private Camera3D cam;
    private float ratio = 1.0f;
    private double lower = -10;
    private double upper = 10;
    private double dt = 0.05;
    private double dx = 0.01;
    private VertexBufferObject vboOne, vboTwo;
    private Point[][] verticesOne, verticesTwo;
    ExpressionBuilder EBOne, EBTwo;
    private boolean isVerticalRotation;
    private String var = "x";
    private double rotateAxis;
    
	
    public TwoFunc3D(ExpressionBuilder EBOne, ExpressionBuilder EBTwo, double precision, double camspeed, double start, double end, boolean isVert, double rotateAxis){
    	this.EBOne = EBOne;
    	this.EBTwo = EBTwo;
    	this.dx = precision;
    	cam = new Camera3D(0f,0f,-15f,0f,0f,0f,1f,camspeed);
    	this.lower = start;
    	this.upper = end;
    	this.isVerticalRotation = isVert;
    	this.rotateAxis = rotateAxis;
    	this.var = (isVert) ? "y" : "x";
    }
    
    public void start(){
        try {
        	if(!isVerticalRotation){
        		verticesOne = genQuadsOneHoriz(lower, upper, dt);
        		verticesTwo = genQuadsTwoHoriz(lower, upper, dt);
        	}
        	else{
        		verticesOne = genQuadsOneVert(lower, upper, dt);
        		verticesTwo = genQuadsTwoVert(lower, upper, dt);
        	}
        	vboOne = new VertexBufferObject(GL11.GL_QUADS, false, true, false, false, verticesOne.length*4);
        	vboTwo = new VertexBufferObject(GL11.GL_QUADS, false, true, false, false, verticesTwo.length*4);
            Display.setDisplayMode(new DisplayMode(640, 640));
            Display.setResizable(true);
            Display.setInitialBackground(1, 1, 1);
            Display.create();
            Display.setTitle("Solid of Revolution");
        }catch (LWJGLException ex){
        	ex.printStackTrace();
        	System.exit(1);
        }
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(90f, ratio, 0.001f, 200f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        initVBO();
        initGL();
        glLineWidth(2.0f);
        Mouse.setGrabbed(true);
        //main loop, constantly update display.
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
        	
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
            drawing();
            cam.update();
            
            String camx = cam.x + "";
            camx = camx.substring(0, Math.min(camx.length(), 8));
            String camy = cam.y + "";
            camy = camy.substring(0, Math.min(camy.length(), 8));
            String camz = cam.z + "";
            camz = camz.substring(0, Math.min(camz.length(), 8));
            
            Display.setTitle("(" + camx + ", " + camy + ", " + camz +")");
            
            Display.sync(maxfps);
            Display.update();
        }
        Display.destroy();
    }
    
    private void drawing(){
    	vboOne.draw();
    	vboTwo.draw();
		drawAxis();
    }
    
    private void initVBO(){
    	vboOne.createBuffer();
    	int i = 0;
    	for(Point[] quad : verticesOne){
    		for(Point point : quad){
    			vboOne.addVertex(point.getX(), point.getY(), point.getZ());
    			if(i%10000 < 4000){
    				vboOne.addColor(new Color(0,0,0,255));
    			}
    			else{
    				vboOne.addColor(new Color(0,255,0,255));
    			}
    			i++;
    		}
    	}
    	vboOne.createVBO();
    	
    	vboTwo.createBuffer();
    	i = 0;
    	for(Point[] quad : verticesTwo){
    		for(Point point : quad){
    			vboTwo.addVertex(point.getX(), point.getY(), point.getZ());
    			if(i%10000 < 4000){
    				vboTwo.addColor(new Color(255,0,255,255));
    			}
    			else{
    				vboTwo.addColor(new Color(0,0,0,255));
    			}
    			i++;
    		}
    	}
    	vboTwo.createVBO();
    }
    
    private Point[][] genQuadsOneHoriz(double startx, double endx, double dtheta){
    	Point[][] result = new Point[(int) (((endx-startx)/dx)*((2/dtheta)))][4];
    	int xcount = 0;
    	int amtx = (int) ((endx - startx)/dx);
    	for(int x = 0; x < amtx; x+=1){
    		for(double theta = 0; theta < 2; theta+=dtheta){
    			result[xcount][0] = new Point(((x*dx)+startx), funcOne(((x*dx)+startx))*Math.sin(theta*Math.PI), funcOne(((x*dx)+startx))*Math.cos(theta*Math.PI));
    			result[xcount][1] = new Point(((x*dx)+startx)+dx, funcOne(((x*dx)+startx)+dx)*Math.sin(theta*Math.PI), funcOne(((x*dx)+startx)+dx)*Math.cos(theta*Math.PI));
    			result[xcount][2] = new Point(((x*dx)+startx)+dx, funcOne(((x*dx)+startx)+dx)*Math.sin((theta+dtheta)*Math.PI), funcOne(((x*dx)+startx)+dx)*Math.cos((theta+dtheta)*Math.PI));
    			result[xcount][3] = new Point(((x*dx)+startx), funcOne(((x*dx)+startx))*Math.sin((theta+dtheta)*Math.PI), funcOne(((x*dx)+startx))*Math.cos((theta+dtheta)*Math.PI));
    			xcount++;
    		}
    	}
    	return result;
    }
    
    private Point[][] genQuadsTwoHoriz(double startx, double endx, double dtheta){
    	Point[][] result = new Point[(int) (((endx-startx)/dx)*((2/dtheta)))][4];
    	int xcount = 0;
    	int amtx = (int) ((endx - startx)/dx);
    	for(int x = 0; x < amtx; x+=1){
    		for(double theta = 0; theta < 2; theta+=dtheta){
    			result[xcount][0] = new Point(((x*dx)+startx), funcTwo(((x*dx)+startx))*Math.sin(theta*Math.PI), funcTwo(((x*dx)+startx))*Math.cos(theta*Math.PI));
    			result[xcount][1] = new Point(((x*dx)+startx)+dx, funcTwo(((x*dx)+startx)+dx)*Math.sin(theta*Math.PI), funcTwo(((x*dx)+startx)+dx)*Math.cos(theta*Math.PI));
    			result[xcount][2] = new Point(((x*dx)+startx)+dx, funcTwo(((x*dx)+startx)+dx)*Math.sin((theta+dtheta)*Math.PI), funcTwo(((x*dx)+startx)+dx)*Math.cos((theta+dtheta)*Math.PI));
    			result[xcount][3] = new Point(((x*dx)+startx), funcTwo(((x*dx)+startx))*Math.sin((theta+dtheta)*Math.PI), funcTwo(((x*dx)+startx))*Math.cos((theta+dtheta)*Math.PI));
    			xcount++;
    		}
    	}
    	return result;
    }
    
    private Point[][] genQuadsOneVert(double startx, double endx, double dtheta){
    	Point[][] result = new Point[(int) (((endx-startx)/dx)*((2/dtheta)))][4];
    	int xcount = 0;
    	int amtx = (int) ((endx - startx)/dx);
    	for(int x = 0; x < amtx; x+=1){
    		for(double theta = 0; theta < 2; theta+=dtheta){
    			result[xcount][0] = new Point(funcOne(((x*dx)+startx))*Math.sin(theta*Math.PI), ((x*dx)+startx),  funcOne(((x*dx)+startx))*Math.cos(theta*Math.PI));
    			result[xcount][1] = new Point(funcOne(((x*dx)+startx)+dx)*Math.sin(theta*Math.PI), ((x*dx)+startx)+dx,  funcOne(((x*dx)+startx)+dx)*Math.cos(theta*Math.PI));
    			result[xcount][2] = new Point(funcOne(((x*dx)+startx)+dx)*Math.sin((theta+dtheta)*Math.PI), ((x*dx)+startx)+dx,  funcOne(((x*dx)+startx)+dx)*Math.cos((theta+dtheta)*Math.PI));
    			result[xcount][3] = new Point(funcOne(((x*dx)+startx))*Math.sin((theta+dtheta)*Math.PI), ((x*dx)+startx),  funcOne(((x*dx)+startx))*Math.cos((theta+dtheta)*Math.PI));
    			xcount++;
    		}
    	}
    	return result;
    }
    
    private Point[][] genQuadsTwoVert(double startx, double endx, double dtheta){
    	Point[][] result = new Point[(int) (((endx-startx)/dx)*((2/dtheta)))][4];
    	int xcount = 0;
    	int amtx = (int) ((endx - startx)/dx);
    	for(int x = 0; x < amtx; x+=1){
    		for(double theta = 0; theta < 2; theta+=dtheta){
    			result[xcount][0] = new Point(funcTwo(((x*dx)+startx))*Math.sin(theta*Math.PI), ((x*dx)+startx),  funcTwo(((x*dx)+startx))*Math.cos(theta*Math.PI));
    			result[xcount][1] = new Point(funcTwo(((x*dx)+startx)+dx)*Math.sin(theta*Math.PI), ((x*dx)+startx)+dx,  funcTwo(((x*dx)+startx)+dx)*Math.cos(theta*Math.PI));
    			result[xcount][2] = new Point(funcTwo(((x*dx)+startx)+dx)*Math.sin((theta+dtheta)*Math.PI), ((x*dx)+startx)+dx,  funcTwo(((x*dx)+startx)+dx)*Math.cos((theta+dtheta)*Math.PI));
    			result[xcount][3] = new Point(funcTwo(((x*dx)+startx))*Math.sin((theta+dtheta)*Math.PI), ((x*dx)+startx),  funcTwo(((x*dx)+startx))*Math.cos((theta+dtheta)*Math.PI));
    			xcount++;
    		}
    	}
    	return result;
    }
    
    private double funcOne(double x){
    	try {
			return (float) EBOne.withVariable(var, x).withCustomFunction(Main.logten).build().calculate();
		}
    	catch(Exception e) {
			e.printStackTrace();
		}
    	return 0;
    }
    
    private double funcTwo(double x){
    	try {
			return (float) EBTwo.withVariable(var, x).withCustomFunction(Main.logten).build().calculate();
		}
    	catch(Exception e) {
			e.printStackTrace();
		}
    	return 0;
    }
    
    private void drawAxis(){
    	double TheAmountThatTheXAxisWillBeMoved = (isVerticalRotation) ? 0 : -rotateAxis;
    	//x
    	glColor3f(1f, 0.0f, 0.0f);
    	glBegin(GL_LINE_STRIP);
    		glVertex3d(-1000, TheAmountThatTheXAxisWillBeMoved, 0);
    		glVertex3d(1000, TheAmountThatTheXAxisWillBeMoved, 0);
    	glEnd();
    	
    	//y
    	glColor3f(0f, 1f, 0.0f);
    	double TheAmountThatTheYAxisWillBeMoved = (isVerticalRotation) ? -rotateAxis : 0;
    	glBegin(GL_LINE_STRIP);
    		glVertex3d(TheAmountThatTheYAxisWillBeMoved, -1000, 0);
    		glVertex3d(TheAmountThatTheYAxisWillBeMoved, 1000, 0);
    	glEnd();
    	
    	//z
    	glColor3f(0f, 0f, 1f);
    	double theAmountThatTheZAxisWillBeMovedAlongTheYAxis = (isVerticalRotation) ? 0.0f : -rotateAxis;
    	double theAmountThatTheZAxisWillBeMovedAlongTheXAxis = (isVerticalRotation) ? -rotateAxis : 0.0f;
    	glBegin(GL_LINE_STRIP);
    		glVertex3d(theAmountThatTheZAxisWillBeMovedAlongTheXAxis, theAmountThatTheZAxisWillBeMovedAlongTheYAxis, -1000);
    		glVertex3d(theAmountThatTheZAxisWillBeMovedAlongTheXAxis, theAmountThatTheZAxisWillBeMovedAlongTheYAxis, 1000);
    	glEnd();
    	
    	glColor3f(0f, 0f, 0f);
    	//Axis of rotation
    	if(rotateAxis != 0.0f){
	    	GL11.glLineStipple(1, (short)0x00FF);
	    	GL11.glEnable(GL11.GL_LINE_STIPPLE);
	    	glBegin(GL11.GL_LINES);
	    		if(!isVerticalRotation){
	    			glVertex3d(rotateAxis, 1000, 0);
	    			glVertex3d(rotateAxis, -1000, 0);
	    		}
	    		else{
	    			glVertex3d(1000, rotateAxis, 0);
	    			glVertex3d(-1000, rotateAxis, 0);
	    		}
	    	glEnd();
	    	GL11.glDisable(GL11.GL_LINE_STIPPLE);
    	}
    }
    
    private void initGL(){
  	  	GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
  	    GL11.glEnable(GL11.GL_COLOR_MATERIAL);
  	    GL11.glEnable(GL11.GL_DEPTH_TEST);
  	    GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
    }
    
    public static void main(String[] args){
        //TwoFunc3D gltest = new TwoFunc3D(new ExpressionBuilder("x"), new ExpressionBuilder("x*x"), dx, camspeed, lowerx, upperx);
        
        //gltest.start();
    }
}