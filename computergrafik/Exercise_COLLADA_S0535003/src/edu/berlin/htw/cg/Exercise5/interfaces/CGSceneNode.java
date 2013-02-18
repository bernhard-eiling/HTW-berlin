package edu.berlin.htw.cg.Exercise5.interfaces;

import javax.media.opengl.GL;

public interface CGSceneNode<G> extends CGSceneObject<G>{
	// A node has exactly ONE parent (tree!)
	// gets called from COLLADA
	void setParent(CGSceneNode<G> parent);
	public CGSceneNode<G> getParent();
	
	// COLLADA needs this for whatever reason, just implement
	void setShortName(String name);
	
	//can rotate translate scale RELATIVE to parent CO-System
	// both get called from COLLADA
	void setLocalMatrix(Matrix4f mat);
	Matrix4f getLocalMatrix();
	
	//same as above but in World CO 
	//--> regularly calculated in update()
	// and used in draw()
	// both not used by COLLADA
	void setWorldMatrix(Matrix4f mat);
	Matrix4f getWorldMatrix();
	
	//can have meshes or not
	// gets called from COLLADA
	void adddMesh(CGMesh<G> mesh);

	//has a list of children
	// gets called from COLLADA
	void addChildNode(CGSceneNode<G> node);

	// gets ANY child node with the name (or short name) id
	// this should go RECURSIVELY through all children!!
	// So: root->getChildNode(id) will return a node id 
	// if this is present SOMEWHERE in the tree
	// gets called from COLLADA
	CGSceneNode<G> getChildNode(String id);
	
	}
