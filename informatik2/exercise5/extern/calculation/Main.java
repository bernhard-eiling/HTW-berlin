/*
 * Calculator Main 
 *
 * Developed for "Rethinking CS101", a project of Lynn Andrea Stein's AP Group.
 * For more information, see <a href="http://www.ai.mit.edu/projects/cs101">the
 * CS101 homepage</a> or email <las@ai.mit.edu>.
 *
 * Copyright (C) 1996 Massachusetts Institute of Technology.
 * Please do not redistribute without obtaining permission.
 */
package calculation;

import java.lang.reflect.Constructor;  // Nutshell 2nd ed, pp 483-484
import java.applet.*;
import java.awt.event.*;
import java.awt.*;										 
/**
 * This is a basic skeleton application which launches the necessary
 * code for a four-function calculator.  It relies on a (Runnable)
 * ButtonHandler to repeatedly call getButton() and consume the
 * buttonIDs that the CalculatorGUI object produces.
 *
 * <P>Copyright (c) 1998 Massachusetts Institute of Technology
 *
 * @author  Emil Sit, sit@mit.edu
 * @author  Lynn Andrea Stein, las@ai.mit.edu
 * @author  Craig Henderson, craigh@ai.mit.edu
 * @author  Natasha Olchanski, natashao@ai.mit.edu
 * @version: $Id: Main.java,v 1.6 1998/07/24 16:37:15 tparnell Exp $
 *
 * @see Calculator
 * @see CalculatorGUI
 * @see ButtonHandler
 * @see cs101.util.semaphore.IntBuffer
 *
 */
public class Main extends Applet {

	/** CalculatorGUI for use when we're started as an Applet. */
	/* private */ CalculatorGUI mygui;  // !private for bug in JDK

	/**
	 * Prevent instantiation.
	 */
	private Main() {}

	/**
	 * Print the usage information to the standard error stream, and quit.
	 */
	public static void printUsageAndQuit() {
		System.err.println( "\n  Only one argument is expected:" +
		"\n\t  the name of your button handler.\n");
		System.exit(1);
	}

	/**
	 * Print the exception and any other debugging information to the
	 * standard error stream, and quit.
	 */
	public static void printExceptionAndQuit(Exception e) {
		e.printStackTrace( System.err );
		System.exit(1);
	}

	/**
	 * Expects a single argument on the command line, which is
	 * the name of the student's button handler class.
	 * The student's class should have a constructor that accepts
	 * either a Calculator, or nothing at all.
	 */
	public static void main (String[] argv) {

		// Only one argument is expected.
		if (argv.length != 1) {
			printUsageAndQuit();
		} else {

			// these must be declared outside the try-block.
			Class myClass = null;
			Constructor constructor = null;
			Object instance = null;
			try {

				// From the name on the command line, produce a Class.
				// Obtain from this Class the Constructor that takes
				// one argument, of type Calculator.

				myClass = Class.forName(argv[0]);
				constructor = myClass.getDeclaredConstructor(
						new Class[] { Calculator.class }  // anonymous array !
				);

				// Using this constructor, create an instance
				// of the student's class.  This is akin to calling:
				//
				//    new ButtonHandler( new CalculatorGUI() );
				//
				// where students may call the "ButtonHandler" what they wish.

				instance = constructor.newInstance(
						new Object[] { new CalculatorGUI() }  // anonymous array !
				);

			} catch (NoSuchMethodException e) {
				// If no such constructor could be found, then
				// call their no-arg constructor.
				try {		    
					instance = myClass.newInstance();		    
				} catch (Exception e2) {
					printExceptionAndQuit( e2 );
				}

			} catch (Exception e) {		
				// Consider any other exception to be fatal.
				printExceptionAndQuit( e );
			}
		}
	}

	/**
	 * Initilize the Calculator (as an Applet).  We don't have 
	 * the complications of main here because we're not worried about
	 * loading student's code. 
	 */
	public void init () {
		Button calcButton = new Button ("Show/Hide Calculator");
		this.mygui = new CalculatorGUI();
		ButtonHandler calc = new ButtonHandler (this.mygui);
		calcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (Main.this.mygui.isVisible()) 
					Main.this.mygui.setVisible(false);
				else Main.this.mygui.show();
			}
		});
		this.add (calcButton);
		this.setSize(100, 100);
	}
}


/* Comments:
 *
 * History:
 *     $Log: Main.java,v $
 *     Revision 1.6  1998/07/24 16:37:15  tparnell
 *     Placate new javadoc behavior
 *
 *     Revision 1.5  1998/07/21 16:36:24  tparnell
 *     added private Main() to prevent instantiation.
 *     added workaround for inner class bug in JDK.
 *
 *     Revision 1.4  1998/07/20 18:04:19  natashao
 *     Added init() method to allow Calculator to run as an Applet.
 *
 *     Revision 1.3  1998/07/06 19:06:56  tparnell
 *     *** empty log message ***
 *
 *     Revision 1.2  1998/06/07 02:31:30  craigh
 *     Significant changes to Main.  Namely, students no longer have to name
 *     their class "ButtonHandler".  Students provide a class name on the
 *     command line, permitting reflection to be used.  If their class has
 *     a constructor that takes one argument of type Calculator, it is invoked
 *     and given an instance of CalculatorGUI.  Otherwise, the no-arg constructor
 *     of their class is called.  Two methods were added to this class as well,
 *     for debugging purposes: printUsageAndQuit(), and printExceptionAndQuit().
 *
 *     Revision 1.1  1998/02/26 17:25:46  tparnell
 *     Reconstruction from hard drive failure.  Everything appears intact.
 *
 *     Revision 1.2  1997/07/16 14:15:22  tparnell
 *     *** empty log message ***
 *
 *     Revision 1.1  1996/10/04 16:20:23  las
 *     Transformed Calculator into an application and made it a package.  See
 *     STAFF_SETUP for which files are public.  To run, use Calculator.Main.
 *
 *     Specifics:
 *         Added Main.java, which starts the calculator program (both
 *     CalculatorGUI and ButtonHandler);
 *         Made Calculator an interface;
 *         Moved GUI implementation (previously in Calculator) to
 *     CalculatorGUI.
 *         Added clear button, which looks pretty gross right now.  (It can
 *     be deleted in a single line, though.)
 *
 *
 */




