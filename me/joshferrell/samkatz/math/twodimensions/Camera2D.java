package me.joshferrell.samkatz.math.twodimensions;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Camera2D {
	public double x;
	public double y;
	public double z;
	private boolean keyForward;
	private boolean keyBack;
	private boolean keyLeft;
	private boolean keyRight;
	private boolean keyUp;
	private boolean keyDown;
	
	private final double SPEED;
	
	public Camera2D(double x, double y, double z, final double SPEED) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.SPEED = SPEED;
	}
	
	public void update(){
		//System.out.println("Current position is " + x + ", " + y + ", " + z);
		//Get the relevant buttons that are pressed
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			keyForward = true;
			
		} else {
			keyForward = false;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			keyBack = true;
		} else {
			keyBack = false;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			keyLeft = true;
		} else {
			keyLeft = false;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			keyRight = true;
		} else {
			keyRight = false;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			keyUp = true;
		} else {
			keyUp = false;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			keyDown = true;
		} else {
			keyDown = false;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F)){
			x = 0.0f;
			y = 0.0f;
			z = -5f;
		}
		glLoadIdentity();
		
		if (keyUp && !keyDown) z -= SPEED*2;
		if (keyDown && !keyUp) z += SPEED*2;
		if(keyForward && !keyBack) {
			y -= SPEED;
		}
		if(keyBack && !keyForward) {
			y += SPEED;
		}
		if(keyRight && !keyLeft) {
			x -= SPEED;
		}
		if(keyLeft && !keyRight) {
			x += SPEED;
		}
		
		/*if(keyForward && keyLeft && !keyBack && !keyRight) {
			z += Math.cos(Math.toRadians(ry - 45))*SPEED;
			x -= Math.sin(Math.toRadians(ry - 45))*SPEED;
		}
		if(keyForward && keyRight && !keyBack && !keyLeft) {
			z += Math.cos(Math.toRadians(ry + 45))*SPEED;
			x -= Math.sin(Math.toRadians(ry + 45))*SPEED;
		}
		if(keyBack && keyLeft && !keyForward && !keyRight) {
			z -= Math.cos(Math.toRadians(ry + 45))*SPEED;
			x += Math.sin(Math.toRadians(ry + 45))*SPEED;
		}
		if(keyBack && keyRight && !keyForward && !keyLeft) {
			z -= Math.cos(Math.toRadians(ry - 45))*SPEED;
			x += Math.sin(Math.toRadians(ry - 45))*SPEED;
		}*/
		
		GL11.glTranslated(x,y,z);
	}

}
