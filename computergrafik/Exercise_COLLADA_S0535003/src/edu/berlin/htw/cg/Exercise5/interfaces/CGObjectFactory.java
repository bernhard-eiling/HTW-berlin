package edu.berlin.htw.cg.Exercise5.interfaces;

public interface CGObjectFactory {
	/***
	 * 
	 * return a new object of the wanted class/interface
	 */
	
	public Vector3f createVector();
	public Matrix4f createMatrix();
	public CGView3D createView();
	public CGScene createScene();
	public CGSceneNode createSceneNode();
	public CGMesh createMesh();
	public CGAnimationChannel createAnimationChannel();
	public CGAnimatedNode createAnimationNode();
	//public DSNodeSkinned createSkinnedNode();
	public CGNodeSkinned createSkinnedNode();
}
