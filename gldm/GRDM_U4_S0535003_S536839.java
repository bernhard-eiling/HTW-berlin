import ij.*;
import ij.io.*;
import ij.process.*;
import ij.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;
import static java.lang.Math.*;

public class GRDM_U4_S0535003_S536839 implements PlugInFilter {

	protected ImagePlus imp;
	final static String[] choices = { "Wischen", "Weiche Blende",
			"Schieb-Blende", "Overlay", "Jalousie", "Chroma Key", "Extra" };

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_RGB + STACK_REQUIRED;
	}

	public static void main(String args[]) {
		ImageJ ij = new ImageJ(); // neue ImageJ Instanz starten und anzeigen
		ij.exitWhenQuitting(true);

		IJ.open("/Users/barthel/HTW/internet/meineWebseite/veranstaltungen/GLDM/uebungen/uebung4/StackB.zip");

		GRDM_U4_S0535003_S536839 sd = new GRDM_U4_S0535003_S536839();
		sd.imp = IJ.getImage();
		ImageProcessor B_ip = sd.imp.getProcessor();
		sd.run(B_ip);
	}

	public void run(ImageProcessor B_ip) {
		// Film B wird uebergeben
		ImageStack stack_B = imp.getStack();

		int length = stack_B.getSize();
		int width = B_ip.getWidth();
		int height = B_ip.getHeight();

		// ermoeglicht das Laden eines Bildes / Films
		Opener o = new Opener();
		OpenDialog od_A = new OpenDialog("Auswählen des 2. Filmes ...", "");

		// Film A wird dazugeladen
		String dateiA = od_A.getFileName();
		if (dateiA == null)
			return; // Abbruch
		String pfadA = od_A.getDirectory();
		ImagePlus A = o.openImage(pfadA, dateiA);
		if (A == null)
			return; // Abbruch

		ImageProcessor A_ip = A.getProcessor();
		ImageStack stack_A = A.getStack();

		if (A_ip.getWidth() != width || A_ip.getHeight() != height) {
			IJ.showMessage("Fehler", "Bildgrößen passen nicht zusammen");
			return;
		}

		// Neuen Film (Stack) "Erg" mit der kleineren Laenge von beiden erzeugen
		length = Math.min(length, stack_A.getSize());

		ImagePlus Erg = NewImage.createRGBImage("Ergebnis", width, height,
				length, NewImage.FILL_BLACK);
		ImageStack stack_Erg = Erg.getStack();

		// Dialog fuer Auswahl des Ueberlagerungsmodus
		GenericDialog gd = new GenericDialog("Überlagerung");
		gd.addChoice("Methode", choices, "");
		gd.showDialog();

		int methode = 0;
		String s = gd.getNextChoice();
		if (s.equals("Wischen"))
			methode = 1;
		if (s.equals("Weiche Blende"))
			methode = 2;
		if (s.equals("Chroma Key"))
			methode = 3;
		if (s.equals("Extra"))
			methode = 4;
		if (s.equals("Schieb-Blende"))
			methode = 5;
		if (s.equals("Overlay"))
			methode = 6;
		if (s.equals("Jalousie"))
			methode = 7;

		// Arrays fuer die einzelnen Bilder
		int[] pixels_B;
		int[] pixels_A;
		int[] pixels_Erg;
		
		// CHROMA KEY
		// orange color for keying
		int keyR = 255;
		int keyG = 199;
		int keyB = 46;
		// threshold for keying
		double threshold = 140.d;
		


		// Schleife ueber alle Bilder
		for (int z = 1; z <= length; z++) {
			pixels_B = (int[]) stack_B.getPixels(z);
			pixels_A = (int[]) stack_A.getPixels(z);
			pixels_Erg = (int[]) stack_Erg.getPixels(z);

			float rate = (float)(z - 2)/ length;
			float rate2 = (float)(z)/ length;


			int pos = 0;
			for (int y = 0; y < height; y++)
				for (int x = 0; x < width; x++, pos++) {
					int cA = pixels_A[pos];
					int rA = (cA & 0xff0000) >> 16;
					int gA = (cA & 0x00ff00) >> 8;
					int bA = (cA & 0x0000ff);

					int cB = pixels_B[pos];
					int rB = (cB & 0xff0000) >> 16;
					int gB = (cB & 0x00ff00) >> 8;
					int bB = (cB & 0x0000ff);

					// Wischen

					if (methode == 1) {
						if (y + 1 > (z - 1) * (double) width / (length - 1))
							pixels_Erg[pos] = pixels_B[pos];
						else
							pixels_Erg[pos] = pixels_A[pos];
					}

					// Weiche Blende

					if (methode == 2) {
						

						int r = (int) (rA * rate2) + (int) (rB * (1 - rate2));
						int g = (int) (gA * rate2) + (int) (gB * (1 - rate2));
						int b = (int) (bA * rate2) + (int) (bB * (1 - rate2));

						pixels_Erg[pos] = 0xFF000000 + ((r & 0xff) << 16)
								+ ((g & 0xff) << 8) + (b & 0xff);

					}

					
					// Chroma Key
					
					if (methode == 3) {

						double colorDelta = Math.sqrt((keyR - rA) * (keyR - rA) + (keyG - gA) * (keyG - gA) + (keyB - bA) * (keyB - bA));
						
						if(colorDelta < threshold) {
							pixels_Erg[pos] = pixels_B[pos];
						} else {
							pixels_Erg[pos] = pixels_A[pos];
						}
					}

					
					// Extra
					
					if (methode == 4) {

						if (((x - width/2) * (x - width/2) + ( y - height/2) * (y - height/2)) > (width/2 * width/2 + height/2 * height/2) * rate) {
							pixels_Erg[pos] = pixels_B[pos];
						} else {
							pixels_Erg[pos] = pixels_A[pos];
						}
					}

					
					// Schieb-Blende
					
					if (methode == 5) {
						
						int zMapping = (int)((z - 1) * (double) width / (length - 1));
						
						if (x + 1 > zMapping) {
							pixels_Erg[pos] = pixels_B[y * width + x - zMapping];
						} else {
							pixels_Erg[pos] = pixels_A[y * width + x + (width - zMapping)];
						}
						
					
					}
					
					
					// Overlay
					
					if(methode == 6) {
						int r;
						int g;
						int b;
						
						if(rB <= 128) {
							r = rB * rA / 128;
						} else {
							r = 255 - ((255 - rA) * (255 - rB)) /128;
						}
						if(gB <= 128) {
							g = gB * gA / 128;
						} else {
							g = 255 - ((255 - gA) * (255 - gB)) /128;
						}
						if(bB <= 128) {
							b = bB * bA / 128;
						} else {
							b = 255 - ((255 - bA) * (255 - bB)) /128;
						}

						pixels_Erg[pos] = 0xFF000000 + ((r & 0xff) << 16)
								+ ((g & 0xff) << 8) + (b & 0xff);
					}
					
					
					// Jalousie
					
					if(methode == 7) {
						
						if(x % 20 > 20 * rate) {
							pixels_Erg[pos] = pixels_B[pos];
						} else {
							pixels_Erg[pos] = pixels_A[pos];
						}
					}
				}
		}

		// neues Bild anzeigen
		Erg.show();
		Erg.updateAndDraw();

	}

}
