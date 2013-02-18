package edu.berlin.htw.cg.Exercise5.impl;

import edu.berlin.htw.cg.Exercise5.dsimpl.DSAnimationChannelImpl;
import edu.berlin.htw.cg.Exercise5.dsimpl.DSMeshImpl;
import edu.berlin.htw.cg.Exercise5.dsimpl.DSNodeSkinnedImpl;
import edu.berlin.htw.cg.Exercise5.dsimpl.DSSceneImpl;
import edu.berlin.htw.cg.Exercise5.dsimpl.DSSceneNodeImpl;
import edu.berlin.htw.cg.Exercise5.dsimpl.DSView3D;
import edu.berlin.htw.cg.Exercise5.dsimpl.Matrix4fImpl;
import edu.berlin.htw.cg.Exercise5.dsimpl.Vector3fImpl;
import edu.berlin.htw.cg.Exercise5.interfaces.CGAnimatedNode;
import edu.berlin.htw.cg.Exercise5.interfaces.CGAnimationChannel;
import edu.berlin.htw.cg.Exercise5.interfaces.CGMesh;
import edu.berlin.htw.cg.Exercise5.interfaces.CGNodeSkinned;
import edu.berlin.htw.cg.Exercise5.interfaces.CGObjectFactory;
import edu.berlin.htw.cg.Exercise5.interfaces.CGScene;
import edu.berlin.htw.cg.Exercise5.interfaces.CGSceneNode;
import edu.berlin.htw.cg.Exercise5.interfaces.CGView3D;
import edu.berlin.htw.cg.Exercise5.interfaces.Matrix4f;
import edu.berlin.htw.cg.Exercise5.interfaces.Vector3f;

public class S0535003ObjectFactoryImpl implements CGObjectFactory {

	@Override
	public Vector3f createVector() {
		return new MyVector3f();
	}

	@Override
	public Matrix4f createMatrix() {
		return new MyMatrix4f();
	}

	@Override
	public CGScene createScene() {
		return new MyCGScene();
	}

	@Override
	public CGSceneNode createSceneNode() {
		return new MyCGSceneNode();
	}

	@Override
	public CGMesh createMesh() {
		return new MyCGMesh();
	}

	@Override
	public CGAnimationChannel createAnimationChannel() {
		return null;
		//return new DSAnimationChannelImpl();
	}

	@Override
	public CGAnimatedNode createAnimationNode() {
		return null;
		//return new DSSceneNodeImpl();
	}

	public CGView3D createView() {
		return new MyCGView3D();
	}

	@Override
	public CGNodeSkinned createSkinnedNode() {
		return null;
		//return new DSNodeSkinnedImpl();
	}

}
