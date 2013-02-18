package edu.berlin.htw.cg.Exercise5.interfaces;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;

public interface CGAnimationChannel {
	public void setName(String name);
	public void setKeys(int size, int stride, FloatBuffer times, FloatBuffer data);
	public void setTransformation(String BoneId, String trans);
	public void setTarget(CGSceneNode target);
	
	//void drawDEBUG(GL gl, Matrix4f parent);
	
	public String getName();
	public String getTargetName();
	public Matrix4f getTransformationForTime(double time);
	public FloatBuffer getTimes();
}
