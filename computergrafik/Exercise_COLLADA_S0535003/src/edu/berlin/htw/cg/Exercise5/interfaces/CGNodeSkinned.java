package edu.berlin.htw.cg.Exercise5.interfaces;

import java.util.List;

import edu.berlin.htw.cg.Exercise5.utilities.DSVertexWeights;

public interface CGNodeSkinned<G> extends CGSceneNode<G> {
	public void setJoints(List<String> joints);
	public void setInverseMatrices(List<Matrix4f> mats);
	public void setWeights(List<DSVertexWeights> weights);
	public void setBindTransform(Matrix4f trans);
}
