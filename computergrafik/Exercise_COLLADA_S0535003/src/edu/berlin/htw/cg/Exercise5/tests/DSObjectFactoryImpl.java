package edu.berlin.htw.cg.Exercise5.tests;

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

public class DSObjectFactoryImpl implements CGObjectFactory {

	@Override
	public Vector3f createVector() {
		return new Vector3fImpl();
	}

	@Override
	public Matrix4f createMatrix() {
		return new Matrix4fImpl();
	}

	@Override
	public CGScene createScene() {
		return new DSSceneImpl();
	}

	@Override
	public CGSceneNode createSceneNode() {
		return new DSSceneNodeImpl();
	}

	@Override
	public CGMesh createMesh() {
		return new DSMeshImpl();
	}

	@Override
	public CGAnimationChannel createAnimationChannel() {
		return new DSAnimationChannelImpl();
	}

	@Override
	public CGAnimatedNode createAnimationNode() {
		return new DSSceneNodeImpl();
	}

	public CGView3D createView() {
		// TODO Auto-generated method stub
		return new DSView3D();
	}

	@Override
	public CGNodeSkinned createSkinnedNode() {
		// TODO Auto-generated method stub
		return new DSNodeSkinnedImpl();
	}

}
