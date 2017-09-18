package me.joshferrell.samkatz.math.utils;

public class Point {

	private double x, y, z;
	
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/*public Point(double x, double y, double z) {
		this(x, (float)y, (float)z);
	}
	
	public Point(double x, double y, double z){
		this((float) x, y, (float) z);
	}*/

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
}
