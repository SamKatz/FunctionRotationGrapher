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

public class OneFunc3D{
	
    //private int lastframe;
    private int maxfps = 60;
    private Camera3D cam;
    private float ratio = 1.0f;
    private double lowerbound = -10;
    private double upperbound = 10;
    private double dt = 0.05;
    private double dx = 0.01;
    private VertexBufferObject vbo;
    private Point[][] vertices;
    ExpressionBuilder EB;
    private double rotateAxis = 1.0;
    private boolean isVerticalRotation;
    private String var = "x";
	
    public OneFunc3D(ExpressionBuilder EB, double precision, double camspeed, double lower, double upper, boolean isVert, double rotateAxis){
    	this.EB = EB;
    	this.dx = precision;
    	this.lowerbound = lower;
    	this.upperbound = upper;
    	this.cam = new Camera3D(0f,0.01f,-15f,0f,0f,0f,1f,camspeed);
    	this.isVerticalRotation = isVert;
    	this.rotateAxis = rotateAxis;
    	this.var = (isVert) ? "y" : "x" ;
    }
    
    public void start(){
        try {
        	if(!isVerticalRotation){
        		vertices = genQuadsX(lowerbound, upperbound, dt);
        	}
        	else{
        		vertices = genQuadsY(lowerbound, upperbound, dt);
        	}
        	vbo = new VertexBufferObject(GL11.GL_QUADS, false, true, false, false, vertices.length*4);
            Display.setDisplayMode(new DisplayMode(640, 640));
            Display.setResizable(true);
            Display.setInitialBackground(1, 1, 1);
            Display.create();
            Display.setTitle("Solid of Revolution");
        }catch (LWJGLException ex){
        	ex.printStackTrace();
        	System.exit(1);
        }
        //lastframe = (int) Sys.getTime();
        
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
        //int framerate_count = 0;
        //long framerate_timestamp = new Date().getTime();
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
        	
        	 /*framerate_count++;
             Date d = new Date();
             long this_framerate_timestamp = d.getTime();
             if ((this_framerate_timestamp - framerate_timestamp) >= 1000) {
               //System.out.println("Frame Rate: " + framerate_count);
               framerate_count = 0;
               framerate_timestamp = this_framerate_timestamp;
             }*/
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
            
            
            //float[] cloc = {cam.x, cam.y, cam.z};
            Display.sync(maxfps);
            Display.update();
        }
        Display.destroy();
        
    }
    
    private void drawing(){
    	vbo.draw();
		drawAxis();
    }
    
    private void initVBO(){
    	vbo.createBuffer();
    	int i = 0;
    	for(Point[] quad : vertices){
    		for(Point point : quad){
    			vbo.addVertex(point.getX(), point.getY(), point.getZ());
    			if(i%10000 < 4000){
    				vbo.addColor(new Color(0,0,0,255));
    			}
    			else{
    				vbo.addColor(new Color(0,255,0,255));
    			}
    			i++;
    		}
    	}
    	vbo.createVBO();
    }
    
    private Point[][] genQuadsX(double startx, double endx, double dtheta){
    	Point[][] result = new Point[(int) (((endx-startx)/dx)*((2/dtheta)))][4];
    	int xcount = 0;
    	//int a = 0;
    	//int b = 0;
    	int amtx = (int) ((endx - startx)/dx);
    	for(int x = 0; x < amtx; x+=1){
    		for(double theta = 0; theta < 2; theta+=dtheta){
    			result[xcount][0] = new Point(((x*dx)+startx), func(((x*dx)+startx))*Math.sin(theta*Math.PI), func(((x*dx)+startx))*Math.cos(theta*Math.PI));
    			result[xcount][1] = new Point(((x*dx)+startx)+dx, func(((x*dx)+startx)+dx)*Math.sin(theta*Math.PI), func(((x*dx)+startx)+dx)*Math.cos(theta*Math.PI));
    			result[xcount][2] = new Point(((x*dx)+startx)+dx, func(((x*dx)+startx)+dx)*Math.sin((theta+dtheta)*Math.PI), func(((x*dx)+startx)+dx)*Math.cos((theta+dtheta)*Math.PI));
    			result[xcount][3] = new Point(((x*dx)+startx), func(((x*dx)+startx))*Math.sin((theta+dtheta)*Math.PI), func(((x*dx)+startx))*Math.cos((theta+dtheta)*Math.PI));
    			xcount++;
    			//b++;
    			//System.out.println(b);
    		}
    		//a++;
    		//System.out.println("outer: " + a);
    		//System.out.println("x: " + x + " endx: " + endx + " dx: " + dx);
    	}
    	//System.out.println("result length: " + result.length);
    	return result;
    }
    
    //x = f(y) not y = f(x)
    private Point[][] genQuadsY(double starty, double endy, double dtheta){
    	Point[][] result = new Point[(int) (((endy-starty)/dx)*((2/dtheta)))][4];
    	int xcount = 0;
    	int amtx = (int) ((endy - starty)/dx);
    	for(int x = 0; x < amtx; x+=1){
    		for(double theta = 0; theta < 2; theta+=dtheta){
    			result[xcount][0] = new Point(((x*dx)+starty), func(((x*dx)+starty))*Math.sin(theta*Math.PI), func(((x*dx)+starty))*Math.cos(theta*Math.PI));
    			result[xcount][1] = new Point(((x*dx)+starty)+dx, func(((x*dx)+starty)+dx)*Math.sin(theta*Math.PI), func(((x*dx)+starty)+dx)*Math.cos(theta*Math.PI));
    			result[xcount][2] = new Point(((x*dx)+starty)+dx, func(((x*dx)+starty)+dx)*Math.sin((theta+dtheta)*Math.PI), func(((x*dx)+starty)+dx)*Math.cos((theta+dtheta)*Math.PI));
    			result[xcount][3] = new Point(((x*dx)+starty), func(((x*dx)+starty))*Math.sin((theta+dtheta)*Math.PI), func(((x*dx)+starty))*Math.cos((theta+dtheta)*Math.PI));
    			xcount++;
    		}
    	}
    	return result;
    }
    
    private double func(double x){
    	try {
			return (float) EB.withVariable(var, x).withCustomFunction(Main.logten).build().calculate() - rotateAxis;
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
	    		if(isVerticalRotation){
	    			glVertex3d(0, 1000, 0);
	    			glVertex3d(0, -1000, 0);
	    		}
	    		else{
	    			glVertex3d(1000, 0, 0);
	    			glVertex3d(-1000,0, 0);
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
        //Graph3D gltest = new Graph3D(new ExpressionBuilder("x"), dx, camspeed, lowerx, upperx);
        
        //gltest.start();
    }
}