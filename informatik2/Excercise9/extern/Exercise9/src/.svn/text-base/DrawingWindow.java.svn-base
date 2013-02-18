import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

//TODO: resize-method, mit rundungsfehlern umgehen (vllt nur 2 potenzen für das erste Dreieck als Seitenlänge benützen), 

public class DrawingWindow extends JFrame {

	private static final long serialVersionUID = 7936890149192918140L;
	
	private Dimension screenSize;
	private int s_width;
	private int s_height;

	private TrianglePane t;	
	
	public DrawingWindow() {
		super("Triangle Drawing Window");		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize);
		s_width = screenSize.width;
		s_height = screenSize.height-40;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	    addComponentListener(new ComponentAdapter()
	    {
	    @Override
	    public void componentResized(ComponentEvent e)
	    {
	    super.componentResized(e);
	    reSizeWindow();	  
	    }
	    });
		
	
		int max_h = (int)(s_width/2*Math.sqrt(3));
		
		if (max_h <= s_height) {

			// only the case if screen width is smaller than screen height
			// TODO maybe not working right
			
			// create triangle with screenSize.getWidth() and max_h
			// side length = width
			TrianglePane t = new TrianglePane(s_width, s_height, s_width);
			this.setContentPane(t);
			
		} else {
		
			int max_w = (int)(s_height*2/Math.sqrt(3));
			
			// default case for anything from quadratic to wide-screen monitors
			// create triangle with screenSize.getHeight() and max_w
			// side length = max_w
			TrianglePane t = new TrianglePane(s_width, s_height, max_w);
			this.t = t;
			this.setContentPane(t);
			
		}
	
	}
	
	public static void main(String[] arguments) {
		
			DrawingWindow DW = new DrawingWindow();
			DW.setVisible(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	}
	

		
	public void reSizeWindow(){
		s_width = this.getWidth()-20;
		s_height = this.getHeight()-40;		
	
		int max_h = (int)(s_width/2*Math.sqrt(3));
		
		if (max_h <= s_height) {
			TrianglePane t = new TrianglePane(s_width, s_height, s_width);
			this.setContentPane(t);
			
		} else {
		
			int max_w = (int)(s_height*2/Math.sqrt(3));			
			TrianglePane t = new TrianglePane(s_width, s_height, max_w);
			this.t = t;
			this.setContentPane(t);			
		}
	}

		
		
				
		
}
	

