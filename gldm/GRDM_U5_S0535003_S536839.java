import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.NewImage;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import static java.lang.Math.*;

public class GRDM_U5_S0535003_S536839 implements PlugInFilter {

	protected ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		if (arg.equals("about")) {
			showAbout();
			return DONE;
		}
		return DOES_RGB + NO_CHANGES;
		// kann RGB-Bilder und veraendert das Original nicht
	}

	public static void main(String args[]) {
		ImageJ ij = new ImageJ(); // neue ImageJ Instanz starten und anzeigen
		ij.exitWhenQuitting(true);

		IJ.open("component.jpg");

		GRDM_U5_S0535003_S536839 sd = new GRDM_U5_S0535003_S536839();
		sd.imp = IJ.getImage();
		ImageProcessor B_ip = sd.imp.getProcessor();
		sd.run(B_ip);
	}

	public void run(ImageProcessor B_ip) {

		String[] dropdownmenue = { "Kopie", "Pixelwiederholung", "Bilinear" };

		GenericDialog gd = new GenericDialog("scale");
		gd.addChoice("Methode", dropdownmenue, dropdownmenue[0]);
		gd.addNumericField("Hoehe:", 500, 0);
		gd.addNumericField("Breite:", 400, 0);

		gd.showDialog();

		int methode = 0;
		String s = gd.getNextChoice();
		if (s.equals("Kopie"))
			methode = 1;
		if (s.equals("Pixelwiederholung"))
			methode = 2;
		if (s.equals("Bilinear"))
			methode = 3;

		int height_n = (int) gd.getNextNumber(); // _n fuer das neue skalierte
													// Bild
		int width_n = (int) gd.getNextNumber();

		int width = B_ip.getWidth(); // Breite bestimmen
		int height = B_ip.getHeight(); // Hoehe bestimmen
		System.out.println("Höhe: " + height_n + " | Breite: " + width_n);
		// height_n = height;
		// width_n = width;

		ImagePlus neu = NewImage.createRGBImage("Skaliertes Bild", width_n,
				height_n, 1, NewImage.FILL_BLACK);

		ImageProcessor ip_n = neu.getProcessor();

		if (methode == 1) {
			int[] pix = (int[]) B_ip.getPixels();
			int[] pix_n = (int[]) ip_n.getPixels();

			// Schleife ueber das neue Bild
			for (int y_n = 0; y_n < height_n; y_n++) {
				for (int x_n = 0; x_n < width_n; x_n++) {

					int y = y_n;
					int x = x_n;

					if (y < height && x < width) {
						int pos_n = y_n * width_n + x_n;
						int pos = y * width + x;

						pix_n[pos_n] = pix[pos];
					}
				}
			}
		}

		if (methode == 2) {

			float stepHeight = (float) height / height_n;
			float stepWidth = (float) width / width_n;

			int[] pix = (int[]) B_ip.getPixels();
			int[] pix_n = (int[]) ip_n.getPixels();

			// Schleife ueber das neue Bild
			for (int y_n = 0; y_n < height_n; y_n++) {
				for (int x_n = 0; x_n < width_n; x_n++) {

					int y = (int) (((float) y_n) * stepHeight);
					int x = (int) (((float) x_n) * stepWidth);

					if (y < height && x < width) {
						int pos_n = y_n * width_n + x_n;
						int pos = y * width + x;

						pix_n[pos_n] = pix[pos];
					}
				}
			}
		}

		if (methode == 3) {

			double stepHeight = (double) height / height_n;
			double stepWidth = (double) width / width_n;

			int[] pix = (int[]) B_ip.getPixels();
			int[] pix_n = (int[]) ip_n.getPixels();

			// Schleife ueber das neue Bild
			for (int y_n = 0; y_n < height_n; y_n++) {
				for (int x_n = 0; x_n < width_n; x_n++) {

					double y_double = ((double) y_n) * stepHeight;
					double x_double = ((double) x_n) * stepWidth;

					int x_a = (int) floor(x_double);
					int y_a = (int) floor(y_double);

					int x_b = (int) ceil(x_double);
					int y_b = (int) floor(y_double);

					int x_c = (int) floor(x_double);
					int y_c = (int) ceil(y_double);

					int x_d = (int) ceil(x_double);
					int y_d = (int) ceil(y_double);

					double h = x_double - x_a;
					double v = y_double - y_a;

					
					
					if (y_d < height && x_d < width) {
						
					
						int cA = pix[y_a * width + x_a];
						int rA = (cA & 0xff0000) >> 16;
						int gA = (cA & 0x00ff00) >> 8;
						int bA = (cA & 0x0000ff);

						int cB = pix[y_b * width + x_b];
						int rB = (cB & 0xff0000) >> 16;
						int gB = (cB & 0x00ff00) >> 8;
						int bB = (cB & 0x0000ff);

						int cC = pix[y_c * width + x_c];
						int rC = (cC & 0xff0000) >> 16;
						int gC = (cC & 0x00ff00) >> 8;
						int bC = (cC & 0x0000ff);

						int cD = pix[y_d * width + x_d];
						int rD = (cD & 0xff0000) >> 16;
						int gD = (cD & 0x00ff00) >> 8;
						int bD = (cD & 0x0000ff);

						int rNew = (int) ((double) rA * (1.0d - h) * (1.0d - v) + (double) rB * h * (1.0d - v) + (double) rC * (1.0d - h) * v + (double) rD * h * v);
						int gNew = (int) ((double) gA * (1.0d - h) * (1.0d - v) + (double) gB * h * (1.0d - v) + (double) gC * (1.0d - h) * v + (double) gD * h * v);
						int bNew = (int) ((double) bA * (1.0d - h) * (1.0d - v) + (double) bB * h * (1.0d - v) + (double) bC * (1.0d - h) * v + (double) bD * h * v);
					
						pix_n[y_n * width_n + x_n] = 0xFF000000 + ((rNew & 0xff) << 16) + ((gNew & 0xff) << 8) + (bNew & 0xff);
					}

				}
			}
		}

		// neues Bild anzeigen
		neu.show();
		neu.updateAndDraw();
	}

	void showAbout() {
		IJ.showMessage("");
	}
}
