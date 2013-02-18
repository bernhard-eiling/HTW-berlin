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

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static java.lang.Math.*;

/**
     Opens an image window and adds a panel below the image
*/
public class GRDM_U2_S0535003 implements PlugIn {

    ImagePlus imp; // ImagePlus object
	private int[] origPixels;
	private int width;
	private int height;
	
	
    public static void main(String args[]) {
		//new ImageJ();

    	IJ.open("C:/Dropbox/HTW Berlin/GDM/ImageJ/_images/orchid.jpg");
		
		GRDM_U2_S0535003 pw = new GRDM_U2_S0535003();
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
    
    
    class CustomWindow extends ImageWindow implements ChangeListener {
         
        private JSlider jSliderBrightness;
		private JSlider jSliderContrast;
		private JSlider jSliderSaturation;
		private JSlider jSLiderHue;
		private double brightness = 0;
		private double contrast = 1;
		private double saturation = 1;
		private double hue = 0;

		CustomWindow(ImagePlus imp, ImageCanvas ic) {
            super(imp, ic);
            addPanel();
        }
    
        void addPanel() {
        	//JPanel panel = new JPanel();
        	Panel panel = new Panel();

            panel.setLayout(new GridLayout(4, 1));
            jSliderBrightness = makeTitledSilder("Helligkeit", -128, 128, 0);
            jSliderContrast = makeTitledSilder("Kontrast", 0, 10, 1);
            
            jSliderSaturation = makeTitledSilder("Saettigung", 0, 5, 1);
            jSLiderHue = makeTitledSilder("Farbton", 0, 360, 0);
            
            panel.add(jSliderBrightness);
            panel.add(jSliderContrast);
            
            panel.add(jSliderSaturation);
            panel.add(jSLiderHue);
            
            
            add(panel);
            
            pack();
         }
      
        private JSlider makeTitledSilder(String string, int minVal, int maxVal, int val) {
		
        	JSlider slider = new JSlider(JSlider.HORIZONTAL, minVal, maxVal, val );
        	Dimension preferredSize = new Dimension(width, 50);
        	slider.setPreferredSize(preferredSize);
			TitledBorder tb = new TitledBorder(BorderFactory.createEtchedBorder(), 
					string, TitledBorder.LEFT, TitledBorder.ABOVE_BOTTOM,
					new Font("Sans", Font.PLAIN, 11));
			slider.setBorder(tb);
			slider.setMajorTickSpacing((maxVal - minVal)/10 );
			slider.setPaintTicks(true);
			slider.addChangeListener(this);
			
			return slider;
		}
        
        //////////////////////
        // SLIDER BESCHRIFTUNG
        private void setSliderTitle(JSlider slider, String str) {
			TitledBorder tb = new TitledBorder(BorderFactory.createEtchedBorder(),
				str, TitledBorder.LEFT, TitledBorder.ABOVE_BOTTOM,
					new Font("Sans", Font.PLAIN, 11));
			slider.setBorder(tb);
		}

		public void stateChanged( ChangeEvent e ){
			JSlider slider = (JSlider)e.getSource();

			if (slider == jSliderBrightness) {
				
				brightness = slider.getValue();
				String str = "Helligkeit " + (brightness); 
				setSliderTitle(jSliderBrightness, str); 
				
			}
			
			if (slider == jSliderContrast) {
				contrast = slider.getValue();
				String str = "Kontrast " + contrast; 
				setSliderTitle(jSliderContrast, str); 
			}
			
			if (slider == jSliderSaturation) {
				saturation = slider.getValue();
				String str = "Saettigung " + saturation; 
				setSliderTitle(jSliderSaturation, str); 
				System.out.println("saturation: " + saturation);
			}
			
			if (slider == jSLiderHue) {
				hue = slider.getValue();
				String str = "Farbton " + hue; 
				hue = toRadians(hue);
				setSliderTitle(jSLiderHue, str); 
			}
			
			changePixelValues(imp.getProcessor());
			
			imp.updateAndDraw();
		}
		//
		///////////////////////
		
		private void changePixelValues(ImageProcessor ip) {
			
			// Array fuer den Zugriff auf die Pixelwerte
			int[] pixels = (int[])ip.getPixels();
			
			for (int y=0; y<height; y++) {
				for (int x=0; x<width; x++) {
					int pos = y*width + x;
					int argb = origPixels[pos];  // Lesen der Originalwerte 
					
					int r = (argb >> 16) & 0xff;
					int g = (argb >>  8) & 0xff;
					int b =  argb        & 0xff;

					
					// anstelle dieser drei Zeilen spaeter hier die Farbtransformation durchfuehren,
					// die Y Cb Cr -Werte veraendern und dann wieder zuruecktransformieren
					
					double luminanz = 0.299 * (double)r + 0.587 * (double)g + 0.114 * (double)b;
					double cb = -0.168736 * (double)r -0.331264 * (double)g + 0.5 * (double)b;
					double cr = 0.5 * (double)r - 0.418688 * (double)g - 0.081312 * (double)b;
					
					// Saettigung
					// veraenderung der Länge des Farbvektors
					cb *= saturation;
					cr *= saturation;
					
					// Farbton
					// Drehung des Farbvektors
					cb = cos(hue) * cb + sin(hue) * cr;
					cr = -sin(hue) * cb + cos(hue) * cr;
					
					r = (int)(luminanz + 1.402 * cr);
					g = (int)(luminanz - 0.3441 * cb - 0.7141 * cr);
					b = (int)(luminanz + 1.772 * cb);
					
					// Veraenderung der Helligkeit und des Kontrastes
					r = (int)(contrast * (r - 128) + 128 + brightness);
					g = (int)(contrast * (g - 128) + 128 + brightness);
					b = (int)(contrast * (b - 128) + 128 + brightness);
					
					
					if (r > 255) {
						r = 255;
					} else if (r < 0) {
						r = 0;
					}
					if (g > 255) {
						g = 255;
					} else if (g < 0) {
						g = 0;
					}
					if (b > 255) {
						b = 255;
					} else if (b < 0) {
						b = 0;
					}
					
					pixels[pos] = (0xFF<<24) | (r<<16) | (g<<8) | b;
					
				
				}
			}
		}
		
    } // CustomWindow inner class
} 
