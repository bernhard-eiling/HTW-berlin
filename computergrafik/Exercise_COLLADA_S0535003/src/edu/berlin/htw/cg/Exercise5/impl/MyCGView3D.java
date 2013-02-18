package edu.berlin.htw.cg.Exercise5.impl;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import static java.lang.Math.*;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.FPSAnimator;

import edu.berlin.htw.cg.Exercise5.interfaces.CGObjectFactory;
import edu.berlin.htw.cg.Exercise5.interfaces.CGScene;
import edu.berlin.htw.cg.Exercise5.interfaces.CGView3D;
import edu.berlin.htw.cg.Exercise5.interfaces.Vector3f;
import edu.berlin.htw.cg.Exercise5.utilities.ColladaImporter;

public class MyCGView3D implements CGView3D {

	float[] cameraPos = { 0, 500, 500 };
	int w = 800;
	int h = 600;
	CGObjectFactory fac = new S0535003ObjectFactoryImpl();
	CGScene<GL2> myScene;
	Vector3f camDir = new MyVector3f();
	Vector3f dir = new MyVector3f();
	Vector3f dirX = new MyVector3f();
	Vector3f dirZ = new MyVector3f();
	Vector3f camPos = fac.createVector();
	ColladaImporter importer = new ColladaImporter();
	int time = 0;
	int currentMouseX = 0;
	int currentMouseY = 0;
	int lastMouseX = 0;
	int lastMouseY = 0;
	double yaw = 0;
	double pitch = 0;
	float forward = 0.0f;
	float side = 0.0f;

	@Override
	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		time++;
		
		dirX.set(0.0f, 1.0f, 0.0f);
		dirX = dirX.cross(dir);
		dirX.setLength(side);
		
		dirZ.set(dir.getX(), dir.getY(), dir.getZ());
		dirZ.setLength(forward);
		 
		this.camPos = camPos.addVector(dirZ);
		this.camPos = camPos.addVector(dirX);
		
		myScene.setCamDir(dir);
		myScene.setCamPos(camPos);
		
		myScene.update(time);
		myScene.draw(gl);

	}

	@Override
	public void dispose(GLAutoDrawable arg0) {

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = GLU.createGLU();

		gl.glViewport(0, 0, w, h);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		glu.gluPerspective(45.0f, w / (float) h, 0.1f, 3000.0f);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		FPSAnimator anim = new FPSAnimator(drawable, 24);
		anim.start();

		myScene = importer.readScene("./Data/stillscene1b.dae", fac);
		myScene.setObjectFactory(fac);
		myScene.init(gl);

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent keyE) {
		if (keyE.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		// left
		if (keyE.getKeyCode() == KeyEvent.VK_A) {
			side = -1.0f;
		}
		// right
		if (keyE.getKeyCode() == KeyEvent.VK_D) {
			side = 1.0f;
		}
		// up
		if (keyE.getKeyCode() == KeyEvent.VK_W) {
			forward = 1.0f;
		}
		// down
		if (keyE.getKeyCode() == KeyEvent.VK_S) {
			forward = -1.0f;
		}

	}

	@Override
	public void keyReleased(KeyEvent keyE) {
		// left
		if (keyE.getKeyCode() == KeyEvent.VK_A) {
			side = 0.0f;
		}
		// right
		if (keyE.getKeyCode() == KeyEvent.VK_D) {
			side = 0.0f;
		}
		// up
		if (keyE.getKeyCode() == KeyEvent.VK_W) {
			forward = 0.0f;
		}
		// down
		if (keyE.getKeyCode() == KeyEvent.VK_S) {
			forward = 0.0f;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent mouse) {
		
		lastMouseX = currentMouseX;
		lastMouseY = currentMouseY;
		currentMouseX = mouse.getX();
		currentMouseY = mouse.getY();
		
		yaw -= (currentMouseX - lastMouseX) * 0.01;
		pitch += (currentMouseY - lastMouseY) * 0.01;
		
		double range = PI / 2;
		if (pitch > range) {
			pitch = range;
		}
		if (pitch < -range) {
			pitch = -range;
		}
		
		float dirX = (float) sin(yaw) * (float) cos(pitch);
		float dirY = (float) -sin(pitch);
		float dirZ = (float) cos(yaw) * (float) cos(pitch);
		dir.set(dirX, dirY, dirZ);
		
		
	}

}
