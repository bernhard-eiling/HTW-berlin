package eiling.info2.exercise9;

import java.awt.Dimension;
import static java.lang.Math.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

class MyCanvas extends JComponent {
	
	
	public void paint(Graphics g) {
		
		int top = (int) (abs(200 - (sqrt(3) / 2) * 200));
		Point p1 = new Point(100, top);
		Point p2 = new Point(0, 200);
		Point p3 = new Point(200, 200);
		drawTri(g, p1, p2, p3);
		drawSierpinski(5, g, p1, p2, p3);
	}

	public void drawSierpinski(int counter, Graphics g, Point p1, Point p2,
			Point p3) {
		System.out.println(counter);
		Point mid1 = midpoint(p1, p2);
		Point mid2 = midpoint(p2, p3);
		Point mid3 = midpoint(p3, p1);

		int[] xCoo = new int[] { mid1.x, mid2.x, mid3.x };
		int[] yCoo = new int[] { mid1.y, mid2.y, mid3.y };

		Polygon middle = new Polygon(xCoo, yCoo, 3);
		g.setColor(Color.green);
		g.fillPolygon(middle);

		g.setColor(Color.black);
		drawTri(g, p1, mid1, mid3);
		drawTri(g, mid1, p2, mid2);
		drawTri(g, mid3, mid2, p3);

		counter--;
		if (counter > 0) {

			drawSierpinski(counter, g, p1, mid1, mid3);
			drawSierpinski(counter, g, mid1, p2, mid2);
			drawSierpinski(counter, g, mid3, mid2, p3);

			return;
		}
	}

	public void drawTri(Graphics g, Point p1, Point p2, Point p3) {
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		g.drawLine(p2.x, p2.y, p3.x, p3.y);
		g.drawLine(p3.x, p3.y, p1.x, p1.y);
		return;
	}

	public Point midpoint(Point p1, Point p2) {
		int x = (p1.x + p2.x) / 2;
		int y = (p1.y + p2.y) / 2;

		return new Point(x, y);
	}

}

public class Main {
	public static void main(String[] a) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();

		JFrame window = new JFrame();
		window.setLayout(new BorderLayout());
		window.setBounds(d.width / 4, d.height / 4, d.width / 2, d.height / 2);
		MyCanvas canvas = new MyCanvas();
		
		window.getContentPane().add(canvas);

		window.setVisible(true);
		

	}

}
