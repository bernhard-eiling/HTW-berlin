import ij.ImageJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.NewImage;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

//erste Uebung (elementare Bilderzeugung)

public class GLDM_U1_S0535003 implements PlugIn {
	
	final static String[] choices = {
		"Schwarzes Bild",
		"Gelbes Bild",
		"Schwarz/Weiss Verlauf",
		"Horiz. Schwarz/Rot vert. Schwarz/Blau Verlauf",
		"Italienische Fahne",
		"Bahamische Fahne",
		"Japanische Fahne",
		"Japanische Fahne mit weichen Kanten",
		"Schachbrett"
	};
	
	private String choice;
	
	public static void main(String args[]) {
		ImageJ ij = new ImageJ(); // neue ImageJ Instanz starten und anzeigen 
		ij.exitWhenQuitting(true);
		
		GLDM_U1_S0535003 imageGeneration = new GLDM_U1_S0535003();
		imageGeneration.run("");
	}
	
	public void run(String arg) {
		
		int width  = 560;  // Breite
		int height = 400;  // Hoehe
		
		// RGB-Bild erzeugen
		ImagePlus imagePlus = NewImage.createRGBImage("GLDM_U1", width, height, 1, NewImage.FILL_BLACK);
		ImageProcessor ip = imagePlus.getProcessor();
		
		// Arrays fuer den Zugriff auf die Pixelwerte
		int[] pixels = (int[])ip.getPixels();
		
		dialog();
		
		////////////////////////////////////////////////////////////////
		// Hier bitte Ihre Aenderungen / Erweiterungen
		
		if ( choice.equals("Schwarzes Bild") ) {
			
			// Schleife ueber die y-Werte
			for (int y=0; y<height; y++) {
				// Schleife ueber die x-Werte
				for (int x=0; x<width; x++) {
					int pos = y*width + x; // Arrayposition bestimmen
					
					int r = 0;
					int g = 0;
					int b = 0;
					
					// Werte zurueckschreiben
					pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
				}
			}
		}
		
		if ( choice.equals("Gelbes Bild") ) {
			
			// Schleife ueber die y-Werte
			for (int y=0; y<height; y++) {
				// Schleife ueber die x-Werte
				for (int x=0; x<width; x++) {
					int pos = y*width + x; // Arrayposition bestimmen
					
					// just changed the color to yellow
					int r = 255;
					int g = 255;
					int b = 0;
					
					// Werte zurueckschreiben
					pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
				}
			}
		}
		
		if ( choice.equals("Schwarz/Weiss Verlauf") ) {
			
			int r = 0;
			int g = 0;
			int b = 0;
			
			float slope = (float)255 / width;
			
			// Schleife ueber die y-Werte
			for (int y=0; y<height; y++) {
				// Schleife ueber die x-Werte
				for (int x=0; x<width; x++) {
					int pos = y*width + x; // Arrayposition bestimmen
					
					// color changes gradually as x increases. theres a slope to measure the gradient to the actual width of the picture drawn
					r = (int)((float)x * slope);
					g = (int)((float)x * slope);
					b = (int)((float)x * slope);
					
					// Werte zurueckschreiben
					pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
				}
			}
		}
		
		if ( choice.equals("Horiz. Schwarz/Rot vert. Schwarz/Blau Verlauf") ) {
			
			int r = 0;
			int g = 0;
			int b = 0;
			
			float slopeW = (float)255 / width;
			float slopeH = (float)255 / height;
			
			// Schleife ueber die y-Werte
			for (int y=0; y<height; y++) {
				// Schleife ueber die x-Werte
				for (int x=0; x<width; x++) {
					int pos = y*width + x; // Arrayposition bestimmen
					
					// same as with the example above. Theres an additional colorchannel which reacts to the y-dimension
					r = (int)((float)x * slopeW);
					g = 0;
					b = (int)((float)y * slopeH);
					
					// Werte zurueckschreiben
					pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
				}
			}
		}
		
		if ( choice.equals("Italienische Fahne") ) {
			
			int r = 0;
			int g = 0;
			int b = 0;
			// Schleife ueber die y-Werte
			for (int y=0; y<height; y++) {
				// Schleife ueber die x-Werte
				for (int x=0; x<width; x++) {
					
					int pos = y*width + x; // Arrayposition bestimmen
					
					// three conditions divide the 3 colorfields of the flag
					if (x <= width/3) {
						r = 0;
						g = 255;
						b = 0;
					} else if (x > width/3 * 2) {
						r = 255;
						g = 0;
						b = 0;
					} else {
						r = 255;
						g = 255;
						b = 255;
					}
					
					// Werte zurueckschreiben
					pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
				}
			}
		}
		
		if ( choice.equals("Bahamische Fahne") ) {
			
			int r = 0;
			int g = 0;
			int b = 0;
			// Schleife ueber die y-Werte
			for (int y=0; y<height; y++) {
				// Schleife ueber die x-Werte
				for (int x=0; x<width; x++) {
					
					int pos = y*width + x; // Arrayposition bestimmen
					
					// 5 conditions for drawing the flag. x > y assures that there will be a 45 degree angle for the triangle
					if (y <= height/3 && x > y) {
						r = 0;
						g = 255;
						b = 255;
					} else if (y > height / 3 * 2 && x > (height - y)) {
						r = 0;
						g = 255;
						b = 255;
					} else if (y > height / 3 && y <= height/2 && x > y ) {
						r = 255;
						g = 255;
						b = 0;
					} else if (y > height/2 && y <= height / 3 * 2 && x > (height - y)){
						r = 255;
						g = 255;
						b = 0;
					} else {
						r = 0;
						g = 0;
						b = 0;
					}
					
					// Werte zurueckschreiben
					pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
				}
			}
		}
		
		if ( choice.equals("Japanische Fahne") ) {
			
			
			// Schleife ueber die y-Werte
			for (int y=0; y<height; y++) {
				// Schleife ueber die x-Werte
				for (int x=0; x<width; x++) {
					
					int r = 0;
					int g = 0;
					int b = 0;
					
					int radius = height / 4;
					int pos = y*width + x; // Arrayposition bestimmen
					
					// theorem of phytagoras provides a measurement that every pixel nearer to the circles origin will be drawn in red and every pixel farther away in white
					// by subtracting width and height respectively the origin of the circle gets translated to the middle of the picture
					if (((x - width/2) * (x - width/2)) + (( y - height/2) * (y - height/2)) < radius * radius) {
						r = 255;
						g = 0;
						b = 0;
					} else {
						r = 255;
						g = 255;
						b = 255;
					}
					
					// Werte zurueckschreiben
					pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
				}
			}
		}
		
		if ( choice.equals("Japanische Fahne mit weichen Kanten") ) {
			
			float slope = (float)255 / width*2;
			
			// Schleife ueber die y-Werte
			for (int y=0; y<height; y++) {
				// Schleife ueber die x-Werte
				for (int x=0; x<width; x++) {
					
					int r = 255;
					int g = 255;
					int b = 255;
					
					int pos = y*width + x; // Arrayposition bestimmen
					
					// similar to the example above. but now the radius of the circle gets larger by every iteration of the for loop.
					// starting to paint plain red in the middle and as the radius extends gradually fading into white.
					for (int i = width/2; i > 0; i--) {
						if (((x - width/2) * (x - width/2)) + (( y - height/2) * (y - height/2)) >= (width/2 - i) * (width/2 - i)) {
							r = 255;
							g = (int)((float)255 - (float)i * slope);
							b = (int)((float)255 - (float)i * slope);
						}
					}
					
					// Werte zurueckschreiben
					pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
				}
			}
		}
		
if ( choice.equals("Schachbrett") ) {
	
			// number of fields per row or column
			int numFields = 8;
	
			int xCounter = 0;
			int yCounter = 0;
			int xNumFields = width/numFields;
			int yNumFields = height/numFields;
			boolean black = true;
			boolean invert = true;
			int r = 0;
			int g = 0;
			int b = 0;
			
			// Schleife ueber die y-Werte
			for (int y=0; y<height; y++) {
				
				
				
				// the invert will swap the color of a field in the y dimension. invert swaps when the y counter exceeds the desired field y-size
				if(yCounter > yNumFields) {
					if(invert) {
						invert = false;
					} else {
						invert = true;
					}
					yCounter = 0;
				}
				yCounter++;
				
				// Schleife ueber die x-Werte
				for (int x=0; x<width; x++) {
					
					int pos = y*width + x; // Arrayposition bestimmen
					
					//the counter counts the pixels drawn. after its larger then the desired x-fieldsize it will swap the color and will reset to zero to count again
					if (xCounter >= xNumFields) {
						if(black) {
							black = false;
						} else {
							black = true;
						}
						xCounter = 0;
					}
					xCounter++;
					
					
					if (black && !invert) {
						r = 0;
						g = 0;
						b = 0;
					} else if (!black && !invert){
						r = 255;
						g = 255;
						b = 255;
					} else if (black && invert) {
						r = 255;
						g = 255;
						b = 255;
					} else if (!black && invert) {
						r = 0;
						g = 0;
						b = 0;
					}
					
					
					
					
					// Werte zurueckschreiben
					pixels[pos] = 0xFF000000 | (r << 16) | (g << 8) |  b;
				}
			}
		}
		
		
		////////////////////////////////////////////////////////////////////
		
		// neues Bild anzeigen
		imagePlus.show();
		imagePlus.updateAndDraw();
	}
	
	
	private void dialog() {
		// Dialog fuer Auswahl der Bilderzeugung
		GenericDialog gd = new GenericDialog("Bildart");
		
		gd.addChoice("Bildtyp", choices, choices[8]);
		
		
		gd.showDialog();	// generiere Eingabefenster
		
		choice = gd.getNextChoice(); // Auswahl uebernehmen
		
		if (gd.wasCanceled())
			System.exit(0);
	}
}

