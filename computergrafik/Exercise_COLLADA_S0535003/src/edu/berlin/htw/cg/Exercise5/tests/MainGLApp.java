package edu.berlin.htw.cg.Exercise5.tests;

import java.awt.BorderLayout;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import edu.berlin.htw.cg.Exercise5.impl.S0535003ObjectFactoryImpl;
import edu.berlin.htw.cg.Exercise5.interfaces.CGObjectFactory;
import edu.berlin.htw.cg.Exercise5.interfaces.CGView3D;

public class MainGLApp  extends JFrame {

	public MainGLApp() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,600);
		setResizable(false);
		setTitle("Exercise COLLADA - MatrNr:S0535003");
		
		//CGObjectFactory fac = new DSObjectFactoryImpl();
		CGObjectFactory fac = new S0535003ObjectFactoryImpl();
		
		DSTestMatrix.testMatrix(fac);
		
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities glcaps = new GLCapabilities(profile);
		GLCanvas canvas = new GLCanvas(glcaps);
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		CGView3D view = fac.createView();
		canvas.addGLEventListener(view);
		canvas.addMouseListener(view);
		canvas.addKeyListener(view);
		canvas.addMouseMotionListener(view);

	}

	public static void main(String[] args) {
		MainGLApp app = new MainGLApp();
		app.setVisible(true);

	}
}
