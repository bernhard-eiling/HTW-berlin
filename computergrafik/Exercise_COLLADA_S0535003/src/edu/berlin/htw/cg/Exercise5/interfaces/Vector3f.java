package edu.berlin.htw.cg.Exercise5.interfaces;

import edu.berlin.htw.cg.Exercise5.dsimpl.Vector3fImpl;

public interface Vector3f {

	Vector3f clone();
	void set(float x, float y, float z);
	void setLength(float f);
	public Vector3f mult(float s);
	
	public float getX();
	public float getY();
	public float getZ();
	public float length();
	public void get(float[] dest);
	
	public Vector3f addVector(Vector3f b);
	public Vector3f subtractVector(Vector3f b);
	public Vector3f cross(Vector3f b);

}
