package edu.berlin.htw.cg.Exercise5.impl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import edu.berlin.htw.cg.Exercise5.interfaces.Matrix4f;
import edu.berlin.htw.cg.Exercise5.interfaces.Vector3f;
import static java.lang.Math.*;

public class MyMatrix4f implements Matrix4f {

	float[] matrix = new float[16];

	MyMatrix4f() {
		this.loadIdentity();
	}

	@Override
	public Matrix4f clone() {
		Matrix4f newMatrix = new MyMatrix4f();
		newMatrix.set(this.matrix);
		return newMatrix;
	}

	@Override
	public Matrix4f add(Matrix4f in2) {
		Matrix4f newMatrix = new MyMatrix4f();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				newMatrix.set(i, j, this.get(i, j) + in2.get(i, j));
			}
		}
		return newMatrix;
	}

	@Override
	public Matrix4f mult(Matrix4f in2) {
		Matrix4f newMatrix = new MyMatrix4f();
		float value = 0.0f;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					value += this.get(i, k) * in2.get(k, j);
				}
				newMatrix.set(i, j, value);
				value = 0.0f;
			}

		}
		return newMatrix;
	}

	@Override
	public Vector3f mult(Vector3f in2) {

		Vector3f newVec = new MyVector3f();
		newVec.set(in2.getX() * this.matrix[0] + in2.getY() * this.matrix[1]
				+ in2.getZ() * this.matrix[2] + this.matrix[3], in2.getX()
				* this.matrix[4] + in2.getY() * this.matrix[5] + in2.getZ()
				* this.matrix[6] + this.matrix[7], in2.getX() * this.matrix[8]
				+ in2.getY() * this.matrix[9] + in2.getZ() * this.matrix[10]
				+ this.matrix[11]);
		return newVec;
	}

	@Override
	public Matrix4f mult(float scalar) {
		Matrix4f newMatrix = new MyMatrix4f();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				newMatrix.set(i, j, this.get(i, j) * scalar);
			}
		}
		return newMatrix;
	}

	@Override
	public void set(float[] matrix) {
		
		this.matrix[0] = matrix[0];
		this.matrix[1] = matrix[4];
		this.matrix[2] = matrix[8];
		this.matrix[3] = matrix[12];
		
		this.matrix[4] = matrix[1];
		this.matrix[5] = matrix[5];
		this.matrix[6] = matrix[9];
		this.matrix[7] = matrix[13];
		
		this.matrix[8] = matrix[2];
		this.matrix[9] = matrix[6];
		this.matrix[10] = matrix[10];
		this.matrix[11] = matrix[14];
		
		this.matrix[12] = matrix[3];
		this.matrix[13] = matrix[7];
		this.matrix[14] = matrix[11];
		this.matrix[15] = matrix[15];
	}

	public float[] getMatrix() {
		return this.matrix;
	}

	@Override
	public void set(int ind1, int ind2, float value) {
		this.matrix[(ind1 + (ind2) * 4)] = value;

	}

	@Override
	public float get(int ind1, int ind2) {
		return this.matrix[(ind1 + (ind2) * 4)];
	}

	@Override
	public Vector3f multNormal(Vector3f in2) {
		return null;
	}

	@Override
	public Matrix4f transposeLocal() {
		Matrix4f newMatrix = new MyMatrix4f();
		newMatrix.set(0, 0, this.get(0, 0));
		newMatrix.set(0, 1, this.get(1, 0));
		newMatrix.set(0, 2, this.get(2, 0));
		newMatrix.set(0, 3, this.get(3, 0));

		newMatrix.set(1, 0, this.get(0, 1));
		newMatrix.set(1, 1, this.get(1, 1));
		newMatrix.set(1, 2, this.get(2, 1));
		newMatrix.set(1, 3, this.get(3, 1));

		newMatrix.set(2, 0, this.get(0, 2));
		newMatrix.set(2, 1, this.get(1, 2));
		newMatrix.set(2, 2, this.get(2, 2));
		newMatrix.set(2, 3, this.get(3, 2));

		newMatrix.set(3, 0, this.get(0, 3));
		newMatrix.set(3, 1, this.get(1, 3));
		newMatrix.set(3, 2, this.get(2, 3));
		newMatrix.set(3, 3, this.get(3, 3));
		return newMatrix;
	}

	@Override
	public void setAngleAxis(float angle_rad, Vector3f axis) {
		Vector3f normVec = new MyVector3f();
		normVec = axis.mult(1.0f / axis.length());
		
		this.set(0, 0,
				(float) Math.cos(angle_rad) + (normVec.getX() * normVec.getX())
						* (1.0f - (float) Math.cos(angle_rad)));
		this.set(
				0,
				1,
				normVec.getX() * normVec.getY()
						* (1.0f - (float) Math.cos(angle_rad)) + normVec.getZ()
						* (float) Math.sin(angle_rad));
		this.set(
				0,
				2,
				normVec.getX() * normVec.getZ()
						* (1.0f - (float) Math.cos(angle_rad)) - normVec.getY()
						* (float) Math.sin(angle_rad));

		this.set(
				1,
				0,
				normVec.getX() * normVec.getY()
						* (1.0f - (float) Math.cos(angle_rad)) - normVec.getZ()
						* (float) Math.sin(angle_rad));
		this.set(
				1,
				1,
				normVec.getY() * normVec.getY()
						* (1.0f - (float) Math.cos(angle_rad))
						+ (float) Math.cos(angle_rad));
		this.set(
				1,
				2,
				normVec.getY() * normVec.getZ()
						* (1.0f - (float) Math.cos(angle_rad)) - normVec.getX()
						* (float) Math.sin(angle_rad));

		this.set(
				2,
				0,
				normVec.getX() * normVec.getZ()
						* (1.0f - (float) Math.cos(angle_rad)) + normVec.getY()
						* (float) Math.sin(angle_rad));
		this.set(
				2,
				1,
				normVec.getY() * normVec.getZ()
						* (1.0f - (float) Math.cos(angle_rad)) + normVec.getX()
						* (float) Math.sin(angle_rad));
		this.set(
				2,
				2,
				normVec.getZ() * normVec.getZ()
						* (1.0f - (float) Math.cos(angle_rad))
						+ (float) Math.cos(angle_rad));

		for (int i = 0; i < 3; i++) {
			this.set(i, 3, 0.0f);
			this.set(3, i, 0.0f);
		}
		this.set(3, 3, 1.0f);
	}

	@Override
	public void angleRotation(float x, float y, float z) {
		this.set(0, 0, (float) cos(x) * (float) cos(y));
		this.set(0, 1, (float) cos(z) * (float) sin(x) * (float) sin(y)
				- (float) cos(x) * (float) sin(z));
		this.set(0, 2, (float) cos(x) * (float) cos(z) * (float) sin(y)
				+ (float) sin(x) * (float) sin(z));

		this.set(1, 0, (float) cos(y) * (float) sin(z));
		this.set(1, 1, (float) cos(x) * (float) cos(z) + (float) sin(x)
				* (float) sin(y) * (float) sin(z));
		this.set(1, 2, -(float) cos(z) * (float) sin(x) + (float) cos(x)
				* (float) sin(y) * (float) sin(z));

		this.set(2, 0, -(float) sin(y));
		this.set(2, 1, (float) cos(y) * (float) sin(z));
		this.set(2, 2, (float) cos(y) * (float) cos(z));

		this.set(0, 3, 0.0f);
		this.set(1, 3, 0.0f);
		this.set(2, 3, 0.0f);
	}

	@Override
	public void setScale(float x, float y, float z) {
		this.set(0, 0, this.get(0, 0) * x);
		this.set(1, 1, this.get(1, 1) * y);
		this.set(2, 2, this.get(2, 2) * z);
	}

	@Override
	public void setTranslation(float x, float y, float z) {
		this.set(0, 3, x);
		this.set(1, 3, y);
		this.set(2, 3, z);
	}

	@Override
	public void loadIdentity() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (i == j) {
					this.set(i, j, 1.0f);
				} else {
					this.set(i, j, 0.0f);
				}
			}
		}
	}

	@Override
	public FloatBuffer toFloatBuffer() {

		ByteBuffer vbb = ByteBuffer.allocateDirect(this.matrix.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer mVertexBuffer = vbb.asFloatBuffer();

		mVertexBuffer.put(this.get(0, 0)).put(this.get(1, 0))
				.put(this.get(2, 0)).put(this.get(3, 0));

		mVertexBuffer.put(this.get(0, 1)).put(this.get(1, 1))
				.put(this.get(2, 1)).put(this.get(3, 1));

		mVertexBuffer.put(this.get(0, 2)).put(this.get(1, 2))
				.put(this.get(2, 2)).put(this.get(3, 2));

		mVertexBuffer.put(this.get(0, 3)).put(this.get(1, 3))
				.put(this.get(2, 3)).put(this.get(3, 3));

		mVertexBuffer.position(0);

		return mVertexBuffer;
	}

	@Override
	public Matrix4f readFloatBuffer(FloatBuffer fb) {
		Matrix4f newMatrix = new MyMatrix4f();

		newMatrix.set(0, 0, fb.get());
		newMatrix.set(1, 0, fb.get());
		newMatrix.set(2, 0, fb.get());
		newMatrix.set(3, 0, fb.get());

		newMatrix.set(0, 1, fb.get());
		newMatrix.set(1, 1, fb.get());
		newMatrix.set(2, 1, fb.get());
		newMatrix.set(3, 1, fb.get());

		newMatrix.set(0, 2, fb.get());
		newMatrix.set(1, 2, fb.get());
		newMatrix.set(2, 2, fb.get());
		newMatrix.set(3, 2, fb.get());

		newMatrix.set(0, 3, fb.get());
		newMatrix.set(1, 3, fb.get());
		newMatrix.set(2, 3, fb.get());
		newMatrix.set(3, 3, fb.get());

		return newMatrix;
	}

	@Override
	public void toTranslationVector(Vector3f vec) {
		vec.set(this.get(0, 3), this.get(1, 3), this.get(2, 3));
	}
}
