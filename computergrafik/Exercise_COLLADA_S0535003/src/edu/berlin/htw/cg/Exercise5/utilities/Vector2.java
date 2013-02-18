package edu.berlin.htw.cg.Exercise5.utilities;

public class Vector2 {

	float x, y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(double x, double y) {
		this.x = (float)x;
		this.y = (float)y;
	}
	
	public String toString() {
		return "(" + this.x + " , " + this.y + ")";
	}

	/**
	 * Multipliziert den Vektor mit einem Skalar.
	 * @param scalar: Skalierungswert
	 */
	public void multiplyScalar (float scalar){
		this.x *= scalar;
		this.y *= scalar;
	}
	
	/**
	 * Addiert einen Vektor zum Vektor
	 * @param vectorToAdd: Vektor der addiert werden soll
	 * @return: Gibt den Ergebnisvektor zurück
	 */
	public Vector2 addVector (Vector2 vectorToAdd) {
		Vector2 returnVector = new Vector2(
									this.x + vectorToAdd.getDeltaX(),
									this.y + vectorToAdd.getDeltaY());
		return returnVector;
	}
	
	/**
	 * Subtrahiert einen Vektor vom Vektor
	 * @param vectorToSubtract: Vektor der subtrahiert werden soll
	 * @return: Gibt den Ergebnisvektor zurück
	 */
	public Vector2 subtractVector (Vector2 vectorToSubtract) {
		Vector2 returnVector = new Vector2(
									this.x - vectorToSubtract.getDeltaX(),
									this.y - vectorToSubtract.getDeltaY());
		return returnVector;
	}

	
	public float getDeltaX() {
		return x;
	}

	
	public float getDeltaY() {
		return y;
	}

	public float length() {
		// TODO Auto-generated method stub
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public static float entfernung(Vector2 a , Vector2 b) {
		Vector2 d = a.subtractVector(b);
		return d.length();
	}
	
	public static float dot(Vector2 a, Vector2 b){
		float dd = 0;
		dd = a.length() * b.length();
		if(dd <= 0.0000f) return 1.0f;
		return (a.x*b.x + a.y*b.y)/dd;
	}
	
	public static float crossZ(Vector2 a, Vector2 b){
		// Just calculate a Z value for the 3D vectors (ax,ay,0) (bx,by,0)
		return (a.x*b.y - a.y*b.x);
	}
	

}