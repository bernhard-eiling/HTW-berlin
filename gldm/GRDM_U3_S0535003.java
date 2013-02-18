import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import static java.lang.Math.*;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
     Opens an image window and adds a panel below the image
 */
public class GRDM_U3_S0535003 implements PlugIn {

	ImagePlus imp; // ImagePlus object
	private int[] origPixels;
	private int width;
	private int height;

	String[] items = {"Original", "Rot-Kanal", "Negativ", "Graustufen", "Binaerbild2", "Binaerbild5", "Binaerbild10", "Sepia", "Sechs Farben"};


	public static void main(String args[]) {

		IJ.open("C:/Dropbox/HTW Berlin/GDM/ImageJ/_images/Bear.jpg");
		//IJ.open("Z:/Pictures/Beispielbilder/orchid.jpg");

		GRDM_U3_S0535003 pw = new GRDM_U3_S0535003();
		pw.imp = IJ.getImage();
		pw.run("");
	}

	public void run(String arg) {
		if (imp==null) 
			imp = WindowManager.getCurrentImage();
		if (imp==null) {
			return;
		}
		CustomCanvas cc = new CustomCanvas(imp);

		storePixelValues(imp.getProcessor());

		new CustomWindow(imp, cc);
	}


	private void storePixelValues(ImageProcessor ip) {
		width = ip.getWidth();
		height = ip.getHeight();

		origPixels = ((int []) ip.getPixels()).clone();
	}


	class CustomCanvas extends ImageCanvas {

		CustomCanvas(ImagePlus imp) {
			super(imp);
		}

	} // CustomCanvas inner class


	class CustomWindow extends ImageWindow implements ItemListener {

		private String method;
		
		CustomWindow(ImagePlus imp, ImageCanvas ic) {
			super(imp, ic);
			addPanel();
		}

		void addPanel() {
			//JPanel panel = new JPanel();
			Panel panel = new Panel();

			JComboBox cb = new JComboBox(items);
			panel.add(cb);
			cb.addItemListener(this);

			add(panel);
			pack();
		}

		public void itemStateChanged(ItemEvent evt) {

			// Get the affected item
			Object item = evt.getItem();

			if (evt.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("Selected: " + item.toString());
				method = item.toString();
				changePixelValues(imp.getProcessor());
				imp.updateAndDraw();
			} 

		}


