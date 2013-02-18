package edu.berlin.htw.cg.Exercise5.impl;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

import edu.berlin.htw.cg.Exercise5.interfaces.CGAnimationChannel;
import edu.berlin.htw.cg.Exercise5.interfaces.CGObjectFactory;
import edu.berlin.htw.cg.Exercise5.interfaces.CGScene;
import edu.berlin.htw.cg.Exercise5.interfaces.CGSceneNode;
import edu.berlin.htw.cg.Exercise5.interfaces.Matrix4f;
import edu.berlin.htw.cg.Exercise5.interfaces.Vector3f;

public class MyCGScene implements CGScene<GL2> {

	Vector3f camPos = new MyVector3f();
	Vector3f camDir = new MyVector3f();
	CGObjectFactory fac;
	Matrix4f drawMatrix = new MyMatrix4f();
	GLUT glut = new GLUT();
	int w = 800;
	int h = 600;

	int time = 0;
	
	CGSceneNode<GL2> root = new MyCGSceneNode();
	GL2 gl;
	GLU glu = GLU.createGLU();

	MyCGScene() {
	}

	@Override
	public void init(GL2 gl) {
		
		this.gl = gl;
		root.init(this);
		root.getParent().getWorldMatrix().loadIdentity();
		
	}

	@Override
	public CGObjectFactory getObjectFactory() {
		return fac;
	}

	@Override
	public void setObjectFactory(CGObjectFactory fac) {
		this.fac = fac;
	}

	@Override
	public void setCamPos(Vector3f pos) {
		this.camPos = pos;

	}

	@Override
	public void setCamDir(Vector3f dir) {
		this.camDir = dir;
		

	}

	@Override
	public Vector3f getCamPos() {
		return camPos;
	}

	@Override
	public Vector3f getCamDir() {
		return camDir;
	}

	@Override
	public GL2 getGL() {
		return this.gl;
	}

	@Override
	public int addLight(Vector3f lightPos, Vector3f color, float intensity) {
		return 0;
	}

	@Override
	public void addAnimation(CGAnimationChannel anim) {

	}

	@Override
	public double getMaxtime() {
		return 0;
	}

	@Override
	public void update(double time) {
		time++;
		this.root.update(time);
		
	}

	@Override
	public CGSceneNode<GL2> getRoot() {
		return this.root;
	}

	@Override
	public void draw(GL2 gl) {
		
		glu.gluLookAt(this.camPos.getX(), this.camPos.getY(), this.camPos.getZ(), camDir.getX(), camDir.getY(), camDir.getZ(), 0.0f, 1.0f, 0.0f);
		
		
		float noAmbient[] = {0.f, 0.f, 0.f, 1.f};
		float whiteDiffuse[] = {0.2f, 0.2f, 0.2f, 1.0f};
		float specular[] = {1.f, 1.f, 1.f, 1.f};
		float position[] = {40.0f, 10.0f, 0.0f, 1.0f};
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, noAmbient,0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, whiteDiffuse,0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specular, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position,0);
		gl.glEnable(GL2.GL_LIGHT0);
		
		gl.glColorMaterial( GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE ) ;
		gl.glEnable( GL2.GL_COLOR_MATERIAL ) ;
		
		
		drawMatrix.loadIdentity();
		this.root.draw(drawMatrix);
	}

}
