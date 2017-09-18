package me.joshferrell.samkatz.math.threedimensions;

import java.awt.Color;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;

public class VertexBufferObject {
	
	private boolean isStatic;
	private boolean hasColor;
	private boolean hasTexture;
	private boolean hasNormals;
	private int renderMode;
	private int vertices;
	private int vertexSize;

	private ByteBuffer buffer;
	private int vboId;
	private boolean isDirty;

	public VertexBufferObject (int renderMode, boolean isStatic, boolean hasColor, boolean hasTexture, boolean hasNormals, int vertices) {
	    this.isStatic = isStatic;
	    this.hasColor = hasColor;
	    this.hasTexture = hasTexture;
	    this.hasNormals = hasNormals;
	    this.vertices = vertices;
	    this.renderMode = renderMode;
	    vertexSize = calculateVertexSize();
	}


	public void markDirty () {
	    isDirty = true;
	}


	public void createBuffer () {
	    buffer = BufferUtils.createByteBuffer(calculateVertexSize());
	    markDirty();
	}

	public void createVBO () {
	    buffer.flip();

	    vboId = ARBVertexBufferObject.glGenBuffersARB();

	    // Buffer into vbo
	    ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboId);
	    ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, buffer, (isStatic ? ARBVertexBufferObject.GL_STATIC_DRAW_ARB : ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB));

	    // Unbind
	    ARBVertexBufferObject.glUnmapBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB);
	    ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);

	    if (isStatic) buffer = null;
	}



	public void addVertex (float x, float y, float z) {
	    buffer.putFloat(x);
	    buffer.putFloat(y);
	    buffer.putFloat(z);
	}
	public void addVertex (double x, double y, double z) {
	    buffer.putFloat((float)x);
	    buffer.putFloat((float)y);
	    buffer.putFloat((float)z);
	}

	public void addTextureCoordinates (float u, float v) {
	    buffer.putFloat(u);
	    buffer.putFloat(v);

	}

	public void addColor (Color color) {
	    addColor(color.getRGB());
	}

	public void addColor (int rgba) {
	    buffer.putInt(rgba);
	    //System.out.println(Integer.toBinaryString(rgba));
	}

	public void addNormal (byte x, byte y, byte z) {
	    buffer.put(x);
	    buffer.put(y);
	    buffer.put(z);
	}



	public void setVertex (int index, float x, float y, float z) {
	    index *= vertexSize;
	    buffer.position(index);
	    buffer.putFloat(x);
	    buffer.putFloat(y);
	    buffer.putFloat(z);
	    markDirty();
	}

	public void setTextureCoordinates (int index, float u, float v) {
	    index *= vertexSize + 12;
	    buffer.position(index);
	    buffer.putFloat(u);
	    buffer.putFloat(v);
	    markDirty();
	}

	public void setColor (int index, Color color) {
	    setColor(index, color.getRGB());
	}

	public void setColor (int index, int rgba) {
	    index *= vertexSize + (hasTexture ? 20 : 12);
	    buffer.position(index);
	    buffer.putInt(rgba);
	    markDirty();
	}

	public void setNormal (int index, byte x, byte y, byte z) {
	    index *= vertexSize - 3;
	    buffer.position(index);
	    buffer.put(x);
	    buffer.put(y);
	    buffer.put(z);
	    markDirty();
	}


	public void draw () {
	    draw(0, vertices);
	}

	public void draw (int start, int vertexNumber) {
	    if (vboId == 0) return;

	    ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboId);

	    // Update Dynamic VBO
	    if (isDirty && !isStatic) {
	        buffer.position(0);
	        int vboType = isStatic ? ARBVertexBufferObject.GL_STATIC_DRAW_ARB : ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB;
	        ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0, vboType);
	        ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, buffer, vboType);
	        isDirty = false;
	    }

	    // Stride
	    int stride = 12;
	    if (hasTexture) stride += 8;
	    if (hasColor) stride += 4;
	    if (hasNormals) stride += 3;

	    // Apply Pointers
	    GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	    GL11.glVertexPointer(3, GL11.GL_FLOAT, stride, 0);

	    if (hasTexture) {
	        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, stride, 12);
	    }

	    if (hasColor) {
	        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
	        GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, stride, hasTexture ? 20 : 12);
	    }

	    if (hasNormals) {
	        GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
	        GL11.glNormalPointer(GL11.GL_BYTE, stride, stride - 3);
	    }

	    // Draw with specified render mode
	    GL11.glDrawArrays(renderMode, start, vertexNumber);

	    // Unbind VBO
	    if (hasTexture) GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	    if (hasColor) GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
	    if (hasNormals) GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
	    GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

	    ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
	}


	public void destroy () {
	    ARBVertexBufferObject.glDeleteBuffersARB(vboId);
	}


	private int calculateVertexSize () {
	    return vertices * 12 + vertices * (hasTexture ? 8 : 0) + vertices * (hasColor ? 4 : 0) + vertices * (hasNormals ? 4 : 0);
	}

}
