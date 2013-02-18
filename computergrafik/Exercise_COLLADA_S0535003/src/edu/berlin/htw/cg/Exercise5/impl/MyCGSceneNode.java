package edu.berlin.htw.cg.Exercise5.impl;

import java.util.ArrayList;
import java.util.Iterator;

import javax.media.opengl.GL2;

import edu.berlin.htw.cg.Exercise5.interfaces.CGMesh;
import edu.berlin.htw.cg.Exercise5.interfaces.CGScene;
import edu.berlin.htw.cg.Exercise5.interfaces.CGSceneNode;
import edu.berlin.htw.cg.Exercise5.interfaces.Matrix4f;

public class MyCGSceneNode implements CGSceneNode<GL2> {

	CGSceneNode<GL2> parent;
	Matrix4f localMatrix = new MyMatrix4f();
	Matrix4f worldMatrix = new MyMatrix4f();
	CGMesh<GL2> mesh = new MyCGMesh();
	ArrayList<CGSceneNode<GL2>> childNodes = new ArrayList<CGSceneNode<GL2>>();
	GL2 gl;
	int id;
	String name;
	String shortName;

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public void init(CGScene<GL2> scene) {
		gl = scene.getGL();
		Iterator<CGSceneNode<GL2>> iterChildNodes = childNodes.iterator();
		while (iterChildNodes.hasNext()) {
			CGSceneNode<GL2> currentNode = iterChildNodes.next();
			currentNode.init(scene);
		}
	}

	@Override
	public void update(double time) {
		
		Iterator<CGSceneNode<GL2>> iterChildNodes = childNodes.iterator();
		while (iterChildNodes.hasNext()) {
			CGSceneNode<GL2> currentNode = iterChildNodes.next();
			currentNode.update(time);
		}
		
		this.worldMatrix = this.parent.getWorldMatrix().mult(this.localMatrix);
	}

	@Override
	public void draw(Matrix4f world) {
		
		gl.glPushMatrix();
		
		gl.glMultMatrixf(worldMatrix.toFloatBuffer());
		
		this.mesh.draw(gl);
		
		gl.glPopMatrix();
		
		Iterator<CGSceneNode<GL2>> iterChildNodes = childNodes.iterator();
		while (iterChildNodes.hasNext()) {
			CGSceneNode<GL2> currentNode = iterChildNodes.next();
			currentNode.draw(world);
		}
	}

	@Override
	public void finish() {

	}

	@Override
	public void setParent(CGSceneNode<GL2> parent) {
		this.parent = parent;

	}

	@Override
	public CGSceneNode<GL2> getParent() {
		return parent;
	}

	@Override
	public void setShortName(String name) {
		this.shortName = name;

	}

	@Override
	public void setLocalMatrix(Matrix4f mat) {
		this.localMatrix = mat;

	}

	@Override
	public Matrix4f getLocalMatrix() {
		return this.localMatrix;
	}

	@Override
	public void setWorldMatrix(Matrix4f mat) {
		this.worldMatrix = mat;

	}

	@Override
	public Matrix4f getWorldMatrix() {
		return this.worldMatrix;
	}

	@Override
	public void adddMesh(CGMesh<GL2> mesh) {
		this.mesh = mesh;

	}

	@Override
	public void addChildNode(CGSceneNode<GL2> node) {
		this.childNodes.add(node);
		node.setParent(this);
	}

	@Override
	public CGSceneNode<GL2> getChildNode(String id) {
		Iterator<CGSceneNode<GL2>> iterChildNodes = childNodes.iterator();
		while (iterChildNodes.hasNext()) {
			CGSceneNode<GL2> currentNode = iterChildNodes.next();
			if (currentNode.getName() == id) {
				return currentNode;
			}
		}
		System.out.println("No childNode found!");
		return null;
	}
}
