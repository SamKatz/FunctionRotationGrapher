package me.joshferrell.samkatz.math.threedimensions;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Camera3D {
	public double x;
	public double y;
	public double z;
	public double rx;
	public double ry;
	public double rz;
	private boolean keyForward;
	private boolean keyBack;
	private boolean keyLeft;
	private boolean keyRight;
	private boolean keyUp;
	private boolean keyDown;
	
	private final double MOUSESPEED;
	private final double SPEED;
	
	public Camera3D(double x, double y, double z, double rx, double ry, double rz, final double MOUSESPEED, final double SPEED) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
		this.MOUSESPEED = MOUSESPEED;
		this.SPEED = SPEED;
	}
	
	public void update(){
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
		
		ry += Mouse.getDX() * MOUSESPEED * 0.16;
		rx += -Mouse.getDY() * MOUSESPEED * 0.16;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F)){
			x = -0.9526213f;
			y = -2.9499984f;
			z = -13.905526f;
			rx = 28.159998f;
			ry = 51.040062f;
			rz = 0;
		}
		if (ry >= 360) {
			ry -= 360;
		} else if (ry < 0) {
			ry += 360;
		}
		if (rx < -90) {
			rx = -90;
		} else if (rx > 90) {
			rx = 90;
		}
		glLoadIdentity();
		GL11.glRotated(rx,1,0,0);
		GL11.glRotated(ry,0,1,0);
		
		if (keyUp && !keyDown) y -= SPEED;
		if (keyDown && !keyUp) y += SPEED;
		if(keyForward && !keyBack && !keyLeft && !keyRight) {
			z += Math.cos(Math.toRadians(ry))*SPEED;
			x -= Math.sin(Math.toRadians(ry))*SPEED;
		}
		if(keyBack && !keyForward && !keyLeft && !keyRight) {
			z -= Math.cos(Math.toRadians(ry))*SPEED;
			x += Math.sin(Math.toRadians(ry))*SPEED;
		}
		if(keyRight && !keyLeft && !keyForward && !keyBack) {
			z += Math.cos(Math.toRadians(ry + 90))*SPEED;
			x -= Math.sin(Math.toRadians(ry + 90))*SPEED;
		}
		if(keyLeft && !keyRight && !keyForward && !keyBack) {
			z += Math.cos(Math.toRadians(ry - 90))*SPEED;
			x -= Math.sin(Math.toRadians(ry - 90))*SPEED;
		}
		
		if(keyForward && keyLeft && !keyBack && !keyRight) {
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
		}
		
		GL11.glTranslated(x,y,z);
	}

}