		private void changePixelValues(ImageProcessor ip) {

			// Array zum Zurückschreiben der Pixelwerte
			int[] pixels = (int[])ip.getPixels();

			if (method.equals("Original")) {

				for (int y=0; y<height; y++) {
					for (int x=0; x<width; x++) {
						int pos = y*width + x;
						
						pixels[pos] = origPixels[pos];
					}
				}
			}
			
			if (method.equals("Rot-Kanal")) {

				for (int y=0; y<height; y++) {
					for (int x=0; x<width; x++) {
						int pos = y*width + x;
						int argb = origPixels[pos];  // Lesen der Originalwerte 

						int r = (argb >> 16) & 0xff;
						//int g = (argb >>  8) & 0xff;
						//int b =  argb        & 0xff;

						int rn = r;
						int gn = 0;
						int bn = 0;

						// Hier muessen die neuen RGB-Werte wieder auf den Bereich von 0 bis 255 begrenzt werden

						pixels[pos] = (0xFF<<24) | (rn<<16) | (gn<<8) | bn;
					}
				}
			}
			
			if (method.equals("Negativ")) {

				for (int y=0; y<height; y++) {
					for (int x=0; x<width; x++) {
						int pos = y*width + x;
						
						int argb = origPixels[pos];  // Lesen der Originalwerte 

						int r = (argb >> 16) & 0xff;
						int g = (argb >>  8) & 0xff;
						int b =  argb        & 0xff;
						
						r = abs(r - 255);
						g = abs(g - 255);
						b = abs(b - 255);

						// Hier muessen die neuen RGB-Werte wieder auf den Bereich von 0 bis 255 begrenzt werden

						pixels[pos] = (0xFF<<24) | (r<<16) | (g<<8) | b;
					}
				}
			}
			
			if (method.equals("Graustufen")) {

				for (int y=0; y<height; y++) {
					for (int x=0; x<width; x++) {
						int pos = y*width + x;
						
						int argb = origPixels[pos];  // Lesen der Originalwerte 

						int r = (argb >> 16) & 0xff;
						int g = (argb >>  8) & 0xff;
						int b =  argb        & 0xff;
						
						r = g = b = (r + g + b) / 3;

						// Hier muessen die neuen RGB-Werte wieder auf den Bereich von 0 bis 255 begrenzt werden

						pixels[pos] = (0xFF<<24) | (r<<16) | (g<<8) | b;
					}
				}
			}
			
			if (method.equals("Binaerbild2")) {

				for (int y=0; y<height; y++) {
					for (int x=0; x<width; x++) {
						int pos = y*width + x;
						
						int argb = origPixels[pos];  // Lesen der Originalwerte 

						int r = (argb >> 16) & 0xff;
						int g = (argb >>  8) & 0xff;
						int b =  argb        & 0xff;
						
						r = g = b = (r + g + b) / 3;
						
						if (r >= 128) {
							r = g = b = 255;
						} else {
							r = g = b = 0;
						}

						// Hier muessen die neuen RGB-Werte wieder auf den Bereich von 0 bis 255 begrenzt werden

						pixels[pos] = (0xFF<<24) | (r<<16) | (g<<8) | b;
					}
				}
			}
			
			if (method.equals("Binaerbild5")) {

				for (int y=0; y<height; y++) {
					for (int x=0; x<width; x++) {
						int pos = y*width + x;
						
						int argb = origPixels[pos];  // Lesen der Originalwerte 

						int r = (argb >> 16) & 0xff;
						int g = (argb >>  8) & 0xff;
						int b =  argb        & 0xff;
						
						r = g = b = (r + g + b) / 3;
						
						int step = 255/5;
						
						for(int i = 0; i < 256; i += step) {
							if (r >= i && r < (i + step)) {
								r = g = b = i + step/2;
								break;
							}
						}	

						// Hier muessen die neuen RGB-Werte wieder auf den Bereich von 0 bis 255 begrenzt werden

						pixels[pos] = (0xFF<<24) | (r<<16) | (g<<8) | b;
					}
				}
			}
			
			if (method.equals("Binaerbild10")) {

				for (int y=0; y<height; y++) {
					for (int x=0; x<width; x++) {
						int pos = y*width + x;
						
						int argb = origPixels[pos];  // Lesen der Originalwerte 

						int r = (argb >> 16) & 0xff;
						int g = (argb >>  8) & 0xff;
						int b =  argb        & 0xff;
						
						r = g = b = (r + g + b) / 3;
						
						int step = 255/10;
						
						for(int i = 0; i < 256; i += step) {
							if (r >= i && r < (i + step)) {
								r = g = b = i + step/2;
								break;
							}
						}	

						// Hier muessen die neuen RGB-Werte wieder auf den Bereich von 0 bis 255 begrenzt werden

						pixels[pos] = (0xFF<<24) | (r<<16) | (g<<8) | b;
					}
				}
			}
			
			if (method.equals("Sepia")) {

				for (int y=0; y<height; y++) {
					for (int x=0; x<width; x++) {
						int pos = y*width + x;
						
						int argb = origPixels[pos];  // Lesen der Originalwerte 

						int r = (argb >> 16) & 0xff;
						int g = (argb >>  8) & 0xff;
						int b =  argb        & 0xff;
						
						r = g = b = (r + g + b) / 3;
						r += 30;
						g += 10;

						// Hier muessen die neuen RGB-Werte wieder auf den Bereich von 0 bis 255 begrenzt werden

						pixels[pos] = (0xFF<<24) | (r<<16) | (g<<8) | b;
					}
				}
			}
			
		}


	} // CustomWindow inner class
} 
