package edu.berlin.htw.cg.Exercise5.interfaces;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public interface CGScene<G> {
	/*******************************
	 * 
	 * A complete 3D scene as loaded from COLLADA
	 * Including the camera Position, direction and lighting
	 */
	
	// Do initialisation, if necessary
	//public void init(Object gl);
	public void init(G gl);
	
	// handle the unique factory object for this scene
	// gets SET by COLLADA importer
	public CGObjectFactory getObjectFactory();
	public void setObjectFactory(CGObjectFactory fac);
	
	//Holds the hierarchical view on the whole scene, including camera and lights
	
	// gets set by COLLADA, if Camera is present
	public void setCamPos(Vector3f pos);
	public void setCamDir(Vector3f dir);
	
	public Vector3f  getCamPos();
	public Vector3f  getCamDir();
	
	public <G> G getGL();
	
	// gets called by COLLADA, if lights are present in the scene
	public int addLight(Vector3f lightPos, Vector3f color, float intensity); //returns ID for this light
	
	// gets called by COLLADA, can be ignored or
	// get used to find a value for getMaxTime() below
	public void addAnimation(CGAnimationChannel anim);
	// return the longest animation time in this scene
	// comes handy, if you want to LOOP your time
	//not needed for COLLADA
	public double getMaxtime();
	
	// main update loop, should call root->update()
	public void update(double time);
	
	// should call root->draw() and set light, camera
	// clearing of the screen should go here
	// setting of glu.gluLookAt() Matrix
	// call root->update() root->draw(mat) with mat == IDENTITY MATRIX (not used yet)
	public void draw(G gl);
	
	// the ONE root-Node of our scene tree!! Must be created by scene (ini init() e.g.)
	public CGSceneNode getRoot();
	
	
	
}
