package me.joshferrell.samkatz.math.utils;

import static org.lwjgl.opengl.GL11.glVertex3d;

public class Utils {
	
	
	
	public static void drawCube(double xpos, double ypos, double zpos, double sidelength){
		glVertex3d(xpos + sidelength, ypos + sidelength, zpos);
        glVertex3d(xpos, ypos + sidelength, zpos);
        glVertex3d(xpos, ypos + sidelength, zpos + sidelength);
        glVertex3d(xpos + sidelength, ypos + sidelength, zpos + sidelength);

        //glColor3f(xpos + sidelength, 0.5f, 0.0f);     //Orange
        glVertex3d(xpos + sidelength, ypos, zpos + sidelength);
        glVertex3d(xpos, ypos, zpos + sidelength);
        glVertex3d(xpos, ypos, zpos);
        glVertex3d(xpos + sidelength, ypos, zpos);

        //glColor3f(xpos + sidelength, 0.0f, 0.0f);     //Red
        glVertex3d(xpos + sidelength, ypos + sidelength, zpos + sidelength);
        glVertex3d(xpos, ypos + sidelength, zpos + sidelength);
        glVertex3d(xpos, ypos, zpos + sidelength);
        glVertex3d(xpos + sidelength, ypos, zpos + sidelength);

        //glColor3f(xpos + sidelength, ypos + sidelength, 0.0f);     //Yellow
        glVertex3d(xpos + sidelength, ypos, zpos);
        glVertex3d(xpos, ypos, zpos);
        glVertex3d(xpos, ypos + sidelength, zpos);
        glVertex3d(xpos + sidelength, ypos + sidelength, zpos);

        //glColor3f(0.0f, 0.0f, zpos + sidelength);     //Blue
        glVertex3d(xpos, ypos + sidelength, zpos + sidelength);
        glVertex3d(xpos, ypos + sidelength, zpos);
        glVertex3d(xpos, ypos, zpos);
        glVertex3d(xpos, ypos, zpos + sidelength);

        //glColor3f(xpos + sidelength, 0.0f, zpos + sidelength);     //Violet
        glVertex3d(xpos + sidelength, ypos + sidelength, zpos);
        glVertex3d(xpos + sidelength, ypos + sidelength, zpos + sidelength);
        glVertex3d(xpos + sidelength, ypos, zpos + sidelength);
        glVertex3d(xpos + sidelength, ypos, zpos);
	}

	public static void drawRectangle(double xpos, double ypos, double zpos, double xsidelength, double ysidelength, double zsidelength){
		
		glVertex3d(xpos + xsidelength, ypos + ysidelength, zpos);
        glVertex3d(xpos, ypos + ysidelength, zpos);
        glVertex3d(xpos, ypos + ysidelength, zpos + zsidelength);
        glVertex3d(xpos + xsidelength, ypos + ysidelength, zpos + zsidelength);

        //glColor3f(xpos + xsidelength, 0.5f, 0.0f);     //Orange
        glVertex3d(xpos + xsidelength, ypos, zpos + zsidelength);
        glVertex3d(xpos, ypos, zpos + zsidelength);
        glVertex3d(xpos, ypos, zpos);
        glVertex3d(xpos + xsidelength, ypos, zpos);

        //glColor3f(xpos + xsidelength, 0.0f, 0.0f);     //Red
        glVertex3d(xpos + xsidelength, ypos + ysidelength, zpos + zsidelength);
        glVertex3d(xpos, ypos + ysidelength, zpos + zsidelength);
        glVertex3d(xpos, ypos, zpos + zsidelength);
        glVertex3d(xpos + xsidelength, ypos, zpos + zsidelength);

        //glColor3f(xpos + xsidelength, ypos + ysidelength, 0.0f);     //Yellow
        glVertex3d(xpos + xsidelength, ypos, zpos);
        glVertex3d(xpos, ypos, zpos);
        glVertex3d(xpos, ypos + ysidelength, zpos);
        glVertex3d(xpos + xsidelength, ypos + ysidelength, zpos);

        //glColor3f(0.0f, 0.0f, zpos + zsidelength);     //Blue
        glVertex3d(xpos, ypos + ysidelength, zpos + zsidelength);
        glVertex3d(xpos, ypos + ysidelength, zpos);
        glVertex3d(xpos, ypos, zpos);
        glVertex3d(xpos, ypos, zpos + zsidelength);

        //glColor3f(xpos + xsidelength, 0.0f, zpos + zsidelength);     //Violet
        glVertex3d(xpos + xsidelength, ypos + ysidelength, zpos);
        glVertex3d(xpos + xsidelength, ypos + ysidelength, zpos + zsidelength);
        glVertex3d(xpos + xsidelength, ypos, zpos + zsidelength);
        glVertex3d(xpos + xsidelength, ypos, zpos);
	}

	/*public static void drawCubeOutline(double x, double y, double z, double sidelength){
		glColor3f(1.0f, 0.0f, 0.0f);
		glBegin(GL_LINES);
			glLineWidth(2.0f);
			glVertex3d(x, y, z);
			glVertex3d(x + sidelength, y, z);
			
		glEnd();
	}*/
}
