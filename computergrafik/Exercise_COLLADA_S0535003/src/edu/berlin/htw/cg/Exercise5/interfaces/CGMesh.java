package edu.berlin.htw.cg.Exercise5.interfaces;

import javax.media.opengl.GL2;

public interface CGMesh<G> {
	/****
	 * 
	 * A small helper class for handling materials
	 *
	 */
	public static class CGMaterial{
		public boolean isLambert = false;
		public Vector3f diffuse;
		public Vector3f ambient;
		public Vector3f specular;
		public float intensity = 5;
	}
	
	/*****
	 * interface for creation, CALLED by COLLADA
	 */
	void setVertices(float[] verts);
	void setIndices(int[] inds);
	void setNormals(float[] norms);
	void setColors(float[] cols);
	
	// either GL.GL_TRIANGLES or GL.GL_QUADS
	// both used by COLLADA
	void setDrawType(int type);
	void setUVCoords(float[] uv);
	
	// used by COLLADA
	void setMaterial (CGMaterial mat);
	
	// used by COLLADA
	public void setTextureSource(int glTexCoordSet, String filename);

	/*****
	 * END of interface for creation, CALLED by COLLADA
	 */

	// Getter methods
	float[] getVertices();
	float[] getNormals();
	
	//if drawType == GL.GL_QUADS
	boolean isQuads();
	
	boolean hasNormals();
	boolean hasColors();
	boolean hasUV();
	
	// the actual drawing of this mesh. Coordinate System must be set by CGSceneNode
	public void draw(G gl);
}
