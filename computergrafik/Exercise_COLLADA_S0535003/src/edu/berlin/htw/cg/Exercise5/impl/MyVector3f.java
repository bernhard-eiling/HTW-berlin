package edu.berlin.htw.cg.Exercise5.impl;

import edu.berlin.htw.cg.Exercise5.interfaces.Vector3f;

public class MyVector3f implements Vector3f {
	
	float[] coo = {0.0f,0.0f,0.0f};

	MyVector3f() {
		
	}
	
	@Override
	public Vector3f clone() {
		Vector3f newVec = new MyVector3f();
		newVec.set(this.coo[0], this.coo[1], this.coo[2]);
		return newVec;
	}

	@Override
	public void set(float x, float y, float z) {
		this.coo[0] = x;
		this.coo[1] = y;
		this.coo[2] = z;
		
	}

	
	@Override
	public void setLength(float f) {
		float lenght = this.length();
		this.coo[0] = (this.coo[0] / lenght) * f;
		this.coo[1] = (this.coo[1] / lenght) * f;
		this.coo[2] = (this.coo[2] / lenght) * f;
	}

	@Override
	public Vector3f mult(float s) {
		float xNew = this.coo[0] * s;
		float yNew = this.coo[1] * s;
		float zNew = this.coo[2] * s;
		Vector3f vecNew = new MyVector3f();
		vecNew.set(xNew, yNew, zNew);
		return vecNew;
	}

	@Override
	public float getX() {
		return this.coo[0];
	}

	@Override
	public float getY() {
		return this.coo[1];
	}

	@Override
	public float getZ() {
		return this.coo[2];
	}

	@Override
	public float length() {
		return (float)Math.sqrt(coo[0]*coo[0] + coo[1]*coo[1] + coo[2]*coo[2]);
	}

	@Override
	public void get(float[] dest) {
		this.coo[0] = dest[0];
		this.coo[1] = dest[1];
		this.coo[2] = dest[2];
	}

	@Override
	public Vector3f addVector(Vector3f b) {
		float xNew = this.coo[0] + b.getX();
		float yNew = this.coo[1] + b.getY();
		float zNew = this.coo[2] + b.getZ();
		Vector3f vecNew = new MyVector3f();
		vecNew.set(xNew, yNew, zNew);
		return vecNew;
	}

	@Override
	public Vector3f subtractVector(Vector3f b) {
		float xNew = this.coo[0] - b.getX();
		float yNew = this.coo[1] - b.getY();
		float zNew = this.coo[2] - b.getZ();
		Vector3f vecNew = new MyVector3f();
		vecNew.set(xNew, yNew, zNew);
		return vecNew;
	}

	@Override
	public Vector3f cross(Vector3f b) {
		Vector3f vecNew = new MyVector3f();
		vecNew.set(this.coo[1] * b.getZ() - this.coo[2] * b.getY(), 
				this.coo[2] * b.getX() - this.coo[0] * b.getZ(),
				this.coo[0] * b.getY() - this.coo[1] * b.getX());
		return vecNew;
	}
	
	
}
