import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TrianglePane extends JPanel {

	private static final long serialVersionUID = 7864663816262275477L;

	private int length;
	@SuppressWarnings("unused")
	private int widht;
	private int height;
	private ArrayList<Triangle> triangles;
	private int iterations;

	public TrianglePane(int width, int height, int length) { // constructor

		this.length = length;
		this.widht = width;
		this.height = height;

		this.iterations = 0;

		triangles = new ArrayList<Triangle>();

		// set panel color and size
		setSize(width, height);

		createInitialTriangle();
		triangles.addAll(createTriangles(triangles));

	}	

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}



	public void setTriangles(ArrayList<Triangle> triangles) {
		this.triangles = triangles;
	}



	private void createInitialTriangle() {
		// create big initial triangle

		// left corner
		Point p1 = new Point(0, height);
		// right corner
		Point p2 = new Point(length, height);
		// top corner
		Point p3 = new Point(length / 2, height
				- (int) (length / 2 * Math.sqrt(3)));
		
		int[] xs = { p1.x, p2.x, p3.x };
		int[] ys = { p1.y, p2.y, p3.y };
		Triangle triangle = new Triangle(xs, ys, xs.length,
				getNextColor(iterations));

//		System.out.println("initial triangle");
//		System.out.println("left corner: " + p1);
//		System.out.println("right corner: " + p2);
//		System.out.println("top corner: " + p3 + "\n");

		triangles.add(triangle);
	}

	// create 3 sub-triangles for one given triangle
	private ArrayList<Triangle> generateSubTriangles(ArrayList<Triangle> T,
			Color c) {
		ArrayList<Triangle> result = new ArrayList<Triangle>();
		for (Triangle p : T) {
			// get corners of given triangle
			Point left_corner = new Point(p.xpoints[0], p.ypoints[0]);
			Point right_corner = new Point(p.xpoints[1], p.ypoints[1]);
			Point top_corner = new Point(p.xpoints[2], p.ypoints[2]);

			// create small triangle left
			Point p11 = left_corner;
			Point p12 = new Point(left_corner.x + length, left_corner.y);
			Point p13 = new Point(left_corner.x + length / 2, left_corner.y
					- (int) (length / 2 * Math.sqrt(3)));

//			System.out.println("left triangle");
//			System.out.println("left corner: " + p11);
//			System.out.println("right corner: " + p12);
//			System.out.println("top corner: " + p13 + "\n");

			int[] xs1 = { p11.x, p12.x, p13.x };
			int[] ys1 = { p11.y, p12.y, p13.y };
			Triangle triangle1 = new Triangle(xs1, ys1, xs1.length, c);

			// create small triangle right
			Point p21 = new Point(right_corner.x - length, right_corner.y);
			Point p22 = right_corner;
			Point p23 = new Point(right_corner.x - length / 2, right_corner.y
					- (int) (length / 2 * Math.sqrt(3)));

//			System.out.println("right triangle");
//			System.out.println("left corner: " + p21);
//			System.out.println("right corner: " + p22);
//			System.out.println("top corner: " + p23 + "\n");

			int[] xs2 = { p21.x, p22.x, p23.x };
			int[] ys2 = { p21.y, p22.y, p23.y };
			Triangle triangle2 = new Triangle(xs2, ys2, xs2.length, c);

			// create small triangle top
			Point p31 = p13;
			Point p32 = p23;
			Point p33 = top_corner;

//			System.out.println("top triangle");
//			System.out.println("left corner: " + p31);
//			System.out.println("right corner: " + p32);
//			System.out.println("top corner: " + p33 + "\n");

			int[] xs3 = { p31.x, p32.x, p33.x };
			int[] ys3 = { p31.y, p32.y, p33.y };
			Triangle triangle3 = new Triangle(xs3, ys3, xs3.length, c);

			// remove old triangle and add 3 new sub-triangles instead
			result.add(triangle1);
			result.add(triangle2);
			result.add(triangle3);
		}
		return result;
	}

	// create sub triangles recursive until iteration-limit is reached
	private ArrayList<Triangle> createTriangles(ArrayList<Triangle> T) {

		ArrayList<Triangle> result = new ArrayList<Triangle>();

		length = (length / 2);
		iterations++;
		result.addAll(generateSubTriangles(T, getNextColor(iterations)));

		// TODO change number of iterations
		// colors for 7 iterations predefined
		if (iterations >= 6) {
			return result;
		} else {
			result.addAll(createTriangles(result));
		}

		return result;

	}

	// return color for the given iteration
	private Color getNextColor(int i) {
		Color result;
		// TODO pick nicer colors =D
		switch (i) {
		case 0:
			result = Color.black;
			break;
		case 1:
			result = Color.blue;
			break;
		case 2:
			result = Color.green;
			break;
		case 3:
			result = Color.red;
			break;
		case 4:
			result = Color.yellow;
			break;
		case 5:
			result = Color.gray;
			break;
		case 6:
			result = Color.orange;
			break;
		case 7:
			result = Color.magenta;
			break;
		default:
			result = Color.white;
			break;
		}

		return result;
	}

	// draw the triangles
	public void paint(Graphics g) {
//		System.out.println("drawing: " + triangles.toString());
		for (Triangle p : triangles) {
			g.setColor(p.getColor());
			g.fillPolygon(p);
//			g.setColor(Color.black);
//			g.drawPolygon(p);
		}

	}

}
