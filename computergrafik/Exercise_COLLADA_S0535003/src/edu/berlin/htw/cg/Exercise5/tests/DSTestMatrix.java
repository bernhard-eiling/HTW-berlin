package edu.berlin.htw.cg.Exercise5.tests;


import edu.berlin.htw.cg.Exercise5.impl.S0535003ObjectFactoryImpl;
import edu.berlin.htw.cg.Exercise5.interfaces.CGMesh;
import edu.berlin.htw.cg.Exercise5.interfaces.CGObjectFactory;
import edu.berlin.htw.cg.Exercise5.interfaces.Matrix4f;
import edu.berlin.htw.cg.Exercise5.interfaces.Vector3f;

public class DSTestMatrix {

	
	public static int resID=0;
	public static int resOK=0;
	public static int resFAIL=0;
	public static void printResult(String name, boolean res){
		System.out.print("Test " + resID++);
		System.out.print(" : " + name + " --> ");
		System.out.print(res ? "OK" : "FAILED!!!");
		if(res) resOK++;
		else resFAIL++;
		System.out.println("");
	}
	
	public static void checkMeshCaps(CGMesh mesh){
		int res = resOK;
		mesh.setColors(new float[]{0,0,0});
		mesh.setNormals(new float[]{0,0,0});
		mesh.setUVCoords(new float[]{0,0});
		mesh.setTextureSource(0,"test");
		if(mesh.hasColors())resOK++; else resFAIL++;
		if(mesh.hasNormals())resOK++; else resFAIL++;
		if(mesh.hasUV())resOK++; else resFAIL++;
		System.out.println("Mesh Test: OK: " +(resOK-res) + " FAIL: " + (3-resOK+res));
	}

	public static void printSummary(){
		System.out.println("---------------------------------");
		System.out.println("Tests run:    " + resID);
		System.out.println("Tests OK:     " + resOK);
		System.out.println("Tests FAILED: " + resFAIL);
		System.out.println("---------------------------------");
	}
	/**
	 * @param args
	 */
	public static void testMatrix(CGObjectFactory fac) {

		CGObjectFactory myfac = new DSObjectFactoryImpl();
		Vector3f myvec1 = myfac.createVector();
		
		// Test Vector and MAtrix implementations
		//Test Vector
		Vector3f vec1 = fac.createVector();
		vec1.set(1, 1, 1);
		myvec1.set(1, 1, 1);
		Vector3f vec2 = vec1.mult(2);
		Vector3f myvec2 = myvec1.mult(2);
		printResult("Simple Vector", myvec2.length() == vec2.length());

		Vector3f vec3 = vec1.mult(2);
		vec3.set(-1, 0, 1);
		Vector3f myvec3 = myvec1.mult(2);
		myvec3.set(-1, 0, 1);
		Vector3f vec4 = vec2.cross(vec3);
		Vector3f myvec4 = myvec2.cross(myvec3);
		printResult("Cross Vector", myvec4.length() == vec4.length());
		
		float[] test = new float[]{1,2,3};
		vec3.get(test);
		printResult("to[] Vector", (vec3.getX() == test[0])&&(vec3.getY() == test[1])&&(vec3.getZ() == test[2]));
	
		// Matrix Test
		Matrix4f m1 = fac.createMatrix();
		Matrix4f mym1 = myfac.createMatrix();
		m1.loadIdentity();
		mym1.loadIdentity();
		m1.set(2,3,25); // set Translation z=25;
		Vector3f vec5 = fac.createVector();
		m1.toTranslationVector(vec5);
		printResult("Identity Matrix", (vec5.getX() == 0)&&(vec5.getY() == 0)&&(vec5.getZ() == 25));		

		m1.loadIdentity();
		m1.mult(2);
		mym1.mult(2);
		vec5 = m1.mult(vec3);
		myvec4 = mym1.mult(vec3);
		printResult("Mult V3 Matrix", myvec4.length() == vec5.length());
		
		//System.out.println("BREAK");
		Matrix4f m2 = m1.add(mym1);
		Matrix4f mym2 = mym1.add(mym1);
		vec5 = m2.mult(vec3);
		myvec4 = mym2.mult(vec3);
		printResult("Add   Matrix", myvec4.length() == vec5.length());
		
		vec5.set(20, 40, -60);
		vec3.set(0, 1, 1);
		m2.setAngleAxis((float)(Math.PI*0.25), vec3);
		mym2.setAngleAxis((float)(Math.PI*0.25), vec3);
		vec4 = m2.mult(vec5);
		myvec4 = mym2.mult(vec5);
		printResult("Axis  Matrix OWN", myvec4.length() == vec4.length());
		printResult("Axis  Matrix", myvec4.length() == vec5.length());
		/*
		System.out.println(vec4.length());
		System.out.println(vec5.length());
		System.out.println("m2: ");
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print( "| "+ i + ", " + j + ": " + m2.get(i, j));
			}
		}
		System.out.println("---------------");
		System.out.println("mym2: ");
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print( "| "+ i + ", " + j + ": " + mym2.get(i, j));
			}
		}
		System.out.println("------------------");
			*/

		m2.setAngleAxis((float)(Math.PI*0.25), vec3);
		mym2.readFloatBuffer(m2.toFloatBuffer());
		vec4 = m2.mult(vec5);
		myvec4 = mym2.mult(vec5);
		/*
		System.out.println("myvec4.length(): " + myvec4.length());
		System.out.println("vec4.length(): " + vec4.length());
		*/
		printResult("FB1  Matrix", myvec4.length() == vec4.length());

		mym2.setAngleAxis((float)(Math.PI*0.25), vec3);
		m2.readFloatBuffer(mym2.toFloatBuffer());
		vec4 = m2.mult(vec5);
		myvec4 = mym2.mult(vec5);
		printResult("FB2  Matrix", myvec4.length() == vec4.length());
		
		CGMesh mesh = fac.createMesh();
		checkMeshCaps(mesh);
}

}
