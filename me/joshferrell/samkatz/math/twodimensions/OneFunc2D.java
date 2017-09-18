package me.joshferrell.samkatz.math.twodimensions;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import me.joshferrell.samkatz.math.Main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import de.congrace.exp4j.ExpressionBuilder;

public class OneFunc2D{
	
	private int maxfps = 60;
	private Camera2D cam;
	private float ratio = 1.0f;
	private ExpressionBuilder EB;
	private double dx, lowerx, upperx;
    
	public OneFunc2D(ExpressionBuilder EB, double precision, double camspeed, double startx, double endx){
    	this.EB = EB;
    	this.dx = precision;
    	this.lowerx = startx;
    	this.upperx = endx;
    	cam = new Camera2D(0f,0f,-15f,camspeed);
    }
	
    public void start(){
        try {
            Display.setDisplayMode(new DisplayMode(640, 640));
            Display.setInitialBackground(1, 1, 1);
            Display.create(new PixelFormat(0, 8, 0, 4));
            Display.setTitle("2D Graph");
        }catch (LWJGLException ex){
        	ex.printStackTrace();
        	System.exit(1);
        }
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(90f, ratio, 0.001f, 200f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        
        glEnable(GL_DEPTH_TEST);
        glLineWidth(2.0f);
        
        //main loop, constantly update display.
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
            logic();
            
            cam.update();
            
            
            GL11.glBegin(GL11.GL_QUADS);
            
            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            
            double dotScale = cam.z / 80;
            
            GL11.glVertex2d(-cam.x + dotScale, -cam.y + dotScale);
            GL11.glVertex2d(-cam.x + dotScale, -cam.y - dotScale);
            GL11.glVertex2d(-cam.x - dotScale, -cam.y - dotScale);
            GL11.glVertex2d(-cam.x - dotScale, -cam.y + dotScale);
            
            String camx = cam.x + "";
            camx = camx.substring(0, Math.min(camx.length(), 8));
            String camy = cam.y + "";
            camy = camy.substring(0, Math.min(camy.length(), 8));
            
            Display.setTitle("(" + camx + ", " + camy + ")");
            
            glEnd();
            
            
            Mouse.setGrabbed(false);
            
            Display.sync(maxfps);
            Display.update();
        }
        Display.destroy();
        
    }
    
    private void logic(){
		redraw();
		drawAxis();
    }
    
    private double func(double x){
    	try {
			return (float) EB.withVariable("x", x).withCustomFunction(Main.logten).build().calculate();
		}
    	catch(Exception e) {
			e.printStackTrace();
		}
    	return 0;
    }
    
    private void drawAxis(){
    	//x
    	glColor3f(0.0f, 0.0f, 0.0f);
    	glBegin(GL_LINE_STRIP);
    		glVertex3d(-1000, 0, 0);
    		glVertex3d(1000, 0, 0);
    	glEnd();
    	
    	//y
    	glBegin(GL_LINE_STRIP);
    		glVertex3d(0, -1000, 0);
    		glVertex3d(0, 1000, 0);
    	glEnd();
    }
    
    private void draw(double x1, double x2, double y1, double y2){
    	double x = dx;
    	glPushMatrix();
    	glColor3f(0.0f, 0.0f, 1.0f);
    	
    	glBegin(GL_LINE_STRIP);
    		for(x = x1; x < x2; x+=dx){
    			glVertex3d(x, func(x), 0);
    		}
    	glEnd();
    }
    
    private void redraw(){
    	draw(lowerx, upperx, -1000, 1000);
    }
}