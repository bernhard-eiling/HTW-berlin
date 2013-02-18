package edu.berlin.htw.cg.Exercise5.utilities;

public class DSVertexWeights {
	public static final int MAXWEIGHTS = 6;
	public float[] weight = new float[MAXWEIGHTS]; 
	public int[] jointIndex = new int[MAXWEIGHTS];
	public DSVertexWeights(){
		for(int i=0;i<MAXWEIGHTS;i++)jointIndex[i] = -1;
	}

}
