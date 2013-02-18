package edu.berlin.htw.cg.Exercise5.interfaces;

public interface CGSceneObject<G> {
	//Each object has a name
	// gets called from COLLADA
	void setName(String name);
	public String getName();

	//has ID (relativ zu DSScene, daher von dieser zu vergeben)
	//not used by COLLADA
	int getID();

	//LIFETIME of an object, not all methods 
	// need to implement something meaningful 
	// for all derived classes!
	// not used by COLLADA

	//initialize object settings 
	public void init(CGScene<G> scene);
	// update all inner states for the object (called from main loop)
	void update(double time);
	// render the updated object (called from main loop)
	void draw(Matrix4f world);
	// do cleanup, if necessary
	public void finish();
}
