package edu.berlin.htw.cg.Exercise5.impl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;

import edu.berlin.htw.cg.Exercise5.interfaces.CGMesh;
import edu.berlin.htw.cg.Exercise5.utilities.TextureReader;

public class MyCGMesh implements CGMesh<GL2> {

	GLUT glut = new GLUT();

	MyCGMesh() {

	}

	int count = 0;
	float[] colors = new float[] { 0, 0, 0 };
	float[] vertices;
	float[] normals;
	int[] textures = new int[2];
	FloatBuffer uvBufferF;
	FloatBuffer normalsBufferF;
	FloatBuffer verticesBufferF;
	FloatBuffer colorsBufferF;
	IntBuffer indicesBufferI;
	CGMesh.CGMaterial material = new CGMesh.CGMaterial();
	float[] materialProperties = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
	float[] materialShininess = new float[] { 1.0f };
	TextureReader.Texture texture;
	String textureSrc = "./Data/textures/TestPattern.jpg";

	int drawType;

	@Override
	public void setVertices(float[] verts) {
		vertices = verts;
		ByteBuffer verticesBuffer = ByteBuffer.allocateDirect(verts.length * 4);
		verticesBuffer.order(ByteOrder.nativeOrder());
		verticesBufferF = verticesBuffer.asFloatBuffer();
		verticesBufferF.put(verts);
		verticesBufferF.position(0);

	}

	@Override
	public void setIndices(int[] inds) {
		ByteBuffer indicesBuffer = ByteBuffer.allocateDirect(inds.length * 4);
		indicesBuffer.order(ByteOrder.nativeOrder());
		indicesBufferI = indicesBuffer.asIntBuffer();
		indicesBufferI.put(inds);
		indicesBufferI.position(0);
	}

	@Override
	public void setNormals(float[] norms) {
		normals = norms;
		ByteBuffer normalsBuffer = ByteBuffer.allocateDirect(norms.length * 4);
		normalsBuffer.order(ByteOrder.nativeOrder());
		normalsBufferF = normalsBuffer.asFloatBuffer();
		normalsBufferF.put(norms);
		normalsBufferF.position(0);
	}

	@Override
	public void setColors(float[] cols) {
		colors = cols;
		ByteBuffer colorsBuffer = ByteBuffer.allocateDirect(cols.length * 4);
		colorsBuffer.order(ByteOrder.nativeOrder());
		colorsBufferF = colorsBuffer.asFloatBuffer();
		colorsBufferF.put(cols);
		colorsBufferF.position(0);
	}

	@Override
	public void setDrawType(int type) {
		this.drawType = type;
	}

	@Override
	public void setUVCoords(float[] uv) {
		ByteBuffer uvBuffer = ByteBuffer.allocateDirect(uv.length * 4);
		uvBuffer.order(ByteOrder.nativeOrder());
		uvBufferF = uvBuffer.asFloatBuffer();
		uvBufferF.put(uv);
		uvBufferF.position(0);

	}

	@Override
	public void setMaterial(CGMaterial mat) {
		this.material = mat;
	}

	@Override
	public void setTextureSource(int glTexCoordSet, String filename) {
	}

	@Override
	public float[] getVertices() {
		return vertices;
	}

	@Override
	public float[] getNormals() {
		return normals;
	}

	@Override
	public boolean isQuads() {
		if (drawType == 4) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasNormals() {
		if (normalsBufferF != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasColors() {

		if (colorsBufferF != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasUV() {
		if (uvBufferF != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void draw(GL2 gl) {

		material.diffuse = new MyVector3f();
		material.ambient = new MyVector3f();
		material.specular = new MyVector3f();

		material.diffuse.get(materialProperties);
		material.ambient.get(materialProperties);
		material.specular.get(materialProperties);

		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, materialProperties, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materialProperties, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR,
				materialProperties, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS,
				materialShininess, 0);

		if (hasNormals()) {
			gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
			gl.glNormalPointer(GL2.GL_FLOAT, 0, normalsBufferF);
		}

		if (hasColors()) {
			gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
			gl.glColorPointer(3, GL2.GL_FLOAT, 0, colorsBufferF);
		}

		// TEXTURES
		if (hasUV()) {
			gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
			try {
				texture = TextureReader.readTexture(
						textureSrc, true);
			} catch (Exception exc) {
				System.out.println("NO Texture found!");
			}
			int texW = texture.getWidth();
			int texH = texture.getHeight();
			ByteBuffer texPix = texture.getPixels();
			gl.glGenTextures(2, textures, 0);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[0]);
			gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, texW, texH, 0,
					GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, texPix);
			
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
					GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
					GL2.GL_LINEAR);

			gl.glEnable(GL2.GL_TEXTURE_2D);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[0]);
			gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

			gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, uvBufferF);
		}
/*
		if (drawType == 7) {
			gl.glBegin(GL2.GL_QUADS);
		} else if (drawType == 4) {
			gl.glBegin(GL2.GL_TRIANGLES);
		}
		for (int i = 0; i < indicesBufferI.limit(); i++) {

			int index = indicesBufferI.get(i);
			index *= 3;
			Vector3f firstVec = new MyVector3f();
			firstVec.set(verticesBufferF.get(index),
					verticesBufferF.get(index + 1),
					verticesBufferF.get(index + 2));
			gl.glVertex3f(firstVec.getX(), firstVec.getY(), firstVec.getZ());
		}
		gl.glEnd();
*/
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);

		if (drawType == 4) {
			count = indicesBufferI.limit();
			gl.glVertexPointer(3, GL2.GL_FLOAT, 0, verticesBufferF);
			gl.glDrawElements(drawType, count, GL2.GL_UNSIGNED_INT,
					indicesBufferI);

		}
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);

		if (hasUV()) {
			gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
			gl.glDisable(GL2.GL_TEXTURE_2D);
		}

		if (hasNormals()) {
			gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
		}

		if (hasColors()) {
			gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
		}
	}
}
