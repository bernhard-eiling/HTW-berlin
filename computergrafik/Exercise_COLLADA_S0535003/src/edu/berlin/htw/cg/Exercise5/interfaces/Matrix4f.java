package edu.berlin.htw.cg.Exercise5.interfaces;

import java.nio.FloatBuffer;

import edu.berlin.htw.cg.Exercise5.dsimpl.Matrix4fImpl;

public interface Matrix4f {
	   public Matrix4f clone();
	   
	   // these methods DO NOT change the matrix inside, just return the result
	   public Matrix4f add(Matrix4f in2);
	   public Matrix4f mult(Matrix4f in2);
	   public Vector3f mult(Vector3f in2);
	   public Matrix4f mult(float scalar);
	   
	   // set Values uses by COLLADA
	   public void set(float[] matrix);
	   public void set(int ind1, int ind2, float value);
	   public float get(int ind1, int ind2);

	   // this should ignore the TRANSLATION aka take the 3x3 matrix!
	   public Vector3f multNormal(Vector3f in2);
	   
	   // TransposeMatrix: Exchange indices of rows and columns
	   public Matrix4f transposeLocal();

	   //Rotation around arbitrary axis, e.g. from formula in section 5.2
	   // from here: http://inside.mines.edu/~gmurray/ArbitraryAxisRotation/
	   public void setAngleAxis(float angle_rad, Vector3f axis);
	   
	   //rotate around the XYZ axis with the given angles
	   //Z=yaw, Y=pitch, X=roll. Multiply (Z*Y)*X
	   // See http://planning.cs.uiuc.edu/node102.html for formula and
	   // explanation
	   public void angleRotation(float x, float y, float z);
	   
	   //multiply m00, m11 and m22 accordingly
	   public void setScale(float x, float y, float z);
	   // set m03, m13, m23 accordingly
	   public void setTranslation(float x, float y, float z);
	   
	   //reset to Identity Matrix
	   public void loadIdentity();
	   
	   // see slides from this exercise page 18
	   public FloatBuffer toFloatBuffer();
	   public Matrix4f readFloatBuffer(FloatBuffer fb);
	   public void toTranslationVector(Vector3f vec);
}
